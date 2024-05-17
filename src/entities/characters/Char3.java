package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;

public class Char3 extends Entity {
    public Char3(Bucket bucket) {
        super(bucket);
    }

    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        System.out.println("Char3 used ability!");
    }
}
