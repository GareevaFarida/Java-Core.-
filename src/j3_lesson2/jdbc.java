package j3_lesson2;

import j3_lesson2.Exceptions.NonuniqueUserRegistrationException;
import lesson4.swing.AuthException;

import java.sql.*;

public class jdbc {

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection connection;
    private PreparedStatement changeNicknameStatement;
    private PreparedStatement registrationStatement;
    private PreparedStatement getPasswordStatement;

    public jdbc() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:dbChatUsers.db");
            changeNicknameStatement = connection.prepareStatement("UPDATE Users SET nickname = ? WHERE login = ?");
            registrationStatement = connection.prepareStatement("INSERT INTO Users (login, password, nickname) VALUES (?, ?, ?)");
            getPasswordStatement = connection.prepareStatement("SELECT password FROM Users WHERE login = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changeNickname(String username, String nickname) throws SQLException {
        changeNicknameStatement.setString(1, nickname);
        changeNicknameStatement.setString(2, username);
        System.out.println("В БД изменено записей: "+changeNicknameStatement.executeUpdate());
    }

    public String getPassword(String username) throws ClassNotFoundException, SQLException, AuthException {
        String password;
        getPasswordStatement.setString(1, username);
        ResultSet resultSet = getPasswordStatement.executeQuery();
        if (resultSet.next()) {
            password = resultSet.getString(1);
            connection.close();
        } else {
            connection.close();
            throw new AuthException("User " + username + " isn't found in DB.");
        }
        return password;
    }

    public void RegistrateUser(String username, String password) throws ClassNotFoundException, SQLException, NonuniqueUserRegistrationException {
        getPasswordStatement.setString(1, username);
        ResultSet resultSet = getPasswordStatement.executeQuery();//ищем в БД пользователя с таким же именем
        if (resultSet.next()) {
            connection.close();
            throw new NonuniqueUserRegistrationException(username);
        }//если нашли, ругаемся и выходим

        registrationStatement.setString(1, username);
        registrationStatement.setString(2, password);
        registrationStatement.setString(3, username);
        System.out.println("Row updated: " + registrationStatement.executeUpdate());
        connection.close();
    }
}

