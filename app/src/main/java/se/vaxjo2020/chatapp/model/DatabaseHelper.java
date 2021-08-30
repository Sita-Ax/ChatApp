package se.vaxjo2020.chatapp.model;

import android.util.Log;

public class DatabaseHelper {
    private int id;
    private String Message;

    public DatabaseHelper(int id, String message) {
        Log.d("TAG", "DatabaseHelper: 11");
        this.id = id;
        this.Message = message;
    }

    @Override
    public String toString() {
        return "DatabaseHelper{" +
                "id=" + id +
                ", Message='" + Message + '\'' +
                '}';
    }

    public DatabaseHelper() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
