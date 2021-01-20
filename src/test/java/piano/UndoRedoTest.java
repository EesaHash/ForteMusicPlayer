package piano;

import org.junit.Test;
import static org.junit.Assert.*;

public class UndoRedoTest {

    @Test
    public void testMakeCopy() {
        Grid grid = new Grid(0,0);
        UndoRedo test = new UndoRedo(grid);
        boolean[][] gridArrayCopy = test.makeCopy();
        //a deep copy is never the same object
        assertNotEquals(gridArrayCopy, grid.enabledBlocks);
    }
    
    @Test
    public void testUndo() {
        Grid grid = new Grid(0,0);
        UndoRedo test = new UndoRedo(grid);

        //Calling undo on an empty stack
        assertFalse(test.undo());
        
        //Adding to undo stack before modifying the grid
        test.addToUndoStack();
        grid.enableBlock(5, 5);

        assertTrue(test.undo());
        assertFalse(grid.enabledBlocks[5][5]);
    }

    @Test
    public void testRedo() {
        Grid grid = new Grid(0,0);
        UndoRedo test = new UndoRedo(grid);
        
        //Calling redo on an empty stack
        assertFalse(test.redo());
        test.addToUndoStack();
        grid.enableBlock(7, 8);
        
        //Adding to redo stack before undoing 
        test.addToRedoStack();
        assertTrue(test.undo());
        assertFalse(grid.enabledBlocks[7][8]);
        assertTrue(test.redo());
    }
    
    
}