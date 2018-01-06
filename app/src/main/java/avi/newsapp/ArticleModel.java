package avi.newsapp;

/**
 * Created by AVI on 04-Jan-18.
 */

public class ArticleModel {
    public String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public ArticleModel(String title, String description,String url, String urlToImage) {
        this.title = title;
        this.description=description;
        this.url = url;
        this.urlToImage = urlToImage;
    }
    public ArticleModel(String title, String urlToImage) {
        this.title = title;
        this.urlToImage = urlToImage;
    }

    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }


    public String getUrl() {
        return url;
    }


    public String getUrlToImage() {
        return urlToImage;
    }


}

