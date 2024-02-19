package org.example.models;

import org.example.models.enums.Post;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Employee {
    private int id;
    private int idDirector;
    @NotEmpty(message = "fill in the NAME field")
    @Size(min = 2, max = 30, message = "Name should be 2-30 chars")
    private String name;
    private Post post;

    public Employee(int id, String name, Post post) {
        this.id = id;
        this.name = name;
        this.post = post;
    }

    public Employee(int id, int idDirector, String name, Post post) {
        this.id = id;
        this.idDirector = idDirector;
        this.name = name;
        this.post = post;
    }

    public Employee() {
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

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }
}
