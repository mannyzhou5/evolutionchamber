package com.fray.evo;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;
import java.util.ArrayList;

import com.fray.evo.action.EcAction;
import com.fray.evo.util.ActionList;

import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.Building;
import com.fray.evo.util.BuildingLibrary;
import com.fray.evo.util.Race;
import com.fray.evo.util.RaceLibraries;
import java.util.HashMap;

public final class EcBuildOrder extends EcState implements Serializable
{
	static final long		serialVersionUID	= 1L;
	public int				dronesGoingOnMinerals	= 6;	
	public int				dronesGoingOnGas	= 0;
	public int				dronesOnMinerals	= 0;
	public int				dronesOnGas			= 0;
	public int				evolvingSpires		= 0;
	public int				evolutionChambersInUse;
	public HashMap<EcAction,Building>       actionBusyIn            = new HashMap<EcAction, Building>();
	public HashMap<Building,ArrayList<EcAction>> madeBusyBy;
	public boolean 			droneIsScouting		= false;

	transient ActionList	futureAction		= new ActionList();
	public ArrayList<EcAction>		actions				= new ArrayList<EcAction>();

	public EcBuildOrder()
	{
        super();
        madeBusyBy = new HashMap<Building, ArrayList<EcAction>>();
        ArrayList<Building> buildingList = RaceLibraries.getBuildingLibrary(settings.race).getList();
        for(int i = 0; i < buildingList.size(); i++){
            madeBusyBy.put(buildingList.get(i), new ArrayList<EcAction>());
        }
        addFutureAction(5, new RunnableAction(){
            @Override
            public void run(GameLog e)
            {
            	dronesOnMinerals +=6;
            	dronesGoingOnMinerals -=6;
            }});
	}
	
	public EcBuildOrder(EcState importDestination)
	{
		//Fixed: Need to assign this to the variable, not the other way around.
		//-Lomilar
		importDestination.assign(this);
	}

	public void tick(GameLog e)
	{
		executeLarvaProduction(e);
		accumulateMaterials(e);
		checkScoutingDrone(e);
	}
	
	@Override
	public EcBuildOrder clone() throws CloneNotSupportedException
	{
		final EcBuildOrder s = new EcBuildOrder();
		assign(s);
		return s;
	}

	private void assign(final EcBuildOrder s)
	{
		s.dronesGoingOnMinerals = dronesGoingOnMinerals;
		s.dronesGoingOnGas = dronesGoingOnGas;
		s.dronesOnMinerals = dronesOnMinerals;
		s.dronesOnGas = dronesOnGas;
		s.evolutionChambersInUse = evolutionChambersInUse;
		super.assign(s);
	}

    @Override
	public String toString()
	{
		return toUnitsOnlyString().replaceAll("\n"," ");
	}
	
	public String toShortString()
	{
		return (messages.getString("short.time") + timestamp() + "\t"+messages.getString("short.minerals")+":" + (int) minerals + "\t"+messages.getString("short.gas")+":" + (int) gas + "\t"+messages.getString("short.larva")+":" + getLarva() + "\t"+messages.getString("short.supply")+":"
				+ ((int) supplyUsed) + "/" + supply());
	}

	public ArrayList<EcAction> getActions()
	{
		return actions;
	}

	public void addAction(EcAction ecActionBuildDrone)
	{
		actions.add(ecActionBuildDrone);
	}

	public void addFutureAction(int time, RunnableAction runnable)
	{
		time = seconds + time;
		if (futureAction == null)
			futureAction = new ActionList();
		futureAction.put(time, runnable);
		actionLength++;
	}

	public RunnableAction getFutureAction(int time)
	{
		RunnableAction result = futureAction.get(time);
		return result;
	}

	public boolean nothingGoingToHappen()
	{
		return futureAction.hasFutureActions();
	}

	public void consumeLarva(final GameLog e)
	{
		int highestLarvaHatch = 0;
		int highestLarva = 0;
		
		for (int i = 0;i < larva.size();i++)
			if (larva.get(i) > highestLarva)
			{
				highestLarvaHatch = i;
				highestLarva = larva.get(i); 
			}
		final int finalHighestLarvaHatch = highestLarvaHatch;
				
		setLarva(finalHighestLarvaHatch,getLarva(finalHighestLarvaHatch) - 1);
	}

	private void executeLarvaProduction(GameLog e)
	{
		for (int hatchIndex = 0; hatchIndex < larva.size(); hatchIndex++) {
			if (getLarva(hatchIndex) < 3)
			{
				if (larvaProduction.get(hatchIndex) == 15)
				{
					if (e.getEnable())
						e.printMessage(this, GameLog.MessageType.Obtained,
								" @" + messages.getString("Hatchery") + " #" + (hatchIndex+1) + " " + messages.getString("Larva") + " +1" );
					setLarva(hatchIndex, getLarva(hatchIndex) + 1);
					larvaProduction.set(hatchIndex, 0);
				}
				larvaProduction.set(hatchIndex, larvaProduction.get(hatchIndex) + 1);
			}
		}
	}

	private void checkScoutingDrone(GameLog e) {
		if(!droneIsScouting && scoutDrone != 0 && seconds >= scoutDrone ) {
			if (dronesGoingOnMinerals > 0) {
				droneIsScouting = true;
				dronesGoingOnMinerals--;
				if (e.getEnable())
					e.printMessage(this, GameLog.MessageType.Scout, " +1 Scouting Drone");
			} else if (dronesOnMinerals > 0) {
				droneIsScouting = true;
				dronesOnMinerals--;
				if (e.getEnable())
					e.printMessage(this, GameLog.MessageType.Scout, " +1 Scouting Drone");
			}
		}
	}

	public boolean hasSupply(double i)
	{
		if (supplyUsed + i <= supply())
			return true;
		return false;
	}

	public int mineralPatches()
	{
		return bases() * 8;
	}

	int[]		patches				= new int[24];
	public int	extractorsBuilding	= 0;
	public int	hatcheriesBuilding	= 0;
	public int	spawningPoolsInUse	= 0;
	public int	roachWarrensInUse	= 0;
	public int	infestationPitInUse	= 0;
	public int	nydusNetworkInUse = 0;

    static double[][] cachedMineralsMined = new double[200][200];

    public double mineMinerals() {
        int mineralPatches = mineralPatches();
        if(dronesOnMinerals <= 0 || mineralPatches <= 0)
            return 0;

        if(dronesOnMinerals >= 200 || mineralPatches >= 200)
            return mineMineralsImpl();

        if(cachedMineralsMined[mineralPatches][dronesOnMinerals] == 0)
            cachedMineralsMined[mineralPatches][dronesOnMinerals] = mineMineralsImpl();

        return cachedMineralsMined[mineralPatches][dronesOnMinerals];
    }

	// Mines minerals on all bases perfectly per one second.
	private double mineMineralsImpl()
	{
		int drones = dronesOnMinerals;
        int mineralPatches = mineralPatches();
        if (patches.length < bases() * 8)
			patches = new int[bases() * 8];

		for (int i = 0; i < mineralPatches; i++)
			patches[i] = 0;
		for (int i = 0; i < mineralPatches; i++)
			// Assign first drone
			if (drones > 0)
			{
				patches[i]++;
				drones--;
			}
		if (drones > 0)
			for (int i = 0; i < mineralPatches; i++)
				// Assign second drone
				if (drones > 0)
				{
					patches[i]++;
					drones--;
				}
		if (drones > 0)
			for (int i = 0; i < mineralPatches; i++)
				// Assign third drone
				if (drones > 0)
				{
					patches[i]++;
					drones--;
				}
		// Assume half the patches are close, and half are far, and the close
		// ones have more SCVs. (perfect)
		double mineralsMined = 0.0;
		for (int i = 0; i < mineralPatches; i++)
			if (i < mineralPatches / 2) // Close patch
				if (patches[i] == 0)
					;
				else if (patches[i] == 1)
					mineralsMined += 45.0 / 60.0; // Per TeamLiquid
				else if (patches[i] == 2)
					mineralsMined += 90.0 / 60.0; // Per TeamLiquid
				else
					mineralsMined += 102.0 / 60.0; // Per TeamLiquid
			else if (patches[i] == 0)
				;
			else if (patches[i] == 1)
				mineralsMined += 35.0 / 60.0; // Per TeamLiquid
			else if (patches[i] == 2)
				mineralsMined += 75.0 / 60.0; // Per TeamLiquid
			else
				mineralsMined += 100.0 / 60.0; // Per TeamLiquid

        return mineralsMined;
	}

    static double[][] cachedGasMined = new double[200][200];

    public double mineGas()
    {
    	int gasExtra = getGasExtractors();
        if (gasExtra == 0 || dronesOnGas == 0)
			return 0;

        if(gasExtra >= 200 || dronesOnGas >= 200)
            return mineGasImpl();

        if(cachedGasMined[gasExtra][dronesOnGas] == 0)
            cachedGasMined[gasExtra][dronesOnGas] = mineGasImpl();

        return cachedGasMined[gasExtra][dronesOnGas];
    }

	// Mines gas on all bases perfectly per one second.
	private double mineGasImpl()
	{
		int drones = dronesOnGas;
		int[] extractors = new int[Math.min(getGasExtractors(),bases()*2)]; // Assign drones/patch
		for (int i = 0; i < extractors.length; i++)
			extractors[i] = 0;
		for (int i = 0; i < extractors.length; i++)
			// Assign first drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		for (int i = 0; i < extractors.length; i++)
			// Assign second drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		for (int i = 0; i < extractors.length; i++)
			// Assign third drone
			if (drones > 0)
			{
				extractors[i]++;
				drones--;
			}
		double gasMined = 0.0;
		for (int i = 0; i < extractors.length; i++)
			if (extractors[i] == 0)
				;
			else if (extractors[i] == 1)
				gasMined += 38.0 / 60.0; // Per TeamLiquid
			else if (extractors[i] == 2)
				gasMined += 82.0 / 60.0; // Per TeamLiquid
			else
				gasMined += 114.0 / 60.0; // Per TeamLiquid

		return gasMined;
	}

	private void accumulateMaterials(GameLog e)
	{
		double mins = mineMinerals();
		minerals += mins;
		totalMineralsMined += mins;
		gas += mineGas();
	}

	public String timestampIncremented(int increment)
	{
		int incrementedSeconds = seconds + increment;
		return incrementedSeconds / 60 + ":" + (incrementedSeconds%60 < 10 ? "0" : "") + incrementedSeconds % 60;
	}

	public int extractors()
	{
		return (bases() + hatcheriesBuilding) * 2;
	}
	
	public int getMineableGasExtractors()
	{
		return Math.min(bases() * 2, getGasExtractors());
	}

	public void makeBuildingBusy(Building consumes,EcAction action)
	{
            //ArrayList<EcAction> acc = madeBusyBy.get(consumes);
            if(madeBusyBy.get(consumes).size() >= buildings.get(consumes)){
                if(consumes == BuildingLibrary.Hatchery){
                    makeBuildingBusy(BuildingLibrary.Lair, action);
                }else if(consumes == BuildingLibrary.Lair){
                    makeBuildingBusy(BuildingLibrary.Hive, action);
                }else{
                    throw new RuntimeException("should not have been called with too few not busy main buildings");
                }
            }else{
                madeBusyBy.get(consumes).add(action);
                actionBusyIn.put(action, consumes);
            }
	}

    public void makeBuildingNotBusy(EcAction action) {
        Building busyBuilding = actionBusyIn.get(action);
        madeBusyBy.get(busyBuilding).remove(action);
        actionBusyIn.remove(action);
    }

    public boolean doesNonBusyExist(Building building){
        if(!doesNonBusyReallyExist(building)){
            if(building == BuildingLibrary.Hatchery){
                return doesNonBusyExist(BuildingLibrary.Lair);
            }else if(building == BuildingLibrary.Lair){
                return doesNonBusyExist(BuildingLibrary.Hive);
            }else if(building == BuildingLibrary.Spire){
                return doesNonBusyExist(BuildingLibrary.GreaterSpire);
            }
            return false;
        }else{
            return true;
        }
    }
    public boolean doesNonBusyReallyExist(Building building){
        if(madeBusyBy.get(building).size() >= buildings.get(building)){
            return false;
        }else{
            return true;
        }
    }

}
