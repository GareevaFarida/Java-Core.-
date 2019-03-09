package j3_lesson2;

import j3_lesson2.Exceptions.NonuniqueUserRegistrationException;
import lesson4.swing.AuthException;

import java.sql.*;

public class jdbc {

    public static void changeNickname(String username,String nickname) throws ClassNotFoundException, SQLException{
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:dbChatUsers.db")) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Users SET nickname = ? WHERE login = ?");
            preparedStatement.setString(1,nickname);
            preparedStatement.setString(2, username);
            System.out.println("В БД изменено записей: "+preparedStatement.executeUpdate());
        }
    }

    public static String getPassword(String username) throws ClassNotFoundException, SQLException, AuthException {
        String password;
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:dbChatUsers.db")) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM Users WHERE login = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                password = resultSet.getString(1);
                connection.close();
            } else {
                connection.close();
                throw new AuthException("User " + username + " isn't found in DB.");
            }
        }
        return password;
    }

    public static void RegistrateUser(String username, String password) throws ClassNotFoundException, SQLException, NonuniqueUserRegistrationException {
        Class.forName("org.sqlite.JDBC");
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:dbChatUsers.db")) {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT password FROM Users WHERE login = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();//ищем в БД пользователя с таким же именем
            if (resultSet.next()) {
                connection.close();
                throw new NonuniqueUserRegistrationException(username);
            }//если нашли, ругаемся и выходим

            PreparedStatement insertClient = connection.prepareStatement("INSERT INTO Users (login, password, nickname) " +
                    "VALUES (?, ?, ?)");

            insertClient.setString(1, username);
            insertClient.setString(2, password);
            insertClient.setString(3, username);
            System.out.println("Row updated: " + insertClient.executeUpdate());

            connection.close();
        }
    }
}

