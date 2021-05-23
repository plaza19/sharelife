package Utils;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import modelos.Token;

public class TokenManager {

    CollectionReference collection;

    public TokenManager() {
        collection = FirebaseFirestore.getInstance().collection("Tokens");
    }

    public void create(String id_usuario) {
        if (id_usuario == null) {
            return;
        }
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                Token token = new Token(instanceIdResult.getToken());
                collection.document(id_usuario).set(token);
            }
        });

    }

    public Task<DocumentSnapshot> getToken(String id_user) {
        return collection.document(id_user).get();
    }

}
