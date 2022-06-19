package principal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.util.List;
import java.io.File;

public class Utilidades {
    /**
     * Metodo que a través de la conexion a la base de datos "PracticaHospital" obtiene los resultados de la tabla temporal "DevolverTodo" para
     * almacenarlo en una lista que posteriormente devolvera
     * @param conexionBD
     * @return : Lista que devuelve el metodo con objetos de la clase factura provenientes de la base de datos
     */
    public static List<Factura> generarLista(Connection conexionBD){
        List<Factura> listaFactura= null;
        String facturas="";
        try {
            String SQL = "SELECT * FROM DevolverTodo()";
            Statement stmt = conexionBD.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                facturas= rs.getString("CodsFacturas");
                listaFactura.add(new Factura(rs.getString("CIF"),
                        rs.getString("NombreEmp"),
                        rs.getString("CodEnvios"),
                        rs.getString("fechaEnvios"),
                        facturas.split(","),
                        rs.getDouble("importeSinIva"),
                        rs.getDouble("importeConIva")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return listaFactura;
    }

    /**
     * Metodo que inserta la lista obtenida del metodo generarLista en diversos ficheros en funcion al nombre de la empresa
     * mediante la llamada al metodo escribirFicheros pasandole un file creado a partir del nombre del resultSet, el valor del
     * ResultSet y la lista
     * @param conexionBD
     * @param facturaList
     */
    public static void  insertarLista(Connection conexionBD, List<Factura> facturaList){
        List<String> listaEmpresas=null;
        try {
            String SQL = "SELECT DISTINCT NombreEmp FROM DevolverTodo";
            Statement stmt = conexionBD.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                File fichero=new File(rs.getString("NombreEmp")+".txt");
                escribirFicheros(fichero, rs.getString("NombreEmp"), facturaList);
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }

    /**
     * Metodo que inserta la lista obtenida del metodo generarLista en diversos ficheros en funcion al nombre de la empresa
     * @param fichero
     * @param nombreEmp
     * @param listaFacturas
     */
    private static void escribirFicheros(File fichero, String nombreEmp, List<Factura> listaFacturas){
        try {
            BufferedWriter bw= new BufferedWriter(new FileWriter(fichero));
            for (int i = 0; i <listaFacturas.size() ; i++) {
                if (nombreEmp.equals(listaFacturas.get(i).getNombreEmp())){
                    bw.write(listaFacturas.get(i).toString());
                }
            }

        }catch (Exception e){
            System.out.println(e);
        }
    }


    /**
     * Metodo que inserta la lista obtenida del metodo generarLista en una tabla de la base de datos a través de un for que recorre la lista, si el registro está duplicado
     * el catch se ocupa de avanzar una posicion y imprime el resultado de esa tabla llamando al metodo leerTablaFinal
     * @param conexionBD
     * @param facturaList
     */
    public static void insertarEnTablaFinal(Connection conexionBD, List<Factura> facturaList){
        int j=0;
        while(j<facturaList.size()){
            try {
                for ( int i=j; j < facturaList.size(); j++) {
                    PreparedStatement stmt = conexionBD.prepareStatement("INSERT INTO FacturaFinal VALUES (?,?,?,?,?,?,?)");
                    stmt.setString(1,facturaList.get(j).getCif());
                    stmt.setString(2,facturaList.get(j).getCodEnvio());
                    stmt.setString(3,facturaList.get(j).getFechaEnvio());
                    stmt.setString(4,facturaList.get(j).getCodFacturas());
                    stmt.setDouble(5,facturaList.get(j).getImporteSinIva());
                    stmt.setDouble(6,facturaList.get(j).getImporteConIva());
                    stmt.setDouble(7,facturaList.get(j).getBonificacion());
                }
            }
            catch (Exception e){
                j++;
            }
        }
        leerTablaFinal(conexionBD);
    }

    /**
     * Metodo que imprime los datos de la tabla FacturaFinal
     * @param conexionBD
     */
    private static void leerTablaFinal(Connection conexionBD){
        String facturas="";
        try {
            String SQL = "SELECT * FROM FacturaFinal";
            Statement stmt = conexionBD.createStatement();
            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {
                facturas= rs.getString("CodsFacturas");
                System.out.println(rs.getString("CIF")+ ", " +
                        rs.getString("NombreEmp")+ ", " +
                        rs.getString("CodEnvios")+ ", " +
                        rs.getString("fechaEnvios")+ ", " +
                        facturas.split(",")+ ", " +
                        rs.getDouble("importeSinIva")+ ", " +
                        rs.getDouble("importeConIva"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}