package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;

public class Magician extends Entity {
    private final Bucket bucket;
    public Magician(Bucket bucket) {
        super(bucket);
        this.bucket = bucket;
    }

    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        this.bucket.reset();
        System.out.println("Char3 used ability!");
    }
}
