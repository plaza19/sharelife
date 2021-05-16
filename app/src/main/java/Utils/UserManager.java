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
        return collection_reference.document(id).get();
    }

    public Task<Void> createUser(Usuario user) {
        return collection_reference.document(user.getId()).set(user);
    }

    public Query getNumPublications() {

        collection_reference = FirebaseFirestore.getInstance().collection("Publicaciones");

        return collection_reference.whereEqualTo("id_usuario", auth.getCurrentUser().getUid());

    }
}
