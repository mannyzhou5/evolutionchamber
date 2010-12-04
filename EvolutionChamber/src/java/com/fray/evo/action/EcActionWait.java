package com.fray.evo.action;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;

public final class EcActionWait extends EcAction implements Serializable
{
	boolean	go	= false;

	@Override
	public void execute(EcBuildOrder s, EcEvolver e)
	{
		s.waits += 1;
	}

	@Override
	public boolean canExecute(EcBuildOrder s,EcEvolver e)
	{
		if (isPossible(s))
			return true;
		s.seconds += 1;
		Runnable futureAction;
		while( ( futureAction = s.getFutureAction( s.seconds ) ) != null ) {
			futureAction.run();
			go = true;
		}
		s.tick(e);
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
