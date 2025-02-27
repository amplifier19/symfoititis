<script setup lang="ts">
import { ref, watch, nextTick } from 'vue'
import { storeToRefs } from 'pinia'
import { ChatMessage as ChatMessageType, Booking } from '@symfoititis-frontend-monorepo/interfaces'
import { useUserStore, useChatStore, useFileStore } from '@symfoititis-frontend-monorepo/stores'
import ChatMessage from './ChatMessage.vue'
import { useChatDataService } from '@symfoititis-frontend-monorepo/core/services'

const props = defineProps<{
    booking: Booking
    chatMessages: ChatMessageType[]
}>()

const chatStore = useChatStore()
const userStore = useUserStore()
const fileStore = useFileStore()
const { attachments } = storeToRefs(fileStore)
const { offset, sendCount, fetchCount, connected } = storeToRefs(chatStore)
const { profile, department } = storeToRefs(userStore)

const { getMessages, sendMessage, uploadAttachments } = useChatDataService()

const chatBody = ref<HTMLElement>()
const textArea = ref<HTMLElement>()
const chatInput = ref<string>('')
const shiftDown = ref<boolean>(false)
const fileInput = ref<HTMLInputElement | null>(null)
const isTeacher = import.meta.env.VITE_KC_REALM === 'teacher'
const loadingMessageBatch = ref<boolean>(false)

const getWeekDay = (date: string) => {
    const weekDays = ['Κυριακή', 'Δευτέρα', 'Τρίτη', 'Τετάρτη', 'Πέμπτη', 'Παρασκευή', 'Σάββατο']
    const bookingDate = new Date(date)
    return weekDays[bookingDate.getDay()]
}

const formatDate = (date: string) => {
    const fields = date.split('-')
    return `${fields.at(2)}/${fields.at(1)}`
}

const sendMessages = async () => {
    const { room, c_id, s_id, t_id } = props.booking
    const recipientId = isTeacher ? s_id : t_id
    if (chatInput.value.trim()) {
        const message: ChatMessageType = {
            room,
            c_id,
            recipient_id: recipientId,
            sender_id: profile.value.id!,
            dep_id: department.value.dep_id,
            is_teacher: isTeacher,
            type: 'TEXT',
            content: chatInput.value.replace(/[\r\n]+/g, ' '),
        }
        sendMessage(message)
        chatInput.value = ''
    }
    if (attachments.value.length > 0) {
        const filenames = await uploadAttachments(room)
        for (const filename of filenames) {
            const message: ChatMessageType = {
                room,
                c_id,
                recipient_id: recipientId,
                sender_id: profile.value.id!,
                dep_id: department.value.dep_id,
                is_teacher: isTeacher,
                type: 'ATTACHMENT',
                content: filename
            }
            sendMessage(message)
        }
        attachments.value = []
    }
}

const handleKeyDown = (e: KeyboardEvent) => {
    if (e.key == 'Shift') {
        shiftDown.value = true
    }
}

const handleKeyUp = (e: KeyboardEvent) => {
    if (e.key == 'Shift') {
        shiftDown.value = false
    }
    textArea.value!.style.height = textArea.value!.scrollHeight + 'px'
}

const handleKeyPress = (e: KeyboardEvent) => {
    if (e.key == 'Enter' && !shiftDown.value) {
        e.preventDefault()
        sendMessages()
        chatInput.value = ''
    }
}

const scrollToBottom = () => {
    nextTick(() => {
        chatBody.value!.scrollTop = chatBody.value!.scrollHeight
    })
}

const handleScroll = async () => {
    const top = chatBody.value!.scrollTop
    const height = chatBody.value!.scrollHeight
    if (top === 0) {
        const { c_id, s_id, t_id } = props.booking
        const participantId = isTeacher ? s_id : t_id
        if (offset.value > 0 && fetchCount.value === 16) {
            loadingMessageBatch.value = true
            await getMessages(c_id, participantId)
            loadingMessageBatch.value = false
        }
        chatBody.value!.scrollTop = chatBody.value!.scrollHeight - height
    }
}

const handleFileChange = () => {
    if (!fileInput.value) return
    attachments.value = Array.from(fileInput.value.files || [])
}

watch(props, (newProps, oldProps) => {
    if (offset.value === 16) {
        scrollToBottom()
    }
})
watch(sendCount, (newCount, oldCount) => {
    scrollToBottom()
})
</script>

<template>
    <div class="chat-container">
        <header class="chat-header">
            <section class="booking-info-container">
                <div class="date">{{ formatDate(props.booking.date) }}</div>
                <div class="name-day-time-container">
                    <span v-if="isTeacher" class="name">{{ props.booking.student_name }}</span>
                    <span v-else class="name">{{ props.booking.teacher_firstname }} {{ props.booking.teacher_lastname
                        }}</span>
                    <span class="day-time">{{ getWeekDay(props.booking.date) }} - {{ props.booking.start_time
                        }}:00</span>
                </div>
            </section>
            <section class="meet-btn-container">
                <button class="meet-btn">Meet</button>
            </section>
        </header>

        <section class="loading-spinner-container" v-if="loadingMessageBatch">
            <svg class="pf-v5-c-spinner pf-m-xl" role="progressbar" viewBox="0 0 100 100" aria-label="Loading...">
                <circle class="pf-v5-c-spinner__path" cx="50" cy="50" r="45" fill="none" />
            </svg>
        </section>

        <section ref="chatBody" @scroll="handleScroll" class="chat-body">
            <ChatMessage v-for="message in chatMessages" :chatMessage="message" :isTeacher="isTeacher" />
        </section>

        <section v-if="connected" class="chat-input-container">
            <div class="icon">
                <form class="file-upload-form" @submit.prevent="">
                    <label class="upload-container">
                        <input ref="fileInput" type="file" name="file" id="file" @change="handleFileChange" hidden
                            multiple />
                        <i class="fa fa-paperclip"></i>
                    </label>
                </form>
            </div>
            <textarea @keydown="handleKeyDown" @keyup="handleKeyUp" @keypress="handleKeyPress" ref="textArea"
                v-model="chatInput" class="chat-input" type="text">
            </textarea>
            <div @click="sendMessages" class="icon">
                <i class="fa fa-paper-plane"></i>
            </div>
        </section>
        <section v-else class="chat-input-container">
            <div class="pf-v5-c-skeleton"></div>
        </section>
        <section v-if="attachments.length > 0" class="file-name">
            <p v-for="file in attachments">
                {{ file?.name }}
            </p>
        </section>
    </div>
</template>

<style>
.pf-v5-c-skeleton {
    height: 2rem;
    width: 100%;
    border-radius: 15px;
}

.chat-container {
    width: 100%;
    display: flex;
    flex-direction: column;
}

.chat-header {
    display: flex;
    flex-direction: row;
    margin-bottom: 2rem;
}

.booking-info-container {
    display: flex;
    flex-direction: row;
}

.date {
    width: 50px;
    height: 50px;
    background-color: var(--orange);
    border-radius: 50px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
    margin-right: 1rem;
}

.name-day-time-container {
    display: flex;
    flex-direction: column;
}

.name {}

.day-time {
    color: var(--orange);
}

.meet-btn-container {
    display: flex;
    flex-grow: 1;
    justify-content: end;
}

.meet-btn {}

.loading-spinner-container {
    width: 100%;
    text-align: center;
    color: var(--orange);
}

.pf-v5-c-spinner {
    --pf-v5-c-spinner--Color: var(--orange);
}

.chat-body {
    overflow-y: scroll;
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 500px;
    background-color: var(--white);
    margin: 1rem 0;
}

.skeleton-container {
    width: calc(100% - 132px);
    height: 500px;
    margin: 1rem auto;
}

.chat-input-container {
    width: 100%;
    display: flex;
    height: fit-content;
    flex-direction: row;
    align-items: center;
    margin: 0 auto;
}

.chat-input {
    flex-grow: 1;
    height: 2rem;
    max-height: 8rem;
    resize: none;
    padding: 0 1rem;
    box-sizing: unset;
    border: 1.5px solid var(--orange);
    border-radius: 15px;
    outline: none;
    background-color: white;
}

.icon {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50px;
    height: 50px;
    cursor: pointer;
    font-size: 20px;
    color: var(--orange);
}

.icon:hover {
    color: var(--orange-shadow);
}

.file-upload-form,
.upload-container {
    width: 100%;
    height: 100%;
    cursor: pointer;
}

.upload-container {
    display: flex;
    justify-content: center;
    align-items: center;
    box-sizing: border-box;
}
</style>