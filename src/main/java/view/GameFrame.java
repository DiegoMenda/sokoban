package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private EntitiesRenderer renderer;
    private int puntuation = 0;
    private String levelName = "";

    private JButton startButton;
    private JButton restartLevelButton;
    private JButton saveGameButton;
    private JButton openSaveGameButton;
    private JLabel puntuationLabel;
    private JLabel levelNameLabel;
    private JLabel undoLabel;

    public GameFrame(EntitiesRenderer renderer) {
        this.renderer = renderer;
        initializeFrame();
        initializeComponents();
    }

    private void initializeFrame() {
        setTitle("Sokoban Game");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void initializeComponents() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(renderer);
        add(centerPanel, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(200, getHeight()));
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        add(sidePanel, BorderLayout.EAST);

        startButton = new JButton("Start a new game");
        restartLevelButton = new JButton("Restart the current level");
        saveGameButton = new JButton("Save the game");
        openSaveGameButton = new JButton("Open a saved game");
        puntuationLabel = new JLabel("Total Puntuation: 0");
        levelNameLabel = new JLabel("Level Name: ");
        undoLabel = new JLabel("Undo the last movement: Z");

        sidePanel.add(startButton);
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
        puntuationLabel.setText("Total Puntuation: " + puntuation);
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
        levelNameLabel.setText("Level Name: " + levelName);
    }

    public void setStartButtonAction(ActionListener action) {
        startButton.addActionListener(action);
    }

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

    public void centerRenderer() {
        int frameWidth = getWidth();
        int frameHeight = getHeight();
        int rendererWidth = renderer.getPreferredSize().width;
        int rendererHeight = renderer.getPreferredSize().height;
        int x = (frameWidth - rendererWidth) / 2;
        int y = (frameHeight - rendererHeight) / 2;

        renderer.setBounds(x, y, rendererWidth, rendererHeight);
    }
}
