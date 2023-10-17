import processing.core.PApplet;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CarRacing extends PApplet {
    // TODO: declare game variables
    ArrayList<Car> cars = new ArrayList<Car>();
    ArrayList<Tire> tires = new ArrayList<Tire>();
    boolean paused = false;

    public void settings() {
        size(800, 800);   // set the window size
        //TODO: create car and tire objects
    }

    public void setup() {
        // TODO: initialize game variables
        cars.add(new Car(10, 390, 0));
        for (int i = 10; i < 800; i+=20) {
            tires.add(new Tire(i, 300));
            tires.add(new Tire(i, 500));
        }
    }

    public void draw() {
        background(217);    // paint screen white
        // TODO: draw game objects
        fill(255);
        rect(0, 300, 800, 200);
        line(200, 300, 200, 500);
        line(600, 300, 600, 500);
        for (Car car : cars) {
            car.draw(this);
            if (!paused) {
                car.act();
            }
        }
        for (Tire tire : tires) {
            tire.draw(this);
        }
        // TODO: check collision
        for (Car car: cars){
            for (Tire tire: tires){
                car.collision(tire, this);
//                car.stop();
            }
        }
    }

    public void keyPressed(){
        //TODO: change car direction/speed
        if (key == CODED) {
            if (keyCode == LEFT) {
                cars.get(0).turnLeft();
            }
            if (keyCode == RIGHT) {
                cars.get(0).turnRight();
            }
            if (keyCode == UP) {
                cars.get(0).gasUp();
            }
            if (keyCode == DOWN) {
                cars.get(0).gasDown();
            }

        }
        if (key == 'p'){
//            paused = !paused;
            cars.get(0).carGoBoom(this);
        }
    }

    public void keyReleased(){

    }

    public static void main(String[] args) {
        PApplet.main("CarRacing");
    }
}
