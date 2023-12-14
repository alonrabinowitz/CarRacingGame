import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Tire {
    private int x, y;
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tire(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(PApplet main) {
        main.fill(46);
        main.ellipse(this.x, this.y, 20, 20);
    }

    public double distanceToCar(Car car) {
        double topLeft = Math.sqrt(Math.pow(car.getX() - this.x, 2) + Math.pow(car.getY() - this.y, 2));
        double topRight = Math.sqrt(Math.pow(car.getFarX() - this.x, 2) + Math.pow(car.getY() - this.y, 2));
        double bottomLeft = Math.sqrt(Math.pow(car.getX() - this.x, 2) + Math.pow(car.getFarY() - this.y, 2));
        double bottomRight = Math.sqrt(Math.pow(car.getFarX() - this.x, 2) + Math.pow(car.getFarY() - this.y, 2));

        return Math.min(Math.min(topLeft, topRight), Math.min(bottomLeft, bottomRight));
    }

}
