package items;

import balloonStuff.Bucket;
import entities.Entity;

public class HealthPack extends Item {

    public HealthPack(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/healthPack_l.png");
        } else {
            this.setImage("resources/Game/healthPack.png");
        }
    }

    @Override
    public void use() {
        this.getFromWho().dryYourself();
    }
}
