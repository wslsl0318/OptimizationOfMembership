package com.wangshenglong.LFM;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class NodeFitness implements Comparator<NodeFitness>
{
	public int id;
	public double fitness;

	public NodeFitness()
	{
		this.id = -1;
		this.fitness = 0;
	}

	public NodeFitness(int id, double fitness)
	{
		this.id = id;
		this.fitness = fitness;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fitness);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		NodeFitness other = (NodeFitness) obj;
		if (Double.doubleToLongBits(fitness) != Double
				.doubleToLongBits(other.fitness))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public int compare(NodeFitness o1, NodeFitness o2)
	{
		// TODO Auto-generated method stub
		NodeFitness node1 = o1;
		NodeFitness node2 = o2;
		if (node1.fitness > node2.fitness)
		{
			return -1;
		} else if (node1.fitness == node2.fitness)
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
		} else if (node1.fitness < node2.fitness)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public String toString()
	{
		return "NodeFitness [id=" + id + ", fitness=" + fitness + "]";
	}

	public static void main(String[] args)
	{
		NodeFitness nf1 = new NodeFitness(3, 3);
		NodeFitness nf2 = new NodeFitness(2, 4);
		TreeSet<NodeFitness> nfs = new TreeSet<NodeFitness>(new NodeFitness());
		nfs.add(nf1);
		nfs.add(nf2);
		for (Iterator<NodeFitness> iter_node = nfs.iterator(); iter_node
				.hasNext();)
		{
			NodeFitness nf = iter_node.next();
			System.out.println(nf.fitness + "," + nf.id);
		}
	}
}
