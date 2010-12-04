package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildCorruptor extends EcActionBuildUnit implements Serializable
{	
	public EcActionBuildCorruptor()
	{
		super(UnitLibrary.Corruptor);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpire() == 0 && s.getGreaterSpire() == 0 && s.evolvingSpires == 0)
			return true;
		return false;
	}

}
