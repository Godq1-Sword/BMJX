package pri.bmjx.principles.lsperr;

/**
 * @author God_q1
 * date 2020/4/29
 * description
 */
public class Bird {
    private double flySpeed;

    public void setSpeed(double flySpeed) {
        this.flySpeed = flySpeed;
    }

    public double getFlyTime(double distance) {
        return distance / flySpeed;
    }
}
