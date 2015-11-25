package com.ghanshyamguides.simpletodo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class NewItemDialog extends DialogFragment {
    private EditText etItemTitle;
    private RadioGroup rgPriorities;

    private String priority;

    public NewItemDialog() {
    }

    public interface NewItemDialogListener {
        void onFinishEditDialog(String title, String priority);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item, parent);
        getDialog().setTitle("Add New Item");

        etItemTitle = (EditText) view.findViewById(R.id.etAddItemTitle);
        rgPriorities = (RadioGroup) view.findViewById(R.id.rgAddPriority);

        rgPriorities.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String priority1;
                switch (checkedId) {
                    case R.id.rbAddHigh: priority = "High";
                        break;
                    case R.id.rbAddMedium: priority = "Medium";
                        break;
                    case R.id.rbAddLow: priority  = "Low";
                        break;
                    default: priority = "Invalid Priority";
                        break;
                }
//                Toast.makeText(this, "Item edited.", Toast.LENGTH_SHORT).show();
//
//                Toast.makeText(this, priority,
//                        Toast.LENGTH_SHORT).show();
            }
        });

        Button btnSave = (Button) view.findViewById(R.id.btnAddSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etItemTitle.getText().toString();
                NewItemDialogListener dl = (NewItemDialogListener) getActivity();
                dl.onFinishEditDialog(title, priority);
                dismiss();
            }
        });

        return view;
    }
}

