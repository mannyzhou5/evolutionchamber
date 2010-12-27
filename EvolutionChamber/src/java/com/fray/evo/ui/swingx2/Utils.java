package com.fray.evo.ui.swingx2;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Utils {
	private static final NumberFormat nfTime = new DecimalFormat("00");
	public static String formatAsTime(int totalSeconds) {
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;

		StringBuilder sb = new StringBuilder();
		if (hours > 0) {
			sb.append(nfTime.format(hours));
			sb.append(':');
		}
		sb.append(nfTime.format(minutes));
		sb.append(':');
		sb.append(nfTime.format(seconds));
		return sb.toString();
	}

	public static int seconds(String text) {
		String[] split = text.split(":+");
		int hours = 0, minutes = 0, seconds = 0;
		if (split.length >= 3){
			hours = Integer.parseInt(split[0]);
			minutes = Integer.parseInt(split[1]);
			seconds = Integer.parseInt(split[2]);
		} else if (split.length == 2){
			minutes = Integer.parseInt(split[0]);
			seconds = Integer.parseInt(split[1]);
		} else {
			seconds = Integer.parseInt(split[0]);
		}
		
		return hours * 3600 + minutes * 60 + seconds;
	}
	
	/**
	 * Escapes text for safe inclusion into HTML.
	 * @param text
	 * @return
	 */
	public static String escapeHtml(String text){
		return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
