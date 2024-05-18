package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;
import entities.Inventory;

/**
 * The King class extends the Entity class and represents a character with a unique ability
 * to swap inventories with another entity.
 */
public class King extends Entity {

    /**
     * Constructs a King object with the specified bucket, x, and y coordinates.
     * @param bucket the bucket associated with the King
     * @param x the x-coordinate of the King's ability bar position
     * @param y the y-coordinate of the King's ability bar position
     */
    public King(Bucket bucket, int x, int y) {
        super(bucket, x, y);
    }

    /**
     * Uses the King's special ability to swap inventories with another entity.
     * @param toWho the entity with which the King will swap inventories
     */
    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        Inventory temp = this.getInventory();
        this.setInventory(toWho.getInventory());
        toWho.setInventory(temp);
    }
}
