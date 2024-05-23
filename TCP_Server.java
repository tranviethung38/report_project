/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TCP_Whiteboard;

/**
 *
 * @author ASUS
 */
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class TCP_Server {

    private static final int PORT = 5000;
    private static ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server is running and listening on port " + PORT);
                       
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                ObjectOutputStream clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStreams.add(clientOutputStream);

                Thread clientHandlerThread = new Thread(() -> handleClient(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            ObjectInputStream clientInputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                Object drawingObject = clientInputStream.readObject();

                // Broadcast the drawingObject to all connected clients
                for (ObjectOutputStream outputStream : outputStreams) {
                    outputStream.writeObject(drawingObject);
                    outputStream.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
