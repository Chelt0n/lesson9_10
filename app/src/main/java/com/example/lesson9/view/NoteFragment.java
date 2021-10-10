package com.example.lesson9.view;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson9.MainActivity;
import com.example.lesson9.Navigation;
import com.example.lesson9.R;
import com.example.lesson9.model.NoteSource;
import com.example.lesson9.model.NoteSourceImpl;
import com.example.lesson9.model.domain.NoteData;
import com.example.lesson9.observe.Observer;
import com.example.lesson9.observe.Publisher;

import java.util.Calendar;

public class NoteFragment extends Fragment implements MyOnClickListener {
    RecyclerView recyclerView;
    NoteAdapter adapter;
    NoteSource data;
    Navigation navigation;
    Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    public static NoteFragment newInstance() {
        return new NoteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = new NoteSourceImpl(getResources()).init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_note, container, false);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new NoteAdapter(data, this);
        recyclerView.setAdapter(adapter);


        adapter.setListener(this);
        return view;

    }

    @Override
    public void onMyClick(View view, int position) {
        Toast.makeText(requireActivity(), "complete" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.toolbar_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAdd:
                navigation.addFragment(NoteUpdateFragment.newInstance(), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(NoteData noteData) {
                        data.addNote(noteData);
                        adapter.notifyItemInserted(data.size() - 1);
                    }
                });
                return true;
            case R.id.itemClear:
                data.clearAllNote();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getPositionContextMenu();
        switch (item.getItemId()) {
            case R.id.itemDelete:
                data.deleteNote(position);
                adapter.notifyItemRemoved(position);

                return true;
            case R.id.itemUpdate:
                navigation.addFragment(NoteUpdateFragment.newInstance(data.getNoteData(position)), true);
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateState(NoteData noteData) {
                        data.updateNote(position, noteData);
                        adapter.notifyItemChanged(position);
                    }
                });

                return true;
        }
        return super.onContextItemSelected(item);
    }
}
