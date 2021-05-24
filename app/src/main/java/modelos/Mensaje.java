package modelos;

import android.view.MenuItem;

public class Mensaje {

    private String id;
    private String idEnvia;
    private String idRecibe;
    private String idChat;
    private String mensaje;
    private Long time;
    private boolean visto;

    public Mensaje(String id, String idEnvia, String idRecibe, String idChat, String mensaje, Long time, boolean visto) {
        this.id = id;
        this.idEnvia = idEnvia;
        this.idRecibe = idRecibe;
        this.idChat = idChat;
        this.mensaje = mensaje;
        this.time = time;
        this.visto = visto;
    }

    public Mensaje() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdEnvia() {
        return idEnvia;
    }

    public void setIdEnvia(String idEnvia) {
        this.idEnvia = idEnvia;
    }

    public String getIdRecibe() {
        return idRecibe;
    }

    public void setIdRecibe(String idRecibe) {
        this.idRecibe = idRecibe;
    }

    public String getIdChat() {
        return idChat;
    }

    public void setIdChat(String idChat) {
        this.idChat = idChat;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public boolean isVisto() {
        return visto;
    }

    public void setVisto(boolean visto) {
        this.visto = visto;
    }
}
