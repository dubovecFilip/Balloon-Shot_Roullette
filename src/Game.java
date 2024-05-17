import balloonStuff.Balloon;
import balloonStuff.Bucket;

import entities.Entity;
import entities.characters.Thief;
import entities.characters.King;
import entities.characters.Char3;
import items.Item;

import fri.shapesge.Circle;
import fri.shapesge.Image;
import fri.shapesge.Manager;
import fri.shapesge.Square;

import javax.swing.border.BevelBorder;
import java.util.Random;

public class Game {

    private final Image background = new Image("resources/Game/bg.png", 0, 0);
    private final Image useButton = new Image("resources/Game/bgUse.png", 0, 0);
    private final Image abilityButton = new Image("resources/Game/bgAbility.png", 0, 0);
    private final Image throwButtons = new Image("resources/game/selectPlayer.png", 0, 0);
    private final Circle thrownBalloonImage = new Circle(448 - 16, 304 - 16);
    private final Circle nextBalloonImage = new Circle(448 - 16, 304 - 16);
    private final Bucket bucket;
    private final Random random = new Random();
    private final Entity user;
    private final Entity enemy;
    private final Behavior behavior;
    private final Square selection = new Square();
    private final boolean lowRes;
    private int selectionTimer = 0;
    private boolean isSelected = false;
    private Item selectedItem = null;
    private int counter = 0;
    private int enemyCounter = 0;
    private boolean abilityReady = false;
    private boolean readyToThrow = false;
    private Balloon thrownBalloon;
    private int itemIndex;
    private boolean playersTurn = true;


    public Game(int user, int enemy, boolean lowRes) {
        this.lowRes = lowRes;
        this.bucket = new Bucket(this.lowRes);
        this.background.makeVisible();
        this.bucket.showBucket();
        Manager manager = new Manager();
        manager.manageObject(this);
        this.selection.changeSize(36);
        this.selection.changeColor("gray");
        //int lives = this.random.nextInt(3) + 1;

        switch (user) {
            case 3:
                this.user = new Char3(this.bucket);
                break;
            case 2:
                this.user = new King(this.bucket);
                break;
            default:
                this.user = new Thief(this.bucket);
                break;
        }

        switch (enemy) {
            case 3:
                this.enemy = new Char3(this.bucket);
                break;
            case 2:
                this.enemy = new King(this.bucket);
                break;
            default:
                this.enemy = new Thief(this.bucket);
                break;
        }

        this.behavior = new Behavior(this.enemy, this.user, this.bucket);
        System.out.println("User: " + this.user + " Enemy: " + this.enemy);
        this.reset();
        this.isSelected = false;
    }

    public void reset() {
        int randomLives = this.random.nextInt(3) + 2;
        this.user.reset(randomLives);
        this.enemy.reset(randomLives);
        this.abilityReady = false;
        this.outOfBalloons();
    }

    public void outOfBalloons() {
        int numberOfItems = this.random.nextInt(3) + 1;
        this.user.generateInventoryItems(numberOfItems, this.enemy, this.lowRes);
        this.enemy.generateInventoryItems(numberOfItems, this.user, this.lowRes);
        this.bucket.empty();
        this.bucket.reset();
        this.bucket.shuffle();
        this.bucket.showBalloons(5, 9);
        this.bucket.shuffle();
        this.counter = 0;
        this.thrownBalloonImage.makeInvisible();
    }

    public void tik() {
        this.counter += 1;
        if (this.counter >= 300) {
            this.bucket.hideBalloons();
            if (this.user != null) {
                this.user.drawLives(15, 10);
                this.user.unDrawInventory();
                this.user.drawInventory(6, 15);
                if (this.user.getAbility() == 5) {
                    this.abilityButton.makeVisible();
                    this.abilityReady = true;
                }
            }
            if (this.enemy != null) {
                this.enemy.drawLives(15, 8);
                this.enemy.drawInventory(6, 1);
            }
            if (this.bucket != null && this.user != null && this.enemy != null && !this.readyToThrow) {
                if (this.bucket.getNumberOfBalloons() == 0) {
                    this.outOfBalloons();
                }
            }

            if (this.bucket.canShowNext()) {
                if (this.bucket.showNext().getType()) {
                    this.nextBalloonImage.changeColor("red");
                } else {
                    this.nextBalloonImage.changeColor("blue");
                }
                this.nextBalloonImage.makeVisible();
                this.bucket.setShowNext(false);
            }

            this.bucket.imageSizeChange(this.readyToThrow);

            if (this.readyToThrow) {
                this.throwButtons.changePosition(0, 0);
                this.throwButtons.makeVisible();
            } else if (!this.readyToThrow) {
                this.throwButtons.makeInvisible();
            }

            this.selectionTimer++;
            if (this.isSelected) {
                this.useButton.makeVisible();
                int clock = 70;
                if (this.selectionTimer % clock == 0) {
                    this.selection.makeInvisible();
                } else if (this.selectionTimer % clock == clock / 2) {
                    if (this.user != null) {
                        this.user.unDrawInventory();
                        this.selection.makeVisible();
                        this.user.drawInventory(6, 15);
                    }
                }
            }
            if (!this.isSelected) {
                this.useButton.makeInvisible();
            }
//            System.out.println(this.isSelected);
//            System.out.println("Players turn");
//            System.out.println(this.playersTurn);
//            System.out.println(this.enemyCounter);
        }
        if (!this.playersTurn) {
            this.enemyCounter += 1;
            if (this.enemyCounter > 200 && this.counter >= 300) {
                if (this.bucket.getNumberOfBalloons() != 0) {
                    this.behavior.makeMove();
                    this.thrownBalloon = this.behavior.getResult().getThrownBalloon();
                    boolean thrownToSelf = this.behavior.getResult().isThrownToSelf();
                    System.out.println("Enemys turn");
                    this.enemyCounter = 0;
                    if (this.thrownBalloon.getType()) {
                        this.thrownBalloonImage.changeColor("red");
                    } else {
                        this.thrownBalloonImage.changeColor("blue");
                    }
                    if (thrownToSelf && this.thrownBalloon.getType() || !thrownToSelf && !this.thrownBalloon.getType()) {
                        this.playersTurn = true;
                    }
                } else {
                    this.bucket.reset();
                }
            }
        }
    }

    public void chooseCoords(int x, int y) {
        if (this.playersTurn) {
            //System.out.println("Chosen coords: " + x / 32 * 32 + " " + y / 32 * 32);
            x = x / 32;
            y = y / 32;
            if (!this.isSelected) {
                this.itemIndex = 0;
            }
            if (x == 6 && y == 15) {
                this.itemIndex = 1;
            } else if (x == 8 && y == 15) {
                this.itemIndex = 2;
            } else if (x == 10 && y == 15) {
                this.itemIndex = 3;
            } else if (x == 12 && y == 15) {
                this.itemIndex = 4;
            } else if (x == 6 && y == 17) {
                this.itemIndex = 5;
            } else if (x == 8 && y == 17) {
                this.itemIndex = 6;
            } else if (x == 10 && y == 17) {
                this.itemIndex = 7;
            } else if (x == 12 && y == 17) {
                this.itemIndex = 8;
            } else if ((x >= 6 && x < 13) && (y >= 6 && y < 13)) {
                //System.out.println("Bucket");
                if (!this.readyToThrow) {
                    this.thrownBalloon = this.bucket.takeAndThrow();
                }

                this.readyToThrow = true;

            } else if (x == 4 && (y == 1 || y == 2) && this.readyToThrow) {
                this.nextBalloonImage.makeInvisible();
                this.enemy.receiveBalloon(this.thrownBalloon);
                this.thrownBalloonImage.makeInvisible();
                if (this.thrownBalloon.getType()) {
                    this.thrownBalloonImage.changeColor("red");
                } else {
                    this.thrownBalloonImage.changeColor("blue");
                    this.playersTurn = false;
                }
                this.thrownBalloonImage.makeVisible();
                this.readyToThrow = false;
            } else if (x == 4 && (y == 16 || y == 17) && this.readyToThrow) {
                this.user.receiveBalloon(this.thrownBalloon);
                this.thrownBalloonImage.makeInvisible();
                if (this.thrownBalloon.getType()) {
                    this.thrownBalloonImage.changeColor("red");
                    this.playersTurn = false;
                } else {
                    this.thrownBalloonImage.changeColor("blue");
                }
                this.thrownBalloonImage.makeVisible();
                this.readyToThrow = false;
            } else if (x == 0 && y == 0) {
                this.user.getInventory().removeItem(0);
            } else if (x >= 1 && x <= 4 && y == 8 && this.isSelected) {
                //System.out.println("Use");
                this.selectedItem.use();
                System.out.printf("Item used: %s\n", this.selectedItem + " " + this.itemIndex);
                this.user.removeItem(this.itemIndex - 1);
                this.user.drawInventory(6, 15);
                this.selectedItem = null;
                this.itemIndex = 0;
            } else if (x >= 1 && x <= 4 && y == 10 && this.abilityReady) {
                this.user.useAbility(this.enemy);
                this.abilityButton.makeInvisible();
                this.abilityReady = false;
            } else {
                this.selection.makeInvisible();
                this.selectedItem = null;
                this.isSelected = false;
                this.itemIndex = 0;
            }
            System.out.println(this.itemIndex);
            if (this.itemIndex != 0 && this.user.getInventory().getItem(this.itemIndex) != null) {
                this.selectedItem = this.user.getInventory().getItem(this.itemIndex);
                this.isSelected = true;
                //System.out.println("Item");
            } else {
                this.selectedItem = null;
                this.isSelected = false;
            }

            if (this.isSelected) {
                this.selection.changePosition(x * 32 - 2, y * 32 - 2);
            } else {
                this.selection.makeInvisible();
            }
        }
    }
}