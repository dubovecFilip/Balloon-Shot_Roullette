package entities;

import balloonStuff.Bucket;
import fri.shapesge.Image;
import fri.shapesge.Rectangle;
import items.WeakPills;
import items.HandCuffs;
import items.HealthPack;
import items.MagnifyingGlass;
import items.Soda;
import items.Item;
import balloonStuff.Balloon;

import java.util.ArrayList;
import java.util.Random;

/**
 * An abstract class that represents an entity in the game.
 */
public abstract class Entity {

    /**
     * Random generator.
     */
    private final Random random = new Random();

    /**
     * The bucket.
     */
    private final Bucket bucket;

    /**
     * X-coordinate of the entity's ability bar.
     */
    private final int x;

    /**
     * Y-coordinate of the entity's ability bar.
     */
    private final int y;

    /**
     * Default number of lives.
     */
    private int defLives;

    /**
     * Current number of lives.
     */
    private int lives;

    /**
     * Inventory of the entity.
     */
    private Inventory inventory;

    /**
     * Hearts representing lives.
     */
    private final ArrayList<Image> hearts = new ArrayList<>();

    /**
     * Ability meter.
     */
    private int abilityMeter = 0;

    /**
     * Rectangle representing ability meter bar.
     */
    private final Rectangle abilityMeterBar = new Rectangle();

    /**
     * Flag indicating if the entity is weak.
     */
    private int isWeak = 0;

    /**
     * Flag indicating if the entity is unable to move.
     */
    private boolean unableToMove = false;

    /**
     * Constructor for Entity class.
     * @param bucket The bucket.
     * @param x X-coordinate of the entity's ability bar.
     * @param y Y-coordinate of the entity's ability bar.
     */
    public Entity(Bucket bucket, int x, int y) {
        this.x = x;
        this.y = y;
        this.bucket = bucket;
        this.lives = 2;
        this.defLives = this.lives;
        this.inventory = new Inventory();
        this.abilityMeterBar.changePosition(this.x * 32 - 8, this.y * 32);
        this.abilityMeterBar.changeColor("gray");
    }

    /**
     * Uses the entity's ability, resetting the ability state.
     * @param theOther The other entity.
     */
    public void useAbility(Entity theOther) {
        this.abilityMeter = 0;
        this.abilityMeterBar.changeColor("gray");
        this.drawAbility();
    }

    /**
     * Heal the entity by one life, if possible
     */
    public void dryYourself() {
        if (this.lives + 1 <= this.defLives) {
            this.lives++;
        }
    }

    /**
     * Method to process receiving a balloon by the entity.
     * @param thrownBalloon The balloon thrown at the entity.
     */
    public void receiveBalloon(Balloon thrownBalloon) {
        if (thrownBalloon.getType()) {
            this.lives = this.lives - (1 + this.isWeak);
        }
        if (this.isWeak == 1) {
            //System.out.print("Not weak!");
            this.isWeak = 0;
        }
        //System.out.println("lives: " + this.lives);
    }

    /**
     * Method to empty the inventory of the entity.
     */
    public void emptyInventory() {
        this.inventory.hideItems();
        this.inventory = new Inventory();
    }

    /**
     * Method to generate inventory items for the entity.
     * @param numberOfItems Number of items to generate.
     * @param otherOne The other entity.
     * @param lowRes Indicator for low resolution images.
     */
    public void generateInventoryItems (int numberOfItems, Entity otherOne, boolean lowRes) {
        for (int i = 0; i < numberOfItems; i++) {
            int item = this.random.nextInt(5);
            switch (item) {
                case 0:
                    this.addItem(new Soda(this, otherOne, this.bucket, lowRes));
                    break;
                case 1:
                    this.addItem(new HandCuffs(this, otherOne, this.bucket, lowRes));
                    break;
                case 2:
                    this.addItem(new MagnifyingGlass(this, otherOne, this.bucket, lowRes));
                    break;
                case 3:
                    this.addItem(new HealthPack(this, otherOne, this.bucket, lowRes));
                    break;
                case 4:
                    this.addItem(new WeakPills(this, otherOne, this.bucket, lowRes));
                    break;
            }
        }
    }

    /**
     * Method to add an item to the entity's inventory.
     * @param item The item to add.
     */
    public void addItem(Item item) {
        this.inventory.addItem(item);
    }

    /**
     * Method to remove an item from the entity's inventory.
     * @param index The index of the item to remove.
     * @return The removed item.
     */
    public Item removeItem(int index) {
        this.unDrawInventory();
        //System.out.println(this.abilityMeter);
        if (this.abilityMeter < 5) {
            this.abilityMeter += 1;
            this.drawAbility();
        }
        if (this.abilityMeter == 5) {
            this.abilityMeterBar.changeColor("orange");
            this.abilityMeterBar.makeVisible();
        }
        return this.inventory.removeItem(index);
    }

    /**
     * Method to remove an item from the entity's inventory.
     * @param item The item to remove.
     * @return The removed item.
     */
    public Item removeItem(Item item) {
        this.unDrawInventory();
        if (this.abilityMeter < 5) {
            this.abilityMeter += 1;
            this.drawAbility();
        }
        if (this.abilityMeter == 5) {
            this.abilityMeterBar.changeColor("orange");
            this.abilityMeterBar.makeVisible();
        }
        return this.inventory.removeItem(item);
    }

    /**
     * Method to draw the entity's inventory.
     * @param x X-coordinate of the inventory.
     * @param y Y-coordinate of the inventory.
     */
    public void drawInventory(int x, int y) {
        this.inventory.drawItems(x, y);
    }

    /**
     * Method to undraw the entity's inventory.
     */
    public void unDrawInventory() {
        this.inventory.hideItems();
    }

    /**
     * Method to draw the entity's lives.
     * @param x X-coordinate of the lives.
     * @param y Y-coordinate of the lives.
     */
    public void drawLives(int x, int y) {
        this.unDrawLives();
        this.hearts.clear();
        int xN = x * 32 - 4;
        int yN = y * 32;
        for (int i = 0; i < this.lives; i++) {
            Image heart = new Image("resources/Game/life.png");
            heart.changePosition(xN, yN);
            heart.makeVisible();
            this.hearts.add(heart);
            xN += 24;
        }
    }

    /**
     * Method to undraw the entity's lives.
     */
    public void unDrawLives() {
        for (Image heart : this.hearts) {
            heart.makeInvisible();
        }
    }

    /**
     * Method to reset the entity's lives and inventory.
     * @param randomLives Random number of lives.
     */
    public void reset(int randomLives) {
        this.unDrawLives();
        this.unDrawInventory();
        this.emptyInventory();
        this.abilityMeter = 0;
        this.abilityMeterBar.makeInvisible();
        this.abilityMeterBar.changeColor("gray");
        this.setLives(randomLives);
    }

    /**
     * Method to set the entity's lives.
     * @param randomLives Random number of lives.
     */
    public void setLives(int randomLives) {
        this.defLives = randomLives;
        this.lives = randomLives;
    }

    /**
     * Method to check if the entity can heal.
     * @return True if the entity can heal, false otherwise.
     */
    public boolean canHeal() {
        return this.lives < this.defLives;
    }

    /**
     * Method to update the visual representation of the entity's ability.
     */
    public void drawAbility() {
        this.abilityMeterBar.changePosition(this.x * 32 - 16, this.y * 32 - 32 * this.abilityMeter);
        this.abilityMeterBar.changeSize(16, 32 * this.abilityMeter);
        this.abilityMeterBar.makeVisible();
    }

    /**
     * Method to get the entity's ability status, i.e. the ability meter.
     */
    public int getAbility() {
        return this.abilityMeter;
    }

    /**
     * Method to set the entity's weakness. The entity will lose an extra life when hit by a red balloon.
     */
    public void setWeak() {
        this.isWeak = 1;
    }

    /**
     * Method to set the entity's movement status.
     */
    public void allowMovement(boolean status) {
        this.unableToMove = !status;
    }

    /**
     * Method to check if the entity can not move.
     * @return True if the entity can not move, false otherwise.
     */
    public boolean cannotMove() {
        return this.unableToMove;
    }

    /**
     * Method to set the entity's inventory.
     * @param inventory The inventory to set.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Method to get the entity's inventory.
     * @return The entity's inventory.
     */
    public Inventory getInventory() {
        return this.inventory;
    }
}
