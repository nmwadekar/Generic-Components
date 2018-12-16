package net.nmw.rule;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import net.nmw.persistence.InventoryEntity;
import net.nmw.persistence.InventoryEventTypeEnum;
import net.nmw.persistence.PurchaseAssignmentEntity;
import net.nmw.persistence.PurchaseAssignmentStatusEnum;
import net.nmw.persistence.SalesAssignmentEntity;
import net.nmw.persistence.SalesAssignmentStatusEnum;
import net.nmw.persistence.WeightStatusEnum;

/**
 * Created by nayan.wadekar on 31-Oct-2018.
 */
public abstract class RuleHelper
{
    protected static final String PURCHASE = "P", SALES = "S", INVENTORY = "I";

    protected static <O, I extends O> O getInstance(final Class<I> clazz, final Class<O> output)
    {
        O rule = null;

        if (output.isAssignableFrom(clazz))
        {
            try
            {
                rule = clazz.newInstance();
            }
            catch (final Exception ex)
            {
                final String errMsg = "Cannot determine [%s] as its not an extension of [%s].";
                throw new RuntimeException(String.format(errMsg, clazz.getName(), output.getName()));
            }
        }

        return rule;
    }

    protected Class<? extends Step>[] getStepsFromKeys(final List<StepKeyEnum> stepKeys)
    {
        int i = 0;
        final Class<? extends Step>[] output = new Class[stepKeys.size()];

        for (final StepKeyEnum key : stepKeys)
        {
            output[i++] = key.getValue();
        }

        return output;
    }

    protected boolean compareStatus(final WeightStatusEnum expected, final WeightStatusEnum actual)
    {
        return actual == expected || expected == WeightStatusEnum.Any;
    }

    protected boolean isAnyEventFound(final InventoryEntity ie, final Set<InventoryEventTypeEnum> eventTypes)
    {
        boolean found = false;

        final Iterator<InventoryEventTypeEnum> iterator = eventTypes.iterator();

        while (iterator.hasNext() && !found)
        {
            found = CollectionUtils.isNotEmpty(ie.getEventsByEventType(iterator.next()));
        }

        return found;
    }

    protected boolean isSaleSuperseded(final SalesAssignmentEntity se)
    {
        return SalesAssignmentStatusEnum.Superseded == se.getCurrentRevision().getStatus();
    }

    protected boolean isPurchaseSuperseded(final PurchaseAssignmentEntity pa)
    {
        return (pa != null) && (PurchaseAssignmentStatusEnum.Superseded == pa.getCurrentRevision().getStatus());
    }

    protected <E> Object getInstance(E e, String type)
    {
        Object output = null;

        if (e instanceof PurchaseAssignmentEntity)
        {
            output = getInstance((PurchaseAssignmentEntity) e, type);
        }
        else if (e instanceof InventoryEntity)
        {
            output = getInstance((InventoryEntity) e, type);
        }
        else if (e instanceof SalesAssignmentEntity)
        {
            output = getInstance((SalesAssignmentEntity) e, type);
        }

        return output;
    }

    protected Object getInstance(PurchaseAssignmentEntity e, String type)
    {
        Object output;

        switch (type)
        {
            case PURCHASE:
                output = e;
                break;

            case SALES:
                output = e.getInventory().getSalesAssignment();
                break;

            case INVENTORY:
                output = e.getInventory();
                break;

            default:
                output = null;
                break;
        }

        return output;
    }


    protected Object getInstance(SalesAssignmentEntity e, String type)
    {
        Object output = null;

        switch (type)
        {
            case PURCHASE:

                if (CollectionUtils.isNotEmpty(e.getSalesAssignmentInventory()))
                {
                    output = e.getActiveSalesAssignmentInventory().getInventory().getPurchaseAssignment();
                }

                break;

            case SALES:

                output = e;
                break;

            case INVENTORY:

                if (CollectionUtils.isNotEmpty(e.getSalesAssignmentInventory()))
                {
                    output = e.getActiveSalesAssignmentInventory().getInventory();
                }

                break;

            default:

                output = null;
                break;
        }

        return output;
    }


    protected Object getInstance(InventoryEntity e, String type)
    {
        Object output;

        switch (type)
        {
            case PURCHASE:
                output = e.getPurchaseAssignment();
                break;

            case SALES:
                output = e.getSalesAssignment();
                break;

            case INVENTORY:
                output = e;
                break;

            default:
                output = null;
                break;
        }

        return output;
    }
}
