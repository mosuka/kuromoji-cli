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

import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KuromojiCLITest {

    @Test
    public void tokenizeNormal() throws IOException {
        KuromojiCLI cli = new KuromojiCLI(JapaneseTokenizer.Mode.NORMAL);

        List<Token> tokens = cli.tokenize("関西国際空港限定トートバッグ");

        List<Token> expected = new ArrayList<>();

        expected.add(
                new Token(
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
                )
        );
        expected.add(
                new Token(
                        "限定",
                        Arrays.asList(
                                "名詞",
                                "サ変接続",
                                "*",
                                "*",
                                "*",
                                "*",
                                "限定",
                                "ゲンテイ",
                                "ゲンテイ"
                        )
                )
        );
        expected.add(
                new Token(
                        "トートバッグ",
                        Arrays.asList(
                                "名詞",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "トートバッグ",
                                "*",
                                "*"
                        )
                )
        );

        assertEquals(expected, tokens);
    }

    @Test
    public void tokenizeSearch() throws IOException {
        KuromojiCLI cli = new KuromojiCLI(JapaneseTokenizer.Mode.SEARCH);

        List<Token> tokens = cli.tokenize("関西国際空港限定トートバッグ");

        List<Token> expected = new ArrayList<>();

        expected.add(
                new Token(
                        "関西",
                        Arrays.asList(
                                "名詞",
                                "固有名詞",
                                "地域",
                                "一般",
                                "*",
                                "*",
                                "関西",
                                "カンサイ",
                                "カンサイ"
                        )
                )
        );
        expected.add(
                new Token(
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
                )
        );
        expected.add(
                new Token(
                        "国際",
                        Arrays.asList(
                                "名詞",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "国際",
                                "コクサイ",
                                "コクサイ"
                        )
                )
        );
        expected.add(
                new Token(
                        "空港",
                        Arrays.asList(
                                "名詞",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "空港",
                                "クウコウ",
                                "クーコー"
                        )
                )
        );
        expected.add(
                new Token(
                        "限定",
                        Arrays.asList(
                                "名詞",
                                "サ変接続",
                                "*",
                                "*",
                                "*",
                                "*",
                                "限定",
                                "ゲンテイ",
                                "ゲンテイ"
                        )
                )
        );
        expected.add(
                new Token(
                        "トートバッグ",
                        Arrays.asList(
                                "名詞",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "トートバッグ",
                                "*",
                                "*"
                        )
                )
        );

        assertEquals(expected, tokens);
    }

    @Test
    public void tokenizeExtended() throws IOException {
        KuromojiCLI cli = new KuromojiCLI(JapaneseTokenizer.Mode.EXTENDED);

        List<Token> tokens = cli.tokenize("関西国際空港限定トートバッグ");

        List<Token> expected = new ArrayList<>();

        expected.add(
                new Token(
                        "関西",
                        Arrays.asList(
                                "名詞",
                                "固有名詞",
                                "地域",
                                "一般",
                                "*",
                                "*",
                                "関西",
                                "カンサイ",
                                "カンサイ"
                        )
                )
        );
        expected.add(
                new Token(
                        "国際",
                        Arrays.asList(
                                "名詞",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "国際",
                                "コクサイ",
                                "コクサイ"
                        )
                )
        );
        expected.add(
                new Token(
                        "空港",
                        Arrays.asList(
                                "名詞",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "空港",
                                "クウコウ",
                                "クーコー"
                        )
                )
        );
        expected.add(
                new Token(
                        "限定",
                        Arrays.asList(
                                "名詞",
                                "サ変接続",
                                "*",
                                "*",
                                "*",
                                "*",
                                "限定",
                                "ゲンテイ",
                                "ゲンテイ"
                        )
                )
        );
        expected.add(
                new Token(
                        "ト",
                        Arrays.asList(
                                "記号",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "ト",
                                "*",
                                "*"
                        )
                )
        );
        expected.add(
                new Token(
                        "ー",
                        Arrays.asList(
                                "記号",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "ー",
                                "*",
                                "*"
                        )
                )
        );
        expected.add(
                new Token(
                        "ト",
                        Arrays.asList(
                                "記号",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "ト",
                                "*",
                                "*"
                        )
                )
        );
        expected.add(
                new Token(
                        "バ",
                        Arrays.asList(
                                "記号",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "バ",
                                "*",
                                "*"
                        )
                )
        );
        expected.add(
                new Token(
                        "ッ",
                        Arrays.asList(
                                "記号",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "ッ",
                                "*",
                                "*"
                        )
                )
        );
        expected.add(
                new Token(
                        "グ",
                        Arrays.asList(
                                "記号",
                                "一般",
                                "*",
                                "*",
                                "*",
                                "*",
                                "グ",
                                "*",
                                "*"
                        )
                )
        );

        assertEquals(expected, tokens);
    }
}
