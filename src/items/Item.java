package items;

import balloonStuff.Bucket;
import entities.Entity;
import fri.shapesge.Image;

public abstract class Item {
    private final Bucket bucket;
    private final Entity toWho;
    private final Entity fromWho;
    private Image image;

    public Item(Entity fromWho, Entity toWho, Bucket bucket) {
        this.bucket = bucket;
        this.toWho = toWho;
        this.fromWho = fromWho;
    }

    public abstract void use();

    public void setImage(String path) {
        this.image = new Image(path);
    }

    public void draw(int x, int y) {
        this.image.changePosition(x, y);
        this.image.makeVisible();
    }

    public void show() {
        this.image.makeVisible();
    }

    public void hide() {
        this.image.makeInvisible();
    }

    public Bucket getBucket() {
        return this.bucket;
    }

    public Entity getToWho() {
        return this.toWho;
    }

    public Entity getFromWho() {
        return this.fromWho;
    }
}
