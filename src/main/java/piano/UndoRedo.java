package piano;

import java.util.EmptyStackException;
import java.util.Stack;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * The undo and redo implementation for the App
 * Modifies the grid's state 
 */

public class UndoRedo {

    private Stack<boolean[][]> undoStack;
    private Stack<boolean[][]> redoStack;

    private Grid grid;

    private PImage undoActiveSprite;
    private PImage redoActiveSprite;

    /**
     * Default constructor for the UndoRedo class
     * @param grid A Grid Object, manipulation of the grid is required
     */
    public UndoRedo(Grid grid) {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.grid = grid;
    }

    /**
     * Loads the images for the undo and redo sprites
     * @param undoActiveSprite Undo sprite
     * @param redoActiveSprite Redo sprite
     */

    public void loadImages(PImage undoActiveSprite, PImage redoActiveSprite) {
        this.undoActiveSprite = undoActiveSprite;
        this.redoActiveSprite = redoActiveSprite;
    }

    /**
     * Draws the loaded images
     * To be used in the draw() method of the main App
     * @param app
     */

    public void draw(PApplet app) {
        app.image(undoActiveSprite, 455, 10);
        app.image(redoActiveSprite, 500, 10);
    }

    /**
     * Adds the copied current state of the grid to the undo stack
     */
    public void addToUndoStack() {
        undoStack.add(makeCopy());
    }

    /**
     * Adds the copied current state of the grid to the redo stack
     */

    public void addToRedoStack() {
        redoStack.add(makeCopy());
    }

    /**
     * Restores the grid to the present state if the user "undid" soemthing
     * @return returns true if successful otherwise false if the redostack is already empty
     */
    public boolean redo() {
        try {
            if (!makeCopy().equals(redoStack.peek())) {
                undoStack.add(makeCopy());
                grid.setGrid(redoStack.pop());
                return true;
            }
            return false;
        } catch (EmptyStackException e) {
            return false;
        }
    }
    /**
     * Restores the grid to the previous state if the user modified the grid
     * @return returns true if successful otherwise false if the undostack is already empty
     */
    public boolean undo() {
        try {
            if (!makeCopy().equals(undoStack.peek())) {
                redoStack.add(makeCopy());
                grid.setGrid(undoStack.pop());
                return true;
            }
            return false;
        } catch (EmptyStackException e) {
            return false;
        }
    }
/**
 * Makes a deep copy of the current grid
 * To be used by undo and redo methods
 * @return The copied boolean 2D array is returned
 */
    public boolean[][] makeCopy() {
        boolean[][] current = grid.getEnabledBlocks();
        boolean[][] newArray = new boolean[13][32];
        for (int row = 0; row < current.length; row++) {
            for (int col = 0; col < current[row].length; col++) {
                if (current[row][col]) {
                    newArray[row][col] = true;
                } else {
                    newArray[row][col] = false;
                }
            }
        }
        return newArray;
    }
}