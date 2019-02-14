package javacore.jb2.lessons.HomeWork6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

// реализуем интерфейс Runnable
public class Client implements Runnable {
    private WebServer server;
    // исходящее сообщение
    private PrintWriter outMessage;
    // входящее собщение
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 7777;
    // клиентский сокет
    private Socket clientSocket = null;



    public Client(Socket socket, WebServer server) {
        try {

            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter (socket.getOutputStream ());
            this.inMessage = new Scanner (socket.getInputStream ());
        } catch (IOException ex) {
            ex.printStackTrace ();
        }
    }

    // Переопределяем метод run(), который вызывается когда
    // мы вызываем new Thread(client).start();
    @Override
    public void run() {
        try {
            while (true) {
                // сервер отправляет сообщение
                server.sendMessageToClient ("Новый участник вошел в чат!");

                break;
            }

            while (true) {
                // Если от клиента пришло сообщение
                if (inMessage.hasNext ()) {
                    String clientMessage = inMessage.nextLine ();
                    // если клиент отправляет данное сообщение, то цикл прерывается и
                    // клиент выходит из чата
                    if (clientMessage.equalsIgnoreCase ("##session##end##")) {
                        break;
                    }
                    // выводим в консоль сообщение
                    System.out.println (clientMessage);

                }
            }

        } finally {
            this.close ();
        }
    }

    // отправляем сообщение
    public void sendMsg(String msg) {
        try {
            outMessage.println (msg);
            outMessage.flush ();
        } catch (Exception ex) {
            ex.printStackTrace ();
        }
    }

    // клиент выходит из чата
    public void close() {
        // удаляем клиента из списка
        server.removeClient (this);
    }


    public static void main(String[] args) {
        WebServer webServer = new WebServer ();
    }
}
