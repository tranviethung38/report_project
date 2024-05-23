/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP_Whiteboard;

/**
 *
 * @author ASUS
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class TCP_Client {

    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drawing Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(700, 400);
            
            URL iconPaint = TCP_Client.class.getResource("vodien.png");
            Image img = Toolkit.getDefaultToolkit().createImage(iconPaint);
            frame.setIconImage(img);
            
            DrawingCanvas drawingCanvas = new DrawingCanvas();
            frame.getContentPane().add(drawingCanvas, BorderLayout.CENTER);

            try {
                Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());

                drawingCanvas.setOutputStream(outputStream);

                Thread drawingThread = new Thread(() -> {
                    try {
                        while (true) {
                            Object receivedObject = inputStream.readObject();

                            if (receivedObject instanceof DrawingObject) {
                                DrawingObject drawingObject = (DrawingObject) receivedObject;
                                drawingCanvas.setFreehandLines(drawingObject.getFreehandLines());
                                drawingCanvas.setShapes(drawingObject.getShapes());
                                drawingCanvas.repaint();
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                drawingThread.start();

                frame.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

