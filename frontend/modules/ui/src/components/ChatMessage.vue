<script setup lang="ts">
import { ref } from 'vue'
import type { ChatMessage } from '@symfoititis-frontend-monorepo/interfaces';
import { useChatDataService } from '@symfoititis-frontend-monorepo/core/services';

const props = defineProps<{
    chatMessage: ChatMessage
}>()

const { generatePresignedUrl } = useChatDataService()

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
    <section v-if="XOR(isTeacher, props.chatMessage.is_teacher)" class="message-container"
        id="recipient-message-container">
        <button v-if="isAttachment" @click="openAttachment" class="content attachment" id="recipient-content">
            <i class="fa fa-file"></i>
            <span @click="openAttachment">
                {{ props.chatMessage.content }}
            </span>
            <a ref="linkRef" :href="attachmentUrl" target="_blank" rel="noopener noreferrer" style="display: none;"></a>
        </button>
        <div v-else class="content" id="recipient-content">
            {{ props.chatMessage.content }}
        </div>
    </section>
    <section v-else class="message-container" id="sender-message-container">
        <button v-if="isAttachment" @click="openAttachment" class="content attachment" id="sender-content">
            <i class="fa fa-file"></i>
            <span>
                {{ props.chatMessage.content }}
            </span>
        </button>
        <div v-else class="content" id="sender-content">
            {{ props.chatMessage.content }}
        </div>
        <a v-if="isAttachment" ref="linkRef" target="_blank" style="display: none;"></a>
    </section>
</template>

<style scoped>
.message-container {
    width: 100%;
    height: max-content;
    display: flex;
    margin-bottom: 2rem;
}

#recipient-message-container {
    justify-content: start;
}

#sender-message-container {
    justify-content: end;
}

.content {
    width: fit-content;
    max-width: 35%;
    height: max-content;
    border: 1.5px solid var(--orange);
    border-radius: 15px;
    padding: 0.5rem 1rem;
}

.fa-file {
    margin-right: .5rem;
}

#recipient-content {
    margin-left: 66px;
    background-color: white;
    color: var(--orange)
}

#sender-content {
    margin-right: 66px;
    background-color: var(--orange);
    color: white;
}

.attachment > span {
    text-decoration: underline;
}
</style>