/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author Cris
 */
public class objHistorial {

    private int idHistorial, cedulaCliente, IDAeropuertoSalida, IDAeropuertoLlegada, cantidadBoletos, duracionVuelo, costoTotal;
    private Date fechaCompra, horaCompra;
    private String codigoAsientos;

    public static ArrayList<objHistorial> historial = new ArrayList<>();

    public objHistorial(int idHistorial, int cedulaCliente, int IDAeropuertoSalida, int IDAeropuertoLlegada, Date fechaCompra, Date horaCompra, int cantidadBoletos, String codigoAsientos, int duracionVuelo, int costoTotal) {
        this.idHistorial = idHistorial;
        this.cedulaCliente = cedulaCliente;
        this.IDAeropuertoSalida = IDAeropuertoSalida;
        this.IDAeropuertoLlegada = IDAeropuertoLlegada;
        this.fechaCompra = fechaCompra;
        this.horaCompra = horaCompra;
        this.cantidadBoletos = cantidadBoletos;
        this.codigoAsientos = codigoAsientos;
        this.duracionVuelo = duracionVuelo;
        this.costoTotal = costoTotal;

    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(int cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public int getIDAeropuertoSalida() {
        return IDAeropuertoSalida;
    }

    public void setIDAeropuertoSalida(int IDAeropuertoSalida) {
        this.IDAeropuertoSalida = IDAeropuertoSalida;
    }

    public int getIDAeropuertoLlegada() {
        return IDAeropuertoLlegada;
    }

    public void setIDAeropuertoLlegada(int IDAeropuertoLlegada) {
        this.IDAeropuertoLlegada = IDAeropuertoLlegada;
    }

    public int getCantidadBoletos() {
        return cantidadBoletos;
    }

    public void setCantidadBoletos(int cantidadBoletos) {
        this.cantidadBoletos = cantidadBoletos;
    }

    public int getDuracionVuelo() {
        return duracionVuelo;
    }

    public void setDuracionVuelo(int duracionVuelo) {
        this.duracionVuelo = duracionVuelo;
    }

    public int getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(int costoTotal) {
        this.costoTotal = costoTotal;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getHoraCompra() {
        return horaCompra;
    }

    public void setHoraCompra(Date horaCompra) {
        this.horaCompra = horaCompra;
    }

    public String getCodigoAsientos() {
        return codigoAsientos;
    }

    public void setCodigoAsientos(String codigoAsientos) {
        this.codigoAsientos = codigoAsientos;
    }
    
}
