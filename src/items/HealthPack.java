package items;

import balloonStuff.Bucket;
import entities.Entity;

/**
 * The HealthPack class extends the Item class and represents an item that restores the entity's health.
 */
public class HealthPack extends Item {

    /**
     * Constructs a HealthPack object with the specified fromWho, toWho, bucket, and lowRes.
     * @param fromWho the entity that uses the health pack
     * @param toWho the entity that isn't necessary (not used here)
     * @param bucket the bucket associated with the current game (not used here)
     * @param lowRes true if the health pack icon is in low resolution, false otherwise
     */
    public HealthPack(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/healthPack_l.png");
        } else {
            this.setImage("resources/Game/healthPack.png");
        }
    }

    /**
     * Uses the health pack to restore the entity's health.
     */
    @Override
    public void use() {
        this.getFromWho().dryYourself();
    }
}
