package com.mcdawn.full.database;

import java.io.*;
import java.util.*;

public class Table {
	public String name;
	public static String getFileExtension() { return ".txt"; }
	public File getFile() { return new File(name); }
	public static File getFile(String tableName) { return new File("plugins/MCDawn/MCDawnDB" + tableName + getFileExtension()); }
	public static boolean exists(String tableName) { return getFile(tableName).exists(); }
	public static void delete(String tableName) { getFile(tableName).delete(); }
	public Table(String tableName) {
		name = tableName;
		if (!exists(tableName)) {
			try { getFile().createNewFile(); }
			catch (IOException ex) { ex.printStackTrace(); }
		}
	}
	public ArrayList<String> getRow() {
		return null;
	}
}