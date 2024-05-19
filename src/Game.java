import balloonStuff.Balloon;
import balloonStuff.Bucket;

import entities.Entity;
import entities.characters.Thief;
import entities.characters.King;
import entities.characters.Magician;
import items.Item;

import fri.shapesge.Circle;
import fri.shapesge.Image;
import fri.shapesge.Manager;
import fri.shapesge.Square;

import java.util.Random;

/**
 * The Game class represents the game.
 */
public class Game {

    // The image of the use button.
    private final Image useButton = new Image("resources/Game/bgUse.png", 0, 0);

    // The image of the ability button.
    private final Image abilityButton = new Image("resources/Game/bgAbility.png", 0, 0);

    // The image of the throw buttons.
    private final Image throwButtons = new Image("resources/game/selectPlayer.png", 0, 0);

    // The image of the end state.
    private final Image endState = new Image("resources/Game/youWon.png", 0, 0);

    // The image of the thrown balloon.
    private final Circle thrownBalloonImage = new Circle(448 - 16, 304 - 16);

    // The image of the next balloon.
    private final Circle nextBalloonImage = new Circle(448 - 16, 304 - 16);

    // The bucket associated with the current game.
    private final Bucket bucket;

    // Random generator.
    private final Random random = new Random();

    // The user entity.
    private final Entity user;

    // The enemy entity.
    private final Entity enemy;

    // The behavior of the enemy entity.
    private final Behavior behavior;

    // The selection square.
    private final Square selection = new Square();

    // True if the game is in low resolution, false otherwise.
    private final boolean lowRes;

    // The counter used for flickering the selected item for user.
    private int selectionTimer = 0;

    // True if an item is selected, false otherwise.
    private boolean isSelected = false;

    // The selected item.
    private Item selectedItem = null;

    // The counter used for the game.
    private int counter = 0;

    // The counter used for the enemy.
    private int enemyCounter = 0;

    // True if the ability is ready, false otherwise.
    private boolean abilityReady = false;

    // True if the player can throw, false otherwise.
    private boolean readyToThrow = false;

    // The thrown balloon.
    private Balloon thrownBalloon;

    // The index of the selected item.
    private int itemIndex;

    // True if it is the player's turn, false otherwise.
    private boolean playersTurn = true;

    // The counter used for the thrown balloon image.
    private int thrownBalloonImageTimer;

    // True if the game has ended, false otherwise.
    private boolean endGame = false;

    /**
     * Constructs a Game object with the specified user, enemy, and lowRes.
     * @param user the user character
     * @param enemy the enemy character
     * @param lowRes true if the game is in low resolution, false otherwise
     */
    public Game(int user, int enemy, boolean lowRes) {
        this.lowRes = lowRes;
        this.bucket = new Bucket(this.lowRes);
        Image background = new Image("resources/Game/bg.png", 0, 0);
        background.makeVisible();
        Manager manager = new Manager();
        manager.manageObject(this);
        this.selection.changeSize(36);
        this.selection.changeColor("gray");

        // Create the user and enemy characters based on the user and enemy values.
        switch (user) {
            case 3:
                this.user = new Magician(this.bucket, 18, 18);
                break;
            case 2:
                this.user = new King(this.bucket, 18, 18);
                break;
            default:
                this.user = new Thief(this.bucket, 18, 18);
                break;
        }

        switch (enemy) {
            case 3:
                this.enemy = new Magician(this.bucket, 18, 6);
                break;
            case 2:
                this.enemy = new King(this.bucket, 18, 6);
                break;
            default:
                this.enemy = new Thief(this.bucket, 18, 6);
                break;
        }

        this.behavior = new Behavior(this.enemy, this.user, this.bucket);
        this.reset();
        this.isSelected = false;
    }

    /**
     * Resets the game.
     */
    public void reset() {
        int randomLives = this.random.nextInt(3) + 2;
        this.user.reset(randomLives);
        this.enemy.reset(randomLives);
        this.abilityReady = false;
        this.outOfBalloons();
    }

    /**
     * When bucket is empty, generate items for both players and reset the bucket.
     */
    public void outOfBalloons() {
        int numberOfItems = this.random.nextInt(3) + 1;
        this.user.generateInventoryItems(numberOfItems, this.enemy, this.lowRes);
        this.enemy.generateInventoryItems(numberOfItems, this.user, this.lowRes);
        this.bucket.empty();
        this.bucket.reset();
        this.bucket.shuffle();
        this.bucket.showBalloonsOnStart(5, 9);
        this.bucket.shuffle();
        this.counter = 0;
        this.thrownBalloonImage.makeInvisible();
    }

    /**
     * Makes the game tick.
     * Game logic is here.
     */
    public void tik() {
        this.counter += 1;
        if (this.counter >= 300 && !this.endGame) {
            if (this.thrownBalloonImageTimer >= 50) {
                this.thrownBalloonImage.makeInvisible();
            } else {
                this.thrownBalloonImageTimer += 1;
            }
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
            if (this.user != null && this.enemy != null && !this.readyToThrow) {
                if (this.bucket.getNumberOfBalloons() == 0) {
                    this.outOfBalloons();
                }
            }

            if (this.bucket.canShowNext()) {
                if (this.bucket.getNext().getType()) {
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
        }

        if (this.user != null && this.user.getLives() <= 0) {
            this.endState.changeImage("resources/Game/youLost.png");
            this.endGame = true;
        } else if (this.enemy != null && this.enemy.getLives() <= 0) {
            this.endState.changeImage("resources/Game/youWon.png");
            this.endGame = true;
        }

        if (this.endGame) {
            this.endState.makeVisible();
        }

        if (!this.playersTurn && !this.endGame) {
            this.enemyCounter += 1;
            if (this.enemyCounter > 200 && this.counter >= 300) {
                if (this.bucket.getNumberOfBalloons() != 0) {
                    this.behavior.makeMove();
                    this.thrownBalloon = this.behavior.getResult().getThrownBalloon();
                    boolean thrownToSelf = this.behavior.getResult().isThrownToSelf();
                    this.enemyCounter = 0;
                    if (this.thrownBalloon.getType()) {
                        this.thrownBalloonImage.changeColor("red");
                    } else {
                        this.thrownBalloonImage.changeColor("blue");
                    }
                    this.thrownBalloonImage.makeVisible();
                    this.thrownBalloonImageTimer = 0;
                    if ((thrownToSelf && this.thrownBalloon.getType()) || (!thrownToSelf && !this.thrownBalloon.getType()) && this.user != null && !this.user.cannotMove()) {
                        this.playersTurn = true;
                    } else if (this.user != null && this.user.cannotMove()) {
                        this.user.allowMovement(true);
                    }
                } else {
                    this.bucket.reset();
                }
            }
        }
    }

    /**
     * Chooses the coordinates of the mouse click.
     * @param x the x-coordinate of the mouse click
     * @param y the y-coordinate of the mouse click
     */
    public void chooseCoords(int x, int y) {
        x = x / 32;
        y = y / 32;
        if (this.playersTurn) {
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
                    if (!this.enemy.cannotMove()) {
                        this.playersTurn = false;
                    } else {
                        this.enemy.allowMovement(true);
                    }
                }
                this.thrownBalloonImage.makeVisible();
                this.thrownBalloonImageTimer = 0;
                this.readyToThrow = false;
            } else if (x == 4 && (y == 16 || y == 17) && this.readyToThrow) {
                this.nextBalloonImage.makeInvisible();
                this.user.receiveBalloon(this.thrownBalloon);
                this.thrownBalloonImage.makeInvisible();
                if (this.thrownBalloon.getType()) {
                    this.thrownBalloonImage.changeColor("red");
                    if (!this.enemy.cannotMove()) {
                        this.playersTurn = false;
                    } else {
                        this.enemy.allowMovement(true);
                    }
                } else {
                    this.thrownBalloonImage.changeColor("blue");
                }
                this.thrownBalloonImage.makeVisible();
                this.thrownBalloonImageTimer = 0;
                this.readyToThrow = false;
            } else if (x >= 1 && x <= 4 && y == 8 && this.isSelected) {
                this.selectedItem.use();
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
            if (this.itemIndex != 0 && this.user.getInventory().getItem(this.itemIndex) != null) {
                this.selectedItem = this.user.getInventory().getItem(this.itemIndex);
                this.isSelected = true;
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
        if (this.endGame && x >= 5 && x < 14 && y >= 9 && y < 11) {
            this.reset();
            this.endState.makeInvisible();
            this.endGame = false;
            this.playersTurn = true;
        }
    }
}