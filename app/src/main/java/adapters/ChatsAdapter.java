package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;


import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;

import Utils.UserManager;
import activity.ChatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import modelos.Chat;

public class ChatsAdapter extends FirestoreRecyclerAdapter<Chat, ChatsAdapter.ViewHolder> {

    Context context;
    UserManager mUsersProvider;
    UserManager mAuthProvider;

    public ChatsAdapter(FirestoreRecyclerOptions<Chat> options, Context context) {
        super(options);
        this.context = context;
        mUsersProvider = new UserManager();
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Chat chat) {

        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        final String chatId = document.getId();
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(chat.getId_user1())) {
            getUserInfo(chat.getId_user2(), holder);
        }
        else {
            getUserInfo(chat.getId_user1(), holder);
        }

        holder.viewHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChatActivity(chatId, chat.getId_user1(), chat.getId_user2());
            }
        });

    }

    private void goToChatActivity(String chat_id, String user1, String user2) {
        Intent i = new Intent(context, ChatActivity.class);
        i.putExtra("idChat", chat_id);
        i.putExtra("idUser1", user1);
        i.putExtra("idUser2", user2);
        context.startActivity(i);

    }

    private void getUserInfo(String idUser, final ViewHolder holder) {
        mUsersProvider.getUser(idUser).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    if (documentSnapshot.contains("user_name")) {
                        String username = documentSnapshot.getString("user_name");
                        holder.textViewUsername.setText(username.toUpperCase());
                    }
                    if (documentSnapshot.contains("profile_image")) {
                        String imageProfile = documentSnapshot.getString("profile_image");
                        if (imageProfile != null) {
                            if (!imageProfile.isEmpty()) {
                                Picasso.with(context).load(imageProfile).into(holder.circleImageChat);
                            }
                        }
                    }
                }
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_chats, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUsername;
        TextView textViewLastMessage;
        CircleImageView circleImageChat;
        View viewHolder;

        public ViewHolder(View view) {
            super(view);
            textViewUsername = view.findViewById(R.id.textViewUsernameChat);
            textViewLastMessage = view.findViewById(R.id.textViewLastMessageChat);
            circleImageChat = view.findViewById(R.id.circleImageChat);
            viewHolder = view;
        }
    }

}

