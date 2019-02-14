package javacore.jb2.lessons.HomeWork6;



import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;


public class WebServer {
    // порт
    static final int PORT = 7777;
   

    public WebServer() {

        Socket clientSocket = null;
        // серверный сокет
        ServerSocket serverSocket = null;
        try {
            // создаём серверный сокет на определенном порту
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            // запускаем бесконечный цикл
            while (true) {
                // ждём подключений от сервера
                clientSocket = serverSocket.accept();
                // создаём обработчик клиента, который подключился к серверу

                Client client = new Client(clientSocket, this);
                
                // каждое подключение клиента обрабатываем в новом потоке
                new Thread(client).start();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                // закрываем подключение
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    
        




    public void sendMessageToClient(String clientMessage) {
    }


    public void removeClient(Client client) {
    }
}




