package com.example.demo.domain.community.dto.request;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class RegisterRequest{
	private final String userId;
	private final String password;
}