package com.publicstructures.methods;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class CellID_and_Time {
	public int id;
	public int time;

	public CellID_and_Time() {

	}

	public CellID_and_Time(int id, int time) {
		this.id = id;
		this.time = time;
	}

	public static void main(String[] args) {
		Hashtable has = new Hashtable();
		has.put(1, new Integer(1));
		has.put(2, new Integer(2));
		has.put(3, new Integer(3));
		has.put(4, new Double(12.3));
		Set s = has.keySet();
		for (Iterator<Integer> i = s.iterator(); i.hasNext();) {
			System.out.println(has.get(i.next()));
		}
		
		System.out.println(has.get(5));
		System.out.println(has.get(1));
		System.out.println(has.get(4));
	}

}
