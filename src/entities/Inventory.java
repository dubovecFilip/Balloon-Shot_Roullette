package entities;

import items.Item;

public class Inventory {

    private Item[] inventory;

    public Inventory()  {
        this.inventory = new Item[8];
    }

    public void addItem(Item item) {
        Item[] newInventory = new Item[8];
        int counter = 0;
        boolean wasAdded = false;
        for (Item itemSlot : this.inventory) {
            if (itemSlot == null) {
                wasAdded = true;
                newInventory[counter] = item;
                break;
            } else {
                newInventory[counter] = itemSlot;
            }
            counter++;
        }
        if (wasAdded) {
            this.inventory = newInventory;
        } else {
            System.out.println("Inventory is full!");
        }
    }

    public Item removeItem(int index) {
        Item removedItem = null;
        Inventory newInventory = new Inventory();
        for (int i = 0; i < 8; i++) {
            if (!(index == i)) {
                newInventory.addItem(this.inventory[i]);
            } else {
                removedItem = this.inventory[i];
                removedItem.hide();
            }
        }
        this.inventory = newInventory.inventory;
        return removedItem;
    }

    public Item removeItem(Item item) {
        Item removedItem = null;
        Inventory newInventory = new Inventory();
        for (int i = 0; i < 8; i++) {
            if (!(this.inventory[i] == item)) {
                newInventory.addItem(this.inventory[i]);
            } else {
                removedItem = this.inventory[i];
                removedItem.hide();
            }
        }
        this.inventory = newInventory.inventory;
        return removedItem;
    }

    public Item getItem(int index) {
        return this.inventory[index - 1];
    }

    public Item getItem(Class classType) {
        for (Item item : this.inventory) {
            if (item != null && item.getClass() == classType) {
                return item;
            }
        }
        return null;
    }

    public void drawItems(int x, int y) {
        int xN = x;
        int yN = y;
        int help = 0;
        for (Item item : this.inventory) {
            if (item != null) {
                item.draw(xN * 32, yN * 32);
                help++;
            }
            xN += 2;
            if (help % 4 == 0) {
                xN = x;
                yN += 2;
            }
        }
    }

    public void redrawItems() {
        for (Item item : this.inventory) {
            if (item != null) {
                item.hide();
                item.show();
            }
        }
    }

    public void hideItems() {
        for (Item item : this.inventory) {
            if (item != null) {
                item.hide();
            }
        }
    }

    public int numberOfItems() {
        int counter = 0;
        for (Item item : this.inventory) {
            if (item != null) {
                counter += 1;
            }
        }
        return counter;
    }

    public boolean hasItem(Class item) {
        for (Item inventoryItem : this.inventory) {
            if (inventoryItem != null && inventoryItem.getClass() == item) {
                return true;
            }
        }
        return false;
    }
}
