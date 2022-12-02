package com.wangshenglong.THIRD;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public class NodeCentrality implements Comparator<NodeCentrality>
{
	public int id;
	public int centrality;

	public NodeCentrality()
	{

	}

	public NodeCentrality(int id, int value)
	{
		this.id = id;
		this.centrality = value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + centrality;
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
		NodeCentrality other = (NodeCentrality) obj;
		if (id != other.id)
			return false;
		if (centrality != other.centrality)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "NodeImportance [id=" + id + ", centrality=" + centrality
				+ "]";
	}

	@Override
	public int compare(NodeCentrality o1, NodeCentrality o2)
	{
		// TODO Auto-generated method stub
		NodeCentrality node_1 = o1;
		NodeCentrality node_2 = o2;
		if (node_1.centrality < node_2.centrality)
		{
			return 1;

		} else if (node_1.centrality == node_2.centrality)
		{
			if (node_1.id > node_2.id)
			{
				return 1;

			} else if (node_1.id == node_2.id)
			{
				return 0;
			} else if (node_1.id < node_2.id)
			{
				return -1;
			}
		} else if (node_1.centrality > node_2.centrality)
		{
			return -1;
		}
		return 0;
	}

	public static void main(String[] args)
	{
		TreeSet<NodeCentrality> nodes = new TreeSet<NodeCentrality>(
				new NodeCentrality());
		NodeCentrality n1 = new NodeCentrality(1, 1);
		NodeCentrality n2 = new NodeCentrality(2, 4);
		NodeCentrality n3 = new NodeCentrality(3, 2);
		NodeCentrality n4 = new NodeCentrality(4, 1);
		NodeCentrality n5 = new NodeCentrality(1, 1);
		nodes.add(n5);
		nodes.add(n4);
		nodes.add(n3);
		nodes.add(n2);
		nodes.add(n1);
		for (Iterator<NodeCentrality> iter = nodes.iterator(); iter
				.hasNext();)
		{
			System.out.println(iter.next());
		}
	}


	/* (Tested) Calculate node centrality */
	public int calculate_node_centrality(int node_id, ArrayList<ArrayList<Integer>> adjacencyTables)
	{
		TreeSet<Integer> neighbors_tree = getNeighbors(node_id, adjacencyTables);
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		neighbors.addAll(neighbors_tree);
		int links_between_neighbors = 0;
		int all_links = 0;
		for (int i = 0; i < neighbors.size(); i++)
		{
			int neighbor_1 = neighbors.get(i);
			for (int j = i + 1; j < neighbors.size(); j++)
			{
				int neighbor_2 = neighbors.get(j);
				if (adjacencyTables.get(neighbor_1).contains(neighbor_2))
				{
					links_between_neighbors++;
				}
			}
		}
		all_links = links_between_neighbors + neighbors.size();
		// System.out.println(all_links);
		return all_links;
	}

	/* (Tested) calculate_node_centrality_for_all_nodes */
	public ArrayList<NodeCentrality> calculate_node_centrality_for_all_nodes(ArrayList<ArrayList<Integer>> adjacencyTables)
	{
		ArrayList<NodeCentrality> nodes_centrality = new ArrayList<NodeCentrality>();
		for (int i = 0; i < adjacencyTables.size(); i++)
		{
			NodeCentrality node_centrality = new NodeCentrality(i,
					calculate_node_centrality(i, adjacencyTables));
			nodes_centrality.add(node_centrality);
		}
		return nodes_centrality;
	}

	public TreeSet<Integer> getNeighbors(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
		TreeSet<Integer> neighbours = new TreeSet<>();
		neighbours.addAll(adjacencyTables.get(id));
		neighbours.remove((Integer)id);
		return neighbours;
	}
}
