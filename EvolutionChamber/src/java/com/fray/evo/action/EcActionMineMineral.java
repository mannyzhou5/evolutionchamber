package com.fray.evo.action;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;

public final class EcActionMineMineral extends EcAction implements Serializable
{
	@Override
	public void execute(final EcBuildOrder s, final EcEvolver e)
	{
		if (s.settings.pullThreeWorkersOnly) 
		{
			s.dronesGoingOnMinerals += 3;
			s.dronesOnGas -= 3;
		}
		else
		{
			s.dronesGoingOnMinerals += 1;
			s.dronesOnGas -= 1;
		}
		s.addFutureAction(2, new Runnable()
		{
			@Override
			public void run()
			{
				if (s.settings.pullThreeWorkersOnly) 
				{
					if (e.debug)
						e.mining(s," "+messages.getString("3onminerals"));
					s.dronesGoingOnMinerals -= 3;
					s.dronesOnMinerals += 3;
				}
				else
				{
					if (e.debug)
						e.mining(s," "+messages.getString("1onminerals"));
					s.dronesGoingOnMinerals--;
					s.dronesOnMinerals++;
				}

			}
		});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s) {
		if (s.dronesOnGas != 0)
			return false;
		return true;
	}

	@Override
	public boolean isPossible(EcBuildOrder s)
	{
		if (s.dronesOnGas != 0)
			return true;
		return false;
	}

}