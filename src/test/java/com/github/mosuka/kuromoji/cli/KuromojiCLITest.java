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
        KuromojiCLI cli = new KuromojiCLI();

        List<Token> tokens = cli.tokenize("関西国際空港限定トートバッグ", JapaneseTokenizer.Mode.NORMAL);

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
        KuromojiCLI cli = new KuromojiCLI();

        List<Token> tokens = cli.tokenize("関西国際空港限定トートバッグ", JapaneseTokenizer.Mode.SEARCH);

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
        KuromojiCLI cli = new KuromojiCLI();

        List<Token> tokens = cli.tokenize("関西国際空港限定トートバッグ", JapaneseTokenizer.Mode.EXTENDED);

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
