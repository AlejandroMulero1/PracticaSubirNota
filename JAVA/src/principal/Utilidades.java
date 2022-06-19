package principal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.*;
import java.util.List;
import java.io.File;

public class Utilidades {
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



    public static void insertarEnTablaFinal(Connection conexionBD){

        leerTablaFinal(conexionBD);
    }

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