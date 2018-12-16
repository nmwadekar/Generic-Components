package net.nmw.rule.steps;

import java.util.Map.Entry;

import com.trafigura.logistics.persistence.SalesAssignmentEntity;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 01-Nov-2018.
 */
public class SaleIsSupersededStep<E> extends RuleHelper implements Step<E, Boolean>
{
    @Override
    public boolean evaluate(final Entry<E, Boolean> operand)
    {
        final SalesAssignmentEntity sa = (SalesAssignmentEntity) getInstance(operand.getKey(), SALES);
        final boolean expected = operand.getValue();
        final boolean actual = (sa != null && isSaleSuperseded(sa));

        return expected == actual;
    }

    @Override
    public String toString()
    {
        return "SaleIsSupercededStep{}";
    }
}
