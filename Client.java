/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author Admin
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter your name to join Bayan's chat: ");
            String name = sc.nextLine();
            System.out.println("Connected! put your messages below:");

            //recive masages and print them
            new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println("\n" + serverMsg);
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            //masages loop
            while (true) {
                String myMsg = sc.nextLine();
                out.println(name + ": " + myMsg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}