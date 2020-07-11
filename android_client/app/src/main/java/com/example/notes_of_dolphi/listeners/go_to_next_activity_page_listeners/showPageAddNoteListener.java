package com.example.notes_of_dolphi.listeners.go_to_next_activity_page_listeners;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.notes_of_dolphi.Validators.LogInValidator;
import com.example.notes_of_dolphi.controllers.AddNoteActivity;
import com.example.notes_of_dolphi.controllers.MainMenuActivity;

public class showPageAddNoteListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), AddNoteActivity.class);
            v.getContext().startActivity(intent);
    }

}
