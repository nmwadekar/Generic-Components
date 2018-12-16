package net.nmw.rule;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nayan.wadekar on 29-Oct-2018.
 */
public final class RuleExecutor extends RuleHelper
{
    private static final Logger LOG = LoggerFactory.getLogger(RuleExecutor.class);

    private RuleExecutor()
    {
    }

    /*
        Returns output NULL if rule step fails or incorrect input entity.
     */
    public static <E, O> O process(final E entity, final RuleKeyEnum ruleKey)
    {
        if (entity == null)
        {
            return null;
        }

        final RuleProvider rule = RuleHelper.getInstance(ruleKey.getValue(), RuleProvider.class);

        final Class<Step>[] steps = rule.getSteps();
        final Object[][] data = rule.getData();
        Map.Entry<?, ?> operand;
        Step step;

        O resultant = null;

        boolean output;
        final int rows = data.length;
        final int columns = steps.length;

        for (int r = 0; r < rows; r++)
        {
            output = true;

            for (int c = 0; c < columns && output; c++)
            {
                LOG.debug(String.format("Processing step - [%s] VALUE = [%s] FOR [%d][%d]",
                    steps[c], data[r][c], r, c));

                step = RuleHelper.getInstance(steps[c], Step.class);

                operand = new AbstractMap.SimpleEntry<>(entity, data[r][c]);

                output &= step.evaluate(operand);
            }

            if (output)
            {
                resultant = (O) (data[r][data[r].length - 1]);

                rule.init(entity, resultant);
                LOG.info("Resultant inventory weight status = " + resultant + " at row - " + r);

                break;
            }
        }

        return resultant;
    }

    /*
         To have input as list of entities so that caller needn't iterate
         explicitly at multiple places.
         Returns output NULL if rule step fails or incorrect input entity.
     */
    public static <E, O> List<O> process(final Collection<E> entities, final RuleKeyEnum ruleKey)
    {
        if (CollectionUtils.isEmpty(entities))
        {
            return null;
        }

        List<O> cumulative = new ArrayList<>();

        for (E e : entities)
        {
            cumulative.add(process(e, ruleKey));
        }

        return cumulative;
    }
}
