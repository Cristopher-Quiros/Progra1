/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.BDAviones;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import objetos.*;
import presentacion.FrmAdmin;

/**
 *
 * @author Cris
 */
public class ClaseNegocio {

    BDAviones bdaviones = new BDAviones();

    public ArrayList<String> LeerUsuarios() {
        ArrayList<String> usuarios = bdaviones.LeerUsuarios();
        return usuarios;
    }

    public ArrayList<String> LeerAviones(String aerolineaID) {
        ArrayList<String> aviones = bdaviones.LeerAvion();
        ArrayList<String> avionesFiltrados = new ArrayList<>();

        for (String avion : aviones) {
            String[] columnas = avion.split(",");
            if (columnas.length > 1 && columnas[2].trim().equals(aerolineaID)) { // Suponiendo que el ID de la aerolínea está en la columna 1
                avionesFiltrados.add(avion);
            }
        }

        return avionesFiltrados;
    }

    public ArrayList<String> LeerAerolineas() {
        ArrayList<String> aerolineas = bdaviones.LeerAerolineas();
        return aerolineas;
    }

    public Map<String, String> LeerAerolineasDesdeBD() {
        List<String> aerolineas = bdaviones.LeerAerolineas();
        Map<String, String> mapaAerolineas = new HashMap<>();

        for (String aerolinea : aerolineas) {
            String[] partes = aerolinea.split(",");
            if (partes.length == 2) {
                mapaAerolineas.put(partes[0].trim(), partes[1].trim());
            } else {
                System.out.println("Formato de datos de aerolínea inválido: " + aerolinea);
            }
        }

        return mapaAerolineas;
    }

    public ArrayList<String> LeerAeropuertos() {
        ArrayList<String> aeropuertos = bdaviones.LeerAeropuertos();
        return aeropuertos;
    }

    public ArrayList<String> LeerTripulacion() {
        ArrayList<String> tripulacion = bdaviones.LeerTripulacion();
        return tripulacion;
    }

    public ArrayList<String> LeerVuelos() {
        ArrayList<String> vuelos = bdaviones.LeerVuelos();
        return vuelos;
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
    public void InsertarVuelo(ArrayList<objVuelos> listaVuelos) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", Locale.forLanguageTag("es-ES"));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < listaVuelos.size(); i++) {
            int identificador = listaVuelos.get(i).getIdentificador();
            String IDAerolinea = listaVuelos.get(i).getIDAerolinea();
            int precio = (int) listaVuelos.get(i).getPrecio();
            Date fechasalida = listaVuelos.get(i).getFechasalida();
            Date horasalida = listaVuelos.get(i).getHorasalida();
            String IDAeropuertoSalida = listaVuelos.get(i).getIDAeropuertoSalida();
            Date fechallegada = listaVuelos.get(i).getFechallegada();
            Date horallegada = listaVuelos.get(i).getHorallegada();
            String IDAeropuertoLlegada = listaVuelos.get(i).getIDAeropuertoLlegada();
            int duracion = listaVuelos.get(i).getDuracion();
            String IDAvion = listaVuelos.get(i).getIDAvion();
            String IDAzafata = listaVuelos.get(i).getIDAzafata();
            String IDPiloto = listaVuelos.get(i).getIDPiloto();

            int duracionHoras = duracion / 100;
            int duracionMinutos = duracion % 100;

            // Formatear la duración
            String duracionFormateada = String.format("%02d:%02d", duracionHoras, duracionMinutos);

            // Formatear las fechas, horas y duración
            String fechasalidaFormatted = dateFormat.format(fechasalida);
            String horasalidaFormatted = timeFormat.format(horasalida);
            String fechallegadaFormatted = dateFormat.format(fechallegada);
            String horallegadaFormatted = timeFormat.format(horallegada);

            String datos = identificador + "," + IDAerolinea + "," + precio + "," + fechasalidaFormatted + "," + horasalidaFormatted + "," + IDAeropuertoSalida + "," + fechallegadaFormatted + "," + horallegadaFormatted + "," + IDAeropuertoLlegada + ","
                    + duracionFormateada + "," + IDAvion + "," + IDAzafata + "," + IDPiloto;

            bdaviones.GuardarVuelos(datos); // Mover dentro del loop para guardar cada vuelo individualmente
        }
    }

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
    // ---------------------------------------------------------------------------------------------------

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

    public void ModificarDisponibilidadAvion(String AvionSeleccionado) {
        try {
            bdaviones.ModificarDisponibilidadAvion(AvionSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
            // Manejo de errores adecuado
        }
    }

    // Validaciones
    public int contarVuelosPorAerolinea(String aerolineaID) {
        int count = 0;
        ArrayList<String> vuelos = bdaviones.LeerVuelos();
        for (String vueloString : vuelos) {
            String[] partes = vueloString.split(",");
            if (partes.length > 1) { // Asegurarse de que hay suficientes columnas
                String vueloAerolineaID = partes[1].trim(); // Ajustar según la posición real de la ID de la aerolínea
                if (vueloAerolineaID.equals(aerolineaID)) {
                    count++;
                }
            }
        }
        System.out.println("Aerolinea ID: " + aerolineaID + ", Conteo de vuelos: " + count); // Depuración
        return count;
    }

    public boolean puedeCrearVuelo(String aerolineaID) {
        int conteoActual = contarVuelosPorAerolinea(aerolineaID);
        return conteoActual < 2;
    }

    public String[][] cargarEstadoAsientos() {
        return bdaviones.cargarEstadoAsientos();
    }

    public void guardarEstadoAsientos(String[][] asientos) {
        bdaviones.guardarEstadoAsientos(asientos);
    }

    public String[][] asignarAsientosAutomáticamente(int numPersonas) {
        String[][] asientos = cargarEstadoAsientos();

        // Asignar asientos automáticamente
        int contador = 0;
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                if (contador >= numPersonas) {
                    break;
                }
                if (asientos[i][j].equals("Disponible")) {
                    asientos[i][j] = "Ocupado";
                    contador++;
                }
            }
            if (contador >= numPersonas) {
                break;
            }
        }

        guardarEstadoAsientos(asientos);
        return asientos;
    }
}
