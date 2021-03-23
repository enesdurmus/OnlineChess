/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author X550V
 */
public class Piece {

    protected JLabel labelPiece;
    protected int square;
    protected JLayeredPane board;
    protected String name;

    public Piece(int square, JLayeredPane board, String name) {

        this.square = square;
        this.board = board;
        this.name = name;
        labelPiece = new JLabel();
        labelPiece.setName(name);
        JPanel panel = (JPanel) board.getComponent(square);
        panel.add(labelPiece);
    }

    public void Move(int square) {
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public int getSquare() {
        return square;
    }

}
