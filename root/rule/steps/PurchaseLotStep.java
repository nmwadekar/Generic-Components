package net.nmw.rule.steps;

import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

import com.trafigura.logistics.persistence.PurchaseAssignmentEntity;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 26-Oct-2018.
 */
public class PurchaseLotStep<E> extends RuleHelper implements Step<E, Boolean>
{
    @Override
    public boolean evaluate(Entry<E, Boolean> operand)
    {
        final PurchaseAssignmentEntity pa = (PurchaseAssignmentEntity) getInstance(operand.getKey(), PURCHASE);

        boolean expected = operand.getValue();
        boolean actual = (pa != null) && (CollectionUtils.isNotEmpty(pa.getLotEntity()));

        return expected == actual;
    }

    @Override
    public String toString()
    {
        return "PurchaseLotStep{}";
    }
}
