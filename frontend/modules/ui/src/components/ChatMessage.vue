<script setup lang="ts">
import { ref } from 'vue'
import type { ChatMessage } from '@symfoititis-frontend-monorepo/interfaces';
import { useChatDataService } from '@symfoititis-frontend-monorepo/core/services';
import { useChatStore } from '@symfoititis-frontend-monorepo/stores';
import { storeToRefs } from 'pinia';

const props = defineProps<{
    chatMessage: ChatMessage
}>()

const { generatePresignedUrl } = useChatDataService()
const chatStore = useChatStore()
const { chatStats } = storeToRefs(chatStore)

const isTeacher = import.meta.env.VITE_KC_REALM === 'teacher'
const isAttachment = props.chatMessage.type === 'ATTACHMENT'
const attachmentUrl = ref<string>('')
const linkRef = ref<HTMLAnchorElement>()
const urlExpiresAt = ref<Date>(new Date())

const XOR = (param1: boolean, param2: boolean) => {
    return param1 ? !param2 : param2
}

const openAttachment = async () => {
    const urlExpired = Date.now() >= urlExpiresAt.value.getTime()
    if (attachmentUrl.value === '' || urlExpired) {
        attachmentUrl.value = await generatePresignedUrl(props.chatMessage.room, props.chatMessage.content)
        urlExpiresAt.value = new Date(Date.now() + 1000 * 1000)
    }
    if (linkRef.value) {
        linkRef.value.href = attachmentUrl.value
        linkRef.value.click()
    }
}
</script>

<template>
    <section v-if="XOR(isTeacher, props.chatMessage.is_teacher)" class="message-container regular-text"
        id="recipient-message-container">
        <button v-if="isAttachment" @click="openAttachment" class="content attachment" id="recipient-content">
            <i class="fa fa-file"></i>
            <span class="message-text regular-text" @click="openAttachment">
                {{ props.chatMessage.content }}
            </span>
            <a ref="linkRef" :href="attachmentUrl" target="_blank" rel="noopener noreferrer" style="display: none;"></a>
        </button>
        <div v-else class="content" id="recipient-content">
            <span class="message-text regular-text">
                {{ props.chatMessage.content }}
            </span>
        </div>
    </section>
    <section v-else class="message-container regular-text" id="sender-message-container">
        <div v-if="props.chatMessage.state == 'error'" class="arrow-cnt">
            <i class="fa fa-exclamation-circle"></i> 
        </div>
        <div v-else-if="chatStats.some((s) => s.otherLastReadMessageId == props.chatMessage.message_id)" class="arrow-cnt">
            <i class="fa fa-check read-check-arrow"></i> 
            <i class="fa fa-check read-check-arrow"></i> 
        </div>
        <div v-else-if="props.chatMessage.state == 'sent' && (props.chatMessage.message_id || 0) > (chatStats.find((s) => s.room == props.chatMessage.room)?.otherLastReadMessageId || 0)"
            class="arrow-cnt" 
        >
            <i class="fa fa-check read-check-arrow"></i> 
        </div>
        <div v-else-if="props.chatMessage.state == 'pending'" class="arrow-cnt">
            <i class="fa fa-check single-check-arrow"></i> 
        </div>
        <button v-if="isAttachment" @click="openAttachment" class="content attachment" id="sender-content">
            <i class="fa fa-file"></i>
            <span class="message-text ">
                {{ props.chatMessage.content }}
            </span>
        </button>
        <div v-else class="content" id="sender-content">
            <span class="message-text regular-text">
                {{ props.chatMessage.content }}
            </span>
        </div> 
        <a v-if="isAttachment" ref="linkRef" target="_blank" style="display: none;"></a>
    </section>
</template>

<style scoped>
.message-container {
    width: 100%;
    display: flex;
    flex-direction: row;
    margin-bottom: clamp(5px, 1vw, 10px);
}

#recipient-message-container {
    justify-content: start;
}

#sender-message-container {
    justify-content: end;
}

.arrow-cnt {
    font-size: .6rem;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 0 .2rem;
}

.read-check-arrow {
    color: var(--orange);
}

.single-check-arrow {
    color: #D4D4D4; 
}

.fa-exclamation-circle {
    font-size: .85rem;
    color: red;
}

.content {
    display: inline-block;
    border: 1.5px solid var(--orange);
    border-radius: 15px;
    padding: 0.2rem .8rem;
    max-width: 78%;
    width: fit-content;
    white-space: pre-wrap;       
    overflow-wrap: break-word;   
}

.fa-file {
    margin-right: .5rem;
}

#recipient-content {
    margin-left: clamp(10px, 4vw, 66px);
    background-color: white;
    color: var(--orange);
    text-align: left;
}

#sender-content {
    color: white;
    text-align: right;
    margin-right: clamp(10px, 4vw, 66px);
    background-color: var(--orange);
}

.attachment > span {
    text-decoration: underline;
}
</style>
