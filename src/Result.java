import balloonStuff.Balloon;

public class Result {
    private final boolean thrownToSelf;
    private final Balloon thrownBalloon;

    public Result(boolean isSuccess, Balloon thrownBalloon) {
        this.thrownToSelf = isSuccess;
        this.thrownBalloon = thrownBalloon;
    }

    public boolean isThrownToSelf() {
        return this.thrownToSelf;
    }

    public Balloon getThrownBalloon() {
        return this.thrownBalloon;
    }
}
