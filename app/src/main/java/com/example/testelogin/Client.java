package com.example.testelogin;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    Socket client;
    ObjectInputStream input;
    String resposta;

    public void connect(String host, int port){

        try {
            client = new Socket(host, port);
            input = new ObjectInputStream(client.getInputStream());
            resposta = (String) input.readObject();
            input.close();
            System.out.println(resposta);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
