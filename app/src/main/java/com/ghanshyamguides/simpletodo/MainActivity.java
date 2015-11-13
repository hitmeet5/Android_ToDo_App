package com.ghanshyamguides.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView taskList;
    private EditText taskItem;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemAdapter;
    private final int REQUEST_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        taskList = (ListView)findViewById(R.id.itemList);
        taskItem = (EditText)findViewById(R.id.itemText);
        readItemsFromDatabase();
        itemAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, items);
        taskList.setAdapter(itemAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> adapter, View item, int pos, long id) {
                items.remove(pos);
                itemAdapter.notifyDataSetChanged();
                storeItemsToDatabase();
                return true;
            }
        });

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapter, View item, int pos, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("item", items.get(pos));
                intent.putExtra("itemPos", String.valueOf(pos));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


    }

    private void readItemsFromDatabase() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e ) {
          items = new ArrayList<String>();
        }
    }

    private void storeItemsToDatabase() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemAddEvent(View view) {
        String newTaskItem =  taskItem.getText().toString();
        itemAdapter.add(newTaskItem);
        storeItemsToDatabase();
        taskItem.setText("");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent editItem) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String item = editItem.getExtras().getString("item");
            int itemPos = Integer.parseInt(editItem.getExtras().getString("itemPos"));
            items.set(itemPos, item);
            itemAdapter.notifyDataSetChanged();
            storeItemsToDatabase();

        }
    }
}
