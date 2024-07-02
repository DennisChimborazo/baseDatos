/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repaso;

import java.awt.Color;
import javax.swing.JTextField;

/**
 *
 * @author Dalex
 */
public class uctTextoNumeros extends JTextField {

    public uctTextoNumeros() {
       initComponents();
    }

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {
        char caracter = evt.getKeyChar();
        if (caracter < '0' || caracter > '9') {
            evt.consume();
        }

    }

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {
        if (this.getText().isEmpty()) {
            this.setBackground(Color.RED);

        } else {
            this.setBackground(Color.WHITE);

        }

    }

    public Integer getTextAsInteger() {
        Integer retornar = 0;
        if (this.getText() == null || this.getText().isEmpty()) {
            return retornar;
        } else {
            retornar = Integer.valueOf(this.getText());
        }
        return retornar;
    }

    public void setTextAsInteger(Integer num) {
        this.setText(String.valueOf(num));
    }
     private void initComponents() {
          this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });
     }

}
