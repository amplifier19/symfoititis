import { storeToRefs } from "pinia";

import Stomp, { Frame } from "webstomp-client";
import SockJS from "sockjs-client/dist/sockjs";
import { v4 as uuidv4 } from "uuid";

import {
  ChatMessage,
  ReadReceipt,
} from "@symfoititis-frontend-monorepo/interfaces";

import { ChatApiService } from "./chat-api.service";

import {
  useUserStore,
  useChatStore,
  useErrorStore,
  useFileStore,
  useResponseStore,
} from "@symfoititis-frontend-monorepo/stores";

export const useChatDataService = () => {
  const chatApiService = ChatApiService.getChatApiFactory();

  const chatStore = useChatStore();
  const userStore = useUserStore();
  const fileStore = useFileStore();
  const errorStore = useErrorStore();
  const responseStore = useResponseStore();
  const { profile } = storeToRefs(userStore);
  const {
    stompClient,
    connected,
    connectionInitiated,
    messages,
    chatStats,
    page,
    currentCourseId,
    currentRoom,
    trackers,
  } = storeToRefs(chatStore);

  const getMessages = async (
    c_id: number,
    participant_id: string,
    loadNextBatch?: boolean,
  ) => {
    try {
      if (currentCourseId.value === c_id) {
        if (!loadNextBatch) return;
      } else {
        page.value = 0;
        messages.value = [];
      }
      const response = await chatApiService.getMessages(
        c_id,
        participant_id,
        page.value,
      );
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      messages.value = [...data.data.reverse(), ...messages.value];
      page.value++;
      currentCourseId.value = c_id;
    } catch (err) {
      errorStore.addError(err);
    }
  };

  const getChatStats = async () => {
    try {
      const response = await chatApiService.getChatStats();
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      chatStats.value = data.data;
      // let unreadCount = 0
      // let str = ''
      // for (let unreadMessage of data.data) {
      //     unreadCount += unreadMessage.unread_count
      // }
      // if (unreadCount == 0) {
      //     return
      // } else if (unreadCount == 1) {
      //     str = 'Έχεις 1 νέο μήνυμα'
      // } else if (unreadCount > 1) {
      //     str = 'Έχεις ' + unreadCount + ' νέα μηνύματα'
      // }
      // if (str.length > 0) {
      //     responseStore.addResponse(str)
      // }
      // fetchedUnread.value = true
    } catch (err) {
      errorStore.addError(err);
    }
  };

  const onAckReceived = (payload: { body: string }) => {
    const ack = JSON.parse(payload.body);
    const t = trackers.value.get(ack.receipt_id);
    if (t) {
      t.onAck(ack.message_id);
      chatStore.updateMessageId(ack.receipt_id, ack.message_id);
      chatStore.updateMessageState(ack.receipt_id, "sent");
    }
  };

  const onReadReceiptReceived = (payload: { body: string }) => {
    const receipt = JSON.parse(payload.body);
    const idx = chatStats.value.findIndex((m) => m.room == receipt.room);
    if (idx >= 0) {
      chatStats.value = chatStats.value.toSpliced(idx, 1, {
        room: receipt.room,
        myUnreadCount: 0,
        otherLastReadMessageId: receipt.message_id,
      });
    }
  };

  const onMessageReceived = (payload: { body: string }) => {
    const message = JSON.parse(payload.body);
    messages.value = [...messages.value, message];
    if (currentRoom.value == message.room) {
      readMessages(message);
    } else {
      const idx = chatStats.value.findIndex((m) => m.room == message.room);
      if (idx >= 0) {
        chatStats.value[idx].myUnreadCount++;
      }
    }
  };

  const onConnected = () => {
    if (stompClient.value) {
      stompClient.value!.subscribe(
        `/user/${profile.value.id}/queue/ack`,
        onAckReceived,
      );
      stompClient.value!.subscribe(
        `/user/${profile.value.id}/queue/read`,
        onReadReceiptReceived,
      );
      stompClient.value!.subscribe(
        `/user/${profile.value.id}/queue/messages`,
        onMessageReceived,
      );
      connected.value = true;
    }
  };

  const onError = () => {
    connected.value = false;
    connectionInitiated.value = false;
    connectToStompServer();
  };

  const connectToStompServer = () => {
    if (connected.value || connectionInitiated.value) return;
    const socket = new SockJS(`/rest/${import.meta.env.VITE_KC_REALM}/chat/ws`);
    stompClient.value = Stomp.over(socket, {
      protocols: ["v10.stomp", "v11.stomp", "v12.stomp"],
    });
    stompClient.value.connect({}, onConnected, onError);
  };

  const sendMessage = (message: ChatMessage) => {
    connectToStompServer();
    const receipt_id = uuidv4();
    message.receipt_id = receipt_id;

    return new Promise<void>((resolve, reject) => {
      let acked = false;

      const cleanup = () => {
        clearTimeout(ackTimer);
        trackers.value.delete(receipt_id);
      };

      const ackTimer = window.setTimeout(() => {
        cleanup();
        chatStore.updateMessageState(receipt_id, "error");
        reject("ACK timeout");
      }, 5000);

      const maybeResolve = () => {
        if (acked) {
          cleanup();
          resolve();
        }
      };
      const tracker = {
        message,
        onAck() {
          clearTimeout(ackTimer);
          acked = true;
          maybeResolve();
        },
        onError(err: Error) {
          cleanup();
          chatStore.updateMessageState(receipt_id, "error");
          reject(err);
        },
      };
      trackers.value.set(receipt_id, tracker);
      stompClient.value!.send("/app/send", JSON.stringify(message), {
        "content-type": "application/json",
        receipt: receipt_id,
      });
      messages.value = [...messages.value, message];
      chatStore.updateMessageState(receipt_id, "pending");
    });
  };

  const readMessages = (message: ChatMessage) => {
    connectToStompServer();
    const receipt: ReadReceipt = {
      receipt_id: message.receipt_id!,
      message_id: message.message_id!,
      room: message.room,
      sender_id: profile.value.id!,
      recipient_id: message.sender_id,
    };
    stompClient.value!.send("/app/read", JSON.stringify(receipt), {
      "content-type": "application/json",
    });
  };

  const uploadAttachments = async (roomId: string) => {
    const formData = new FormData();
    for (const file of fileStore.attachments) {
      formData.append("files", file);
    }
    try {
      const response = await chatApiService.uploadAttachments(roomId, formData);
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      return data.data;
    } catch (err) {
      errorStore.addError(err);
    }
  };

  const generatePresignedUrl = async (roomId: string, objectName: string) => {
    try {
      const response = await chatApiService.generatePresignedUrl(
        roomId,
        objectName,
      );
      const data = await response.json();
      if (!!data.error) {
        errorStore.addError(data);
        return;
      }
      return data.data;
    } catch (err) {
      errorStore.addError(err);
    }
  };

  return {
    connected,
    connectToStompServer,
    getMessages,
    getChatStats,
    readMessages,
    uploadAttachments,
    generatePresignedUrl,
    sendMessage,
  };
};
