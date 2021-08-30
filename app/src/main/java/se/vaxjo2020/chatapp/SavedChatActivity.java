package se.vaxjo2020.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import se.vaxjo2020.chatapp.DbHelper.DBHelper;
import se.vaxjo2020.chatapp.model.DatabaseHelper;

public class SavedChatActivity extends AppCompatActivity {

    private Button deleteBtn, show;
    private ListView listView;

    private FirebaseUser currentUser;
    private DBHelper dbHelper;
    List<DatabaseHelper> allmessages;
    ArrayAdapter chatArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_chat);

        deleteBtn = findViewById(R.id.delete_btn);
        listView = findViewById(R.id.savedChats);
        show = findViewById(R.id.show);
        dbHelper = new DBHelper(SavedChatActivity.this);
        Showall();
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("This is the deleted");
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper = new DBHelper(SavedChatActivity.this);
                Showall();
                toastMessage("Show all");
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper selectMessage = (DatabaseHelper) parent.getItemAtPosition(position);
                dbHelper.deleteData(selectMessage);
                Showall();
            }
        });
    }

    public void Showall() {
        chatArrayAdapter = new ArrayAdapter<DatabaseHelper>(SavedChatActivity.this, android.R.layout.simple_expandable_list_item_1, dbHelper.getAllUserData());
        listView.setAdapter(chatArrayAdapter);
    }

    //this is the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SavedChatActivity.this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (id == R.id.saveddata){
            FirebaseAuth.getInstance();
            startActivity(new Intent(SavedChatActivity.this, SavedChatActivity.class));
            finish();
        }
        else if (id == R.id.main){
            FirebaseAuth.getInstance();
            startActivity(new Intent(SavedChatActivity.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}