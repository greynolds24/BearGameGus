import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Enemy {

    //VARIABLE DECLARATION SECTION
    //Here's where you state which variables you are going to use.
    public String name;                //holds the name of the hero
    public double xpos;                //the x position
    public double ypos;                //the y position
    public double dx;                    //the speed of the hero in the x direction
    public double dy;                    //the speed of the hero in the y direction
    public int width;
    public int height;
    public boolean isAlive;   //a boolean to denote if the hero is alive or dead.
    public Rectangle rec;
    public Image pic;

    // METHOD DEFINITION SECTION

    // Constructor Definition
    // A constructor builds the object when called and sets variable values.


    //This is a SECOND constructor that takes 3 parameters.  This allows us to specify the hero's name and position when we build it.
    // if you put in a String, an int and an int the program will use this constructor instead of the one above.
    public Enemy(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = Math.random();
        dy = Math.random();
        width = 60;
        height = 60;
        isAlive = true;
        rec = new Rectangle((int)xpos,(int) ypos, width, height);

    } // constructor


    public void printInfo() {
        System.out.println(" ");
        System.out.println(xpos + " ,  " + ypos);


    }


    //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;


        if (ypos >= 700 - height) {
            dy = -dy;
        }
        if (xpos >=1000-width) {
            dx = -dx;
        }
        if (ypos < 0) {
            dy = -dy;
        }
        if (xpos <0) {
            dx = -dx;
        }
        rec = new Rectangle((int)xpos, (int)ypos, width, height);
    }

    public void wrap() {
        xpos = xpos + dx;
        ypos = ypos + dy;


        if (ypos >= 700) {
            ypos = 0;
        }
        if (xpos >=1000-width) {
            xpos = 100;
        }
        if (ypos <= 0) {
            ypos = 700;
        }
        if (xpos <=0) {
            xpos = 900;
        }
        rec = new Rectangle((int)xpos, (int)ypos, width, height);
    }


}









