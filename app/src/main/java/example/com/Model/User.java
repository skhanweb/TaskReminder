package example.com.Model;

/**
 * Created by Toshiba on 28-Jan-16.
 */
public class User {

    private String username;
    private String password;
    private String email;

    public User(String name, String pass,String email){
        this.username = name;
        this.password = pass;
        this.email = email;
    }
    public User(){}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    

}
