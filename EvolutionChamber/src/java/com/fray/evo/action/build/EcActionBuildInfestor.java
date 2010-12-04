package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildInfestor extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildInfestor()
	{
		super(UnitLibrary.Infestor);
	}

}
