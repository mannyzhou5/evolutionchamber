package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXHyperlink;
import org.jdesktop.swingx.JXLabel;

import com.fray.evo.EvolutionChamber;

/**
 * Displays general information about the application.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class AboutDialog extends JDialog {
	private static AboutDialog instance;

	/**
	 * Gets an instance of this class. The singleton pattern prevents the user
	 * from displaying more than one dialogs at once.
	 * 
	 * @param parent the parent window
	 * @return an instance of the dialog
	 */
	public static AboutDialog getInstance(Window parent) {
		if (instance == null) {
			instance = new AboutDialog(parent);
		}
		return instance;
	}

	/**
	 * Constructor.
	 * 
	 * @param parent the parent window
	 */
	private AboutDialog(Window parent) {
		super(parent, "About", JDialog.ModalityType.DOCUMENT_MODAL);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				instance = null;
			}
		});

		getContentPane().setLayout(new MigLayout("fillx"));

		JXLabel label = new JXLabel(new ImageIcon(AboutDialog.class.getResource(EcSwingXMain.iconLocation)));
		getContentPane().add(label, "align center, span 2, growx, wrap");

		label = new JXLabel("<html><b><font size=+1>" + messages.getString("title") + "</font></b><br><center><i>version " + EvolutionChamber.VERSION + "</i></center></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		getContentPane().add(label, "align center, span 2, wrap");

		JXHyperlink link = new JXHyperlink();
		try {
			link.setURI(new URI("http://evolutionchamber.googlecode.com"));
		} catch (URISyntaxException e) {
		}
		getContentPane().add(link, "align center, span 2, gapbottom 15, wrap");

		getContentPane().add(new JXLabel("<html><b>Please submit bug reports and feature requests here:</b></html>"), "align center, span 2, wrap");
		link = new JXHyperlink();
		try {
			link.setURI(new URI("http://code.google.com/p/evolutionchamber/issues/list"));
		} catch (URISyntaxException e) {
		}
		getContentPane().add(link, "align center, span 2, gapbottom 15, wrap");

		getContentPane().add(new JXLabel("<html><b>Contributors</b></html>"), "align center, span 2, wrap");

		label = new JXLabel("<html>Azzurite (UI)<br>DocMaboul (Timing)<br>Lomilar (Lead)<br>mulander (Auto-updater)<br>Utena (Genetics)<br>Bumblebees (Features)<br>Qwerty10010 (Docs)</html>");
		getContentPane().add(label, "wmin 0, top");
		label = new JXLabel("<html>Abydos1 (Terran/Protoss)<br>Infinity0 (Terran/Protoss)<br>Mangst (Overall Stud Muffin)<br>kjoonlee (Korean translation)<br>Cyrik (Refactoring)<br>Nafets.st (Optimization)<br>bdurrer (German translation)</html>");
		getContentPane().add(label, "wmin 0, top");

		pack();
		setLocationRelativeTo(parent);
	}
}
