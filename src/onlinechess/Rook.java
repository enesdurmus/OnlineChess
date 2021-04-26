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
public class Rook extends Piece {

    public Rook(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.startsWith("w")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteRook.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackRook.png")));
        }
    }

    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        this.getSquaresCanMove().clear();
        this.getAttackablePieces().clear();
        boolean isEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        // To the right
        for (int i = column + 1; i < 8; i++) { // We get all squares in the right part.

            isEmpty = CheckAllPieces(allPieces, row * 8 + i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            this.getSquaresCanMove().add(row * 8 + i);
        }

        // To the left
        for (int i = column - 1; i >= 0; i--) { // We get all squares in the left part.

            isEmpty = CheckAllPieces(allPieces, row * 8 + i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            this.getSquaresCanMove().add(row * 8 + i);
        }

        //To The Bottom
        for (int i = row + 1; i < 8; i++) { // We get all squares in the particular row.

            isEmpty = CheckAllPieces(allPieces, column + 8 * i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            this.getSquaresCanMove().add(column + 8 * i);
        }

        //To The Top
        for (int i = row - 1; i >= 0; i--) { // We get all squares in the particular row.

            isEmpty = CheckAllPieces(allPieces, column + 8 * i, isEmpty);

            if (!isEmpty) {
                break;
            }
            this.getSquaresCanMove().add(column + 8 * i);
        }
    }
}
