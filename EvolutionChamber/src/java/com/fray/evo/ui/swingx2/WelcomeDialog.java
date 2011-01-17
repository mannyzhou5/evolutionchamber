package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXLabel;

/**
 * A welcome dialog that is shown when the user first runs Evolution Chamber.
 * @author mike.angstadt
 *
 */
@SuppressWarnings("serial")
public class WelcomeDialog extends JDialog{
	/**
	 * The welcome message.
	 */
	private final JXLabel welcome;
	
	/**
	 * A checkbox that prevents the dialog from appearing again.
	 */
	private final JCheckBox dontShowAgain;
	
	/**
	 * Closes the dialog.
	 */
	private final JXButton close;
	
	public WelcomeDialog(Window parent) {
		super(parent, "Welcome", JDialog.ModalityType.DOCUMENT_MODAL);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e){
				if (dontShowAgain.isSelected()){
					EcSwingXMain.userSettings.setShowWelcome(false);
				}
			}
		});

		getContentPane().setLayout(new MigLayout("fillx"));
		
		welcome = new JXLabel(messages.getString("welcome"));
		getContentPane().add(welcome, "align center, span 2, growx, growy, wrap");
		
		dontShowAgain = new JCheckBox("Don't show this again.");
		getContentPane().add(dontShowAgain);
		
		close = new JXButton("Close");
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				WelcomeDialog.this.dispose();
			}
		});
		getContentPane().add(close, "align right");

		pack();
		setLocationRelativeTo(parent);
	}
}
