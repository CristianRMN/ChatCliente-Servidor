package ui;


import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Clase que representa la interfaz de usuario de la aplicación WhatHappen.
 * Implementa un patrón Singleton para asegurar una única instancia.
 *
 * @author cristian
 * @version 1.0
 */
public class SalaUI {

    /**
     * Instancia única de la clase SalaUI.
     */
    private static SalaUI instance;

    /**
     * Documento estilizado para mostrar mensajes en la interfaz.
     */
    private final StyledDocument doc;

    /**
     * Etiqueta para mostrar el nombre del usuario.
     */
    private final JLabel label;

    /**
     * Etiqueta para mostrar la imagen del usuario.
     */

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa la interfaz gráfica de usuario.
     */
    private SalaUI() {

        //iniciamos el frame
        JFrame frame = new JFrame("WhatHappen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());


        //iniciamos el textpane
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(new Color(0, 29, 0));
        textPane.setForeground(Color.WHITE);
        textPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        textPane.setBackground(new Color(0, 0, 100));
        textPane.setMargin(new Insets(5, 5, 5, 5));

        //establecemos un estilo al doc
        doc = textPane.getStyledDocument();

        Style estiloNick = doc.addStyle("nick", null);
        StyleConstants.setForeground(estiloNick, Color.YELLOW);
        StyleConstants.setBold(estiloNick, true);

        Style estiloMensaje = doc.addStyle("mensaje", null);
        StyleConstants.setForeground(estiloMensaje, Color.WHITE);


        //hacemos el panel horizonal
        JPanel horizontalPanel = new JPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));

        //creamos el label para el nombre de usuario
        label = new JLabel();
        label.setForeground(Color.cyan);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 20));



        //lo añadimos al panel horizontal
        horizontalPanel.add(label);

        //hacemos un scroll para el area de texto
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(horizontalPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setResizable(false);
        frame.setVisible(true);
    }



    /**
     * Metodo para obtener la instancia única de la clase SalaUI.
     *
     * @return instancia única de SalaUI
     */
    public static SalaUI getInstance() {
        if (instance == null) {
            instance = new SalaUI();
        }
        return instance;
    }

    /**
     * Establece el nombre del usuario en la interfaz gráfica.
     *
     * @param nickName nombre del usuario
     */
    public void establecerLabel(String nickName){
        SwingUtilities.invokeLater(() -> label.setText(nickName));
    }

    /**
     * Agrega un mensaje al área de texto de la interfaz.
     *
     * @param mensaje mensaje a agregar
     */
    public void agregarMensaje(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            try {
                if (mensaje.contains(":")) {
                    String nickName = mensaje.split(":", 2)[0] + ": ";
                    String contenido = mensaje.split(":", 2)[1];

                    doc.insertString(doc.getLength(), nickName, doc.getStyle("nick"));
                    doc.insertString(doc.getLength(), contenido + "\n", doc.getStyle("mensaje"));
                } else {
                    doc.insertString(doc.getLength(), mensaje + "\n", doc.getStyle("mensaje"));
                }
            } catch (BadLocationException e) {
                System.out.println("Ups, se fue a la mierda");
            }
        });
    }


}
