/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package presentacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Random;
import javax.swing.*;

import negocio.ClaseNegocio;
import objetos.objVuelos;

/**
 *
 * @author Cris
 */
public class FrmAdmin extends javax.swing.JFrame {

    private Map<String, String> aerolineasMap;
    private Map<String, String> aeropuertosLlegadaMap;
    private Map<String, String> aeropuertosSalidaMap;

    ClaseNegocio aviones = new ClaseNegocio();

    public FrmAdmin() {
        initComponents();
        aerolineasMap = new HashMap<>(); // Inicializar el HashMap aquí
        aeropuertosLlegadaMap = new HashMap<>();
        aeropuertosSalidaMap = new HashMap<>();
        verAerolineas();
        verAeropuertosLlegada();
        verAeropuertosSalida();
        inicializarHoras();

        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spHoraLlegada.getEditor();
        JFormattedTextField textField = editor.getTextField();

        JSpinner.DefaultEditor editor1 = (JSpinner.DefaultEditor) spHoraSalida.getEditor();
        JFormattedTextField textField1 = editor1.getTextField();

        textField.setEditable(false);
        textField.setFocusable(false);

        textField1.setEditable(false);
        textField1.setFocusable(false);

        fechaSalida.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
        fechaSalida.setDateFormatString("dd-MM-yyyy");
        fechaSalida.setDate(new Date());

        fechaLlegada.setMinSelectableDate(new GregorianCalendar(2024, Calendar.JANUARY, 1).getTime());
        fechaLlegada.setDateFormatString("dd-MM-yyyy");
        fechaLlegada.setDate(new Date());

        fechaSalida.getDateEditor().addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    Date fechasalida = fechaSalida.getDate();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(fechasalida);
                    cal.add(Calendar.YEAR, 1); // Añadir un año a la fecha de salida
                    Date maxFechaLlegada = cal.getTime();
                    fechaLlegada.setSelectableDateRange(fechasalida, maxFechaLlegada);
                }
            }
        });

        txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioKeyTyped(evt);
            }
        });

        spHoraSalida.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                validarHoras();
            }
        });

        spHoraLlegada.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                validarHoras();
            }
        });

    }

// ---------------------------------------------------------------------------------------------------------------------------------
    // Cosas que van a ir en el objeto
    public Date verFechaSalida() {
        return fechaSalida.getDate();
    }

    public Date verFechaLlegada() {
        return fechaLlegada.getDate();
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

        long diferencia = horaLlegada.getTime() - horaSalida.getTime();

        long diffMinutes = diferencia / (60 * 1000) % 60;
        long diffHours = diferencia / (60 * 60 * 1000) % 24;

        int duracionCombinada = (int) (diffHours * 100 + diffMinutes);

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

    private void verAeropuertosSalida() {
        DefaultComboBoxModel<String> modelSalida = new DefaultComboBoxModel<>();

        ArrayList<String> lista = aviones.LeerAeropuertos();

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
        ArrayList<String> lista = aviones.LeerAeropuertos();
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

// ---------------------------------------------------------------------------------------------------------------------------------
    // Selecciona ID de Piloto y Servicio al Cliente!
    private String seleccionarPiloto(String aerolineaID) {
        ArrayList<String> pilotos = aviones.filtrarPilotos(aerolineaID);
        String pilotoID = buscarSiguienteDisponible(pilotos);
        if (pilotoID != null) {
            return pilotoID;
        } else {
            // En caso de que no haya pilotos disponibles, retorna null
            System.out.println("No hay pilotos disponibles para la aerolínea seleccionada.");
            return null;
        }
    }

    private String seleccionarServicioAlCliente(String aerolineaID) {
        ArrayList<String> servicioAlCliente = aviones.filtrarServicioAlCliente(aerolineaID);
        String servicioID = buscarSiguienteDisponible(servicioAlCliente);
        if (servicioID != null) {
            return servicioID;
        } else {
            System.out.println("No hay servicios al cliente disponibles para la aerolínea seleccionada.");
            return null;
        }
    }

// ---------------------------------------------------------------------------------------------------------------------------------
    private void ModificarPilotoYServicio(String aerolineaID) {
        ArrayList<String> pilotos = aviones.filtrarPilotos(aerolineaID);
        ArrayList<String> servicioAlCliente = aviones.filtrarServicioAlCliente(aerolineaID);
        ArrayList<String> listaAviones = aviones.LeerAviones(aerolineaID);

        if (pilotos.size() >= 2 && servicioAlCliente.size() >= 2 && listaAviones.size() >= 2) {

            String pilotoID = buscarSiguienteDisponible(pilotos);
            String servicioID = buscarSiguienteDisponible(servicioAlCliente);
            String avionID = buscarSiguienteAvion(listaAviones);

            if (pilotoID != null && servicioID != null && avionID != null) {
                try {
                    // Modifica la disponibilidad de los tripulantes seleccionados
                    aviones.ModificarDisponibilidad(pilotoID);
                    aviones.ModificarDisponibilidadServicioAlCLiente(servicioID);
                    aviones.ModificarDisponibilidadAvion(avionID);
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

    private String buscarSiguienteAvion(ArrayList<String> lista) {
        for (String item : lista) {
            String[] partes = item.split("\\s*,\\s*");
            String id = partes[0].trim();
            String disponibilidad = partes[3].trim(); // Suponiendo que la disponibilidad está en la posición 4

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
        fechaSalida = new com.toedter.calendar.JDateChooser();
        fechaLlegada = new com.toedter.calendar.JDateChooser();

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

        JSpinner.DateEditor de = new JSpinner.DateEditor(spHoraSalida, "HH:mm");
        spHoraSalida.setEditor(de);

        JSpinner.DateEditor xd = new JSpinner.DateEditor(spHoraLlegada, "HH:mm");
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
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(spHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7)
                                        .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(fechaLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2))))
                            .addComponent(spHoraLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(278, 278, 278)
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(229, Short.MAX_VALUE))
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
                        .addComponent(fechaSalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fechaLlegada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69)
                .addComponent(btnCrear)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        if (!validaciones()) {
            return;
        }

        String aerolineaID = obtenerIdAerolineaSeleccionada();
        if (!aviones.puedeCrearVuelo(aerolineaID)) {
            JOptionPane.showMessageDialog(null, "No se pueden crear más de dos vuelos por aerolínea.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int identificador = IdVuelo();
        double precio;
        try {
            precio = Double.parseDouble(txtPrecio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Precio inválido.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date fechasalida = verFechaSalida();
        Date horaSalida = horaSalida();
        String aeropuertoSalida = obtenerIDAeropuertoSalida();
        Date fechallegada = verFechaLlegada();
        Date horaLlegada = horaLlegada();
        String aeropuertoLlegada = obtenerIDAeropuertoLlegada();
        int duracion = CalcularDuracion();

        String idPiloto = seleccionarPiloto(aerolineaID);
        String idServicioAlCliente = seleccionarServicioAlCliente(aerolineaID);

        if (idPiloto == null || idServicioAlCliente == null) {
            JOptionPane.showMessageDialog(null, "No se pudo seleccionar un piloto o servicio al cliente para la aerolínea.", "Error!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ModificarPilotoYServicio(aerolineaID);

        objVuelos nuevoVuelo = new objVuelos(
                identificador,
                aerolineaID,
                precio,
                fechasalida,
                horaSalida,
                aeropuertoSalida,
                fechallegada,
                horaLlegada,
                aeropuertoLlegada,
                duracion,
                aerolineaID,
                idServicioAlCliente,
                idPiloto
        );

        objVuelos.vuelos.add(nuevoVuelo);
        aviones.InsertarVuelo(objVuelos.vuelos);
        JOptionPane.showMessageDialog(null, "Registrado Correctamente!", "Confirmación!", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnCrearActionPerformed

// ---------------------------------------------------------------------------------------------------------------------------------
    // Conseguir IDs
    public String obtenerIdAerolineaSeleccionada() {
        String aerolineaSeleccionada = (String) cmbAerolinea.getSelectedItem();
        String aerolineaID = null;
        if (aerolineaSeleccionada != null) {
            aerolineaID = aerolineasMap.get(aerolineaSeleccionada);
        }
        return aerolineaID;
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

// ---------------------------------------------------------------------------------------------------------------------------------
    // Eventos ComboBoxes inutiles xd
    private void cmbAerolineaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAerolineaActionPerformed

    }//GEN-LAST:event_cmbAerolineaActionPerformed

    private void cmbAeropuertoSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAeropuertoSalidaActionPerformed

    }//GEN-LAST:event_cmbAeropuertoSalidaActionPerformed

    private void cmbAeropuertoLlegadaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAeropuertoLlegadaActionPerformed

    }//GEN-LAST:event_cmbAeropuertoLlegadaActionPerformed

// ---------------------------------------------------------------------------------------------------------------------------------
    private boolean validaciones() {
        String aeropuertoLlegada = (String) cmbAeropuertoLlegada.getSelectedItem();
        String aeropuertoSalida = (String) cmbAeropuertoSalida.getSelectedItem();
        Date horallegada = (Date) spHoraLlegada.getValue();
        Date horasalida = (Date) spHoraSalida.getValue();

        if (aeropuertoLlegada.equals(aeropuertoSalida)) {
            JOptionPane.showMessageDialog(null, "Los aeropuertos no pueden ser iguales", "Error Aeropuertos", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (horallegada.equals(horasalida)) {
            JOptionPane.showMessageDialog(null, "La hora de salida y llegada no pueden ser iguales!", "Error con las horas!", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (horallegada.before(horasalida)) {
            JOptionPane.showMessageDialog(null, "La hora de llegada no puede ser menor que la de salida!", "Error con las horas!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void txtPrecioKeyTyped(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();

        // Permitir números y un solo punto decimal
        if (!(Character.isDigit(caracter) || caracter == '.')) {
            evt.consume(); // Descartar caracteres no permitidos
        }

        // Permitir solo un punto decimal
        if (caracter == '.' && txtPrecio.getText().contains(".")) {
            evt.consume(); // Descartar puntos adicionales
        }
    }

    private void validarHoras() {
        Date horaSalida = horaSalida();
        Date horaLlegada = horaLlegada();

        if (horaSalida != null && horaLlegada != null) {
            long diferenciaMillis = horaLlegada.getTime() - horaSalida.getTime();

            // Verifica que la diferencia sea al menos una hora (3600000 milisegundos)
            if (diferenciaMillis < 3600000) {
                JOptionPane.showMessageDialog(null, "La hora de llegada debe ser al menos una hora después de la hora de salida.", "Error!", JOptionPane.ERROR_MESSAGE);
                // Ajustar la hora de llegada al menos una hora después de la hora de salida
                Calendar cal = Calendar.getInstance();
                cal.setTime(horaSalida);
                cal.add(Calendar.HOUR_OF_DAY, 1);
                spHoraLlegada.setValue(cal.getTime());
            }
        }
    }

    private void inicializarHoras() {
        Calendar cal = Calendar.getInstance();

        // Establece la hora de salida a las 1 PM
        cal.set(Calendar.HOUR_OF_DAY, 13);  // 13:00 en formato de 24 horas
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        spHoraSalida.setValue(cal.getTime());

        // Establece la hora de llegada a una hora después de la hora de salida
        cal.add(Calendar.HOUR_OF_DAY, 1);
        spHoraLlegada.setValue(cal.getTime());
    }

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
    private com.toedter.calendar.JDateChooser fechaLlegada;
    private com.toedter.calendar.JDateChooser fechaSalida;
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
