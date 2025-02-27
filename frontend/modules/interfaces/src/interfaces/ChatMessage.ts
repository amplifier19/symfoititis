export interface ChatMessage {
    message_id?: number
    room: string
    dep_id: number
    c_id: number
    is_teacher: boolean
    sender_id: string
    recipient_id: string
    type: string
    content: string
    created_at?: string
}