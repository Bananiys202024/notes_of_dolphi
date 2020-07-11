package com.example.notes_of_dolphi.model;

import java.io.Serializable;

import javax.annotation.Generated;

public class Note implements Serializable {

	private int id;
	private String title;
	private String note;
	private String date_of_note;

	public Note(){}
	
	@Generated("SparkTools")
	private Note(Builder builder) {
		this.title = builder.title;
		this.note = builder.note;
		this.date_of_note = builder.date_of_note;
		this.id = builder.id;
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
	
	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	/**
	 * Creates builder to build {@link Note}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}
	

	/**
	 * Builder to build {@link Note}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String title;
		private String note;
		private String date_of_note;
		private int id;

		private Builder() {
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withNote(String note) {
			this.note = note;
			return this;
		}

		public Builder withDate_of_note(String date_of_note) {
			this.date_of_note = date_of_note;
			return this;
		}
		
		public Builder withId(int id) {
			this.id = id;
			return this;
		}

		public Note build() {
			return new Note(this);
		}
	}
	
}
