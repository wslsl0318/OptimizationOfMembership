package com.wangshenglong.THIRD;

import java.util.ArrayList;
import java.util.TreeSet;

public class CalculateNodeCentrality {

    public int n;// node number
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public String centrality_type;

    public CalculateNodeCentrality(int n, ArrayList<ArrayList<Integer>> adjacencyTable, String centrality_type)
    {
        this.n = n;
        this.adjacencyTable = adjacencyTable;
        this.centrality_type = centrality_type;
    }

    public ArrayList<NodeCentrality> calculate_node_centrality_for_all_nodes()
    {
        ArrayList<NodeCentrality> nodes_centrality = new ArrayList<NodeCentrality>();
        for (int i = 0; i < this.n; i++)
        {
            NodeCentrality node_centrality = new NodeCentrality(i,
                    calculate_node_centrality(i));
            nodes_centrality.add(node_centrality);
        }
        return nodes_centrality;
    }

    public int calculate_node_centrality(int node_id)
    {
        int node_centrality = 0;
        //Take the node mass as the node centrality.
        if("node_mass".equals(centrality_type)) {
            TreeSet<Integer> neighbors_tree = get_node_neighbors(node_id);
            ArrayList<Integer> neighbors = new ArrayList<Integer>();
            neighbors.addAll(neighbors_tree);
            int links_between_neighbors = 0;
            int all_links = 0;
            for (int i = 0; i < neighbors.size(); i++) {
                int neighbor_1 = neighbors.get(i);
                for (int j = i + 1; j < neighbors.size(); j++) {
                    int neighbor_2 = neighbors.get(j);
                    if (adjacencyTable.get(neighbor_1).contains(neighbor_2)) {
                        links_between_neighbors++;
                    }
                }
            }
            all_links = links_between_neighbors + neighbors.size();
            // System.out.println(all_links);
            node_centrality = all_links;
        //20221008 Take the node degree as the node centrality.
        } else if ("node_degree".equals(centrality_type)) {
            int degree = adjacencyTable.get(node_id).size() - 1;
            node_centrality = degree;
        //20221009 Take the sum degree of node neighbors as the node centrality.
        } else if ("degrees".equals(centrality_type)) {
            int degrees = 0;
            TreeSet<Integer> neighbors_tree = get_node_neighbors(node_id);
            ArrayList<Integer> neighbors = new ArrayList<Integer>();
            neighbors.addAll(neighbors_tree);
            for(int neighbor_id : neighbors) {
                int degree = adjacencyTable.get(node_id).size() - 1;
                degrees += degree;
            }
            node_centrality = degrees;
        }
        return node_centrality;
    }

    public TreeSet<Integer> get_node_neighbors(int node_id)
    {
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(adjacencyTable.get(node_id));
        neighbors.remove((Integer) node_id);
        return neighbors;
    }
}
