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

import java.util.List;

public class ChuyennganhAdapter extends ArrayAdapter<Chuyennganh> {
    private Context context;
    private List<Chuyennganh> chuyennganhList;
    private DatabaseHelper databaseHelper; // Khai báo DatabaseHelper

    public ChuyennganhAdapter(Context context, List<Chuyennganh> chuyennganhList) {
        super(context, R.layout.list_item_chuyennganh, chuyennganhList);
        this.context = context;
        this.chuyennganhList = chuyennganhList;
        this.databaseHelper = new DatabaseHelper(context); // Khởi tạo DatabaseHelper
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_chuyennganh, parent, false);
        }

        Chuyennganh chuyennganh = chuyennganhList.get(position);

        TextView textViewIdNganh = convertView.findViewById(R.id.textViewIdNganh);
        TextView textViewNameNganh = convertView.findViewById(R.id.textViewNameNganh);
        Button updateButton = convertView.findViewById(R.id.buttonUpdateNganh);
        Button deleteButton = convertView.findViewById(R.id.buttonDeleteNganh);

        textViewIdNganh.setText("ID: " + chuyennganh.getId());
        textViewNameNganh.setText("Tên Ngành: " + chuyennganh.getNameNganh());

        // Thiết lập sự kiện cho nút "Cập nhật"
        updateButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, UpdateChuyennganhActivity.class);
            intent.putExtra("chuyennganh_id", chuyennganh.getId());
            context.startActivity(intent);
        });

        // Thiết lập sự kiện cho nút "Xóa"
        deleteButton.setOnClickListener(v -> {
            int deleteResult = databaseHelper.deleteChuyennganh(chuyennganh.getId());
            if (deleteResult > 0) {
                chuyennganhList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Xóa ngành thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Xóa ngành thất bại!", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
