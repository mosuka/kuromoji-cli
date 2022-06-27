# Kuromoji CLI

A Japanese morphological analysis command-line interface for [Lucene Kuromoji](https://github.com/apache/lucene-solr/tree/master/lucene/analysis/kuromoji).


## Build

### Build Requirement

- Java >= 1.17.0
- gradle >= 7.0.0

### Build Kuromoji CLI

```shell
% ./gradlew build
```


## Test

```shell
% ./gradlew test
```


## Make JAR file

Combine dependent classes and resources into a single JAR file.

```shell
% ./gradlew shadowJar
```


## Make distribution package

Archive executable script file and JAR file.

```shell
% ./gradlew archiveZip
```


## Install Kuromoji CLI

```shell
% unzip ./build/distributions/kuromoji-cli-<VERSION>.zip
```


## Usage

### Basic usage

You can easily tokenize the text and see the results as follows:

```shell
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

### Tokenize mode

Kuromoji-cli provides two tokenization modes: `normal`, `search` and `extended`.

#### Normal mode

`normal` mode tokenizes faithfully based on words registered in the dictionary. (Default):

```shell
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -m normal
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

#### Search mode

`search` mode tokenizes a compound noun words additionally:

```shell
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -m search
関西    名詞,固有名詞,地域,一般,*,*,関西,カンサイ,カンサイ
国際    名詞,一般,*,*,*,*,国際,コクサイ,コクサイ
空港    名詞,一般,*,*,*,*,空港,クウコウ,クーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

#### Extended mode

`extended` mode tokenizes similar to search mode, but also unigram unknown words (experimental):

```shell
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -m extended
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

#### MeCab format

`mecab` outputs results in a format like MeCab:

```shell
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -o mecab
関西国際空港    名詞,固有名詞,組織,*,*,*,関西国際空港,カンサイコクサイクウコウ,カンサイコクサイクーコー
限定    名詞,サ変接続,*,*,*,*,限定,ゲンテイ,ゲンテイ
トートバッグ    名詞,一般,*,*,*,*,トートバッグ,*,*
EOS
```

#### Whietespace delimitation format

`wakati` outputs the token text separated by spaces:

```shell
% echo "関西国際空港限定トートバッグ" | ./bin/kuromoji -o wakati
関西国際空港 限定 トートバッグ
```

#### JSON format
`json` outputs the token information in JSON format:

```shell
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
