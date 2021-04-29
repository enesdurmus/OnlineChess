/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author X550V
 */
public class Pawn extends Piece {

    private boolean hasMoved = false;
    private ArrayList<JButton> upgradeButtons;

    public Pawn(int square, JLayeredPane board, String name, ArrayList<JButton> upgradeButtons) {
        super(square, board, name);

        if (name.startsWith("w")) {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/WhitePawn.png")));
        } else {
            getLabelPiece().setIcon(new ImageIcon(getClass().getResource("/Images/BlackPawn.png")));
        }

        this.upgradeButtons = upgradeButtons;
    }

    @Override
    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
        this.getSquaresCanMove().clear();
        this.getAttackablePieces().clear();
        boolean isEmpty = true;
        boolean isSecondSquareEmpty = true;

        int row = getSquare() / 8, column = getSquare() % 8; // We get our row and colums so we can ignore squares that we cant go.

        if (hasMoved) {
            switch (column) {
                case 0:
                    isEmpty = ChechAllPiecesHasMoved(allPieces, isEmpty, 7);
                    break;
                case 7:
                    isEmpty = ChechAllPiecesHasMoved(allPieces, isEmpty, 9);
                    break;
                default:
                    isEmpty = ChechAllPiecesHasMoved(allPieces, isEmpty, 7, 9);
                    break;
            }

        } else {
            switch (column) {
                case 0:
                    boolean a[] = ChechAllPiecesHasntMoved(allPieces, isEmpty, isSecondSquareEmpty, 7);
                    isEmpty = a[0];
                    isSecondSquareEmpty = a[1];
                    break;
                case 7:
                    a = ChechAllPiecesHasntMoved(allPieces, isEmpty, isSecondSquareEmpty, 9);
                    isEmpty = a[0];
                    isSecondSquareEmpty = a[1];
                default:
                    a = ChechAllPiecesHasntMoved(allPieces, isEmpty, isSecondSquareEmpty, 7, 9);
                    isEmpty = a[0];
                    isSecondSquareEmpty = a[1];
                    break;
            }
        }

        if (isEmpty) {
            if (!hasMoved) {
                this.getSquaresCanMove().add(getSquare() - 8);
                if (isSecondSquareEmpty) {
                    this.getSquaresCanMove().add(getSquare() - 16);
                }
            } else {
                this.getSquaresCanMove().add(getSquare() - 8);
            }
        }
    }

    @Override
    public boolean Move(int square) {
        super.Move(square); //To change body of generated methods, choose Tools | Templates.
        hasMoved = true;
        if (square / 8 == 0) {
            OpenUpgradePanel();
        }
        return true;
    }

    @Override
    public boolean Attack(String name) {
        Piece p = null;
        for (Piece a : this.getAttackablePieces()) {
            if (a.getName().equals(name)) {
                p = a;
                break;
            }
        }

        if (p != null) {
            p.getLabelPiece().getParent().remove(p.getLabelPiece());
            setSquare(p.getSquare());
            JPanel panel = (JPanel) getBoard().getComponent(p.getSquare());
            panel.add(getLabelPiece());
            ClearGreenDots();
            ClearGreenFrames();
            if (p.getSquare() / 8 == 0) {
                OpenUpgradePanel();
            }
            return true;
        }
        return false;
    }

    private void OpenUpgradePanel() {
        upgradeButtons.forEach((upgradeButton) -> {
            upgradeButton.setVisible(true);
        });
    }

    private void CloseUpgradePanel() {
        upgradeButtons.forEach((upgradeButton) -> {
            upgradeButton.setVisible(false);
        });

    }

    public Piece UpgradePawn(String name, int upgradeCounter, String side) {
        this.getLabelPiece().getParent().remove(this.getLabelPiece());
        Piece upgradedPawn = null;
        switch (name) {
            case "knight":
                upgradedPawn = new Knight(getSquare(), getBoard(), side.charAt(0) + "newKnight" + upgradeCounter);
                break;
            case "bishop":
                upgradedPawn = new Bishop(getSquare(), getBoard(), side.charAt(0) + "newBishop" + upgradeCounter);
                break;
            case "rook":
                upgradedPawn = new Rook(getSquare(), getBoard(), side.charAt(0) + "newRook" + upgradeCounter);
                break;
            case "queen":
                upgradedPawn = new Queen(getSquare(), getBoard(), side.charAt(0) + "newQueen" + upgradeCounter);
                break;
        }
        CloseUpgradePanel();
        return upgradedPawn;
    }

    public boolean ChechAllPiecesHasMoved(ArrayList<Piece> allPieces, boolean isEmpty, int n) {
        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == getSquare() - n) {
                if (p.getName().charAt(0) != getName().charAt(0)) {
                    this.getAttackablePieces().add(p);
                }
            } else if (p.getSquare() == getSquare() - 8) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public boolean ChechAllPiecesHasMoved(ArrayList<Piece> allPieces, boolean isEmpty, int n, int n2) {
        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == getSquare() - n || p.getSquare() == getSquare() - n2) {
                if (p.getName().charAt(0) != getName().charAt(0)) {
                    this.getAttackablePieces().add(p);
                }
            } else if (p.getSquare() == getSquare() - 8) {
                isEmpty = false;
            }
        }
        return isEmpty;
    }

    public boolean[] ChechAllPiecesHasntMoved(ArrayList<Piece> allPieces, boolean isEmpty, boolean isSecondSquareEmpty, int n) {
        boolean a[] = new boolean[2];

        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == getSquare() - n) {
                if (p.getName().charAt(0) != getName().charAt(0)) {
                    this.getAttackablePieces().add(p);
                }
            } else if (p.getSquare() == getSquare() - 8) {
                isEmpty = false;
            } else if (p.getSquare() == getSquare() - 16) {
                isSecondSquareEmpty = false;
            }
        }
        a[0] = isEmpty;
        a[1] = isSecondSquareEmpty;

        return a;
    }

    public boolean[] ChechAllPiecesHasntMoved(ArrayList<Piece> allPieces, boolean isEmpty, boolean isSecondSquareEmpty, int n, int n2) {
        boolean a[] = new boolean[2];

        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == getSquare() - n || p.getSquare() == getSquare() - n2) {
                if (p.getName().charAt(0) != getName().charAt(0)) {
                    this.getAttackablePieces().add(p);
                }
            } else if (p.getSquare() == getSquare() - 8) {
                isEmpty = false;
            } else if (p.getSquare() == getSquare() - 16) {
                isSecondSquareEmpty = false;
            }
        }
        a[0] = isEmpty;
        a[1] = isSecondSquareEmpty;

        return a;
    }
}
