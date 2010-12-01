/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fray.evo.fitness;

/**
 *
 * @author Cyrik
 */
import com.fray.evo.EcState;
import com.fray.evo.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EcStandardFitnessCleanup implements EcFitness {

    public double augmentScore(EcState current, EcState destination, double score, boolean waypoint) {

        int overlordScore;

        if (current.overlords > destination.overlords) {
            overlordScore = (int) Math.min(100, (destination.overlords * (1 / Math.max(1, destination.supply() - destination.supplyUsed))) * 10);
        } else {
            overlordScore = (int) Math.min(100, (current.overlords * (1 / Math.max(1, current.supply() - current.supplyUsed))) * 10);
        }
        for (Unit unit : UnitLibrary.getAllZergUnits()) {
            if (unit == UnitLibrary.Overlord) {
                continue;
            }

            int currentlyBuiltUnits = getUnitByString(unit.getName(), current);
            int neededUnits = getUnitByString(unit.getName(), destination);
            score = augmentScore(score, currentlyBuiltUnits, neededUnits, unit.getMinerals()+unit.getGas());
        }

        score = augmentScore(score, current.overlords, destination.overlords, overlordScore);

        score = augmentScore(score, current.gasExtractors, destination.gasExtractors, 25);

        score = augmentScore(score, current.hatcheries, destination.hatcheries, 300);
        score = augmentDropoffScore(score, current.lairs, destination.lairs, 550, 5.5, waypoint);
        score = augmentDropoffScore(score, current.hives, destination.hives, 900, 9, waypoint);
        score = augmentDropoffScore(score, current.spawningPools, destination.spawningPools, 200, 2, waypoint);
        score = augmentDropoffScore(score, current.roachWarrens, destination.roachWarrens, 150, 1.5, waypoint);
        score = augmentDropoffScore(score, current.hydraliskDen, destination.hydraliskDen, 200, 2, waypoint);
        score = augmentDropoffScore(score, current.banelingNest, destination.banelingNest, 150, 1.5, waypoint);
        score = augmentDropoffScore(score, current.greaterSpire, destination.greaterSpire, 650, 6.5, waypoint);
        score = augmentDropoffScore(score, current.ultraliskCavern, destination.ultraliskCavern, 350, 3.5, waypoint);
        score = augmentDropoffScore(score, current.spire, destination.spire, 400, 4, waypoint);
        score = augmentDropoffScore(score, current.infestationPit, destination.infestationPit, 200, 2.0, waypoint);
        score = augmentDropoffScore(score, current.evolutionChambers, destination.evolutionChambers, 75, 0.75, waypoint);
        score = augmentScore(score, current.spineCrawlers, destination.spineCrawlers, 100);
        score = augmentScore(score, current.sporeCrawlers, destination.sporeCrawlers, 75);
        score = augmentDropoffScore(score, current.nydusNetwork, destination.nydusNetwork, 350, 3.00, waypoint);
        score = augmentScore(score, current.nydusWorm, destination.nydusWorm, 200);

        for (Upgrade upgrade : UpgradeLibrary.getAllZergUpgrades()) {

            boolean currentlyBuiltUnits = getUpgradeByString(upgrade.getName(), current);
            boolean neededUnits = getUpgradeByString(upgrade.getName(), destination);
            score = augmentScore(score, currentlyBuiltUnits, neededUnits, upgrade.getMinerals()+upgrade.getGas());
        }
        return score;
    }

    private int getByString(String name, EcState state) {
        try {
            return state.getClass().getField(name).getInt(state);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }


    private boolean getUpgradeByString(String upgradeName, EcState state) {
        String fieldName = upgradeName.substring(0, 1).toLowerCase() + upgradeName.substring(1);
        try {
            return state.getClass().getField(fieldName).getBoolean(state);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(EcStandardFitnessCleanup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private int getUnitByString(String unitName, EcState state) {
        String fieldName = unitName.toLowerCase() + "s";
        if (fieldName.equals("roachs")) {
            fieldName = "roaches";
        }
        return getByString(fieldName, state);
    }

    private double augmentScore(double score, boolean candidate, boolean destination, int cost) {
        return augmentScore(score, candidate ? 1 : 0, destination ? 1 : 0, cost);
    }

    private double augmentScore(double score, boolean a, boolean b, int mula, double mulb, boolean waypoint) {
        return augmentScore(score, a ? 1 : 0, b ? 1 : 0, mula, mulb, waypoint);
    }

    private double augmentScore(double score, int candidate, int destination, double cost) {

        return augmentScore(score, candidate, destination, cost, cost / 100.0, false);
    }

    private double augmentScore(double score, int a, int b, double mula, double mulb, boolean waypoint) {
        score += Math.max(Math.min(a, b), 0) * mula;
        if (!waypoint) {
            score += Math.max(a - b, 0) * mulb;
        }
        return score;
    }

    private double augmentDropoffScore(double score, int a, int b, double mula, double mulb, boolean waypoint) {
        score += Math.max(Math.min(a, b), 0) * mula;
        if (!waypoint) {
            for (int i = 0; i < Math.max(a - b, 0); i++) {
                mulb /= 2;
                score += mulb;
            }
        }
        // score += Math.max(a - b, 0) * mulb;
        return score;
    }

    private double augmentSlowDropoffScore(double score, int a, int b, double mula, double mulb, boolean waypoint) {
        score += Math.max(Math.min(a, b), 0) * mula;
        if (!waypoint) {
            for (int i = 0; i < Math.max(a - b, 0); i++) {
                mulb *= .97;
                score += mulb;
            }
        }
        // score += Math.max(a - b, 0) * mulb;
        return score;
    }

    @Override
    public double score(EcState candidate, EcState metric) {
        double score = 0;

        boolean keepgoing = true;
        EcState state = EcState.defaultDestination();
        for (EcState s : metric.waypoints) {
            if (keepgoing) {
                state.union(s);
            }
            if (!s.isSatisfied(candidate)) {
                keepgoing = false;
            }
        }
        if (keepgoing) {
            state.union(metric);
        }

        score = augmentScore(candidate, state, score, false);

        if (state.isSatisfied(candidate)) {//user options satisfied
            //by setting the "too many" drone score to .58 we weigh drones higher then minerals
            score = augmentScore(score, candidate.drones, state.drones, 50, .58, false);
            //by setting mineral scores above 0.01, builds without required buildings/units get a higher score
            score = augmentScore(score, (int) candidate.minerals, (int) state.minerals, .011, .011, false);
            //by setting gas scores above 0.01, builds without required buildings/units get a higher score
            score = augmentScore(score, (int) candidate.gas, (int) state.gas, .015, .015, false);
            score = Math.max(score, 0);

            candidate.preTimeScore = score;
            //using targetSeconds
            score *= ((double) candidate.targetSeconds / (double) candidate.seconds) * ((double) candidate.targetSeconds / (double) candidate.seconds);
            candidate.timeBonus = score - candidate.preTimeScore;

            //System.out.println(String.format("PreTimeScore: %.2f",c.preTimeScore));
            //System.out.println(String.format("Time Bonus: %.2f",c.timeBonus));

        } else {//user options not satisfied
            double xtraDroneScore = .6;
            if (metric.settings.overDrone || metric.settings.workerParity) {
                xtraDroneScore = 2;
            }
            score = augmentScore(score, candidate.drones, state.drones, 50, xtraDroneScore, false);
            score = augmentScore(score, (int) candidate.minerals, (int) state.minerals, .001, .001, false);
            score = augmentScore(score, (int) candidate.gas, (int) state.gas, .0012, .0012, false);
            score = Math.max(score - candidate.invalidActions, 0);
        }
        // score = Math.max(score - candidate.invalidActions -
        // candidate.actionLength - candidate.waits, 0);
        return score;
    }
}
