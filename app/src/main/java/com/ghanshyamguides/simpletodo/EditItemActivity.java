package com.ghanshyamguides.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EditItemActivity extends AppCompatActivity {

    int position;
    String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        EditText etEditField = (EditText) findViewById(R.id.etEditItem);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rgEditPriority);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbEditHigh:
                        priority = "High";
                        break;
                    case R.id.rbEditMedium:
                        priority = "Medium";
                        break;
                    case R.id.rbEditLow:
                        priority  = "Low";
                        break;
                    default: priority = "Invalid Priority";
                        break;
                }

                //Toast.makeText(getApplicationContext(), priority + " Priority, checkedId : " + checkedId, Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        String item = intent.getStringExtra("item");
        String priority = intent.getStringExtra("priority");

        etEditField.setText(item);
        switch(priority) {
            case "High" :
                radioGroup.check(R.id.rbEditHigh);
                break;
            case "Medium" :
                radioGroup.check(R.id.rbEditMedium);
                break;
            case "Low" :
                radioGroup.check(R.id.rbEditLow);
                break;
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

    public void onItemSaveEvent(View v) {
        EditText etEditField = (EditText) findViewById(R.id.etEditItem);

        String updatedTask=etEditField.getText().toString();
        if(updatedTask==null || updatedTask.trim().equals("")) {
            Toast.makeText(this, "EmptyTask Not allowed", Toast.LENGTH_SHORT).show();
        } else {
            Intent data = new Intent();
            data.putExtra("item", etEditField.getText().toString());
            data.putExtra("priority", priority);
            data.putExtra("position", position);
            setResult(RESULT_OK, data);
            finish();
        }
    }

}
