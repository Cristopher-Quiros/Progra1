/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetos;

import java.util.ArrayList;

/**
 *
 * @author Cris
 */
public class objUsuarios {
    
    private String cedula;
    private String nombre;
    private String edad;
    private String contrasena;
    private String correo;
    private String tipo;
    
    public static ArrayList<objUsuarios> listaUsuarios = new ArrayList<>();

    
    public objUsuarios (String cedula, String nombre, String edad, String contrasena, String correo, String tipo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.contrasena = contrasena;
        this.correo = correo;
        this.tipo = tipo;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }


    
    
}
