package com.biswo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.biswo.entity.ChatMessage;

@Controller
public class ChatController {
	
	//We have to inform all user a new user will join in our chat boat
	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(
			@Payload ChatMessage message
	) {
		return message;
	}
	
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(
			@Payload ChatMessage message,
			SimpMessageHeaderAccessor headerAccessor
	) {
		headerAccessor.getSessionAttributes().put("username", message.getSender());
		return message;
	}
}
