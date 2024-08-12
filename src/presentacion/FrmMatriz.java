/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import presentacion.FrmCliente;
import negocio.ClaseNegocio;

/**
 *
 * @author Cris
 */
public class FrmMatriz extends javax.swing.JFrame {

    /**
     * Creates new form FrmMatriz
     */
    FrmCliente cliente = new FrmCliente();
    ClaseNegocio negocio = new ClaseNegocio();
    private String[][] asientos; // Para almacenar el estado actual de los asientos
    private int numPersonas;

    public FrmMatriz() {
        initComponents();
        actualizarMatriz();
    }

    public int IdHistorial() {
        Random rand = new Random();

        int max = 99999;
        int min = 10000;

        int random = rand.nextInt(min, max);
        return random;
    }

    public void setCantidadPersonas(int numPersonas) {
        this.numPersonas = numPersonas;
        System.out.println("Numero de Personas: " + numPersonas);
        mostrarMatriz();
    }

    public void actualizarMatriz() {
        mostrarMatriz();
    }

// Presentación
    private void mostrarMatriz() {
        // Crear la matriz de asientos
        String[][] asientos = {
            {"A1", "B1", "C1", "---", "D1", "E1", "F1"},
            {"A2", "B2", "C2", "---", "D2", "E2", "F2"},
            {"A3", "B3", "C3", "---", "D3", "E3", "F3"},
            {"A4", "B4", "C4", "---", "D4", "E4", "F4"},
            {"A5", "B5", "C5", "---", "D5", "E5", "F5"},
            {"A6", "B6", "C6", "---", "D6", "E6", "F6"}
        };

        MatrizPanel.setLayout(new GridLayout(asientos.length, asientos[0].length));

        // Crear los botones para los asientos
        int contador = 0;
        MatrizPanel.removeAll();  // Limpiar el panel antes de añadir nuevos botones
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                JButton boton = new JButton(asientos[i][j]);
                if (asientos[i][j].equals("---")) {
                    boton.setEnabled(false);
                    boton.setForeground(Color.GRAY);
                } else {
                    if (this.numPersonas >= 4) { // Si son 4 o más personas, colorear en un mismo lado
                        if (j < asientos[i].length / 2) { // Asientos a la izquierda del pasillo
                            if (contador < this.numPersonas) {
                                boton.setBackground(Color.RED);
                                contador++;
                            } else {
                                boton.setBackground(Color.WHITE);
                            }
                        } else { // Asientos a la derecha del pasillo, no colorear
                            boton.setBackground(Color.WHITE);
                        }
                    } else { // Si son menos de 4 personas, colorear en ambos lados
                        if (contador < this.numPersonas) {
                            boton.setBackground(Color.RED);
                            contador++;
                        } else {
                            boton.setBackground(Color.WHITE);
                        }
                    }
                }
                boton.setPreferredSize(new Dimension(50, 30));
                boton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                boton.setForeground(Color.DARK_GRAY);
                boton.setFont(new Font("Arial", Font.BOLD, 14));

                MatrizPanel.add(boton);
            }
        }

        // Refrescar el panel para que se muestren los cambios
        MatrizPanel.setPreferredSize(new Dimension(500, 400));
        revalidate();  // Actualizar el diseño
        repaint();  // Volver a dibujar el panel
        System.out.println("contador: " + contador + ", numPersonas: " + numPersonas);

        // Guardar solo los asientos rojos
        guardarAsientosRojos();
    }

    private void guardarAsientosRojos() {
        // Crear una matriz de asientos con el estado actual
        String[][] asientos = new String[6][7];
        Component[] components = MatrizPanel.getComponents();

        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                JButton boton = (JButton) components[index++];
                if (boton.getBackground().equals(Color.RED)) {
                    asientos[i][j] = boton.getText(); // Guardar solo el texto si el fondo es rojo
                } else {
                    asientos[i][j] = "Disponible"; // Marcar los demás como "Disponible" o el estado por defecto
                }
            }
        }

        // Guardar la matriz de asientos en el archivo
        negocio.guardarEstadoAsientos(asientos);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MatrizPanel = new javax.swing.JPanel();
        btnComprar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        MatrizPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Asientos"));

        javax.swing.GroupLayout MatrizPanelLayout = new javax.swing.GroupLayout(MatrizPanel);
        MatrizPanel.setLayout(MatrizPanelLayout);
        MatrizPanelLayout.setHorizontalGroup(
            MatrizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 522, Short.MAX_VALUE)
        );
        MatrizPanelLayout.setVerticalGroup(
            MatrizPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        btnComprar.setBackground(new java.awt.Color(0, 0, 0));
        btnComprar.setForeground(new java.awt.Color(255, 255, 255));
        btnComprar.setText("Comprar Vuelo");
        btnComprar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MatrizPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(159, 159, 159)
                .addComponent(btnComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MatrizPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnComprar, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmMatriz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmMatriz().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel MatrizPanel;
    private javax.swing.JButton btnComprar;
    // End of variables declaration//GEN-END:variables
}
