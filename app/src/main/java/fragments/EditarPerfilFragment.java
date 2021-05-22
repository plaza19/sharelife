package fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.UploadTask;
import com.plaza19.sharelife.BuildConfig;
import com.plaza19.sharelife.R;
import com.squareup.picasso.Picasso;
import com.yanzhenjie.loading.dialog.LoadingDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import Utils.FileUtil;
import Utils.ImageHander;
import Utils.PublicacionManager;
import Utils.UserManager;
import activity.PublicacionActivity;
import modelos.Publicacion;


public class EditarPerfilFragment extends Fragment {

    private String nombre_usuario = "";
    private String correo = "";
    private String url_profile_foto;
    private View view;
    private EditText edit_usuario, edit_correo;
    private Button btn_guardar;
    private FirebaseAuth auth;
    private UserManager userManager;
    private CardView cardView_galeria;
    private final int REQUEST_CODE = 1;
    private final int FOTO_REQUEST_CODE = 2;
    private File imagen, imagen_foto;
    private ImageView preview, back_arrow;
    private Button btn_publicar;
    private ImageHander imageHandler;
    private PublicacionManager publicacionManager;
    private EditText edit_comentario;
    private LoadingDialog loading_dialog;
    private AlertDialog.Builder builderAlert;
    private CharSequence opciones[];
    private String foto_absolute_path;
    private String foto_path;
    private ImageView profile_foto;
    private String aux_image_profile_foto;



    public EditarPerfilFragment() {
        // Required empty public constructor
    }

    public EditarPerfilFragment(String correo, String nombre_usuario, String url_profile_foto) {

        this.nombre_usuario = nombre_usuario;
        this.correo = correo;
        this.url_profile_foto = url_profile_foto;
        this.aux_image_profile_foto = "-1";

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        edit_usuario = view.findViewById(R.id.editText_usuario_editar_perfil);
        edit_correo = view.findViewById(R.id.editText_coreo_editar_perfil);
        btn_guardar = view.findViewById(R.id.btn_guardar_editar_perfil);
        edit_usuario.setText(this.nombre_usuario);
        edit_correo.setText(this.correo);
        btn_guardar.setEnabled(false);
        auth = FirebaseAuth.getInstance();
        userManager = new UserManager();
        profile_foto = view.findViewById(R.id.imageView_foto_perfil_editar_perfil);

        //otherFragment
        cardView_galeria = view.findViewById(R.id.card_view_galeria);
        preview = view.findViewById(R.id.imageView_preview_publicacion);
        back_arrow = view.findViewById(R.id.back_arrow_publicacion);
        btn_publicar = view.findViewById(R.id.btn_publicar_publicacion);
        edit_comentario = view.findViewById(R.id.edit_text_comentario_pubicacion);
        imageHandler = new ImageHander();
        publicacionManager = new PublicacionManager();
        loading_dialog = new LoadingDialog(getContext());
        loading_dialog.setMessage("Publicando");
        opciones = new CharSequence[] {"Seleccionardesde galería", "Hacer una foto"};
        builderAlert = new AlertDialog.Builder(getContext());
        builderAlert.setTitle("Selecciona una opción");

        profile_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarOpcionImagen(REQUEST_CODE);
            }
        });



        edit_usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("USERNAME", s.toString());
                if (nombre_usuario.equals(s.toString())) {
                    btn_guardar.setEnabled(false);
                    Log.d("USERNAME", s.toString());
                }else {
                    btn_guardar.setEnabled(true);
                }

            }
        });

        edit_correo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("CORREO", s.toString());
                if (correo.equals(s.toString())) {
                    btn_guardar.setEnabled(false);
                    Log.d("CORREO", s.toString());
                }else {
                    btn_guardar.setEnabled(true);
                }
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit_correo.getText().toString().matches(correo)) {
                    auth.getCurrentUser().updateEmail("user@example.com").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Correo actualizado con éxito", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "No se ha podido actualizar el correo", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

                if (!edit_usuario.getText().toString().matches(nombre_usuario)) {
                    userManager.UpdateUser(auth.getCurrentUser().getUid(), "user_name",  edit_usuario.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(getContext(), "Nombre de usuario actualizado con éxito", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "No se ha podido actualizar el nombre se usuario", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

                if (!aux_image_profile_foto.equals("-1")) {
                    guardarImagen();
                }

            }
        });

        return view;
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
        if (i.resolveActivity(getActivity().getPackageManager()) != null) {
            File fotoFile = null;
            try {
                fotoFile = crearFotoFile();
            }catch (Exception e) {
                Toast.makeText(getContext(),"Error con el archivo" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if(fotoFile != null) {
                //Uri foto_uri = FileProvider.getUriForFile(PublicacionActivity.this, "com.plaza10.sharelife", fotoFile);
                Uri foto_uri = FileProvider.getUriForFile(Objects.requireNonNull(getActivity().getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", fotoFile);
                i.putExtra(MediaStore.EXTRA_OUTPUT, foto_uri);
                startActivityForResult(i, FOTO_REQUEST_CODE);
            }
        }


    }

    private File crearFotoFile() {
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        imageHandler.save(getContext(), imagen).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    imageHandler.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();

                            publicacionManager.saveProfile_foto(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task_save) {
                                    if (task_save.isSuccessful()) {
                                        loading_dialog.hide();
                                        Toast.makeText(getContext(), "La imagen se ha guardado correctamente", Toast.LENGTH_SHORT).show();
                                    }else {
                                        loading_dialog.hide();
                                        Toast.makeText(getContext(), "Se ha producido un error al guardar la imagen", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }else {
                    loading_dialog.hide();
                    Toast.makeText(getContext(), "Se ha producido un error al guardar la imagen", Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //GALERIA

        if(requestCode == REQUEST_CODE && resultCode == -1) {//RESULT_OK
            try {
                Picasso.with(getContext()).load(data.getData()).into(profile_foto);
                imagen = FileUtil.from(getContext(), data.getData());
                btn_guardar.setEnabled(true);
                aux_image_profile_foto = "OK";

            }catch (Exception e) {
                Log.d("ERROR", "Error");
                Toast.makeText(getContext(), "Se ha producido un errror: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        //CAMARA

        if(requestCode == FOTO_REQUEST_CODE && resultCode == -1) {//RESULT_OK

            try {
                Picasso.with(getContext()).load(foto_path).into(profile_foto);
                imagen = FileUtil.from(getContext(), Uri.parse(foto_path));
                btn_guardar.setEnabled(true);
                aux_image_profile_foto = "OK";
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}