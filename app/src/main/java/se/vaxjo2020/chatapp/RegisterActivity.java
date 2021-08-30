package se.vaxjo2020.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //widgets
    private EditText username, password, email;
    private TextView Login;
    private Button register;

    //Firebase
    private FirebaseAuth fAuth;
    private DatabaseReference myRef;
    private FirebaseUser currentUser;

    //show if the app is process
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("TAG", "onCreate: REG53");
        username = findViewById(R.id.lUsername);
        password = findViewById(R.id.lPassword);
        email = findViewById(R.id.lEmail);
        Login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.register);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.regProgress);
        progressBar = new ProgressBar(RegisterActivity.this);

//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //all the "titles" in my database
//                final String usernameValue = username.getText().toString().trim();
//                final String emailValue = email.getText().toString().trim();
//                final String passwordValue = password.getText().toString().trim();

                //valid the data
                if (TextUtils.isEmpty(username.getText().toString().trim())) {
                    username.setError("Inset your username.");
                    username.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email.getText().toString().trim())) {
                    email.setError("Email is Required.");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    password.setError("Password is Required.");
                    password.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    password.setError("The password must be longer than 6 characters");
                    password.requestFocus();
                }
                progressBar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            currentUser = fAuth.getCurrentUser();
                            assert currentUser != null;
                            String userId = currentUser.getUid();
                            fAuth = FirebaseAuth.getInstance();
                            myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(userId);
                            progressBar.setVisibility(View.GONE);
                            Log.d("TAG", "onComplete: 152Reg" + currentUser.toString());
                            //hashmaps
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username", username.getText().toString());
                            hashMap.put("imageURL", "default");

                            myRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("TAG", "onComplete: REG" + currentUser.toString());
                                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            toastMessage("invalid email or password");
                        }
                    }
                });
            }
        });
        Login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}