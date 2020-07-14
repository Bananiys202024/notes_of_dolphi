package com.example.notes_of_dolphi.model;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

	private String message;
	private Note note;
	private User user;
	private Draft draft;
	List<User> list_all_users;
	List<Note> list_all_notes;
	List<Draft> list_all_drafts;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getList_all_users() {
		return list_all_users;
	}

	public void setList_all_users(List<User> list_all_users) {
		this.list_all_users = list_all_users;
	}

	public List<Note> getList_all_notes() {
		return list_all_notes;
	}

	public void setList_all_notes(List<Note> list_all_notes) {
		this.list_all_notes = list_all_notes;
	}

	public List<Draft> getList_all_drafts() {
		return list_all_drafts;
	}

	public void setList_all_drafts(List<Draft> list_all_drafts) {
		this.list_all_drafts = list_all_drafts;
	}

	public Draft getDraft() {
		return draft;
	}

	public void setDraft(Draft draft) {
		this.draft = draft;
	}
	
}
