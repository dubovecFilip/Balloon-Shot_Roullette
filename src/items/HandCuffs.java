package items;

import balloonStuff.Bucket;
import entities.Entity;

/**
 * The HandCuffs class extends the Item class and represents an item that prevents the enemy from moving.
 */
public class HandCuffs extends Item {

    /**
     * Constructs a HandCuffs object with the specified fromWho, toWho, bucket, and lowRes.
     * @param fromWho the entity that uses the handcuffs
     * @param toWho the entity that will be handcuffed
     * @param bucket the bucket associated with the current game
     * @param lowRes true if the handcuffs icon is in low resolution, false otherwise
     */
    public HandCuffs(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/handCuffs_l.png");
        } else {
            this.setImage("resources/Game/handCuffs.png");
        }
    }

    /**
     * Uses the handcuffs to prevent the enemy from moving.
     */
    @Override
    public void use() {
        super.getToWho().allowMovement(false);
    }
}
