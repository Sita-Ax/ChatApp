package se.vaxjo2020.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseDatabase firebaseDatabase;

    DatabaseReference myRef;
    EditText email, password;
    Button login, register, newPassword;

    ProgressBar progressBar;
    FirebaseUser currentUser;

    //create the login class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d("TAG", "onCreate: login 68");
        email = findViewById(R.id.lEmail);
        password = findViewById(R.id.lPassword);
        login = findViewById(R.id.loginBtn);
        register = findViewById(R.id.register);
        newPassword = findViewById(R.id.newPassword);

        fAuth = FirebaseAuth.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = findViewById(R.id.lProgressBar);
        firebaseDatabase = FirebaseDatabase.getInstance();

        //login button method
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String Email = email.getText().toString().trim();
//                String passWord = password.getText().toString().trim();
                if (TextUtils.isEmpty(email.getText().toString().trim())) {
                    email.setError("Email is Required.");
                    email.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString().trim())) {
                    password.setError("Password is Required.");
                    password.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    fAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i);
                                finish();
                            } else {
                                toastMessage("Wrong!");
                            }
                        }
                    });

                }
            }
        });
        //allow the user to registration class and to send a new password request ti email
        register.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
        newPassword.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, CreatePasswordActivity.class)));
    }

    //this is the toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(LoginActivity.this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        super.onStart();
        if (currentUser !=null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    //stop this
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("TAG", "onStop: in Login 222");
//        if(mAuthListener != null){
//            fAuth.removeAuthStateListener(mAuthListener);
//        }
//    }
    //use to send a Toast easy to use
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}