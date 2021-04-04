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
public class Queen extends Piece {

    public Queen(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wQueen") || name.equals("wQueen")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteQueen.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackQueen.png")));
        }
    }

    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        squaresCanMove.clear();
        attackablePieces.clear();
        boolean isEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        //To the top right corner
        for (int i = 1; i <= 7; i++) {

            if (column < (getSquare() - 8 * i + i) % 8 && getSquare() - 8 * i + i >= 0) {

                isEmpty = CheckAllPieces(allPieces, getSquare() - 8 * i + i, isEmpty);

                if (!isEmpty) {
                    isEmpty = true;
                    break;
                }

                squaresCanMove.add(getSquare() - 8 * i + i);
            } else {
                break;
            }
        }

        //To the top left corner
        for (int i = 1; i <= 7; i++) {

            if (column > (getSquare() - 8 * i - i) % 8 && getSquare() - 8 * i - i >= 0) {

                isEmpty = CheckAllPieces(allPieces, getSquare() - 8 * i - i, isEmpty);

                if (!isEmpty) {
                    isEmpty = true;
                    break;
                }

                squaresCanMove.add(getSquare() - 8 * i - i);
            } else {
                break;
            }
        }

        //To the bottom left corner
        for (int i = 1; i <= 7; i++) {

            if (column > (getSquare() + 8 * i - i) % 8 && getSquare() + 8 * i - i <= 63) {

                isEmpty = CheckAllPieces(allPieces, getSquare() + 8 * i - i, isEmpty);

                if (!isEmpty) {
                    isEmpty = true;
                    break;
                }

                squaresCanMove.add(getSquare() + 8 * i - i);
            } else {
                break;
            }
        }
        //To the bottom right corner
        for (int i = 1; i <= 7; i++) {

            if (column < (getSquare() + 8 * i + i) % 8 && getSquare() + 8 * i + i <= 63) {

                isEmpty = CheckAllPieces(allPieces, getSquare() + 8 * i + i, isEmpty);

                if (!isEmpty) {
                    isEmpty = true;
                    break;
                }

                squaresCanMove.add(getSquare() + 8 * i + i);
            } else {
                break;
            }
        }

        // To the right
        for (int i = column + 1; i < 8; i++) { // We get all squares in the right part.

            isEmpty = CheckAllPieces(allPieces, row * 8 + i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(row * 8 + i);
        }

        // To the left
        for (int i = column - 1; i >= 0; i--) { // We get all squares in the left part.

            isEmpty = CheckAllPieces(allPieces, row * 8 + i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(row * 8 + i);
        }

        //To The Bottom
        for (int i = row + 1; i < 8; i++) { // We get all squares in the particular row.

            isEmpty = CheckAllPieces(allPieces, column + 8 * i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(column + 8 * i);
        }

        //To The Top
        for (int i = row - 1; i >= 0; i--) { // We get all squares in the particular row.

            isEmpty = CheckAllPieces(allPieces, column + 8 * i, isEmpty);

            if (!isEmpty) {
                break;
            }
            squaresCanMove.add(column + 8 * i);
        }
        System.out.println(squaresCanMove.size());
    }
}
