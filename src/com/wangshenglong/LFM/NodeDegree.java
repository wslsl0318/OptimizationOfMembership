package com.wangshenglong.LFM;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class NodeDegree implements Comparator<NodeDegree>
{
	public int id;
	public int degree;
	
	public NodeDegree()
	{
		this.id = -1;
		this.degree = 0;
	}

	public NodeDegree(int id, int degree)
	{
		this.id = id;
		this.degree = degree;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + degree;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeDegree other = (NodeDegree) obj;
		if (degree != other.degree)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "NodeDegree [id=" + id + ", degree=" + degree + "]";
	}

	@Override
	public int compare(NodeDegree o1, NodeDegree o2)
	{
		NodeDegree node1 = o1;
		NodeDegree node2 = o2;
		if (node1.degree > node2.degree)
		{
			return -1;
		} else if (node1.degree == node2.degree)
		{
			if (node1.id < node2.id)
			{
				return -1;
			} else if (node1.id == node2.id)
			{
				return 0;
			} else if (node1.id > node2.id)
			{
				return 1;
			}
		} else if (node1.degree < node2.degree)
		{
			return 1;
		}
		return 0;
	}
	
	public static void main(String[] args)
	{
		NodeDegree nf1 = new NodeDegree(3, 3);
		NodeDegree nf2 = new NodeDegree(2, 4);
		TreeSet<NodeDegree> nfs = new TreeSet<NodeDegree>(new NodeDegree());
		nfs.add(nf1);
		nfs.add(nf2);
		for (Iterator<NodeDegree> iter_node = nfs.iterator(); iter_node
				.hasNext();)
		{
			NodeDegree nf = iter_node.next();
			System.out.println(nf.degree + "," + nf.id);
		}
	}
}
