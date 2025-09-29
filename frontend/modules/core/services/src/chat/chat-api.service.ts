export class ChatApiService {
    private static instance = new ChatApiService()
    private readonly API_BASE_URL = import.meta.env.VITE_API_BASE

    public constructor() { }

    public static getChatApiFactory() {
        return this.instance
    }

    public getMessages = (c_id: number, participant_id: string, page: number) => {
        return fetch(
            `${this.API_BASE_URL}/chat/messages/${c_id}/${participant_id}?page=${page}`,
            {
                method: 'GET'
            }
        )
    }

    public getChatStats = () => {
        return fetch(
            `${this.API_BASE_URL}/chat/stats`,
            {
                method: 'GET'
            }
        )
    }

    public readMessages = (room: string, messageId: number) => {
        return fetch(
            `${this.API_BASE_URL}/chat/messages/read/${room}/${messageId}`,
            {
                method: 'PUT'
            }
        )
    }

    public uploadAttachments = (roomId: string, formData: FormData) => {
        return fetch(
            `${this.API_BASE_URL}/chat/objects/${roomId}`,
            {
                method: 'POST',
                body: formData
            }
        )
    }
    public generatePresignedUrl = (roomId: string, objectName: string) => {
        return fetch(
            `${this.API_BASE_URL}/chat/object/${roomId}/${objectName}/generateUrl`,
            {
                method: 'GET',
            }
        )
    }
}
