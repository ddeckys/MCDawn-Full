package com.mcdawn.full.commands;

public abstract class Category {
	public abstract String getName();
	public abstract String getHelpCategory();
	public abstract String[] getAllCommands();
	
	public static Category[] getAll() {
		return new Category[] {
				new BuildingCommands(),
				new InformationCommands(),
				new ModerationCommands(),
				new OtherCommands()
		};
	}
	
	public static Category getCategoryByName(String name) {
		for (Category c : getAll())
			if (c.getName().equalsIgnoreCase(name))
				return c;
		return null;
	}
	
	public static Category getCategoryByHelp(String help) {
		for (Category c : getAll())
			if (c.getHelpCategory().equalsIgnoreCase(help))
				return c;
		return null;
	}
}