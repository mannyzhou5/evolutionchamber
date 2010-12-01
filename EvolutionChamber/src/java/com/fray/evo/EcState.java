package com.fray.evo;
import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import com.fray.evo.util.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;


public class EcState implements Serializable
{
	public EcSettings	settings	= new EcSettings();
        private HashSet<Upgrade> upgrades;
        private HashMap<Building, Integer> buildings;
        private HashMap<Unit, Integer> units;

	public EcState()
	{
		hatcheryTimes.add(new Integer(0));
                units = new HashMap<Unit, Integer>();
                for(Unit unit: UnitLibrary.getAllZergUnits()){
                    units.put(unit, 0);
                }
                buildings = new HashMap<Building, Integer>();
                for(Building building: BuildingLibrary.getAllZergBuildings()){
                    buildings.put(building, 0);
                }
                upgrades = new HashSet<Upgrade>();
	}

	public double					preTimeScore		= 0.0;
	public double					timeBonus			= 0.0;

	public double					minerals			= 50;
	public double					gas					= 0;
	public double					supplyUsed			= 6;
	public int						evolvingHatcheries	= 0;
	public int						evolvingLairs		= 0;
	public int						evolvingHives		= 0;
	public int						requiredBases		= 1;
	public int						hatcheries			= 1;
	public int						lairs				= 0;
	public int						hives				= 0;
	public int						spawningPools		= 0;
	public int						evolutionChambers	= 0;
	public int						roachWarrens		= 0;
	public int						hydraliskDen		= 0;
	public int						banelingNest		= 0;
	public int						infestationPit		= 0;
	public int						greaterSpire		= 0;
	public int						ultraliskCavern		= 0;
	public int						gasExtractors		= 0;
	public int						spire				= 0;
	public int						spineCrawlers		= 0;
	public int						sporeCrawlers		= 0;
	public int						nydusNetwork		= 0;
	public int						nydusWorm			= 0;

	public int						larva				= 3;

	public int						drones				= 6;
	public int						overlords			= 1;
	public int						overseers			= 0;
	public int						zerglings			= 0;
	public int						banelings			= 0;
	public int						roaches				= 0;
	public int						mutalisks			= 0;
	public int						infestors			= 0;
	public int						queens				= 0;
	public int						hydralisks			= 0;
	public int						corruptors			= 0;
	public int						ultralisks			= 0;
	public int						broodlords			= 0;
	public int						scoutDrone			= 0;

	public boolean					metabolicBoost		= false;
	public boolean					adrenalGlands		= false;
	public boolean					glialReconstitution	= false;
	public boolean					tunnelingClaws		= false;
	public boolean					burrow				= false;
	public boolean					pneumatizedCarapace	= false;
	public boolean					ventralSacs			= false;
	public boolean					centrifugalHooks	= false;
	public boolean					melee1				= false;
	public boolean					melee2				= false;
	public boolean					melee3				= false;
	public boolean					missile1			= false;
	public boolean					missile2			= false;
	public boolean					missile3			= false;
	public boolean					armor1				= false;
	public boolean					armor2				= false;
	public boolean					armor3				= false;
	public boolean					groovedSpines		= false;
	public boolean					neuralParasite		= false;
	public boolean					pathogenGlands		= false;
	public boolean					flyerAttack1		= false;
	public boolean					flyerAttack2		= false;
	public boolean					flyerAttack3		= false;
	public boolean					flyerArmor1			= false;
	public boolean					flyerArmor2			= false;
	public boolean					flyerArmor3			= false;
	public boolean					chitinousPlating	= false;

	public int						seconds				= 0;
	public int						targetSeconds		= 0;
	public int						invalidActions		= 0;
	public double					actionLength		= 0;
	public int						waits;

	public int						maxOverDrones		= 50;
	public int						overDroneEfficiency	= 80;

	public List<Integer>			hatcheryTimes		= new ArrayList<Integer>();

	public ArrayList<EcState>		waypoints			= new ArrayList<EcState>();
	public EcState					mergedWaypoints		= null;

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		EcState s = new EcState();
		assign(s);
		return s;
	}

	protected void assign(EcState s)
	{
		for (EcState st : waypoints)
			try
			{
				s.waypoints.add((EcState) st.clone());
			}
			catch (CloneNotSupportedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		try
		{
			if( mergedWaypoints == null )
				s.mergedWaypoints = null;
			else
				s.mergedWaypoints = (EcState)mergedWaypoints.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		s.settings = settings;
		s.minerals = minerals;
		s.gas = gas;
		s.supplyUsed = supplyUsed;

		s.requiredBases = requiredBases;
		s.hatcheries = hatcheries;
		s.lairs = lairs;
		s.hives = hives;
		s.spawningPools = spawningPools;
		s.banelingNest = banelingNest;
		s.evolutionChambers = evolutionChambers;
		s.roachWarrens = roachWarrens;
		s.hydraliskDen = hydraliskDen;
		s.infestationPit = infestationPit;
		s.greaterSpire = greaterSpire;
		s.ultraliskCavern = ultraliskCavern;
		s.gasExtractors = gasExtractors;
		s.spire = spire;
		s.greaterSpire = greaterSpire;
		s.spineCrawlers = spineCrawlers;
		s.sporeCrawlers = sporeCrawlers;
		s.nydusNetwork = nydusNetwork;
		s.nydusWorm = nydusWorm;
		s.scoutDrone = scoutDrone;

		s.zerglings = zerglings;
		s.banelings = banelings;
		s.roaches = roaches;
		s.mutalisks = mutalisks;
		s.drones = drones;
		s.queens = queens;
		s.hydralisks = hydralisks;
		s.infestors = infestors;
		s.corruptors = corruptors;
		s.ultralisks = ultralisks;
		s.broodlords = broodlords;
		s.overlords = overlords;
		s.overseers = overseers;

		s.metabolicBoost = metabolicBoost;
		s.adrenalGlands = adrenalGlands;
		s.glialReconstitution = glialReconstitution;
		s.tunnelingClaws = tunnelingClaws;
		s.burrow = burrow;
		s.pneumatizedCarapace = pneumatizedCarapace;
		s.ventralSacs = ventralSacs;
		s.centrifugalHooks = centrifugalHooks;
		s.melee1 = melee1;
		s.melee2 = melee2;
		s.melee3 = melee3;
		s.missile1 = missile1;
		s.missile2 = missile2;
		s.missile3 = missile3;
		s.armor1 = armor1;
		s.armor2 = armor2;
		s.armor3 = armor3;
		s.groovedSpines = groovedSpines;
		s.neuralParasite = neuralParasite;
		s.pathogenGlands = pathogenGlands;
		s.flyerAttack1 = flyerAttack1;
		s.flyerAttack2 = flyerAttack2;
		s.flyerAttack3 = flyerAttack3;
		s.flyerArmor1 = flyerArmor1;
		s.flyerArmor2 = flyerArmor2;
		s.flyerArmor3 = flyerArmor3;
		s.chitinousPlating = chitinousPlating;

		s.seconds = seconds;
		s.targetSeconds = targetSeconds;
		s.invalidActions = invalidActions;
		s.actionLength = actionLength;
	}

    protected void assignClean(EcState s) {
        for (EcState st : waypoints) {
            try {
                s.waypoints.add((EcState) st.clone());
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            if (mergedWaypoints == null) {
                s.mergedWaypoints = null;
            } else {
                s.mergedWaypoints = (EcState) mergedWaypoints.clone();
            }
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        s.settings = settings;
        s.minerals = minerals;
        s.gas = gas;
        s.supplyUsed = supplyUsed;

        s.requiredBases = requiredBases;
        s.scoutDrone = scoutDrone;

        s.buildings = (HashMap<Building, Integer>) buildings.clone();
        s.upgrades = (HashSet<Upgrade>) upgrades.clone();
        s.units = (HashMap<Unit, Integer>) units.clone();

        s.seconds = seconds;
        s.targetSeconds = targetSeconds;
        s.invalidActions = invalidActions;
        s.actionLength = actionLength;
    }

	public int supply()
	{
		return Math.min((overlords + overseers) * 8 + 2 * bases(), 200);
	}

        public int supplyClean()
	{
		return Math.min((units.get(UnitLibrary.Overlord) + units.get(UnitLibrary.Overlord)) * 8 + 2 * bases(), 200);
	}

	public static EcState defaultDestination()
	{
		EcState d = new EcState();

		d.drones = 0;
		d.overlords = 0;
		d.hatcheries = 0;
		d.targetSeconds = 60 * 120;

		return d;
	}

	public double score(EcState candidate)
	{
		return settings.getFitnessFunction().score(candidate, this);
	}

	public void union(EcState s)
	{
		if (s.requiredBases > requiredBases)
			requiredBases = s.requiredBases;

		if (s.lairs > lairs)
			lairs = s.lairs;

		if (s.hives > hives)
			hives = s.hives;

		if (s.spawningPools > spawningPools)
			spawningPools = s.spawningPools;

		if (s.banelingNest > banelingNest)
			banelingNest = s.banelingNest;

		if (s.evolutionChambers > evolutionChambers)
			evolutionChambers = s.evolutionChambers;

		if (s.roachWarrens > roachWarrens)
			roachWarrens = s.roachWarrens;

		if (s.hydraliskDen > hydraliskDen)
			hydraliskDen = s.hydraliskDen;

		if (s.infestationPit > infestationPit)
			infestationPit = s.infestationPit;

		if (s.greaterSpire > greaterSpire)
			greaterSpire = s.greaterSpire;

		if (s.ultraliskCavern > ultraliskCavern)
			ultraliskCavern = s.ultraliskCavern;

		if (s.gasExtractors > gasExtractors)
			gasExtractors = s.gasExtractors;

		if (s.spire > spire)
			spire = s.spire;

		if (s.spineCrawlers > spineCrawlers)
			spineCrawlers = s.spineCrawlers;

		if (s.sporeCrawlers > sporeCrawlers)
			sporeCrawlers = s.sporeCrawlers;

		if (s.nydusNetwork > nydusNetwork)
			nydusNetwork = s.nydusNetwork;

		if (s.nydusWorm > nydusWorm)
			nydusWorm = s.nydusWorm;

		if (s.zerglings > zerglings)
			zerglings = s.zerglings;

		if (s.banelings > banelings)
			banelings = s.banelings;

		if (s.roaches > roaches)
			roaches = s.roaches;

		if (s.mutalisks > mutalisks)
			mutalisks = s.mutalisks;

		if (s.drones > drones)
			drones = s.drones;

		if (s.nydusNetwork > nydusNetwork)
			nydusNetwork = s.nydusNetwork;

		if (s.queens > queens)
			queens = s.queens;

		if (s.hydralisks > hydralisks)
			hydralisks = s.hydralisks;

		if (s.infestors > infestors)
			infestors = s.infestors;

		if (s.corruptors > corruptors)
			corruptors = s.corruptors;

		if (s.ultralisks > ultralisks)
			ultralisks = s.ultralisks;

		if (s.broodlords > broodlords)
			broodlords = s.broodlords;

		if (s.overlords > overlords)
			overlords = s.overlords;

		if (s.overseers > overseers)
			overseers = s.overseers;

		metabolicBoost = s.metabolicBoost | metabolicBoost;
		adrenalGlands = s.adrenalGlands | adrenalGlands;
		glialReconstitution = s.glialReconstitution | glialReconstitution;
		tunnelingClaws = s.tunnelingClaws | tunnelingClaws;
		burrow = s.burrow | burrow;
		pneumatizedCarapace = s.pneumatizedCarapace | pneumatizedCarapace;
		ventralSacs = s.ventralSacs | ventralSacs;
		centrifugalHooks = s.centrifugalHooks | centrifugalHooks;
		melee1 = s.melee1 | melee1;
		melee2 = s.melee2 | melee2;
		melee3 = s.melee3 | melee3;
		missile1 = s.missile1 | missile1;
		missile2 = s.missile2 | missile2;
		missile3 = s.missile3 | missile3;
		armor1 = s.armor1 | armor1;
		armor2 = s.armor2 | armor2;
		armor3 = s.armor3 | armor3;
		groovedSpines = s.groovedSpines | groovedSpines;
		neuralParasite = s.neuralParasite | neuralParasite;
		pathogenGlands = s.pathogenGlands | pathogenGlands;
		flyerAttack1 = s.flyerAttack1 | flyerAttack1;
		flyerAttack2 = s.flyerAttack2 | flyerAttack2;
		flyerAttack3 = s.flyerAttack3 | flyerAttack3;
		flyerArmor1 = s.flyerArmor1 | flyerArmor1;
		flyerArmor2 = s.flyerArmor2 | flyerArmor2;
		flyerArmor3 = s.flyerArmor3 | flyerArmor3;
		chitinousPlating = s.chitinousPlating | chitinousPlating;

	}


	public void unionClean(EcState s)
	{
		if (s.requiredBases > requiredBases)
			requiredBases = s.requiredBases;

		for(Unit unit: units.keySet()){
                    units.put(unit, Math.max(units.get(unit), s.units.get(unit)));
                }
                for(Building building: buildings.keySet()){
                    buildings.put(building, Math.max(buildings.get(building), s.buildings.get(building)));
                }
                upgrades.addAll(s.upgrades);
	}

	public boolean isSatisfied(EcState candidate)
	{
		if (waypoints.size() > 0)
		{
			if( mergedWaypoints == null )
				mergedWaypoints = getMergedState();
			return mergedWaypoints.isSatisfied(candidate);
		}

		if (candidate.drones < drones)
			return false;
		if (candidate.zerglings < zerglings)
			return false;
		if (candidate.banelings < banelings)
			return false;
		if (candidate.roaches < roaches)
			return false;
		if (candidate.mutalisks < mutalisks)
			return false;
		if (candidate.queens < queens)
			return false;
		if (candidate.hydralisks < hydralisks)
			return false;
		if (candidate.infestors < infestors)
			return false;
		if (candidate.corruptors < corruptors)
			return false;
		if (candidate.ultralisks < ultralisks)
			return false;
		if (candidate.broodlords < broodlords)
			return false;
		if (candidate.overlords < overlords)
			return false;
		if (candidate.overseers < overseers)
			return false;

		if (candidate.bases() < requiredBases)
			return false;
		if (candidate.lairs < lairs)
			return false;
		if (candidate.hives < hives)
			return false;
		if (candidate.gasExtractors < gasExtractors)
			return false;
		if (candidate.spawningPools < spawningPools)
			return false;
		if (candidate.banelingNest < banelingNest)
			return false;
		if (candidate.roachWarrens < roachWarrens)
			return false;
		if (candidate.hydraliskDen < hydraliskDen)
			return false;
		if (candidate.infestationPit < infestationPit)
			return false;
		if (candidate.spire < spire)
			return false;
		if (candidate.greaterSpire < greaterSpire)
			return false;
		if (candidate.ultraliskCavern < ultraliskCavern)
			return false;
		if (candidate.evolutionChambers < evolutionChambers)
			return false;
		if (candidate.spineCrawlers < spineCrawlers)
			return false;
		if (candidate.sporeCrawlers < sporeCrawlers)
			return false;
		if (candidate.nydusNetwork < nydusNetwork)
			return false;
		if (candidate.nydusWorm < nydusWorm)
			return false;

		if ((!candidate.metabolicBoost) & metabolicBoost)
			return false;
		if ((!candidate.adrenalGlands) & adrenalGlands)
			return false;
		if ((!candidate.glialReconstitution) & glialReconstitution)
			return false;
		if ((!candidate.tunnelingClaws) & tunnelingClaws)
			return false;
		if ((!candidate.burrow) & burrow)
			return false;
		if ((!candidate.pneumatizedCarapace) & pneumatizedCarapace)
			return false;
		if ((!candidate.ventralSacs) & ventralSacs)
			return false;
		if ((!candidate.centrifugalHooks) & centrifugalHooks)
			return false;
		if ((!candidate.melee1) & melee1)
			return false;
		if ((!candidate.melee2) & melee2)
			return false;
		if ((!candidate.melee3) & melee3)
			return false;
		if ((!candidate.missile1) & missile1)
			return false;
		if ((!candidate.missile2) & missile2)
			return false;
		if ((!candidate.missile3) & missile3)
			return false;
		if ((!candidate.armor1) & armor1)
			return false;
		if ((!candidate.armor2) & armor2)
			return false;
		if ((!candidate.armor3) & armor3)
			return false;
		if ((!candidate.groovedSpines) & groovedSpines)
			return false;
		if ((!candidate.neuralParasite) & neuralParasite)
			return false;
		if ((!candidate.pathogenGlands) & pathogenGlands)
			return false;
		if ((!candidate.flyerAttack1) & flyerAttack1)
			return false;
		if ((!candidate.flyerAttack2) & flyerAttack2)
			return false;
		if ((!candidate.flyerAttack3) & flyerAttack3)
			return false;
		if ((!candidate.flyerArmor1) & flyerArmor1)
			return false;
		if ((!candidate.flyerArmor2) & flyerArmor2)
			return false;
		if ((!candidate.flyerArmor3) & flyerArmor3)
			return false;
		if ((!candidate.chitinousPlating) & chitinousPlating)
			return false;

		if (candidate.settings.overDrone || candidate.settings.workerParity)
		{
			int overDrones = getOverDrones(candidate);

			if (candidate.settings.overDrone && candidate.drones < overDrones)
			{
				return false;
			}
			if (candidate.settings.workerParity)
			{
				int parityDrones = getParityDrones(candidate);

				if (candidate.drones < parityDrones)
				{
					return false;
				}
			}
		}

		return true;
	}
        public boolean isSatisfiedClean(EcState candidate)
	{
		if (waypoints.size() > 0)
		{
			if( mergedWaypoints == null )
				mergedWaypoints = getMergedState();
			return mergedWaypoints.isSatisfied(candidate);
		}

		for(Unit unit: units.keySet()){
                    if(candidate.units.get(unit) < units.get(unit)){
                        return false;
                    }
                }

		if (candidate.bases() < requiredBases)
			return false;

                for(Building building: buildings.keySet()){
                    if(candidate.buildings.get(building) < buildings.get(building)){
                        return false;
                    }
                }

                if(!candidate.upgrades.containsAll(upgrades)){
                    return false;
                }

		if (candidate.settings.overDrone || candidate.settings.workerParity)
		{
			int overDrones = getOverDrones(candidate);

			if (candidate.settings.overDrone && candidate.units.get(UnitLibrary.Drone) < overDrones)
			{
				return false;
			}
			if (candidate.settings.workerParity)
			{
				int parityDrones = getParityDrones(candidate);

				if (candidate.units.get(UnitLibrary.Drone) < parityDrones)
				{
					return false;
				}
			}
		}

		return true;
	}

	public int getOverDrones(EcState s)
	{
		int overDrones = ((s.productionTime() / 17) + s.usedDrones()) * overDroneEfficiency / 100;

		overDrones = Math.min(overDrones, maxOverDrones);

		return overDrones;
	}

	public int getParityDrones(EcState s)
	{
		int optimalDrones = Math.min((Math.min(s.bases(), 3) * 16) + (s.gasExtractors * 3), maxOverDrones);
		int parityDrones = Math.min(s.getOverDrones(s), optimalDrones);

		return parityDrones;
	}

        public int getParityDronesClean(EcState s)
	{
		int optimalDrones = Math.min((Math.min(s.bases(), 3) * 16) +
                        (s.buildings.get(BuildingLibrary.Extractor) * 3), maxOverDrones);
		int parityDrones = Math.min(s.getOverDrones(s), optimalDrones);

		return parityDrones;
	}

	public int bases()
	{
		return hatcheries + lairs + evolvingHatcheries + evolvingLairs + hives + evolvingHives;
	}

        public int basesClean()
	{
		return buildings.get(BuildingLibrary.Hatchery) + buildings.get(BuildingLibrary.Lair)
                        + evolvingHatcheries + evolvingLairs + buildings.get(BuildingLibrary.Hive) + evolvingHives;
	}

	public int productionTime()
	{
		int productionTime = 0;

		// Calculate raw hatchery production time
		for (int i = 0; i < Math.min(hatcheryTimes.size(),4); i++)
		{
			productionTime += seconds - hatcheryTimes.get(i); // TODO: Change to
																// constant
		}

		return productionTime;
	}

	public int usedDrones()
	{
		return (evolvingHatcheries + evolvingLairs + evolvingHives + (hatcheries - 1) + lairs + hives + spawningPools
				+ evolutionChambers + roachWarrens + hydraliskDen + banelingNest + infestationPit + ultraliskCavern
				+ gasExtractors + spire + spineCrawlers + sporeCrawlers + nydusWorm);
	}

        public int usedDronesClean()
	{
                int total = (evolvingHatcheries + evolvingLairs + evolvingHives + -1 );
                for(Building building: buildings.keySet()){
                    if(building.getConsumes() == UnitLibrary.Drone){
                        total += buildings.get(building);
                    }
                }
		return total;
	}

	public int getEstimatedActions()
	{
		if (waypoints.size() > 0)
		{
			if( mergedWaypoints == null )
				mergedWaypoints = getMergedState();
			return mergedWaypoints.getEstimatedActions();
		}

		int i = requiredBases + lairs + hives + spawningPools + evolutionChambers + roachWarrens + hydraliskDen
				+ banelingNest + infestationPit + greaterSpire + ultraliskCavern + gasExtractors + spire
				+ spineCrawlers + sporeCrawlers + nydusNetwork + nydusWorm

				+ drones + overlords + overseers + zerglings + banelings * 2 + roaches + mutalisks*2 + infestors*2 + queens
				+ hydralisks*2 + corruptors*2 + ultralisks*2 + broodlords * 4;

		if (metabolicBoost)
			i++;
		if (adrenalGlands)
			i++;
		if (glialReconstitution)
			i++;
		if (tunnelingClaws)
			i++;
		if (burrow)
			i++;
		if (pneumatizedCarapace)
			i++;
		if (ventralSacs)
			i++;
		if (centrifugalHooks)
			i++;
		if (melee1)
			i++;
		if (melee2)
			i++;
		if (melee3)
			i++;
		if (missile1)
			i++;
		if (missile2)
			i++;
		if (missile3)
			i++;
		if (armor1)
			i++;
		if (armor2)
			i++;
		if (armor3)
			i++;
		if (groovedSpines)
			i++;
		if (neuralParasite)
			i++;
		if (pathogenGlands)
			i++;
		if (flyerAttack1)
			i++;
		if (flyerAttack2)
			i++;
		if (flyerAttack3)
			i++;
		if (flyerArmor1)
			i++;
		if (flyerArmor2)
			i++;
		if (flyerArmor3)
			i++;
		if (chitinousPlating)
			i++;
		for (EcState s : waypoints)
			i += s.getEstimatedActions();
		return i;
	}

        public int getEstimatedActionsClean()
	{
		if (waypoints.size() > 0)
		{
			if( mergedWaypoints == null )
				mergedWaypoints = getMergedState();
			return mergedWaypoints.getEstimatedActions();
		}

		int i = requiredBases;
                
                for(Integer count: units.values()){
                    i+=count;
                }

                for(Integer count: buildings.values()){
                    i+= count;
                }
		i+= upgrades.size();
		for (EcState s : waypoints)
			i += s.getEstimatedActions();
		return i;
	}

	public EcState getMergedState()
	{
		EcState state = defaultDestination();
		for (EcState s : waypoints)
		{
			state.union(s);
		}
		state.union(this);
		return state;
	}

	public String timestamp()
	{
		return seconds / 60 + ":" + (seconds % 60 < 10 ? "0" : "") + seconds % 60;
	}

	public String toCompleteString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(messages.getString("AtTime")+": " + timestamp());
		sb.append("\n"+messages.getString("Minerals")+": " + (int) minerals + "\t"+messages.getString("Gas")+":      " + (int) gas + "\t"+messages.getString("Supply")+":   " + ((int) supplyUsed)
				+ "/" + supply() + "\t"+messages.getString("Larva")+": " + larva);
		appendBuildStuff(sb);
		return sb.toString();
	}

	public String toUnitsOnlyString()
	{
		StringBuilder sb = new StringBuilder();
		appendBuildStuff(sb);
		return sb.toString();
	}

	private void appendBuildStuff(StringBuilder sb)
	{
		append(sb, "Drone", drones);
		append(sb, "Overlord", overlords);
		append(sb, "Overseer", overseers);
		append(sb, "Queen", queens);
		append(sb, "Zergling", zerglings);
		append(sb, "Baneling", banelings);
		append(sb, "Roach", roaches);
		append(sb, "Hydralisk", hydralisks);
		append(sb, "Infestor", infestors);
		append(sb, "Mutalisk", mutalisks);
		append(sb, "Corruptor", corruptors);
		append(sb, "Ultralisk", ultralisks);
		append(sb, "Brood Lord", broodlords);

		append(sb, "Bases", requiredBases);
		append(sb, "Lair", lairs);
		append(sb, "Hive", hives);
		append(sb, "Gas Extractor", gasExtractors);
		append(sb, "Spawning Pool", spawningPools);
		append(sb, "Baneling Nest", banelingNest);
		append(sb, "Roach Warren", roachWarrens);
		append(sb, "Hydralisk Den", hydraliskDen);
		append(sb, "Infestation Pit", infestationPit);
		append(sb, "Spire", spire);
		append(sb, "Ultralisk Cavern", ultraliskCavern);
		append(sb, "Greater Spire", greaterSpire);
		append(sb, "Evolution Chamber", evolutionChambers);
		append(sb, "Spine Crawler", spineCrawlers);
		append(sb, "Spore Crawler", sporeCrawlers);
		append(sb, "Nydus Network", nydusNetwork);
		append(sb, "Nydus Worm", nydusWorm);

		append(sb, "Melee +1", melee1);
		append(sb, "Melee +2", melee2);
		append(sb, "Melee +3", melee3);
		append(sb, "Missile +1", missile1);
		append(sb, "Missile +2", missile2);
		append(sb, "Missile +3", missile3);
		append(sb, "Carapace +1", armor1);
		append(sb, "Carapace +2", armor2);
		append(sb, "Carapace +3", armor3);
		append(sb, "Flyer Attacks +1", flyerAttack1);
		append(sb, "Flyer Attacks +2", flyerAttack2);
		append(sb, "Flyer Attacks +3", flyerAttack3);
		append(sb, "Flyer Armor +1", flyerArmor1);
		append(sb, "Flyer Armor +2", flyerArmor2);
		append(sb, "Flyer Armor +3", flyerArmor3);
		append(sb, "Metabolic Boost", metabolicBoost);
		append(sb, "Adrenal Glands", adrenalGlands);
		append(sb, "Glial Reconstitution", glialReconstitution);
		append(sb, "Tunneling Claws", tunnelingClaws);
		append(sb, "Burrow", burrow);
		append(sb, "Pneumatized Carapace", pneumatizedCarapace);
		append(sb, "Ventral Sacs", ventralSacs);
		append(sb, "Centrifugal Hooks", centrifugalHooks);
		append(sb, "Grooved Spines", groovedSpines);
		append(sb, "Neural Parasite", neuralParasite);
		append(sb, "Pathogen Glands", pathogenGlands);
		append(sb, "Chitinous Plating", chitinousPlating);
	}
        private void appendBuildStuffClean(StringBuilder sb)
	{
		for(Unit unit:units.keySet()){
                    append(sb, unit.getName(), units.get(unit));
                }

		append(sb, "Bases", requiredBases);

                for(Building building: buildings.keySet()){
                    append(sb, building.getName(), buildings.get(building));
                }
                //TODO clean that up
                for(Upgrade upgrade: upgrades){
                    append(sb, upgrade.getName(), true);
                }
	}

	private void append(StringBuilder sb, String name, boolean doit)
	{
		if (doit)
			sb.append("\n" + messages.getString(name.replace(' ','.')));
	}

	private void append(StringBuilder sb, String name, int count)
	{
		if (count > 0)
			sb.append("\n" + messages.getString(name.replace(' ','.')) + ": " + count);
	}

	private int currWaypoint = 0;
	public boolean waypointMissed(EcBuildOrder candidate)
	{
		if (waypoints == null || currWaypoint >= waypoints.size())
			return false;
		
		EcState s = waypoints.get(currWaypoint);
		if (candidate.seconds != s.targetSeconds)
			return false;

		if (s.isSatisfied(candidate)) {
			currWaypoint++;
			return false;
		}
			
		return true;
	}
	
	public int getCurrWaypointIndex(EcBuildOrder candidate) {
		if(currWaypoint == 0)
			return -1;

		EcState r = waypoints.get(currWaypoint - 1);
		if (r.targetSeconds == candidate.seconds)
			return currWaypoint - 1;
		return -1;
	}
	
	public int getWaypointActions(int index) {
		return waypoints.get(index).getEstimatedActions();
	}
}
