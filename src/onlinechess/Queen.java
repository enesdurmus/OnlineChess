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
public class Queen extends Piece {

    public Queen(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wQueen") || name.equals("wQueen")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteQueen.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackQueen.png")));
        }
    }

}
