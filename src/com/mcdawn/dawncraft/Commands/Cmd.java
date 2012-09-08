/*
Copyright 2012 DawnCraft Team

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
*/
package com.mcdawn.dawncraft.Commands;

import org.bukkit.entity.Player;

// "base" dawncraft command class
public abstract class Cmd {
	public abstract String name(); // all lowercase
	public abstract String[] aliases(); // all lowercase
	public abstract CommandType type();
	public abstract int permission();
	public abstract void run(Player sender, String args[], boolean console);
	public abstract void help(Player sender);
	
	public enum CommandType { build, moderation, information, other };
}
