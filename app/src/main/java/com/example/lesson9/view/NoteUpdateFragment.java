package com.example.lesson9.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lesson9.MainActivity;
import com.example.lesson9.R;
import com.example.lesson9.model.domain.NoteData;
import com.example.lesson9.observe.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class NoteUpdateFragment extends Fragment {
    NoteData noteData;
    Publisher publisher;
    private TextInputEditText title, message;
    private DatePicker datePicker;
    private static final String ARG_NOTE_DATA = "ARG_NOTE_DATA";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        publisher = null;
    }

    public static NoteUpdateFragment newInstance(NoteData noteData) {
        NoteUpdateFragment fragment = new NoteUpdateFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE_DATA, noteData);
        fragment.setArguments(args);
        return fragment;
    }

    public static NoteUpdateFragment newInstance() {
        NoteUpdateFragment fragment = new NoteUpdateFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_NOTE_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_update, container, false);
        initView(view);
        if (noteData != null) {
            populateView();
        }
        return view;
    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        message = view.findViewById(R.id.inputMessage);
        datePicker = view.findViewById(R.id.inputDate);
    }

    private void populateView() {
        title.setText(noteData.getTitle());
        message.setText(noteData.getMessage());
        initDatePicker(noteData.getDate());
    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                null);
    }

    private Date getDateFromDatePicker() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, this.datePicker.getYear());
        cal.set(Calendar.MONTH, this.datePicker.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, this.datePicker.getDayOfMonth());
        return cal.getTime();
    }

    private NoteData collectNoteData() {
        String title = this.title.getText().toString();
        String message = this.message.getText().toString();
        Date date = getDateFromDatePicker();
        return new NoteData(title, message, date);
    }

    @Override
    public void onStop() {
        super.onStop();
        noteData = collectNoteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifyTask(noteData);
    }
}
