package net.nmw.rule;

import net.nmw.rule.steps.AdjustWeightStep;
import net.nmw.rule.steps.PurchaseIsActiveStep;
import net.nmw.rule.steps.PurchaseIsSupersededStep;
import net.nmw.rule.steps.PurchaseLotStep;
import net.nmw.rule.steps.PurchaseWeightStatusStep;
import net.nmw.rule.steps.SaleIsActiveStep;
import net.nmw.rule.steps.SaleIsSupersededStep;
import net.nmw.rule.steps.SaleLotStep;
import net.nmw.rule.steps.SaleWeightStatusStep;

/**
 * Created by nayan.wadekar on 05-Nov-2018.
 */
public enum StepKeyEnum
{
    PURCHASE_LOT_STEP(PurchaseLotStep.class),
    PURCHASE_WEIGHT_STATUS_STEP(PurchaseWeightStatusStep.class),
    PURCHASE_IS_ACTIVE_STEP(PurchaseIsActiveStep.class),
    PURCHASE_IS_SUPERSEDED_STEP(PurchaseIsSupersededStep.class),
    ADJUST_WEIGHT_STATUS_STEP(AdjustWeightStep.class),
    SALE_LOT_STEP(SaleLotStep.class),
    SALE_WEIGHT_STATUS_STEP(SaleWeightStatusStep.class),
    SALE_IS_ACTIVE_STEP(SaleIsActiveStep.class),
    SALE_IS_SUPERSEDED_STEP(SaleIsSupersededStep.class);

    private final Class<? extends Step> stepClazz;

    StepKeyEnum(final Class<? extends Step> clazz)
    {
        stepClazz = clazz;
    }

    public Class<? extends Step> getValue()
    {
        return stepClazz;
    }

    @Override
    public String toString()
    {
        return "StepKeyEnum { KeyClass = " + stepClazz + '}';
    }
}
