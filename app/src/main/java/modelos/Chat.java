package modelos;

import java.util.ArrayList;

public class Chat {

    private String id_chat;
    private String id_user1;
    private String id_user2;
    private boolean estaEscribiendo;
    private long time;
    private ArrayList<String> ids;

    public Chat() {

    }

    public Chat(String id_chat, String id_user1, String id_user2, boolean estaEscribiendo, long time, ArrayList<String> ids) {
        this.id_chat = id_chat;
        this.id_user1 = id_user1;
        this.id_user2 = id_user2;
        this.estaEscribiendo = estaEscribiendo;
        this.time = time;
        this.ids = ids;
    }

    public boolean isEstaEscribiendo() {
        return estaEscribiendo;
    }

    public void setEstaEscribiendo(boolean estaEscribiendo) {
        this.estaEscribiendo = estaEscribiendo;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId_user1() {
        return id_user1;
    }

    public void setId_user1(String id_user1) {
        this.id_user1 = id_user1;
    }

    public String getId_user2() {
        return id_user2;
    }

    public void setId_user2(String id_user2) {
        this.id_user2 = id_user2;
    }

    public String getId_chat() {
        return id_chat;
    }

    public void setId_chat(String id_chat) {
        this.id_chat = id_chat;
    }

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }
}
