/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

/**
 *
 * @author X550V
 */
public class Bishop extends Piece {

    public Bishop(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wBishop1") || name.equals("wBishop2")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteBishop.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackBishop.png")));
        }
    }

    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
  /*      squaresCanMove.clear();
        attackablePieces.clear();
        boolean isEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        
        //To the top right corner
        for (int i = 0; i < 10; i++) {
            
        }*/
        
    }

}
