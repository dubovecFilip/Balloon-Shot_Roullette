package items;

import balloonStuff.Bucket;
import entities.Entity;

public class MagnifyingGlass extends Item {

    public MagnifyingGlass(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/magnifyingGlass_l.png");
        } else {
            this.setImage("resources/Game/magnifyingGlass.png");
        }
    }

    @Override
    public void use() {
        this.getBucket().setShowNext(true);
    }
}
