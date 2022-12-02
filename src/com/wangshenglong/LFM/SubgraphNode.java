package com.wangshenglong.LFM;

import java.util.TreeSet;

public class SubgraphNode
{
	public TreeSet<Integer> community;
	public TreeSet<Integer> neighbors;
	public int k_in_G;
	public int k_out_G;

	public SubgraphNode()
	{
		this.community = new TreeSet<Integer>();
		this.neighbors = new TreeSet<Integer>();
	}

	@Override
	public String toString()
	{
		return "SubgraphNode [community=" + community + ", neighbors="
				+ neighbors + ", k_in_G=" + k_in_G + ", k_out_G=" + k_out_G
				+ "]";
	}

}
