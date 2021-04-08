package com.ridoy.mvvm.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ridoy.mvvm.Models.Note;

import java.util.List;

@Dao
public interface Notedao {
    @Insert
    void insertNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("DELETE FROM Notes_table")
    void deleteAllNotes();

    @Query("SELECT * FROM Notes_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotesByPriority();
}
