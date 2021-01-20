package piano;

import org.junit.Test;
import static org.junit.Assert.*;

public class CursorTest {

    @Test
    public void testConstruction() {
        Cursor cursor = new Cursor(49);
        assertNotNull(cursor);
    }

    @Test 
    public void testPosition() {
        Cursor cursor = new Cursor(49);
        assertEquals(49,(int) cursor.xCoordinate);
    }

    @Test
    public void testResetCursor() {
        Cursor cursor = new Cursor(61);
        cursor.resetCursor();
        assertEquals(49,(int) cursor.xCoordinate);
    }

    @Test
    public void testTick() {
        Cursor cursor = new Cursor(61);
        cursor.tick(12);
        assertEquals(61+12,(int) cursor.xCoordinate);
        //Moving beyond boundary should reset it
        cursor.xCoordinate = 540;
        cursor.tick(1);
        assertEquals(49,(int) cursor.xCoordinate);
    }
    
}