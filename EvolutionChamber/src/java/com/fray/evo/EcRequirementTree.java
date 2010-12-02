package com.fray.evo;

import com.fray.evo.action.ActionManager;
import java.util.Map;

import com.fray.evo.action.EcAction;
import com.fray.evo.action.EcActionExtractorTrick;
import com.fray.evo.action.EcActionMineGas;
import com.fray.evo.action.EcActionWait;
import com.fray.evo.action.build.EcActionBuildDrone;
import com.fray.evo.action.build.EcActionBuildExtractor;
import com.fray.evo.action.build.EcActionBuildHatchery;
import com.fray.evo.action.build.EcActionBuildOverlord;
import com.fray.evo.action.build.EcActionBuildQueen;
import com.fray.evo.action.build.EcActionBuildSpawningPool;
import com.fray.evo.action.build.EcActionBuildZergling;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import java.util.Map.Entry;

public class EcRequirementTree {

    static int max;

    public static void execute(EcState destination) {
        max = 0;
        //BIG AND EVIL GLOBAL STATE, BE VERY CAREFUL HERE!!!!!!!!!!!!!!!!!!!!!!!
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        //TODO take this out!
        Map<Integer, Class> actions = EcAction.actions;
        actions.clear();

        add(actions, new EcActionWait());
        add(actions, new EcActionBuildQueen());
        add(actions, new EcActionBuildDrone());
        if (destination.settings.useExtractorTrick) {
            add(actions, new EcActionExtractorTrick());
        }
        add(actions, new EcActionBuildHatchery());
        add(actions, new EcActionBuildOverlord());
        add(actions, new EcActionBuildSpawningPool());
        //TODO take this out and add dynamic gass needed checks
        add(actions, new EcActionBuildExtractor());
        add(actions, new EcActionMineGas());

        actions(destination, actions);

    }

    private static void actions(EcState destination,Map<Integer, Class> map) {
        for (Upgrade upgrade : destination.getUpgrades()) {
            require(upgrade, destination, map);
        }
        for (Entry<Building, Integer> entry : destination.getBuildings().entrySet()) {
            if (entry.getValue() > 0) {
                require(entry.getKey(), destination,map);
            }
        }
        for (Entry<Unit, Integer> entry : destination.getUnits().entrySet()) {
            if (entry.getValue() > 0) {
                require(entry.getKey(), destination, map);
            }
        }
        for (EcState s : destination.waypoints) {
            actions(s,map);
        }
    }

    private static void add(Map<Integer, Class> actions, EcAction action) {
        if (!actions.containsValue(action.getClass())) {
            actions.put(max++, action.getClass());
        }
    }

    private static void require(Buildable requirement, EcState destination,Map<Integer, Class> map) {
        if (requirement.getClass() == Upgrade.class) {
            destination.AddUpgrade((Upgrade) requirement);
        } else if (requirement.getClass() == Building.class) {
            destination.RequireBuilding((Building) requirement);
        } else if (requirement.getClass() == Unit.class) {
            destination.RequireUnit((Unit) requirement);
        }
        add(map,ActionManager.getActionFor(requirement));
        for (Buildable buildable : requirement.getRequirement()) {
            require(buildable, destination,map);
        }

    }
}
