package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;
import java.util.Random;

public class Thief extends Entity {
    public Thief(Bucket bucket) {
        super(bucket);
    }

    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        Random random = new Random();
        this.addItem(toWho.removeItem(random.nextInt(toWho.getInventory().numberOfItems())));
        System.out.println("Thief used ability!");
    }
}
