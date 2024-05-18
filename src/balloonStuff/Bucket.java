package balloonStuff;

import fri.shapesge.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * The Bucket class represents a container for balloons in the game.
 * It allows managing balloons, changing their display, shuffling them, and retrieving information about their state.
 */
public class Bucket {

    // Indicator for low resolution images
    private final boolean lowRes;

    // List of balloon images
    private ArrayList<Image> balloonsList = new ArrayList<>();

    // List of Balloon objects
    private ArrayList<Balloon> balloons = new ArrayList<>();

    // Background image for balloons
    private final Image balloonBackground = new Image("resources/Game/balloonPlate.png");

    // Image of the bucket
    private final Image bucketImage = new Image("resources/Game/bucket.png");

    // Indicator for whether the next balloon can be displayed
    private boolean showNext = false;

    /**
     * Constructor for the Bucket class, initializing the state of the bucket.
     * @param lowRes Indicator for low resolution images for the balloons
     */
    public Bucket(boolean lowRes) {
        this.lowRes = lowRes;
        this.balloonBackground.changePosition(0, 0);
        this.bucketImage.changePosition(0, 0);
        this.bucketImage.makeVisible();
    }

    /**
     * Method to change the bucket image based on the status.
     * @param status If true, a low resolution image is used.
     */
    public void imageSizeChange(boolean status) {
        if (status) {
            this.bucketImage.changeImage("resources/Game/bucketS.png");
        } else {
            this.bucketImage.changeImage("resources/Game/bucket.png");
        }

    }

    /**
     * Display balloons at the start of the game.
     * @param x Horizontal position for display
     * @param y Vertical position for display
     */
    public void showBalloonsOnStart(int x, int y) {
        int xN = x * 32;
        int yN = y * 32;
        this.balloonBackground.makeVisible();
        for (Balloon balloon : this.balloons) {
            if (balloon.getType()) {
                Image balloonImage;
                if (this.lowRes) {
                    balloonImage = new Image("resources/Game/balloon_R_l.png", xN, yN);
                } else {
                    balloonImage = new Image("resources/Game/balloon_R.png", xN, yN);
                }
                balloonImage.makeVisible();
                this.balloonsList.add(balloonImage);
            } else {
                Image balloonImage;
                if (this.lowRes) {
                    balloonImage = new Image("resources/Game/balloon_B_l.png", xN, yN);
                } else {
                    balloonImage = new Image("resources/Game/balloon_B.png", xN, yN);
                }
                balloonImage.makeVisible();
                this.balloonsList.add(balloonImage);
            }
            xN += 32;
        }
    }

    /**
     * Hide all balloons.
     */
    public void hideBalloons() {
        for (Image balloon : this.balloonsList) {
            balloon.makeInvisible();
        }
        this.balloonBackground.makeInvisible();
    }

    /**
     * Shuffle the balloons in the list.
     */
    public void shuffle() {
        Collections.shuffle(this.balloons);
    }

    /**
     * Return the first balloon from the list and remove it.
     * @return The first balloon from the list
     */
    public Balloon takeAndThrow() {
        return this.balloons.removeFirst();
    }

    /**
     * Set whether the next balloon can be shown.
     * @param input True if the next balloon can be shown
     */
    public void setShowNext(boolean input) {
        this.showNext = input;
    }

    /**
     * Check if the next balloon can be shown.
     * @return True if the next balloon can be shown
     */
    public boolean canShowNext() {
        return this.showNext;
    }

    /**
     * Get the next balloon in the list.
     * @return The first balloon in the list
     */
    public Balloon getNext() {
        return this.balloons.getFirst();
    }

    /**
     * Empty the list of balloons.
     */
    public void empty() {
        this.balloons = new ArrayList<>();
    }

    /**
     * Check if the list of balloons is empty.
     * @return True if the list of balloons is empty
     */
    public boolean isEmpty() {
        return this.balloons.isEmpty();
    }

    /**
     * Get the number of balloons in the bucket.
     * @return The number of balloons in the bucket
     */
    public int getNumberOfBalloons() {
        if (this.balloons == null) {
            return -1;
        }
        return this.balloons.size();
    }

    /**
     * Get the number of "bad" (hurtful) balloons in the bucket.
     * @return The number of "bad" (hurtful) balloons in the bucket
     */
    public int getNumberOfBadBalloons() {
        int counter = 0;
        for (Balloon balloon : this.balloons) {
            if (balloon.getType()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Reset the bucket and load new balloons from a text file.
     */
    public void reset() {
        this.hideBalloons();
        File file = new File("resources/combinations.txt");
        try {
            Scanner scanner = new Scanner(file);
            Random rand = new Random();
            int whatLine = rand.nextInt(10);
            for (int i = 0; i < whatLine; i++) {
                scanner.nextLine();
            }
            int waterB = scanner.nextInt();
            int glueB = scanner.nextInt();
            for (int i = 0; i < waterB; i++) {
                Balloon balloon = new Balloon(false);
                this.balloons.add(balloon);
            }
            for (int i = 0; i < glueB; i++) {
                Balloon balloon = new Balloon(true);
                this.balloons.add(balloon);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
