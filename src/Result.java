import balloonStuff.Balloon;

/**
 * The Result class represents the result of a behavior.
 */
public class Result {

    // True if the balloon is thrown to self, false otherwise.
    private final boolean thrownToSelf;

    // The balloon that is thrown.
    private final Balloon thrownBalloon;

    /**
     * Constructs a Result object with the specified isSuccess and thrownBalloon.
     * @param thrownToSelf true if the balloon is thrown to self, false otherwise
     * @param thrownBalloon the balloon that is thrown
     */
    public Result(boolean thrownToSelf, Balloon thrownBalloon) {
        this.thrownToSelf = thrownToSelf;
        this.thrownBalloon = thrownBalloon;
    }

    /**
     * Returns true if the balloon is thrown to self, false otherwise.
     * @return true if the balloon is thrown to self, false otherwise
     */
    public boolean isThrownToSelf() {
        return this.thrownToSelf;
    }

    /**
     * Returns the balloon that is thrown.
     * @return the balloon that is thrown
     */
    public Balloon getThrownBalloon() {
        return this.thrownBalloon;
    }
}
