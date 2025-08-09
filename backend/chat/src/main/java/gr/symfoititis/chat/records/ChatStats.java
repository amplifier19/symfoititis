package gr.symfoititis.chat.records;

public record ChatStats(String room, Integer myUnreadCount, Integer otherLastReadMessageId) { }
