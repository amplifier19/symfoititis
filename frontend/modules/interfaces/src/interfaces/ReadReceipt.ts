export interface ReadReceipt {
  receipt_id: string | null;
  message_id: number;
  room: string;
  sender_id: string;
  recipient_id: string;
}
