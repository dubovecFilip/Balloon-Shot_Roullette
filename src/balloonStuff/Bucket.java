package balloonStuff;

import fri.shapesge.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Bucket {

    private final boolean lowRes;
    private ArrayList<Image> balloonsList = new ArrayList<>();
    private ArrayList<Balloon> balloons = new ArrayList<>();
    private final Image balloonBackground = new Image("resources/Game/balloonPlate.png");
    private final Image bucketImage = new Image("resources/Game/bucket.png");
    private boolean showNext = false;

    public Bucket(boolean lowRes) {
        this.lowRes = lowRes;
        this.balloonBackground.changePosition(0, 0);
        this.bucketImage.changePosition(0, 0);
    }

    public void showBucket() {
        this.bucketImage.makeVisible();
    }

    public void imageSizeChange(boolean status) {
        if (status) {
            this.bucketImage.changeImage("resources/Game/bucketS.png");
        } else {
            this.bucketImage.changeImage("resources/Game/bucket.png");
        }

    }

    public void showBalloons(int x, int y) {
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
                xN += 32;
            } else {
                Image balloonImage;
                if (this.lowRes) {
                    balloonImage = new Image("resources/Game/balloon_B_l.png", xN, yN);
                } else {
                    balloonImage = new Image("resources/Game/balloon_B.png", xN, yN);
                }
                balloonImage.makeVisible();
                this.balloonsList.add(balloonImage);
                xN += 32;
            }
        }
    }

    public void hideBalloons() {
        for (Image balloon : this.balloonsList) {
            balloon.makeInvisible();
            this.balloonBackground.makeInvisible();
        }
    }

    public ArrayList<Balloon> getBalloons() {
        return this.balloons;
    }

    public void shuffle() {
        Collections.shuffle(this.balloons);
    }

    public Balloon takeAndThrow() {
        return this.balloons.removeFirst();
    }

    public void setShowNext(boolean input) {
        this.showNext = input;
    }

    public boolean canShowNext() {
        return this.showNext;
    }

    public Balloon showNext() {
        return this.balloons.getFirst();
    }

    public void empty() {
        this.balloons = new ArrayList<>();
    }

    public int getNumberOfBalloons() {
        if (this.balloons == null) {
            return -1;
        }
        return this.balloons.size();
    }

    public int numberOfGlueBalloons() {
        int counter = 0;
        for (Balloon balloon : this.balloons) {
            if (balloon.getType()) {
                counter++;
            }
        }
        return counter;
    }

    public void reset() {
        this.hideBalloons();
        File file = new File("resources/combinations.txt");
        try {
            Scanner scanner = new Scanner(file);
            Random rand = new Random();
            int whatLine = rand.nextInt(10);
            //System.out.println(whatLine + 1);
            for (int i = 0; i < whatLine; i++) {
                scanner.nextLine();
            }
            int waterB = scanner.nextInt();
            int glueB = scanner.nextInt();
            //System.out.println(waterB + " " + glueB);
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
