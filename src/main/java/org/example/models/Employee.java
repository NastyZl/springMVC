package org.example.models;

import org.example.models.enums.Post;

public class Employee {
    private int id;
    private String name;
    private Post post;

    public Employee(int id, String name, Post post) {
        this.id = id;
        this.name = name;
        this.post = post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
