package com.wangshenglong.THIRD;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class ThirdDingAlgorithm {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<NodeCentrality> node_centrality;
    public String seeding_type;

    public ThirdDingAlgorithm(int n, int m,
                          ArrayList<ArrayList<Integer>> adjacencyTable,
                          ArrayList<NodeCentrality> node_centrality,
                              String seeding_type) {
        this.node_size = n;
        this.link_size = m;
        this.numberOfCommunities = 0;
        this.adjacencyTable = adjacencyTable;
        this.node_centrality = node_centrality;
        this.seeding_type = seeding_type;
    }

    /* (Tested) Get node neighbors */
    public TreeSet<Integer> get_node_neighbors(int node_id) {
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(adjacencyTable.get(node_id));
        neighbors.remove((Integer) node_id);
        return neighbors;
    }

    /* (Tested) Get insection of node sets */
    public TreeSet<Integer> get_insection(TreeSet<Integer> node_set_1,
                                          TreeSet<Integer> node_set_2) {
        TreeSet<Integer> insection = new TreeSet<Integer>();
        for (Iterator<Integer> iter = node_set_1.iterator(); iter.hasNext();) {
            Integer node = iter.next();
            if (node_set_2.contains(node)) {
                insection.add(node);
            }
        }
        return insection;
    }

    /* (Tested) Calculate weight of common neighbors */
    public double calculate_weight_of_common_neighbors(
            TreeSet<Integer> common_neighbors, int new_seed) {
        // int new_seed_centrality =
        // node_centrality.get(new_seed).centrality;//
        // calculate_node_centrality(new_seed);
        double common_neighbors_weight = 0;
        for (Iterator<Integer> iter = common_neighbors.iterator(); iter
                .hasNext();) {
            Integer neighbor = iter.next();
            int neighbor_centrality = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
            // if (neighbor_centrality <= new_seed_centrality)
            // {
            common_neighbors_weight += neighbor_centrality;
            // }
        }
        return common_neighbors_weight;
    }

    /* (Tested) Calculate common neighbors density */
    public double calculate_common_neighbors_density(TreeSet<Integer> insection) {
        double density = 0;
        ArrayList<Integer> insection_temp = new ArrayList<Integer>();
        insection_temp.addAll(insection);
        int number_of_nodes = insection_temp.size();
        int number_of_links = 0;
        insection_temp.addAll(insection);
        for (int i = 0; i < number_of_nodes; i++) {
            Integer member_1 = insection_temp.get(i);
            for (int j = i + 1; j < number_of_nodes; j++) {
                Integer member_2 = insection_temp.get(j);
                if (adjacencyTable.get(member_1).contains(member_2)) {
                    number_of_links++;
                }
            }
        }
        density = (double) 2 * number_of_links / (double) number_of_nodes
                * (number_of_nodes - 1);
        return density;
    }

    /* (Tested) Calculate relation strength */
    public double calculate_relation_strength(int old_seed, int candidate_seed) {
        double relation_strength = 0;
        TreeSet<Integer> neighbors_of_node_1 = get_node_neighbors(old_seed);
        TreeSet<Integer> neighbors_of_node_2 = get_node_neighbors(candidate_seed);
        TreeSet<Integer> insection = get_insection(neighbors_of_node_1,
                neighbors_of_node_2);
        insection.add(candidate_seed);
        insection.add(old_seed);

        double common_neighbors_weight = calculate_weight_of_common_neighbors(
                insection, candidate_seed);
        double common_neighbors_density = calculate_common_neighbors_density(insection);
        relation_strength = common_neighbors_weight * common_neighbors_density;


        return relation_strength;
    }


    /* (Tested) find the replacement seed node forthe initial_seed_node */
    public int find_replacement_seed_node_for_initial_seed_node_from_neighbors(
            int node_id) {
        int centrality_of_seed = node_centrality.get(node_id).centrality; // calculate_node_centrality(node_id);
        int seed_id_new = -1;
        double relation_strength_strongest = 0;
        TreeSet<Integer> neighbors_of_seed = get_node_neighbors(node_id);

        /***********************************************************************************/
        //20221006 Thinking of centrality first, and then similarity.
        if("old".equals(seeding_type)) {
            for (Iterator<Integer> iter = neighbors_of_seed.iterator(); iter
                    .hasNext(); ) {
                Integer neighbor = iter.next();
                int centrality_of_neighbor = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
                if (centrality_of_neighbor > centrality_of_seed) {
                    double relation_strength = calculate_relation_strength(node_id,
                            neighbor);
                    if (relation_strength > relation_strength_strongest)
                    // && relation_strength >
                    // relation_strength_of_seed_with_less_important_neighbors)
                    {
                        relation_strength_strongest = relation_strength;
                        seed_id_new = neighbor;
                    }
                }
            }
        } else if("new".equals(seeding_type)) {

            //20221006 Thinking of similarity first, and then centrality.
            for (Iterator<Integer> iter = neighbors_of_seed.iterator(); iter
                    .hasNext(); ) {
                Integer neighbor = iter.next();
                double relation_strength = calculate_relation_strength(node_id,
                        neighbor);
                if (relation_strength > relation_strength_strongest) {
                    //20221007 Lead to the better results. However, some nodes with the similar centrality could not be expanded.
                    relation_strength_strongest = relation_strength;

                    {
                        int centrality_of_neighbor = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
                        if (centrality_of_neighbor > centrality_of_seed) {
                            //20221007 The same as previous.
                            //relation_strength_strongest = relation_strength;
                            seed_id_new = neighbor;
                        }
                    }

                }
            }
            /***********************************************************************************/
        }

        return seed_id_new;
    }

    /* (Tested) Find replacement node of the seed node */
    public int find_replacement_seed_node_for_initial_seed_node(int seed_id) {
        int seed_id_new = seed_id;
        do {
            int candidaste_seed = find_replacement_seed_node_for_initial_seed_node_from_neighbors(seed_id_new);
            if (candidaste_seed == -1) {
                break;
            } else {
                seed_id_new = candidaste_seed;
            }
        } while (true);
        return seed_id_new;
    }

    /* (Tested) Initial community */
    public TreeSet<Integer> initial_community(int seed_id) {
        TreeSet<Integer> community = new TreeSet<Integer>();
        community.add(seed_id);
        return community;
    }

    /* (Tested) initial community neighbors */
    public TreeSet<Integer> initial_community_neighbors(int seed_id) {
        TreeSet<Integer> community_neighbors = new TreeSet<Integer>();
        community_neighbors.addAll(adjacencyTable.get(seed_id));
        community_neighbors.remove((Integer) seed_id);
        return community_neighbors;
    }

    /* (Tested) */
    public void update_community_and_community_neighbors(
            TreeSet<Integer> community, TreeSet<Integer> community_neighbors,
            int node_id) {
        TreeSet<Integer> node_neighbors = get_node_neighbors(node_id);
        community.add(node_id);
        community_neighbors.remove(node_id);
        for (Iterator<Integer> iter = node_neighbors.iterator(); iter.hasNext();) {
            Integer neighbor = iter.next();
            if (!community.contains(neighbor)) {
                community_neighbors.add(neighbor);
            }
        }
    }

    /* (Tested) make_community_neighbors_in_decrease_order_of_centrality */
    public TreeSet<NodeCentrality> make_community_neighbors_in_decrease_order_of_centrality(
            TreeSet<Integer> community_neighbors) {
        TreeSet<NodeCentrality> community_neighbors_in_decrease_orde = new TreeSet<NodeCentrality>(
                new NodeCentrality());
        for (Iterator<Integer> iter = community_neighbors.iterator(); iter
                .hasNext();) {
            Integer neighbor = iter.next();
            int neighbor_centrality = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
            NodeCentrality node = new NodeCentrality(neighbor,
                    neighbor_centrality);
            community_neighbors_in_decrease_orde.add(node);
        }
        return community_neighbors_in_decrease_orde;
    }

    /*
     * (Tested) Calculate the relation strength of the neighbors of the node
     * which have higher centrality than the node
     */
    public double calculate_relation_strength_of_node_set(
            ArrayList<Integer> node_set, int community_neighbor,
            int community_neighbor_centrality) {
        /* do not use node influence area */
        /* nodes_with_higher_centrality is changed into node_set */
        // ArrayList<Integer> nodes_with_higher_centrality = new
        // ArrayList<Integer>();
        // for (Iterator<Integer> iter = node_set.iterator(); iter.hasNext();) {
        // Integer node = iter.next();
        // int centrality_of_node = node_centrality.get(node).centrality; //
        // calculate_node_centrality(node);
        // if (centrality_of_node >= community_neighbor_centrality) {
        // nodes_with_higher_centrality.add(node);
        // }
        // }
        node_set.add(community_neighbor);
        int number_of_links = 0;
        int number_of_nodes = node_set.size();
        double density = 0;
        for (int i = 0; i < number_of_nodes; i++) {
            Integer member_1 = node_set.get(i);
            for (int j = i + 1; j < number_of_nodes; j++) {
                Integer member_2 = node_set.get(j);
                if (adjacencyTable.get(member_1).contains(member_2)) {
                    number_of_links++;
                }
            }
        }
        density = (double) 2 * number_of_links / (double) number_of_nodes
                * (number_of_nodes - 1);
        double weight = 0;
        for (Iterator<Integer> iter = node_set.iterator(); iter.hasNext();) {
            Integer neighbor = iter.next();
            int centrality_of_neighbor = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
            weight += centrality_of_neighbor;
        }
        double node_set_relation_strength = weight * density;
        return node_set_relation_strength;
    }

    /* (Tested) extend community */
    public void extend_community(TreeSet<Integer> community,
                                 TreeSet<Integer> community_neighbors) {
        do {
            int community_size = community.size();
            for (Iterator<Integer> iter = community_neighbors.iterator(); iter
                    .hasNext();) {
                Integer community_neighbor = iter.next();
                int more_important_neighbor_with_strongest_relation_strength = find_replacement_seed_node_for_initial_seed_node_from_neighbors(community_neighbor);
                if (community
                        .contains(more_important_neighbor_with_strongest_relation_strength))

                {
                    update_community_and_community_neighbors(community,
                            community_neighbors, community_neighbor);
                    break;
                } else {
                    TreeSet<Integer> neighbor_neighbors = get_node_neighbors(community_neighbor);
                    ArrayList<Integer> neighbors_in_community = new ArrayList<Integer>();
                    ArrayList<Integer> neighbors_out_community = new ArrayList<Integer>();
                    for (Iterator<Integer> iter_neighbor = neighbor_neighbors
                            .iterator(); iter_neighbor.hasNext();) {
                        Integer neighbor_neighbor = iter_neighbor.next();
                        if (community.contains(neighbor_neighbor)) {
                            neighbors_in_community.add(neighbor_neighbor);
                        } else {
                            neighbors_out_community.add(neighbor_neighbor);
                        }
                    }
                    int community_neighbor_centrality = node_centrality
                            .get(community_neighbor).centrality; // calculate_node_centrality(community_neighbor);
                    double relation_strength_with_community = calculate_relation_strength_of_node_set(
                            neighbors_in_community, community_neighbor,
                            community_neighbor_centrality);
                    double relation_strength_with_rest = calculate_relation_strength_of_node_set(
                            neighbors_out_community, community_neighbor,
                            community_neighbor_centrality);
                    if (relation_strength_with_community > relation_strength_with_rest) {
                        update_community_and_community_neighbors(community,
                                community_neighbors, community_neighbor);
                        break;
                    }
                }
            }
            if (community_size < community.size()) {
                continue;
            } else {
                break;
            }
        } while (true);
    }

    public ArrayList<ArrayList<Integer>> run_thirdDing(int seed_id) {
        int seed_id_new = find_replacement_seed_node_for_initial_seed_node(seed_id);
        TreeSet<Integer> detected_community = initial_community(seed_id_new);
        TreeSet<Integer> community_neighbors = initial_community_neighbors(seed_id_new);
        extend_community(detected_community, community_neighbors);
        // System.out.println("seed_id_new: " + seed_id_new);
        // System.out.println("community:" + community);
        // if (community.contains(seed_id))
        // {
        // System.out.println(true);
        // }
        // System.out.println("community_neighbors: " + community_neighbors);

        if (detected_community.contains((Integer) seed_id)) {
            // System.out.println("detected_community: " + detected_community);
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> detected_community_list = new ArrayList<Integer>();
            {
                detected_community_list.addAll(detected_community);
            }
            ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
            for (int i = 0; i < this.node_size; i++) {
                if (!detected_community.contains(i)) {
                    rest_nodes.add(i);
                }
            }
            partitionF.add(detected_community_list);
            partitionF.add(rest_nodes);
            return partitionF;
        } else {
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> community_1 = new ArrayList<Integer>();
            ArrayList<Integer> community_2 = new ArrayList<Integer>();
            for (int i = 0; i < this.node_size; i++) {
                community_2.add(i);
            }
            partitionF.add(community_1);
            partitionF.add(community_2);
            return partitionF;
        }
    }

    public static void main(String[] args) throws IOException {
        String file_path = "D:/real world networks/";
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

//		FifthAlgorithm fifth = new FifthAlgorithm(data_reader.n,
//		 data_reader.m,
//		 data_reader.adjacencyTable,);
        // for (int i = 0; i < data_reader.n; i++)
        // {
        // fifth.run_fifth(i);
        // }
//		fifth.run_fifth(24);
    }
}
