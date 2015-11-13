package com.ghanshyamguides.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    private EditText editTodoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        editTodoItem = (EditText)findViewById(R.id.editTodoItem);
        editTodoItem.setText(getIntent().getStringExtra("item"));

    }


    public void onItemSaveEvent(View view) {
        Intent item = new Intent();
        item.putExtra("item",editTodoItem.getText().toString());
        item.putExtra("itemPos",getIntent().getStringExtra("itemPos"));
        setResult(RESULT_OK,item);
        finish();
    }

}
