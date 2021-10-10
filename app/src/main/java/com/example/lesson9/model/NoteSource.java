package com.example.lesson9.model;

import com.example.lesson9.model.domain.NoteData;

public interface NoteSource {
    int size();
    NoteData getNoteData(int position);
    void deleteNote(int position);
    void clearAllNote();
    void addNote(NoteData newNote);
    void updateNote(int position, NoteData newNote);

}
