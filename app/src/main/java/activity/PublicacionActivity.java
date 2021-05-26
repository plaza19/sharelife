package activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;
import com.plaza19.sharelife.BuildConfig;
import com.plaza19.sharelife.PrincipalActivity;
import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.loading.dialog.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import Utils.FileUtil;
import Utils.Hash;
import Utils.ImageHander;
import Utils.PublicacionManager;
import Utils.UserManager;
import modelos.Publicacion;

public class PublicacionActivity extends AppCompatActivity {

    private CardView cardView_galeria;
    private final int REQUEST_CODE = 1;
    private final int FOTO_REQUEST_CODE = 2;
    private File imagen, imagen_foto;
    private ImageView preview, back_arrow;
    private Button btn_publicar;
    private ImageHander imageHandler;
    private PublicacionManager publicacionManager;
    private EditText edit_comentario;
    private FirebaseAuth auth;
    private LoadingDialog loading_dialog;
    private AlertDialog.Builder builderAlert;
    private CharSequence opciones[];
    private String foto_absolute_path;
    private String foto_path;
    private UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacion);

        userManager = new UserManager();
        cardView_galeria = findViewById(R.id.card_view_galeria);
        preview = findViewById(R.id.imageView_preview_publicacion);
        back_arrow = findViewById(R.id.back_arrow_publicacion);
        btn_publicar = findViewById(R.id.btn_publicar_publicacion);
        edit_comentario = findViewById(R.id.edit_text_comentario_pubicacion);
        imageHandler = new ImageHander();
        publicacionManager = new PublicacionManager();
        auth = FirebaseAuth.getInstance();
        loading_dialog = new LoadingDialog(PublicacionActivity.this);
        loading_dialog.setMessage("Publicando");
        opciones = new CharSequence[] {"Seleccionardesde galería", "Hacer una foto"};
        builderAlert = new AlertDialog.Builder(PublicacionActivity.this);
        builderAlert.setTitle("Selecciona una opción");
        cardView_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcionImagen(REQUEST_CODE);

            }
        });
        
        btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imagen != null) {
                    guardarImagen();
                }

            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PublicacionActivity.this, PrincipalActivity.class);
                startActivity(i);
            }
        });
    }

    private void seleccionarOpcionImagen(int REQUEST_CODE) {

        builderAlert.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               if (which == 0) {
                   abrirGaleria();
               }else if (which == 1) {
                   hacerFoto();
               }
            }
        });

        builderAlert.show();

    }

    private void hacerFoto() {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {
            File fotoFile = null;
            try {
                fotoFile = crearFotoFile();
            }catch (Exception e) {
                Toast.makeText(PublicacionActivity.this,"Error con el archivo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(fotoFile != null) {
                //Uri foto_uri = FileProvider.getUriForFile(PublicacionActivity.this, "com.plaza10.sharelife", fotoFile);
                Uri foto_uri = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", fotoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, foto_uri);
                startActivityForResult(i, FOTO_REQUEST_CODE);
            }
        }


    }

    private File crearFotoFile() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File fotoFile = null;
        try {
            fotoFile = File.createTempFile(new Date() + "_foto", ".jpg", storageDir);
            foto_path = "file:" + fotoFile.getAbsolutePath();
            foto_absolute_path = fotoFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fotoFile;
    }

    private void guardarImagen() {
        loading_dialog.show();
        imageHandler.save(PublicacionActivity.this, imagen).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    imageHandler.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Publicacion publicacion = new Publicacion();
                            publicacion.setId_usuario(auth.getCurrentUser().getUid());
                            publicacionManager.getAllFromUser(publicacion.getId_usuario()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    userManager.getListFollowers(auth.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (task.isSuccessful()) {
                                                ArrayList<String> viewers = new ArrayList<String>(); //se guardan las personas que pueden ver
                                                viewers = (ArrayList<String>) documentSnapshot.get("followed_by");
                                                publicacion.setViewers(viewers);
                                                String url = uri.toString();
                                                Publicacion publicacion = new Publicacion();
                                                publicacion.setImage(url);
                                                publicacion.setViewers(viewers);
                                                publicacion.setComentario(edit_comentario.getText().toString());
                                                publicacion.setId_usuario(auth.getCurrentUser().getUid());
                                                ArrayList<String> liked_by = new ArrayList<>(); //personas a las que le ha gustado la imagen
                                                liked_by.add(auth.getCurrentUser().getUid());
                                                publicacion.setLiked_by(liked_by);
                                                publicacion.setLikes(0);
                                                publicacion.setId(Hash.md5(publicacion.getImage() + publicacion.getId_usuario() + new Date().toString()));
                                                publicacionManager.save(publicacion).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task_save) {
                                                        if (task_save.isSuccessful()) {
                                                            loading_dialog.hide();
                                                            Toast.makeText(PublicacionActivity.this, "La imagen se ha guardado correctamente", Toast.LENGTH_SHORT).show();
                                                            resetForm();
                                                        }else {
                                                            loading_dialog.hide();
                                                            Toast.makeText(PublicacionActivity.this, "Se ha producido un error al guardar la imagen", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    });
                                }
                            });


                        }
                    });
                }else {
                    loading_dialog.hide();
                    Toast.makeText(PublicacionActivity.this, "Se ha producido un error al guardar la imagen", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void abrirGaleria() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GALERIA

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                imagen = FileUtil.from(PublicacionActivity.this, data.getData());
                preview.setImageBitmap(BitmapFactory.decodeFile(imagen.getAbsolutePath()));
                preview.setVisibility(View.VISIBLE);
            }catch (Exception e) {
                Log.d("ERROR", "Error");
                Toast.makeText(PublicacionActivity.this, "Se ha producido un errror: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        //CAMARA

        if (requestCode == FOTO_REQUEST_CODE && resultCode == RESULT_OK) {

            try {
                Picasso.with(PublicacionActivity.this).load(foto_path).into(preview);
                imagen = FileUtil.from(PublicacionActivity.this, Uri.parse(foto_path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            preview.setVisibility(View.VISIBLE);

        }
    }

    public void resetForm() {
        preview.setImageBitmap(null);
        edit_comentario.setText("");
        preview.setVisibility(View.GONE);
    }
}