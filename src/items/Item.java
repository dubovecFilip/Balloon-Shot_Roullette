package items;

import balloonStuff.Bucket;
import entities.Entity;
import fri.shapesge.Image;

/**
 * The Item class represents an item that can be used by an entity.
 */
public abstract class Item {

    // The bucket associated with the current game.
    private final Bucket bucket;

    // The entity that will use or be effected by the item.
    private final Entity toWho;

    // The entity that will use or be effected by the item.
    private final Entity fromWho;

    // The image of the item.
    private Image image;

    /**
     * Constructs an Item object with the specified fromWho, toWho, and bucket.
     * @param fromWho the entity that will use or be effected by the item.
     * @param toWho the entity that will use or be effected by the item.
     * @param bucket the bucket associated with the current game
     */
    public Item(Entity fromWho, Entity toWho, Bucket bucket) {
        this.bucket = bucket;
        this.toWho = toWho;
        this.fromWho = fromWho;
    }

    /**
     * Uses the item.
     */
    public abstract void use();

    /**
     * Sets the image of the item to the image at the specified path.
     * @param path the path of the image
     */
    public void setImage(String path) {
        this.image = new Image(path);
    }

    /**
     * Draws the item at the specified position.
     * @param x the x-coordinate of the item
     * @param y the y-coordinate of the item
     */
    public void draw(int x, int y) {
        this.image.changePosition(x, y);
        this.image.makeVisible();
    }

    /**
     * Hides the item.
     */
    public void hide() {
        this.image.makeInvisible();
    }

    /**
     * Returns the bucket associated with the current game.
     * @return the bucket associated with the current game
     */
    public Bucket getBucket() {
        return this.bucket;
    }

    /**
     * Returns the entity that will use or be effected by the item.
     * @return the entity that will use or be effected by the item
     */
    public Entity getToWho() {
        return this.toWho;
    }

    /**
     * Returns the entity that will use or be effected by the item.
     * @return the entity that will use or be effected by the item
     */
    public Entity getFromWho() {
        return this.fromWho;
    }
}
