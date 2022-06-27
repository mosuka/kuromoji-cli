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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.cli.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ja.JapaneseTokenizer;
import org.apache.lucene.analysis.ja.tokenattributes.BaseFormAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.InflectionAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.PartOfSpeechAttribute;
import org.apache.lucene.analysis.ja.tokenattributes.ReadingAttribute;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KuromojiCLI {
    private Analyzer analyzer;

    public KuromojiCLI(JapaneseTokenizer.Mode mode) {
        this.analyzer = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String fieldName) {
                JapaneseTokenizer tokenizer = new JapaneseTokenizer(null, false, mode);
                return new TokenStreamComponents(tokenizer, tokenizer);
            }
        };
    }

    enum OutputFormat {
        MECAB,
        WAKATI,
        JSON
    }

    List<Token> tokenize(String text) throws IOException {
        // tokenize text
        TokenStream tokenStream = this.analyzer.tokenStream("ignored", new StringReader(text));
        CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
        PartOfSpeechAttribute partOfSpeechAttribute = tokenStream.getAttribute(PartOfSpeechAttribute.class);
        InflectionAttribute inflectionAttribute = tokenStream.getAttribute(InflectionAttribute.class);
        BaseFormAttribute baseFormAttribute = tokenStream.getAttribute(BaseFormAttribute.class);
        ReadingAttribute readingAttribute = tokenStream.getAttribute(ReadingAttribute.class);

        List<Token> tokens = new ArrayList<>();

        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            String surface = charTermAttribute.toString();

            ArrayList<String> attrs = new ArrayList<>();
            for (String pos : partOfSpeechAttribute.getPartOfSpeech().split("-")) {
                attrs.add(pos);
            }
            for (int i = attrs.size(); i < 4; i++) {
                attrs.add("*");
            }
            if (inflectionAttribute.getInflectionType() == null) {
                attrs.add("*");
            } else {
                attrs.add(inflectionAttribute.getInflectionType());
            }
            if (inflectionAttribute.getInflectionForm() == null) {
                attrs.add("*");
            } else {
                attrs.add(inflectionAttribute.getInflectionForm());
            }
            if (baseFormAttribute.getBaseForm() == null) {
                attrs.add(surface);
            } else {
                attrs.add(baseFormAttribute.getBaseForm());
            }
            if (readingAttribute.getReading() == null) {
                attrs.add("*");
            } else {
                attrs.add(readingAttribute.getReading());
            }
            if (readingAttribute.getPronunciation() == null) {
                attrs.add("*");
            } else {
                attrs.add(readingAttribute.getPronunciation());
            }

            tokens.add(new Token(surface, attrs));
        }
        tokenStream.close();

        return tokens;

    }

    void print(List<Token> tokens, OutputFormat outputFormat) {
        switch (outputFormat) {
            case MECAB:
                for (Token token : tokens) {
                    System.out.printf("%s\t%s\n", token.getSurface(), String.join(",", token.getAttrs()));
                }
                System.out.println("EOS");
                break;
            case WAKATI:
                List<String> surfaces = new ArrayList<>();
                for (Token token : tokens) {
                    surfaces.add(token.getSurface());
                }
                System.out.printf("%s\n", String.join(" ", surfaces));
                break;
            case JSON:
                ObjectMapper mapper = new ObjectMapper();
                try {
                    System.out.println(mapper.writeValueAsString(tokens));
                } catch (JsonProcessingException e) {
                    System.out.println(e.getMessage());
                }
                break;
            default:
                for (Token token : tokens) {
                    System.out.printf("%s\t%s\n", token.getSurface(), String.join(",", token.getAttrs()));
                }
                System.out.println("EOS");
                break;
        }

    }

    public static void main(String args[]) {
        Options options = new Options();
        options.addOption(
                Option.builder("m")
                        .longOpt("tokenize-mode")
                        .hasArg()
                        .desc("The tokenization mode. `normal`, `search` or `extended` can be specified. If not specified, use `normal` as default mode.")
                        .build()
        );
        options.addOption(
                Option.builder("o")
                        .longOpt("output-format")
                        .hasArg()
                        .desc("The output format. `mecab`, `wakati` or `json` can be specified. If not specified, use `mecab` as default mode.")
                        .build()
        );
        options.addOption(
                Option.builder("v")
                        .longOpt("version")
                        .desc("Print version.")
                        .build()
        );
        options.addOption(
                Option.builder("h")
                        .longOpt("help")
                        .desc("Print this message.")
                        .build()
        );

        HelpFormatter hf = new HelpFormatter();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            hf.printHelp(String.format("%s [OPTIONS] [INPUT_FILE]", KuromojiCLI.class.getPackage().getImplementationTitle()), options);
            return;
        }
        if (cmd.hasOption("h")) {
            hf.printHelp(String.format("%s [OPTIONS] [INPUT_FILE]", KuromojiCLI.class.getPackage().getImplementationTitle()), options);
            return;
        }
        if (cmd.hasOption("v")) {
            System.out.println(KuromojiCLI.class.getPackage().getImplementationVersion());
            return;
        }

        // mode
        JapaneseTokenizer.Mode mode = JapaneseTokenizer.Mode.NORMAL;
        if (cmd.hasOption("m")) {
            switch (cmd.getOptionValue("m")) {
                case "normal":
                    mode = JapaneseTokenizer.Mode.NORMAL;
                    break;
                case "search":
                    mode = JapaneseTokenizer.Mode.SEARCH;
                    break;
                case "extended":
                    mode = JapaneseTokenizer.Mode.EXTENDED;
                    break;
                default:
                    System.out.printf("unexpected tokenization mode: %s\n", cmd.getOptionValue("m"));
                    hf.printHelp(String.format("%s [OPTIONS] [INPUT_FILE]", KuromojiCLI.class.getPackage().getImplementationTitle()), options);
                    return;
            }
        }

        // output format
        OutputFormat outputFormat = OutputFormat.MECAB;
        if (cmd.hasOption("o")) {
            switch (cmd.getOptionValue("o")) {
                case "mecab":
                    outputFormat = OutputFormat.MECAB;
                    break;
                case "wakati":
                    outputFormat = OutputFormat.WAKATI;
                    break;
                case "json":
                    outputFormat = OutputFormat.JSON;
                    break;
                default:
                    System.out.printf("unexpected output format: %s\n", cmd.getOptionValue("o"));
                    hf.printHelp(String.format("%s [OPTIONS] [INPUT_FILE]", KuromojiCLI.class.getPackage().getImplementationTitle()), options);
                    return;
            }
        }

        KuromojiCLI cli = new KuromojiCLI(mode);

        // read file
        if (cmd.getArgs().length > 0) {
            try {
                FileReader fileReader = new FileReader(new File(cmd.getArgs()[0]));
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    List<Token> tokens = cli.tokenize(line);
                    cli.print(tokens, outputFormat);
                }
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            return;
        }

        // read stdin
        try {
            Scanner stdin = new Scanner(System.in);
            while (stdin.hasNextLine()) {
                String text = stdin.nextLine();
                List<Token> tokens = cli.tokenize(text);
                cli.print(tokens, outputFormat);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
