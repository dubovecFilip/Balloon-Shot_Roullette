package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;

import java.util.Random;

/**
 * The Magician class extends the Entity class and represents a character with a unique ability
 * to remove one of the enemy's items and to reset the bucket of balloons to an unknown state.
 */
public class Magician extends Entity {

    private final Bucket bucket;

    /**
     * Constructs a Magician object with the specified bucket, x, and y coordinates.
     * @param bucket the bucket associated with the Magician
     * @param x the x-coordinate of the Magician's ability bar position
     * @param y the y-coordinate of the Magician's ability bar position
     */
    public Magician(Bucket bucket, int x, int y) {
        super(bucket, x, y);
        this.bucket = bucket;
    }

    /**
     * Uses the Magician's special ability to reset the bucket of balloons to an unknown state.
     * @param toWho the entity whose item "will be sacrificed" to reset the bucket
     */
    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        Random random = new Random();
        toWho.removeItem(random.nextInt(toWho.getInventory().numberOfItems() - 1));
        this.bucket.reset();
    }
}
