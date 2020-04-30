package pri.bmjx.principles.lsperr;

/**
 * @author God_q1
 * date 2020/4/29
 * description
 */
public class LspMain {
    public static void main(String[] args) {
        Bird swallow = new Swallow();
        swallow.setSpeed(50);
        Bird brownKiwi = new BrownKiwi();
        brownKiwi.setSpeed(20);
        System.out.println(swallow.getFlyTime(50));
        System.out.println(brownKiwi.getFlyTime(50));
    }
}
