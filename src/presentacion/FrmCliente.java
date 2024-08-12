/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import static java.lang.Integer.parseInt;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import negocio.ClaseNegocio;
import objetos.objVuelos;
import presentacion.FrmMatriz;

/**
 *
 * @author Estudiante
 */
public class FrmCliente extends javax.swing.JFrame {

    ClaseNegocio negocio = new ClaseNegocio();
    private Map<String, String> aeropuertosLlegadaMap;
    private Map<String, String> aeropuertosSalidaMap;
    SimpleDateFormat formatoParaFechas = new SimpleDateFormat("d MMM yyyy", Locale.forLanguageTag("es-ES"));
    private Map<String, String> mapaAerolineas;

    /**
     * Creates new form FrmCliente
     */
    public FrmCliente() {
        initComponents();
        aeropuertosLlegadaMap = new HashMap<>();
        aeropuertosSalidaMap = new HashMap<>();

        verAeropuertosLlegada();
        verAeropuertosSalida();
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) SpinnerPersonas.getEditor();
        JFormattedTextField textField = editor.getTextField();

        textField.setEditable(false);
        textField.setFocusable(false);

        textField.setEditable(false);
        textField.setFocusable(false);

        fechaSalida.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
        fechaSalida.setDateFormatString("dd-MM-yyyy");
        fechaSalida.setDate(new Date());

        reiniciarTable();
    }

    public Date verFechaSalida() {
        return fechaSalida.getDate();
    }

    private void verAeropuertosSalida() {
        DefaultComboBoxModel<String> modelSalida = new DefaultComboBoxModel<>();

        ArrayList<String> lista = negocio.LeerAeropuertos();

        aeropuertosSalidaMap.clear(); // Limpia el mapa antes de llenarlo

        for (String aeropuerto : lista) {
            String[] partes = aeropuerto.split(",");
            if (partes.length > 1) {
                String id = partes[0].trim();
                String nombreAeropuerto = partes[1].trim();
                modelSalida.addElement(nombreAeropuerto);
                aeropuertosSalidaMap.put(nombreAeropuerto, id);
            }
        }
        cmbAeropuertoLlegada.setModel(modelSalida);
    }

    private void verAeropuertosLlegada() {
        DefaultComboBoxModel<String> modelLlegada = new DefaultComboBoxModel<>();
        ArrayList<String> lista = negocio.LeerAeropuertos();
        aeropuertosLlegadaMap.clear(); // Limpia el mapa antes de llenarlo

        for (String aeropuerto : lista) {
            String[] partes = aeropuerto.split(",");
            if (partes.length > 1) {
                String id = partes[0].trim();
                String nombreAeropuerto = partes[1].trim();
                modelLlegada.addElement(nombreAeropuerto);
                aeropuertosLlegadaMap.put(nombreAeropuerto, id);
            }
        }
        cmbAeropuertoSalida.setModel(modelLlegada);
    }

    private void reiniciarTable() {
        DefaultTableModel modelo = (DefaultTableModel) JTableVuelo.getModel();
        modelo.setRowCount(0);
        modelo.setColumnCount(8);
    }

    private void InicializarBusqueda(String AeropuertoSalida, String AeropuertoLlegada, String fechasalida, int cantidadPersonas, String idAeropuertoLlegada, String idAeropuertoSalida, ArrayList<String> VuelosDisponibles) {
        objVuelos.vuelos.clear();
        reiniciarTable();
        DefaultTableModel modelo = (DefaultTableModel) JTableVuelo.getModel();
        mapaAerolineas = negocio.LeerAerolineasDesdeBD();
        try {
            for (String iterador : VuelosDisponibles) {
                String[] partes = iterador.split(",");
                if (partes.length >= 10) {
                    String aerolineaA = partes[0].trim();
                    String precioA = partes[2].trim();
                    String fechaquesaleA = partes[3].trim();
                    String horaSalidaA = partes[4].trim();
                    String idSalidaA = partes[5].trim();
                    String horaLlegadaA = partes[7].trim();
                    String idLlegadaA = partes[8].trim();
                    String duracionA = partes[9].trim();
                    if (idSalidaA.equals(idAeropuertoSalida) && idLlegadaA.equals(idAeropuertoLlegada) && fechaquesaleA.equals(fechasalida)) {
                        Object[] posicion = new Object[8];
                        posicion[0] = mostrarnombreaerolinea(aerolineaA);
                        posicion[1] = AeropuertoSalida;
                        posicion[2] = AeropuertoLlegada;
                        posicion[3] = "";
                        posicion[4] = horaSalidaA;
                        posicion[5] = horaLlegadaA;
                        posicion[6] = duracionA;
                        int PrecioXPersona = parseInt(precioA);
                        int PrecioNeto = PrecioXPersona * cantidadPersonas;
                        posicion[7] = PrecioNeto;
                        modelo.addRow(posicion);

                    }
                } else {
                    System.out.println("Formato de datos de vuelo inválido: " + iterador);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir precio a número: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Índice fuera de rango en el arreglo de partes: " + e.getMessage());
        }
    }

    public String obtenerIDAeropuertoSalida() {
        String aeropuertoSeleccionado = (String) cmbAeropuertoSalida.getSelectedItem();
        String aeropuertoID = null;
        if (aeropuertoSeleccionado != null) {
            aeropuertoID = aeropuertosSalidaMap.get(aeropuertoSeleccionado);
        }
        return aeropuertoID;
    }

    public String obtenerIDAeropuertoLlegada() {
        String aeropuertoSeleccionado = (String) cmbAeropuertoLlegada.getSelectedItem();
        String aeropuertoID = null;
        if (aeropuertoSeleccionado != null) {
            aeropuertoID = aeropuertosLlegadaMap.get(aeropuertoSeleccionado);
        }
        return aeropuertoID;
    }

    private String mostrarnombreaerolinea(String nombreAerolinea) {
        return mapaAerolineas.getOrDefault(nombreAerolinea, nombreAerolinea);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SpinnerPersonas = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        fechaSalida = new com.toedter.calendar.JDateChooser();
        cmbAeropuertoSalida = new javax.swing.JComboBox<>();
        cmbAeropuertoLlegada = new javax.swing.JComboBox<>();
        btnBuscarVuelos = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTableVuelo = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        SpinnerPersonas.setModel(new javax.swing.SpinnerNumberModel(1, 1, 5, 1));

        jLabel1.setText("Aeropuerto Salida:");

        jLabel2.setText("Aeropuerto llegada:");

        jLabel3.setText("Fecha de Salida");

        jLabel4.setText("Cantidad de personas");

        cmbAeropuertoSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAeropuertoSalidaActionPerformed(evt);
            }
        });

        cmbAeropuertoLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAeropuertoLlegadaActionPerformed(evt);
            }
        });

        btnBuscarVuelos.setText("Buscar Vuelos");
        btnBuscarVuelos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarVuelosActionPerformed(evt);
            }
        });

        JTableVuelo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre Aerolinea", "Aeropuerto Salida", "Aeropuerto Llegada", "Escalas", "Hora De Salida", "Hora De Llegada", "Duracion", "Precio Total"
            }
        ));
        jScrollPane1.setViewportView(JTableVuelo);

        jButton1.setText("Comprar Vuelo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(186, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SpinnerPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(379, 379, 379)
                                .addComponent(btnBuscarVuelos))
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(fechaSalida, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 368, Short.MAX_VALUE)
                                .addComponent(cmbAeropuertoLlegada, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cmbAeropuertoSalida, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(647, 647, 647)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmbAeropuertoSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbAeropuertoLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SpinnerPersonas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 9, Short.MAX_VALUE)
                        .addComponent(btnBuscarVuelos)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void cmbAeropuertoSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAeropuertoSalidaActionPerformed

    }//GEN-LAST:event_cmbAeropuertoSalidaActionPerformed

    private void cmbAeropuertoLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAeropuertoLlegadaActionPerformed

    }//GEN-LAST:event_cmbAeropuertoLlegadaActionPerformed

    private void btnBuscarVuelosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarVuelosActionPerformed
        String AeropuertoSalida = (String) cmbAeropuertoSalida.getSelectedItem();
        String AeropuertoLlegada = (String) cmbAeropuertoLlegada.getSelectedItem();
        Date fechaSalidaa = fechaSalida.getDate();
        int cantidadPersonas = (int) SpinnerPersonas.getValue();
        String idAeropuertoSalida = obtenerIDAeropuertoSalida();
        String idAeropuertoLlegada = obtenerIDAeropuertoLlegada();
        String fechasalida = formatoParaFechas.format(fechaSalidaa);
        DefaultTableModel modelo = (DefaultTableModel) JTableVuelo.getModel();
        modelo.setRowCount(0);

        ArrayList<String> VuelosDisponibles = negocio.LeerVuelos();

        if (AeropuertoSalida.equals(AeropuertoLlegada)) {
            JOptionPane.showMessageDialog(null, "Los aeropuertos no pueden ser los mismos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            InicializarBusqueda(AeropuertoSalida, AeropuertoLlegada, fechasalida, cantidadPersonas, idAeropuertoLlegada, idAeropuertoSalida, VuelosDisponibles);
        }


    }//GEN-LAST:event_btnBuscarVuelosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FrmMatriz ventana = new FrmMatriz();
        ventana.setCantidadPersonas((int) SpinnerPersonas.getValue());
        ventana.setVisible(true);

    }//GEN-LAST:event_jButton1ActionPerformed

    // ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTableVuelo;
    private javax.swing.JSpinner SpinnerPersonas;
    private javax.swing.JToggleButton btnBuscarVuelos;
    private javax.swing.JComboBox<String> cmbAeropuertoLlegada;
    private javax.swing.JComboBox<String> cmbAeropuertoSalida;
    private com.toedter.calendar.JDateChooser fechaSalida;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    private Map<String, String> LeerAerolineasDesdeBD() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
