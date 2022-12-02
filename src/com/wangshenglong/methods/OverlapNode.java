package com.wangshenglong.methods;

public class OverlapNode
{
	public int node_id;
	public int overlap_times;

	// public TreeSet<Integer> community_ids;

	public OverlapNode()
	{

	}

	public OverlapNode(int node_id, int overlap_times)
	{
		this.node_id = node_id;
		this.overlap_times = overlap_times;
		// this.community_ids = new TreeSet<Integer>();
	}

	@Override
	public String toString()
	{
		return "OverlapNode [node_id=" + node_id + ", overlap_times="
				+ overlap_times + "]";
	}

}
