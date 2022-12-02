package com.wangshenglong.LFM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class LFM
{
	public int size;
	// public int[][] adjacencyMatrix;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public TreeSet<NodeDegree> node_degrees;
	public double a;
	public int k;
	public ArrayList<TreeSet<Integer>> nature_communities;

	public LFM(int n, ArrayList<ArrayList<Integer>> adjacencyTable,
			double a)
	{
		this.size = n;
		// this.adjacencyMatrix = fr.adjacencyMatrix;
		this.adjacencyTable = adjacencyTable;
		this.node_degrees = new TreeSet<NodeDegree>(new NodeDegree());
		this.a = a;
		this.nature_communities = new ArrayList<TreeSet<Integer>>();
		// System.out.println("AdjacencyTable:");
		// for (Iterator<ArrayList<Integer>> iter = adjacencyTable.iterator();
		// iter
		// .hasNext();)
		// {
		// System.out.println(iter.next());
		// }
	}

	/* Get fg of a sub-graph */
	public double get_graph_fg(TreeSet<Integer> community)
	{
		double fg = 0;
		/* Get k_in_G and k_out_G */
		int k_in_G = 0;
		int k_out_G = 0;
		for (Iterator<Integer> iter_member = community.iterator(); iter_member
				.hasNext();)
		{
			Integer node_id = iter_member.next();
			for (Iterator<Integer> iter_neighbor = adjacencyTable.get(node_id)
					.iterator(); iter_neighbor.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (!node_id.equals(neighbor))
				{
					if (community.contains(neighbor))
					{
						k_in_G++;
					} else
					{
						k_out_G++;
					}
				}
			}
		}
		/* Get fg */
		fg = (double) k_in_G / (double) Math.pow((k_in_G + k_out_G), a);
		// System.out.println(k_in_G + "," + k_out_G);
		return fg;
	}

	/* Get fitness of a node A outside sub-graph G */
	public double get_fitness_add(TreeSet<Integer> community, int neighbor)
	{
		double fitness = 0;
		TreeSet<Integer> community_new = new TreeSet<Integer>();
		community_new.addAll(community);
		community_new.add(neighbor);
		double fg_old = get_graph_fg(community);
		double fg_new = get_graph_fg(community_new);
		fitness = fg_new - fg_old;
		return fitness;
	}

	/* Get fitness of a node A inside sub-graph G */
	public double get_fitness_remove(TreeSet<Integer> community, int node)
	{
		double fitness = 0;
		TreeSet<Integer> community_new = new TreeSet<Integer>();
		community_new.addAll(community);
		community_new.remove((Integer) node);
		double fg_old = get_graph_fg(community);
		double fg_new = get_graph_fg(community_new);
		fitness = fg_old - fg_new;
		return fitness;
	}

	/* Get the node with highest fitness in neighbors */
	public NodeFitness get_max_fitness_node(SubgraphNode nature_community)
	{
		NodeFitness max_fitness_node = new NodeFitness();
		for (Iterator<Integer> iter_neighbor = nature_community.neighbors
				.iterator(); iter_neighbor.hasNext();)
		{
			Integer neighbor = iter_neighbor.next();
			double fitness = get_fitness_add(nature_community.community,
					neighbor);
			if (fitness > 0 && fitness > max_fitness_node.fitness)
			{
				max_fitness_node.id = neighbor;
				max_fitness_node.fitness = fitness;
			}
		}
		return max_fitness_node;
	}

	/* Get the node with lowest fitness in community */
	public NodeFitness get_min_fitness_node(SubgraphNode nature_community)
	{
		NodeFitness min_fitness_node = new NodeFitness();
		for (Iterator<Integer> iter_member = nature_community.community
				.iterator(); iter_member.hasNext();)
		{
			Integer member = iter_member.next();
			double fitness = get_fitness_remove(nature_community.community,
					member);
			if (fitness < 0 && fitness < min_fitness_node.fitness)
			{
				min_fitness_node.id = member;
				min_fitness_node.fitness = fitness;
			}
		}
		return min_fitness_node;
	}

	/* To judge if two communities contain the same members */
	public boolean is_communities_equal(TreeSet<Integer> community,
			TreeSet<Integer> nature_community)
	{
		if (community.size() == nature_community.size())
		{
			for (Iterator<Integer> iter_member = community.iterator(); iter_member
					.hasNext();)
			{
				Integer member = iter_member.next();
				if (!nature_community.contains(member))
				{
					return false;
				}
			}
			return true;
		} else
		{
			return false;
		}

	}

	/* To judge the new nature community is already existed */
	public boolean is_community_exists(TreeSet<Integer> nature_community)
	{
		for (Iterator<TreeSet<Integer>> iter_community = nature_communities
				.iterator(); iter_community.hasNext();)
		{
			TreeSet<Integer> community = iter_community.next();
			if (is_communities_equal(community, nature_community))
			{
				return true;
			}
		}
		return false;
	}

	/* Reflash nature communiy with a new node */
	public void reflash_graph_neighbors_add(SubgraphNode nature_community,
			int node_new)
	{
		/* Refresh community */
		nature_community.community.add((Integer) node_new);
		/* Refreash neighbors */
		nature_community.neighbors.remove((Integer) node_new);
		for (Iterator<Integer> iter_neighbor = adjacencyTable.get(node_new)
				.iterator(); iter_neighbor.hasNext();)
		{
			Integer neighbor = iter_neighbor.next();
			if (node_new != neighbor)
			{
				if (adjacencyTable.get(node_new).contains(neighbor))
				// if (adjacencyMatrix[node_new][neighbor] != 0)
				{
					if (!nature_community.community.contains(neighbor))
					{
						if (!nature_community.neighbors.contains(neighbor))
						{
							nature_community.neighbors.add(neighbor);
							nature_community.k_out_G++;
						} else
						{
							nature_community.k_out_G++;
						}
					} else
					{
						nature_community.k_in_G += 2;
						nature_community.k_out_G--;
					}
				}
			}
		}
	}

	/*
	 * When remove a node from a community, should judge if its neighbor still
	 * in the neighbors list
	 */
	public boolean is_still_neighbor(TreeSet<Integer> community,
			Integer neighbor)
	{
		for (Iterator<Integer> iter_member = community.iterator(); iter_member
				.hasNext();)
		{
			Integer member = iter_member.next();
			if (adjacencyTable.get(member).contains(neighbor))
			// if (adjacencyMatrix[member][neighbor] != 0)
			{
				return true;
			}
		}
		return false;
	}

	/* Reflash nature communiy with a removed node */
	public void reflash_graph_neighbors_remove(SubgraphNode nature_community,
			int node_removed)
	{
		/* Refresh community */
		nature_community.community.remove((Integer) node_removed);
		/* Refreash neighbors */
		nature_community.neighbors.add((Integer) node_removed);
		for (Iterator<Integer> iter_neighbor = adjacencyTable.get(node_removed)
				.iterator(); iter_neighbor.hasNext();)
		{
			Integer neighbor = iter_neighbor.next();
			if (node_removed != neighbor)
			{
				if (adjacencyTable.get(node_removed).contains(neighbor))
				// if (adjacencyMatrix[node_removed][neighbor] != 0)
				{
					if (!nature_community.community.contains(neighbor))
					{
						if (is_still_neighbor(nature_community.community,
								neighbor))
						{
							nature_community.k_out_G--;
						} else
						{
							nature_community.neighbors.remove(neighbor);
							nature_community.k_out_G--;
						}
					} else
					{
						nature_community.k_in_G -= 2;
						nature_community.k_out_G++;
					}
				}
			}
		}
		// System.out.println("(Remove)reflashed community: ");
		// System.out.println(nature_community);
	}

	/* Initial a nature community of a node */
	public SubgraphNode initial_SubgraphNode(int node_id)
	{
		SubgraphNode nature_community = new SubgraphNode();
		nature_community.community.add(node_id);
		nature_community.neighbors.addAll(adjacencyTable.get(node_id));
		nature_community.neighbors.remove((Integer) node_id);
		nature_community.k_in_G = 0;
		nature_community.k_out_G = nature_community.neighbors.size();
		return nature_community;
	}

	/* Get the final nature community of a node */
	public TreeSet<Integer> get_node_nature_community(int node_id)
	{
		/* Initial a nature community of a node */
		SubgraphNode nature_community = initial_SubgraphNode(node_id);
		NodeFitness max_fitness_node = get_max_fitness_node(nature_community);
		while (max_fitness_node.fitness > 0)
		{
			/* Addition Process */
			if (max_fitness_node.id != -1)
			{
				/* Reflash nature communiy with a new node */
				reflash_graph_neighbors_add(nature_community,
						max_fitness_node.id);
			}
			NodeFitness min_fitness_node = get_min_fitness_node(nature_community);
			/* Deletion Process */
			while (min_fitness_node.fitness < 0)
			{
				/* Reflash nature communiy with a removed node */
				reflash_graph_neighbors_remove(nature_community,
						min_fitness_node.id);
				min_fitness_node = get_min_fitness_node(nature_community);
			}
			max_fitness_node = get_max_fitness_node(nature_community);
		}
		return nature_community.community;
	}

	/* (Tested) Get node degrees */
	public void get_node_degrees()
	{
		for (int i = 0; i < this.size; i++)
		{
			NodeDegree node_degree = new NodeDegree(i, adjacencyTable.get(i)
					.size() - 1);
			this.node_degrees.add(node_degree);
		}
		// for(Iterator<NodeDegree> iter =
		// this.node_degrees_decrease_order.iterator();iter.hasNext();)
		// {
		// System.out.println(iter.next());
		// }
	}

	/* Get removed node_degree from node_degrees_decrease_order by id */
	public NodeDegree get_removed_node_degree_from_node_degrees_decrease_order_by_id(
			int node_id)
	{
		for (Iterator<NodeDegree> iter = this.node_degrees.iterator(); iter
				.hasNext();)
		{
			NodeDegree node_degree = iter.next();
			if (node_degree.id == node_id)
			{
				return node_degree;
			}
		}
		return null;
	}

	/* Get nature communities at random */
	public ArrayList<TreeSet<Integer>> run_lfm()
	{
		get_node_degrees();
		// ArrayList<TreeSet<Integer>> finial_communities = new
		// ArrayList<TreeSet<Integer>>();
		while (!node_degrees.isEmpty())
		{
			NodeDegree node_with_max_degree = node_degrees.pollFirst();
			TreeSet<Integer> nature_community = get_node_nature_community(node_with_max_degree.id);
			if (!is_community_exists(nature_community))
			{
				// System.out.println(nature_community);
				nature_communities.add(nature_community);
				// ArrayList<Integer> community_new = new ArrayList<Integer>();
				// community_new.addAll(nature_community);
				// finial_communities.add(community_new);
				// System.out.print(node_with_max_degree.id + " ");
				for (Iterator<Integer> iter = nature_community.iterator(); iter
						.hasNext();)
				{
					Integer member = iter.next();
					NodeDegree node_degree = get_removed_node_degree_from_node_degrees_decrease_order_by_id(member);
					if (node_degree != null)
					{
						node_degrees.remove(node_degree);
					}
				}
			}
		}
		return nature_communities;
	}

	public static void main(String[] args) throws IOException
	{
		String file_path = "D:/real world networks/";
		String file_name = "karate";
		// karate;dolphin;football;krebsbook;dblp;amazon;youtube
		// karate_network;dolphin_network;football_network;krebsbook_network
		// dblp_network;amazon_network;youtube_network
		String community = "_community.txt";
		// karate_community;dolphin_community;football_community;krebsbook_community
		// dblp_community;amazon_community;youtube_community
		String table = "_table.txt";
		// karate_table;dolphin_table;football_table;krebsbook_table
		// dblp_table;amazon_table;youtube_table
		String file_path_community = file_path + file_name + community;
		String file_path_table = file_path + file_name + table;

		DataReaderRealWorldNetwork data_reader = new DataReaderRealWorldNetwork(
				file_path_community, file_path_table);
		data_reader.read_realworld_network();
		double a = 1;
		LFM lfm = new LFM(data_reader.n,
				data_reader.adjacencyTable, a);
		lfm.run_lfm();
	}
}
