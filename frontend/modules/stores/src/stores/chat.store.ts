import { ref } from 'vue'
import { defineStore } from 'pinia'
import { Client } from 'webstomp-client'
import type { ChatMessage, MessageTracker, ChatStats } from '@symfoititis-frontend-monorepo/interfaces'

export const useChatStore = defineStore('chat', () => {
    const stompClient = ref<Client>()
    const connected = ref<boolean>(false)
    const connectionInitiated = ref<boolean>(false)

    const currentCourseId = ref<number>(-1)
    const currentRoom = ref<string>('')

    const messages = ref<ChatMessage[]>([])
    const fetchCount = ref<number>(0)
    const page = ref<number>(0)

    const chatStats = ref<ChatStats[]>([])
    const fetchedUnread = ref<boolean>(false)

    const trackers = ref<Map<string, MessageTracker>>(new Map<string, MessageTracker>())

    const updateMessageId = (receiptId: string, messageId: number) => {
        const idx = messages.value.findIndex((m) => {
            if (m.receipt_id) {
                return m.receipt_id == receiptId
            } 
            return false
        })
        if (idx >= 0) {
            messages.value[idx].message_id = messageId 
        }
    }

    const updateMessageState = (receiptId: string, state: 'pending' | 'sent' | 'read' | 'error') => {
        const idx = messages.value.findIndex((m) => {
            if (m.receipt_id) {
                return m.receipt_id == receiptId
            } 
            return false
        })
        if (idx >= 0) {
            messages.value[idx].state = state
        }
    }

    return { 
        stompClient,
        connected, 
        connectionInitiated, 
        messages, 
        chatStats, 
        fetchedUnread, 
        fetchCount, 
        page, 
        currentCourseId, 
        currentRoom,
        trackers,
        updateMessageId,
        updateMessageState
     }
})