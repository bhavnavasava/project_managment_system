package com.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
	public String generateToken(int size) {// 3
		String allWords = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
		String token = "";
		for (int i = 1; i <= size; i++) {
			int index = (int) (allWords.length() * Math.random());
			token = token + allWords.charAt(index);
		}
		return token;
	}
}
