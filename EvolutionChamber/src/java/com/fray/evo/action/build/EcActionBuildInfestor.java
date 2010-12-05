package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergUnitLibrary;

public final class EcActionBuildInfestor extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildInfestor()
	{
		super(ZergUnitLibrary.Infestor);
	}

}
