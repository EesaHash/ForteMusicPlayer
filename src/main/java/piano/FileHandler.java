package piano;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * The FileHandler class is designed to perform various I/O operations for the Forte App
 * Methods useful are save() and load()
 */

public class FileHandler {

    private Grid grid;
    private Player player;

    private PImage saveSprite;
    private PImage loadSprite;

    /**
     * Default constructor for FileHandler
     * @param grid is a Grid Object
     * @param player is a Player Object
     */

    public FileHandler(Grid grid, Player player) {
        this.grid = grid;
        this.player = player;
    }

    /**
     * Loads Images for the FileHandler class and is a prerequisite if draw() is intended to be used
     * @param saveSprite
     * @param loadSprite
     */

    public void loadImages(PImage saveSprite, PImage loadSprite) {
        this.saveSprite = saveSprite;
        this.loadSprite = loadSprite;
    }
    /**
     * Method to save the current state of the Grid to an external source, to be specific a "txt" file
     * @param filename
     * @return returns "true" if the file is saved successfully or "false" incase it fails or an exception occurs
     */

    public boolean save(String filename) {
        if (filename == null) {
            return false;
        }
        try {
            FileWriter file = new FileWriter(filename);
            for (int i = 0; i < grid.enabledBlocks.length; i++) {
                for (int j = 0; j < grid.enabledBlocks[i].length; j++) {
                    if (grid.enabledBlocks[i][j]) {
                        file.write("1");
                    } else {
                        file.write("0");
                    }
                }
                file.write("\n");
                if (i == grid.enabledBlocks.length - 1) {
                    file.write(new Integer(player.chosenInstrumentIndex).toString());
                }
            }
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Method to load a previously saved file or a custom one (if it exists)
     * @param filename
     * @return "true" is returned if the method is successful else "false" is returned if an exception occured or an
     * invalid filename was passed
     */

    public boolean load(String filename) {
        if (filename == null) {
            return false;
        }
        try {
            Scanner scan = new Scanner(new File(filename));
            grid.resetGrid();
            int row = 0;
            while (scan.hasNextLine()) {
                String s = scan.nextLine();
                if (row < grid.enabledBlocks.length) {
                    int[] vals = Stream.of(s.split("")).mapToInt(Integer::parseInt).toArray();
                    for (int col = 0; col < grid.enabledBlocks[row].length; col++) {
                        if (vals[col] == 1) {
                            grid.enabledBlocks[row][col] = true;
                        } else {
                            grid.enabledBlocks[row][col] = false;
                        }
                    }
                }
                row++;
            }
            String s = "";
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNextLine()) {
                s = sc.nextLine();
            }
            int i = Integer.parseInt(s);
            player.setInstrument(i);
            grid.tick();
            sc.close();
            scan.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }

    }

    /**
     * To be used to be inside the main App's draw() method to display graphics
     * @param app
     */

    public void draw(PApplet app) {
        app.image(saveSprite, 140, 5);
        app.image(loadSprite, 185, 5);
    }


}