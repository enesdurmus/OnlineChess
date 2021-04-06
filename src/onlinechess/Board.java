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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Timer timer;
    // private Timer opponentTimer;

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

    private void naber() {
        System.out.println("naber");
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
        //System.out.println(pieceLabel.getName());
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
        System.out.println(selectedPiece.getName() + " is moving to " + square);
        selectedPiece.Move(square);
        selectedPanel.setBackground(previousColor);
        selectedPiece = null;
        isSelected = false;
        if (timer.GetIsRunning()) {
            timer.PauseTimer();
        } else {
            timer.ResumeTimer();
        }
    }

    private void AttackPiece(String name) {
        if (selectedPiece.Attack(name)) {
            opponentPieces.stream().filter((piece) -> (name.equals(piece.getName()))).forEachOrdered((piece) -> {
                allPieces.remove(piece);
            });
        }
    }

    public Board() {

        initComponents();

        this.opponentPieces = new ArrayList<>(16);
        this.myPieces = new ArrayList<>(16);
        this.allPieces = new ArrayList<>(32);
        this.timer = new Timer(OurClock, OpponentClock);
        this.Side = "white";

        DrawSquares();

        DrawPieces();

        timer.StartTimer(60);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Container = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        InformationContainer = new javax.swing.JPanel();
        OurClock = new javax.swing.JLabel();
        OpponentClock = new javax.swing.JLabel();
        OpponentUserName = new javax.swing.JLabel();
        OurUserName = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(900, 600));
        setResizable(false);

        Container.setBackground(new java.awt.Color(189, 210, 182));
        Container.setPreferredSize(new java.awt.Dimension(900, 600));

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

        InformationContainer.setBackground(new java.awt.Color(248, 237, 227));

        OurClock.setBackground(new java.awt.Color(162, 178, 159));
        OurClock.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        OurClock.setForeground(new java.awt.Color(121, 135, 119));
        OurClock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OurClock.setText("jLabel1");
        OurClock.setToolTipText("");
        OurClock.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                OurClockPropertyChange(evt);
            }
        });

        OpponentClock.setBackground(new java.awt.Color(162, 178, 159));
        OpponentClock.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        OpponentClock.setForeground(new java.awt.Color(121, 135, 119));
        OpponentClock.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OpponentClock.setText("jLabel1");
        OpponentClock.setToolTipText("");
        OpponentClock.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                OpponentClockPropertyChange(evt);
            }
        });

        OpponentUserName.setBackground(new java.awt.Color(162, 178, 159));
        OpponentUserName.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        OpponentUserName.setForeground(new java.awt.Color(121, 135, 119));
        OpponentUserName.setText("OpponentUserName");
        OpponentUserName.setPreferredSize(new java.awt.Dimension(150, 30));

        OurUserName.setBackground(new java.awt.Color(162, 178, 159));
        OurUserName.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        OurUserName.setForeground(new java.awt.Color(121, 135, 119));
        OurUserName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        OurUserName.setText("OurUserName");
        OurUserName.setPreferredSize(new java.awt.Dimension(150, 30));

        javax.swing.GroupLayout InformationContainerLayout = new javax.swing.GroupLayout(InformationContainer);
        InformationContainer.setLayout(InformationContainerLayout);
        InformationContainerLayout.setHorizontalGroup(
            InformationContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InformationContainerLayout.createSequentialGroup()
                .addGroup(InformationContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(OurUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OpponentUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(InformationContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InformationContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OpponentClock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OurClock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        InformationContainerLayout.setVerticalGroup(
            InformationContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InformationContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OpponentClock, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OpponentUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(OurUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(OurClock, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        OpponentClock.getAccessibleContext().setAccessibleName("OurClock");

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(InformationContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(InformationContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Container, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

            AttackPiece(jLayeredPane1.findComponentAt(evt.getPoint()).getName());

        } else {

            MovePiece(Integer.valueOf(jLayeredPane1.findComponentAt(evt.getPoint()).getName()));

        }

        SwingUtilities.updateComponentTreeUI(jLayeredPane1);
    }//GEN-LAST:event_jLayeredPane1MousePressed

    private void jLayeredPane1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLayeredPane1MouseDragged
        // k.getKing().setLocation(evt.getPoint());
    }//GEN-LAST:event_jLayeredPane1MouseDragged

    private void OpponentClockPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_OpponentClockPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_OpponentClockPropertyChange

    private void OurClockPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_OurClockPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_OurClockPropertyChange

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
    private javax.swing.JPanel Container;
    private javax.swing.JPanel InformationContainer;
    private javax.swing.JLabel OpponentClock;
    private javax.swing.JLabel OpponentUserName;
    private javax.swing.JLabel OurClock;
    private javax.swing.JLabel OurUserName;
    private javax.swing.JLayeredPane jLayeredPane1;
    // End of variables declaration//GEN-END:variables
}
