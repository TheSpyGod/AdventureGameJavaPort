package Files.Tools;

import java.awt.*;
import javax.swing.*;

public class FightScene extends JPanel {
    private ActorModel.Actor player;
    private ActorModel.Actor enemy;
    private JTextArea fightLog;

    public FightScene(ActorModel.Actor player, ActorModel.Actor enemy) {
        this.player = player;
        this.enemy = enemy;
        setLayout(new BorderLayout());

        fightLog = new JTextArea();
        fightLog.setEditable(false);
        add(new JScrollPane(fightLog), BorderLayout.CENTER);

        JButton attackButton = new JButton("Attack");
        attackButton.addActionListener(e -> performAttack());
        add(attackButton, BorderLayout.SOUTH);
    }

    private void performAttack() {
        enemy.setHealth(enemy.getHealth() - 10);
        fightLog.append("Player attacks! Enemy health: " + enemy.getHealth() + "\n");

        if (enemy.getHealth() <= 0) {
            fightLog.append("Enemy defeated!\n");
            // Optionally, switch back to the main scene
            // Assuming scene switching is handled outside this class
        }
    }
}