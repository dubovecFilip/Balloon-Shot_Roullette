import balloonStuff.Balloon;
import balloonStuff.Bucket;
import entities.Entity;
import items.HandCuffs;
import items.HealthPack;
import items.Item;
import items.MagnifyingGlass;
import items.Soda;
import items.WeakPills;

import java.util.Random;

/**
 * The Behavior class represents the behavior of an entity in the game.
 */
public class Behavior {

    // The entity that is the enemy.
    private final Entity enemy;

    // The entity that is the player.
    private final Entity player;

    // The bucket associated with the current game.
    private final Bucket bucket;

    // Random generator.
    private final Random random;

    // True if the enemy has the magnifying glass ability, false otherwise.
    private boolean magnifyingGlassAbility;

    // Result of the behavior.
    private Result result;

    /**
     * Constructs a Behavior object with the specified enemy, player, and bucket.
     * @param enemy the entity that is the enemy
     * @param player the entity that is the player
     * @param bucket the bucket associated with the current game
     */
    public Behavior(Entity enemy, Entity player, Bucket bucket) {
        this.enemy = enemy;
        this.player = player;
        this.bucket = bucket;
        this.random = new Random();
    }

    /**
     * Makes the enemy entity make a move.
     */
    public void makeMove() {

        Item itemUsed;
        if (this.enemy.canHeal() && this.enemy.getInventory().hasItem(HealthPack.class)) {
            itemUsed = this.enemy.getInventory().getItem(HealthPack.class);
            this.enemy.removeItem(itemUsed).use();
        }
        if (this.random.nextBoolean() && this.enemy.getInventory().hasItem(WeakPills.class)) {
            itemUsed = this.enemy.getInventory().getItem(WeakPills.class);
            this.enemy.removeItem(itemUsed).use();
        }
        if (this.enemy.getInventory().hasItem(HandCuffs.class)) {
            itemUsed = this.enemy.getInventory().getItem(HandCuffs.class);
            this.enemy.removeItem(itemUsed).use();
        }
        if (this.enemy.getInventory().hasItem(MagnifyingGlass.class)) {
            itemUsed = this.enemy.getInventory().getItem(MagnifyingGlass.class);
            this.enemy.removeItem(itemUsed).use();
            this.magnifyingGlassAbility = true;
        }
        if (this.enemy.getInventory().hasItem(Soda.class)) {
            itemUsed = this.enemy.getInventory().getItem(Soda.class);
            this.enemy.removeItem(itemUsed).use();
        }

        if (this.enemy.getAbility() == 5) {
            this.enemy.useAbility(this.player);
        }

        Balloon thrownBalloon = null;
        boolean thrownToSelf = false;
        if (!this.bucket.isEmpty()) {
            thrownBalloon = this.bucket.takeAndThrow();
            if (this.magnifyingGlassAbility) {
                if (thrownBalloon.getType()) {
                    this.player.receiveBalloon(thrownBalloon);
                } else {
                    this.enemy.receiveBalloon(thrownBalloon);
                    thrownToSelf = true;
                }
            } else {
                if (this.bucket.getNumberOfBadBalloons() < this.bucket.getNumberOfBalloons() / 2) {
                    this.enemy.receiveBalloon(thrownBalloon);
                    thrownToSelf = true;
                } else {
                    this.player.receiveBalloon(thrownBalloon);
                }
            }
        }
        this.result = new Result(thrownToSelf, thrownBalloon);
    }

    /**
     * Returns the result of the behavior.
     * @return the result of the behavior
     */
    public Result getResult() {
        return this.result;
    }
}