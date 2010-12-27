package com.fray.evo.ui.swingx2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Locale;
import java.util.logging.LogManager;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.fray.evo.ui.swingx.MacHandler;
import com.fray.evo.ui.swingx.MacSupport;
import com.fray.evo.ui.swingx.UserSettings;
import com.fray.evo.util.EcMessages;

/**
 * Contains the entry point to the application.
 *
 */
public class EcSwingXMain {
	/**
	 * I18n messages.
	 */
	public static final EcMessages messages = new EcMessages("com/fray/evo/ui/swingx2/messages");
	
	/**
	 * The directory that stores the user's configuration files.
	 */
	public static final File userConfigDir;
	static{
		userConfigDir = new File(System.getProperty("user.home"), ".evolutionchamber");
		userConfigDir.mkdir();
	}
	
	/**
	 * The user's settings.
	 */
	public static final UserSettings userSettings = new UserSettings(new File(userConfigDir, "settings.properties"));

	/**
	 * The classpath location of the icon.
	 */
	public static final String iconLocation = "/com/fray/evo/ui/swingx/evolution_chamber.png";

	/**
	 * The main window.
	 */
	public static EcSwingX mainWindow;

	public static void main(String args[])
	{
		//setup the logger
		try
		{
			LogManager.getLogManager().readConfiguration(EcSwingXMain.class.getResourceAsStream("logging.properties"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		//setup i18n
		Locale locale = userSettings.getLocale();
		if (locale != null)
		{
			messages.changeLocale(locale);
		}
		
		//run Mac OS X customizations if user is on a Mac
		//this code must run before *anything* else graphics-related
		if (MacSupport.isMac()){
			MacSupport.init(messages.getString("title"), false, iconLocation, new MacHandler()
			{
				@Override
				public void handleQuit(Object applicationEvent)
				{
					mainWindow.saveWindowSettings();
					System.exit(0);
				}
	
				@Override
				public void handleAbout(Object applicationEvent)
				{
					AboutDialog about = AboutDialog.getInstance(mainWindow);
					about.setVisible(true);
				}
			});
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				//create the main window
				final EcSwingX frame = new EcSwingX();
				mainWindow = frame; //for when a Mac user selects "Quit" from the application menu
				frame.setPreferredSize(calculateOptimalAppSize(1200, 850));
				ImageIcon icon = new ImageIcon(EcSwingXMain.class.getResource(iconLocation));
				frame.setIconImage(icon.getImage());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setExtendedState(getOptimalExtendedState(frame));
				
				//create the "checking for updates" window
				final AutoUpdater updateFrame = new AutoUpdater();
				updateFrame.addWindowListener(new WindowAdapter(){
					@Override
					public void windowClosed(WindowEvent event){
						frame.setVisible(true);
					}
				});
				updateFrame.setResizable(false);
				updateFrame.setTitle(messages.getString("update.title"));
				updateFrame.setIconImage(icon.getImage());
				updateFrame.setSize(new Dimension(300, 120));
				updateFrame.setLocationRelativeTo(null);
				updateFrame.setVisible(true);
				updateFrame.check();
			}
		});
	}
	
	/**
	 * checks if this JFrame is too big to be viewed on the main screen and returns the recommended extension state
	 * if the userSettings contains an preference, this is used as initial value. However, this may get overwritten if the screen resolution changed.
	 * @param frame the frame to check
	 * @return the state, to tell if this window should be maximized in any direction
	 * @see JFrame#setExtendedState(int)
	 */
	private static int getOptimalExtendedState(JFrame frame){
		int extendedState = frame.getExtendedState();
		
		Integer userPrefExtensionState = userSettings.getWindowExtensionState();
		if( userPrefExtensionState != null){
			extendedState = userPrefExtensionState.intValue();
		}
		
		
		int width = frame.getPreferredSize().width;
		int height = frame.getPreferredSize().height;
		
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		if( screenDimensions.getHeight() <= height){
			height = screenDimensions.height;
			extendedState = extendedState | JFrame.MAXIMIZED_VERT;
		}
		
		if( screenDimensions.getWidth() <= width){
			width = screenDimensions.width;
			extendedState = extendedState | JFrame.MAXIMIZED_HORIZ;
		}
		
		return extendedState;
	}
	
	/**
	 * fits the app size to the resolution of the users screen
	 * if user settings exist, those get used as default value. However, if the screen size is too low, this value is overwritten.
	 * 
	 * @param width the default width
	 * @param height the default height
	 * @return the preferred values or lower sizes if the screen resolution is too low
	 */
	private static Dimension calculateOptimalAppSize(int width, int height){
		Dimension userWindowSize = userSettings.getWindowSize();
		if( userWindowSize != null ){
			width = userWindowSize.width;
			height = userWindowSize.height;
		}
		
		Dimension screenDimensions = Toolkit.getDefaultToolkit().getScreenSize();
		if( screenDimensions.getHeight() < height){
			height = screenDimensions.height;
		}
		
		if( screenDimensions.getWidth() < width){
			width = screenDimensions.width;
		}
		
		return new Dimension(width, height);
	}
}
