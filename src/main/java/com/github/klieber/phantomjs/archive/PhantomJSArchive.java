/*
 * Copyright (c) 2013 Kyle Lieber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.klieber.phantomjs.archive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PhantomJSArchive {
	private static final Logger LOGGER = LoggerFactory.getLogger(PhantomJSArchive.class);
	
	private final String basename;
	private final String version;

	public PhantomJSArchive(String version) {
		this.basename = "phantomjs";
		this.version  = version;		
	}
	
	public abstract String getExtension();
	protected abstract String getPlatform();
	protected abstract String getExecutable();
	
	protected String getArch() {
		return null;
	}
	
	public final String getArchiveName() {
		return this.getArchiveNameSB().toString();
	}
	
	public final String getPathToExecutable() {
		StringBuilder sb = this.getNameWithoutExtension().append("/");

		if (getMajorVersion() >= 2) {
			sb.append("bin/");
		}

		return	sb.append(this.getExecutable())	.toString();
	}
	
	public final String getExtractToPath() {
		return this.getNameWithoutExtension().append("/").append(this.getExecutable()).toString();
	}
	
	private StringBuilder getArchiveNameSB() {
		return this.getNameWithoutExtension()
			.append(".")
			.append(this.getExtension());
	}
	
	private StringBuilder getNameWithoutExtension() {
		StringBuilder sb = new StringBuilder()
		.append(this.basename)
		.append("-")
		.append(this.version)
		.append("-")
		.append(this.getClassifier());
		return sb;
	}

  public final String getVersion() {
    return this.version;
  }
	
	public final String getClassifier() {
		StringBuilder sb = new StringBuilder().append(this.getPlatform());
		if (this.getArch() != null) {
			sb.append("-").append(this.getArch());
		}
		return sb.toString();
	}
    
	public int getMajorVersion() {
		if (version == null) {
			return 0;
		}

		try {
			String[] strings = version.split("\\.");

			int majorVersion = Integer.parseInt(strings[0]);

			return majorVersion;
		} catch (Exception e) {
			LOGGER.error("Failed to parse version: " + version, e);
			return 0;
		}
	}
}
