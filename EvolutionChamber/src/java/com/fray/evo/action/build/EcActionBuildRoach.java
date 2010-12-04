package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildRoach extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildRoach()
	{
		super(UnitLibrary.Roach);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getRoachWarrens() == 0)
			return true;
		return false;
	}

}
