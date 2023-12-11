import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.IOException;
import java.util.ArrayList;

public class CarRacing extends PApplet {
    Minim loader;
    AudioPlayer vroom, boom;
    ArrayList<Car> cars;
    ArrayList<Tire> tires;
    boolean paused = false;
    PImage track, carImage;
    Tire tempTire;

    public void settings() {
        size(800, 800);   // set the window size
    }

    public void setup() {

        cars = new ArrayList<>();
        tires = new ArrayList<>();
        track = loadImage("data/pathImg.png");
        carImage = loadImage("car_carGoBoom.png");
        carImage.resize(30, 75);

        loader = new Minim(this);
        boom = loader.loadFile("Boom-AF.mp3");
        vroom = loader.loadFile("Vroom-AF.mp3");

        cars.add(new Car(10, 50, 0, vroom, boom, carImage));
//        for (int i = 10; i < 800; i+=20) {
//            tires.add(new Tire(i, 300));
//            tires.add(new Tire(i, 500));
//        }
    }

    public void draw() {
        background(217);    // paint screen white
        fill(255);
//        rect(0, 300, 800, 200);
        image(track, 0, 0);
        line(100, 20, 100, 120);
        line(790, 550, 790, 750);
        for (Car car : cars) {
            car.draw(this);
            if (!paused) {
                car.act();
            }
        }
        for (Tire tire : tires) {
            tire.draw(this);
        }
        for (Car car: cars){
            for (Tire tire: tires){
                car.collision(tire, this);
//                car.stop();
            }
        }
        tempTire = new Tire(mouseX, mouseY);
        tempTire.draw(this);
    }

    public void mouseReleased() {
        tires.add(tempTire);
    }

    public void keyPressed(){
        if (key == CODED) {
            if (keyCode == LEFT) {
                cars.get(0).turnLeft();
            }
            if (keyCode == RIGHT) {
                cars.get(0).turnRight();
            }
            if (keyCode == UP) {
                cars.get(0).gasUp();
                vroom.rewind();
                vroom.loop();

            }
            if (keyCode == DOWN) {
                cars.get(0).gasDown();
            }

        }
        if (key == 'p'){
//            paused = !paused;
            for (int i = 0; i < cars.size(); i++) {
                cars.get(i).carGoBoom(this);
                boom.play();
                boom.rewind();
            }

        }
        if (key == 's') {
            try {
                writeTiresToFile(tires);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (key == 'l') {
            try {
                tires = getTiresFromFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void writeTiresToFile(ArrayList<Tire> tires) throws IOException {
        String output = "";
        for (int i = 0; i < tires.size()-1; i++) {
            Tire curr = tires.get(i);
            output += curr.getX() + "," + curr.getY() + "\n";
        }
        output += tires.get(tires.size()-1).getX() + "," + tires.get(tires.size()-1).getY();
        FileIO.writeDataToFile("data/tires.txt", output);
    }

    public ArrayList<Tire> getTiresFromFile() throws IOException {
        ArrayList<Tire> tireList = new ArrayList<>();
        String[] tires = FileIO.readFile("data/tires.txt").trim().split("\n");
        for (String tire : tires) {
            String[] coords = tire.split(",");
            tireList.add(new Tire(Integer.parseInt(coords[0].trim()), Integer.parseInt(coords[1].trim())));
        }
        return tireList;
    }

    public void keyReleased(){

    }

    public static void main(String[] args) {
        PApplet.main("CarRacing");
    }
}
