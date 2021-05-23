package Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
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
        return collection.document(publicacion.getId()).set(publicacion);
    }

    public Task<Void> saveProfile_foto(String url) {
        return collection_user.document(auth.getCurrentUser().getUid()).update("profile_image", url );
    }

    public Query getAll() {

        return collection.orderBy("image").whereArrayContains("viewers", auth.getCurrentUser().getUid());
    }

    public Query getAllFromUser(String uid) {

        return collection.whereEqualTo("id_usuario", uid);
    }

    public Task<Void> updateViewersUser(String id_publication, String newValue) {

        return collection.document(id_publication).update("viewers", FieldValue.arrayUnion(newValue));
    }

    public Task<DocumentSnapshot> getLikesFromPublication(String id_publication, String id_user) {

        return collection.document(id_publication).get();
    }

    public Task<Void> UpdateLike_list(String id_publicacion, String id_user) {
        return collection.document(id_publicacion).update("liked_by", FieldValue.arrayUnion(id_user));
    }

    public Query getNumPublications(String uid) {
        return collection.whereEqualTo("id_usuario", uid);

    }











}
