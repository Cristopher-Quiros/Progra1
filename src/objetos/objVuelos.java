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
    private Integer IDAerolinea;
    private Integer precio;
    private Date fechasalida;
    private Date horasalida;
    private Integer IDAeropuerto;
    private Integer duracion;
    private Integer IDAvion;
    private Integer IDAzafata;
    private Integer IDPiloto;
    
    public static HashMap<Integer, objVuelos> vuelos = new HashMap<>();
    
    public objVuelos(Integer Identificador, Integer IDAerolinea, Integer precio, Date fechasalida, Date horasalida, Integer IDAeropuerto, Integer duracion, Integer IDAvion, Integer IDAzafata, Integer IDPiloto) {
        this.Identificador = Identificador;
        this.IDAerolinea = IDAerolinea;
        this.precio = precio;
        this.fechasalida = fechasalida;
        this.horasalida = horasalida;
        this.IDAeropuerto = IDAeropuerto;
        this.duracion = duracion;
        this.IDAvion = IDAvion;
        this.IDAzafata = IDAzafata;
        this.IDPiloto = IDPiloto;

       
}

    public Integer getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(Integer Identificador) {
        this.Identificador = Identificador;
    }

    public Integer getIDAerolinea() {
        return IDAerolinea;
    }

    public void setIDAerolinea(Integer IDAerolinea) {
        this.IDAerolinea = IDAerolinea;
    }

    public Integer getPrecio() {
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

    public Integer getIDAeropuerto() {
        return IDAeropuerto;
    }

    public void setIDAeropuerto(Integer IDAeropuerto) {
        this.IDAeropuerto = IDAeropuerto;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public Integer getIDAvion() {
        return IDAvion;
    }

    public void setIDAvion(Integer IDAvion) {
        this.IDAvion = IDAvion;
    }

    public Integer getIDAzafata() {
        return IDAzafata;
    }

    public void setIDAzafata(Integer IDAzafata) {
        this.IDAzafata = IDAzafata;
    }

    public Integer getIDPiloto() {
        return IDPiloto;
    }

    public void setIDPiloto(Integer IDPiloto) {
        this.IDPiloto = IDPiloto;
    }
}
