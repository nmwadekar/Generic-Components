package net.nmw.rule;

import net.nmw.rule.provider.InitInventoryWeightStatus;

/**
 * Created by nayan.wadekar on 19-Nov-2018.
 */
public enum RuleKeyEnum
{

    INVENTORY_WEIGHT_STATUS(InitInventoryWeightStatus.class);

    private final Class<? extends RuleProvider> stepClazz;

    RuleKeyEnum(final Class<? extends RuleProvider> clazz)
    {
        stepClazz = clazz;
    }

    public Class<? extends RuleProvider> getValue()
    {
        return stepClazz;
    }

    @Override
    public String toString()
    {
        return "RuleKeyEnum { KeyClass = " + stepClazz + '}';
    }
}
