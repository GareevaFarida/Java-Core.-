package lesson6;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.regex.Pattern;

public final class Constants {
    //время ожидания авторизации
    public static final int AUTORIZATION_TIMEOUT = 120_000;

    //константы класса ChatServer
    public static final Pattern AUTH_PATTERN_FOR_READ = Pattern.compile("^/auth (.+) (.+)$");
    public static final String MESSAGE_PATTERN = "/w %s %s";
    public static final String USER_ONLINE_PATTERN = "/online %s";
    public static final String USER_OFFLINE_PATTERN = "/offline %s";
    public static final String USERLIST_PATTERN = "/userlist %s";
    public static final Pattern REGISTRATION_PATTERN_FOR_READ = Pattern.compile("^/reg (.+) (.+)$");
    public static final Pattern NICKNAME_PATTERN_FOR_READ = Pattern.compile("^/nick (.+) (.+)$");

    //константы класса ClientHandler
    public static final Pattern MESSAGE_PATTERN_FOR_READ = Pattern.compile("^/w (.+) (.+)$");

    //константы класса Network
    public static final String AUTH_PATTERN = "/auth %s %s";
    public static final Pattern USERLIST_PATTERN_FOR_READ = Pattern.compile("^/userlist (.+)$");
    public static final Pattern USER_ONLINE_PATTERN_FOR_READ = Pattern.compile("^/online (.+)$");
    public static final Pattern USER_OFFLINE_PATTERN_FOR_READ = Pattern.compile("^/offline (.+)$");
    public static final String REGISTRATION_PATTERN = "/reg %s %s";
    public static final String NICKNAME_PATTERN = "/nick %s %s";

    //константы класса MainWindow
    public static final int COUNT_ROWS = 100;
    public static final String INNER_SEPARATOR = ": ";
}
