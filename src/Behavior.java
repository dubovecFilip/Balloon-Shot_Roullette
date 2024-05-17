import balloonStuff.Balloon;
import balloonStuff.Bucket;
import entities.Entity;
import items.*;

import java.util.Random;

public class Behavior {
    private final Entity enemy;
    private final Entity player;
    private final Bucket bucket;
    private final Random random;
    private Item itemUsed;
    private boolean magnifyingGlassAblitity;
    private Result result;

    public Behavior(Entity enemy, Entity player, Bucket bucket) {
        this.enemy = enemy;
        this.player = player;
        this.bucket = bucket;
        this.random = new Random();
    }

    public void makeMove() {
        // Health Check
        if (this.enemy.canHeal() && this.enemy.getInventory().hasItem(HealthPack.class)) {
            this.itemUsed = this.enemy.getInventory().getItem(HealthPack.class);
            this.enemy.removeItem(this.itemUsed).use();
        }
        if (this.random.nextBoolean() && this.enemy.getInventory().hasItem(WeakPills.class)) {
            this.itemUsed = this.enemy.getInventory().getItem(WeakPills.class);
            this.enemy.removeItem(this.itemUsed).use();
        }

        // Strategic Item Use
        if (this.enemy.getInventory().hasItem(HandCuffs.class)) {
            this.itemUsed = this.enemy.getInventory().getItem(HandCuffs.class);
            this.enemy.removeItem(this.itemUsed).use();
        }
        if (this.enemy.getInventory().hasItem(MagnifyingGlass.class)) {
            this.itemUsed = this.enemy.getInventory().getItem(MagnifyingGlass.class);
            this.enemy.removeItem(this.itemUsed).use();
        }
        if (this.enemy.getInventory().hasItem(Soda.class)) {
            this.itemUsed = this.enemy.getInventory().getItem(Soda.class);
            this.enemy.removeItem(this.itemUsed).use();
        }

        // Ability Meter Check
        if (this.enemy.getAbility() == 5) {
            this.enemy.useAbility(this.player);
        }

        Balloon thrownBalloon = null;
        boolean thrownToSelf = false;
        if (!this.bucket.getBalloons().isEmpty()) {
            // Default Actions
            thrownBalloon = this.bucket.takeAndThrow();
            if (this.magnifyingGlassAblitity) {
                if (thrownBalloon.getType()) {
                    this.player.receiveBalloon(thrownBalloon);
                } else {
                    this.enemy.receiveBalloon(thrownBalloon);
                    thrownToSelf = true;
                }
            } else {
                if (this.bucket.numberOfGlueBalloons() < this.bucket.getNumberOfBalloons() / 2) {
                    this.enemy.receiveBalloon(thrownBalloon);
                    thrownToSelf = true;
                } else {
                    this.player.receiveBalloon(thrownBalloon);
                }
            }
        }
        this.result = new Result(thrownToSelf, thrownBalloon);
    }

    public Result getResult() {
        return this.result;
    }
}