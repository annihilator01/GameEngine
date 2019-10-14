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

        System.out.print(testUnitAngel.toString());
        System.out.print(testUnitDevil.toString());
        System.out.print(testUnitLich.toString());
        System.out.print(testUnitGriffin.toString());
        // Unit (end)

        // UnitsStack (begin)
        UnitsStack testUnitsStackAngel = new UnitsStack(testUnitAngel, 13);
        UnitsStack testUnitsStackDevil = new UnitsStack(testUnitDevil, 666);
        UnitsStack testUnitsStackLich = new UnitsStack(testUnitLich, 1);
        UnitsStack testUnitsStackGriffin = new UnitsStack(testUnitGriffin, 5);

        System.out.print(testUnitsStackAngel.toString());
        System.out.print(testUnitsStackDevil.toString());
        System.out.print(testUnitsStackLich.toString());
        System.out.print(testUnitsStackGriffin.toString());
        // UnitsStack (end)

        // Army (begin)
        UnitsStack[] stacks = new UnitsStack[4];
        stacks[0] = testUnitsStackAngel;
        stacks[1] = testUnitsStackDevil;
        stacks[2] = testUnitsStackLich;
        stacks[3] = testUnitsStackGriffin;

        Army army = new Army(stacks);
        System.out.print(army.toString());
        // Army (end)
    }
}
