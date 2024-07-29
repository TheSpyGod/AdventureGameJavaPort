package Files;

import Files.Tools.Scene;
import Files.Tools.FightScene;
import Files.Tools.ActorModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Control {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public Control() {
        frame = new JFrame("Adventure Game");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        ActorModel.Actor player = new ActorModel.Actor("Hero", 100, new Point2D.Float(10, 10));
        ActorModel.Actor enemy = new ActorModel.Actor("Enemy", 20, new Point2D.Float(1, 1));

        Scene scene = new Scene(cardLayout, mainPanel, player, enemy);
        FightScene fightScene = new FightScene(player, enemy);

        mainPanel.add(scene, "MapScene");
        mainPanel.add(fightScene, "FightScene");

        frame.add(mainPanel);
        frame.setSize(420, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        scene.startGameLoop();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Control::new);
    }
}