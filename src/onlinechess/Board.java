/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package onlinechess;

import Message.Message;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    private final ArrayList<JButton> upgradeButtons;
    private boolean isUpgrading = false;
    private int upgradeCounter = 0;
    private final Timer timer;
    // private Timer opponentTimer;

    private void DrawSquares() {
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel(new BorderLayout());
            square.setName(String.valueOf(i));
            ChessBoardPanel.add(square);
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
            opponentPieces.add(new Rook(0, ChessBoardPanel, "bRook1"));
            opponentPieces.add(new Knight(1, ChessBoardPanel, "bKnight1"));
            opponentPieces.add(new Bishop(2, ChessBoardPanel, "bBishop1"));
            opponentPieces.add(new Queen(3, ChessBoardPanel, "bQueen"));
            opponentPieces.add(new King(4, ChessBoardPanel, "bKing"));
            opponentPieces.add(new Bishop(5, ChessBoardPanel, "bBishop2"));
            opponentPieces.add(new Knight(6, ChessBoardPanel, "bKnight2"));
            opponentPieces.add(new Rook(7, ChessBoardPanel, "bRook2"));
            for (int i = 1; i <= 8; i++) {
                opponentPieces.add(new Pawn(i + 7, ChessBoardPanel, "bPawn".concat(String.valueOf(i)), upgradeButtons));
            }

            myPieces.add(new Rook(56, ChessBoardPanel, "wRook1"));
            myPieces.add(new Knight(57, ChessBoardPanel, "wKnight1"));
            myPieces.add(new Bishop(58, ChessBoardPanel, "wBishop1"));
            myPieces.add(new Queen(59, ChessBoardPanel, "wQueen"));
            myPieces.add(new King(60, ChessBoardPanel, "wKing"));
            myPieces.add(new Bishop(61, ChessBoardPanel, "wBishop2"));
            myPieces.add(new Knight(35, ChessBoardPanel, "wKnight2"));
            myPieces.add(new Rook(63, ChessBoardPanel, "wRook2"));
            for (int i = 1; i <= 8; i++) {
                myPieces.add(new Pawn(i + 47, ChessBoardPanel, "wPawn".concat(String.valueOf(i)), upgradeButtons));
            }
        } else {
            myPieces.add(new Rook(56, ChessBoardPanel, "bRook1"));
            myPieces.add(new Knight(57, ChessBoardPanel, "bKnight1"));
            myPieces.add(new Bishop(58, ChessBoardPanel, "bBishop1"));
            myPieces.add(new Queen(60, ChessBoardPanel, "bQueen"));
            myPieces.add(new King(59, ChessBoardPanel, "bKing"));
            myPieces.add(new Bishop(61, ChessBoardPanel, "bBishop2"));
            myPieces.add(new Knight(62, ChessBoardPanel, "bKnight2"));
            myPieces.add(new Rook(63, ChessBoardPanel, "bRook2"));
            for (int i = 1; i <= 8; i++) {
                myPieces.add(new Pawn(i + 47, ChessBoardPanel, "bPawn".concat(String.valueOf(i)), upgradeButtons));
            }

            opponentPieces.add(new Rook(0, ChessBoardPanel, "wRook1"));
            opponentPieces.add(new Knight(1, ChessBoardPanel, "wKnight1"));
            opponentPieces.add(new Bishop(2, ChessBoardPanel, "wBishop1"));
            opponentPieces.add(new Queen(4, ChessBoardPanel, "wQueen"));
            opponentPieces.add(new King(3, ChessBoardPanel, "wKing"));
            opponentPieces.add(new Bishop(5, ChessBoardPanel, "wBishop2"));
            opponentPieces.add(new Knight(6, ChessBoardPanel, "wKnight2"));
            opponentPieces.add(new Rook(7, ChessBoardPanel, "wRook2"));
            for (int i = 1; i <= 8; i++) {
                opponentPieces.add(new Pawn(i + 7, ChessBoardPanel, "wPawn".concat(String.valueOf(i)), upgradeButtons));
            }
        }

        allPieces.addAll(myPieces);
        allPieces.addAll(opponentPieces);

    }

    private void SetUpgradeButtons() {
        knightUpgradeButton.setVisible(false);
        knightUpgradeButton.setIcon(new ImageIcon(getClass().getResource("/Images/" + this.Side + "Knight.png")));
        bishopUpgradeButton.setVisible(false);
        bishopUpgradeButton.setIcon(new ImageIcon(getClass().getResource("/Images/" + this.Side + "Bishop.png")));
        rookUpgradeButton.setVisible(false);
        rookUpgradeButton.setIcon(new ImageIcon(getClass().getResource("/Images/" + this.Side + "Rook.png")));
        queenUpgradeButton.setVisible(false);
        queenUpgradeButton.setIcon(new ImageIcon(getClass().getResource("/Images/" + this.Side + "Queen.png")));

        upgradeButtons.add(knightUpgradeButton);
        upgradeButtons.add(bishopUpgradeButton);
        upgradeButtons.add(rookUpgradeButton);
        upgradeButtons.add(queenUpgradeButton);

    }

    private void AddUpgradedPawn(Piece p) {
        myPieces.remove(selectedPiece);
        allPieces.remove(selectedPiece);
        myPieces.add(p);
        allPieces.add(p);
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

        if (selectedPiece.getClass().getName().substring(12).equalsIgnoreCase("Pawn")) {
            if (selectedPiece.getSquare() / 8 == 0) {
                isUpgrading = true;
            }
        } else {
            selectedPiece = null;
            isSelected = false;
        }

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
                selectedPanel.setBackground(previousColor);
                if (selectedPiece.getClass().getName().substring(12).equalsIgnoreCase("Pawn")) {
                    if (selectedPiece.getSquare() / 8 == 0) {
                        isUpgrading = true;
                    }
                } else {
                    selectedPiece = null;
                    isSelected = false;
                }
            });
        }
    }

    public Board() {

        initComponents();

        this.opponentPieces = new ArrayList<>(16);
        this.myPieces = new ArrayList<>(16);
        this.allPieces = new ArrayList<>(32);
        this.upgradeButtons = new ArrayList<>(4);
        this.timer = new Timer(OurClock, OpponentClock);
        this.Side = "white";

        DrawSquares();

        DrawPieces();

        SetUpgradeButtons();

        timer.StartTimer(60);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Container = new javax.swing.JPanel();
        ChessBoardPanel = new javax.swing.JLayeredPane();
        InformationPanel = new javax.swing.JPanel();
        OurClock = new javax.swing.JLabel();
        OpponentClock = new javax.swing.JLabel();
        OpponentUserName = new javax.swing.JLabel();
        OurUserName = new javax.swing.JLabel();
        PawnUpgradePanel = new javax.swing.JPanel();
        knightUpgradeButton = new javax.swing.JButton();
        bishopUpgradeButton = new javax.swing.JButton();
        rookUpgradeButton = new javax.swing.JButton();
        queenUpgradeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        Container.setBackground(new java.awt.Color(189, 210, 182));
        Container.setPreferredSize(new java.awt.Dimension(900, 600));

        ChessBoardPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        ChessBoardPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                ChessBoardPanelMouseDragged(evt);
            }
        });
        ChessBoardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ChessBoardPanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ChessBoardPanelMousePressed(evt);
            }
        });
        ChessBoardPanel.setLayout(new java.awt.GridLayout(8, 8));

        InformationPanel.setBackground(new java.awt.Color(248, 237, 227));

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
        OpponentUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OpponentUserName.setText("OpponentUserName");
        OpponentUserName.setPreferredSize(new java.awt.Dimension(150, 30));

        OurUserName.setBackground(new java.awt.Color(162, 178, 159));
        OurUserName.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        OurUserName.setForeground(new java.awt.Color(121, 135, 119));
        OurUserName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        OurUserName.setText("OurUserName");
        OurUserName.setPreferredSize(new java.awt.Dimension(150, 30));

        javax.swing.GroupLayout InformationPanelLayout = new javax.swing.GroupLayout(InformationPanel);
        InformationPanel.setLayout(InformationPanelLayout);
        InformationPanelLayout.setHorizontalGroup(
            InformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InformationPanelLayout.createSequentialGroup()
                .addGroup(InformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(OurUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(OpponentUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(InformationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OpponentClock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(OurClock, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        InformationPanelLayout.setVerticalGroup(
            InformationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InformationPanelLayout.createSequentialGroup()
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

        PawnUpgradePanel.setBackground(new java.awt.Color(189, 210, 182));
        PawnUpgradePanel.setPreferredSize(new java.awt.Dimension(360, 60));

        knightUpgradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                knightUpgradeButtonActionPerformed(evt);
            }
        });

        bishopUpgradeButton.setPreferredSize(new java.awt.Dimension(60, 60));
        bishopUpgradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bishopUpgradeButtonActionPerformed(evt);
            }
        });

        rookUpgradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rookUpgradeButtonActionPerformed(evt);
            }
        });

        queenUpgradeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                queenUpgradeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PawnUpgradePanelLayout = new javax.swing.GroupLayout(PawnUpgradePanel);
        PawnUpgradePanel.setLayout(PawnUpgradePanelLayout);
        PawnUpgradePanelLayout.setHorizontalGroup(
            PawnUpgradePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PawnUpgradePanelLayout.createSequentialGroup()
                .addComponent(knightUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(bishopUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(rookUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(queenUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        PawnUpgradePanelLayout.setVerticalGroup(
            PawnUpgradePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(knightUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(bishopUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(rookUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(queenUpgradeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout ContainerLayout = new javax.swing.GroupLayout(Container);
        Container.setLayout(ContainerLayout);
        ContainerLayout.setHorizontalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(ChessBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContainerLayout.createSequentialGroup()
                        .addContainerGap(150, Short.MAX_VALUE)
                        .addComponent(PawnUpgradePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 165, Short.MAX_VALUE)))
                .addComponent(InformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );
        ContainerLayout.setVerticalGroup(
            ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PawnUpgradePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(ContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ChessBoardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50))
                    .addGroup(ContainerLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(InformationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106))))
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

    private void ChessBoardPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardPanelMouseClicked
    }//GEN-LAST:event_ChessBoardPanelMouseClicked

    private void ChessBoardPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardPanelMousePressed

        if (!isUpgrading) {
            if (!isSelected) {
                if (ChessBoardPanel.findComponentAt(evt.getPoint()).getName().charAt(0) == Side.charAt(0)) {
                    SelectPiece((JLabel) ChessBoardPanel.findComponentAt(evt.getPoint()));
                }

            } else if (isSelected && ChessBoardPanel.findComponentAt(evt.getPoint()).getName().charAt(0) == Side.charAt(0)) {

                if (ChessBoardPanel.findComponentAt(evt.getPoint()).getName().charAt(0) == Side.charAt(0)) {
                    SelectAnotherPiece((JLabel) ChessBoardPanel.findComponentAt(evt.getPoint()));
                }

            } else if (isSelected && ChessBoardPanel.findComponentAt(evt.getPoint()).getName().charAt(0) == opponentPieces.get(0).getName().charAt(0)) {

                AttackPiece(ChessBoardPanel.findComponentAt(evt.getPoint()).getName());

            } else {

                MovePiece(Integer.valueOf(ChessBoardPanel.findComponentAt(evt.getPoint()).getName()));

            }
        }
        SwingUtilities.updateComponentTreeUI(ChessBoardPanel);
    }//GEN-LAST:event_ChessBoardPanelMousePressed

    private void ChessBoardPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ChessBoardPanelMouseDragged
        // k.getKing().setLocation(evt.getPoint());
    }//GEN-LAST:event_ChessBoardPanelMouseDragged

    private void OpponentClockPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_OpponentClockPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_OpponentClockPropertyChange

    private void OurClockPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_OurClockPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_OurClockPropertyChange

    private void knightUpgradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_knightUpgradeButtonActionPerformed
        Knight k;
        Pawn p = (Pawn) selectedPiece;
        k = (Knight) p.UpgradePawn("knight", upgradeCounter);
        AddUpgradedPawn(k);
        isUpgrading = false;
        selectedPiece = null;
        isSelected = false;
        upgradeCounter++;
    }//GEN-LAST:event_knightUpgradeButtonActionPerformed

    private void bishopUpgradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bishopUpgradeButtonActionPerformed
        Bishop b;
        Pawn p = (Pawn) selectedPiece;
        b = (Bishop) p.UpgradePawn("bishop", upgradeCounter);
        AddUpgradedPawn(b);
        isUpgrading = false;
        selectedPiece = null;
        isSelected = false;
        upgradeCounter++;


    }//GEN-LAST:event_bishopUpgradeButtonActionPerformed

    private void rookUpgradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rookUpgradeButtonActionPerformed
        Rook r;
        Pawn p = (Pawn) selectedPiece;
        r = (Rook) p.UpgradePawn("rook", upgradeCounter);
        AddUpgradedPawn(r);
        isUpgrading = false;
        selectedPiece = null;
        isSelected = false;
        upgradeCounter++;


    }//GEN-LAST:event_rookUpgradeButtonActionPerformed

    private void queenUpgradeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_queenUpgradeButtonActionPerformed
        Queen q;
        Pawn p = (Pawn) selectedPiece;
        q = (Queen) p.UpgradePawn("queen", upgradeCounter);
        AddUpgradedPawn(q);
        isUpgrading = false;
        selectedPiece = null;
        isSelected = false;
        upgradeCounter++;

    }//GEN-LAST:event_queenUpgradeButtonActionPerformed

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
    private javax.swing.JLayeredPane ChessBoardPanel;
    private javax.swing.JPanel Container;
    private javax.swing.JPanel InformationPanel;
    private javax.swing.JLabel OpponentClock;
    private javax.swing.JLabel OpponentUserName;
    private javax.swing.JLabel OurClock;
    private javax.swing.JLabel OurUserName;
    private javax.swing.JPanel PawnUpgradePanel;
    private javax.swing.JButton bishopUpgradeButton;
    private javax.swing.JButton knightUpgradeButton;
    private javax.swing.JButton queenUpgradeButton;
    private javax.swing.JButton rookUpgradeButton;
    // End of variables declaration//GEN-END:variables
}
