/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author X550V
 */
public class King extends Piece {

    public King(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wKing")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteKing.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackKing.png")));
        }
    }

    @Override
    public void SquaresCanGo() {
        
    }

}
