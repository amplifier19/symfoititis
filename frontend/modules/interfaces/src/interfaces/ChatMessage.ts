export interface ChatMessage {
  message_id?: number;
  receipt_id?: string;
  room: string;
  dep_id: number;
  c_id: number;
  is_teacher: boolean;
  sender_id: string;
  recipient_id: string;
  type: string;
  content: string;
  created_at?: string;
  state?: "pending" | "sent" | "read" | "error";
}
