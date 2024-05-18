package items;

import balloonStuff.Bucket;
import entities.Entity;

/**
 * The MagnifyingGlass class extends the Item class and represents an item that shows the next balloon.
 */
public class MagnifyingGlass extends Item {

    /**
     * Constructs a MagnifyingGlass object with the specified fromWho, toWho, bucket, and lowRes.
     * @param fromWho the entity that isn't necessary (not used here)
     * @param toWho the entity that isn't necessary (not used here)
     * @param bucket the bucket from which the next balloon will be shown
     * @param lowRes true if the magnifying glass icon is in low resolution, false otherwise
     */
    public MagnifyingGlass(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/magnifyingGlass_l.png");
        } else {
            this.setImage("resources/Game/magnifyingGlass.png");
        }
    }

    /**
     * Uses the magnifying glass to show the next balloon.
     */
    @Override
    public void use() {
        this.getBucket().setShowNext(true);
    }
}
