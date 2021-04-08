package com.ridoy.mvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ridoy.mvvm.databinding.ActivityAddNoteBinding;

public class AddNoteActivity extends AppCompatActivity {

    ActivityAddNoteBinding binding;

    public static final String EXTRA_TITLE="EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION="EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY="EXTRA_PRIORITY";
    public static final String EXTRA_ID="EXTRA_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.notePriority.setMinValue(1);
        binding.notePriority.setMaxValue(10);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        if (getIntent().hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            binding.noteTitle.setText(getIntent().getStringExtra(AddNoteActivity.EXTRA_TITLE));
            binding.noteDescription.setText(getIntent().getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION));
            binding.notePriority.setValue(getIntent().getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_top_save:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveNote() {
        String title = binding.noteTitle.getText().toString();
        String description = binding.noteDescription.getText().toString();
        int priority = binding.notePriority.getValue();

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Insert Title and Description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data=new Intent();
        data.putExtra(EXTRA_TITLE,title);
        data.putExtra(EXTRA_DESCRIPTION,description);
        data.putExtra(EXTRA_PRIORITY,priority);

        int id=getIntent().getIntExtra(AddNoteActivity.EXTRA_ID,-1);
        if (id!=-1){
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }
}