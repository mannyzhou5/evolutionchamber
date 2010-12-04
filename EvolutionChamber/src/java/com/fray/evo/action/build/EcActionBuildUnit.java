package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;
import com.fray.evo.util.Upgrade;

public abstract class EcActionBuildUnit extends EcActionBuild implements Serializable
{
	public double supply;
	public boolean consumeLarva;
	
	public EcActionBuildUnit(Unit unit)
	{
		super(unit);
		this.supply = unit.getSupply();
		this.consumeLarva = (UnitLibrary.Larva == unit.getConsumes());
	}

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= getMinerals();
		s.gas -= getGas();
		s.supplyUsed += supply-consumesUnitSupply();
		if (consumeLarva)
			s.consumeLarva(e);
		preExecute(s);
		s.addFutureAction(getTime(), new Runnable()
		{
			@Override
			public void run()
			{
				obtainOne(s, e);
				postExecute(s,e);
			}
		});
	}

	private double consumesUnitSupply()
	{
		return (getConsumes() != null ? ((Unit)getConsumes()).getSupply() : 0);
	}
	@Override
	protected final boolean isPossibleResources(EcBuildOrder s)
	{
		if (!s.hasSupply(supply-consumesUnitSupply()))
			return false;
		if (consumeLarva)
			if (s.getLarva() < 1)
				return false;

		//inlined super.isPossibleResources(s);
        if (s.minerals < getMinerals()) {
            return false;
        }
        if (s.gas < getGas()) {
            return false;
        }
        return true;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return isPossibleResources(s);
	}
	
	protected void postExecute(EcBuildOrder s, EcEvolver e){
            s.AddUnits((Unit) buildable, 1);
        }

	protected void preExecute(EcBuildOrder s){}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
        if(!s.hasSupply(((Unit)buildable).getSupply())){
            return true;
        }

        ArrayList<Buildable> reqs = ((Unit)buildable).getRequirement();
        for(int i = 0; i < reqs.size(); ++i) {
        	Buildable req = reqs.get(i);
            if(req.getClass() == Building.class){
                if(s.getBuildingCount((Building)req) < 1){
                    return true;
                }
            }
            if(req.getClass() == Unit.class){
                if(s.getUnitCount((Unit)req) < 1){
                    return true;
                }
            }
            if(req.getClass() == Upgrade.class){
                if(!s.isUpgrade((Upgrade)req)){
                    return true;
                }
            }
        }
        return false;
	}
}
