package se.vaxjo2020.chatapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import se.vaxjo2020.chatapp.R;

public class ChatsFragment extends Fragment {
//    private RecyclerView recyclerViewC;
//    private ChatAdapter chatAdapter;
//    private List<Chats> MyChats;
//    ImageView imageView;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chats, container, false);

//        View view = inflater.inflate(R.layout.fragment_chats, container, false);

//        recyclerViewC = view.findViewById(R.id.recyclerViewC);
//        recyclerViewC.setHasFixedSize(true);
//        recyclerViewC.setLayoutManager(new LinearLayoutManager(getContext()));
//        ReadChat();
//        MyChats = new ArrayList<>();
//        return view;
    }

//    private void ReadChat(){
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("MyChats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                MyChats.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chats chats = snapshot.getValue(Chats.class);
//                    assert chats != null;
//                    if (!chats.getSender().equals(firebaseUser.getUid())){
//                        MyChats.add(chats);
//                    }
//                    chatAdapter = new ChatAdapter(getContext(), MyChats, "imgURL");
//                    recyclerViewC.setAdapter(chatAdapter);
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}