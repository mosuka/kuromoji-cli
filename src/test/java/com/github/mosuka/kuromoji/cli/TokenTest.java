package com.github.mosuka.kuromoji.cli;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenTest {

    @Test
    public void equals() {
        Token token1 = new Token(
                "関西国際空港",
                Arrays.asList(
                        "名詞",
                        "固有名詞",
                        "組織",
                        "*",
                        "*",
                        "*",
                        "関西国際空港",
                        "カンサイコクサイクウコウ",
                        "カンサイコクサイクーコー"
                )
        );
        Token token2 = new Token(
                "関西国際空港",
                Arrays.asList(
                        "名詞",
                        "固有名詞",
                        "組織",
                        "*",
                        "*",
                        "*",
                        "関西国際空港",
                        "カンサイコクサイクウコウ",
                        "カンサイコクサイクーコー"
                )
        );

        assertEquals(token1, token2);
    }

    @Test
    public void notEquals() {
        Token token1 = new Token(
                "羽田空港",
                Arrays.asList(
                        "名詞",
                        "固有名詞",
                        "一般",
                        "*",
                        "*",
                        "*",
                        "羽田空港",
                        "ハネダクウコウ",
                        "ハネダクーコー"
                )
        );
        Token token2 = new Token(
                "関西国際空港",
                Arrays.asList(
                        "名詞",
                        "固有名詞",
                        "組織",
                        "*",
                        "*",
                        "*",
                        "関西国際空港",
                        "カンサイコクサイクウコウ",
                        "カンサイコクサイクーコー"
                )
        );

        assertNotEquals(token1, token2);
    }
}
