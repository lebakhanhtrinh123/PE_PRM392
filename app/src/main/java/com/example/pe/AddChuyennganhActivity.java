package com.example.pe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AddChuyennganhActivity extends AppCompatActivity {
    private EditText editTextNameNganh;
    private Button buttonAddChuyennganh;
    private DatabaseHelper databaseHelper;
    private ListView listViewChuyennganh;
    private ChuyennganhAdapter chuyennganhAdapter;
    private List<Chuyennganh> chuyennganhList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chuyennganh);

        editTextNameNganh = findViewById(R.id.editTextNameNganh);
        buttonAddChuyennganh = findViewById(R.id.buttonAddChuyennganh);
        listViewChuyennganh = findViewById(R.id.listViewChuyennganh);
        databaseHelper = new DatabaseHelper(this);

        loadChuyennganhList();

        buttonAddChuyennganh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameNganh = editTextNameNganh.getText().toString().trim();

                if (!nameNganh.isEmpty()) {
                    long result = databaseHelper.insertChuyennganh(nameNganh);

                    if (result != -1) {
                        Toast.makeText(AddChuyennganhActivity.this, "Thêm ngành thành công!", Toast.LENGTH_SHORT).show();
                        editTextNameNganh.setText(""); // Xóa nội dung sau khi thêm
                        loadChuyennganhList(); // Cập nhật danh sách
                    } else {
                        Toast.makeText(AddChuyennganhActivity.this, "Thêm ngành thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddChuyennganhActivity.this, "Vui lòng nhập tên ngành", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện nhấn đúp
        listViewChuyennganh.setOnItemClickListener((parent, view, position, id) -> {
            // Đợi một chút để xem có nhấn đúp không
            view.postDelayed(() -> {
                // Nếu vẫn nhấn vào cùng một mục, thực hiện xóa
                if (listViewChuyennganh.isItemChecked(position)) {
                    Chuyennganh chuyennganh = chuyennganhList.get(position);
                    int deleteResult = databaseHelper.deleteChuyennganh(chuyennganh.getId());
                    if (deleteResult > 0) {
                        Toast.makeText(AddChuyennganhActivity.this, "Xóa ngành thành công!", Toast.LENGTH_SHORT).show();
                        loadChuyennganhList(); // Cập nhật danh sách
                    } else {
                        Toast.makeText(AddChuyennganhActivity.this, "Xóa ngành thất bại", Toast.LENGTH_SHORT).show();
                    }
                    listViewChuyennganh.clearChoices(); // Xóa lựa chọn
                }
            }, 200); // Thời gian nhấn đúp (200ms)
        });
    }

    private void loadChuyennganhList() {
        // Lấy danh sách ngành từ cơ sở dữ liệu
        chuyennganhList = databaseHelper.getAllChuyennganh();

        // Khởi tạo adapter cho ListView
        if (chuyennganhAdapter == null) {
            chuyennganhAdapter = new ChuyennganhAdapter(this, chuyennganhList);
            listViewChuyennganh.setAdapter(chuyennganhAdapter);
        } else {
            // Cập nhật dữ liệu cho adapter
            chuyennganhAdapter.clear();
            chuyennganhAdapter.addAll(chuyennganhList);
            chuyennganhAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tải lại danh sách khi Activity được quay lại
        loadChuyennganhList();
    }
}
