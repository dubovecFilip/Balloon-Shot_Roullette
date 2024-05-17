package items;

import balloonStuff.Bucket;
import entities.Entity;

public class WeakPills extends Item {

    public WeakPills(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/energyPill_l.png");
        } else {
            this.setImage("resources/Game/energyPill.png");
        }
    }

    @Override
    public void use() {
        this.toWho.setWeak();
        System.out.println("Weak Pills used!");
    }
}
