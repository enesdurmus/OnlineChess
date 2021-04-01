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
public class Pawn extends Piece {

    private boolean hasMoved = false;

    public Pawn(int square, JLayeredPane board, String name) {
        super(square, board, name);

        if (name.equals("wPawn1") || name.equals("wPawn2") || name.equals("wPawn3") || name.equals("wPawn4")
                || name.equals("wPawn5") || name.equals("wPawn6") || name.equals("wPawn7") || name.equals("wPawn8")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhitePawn.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackPawn.png")));
        }
    }

    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        squaresCanMove.clear();
        attackablePieces.clear();
        boolean isEmpty = true;
        boolean isSecondSquareEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        if (hasMoved) {
            switch (column) {
                case 0:
                    for (Piece p : allPieces) {   // We check square if there is any piece.
                        if (p.getSquare() == getSquare() - 7) {
                            if (p.getName().charAt(0) != getName().charAt(0)) {
                                attackablePieces.add(p);
                            }
                        } else if (p.getSquare() == getSquare() - 8) {
                            isEmpty = false;
                        }
                    }
                    break;
                case 7:
                    for (Piece p : allPieces) {   // We check square if there is any piece.
                        if (p.getSquare() == getSquare() - 9) {
                            if (p.getName().charAt(0) != getName().charAt(0)) {
                                attackablePieces.add(p);
                            }
                        } else if (p.getSquare() == getSquare() - 8) {
                            isEmpty = false;
                        }
                    }
                    break;
                default:
                    for (Piece p : allPieces) {   // We check square if there is any piece.
                        if (p.getSquare() == getSquare() - 7 || p.getSquare() == getSquare() - 9) {
                            if (p.getName().charAt(0) != getName().charAt(0)) {
                                attackablePieces.add(p);
                            }
                        } else if (p.getSquare() == getSquare() - 8) {
                            isEmpty = false;
                        }
                    }
                    break;
            }
        } else {
            switch (column) {
                case 0:
                    for (Piece p : allPieces) {   // We check square if there is any piece.
                        if (p.getSquare() == getSquare() - 7) {
                            if (p.getName().charAt(0) != getName().charAt(0)) {
                                attackablePieces.add(p);
                            }
                        } else if (p.getSquare() == getSquare() - 8) {
                            isEmpty = false;
                        } else if (p.getSquare() == getSquare() - 16) {
                            isSecondSquareEmpty = false;
                        }
                    }
                    break;
                case 7:
                    for (Piece p : allPieces) {   // We check square if there is any piece.
                        if (p.getSquare() == getSquare() - 9) {
                            if (p.getName().charAt(0) != getName().charAt(0)) {
                                attackablePieces.add(p);
                            }
                        } else if (p.getSquare() == getSquare() - 8) {
                            isEmpty = false;
                        } else if (p.getSquare() == getSquare() - 16) {
                            isSecondSquareEmpty = false;
                        }
                    }
                    break;
                default:
                    for (Piece p : allPieces) {   // We check square if there is any piece.
                        if (p.getSquare() == getSquare() - 7 || p.getSquare() == getSquare() - 9) {
                            if (p.getName().charAt(0) != getName().charAt(0)) {
                                attackablePieces.add(p);
                            }
                        } else if (p.getSquare() == getSquare() - 8) {
                            isEmpty = false;
                        }
                    }
                    break;
            }

        }

        if (isEmpty) {
            if (!hasMoved) {
                squaresCanMove.add(getSquare() - 8);
                if (isSecondSquareEmpty) {
                    squaresCanMove.add(getSquare() - 16);
                }
            } else {
                squaresCanMove.add(getSquare() - 8);
            }
        }
    }

    @Override
    public void Move(int square) {
        super.Move(square); //To change body of generated methods, choose Tools | Templates.
        hasMoved = true;
    }

}
