import gamengine.*;
import gamengine.units.*;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Unit (begin)
        Unit testUnitAngel = new Angel();
        Unit testUnitDevil = new Devil();
        Unit testUnitLich = new Lich();
        Unit testUnitGriffin = new Griffin();

        System.out.print(testUnitAngel);
        System.out.print(testUnitDevil);
        System.out.print(testUnitLich);
        System.out.print(testUnitGriffin);
        // Unit (end)

        // UnitsStack (begin)
        UnitsStack testUnitsStackAngel = new UnitsStack(testUnitAngel, 13);
        UnitsStack testUnitsStackDevil = new UnitsStack(testUnitDevil, 666);
        UnitsStack testUnitsStackLich = new UnitsStack(testUnitLich, 1);
        UnitsStack testUnitsStackGriffin = new UnitsStack(testUnitGriffin, 5);

        System.out.print(testUnitsStackAngel);
        System.out.print(testUnitsStackDevil);
        System.out.print(testUnitsStackLich);
        System.out.print(testUnitsStackGriffin);
        // UnitsStack (end)

        // Army (begin)
        UnitsStack[] stacks = new UnitsStack[4];
        stacks[0] = testUnitsStackAngel;
        stacks[1] = testUnitsStackDevil;
        stacks[2] = testUnitsStackLich;
        stacks[3] = testUnitsStackGriffin;

        Army army = new Army(stacks);
        System.out.print(army);
        // Army (end)
    }
}
