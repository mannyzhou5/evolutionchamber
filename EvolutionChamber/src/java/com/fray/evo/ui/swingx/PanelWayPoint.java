package com.fray.evo.ui.swingx;

import com.fray.evo.EcState;
import com.fray.evo.util.ZergBuildingLibrary;
import com.fray.evo.util.ZergUnitLibrary;
import com.fray.evo.util.ZergUpgradeLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.fray.evo.ui.swingx.EcSwingXMain.messages;


public class PanelWayPoint extends PanelBase
{
	private static final long serialVersionUID = -3838081126063166169L;
	private final EcState state;

    /**
	 * Constructor.
	 * @param parent the window that holds this panel.
	 */
	public PanelWayPoint(final EcSwingX parent, final EcState state)
	{
        super(parent, new GridBagLayout());

        this.state = state;

        // addInput(component, "", new ActionListener()
        // {
        // public void actionPerformed(ActionEvent e)
        // {
        // ec.POPULATION_SIZE = getDigit(e);
        // }
        // }).setText("30");
        // addInput(component, "Chromosome Length", new ActionListener()
        // {
        // public void actionPerformed(ActionEvent e)
        // {
        // ec.CHROMOSOME_LENGTH = getDigit(e);
        // }
        // }).setText("120");
        parent.addInput(this, messages.getString("waypoint.drones"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits(ZergUnitLibrary.Drone, EcSwingX.getDigit(e));
            }

            @Override
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getDrones()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.deadline"), TimeTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.targetSeconds = EcSwingX.getDigit(e);
                ((JTextField)e.getSource()).setText( formatAsTime(state.targetSeconds) );
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(formatAsTime(state.targetSeconds));
            }
        }).setText(formatAsTime(state.targetSeconds));
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.overlords"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits( ZergUnitLibrary.Overlord, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getOverlords()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.overseers"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits(ZergUnitLibrary.Overseer, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getOverseers()));
            }
        });
        parent.gridy++;
        if (state == parent.destination.get(parent.destination.size()-1)) // only put this option on the Final waypoint.
        {
            parent.addInput(this, messages.getString("waypoint.scoutTiming"), TimeTextField.class, new CustomActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    parent.destination.get(parent.destination.size()-1).scoutDrone = EcSwingX.getDigit(e);
                    ((JTextField)e.getSource()).setText( formatAsTime(state.scoutDrone) );
                }
                void reverse(Object o)
                {
                    JTextField c = (JTextField) o;
                    c.setText(formatAsTime((parent.destination.get(parent.destination.size()-1).scoutDrone)));
                }
            }).setText( formatAsTime(state.scoutDrone));
        }
        addCheck(messages.getString("waypoint.burrow"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Burrow);
                else state.removeUpgrade(ZergUpgradeLibrary.Burrow);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isBurrow());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.queens"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setUnits(ZergUnitLibrary.Queen, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getQueens()));
            }
        });
        addCheck(messages.getString("waypoint.pneumatizedCarapace"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
                else state.removeUpgrade(ZergUpgradeLibrary.PneumatizedCarapace);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isPneumatizedCarapace());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.zerglings"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setUnits(ZergUnitLibrary.Zergling, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getZerglings()));
            }
        });
        addCheck(messages.getString("waypoint.ventralSacs"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.VentralSacs);
                else state.removeUpgrade(ZergUpgradeLibrary.VentralSacs);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isVentralSacs());
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.metabolicBoost"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.MetabolicBoost);
                else state.removeUpgrade(ZergUpgradeLibrary.MetabolicBoost);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMetabolicBoost());
            }
        });
        addCheck(messages.getString("waypoint.adrenalGlands"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.AdrenalGlands);
                else state.removeUpgrade(ZergUpgradeLibrary.AdrenalGlands);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isAdrenalGlands());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.banelings"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setUnits(ZergUnitLibrary.Baneling, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getBanelings()));
            }
        });
        addCheck(messages.getString("waypoint.centrifugalHooks"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
                else state.removeUpgrade(ZergUpgradeLibrary.CentrifugalHooks);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isCentrifugalHooks());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.roaches"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setUnits(ZergUnitLibrary.Roach, EcSwingX.getDigit(e));

            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getRoaches()));
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.glialReconstitution"), new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.GlialReconstitution);
                else state.removeUpgrade(ZergUpgradeLibrary.GlialReconstitution);
            }

            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isGlialReconstitution());
            }
        });
        addCheck(messages.getString("waypoint.tunnelingClaws"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.TunnelingClaws);
                else state.removeUpgrade(ZergUpgradeLibrary.TunnelingClaws);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isTunnelingClaws());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.hydralisks"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits(ZergUnitLibrary.Hydralisk, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getHydralisks()));
            }
        });
        addCheck(messages.getString("waypoint.groovedSpines"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.GroovedSpines);
                else state.removeUpgrade(ZergUpgradeLibrary.GroovedSpines);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isGroovedSpines());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.infestors"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setUnits(ZergUnitLibrary.Infestor, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getInfestors()));
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.neuralParasite"), new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.NeuralParasite);
                else state.removeUpgrade(ZergUpgradeLibrary.NeuralParasite);
            }

            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isNeuralParasite());
            }
        });
        addCheck(messages.getString("waypoint.pathogenGlands"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.PathogenGlands);
                else state.removeUpgrade(ZergUpgradeLibrary.PathogenGlands);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isPathogenGlands());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.mutalisks"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits(ZergUnitLibrary.Mutalisk, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getMutalisks()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.ultralisks"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setUnits(ZergUnitLibrary.Ultralisk, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getUltralisks()));
            }
        });
        addCheck(messages.getString("waypoint.chitinousPlating"), new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.ChitinousPlating);
                else state.removeUpgrade(ZergUpgradeLibrary.ChitinousPlating);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isChitinousPlating());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.corruptors"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits(ZergUnitLibrary.Corruptor, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getCorruptors()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.broodlords"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setUnits( ZergUnitLibrary.Broodlord, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getBroodlords()));
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.melee") + " +1", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Melee1);
                else state.removeUpgrade(ZergUpgradeLibrary.Melee1);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMelee1());
            }
        });
        addCheck("+2", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Melee2);
                else state.removeUpgrade(ZergUpgradeLibrary.Melee2);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMelee2());
            }
        });
        addCheck("+3", new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Melee3);
                else state.removeUpgrade(ZergUpgradeLibrary.Melee3);
            }

            void reverse(Object o) {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMelee3());
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.missile") + " +1", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Missile1);
                else state.removeUpgrade(ZergUpgradeLibrary.Missile1);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMissile1());
            }
        });
        addCheck("+2", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Missile2);
                else state.removeUpgrade(ZergUpgradeLibrary.Missile2);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMissile2());
            }
        });
        addCheck("+3", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Missile3);
                else state.removeUpgrade(ZergUpgradeLibrary.Missile3);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isMissile3());
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.carapace") + " +1", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Armor1);
                else state.removeUpgrade(ZergUpgradeLibrary.Armor1);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isArmor1());
            }
        });
        addCheck("+2", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Armor2);
                else state.removeUpgrade(ZergUpgradeLibrary.Armor2);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isArmor2());
            }
        });
        addCheck("+3", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.Armor3);
                else state.removeUpgrade(ZergUpgradeLibrary.Armor3);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isArmor3());
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.flyerAttack") + " +1", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks1);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isFlyerAttack1());
            }
        });
        addCheck("+2", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks2);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isFlyerAttack2());
            }
        });
        addCheck("+3", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerAttacks3);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isFlyerAttack3());
            }
        });
        parent.gridy++;
        addCheck(messages.getString("waypoint.flyerArmor") + " +1", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerArmor1);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor1);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isFlyerArmor1());
            }
        });
        addCheck("+2", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerArmor2);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor2);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isFlyerArmor2());
            }
        });
        addCheck("+3", new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(parent.getTrue(e)) state.addUpgrade(ZergUpgradeLibrary.FlyerArmor3);
                else state.removeUpgrade(ZergUpgradeLibrary.FlyerArmor3);
            }
            void reverse(Object o)
            {
                JCheckBox c = (JCheckBox) o;
                c.setSelected(state.isFlyerArmor3());
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.bases"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.requiredBases = EcSwingX.getDigit(e);
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.requiredBases));
            }
        });
        parent.addInput(this, messages.getString("waypoint.lairs"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setBuilding(ZergBuildingLibrary.Lair, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getLairs()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.hives"), NumberTextField.class, new CustomActionListener() {
            public void actionPerformed(ActionEvent e) {
                state.setBuilding(ZergBuildingLibrary.Hive, EcSwingX.getDigit(e));
            }

            void reverse(Object o) {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getHives()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.gasExtractors"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.Extractor, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getGasExtractors()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.evolutionChambers"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.EvolutionChamber, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getEvolutionChambers()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.spineCrawlers"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.SpineCrawler, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getSpineCrawlers()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.sporeCrawlers"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.SporeCrawler, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getSporeCrawlers()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.spawningPools"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.SpawningPool, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getSpawningPools()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.banelingNests"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.BanelingNest, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getBanelingNest()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.roachWarrens"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.RoachWarren, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getRoachWarrens()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.hydraliskDens"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.HydraliskDen, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getHydraliskDen()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.infestationPits"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.InfestationPit, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getInfestationPit()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.spires"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.Spire, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getSpire()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.nydusNetworks"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.NydusNetwork, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getNydusNetwork()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.nydusWorms"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.NydusWorm, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getNydusWorm()));
            }
        });
        parent.gridy++;
        parent.addInput(this, messages.getString("waypoint.ultraliskCaverns"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.UltraliskCavern, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getUltraliskCavern()));
            }
        });
        parent.addInput(this, messages.getString("waypoint.greaterSpires"), NumberTextField.class, new CustomActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                state.setBuilding(ZergBuildingLibrary.GreaterSpire, EcSwingX.getDigit(e));
            }
            void reverse(Object o)
            {
                JTextField c = (JTextField) o;
                c.setText(Integer.toString(state.getGreaterSpire()));
            }
        });
        parent.gridy++;

        int width = 4;
        final PanelWayPoint tmp = this;
        if (state != parent.destination.get(parent.destination.size()-1)){ //add a "remove" button for all waypoints except the final destination
            parent.inputControls.add(parent.addButton(this, messages.getString("waypoint.remove"), 1, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    parent.removeTab(tmp);
                }
            }));
            width = 3;
        }

        parent.inputControls.add(parent.addButton(this, messages.getString("waypoint.reset"), width, new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                for (int i = 0; i < getComponentCount(); i++)
                {
                    Component component = getComponent(i);
                    if (component instanceof JTextField)
                    {
                        JTextField textField = (JTextField) component;
                        if (textField.getText().indexOf(":") == -1) // is
                        {
                            // not
                            // a
                            // "Deadline"
                            // field
                            textField.setText("0");
                            textField.getActionListeners()[0].actionPerformed(new ActionEvent(textField, 0, ""));
                        }
                    }
                    else if (component instanceof JCheckBox)
                    {
                        JCheckBox checkBox = (JCheckBox) component;
                        checkBox.setSelected(false);
                        checkBox.getActionListeners()[0].actionPerformed(new ActionEvent(checkBox, 0, ""));
                    }
                }
            }
        }));
    }

    String formatAsTime(int time) {
        int minutes = time / 60;
        int seconds = time % 60;

        return Integer.toString(minutes) + ":"
            + (seconds < 10 ? "0" : "")
            + Integer.toString(seconds);
    }

    public EcState getState() {
        return state;
    }
}
