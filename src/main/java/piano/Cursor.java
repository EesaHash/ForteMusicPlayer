package piano;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Main cursor class with methods to manipulate it
 * To be controlled by the main app
 */

public class Cursor {

    public float xCoordinate;
    public PImage pointer;
/**
 * Constructor for the cursor class
 * @param xCoordinate represents the x position of the cursor on the Forte window
 */

    public Cursor(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    /**
     * Used to assign an image to the cursor class
     * @param pointer type is a PImage
     */

    public void loadImages(PImage pointer) {
        this.pointer = pointer;
    }
    /**
     * Main logic for moving the cursor is handled here.
     * @param xDist increments the cursor's x position by it's value
     */

    public void tick(float xDist) {
        this.xCoordinate += xDist;
        if (this.xCoordinate >= 540) {
            this.xCoordinate = 49;
        }
    }

    /**
     * The draw method for the cursor class, to be used inside the draw() method of the Forte App
     * @param app Main Forte app
     */

    public void draw(PApplet app) {
        app.image(pointer, xCoordinate, 59);
    }
    /**
     * Resets the cursor to it's default position on the window
     */

    public void resetCursor() {
        this.xCoordinate = 49;
    }
}