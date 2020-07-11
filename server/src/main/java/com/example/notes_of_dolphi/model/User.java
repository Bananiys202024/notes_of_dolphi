package com.example.notes_of_dolphi.model;

import java.io.Serializable;

public class User implements Serializable  {

    private int id;
    private String username;
    private String password;
    private String email;
    private String permission;
    private Boolean synchronized_server;
    private Boolean deleted;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getSynchronized_server() {
        return synchronized_server;
    }

    public void setSynchronized_server(Boolean synchronized_server) {
        this.synchronized_server = synchronized_server;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
