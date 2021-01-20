package piano;

import org.junit.Test;
import static org.junit.Assert.*;

public class FileHandlerTest {

    @Test
    public void testLoadBadFile() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        FileHandler filer = new FileHandler(grid, player);

        assertFalse(filer.load("bad_file.txt"));
        assertFalse(filer.load(null));
        
    }
    @Test
    public void testSaveFile() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        FileHandler filer = new FileHandler(grid, player);

        assertFalse(filer.save(null));
        assertTrue(filer.save("some_file.txt"));
    }

    @Test
    public void loadValidSavedFile() {
        Grid grid = new Grid(0,0);
        
        Player player = new Player(grid);
        FileHandler filer = new FileHandler(grid, player);

        grid.enableBlock(5, 5);
        filer.save("some_file.txt");
        //disabling an already enabled block , calling enableBlock on the same block disables it
        grid.enableBlock(5, 5);
        filer.load("some_file.txt");
        assertTrue(grid.enabledBlocks[5][5]);
    }
    
    
}