package com.huateng.cdts.tools;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTimer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "2016-08-25 15:15:16,552 INFO com.paic.cdts - <VmxSocketHandlerListener> send msg:";
		GetTimer runner = new GetTimer();
		System.out.println(runner.run(s));

	}

	public String run(String text) {
		String dateStr = text.replaceAll("\\s+", " ");

		try {
			List matches = null;
			Pattern p = Pattern.compile("(\\d{1,4}[-|\\/]\\d{1,2}[-|\\/]\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2},\\d{1,3})", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
			Matcher matcher = p.matcher(dateStr);
			if (matcher.find() && matcher.groupCount() >= 1) {
				matches = new ArrayList();
				for (int i = 1; i <= matcher.groupCount(); i++) {
					String temp = matcher.group(i);
					matches.add(temp);
				}
			} else {
				matches = Collections.EMPTY_LIST;
			}

			if (matches.size() > 0) {
				return ((String) matches.get(0)).trim();
			} else {
				return null;
			}

		} catch (Exception e) {
			return null;
		}
	}
}
