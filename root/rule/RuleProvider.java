package net.nmw.rule;

/**
 * Created by nayan.wadekar on 29-Oct-2018.
 */
public interface RuleProvider<E, V>
{
    Object[][] getData();

    Class<? extends Step>[] getSteps();

    void init(E e, V v);
}
