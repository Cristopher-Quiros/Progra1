/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import datos.BDAviones;
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

    public void InsertarUsuario(ArrayList<objUsuarios> listaUsuarios) {
        String datos = "";
        for (int i = 0; i <= listaUsuarios.size() - 1; i++) {
            String cedula = listaUsuarios.get(i).getCedula();
            String nombre = listaUsuarios.get(i).getNombre();
            String contrasena = listaUsuarios.get(i).getContrasena();
            String correo = listaUsuarios.get(i).getCorreo();
            String edad = listaUsuarios.get(i).getEdad();
            datos = cedula + "," + nombre + "," + contrasena + "," + correo + "," + edad;
        }

        bdaviones.GuardarEnArchivo(datos);
    }
}
