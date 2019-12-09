package gamengine;

import gamengine.unit.Unit;
import gamengine.unit.BaseUnits;
import org.json.JSONException;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UnitsLoader {
    private final ArrayList<Unit> allUnits;

    public UnitsLoader () throws Exception {
        allUnits = new ArrayList<>();

        for (BaseUnits baseUnit : BaseUnits.values()) {
            allUnits.add(baseUnit.getUnit());
        }


        // loading custom heroes from mods
        String separator = "";
        if (System.getProperty("os.name").contains("Windows")) {
            separator += "\\";
        } else if (System.getProperty("os.name").contains("Linux")) {
            separator += "/";
        } else {
            throw new Exception("You operation system is not supported!");
        }

        String modsPathname = System.getProperty("user.dir") + separator + "mods";
        File filesInMods = new File(modsPathname);
        FilenameFilter filterJSON = (dir, name) -> name.endsWith(".json");
        String[] filesJSON = filesInMods.list(filterJSON);

        if (filesJSON == null) {
            return;
        }

        for (String fileJSON : filesJSON) {
            String pathFileJSON = modsPathname + separator + fileJSON;
            String unitStringJSON = new String(Files.readAllBytes(Paths.get(pathFileJSON)));

            try {
                allUnits.add(new Unit(unitStringJSON));
            } catch (JSONException ignored) {}
        }
    }

    public ArrayList<Unit> getAllUnits() {
        return new ArrayList<>(allUnits);
    }
}
