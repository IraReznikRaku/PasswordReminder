package databaseService;

import data.Record;
import util.FileSystemService;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DatabaseService {

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL    = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN  = "postgres";
    private static final String PASS   = "postgres";
    private static final String CREATE = "CREATE TABLE IF NOT EXISTS passwordReminder (address VARCHAR (20) PRIMARY KEY, login VARCHAR (20), password VARCHAR (20), date VARCHAR (50))";
    private static final String INSERT = "INSERT INTO passwordReminder VALUES (?, ?, ?, ?)";
    private static final String SELECT_FOR_CHOOSE = "SELECT * FROM passwordReminder WHERE address LIKE ?";
    private static final String SELECT_FOR_SHOW = "SELECT * FROM passwordReminder";

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(CREATE);
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASS);
    }

    private static PreparedStatement prepareStatement(Connection c, String sql) throws SQLException {
        return c.prepareStatement(sql);
    }

    public static void addRecord(Record r) {
        FileSystemService.createKey();
        FileSystemService.writeKeyToFile();
        try (Connection c = getConnection(); PreparedStatement ps = prepareStatement(c, INSERT)){
            ps.setString(1, r.getAddress());
            ps.setString(2, r.getLogin());
            try {
                ps.setString(3, FileSystemService.encryptionPass(r.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ps.setString(4, r.getData().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void chooseRecordFromTableByAddress(String st) {
        try (Connection c = getConnection(); PreparedStatement ps = prepareStatement(c, SELECT_FOR_CHOOSE)){
            ps.setString(1, st);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("Address: %s, login: %s, pass: %s, data: %s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                System.out.println();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showRecordFromTable() {
        try (Connection c = getConnection(); PreparedStatement ps = prepareStatement(c, SELECT_FOR_SHOW)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("Address: %s, login: %s, pass: %s, data: %s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
                System.out.println();
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

