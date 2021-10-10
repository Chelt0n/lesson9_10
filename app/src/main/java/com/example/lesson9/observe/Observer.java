package com.example.lesson9.observe;

import com.example.lesson9.model.domain.NoteData;

public interface Observer {
    void updateState(NoteData noteData);
}
