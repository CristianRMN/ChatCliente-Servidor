package service.metodosclases;

import javax.swing.*;

/**
 * Clase que contiene métodos globales de utilidad para la aplicación.
 * @author cristian
 * @version 1.0
 */
public class MetodosGlobales {


    /**
     * Solicita al usuario un número de puerto válido para conectar el servidor de WhatHappen.
     * El puerto debe estar dentro del rango de 1024 a 65535.
     *
     * @return el número de puerto introducido por el usuario.
     */
    public int solicitarPuerto() {
        int puerto = 0;
        boolean puertoValido = false;
        while (!puertoValido) {
            try {
                puerto = Integer.parseInt(JOptionPane.showInputDialog("Introduce el puerto en el que conectar el servidor de WhatHappen: "));
                if (checkPuertoIsDigit(puerto) && checkLimitPuerto(puerto)) {
                    puertoValido = true;
                } else {
                    JOptionPane.showMessageDialog(null, "El puerto debe estar entre 1024 y 65535. Prueba otra vez: ");
                }
            }catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "el puerto introducido no es un número, prueba otra vez");
            }
        }
        return puerto;
    }

    /**
     * Verifica si un número es completamente numérico.
     *
     * @param numero el número a verificar.
     * @return true si el número solo contiene dígitos, false en caso contrario.
     */
    public boolean checkPuertoIsDigit(int numero) {
        String puertoString = String.valueOf(numero);
        for (char c : puertoString.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Comprueba si el puerto está dentro del rango permitido (1024-65535).
     *
     * @param puerto el número de puerto a verificar.
     * @return true si el puerto está en el rango válido, false en caso contrario.
     */
    public boolean checkLimitPuerto(int puerto) {
        return puerto >= 1024 && puerto <= 65535;
    }
}
