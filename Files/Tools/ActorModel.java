package Files.Tools;

import java.awt.geom.Point2D;

public class ActorModel {
    public static class Actor
    {
        private String name;
        private int health;
        private Point2D location;

        public Actor(String name, int health, Point2D location) {
            this.name = name;
            this.health = health;
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHealth() {
            return health;
        }

        public void setHealth(int health) {
            this.health = health;
        }

        public Point2D getLocation() {
            return location;
        }

        public void setLocation(Point2D location) {
            this.location = location;
        }
    }
}