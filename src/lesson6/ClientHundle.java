package lesson6;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHundle implements Runnable {
    public static void main(String[] args) {

    }
    private Socket socket;
    private PrintWriter out;
    private Scanner in;

    private String name;


    public ClientHundle(Socket socket) {
        try {
            this.socket = socket;
            out = new PrintWriter(socket.getOutputStream());
            in = new Scanner(socket.getInputStream());
            //clientsCount++;

            name = "Client";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            if (in.hasNext()) {
                String w = in.nextLine();
                System.out.println(name + ": " + w);
                out.println("echo: " + w);
                out.flush();
                if (w.equalsIgnoreCase("the end")) break;
            }
        }
        try{
            System.out.println("Client disconnected");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
