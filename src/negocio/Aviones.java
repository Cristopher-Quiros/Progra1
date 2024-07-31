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
            String tipo = listaUsuarios.get(i).getTipo();
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
    public ArrayList<String> filtrarPilotos(String aerolineaID) {
        ArrayList<String> tripulacion = bdaviones.LeerTripulacion(); 
        ArrayList<String> pilotos = new ArrayList<>();


        for (String tripulante : tripulacion) {
            String limpiaTripulante = tripulante.trim();      
            String[] partes = limpiaTripulante.split("\\s*,\\s*"); // Divide la línea en partes usando coma como delimitador
  
            if (partes.length == 5) {
                String rol = partes[3].trim();
                String idAerolineaTripulante = partes[2].trim();
                if (rol.equalsIgnoreCase("Piloto") && idAerolineaTripulante.equals(aerolineaID)) {
                    pilotos.add(limpiaTripulante);
                }
            } else {
                System.out.println("Formato inesperado para tripulante: " + limpiaTripulante);
            }
        }
        return pilotos;
    }

    public ArrayList<String> filtrarServicioAlCliente(String aerolineaID) {
        ArrayList<String> tripulacion = bdaviones.LeerTripulacion(); // Aquí obtenemos la lista de tripulantes
        ArrayList<String> servicioAlCliente = new ArrayList<>();

        for (String tripulante : tripulacion) {
            String limpiaTripulante = tripulante.trim();
            String[] partes = limpiaTripulante.split("\\s*,\\s*");

            // Verifica la longitud de las partes y muestra información para depuración
            if (partes.length == 5) {
                String rol = partes[3].trim();
                String idAerolineaTripulante = partes[2].trim();

                if (rol.equalsIgnoreCase("Servicio al Cliente") && idAerolineaTripulante.equals(aerolineaID)) {
                    servicioAlCliente.add(limpiaTripulante);
                }
            } else {
                System.out.println("Formato inesperado para tripulante: " + limpiaTripulante);
            }
        }
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

    public void ModificarDisponibilidadServicioAlCLiente(String AzafataSeleccionada) {
        try {
            bdaviones.ModificarDisponibilidad(AzafataSeleccionada);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores adecuado
        }
    }
}
