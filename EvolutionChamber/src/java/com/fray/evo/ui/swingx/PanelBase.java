package com.fray.evo.ui.swingx;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;


public abstract class PanelBase extends JPanel
{
	private static final long serialVersionUID = 4799832117789414417L;
	private final EcSwingX parent;

    /**
	 * Constructor.
	 * @param parent the window that holds this panel.
     * @param layout
	 */
	protected PanelBase(final EcSwingX parent, LayoutManager layout)
	{
        super(layout);
        this.parent = parent;
    }

    protected JPanel addRadioButtonBox(String title, String[] captions, int defaultSelected, final CustomActionListener a)
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.gridy = parent.gridy;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);

        JRadioButton[] buttons = new JRadioButton[captions.length];
        ButtonGroup group = new ButtonGroup();
        JPanel radioButtonBox = new JPanel();
        radioButtonBox.setBorder(BorderFactory.createTitledBorder(title));

        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JRadioButton(captions[i]);
            buttons[i].addActionListener(a);
            parent.inputControls.add(buttons[i]);
            group.add(buttons[i]);
            if (i == defaultSelected)
                buttons[i].setSelected(true);
            radioButtonBox.add(buttons[i]);
        }
        add(radioButtonBox, gridBagConstraints);
        return radioButtonBox;
    }

    protected JCheckBox addCheck(String name, final CustomActionListener a)
    {
        GridBagConstraints gridBagConstraints;

        final JCheckBox checkBox = new JCheckBox();
        checkBox.setText(name);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = .5;
        if (name.length() == 2)
            gridBagConstraints.gridwidth = 1;
        else
            gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridy = parent.gridy;
        gridBagConstraints.insets = new Insets(1, 1, 1, 1);
        add(checkBox, gridBagConstraints);
        checkBox.addActionListener(a);
        checkBox.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent arg0)
            {
                a.actionPerformed(new ActionEvent(checkBox, 0, "changed"));
            }
        });
        parent.inputControls.add(checkBox);
        return checkBox;
    }
}
