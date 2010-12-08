package com.fray.evo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import com.fray.evo.action.ActionManager;
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
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.Upgrade;
import com.fray.evo.util.ZergUnitLibrary;

/**
 * Utility to populate the list of require actions for the evolver
 */
public final class EcRequirementTree {

    /**
     * fills a List with all required actions for a EcState
     * @param destination the destination to build the action list for
     * @return a unmodifiable list of all required actions
     */
    public static List<Class<? extends EcAction>> createActionList(EcState destination) {
    	List<Class<? extends EcAction>> actions = new ArrayList<Class<? extends EcAction>>();
        actions.clear();
        actions.add(EcActionWait.class);
        actions.add(EcActionBuildQueen.class);
        actions.add(EcActionBuildDrone.class);
        if (destination.settings.useExtractorTrick) {
            actions.add(EcActionExtractorTrick.class);
        }
        actions.add(EcActionBuildHatchery.class);
        actions.add(EcActionBuildOverlord.class);
        actions.add(EcActionBuildSpawningPool.class);
        //TODO take this out and add dynamic gass needed checks
        actions.add(EcActionBuildExtractor.class);
        actions.add(EcActionMineGas.class);

        populateActionList(destination, actions);
        
        return Collections.unmodifiableList(actions);
    }

    /**
     * populates the action list with the actions of a state
     * @param destination state to take actions from
     * @param actions actionlist
     */
    private static void populateActionList(EcState destination,List<Class<? extends EcAction>> actions) {
        for (Upgrade upgrade : (HashSet<Upgrade>)destination.getUpgrades().clone()) {
            require(upgrade, destination, actions);
        }
        for (Entry<Building, Integer> entry : destination.getBuildings().entrySet()) {
            if (entry.getValue() > 0) {
                require(entry.getKey(), destination,actions);
            }
        }
        for (Entry<Unit, Integer> entry : destination.getUnits().entrySet()) {
            if (entry.getValue() > 0) {
                require(entry.getKey(), destination, actions);
            }
        }
        for (EcState s : destination.waypoints) {
            populateActionList(s,actions);
        }
    }

    /**
     * adds an action to the list of required actions
     * @param actions list of required actions
     * @param action an action to add
     */
    private static void addActionToList(List<Class<? extends EcAction>> actions, EcAction action) {
    	if (action == null) throw new InvalidParameterException();
        if (!actions.contains(action.getClass())) {
            actions.add(action.getClass());
        }
    }


    private static void require(Buildable requirement, EcState destination, List<Class<? extends EcAction>> actions) {
        if(requirement == ZergUnitLibrary.Larva){
            return;
        }
        if (requirement instanceof  Upgrade) {
            destination.AddUpgrade((Upgrade) requirement);
            require(((Upgrade)requirement).getBuiltIn(), destination, actions);
        } else if (requirement instanceof  Building) {
            destination.RequireBuilding((Building) requirement);
        } else if (requirement instanceof  Unit) {
            destination.RequireUnit((Unit) requirement);
        }
        for (int i = 0; i < requirement.getRequirement().size(); i++) {
            require(requirement.getRequirement().get(i), destination, actions);
        }
        if(requirement.getConsumes()!=null){
            require(requirement.getConsumes(), destination, actions);
        }
        addActionToList(actions, ActionManager.getActionFor(requirement));
        for (Buildable buildable : requirement.getRequirement()) {
            require(buildable, destination, actions);
        }

    }
}
