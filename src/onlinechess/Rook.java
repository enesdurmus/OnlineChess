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

        if (name.equals("wRook1") || name.equals("wRook2")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhiteRook.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackRook.png")));
        }
    }

    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        squaresCanMove.clear();
        attackablePieces.clear();
        boolean isEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        // To the right
        for (int i = column + 1; i < 8; i++) { // We get all squares in the right part.

            isEmpty = CheckAllPiecesRow(allPieces, row, i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(row * 8 + i);
        }

        // To the left
        for (int i = column - 1; i >= 0; i--) { // We get all squares in the left part.

            isEmpty = CheckAllPiecesRow(allPieces, row, i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(row * 8 + i);
        }

        //To The Bottom
        for (int i = row + 1; i < 8; i++) { // We get all squares in the particular row.

            isEmpty = CheckAllPiecesColumn(allPieces, column, i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(column + 8 * i);
        }

        //To The Top
        for (int i = row - 1; i >= 0; i--) { // We get all squares in the particular row.

            isEmpty = CheckAllPiecesColumn(allPieces, column, i, isEmpty);

            if (!isEmpty) {
                isEmpty = true;
                break;
            }
            squaresCanMove.add(column + 8 * i);
        }
    }

    private boolean CheckAllPiecesRow(ArrayList<Piece> allPieces, int row, int i, boolean isEmpty) {
        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == row * 8 + i) {
                if (p.getName().charAt(0) != getName().charAt(0)) {
                    attackablePieces.add(p);
                    isEmpty = false;
                    break;
                } else if (p.getName().charAt(0) == getName().charAt(0)) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

    private boolean CheckAllPiecesColumn(ArrayList<Piece> allPieces, int column, int i, boolean isEmpty) {
        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == column + 8 * i) {
                if (p.getName().charAt(0) != getName().charAt(0)) {
                    attackablePieces.add(p);
                    isEmpty = false;
                    break;
                } else if (p.getName().charAt(0) == getName().charAt(0)) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }
}
