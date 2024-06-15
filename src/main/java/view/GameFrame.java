package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private EntitiesRenderer renderer;
    private int puntuation = 0;
    private String levelName = "";

    private JButton startButton;
    //private JButton loadButton;
    private JButton restartLevelButton;
    private JButton saveGameButton;
    private JButton openSaveGameButton;
    private JLabel puntuationLabel;
    private JLabel levelNameLabel;
    private JLabel undoLabel;

    public GameFrame(EntitiesRenderer renderer) {
        setTitle("Sokoban Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        this.renderer = renderer;
        renderer.setPreferredSize(new Dimension(400, 300));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(renderer);
        add(centerPanel, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        add(sidePanel, BorderLayout.EAST);

        startButton = new JButton("Start a new game");
        //loadButton = new JButton("Load a level");
        restartLevelButton = new JButton("Restart the current level");
        saveGameButton = new JButton("Save the game");
        openSaveGameButton = new JButton("Open a saved game");
        puntuationLabel = new JLabel("Total Puntuation: 0");
        levelNameLabel = new JLabel("Level Name: ");
        undoLabel = new JLabel("Undo the last movement: Z");

        sidePanel.add(startButton);
        //sidePanel.add(loadButton);
        sidePanel.add(restartLevelButton);
        sidePanel.add(saveGameButton);
        sidePanel.add(openSaveGameButton);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(puntuationLabel);
        sidePanel.add(levelNameLabel);
        sidePanel.add(Box.createVerticalStrut(20));
        sidePanel.add(undoLabel);
    }

    public void setPuntuation(int puntuation) {
        this.puntuation = puntuation;
        puntuationLabel.setText("Total Puntuation " + puntuation);
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
        levelNameLabel.setText("Level Name: " + levelName);
    }

    public void setStartButtonAction(ActionListener action) {
        startButton.addActionListener(action);
    }

//    public void setLoadButtonAction(ActionListener action) {
//        loadButton.addActionListener(action);
//    }

    public void setRestartLevelButtonAction(ActionListener action) {
        restartLevelButton.addActionListener(action);
    }

    public void setSaveGameButtonAction(ActionListener action) {
        saveGameButton.addActionListener(action);
    }

    public void setOpenSaveGameButtonAction(ActionListener action) {
        openSaveGameButton.addActionListener(action);
    }

    public void renderFrame() {
        renderer.repaint();
        repaint();
    }


    private class OverlayPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.setColor(Color.BLACK); 
            g.drawString("Total Puntuation: " + puntuation, 5, 30);
            g.drawString("Level Name: " + levelName , 5, 55);
        }
    }
    public void centerRenderer() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int rendererWidth = renderer.getPreferredSize().width;
        System.out.println("W: "+rendererWidth);
        int rendererHeight = renderer.getPreferredSize().height;
        System.out.println("H: "+rendererHeight);
        int x = (frameWidth - rendererWidth) / 2;
        int y = (frameHeight - rendererHeight) / 2;

        renderer.setBounds(x, y, rendererWidth, rendererHeight);
    }
}