package se.vaxjo2020.chatapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import se.vaxjo2020.chatapp.ChatActivity;
import se.vaxjo2020.chatapp.R;
import se.vaxjo2020.chatapp.model.Users;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context context;
    private List<Users> MyUsers;

    public UsersAdapter(Context context, List<Users> MyUsers) {
        this.context = context;
        this.MyUsers = MyUsers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: UserA");
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users users = MyUsers.get(position);
        holder.username.setText(users.getUsername());
        if (users.getImageURL().equals("default")){
            holder.myImage.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(context).load(users.getImageURL())
                    .into(holder.myImage);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatActivity.class);
                i.putExtra("userid",users.getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MyUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView myImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.userName);
            myImage = itemView.findViewById(R.id.userImage);
        }
    }

}
