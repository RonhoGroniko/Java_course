import java.util.*;

public class User{
    private String name;
    private String password;
    private boolean isAdmin;

    public User(String name, String password, boolean isAdmin){
        this.name = name;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUsername(){
        return name;
    }
    public boolean isAdmin(){
        return isAdmin;
    }

    public boolean verifyPassword(String password){
        return this.password.equals(password);
    }

}