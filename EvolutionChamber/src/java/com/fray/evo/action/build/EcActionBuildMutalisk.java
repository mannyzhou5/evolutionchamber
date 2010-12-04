package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildMutalisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildMutalisk()
	{
		super(UnitLibrary.Mutalisk);
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpire() == 0 && s.evolvingSpires == 0 && s.getGreaterSpire() == 0)
			return true;
		return false;
	}

}
