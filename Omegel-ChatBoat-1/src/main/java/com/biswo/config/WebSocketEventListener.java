package com.biswo.config;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.biswo.entity.ChatMessage;
import com.biswo.entity.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//Handling event if user disconnect then it inform to all user to this user now disconnect
public class WebSocketEventListener {
	private final SimpMessageSendingOperations messageTemplate;

	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String) headerAccessor.getSessionAttributes().get("username");

		if (username != null) {
			log.info("User Disconnected: {}", username);
			var chatMessage = ChatMessage.builder()
					.type(MessageType.LEAVER)
					.sender(username)
					.build();
			messageTemplate.convertAndSend("/topic/public",chatMessage);
		}

	}

}
