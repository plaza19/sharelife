package Utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import modelos.Mensaje;

public class MessageManager {

    CollectionReference collection;

    public MessageManager() {
        collection = FirebaseFirestore.getInstance().collection("Messages");
    }

    public Task<Void> crearMensaje(Mensaje mensaje) {
        DocumentReference document = collection.document();
        mensaje.setId(document.getId());
        return document.set(mensaje);
    }
}
