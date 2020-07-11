package com.example.notes_of_dolphi.model;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

	private String message;
	private Note note;
	private User user;
	List<User> list_all_users;

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
}
