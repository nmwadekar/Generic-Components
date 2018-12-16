package net.nmw.rule.steps;

import java.util.Map;

import com.trafigura.logistics.persistence.SalesAssignmentEntity;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 31-Oct-2018.
 */
public class SaleIsActiveStep<E> extends RuleHelper implements Step<E, Boolean>
{
    @Override
    public boolean evaluate(Map.Entry<E, Boolean> operand)
    {
        final SalesAssignmentEntity sa = (SalesAssignmentEntity) getInstance(operand.getKey(), SALES);

        boolean expected = operand.getValue();
        boolean actual = (sa != null && sa.getCurrentRevision() != null &&
            sa.getCurrentRevision().getStatus() != null && sa.getCurrentRevision().getStatus().isActive());

        return expected == actual;
    }

    @Override
    public String toString()
    {
        return "SaleActiveStep{}";
    }
}
