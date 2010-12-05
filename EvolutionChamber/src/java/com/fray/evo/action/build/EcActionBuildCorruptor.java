package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildCorruptor extends EcActionBuildUnit implements Serializable
{	
	public EcActionBuildCorruptor()
	{
		super(UnitLibrary.Corruptor);
	}

}
