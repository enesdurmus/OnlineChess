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
public class King extends Piece {

    public King(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.startsWith("w")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteKing.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackKing.png")));
        }
    }

    /*
    
    * * *
    * s *
    * * *
    
     */
    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        squaresCanMove.clear();
        attackablePieces.clear();
        boolean isEmpty = true;
        int a = getSquare() - 9; // start from the left top corner of our king.
        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        for (int i = 0; i < 3; i++) {   // for rows
            for (int j = -1; j < 2; j++) {  // for cols

                if (column == 0 && j == - 1 || column == 7 && j == 1 || row == 0 && i == 0 || row == 7 && i == 2) { // We check our positions for impossible squares.
                    System.out.println("NoWhereToGo");
                } else {

                    isEmpty = CheckAllPieces(allPieces, a, isEmpty);

                    if (isEmpty) {   // if its empty we can move there
                        squaresCanMove.add(a);
                    }
                }
                a++;
                isEmpty = true;
            }
            a += 5;
        }
    }
}
