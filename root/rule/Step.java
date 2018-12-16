package net.nmw.rule;

import java.util.Map.Entry;

/**
 * Created by nayan.wadekar on 26-Oct-2018.
 */
public interface Step<K, V>
{
    boolean evaluate(Entry<K, V> operand);
}
