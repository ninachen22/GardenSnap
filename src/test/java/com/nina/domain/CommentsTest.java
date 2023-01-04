package com.nina.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.nina.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comments.class);
        Comments comments1 = new Comments();
        comments1.setId(1L);
        Comments comments2 = new Comments();
        comments2.setId(comments1.getId());
        assertThat(comments1).isEqualTo(comments2);
        comments2.setId(2L);
        assertThat(comments1).isNotEqualTo(comments2);
        comments1.setId(null);
        assertThat(comments1).isNotEqualTo(comments2);
    }
}
