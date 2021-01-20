package piano;


import processing.core.PApplet;
import processing.core.PImage;

/**
 * Main class for handling playback in another Thread!
 * Runs the Player's run method inside a seperate thread
 * Also responsible for changing the instruments
 * It's like a remote for the player
 */

public class PlaybackControls {

    private PImage playSprite;
    private PImage stopSprite;
    private PImage pauseSprite;
    private PImage incrementSprite;
    private PImage decrementSprite;

    public Player player;
    public Thread audioThread;
/**
 * Main constructor for the playback controls
 * @param player the Player object
 */
    public PlaybackControls(Player player) {
        this.player = player;
        this.audioThread = new Thread(player);
    }

    /**
     * Used to load images for all the sprites in this class
     * @param playSprite the image for the play button
     * @param stopSprite the image for the stop button
     * @param pauseSprite the image to be displayed instead of the play button if the app is paused
     * @param incrementSprite the image for the "plus" button for the next instrument
     * @param decrementSprite the image for the "minus" button for the previous instrument
     */

    public void loadImages(PImage playSprite, PImage stopSprite, PImage pauseSprite, PImage incrementSprite, PImage decrementSprite) {
        this.playSprite = playSprite;
        this.stopSprite = stopSprite;
        this.pauseSprite = pauseSprite;
        this.incrementSprite = incrementSprite;
        this.decrementSprite = decrementSprite;
    }
    /**
     * The main draw() method for this class
     * Used only if the loadImages() method has been called and by the App
     * @param app The main App
     */

    public void draw(PApplet app) {
        app.image(incrementSprite, 365, 5);
        app.image(decrementSprite, 275, 5);
        if (!audioThread.isAlive()) {
            app.image(playSprite, 5, 5);
        }
        if (audioThread.isAlive()) {
            app.image(pauseSprite, 5, 5);
        }
        app.image(stopSprite, 50, 5);
    }
    /**
     * Starts the player in a new thread utilizing the power of multithreading before COMP2017!
     * Also pauses playback if the it's already playing 
     */

    public void play() {
        if (!audioThread.isAlive()) {
            audioThread = new Thread(player);
            audioThread.start();
        }
        if (Player.isPlaying) {
            Player.isPlaying = false;
        } else {
            Player.isPlaying = true;
        }
    }
    
    /**
     * Interrupts the audiothread to and stops playback
     */
    public void stop() {
        audioThread.interrupt();
        player.resetTrack();
        Player.isPlaying = false;
    }

    /**
     * Changes the player's instrument to the next one if available
     * @return returns "true" if successful
     */

    public boolean setNextInstrument() {
        if (player.changeInstrument(1)){
            return true;
        }
        return false;
    }

    /**
     * Changes the players instrument to the previous instrument if available
     * @return returns "true" if succesful or "false" otherwise
     */
    public boolean setPreviousInstrument() {
        if (player.changeInstrument(-1)) {
            return true;
        }
        return false;
    }
}