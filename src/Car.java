import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import processing.core.PApplet;
import processing.core.PImage;

import java.security.Key;

public class Car {
    private int lapCounter;
    private float x, y;
    private double speed, direction, timeFirstLine, timeSecondLine;
    private boolean timerActive, finished, playingBoom;
    Minim loader;
    AudioPlayer vroom, boom;
    PImage carImage;

    public Car(int x, int y, int direction, AudioPlayer vroom, AudioPlayer boom, PImage carImage){
        this.x  = x;
        this.y = y;
        this.direction = direction;
        this.speed = 0;
        timerActive = false;
        finished = false;
        loader = new Minim(this);
        this.boom = boom;
        this.vroom = vroom;
        this.carImage = carImage;
        this.playingBoom = false;
    }


    public void draw(PApplet main){
        main.fill(17, 155, 17);
        main.translate(this.x+25, this.y+10);
        main.rotate((float) direction + (float) (3*Math.PI/2));
        main.image(carImage, -10, -25);
        main.rotate((float) -direction - (float) (3*Math.PI/2));
        main.translate(-(this.x+25), -(this.y+10));

        main.textSize(30);
        main.text("Time: " + ((int)(1000*(timeSecondLine-timeFirstLine))/1000.0) + " seconds", 10, 100);
    }

    public void act(){
        this.x += (int) (this.speed * Math.cos(this.direction));
        this.y += (int) (this.speed * Math.sin(this.direction));
        speed *= 0.999;
        if (timerActive && !finished) {
            timeSecondLine = (double) System.currentTimeMillis() /1000;
        }
        if (checkStartLine() && this.timeFirstLine == 0) {
            startTime((double) System.currentTimeMillis() /1000);
        }
        if (checkFinishLine() && !finished) {
            endTime((double) System.currentTimeMillis() /1000);
        }
    }

    public void collision(Tire tire, PApplet main){
        if (tire.distanceToCar(this) < 5) {
            this.carGoBoom(main);
        }
    }

    public boolean checkFinishLine(){
        return this.x >= 790;
    }

    public boolean checkStartLine(){
        return this.x >= 30;
    }

    public float getX() {
        return x;
    }

    public float getFarX() {
        return x+50;
    }

    public float getY() {
        return y;
    }

    public float getFarY() {
        return y+20;
    }


    public void carGoBoom(PApplet main){
        if (!playingBoom) {
            vroom.mute();
            boom.rewind();
            boom.play();
            playingBoom = true;
        }
        main.strokeWeight(0);
        main.fill(245,200,10);
        main.ellipse(this.x+25, this.y+10, 300, 300);
        main.fill(245,150,10);
        main.ellipse(this.x+25, this.y+10, 150, 150);
        main.fill(245,50,10);
        main.ellipse(this.x+25, this.y+10, 50, 50);
        main.fill(0);
        main.textSize(100);
        main.text("BOOM", this.x-125, this.y+45);
        stop();


    }

    public void turnLeft(){
        direction -= 0.1;
        speed *= 0.99999;
        speed *= 0.99;
    }

    public void turnRight(){
        direction += 0.1;
        speed *= 0.99999;
    }

    public void gasUp(){
        speed += 0.1;

    }

    public void gasDown() {
        speed -= 0.1;
    }

    public void stop(){
        this.speed = 0;
    }

    public void startTime(double timeFirstLine) {
        this.timeFirstLine = timeFirstLine;
        timerActive = true;
    }

    public void endTime(double timeSecondLine) {
        this.timeSecondLine = timeSecondLine;
        finished = true;
    }
}
