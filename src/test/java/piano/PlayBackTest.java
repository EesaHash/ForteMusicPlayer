package piano;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayBackTest {

    @Test
    public void checkAudioThreadCreation() {
        Player player = new Player(new Grid(0,0));
        PlaybackControls ps = new PlaybackControls(player);
        assertTrue(ps.audioThread.getState().toString().equals("NEW"));
    }

    @Test
    public void testSetNextInstrument() {
        Player player = new Player(new Grid(0,0));
        PlaybackControls ps = new PlaybackControls(player);
        assertTrue(ps.setNextInstrument());
        assertEquals(1,player.chosenInstrumentIndex);
        ps.setNextInstrument();
        ps.setNextInstrument();
        ps.setNextInstrument();
        assertFalse(ps.setNextInstrument());
    }

    @Test
    public void testSetPreviousInstrument() {
        Player player = new Player(new Grid(0,0));
        PlaybackControls ps = new PlaybackControls(player);
        assertFalse(ps.setPreviousInstrument());
        ps.setNextInstrument();
        assertTrue(ps.setPreviousInstrument());
}
}