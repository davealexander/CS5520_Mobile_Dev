package edu.northeastern.numad23su_davidcenteno;

public class LinkModel {
    private String title, link;

    public LinkModel(String title, String link){
        this.title = title;
        this.link = link;

    }

    public String getTitle(){
        return title;
    }
    public String getLink(){
        return link;
    }

}
