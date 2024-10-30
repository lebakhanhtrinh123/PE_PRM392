package com.example.pe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewSinhvien;
    private SinhvienAdapter sinhvienAdapter;
    private DatabaseHelper databaseHelper;
    private Button buttonGoToAddSinhvien;
    private Button buttonGoToAddChuyennganh;
    private Button buttonUpdateSinhvien;
    private Button buttonUpdateChuyennganh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSinhvien = findViewById(R.id.listViewSinhvien);
        buttonGoToAddSinhvien = findViewById(R.id.buttonGoToAddSinhvien);
        buttonGoToAddChuyennganh = findViewById(R.id.buttonGoToAddChuyennganh);
        databaseHelper = new DatabaseHelper(this);
        buttonUpdateSinhvien = findViewById(R.id.buttonUpdateSinhvien);
        buttonUpdateChuyennganh = findViewById(R.id.buttonUpdateChuyennganh);

        // Lấy danh sách sinh viên và gán vào Adapter
        loadSinhvienList();

        // Chuyển đến AddSinhvienActivity để thêm sinh viên mới
        buttonGoToAddSinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddSinhvienActivity.class);
                startActivity(intent);
            }
        });

        // Chuyển đến AddChuyennganhActivity để thêm ngành mới
        buttonGoToAddChuyennganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddChuyennganhActivity.class);
                startActivity(intent);
            }
        });

        buttonUpdateSinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateSinhvienActivity.class);
                intent.putExtra("sinhvien_id", 1); // Truyền ID sinh viên cần cập nhật (ví dụ 1)
                startActivity(intent);
            }
        });

        // Sự kiện cho nút cập nhật Chuyennganh
        buttonUpdateChuyennganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateChuyennganhActivity.class);
                intent.putExtra("chuyennganh_id", 1); // Truyền ID ngành cần cập nhật (ví dụ 1)
                startActivity(intent);
            }
        });

        // Xử lý sự kiện nhấn đúp
        listViewSinhvien.setOnItemClickListener((parent, view, position, id) -> {
            // Đợi một chút để xem có nhấn đúp không
            view.postDelayed(() -> {
                // Nếu vẫn nhấn vào cùng một mục, thực hiện xóa
                if (listViewSinhvien.isItemChecked(position)) {
                    Sinhvien sinhvien = sinhvienAdapter.getItem(position);
                    if (sinhvien != null) {
                        int deleteResult = databaseHelper.deleteSinhvien(sinhvien.getId());
                        if (deleteResult > 0) {
                            Toast.makeText(MainActivity.this, "Xóa sinh viên thành công!", Toast.LENGTH_SHORT).show();
                            loadSinhvienList(); // Cập nhật danh sách
                        } else {
                            Toast.makeText(MainActivity.this, "Xóa sinh viên thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    listViewSinhvien.clearChoices(); // Xóa lựa chọn
                }
            }, 200); // Thời gian nhấn đúp (200ms)
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSinhvienList(); // Tải lại danh sách sinh viên khi quay lại MainActivity
    }

    private void loadSinhvienList() {
        List<Sinhvien> sinhvienList = databaseHelper.getAllSinhvien();
        sinhvienAdapter = new SinhvienAdapter(this, sinhvienList);
        listViewSinhvien.setAdapter(sinhvienAdapter);
    }
}
