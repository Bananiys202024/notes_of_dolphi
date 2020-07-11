package com.example.notes_of_dolphi.controllers;

import android.content.Intent;
import android.view.View;

public class showPageAddDraft implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), AddDraftActivity.class);
        v.getContext().startActivity(intent);
    }

}
