package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;
import java.util.Random;

/**
 * The Thief class extends the Entity class and represents a character with a unique ability
 * to steal one of the enemy's items.
 */
public class Thief extends Entity {

    /**
     * Constructs a Thief object with the specified bucket, x, and y coordinates.
     * @param bucket the bucket associated with the Magician
     * @param x the x-coordinate of the Magician's ability bar position
     * @param y the y-coordinate of the Magician's ability bar position
     */
    public Thief(Bucket bucket, int x, int y) {
        super(bucket, x, y);
    }

    /**
     * Uses the Thief's special ability to steal one of the enemy's items.
     * @param toWho the entity whose item will be stolen
     */
    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        Random random = new Random();
        this.addItem(toWho.removeItem(random.nextInt(toWho.getInventory().numberOfItems() - 1)));
    }
}
