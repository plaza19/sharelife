package Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import modelos.Chat;

public class ChatManager {

    CollectionReference collection;

    public ChatManager() {
        collection = FirebaseFirestore.getInstance().collection("Chats");

    }

    public void create(Chat chat) {
        collection.document(chat.getId_user1() + chat.getId_user2()).set(chat);
    }

    public Query getAll(String id_user) {
        return collection.whereArrayContains("ids", id_user);
    }

    public Query getChatByUser1and2(String id_user1, String id_user2) {
        ArrayList<String> ids = new ArrayList<>();
        ids.add(id_user1 + id_user2);
        ids.add(id_user2 + id_user1);
        return collection.whereIn("id_chat", ids);
    }

}
