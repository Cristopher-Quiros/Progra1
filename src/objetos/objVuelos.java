/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Cris
 */
public class objVuelos {
    
    private Integer Identificador;
    private String IDAerolinea;
    private double precio;
    private Date fechasalida;
    private Date horasalida;
    private String IDAeropuertoSalida;
    private Date fechallegada;
    private Date horallegada;
    private String IDAeropuertoLlegada;
    private Integer duracion;
    private String IDAvion;
    private String IDAzafata;
    private String IDPiloto;
    
    public static ArrayList<objVuelos> vuelos = new ArrayList<>();
    
    public objVuelos(Integer Identificador, String IDAerolinea, double precio, Date fechasalida, Date horasalida, String IDAeropuertoSalida, Date fechallegada, Date horallegada, 
            String IDAeropuertoLlegada, Integer duracion, String IDAvion, String IDAzafata, String IDPiloto) {
       this.Identificador = Identificador;
       this.IDAerolinea = IDAerolinea;
       this.precio = precio;
       this.fechasalida = fechasalida;
       this.horasalida = horasalida;
       this.IDAeropuertoSalida = IDAeropuertoSalida;
       this.fechallegada = fechallegada;
       this.horallegada = horallegada;
       this.IDAeropuertoLlegada = IDAeropuertoLlegada;
       this.duracion = duracion;
       this.IDAvion = IDAvion;
       this.IDAzafata = IDAzafata;
       this.IDPiloto = IDPiloto;
}

    public String getIDAeropuertoSalida() {
        return IDAeropuertoSalida;
    }

    public void setIDAeropuertoSalida(String IDAeropuertoSalida) {
        this.IDAeropuertoSalida = IDAeropuertoSalida;
    }

    public Date getFechallegada() {
        return fechallegada;
    }

    public void setFechallegada(Date fechallegada) {
        this.fechallegada = fechallegada;
    }

    public Date getHorallegada() {
        return horallegada;
    }

    public void setHorallegada(Date horallegada) {
        this.horallegada = horallegada;
    }

    public String getIDAeropuertoLlegada() {
        return IDAeropuertoLlegada;
    }

    public void setIDAeropuertoLlegada(String IDAeropuertoLlegada) {
        this.IDAeropuertoLlegada = IDAeropuertoLlegada;
    }    

    public Integer getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(Integer Identificador) {
        this.Identificador = Identificador;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Date getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(Date fechasalida) {
        this.fechasalida = fechasalida;
    }

    public Date getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(Date horasalida) {
        this.horasalida = horasalida;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getIDAerolinea() {
        return IDAerolinea;
    }

    public void setIDAerolinea(String IDAerolinea) {
        this.IDAerolinea = IDAerolinea;
    }


    public String getIDAvion() {
        return IDAvion;
    }

    public void setIDAvion(String IDAvion) {
        this.IDAvion = IDAvion;
    }

    public String getIDAzafata() {
        return IDAzafata;
    }

    public void setIDAzafata(String IDAzafata) {
        this.IDAzafata = IDAzafata;
    }

    public String getIDPiloto() {
        return IDPiloto;
    }

    public void setIDPiloto(String IDPiloto) {
        this.IDPiloto = IDPiloto;
    }
    
    

}
