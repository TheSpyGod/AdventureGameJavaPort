package Files.Tools;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Point2D;
import java.util.*;
import javax.swing.*;

public class Scene extends JPanel implements KeyListener {
    private char[][] map;
    private final int dimensions = 20;
    private Generation gen;
    private ActorModel.Actor player;
    private ActorModel.Actor enemy;
    private boolean waitingForPlayer = true;
    private boolean exit = false;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public Scene(CardLayout cardLayout, JPanel mainPanel, ActorModel.Actor player, ActorModel.Actor enemy) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.player = player;
        this.enemy = enemy;

        gen = new Generation();
        map = gen.TileSet();

        setFocusable(true);
        addKeyListener(this);
    }

    private void updateGame() {
        if (exit) {
            System.exit(0);
        }
        if (!waitingForPlayer) {
        MapFrame();
        EnemyTurn();
        MapFrame();
        waitingForPlayer = true;
        }
    }

    private void MapFrame() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Monospaced", Font.PLAIN, 12));
        int tileSize = 20;

        for (int i = 0; i < dimensions; i++) {
            for (int j = 0; j < dimensions; j++) {
                if (map[i][j] == '*') {
                    g.setColor(Color.BLACK);
                } else if (i == player.getLocation().getX() && j == player.getLocation().getY()) {
                    g.setColor(Color.GREEN);
                } else if (i == enemy.getLocation().getX() && j == enemy.getLocation().getY()) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * tileSize, i * tileSize, tileSize, tileSize);
                g.setColor(Color.GRAY);
                g.drawRect(j * tileSize, i * tileSize, tileSize, tileSize);
            }
        }

        g.setColor(Color.BLACK);
        g.drawString("Player Health: " + player.getHealth(), 10, dimensions * tileSize + 15);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (PlayerTurn(keyCode)) {
            exit = true;
        } else
        {
            waitingForPlayer = false;
            updateGame();    
        }
       
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    private boolean PlayerTurn(int keyCode) {
        map[(int) player.getLocation().getX()][(int) player.getLocation().getY()] = ' ';

        int newRow = (int) player.getLocation().getX();
        int newCol = (int) player.getLocation().getY();

        switch (keyCode) {
            case KeyEvent.VK_W:
                if (newRow > 0) {
                    newRow -= 1;
                }
                break;
            case KeyEvent.VK_S:
                if (newRow < dimensions - 1) {
                    newRow += 1;
                }
                break;
            case KeyEvent.VK_A:
                if (newCol > 0) {
                    newCol -= 1;
                }
                break;
            case KeyEvent.VK_D:
                if (newCol < dimensions - 1) {
                    newCol += 1;
                }
                break;
            case KeyEvent.VK_E:
                return true;
        }

        if (newRow >= 0 && newRow < dimensions && newCol >= 0 && newCol < dimensions && map[newRow][newCol] != '*') {
            player.setLocation(new Point2D.Float(newRow, newCol));
        }
        map[(int) player.getLocation().getX()][(int) player.getLocation().getY()] = '&';
        return false;
    }

    private void EnemyTurn() {
        Point2D.Float nextStep = EnemyPathFinding((Point2D.Float) enemy.getLocation(), (Point2D.Float) player.getLocation());
        if (nextStep != null && !enemy.getLocation().equals(player.getLocation())) {
            map[(int) enemy.getLocation().getX()][(int) enemy.getLocation().getY()] = ' ';
            enemy.setLocation(nextStep);
            map[(int) enemy.getLocation().getX()][(int) enemy.getLocation().getY()] = 'E';
        } else {
            // Switch to FightScene
            cardLayout.show(mainPanel, "FightScene");
        }
    }

    private Point2D.Float EnemyPathFinding(Point2D.Float start, Point2D.Float target) {
        Deque<Point2D.Float> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[dimensions][dimensions];
        Point2D.Float[] directions = {
                new Point2D.Float(-1, 0), // up
                new Point2D.Float(1, 0),  // down
                new Point2D.Float(0, -1), // left
                new Point2D.Float(0, 1)   // right
        };

        queue.add(start);
        visited[(int) start.getX()][(int) start.getY()] = true;

        Map<Point2D.Float, Point2D.Float> parentMap = new HashMap<>();
        parentMap.put(start, start);

        while (!queue.isEmpty()) {
            Point2D.Float current = queue.poll();
            if (current.equals(target)) {
                Point2D.Float step = current;
                while (!parentMap.get(step).equals(start)) {
                    step = parentMap.get(step);
                }
                return step;
            }

            for (Point2D.Float direction : directions) {
                Point2D.Float next = new Point2D.Float((float) (current.getX() + direction.getX()), (float) (current.getY() + direction.getY()));
                int nextX = (int) next.getX();
                int nextY = (int) next.getY();

                if (nextX >= 0 && nextX < dimensions && nextY >= 0 && nextY < dimensions &&
                        !visited[nextX][nextY] && map[nextX][nextY] != '*') {
                    queue.add(next);
                    visited[nextX][nextY] = true;
                    parentMap.put(next, current);
                }
            }
        }
        return null;
    }

    public void startGameLoop() {
        javax.swing.Timer timer = new javax.swing.Timer(100, e -> updateGame());
        timer.start();
    }
}