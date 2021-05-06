package Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import modelos.Usuario;

public class UserManager {

    private CollectionReference collection_reference;

    public UserManager() {
        collection_reference = FirebaseFirestore.getInstance().collection("Users");
    }

    public Task<DocumentSnapshot> getUser(String id) {
        return collection_reference.document(id).get();
    }

    public Task<Void> createUser(Usuario user) {
        return collection_reference.document(user.getId()).set(user);
    }
}
