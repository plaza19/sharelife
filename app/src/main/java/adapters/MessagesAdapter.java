package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.plaza19.sharelife.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import Utils.UserManager;

import modelos.Mensaje;


public class MessagesAdapter extends FirestoreRecyclerAdapter<Mensaje, MessagesAdapter.ViewHolder> {

    Context context;
    UserManager mUsersProvider;
    UserManager mAuthProvider;
    DateFormat hourFormat;

    public MessagesAdapter(FirestoreRecyclerOptions<Mensaje> options, Context context) {
        super(options);
        this.context = context;
        mUsersProvider = new UserManager();
        hourFormat = new SimpleDateFormat("HH:mm");
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Mensaje mensaje) {

        DocumentSnapshot document = getSnapshots().getSnapshot(position);
        final String idMensaje = document.getId();
        holder.textViewMensaje.setText(mensaje.getMensaje());
        holder.textViewFecha.setText(hourFormat.format(new Date(mensaje.getTime())));

        if(mensaje.getIdEnvia().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.setMargins(150, 0, 0, 0);
            holder.linearLayout_mensaje.setLayoutParams(params);
            holder.linearLayout_mensaje.setPadding(30,20,25,20);
            holder.linearLayout_mensaje.setBackground(context.getResources().getDrawable(R.drawable.message_round));
            holder.visto.setVisibility(View.VISIBLE);

        }else {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.setMargins(0, 0, 150, 0);
            holder.linearLayout_mensaje.setLayoutParams(params);
            holder.linearLayout_mensaje.setPadding(30,20,30,20);
            holder.linearLayout_mensaje.setBackground(context.getResources().getDrawable(R.drawable.message_round_user2));
            holder.visto.setVisibility(View.GONE);
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_item, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewMensaje;
        TextView textViewFecha;
        ImageView visto;
        View viewHolder;
        LinearLayout linearLayout_mensaje;

        public ViewHolder(View view) {
            super(view);
            textViewMensaje = view.findViewById(R.id.textViewMessage_chat_message);
            textViewFecha = view.findViewById(R.id.textView_date_chat_message);
            visto = view.findViewById(R.id.imageView_viewed_chat_message);
            linearLayout_mensaje = view.findViewById(R.id.linearLayout_chat_message);
            viewHolder = view;
        }
    }

}

