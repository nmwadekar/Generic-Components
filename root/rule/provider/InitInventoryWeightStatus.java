package net.nmw.rule.provider;

import java.util.ArrayList;
import java.util.List;

import com.trafigura.logistics.persistence.InventoryEntity;
import com.trafigura.logistics.persistence.InventoryWeightStatusEnum;
import net.nmw.rule.RuleHelper;
import net.nmw.rule.RuleProvider;
import net.nmw.rule.Step;
import net.nmw.rule.StepKeyEnum;
import net.nmw.rule.data.InventoryWeightStatusAssertionData;

/**
 * Created by nayan.wadekar on 26-Oct-2018.
 */
public class InitInventoryWeightStatus<E> extends RuleHelper implements RuleProvider<E, InventoryWeightStatusEnum>
{
    private final List<StepKeyEnum> steps = new ArrayList<>();

    public InitInventoryWeightStatus()
    {
        steps.add(StepKeyEnum.PURCHASE_LOT_STEP);
        steps.add(StepKeyEnum.PURCHASE_WEIGHT_STATUS_STEP);
        steps.add(StepKeyEnum.PURCHASE_IS_ACTIVE_STEP);
        steps.add(StepKeyEnum.PURCHASE_IS_SUPERSEDED_STEP);
        steps.add(StepKeyEnum.ADJUST_WEIGHT_STATUS_STEP);
        steps.add(StepKeyEnum.SALE_LOT_STEP);
        steps.add(StepKeyEnum.SALE_WEIGHT_STATUS_STEP);
        steps.add(StepKeyEnum.SALE_IS_ACTIVE_STEP);
        steps.add(StepKeyEnum.SALE_IS_SUPERSEDED_STEP);
    }

    @Override
    public Object[][] getData()
    {
        return InventoryWeightStatusAssertionData.getData();
    }

    @Override
    public Class<? extends Step>[] getSteps()
    {
        return getStepsFromKeys(steps);
    }

    @Override
    public String toString()
    {
        return "InventoryWeightStatusInitializer { steps = " + steps + '}';
    }

    @Override
    public void init(final E entity, final InventoryWeightStatusEnum weightStatus)
    {
        InventoryEntity ie = (InventoryEntity) getInstance(entity, RuleHelper.INVENTORY);

        if (ie != null)
        {
            ie.setWeightStatus(weightStatus);
        }
    }
}
