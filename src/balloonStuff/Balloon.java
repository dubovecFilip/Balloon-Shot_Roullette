package balloonStuff;

/**
 * The Balloon class represents a balloon with a specified danger status.
 * The danger status indicates whether the balloon is considered dangerous.
 */
public class Balloon {

    // A private final field that indicates the danger status of the balloon.
    private final boolean dangerStatus;

    /**
     * Constructor for creating a new Balloon instance.
     * @param dangerStatus A Boolean indicating if the balloon is dangerous (true) or not (false).
     */
    public Balloon(boolean dangerStatus) {
        this.dangerStatus = dangerStatus;
    }

    /**
     * Retrieves the danger status of the balloon.
     * @return A Boolean indicating the danger status: true if the balloon is dangerous, false otherwise.
     */
    public boolean getType() {
        return this.dangerStatus;
    }
}
