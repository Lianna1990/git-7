package javacore.jb2.Lesson5.lesson6;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Socket socket;

    public static void main(String[] args) {
        try (ServerSocket ignored = new ServerSocket (7777)) {
            System.out.println ("Server started!");
            ServerSocket server = null;


            //start
            try {
                server = new ServerSocket (7777);
                System.out.println ("Server created. Wait for client,please...");
                //client connection monitoring
                while (true) {
                    socket = server.accept ();
                    System.out.println ("Client connected");
                    new Thread ((Runnable) new EchoServer (socket)).start ();
                }
            } catch (IOException e) {
                e.printStackTrace ();
            } finally {
                try {
                    socket.close ();
                    server.close ();
                    System.out.println ("Server closed");
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        } catch (IOException e) {
            e.printStackTrace ();
        }
    }
}
