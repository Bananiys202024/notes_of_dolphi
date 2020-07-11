package com.example.notes_of_dolphi.listeners.go_to_next_activity_page_listeners;

import android.content.Intent;
import android.view.View;

import com.example.notes_of_dolphi.controllers.RegistrationActivity;

public class showPageRegistrationListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), RegistrationActivity.class);
        v.getContext().startActivity(intent);
    }
}
