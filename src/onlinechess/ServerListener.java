/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import Message.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static onlinechess.Client.sInput;

/**
 *
 * @author X550V
 */
public class ServerListener extends Thread {

    @Override
    public void run() {
        //soket bağlı olduğu sürece dön
        while (Client.socket.isConnected()) {
            try {
                //mesaj gelmesini bloking olarak dinyelen komut
                Message received = (Message) (sInput.readObject());
                // print out the text of every message
                switch (received.type) {

                    case ReturnRoomsNames:
                        Client.rooms = (ArrayList<String>) received.content;
                        Client.rooms.forEach((r) -> {
                            Main.Game.lm1.addElement(r);
                        });
                        Main.Game.RefreshRooms();
                        System.out.println("Reached rooms list from server...");

                        break;
                    case JoinRoom:
                        if (Main.Game.isRoomOwner) {
                            Board.Game.OpponentJoinedTheRoom(received.content.toString());
                            System.out.println("User " + received.content.toString() + " has joined the room...");
                        } else {
                            Main.Game.JoinRoom((ArrayList<String>) received.content);
                            System.out.println("You have joined the room");
                        }
                        Board.Game.StartTimer();
                        break;
                    case MovePiece:
                        ArrayList readMoveInf = (ArrayList) received.content;
                        Board.Game.ReadMoveInfFromServer(readMoveInf);
                        Board.Game.isOurTurn = true;
                        Board.Game.ToggleTimer();
                        System.out.println("Opponent has made a move " + readMoveInf.get(0) + " is moving to " + (63 - (int) readMoveInf.get(1)));
                        break;
                    case Attack:
                        ArrayList readAttackInf = (ArrayList) received.content;
                        Board.Game.ReadAttackInfFromServer(readAttackInf);
                        Board.Game.isOurTurn = true;
                        Board.Game.ToggleTimer();
                        System.out.println("Opponent piece " + readAttackInf.get(0) + " is attacking to " + readAttackInf.get(1));
                        break;
                }

            } catch (IOException | ClassNotFoundException ex) {

                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                //Client.Stop();
                break;
            }
            //Client.Stop();

        }

    }
}
