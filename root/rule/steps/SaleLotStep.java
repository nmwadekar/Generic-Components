package net.nmw.rule.steps;

import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

import net.nmw.persistence.SalesAssignmentEntity;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 01-Nov-2018.
 */
public class SaleLotStep<E> extends RuleHelper implements Step<E, Boolean>
{
    @Override
    public boolean evaluate(Entry<E, Boolean> operand)
    {
        final SalesAssignmentEntity sa = (SalesAssignmentEntity) getInstance(operand.getKey(), SALES);

        boolean expected = operand.getValue();
        boolean actual = (sa != null && CollectionUtils.isNotEmpty(sa.getLotEntity()));

        return expected == actual;
    }

    @Override
    public String toString()
    {
        return "SaleLotStep{}";
    }
}
