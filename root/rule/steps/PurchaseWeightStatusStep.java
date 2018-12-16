package net.nmw.rule.steps;

import java.util.Map.Entry;

import com.trafigura.logistics.persistence.PurchaseAssignmentEntity;
import com.trafigura.logistics.persistence.WeightStatusEnum;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 31-Oct-2018.
 */
public class PurchaseWeightStatusStep<E> extends RuleHelper implements Step<E, WeightStatusEnum>
{
    @Override
    public boolean evaluate(final Entry<E, WeightStatusEnum> operand)
    {
        final PurchaseAssignmentEntity pa = (PurchaseAssignmentEntity) getInstance(operand.getKey(), PURCHASE);

        final WeightStatusEnum expected = operand.getValue();
        final WeightStatusEnum actual = (pa != null ? pa.getCurrentRevision().getWeightStatus() : null);

        return compareStatus(expected, actual);
    }

    @Override
    public String toString()
    {
        return "PurchaseWeightStatusStep{}";
    }
}
