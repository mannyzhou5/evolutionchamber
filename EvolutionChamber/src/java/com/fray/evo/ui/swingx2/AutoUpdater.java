package com.fray.evo.ui.swingx2;

import static com.fray.evo.ui.swingx2.EcSwingXMain.messages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextArea;

import com.fray.evo.EvolutionChamber;
import com.fray.evo.util.EcAutoUpdate;

/**
 * Auto-updates the application.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class AutoUpdater extends JXFrame {
	private final EcAutoUpdate ecUpdater;

	private final JXBusyLabel busyLabel;

	private final JProgressBar updateProgress;

	private final JXButton yes, no, cancel;

	private final JXTextArea message;

	/**
	 * Creates the window. Call {@link #check()} to check for updates.
	 */
	public AutoUpdater() {
		setContentPane(new JXPanel(new MigLayout("fillx")));

		ecUpdater = new EcAutoUpdate(EvolutionChamber.VERSION, new EcAutoUpdate.DownloadCallback() {
			@Override
			public void checksumFailed() {
				JOptionPane.showMessageDialog(AutoUpdater.this, messages.getString("update.checksumFailed.message"), messages.getString("update.checksumFailed.title"), JOptionPane.ERROR_MESSAGE);
				dispose();
			}

			@Override
			public void updateCheckFailed() {
				JOptionPane.showMessageDialog(AutoUpdater.this, messages.getString("update.updateCheckFailed.message"), messages.getString("update.updateCheckFailed.title"), JOptionPane.WARNING_MESSAGE);
				dispose();
			}
		});

		busyLabel = new JXBusyLabel();
		busyLabel.setText(messages.getString("update.checking"));
		busyLabel.setBusy(true);
		busyLabel.setHorizontalAlignment(JLabel.CENTER);

		message = new JXTextArea();
		message.setEditable(false);
		message.setBackground(getBackground());
		message.setLineWrap(true);
		message.setWrapStyleWord(true);

		updateProgress = new JProgressBar();
		updateProgress.setValue(0);
		updateProgress.setStringPainted(true);

		cancel = new JXButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//cancel the update check or download operation
				
				if (ecUpdater.isUpdating()) {
					ecUpdater.cancel(false);
				}
				dispose();
			}
		});

		yes = new JXButton(messages.getString("update.updateAvailable.yes"));
		yes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				//download the update
				
				ecUpdater.addPropertyChangeListener(new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent evt) {
						if ("progress".equals(evt.getPropertyName())) {
							updateProgress.setValue((Integer) evt.getNewValue());
						}
					}
				});
				ecUpdater.execute();

				getContentPane().removeAll();
				getContentPane().add(updateProgress, "align center, growx, wrap");
				getContentPane().add(cancel, "align center");
				validate();
				repaint();
			}
		});

		no = new JXButton(messages.getString("update.updateAvailable.no"));
		no.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});

		getContentPane().add(busyLabel, "align center, growx, wrap");
		getContentPane().add(cancel, "align center, wrap");
	}

	/**
	 * Checks for updates.
	 */
	public void check() {
		ecUpdater.findLatestVersion(new EcAutoUpdate.FindLatestVersionCallback() {
			@Override
			public void updateAvailable(String version) {
				message.setText(messages.getString("update.updateAvailable.message"));

				getContentPane().removeAll();
				getContentPane().add(message, "align center, growx, wmin 0, wrap");
				getContentPane().add(yes, "split 2, align center");
				getContentPane().add(no);
				validate();
				repaint();
			}

			@Override
			public void noUpdateAvailable() {
				dispose();
			}
		});
	}
}
