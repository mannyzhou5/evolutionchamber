package com.fray.evo.action.build;

import java.io.Serializable;

import com.fray.evo.util.ZergBuildingLibrary;

public final class EcActionBuildUltraliskCavern extends EcActionBuildBuilding implements Serializable
{
	public EcActionBuildUltraliskCavern()
	{
		super(ZergBuildingLibrary.UltraliskCavern);
	}
}
