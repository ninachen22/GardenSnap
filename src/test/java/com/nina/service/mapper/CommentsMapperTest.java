package com.nina.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommentsMapperTest {

    private CommentsMapper commentsMapper;

    @BeforeEach
    public void setUp() {
        commentsMapper = new CommentsMapperImpl();
    }
}
