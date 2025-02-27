import { ref } from 'vue'
import { defineStore } from 'pinia'
import { Client } from 'webstomp-client'
import type { ChatMessage } from '@symfoititis-frontend-monorepo/interfaces'

export const useChatStore = defineStore('chat', () => {
    const stompClient = ref<Client>()
    const connected = ref<boolean>(false)
    const connectionInitiated = ref<boolean>(false)
    const messages = ref<ChatMessage[]>([])
    const sendCount = ref<number>(0)
    const fetchCount = ref<number>(0)
    const offset = ref<number>(0)
    const currentCourseId = ref<number>(-1)

    return { stompClient, connected, connectionInitiated, messages, sendCount, fetchCount, offset, currentCourseId }
})