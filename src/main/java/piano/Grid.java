package piano;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * The main Grid for the Forte app. Used to keep track of what notes to play and stores their corresponding
 * MIDI values
 * Has two 2D arrays, one to keep track of the "enabled notes" and the other to store the MIDI values of the notes
 */

public class Grid {

    private int x;
    private int y;
    public int[][] notes;
    public boolean[][] enabledBlocks;

    private PImage sprite;
    private PImage block;
    private PImage clearSprite;

    /**
     * Default Constructor for the Grid object
     * @param x the x coordinate of the grid's sprite on the App window
     * @param y the y coordinate of the grid's sprite on the App window
     */
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
        this.notes = new int[13][32];
        this.enabledBlocks = new boolean[13][32];

    }
    /**
     * This method loads the images that are to be drawn by the draw() method
     * Is a prequisite if the draw() method is to be invoked
     * @param sprite
     * @param block
     * @param clearSprite
     */

    public void loadImages(PImage sprite, PImage block, PImage clearSprite) {
        this.sprite = sprite;
        this.block = block;
        this.clearSprite = clearSprite;
    }

    /**
     * The main grid logic that assigns a note to each block
     */

    public void tick() {
        int note = 72;
        for (int i = 0; i < notes.length; i++) {
            for (int j = 0; j < notes[i].length; j++) {
                notes[i][j] = note;
            }
            note--;
        }
    }
    /**
     * The main draw() method for the grid app to be used by the main App
     * Images need to be loaded beforehand
     * @param app
     */

    public void draw(PApplet app) {
        app.image(this.sprite, this.x, this.y);
        app.image(clearSprite, 95, 5);
        x = 60;
        y = 75;
        for (int row = 0; row < enabledBlocks.length; row++) {
            for (int col = 0; col < enabledBlocks[row].length; col++) {
                if (enabledBlocks[row][col] == true) {
                    app.fill(64, 224, 208);
                    app.image(block, x + 1, y + 1);
                } else {
                    app.fill(0);
                    app.rect(x + 1, y + 1, 13, 18);
                }
                x = x + 15;
            }
            y = y + 20;
            x = 60;
        }
    }

    /**
     * Enables the "block" on the grid also known as the cell of a 2D array
     * Also used to disable an already enabled block/note
     * @param j column position of the block in the array
     * @param i row position of the block in the array
     * @return returns true if has been successfully enabled or disabled
     */

    public boolean enableBlock(int j, int i) {
        if (j > enabledBlocks[0].length || i > enabledBlocks.length) {
            return false;
        }
        if (enabledBlocks[i][j] == false) {
            enabledBlocks[i][j] = true;
        } else {
            enabledBlocks[i][j] = false;
        }
        return true;
    }
    /**
     * Resets the grid such that no notes are enabled and everything is back to the state when the object was first
     * constructed
     * @return "true" is always returned
     */

    public boolean resetGrid() {
        for (int i = 0; i < enabledBlocks.length; i++) {
            for (int j = 0; j < enabledBlocks[i].length; j++) {
                enabledBlocks[i][j] = false;
            }
        }
        return true;
    }

    /**
     * Used to manually enable different blocks on the grid.
     * Handy for the FileHandler when loading saved files
     * @param customBlocks
     */

    public void setGrid(boolean[][] customBlocks) {
        this.resetGrid();
        this.enabledBlocks = customBlocks;
    }

    /**
     * Partially getter method, can be used if attributes are private
     * @return returns the current enabledBlocks 2D array of the grid
     */

    public boolean[][] getEnabledBlocks() {
        return this.enabledBlocks;
    }

}