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
public class Knight extends Piece {

    public Knight(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wKnight1") || name.equals("wKnight2")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteKnight.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackKnight.png")));
        }
    }

    /*
    ********
    ***-*-**
    ********
    ****-***
    ********
    ***-*-**
     */
    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        squaresCanMove.clear();
        attackablePieces.clear();
        boolean isEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.
        int x = 2;
        //For the top
        for (int i = 1; i <= 2; i++) {
            //for the left
            if ((getSquare() - 8 * i - x) % 8 < column && getSquare() + 8 * i - x >= 0 && CheckAllPieces(allPieces, getSquare() - 8 * i - x, isEmpty)) {
                squaresCanMove.add(getSquare() - 8 * i - x);
            }
            //for the right
            if ((getSquare() - 8 * i + x) % 8 > column && getSquare() + 8 * i - x >= 0 && CheckAllPieces(allPieces, getSquare() - 8 * i + x, isEmpty)) {
                squaresCanMove.add(getSquare() - 8 * i + x);
            }
            x--;
        }
        x = 2;
        //For the bottom
        for (int i = 1; i <= 2; i++) {
            //for the left
            if ((getSquare() + 8 * i - x) % 8 < column && getSquare() + 8 * i - x <= 63 && CheckAllPieces(allPieces, getSquare() + 8 * i - x, isEmpty)) {
                squaresCanMove.add(getSquare() + 8 * i - x);
            }
            //for the right
            if ((getSquare() + 8 * i + x) % 8 > column && getSquare() + 8 * i + x <= 63 && CheckAllPieces(allPieces, getSquare() + 8 * i + x, isEmpty)) {
                squaresCanMove.add(getSquare() + 8 * i + x);
            }
            x--;
        }
        System.out.println(squaresCanMove.size());
    }
}
