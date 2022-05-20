package com.homefix.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("chatbot")
@Data
public class ChatBotConfig {
	
	private String secretKey;
	private String apiUrl;

}
