package principal;

import java.sql.*;

public class Conexion {
    /**
     * Metodo que obtiene la conexion a la base de datos a través de una url, usuario y contraseña
     * @param url
     * @param usuario
     * @param contraseña
     * @return : Retorna un objeto de la clase Connection el cual almacena la conexión a la base de datos deseada
     */
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