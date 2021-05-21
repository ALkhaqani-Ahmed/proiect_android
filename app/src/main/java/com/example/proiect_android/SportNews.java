package com.example.proiect_android;

public class SportNews {
    private String link;
    private String title;


    public SportNews(String link, String title) {
        this.link = link;
        this.title = title;

    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "News{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
