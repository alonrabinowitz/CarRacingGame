import processing.core.PApplet;

import java.security.Key;

public class Car {
    private int x, y, lapCounter;
    private double speed, direction, timeFirstLine, timeSecondLine;
    private boolean timerActive, finished;

    public Car(int x, int y, int direction){
        this.x  = x;
        this.y = y;
        this.direction = direction;
        this.speed = 0;
        timerActive = false;
        finished = false;
    }

    public void draw(PApplet main){
        //TODO: draw car
        main.fill(17, 155, 17);
        main.translate(this.x+25, this.y+10);
        main.rotate((float) direction);
        main.rect(-25, -10, 50, 20);
        main.rotate((float) -direction);
        main.translate(-(this.x+25), -(this.y+10));

        main.textSize(30);
        main.text("Time: " + ((int)(1000*(timeSecondLine-timeFirstLine))/1000.0) + " seconds", 10, 100);
    }

    public void act(){
        //TODO: move car
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
        //TODO: check for collision, if yes do something(crash car/change direction)
        if (tire.distanceToCar(this) < 10) {
            this.carGoBoom(main);
        }
    }

    public boolean checkFinishLine(){
        //TODO: check if car has crossed the finish line
        return this.x >= 550;
    }

    public boolean checkStartLine(){
        //TODO: check if car has crossed the start line
        return this.x >= 150;
    }

    public int getX() {
        return x;
    }

    public int getFarX() {
        return x+50;
    }

    public int getY() {
        return y;
    }

    public int getFarY() {
        return y+20;
    }


    public void carGoBoom(PApplet main){
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
