package com.ridoy.mvvm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ridoy.mvvm.AddNoteActivity;
import com.ridoy.mvvm.MainActivity;
import com.ridoy.mvvm.Models.Note;
import com.ridoy.mvvm.R;
import com.ridoy.mvvm.databinding.NotesBinding;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.NoteViewHolder>  {

    private OnItemClickListener listener;

    public NoteAdapter() {
        super(diffCallback);
    }
    private static final DiffUtil.ItemCallback<Note> diffCallback=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getId()==newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getPriority()==newItem.getPriority();
        }
    };

    public Note getNoteAt(int pos){
        return getItem(pos);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note=getItem(position);
        holder.binding.notesTitle.setText(note.getTitle());
        holder.binding.notesPriority.setText(String.valueOf(note.getPriority()));
        holder.binding.notesDescription.setText(note.getDescription());
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder {
        NotesBinding binding;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            binding=NotesBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(Note note);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
