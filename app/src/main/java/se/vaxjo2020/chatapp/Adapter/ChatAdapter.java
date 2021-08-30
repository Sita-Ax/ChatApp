package se.vaxjo2020.chatapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import se.vaxjo2020.chatapp.R;
import se.vaxjo2020.chatapp.model.Chats;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private final Context context;
    private final List<Chats> MyChats;
    private final String imgURL;
    FirebaseUser currentUser;

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    public ChatAdapter(Context context, List<Chats> MyChats, String imgURL) {
        this.context = context;
        this.MyChats = MyChats;
        this.imgURL = imgURL;

        }
    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("TAG", "onCreateViewHolder: ChatA");
        if (viewType == MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_right,parent,false);
            return new ChatAdapter.ViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_left,parent,false);
            return new ChatAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Chats chats = MyChats.get(position);
        holder.show.setText(chats.getMessage());
        if (imgURL.equals("default")){
            holder.profImage.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(imgURL).into(holder.profImage);
        }
    }

    @Override
    public int getItemCount() {
        return MyChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show;
        public ImageView profImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show = itemView.findViewById(R.id.outputMess);
            profImage = itemView.findViewById(R.id.image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (MyChats.get(position).getSender().equals(currentUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
