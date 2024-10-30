package com.example.pe;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SinhvienAdapter extends ArrayAdapter<Sinhvien> {
    private Context context;
    private List<Sinhvien> sinhvienList;
    private DatabaseHelper databaseHelper; // Thêm đối tượng DatabaseHelper

    public SinhvienAdapter(Context context, List<Sinhvien> sinhvienList) {
        super(context, R.layout.list_item_sinhvien, sinhvienList);
        this.context = context;
        this.sinhvienList = sinhvienList;
        this.databaseHelper = new DatabaseHelper(context); // Khởi tạo DatabaseHelper
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_sinhvien, parent, false);
        }

        Sinhvien sinhvien = sinhvienList.get(position);

        // Khai báo các TextView và Button
        TextView idTextView = convertView.findViewById(R.id.idTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        TextView ageTextView = convertView.findViewById(R.id.ageTextView);
        TextView genderTextView = convertView.findViewById(R.id.genderTextView);
        TextView addressTextView = convertView.findViewById(R.id.addressTextView);
        TextView idNganhTextView = convertView.findViewById(R.id.idNganhTextView);
        Button updateButton = convertView.findViewById(R.id.buttonUpdateSinhvien);
        Button buttonDeleteSinhvien = convertView.findViewById(R.id.buttonDeleteSinhvien);
        Button buttonLocateSinhvien = convertView.findViewById(R.id.buttonLocateSinhvien); // Nút định vị

        // Hiển thị thông tin sinh viên
        idTextView.setText("ID: " + sinhvien.getId());
        nameTextView.setText("Tên: " + sinhvien.getName());
        ageTextView.setText("Tuổi: " + calculateAge(sinhvien.getDate()));
        genderTextView.setText("Giới tính: " + sinhvien.getGender());
        addressTextView.setText("Địa chỉ: " + sinhvien.getAddress());
        idNganhTextView.setText("ID Ngành: " + sinhvien.getIdNganh());

        // Thiết lập sự kiện cho nút cập nhật
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để mở UpdateSinhvienActivity
                Intent intent = new Intent(context, UpdateSinhvienActivity.class);
                intent.putExtra("sinhvien_id", sinhvien.getId()); // Truyền ID sinh viên
                context.startActivity(intent); // Bắt đầu Activity
            }
        });

        // Thiết lập sự kiện cho nút xóa
        buttonDeleteSinhvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa sinh viên từ cơ sở dữ liệu
                int result = databaseHelper.deleteSinhvien(sinhvien.getId()); // Gọi phương thức xóa
                if (result > 0) {
                    sinhvienList.remove(position); // Xóa sinh viên khỏi danh sách
                    notifyDataSetChanged(); // Cập nhật ListView
                    Toast.makeText(context, "Đã xóa sinh viên!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa sinh viên thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonLocateSinhvien.setOnClickListener(v -> {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra("ADDRESS", sinhvien.getAddress()); // Truyền địa chỉ sinh viên
            context.startActivity(intent); // Bắt đầu Activity
        });

        return convertView;
    }

    // Phương thức tính tuổi từ ngày sinh
    private int calculateAge(String birthDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày tháng
        try {
            Date date = sdf.parse(birthDate);
            Calendar birthCalendar = Calendar.getInstance();
            birthCalendar.setTime(date);
            Calendar today = Calendar.getInstance();

            int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }
            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Trả về 0 nếu không tính được tuổi
        }
    }
}
