package items;

import balloonStuff.Bucket;
import entities.Entity;

public class Soda extends Item {


    public Soda(Entity fromWho, Entity toWho, Bucket bucket, boolean lowRes) {
        super(fromWho, toWho, bucket);
        if (lowRes) {
            this.setImage("resources/Game/soda_l.png");
        } else {
            this.setImage("resources/Game/soda.png");
        }

    }

    @Override
    public void use() {
        System.out.println("Soda used!");
    }
}
