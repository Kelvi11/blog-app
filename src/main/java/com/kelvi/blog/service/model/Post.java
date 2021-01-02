package com.kelvi.blog.service.model;

import java.time.LocalDateTime;

public class Post {

    public String uuid;

    public String title;
    public LocalDateTime last_modified;
    public String author;
    public String content;

    public Post() {
    }

    //TODO: Delete later when plugged a datasource
    public Post(String uuid, String title, LocalDateTime last_modified, String author, String content) {
        this.uuid = uuid;
        this.title = title;
        this.last_modified = last_modified;
        this.author = author;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "uuid='" + uuid + '\'' +
                ", title='" + title + '\'' +
                ", last_modified=" + last_modified +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
