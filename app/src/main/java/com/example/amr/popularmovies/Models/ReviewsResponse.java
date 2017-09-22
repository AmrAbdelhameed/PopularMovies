package com.example.amr.popularmovies.Models;

import java.util.List;

/**
 * Created by amr on 22/09/17.
 */

public class ReviewsResponse {

    /**
     * id : 211672
     * page : 1
     * results : [{"id":"55a58e46c3a3682bb2000065","author":"Andres Gomez","content":"The minions are a nice idea and the animation and London recreation is really good, but that's about it.\r\n\r\nThe script is boring and the jokes not really funny.","url":"https://www.themoviedb.org/review/55a58e46c3a3682bb2000065"},{"id":"55e108c89251416c0b0006dd","author":"movizonline.com","content":"a nice idea and the animation.the new thing in animation field.a movie that every one should like an kid or old man.","url":"https://www.themoviedb.org/review/55e108c89251416c0b0006dd"},{"id":"59c38385c3a368141e00e1f2","author":"Tom Morvolo Riddle","content":"I enjoyed it a lot with my nephews. It's a fun family movie to watch with young'uns and there are some adult flavored jokes here and there.","url":"https://www.themoviedb.org/review/59c38385c3a368141e00e1f2"}]
     * total_pages : 1
     * total_results : 3
     */

    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private List<ResultsBean> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * id : 55a58e46c3a3682bb2000065
         * author : Andres Gomez
         * content : The minions are a nice idea and the animation and London recreation is really good, but that's about it.

         The script is boring and the jokes not really funny.
         * url : https://www.themoviedb.org/review/55a58e46c3a3682bb2000065
         */

        private String id;
        private String author;
        private String content;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
