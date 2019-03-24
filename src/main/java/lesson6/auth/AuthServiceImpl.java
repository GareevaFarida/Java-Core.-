package lesson6.auth;

import j3_lesson2.jdbc;
import lesson4.swing.AuthException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AuthServiceImpl implements AuthService {

    public Map<String, String> users = new HashMap<>();

    public AuthServiceImpl() {
//        users.put("ivan", "123");
//        users.put("petr", "345");
//        users.put("julia", "678");
//        users.put("vova", "333");
    }

    @Override
    public boolean authUser(String username, String password) {
//        String pwd = users.get(username);
//        return pwd != null && pwd.equals(password);

        try {
            String pwd = new jdbc().getPassword(username);
            return pwd != null && pwd.equals(password);
        } catch (ClassNotFoundException | SQLException | AuthException e) {
            e.printStackTrace();
        }

        return false;
    }
}
