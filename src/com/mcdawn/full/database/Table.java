package com.mcdawn.full.database;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Table {
	public String name;
	public static String getFileExtension() { return ".txt"; }
	public static String getColumnSeperator() { return "|"; }
	public File getFile() { return new File(name + getFileExtension()); }
	public static boolean exists(String tableName) { return new File(tableName + getFileExtension()).exists(); }
	public static void delete(String tableName) { new File(tableName + getFileExtension()).delete(); }
	public Table(String tableName) {
		name = tableName;
		if (!exists(tableName)) {
			try { getFile().createNewFile(); }
			catch (IOException ex) { ex.printStackTrace(); }
		}
	}
	public List<String> getLines() {
		try {
			return Files.readAllLines(Paths.get(getFile().getPath()), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<String>();
		}
	}
	public List<List<String>> getRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		List<String> lines = getLines();
		for (String s : lines) rows.add(Arrays.asList(s.split(getColumnSeperator())));
		
		return null;
	}
}