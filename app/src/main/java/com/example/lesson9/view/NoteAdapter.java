package com.example.lesson9.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson9.R;
import com.example.lesson9.model.NoteSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private NoteSource dataSource;
    private MyOnClickListener listener;
    private Fragment fragment;
    private int positionContextMenu;


    public int getPositionContextMenu() {
        return positionContextMenu;
    }


    public void setListener(MyOnClickListener listener) {
        this.listener = listener;
    }

    public NoteAdapter(NoteSource dataSource,Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(dataSource.getNoteData(position).getTitle());
        holder.message.setText(dataSource.getNoteData(position).getMessage());
        holder.date.setText(dataSource.getNoteData(position).getDate().toString());



    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView getTitle() {
            return title;
        }

        public void setTitle(TextView title) {
            this.title = title;
        }

        public TextView getMessage() {
            return message;
        }

        public void setMessage(TextView message) {
            this.message = message;
        }

        public TextView getDate() {
            return date;
        }

        public void setDate(TextView date) {
            this.date = date;
        }

        private TextView title;
        private TextView message;
        private TextView date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleNote);
            message = itemView.findViewById(R.id.messageNote);
            date = itemView.findViewById(R.id.dataNote);

            fragment.registerForContextMenu(message);

            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMyClick(v, getAdapterPosition());
                }
            });
            message.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();
                    positionContextMenu = getAdapterPosition();
                    return false;
                }
            });

        }
    }
}
