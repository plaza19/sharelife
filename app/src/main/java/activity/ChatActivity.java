package activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;
import com.plaza19.sharelife.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import Utils.ChatManager;
import Utils.MessageManager;
import adapters.ChatsAdapter;
import modelos.Chat;
import modelos.Mensaje;

public class ChatActivity extends AppCompatActivity {

    String idUser1;
    String idUser2;
    String id_chat;

    ChatManager chatManager;
    MessageManager messageManager;
    EditText editMensaje;
    ImageView enviar_mensaje;

    View actionbarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ponerToolbar(R.layout.chat_toolbar);

        idUser1 = getIntent().getStringExtra("idUser1");
        idUser2 = getIntent().getStringExtra("idUser2");
        id_chat = getIntent().getStringExtra("idChat");
        chatManager = new ChatManager();
        messageManager = new MessageManager();
        editMensaje = findViewById(R.id.edit_mensaje_chat);
        enviar_mensaje = findViewById(R.id.imag_enviar_mensaje_chat);
        
        enviar_mensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarMensaje();
            }


        });

        verSiExisteChat();
    }

    private void enviarMensaje() {
        String textmensaje = editMensaje.getText().toString();
        if(!textmensaje.isEmpty()) {
            Mensaje mensaje = new Mensaje();
            if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(idUser1)) {
                mensaje.setIdEnvia(idUser1);
                mensaje.setIdRecibe(idUser2);
            }else {
                mensaje.setIdEnvia(idUser2);
                mensaje.setIdRecibe(idUser1);
            }
            mensaje.setIdChat(id_chat);
            mensaje.setTime(new Date().getTime());
            mensaje.setVisto(false);
            mensaje.setMensaje(textmensaje);

            messageManager.crearMensaje(mensaje).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        editMensaje.setText("");
                        Toast.makeText(ChatActivity.this, "El mensaje se creó correctamente", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ChatActivity.this, "El mensaje no se creó correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private void ponerToolbar(int chat_toolbar) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        actionbarView = inflater.inflate(chat_toolbar, null);
        actionBar.setCustomView(actionbarView);


    }

    private void verSiExisteChat() {
        chatManager.getChatByUser1and2(idUser1, idUser2).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                int tam = queryDocumentSnapshots.size();
                if (tam == 0) {
                    Toast.makeText(ChatActivity.this, "EL chat no existe", Toast.LENGTH_SHORT).show();
                    crearChat();
                }else {
                    Toast.makeText(ChatActivity.this, "EL chat existe", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void crearChat() {
        Chat chat = new Chat();
        chat.setId_user1(idUser1);
        chat.setId_user2(idUser2);
        chat.setEstaEscribiendo(false);
        chat.setTime(new Date().getTime());
        chat.setId_chat(idUser1 + idUser2);
        ArrayList<String> ids = new ArrayList<>();
        ids.add(idUser1);
        ids.add(idUser2);
        chat.setIds(ids);
        chatManager.create(chat);


    }
}