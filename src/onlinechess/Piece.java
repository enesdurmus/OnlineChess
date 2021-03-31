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
    public ArrayList<Integer> squaresCanMove;
    private ArrayList<JLabel> greenDots;

    public Piece(int square, JLayeredPane board, String name) {
        squaresCanMove = new ArrayList<>();
        greenDots = new ArrayList<>();
        this.square = square;
        this.board = board;
        this.name = name;
        labelPiece = new JLabel();
        labelPiece.setName(name);
        JPanel panel = (JPanel) board.getComponent(square);
        panel.add(labelPiece);
    }

    public void Move(int square) {
        if (MoveControl(square)) {
            setSquare(square);
            JPanel panel = (JPanel) getBoard().getComponent(square);
            System.out.println(getBoard().getComponent(square).getName());
            panel.add(getLabelPiece());
        }
        ClearGreenDots();

    }

    public boolean MoveControl(int s) {
        return squaresCanMove.stream().anyMatch((i) -> (i == s));
    }

    public void SetSquaresCanMove(ArrayList<Piece> allPieces) {
    }

    public void DrawnGreenDots() {
        squaresCanMove.forEach((s) -> {
            JPanel panel = (JPanel) getBoard().getComponent(s);
            JLabel greenDot = new JLabel();
            greenDot.setName(String.valueOf(s));
            greenDot.setIcon(new ImageIcon(getClass().getResource("/Images/GreenDot.png")));
            greenDots.add(greenDot);
            panel.add(greenDot);
        });
    }

    public void ClearGreenDots() {
        greenDots.forEach((greenDot) -> {
            greenDot.getParent().remove(greenDot);
        });
        greenDots.clear();
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

}
