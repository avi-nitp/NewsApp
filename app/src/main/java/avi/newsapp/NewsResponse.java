package avi.newsapp;

import java.util.ArrayList;

/**
 * Created by AVI on 04-Jan-18.
 */

public class NewsResponse {
    private String status;
    private int totalResults;
    private ArrayList<ArticleModel> articles;

    public String getStatus(){
         return status;
    }
    public void setStatus(String status){
        this.status=status;
    }
    public int getTotalResults(){
        return totalResults;
    }
    public void setTotalResults(int totalResults){
        this.totalResults=totalResults;
    }
    public ArrayList<ArticleModel> getArticles(){
        return articles;
    }
    public void setArticles(ArrayList<ArticleModel> articles){
        this.articles=articles;
    }

}
