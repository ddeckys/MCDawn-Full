package com.mcdawn.full.database;

import java.io.*;
import java.util.*;

public class Database {
	public static File getDirectory() { return new File("plugins/MCDawn/MCDawnDB"); }
	public static boolean exists() { return getDirectory().isDirectory(); }
	
	public static void initialize() { if (!exists()) getDirectory().mkdir(); }
	
	public static Table getOrCreateTable(String tableName) { return new Table(tableName); }
	public static Table[] getTables() {
		ArrayList<Table> tables = new ArrayList<Table>();
		for (File f : getDirectory().listFiles())
			if (f.getName().endsWith(".txt"))
				tables.add(new Table(f.getName().substring(0, f.getName().lastIndexOf(".txt"))));
		return (Table[])tables.toArray();
	}
}
