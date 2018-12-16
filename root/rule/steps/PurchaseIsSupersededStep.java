package net.nmw.rule.steps;

import java.util.Map.Entry;

import com.trafigura.logistics.persistence.PurchaseAssignmentEntity;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 01-Nov-2018.
 */
public class PurchaseIsSupersededStep<E> extends RuleHelper implements Step<E, Boolean>
{
    @Override
    public boolean evaluate(final Entry<E, Boolean> operand)
    {
        final PurchaseAssignmentEntity pa = (PurchaseAssignmentEntity) getInstance(operand.getKey(), PURCHASE);
        final boolean expected = operand.getValue();

        return expected == isPurchaseSuperseded(pa);
    }

    @Override
    public String toString()
    {
        return "PurchaseIsSupersededStep{}";
    }
}
