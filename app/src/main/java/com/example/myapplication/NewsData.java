package com.example.myapplication;

public class NewsData {

    private String newsName;
    private String newsLink;
    private int newsImage;

    public NewsData(String newsName, String newsLink, int newsImage) {
        this.newsName = newsName;
        this.newsLink = newsLink;
        this.newsImage = newsImage;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public int getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(int newsImage) {
        this.newsImage = newsImage;
    }
}
