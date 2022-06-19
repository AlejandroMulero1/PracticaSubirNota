package principal;


import java.util.Arrays;

public class Factura {
    private String cif, nombreEmp, codEnvio, fechaEnvio;
    private String[] codFacturas;
    private double importeSinIva, importeConIva, bonificacion;


    public Factura(String cif, String nombreEmp, String codEnvio, String fechaEnvio, String[] codFacturas, double importeSinIva, double importeConIva){
        this.cif=cif;
        this.nombreEmp=nombreEmp;
        this.codEnvio=codEnvio;
        this.fechaEnvio=fechaEnvio;
        this.codFacturas=codFacturas;
        this.importeSinIva=importeSinIva;
        this.importeConIva=importeConIva;
        setBonificacion(importeConIva);

    }

    public void setBonificacion(double importeConIva) {
        double bonificacion=0;
        if (importeConIva<=5000 && importeConIva>0){
            bonificacion=importeConIva*0.05;
        }
        else if(importeConIva<=10000 && importeConIva>5000){
            bonificacion=importeConIva*0.10;
        }
        else if (importeConIva>10000){
            bonificacion=importeConIva*0.15;
        }
        this.bonificacion = bonificacion;
    }

    public String toString(){
        return cif + "," + nombreEmp + "," + codEnvio + "," + fechaEnvio + "," + Arrays.toString(codFacturas) + "," + importeSinIva + "," + importeConIva + "," + bonificacion + "\n";
    }

    public String getNombreEmp() {
        return nombreEmp;
    }

    public String getCif() {
        return cif;
    }

    public String getCodEnvio() {
        return codEnvio;
    }

    public String getFechaEnvio() {
        return fechaEnvio;
    }

    public String[] getCodFacturas() {
        return codFacturas;
    }

    public double getImporteSinIva() {
        return importeSinIva;
    }

    public double getImporteConIva() {
        return importeConIva;
    }

    public double getBonificacion() {
        return bonificacion;
    }
}