package pri.bmjx.uml.demo;

import java.util.List;

/**
 * @author God_q1
 * date 2020/4/28
 * description
 */
public class ExampleClass extends ExampleExtend implements ExampleInterface {
    // StaticFinal
    private static final String STATIC_FINAL_STRING = "staticFinalString";
    // Static
    public static String staticString = "staticString";
    // Final
    public final String finalString = "finalString";

    /**
     * 依赖关系 - 各种方式的调用
     * 1.形参调用
     * 2.静态调用
     * 3.局部调用
     */
    // 依赖关系 - 形参调用
    private ExampleClass(ExampleDep exampleDep) {
    }

    // 依赖关系 - 静态调用
    private void DepPrint() {
        ExampleDep.Pinrt();
    }

    // 依赖关系 - 局部使用
    private String UseDep() {
        return new ExampleDep().toString();
    }

    /**
     * UML逻辑上关联/聚合/组合关系 - 作为成员变量实现
     */
    // 关联 类与类之间的关系 - 我和我的朋友
    private List<ExampleAgg> exampleAggs;
    // 聚合 关联的特例,整体与部分,部分可有可无 - 公司与员工
    private List<ExampleAss> exampleAsses;
    // 组合 关联的特例,整体与部分且不可分割 - 身体和大脑
    private List<ExampleComp> exampleComps;

    @Override
    public void Print() {
        System.out.println(text);
    }
}
