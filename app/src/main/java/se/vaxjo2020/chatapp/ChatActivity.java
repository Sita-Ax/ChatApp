package se.vaxjo2020.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.vaxjo2020.chatapp.Adapter.ChatAdapter;
import se.vaxjo2020.chatapp.DbHelper.DBHelper;
import se.vaxjo2020.chatapp.model.Chats;
import se.vaxjo2020.chatapp.model.DatabaseHelper;
import se.vaxjo2020.chatapp.model.Users;

public class ChatActivity extends AppCompatActivity {

    TextView username;
    ImageView imageView;

    EditText msg_editText;
    Button sendBtn, mic;
    FirebaseUser currentUser;
    DatabaseReference myRef;
    Intent intent;
    public ArrayList<String> arrayList;

    ChatAdapter chatAdapter;
    List<Chats> MyChats;

    public String test;
    RecyclerView recyclerView1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.d("TAG", "onCreate: CHAT63");
        imageView = findViewById(R.id.imageview_profile);
        username = findViewById(R.id.username_profile);
        sendBtn = findViewById(R.id.btn_send);
        mic = findViewById(R.id.mic);
        msg_editText= findViewById(R.id.editMessage);

        recyclerView1 = findViewById(R.id.recycler_View);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager);

        intent = getIntent();
        String userid = intent.getStringExtra("userid");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
                Users users = DataSnapshot.getValue(Users.class);
                username.setText(users.getUsername());
                if (users.getImageURL().equals("default")){
                    imageView.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Glide.with(ChatActivity.this)
                            .load(users.getImageURL())
                            .into(imageView);
                }
                readMessages(currentUser.getUid(),userid,users.getImageURL());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = msg_editText.getText().toString();
//                test = msg_editText.getText().toString();
                DBHelper dbHelper = new DBHelper(ChatActivity.this);
                if (!msg.equals("")){
                    DatabaseHelper databaseHelper = new DatabaseHelper(-1,msg);
//                    test = msg_editText.getText().toString();
                    msg = msg_editText.getText().toString();
                    boolean Succes = dbHelper.addData(databaseHelper);
                    toastMessage("Success");
                    sendMessage(currentUser.getUid(),userid,msg);
                }else {
                    toastMessage("Don't send a empty message. ");
                }
                msg_editText.setText("");
            }
        });

    }

    private void sendMessage(String sender, String receiver, String message){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        HashMap<String , Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);
        myRef.child("MyChats").push().setValue(hashMap);
    }


    private void readMessages(String myId,String userId,String imageurl){
        MyChats = new ArrayList<>();
        myRef = FirebaseDatabase.getInstance().getReference("MyChats");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot DataSnapshot) {
//                MyChats.clear();
                for (DataSnapshot snapshot : DataSnapshot.getChildren()){
                    Chats chats = snapshot.getValue(Chats.class);
                    assert chats != null;
                    if (chats.getReceiver().equals(myId) && chats.getSender().equals(userId) || chats.getReceiver().equals(userId) && chats.getSender().equals(myId)){
                        MyChats.add(chats);
                    }
                    chatAdapter = new ChatAdapter(ChatActivity.this, MyChats, imageurl);
                    recyclerView1.setAdapter(chatAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}