package Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

import modelos.Publicacion;

public class PublicacionManager {

    CollectionReference collection;
    CollectionReference collection_user;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    List<String> group;



    public PublicacionManager() {
        collection = FirebaseFirestore.getInstance().collection("Publicaciones");
        collection_user = FirebaseFirestore.getInstance().collection("Users");
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();




    }

    public Task<Void> save(Publicacion publicacion) {
        return collection.document().set(publicacion);
    }

    public Query getAll() {

        return collection.orderBy("image").whereArrayContains("viewers", auth.getCurrentUser().getUid());
    }

    public Query getAllFromUser() {

        return collection.whereEqualTo("id_usuario", auth.getCurrentUser().getUid());
    }






}
