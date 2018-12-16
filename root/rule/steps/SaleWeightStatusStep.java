package net.nmw.rule.steps;

import java.util.Map.Entry;

import net.nmw.persistence.SalesAssignmentEntity;
import net.nmw.persistence.WeightStatusEnum;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 31-Oct-2018.
 */
public class SaleWeightStatusStep<E> extends RuleHelper implements Step<E, WeightStatusEnum>
{
    @Override
    public boolean evaluate(final Entry<E, WeightStatusEnum> operand)
    {
        final SalesAssignmentEntity sa = (SalesAssignmentEntity) getInstance(operand.getKey(), SALES);

        final WeightStatusEnum expected = operand.getValue();
        final WeightStatusEnum actual = (sa != null ? sa.getCurrentRevision().getWeightStatus() : null);

        return compareStatus(expected, actual);
    }

    @Override
    public String toString()
    {
        return "SaleWeightStatusStep{}";
    }
}
