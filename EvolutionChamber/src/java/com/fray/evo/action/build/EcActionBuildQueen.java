package com.fray.evo.action.build;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;

import java.io.Serializable;

import com.fray.evo.EcBuildOrder;
import com.fray.evo.EcEvolver;
import com.fray.evo.util.GameLog;
import com.fray.evo.util.RunnableAction;
import com.fray.evo.util.Unit;
import com.fray.evo.util.UnitLibrary;

public final class EcActionBuildQueen extends EcActionBuildUnit implements Serializable
{
	public EcActionBuildQueen()
	{
		super(UnitLibrary.Queen);
	}

	@Override
	protected void preExecute(EcBuildOrder s)
	{
		s.consumeHatch(((Unit)buildable).getBuiltFrom(),this);
	}

	@Override
	protected void postExecute(final EcBuildOrder s, final GameLog e)
	{
            s.unconsumeHatch(this);
		s.AddUnits((Unit) buildable, 1);
		if (s.larva.size() > s.hasQueen.size())
		{
			spawnLarva(s, e);
		}
		else
			s.addFutureAction(5, new RunnableAction()
			{
				@Override
				public void run(GameLog e)
				{
					if (s.larva.size() > s.hasQueen.size())
						spawnLarva(s, e);
					else
						s.addFutureAction(5, this);
				}
			});
	}

	private void spawnLarva(final EcBuildOrder s, final GameLog e)
	{
		int hatchWithoutQueen = s.hasQueen.size();
		if (s.larva.size() > hatchWithoutQueen)
		{
			s.hasQueen.add(true);

			final int hatchIndex = hatchWithoutQueen;
			s.addFutureAction(40, new RunnableAction()
			{
				@Override
				public void run(GameLog e)
				{
					if (e.getEnable() && s.getLarva() < s.bases() * 19)
						e.printMessage(s, GameLog.MessageType.Obtained,
								" @"+messages.getString("Hatchery") + " #" + (hatchIndex+1) +" "
								+ messages.getString("Larva")
								+ " +"
								+ (Math.min(19, s.getLarva(hatchIndex) + 2) - s
										.getLarva(hatchIndex)));
					s.setLarva(hatchIndex, Math.min(19, s.getLarva(hatchIndex) + 2));
					s.addFutureAction(1, new RunnableAction()
					{
						@Override
						public void run(GameLog e)
						{
							if (e.getEnable() && s.getLarva() < s.bases() * 19)
								e.printMessage(s, GameLog.MessageType.Obtained,
										" @"+messages.getString("Hatchery") + " #" + (hatchIndex+1) +" "
										+ messages.getString("Larva")
										+ " +"
										+ (Math.min(19, s.getLarva(hatchIndex) + 2) - s
												.getLarva(hatchIndex)));
							s.setLarva(hatchIndex, Math.min(19, s.getLarva(hatchIndex) + 2));
						}
					});
					s.addFutureAction(45, this);
					s.larvaProduction.set(hatchIndex, s.larvaProduction.get(hatchIndex)-1);
				}
			});
		}
	}

	@Override
	public boolean isInvalid(EcBuildOrder s)
	{
		if (s.getSpawningPools() == 0)
			return true;
		if (!s.doesNonBusyExist(((Unit)buildable).getBuiltFrom()))
			return true;
		return false;
	}

}
