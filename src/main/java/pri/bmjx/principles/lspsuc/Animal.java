package pri.bmjx.principles.lspsuc;

/**
 * @author God_q1
 * date 2020/4/29
 * description
 */
public class Animal {
    private double runSpeed;

    public void setRunSpeed(double runSpeed) {
        this.runSpeed = runSpeed;
    }

    public double getRunTime(double distance) {
        return distance / runSpeed;
    }
}
