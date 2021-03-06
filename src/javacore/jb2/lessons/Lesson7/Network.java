package javacore.jb2.lessons.Lesson7;

import com.sun.xml.internal.ws.api.message.Message;

import javax.swing.*;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Network implements Closeable {

    private static final String AUTH_PATTERN = "/auth %s %s";
    private static final String MESSAGE_PATTERN = "/w %s %s";

    private final Socket socket;
    private final DataOutputStream out;
    private final DataInputStream in;
    private final MessageSender messageSender;
    private final Thread receiver;
    private String username;
    public Network(String hostName, int port, MessageSender messageSender) throws IOException {
        this.socket = new Socket(hostName, port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        this.messageSender = messageSender;

        this.receiver = new Thread(new Runnable() {
        this.receiver =

            createReceiverThread();
        }
    }

        private Thread createReceiverThread() {
            return new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            String msg = in.readUTF();
                            String text = in.readUTF();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    System.out.println("New message " + msg);
                                    messageSender.submitMessage("server", msg);
                                }
                            });
                            @Override
                            public void run() {
                                System.out.println("New message " + text);
                                Message msg = new Message("server", username,  text);
                                messageSender.submitMessage(msg);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendMessageToUser(Message message) {
        // TODO здесь нужно сформировать личное сообщение в понятном для сервера формате
        sendMessage(message.getText());

        System.out.println("Введите сообщение:");
        Scanner scan = null;
        System.out.println(scan.nextLine());
        String str = "";
        while (!str.equals("exit")) {
            str = scan.nextLine();
            System.out.println(str);
        }
    }

    public void sendMessage(String msg) {
        private void sendMessage(String msg) {
            try {
                out.writeUTF(msg);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void authorize(String username, String password) {
            try {
                out.writeUTF(String.format(AUTH_PATTERN, username, password));
                String response = in.readUTF();
                if (response.equals("/auth successful")) {
                    this.username = username;
                    receiver.start();
                } else {
                    throw new AuthException("");
                    throw new AuthException();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public String getUsername() {
            return username;
        }
        @Override
        public void close() throws IOException {
            socket.close();
            receiver.interrupt();
            try {
                receiver.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }