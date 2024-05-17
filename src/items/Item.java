package items;

import balloonStuff.Bucket;
import entities.Entity;
import fri.shapesge.Image;

public abstract class Item {
    protected Bucket bucket;
    protected Entity toWho;
    protected Entity fromWho;
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
}
