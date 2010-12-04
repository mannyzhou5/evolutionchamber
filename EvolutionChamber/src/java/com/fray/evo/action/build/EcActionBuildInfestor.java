package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildInfestor extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildInfestor()
	{
		super(UnitLibrary.Infestor);
	}

}
