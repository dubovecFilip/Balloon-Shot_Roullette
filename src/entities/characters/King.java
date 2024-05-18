package entities.characters;

import balloonStuff.Bucket;
import entities.Entity;
import entities.Inventory;

public class King extends Entity {
    public King(Bucket bucket) {
        super(bucket);
    }

    @Override
    public void useAbility(Entity toWho) {
        super.useAbility(toWho);
        Inventory temp = this.getInventory();
        this.setInventory(toWho.getInventory());
        toWho.setInventory(temp);
    }
}
