package Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import modelos.Usuario;

public class UserManager {

    private CollectionReference collection_reference;
    private FirebaseAuth auth;

    public UserManager() {
        collection_reference = FirebaseFirestore.getInstance().collection("Users");
        auth = FirebaseAuth.getInstance();
    }

    public Task<DocumentSnapshot> getUser(String id) {
        return FirebaseFirestore.getInstance().collection("Users").document(id).get();
    }

    public Task<Void> UpdateUser(String id, String value_id, String newValue) {
        return FirebaseFirestore.getInstance().collection("Users").document(id).update(value_id, newValue);
    }



    public Task<Void> createUser(Usuario user) {
        return collection_reference.document(user.getId()).set(user);
    }



    public Query getUsers_list_query(String newText) {

        return collection_reference.orderBy("user_name").startAt(newText).endAt(newText + "\uf8ff");
    }

    public Query getUserByUserName(String username) {
        return collection_reference.whereEqualTo("user_name", username);
    }

    public Task<DocumentSnapshot> getCountFollowers(String id_user) {
        return collection_reference.document(id_user).get();
    }
}
