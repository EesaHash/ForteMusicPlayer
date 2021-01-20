package piano;

import org.junit.Test;
import static org.junit.Assert.*;
import processing.core.PApplet;

public class AppTest {

    @Test
    public void testSkeletonRender(){
        //To test the skeleton class since it's all drawing
        App app = new App();
        PApplet.runSketch(new String[]{"--location=0,0", ""}, app);
    }

    @Test
    public void testChangeInstrumentButton() {
        App app = new App();
        app.noLoop();
        assertEquals(0,app.player.chosenInstrumentIndex);
        //next button
        app.mouseHelper(366,5);
        assertEquals(1,app.player.chosenInstrumentIndex);
        //prev button
        app.mouseHelper(276,5);
        assertEquals(0,app.player.chosenInstrumentIndex);
    }

    @Test
    public void testPlayStop() {
        App app = new App();
        app.loop();
        while(app.isLooping()){
            app.controls.play();
            assertTrue(Player.isPlaying);
            app.controls.stop();
            break;
        }
        assertFalse(Player.isPlaying);
    }

    @Test
    public void testPlayPause(){
        App app = new App();
        app.loop();
        while(app.isLooping()){
            app.controls.play();
            //Pressing play again pauses
            if (app.player.col == 2)
            {
                app.controls.play();
                app.noLoop();
            }
        }
        //check to see if paused at the right column
        assertEquals(2,app.player.col);
    }

    @Test
    public void testGrid(){
        App app = new App();
        app.loop();
        while(app.isLooping()){
            app.controls.play();
            //Pressing play again pauses
            if (app.player.col == 2)
            {
                app.controls.play();
                app.noLoop();
            }
        }
        //check to see if paused at the right column
        assertEquals(2,app.player.col);
    }

}