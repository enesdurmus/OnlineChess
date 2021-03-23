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
public class Rook extends Piece {

    public Rook(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wRook1") || name.equals("wRook2")) {
            labelPiece.setIcon(new ImageIcon(getClass().getResource("/Images/WhiteRook.png")));
        } else {
            labelPiece.setIcon(new ImageIcon(getClass().getResource("/Images/BlackRook.png")));
        }
    }
}
