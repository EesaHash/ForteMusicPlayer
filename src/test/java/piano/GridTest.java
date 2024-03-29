/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package piano;

import org.junit.Test;
import static org.junit.Assert.*;

public class GridTest {
    
    @Test
    public void testConstruction() {
        Grid grid = new Grid(60,75);
        assertNotNull(grid);
    }
    @Test
    public void testGridTick() {
        Grid grid = new Grid(60,75);
        grid.tick();
        int note = 72;
        for (int i = 0; i < grid.notes.length; i++) {
            for (int j = 0; j < grid.notes[i].length; j++) {
                assertEquals(note,grid.notes[i][j]);
            }
            note--;
        }
    }

    @Test
    public void testEnabledBlocks() {
        Grid grid = new Grid(60,75);
        grid.enableBlock(0, 0);
        assertTrue(grid.enabledBlocks[0][0]);
        //Enable a corner block
        grid.enableBlock(31,12);
        assertTrue(grid.enabledBlocks[12][31]);
        //Enable invalid block (out of range)
        assertFalse(grid.enableBlock(50,0));
        //Enable all blocks
        for (int i = 0; i < grid.enabledBlocks.length; i++) {
            for (int j = 0; j < grid.enabledBlocks[i].length; j++) {
                if (!grid.enabledBlocks[i][j]) {
                    grid.enableBlock(j, i);
                }
                assertTrue(grid.enabledBlocks[i][j]);
            }
        }
    }

    @Test
    public void testResetGrid() {
        Grid grid = new Grid(60,75);
        //Enabling all blocks then resetting
        for (int i = 0; i < grid.enabledBlocks.length; i++) {
            for (int j = 0; j < grid.enabledBlocks[i].length; j++) {
                grid.enableBlock(j, i);
            }
        }
        grid.resetGrid();
        for (int i = 0; i < grid.enabledBlocks.length; i++) {
            for (int j = 0; j < grid.enabledBlocks[i].length; j++) {
                assertFalse(grid.enabledBlocks[i][j]);
            }
        }
    }

    @Test
    public void testSetGrid() {
        Grid grid = new Grid(60,75);
        boolean[][] array = new boolean[13][32];
        array[12][5] = true;
        grid.setGrid(array);
        assertTrue(grid.enabledBlocks[12][5]);
        assertSame(array,grid.enabledBlocks);
    }
    
}
