package piano;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * Used make the code more readable and cocise
 * Purely for displaying images
 */
public class SkeletonRender {

    private PImage bannerSprite;
    private PImage backSprite;
    private PImage middleBannerSprite;
    private PImage keyboardSprite;

    /**
     * Loads images for the draw() method
     * @param bannerSprite The banner for the app
     * @param backSprite The background for the buttons
     * @param middleBannerSprite The middle banner
     * @param keyboardSprite The keyboard 
     */

    public void loadImages(PImage bannerSprite, PImage backSprite, PImage middleBannerSprite, PImage keyboardSprite) {
        this.bannerSprite = bannerSprite;
        this.backSprite = backSprite;
        this.middleBannerSprite = middleBannerSprite;
        this.keyboardSprite = keyboardSprite;
    }

    /**
     * Used for displaying graphics. To be invoked by the main App.
     * @param app
     */

    public void draw(PApplet app) {
        app.image(middleBannerSprite, 0, 0);
        app.image(bannerSprite, 0, 0);
        app.image(backSprite, 5, 5);
        app.image(backSprite, 50, 5);
        app.image(backSprite, 95, 5);
        app.image(backSprite, 140, 5);
        app.image(backSprite, 185, 5);
        app.image(backSprite, 275, 5);
        app.image(backSprite, 365, 5);
        app.image(backSprite, 320, 5);
        app.image(keyboardSprite, 0, 75);
        app.image(backSprite, 450, 5);
        app.image(backSprite, 495, 5);

    }

}