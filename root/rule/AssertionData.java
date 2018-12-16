package net.nmw.rule;

import static java.lang.System.arraycopy;

/**
 * Created by nayan.wadekar on 06-Nov-2018.
 */
public abstract class AssertionData
{
    protected static Object[][] cloneArray(final Object[][] source)
    {
        final int length = source.length;
        final Object[][] target = new Object[length][source[0].length];

        for (int i = 0; i < length; i++)
        {
            arraycopy(source[i], 0, target[i], 0, source[i].length);
        }

        return target;
    }
}
