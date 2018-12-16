package net.nmw.rule.steps;

import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import net.nmw.persistence.InventoryEntity;
import net.nmw.persistence.InventoryEventTypeEnum;
import net.nmw.persistence.WeightStatusEnum;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.Step;

/**
 * Created by nayan.wadekar on 31-Oct-2018.
 */
public class AdjustWeightStep<E, V> extends RuleHelper implements Step<E, V>
{
    @Override
    public boolean evaluate(Entry<E, V> operand)
    {
        Set<InventoryEventTypeEnum> weightAdjustingEventTypes = ImmutableSet.of(
            InventoryEventTypeEnum.Weigh,
            InventoryEventTypeEnum.Delivery,
            InventoryEventTypeEnum.CreatedFromSplit);

        InventoryEntity ie = (InventoryEntity) getInstance(operand.getKey(), INVENTORY);

        V initialExpected = operand.getValue();
        boolean actual = ie != null && isAnyEventFound(ie, weightAdjustingEventTypes);

        return evaluateCondition(initialExpected, actual);
    }

    private boolean evaluateCondition(V input, boolean actual)
    {
        boolean output = false;

        if (input instanceof Boolean)
        {
            output = ((Boolean) input) == actual;
        }
        else if (input instanceof WeightStatusEnum)
        {
            output = (input == WeightStatusEnum.Any);
        }

        return output;
    }

    @Override
    public String toString()
    {
        return "AdjustWeightStep{}";
    }
}
