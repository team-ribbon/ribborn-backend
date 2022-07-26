package com.spring.ribborn.sse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SseDto {
    private Long senderId;
    private String message;
    private Long SseId;
}
