import java.awt.*;

public class PowerUp {


    /**
     * Created by chales on 11/6/2017.
     */


        public String name;
        public double xpos;
        public double ypos;
        public double dx;
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
        public PowerUp(int pXpos, int pYpos) {
            xpos = pXpos;
            ypos = pYpos;
            dx = Math.random()*10;
            dy = Math.random()*20;
            width = 60;
            height = 60;
            isAlive = true;
            rec = new Rectangle((int)xpos,(int) ypos, width, height);

        } // constructor








        //The move method.  Everytime this is run (or "called") the hero's x position and y position change by dx and dy
        public void RandomPos() {
            xpos = xpos + dx;
            ypos = ypos + dy;



            rec = new Rectangle((int)xpos, (int)ypos, width, height);
        }


    }

