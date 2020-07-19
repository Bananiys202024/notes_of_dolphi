package com.example.notes_of_dolphi.listeners.go_to_next_activity_page_listeners;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.notes_of_dolphi.controllers.RegistrationActivity;

public class OfflineRegistrationListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        System.out.println("Checking 1");
        Toast.makeText(v.getContext(), "Not possible register new user without connection with server", Toast.LENGTH_SHORT).show();
        System.out.println("Checking 2");

    }
}
