/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author X550V
 */
public class Piece {

    private JLabel labelPiece;
    private int square;
    private JLayeredPane board;
    private String name;
    private ArrayList<Integer> squaresCanMove;
    private ArrayList<Piece> attackablePieces;
    private ArrayList<JLabel> greenDots;
    private ArrayList<JLabel> greenFrames;

    public Piece(int square, JLayeredPane board, String name) {
        squaresCanMove = new ArrayList<>();
        attackablePieces = new ArrayList<>();
        greenDots = new ArrayList<>();
        greenFrames = new ArrayList<>();

        this.square = square;
        this.board = board;
        this.name = name;
        labelPiece = new JLabel();
        labelPiece.setName(name);
        JPanel panel = (JPanel) board.getComponent(square);
        panel.add(labelPiece);
    }

    public boolean Move(int square) {
        if (MoveControl(square)) {
            setSquare(square);
            JPanel panel = (JPanel) getBoard().getComponent(square);
            panel.add(getLabelPiece());
            ClearGreenDots();
            ClearGreenFrames();
            return true;
        }
        ClearGreenDots();
        ClearGreenFrames();
        return false;

    }

    public void MoveOpponent(int square) {
        setSquare(square);
        JPanel panel = (JPanel) getBoard().getComponent(square);
        panel.add(getLabelPiece());
    }

    public boolean Attack(String name) {
        Piece p = null;
        for (Piece a : attackablePieces) {
            if (a.getName().equals(name)) {
                p = a;
                break;
            }
        }

        if (p != null) {
            p.labelPiece.getParent().remove(p.labelPiece);
            setSquare(p.getSquare());
            JPanel panel = (JPanel) getBoard().getComponent(square);
            panel.add(getLabelPiece());
            ClearGreenDots();
            ClearGreenFrames();
            return true;
        }
        return false;
    }

    public void AttackForOpponent(String name, ArrayList<Piece> allPieces) {
        Piece p = null;
        for (Piece a : allPieces) {
            if (a.getName().equals(name)) {
                p = a;
                break;
            }
        }
        if (p != null) {
            p.labelPiece.getParent().remove(p.labelPiece);
            setSquare(p.getSquare());
            JPanel panel = (JPanel) getBoard().getComponent(square);
            panel.add(getLabelPiece());
            ClearGreenDots();
            ClearGreenFrames();
        }
    }

    protected boolean MoveControl(int s) {
        return squaresCanMove.stream().anyMatch((i) -> (i == s));
    }

    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
    }
    
    public boolean CheckAllPieces(ArrayList<Piece> allPieces, int square, boolean isEmpty) {
        for (Piece p : allPieces) {   // We check square if there is any piece.
            if (p.getSquare() == square) {
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

    public void DrawGreenDots() {
        squaresCanMove.forEach((s) -> {
            JPanel panel = (JPanel) getBoard().getComponent(s);
            JLabel greenDot = new JLabel();
            greenDot.setName(String.valueOf(s));
            greenDot.setIcon(new ImageIcon(getClass().getResource("/Images/GreenDot.png")));
            greenDots.add(greenDot);
            panel.add(greenDot);
        });
    }

    public void DrawGreenFrames() {
        attackablePieces.forEach((s) -> {
            JPanel panel = (JPanel) getBoard().getComponent(s.getSquare());
            JLabel greenFrame = new JLabel();
            greenFrame.setName(String.valueOf(s));
            greenFrame.setIcon(new ImageIcon(getClass().getResource("/Images/GreenFrame.png")));
            greenFrames.add(greenFrame);
            panel.add(greenFrame);
        });
    }

    public void ClearGreenDots() {
        greenDots.forEach((greenDot) -> {
            greenDot.getParent().remove(greenDot);
        });
        greenDots.clear();
    }

    public void ClearGreenFrames() {
        greenFrames.forEach((greenFrame) -> {
            greenFrame.getParent().remove(greenFrame);
        });
        greenFrames.clear();
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public int getSquare() {
        return square;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(JLayeredPane board) {
        this.board = board;
    }

    public JLayeredPane getBoard() {
        return board;
    }

    public final JLabel getLabelPiece() {
        return labelPiece;
    }

    public void setLabelPiece(JLabel labelPiece) {
        this.labelPiece = labelPiece;
    }

    public void setAttackablePieces(ArrayList<Piece> attackablePieces) {
        this.attackablePieces = attackablePieces;
    }

    public void setSquaresCanMove(ArrayList<Integer> squaresCanMove) {
        this.squaresCanMove.clear();
        this.squaresCanMove = squaresCanMove;
        this.squaresCanMove.forEach((integer) -> {
            System.out.println("son hali" + integer);
        });
    }

    public ArrayList<Piece> getAttackablePieces() {
        return attackablePieces;
    }

    public ArrayList<Integer> getSquaresCanMove() {
        return squaresCanMove;
    }

}
