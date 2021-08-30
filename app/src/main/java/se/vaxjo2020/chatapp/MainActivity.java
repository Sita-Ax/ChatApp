package se.vaxjo2020.chatapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import se.vaxjo2020.chatapp.Fragment.ChatsFragment;
import se.vaxjo2020.chatapp.Fragment.UsersFragment;
import se.vaxjo2020.chatapp.model.Users;

public class MainActivity extends AppCompatActivity {

    //Firebase
    FirebaseUser currentUser;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("TAG", "onCreate: MAIN44");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(currentUser
                .getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Tab layout and viewpager
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter ViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        ViewPagerAdapter.addFragment(new ChatsFragment(), "MyChats");
        ViewPagerAdapter.addFragment(new UsersFragment(), "MyUsers");
//        ViewPagerAdapter.addFragment(new ProfileFragment(), "MyProf");

        viewPager.setAdapter(ViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
        }
        else if (id == R.id.saveddata){
            FirebaseAuth.getInstance();
            startActivity(new Intent(MainActivity.this, SavedChatActivity.class));
            finish();
        }
        else if (id == R.id.main){
            FirebaseAuth.getInstance();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //Class viewpageradapter
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
            this.fragments = new ArrayList<>();
            this.titles    = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    private void toastMessage() {
        Toast.makeText(this, "Something get wrong!", Toast.LENGTH_SHORT).show();
    }
}