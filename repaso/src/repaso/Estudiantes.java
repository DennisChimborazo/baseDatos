/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package repaso;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 *  * @author Dalex
 */
public class Estudiantes extends javax.swing.JInternalFrame {

    String rol;
    /**
     * Creates new form Estudiantes
     */
    DefaultTableModel modelo;

    public Estudiantes() {

        initComponents();
        cargarCursos();
        cargarTabla("");
        seleccionarEstudianteTabla();
     
    }

    public void asignarRol(String rolPersonal) {
        this.rol = rolPersonal;
    }
    
    public void seleccionarEstudianteTabla() {
        jtblEstudiantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (jtblEstudiantes.getSelectedRow() != -1) {
                    int fila = jtblEstudiantes.getSelectedRow();
                    jtxtCedula.setText(jtblEstudiantes.getValueAt(fila, 0).toString());
                    jtxtNombre.setText(jtblEstudiantes.getValueAt(fila, 1).toString());
                    jtxtApellido.setText(jtblEstudiantes.getValueAt(fila, 2).toString());
                    jtxtTelefono.setText(jtblEstudiantes.getValueAt(fila, 3).toString());
                    jtxtDireccion.setText(jtblEstudiantes.getValueAt(fila, 4).toString());
                    if (jtblEstudiantes.getValueAt(fila, 4).toString().equals("sin direccion")) {
                        jtxtDireccion.setText("");

                    } else {
                        jtxtDireccion.setText(jtblEstudiantes.getValueAt(fila, 4).toString());

                    }
                    jcmbCursos.setSelectedItem(jtblEstudiantes.getValueAt(fila, 5));

                    if (rol.equals("admin")) {

                    } else {
                        jbtnReporteIndividual.setEnabled(true);
                    }
                }

            }
        });
    }

    public void guardarEstudiante() {
            String ce = this.jtxtCedula.getText();
            ce = ce.replace(" ", "");
            if (ce.length() == 10) {
                if (!this.jcmbCursos.getSelectedItem().toString().equals("Seleccione un curso")) {
                    String tel = this.jtxtTelefono.getText();
                    tel = tel.replace(" ", "");
                    if (tel.length() == 10) {
                        try {
                            Conexion cc = new Conexion();
                            Connection cn = cc.conectar();
                            String sql = "insert into estudiantes (estCedula,estNombre,estApellido,estTelefono,estDireccion,curId)values(?,?,?,?,?,?)";
                            PreparedStatement psd = cn.prepareStatement(sql);
                            psd.setString(1, this.jtxtCedula.getText());
                            psd.setString(2, this.jtxtNombre.getText().toLowerCase());
                            psd.setString(3, this.jtxtApellido.getText().toLowerCase());
                            psd.setString(4, this.jtxtTelefono.getText());
                            if (!this.jtxtDireccion.getText().isEmpty()) {
                                psd.setString(5, this.jtxtDireccion.getText().toLowerCase());
                            } else {
                                psd.setString(5, "sin direccion");
                            }
                            psd.setString(6, this.jcmbCursos.getSelectedItem().toString().substring(0, 1));
                            int num = psd.executeUpdate();
                            if (num != 0) {
                                JOptionPane.showMessageDialog(null, "Se guardo el nuevo estudiante");
                                borrarDatos();

                                cargarTabla("");
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Verifique los datos que desea guardar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El telefono debe tener 10 digitos");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un curso");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La cedula debe tener 10 digitos");
            }


    }

    public void cancelar() {
        if (this.jtblEstudiantes.getSelectedRow() != -1) {
            if (verificarDatosEditar()) {
                int op = JOptionPane.showConfirmDialog(null, "Desea cancelar los cambios del estudiante", "Confirmacion", JOptionPane.YES_NO_OPTION);
                if (op == 0) {
                   
                    cargarTabla("");
                }
            } else {
                borrarDatos();
                cargarTabla("");

            }
        } else {
            if (!this.jtxtNombre.getText().isEmpty()
                    || !this.jtxtCedula.getText().isEmpty()
                    || !this.jtxtApellido.getText().isEmpty()
                    || this.jtxtTelefono.getText().trim().length() == 10
                    || !this.jcmbCursos.getSelectedItem().toString().trim().equals("Seleccione un curso")) {
                int op = JOptionPane.showConfirmDialog(null, "Desea cancelar añadir un nuevo estudiante", "Confirmacion", JOptionPane.YES_NO_OPTION);
                if (op == 0) {

                    borrarDatos();
                    cargarTabla("");
                }
            } else {
                borrarDatos();
                cargarTabla("");
            }

        }

    }

    public void borrarDatos() {
        this.jtxtCedula.setText("");
        this.jtxtNombre.setText("");
        this.jtxtApellido.setText("");
        this.jtxtDireccion.setText("");
        this.jtxtTelefono.setText("");
        this.jcmbCursos.setSelectedIndex(0);
        this.jtxtTelefono.setText("");

    }

    public void cargarTabla(String dato) {
        try {
            String titulos[] = {"Cedula", "Nombre", "Apellido", "Telefono", "Dirección", "Curso"};
            String registros[] = new String[6];
            modelo = new DefaultTableModel(null, titulos);

            Conexion cn = new Conexion();
            Connection cc = cn.conectar();
            String sql = "select estudiantes.estCedula, estudiantes.estNombre, estudiantes.estApellido,estudiantes.estTelefono,estudiantes.estDireccion,estudiantes.curId, cursos.Nombre from estudiantes,cursos where estudiantes.curId=cursos.curId and estudiantes.estCedula like'%" + dato + "'";
            Statement psd = cc.createStatement();
            ResultSet rs = psd.executeQuery(sql);
            while (rs.next()) {
                registros[0] = rs.getString("estCedula");
                registros[1] = rs.getString("estNombre");
                registros[2] = rs.getString("estApellido");
                registros[3] = rs.getString("estTelefono");
                registros[4] = rs.getString("estDireccion");
                registros[5] = rs.getString("curId") + " - " + rs.getString("Nombre");
                modelo.addRow(registros);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        jtblEstudiantes.setModel(modelo);
    }

    public void borrarEstudiante() {
        int op = JOptionPane.showConfirmDialog(null, "Desea borrar el estudiante", "Confirmacion", JOptionPane.YES_NO_OPTION);
        if (op == 0) {
            try {
                Conexion cc = new Conexion();
                Connection cn = cc.conectar();
                String Sql = "delete from estudiantes where  estCedula='" + jtxtCedula.getText() + " ' ";
                PreparedStatement psd = cn.prepareStatement(Sql);
                int n = psd.executeUpdate();
                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Se elimino al estudiante del registro");
                    borrarDatos();
                    cargarTabla("");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Verifique los datos que desea borrar");
            }

        }

    }

    public void editarEstudiante() {
        if (verificarDatosEditar()) {
            int op = JOptionPane.showConfirmDialog(null, "Desea editar el estudiante", "Confirmacion", JOptionPane.YES_NO_OPTION);
            if (op == 0) {
                if (!this.jcmbCursos.getSelectedItem().toString().equals("Seleccione un curso")) {
                    String tel = this.jtxtTelefono.getText();
                    tel = tel.replace(" ", "");
                    if (tel.length() == 10) {

                        String direccion = this.jtxtDireccion.getText();
                        if (direccion.trim().isEmpty()) {
                            direccion = "sin direccion";
                        }
                        try {
                            Conexion cc = new Conexion();
                            Connection cn = cc.conectar();
                            String sql = "update estudiantes set estNombre='" + jtxtNombre.getText()
                                    + "',estApellido='" + jtxtApellido.getText() + "', estTelefono='"
                                    + jtxtTelefono.getText() + "', estDireccion='" + direccion.trim() + "',"
                                    + "curId='" + this.jcmbCursos.getSelectedItem().toString().substring(0, 1) + "'"
                                    + "where estCedula = '" + jtxtCedula.getText() + "'";
                            PreparedStatement psd = cn.prepareStatement(sql);
                            int n = psd.executeUpdate();
                            if (n > 0) {
                                JOptionPane.showMessageDialog(null, "Se actualizo la infomacion del estudiante");
                              
                                borrarDatos();
                                cargarTabla("");
                            }
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Verifique los datos que desea editar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El telefono debe tener 10 digitos");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un curso");
                }

            }
        } else {
            JOptionPane.showMessageDialog(null, "No se a registrado ningun cambio");

        }

    }

    public boolean verificarDatosEditar() {
        int fila = this.jtblEstudiantes.getSelectedRow();
        String nombreTabla = this.jtblEstudiantes.getValueAt(fila, 1).toString();
        String apellidoTabla = this.jtblEstudiantes.getValueAt(fila, 2).toString();
        String telefonoTabla = this.jtblEstudiantes.getValueAt(fila, 3).toString();
        String direccionTabla = this.jtblEstudiantes.getValueAt(fila, 4).toString();
        String curso = this.jtblEstudiantes.getValueAt(fila, 5).toString();
        if (direccionTabla.equals("sin direccion")) {
            direccionTabla = "";
        }
        return !this.jtxtNombre.getText().equals(nombreTabla)
                || !this.jtxtApellido.getText().equals(apellidoTabla)
                || !this.jtxtTelefono.getText().equals(telefonoTabla)
                || !this.jtxtDireccion.getText().equals(direccionTabla)
                || !this.jcmbCursos.getSelectedItem().toString().equals(curso);
    }

    public void cargarCursos() {
        this.jcmbCursos.addItem("Seleccione un curso");

        try {
            String curId = "";
            String nombre = "";

            Conexion cc = new Conexion();
            Connection cn = cc.conectar();
            String slq = "select * from cursos";
            Statement psd = cn.createStatement();
            ResultSet rs = psd.executeQuery(slq);

            while (rs.next()) {
                curId = rs.getString("curId");
                nombre = rs.getString("nombre");
                this.jcmbCursos.addItem(curId + " - " + nombre);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jtxtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jcmbCursos = new javax.swing.JComboBox<>();
        jtxtTelefono = new javax.swing.JFormattedTextField();
        jtxtNombre = new ComponentesPropios.jtxtFieldLetras();
        jtxtApellido = new ComponentesPropios.jtxtFieldLetras();
        jtxtCedula = new ComponentesPropios.jtxtFieldNumeros();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jbtnNuevo = new javax.swing.JButton();
        jbtnEditar = new javax.swing.JButton();
        jbtnGuardar = new javax.swing.JButton();
        jbtnBorrar = new javax.swing.JButton();
        jbtnCancelar = new javax.swing.JButton();
        jbtnReporteIndividual = new javax.swing.JButton();
        jbtnReporte = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();
        jtxtBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setText("Cedula:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, -1, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setText("Nombre:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 110, -1, -1));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setText("Apellido:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel4.setText("Telefono:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, -1, -1));

        jtxtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtxtDireccionKeyTyped(evt);
            }
        });
        jPanel1.add(jtxtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 230, 30));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel5.setText("Direccion:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, -1, -1));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel6.setText("Curso:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 290, -1, -1));

        jPanel1.add(jcmbCursos, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 230, 30));

        try {
            jtxtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("09########")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jtxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtTelefonoKeyPressed(evt);
            }
        });
        jPanel1.add(jtxtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 200, 230, 30));
        jPanel1.add(jtxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 230, 30));
        jPanel1.add(jtxtApellido, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 230, 30));
        jPanel1.add(jtxtCedula, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 230, 30));

        jLabel8.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel8.setText("Datos Personales");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 450, 410));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNuevoActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 120, -1));

        jbtnEditar.setText("Editar");
        jbtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditarActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 120, -1));

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 120, -1));

        jbtnBorrar.setText("Borrar");
        jbtnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnBorrarActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 120, -1));

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelarActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 120, -1));

        jbtnReporteIndividual.setText("Reporte Individual");
        jbtnReporteIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReporteIndividualActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnReporteIndividual, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        jbtnReporte.setText("Reporte");
        jbtnReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReporteActionPerformed(evt);
            }
        });
        jPanel2.add(jbtnReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 250, 120, -1));

        jLabel9.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel9.setText("Selecione");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jButton1.setText("Reporte Grafico");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, -1, -1));

        jButton2.setText("Reporte Maestro");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 200, 410));

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jtblEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jtblEstudiantes);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 690, 160));

        jtxtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtxtBuscarActionPerformed(evt);
            }
        });
        jtxtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtBuscarKeyReleased(evt);
            }
        });
        jPanel3.add(jtxtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 133, -1));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        jLabel7.setText("Buscar");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, 710, 220));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        guardarEstudiante();
        cargarTabla("");
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        editarEstudiante();
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnBorrarActionPerformed
        borrarEstudiante();
    }//GEN-LAST:event_jbtnBorrarActionPerformed

    private void jtxtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtBuscarKeyReleased
        cargarTabla(jtxtBuscar.getText());
    }//GEN-LAST:event_jtxtBuscarKeyReleased

    private void jtxtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtxtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtxtBuscarActionPerformed

    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        borrarDatos();
        cargarTabla("");
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    private void jtxtTelefonoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtTelefonoKeyPressed
    }//GEN-LAST:event_jtxtTelefonoKeyPressed

    private void jbtnReporteIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReporteIndividualActionPerformed
    }//GEN-LAST:event_jbtnReporteIndividualActionPerformed

    private void jbtnReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReporteActionPerformed
    }//GEN-LAST:event_jbtnReporteActionPerformed

    private void jtxtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtDireccionKeyTyped

        char carc = evt.getKeyChar();
        if (!Character.isDigit(carc) && !Character.isLetter(carc) && carc != ' ') {
            evt.consume();
        }
    }//GEN-LAST:event_jtxtDireccionKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Estudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtnBorrar;
    private javax.swing.JButton jbtnCancelar;
    private javax.swing.JButton jbtnEditar;
    private javax.swing.JButton jbtnGuardar;
    private javax.swing.JButton jbtnNuevo;
    private javax.swing.JButton jbtnReporte;
    private javax.swing.JButton jbtnReporteIndividual;
    private javax.swing.JComboBox<String> jcmbCursos;
    private javax.swing.JTable jtblEstudiantes;
    private ComponentesPropios.jtxtFieldLetras jtxtApellido;
    private javax.swing.JTextField jtxtBuscar;
    private ComponentesPropios.jtxtFieldNumeros jtxtCedula;
    private javax.swing.JTextField jtxtDireccion;
    private ComponentesPropios.jtxtFieldLetras jtxtNombre;
    private javax.swing.JFormattedTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
