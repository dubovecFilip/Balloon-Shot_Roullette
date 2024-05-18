package entities;

import items.Item;

public class Inventory {

    /**
     * The inventory of the entity.
     */
    private Item[] inventory;

    /**
     * Constructs an 8 slot Inventory object with an empty inventory.
     */
    public Inventory()  {
        this.inventory = new Item[8];
    }

    /**
     * Adds an item to the inventory, if available. Otherwise, the item is not added.
     * @param item the item to be added
     */
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
        }
    }

    /**
     * Removes an item from the inventory at the specified index.
     * @param index the index of the item to be removed
     * @return the removed item
     */
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

    /**
     * Removes a specified item from the inventory.
     * USED SOLELY FOR ENEMY'S BEHAVIOR!
     * @param item the specified item of the item to be removed
     * @return the removed item
     */
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

    /**
     * Gets the item at the specified index.
     * @param index the index of the item to get
     * @return the item at the specified index
     */
    public Item getItem(int index) {
        return this.inventory[index - 1];
    }

    /**
     * Gets the item of the specified class type.
     * USED SOLELY FOR ENEMY'S BEHAVIOR!
     * @param classType the class type of the item to get
     * @return the item of the specified class type
     */
    public Item getItem(Class classType) {
        for (Item item : this.inventory) {
            if (item != null && item.getClass() == classType) {
                return item;
            }
        }
        return null;
    }

    /**
     * Shows all items in the inventory.
     * @param x the x-coordinate of the inventory
     * @param y the y-coordinate of the inventory
     */
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

    /**
     * Hides all items in the inventory.
     */
    public void hideItems() {
        for (Item item : this.inventory) {
            if (item != null) {
                item.hide();
            }
        }
    }

    /**
     * Counts how many items are in the inventory.
     * @return the number of items in the inventory
     */
    public int numberOfItems() {
        int counter = 0;
        for (Item item : this.inventory) {
            if (item != null) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Checks if the inventory contains an item of the specified class type.
     * USED SOLELY FOR ENEMY'S BEHAVIOR!
     * @param item the class type of the item to check
     * @return true if the inventory contains an item of the specified class type, false otherwise
     */
    public boolean hasItem(Class item) {
        for (Item inventoryItem : this.inventory) {
            if (inventoryItem != null && inventoryItem.getClass() == item) {
                return true;
            }
        }
        return false;
    }
}
