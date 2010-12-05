package com.fray.evo.action.build;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;
import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildZergling extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildZergling()
	{
		super(UnitLibrary.Zergling);
	}

	@Override
	protected void postExecute(EcBuildOrder s, GameLog e)
	{
		s.AddUnits((Unit) buildable, 2);
	}

	@Override
	protected void obtainOne(EcBuildOrder s, GameLog e)
	{
		if (e.isEnabled())
			e.printMessage(s, GameLog.MessageType.Obtained,
					" " + messages.getString(getName()) + "+2");
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		if (s.minerals >= 50 && !s.hasSupply(1))
			return true;
		return false;
	}

}
