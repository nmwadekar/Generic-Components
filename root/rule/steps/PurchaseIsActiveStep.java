package net.nmw.rule.steps;

import java.util.Map;

import com.trafigura.logistics.persistence.PurchaseAssignmentEntity;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 31-Oct-2018.
 */
public class PurchaseIsActiveStep<E> extends RuleHelper implements Step<E, Boolean>
{
    @Override
    public boolean evaluate(final Map.Entry<E, Boolean> operand)
    {
        final PurchaseAssignmentEntity pa = (PurchaseAssignmentEntity) getInstance(operand.getKey(), PURCHASE);

        final boolean expected = operand.getValue();
        final boolean actual = (pa != null && pa.getCurrentRevision().getStatus().isActive());

        return expected == actual;
    }

    @Override
    public String toString()
    {
        return "PurchaseIsActiveStep{}";
    }
}
