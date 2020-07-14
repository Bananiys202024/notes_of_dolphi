package com.example.notes_of_dolphi.model;

import java.io.Serializable;

public class Draft implements Serializable {

    private int id;
    private String title;
    private String note;
    private String date_of_note;
    private String owner;
    private Boolean synchronized_server;
    private Boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate_of_note() {
        return date_of_note;
    }

    public void setDate_of_note(String date_of_note) {
        this.date_of_note = date_of_note;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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
}
