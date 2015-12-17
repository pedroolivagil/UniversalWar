package cat.olivadevelop.universalwar.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static cat.olivadevelop.universalwar.tools.GameLogic.getServerData;

/**
 * Created by Oliva on 30/08/2015.
 */
public class ConnectDB {
    private Connection conn;
    private String url;
    private String user;
    private String pass;
    private ResultSet result;
    private Statement estado;

    public ConnectDB() {
        this.user = getServerData(2);
        this.pass = getServerData(3);
        this.url = "jdbc:mysql://" + getServerData(0) + "/" + getServerData(1);
    }

    public ResultSet query(String query) {
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
            //Gdx.app.log("Server", "Connected");
            this.estado = this.conn.createStatement();
            this.result = this.estado.executeQuery(query);
            //conn.close();
        } catch (SQLException e) {
            //Gdx.app.log("Server", "Error MYSQL");
            e.printStackTrace();
        } catch (Exception e) {
            //Gdx.app.log("Server", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return this.result;*/
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(this.url, this.user, this.pass);
            System.out.println("Connected");
            this.estado = this.conn.createStatement();
            this.result = estado.executeQuery(query);
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error MYSQL");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public void insert(String query) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection(url, user, pass);
            //Gdx.app.log("Server", "Connected");
            this.estado = this.conn.createStatement();
            this.estado.executeUpdate(query);
            //conn.close();
        } catch (SQLException e) {
            //Gdx.app.log("Server", "Error MYSQL");
            e.printStackTrace();
        } catch (Exception e) {
            //Gdx.app.log("Server", "Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        if (result != null) {
            result.close();
        }
        estado.close();
        conn.close();
    }
}