package fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.Query;
import com.plaza19.sharelife.R;

import Utils.ChatManager;
import adapters.ChatsAdapter;
import adapters.PublicacionAdapter;
import modelos.Chat;
import modelos.Publicacion;


public class ChatFragment extends Fragment {

    ChatsAdapter chatsAdapter;
    RecyclerView recyclerView;
    View view;
    ChatManager chatManager;


    public ChatFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_chat, container, false);
        chatManager = new ChatManager();
        recyclerView = view.findViewById(R.id.RecyclerViewChats);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Query query = chatManager.getAll(FirebaseAuth.getInstance().getCurrentUser().getUid());
        FirestoreRecyclerOptions<Chat> opciones = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class).build();

        chatsAdapter = new ChatsAdapter(opciones, getContext());
        recyclerView.setAdapter(chatsAdapter);
        chatsAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        chatsAdapter.stopListening();
    }
}