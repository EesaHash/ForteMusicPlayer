package piano;

import org.junit.Test;
import static org.junit.Assert.*;



public class PlayerTest {
    @Test
    public void testPlayerConstruction() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        assertNotNull(player);
    }
    @Test
    public void testCheckDefaultInstrument(){
        //Should be a piano or 0
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        assertEquals(0,player.chosenInstrumentIndex);
    }

    @Test
    public void testChangeInstrument() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        player.changeInstrument(1);
        player.changeInstrument(1);
        assertEquals(2,player.chosenInstrumentIndex);
        player.changeInstrument(-1);
        assertEquals(1,player.chosenInstrumentIndex);
        player.changeInstrument(1);
        player.changeInstrument(1);
        player.changeInstrument(1);
        player.changeInstrument(1);
        assertEquals(3, player.chosenInstrumentIndex);
        assertFalse(player.changeInstrument(2));
    }

    @Test
    public void testManuallySetInstrument() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        assertTrue(player.setInstrument(2));
        assertEquals(2,player.chosenInstrumentIndex);
        assertFalse(player.setInstrument(5));
    }

    @Test
    public void testResetTrack() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        assertTrue(Player.isAtStart);
        player.row = 5;
        player.col = 6;
        player.resetTrack();
        assertEquals(0,player.row);
        assertEquals(0,player.col);
    }

    public void testCanRun() {
        Grid grid = new Grid(0,0);
        Player player = new Player(grid);
        Thread audioThread = new Thread(player);
        Player.isPlaying = true;
        audioThread.run();
        assertTrue(Player.threadActive);
        audioThread.interrupt();
        assertFalse(Player.threadActive);
    }
}