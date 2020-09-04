package com.example.hanium_saeteomin.homefragment.bestword;

public class WordData {
    private String word;
    private String word_meaning;

    public WordData(String wordName, String wordMean) {
        this.word = wordName;
        this.word_meaning = wordMean;
    }

    public String getWordName() {
        return word;
    }

    public void setWordName(String wordName) {
        this.word = wordName;
    }

    public String getWordMean() {
        return word_meaning;
    }

    public void setWordMean(String wordMean) {
        this.word_meaning = wordMean;
    }
}
