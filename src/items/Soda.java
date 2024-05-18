package items;

import balloonStuff.Bucket;
import entities.Entity;

/**
 * The Soda class extends the Item class and represents an item that removes the next balloon from the bucket.
 */
public class Soda extends Item {

    // The bucket associated with the current game.
    private final Bucket bucket;

    /**
     * Constructs a Soda object with the specified fromWho, toWho, bucket, and lowRes.
     * @param fromWho the entity that isn't necessary (not used here)
     * @param toWho the entity that isn't necessary (not used here)
     * @param bucket the bucket from which the next balloon will be removed
     * @param lowRes true if the soda icon is in low resolution, false otherwise
     */
    public Soda(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        this.bucket = bucket;
        if (lowRes) {
            this.setImage("resources/Game/soda_l.png");
        } else {
            this.setImage("resources/Game/soda.png");
        }

    }

    /**
     * Uses the soda to remove the next balloon from the bucket.
     */
    @Override
    public void use() {
        this.bucket.takeAndThrow();
    }
}
