package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXButton;
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
	private static Logger logger = Logger.getLogger(AboutDialog.class.getName());
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
		
		getContentPane().setLayout(new MigLayout("filly"));
		
		JPanel left = createLeftHalf();
		getContentPane().add(left, "growy");
		
		JPanel right = createRightHalf();
		getContentPane().add(right, "gapleft 10px, growy");

		pack();
		setLocationRelativeTo(parent);
	}

	private JPanel createLeftHalf() {
		JPanel left = new JPanel(new MigLayout("ins 0"));
		
		JXLabel label = new JXLabel(new ImageIcon(AboutDialog.class.getResource(EcSwingXMain.iconLocation)));
		left.add(label, "align center, span 2, growx, wrap");

		label = new JXLabel("<html><b><font size=+1>" + messages.getString("title") + "</font></b><br><center><i>version " + EvolutionChamber.VERSION + "</i></center></html>");
		label.setHorizontalAlignment(JLabel.CENTER);
		left.add(label, "align center, span 2, wrap");

		JXHyperlink link = new JXHyperlink();
		try {
			link.setURI(new URI("http://evolutionchamber.googlecode.com"));
		} catch (URISyntaxException e) {
		}
		left.add(link, "align center, span 2, gapbottom 15, wrap");

		left.add(new JXLabel("<html><b>Please submit bug reports and feature requests here:</b></html>"), "align center, span 2, wrap");
		link = new JXHyperlink();
		try {
			link.setURI(new URI("http://code.google.com/p/evolutionchamber/issues/list"));
		} catch (URISyntaxException e) {
		}
		left.add(link, "align center, span 2, gapbottom 15, wrap");

		label = new JXLabel("<html><b>Contributors</b></html>");
		left.add(label, "align center, span 2, wrap");
		label = new JXLabel("<html>Azzurite (UI)<br>DocMaboul (Timing)<br>Lomilar (Lead)<br>mulander (Auto-updater)<br>Utena (Genetics)<br>Bumblebees (Features)<br>Qwerty10010 (Docs)</html>");
		left.add(label, "wmin 0, top");
		label = new JXLabel("<html>Abydos1 (Terran/Protoss)<br>Infinity0 (Terran/Protoss)<br>Mangst (Overall Stud Muffin)<br>kjoonlee (Korean translation)<br>Cyrik (Refactoring)<br>Nafets.st (Optimization)<br>bdurrer (German translation)</html>");
		left.add(label, "wmin 0, top");

		return left;
	}

	private JPanel createRightHalf() {
		JPanel right = new JPanel(new MigLayout("ins 0"));
		
		JXLabel label = new JXLabel("<html><b>Changelog</b></html>");
		right.add(label, "wrap");

		String lang = messages.getLocale().getLanguage();
		String changelogStr = getChangelog(lang);
		
		JEditorPane changelog = new JEditorPane("text/html", changelogStr);
		changelog.setText(changelogStr);
		changelog.setEditable(false);
		changelog.setCaretPosition(0); //force the JScrollPane to scroll to the top
		JScrollPane sp = new JScrollPane(changelog);
		right.add(sp, "width 300px, height 100%, wrap");
		
		JXButton close = new JXButton("Close");
		close.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				AboutDialog.this.dispose();
			}
		});
		right.add(close, "align right");

		return right;
	}

	/**
	 * Gets the text of the changelog.
	 * @param language the requested language of the changelog
	 * @return the text of the changelog.  Returns the English changelog if the requested language is not available.
	 */
	private static String getChangelog(String language) {
		InputStream in = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			in = AboutDialog.class.getResourceAsStream("changelog_" + language + ".txt");
			if (in == null) {
				//use the English changelog if a changelog for the requested language doesn't exist
				in = AboutDialog.class.getResourceAsStream("changelog_en.txt");
			}

			int read;
			while ((read = in.read()) != -1) {
				out.write(read);
			}

			return new String(out.toByteArray());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Problem getting changelog.", e);
			return "Error retrieveing changelog";
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
