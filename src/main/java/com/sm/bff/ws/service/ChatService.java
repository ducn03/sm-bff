package com.sm.bff.ws.service;

import com.sm.bff.ws.message.ChatMessage;
import com.sm.bff.ws.room.RoomRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.Session;

@ApplicationScoped
public class ChatService {
    @Inject
    private WebSocketService webSocketService;

    @Inject
    private RoomRepository roomRepository;

    public Uni<Void> processMessage(Session session, ChatMessage chatMessage) {
        // Xác thực người gửi
        String senderId = (String) session.getUserProperties().get("userId");
        if (!senderId.equals(chatMessage.getSenderId())) {
            // Không cho phép giả mạo ID người gửi
            return Uni.createFrom().nullItem();
        }

        String roomId = chatMessage.getRoomId();
        // Kiểm tra người gửi có nằm trong phòng không
        return roomRepository.isMemberInRoom(roomId, senderId)
                .flatMap(isMember -> {
                    if (!isMember) {
                        // Không nằm trong phòng, không cho phép gửi tin
                        return Uni.createFrom().nullItem();
                    }

                    // Lấy danh sách thành viên phòng và gửi tin nhắn
                    return roomRepository.getRoomMembers(roomId)
                            .flatMap(roomMembers ->
                                    webSocketService.sendToUsers(roomMembers, chatMessage));
                });
    }

    public void joinRoom(String userId, String roomId) {
        roomRepository.addMemberToRoom(roomId, userId);
    }

    public void leaveRoom(String userId, String roomId) {
        roomRepository.removeMemberFromRoom(roomId, userId);
    }
}
