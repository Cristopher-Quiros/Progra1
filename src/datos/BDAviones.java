/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Cris
 */
public class BDAviones {

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

    public static ArrayList<String> LeerTripulacion() {
        ArrayList<String> tripulacion = new ArrayList<>();
        try {
            File archivo = new File("Games.csv");
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                // System.out.println("Línea leída: " + data); // Verificar qué líneas se están leyendo
                tripulacion.add(data);
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        return tripulacion;
    }

    public static ArrayList<String> LeerAeropuertos() {
        ArrayList<String> juegosArchivo = new ArrayList<>();
        try {
            File archivo = new File("Games.csv");
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                // System.out.println("Línea leída: " + data); // Verificar qué líneas se están leyendo
                juegosArchivo.add(data);
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        return juegosArchivo;
    }

    public static ArrayList<String> LeerAerolineas() {
        ArrayList<String> juegosArchivo = new ArrayList<>();
        try {
            File archivo = new File("Games.csv");
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String data = lector.nextLine();
                // System.out.println("Línea leída: " + data); // Verificar qué líneas se están leyendo
                juegosArchivo.add(data);
            }
            lector.close();
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            e.printStackTrace();
        }
        return juegosArchivo;
    }

    public void GuardarEnArchivo(String Usuarios) {
        File archivo = new File("usuarios.txt");
        System.out.println("Ruta absoluta del archivo: " + archivo.getAbsolutePath());

        try (BufferedWriter archi = new BufferedWriter(new FileWriter(archivo, true))) {
            archi.write(Usuarios + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
