/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author X550V
 */
public class Board extends javax.swing.JFrame {

    private final String Side;
    private boolean isSelected = false;
    private Container selectedPanel;
    private Color previousColor;
    private Piece selectedPiece;
    private final ArrayList<Piece> myPieces;
    private final ArrayList<Piece> opponentPieces;
    private final ArrayList<Piece> allPieces;

    private void DrawSquares() {
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setName(String.valueOf(i));
            jLayeredPane1.add(square);
            int row = (i / 8) % 2;
            if (row == 0) {
                square.setBackground(i % 2 == 0 ? new Color(184, 139, 74) : new Color(227, 193, 111));
            } else {
                square.setBackground(i % 2 == 0 ? new Color(227, 193, 111) : new Color(184, 139, 74));
            }
        }
    }

    private void DrawPieces() {

        if (Side.equals("white")) {
            opponentPieces.add(new Rook(0, jLayeredPane1, "bRook1"));
            opponentPieces.add(new Knight(1, jLayeredPane1, "bKnight1"));
            opponentPieces.add(new Bishop(2, jLayeredPane1, "bBishop1"));
            opponentPieces.add(new Queen(3, jLayeredPane1, "bQueen"));
            opponentPieces.add(new King(4, jLayeredPane1, "bKing"));
            opponentPieces.add(new Bishop(5, jLayeredPane1, "bBishop2"));
            opponentPieces.add(new Knight(6, jLayeredPane1, "bKnight2"));
            opponentPieces.add(new Rook(7, jLayeredPane1, "bRook2"));
            for (int i = 1; i <= 8; i++) {
                opponentPieces.add(new Pawn(i + 7, jLayeredPane1, "bPawn".concat(String.valueOf(i))));
            }

            myPieces.add(new Rook(56, jLayeredPane1, "wRook1"));
            myPieces.add(new Knight(57, jLayeredPane1, "wKnight1"));
            myPieces.add(new Bishop(58, jLayeredPane1, "wBishop1"));
            myPieces.add(new Queen(59, jLayeredPane1, "wQueen"));
            myPieces.add(new King(60, jLayeredPane1, "wKing"));
            myPieces.add(new Bishop(61, jLayeredPane1, "wBishop2"));
            myPieces.add(new Knight(35, jLayeredPane1, "wKnight2"));
            myPieces.add(new Rook(63, jLayeredPane1, "wRook2"));
            for (int i = 1; i <= 8; i++) {
                myPieces.add(new Pawn(i + 47, jLayeredPane1, "wPawn".concat(String.valueOf(i))));
            }
        } else {
            myPieces.add(new Rook(56, jLayeredPane1, "bRook1"));
            myPieces.add(new Knight(57, jLayeredPane1, "bKnight1"));
            myPieces.add(new Bishop(58, jLayeredPane1, "bBishop1"));
            myPieces.add(new Queen(60, jLayeredPane1, "bQueen"));
            myPieces.add(new King(59, jLayeredPane1, "bKing"));
            myPieces.add(new Bishop(61, jLayeredPane1, "bBishop2"));
            myPieces.add(new Knight(62, jLayeredPane1, "bKnight2"));
            myPieces.add(new Rook(63, jLayeredPane1, "bRook2"));
            for (int i = 1; i <= 8; i++) {
                myPieces.add(new Pawn(i + 47, jLayeredPane1, "bPawn".concat(String.valueOf(i))));
            }

            opponentPieces.add(new Rook(0, jLayeredPane1, "wRook1"));
            opponentPieces.add(new Knight(1, jLayeredPane1, "wKnight1"));
            opponentPieces.add(new Bishop(2, jLayeredPane1, "wBishop1"));
            opponentPieces.add(new Queen(4, jLayeredPane1, "wQueen"));
            opponentPieces.add(new King(3, jLayeredPane1, "wKing"));
            opponentPieces.add(new Bishop(5, jLayeredPane1, "wBishop2"));
            opponentPieces.add(new Knight(6, jLayeredPane1, "wKnight2"));
            opponentPieces.add(new Rook(7, jLayeredPane1, "wRook2"));
            for (int i = 1; i <= 8; i++) {
                opponentPieces.add(new Pawn(i + 7, jLayeredPane1, "wPawn".concat(String.valueOf(i))));
            }
        }

        allPieces.addAll(myPieces);
        allPieces.addAll(opponentPieces);

    }

    private void FindPiece(String name) {
        myPieces.stream().filter((piece) -> (name.equals(piece.getName()))).forEachOrdered((piece) -> {
            selectedPiece = piece;
            selectedPiece.SetSquaresCanMove(this.allPieces);
            selectedPiece.DrawGreenDots();
            selectedPiece.DrawGreenFrames();
        });
    }

    private void SelectPiece(JLabel pieceLabel) {
        System.out.println(pieceLabel.getName());
        selectedPanel = pieceLabel.getParent();
        previousColor = selectedPanel.getBackground();
        selectedPanel.setBackground(new Color(118, 150, 86));
        FindPiece(pieceLabel.getName());
        isSelected = true;
    }

    private void SelectAnotherPiece(JLabel pieceLabel) {
        selectedPanel.setBackground(previousColor);
        selectedPiece.ClearGreenDots();
        selectedPiece.ClearGreenFrames();
        selectedPiece = null;
        SelectPiece(pieceLabel);
    }

    private void MovePiece(int square) {
        selectedPiece.Move(square);
        selectedPanel.setBackground(previousColor);
        selectedPiece = null;
        isSelected = false;
    }

    public Board() {
        this.opponentPieces = new ArrayList<>(16);
        this.myPieces = new ArrayList<>(16);
        this.allPieces = new ArrayList<>(32);

        this.Side = "white";

        initComponents();

        DrawSquares();

        DrawPieces();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane1 = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPane1.setPreferredSize(new java.awt.Dimension(500, 500));
        jLayeredPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLayeredPane1MouseDragged(evt);
            }
        });
        jLayeredPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLayeredPane1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLayeredPane1MousePressed(evt);
            }
        });
        jLayeredPane1.setLayout(new java.awt.GridLayout(8, 8));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(197, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLayeredPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MouseClicked
    }//GEN-LAST:event_jLayeredPane1MouseClicked

    private void jLayeredPane1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MousePressed
        if (!isSelected) {
            if (jLayeredPane1.findComponentAt(evt.getPoint()).getName().charAt(0) == Side.charAt(0)) {
                SelectPiece((JLabel) jLayeredPane1.findComponentAt(evt.getPoint()));
            }

        } else if (isSelected && jLayeredPane1.findComponentAt(evt.getPoint()).getName().charAt(0) == Side.charAt(0)) {

            if (jLayeredPane1.findComponentAt(evt.getPoint()).getName().charAt(0) == Side.charAt(0)) {
                SelectAnotherPiece((JLabel) jLayeredPane1.findComponentAt(evt.getPoint()));
            }

        } else if (isSelected && jLayeredPane1.findComponentAt(evt.getPoint()).getName().charAt(0) == opponentPieces.get(0).getName().charAt(0)) {

            System.out.println("yeme metodu");

        } else {

            MovePiece(Integer.valueOf(jLayeredPane1.findComponentAt(evt.getPoint()).getName()));

        }

        SwingUtilities.updateComponentTreeUI(jLayeredPane1);
    }//GEN-LAST:event_jLayeredPane1MousePressed

    private void jLayeredPane1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MouseDragged
        // k.getKing().setLocation(evt.getPoint());
    }//GEN-LAST:event_jLayeredPane1MouseDragged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Board.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

                new Board().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
