package com.ghanshyamguides.simpletodo;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewItemDialog.NewItemDialogListener {

    private ListView taskList;
    private ArrayList<TodoItem> items;
    private ArrayAdapter<TodoItem> itemAdapter;
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
                onItemAddEvent();
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });



        taskList = (ListView)findViewById(R.id.itemList);
        items = TodoItem.loadAll();
        //taskItem = (EditText)findViewById(R.id.itemText);
       // readItemsFromDatabase();
        itemAdapter = new com.ghanshyamguides.simpletodo.TodoItemAdapter(this,items);
        taskList.setAdapter(itemAdapter);
        setupListViewListener();

    }

    private void setupListViewListener() {
        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                items.get(pos).delete();
                items.remove(pos);
                itemAdapter.notifyDataSetChanged();
                return true;
            }
        });

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                TodoItem currentItem = items.get(pos);
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("priority", currentItem.Priority);
                i.putExtra("position", pos);
                i.putExtra("id", currentItem.getId());
                i.putExtra("item", currentItem.Title);
                startActivityForResult(i, REQUEST_CODE);
            }
        });


    }

   /* private void readItemsFromDatabase() {
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
    }*/

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

        if (id == R.id.add_new) {
            onItemAddEvent();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void onItemAddEvent() {
       /* String newTaskItem =  taskItem.getText().toString();
        itemAdapter.add(newTaskItem);
        storeItemsToDatabase();
        taskItem.setText("");*/
        FragmentManager fm = getFragmentManager();
        com.ghanshyamguides.simpletodo.NewItemDialog frag = new com.ghanshyamguides.simpletodo.NewItemDialog();
        frag.show(fm, "fragment_add_item");
    }

    public void onItemAddEvent(View view) {
       /* String newTaskItem =  taskItem.getText().toString();
        itemAdapter.add(newTaskItem);
        storeItemsToDatabase();
        taskItem.setText("");*/
        FragmentManager fm = getFragmentManager();
        com.ghanshyamguides.simpletodo.NewItemDialog frag = new com.ghanshyamguides.simpletodo.NewItemDialog();
        frag.show(fm, "fragment_add_item");
  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent editItem) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int position = editItem.getExtras().getInt("position");
            String editedItem = editItem.getExtras().getString("item");
            String priority = editItem.getExtras().getString("priority");

            TodoItem item = items.get(position);
            item.Title = editedItem;
            item.Priority = priority;
            item.save();

            itemAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Item edited", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onFinishEditDialog(String title, String priority) {
        if(priority==null){
            priority="Low";
        }
        if(title==null || title.trim().equals("")) {
            Toast.makeText(this, "Empty Task ignored", Toast.LENGTH_SHORT).show();
        } else {
            com.ghanshyamguides.simpletodo.TodoItem item = new com.ghanshyamguides.simpletodo.TodoItem(title, priority);
            item.save();
            itemAdapter.add(item);
        }
    }
}
