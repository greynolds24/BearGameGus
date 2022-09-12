//Basic Game Application
//Version 2
// Basic Object, Image, Movement

// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    public Enemy Guy;
    public Enemy Jeff;



    public Enemy[] enemy;

    public Player user;
    public Player bear; //bear

    public PowerUp speedboost;
    public PowerUp food;



    //image declarations
    public Image GuyPic;
    public Image ManPic;
    public Image backgroundImage;
    public Image MugShot;
    public Image MousePic;
    public Image Man;
    public Image BearPic;
    public Image Gameover;
    public Image speed;
    public Image foodpic;
    public Image BlackScreen;

    public boolean startscreen = true;

    public SoundFile Scream;
    public SoundFile crashSound;
    public SoundFile GameOver;






    public int timer;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public BasicGameApp() {
        setUpGraphics();
        canvas.addKeyListener(this);

        System.out.println("Setting up game");
        //variable and objects
        //create (construct) the objects needed for the game and load up
        speedboost = new PowerUp((int)(Math.random()*500),(int)(Math.random()*500));
        food = new PowerUp((int)(Math.random()*600),(int)(Math.random()*500));

        Guy = new Enemy(300, 300);
        Guy.printInfo();
        Guy.dy = -1;
        Guy.dx = 1;

        Jeff = new Enemy(500, 700);
        Jeff.printInfo();
        Jeff.dy = -.2;
        Jeff.dx = .2;

        bear = new Player(50, 0,1,1,MugShot);

        user = new Player(100,100,1,1,MugShot);

        enemy = new Enemy[8];
        for (int k = 0; k < enemy.length; k++) {
            enemy[k] = new Enemy((int)(200 + k * 20), 300);
            System.out.println(enemy[k].xpos);
        }

        enemy[0].pic = Toolkit.getDefaultToolkit().getImage("frog.png");
        enemy[1].pic = Toolkit.getDefaultToolkit().getImage("peng.gif");
        enemy[2].pic = Toolkit.getDefaultToolkit().getImage("bird.png");
        enemy[3].pic = Toolkit.getDefaultToolkit().getImage("shoephoto.png");
        enemy[4].pic = Toolkit.getDefaultToolkit().getImage("SuperGuy.png");
        enemy[5].pic = Toolkit.getDefaultToolkit().getImage("policeguy.jpeg");
        enemy[6].pic = Toolkit.getDefaultToolkit().getImage("crab.png");
        enemy[7].pic = Toolkit.getDefaultToolkit().getImage("SkierPic1.png");




        Gameover = Toolkit.getDefaultToolkit().getImage("gameoverscreen.jpeg");
        GuyPic = Toolkit.getDefaultToolkit().getImage("Man.png");
        ManPic = Toolkit.getDefaultToolkit().getImage("SkierPic2.png");
        MugShot = Toolkit.getDefaultToolkit().getImage("MugShot2.png");
        backgroundImage = Toolkit.getDefaultToolkit().getImage("SkiHill.jpeg");
        MousePic = Toolkit.getDefaultToolkit().getImage("MousePic.png");
        Man = Toolkit.getDefaultToolkit().getImage("Man.png");
        BearPic = Toolkit.getDefaultToolkit().getImage("BearPic.png");
        BlackScreen = Toolkit.getDefaultToolkit().getImage("BlackScreen.jpeg");

        foodpic = Toolkit.getDefaultToolkit().getImage("baby.png");
        speed = Toolkit.getDefaultToolkit().getImage("speed.png");



        crashSound = new SoundFile("crashSound.wav");
        Scream = new SoundFile("Scream.wav");
        GameOver = new SoundFile("GameOver.wav");


    }
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {
        while (true) {
            moveThings();
            crash();
            if (bear.isAlive==true) {
                timer++;
            }

            render();
            pause(2);


        }

    }


    public void moveThings() {
        bear.move();
        Guy.move();
        Jeff.wrap();
        user.move();


        for(int k = 0; k < enemy.length; k++) {
            enemy[k].move();
        }

    }

    public void crash(){
        if (Guy.rec.intersects(Jeff.rec)&&(Jeff.isAlive)&& (bear.isAlive)){
            Guy.dx = 7;
            System.out.println("crash");
            Jeff.isAlive = true;
            Scream.play();
            crashSound.play();
        }
        if (Guy.rec.intersects(bear.rec)&&(bear.isAlive == true)){
            bear.isAlive=false;
            GameOver.play();
        }
        if (Jeff.rec.intersects(bear.rec)&&(bear.isAlive == true)){
            bear.isAlive=false;
            GameOver.play();
        }


        for(int k = 0; k< enemy.length; k++)
            if(bear.isAlive == true && enemy[k].rec.intersects(bear.rec)){
                bear.isAlive=false;
                enemy[k].height = enemy[k].height+5;
                enemy[k].width = enemy[k].width+5;
                GameOver.play();
            }

        if (bear.rec.intersects(speedboost.rec)&&(bear.isAlive == true)){
            bear.dy = 3;
            bear.dx = 3;
            speedboost.isAlive = false;
        }
        if (bear.rec.intersects(food.rec)&&(bear.isAlive == true)&&(food.isAlive)){
            timer = timer+1000;
            bear.height = bear.height+50;
            bear.width = bear.width+50;
            food.isAlive = false;

        }


    }



    //paints things on the screen using bufferStrategy
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (startscreen == true){
            g.drawImage(BlackScreen, -50, -50, WIDTH + 100, HEIGHT + 170, null);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.setColor(Color.BLUE);
            g.drawString("Press W to Start", 260, 400);
        }
        else {


            g.drawImage(backgroundImage, -50, -50, WIDTH + 100, HEIGHT + 170, null);


            if (bear.isAlive == false) {
                g.drawImage(Gameover, 0, 0, 1000, 700, null);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
                g.setColor(Color.BLUE);
                g.drawString(String.valueOf(timer), 520, 500);
                g.drawString("Your Score:", 260, 500);


            } else {

                //draw things
                g.drawString(" ", (int) Guy.xpos, (int) Guy.ypos);
                g.drawImage(GuyPic, (int) Guy.xpos, (int) Guy.ypos, Guy.width, Guy.height, null);

                if (Jeff.isAlive == true) {
                    g.drawString(" ", (int) Jeff.xpos, (int) Jeff.ypos);
                    g.drawImage(MugShot, (int) Jeff.xpos, (int) Jeff.ypos, Jeff.width, Jeff.height, null);
                }

                if (bear.isAlive == true) {
                    g.drawString("", (int) bear.xpos, (int) bear.ypos);
                    g.drawImage(BearPic, (int) bear.xpos, (int) bear.ypos, bear.width, bear.height, null);
                }

                for (int k = 0; k < enemy.length; k++) {
                    g.drawImage(enemy[k].pic, (int) enemy[k].xpos, (int) enemy[k].ypos, enemy[k].width, enemy[k].height, null);
                }
                if (speedboost.isAlive == true) {
                    g.drawImage(speed, (int) speedboost.xpos, (int) speedboost.ypos, speedboost.width, speedboost.height, null);
                    System.out.println("speed x, y: " + speedboost.xpos + ", " + speedboost.ypos);
                }
                if (food.isAlive == true) {
                    g.drawImage(foodpic, (int) food.xpos, (int) food.ypos, food.width, food.height, null);
                }
            }
            if (bear.isAlive == true) {
                g.drawString(timer + "", 700, 40);
            }
        }
        g.dispose();

        bufferStrategy.show();


    }



    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("My First Game");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if( ) statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 87) {
            bear.up = true;
            startscreen = false;
        }
        if (keyCode == 68) {
            bear.right = true;
        }
        if (keyCode == 65){
            bear.left = true;
        }
        if (keyCode == 83) {
            bear.down = true;
        }



    }

    @Override
    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
        if (keyCode == 87) {
            bear.up = false;
        }
        if (keyCode == 65){
            bear.left = false;
        }
        if (keyCode == 68) {
            bear.right = false;
        }
        if (keyCode == 83) {
            bear.down = false;
        }
    }
}
