package Files;

import Files.Tools.*;

public class Control {
    Scene currentScene;
    private Control() {
    currentScene = new Scene();
    }
    public static void main(String[] args) {
        Control m = new Control();
        m.Game();
    }

    private void Game() {
        currentScene.startGameLoop();
    }
}