package com.sylla;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class RegexFileFilter implements FilenameFilter {

	private Pattern pattern;

	public RegexFileFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}
}
