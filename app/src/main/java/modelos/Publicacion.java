package modelos;

import java.util.ArrayList;

public class Publicacion {

    public Publicacion() {

    }

    private String id, comentario, image, id_usuario;
    private ArrayList<String> viewers, liked_by;
    private int likes =0;

    public Publicacion(String id, String comentario, String image, String id_usuario) {
        this.id = id;
        this.comentario = comentario;
        this.image = image;
        this.id_usuario = id_usuario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getImage() {
        return image;
    }

    public ArrayList<String> getLiked_by() {
        return liked_by;
    }

    public void setLiked_by(ArrayList<String> liked_by) {
        this.liked_by = liked_by;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public ArrayList<String> getViewers() {
        return viewers;
    }

    public void setViewers(ArrayList<String> viewers) {
        this.viewers = viewers;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
