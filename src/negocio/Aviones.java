/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.BDAviones;
import java.io.IOException;
import java.util.ArrayList;
import objetos.objUsuarios;

/**
 *
 * @author Cris
 */
public class Aviones {

    BDAviones bdaviones = new BDAviones();

    public ArrayList<String> LeerAviones() {
        ArrayList<String> aviones = bdaviones.LeerAvion();
        return aviones;
    }

    public ArrayList<String> LeerUsuarios() {
        ArrayList<String> usuarios = bdaviones.LeerUsuarios();
        return usuarios;
    }

    public ArrayList<String> LeerAerolineas() {
        ArrayList<String> aerolineas = bdaviones.LeerAerolineas();
        return aerolineas;
    }

    public ArrayList<String> LeerAeropuertos() {
        ArrayList<String> aeropuertos = bdaviones.LeerAeropuertos();
        return aeropuertos;
    }

    public ArrayList<String> LeerTripulacion() {
        ArrayList<String> tripulacion = bdaviones.LeerTripulacion();
        return tripulacion;
    }

    public void InsertarUsuario(ArrayList<objUsuarios> listaUsuarios) {
        String datos = "";
        for (int i = 0; i <= listaUsuarios.size() - 1; i++) {
            String cedula = listaUsuarios.get(i).getCedula();
            String nombre = listaUsuarios.get(i).getNombre();
            String contrasena = listaUsuarios.get(i).getContrasena();
            String correo = listaUsuarios.get(i).getCorreo();
            String edad = listaUsuarios.get(i).getEdad();
            int tipo = listaUsuarios.get(i).getTipo();
            datos = cedula + "," + nombre + "," + contrasena + "," + correo + "," + edad + "," + tipo;
        }

        bdaviones.GuardarEnArchivo(datos);

    }

    // ---------------------------------------------------------------------------------------------------
    /* public void InsertarVuelo(ArrayList<objVuelos> listaVuelos) {
        String datos = "";
        for (int i = 0; i <= listaUsuarios.size() - 1; i++) {
            String cedula = listaUsuarios.get(i).getCedula();
            String nombre = listaUsuarios.get(i).getNombre();
            String contrasena = listaUsuarios.get(i).getContrasena();
            String correo = listaUsuarios.get(i).getCorreo();
            String edad = listaUsuarios.get(i).getEdad();
            int tipo = listaUsuarios.get(i).getTipo();
            datos = cedula + "," + nombre + "," + contrasena + "," + correo + "," + edad + "," + tipo;
        }

        bdaviones.GuardarEnArchivo(datos);
    } */
    // ---------------------------------------------------------------------------------------------------
    public ArrayList<String> filtrarPilotos() {
        ArrayList<String> tripulacion = LeerTripulacion();  // Obtén la lista de tripulación
        ArrayList<String> pilotos = new ArrayList<>();      // Lista para almacenar los pilotos

        for (String tripulante : tripulacion) {
            String[] partes = tripulante.split(",");  // Divide cada cadena de tripulación en partes

            if (partes.length >= 5) {  // Asegúrate de que hay suficientes partes
                String rol = partes[3].trim();  // Obtén el rol del tripulante
                if (rol.equalsIgnoreCase("Piloto")) {  // Verifica si el rol es Piloto
                    pilotos.add(tripulante);  // Agrega el tripulante a la lista de pilotos
                }
            }
        }

        // Devuelve la lista de pilotos filtrados
        return pilotos;
    }

    // Método para filtrar servicio al cliente
    public ArrayList<String> filtrarServicioAlCliente() {
        ArrayList<String> tripulacion = LeerTripulacion();  // Obtén la lista de tripulación
        ArrayList<String> servicioAlCliente = new ArrayList<>();      // Lista para almacenar el servicio al cliente

        for (String tripulante : tripulacion) {
            String[] partes = tripulante.split(",");  // Divide cada cadena de tripulación en partes

            if (partes.length >= 5) {  // Asegúrate de que hay suficientes partes
                String rol = partes[3].trim();  // Obtén el rol del tripulante
                if (rol.equalsIgnoreCase("Servicio al Cliente")) {  // Verifica si el rol es Servicio al Cliente
                    servicioAlCliente.add(tripulante);  // Agrega el tripulante a la lista de servicio al cliente
                }
            }
        }

        // Devuelve la lista de servicio al cliente filtrado
        return servicioAlCliente;
    }

    public void ModificarDisponibilidad(String pilotoSeleccionado) {
        try {
            bdaviones.ModificarDisponibilidad(pilotoSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores adecuado
        }
    }
}
