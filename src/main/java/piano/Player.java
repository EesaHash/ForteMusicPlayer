package piano;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * Main Player class that implements Runnable
 * To be controlled by the PlayBackControls class
 * The synth and the MIDI channel reside here
 */

public class Player implements Runnable {

    private MidiChannel instrument;

    public static boolean isPlaying = false;
    public static boolean threadActive = false;
    public static boolean isAtStart = true;

    private Grid grid;

    private PImage pianoSprite;
    private PImage marimbaSprite;
    private PImage banjoSprite;
    private PImage saxophoneSprite;

    public int row;
    public int col;

    private int[] instruments;
    public int chosenInstrumentIndex;
/**
 * Default constructor for the player
 * @param grid Takes in a Grid Object to figure out what notes to play
 */
    public Player(Grid grid) {
        try {
            Synthesizer synth = MidiSystem.getSynthesizer();
            synth.open();
            instrument = synth.getChannels()[0];
        } catch (final MidiUnavailableException e) {
            e.printStackTrace();
        }
        this.grid = grid;
        this.row = 0;
        this.col = 0;
        this.instruments = new int[]{0, 12, 105, 65};
        chosenInstrumentIndex = 0;
        instrument.programChange(instruments[chosenInstrumentIndex]);
    }

    /**
     * Method for loading images for the player's MIDI channel's instruments
     * @param pianoSprite image for the piano
     * @param marimbaSprite image for the marimba
     * @param banjoSprite image for the banjo
     * @param saxophoneSprite image for the saxophone
     */

    public void loadImages(PImage pianoSprite, PImage marimbaSprite, PImage banjoSprite, PImage saxophoneSprite) {
        this.pianoSprite = pianoSprite;
        this.marimbaSprite = marimbaSprite;
        this.banjoSprite = banjoSprite;
        this.saxophoneSprite = saxophoneSprite;
    }

    /**
     * The draw method of this class to be used inside the App' draw method
     * Must loadImages before calling this
     * @param app
     */

    public void draw(PApplet app) {
        switch (chosenInstrumentIndex) {
            case 0:
                app.image(pianoSprite, 320, 5);
                break;

            case 1:
                app.image(marimbaSprite, 320, 5);
                break;

            case 2:
                app.image(banjoSprite, 320, 5);
                break;

            case 3:
                app.image(saxophoneSprite, 320, 5);
                break;
        }
        if (col == 0) {
            isAtStart = true;
        } else {
            isAtStart = false;
        }
    }
    /**
     * Used to reset the player to the start of the grid
     * Sets the column and row to 0
     */

    public void resetTrack() {
        col = 0;
        row = 0;
    }

    /**
     * The logic for playing all the sounds is here. A new sound plays every 250 ms which is the default tempo
     */
    public void tick() {
        while (isPlaying) {
            if (row == 13) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    instrument.allNotesOff();
                    break;
                }
                instrument.allNotesOff();
                row = 0;
                col++;
                if (col == 32) {
                    row = 0;
                    col = 0;
                }
            }
            if (grid.enabledBlocks[row][col] == true) {
                instrument.noteOn(grid.notes[row][col], 90);
            }
            if (row != 13) {
                row++;
            }
        }
    }
/**
 * The run method for a thread
 */
    @Override
    public void run() {
        threadActive = true;
        this.tick();
        threadActive = false;
    }
/**
 * Used to manually 'set' the MIDI channel to play another instrument
 * Useful for the FileHandler when saving or loading files (it also saves the instrument)
 * @param idx Used to select an instrument from the available instruments array
 * @return
 */
    public boolean setInstrument(int idx) {
        if (idx < 0 || idx >= instruments.length){
            return false;
        }
        chosenInstrumentIndex = idx;
        instrument.programChange(instruments[chosenInstrumentIndex]);
        return true;
    }

/**
 * Used to 'change' the MIDI channel to play a different instrument
 * @param step is the step size. Can only be +1 or -1
 * @return returns "true" if +1 or -1 are the step param, otherwise "false"
 */

    public boolean changeInstrument(int step) {
        if (step == 1 || step == -1) {
            if (chosenInstrumentIndex == this.instruments.length - 1) {
                if (step == 1) {
                    return false;
                }
            } else if (chosenInstrumentIndex == 0) {
                if (step == -1) {
                    return false;
                }
            }

            chosenInstrumentIndex += step;
            this.setInstrument(chosenInstrumentIndex);
            return true;
        }
        return false;
    }
}

