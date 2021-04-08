package com.ridoy.mvvm.Databases;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ridoy.mvvm.Dao.Notedao;
import com.ridoy.mvvm.Models.Note;

@Database(entities = Note.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance;

    public abstract Notedao notedao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "Note_Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        Notedao notedao;

        public PopulateDbAsyncTask(NoteDatabase database) {
            notedao = database.notedao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            notedao.insertNote(new Note("Title 1", "Description 1", 1));
            notedao.insertNote(new Note("Title 2", "Description 2", 2));
            notedao.insertNote(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}
