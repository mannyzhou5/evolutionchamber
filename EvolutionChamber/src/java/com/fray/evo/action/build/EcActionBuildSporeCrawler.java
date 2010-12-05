package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildSporeCrawler extends EcActionBuildBuilding implements Serializable
{

	public EcActionBuildSporeCrawler()
	{
		super(ZergBuildingLibrary.SporeCrawler);
	}
}
