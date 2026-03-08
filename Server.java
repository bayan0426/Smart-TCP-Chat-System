/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package server;

/**
 *
 * @author Admin
 */

//import 
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 8888;
    private static Set<PrintWriter> allClients = new HashSet<>();
    private static final List<String> BAdWORDS = Arrays.asList("badword1", "badword2", "spam","بذيئة");

    public static void main(String[] args) {
        System.out.println("--- Smart Chat Server Started ---");
        //open the port and wait for the clints
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                //to freez the program wating for a clint to conect to creat a conction chanle for the clint
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                allClients.add(writer);
                //to open multi indepndent chanles for example aroom for bayan and room for sara
                new Thread(new ClientHandler(socket, writer)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // to avoid bad words masages
    private static String filterMessage(String originalMsg) {
        String cleanMsg = originalMsg;
        for (String word : BAdWORDS) {
            if (cleanMsg.toLowerCase().contains(word.toLowerCase())) {
                cleanMsg = cleanMsg.replaceAll("(?i)" + word, "****");
            }
        }
        return cleanMsg;
    }

    public static void broadcast(String message) {
        for (PrintWriter client : allClients) {
            client.println(message);
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter writer;
        
        //avoid spam masages
        private long lastMessageTime = 0;
        private int messageCount = 0;
        private static final int SPAM_THRESHOLD = 3; // num of the acacpted masages
        private static final long TIME_LIMIT = 5000; // time limtiation

        public ClientHandler(Socket socket, PrintWriter writer) {
            this.socket = socket;
            this.writer = writer;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                String msg;
                while ((msg = in.readLine()) != null) {
                    long currentTime = System.currentTimeMillis();
                    
                    // spam 
                    if (currentTime - lastMessageTime < TIME_LIMIT) {
                        messageCount++;
                    } else {
                        messageCount = 1; 
                        lastMessageTime = currentTime;
                    }

                    if (messageCount > SPAM_THRESHOLD) {
                        writer.println("[SYSTEM ALERT]: Stop spamming! Wait a few seconds.");
                        continue; // ignoring masages
                    }

                    //bad word filiter
                    String filtered = filterMessage(msg);
                    broadcast(filtered);
                }
            } catch (IOException e) {
                System.out.println("User disconnected.");
            } finally {
                allClients.remove(writer);
            }
        }
    }
}