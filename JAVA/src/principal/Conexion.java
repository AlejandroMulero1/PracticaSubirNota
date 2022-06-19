package principal;

import java.sql.*;

public class Conexion {
    public static Connection conectarBD(String url, String usuario, String contraseña){
        Connection conexionBD = null;
        try {
            conexionBD = DriverManager.getConnection(url, usuario, contraseña);
        } catch (SQLException e) {
            System.out.println("Error " + e);
        }
        return conexionBD;
    }
}