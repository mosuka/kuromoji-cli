// Copyright (c) 2022 Minoru Osuka
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// 		http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.github.mosuka.kuromoji.cli;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TokenTest {
    @Test
    public void getSurface() {
        Token token = new Token(
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

        assertEquals(token.getSurface(), "関西国際空港");
    }

    @Test
    public void getAttrs() {
        Token token = new Token(
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

        assertEquals(token.getAttrs(), Arrays.asList(
                "名詞",
                "固有名詞",
                "組織",
                "*",
                "*",
                "*",
                "関西国際空港",
                "カンサイコクサイクウコウ",
                "カンサイコクサイクーコー"
        ));
    }

    @Test
    public void setSurface() {
        Token token = new Token(
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
        token.setSurface("羽田空港");

        assertEquals(token.getSurface(), "羽田空港");
    }

    @Test
    public void setAttrs() {
        Token token = new Token(
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
        token.setAttrs(Arrays.asList(
                "名詞",
                "固有名詞",
                "一般",
                "*",
                "*",
                "*",
                "羽田空港",
                "ハネダクウコウ",
                "ハネダクーコー"
        ));

        assertEquals(token.getAttrs(), Arrays.asList(
                "名詞",
                "固有名詞",
                "一般",
                "*",
                "*",
                "*",
                "羽田空港",
                "ハネダクウコウ",
                "ハネダクーコー"
        ));
    }

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
