package modelos;

public class Usuario {

    private String id, email, username, password, url_profile_photo;

    public  Usuario() {

    }

    public Usuario(String id, String email, String username, String password, String url_profile_photo) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.url_profile_photo = url_profile_photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl_profile_photo() {
        return url_profile_photo;
    }

    public void setUrl_profile_photo(String url_profile_photo) {
        this.url_profile_photo = url_profile_photo;
    }
}
