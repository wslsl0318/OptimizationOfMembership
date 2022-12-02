package com.wangshenglong.clauset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class ClausetAlgorithm
{
	public int node_size;
	public int link_size;
	public int numberOfCommunities;
	public ArrayList<ArrayList<Integer>> adjacencyTable;
	public TreeSet<Integer> C;
	public TreeSet<Integer> B;
	public TreeSet<Integer> U;

	public ClausetAlgorithm(int n, int m,
			ArrayList<ArrayList<Integer>> adjacencyTable)
	{
		this.node_size = n;
		this.link_size = m;
		this.numberOfCommunities = 0;
		this.adjacencyTable = adjacencyTable;
		this.C = new TreeSet<Integer>();
		this.B = new TreeSet<Integer>();
		this.U = new TreeSet<Integer>();
	}

	/* (Tested) initial Set C(nodes within community) */
	public void initSetC_B_U(Integer v)
	{
		C.clear();
		B.clear();
		U.clear();
		C.add(v);
		B.add(v);
		U.addAll(adjacencyTable.get(v));
		Integer first = adjacencyTable.get(v).get(0);
		U.remove(first);
	}

	/* (Tested) update Set C (nodes that within community) */
	public TreeSet<Integer> updateSetC(TreeSet<Integer> c, Integer v)
	{
		TreeSet<Integer> c_new = new TreeSet<Integer>();
		c_new.addAll(c);
		c_new.add(v);
		return c_new;
	}

	/* (Tested) update Set U (nodes that out of and connected with community ) */
	public TreeSet<Integer> updateSetU(TreeSet<Integer> c)
	{
		TreeSet<Integer> u_new = new TreeSet<Integer>();
		for (Iterator<Integer> iter = c.iterator(); iter.hasNext();)
		{
			Integer member_in_C = iter.next();
			for (Iterator<Integer> iter_neighbor = adjacencyTable.get(
					member_in_C).iterator(); iter_neighbor.hasNext();)
			{
				Integer neighbor = iter_neighbor.next();
				if (!c.contains(neighbor))
				{
					u_new.add(neighbor);
				}
			}
		}
		return u_new;
	}

	/* (Tested) update Set B (nodes on the boundray of community */
	public TreeSet<Integer> updateSetB(TreeSet<Integer> c, TreeSet<Integer> u)
	{
		TreeSet<Integer> b_new = new TreeSet<Integer>();
		for (Iterator<Integer> iter = c.iterator(); iter.hasNext();)
		{
			Integer member_in_C = iter.next();
			for (Iterator<Integer> iter_neghbor = adjacencyTable.get(
					member_in_C).iterator(); iter_neghbor.hasNext();)
			{
				Integer neighbor = iter_neghbor.next();
				if (u.contains(neighbor))
				{
					b_new.add(member_in_C);
					break;
				}
			}
		}
		return b_new;
	}

	/* (Tested) calculate R of Community */
	public double calculateRCommunity(TreeSet<Integer> c, TreeSet<Integer> b)
	{
		double r = 0;
		int innerLinks_of_C = 0;
		int innerLinks_of_B = 0;
		int innerLinks = 0;
		int outerLinks = 0;
		for (Iterator<Integer> iter = b.iterator(); iter.hasNext();)
		{
			Integer memberInB = iter.next();
			ArrayList<Integer> line = this.adjacencyTable.get(memberInB);
			for (Iterator<Integer> iter2 = line.iterator(); iter2.hasNext();)
			{
				Integer neighbor = iter2.next();
				if (neighbor != memberInB)
				{
					if (c.contains(neighbor))
					{
						if (!b.contains(neighbor))
						{
							innerLinks_of_C++;
						} else
						{
							innerLinks_of_B++;
						}
					} else
					{
						outerLinks++;
					}
				}
			}
		}
		innerLinks = innerLinks_of_C + (innerLinks_of_B / 2);
		r = (double) innerLinks / (double) (innerLinks + outerLinks);
		// System.out.println(innerLinks_of_C + "," + innerLinks_of_B + ","
		// + outerLinks+","+c+","+innerLinks_of_B%2);
		return r;
	}

	/* (Tested) get The Node With Max Date R */
	public Integer getTheNodeWithMaxDateR(TreeSet<Integer> c,
			TreeSet<Integer> b, TreeSet<Integer> u)
	{
		Integer id = -1;
		double maxDateR = 0;
		double oldR = calculateRCommunity(c, b);
		for (Iterator<Integer> iter = u.iterator(); iter.hasNext();)
		{
			Integer memberInU = iter.next();
			TreeSet<Integer> c_temp = updateSetC(c, memberInU);
			TreeSet<Integer> u_temp = updateSetU(c_temp);
			TreeSet<Integer> b_temp = updateSetB(c_temp, u_temp);
			// System.out.println(c_temp);
			// System.out.println(u_temp);
			// System.out.println(b_temp);
			// System.out.println("****************");
			double newR = calculateRCommunity(c_temp, b_temp);
			double dateR = newR - oldR;
			if (dateR > maxDateR)
			{
				id = memberInU;
				maxDateR = dateR;
			}
		}
		return id;
	}

	/* (Tested) */
	public ArrayList<ArrayList<Integer>> run_clauset(Integer v)// , int k
	{
		initSetC_B_U(v);
		while (true)
		{
			int id = getTheNodeWithMaxDateR(C, B, U);
			if (id == -1)
			{
				break;
			} else
			{
				C = updateSetC(C, id);
				U = updateSetU(C);
				B = updateSetB(C, U);
			}
		}
		if (this.C.contains((Integer) v))
		{
			TreeSet<Integer> detected_community = new TreeSet<Integer>();
			detected_community.addAll(C);
			// System.out.println("detected_community: " + detected_community);
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> detected_community_list = new ArrayList<Integer>();
			{
				detected_community_list.addAll(detected_community);
			}
			ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
			for (int i = 0; i < this.node_size; i++)
			{
				if (!detected_community.contains(i))
				{
					rest_nodes.add(i);
				}
			}
			partitionF.add(detected_community_list);
			partitionF.add(rest_nodes);
			return partitionF;
		} else
		{
			ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> community_1 = new ArrayList<Integer>();
			ArrayList<Integer> community_2 = new ArrayList<Integer>();
			for (int i = 0; i < this.node_size; i++)
			{
				community_2.add(i);
			}
			partitionF.add(community_1);
			partitionF.add(community_2);
			return partitionF;
		}
	}

	public static void main(String[] args) throws IOException
	{
		String file_path = "F:/1papers/20190411 first/data set/karate/";
		String file_name = "karate";
		// karate;dolphin;football;krebsbook;dblp;amazon;youtube
		// String network = "_network.txt";
		// karate_network;dolphin_network;football_network;krebsbook_network
		// dblp_network;amazon_network;youtube_network
		String community = "_community.txt";
		// karate_community;dolphin_community;football_community;krebsbook_community
		// dblp_community;amazon_community;youtube_community
		String table = "_table.txt";
		// karate_table;dolphin_table;football_table;krebsbook_table
		// dblp_table;amazon_table;youtube_table
		// String file_path_network = file_path + file_name + network;
		String file_path_community = file_path + file_name + community;
		String file_path_table = file_path + file_name + table;

		DataReaderRealWorldNetwork data_reader = new DataReaderRealWorldNetwork(
				file_path_community, file_path_table);
		data_reader.read_realworld_network();

		List result = new ArrayList<>();

		ClausetAlgorithm clauset = new ClausetAlgorithm(data_reader.n,
				data_reader.m, data_reader.adjacencyTable);
		for (int i = 0; i < data_reader.n; i++)
		{
			result.add(clauset.run_clauset(i));
		}
		// ca.run_clauset(0);
	}
}
