/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import java.util.*;
import java.util.Random;
import javax.swing.*;

import negocio.Aviones;

/**
 *
 * @author Cris
 */
public class FrmAdmin extends javax.swing.JFrame {

    /**
     * Creates new form FrmAdmin
     */
    private Map<String, String> aerolineasMap; // Mapa para almacenar la relación aerolínea-ID
    private Map<String, String> aeropuertosMap;

    Aviones aviones = new Aviones();

    public FrmAdmin() {
        initComponents();
        aerolineasMap = new HashMap<>(); // Inicializar el HashMap aquí
        aeropuertosMap = new HashMap<>();
        verAerolineas();
        verAeropuertos();
        IdVuelo();

    }

// ---------------------------------------------------------------------------------------------------------------------------------
    // Cosas que van a ir en el objeto
    public Date verFechaSalida() {
        return jDateChooser1.getDate();
    }

    public Date verFechaLlegada() {
        return jDateChooser2.getDate();
    }

    public Date horaSalida() {
        return (Date) spHoraSalida.getValue();
    }

    public Date horaLlegada() {
        return (Date) spHoraLlegada.getValue();
    }

    public int IdVuelo() {
        Random rand = new Random();

        int min = 1000;
        int max = 9999;

        int random = rand.nextInt(min, max);

        return random;
    }

    public int CalcularDuracion() {
        Date horaSalida = (Date) spHoraSalida.getValue();
        Date horaLlegada = (Date) spHoraLlegada.getValue();

        // Verifica que las horas no sean nulas
        if (horaSalida == null || horaLlegada == null) {
            JOptionPane.showMessageDialog(null, "Las horas no pueden ser nulas!", "Error!", JOptionPane.ERROR_MESSAGE);
            return 0; // Retorna 0 para indicar un error
        }

        // Verifica que las horas no sean iguales
        if (horaSalida.equals(horaLlegada)) {
            JOptionPane.showMessageDialog(null, "Las horas no pueden ser iguales!", "Error!", JOptionPane.ERROR_MESSAGE);
            return 0; // Retorna 0 para indicar un error
        }

        // Calcular la diferencia en milisegundos
        long diferenciaEnMillis = horaLlegada.getTime() - horaSalida.getTime();

        // Verifica que la hora de llegada sea después de la hora de salida
        if (diferenciaEnMillis < 0) {
            JOptionPane.showMessageDialog(null, "La hora de llegada debe ser después de la hora de salida!", "Error!", JOptionPane.ERROR_MESSAGE);
            return 0; // Retorna 0 para indicar un error
        }

        // Convertir la diferencia a horas y minutos
        long diferenciaEnHoras = diferenciaEnMillis / (1000 * 60 * 60);
        long diferenciaEnMinutos = (diferenciaEnMillis / (1000 * 60)) % 60;

        // Combinar horas y minutos en un solo entero
        int duracionCombinada = (int) (diferenciaEnHoras * 100 + diferenciaEnMinutos);

        return duracionCombinada;
    }

// ---------------------------------------------------------------------------------------------------------------------------------
    // Ver en ComboBoxes
    private void verAerolineas() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cmbAerolinea.getModel();
        ArrayList<String> lista = aviones.LeerAerolineas();

        for (String aerolinea : lista) {
            String[] partes = aerolinea.split(",");
            if (partes.length > 1) {
                String id = partes[0].trim();
                String aerolineas = partes[1].trim();
                model.addElement(aerolineas);
                aerolineasMap.put(aerolineas, id);

            }
        }
        cmbAerolinea.setModel(model);
    }

    private void verAeropuertos() {
        DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) cmbAeropuertoLlegada.getModel();
        ArrayList<String> lista = aviones.LeerAeropuertos();

        aeropuertosMap.clear(); // Limpia el mapa antes de llenarlo

        for (String aeropuerto : lista) {
            String[] partes = aeropuerto.split(",");
            if (partes.length > 1) {
                String id = partes[0].trim();
                String nombreAeropuerto = partes[1].trim();
                model.addElement(nombreAeropuerto);
                aeropuertosMap.put(nombreAeropuerto, id);
            }
        }

        cmbAeropuertoLlegada.setModel(model);
        cmbAeropuertoSalida.setModel(model);
    }

// ---------------------------------------------------------------------------------------------------------------------------------
    // Selecciona Piloto y Servicio Al Cliente Basado en el ID de Aerolinea
    private void seleccionarPilotoYServicio(String aerolineaID) {
        ArrayList<String> pilotos = aviones.filtrarPilotos(aerolineaID);
        ArrayList<String> servicioAlCliente = aviones.filtrarServicioAlCliente(aerolineaID);

        if (pilotos.size() >= 2 && servicioAlCliente.size() >= 2) {
            // Selecciona los dos primeros pilotos y servicios al cliente
            String piloto1 = pilotos.get(0);
            String piloto2 = pilotos.get(1);
            String servicio1 = servicioAlCliente.get(0);
            String servicio2 = servicioAlCliente.get(1);

            // Extrae los IDs de los tripulantes
            String pilotoID1 = piloto1.split(",")[0].trim();
            String pilotoID2 = piloto2.split(",")[0].trim();
            String servicioID1 = servicio1.split(",")[0].trim();
            String servicioID2 = servicio2.split(",")[0].trim();

            // Imprime los resultados para depuración
            System.out.println("Pilotos seleccionados:");
            System.out.println("Piloto 1 ID: " + pilotoID1);
            System.out.println("Piloto 2 ID: " + pilotoID2);

            System.out.println("Servicios al Cliente seleccionados:");
            System.out.println("Servicio 1 ID: " + servicioID1);
            System.out.println("Servicio 2 ID: " + servicioID2);
        } else {
            System.out.println("No hay suficientes pilotos o servicios al cliente disponibles para la aerolínea seleccionada.");
        }
    }

    private void ModificarPilotoYServicio(String aerolineaID) {
        ArrayList<String> pilotos = aviones.filtrarPilotos(aerolineaID);
        ArrayList<String> servicioAlCliente = aviones.filtrarServicioAlCliente(aerolineaID);

        // Imprime la cantidad de pilotos y servicios al cliente
        System.out.println("Número de pilotos disponibles: " + pilotos.size());
        System.out.println("Número de servicios al cliente disponibles: " + servicioAlCliente.size());

        if (pilotos.size() >= 2 && servicioAlCliente.size() >= 2) {
            // Busca el siguiente piloto disponible
            String pilotoID = buscarSiguienteDisponible(pilotos);
            // Busca el siguiente servicio al cliente disponible
            String servicioID = buscarSiguienteDisponible(servicioAlCliente);

            if (pilotoID != null && servicioID != null) {
                try {
                    // Modifica la disponibilidad de los tripulantes seleccionados
                    aviones.ModificarDisponibilidad(pilotoID);
                    aviones.ModificarDisponibilidadServicioAlCLiente(servicioID);

                    System.out.println("Disponibilidad modificada con éxito.");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error al modificar la disponibilidad.");
                }
            } else {
                System.out.println("No hay suficientes tripulantes disponibles.");
            }
        } else {
            System.out.println("No hay suficientes pilotos o servicio al cliente disponibles para la aerolínea seleccionada.");
        }
    }

    private String buscarSiguienteDisponible(ArrayList<String> lista) {
        for (String item : lista) {
            String[] partes = item.split("\\s*,\\s*");
            String id = partes[0].trim();
            String disponibilidad = partes[4].trim(); // Suponiendo que la disponibilidad está en la posición 4

            if (disponibilidad.equals("0")) { // 0 significa disponible
                return id;
            }
        }
        return null; // No hay disponibles
    }

// ---------------------------------------------------------------------------------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cmbAerolinea = new javax.swing.JComboBox<>();
        cmbAeropuertoLlegada = new javax.swing.JComboBox<>();
        cmbAeropuertoSalida = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        Date date = new Date();
        SpinnerDateModel sm =
        new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spHoraSalida = new javax.swing.JSpinner(sm);
        Date fecha = new Date();
        SpinnerDateModel hora =
        new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        spHoraLlegada = new javax.swing.JSpinner(hora);
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmbAerolinea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAerolineaActionPerformed(evt);
            }
        });

        cmbAeropuertoLlegada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAeropuertoLlegadaActionPerformed(evt);
            }
        });

        cmbAeropuertoSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAeropuertoSalidaActionPerformed(evt);
            }
        });

        jLabel1.setText("Aerolinea");

        jLabel2.setText("Aeropuerto de Salida");

        jLabel3.setText("Aeropuerto de Llegada");

        jLabel4.setText("Precio");

        JSpinner.DateEditor de = new JSpinner.DateEditor(spHoraSalida, "hh:mm a");
        spHoraSalida.setEditor(de);

        JSpinner.DateEditor xd = new JSpinner.DateEditor(spHoraLlegada, "hh:mm: a");
        spHoraLlegada.setEditor(xd);

        jLabel5.setText("Hora de Salida");

        jLabel6.setText("Hora de Llegada");

        jLabel7.setText("Fecha de Salida");

        jLabel8.setText("Fecha de Llegada");

        btnCrear.setText("Crear Vuelo");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(cmbAeropuertoLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cmbAeropuertoSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(cmbAerolinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(142, 142, 142)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(spHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)))
                            .addComponent(spHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(278, 278, 278)
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(72, 72, 72)
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbAerolinea, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbAeropuertoSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbAeropuertoLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(50, 50, 50)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(spHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69)
                .addComponent(btnCrear)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Boton para meter todo al objeto

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        String aerolineaID = obtenerIdAerolineaSeleccionada();

        // Asegúrate de que el ID no sea nulo o vacío antes de proceder
        /* if (aerolineaID != null && !aerolineaID.trim().isEmpty()) {
            // Seleccionar el piloto y el servicio al cliente para la aerolínea seleccionada
            seleccionarPilotoYServicio(aerolineaID);
            ModificarPilotoYServicio(aerolineaID);

        } else {
            System.out.println("No se ha seleccionado una aerolínea válida.");
        } */
      //   int duracion = CalcularDuracion();
      /*  Date fechaSalida = verFechaSalida();
        Date fechaLlegada = verFechaLlegada();

        if (fechaSalida.before(fechaLlegada)) {
            JOptionPane.showMessageDialog(null, "La fecha de llegada no puede ser menor a la de salida!", "Error con las fechas", JOptionPane.ERROR_MESSAGE);
        } else if (fechaSalida.equals(fechaLlegada)) {
            JOptionPane.showMessageDialog(null, "La fecha de llegada no puede ser igual a la de salida!", "Error con las fechas", JOptionPane.ERROR_MESSAGE);
        } */
    }//GEN-LAST:event_btnCrearActionPerformed

// ---------------------------------------------------------------------------------------------------------------------------------
    // Conseguir IDs
    public String obtenerIdAerolineaSeleccionada() {
        String aerolineaSeleccionada = (String) cmbAerolinea.getSelectedItem();
        String aerolineaID = null;
        if (aerolineaSeleccionada != null) {
            aerolineaID = aerolineasMap.get(aerolineaSeleccionada);
            System.out.println("ID de la aerolínea seleccionada: " + aerolineaID);
        }
        return aerolineaID;
    }

    public String obtenerIDAeropuerto() {
        String aeropuertoSeleccionado = (String) cmbAeropuertoSalida.getSelectedItem();
        String aeropuertoID = null;
        if (aeropuertoSeleccionado != null) {
            aeropuertoID = aeropuertosMap.get(aeropuertoSeleccionado);
            System.out.println("ID del Aeropuerto: " + aeropuertoID);

        }
        return aeropuertoID;
    }

// ---------------------------------------------------------------------------------------------------------------------------------
    // Eventos ComboBoxes inutiles xd
    private void cmbAerolineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAerolineaActionPerformed

    }//GEN-LAST:event_cmbAerolineaActionPerformed

    private void cmbAeropuertoSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAeropuertoSalidaActionPerformed

    }//GEN-LAST:event_cmbAeropuertoSalidaActionPerformed

    private void cmbAeropuertoLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAeropuertoLlegadaActionPerformed

    }//GEN-LAST:event_cmbAeropuertoLlegadaActionPerformed

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
            java.util.logging.Logger.getLogger(FrmAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAdmin.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JComboBox<String> cmbAerolinea;
    private javax.swing.JComboBox<String> cmbAeropuertoLlegada;
    private javax.swing.JComboBox<String> cmbAeropuertoSalida;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSpinner spHoraLlegada;
    private javax.swing.JSpinner spHoraSalida;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
