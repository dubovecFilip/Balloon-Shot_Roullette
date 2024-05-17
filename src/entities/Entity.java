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

public abstract class Entity {

    private final Random random = new Random();
    private final Bucket bucket;
    private int defLives;
    private int lives;
    private Inventory inventory;
    private final ArrayList<Image> hearts = new ArrayList<>();
    private int abilityMeter = 0;
    private final Rectangle abilityMeterBar = new Rectangle();
    private int isWeak = 0;
    private boolean unableToMove = false;

    public Entity(Bucket bucket) {
        this.bucket = bucket;
        this.lives = 2;
        this.defLives = this.lives;
        this.inventory = new Inventory();
        //System.out.println("Entity created!");
        this.abilityMeterBar.changePosition(18 * 32 - 8, 18 * 32);
        this.abilityMeterBar.changeColor("gray");
    }

    public void useAbility(Entity theOther) {
        this.abilityMeter = 0;
        this.abilityMeterBar.changeColor("gray");
        this.drawAbility();
    }

    public void dryYourself() {
        if (this.lives + 1 <= this.defLives) {
            this.lives++;
        }
    }

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

    public void emptyInventory() {
        this.inventory.hideItems();
        this.inventory = new Inventory();
    }

    public void generateInventoryItems (int numberOfItems, Entity otherOne, boolean lowRes) {
        for (int i = 0; i < numberOfItems; i++) {
            int item = this.random.nextInt(5);
            switch (item) {
                case 0:
                    this.addItem(new WeakPills(this, otherOne, this.bucket, lowRes));
                    break;
                case 1:
                    this.addItem(new HandCuffs(this, otherOne, this.bucket, lowRes));
                    break;
                case 2:
                    this.addItem(new HealthPack(this, otherOne, this.bucket, lowRes));
                    break;
                case 3:
                    this.addItem(new MagnifyingGlass(this, otherOne, this.bucket, lowRes));
                    break;
                case 4:
                    this.addItem(new Soda(this, otherOne, this.bucket, lowRes));
                    break;
            }
        }
    }

    public void addItem(Item item) {
        this.inventory.addItem(item);
    }

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

    public Item removeItem(Item item) {
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
        return this.inventory.removeItem(item);
    }

    public void drawInventory(int x, int y) {
        this.inventory.drawItems(x, y);
    }

    public void unDrawInventory() {
        this.inventory.hideItems();
    }

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

    public void unDrawLives() {
        for (Image heart : this.hearts) {
            heart.makeInvisible();
        }
    }

    public void reset(int randomLives) {
        this.unDrawLives();
        this.unDrawInventory();
        this.emptyInventory();
        this.abilityMeter = 0;
        this.abilityMeterBar.makeInvisible();
        this.abilityMeterBar.changeColor("gray");
        this.setLives(randomLives);
    }

    public void setLives(int randomLives) {
        this.defLives = randomLives;
        this.lives = randomLives;
    }

    public boolean canHeal() {
        return this.lives < this.defLives;
    }

    public void drawAbility() {
        this.abilityMeterBar.changePosition(18 * 32 - 16, 18 * 32 - 32 * this.abilityMeter);
        this.abilityMeterBar.changeSize(16, 32 * this.abilityMeter);
        this.abilityMeterBar.makeVisible();
    }

    public int getAbility() {
        return this.abilityMeter;
    }

    public void setWeak() {
        this.isWeak = 1;
    }

    public void allowMovement(boolean status) {
        this.unableToMove = status;
    }

    public boolean getMovementStatus() {
        return this.unableToMove;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Inventory getInventory() {
        return this.inventory;
    }
}
