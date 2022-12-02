package com.publicstructures.methods;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

public class Pairs implements Comparator<Pairs> {
	public int id_f;// from
	public int id_t;// to

	public Pairs() {

	}

	public Pairs(int id_f, int id_t) {
		this.id_f = id_f;
		this.id_t = id_t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id_f;
		result = prime * result + id_t;
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
		Pairs other = (Pairs) obj;
		if (id_f != other.id_f)
			return false;
		if (id_t != other.id_t)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pairs [id_f=" + id_f + ", id_t=" + id_t + "]";
	}

	@Override
	public int compare(Pairs o1, Pairs o2) {
		// TODO Auto-generated method stub
		Pairs node1 = o1;
		Pairs node2 = o2;
		if (node1.id_f < node2.id_f) {
			return -1;
		} else if (node1.id_f == node2.id_f) {
			if (node1.id_t < node2.id_t) {
				return -1;
			} else if (node1.id_t == node2.id_t) {
				return 0;
			} else if (node1.id_t > node2.id_t) {
				return 1;
			}
		} else if (node1.id_f > node2.id_f) {
			return 1;
		}
		return 0;
	}

	/* (Tested) Get insection of two node set */
	public TreeSet<Pairs> get_insection(TreeSet<Pairs> set_1,
			TreeSet<Pairs> set_2) {
		TreeSet<Pairs> insection = new TreeSet<Pairs>(new Pairs());
		if (set_1.size() < set_2.size()) {
			for (Iterator<Pairs> iter = set_1.iterator(); iter.hasNext();) {
				Pairs member = iter.next();
				if (set_2.contains(member)) {
					insection.add(member);
				}
			}
		} else {
			for (Iterator<Pairs> iter = set_2.iterator(); iter.hasNext();) {
				Pairs member = iter.next();
				if (set_1.contains(member)) {
					insection.add(member);
				}
			}
		}
		return insection;
	}

	public static void main(String[] args) {
		// HashSet<Pairs> tree = new HashSet<Pairs>();
		TreeSet<Pairs> tree = new TreeSet<Pairs>(new Pairs());
		Pairs n1 = new Pairs(1, 2);
		Pairs n2 = new Pairs(3, 6);
		Pairs n3 = new Pairs(2, 5);
		Pairs n4 = new Pairs(6, 3);
		Pairs n5 = new Pairs(4, 1);
		Pairs n6 = new Pairs(1, 2);
		tree.add(n1);
		tree.add(n2);
		tree.add(n3);

		for (Iterator<Pairs> iter = tree.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
		System.out.println();

		TreeSet<Pairs> tree1 = new TreeSet<Pairs>(new Pairs());
		tree1.add(n3);
		tree1.add(n4);
		tree1.add(n6);

		for (Iterator<Pairs> iter = tree1.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
		System.out.println();

		TreeSet<Pairs> insection = n1.get_insection(tree, tree1);

		for (Iterator<Pairs> iter = insection.iterator(); iter.hasNext();) {
			System.out.println(iter.next());
		}
		System.out.println();

		// for (Iterator<Pairs> iter = tree.iterator(); iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// tree.add(n6);
		// if (tree.contains(n6)) {
		// for (Iterator<Pairs> iter_2 = tree.iterator(); iter_2.hasNext();) {
		// Pairs p = iter_2.next();
		// if (p.equals(n6)) {
		// p.time++;
		// }
		// }
		// } else {
		// tree.add(n6);
		// }
		// System.out.println(n1.equals(n6));
		// System.out.println(tree.contains(n6));
		// System.out.println(tree.remove(n6));
		// System.out.println();
		// for (Iterator<Pairs> iter = tree.iterator(); iter.hasNext();) {
		// System.out.println(iter.next());
		// }
	}
}
