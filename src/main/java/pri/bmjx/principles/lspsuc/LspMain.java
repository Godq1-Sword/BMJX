package pri.bmjx.principles.lspsuc;

/**
 * @author God_q1
 * date 2020/4/29
 * description
 */
public class LspMain {
    public static void main(String[] args) {
        Animal brownKiwi = new BrownKiwi();
        Bird swallow = new Swallow();
        brownKiwi.setRunSpeed(50);
        swallow.setRunSpeed(50);
        System.out.println(brownKiwi.getRunTime(50));
        System.out.println(swallow.getRunTime(50));
    }
}
