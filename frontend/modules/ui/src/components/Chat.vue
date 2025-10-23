<script setup lang="ts">
import { ref, watch, nextTick, onMounted, onBeforeUnmount, computed } from 'vue'
import { storeToRefs } from 'pinia'
import { ChatMessage as ChatMessageType, Booking } from '@symfoititis-frontend-monorepo/interfaces'
import { useUserStore, useChatStore, useFileStore } from '@symfoititis-frontend-monorepo/stores'
import ChatMessage from './ChatMessage.vue'
import { useChatDataService } from '@symfoititis-frontend-monorepo/core/services'

const props = defineProps<{
  booking: Booking
}>()

const chatStore = useChatStore()
const userStore = useUserStore()
const fileStore = useFileStore()
const { attachments } = storeToRefs(fileStore)
const { messages, connected, currentRoom } = storeToRefs(chatStore)
const { profile, department } = storeToRefs(userStore)

const { getMessages, sendMessage, uploadAttachments, readMessages } = useChatDataService()

const chatInputElement = ref<HTMLDivElement>()
const shiftDown = ref<boolean>(false)
const fileInput = ref<HTMLInputElement | null>(null)
const isTeacher = import.meta.env.VITE_KC_REALM === 'teacher'
const loadingNextBatch = ref<boolean>(false)
const sendingMessages = ref<boolean>(false)
let isProgrammaticScroll = true

const sendMessages = async () => {
  if (!chatInputElement.value) return
  sendingMessages.value = true
  const { room, c_id, s_id, t_id } = props.booking
  const recipientId = isTeacher ? s_id : t_id
  const chatInput = chatInputElement.value.innerText || ''
  if (chatInput.trim()) {
    const message: ChatMessageType = {
      room,
      c_id,
      recipient_id: recipientId,
      sender_id: profile.value.id!,
      dep_id: department.value.dep_id!,
      is_teacher: isTeacher,
      type: 'TEXT',
      content: chatInput.replaceAll(/ +/g, ' '),
    }
    sendMessage(message)
  }
  if (attachments.value.length > 0) {
    const filenames = await uploadAttachments(room)
    for (const filename of filenames) {
      const message: ChatMessageType = {
        room,
        c_id,
        recipient_id: recipientId,
        sender_id: profile.value.id!,
        dep_id: department.value.dep_id!,
        is_teacher: isTeacher,
        type: 'ATTACHMENT',
        content: filename
      }
      sendMessage(message)
    }
    attachments.value = []
  }
  chatInputElement.value.innerText = ''
  sendingMessages.value = false
}

const handleFileChange = () => {
  if (!fileInput.value) return
  attachments.value = Array.from(fileInput.value.files || [])
}

const handleFileDelete = (idx: number) => {
  attachments.value = attachments.value.toSpliced(idx, 1)
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
}

const handleKeyPress = (e: KeyboardEvent) => {
  if (e.key == 'Enter' && !shiftDown.value) {
    e.preventDefault()
    sendMessages()
  }
}

const scrollAt = (top: number, behavior: 'smooth' | 'instant') => {
  isProgrammaticScroll = true
  nextTick(() => {
    const pageWrapper = document.getElementById('page-wrapper')
    if (pageWrapper) {
      pageWrapper.scrollTo({
        top: top,
        behavior
      })
    }
  })
  setTimeout(() => {
    isProgrammaticScroll = false
  }, 500)
}

const handleScrollEvent = async () => {
  if (isProgrammaticScroll) return
  const pageWrapper = document.getElementById("page-wrapper")
  if (!pageWrapper) return
  const height = pageWrapper.scrollHeight
  if (pageWrapper.scrollTop == 0) {
    loadingNextBatch.value = true
    const { c_id, s_id, t_id } = props.booking
    const participantId = isTeacher ? s_id : t_id
    await getMessages(c_id, participantId, true)
    scrollAt(pageWrapper.scrollHeight - height, 'instant')
  }
}


const setScrollListener = () => {
  const pageWrapper = document.getElementById('page-wrapper')
  if (pageWrapper) {
    pageWrapper.addEventListener('scroll', handleScrollEvent);
    scrollAt(pageWrapper.scrollHeight, 'smooth')
  }
}

const removeScrollListener = () => {
  const pageWrapper = document.getElementById('page-wrapper')
  if (pageWrapper) {
    pageWrapper.removeEventListener('scroll', handleScrollEvent);
  }
}

const ifLoadedPreviousMessagesScrollToStart = () => {
  if (loadingNextBatch.value) {
    loadingNextBatch.value = false
    return
  }
  const pageWrapper = document.getElementById("page-wrapper")
  if (pageWrapper) {
    scrollAt(pageWrapper.scrollHeight, 'smooth')
  }
}

const getLastRecievedMessage = () => {
  const receivedMessages = messages.value.filter((m) => m.recipient_id == profile.value.id)
  return receivedMessages.toSorted((a, b) => b.message_id! - a.message_id!)[0]
}

const handleMessageRead = () => {
  const lastRecievedMessage = getLastRecievedMessage()
  if (lastRecievedMessage) {
    readMessages(lastRecievedMessage)
  }
}

watch(messages, () => {
  ifLoadedPreviousMessagesScrollToStart()
})

onMounted(() => {
  currentRoom.value = props.booking.room
  setScrollListener()
  handleMessageRead()
})

onBeforeUnmount(() => {
  removeScrollListener()
  currentRoom.value = ''
})

const getSeperatorTime = (createdAt: string | undefined) => {
  if (!createdAt) return ''
  const time = createdAt.split('T')[1]
  const fields = time.split(':')
  return fields[0] + ':' + fields[1]
}

const formatSeperatorDate = (createdAt: string | undefined) => {
  if (!createdAt) return ''
  const dateString = createdAt.split('T')[0]
  const [y, m, d] = dateString.split('-').map(s => parseInt(s, 10));
  const dt = new Date(y, m - 1, d);

  const today = new Date();
  const dow = today.getDay() === 0 ? 7 : today.getDay();
  const monday = new Date(today);
  monday.setDate(today.getDate() + (1 - dow));
  monday.setHours(0, 0, 0, 0);
  const sunday = new Date(monday);
  sunday.setDate(monday.getDate() + 6);
  sunday.setHours(23, 59, 59, 999);

  const WEEKDAYS = ['ΔΕΥ', 'ΤΡΙ', 'ΤΕΤ', 'ΠΕΜ', 'ΠΑΡ', 'ΣΑΒ', 'ΚΥΡ'];
  const MONTHS = ['ΙΑΝ', 'ΦΕΒ', 'ΜΑΡ', 'ΑΠΡ', 'ΜΑΙ', 'ΙΟΥΝ', 'ΙΟΥΛ', 'ΑΥΓ', 'ΣΕΠ', 'ΟΚΤ', 'ΝΟΕ', 'ΔΕΚ'];

  if (dt >= monday && dt <= sunday) {
    const idx = dt.getDay() === 0 ? 6 : dt.getDay() - 1;
    return WEEKDAYS[idx];
  }

  const dayNum = dt.getDate();
  const monthAbbrev = MONTHS[dt.getMonth()];
  let out = `${dayNum} ${monthAbbrev}`;
  if (dt.getFullYear() !== today.getFullYear()) {
    out += ` ${dt.getFullYear()}`;
  }
  return out;
}

const distinctDates = computed(() => {
  const d = [
    ...new Set(
      messages.value.map((m) => m.created_at?.split('T')[0])
    )
  ]
  return d.map((el) => {
    return {
      date: el,
      displayed: false
    }
  })
})

const displayDateSeperator = (createdAt: string | undefined) => {
  if (!createdAt) return false
  const date = createdAt.split('T')[0]
  return distinctDates.value.some((d) => {
    const condition = d.date == date && d.displayed == false
    if (condition) {
      d.displayed = true
    }
    return condition
  })
}
</script>

<template>
  <section id="chat-body-wrapper" class="chat-body-wrapper wrapper">
    <div class="chat-body-container">
      <div v-if="loadingNextBatch" class="loading-spinner-container">
        <svg class="pf-v5-c-spinner pf-m-xl" role="progressbar" viewBox="0 0 100 100" aria-label="Loading...">
          <circle class="pf-v5-c-spinner__path" cx="50" cy="50" r="45" fill="none" />
        </svg>
      </div>
      <div class="chat-body">
        <div class="chat-message-container" v-for="message in messages">
          <div v-if="displayDateSeperator(message.created_at)" class="date-seperator-container">
            <p class="seperator-line"></p>
            <span class="date-seperator-text">{{ formatSeperatorDate(message.created_at) }}, {{
              getSeperatorTime(message.created_at) }}</span>
            <p class="seperator-line"></p>
          </div>
          <ChatMessage :chatMessage="message" :isTeacher="isTeacher" />
        </div>
      </div>
    </div>
  </section>

  <section v-if="attachments.length > 0" class="chat-attachments-wrapper wrapper">
    <transition-group tag="ul" name="chat-attachment-list" class="chat-attachments-container content-width" appear>
      <li class="chat-attachment-item" v-for="(file, index) in attachments" :key="file.size" :data-index="index">
        <span>
          {{ file.name }}
        </span>
        <i @click="handleFileDelete(index)" class="fa fa-close"></i>
      </li>
    </transition-group>
  </section>

  <section class="chat-input-wrapper wrapper">
    <div v-if="connected" class="chat-input-container">
      <div class="icon">
        <form class="file-upload-form" @submit.prevent="">
          <label class="upload-container">
            <input ref="fileInput" type="file" name="file" id="file" @change="handleFileChange" hidden multiple />
            <i class="fa fa-paperclip"></i>
          </label>
        </form>
      </div>
      <div id="chatInput" @click.once="handleMessageRead" @keydown="handleKeyDown" @keyup="handleKeyUp"
        @keypress="handleKeyPress" class="chat-input" contenteditable="true" role="textbox" aria-multiline="true"
        spellcheck="true" data-placeholder="Type a message…" ref="chatInputElement"></div>
      <div v-if="!sendingMessages" @click="sendMessages" class="icon">
        <i class="fa fa-paper-plane"></i>
      </div>
      <div v-else class="icon">
        <svg class="pf-v5-c-spinner pf-m-xl" id="send-loading-spinner" role="progressbar" viewBox="0 0 100 100"
          aria-label="Loading...">
          <circle class="pf-v5-c-spinner__path" cx="50" cy="50" r="45" fill="none" />
        </svg>
      </div>
    </div>

    <div v-else class="chat-input-container content-width">
      <div class="pf-v5-c-skeleton"></div>
    </div>
    <div>

    </div>
  </section>
</template>

<style scoped>
.chat-body-wrapper {
  flex: 1;
}

.chat-body-container {
  height: 100%;
  width: 800px;
}

.chat-attachments-wrapper {
  background-color: var(--white);
  position: sticky;
  bottom: 50px;
}

.chat-attachments-container {
  display: flex;
  flex-direction: row;
  max-height: clamp(60px, 4vw, 110px);
  padding-left: 50px;
  overflow-x: auto;
}

.chat-input-wrapper {
  min-height: 70px;
  display: flex;
  align-items: baseline;
  position: sticky;
  bottom: 0px;
  margin: 0 auto;
  border-radius: 15px 15px 0 0;
  background-color: var(--white);
}

.pf-v5-c-skeleton {
  height: 2rem;
  width: 100%;
  border-radius: 15px;
}

.loading-spinner-container {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--orange);
}

#send-loading-spinner {
  width: 60%;
  height: 60%;
}

.pf-v5-c-spinner {
  --pf-v5-c-spinner--Color: var(--orange);
}

.chat-body {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: var(--white);
  margin: 1rem 0;
}

.chat-message-container {
  width: 100%;
}

.date-seperator-container {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.date-seperator-text {
  color: var(--orange);
  font-size: .7rem;
}

.seperator-line {
  width: 15%;
  height: 50%;
  border-top: var(--main-border);
  margin: 0 .5rem;
}

.chat-input-container {
  display: flex;
  height: fit-content;
  width: 804px;
  flex-direction: row;
  border: 1.5px solid var(--orange);
  border-radius: 15px;
}

.chat-input {
  flex-grow: 1;
  outline: none;
  background-color: var(--white);

  min-height: 32px;
  max-height: 200px;
  overflow-y: auto;

  display: flex;
  align-items: center;
  padding: 4px 8px;
  /* some breathing room */
  line-height: 1.4;
  /* keep lines nicely spaced */
}

.icon {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 50px;
  height: auto;
  cursor: pointer;
  font-size: 20px;
  color: var(--orange);
}

.file-upload-form,
.upload-container {
  cursor: pointer;
}

.upload-container {
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
}

.chat-attachment-list-leave-to {
  opacity: 0;
  transform: scale(0.5);
}

.chat-attachment-list-leave-active {
  transition: all 0.4s ease;
  position: absolute;
}

.chat-attachment-list-move {
  transition: all 0.3s ease;
}

.chat-attachment-wrapper {
  display: flex;
  align-items: center;
  position: relative;
  background-color: var(--white);
  min-height: clamp(50px, 3vw, 60px);
  overflow-x: auto;
}

.chat-attachment-container {
  display: flex;
  flex-direction: row;
  font-size: .95rem;
}

.chat-attachment-item {
  margin: 0rem 0.4rem;
  padding: 0.5rem 0.9rem;
  border: var(--main-border);
  border-top-right-radius: 14px;
  white-space: nowrap;
}

.current-chat-attachment-item {
  background-color: var(--orange);
}

.chat-attachment-link {
  color: var(--gray);
}

.fa-close {
  cursor: pointer;
  color: var(--orange);
  padding-left: .9rem;
}

@media screen and (max-width: 1800px) {
  .chat-attachments-container {
    font-size: 0.85rem;
  }
}

@media screen and (max-width: 1300px) {
  .chat-attachments-container {
    font-size: 0.8rem;
  }

  .chat-attachments-container {
    padding: 0.3rem 0.7rem;
  }

  .chat-body-container,
  .chat-input-container {
    width: clamp(350px, 70vw, 1500px);
  }
}
</style>