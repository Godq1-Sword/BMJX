package pri.bmjx.principles.lspsuc;

/**
 * @author God_q1
 * date 2020/4/29
 * description
 */
public class Bird extends Animal {
    private double flySpeed;

    public void setFlySpeed(double flySpeed) {
        this.flySpeed = flySpeed;
    }

    public double getFlyTime(double distance) {
        return distance / flySpeed;
    }
}
