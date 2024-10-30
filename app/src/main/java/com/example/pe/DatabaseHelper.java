package com.example.pe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_SINHVIEN = "Sinhvien";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_IDNGANH = "idNganh";


    // Tên bảng và các cột cho bảng Chuyennganh
    private static final String TABLE_CHUYENNGANH = "Chuyennganh";
    private static final String COLUMN_IDNGANH_CN = "IDnganh";
    private static final String COLUMN_NAME_NGANH = "nameNganh";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Sinhvien
        String CREATE_SINHVIEN_TABLE = "CREATE TABLE " + TABLE_SINHVIEN + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DATE + " TEXT, "
                + COLUMN_GENDER + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_IDNGANH + " INTEGER)";
        db.execSQL(CREATE_SINHVIEN_TABLE);

        String CREATE_CHUYENNGANH_TABLE = "CREATE TABLE " + TABLE_CHUYENNGANH + " ("
                + COLUMN_IDNGANH_CN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_NGANH + " TEXT)";
        db.execSQL(CREATE_CHUYENNGANH_TABLE);
    }
    public long insertChuyennganh(String nameNganh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NGANH, nameNganh);

        // Chèn dữ liệu vào bảng Chuyennganh
        long result = db.insert(TABLE_CHUYENNGANH, null, values);
        db.close();
        return result; // Trả về ID của ngành mới hoặc -1 nếu thất bại
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SINHVIEN);
        onCreate(db);
    }

    // Phương thức để lấy danh sách sinh viên
    public List<Sinhvien> getAllSinhvien() {
        List<Sinhvien> sinhvienList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SINHVIEN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Sinhvien sinhvien = new Sinhvien();
                sinhvien.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                sinhvien.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                sinhvien.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                sinhvien.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                sinhvien.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                sinhvien.setIdNganh(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IDNGANH)));

                sinhvienList.add(sinhvien);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();

        return sinhvienList;
    }

    // Phương thức thêm sinh viên mới vào database
    public long insertSinhvien(String name, String date, String gender, String address, int idNganh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_IDNGANH, idNganh);

        // Chèn dữ liệu vào bảng Sinhvien
        long result = db.insert(TABLE_SINHVIEN, null, values);
        db.close();
        return result; // Trả về ID của sinh viên mới hoặc -1 nếu thất bại
    }
    public int updateSinhvien(int id, String name, String date, String gender, String address, int idNganh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_IDNGANH, idNganh);

        // Cập nhật thông tin sinh viên với ID xác định
        int result = db.update(TABLE_SINHVIEN, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result; // Trả về số dòng được cập nhật, -1 nếu thất bại
    }

    // Phương thức cập nhật thông tin Chuyennganh
    public int updateChuyennganh(int idNganh, String nameNganh) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_NGANH, nameNganh);

        // Cập nhật thông tin ngành với ID xác định
        int result = db.update(TABLE_CHUYENNGANH, values, COLUMN_IDNGANH_CN + "=?", new String[]{String.valueOf(idNganh)});
        db.close();
        return result; // Trả về số dòng được cập nhật, -1 nếu thất bại
    }
    public List<Chuyennganh> getAllChuyennganh() {
        List<Chuyennganh> chuyennganhList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CHUYENNGANH;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(selectQuery, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Chuyennganh chuyennganh = new Chuyennganh();

                    int idIndex = cursor.getColumnIndex(COLUMN_IDNGANH_CN);
                    int nameIndex = cursor.getColumnIndex(COLUMN_NAME_NGANH);

                    if (idIndex != -1 && nameIndex != -1) {
                        chuyennganh.setId(cursor.getInt(idIndex));
                        chuyennganh.setNameNganh(cursor.getString(nameIndex));

                        // Log thông tin chuyên ngành để kiểm tra
                        Log.d("DatabaseHelper", "ID: " + chuyennganh.getId() + ", Name: " + chuyennganh.getNameNganh());

                        chuyennganhList.add(chuyennganh);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log lỗi để dễ dàng kiểm tra
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return chuyennganhList;
    }
    public int deleteChuyennganh(int idNganh) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa chuyên ngành theo ID
        int result = db.delete(TABLE_CHUYENNGANH, COLUMN_IDNGANH_CN + "=?", new String[]{String.valueOf(idNganh)});
        db.close();
        return result; // Trả về số dòng đã xóa
    }
    public int deleteSinhvien(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Xóa sinh viên theo ID
        int result = db.delete(TABLE_SINHVIEN, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result; // Trả về số dòng đã xóa
    }


    public Sinhvien getSinhvienById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null; // Khai báo con trỏ
        Sinhvien sinhvien = null; // Khởi tạo biến sinhvien

        try {
            // Truy vấn dữ liệu
            cursor = db.query(TABLE_SINHVIEN, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

            // Kiểm tra nếu con trỏ có dữ liệu
            if (cursor != null && cursor.moveToFirst()) {
                sinhvien = new Sinhvien();
                sinhvien.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                sinhvien.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                sinhvien.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                sinhvien.setGender(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)));
                sinhvien.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                sinhvien.setIdNganh(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IDNGANH)));
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving student by ID: " + id, e); // Log lỗi nếu có
        } finally {
            if (cursor != null) {
                cursor.close(); // Đảm bảo đóng con trỏ nếu không null
            }
            db.close(); // Đóng cơ sở dữ liệu sau khi hoàn thành
        }
        return sinhvien; // Trả về sinh viên tìm thấy hoặc null nếu không tìm thấy
    }
//    public int deleteSinhvien(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete("sinhvien_table", "id = ?", new String[]{String.valueOf(id)});
//    }
public String getNganhNameById(int id) {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT " + COLUMN_NAME_NGANH + " FROM " + TABLE_CHUYENNGANH + " WHERE " + COLUMN_IDNGANH_CN + " = ?";
    Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
    String name = null;

    if (cursor.moveToFirst()) {
        name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_NGANH));
    }

    cursor.close();
    db.close();
    return name;
}



}
