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
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -o mecab
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

`wakati` outputs the token text separated by spaces:

```
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -o wakati
関西国際空港 限定 トートバッグ
```

`json` outputs the token information in JSON format:

```
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -o json | jq .
[
  {
    "surface": "関西国際空港",
    "attrs": [
      "名詞",
      "固有名詞",
      "組織",
      "*",
      "*",
      "*",
      "関西国際空港",
      "カンサイコクサイクウコウ",
      "カンサイコクサイクーコー"
    ]
  },
  {
    "surface": "限定",
    "attrs": [
      "名詞",
      "サ変接続",
      "*",
      "*",
      "*",
      "*",
      "限定",
      "ゲンテイ",
      "ゲンテイ"
    ]
  },
  {
    "surface": "トートバッグ",
    "attrs": [
      "名詞",
      "一般",
      "*",
      "*",
      "*",
      "*",
      "トートバッグ",
      "*",
      "*"
    ]
  }
]
```
