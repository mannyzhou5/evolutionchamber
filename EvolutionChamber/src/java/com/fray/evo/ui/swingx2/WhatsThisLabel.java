package com.fray.evo.ui.swingx2;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.plaf.metal.MetalToolTipUI;

import org.jdesktop.swingx.JXLabel;

/**
 * A small icon that displays a short description of something when the mouse
 * hovers over it.
 * 
 * @author mike.angstadt
 * 
 */
@SuppressWarnings("serial")
public class WhatsThisLabel extends JXLabel {
	private static final ImageIcon helpIcon = new ImageIcon(WhatsThisLabel.class.getResource("images/help-icon.png"));

	static {
		ToolTipManager.sharedInstance().setInitialDelay(0);
		ToolTipManager.sharedInstance().setDismissDelay(10 * 60 * 1000);
	}

	public WhatsThisLabel(String message) {
		super(helpIcon);
		setToolTipText(message);
	}

	@Override
	public JToolTip createToolTip() {
		MultiLineToolTip tip = new MultiLineToolTip();
		tip.setComponent(this);
		return tip;
	}

	/**
	 * Makes tooltips recognize new line characters.
	 * @author mike.angstadt
	 * @see http://www.java2s.com/Code/Java/Swing-JFC/MultiLineToolTipExample.htm
	 */
	private static class MultiLineToolTip extends JToolTip {
		public MultiLineToolTip() {
			setUI(new MultiLineToolTipUI());
		}
	}

	/**
	 * Makes tooltips recognize new line characters.
	 * @author mike.angstadt
	 * @see http://www.java2s.com/Code/Java/Swing-JFC/MultiLineToolTipExample.htm
	 */
	private static class MultiLineToolTipUI extends MetalToolTipUI {
		private String[] strs;

		public void paint(Graphics g, JComponent c) {
			FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(g.getFont());
			Dimension size = c.getSize();
			g.setColor(c.getBackground());
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(c.getForeground());
			if (strs != null) {
				for (int i = 0; i < strs.length; i++) {
					g.drawString(strs[i], 3, (metrics.getHeight()) * (i + 1));
				}
			}
		}

		public Dimension getPreferredSize(JComponent c) {
			FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(c.getFont());
			String tipText = ((JToolTip) c).getTipText();
			if (tipText == null) {
				tipText = "";
			}
			BufferedReader br = new BufferedReader(new StringReader(tipText));
			String line;
			int maxWidth = 0;
			List<String> v = new ArrayList<String>();
			try {
				while ((line = br.readLine()) != null) {
					int width = SwingUtilities.computeStringWidth(metrics, line);
					maxWidth = (maxWidth < width) ? width : maxWidth;
					v.add(line);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			int lines = v.size();
			if (lines < 1) {
				strs = null;
				lines = 1;
			} else {
				strs = new String[lines];
				for (int i = 0; i < v.size(); i++) {
					strs[i] = v.get(i);
				}
			}
			int height = metrics.getHeight() * lines;
			return new Dimension(maxWidth + 6, height + 4);
		}
	}
}
