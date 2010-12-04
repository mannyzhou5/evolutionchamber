package com.fray.evo.action.build;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.EcState;
import com.fray.evo.action.EcAction;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;

public class EcActionBuildQueen extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildQueen()
	{
		super(UnitLibrary.Queen);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.consumeHatch(this);
	}
	
	@Override
	protected void postExecute(final EcBuildOrder s, final EcEvolver e)
	{
            s.unconsumeHatch(this);
		s.AddUnits((Unit) buildable, 1);
		if (s.hatcheriesSpawningLarva < s.bases())
		{
			s.hatcheriesSpawningLarva++;
			s.addFutureAction(45, new Runnable()
			{
				@Override
				public void run()
				{
					if (e.debug && s.getLarva() < s.bases() * 19)
						e.obtained(s, " "+messages.getString("Larva") + (Math.min(s.bases()*19,s.getLarva()+4) - s.getLarva()));
					s.setLarva(Math.min(s.bases() * 19, s.getLarva() + 4));
					s.addFutureAction(45, this);
				}
			});
		}
		else
			s.addFutureAction(5, new Runnable()
			{
				@Override
				public void run()
				{
					if (s.hatcheriesSpawningLarva < s.bases())
					{
						s.hatcheriesSpawningLarva++;
						s.addFutureAction(45, new Runnable()
						{
							@Override
							public void run()
							{
								if (e.debug && s.getLarva() < s.bases() * 19)
									e.obtained(s, " Larva+" + (Math.min(s.bases()*19,s.getLarva()+4) - s.getLarva()));
								s.setLarva(Math.min(s.bases() * 19, s.getLarva() + 4));
								s.addFutureAction(45, this);
							}
						});
					}
					else
						s.addFutureAction(5, this);
				}
			});
	}
	
	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		if (s.getHatcheries() + s.getLairs() + s.getHives() == s.busyMainBuildings)
			return true;
		return false;
	}

}
