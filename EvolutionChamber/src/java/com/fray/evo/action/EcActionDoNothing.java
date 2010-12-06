package com.fray.evo.action;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.util.GameLog;

public final class EcActionDoNothing extends EcAction
{

	@Override
	public void execute(EcBuildOrder s, GameLog e)
	{
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return true;
	}

}
