package balloonStuff;

public class Balloon {
    private Boolean dangerStatus;

    public Balloon(Boolean dangerStatus) {
        this.dangerStatus = dangerStatus;
    }

    public Boolean getType() {
        return this.dangerStatus;
    }
}
