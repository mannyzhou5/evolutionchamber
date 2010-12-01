package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;

public abstract class EcActionBuildUnit extends EcActionBuild implements Serializable
{
	public double supply;
	public boolean consumeLarva;
	
	public EcActionBuildUnit(Unit unit)
	{
		super(unit);
		this.supply = unit.getSupply();
		this.consumeLarva = (unit.getConsumes() == UnitLibrary.Larva);
	}

	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		s.minerals -= getMinerals();
		s.gas -= getGas();
		s.supplyUsed += supply;
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
	@Override
	protected boolean isPossibleResources(EcBuildOrder s)
	{
		if (!s.hasSupply(supply))
			return false;
		if (consumeLarva)
			if (s.larva < 1)
				return false;
		return super.isPossibleResources(s);
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return isPossibleResources(s);
	}
	
	protected abstract void postExecute(EcBuildOrder s, EcEvolver e);

	protected void preExecute(EcBuildOrder s){}

}
