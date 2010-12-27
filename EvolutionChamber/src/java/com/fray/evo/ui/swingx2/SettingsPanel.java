package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.JXLabel;

import com.fray.evo.EcState;
import com.fray.evo.fitness.EcFitnessType;

/**
 * Contains all the settings for the build order.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class SettingsPanel extends JPanel {
	private static enum WorkerParity {
		None("settings.workerParity.none"), UntilSaturation("settings.workerParity.untilSaturation"), AllowOverdroning("settings.workerParity.allowOverdroning");

		private final String property;

		private WorkerParity(String property) {
			this.property = property;
		}

		@Override
		public String toString() {
			return messages.getString(property);
		}
	}

	private static enum Fitness {
		Standard("Standard"), Econ("Economy");

		private final String property;

		private Fitness(String property) {
			this.property = property;
		}

		@Override
		public String toString() {
			//return messages.getString(property); //TODO
			return property;
		}
	}

	private final JXComboBox workerParity;
	private final JXComboBox fitness;
	private final JCheckBox extractorTrick;
	private final JCheckBox pullPushWorkersFromGas;
	private final JCheckBox pullPush3;
	private final JCheckBox avoidExtractor;
	private final NumberTextField maxExtractorTrickSupply;
	private final NumberTextField minPoolSupply;
	private final NumberTextField minExtractorSupply;
	private final NumberTextField minHatcherySupply;
	private final TimeTextField scoutTiming;

	public SettingsPanel() {
		setLayout(new MigLayout());

		WhatsThisLabel label;

		//add "worker parity" dropdown
		workerParity = new JXComboBox(WorkerParity.values());
		add(new JXLabel(messages.getString("settings.workerParity")), "split 3, span 3");
		add(workerParity);
		label = new WhatsThisLabel("Worker parity description.\n * None: \n * Until Saturation: \n * Allow Overdroning: ");
		add(label, "wrap");

		//add "fitness" dropdown
		fitness = new JXComboBox(Fitness.values());
		add(new JXLabel("Fitness type"), "split 3, span 3");
		add(fitness);
		label = new WhatsThisLabel("The criteria EC uses to judge how good a build is.\n * Standard: The default type.\n * Economy: Favors builds with higher income rates (more workers/bases).");
		add(label, "wrap");

		//add "scout timing" textbox
		scoutTiming = new TimeTextField();
		scoutTiming.setSeconds(0);
		add(new JXLabel("Scout Timing"), "split 3, span 3");
		add(scoutTiming, "width 50!");
		label = new WhatsThisLabel("The time that a worker should be sent off to scout (\"0:00\" to disable scouting).");
		add(label, "wrap");

		//add "extractor trick" checkbox
		extractorTrick = new JCheckBox(messages.getString("settings.useExtractorTrick"));
		add(extractorTrick, "split 2");
		label = new WhatsThisLabel("Make use of the Zerg Extractor trick.\nThis temporarily frees up 1 supply by instructing a Drone to build an Extractor and then canceling the building just a few seconds later.");
		add(label);

		//add "maximum extractor supply" textbox
		maxExtractorTrickSupply = new NumberTextField();
		add(new JXLabel(messages.getString("settings.maxExtractorTrickSupply")), "align right");
		add(maxExtractorTrickSupply, "width 75!, split 2");
		label = new WhatsThisLabel("Allow the Zerg Extractor trick to be performed until the build reaches this amount of supply.");
		add(label, "wrap");

		//add "pull/push workers from gas" checkbox
		pullPushWorkersFromGas = new JCheckBox(messages.getString("settings.pullWorkersFromGas"));
		add(pullPushWorkersFromGas, "split 2");
		label = new WhatsThisLabel("Whether or not to remove workers from gas to construct buildings.");
		add(label);

		//add "minimum pool supply" textbox
		minPoolSupply = new NumberTextField();
		add(new JXLabel(messages.getString("settings.minPoolSupply")), "align right");
		add(minPoolSupply, "width 75!, split 2");
		label = new WhatsThisLabel("The minimum total supply the build must have before EC considers building a Spawning Pool.");
		add(label, "wrap");

		//add "push/pull 3 at a time" checkbox
		pullPush3 = new JCheckBox(messages.getString("settings.pullThreeWorkersTogether"));
		add(pullPush3, "split 2");
		label = new WhatsThisLabel("Always add/remove workers from gas in groups of three.");
		add(label);

		//add "minimum extractor supply" textbox
		minExtractorSupply = new NumberTextField();
		add(new JXLabel(messages.getString("settings.minExtractorSupply")), "align right");
		add(minExtractorSupply, "width 75!, split 2");
		label = new WhatsThisLabel("The minimum total supply the build must have before EC considers building an Extractor.");
		add(label, "wrap");

		//add "avoid extractor" textbox
		avoidExtractor = new JCheckBox(messages.getString("settings.avoidMiningGas"));
		add(avoidExtractor, "split 2");
		label = new WhatsThisLabel("Avoid building an Extractor if the build does not require any gas.");
		add(label);

		//add "minimum hatchery supply" textbox
		minHatcherySupply = new NumberTextField();
		add(new JXLabel(messages.getString("settings.minHatcherySupply")), "align right");
		add(minHatcherySupply, "width 75!, split 2");
		label = new WhatsThisLabel("The minimum total supply the build must have before EC considers building additional Hatcheries.");
		add(label, "wrap");

		//initialize settings to the default
		setSettings(EcState.defaultDestination());
	}

	/**
	 * Takes the settings that are defined in this panel and applies them to the
	 * given {@link EcState} object.
	 * 
	 * @param finalDestination
	 */
	public void applySettings(EcState finalDestination) {
		WorkerParity wp = (WorkerParity) workerParity.getSelectedItem();
		switch (wp) {
		case None:
			finalDestination.settings.workerParity = false;
			finalDestination.settings.overDrone = false;
			break;
		case AllowOverdroning:
			finalDestination.settings.workerParity = false;
			finalDestination.settings.overDrone = true;
			break;
		case UntilSaturation:
			finalDestination.settings.workerParity = true;
			finalDestination.settings.overDrone = false;
			break;
		}

		Fitness f = (Fitness) fitness.getSelectedItem();
		switch (f) {
		case Econ:
			finalDestination.settings.fitnessType = EcFitnessType.ECON;
			break;
		case Standard:
			finalDestination.settings.fitnessType = EcFitnessType.STANDARD;
			break;
		}

		finalDestination.scoutDrone = scoutTiming.getSeconds();
		finalDestination.settings.useExtractorTrick = extractorTrick.isSelected();
		finalDestination.settings.maximumExtractorTrickSupply = maxExtractorTrickSupply.getValue();
		finalDestination.settings.pullWorkersFromGas = pullPushWorkersFromGas.isSelected();
		finalDestination.settings.minimumPoolSupply = minPoolSupply.getValue();
		finalDestination.settings.pullThreeWorkersOnly = pullPush3.isSelected();
		finalDestination.settings.minimumExtractorSupply = minExtractorSupply.getValue();
		finalDestination.settings.avoidMiningGas = avoidExtractor.isSelected();
		finalDestination.settings.minimumHatcherySupply = minHatcherySupply.getValue();
	}

	/**
	 * Takes the settings in the given {@link EcState} object and applies them
	 * to this panel.
	 * 
	 * @param state
	 */
	public void setSettings(EcState state) {
		WorkerParity wp;
		if (state.settings.workerParity) {
			wp = WorkerParity.UntilSaturation;
		} else if (state.settings.overDrone) {
			wp = WorkerParity.AllowOverdroning;
		} else {
			wp = WorkerParity.None;
		}
		workerParity.setSelectedItem(wp);

		Fitness f;
		switch (state.settings.fitnessType) {
		case ECON:
			f = Fitness.Econ;
			break;
		case STANDARD:
			f = Fitness.Standard;
			break;
		default:
			f = Fitness.Standard;
		}
		fitness.setSelectedItem(f);

		scoutTiming.setSeconds(state.scoutDrone);
		extractorTrick.setSelected(state.settings.useExtractorTrick);
		maxExtractorTrickSupply.setValue(state.settings.maximumExtractorTrickSupply);
		pullPushWorkersFromGas.setSelected(state.settings.pullWorkersFromGas);
		minPoolSupply.setValue(state.settings.minimumPoolSupply);
		pullPush3.setSelected(state.settings.pullThreeWorkersOnly);
		minExtractorSupply.setValue(state.settings.minimumExtractorSupply);
		avoidExtractor.setSelected(state.settings.avoidMiningGas);
		minHatcherySupply.setValue(state.settings.minimumHatcherySupply);
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (Component c : this.getComponents()) {
			if (!(c instanceof WhatsThisLabel)) {
				c.setEnabled(enabled);
			}
		}
		super.setEnabled(enabled);
	}
}
