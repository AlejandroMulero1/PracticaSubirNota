package principal;

import java.io.FileReader;
import java.util.Properties;
import java.util.List;

import static principal.Conexion.conectarBD;


public class Main {

    public static void main(String[] args){
        Properties p = new Properties();
        String jdbc="";
        String usuario="";
        String clave="";

        try {
            p.load(new FileReader("PracticaSubirNota/JAVA/src/datos.properties"));
            jdbc=p.getProperty("jdbc");
            usuario=p.getProperty("USUARIO");
            clave=p.getProperty("CLAVE");
        }
        catch (Exception e){
            System.out.println(e);
        }

        List<Factura> listaFactura;
        listaFactura=Utilidades.generarLista(conectarBD(jdbc, usuario, clave));
        Utilidades.insertarLista(conectarBD(jdbc, usuario, clave), listaFactura);
        Utilidades.insertarEnTablaFinal(conectarBD(jdbc, usuario, clave));
    }
}
