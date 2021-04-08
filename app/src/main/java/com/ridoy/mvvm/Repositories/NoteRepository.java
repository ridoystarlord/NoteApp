package com.ridoy.mvvm.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ridoy.mvvm.Dao.Notedao;
import com.ridoy.mvvm.Databases.NoteDatabase;
import com.ridoy.mvvm.Models.Note;

import java.util.List;

public class NoteRepository {

    private Notedao notedao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application){
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        notedao=noteDatabase.notedao();
        allNotes=notedao.getAllNotesByPriority();
    }
    public void insert(Note note){
        new InsertNotesAsyncTask(notedao).execute(note);
    }
    public void update(Note note){

        new UpdateNotesAsyncTask(notedao).execute(note);

    }
    public void delete(Note note){

        new DeleteNotesAsyncTask(notedao).execute(note);

    }
    public void deleteAllNotes(){

        new DeleteAllNotesAsyncTask(notedao).execute();

    }
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }

    //AsyncTask

    private static class InsertNotesAsyncTask extends AsyncTask<Note,Void,Void>{
        Notedao notedao;

        public InsertNotesAsyncTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.insertNote(notes[0]);
            return null;
        }
    }

    private static class UpdateNotesAsyncTask extends AsyncTask<Note,Void,Void>{
        Notedao notedao;

        public UpdateNotesAsyncTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.updateNote(notes[0]);
            return null;
        }
    }
    private static class DeleteNotesAsyncTask extends AsyncTask<Note,Void,Void>{
        Notedao notedao;

        public DeleteNotesAsyncTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            notedao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void,Void,Void>{
        Notedao notedao;

        public DeleteAllNotesAsyncTask(Notedao notedao) {
            this.notedao = notedao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notedao.deleteAllNotes();
            return null;
        }
    }

}
