# Kuromoji CLI

A Japanese morphological analysis command-line interface for [Lucene Kuromoji](https://github.com/apache/lucene-solr/tree/master/lucene/analysis/kuromoji).


## Build Requirement

- Java >= 1.8.0
- gradle >= 6.0.1

## Build

```
% gradle build
```

## Test

```
% gradle test
```

## Make JAR

```
% gradle jar
```

## Make distribution package

```$xslt
% gradle archiveZip
```

## Install kuromoji-cli

```
% unzip ./build/distributions/kuromoji-cli-<VERSION>.zip
```


## Usage

### Basic usage

You can easily tokenize the text and see the results as follows:

```
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

### Tokenize mode

Kuromoji-cli provides two tokenization modes: `normal`, `search` and `extended`.

`normal` mode tokenizes faithfully based on words registered in the dictionary. (Default):

```
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -m normal
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

`search` mode tokenizes a compound noun words additionally:

```
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -m search
関西    名詞,固有名詞,地域,一般,*,*,関西,カンサイ,カンサイ
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
国際    名詞,一般,*,*,*,*,国際,コクサイ,コクサイ
空港    名詞,一般,*,*,*,*,空港,クウコウ,クーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

`extended` mode tokenizes similar to search mode, but also unigram unknown words (experimental):

```
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -m search
関西    名詞,固有名詞,地域,一般,*,*,関西,カンサイ,カンサイ
国際    名詞,一般,*,*,*,*,国際,コクサイ,コクサイ
空港    名詞,一般,*,*,*,*,空港,クウコウ,クーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
ト      記号,一般,*,*,*,*,ト,*,*
ー      記号,一般,*,*,*,*,ー,*,*
ト      記号,一般,*,*,*,*,ト,*,*
バ      記号,一般,*,*,*,*,バ,*,*
ッ      記号,一般,*,*,*,*,ッ,*,*
グ      記号,一般,*,*,*,*,グ,*,*
EOS
```


### Output format

kuromoji-cli provides three output formats: `mecab`, `wakati` and `json`.

`mecab` outputs results in a format like MeCab:

```
% echo "お待ちしております。" | ./bin/kuromoji -o mecab
お待ち  名詞,サ変接続,*,*,*,*,お待ち,オマチ,オマチ
し      動詞,自立,*,*,サ変・スル,連用形,する,シ,シ
て      助詞,接続助詞,*,*,*,*,て,テ,テ
おり    動詞,非自立,*,*,五段・ラ行,連用形,おる,オリ,オリ
ます    助動詞,*,*,*,特殊・マス,基本形,ます,マス,マス
。      記号,句点,*,*,*,*,。,。,。
EOS
```

`wakati` outputs the token text separated by spaces:

```
% echo "お待ちしております。" | ./bin/kuromoji -o wakati
お待ち し て おり ます 。
```

`json` outputs the token information in JSON format:

```
% echo "お待ちしております。" | ./bin/kuromoji -o json | jq .
[
  {
    "text": "お待ち",
    "detail": [
      "名詞",
      "サ変接続",
      "*",
      "*",
      "*",
      "*",
      "お待ち",
      "オマチ",
      "オマチ"
    ]
  },
  {
    "text": "し",
    "detail": [
      "動詞",
      "自立",
      "*",
      "*",
      "サ変・スル",
      "連用形",
      "する",
      "シ",
      "シ"
    ]
  },
  {
    "text": "て",
    "detail": [
      "助詞",
      "接続助詞",
      "*",
      "*",
      "*",
      "*",
      "て",
      "テ",
      "テ"
    ]
  },
  {
    "text": "おり",
    "detail": [
      "動詞",
      "非自立",
      "*",
      "*",
      "五段・ラ行",
      "連用形",
      "おる",
      "オリ",
      "オリ"
    ]
  },
  {
    "text": "ます",
    "detail": [
      "助動詞",
      "*",
      "*",
      "*",
      "特殊・マス",
      "基本形",
      "ます",
      "マス",
      "マス"
    ]
  },
  {
    "text": "。",
    "detail": [
      "記号",
      "句点",
      "*",
      "*",
      "*",
      "*",
      "。",
      "。",
      "。"
    ]
  }
]
```
