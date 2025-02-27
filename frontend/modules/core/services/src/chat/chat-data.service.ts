import { storeToRefs } from 'pinia'

import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client/dist/sockjs'

import { ChatMessage } from '@symfoititis-frontend-monorepo/interfaces'

import { ChatApiService } from "./chat-api.service"

import { useUserStore, useChatStore, useErrorStore, useFileStore } from "@symfoititis-frontend-monorepo/stores"

export const useChatDataService = () => {
    const chatApiService = ChatApiService.getChatApiFactory()

    const chatStore = useChatStore()
    const userStore = useUserStore()
    const fileStore = useFileStore()
    const errorStore = useErrorStore()
    const { profile } = storeToRefs(userStore)
    const { stompClient, connected, connectionInitiated, messages, sendCount, fetchCount, offset, currentCourseId } = storeToRefs(chatStore)


    const getMessages = async (c_id: number, participant_id: string) => {
        try {
            if (offset.value > 0 && currentCourseId.value != c_id) {
                offset.value = 0
                fetchCount.value = 0
                messages.value = []
            }
            const response = await chatApiService.getMessages(c_id, participant_id, offset.value)
            const data = await response.json()
            if (!!data.error) {
                errorStore.addError(data)
                return
            }
            messages.value = [...data.data.reverse(), ...messages.value]
            fetchCount.value = data.data.length
            offset.value = offset.value + 16
            currentCourseId.value = c_id
        } catch (err) {
            errorStore.addError(err)
        }
    }

    const onMessageReceived = (payload: { body: string }) => {
        const message = JSON.parse(payload.body)
        messages.value.push(message)
        sendCount.value ++
    }

    const onConnected = () => {
        stompClient.value!.subscribe(`/user/${profile.value.id}/queue/messages`, onMessageReceived)
        connected.value = true
    }

    const onError = () => {
        const err = 'Error while attempting websocket connection'
        errorStore.addError(err)
        connected.value = false
        connectionInitiated.value = false
    }

    const connectToStompServer = () => {
        if (connected.value || connectionInitiated.value) return
        const socket = new SockJS(`/rest/${import.meta.env.VITE_KC_REALM}/chat/ws`)
        stompClient.value = Stomp.over(socket, {
            protocols: ['v10.stomp', 'v11.stomp', 'v12.stomp'],
        })
        stompClient.value.connect({}, onConnected, onError)
    }

    const uploadAttachments = async (roomId: string) => {
        const formData = new FormData()
        for (const file of fileStore.attachments) {
            formData.append('files', file)
        }
        try {
            const response = await chatApiService.uploadAttachments(roomId, formData)
            const data = await response.json()
            if (!!data.error) {
                errorStore.addError(data)
                return
            }
            return data.data
        } catch (err) {
            errorStore.addError(err)
        }
    }

    const generatePresignedUrl = async (roomId: string, objectName: string) => {
        try {
            const response = await chatApiService.generatePresignedUrl(roomId, objectName)
            const data = await response.json()
            if (!!data.error) {
                errorStore.addError(data)
                return
            }
            return data.data
        } catch (err) {
            errorStore.addError(err)
        }
    }

    const sendMessage = (message: ChatMessage) => {
        connectToStompServer()
        stompClient.value!.send('/app/send', JSON.stringify(message), {
            'content-type': 'application/json',
        })
        chatStore.messages.push(message)
        chatStore.sendCount++
    }

    return { connected, connectToStompServer, getMessages, uploadAttachments, generatePresignedUrl, sendMessage }
}
