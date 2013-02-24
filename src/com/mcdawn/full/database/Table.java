package com.mcdawn.full.database;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import com.mcdawn.full.Util;

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
		try { return Files.readAllLines(Paths.get(getFile().getPath()), Charset.defaultCharset()); }
		catch (IOException e) { e.printStackTrace(); return new ArrayList<String>(); }
	}
	public void setLines(List<String> lines) {
		try { Util.fileWriteAllLines(getFile().getPath(), lines); }
		catch (IOException e) { e.printStackTrace(); }
	}
	public List<List<String>> getRows() {
		List<List<String>> rows = new ArrayList<List<String>>();
		for (String s : getLines()) rows.add(Arrays.asList(s.split(getColumnSeperator())));
		return rows;
	}
	public void setRows(List<List<String>> rows) {
		List<String> lines = new ArrayList<String>();
		for (List<String> s : rows) lines.add(org.apache.commons.lang.StringUtils.join(s, getColumnSeperator()));
		setLines(lines);
	}
	public List<String> getColumns() { return Arrays.asList(getLines().get(0).split(getColumnSeperator())); }
	public boolean columnExists(String columnName) { return getColumns().contains(columnName); }
	public int getColumnIndex(String columnName) { return getColumns().indexOf(columnName); }
	public void addColumn(String columnName, String defaultValue) throws Exception {
		if (columnExists(columnName)) throw new Exception("Column with name of " + columnName + " already exists.");
		List<List<String>> rows = getRows();
		for (int i = 0; i < rows.size(); i++) {
			List<String> row = rows.get(i);
			row.add(i == 0 ? columnName : defaultValue);
			rows.set(i, row);
		}
		setRows(rows);
	}
	public void removeColumn(String columnName) throws Exception {
		if (!columnExists(columnName)) throw new Exception("Column with name of " + columnName + " does not exist.");
		List<List<String>> rows = getRows();
		int columnIndex = getColumnIndex(columnName);
		for (int i = 0; i < rows.size(); i++) {
			List<String> row = rows.get(i);
			row.remove(columnIndex);
			rows.set(i, row);
		}
		setRows(rows);
	}
	public void renameColumn(String oldColumnName, String newColumnName) throws Exception {
		if (!columnExists(oldColumnName)) throw new Exception("Column with name of " + oldColumnName + " does not exist.");
		if (columnExists(newColumnName)) throw new Exception("Column with name of " + newColumnName + " already exists.");
		List<String> columns = getColumns();
		columns.set(getColumnIndex(oldColumnName), newColumnName);
		setRow(0, columns);
	}
	public List<Integer> getRowNumbersWhere(String thisColumnEquals, String thisValue) {
		List<Integer> rowNumbers = new ArrayList<Integer>();
		List<List<String>> rows = getRows();
		int columnIndex = getColumnIndex(thisValue);
		for (int i = 0; i < rows.size(); i++)
			if (getRow(i).get(columnIndex) == thisValue)
				rowNumbers.add(i);
		return rowNumbers;
	}
	public List<String> getRow(int rowNumber) { return getRows().get(rowNumber); }
	public void setRow(int rowNumber, List<String> row) {
		List<List<String>> rows = getRows();
		rows.set(rowNumber, row);
		setRows(rows);
	}
	public void addRow(List<String> row) {
		List<List<String>> rows = getRows();
		rows.add(row);
		setRows(rows);
	}
	public void removeRow(int rowNumber) {
		List<List<String>> rows = getRows();
		rows.remove(rowNumber);
		setRows(rows);
	}
	public String getValue(int rowNumber, String columnName) { return getRow(rowNumber).get(getColumnIndex(columnName)); }
	public void setValue(int rowNumber, String columnName, String value) {
		List<String> row = getRow(rowNumber);
		row.set(getColumnIndex(columnName), value);
		setRow(rowNumber, row);
	}
}