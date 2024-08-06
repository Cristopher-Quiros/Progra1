/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Cris
 */
public class BDAviones {

    private final Object fileLock = new Object();
    private final String archivoTxt = "Tripulaciones.txt";
    private final String archivoCopia = "Tripulacion_temp.txt";
    private final String archivoTxtAvion = "Aviones.txt";
    private final String archivoCopiaAvion = "Aviones_temp.txt";

// --------------------------------------------------------------------------------------------------------------
    // Aviones, Aerolineas
    public ArrayList<String> LeerAvion() {
        ArrayList<String> lista = new ArrayList<>();
        try {
            File archivo = new File("Aviones.txt");
            BufferedReader archi = new BufferedReader(new FileReader(archivo));
            while (archi.ready()) {
                lista.add(archi.readLine());
            }
            archi.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer del archivo",
                    "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
        return lista;
    }

    public ArrayList<String> LeerAeropuertos() {
        ArrayList<String> aeropuertos = new ArrayList<>();
        try {
            File archivo = new File("Aeropuertos.txt");
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                // System.out.println("Línea leída: " + data); // Verificar qué líneas se están leyendo
                aeropuertos.add(data);
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        return aeropuertos;
    }

    public ArrayList<String> LeerAerolineas() {
        ArrayList<String> aerolineas = new ArrayList<>();
        try {
            File archivo = new File("Aerolineas.txt");
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                // System.out.println("Línea leída: " + data); // Verificar qué líneas se están leyendo
                aerolineas.add(data);
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        return aerolineas;
    }

    public ArrayList<String> LeerVuelos() {
        ArrayList<String> vuelos = new ArrayList<>();
        try {
            File archivo = new File("vuelos.txt");
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                // System.out.println("Línea leída: " + data); // Verificar qué líneas se están leyendo
                vuelos.add(data);
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        return vuelos;
    }

// --------------------------------------------------------------------------------------------------------------
    // Personas
    public ArrayList<String> LeerTripulacion() {
        ArrayList<String> tripulacion = new ArrayList<>();
        synchronized (fileLock) {
            try {
                File archivo = new File(archivoTxt);
                try (Scanner lector = new Scanner(archivo)) {
                    while (lector.hasNextLine()) {
                        String data = lector.nextLine();
                        tripulacion.add(data);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al leer el archivo");
                e.printStackTrace();
            }
        }
        return tripulacion;
    }

    public ArrayList<String> LeerUsuarios() {
        ArrayList<String> usuarios = new ArrayList<>();
        try {
            File archivo = new File("usuarios.txt");
            BufferedReader archi = new BufferedReader(new FileReader(archivo));
            while (archi.ready()) {
                usuarios.add(archi.readLine());
            }
            archi.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al leer del archivo",
                    "Mensaje de error", JOptionPane.ERROR_MESSAGE);
        }
        return usuarios;
    }

// --------------------------------------------------------------------------------------------------------------
    // Modificaciones
    public void ModificarDisponibilidad(String pilotoSeleccionado) {
        synchronized (fileLock) {
            Path pathOriginal = Paths.get(archivoTxt);
            Path pathTemporal = Paths.get(archivoCopia);

            try (BufferedReader reader = new BufferedReader(new FileReader(pathOriginal.toFile())); BufferedWriter writer = new BufferedWriter(new FileWriter(pathTemporal.toFile()))) {

                String linea;
                boolean modificacionRealizada = false;

                while ((linea = reader.readLine()) != null) {
                    String[] columnas = linea.split(",");

                    if (columnas.length > 1 && columnas[0].equals(pilotoSeleccionado)) {
                        // Si el piloto está disponible (indicado por "0"), cámbialo a no disponible ("1")
                        if (columnas[columnas.length - 1].trim().equals("0")) {
                            columnas[columnas.length - 1] = "1";
                            modificacionRealizada = true;
                        }
                    }

                    writer.write(String.join(",", columnas));
                    writer.newLine();
                }

                if (!modificacionRealizada) {
                    System.out.println("No se encontró el piloto o la disponibilidad ya está modificada.");
                }

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Files.move(pathTemporal, pathOriginal, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ModificarDisponibilidadServicioAlCliente(String AzafataSeleccionada) {
        synchronized (fileLock) {
            Path pathOriginal = Paths.get(archivoTxt);
            Path pathTemporal = Paths.get(archivoCopia);

            try (BufferedReader reader = new BufferedReader(new FileReader(pathOriginal.toFile())); BufferedWriter writer = new BufferedWriter(new FileWriter(pathTemporal.toFile()))) {

                String linea;
                boolean modificacionRealizada = false;

                while ((linea = reader.readLine()) != null) {
                    String[] columnas = linea.split(",");

                    if (columnas.length > 1 && columnas[0].equals(AzafataSeleccionada)) {
                        // Si el piloto está disponible (indicado por "0"), cámbialo a no disponible ("1")
                        if (columnas[columnas.length - 1].trim().equals("0")) {
                            columnas[columnas.length - 1] = "1";
                            modificacionRealizada = true;
                        }
                    }

                    writer.write(String.join(",", columnas));
                    writer.newLine();
                }

                if (!modificacionRealizada) {
                    System.out.println("No se encontró el personal de servicio al cliente o la disponibilidad ya está modificada.");
                }

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Files.move(pathTemporal, pathOriginal, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ModificarDisponibilidadAvion(String AvionSeleccionado) {
        synchronized (fileLock) {
            Path pathOriginal = Paths.get(archivoTxtAvion);
            Path pathTemporal = Paths.get(archivoCopiaAvion);

            try (BufferedReader reader = new BufferedReader(new FileReader(pathOriginal.toFile())); BufferedWriter writer = new BufferedWriter(new FileWriter(pathTemporal.toFile()))) {

                String linea;
                boolean modificacionRealizada = false;

                while ((linea = reader.readLine()) != null) {
                    String[] columnas = linea.split(",");

                    if (columnas.length > 1 && columnas[0].equals(AvionSeleccionado)) {
                        // Si el piloto está disponible (indicado por "0"), cámbialo a no disponible ("1")
                        if (columnas[columnas.length - 1].trim().equals("0")) {
                            columnas[columnas.length - 1] = "1";
                            modificacionRealizada = true;
                        }
                    }

                    writer.write(String.join(",", columnas));
                    writer.newLine();
                }

                if (!modificacionRealizada) {
                    System.out.println("No se encontró el personal de servicio al cliente o la disponibilidad ya está modificada.");
                }

                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    Files.move(pathTemporal, pathOriginal, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

// --------------------------------------------------------------------------------------------------------------
// Guardar
    public void GuardarEnArchivo(String Usuarios) {
        File archivo = new File("usuarios.txt");
        System.out.println("Ruta absoluta del archivo: " + archivo.getAbsolutePath());

        try (BufferedWriter archi = new BufferedWriter(new FileWriter(archivo, true))) {
            archi.write(Usuarios + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GuardarVuelos(String Vuelos) {
        File archivo = new File("vuelos.txt");
        System.out.println("Ruta absoluta del archivo: " + archivo.getAbsolutePath());

        try (BufferedWriter archi = new BufferedWriter(new FileWriter(archivo, true))) {
            archi.write(Vuelos + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
