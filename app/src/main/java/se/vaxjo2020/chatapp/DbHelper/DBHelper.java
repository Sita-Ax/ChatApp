package se.vaxjo2020.chatapp.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import se.vaxjo2020.chatapp.model.DatabaseHelper;

public class DBHelper extends SQLiteOpenHelper {
    //Constructor
    public DBHelper(@Nullable Context context){
        super(context, "SAVED_CHAT_ACTIVITY", null, 1);
    }

//    private Context context;
//    private static final String TABLE_NAME = "SAVED_CHAT_ACTIVITY";
//
//    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
//
//    public static void Message(Context context, String message) {
//        Message(context, "");
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("TAG", "onCreateView: DBHELPER");
        String createTableStatement ="CREATE TABLE SAVED_CHAT_ACTIVITY (ID INTEGER PRIMARY KEY AUTOINCREMENT, MESSAGES TEXT)";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        try{
//            Message(context, "OnUpgade");
//            db.execSQL(DROP_TABLE);
//            onCreate(db);
//        }catch (Exception e){
//            Message(context, "" + e);
//        }
    }
    //insert the data
    public Boolean addData(DatabaseHelper databaseHelper){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MESSAGES", databaseHelper.getMessage());
        long insert = sqLiteDatabase.insert("SAVED_CHAT_ACTIVITY", null, cv);
        if (insert == -1)
        {
            return false;
        }
        else{
            return true;
        }
    }

    public List<DatabaseHelper> getAllUserData(){
        List<DatabaseHelper> userList= new ArrayList<>();
        String queryString = "SELECT * FROM SAVED_CHAT_ACTIVITY";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(queryString,null);
        if (cursor.moveToFirst()){
            do {
                int messageID = cursor.getInt(0);
                String message = cursor.getString(1);
                DatabaseHelper databaseHelper = new DatabaseHelper(messageID,message);
                userList.add(databaseHelper);
            }while (cursor.moveToNext());
        }
        else{
        }
        cursor.close();
        sqLiteDatabase.close();
        return userList;
    }

    public boolean deleteData(DatabaseHelper databaseHelper){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String queryString = "DELETE FROM SAVED_CHAT_ACTIVITY WHERE ID = " + databaseHelper.getId();//if deleted a message to tap on it
        Cursor cursor = sqLiteDatabase.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }
}
