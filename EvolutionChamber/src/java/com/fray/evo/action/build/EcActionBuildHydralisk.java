package com.fray.evo.action.build;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.Buildable;
import com.fray.evo.util.Building;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;
import com.fray.evo.util.Upgrade;

public class EcActionBuildHydralisk extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildHydralisk()
	{
		super(UnitLibrary.Hydralisk);
	}

}
