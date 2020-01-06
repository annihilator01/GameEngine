package gamengine;

import gamengine.unit.BaseUnit;
import gamengine.unit.Unit;
import org.json.JSONException;

import javax.swing.*;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class UnitLoader {
    private final HashMap<String, Unit> allUnits;

    public UnitLoader() throws Exception {
        allUnits = new HashMap<>();

        for (BaseUnit baseUnit : BaseUnit.values()) {
            allUnits.put(baseUnit.getUnit().getType(), baseUnit.getUnit());
        }


        // loading custom heroes from mods
        String modsPathname = System.getProperty("user.dir") + "/mods";
        File filesInMods = new File(modsPathname);
        FilenameFilter filterJSON = (dir, name) -> name.endsWith(".json");
        String[] filesJSON = filesInMods.list(filterJSON);

        if (filesJSON == null) {
            return;
        }

        for (String fileJSON : filesJSON) {
            String pathFileJSON = modsPathname + "/" + fileJSON;
            String unitStringJSON = new String(Files.readAllBytes(Paths.get(pathFileJSON)));

            try {
                Unit tmpUnitFromJSON = new Unit(unitStringJSON);
                allUnits.put(tmpUnitFromJSON.getType(), tmpUnitFromJSON);
            } catch (JSONException ignored) {}
        }
    }

    public ArrayList<Unit> getAllUnits() {
        return new ArrayList<>(allUnits.values());
    }

    public Unit createUnit(String unitType) {
        if (allUnits.containsKey(unitType)) {
            return new Unit(allUnits.get(unitType));
        } else {
            throw new IllegalArgumentException("\nInvalid unit type: doesn't exist neither in base units group nor in mods");
        }
    }
}
