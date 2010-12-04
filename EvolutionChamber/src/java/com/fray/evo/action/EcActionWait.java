package com.fray.evo.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;

public class EcActionWait extends EcAction implements Serializable
{
	boolean	go	= false;

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		s.waits += 1;
	}

	@Override
	public boolean canExecute(EcBuildOrder s)
	{
		if (isPossible(s))
			return true;
		s.seconds += 1;
		Runnable futureAction;
		while( ( futureAction = s.getFutureAction( s.seconds ) ) != null ) {
			futureAction.run();
			go = true;
		}
		s.accumulateMaterials();
		return false;
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.nothingGoingToHappen())
			return true;
		return super.isInvalid(s);
	}
	
	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		return go;
	}

}
