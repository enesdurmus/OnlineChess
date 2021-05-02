/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chessserver;

import Message.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author X550V
 */
public class Server {

    public static ServerSocket serverSocket;
    public static int IdClient = 0;
    public static int port = 0;
    public static NewClientListener runThread;
    public static ArrayList<Client> Clients = new ArrayList<>();

    public static void Start(int openport) {
        try {
            Server.port = openport;
            Server.serverSocket = new ServerSocket(Server.port);

            Server.runThread = new NewClientListener();
            Server.runThread.start();

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void Send(Client cl, Message msg) {

        try {
            cl.sOutput.writeObject(msg);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<String> ReturnRooms() {
        ArrayList<String> rooms = new ArrayList<>();

        Clients.stream().filter((c) -> (!c.roomName.equals("Null"))).forEachOrdered((c) -> {
            if (c.opponent == null) {
                System.out.println(c.opponent);
                rooms.add(c.roomName);
            }
        });
        return rooms;
    }

    public static Client JoinRoom(String roomName) {

        for (Client c : Clients) {
            if (c.roomName.equals(roomName)) {
                return c;
            }
        }
        return null;
    }
}
