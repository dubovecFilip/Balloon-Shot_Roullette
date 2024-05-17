import fri.shapesge.Image;
import fri.shapesge.Manager;

public class Menu {

    private final Manager manager = new Manager();
    private final Image menu = new Image("resources/Menu/bg.png");
    private final Image bg = new Image("resources/table2.png");
    private final Image aboutBg = new Image("resources/Menu/about.png");
    private final Image charPickBg = new Image("resources/Menu/charBg.png");
    private final Image selectedUser = new Image("resources/Menu/user1.png");
    private final Image selectedEnemy = new Image("resources/Menu/user1.png");
    private final Image lowResBoxTick = new Image("resources/Menu/box.png");

    private boolean aboutPage = false;
    private boolean charPage = false;

    private int userCounter = 1;
    private int enemyCounter = 1;
    private boolean lowRes = false;

    public Menu() {
        this.manager.manageObject(this);
        this.bg.changePosition(0, 0);
        this.bg.makeVisible();
        this.menu.changePosition(0, 0);
        this.menu.makeVisible();
        this.aboutBg.changePosition(0, 0);
        this.charPickBg.changePosition(0, 0);
        this.lowResBoxTick.changePosition(32, 16 * 32);
        this.lowResBoxTick.makeVisible();
    }

    public void chooseCoords(int x, int y) {
        System.out.println("Chosen coords: " + x / 32 + " " + y / 32);
        x = x / 32;
        y = y / 32;
        if (x >= 5 && x <= 13 && y >= 6 && y <= 7 && !this.aboutPage && !this.charPage) {
            this.menu.makeInvisible();
            this.charPage = true;
            this.charPickBg.makeVisible();
            this.selectedUser.changePosition(4 * 32, 8 * 32);
            this.selectedUser.makeVisible();
            this.selectedEnemy.changePosition(12 * 32, 8 * 32);
            this.selectedEnemy.makeVisible();
            this.lowResBoxTick.makeInvisible();
        } else if (x >= 5 && x <= 10 && y >= 9 && y <= 10 && !this.aboutPage && !this.charPage) {
            this.menu.makeInvisible();
            this.aboutBg.makeVisible();
            this.aboutPage = true;
            this.lowResBoxTick.makeInvisible();
        } else if (x == 1 && y == 16 && !this.aboutPage && !this.charPage) {
            if (this.lowRes) {
                this.lowRes = false;
                this.lowResBoxTick.changeImage("resources/Menu/box.png");
            } else {
                this.lowRes = true;
                this.lowResBoxTick.changeImage("resources/Menu/boxTick.png");
            }
        } else if (x >= 7 && x <= 11 && y >= 13 && y <= 14 && !this.aboutPage && !this.charPage) {
            System.exit(0);
        } else if (this.aboutPage) {
            this.aboutBg.makeInvisible();
            this.aboutPage = false;
            this.lowResBoxTick.makeVisible();
            this.menu.makeVisible();
        } else if (this.charPage) {
            if (x >= 3 && x <= 7) {
                if (y >= 6 && y <= 7) {
                    this.userCounter += 1;
                } else if (y >= 12 && y <= 13) {
                    this.userCounter -= 1;
                }
                if (this.userCounter > 3) {
                    this.userCounter = 1;
                } else if (this.userCounter < 1) {
                    this.userCounter = 3;
                }
                this.selectedUser.makeInvisible();
                this.selectedUser.changeImage("resources/Menu/user" + this.userCounter + ".png");
                this.selectedUser.changePosition(4 * 32, 8 * 32);
                this.selectedUser.makeVisible();
            }

            if (x >= 11 && x <= 15) {
                if (y >= 6 && y <= 7) {
                    this.enemyCounter += 1;
                } else if (y >= 12 && y <= 13) {
                    this.enemyCounter -= 1;
                }
                if (this.enemyCounter > 3) {
                    this.enemyCounter = 1;
                } else if (this.enemyCounter < 1) {
                    this.enemyCounter = 3;
                }
                this.selectedEnemy.makeInvisible();
                this.selectedEnemy.changeImage("resources/Menu/user" + this.enemyCounter + ".png");
                this.selectedEnemy.changePosition(12 * 32, 8 * 32);
                this.selectedEnemy.makeVisible();
            }

            if (x >= 5 && x <= 14 && y >= 16 && y <= 18) {
                this.charPickBg.makeInvisible();
                this.selectedUser.makeInvisible();
                this.selectedEnemy.makeInvisible();
                this.manager.stopManagingObject(this);
                Game game = new Game(this.userCounter, this.enemyCounter, this.lowRes);
            }
        }
    }
}
