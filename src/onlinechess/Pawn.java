/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

/**
 *
 * @author X550V
 */
public class Pawn extends Piece {

    public Pawn(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wPawn1") || name.equals("wPawn2") || name.equals("wPawn3") || name.equals("wPawn4")
                || name.equals("wPawn5") || name.equals("wPawn6") || name.equals("wPawn7") || name.equals("wPawn8")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhitePawn.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackPawn.png")));
        }
    }
}
