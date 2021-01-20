package piano;

import processing.core.PApplet;

/** 
 * Represents the main Forte app which extends the PApplet class
 * @author 480271521
 * @version 4.0
 * @since 1.0
*/

public class App extends PApplet {

    public SkeletonRender skeleton;
    public Player player;
    public PlaybackControls controls;
    public Cursor cursor;
    public Grid grid;
    public FileHandler fileHandler;
    public UndoRedo undoRedo;

    /**
     * Constructs and initializes the whole app take in no parameters
     */


    public App() {
        grid = new Grid(60, 75);
        player = new Player(grid);
        cursor = new Cursor(49);
        controls = new PlaybackControls(player);
        fileHandler = new FileHandler(grid, player);
        skeleton = new SkeletonRender();
        undoRedo = new UndoRedo(grid);
    }
    /**
     * Settings for the forte app, by default this sets the size of the window to 540x335
     */

    public void settings() {
        // Don't touch
        size(540, 335);
    }
    /**
     * Used to load all the images for all objects in the Forte application
     */

    public void setup() {
        frameRate(60);
        skeleton.loadImages(loadImage("src/main/resources/banner.png"), loadImage("src/main/resources/buttonBack.png"), loadImage("src/main/resources/middleBanner.png"), loadImage("src/main/resources/keyboard.png"));
        grid.loadImages(loadImage("src/main/resources/grid.png"), loadImage("src/main/resources/block.png"), loadImage("src/main/resources/reset.png"));
        cursor.loadImages(loadImage("src/main/resources/pointer.png"));
        controls.loadImages(loadImage("src/main/resources/play.png"), loadImage("src/main/resources/stop.png"), loadImage("src/main/resources/pause.png"), loadImage("src/main/resources/next.png"), loadImage("src/main/resources/prev.png"));
        fileHandler.loadImages(loadImage("src/main/resources/save.png"), loadImage("src/main/resources/load.png"));
        player.loadImages(loadImage("src/main/resources/P.png"), loadImage("src/main/resources/M.png"), loadImage("src/main/resources/B.png"), loadImage("src/main/resources/S.png"));
        undoRedo.loadImages(loadImage("src/main/resources/undo.png"), loadImage("src/main/resources/redo.png"));
    }
    /**
     * Main draw method, used for displaying all the graphics
     * No Logic is implemented here
     */

    public void draw() {
        skeleton.draw(this);
        this.image(loadImage("src/main/resources/grid.png"), 60, 75);
        player.draw(this);
        controls.draw(this);
        grid.draw(this);
        this.cursorHandler();
        cursor.draw(this);
        fileHandler.draw(this);
        undoRedo.draw(this);
    }
    /**
     * This method registers any mouseclick if the user clicks and determines where the mouse has been clicked
     * Allows different methods of other objects to be invoked
     */

    public void mouseClicked() {
        if (mouseX >= 60 && mouseY >= 75) {
            if (!Player.threadActive && Player.isAtStart) {
                undoRedo.addToUndoStack();
                grid.enableBlock((mouseX - 60) / 15, (mouseY - 75) / 20);
            }
            grid.tick();
        } else if ((mouseX >= 5 && mouseY >= 5) && (mouseX <= 45 && mouseY <= 45)) {
            controls.play();
        } else if ((mouseX >= 50 && mouseY >= 5) && (mouseX <= 90 && mouseY <= 45)) {
            controls.stop();
            cursor.resetCursor();
        } else if ((mouseX >= 95 && mouseY >= 5) && (mouseX <= 135 && mouseY <= 45)) {
            if (!Player.isPlaying) {
                undoRedo.addToUndoStack();
                grid.resetGrid();
                controls.stop();
            }
        } else if ((mouseX >= 140 && mouseY >= 5) && (mouseX <= 180 && mouseY <= 45)) {
            controls.stop();
            fileHandler.save("my_save.txt");
        } else if ((mouseX >= 185 && mouseY >= 5) && (mouseX <= 225 && mouseY <= 45)) {
            controls.stop();
            undoRedo.addToUndoStack();
            fileHandler.load("my_save.txt");
        } else if ((mouseX >= 275 && mouseY >= 5) && (mouseX <= 315 && mouseY <= 45)) {
            controls.setPreviousInstrument();
        } else if ((mouseX >= 365 && mouseY >= 5) && (mouseX <= 405 && mouseY <= 45)) {
            controls.setNextInstrument();
        } else if ((mouseX >= 450 && mouseY >= 5) && (mouseX <= 490 && mouseY <= 45)) {
            if (!Player.isPlaying) {
                undoRedo.undo();
            }
        } else if ((mouseX >= 495 && mouseY >= 5) && (mouseX <= 535 && mouseY <= 45)) {
            if (!Player.isPlaying) {
                undoRedo.redo();
            }
        }
    }

    /**
     * A Helper method for mousePressed used purely for testing purposes(brute forcing a click)
     * @param x  - the x coordinate of the mouse
     * @param y  - the y coordinate of the mouse
     */

    public void mouseHelper(int x, int y) {
        mouseX = x;
        mouseY = y;
        mouseClicked();

    }

    /**
     * Used to move the cursor accordingly when the Player is playing, this is only to be used in draw()
     */

    public void cursorHandler()
    {
        if (Player.threadActive) {
            cursor.tick(1);
        }
        if (Player.isAtStart) {
            if (cursor.xCoordinate != 540 && cursor.xCoordinate >= 500) {
                cursor.tick(1);
            } else {
                cursor.resetCursor();
            }
        }
    }

    public static void main(String[] args) {
        // Don't touch this
        PApplet.main("piano.App");
    }
}