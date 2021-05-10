package Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;

import modelos.Publicacion;

public class PublicacionManager {

    CollectionReference collection;

    public PublicacionManager() {
        collection = FirebaseFirestore.getInstance().collection("Publicaciones");
    }

    public Task<Void> save(Publicacion publicacion) {
        return collection.document().set(publicacion);
    }

    public Query getAll() {
        return collection.orderBy("image", Query.Direction.DESCENDING);
    }
}
