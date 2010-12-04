package com.fray.evo.ui.swingx;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import com.fray.evo.util.EcAutoUpdate;
import com.fray.evo.util.EcMessages;

/**
 * Contains the main method to launch the GUI application.
 * 
 * @author mike.angstadt
 * 
 */
public class EcSwingXMain
{
	public static final EcMessages	messages	= new EcMessages("com/fray/evo/ui/swingx/messages");
	public static final String		EC_VERSION	= "0022";
	public static final String iconLocation = "/com/fray/evo/ui/swingx/evolution_chamber.png";

	public static void main(String args[])
	{
		// run Mac OS X customizations if user is on a Mac
		// this code must *literally* run before *anything* else
		// graphics-related...putting this code at the beginning of
		// EcSwingX.main() doesn't quite work--the application name (which
		// appears as the first menu item in the Mac menu bar) does not get
		// set--the only reason I can think of why this happens is that EcSwingX
		// extends a class, so some static initialization code must be running
		// before EcSwingX.main() gets executed
		MacSupport.initIfMac(messages.getString("title", EC_VERSION), false, iconLocation, new MacHandler()
		{
			@Override
			public void handleQuit(Object applicationEvent)
			{
				System.exit(0);
			}

			@Override
			public void handleAbout(Object applicationEvent)
			{
				JOptionPane.showMessageDialog(null, messages.getString("about.message", EC_VERSION), messages
						.getString("about.title"), JOptionPane.INFORMATION_MESSAGE);
			}
		});

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
				//	javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

				final JFrame frame = new JFrame();
				frame.setTitle(messages.getString("title", EC_VERSION));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new EcSwingX());
				frame.setPreferredSize(new Dimension(900, 830));
				ImageIcon icon = new ImageIcon(EcSwingXMain.class.getResource(iconLocation));
				frame.setIconImage(icon.getImage());
				frame.pack();
				frame.setLocationRelativeTo(null);

				final JFrame updateFrame = new JFrame();
				updateFrame.setTitle(messages.getString("update.title"));
				updateFrame.setIconImage(icon.getImage());
				JLabel waiting = new JLabel(messages.getString("update.checking"));
				updateFrame.getContentPane().setLayout(new FlowLayout());
				updateFrame.getContentPane().add(waiting);
				updateFrame.setSize(new Dimension(250, 70));
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);

				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						EcAutoUpdate ecUpdater = checkForUpdates();
						// Show the main window only when there are no updates
						// running
						frame.setVisible(!ecUpdater.isUpdating());
						updateFrame.dispose();
					}
				});
			}
		});
	}

	private static EcAutoUpdate checkForUpdates()
	{
		EcAutoUpdate ecUpdater = new EcAutoUpdate(EC_VERSION, new EcAutoUpdate.Callback(){
			@Override
			public void checksumFailed()
			{
				JOptionPane.showMessageDialog(null, messages.getString("update.checksumFailed.message"), messages.getString("update.checksumFailed.title"), JOptionPane.ERROR_MESSAGE);
			}
			
			@Override
			public void updateCheckFailed()
			{
				JOptionPane.showMessageDialog(null, messages.getString("update.updateCheckFailed.message"), messages.getString("update.updateCheckFailed.title"), JOptionPane.WARNING_MESSAGE);
			}
		});
		if (ecUpdater.isUpdateAvailable())
		{
			JOptionPane pane = new JOptionPane(messages.getString("update.updateAvailable.message"));
			String yes = messages.getString("update.updateAvailable.yes");
			String no = messages.getString("update.updateAvailable.no");
			pane.setOptions(new String[] { yes, no });
			JDialog dialog = pane.createDialog(new JFrame(), messages.getString("update.updateAvailable.title", ecUpdater.getLatestVersion()));
			dialog.setVisible(true);

			Object selection = pane.getValue();

			if (selection.equals(yes))
			{
				JFrame updateFrame = new JFrame();
				updateFrame.setTitle(messages.getString("update.updating.title"));
				updateFrame.setResizable(false);
				ImageIcon icon = new ImageIcon(EcSwingXMain.class.getResource(iconLocation));
				updateFrame.setIconImage(icon.getImage());

				final JProgressBar updateProgress = new JProgressBar(0, 100);
				updateProgress.setValue(0);
				updateProgress.setStringPainted(true);
				updateFrame.add(updateProgress);
				updateFrame.setPreferredSize(new Dimension(200, 100));
				updateFrame.pack();
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);
				ecUpdater.addPropertyChangeListener(new PropertyChangeListener()
				{
					public void propertyChange(PropertyChangeEvent evt)
					{
						if ("progress".equals(evt.getPropertyName()))
						{
							updateProgress.setValue((Integer) evt.getNewValue());
						}
					}
				});
				ecUpdater.setUpdating(true);
				ecUpdater.execute();
			}
		}
		return ecUpdater;
	}
}
