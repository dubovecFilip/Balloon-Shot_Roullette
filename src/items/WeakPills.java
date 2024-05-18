package items;

import balloonStuff.Bucket;
import entities.Entity;

/**
 * The WeakPills class extends the Item class and represents an item that weakens the enemy.
 */
public class WeakPills extends Item {

    /**
     * Constructs a WeakPills object with the specified fromWho, toWho, bucket, and lowRes.
     * @param fromWho the entity that isn't necessary (not used here)
     * @param toWho the entity that will be weakened
     * @param bucket the bucket associated with the current game (not used here)
     * @param lowRes true if the weak pills icon is in low resolution, false otherwise
     */
    public WeakPills(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/energyPill_l.png");
        } else {
            this.setImage("resources/Game/energyPill.png");
        }
    }

    /**
     * Uses the weak pills to weaken the enemy.
     */
    @Override
    public void use() {
        this.getToWho().setWeak();
    }
}
