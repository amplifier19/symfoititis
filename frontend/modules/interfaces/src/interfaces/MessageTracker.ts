import type { ChatMessage } from "./ChatMessage";

export interface MessageTracker {
  message: ChatMessage;
  onAck: (id: number) => void;
  onError: (err: Error) => void;
}
