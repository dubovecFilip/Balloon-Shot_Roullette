package items;

import balloonStuff.Bucket;
import entities.Entity;

public class HandCuffs extends Item {

    public HandCuffs(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/handCuffs_l.png");
        } else {
            this.setImage("resources/Game/handCuffs.png");
        }
    }

    @Override
    public void use() {
        super.getToWho().allowMovement(false);
    }
}
