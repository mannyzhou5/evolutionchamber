package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildRoach extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildRoach()
	{
		super(ZergUnitLibrary.Roach);
	}

}
