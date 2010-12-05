package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
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
	public void execute(final EcBuildOrder s, final GameLog e)
	{
		s.minerals -= getMinerals();
		s.gas -= getGas();
		s.supplyUsed += supply-consumesUnitSupply();
		if (consumeLarva)
			s.consumeLarva(e);
		preExecute(s);
		s.addFutureAction(getTime(), new RunnableAction()
		{
			@Override
			public void run(GameLog e)
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
    public boolean isPossible(EcBuildOrder s) {
        Buildable consumes = getConsumes();
        if (consumes != UnitLibrary.Larva) {
            if (consumes instanceof Unit && s.getUnitCount((Unit) consumes) < 1) {
                return false;
            }
        }
        return isPossibleResources(s);
    }

    protected void postExecute(EcBuildOrder s, GameLog e) {
        Building builtFrom = ((Unit) buildable).getBuiltFrom();
        if (builtFrom != null) {
            s.makeBuildingNotBusy(this);
        }
        s.AddUnits((Unit) buildable, 1);
    }

    protected void preExecute(EcBuildOrder s) {
        Building builtFrom = ((Unit) buildable).getBuiltFrom();
        if (builtFrom != null) {
            s.makeBuildingBusy(builtFrom, this);
        }
        Buildable consumes = getConsumes();
        if (consumes != null && getConsumes() != UnitLibrary.Larva) {
            s.RemoveUnits((Unit) getConsumes(), 1);
        }
    }

    @Override
    public boolean isInvalid(EcBuildOrder s) {
        if (!s.hasSupply(((Unit) buildable).getSupply())) {
            return true;
        }

        ArrayList<Buildable> reqs = ((Unit) buildable).getRequirement();
        for (int i = 0; i < reqs.size(); i++) {
            Buildable req = reqs.get(i);
            if (req instanceof  Building) {
                if (!s.isBuilding((Building)req)) {
                    return true;
                }
            }
            if (req instanceof  Unit) {
                if (s.getUnitCount((Unit) req) < 1) {
                    return true;
                }
            }
            if (req instanceof Upgrade) {
                if (!s.isUpgrade((Upgrade) req)) {
                    return true;
                }
            }
        }
        Building builtFrom = ((Unit) buildable).getBuiltFrom();
        if (builtFrom != null && !s.doesNonBusyExist(((Unit)buildable).getBuiltFrom())) {
            return true;
        }
        return false;
    }
}
