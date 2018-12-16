package net.nmw.rule.data;

import com.trafigura.logistics.persistence.InventoryWeightStatusEnum;
import net.nmw.rule.AssertionData;

import static com.trafigura.logistics.persistence.InventoryWeightStatusEnum.Blank;
import static com.trafigura.logistics.persistence.InventoryWeightStatusEnum.DerivedFinal;
import static com.trafigura.logistics.persistence.InventoryWeightStatusEnum.DerivedProvisional;
import static com.trafigura.logistics.persistence.WeightStatusEnum.Any;
import static com.trafigura.logistics.persistence.WeightStatusEnum.OfficialFinal;
import static com.trafigura.logistics.persistence.WeightStatusEnum.OfficialNotFinal;
import static com.trafigura.logistics.persistence.WeightStatusEnum.Provisional;

/**
 * Created by nayan.wadekar on 26-Oct-2018.
 */
public final class InventoryWeightStatusAssertionData extends AssertionData
{
    /*
        Below is the mapping of columns for the data.
        From [1-9] are conditions while 10] is output if each condition fulfills.

        1]  Purchase Assignment Lots recorded
        2]  Purchase Assignment Weight status
        3]  Active
        4]  Superceded
        5]  Adjust weights weigh event recorded
        6]  Sales Assignment Lots recorded
        7]  Sales Assignment weight status
        8]  Active
        9]  Superceded
        10] Inventory Weight Status
     */
    private static final Object[][] DATA = {

//        1     2       3       4     5      6     7     8      9      10
        {false, null, false, false, false, false, null, false, false, Blank},
        {true, Provisional, true, false, false, false, null, false, false, DerivedProvisional},
        {true, Provisional, false, true, false, false, null, false, false, DerivedProvisional},
        {false, OfficialNotFinal, true, false, false, false, null, false, false, DerivedProvisional},
        {true, OfficialNotFinal, false, true, false, false, null, false, false, DerivedProvisional},
        {true, OfficialFinal, true, false, false, false, null, false, false, DerivedFinal},
        {true, OfficialFinal, false, true, false, false, null, false, false, DerivedFinal},
        {true, Any, false, true, true, false, null, false, false, InventoryWeightStatusEnum.Provisional},
        {true, Any, false, true, Any, true, Provisional, true, false, DerivedProvisional},
        {true, Any, false, true, Any, true, Provisional, false, true, DerivedProvisional},
        {true, Any, false, true, Any, true, OfficialNotFinal, true, false, DerivedProvisional},
        {true, Any, false, true, Any, true, OfficialNotFinal, false, true, DerivedProvisional},
        {true, Any, false, true, Any, true, OfficialFinal, true, false, DerivedFinal},
        {true, Any, false, true, Any, true, OfficialFinal, false, true, DerivedFinal}
    };

    private InventoryWeightStatusAssertionData()
    {
    }

    public static Object[][] getData()
    {
        return AssertionData.cloneArray(DATA);
    }
}
