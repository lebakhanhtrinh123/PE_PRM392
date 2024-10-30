package com.example.pe;

public class Chuyennganh {
    private int id;
    private String nameNganh;

    // Constructor mặc định
    public Chuyennganh() {}

    // Constructor với các tham số
    public Chuyennganh(int id, String nameNganh) {
        this.id = id;
        this.nameNganh = nameNganh;
    }

    // Getter và Setter cho ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho Tên Ngành
    public String getNameNganh() {
        return nameNganh;
    }

    public void setNameNganh(String nameNganh) {
        this.nameNganh = nameNganh;
    }
}
