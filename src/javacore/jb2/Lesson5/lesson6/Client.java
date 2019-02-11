package javacore.jb2.Lesson5.lesson6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public final String SERVER_ADDRESS = "localhost";
    public final int SERVER_PORT = 7777;
    Socket socket;
    Scanner in, console;
    PrintWriter out;

    public Client() {

        try {
            socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        new Thread(() -> {
            try {
                while (true) {
                    if (in.hasNext()) {
                        String w = in.nextLine();
                        if (w.equalsIgnoreCase("end session")) break;
                        System.out.println(w);
                    }
                }
            } catch (Exception e) {
                System.out.println("Good bye, have a nice day!");
            }
        }).start();

        startMessaging();
    }

    //send messages
    private void startMessaging() {
        String message;
        console = new Scanner(System.in);
        System.out.println("Enter your message, please:");

        while (true) {
            message = console.next();
            out.println(message);
            out.flush();
            if (message.equalsIgnoreCase("the end")) {
                try {
                    console.close();
                    out.close();
                    in.close();
                    socket.close();
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}