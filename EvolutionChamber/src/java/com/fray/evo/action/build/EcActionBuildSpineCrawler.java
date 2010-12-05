package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildSpineCrawler extends EcActionBuildBuilding implements Serializable
{

	public EcActionBuildSpineCrawler()
	{
		super(ZergBuildingLibrary.SpineCrawler);
	}

}
