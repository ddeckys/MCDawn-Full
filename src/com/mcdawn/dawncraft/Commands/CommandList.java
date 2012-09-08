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

import java.util.ArrayList;

public class CommandList {
	public static ArrayList<Cmd> all = new ArrayList<Cmd>();
	public static void LoadAll() {
		all.add(new cmdHelp());
		all.add(new cmdMark());
		//all.add(new cmdTest());
		all.add(new cmdBroadcast());
		all.add(new cmdPlace());
		all.add(new cmdTp());
		all.add(new cmdTphere());
		all.add(new cmdKick());
		all.add(new cmdCuboid());
		all.add(new cmdDevs());
	}
}