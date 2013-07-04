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

package com.mcdawn.dawncraft.utils;

import java.util.*;

import org.bukkit.*;
import org.bukkit.material.*;

public class Area {
	boolean first=true;
	Location pos1;
	Location pos2;
	public Area (){}
	public boolean isfirst(){
		return first;
	}
	public void setPos1(Location l1){
		pos1 = l1;
		first=false;
	}
	public void setPos2(Location l2){
		pos2 = l2;
	}
	
	public boolean isSet() {
		if (pos1 != null && pos2 != null) return true; else return false;
	}
	
	// Cuboid (WorldEdit's /set)
	public enum CuboidType { SOLID, HOLLOW, WALLS, HOLES, WIRE, RANDOM };
	public void Cuboid(MaterialData m, CuboidType type) {
		if (pos1.getWorld() != pos2.getWorld()) return;
		World w = pos2.getWorld();
		double xx, yy, zz; Location l;
		switch (type)
		{
		case SOLID:
			for (xx = Math.min(pos1.getX(), pos2.getX()); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
				for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
					for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
					{
						l = new Location(w, xx, yy, zz);
						l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
					}
			break;
		case HOLLOW:
			for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
				for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
				{
					l = new Location(w, pos1.getX(), yy, zz);
					l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				}
			if (Math.abs(pos1.getX() - pos2.getX()) >= 2)
			{
				for (xx = Math.min(pos1.getX(), pos2.getX() + 1); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
					for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
					{
						l = new Location(w, xx, pos1.getY(), zz);
						l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
					}
				if (Math.abs(pos1.getY() - pos2.getY() + 1) >= 2)
				{
					for (xx = Math.min(pos1.getX(), pos2.getX()); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
						for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
						{
							l = new Location(w, xx, yy, pos1.getZ());
							l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
						}
				}
			}
			break;
		case WALLS:
			for (yy = Math.min(pos1.getY(), pos2.getY() + 1); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
				for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
				{
					l = new Location(w, pos1.getX(), yy, zz);
					l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				}
			if (Math.abs(pos1.getX() - pos2.getX()) >= 2)
			{
				if (Math.abs(pos1.getY() - pos2.getY()) >= 2)
				{
					for (xx = Math.min(pos1.getX(), pos2.getX()); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
						for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
						{
							l = new Location(w, xx, yy, pos1.getZ());
							l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
						}
				}
			}
			break;
		case HOLES:
			boolean b = true, startZ, startY;
			for (xx = Math.min(pos1.getX(), pos2.getX()); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
			{
				startY = b;
				for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
				{
					startZ = b;
					for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
					{
						l = new Location(w, xx, yy, zz);
						b = !b;
						if (b && l.getBlock().getType().getNewData(m.getData()) != m)
							l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
					}
					b = !startZ;
				}
				b = !startY;
			}
			break;
		case WIRE:
			for (xx = Math.min(pos1.getX(), pos2.getX()); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
			{
				l = new Location(w, xx, pos2.getY(), pos2.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, xx, pos2.getY(), pos1.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, xx, pos1.getY(), pos2.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, xx, pos1.getY(), pos1.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
			}
			for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
			{
				l = new Location(w, pos2.getX(), yy, pos2.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, pos2.getX(), yy, pos1.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, pos1.getX(), yy, pos2.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, pos1.getX(), yy, pos1.getZ());
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
			}
			for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
			{
				l = new Location(w, pos2.getX(), pos2.getY(), zz);
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, pos2.getX(), pos1.getY(), zz);
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, pos1.getX(), pos2.getY(), zz);
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
				l = new Location(w, pos1.getX(), pos1.getY(), zz);
				l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
			}
			break;
		case RANDOM:
			Random r = new Random();
			for (xx = Math.min(pos1.getX(), pos2.getX()); xx <= Math.max(pos1.getX(), pos2.getX()); ++xx)
				for (yy = Math.min(pos1.getY(), pos2.getY()); yy <= Math.max(pos1.getY(), pos2.getY()); ++yy)
					for (zz = Math.min(pos1.getZ(), pos2.getZ()); zz <= Math.max(pos1.getZ(), pos2.getZ()); ++zz)
						if (r.nextInt(11) + 1 <= 5)
						{
							l = new Location(w, xx, yy, zz);
							l.getBlock().setTypeIdAndData(m.getItemTypeId(), m.getData(), false);
						}
			break;
		}
	}
}