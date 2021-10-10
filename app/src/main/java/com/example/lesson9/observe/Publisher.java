package com.example.lesson9.observe;

import com.example.lesson9.model.domain.NoteData;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        this.observers = new ArrayList<Observer>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notifyTask(NoteData noteData){
        for (Observer observer:observers){
            observer.updateState(noteData);
            unsubscribe(observer);
        }
    }
}
