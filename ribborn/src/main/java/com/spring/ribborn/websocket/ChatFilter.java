package com.spring.ribborn.websocket;


import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ChatFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filter;
}
