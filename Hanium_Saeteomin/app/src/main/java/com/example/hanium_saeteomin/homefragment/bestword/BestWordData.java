package com.example.hanium_saeteomin.homefragment.bestword;

public class BestWordData {
    private String wordName;
    private String wordMean;

    public BestWordData(String wordName, String wordMean) {
        this.wordName = wordName;
        this.wordMean = wordMean;
    }

    public String getWordName() {
        return wordName;
    }

    public void setWordName(String wordName) {
        this.wordName = wordName;
    }

    public String getWordMean() {
        return wordMean;
    }

    public void setWordMean(String wordMean) {
        this.wordMean = wordMean;
    }
}
