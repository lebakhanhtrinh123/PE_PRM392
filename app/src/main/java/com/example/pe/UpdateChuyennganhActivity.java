package com.example.pe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class UpdateChuyennganhActivity extends AppCompatActivity {
    private EditText editTextUpdateNameNganh;
    private Button buttonUpdateChuyennganh;
    private DatabaseHelper databaseHelper;
    private int chuyennganhId; // ID của ngành cần cập nhật

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_chuyennganh);

        editTextUpdateNameNganh = findViewById(R.id.editTextUpdateNameNganh);
        buttonUpdateChuyennganh = findViewById(R.id.buttonUpdateChuyennganh);
        databaseHelper = new DatabaseHelper(this);

        // Lấy ID của ngành từ Intent
        chuyennganhId = getIntent().getIntExtra("chuyennganh_id", -1);

        // Lấy tên chuyên ngành hiện tại và hiển thị
        if (chuyennganhId != -1) {
            String currentName = databaseHelper.getNganhNameById(chuyennganhId);
            if (currentName != null) {
                editTextUpdateNameNganh.setText(currentName);
            } else {
                Toast.makeText(this, "Không tìm thấy chuyên ngành", Toast.LENGTH_SHORT).show();
            }
        }

        // Sự kiện cập nhật chuyên ngành
        buttonUpdateChuyennganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameNganh = editTextUpdateNameNganh.getText().toString().trim();

                if (!nameNganh.isEmpty() && chuyennganhId != -1) {
                    int result = databaseHelper.updateChuyennganh(chuyennganhId, nameNganh);

                    if (result > 0) {
                        Toast.makeText(UpdateChuyennganhActivity.this, "Cập nhật ngành thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(UpdateChuyennganhActivity.this, "Cập nhật ngành thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(UpdateChuyennganhActivity.this, "Vui lòng nhập tên ngành", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
