package com.example.lesson9.model;

import android.content.res.Resources;

import com.example.lesson9.R;
import com.example.lesson9.model.domain.NoteData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private List<NoteData> dataSource;
    private Resources resources;

    public NoteSourceImpl(Resources resources) {
        dataSource = new ArrayList<NoteData>();
        this.resources = resources;
    }

    public NoteSourceImpl init() {
        String[] title = resources.getStringArray(R.array.title);
        String[] message = resources.getStringArray(R.array.note);
        for (int i = 0; i < title.length; i++) {
            dataSource.add(new NoteData(title[i], message[i], Calendar.getInstance().getTime()));
        }

        return this;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public void deleteNote(int position) {
        dataSource.remove(position);
    }

    @Override
    public void clearAllNote() {
        dataSource.clear();
    }

    @Override
    public void addNote(NoteData newNote) {
        dataSource.add(0,newNote);

    }

    @Override
    public void updateNote(int position, NoteData newNote) {
        dataSource.set(position, newNote);
    }
}
