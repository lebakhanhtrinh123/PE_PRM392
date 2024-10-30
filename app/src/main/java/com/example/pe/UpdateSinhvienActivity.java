package com.example.pe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateSinhvienActivity extends AppCompatActivity {
    private EditText editTextUpdateName, editTextUpdateDate, editTextUpdateGender, editTextUpdateAddress, editTextUpdateIdNganh;
    private Button buttonUpdateSinhvien;
    private DatabaseHelper databaseHelper;
    private int sinhvienId; // ID của sinh viên cần cập nhật

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sinhvien);

        // Ánh xạ các view
        editTextUpdateName = findViewById(R.id.editTextUpdateName);
        editTextUpdateDate = findViewById(R.id.editTextUpdateDate);
        editTextUpdateGender = findViewById(R.id.editTextUpdateGender);
        editTextUpdateAddress = findViewById(R.id.editTextUpdateAddress);
        editTextUpdateIdNganh = findViewById(R.id.editTextUpdateIdNganh);
        buttonUpdateSinhvien = findViewById(R.id.buttonUpdateSinhvien);

        // Lấy ID của sinh viên cần cập nhật từ Intent
        sinhvienId = getIntent().getIntExtra("sinhvien_id", -1);

        databaseHelper = new DatabaseHelper(this);

        // Lấy thông tin sinh viên theo ID và hiển thị
        loadSinhvienData(sinhvienId);

        // Cập nhật thông tin sinh viên
        buttonUpdateSinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextUpdateName.getText().toString().trim();
                String date = editTextUpdateDate.getText().toString().trim();
                String gender = editTextUpdateGender.getText().toString().trim();
                String address = editTextUpdateAddress.getText().toString().trim();
                int idNganh;

                // Kiểm tra nếu ID ngành hợp lệ
                try {
                    idNganh = Integer.parseInt(editTextUpdateIdNganh.getText().toString().trim());
                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateSinhvienActivity.this, "Vui lòng nhập ID Ngành hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!name.isEmpty() && sinhvienId != -1) {
                    int result = databaseHelper.updateSinhvien(sinhvienId, name, date, gender, address, idNganh);

                    if (result > 0) {
                        Toast.makeText(UpdateSinhvienActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Quay lại Activity trước
                    } else {
                        Toast.makeText(UpdateSinhvienActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateSinhvienActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Hàm để tải thông tin sinh viên và hiển thị trong EditText
    private void loadSinhvienData(int sinhvienId) {
        Sinhvien sinhvien = databaseHelper.getSinhvienById(sinhvienId);
        if (sinhvien != null) {
            editTextUpdateName.setText(sinhvien.getName());
            editTextUpdateDate.setText(sinhvien.getDate());
            editTextUpdateGender.setText(sinhvien.getGender());
            editTextUpdateAddress.setText(sinhvien.getAddress());
            editTextUpdateIdNganh.setText(String.valueOf(sinhvien.getIdNganh()));
        } else {
            Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
            finish(); // Nếu không tìm thấy sinh viên, quay lại Activity trước
        }
    }
}
