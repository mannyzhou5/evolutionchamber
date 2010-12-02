package com.fray.evo;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;
import java.util.*;

import com.fray.evo.util.ActionList;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.BuildingLibrary;

public class EcBuildOrder extends EcState implements Serializable
{
	static final long		serialVersionUID	= 1L;
	public int				dronesGoingOnMinerals	= 6;	
	public int				dronesGoingOnGas	= 0;
	public int				dronesOnMinerals	= 0;
	public int				dronesOnGas			= 0;
	boolean					buildingLarva		= false;
	public int				evolvingSpires		= 0;
	public int				queensBuilding		= 0;
	public int				spiresInUse			= 0;
	public int				evolutionChambersInUse;
	public boolean 			droneIsScouting		= false;

	transient ActionList	futureAction		= new ActionList();
	public ArrayList<EcAction>		actions				= new ArrayList<EcAction>();

	public EcBuildOrder()
	{
        super();
        addFutureAction(5, new Runnable(){
            @Override
            public void run()
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

	@Override
	public EcBuildOrder clone() throws CloneNotSupportedException
	{
		final EcBuildOrder s = new EcBuildOrder();
		assign(s);
		return s;
	}

	private void assign(final EcBuildOrder s)
	{
		s.setLarva(getLarva());
		s.dronesGoingOnMinerals = dronesGoingOnMinerals;
		s.dronesGoingOnGas = dronesGoingOnGas;
		s.dronesOnMinerals = dronesOnMinerals;
		s.dronesOnGas = dronesOnGas;
		s.buildingLarva = buildingLarva;
		s.queensBuilding = queensBuilding;
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

	public List<EcAction> getActions()
	{
		return actions;
	}

	public void addAction(EcAction ecActionBuildDrone)
	{
		actions.add(ecActionBuildDrone);
	}

	public void addFutureAction(int time, Runnable runnable)
	{
		time = seconds + time;
		if (futureAction == null)
			futureAction = new ActionList();
		futureAction.put(time, runnable);
		actionLength++;
	}

	public Runnable getFutureAction(int time)
	{
		Runnable result = futureAction.get(time);
		return result;
	}

	public boolean nothingGoingToHappen()
	{
		return futureAction.hasFutureActions();
	}

	public void consumeLarva(final EcEvolver e)
	{
		final EcBuildOrder t = this;
		setLarva(getLarva() - 1);
		if (!buildingLarva)
		{
			buildingLarva = true;
			addFutureAction(15, new Runnable()
			{
				@Override
				public void run()
				{
					if (e.debug)
						e.obtained(t, " "+messages.getString("Larva")+"+1");
					setLarva(Math.max(Math.min(getLarva() + bases(), bases() * 3), getLarva()));
					if (getLarva() < 3 * bases())
						addFutureAction(15, this);
					else
						buildingLarva = false;
				}
			});
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
	public int	hatcheriesSpawningLarva = 0;
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
        if (getGasExtractors() == 0 || dronesOnGas == 0)
			return 0;

        if(getGasExtractors() >= 200 || dronesOnGas >= 200)
            return mineGasImpl();

        if(cachedGasMined[getGasExtractors()][dronesOnGas] == 0)
            cachedGasMined[getGasExtractors()][dronesOnGas] = mineGasImpl();

        return cachedGasMined[getGasExtractors()][dronesOnGas];
    }

	// Mines gas on all bases perfectly per one second.
	public double mineGasImpl()
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

	public void accumulateMaterials()
	{
		minerals += mineMinerals();
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

	public void consumeHatch(int seconds)
	{
		boolean usehatch = false;
		boolean uselair = false;
		if (getHatcheries() > 0)
		{
			RemoveBuilding(BuildingLibrary.Hatchery);
			evolvingHatcheries++;
			usehatch = true;
		}
		else if (getLairs() > 0)
		{
			RemoveBuilding(BuildingLibrary.Lair);
			evolvingLairs++;
			uselair = true;
		}
		else
		{
			RemoveBuilding(BuildingLibrary.Hive);
			evolvingHives++;
		}
		final boolean useHatch = usehatch;
		final boolean useLair = uselair;
		addFutureAction(seconds, new Runnable()
		{
			@Override
			public void run()
			{
				if (useHatch)
				{
					evolvingHatcheries--;
					AddBuilding(BuildingLibrary.Hatchery);
				}
				else if (useLair)
				{
					evolvingLairs--;
					AddBuilding(BuildingLibrary.Lair);
				}
				else
				{
					evolvingHives--;
					AddBuilding(BuildingLibrary.Hive);
				}
			}
		});
	}


}
