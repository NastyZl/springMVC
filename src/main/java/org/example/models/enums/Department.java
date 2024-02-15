package org.example.models.enums;

public enum Department {
    SECURITY(1),
    DEVELOPMENT(2),
    MANAGEMENT(3),
    ;
    private int id;


    Department(int id) {
        this.id = id;

    }

    public int getId() {
        return id;
    }


}
