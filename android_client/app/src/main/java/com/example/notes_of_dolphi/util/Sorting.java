package com.example.notes_of_dolphi.util;

import com.example.notes_of_dolphi.model.Note;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Sorting {

        public List<Note> sort_list_by_date(List<Note> list)
        {
            return   list.stream()
                    .sorted(Comparator.comparing(Note::getDate_of_note))
                    .collect(Collectors.toList());
        }
}
