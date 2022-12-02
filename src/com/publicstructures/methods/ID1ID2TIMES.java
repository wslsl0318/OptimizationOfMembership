package com.publicstructures.methods;

import java.util.Comparator;
import java.util.TreeSet;

public class ID1ID2TIMES implements Comparator<ID1ID2TIMES> {
	public int id1;
	public int id2;
	public int times;

	public ID1ID2TIMES() {
	}

	public ID1ID2TIMES(int id1, int id2, int times) {
		this.id1 = id1;
		this.id2 = id2;
		this.times = times;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id1;
		result = prime * result + id2;
		result = prime * result + times;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ID1ID2TIMES other = (ID1ID2TIMES) obj;
		if (id1 != other.id1)
			return false;
		if (id2 != other.id2)
			return false;
		if (times != other.times)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ID1ID2TIMES [id1=" + id1 + ", id2=" + id2 + ", times=" + times
				+ "]";
	}

	@Override
	public int compare(ID1ID2TIMES o1, ID1ID2TIMES o2) {
		// TODO Auto-generated method stub
		ID1ID2TIMES node1 = o1;
		ID1ID2TIMES node2 = o2;

		if (node1.times < node2.times) {
			return -1;
		} else if (node1.times == node2.times) {
			if (node1.id1 < node2.id1) {
				return -1;
			} else if (node1.id1 == node2.id1) {
				if (node1.id2 < node2.id2) {
					return -1;
				} else if (node1.id2 == node2.id2) {
					return 0;
				} else if (node1.id2 > node2.id2) {
					return 1;
				}
			} else if (node1.id1 > node2.id1) {
				return 1;
			}
		} else if (node1.times > node2.times) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) {
		TreeSet<ID1ID2TIMES> t = new TreeSet<ID1ID2TIMES>(new ID1ID2TIMES());
		ID1ID2TIMES x4 = new ID1ID2TIMES(3, 4, 4);
		ID1ID2TIMES x3 = new ID1ID2TIMES(2, 3, 3);
		ID1ID2TIMES x2 = new ID1ID2TIMES(2, 2, 2);
		ID1ID2TIMES x1 = new ID1ID2TIMES(0, 1, 1);

		t.add(x1);
		t.add(x2);
		t.add(x3);
		t.add(x4);

		for (ID1ID2TIMES x : t) {
			System.out.println(x);
		}
	}
}
