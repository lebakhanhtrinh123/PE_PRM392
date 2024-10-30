package com.example.pe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddSinhvienActivity extends AppCompatActivity {
    private EditText editTextName, editTextDate, editTextGender, editTextAddress, editTextIdNganh;
    private Button buttonAddSinhvien;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sinhvien);

        editTextName = findViewById(R.id.editTextName);
        editTextDate = findViewById(R.id.editTextDate);
        editTextGender = findViewById(R.id.editTextGender);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextIdNganh = findViewById(R.id.editTextIdNganh);
        buttonAddSinhvien = findViewById(R.id.buttonAddSinhvien);

        databaseHelper = new DatabaseHelper(this);

        buttonAddSinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String gender = editTextGender.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                int idNganh = Integer.parseInt(editTextIdNganh.getText().toString().trim());

                if (!name.isEmpty() && !date.isEmpty() && !gender.isEmpty() && !address.isEmpty()) {
                    long result = databaseHelper.insertSinhvien(name, date, gender, address, idNganh);

                    if (result != -1) {
                        Toast.makeText(AddSinhvienActivity.this, "Sinhvien added successfully!", Toast.LENGTH_SHORT).show();
                        finish(); // Kết thúc activity sau khi thêm thành công
                    } else {
                        Toast.makeText(AddSinhvienActivity.this, "Failed to add Sinhvien", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddSinhvienActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}