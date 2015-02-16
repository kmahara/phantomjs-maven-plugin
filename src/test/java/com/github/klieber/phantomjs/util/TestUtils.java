package com.github.klieber.phantomjs.util;


public final class TestUtils {
	private static final boolean isWindows;

	static {
		if (System.getProperty("os.name").indexOf("Windows") != -1) {
			isWindows = true;
		} else {
			isWindows = false;
		}
	}

	private TestUtils() {
	}

	public static String normalizePath(String s) {
		if (isWindows) {
			return s.replace('\\', '/').replaceAll("^[A-Z]:", "");
		}

		return s;
	}
}
