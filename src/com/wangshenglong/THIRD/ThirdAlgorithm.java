package com.wangshenglong.THIRD;

import java.io.IOException;
import java.util.*;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;

public class ThirdAlgorithm {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<NodeCentrality> node_centrality;
    public ArrayList<NodeKCoreValue> node_KCore_value;
    public String seeding_type;
    public String similarity_type;

    public ThirdAlgorithm(int n, int m,
                          ArrayList<ArrayList<Integer>> adjacencyTable,
                          ArrayList<NodeCentrality> node_centrality,
                          ArrayList<NodeKCoreValue> node_KCore_value,
                          String similarity_type,
                          String seeding_type) {
        this.node_size = n;
        this.link_size = m;
        this.numberOfCommunities = 0;
        this.adjacencyTable = adjacencyTable;
        this.node_centrality = node_centrality;
        this.node_KCore_value = node_KCore_value;
        this.seeding_type = seeding_type;
        this.similarity_type = similarity_type;
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
        for (Iterator<Integer> iter = node_set_1.iterator(); iter.hasNext(); ) {
            Integer node = iter.next();
            if (node_set_2.contains(node)) {
                insection.add(node);
            }
        }
        return insection;
    }

    public ArrayList<Integer> get_insection(ArrayList<Integer> node_set_1,
                                            ArrayList<Integer> node_set_2) {
        ArrayList<Integer> insection = new ArrayList<>();
        for (int node :node_set_1 ) {
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
                .hasNext(); ) {
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
    public int find_candidate(
            int node_id) {
        int centrality_of_seed = node_centrality.get(node_id).centrality; // calculate_node_centrality(node_id);
        int seed_id_new = -1;
        double relation_strength_strongest = 0;
        TreeSet<Integer> neighbors_of_seed = get_node_neighbors(node_id);

        /***********************************************************************************/
        //20221006 Thinking of centrality first, and then similarity.
        if ("old".equals(seeding_type)) {
            for (Iterator<Integer> iter = neighbors_of_seed.iterator(); iter
                    .hasNext(); ) {
                Integer neighbor = iter.next();
                int centrality_of_neighbor = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
                if (centrality_of_neighbor > centrality_of_seed) {
                    //20221019 First means using the complex or simple similarity for seeding. Second means using own similarity index for seeding.
                    double relation_strength = calculateNodeToNodeSimilarityForSeeding(node_id, neighbor);
//                double relation_strength = calculateNodeToNodeSimilarity(node_id, neighbor);
                    if (relation_strength > relation_strength_strongest)
                    // && relation_strength >
                    // relation_strength_of_seed_with_less_important_neighbors)
                    {
                        relation_strength_strongest = relation_strength;
                        seed_id_new = neighbor;
                    }
                }
            }
        } else if ("new".equals(seeding_type)) {

            //20221006 Thinking of similarity first, and then centrality.
            for (Iterator<Integer> iter = neighbors_of_seed.iterator(); iter
                    .hasNext(); ) {
                Integer neighbor = iter.next();
                //20221019 First means using the complex or simple similarity for seeding. Second means using own similarity index for seeding.
                double relation_strength = calculateNodeToNodeSimilarityForSeeding(node_id, neighbor);
//                double relation_strength = calculateNodeToNodeSimilarity(node_id, neighbor);
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
    public int seeding(int seed_id) {
        int seed_id_new = seed_id;
        do {
            int candidaste_seed = find_candidate(seed_id_new);
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
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(get_node_neighbors(seed_id));
        community.add(seed_id);
        community.addAll(neighbors);
        community = communityCleanup(community, seed_id);
        return community;
    }
    //20221009 cleanup initial community
    public TreeSet<Integer> communityCleanup(TreeSet<Integer> community, int seed_id) {
        Queue<Integer> community_queue = new LinkedList<>();
        TreeSet<Integer> community_nodes = new TreeSet<>();
        TreeSet<Integer> temp_list = new TreeSet<>();
        community_nodes.addAll(community);

        do {
            temp_list.clear();
            community_queue.addAll(community_nodes);
            community_queue.remove((Integer)seed_id);
            while(!community_queue.isEmpty()) {
                int node_id = community_queue.poll();
                community_nodes.remove((Integer)node_id);
                boolean contain = isContained(node_id, community_nodes);
                if (contain) {
                    community_nodes.add(node_id);
                } else {
                    temp_list.add(node_id);
                }
            }
        } while(!temp_list.isEmpty());

        return community_nodes;
    }

    public boolean isContained(int node_id, TreeSet<Integer> detected_community) {
        boolean isContained = true;
        TreeSet<Integer> neighbors = get_node_neighbors(node_id);
        TreeSet<Integer> neighbors_in = new TreeSet<>();
        TreeSet<Integer> neighbors_out = new TreeSet<>();
        double neighbors_conectivity_in = 0;
        double neighbors_conectivity_out = 0;
        TreeSet<Integer> connectivity_out = new TreeSet();
        for(int neighbor : neighbors) {
            if(detected_community.contains(neighbor)) {
                neighbors_in.add(neighbor);
            } else {
                neighbors_out.add(neighbor);
            }
        }

        neighbors_conectivity_in = calculateNodeCommunitySimilarity(node_id, neighbors_in);
        neighbors_conectivity_out = calculateNodeCommunitySimilarity(node_id, neighbors_out);

        if(neighbors_conectivity_in > neighbors_conectivity_out) {
            isContained = true;
        } else {
            isContained = false;
        }
        return isContained;
    }

    public double calculateNodeCommunitySimilarity(int node, TreeSet<Integer> community) {
        double similarity = 0;
        TreeSet<Integer> node_neighbors = new TreeSet<>();
        TreeSet<Integer> nodeCommunityCommon = new TreeSet<>();
        node_neighbors = get_node_neighbors(node);
        nodeCommunityCommon = get_insection(community, node_neighbors);

        if("First".equals(similarity_type)) {
            nodeCommunityCommon.add(node);
            double edge_number = 0;
            double node_number = (double) nodeCommunityCommon.size();
            while (!nodeCommunityCommon.isEmpty()) {
                int node_id = nodeCommunityCommon.pollFirst();
                TreeSet<Integer> node_id_neighbors = new TreeSet<>();
                node_id_neighbors = get_node_neighbors(node_id);
                for (int neighbor : node_id_neighbors) {
                    if (nodeCommunityCommon.contains(neighbor)) {
                        //20201202 给边增加权重
                        double degree_node_id = getDegree(node_id);
                        double degree_neighbor = getDegree(neighbor);
                        edge_number += degree_node_id + degree_neighbor;
                    }
                }
            }
            similarity = edge_number * node_number;
        } else if("Jaccard".equals(similarity_type) || "Salton".equals(similarity_type) || "Access21".equals(similarity_type)
                || "NS22".equals(similarity_type) || "CoNeighbors".equals(similarity_type) || "RAIndex".equals(similarity_type)
                || "Ding19".equals(similarity_type) || "SecLevel".equals(similarity_type) || "LocalKCore".equals(similarity_type)
                || "Ding18Wrong".equals(similarity_type) || "One".equals(similarity_type) || "Two".equals(similarity_type)
                || "Three".equals(similarity_type)) {
            for(int nodeB : nodeCommunityCommon) {
                similarity += calculateNodeToNodeSimilarity(node, nodeB);
            }
        } else if("Ding20".equals(similarity_type)) {
            nodeCommunityCommon.add(node);
            int links_between_neighbors = 0;
            int all_links = 0;
            ArrayList<Integer> node_mass = new ArrayList<>();
            node_mass.addAll(nodeCommunityCommon);
            for (int i = 0; i < node_mass.size(); i++) {
                int neighbor_1 = node_mass.get(i);
                for (int j = i + 1; j < node_mass.size(); j++) {
                    int neighbor_2 = node_mass.get(j);
                    if (adjacencyTable.get(neighbor_1).contains(neighbor_2)) {
                        links_between_neighbors++;
                    }
                }
            }
            similarity = links_between_neighbors;
        } else if("Ding18".equals(similarity_type)) {
            TreeSet<Integer> neighbors = new TreeSet<>();
            TreeSet<Integer> common_neighbors = new TreeSet<>();
            neighbors = get_node_neighbors(node);
            neighbors.add(node);
            common_neighbors = get_insection(neighbors, community);

            ArrayList<Integer> common_neighbors_ArrayList = new ArrayList<>();
            common_neighbors_ArrayList.addAll(common_neighbors);

            int weight = 0;
            for(int node_id : common_neighbors) {
                ArrayList<Integer> node_mass = new ArrayList<>();
                node_mass.addAll(get_node_neighbors(node_id));
                node_mass.add(node_id);
                weight += calculate_links_among_community(node_mass);
            }


            int real_links = calculate_links_among_community(common_neighbors_ArrayList);
            double will_links = 0;
            double influence_area_IA = (double)common_neighbors.size();
            will_links = (influence_area_IA * (influence_area_IA - 1)) / 2;
            double density = real_links / will_links;

            similarity = weight * density;
        } else if("FirstKCore".equals(similarity_type)) {
            nodeCommunityCommon.add(node);
            double edge_number = 0;
            double node_number = (double) nodeCommunityCommon.size();
            while (!nodeCommunityCommon.isEmpty()) {
                int node_id = nodeCommunityCommon.pollFirst();
                TreeSet<Integer> node_id_neighbors = new TreeSet<>();
                node_id_neighbors = get_node_neighbors(node_id);
                for (int neighbor : node_id_neighbors) {
                    if (nodeCommunityCommon.contains(neighbor)) {
                        //20201202 给边增加权重
                        double kcore_node_id = (double)node_KCore_value.get(node_id).getCore_value();
                        double kcore_neighbor = (double)node_KCore_value.get(neighbor).getCore_value();
                        edge_number += kcore_node_id + kcore_neighbor;
                    }
                }
            }
            similarity = edge_number * node_number;
        }


        return similarity;
    }

    public double calculateNodeToNodeSimilarity(int nodeA, int nodeB) {
        double similarity = 0;
        TreeSet<Integer> node_neighborsA = new TreeSet<>();
        TreeSet<Integer> node_neighborsB = new TreeSet<>();
        TreeSet<Integer> common_neighbors = new TreeSet<>();
        node_neighborsA = get_node_neighbors(nodeA);
        node_neighborsB = get_node_neighbors(nodeB);
        node_neighborsA.add(nodeA);
        node_neighborsB.add(nodeB);
        common_neighbors = get_insection(node_neighborsA, node_neighborsB);
        TreeSet<Integer> unionOfAB = new TreeSet<>();
        unionOfAB.addAll(node_neighborsA);
        unionOfAB.addAll(node_neighborsB);

        if("Jaccard".equals(similarity_type)) {
            //1.Jaccard Index
            similarity = (double) common_neighbors.size() / (double) unionOfAB.size();
        } else if ("Salton".equals(similarity_type)) {
            //2.Salton Index(Cosine similarity)
            similarity = (double) common_neighbors.size() / java.lang.Math.sqrt((double) (node_neighborsA.size() * node_neighborsB.size()));
        } else if ("Access21".equals(similarity_type)) {
            double similarityAccess21 = 0;
            for(int nodeFirst : common_neighbors) {
                similarityAccess21 += 1/(double)getDegree(nodeFirst);
            }
            for(int nodeA_neighbor : node_neighborsA) {
                TreeSet<Integer> nodeAneighbors_neighbors = new TreeSet<>();
                nodeAneighbors_neighbors = get_node_neighbors(nodeA_neighbor);
                for(int nodeB_neighbor : node_neighborsB) {
                    if(nodeAneighbors_neighbors.contains(nodeB_neighbor)) {
                        int nodeA_neighbor_degree = getDegree(nodeA_neighbor);
                        int nodeB_neighbor_degree = getDegree(nodeB_neighbor);
                        similarityAccess21 += 1 / java.lang.Math.sqrt((double) ((double) (nodeA_neighbor_degree * nodeB_neighbor_degree)));
                    }
                }
            }
            similarity = similarityAccess21;
        } else if ("NS22".equals(similarity_type)) {
            double NS22 = 0;
            NS22 = (double) (common_neighbors.size() + 1) / (double) unionOfAB.size();
            NS22 += 1 / ((double)getDegree(nodeA) * (double)getDegree(nodeB));
            int links_between_neighbors = 0;
            ArrayList<Integer> neighbors = new ArrayList<Integer>();
            neighbors.addAll(common_neighbors);
            links_between_neighbors = calculate_links_among_community(neighbors);
            NS22 += (double) links_between_neighbors / (double) (common_neighbors.size());
            similarity = NS22;
        } else if ("CoNeighbors".equals(similarity_type)) {
            similarity = common_neighbors.size();
        } else if ("RAIndex".equals(similarity_type)) {
            for(int commonNode : common_neighbors) {
                int commonNodeDegree = getDegree(commonNode);
                similarity += 1/(double)commonNodeDegree;
            }
        } else if ("Ding19".equals(similarity_type)) {
            //Calculate local K-core value
            ArrayList<Integer> nodeSet = new ArrayList<>();
            nodeSet.addAll(common_neighbors);
            int local_KCore_NodeA = calculate_local_KCore(nodeA,nodeSet);

            int KCore_value_nodeA = node_KCore_value.get(nodeA).core_value;
            int KCore_value_nodeB = node_KCore_value.get(nodeB).core_value;
            similarity = ((double)local_KCore_NodeA/(double)KCore_value_nodeA) * java.lang.Math.sqrt((double) ((double) (KCore_value_nodeA * KCore_value_nodeB)));
        }  else if ("Ding18Wrong".equals(similarity_type)) {
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(common_neighbors);

            int weight = 0;
            for(int node : common_neighbors) {
                ArrayList<Integer> node_mass = new ArrayList<>();
                node_mass.addAll(get_node_neighbors(node));
                node_mass.add(node);
                weight += calculate_links_among_community(node_mass);
            }


            int real_links = calculate_links_among_community(community);
            double will_links = 0;
            double influence_area_IA = (double)common_neighbors.size();
            will_links = (influence_area_IA * (influence_area_IA - 1)) / 2;
            double density = real_links / will_links;
            density = calculate_common_neighbors_density(common_neighbors);

            similarity = weight * density;
        } else if ("SecLevel".equals(similarity_type)) {
            TreeSet<Integer> second_neighbors_nodeA = new TreeSet<>();
            TreeSet<Integer> second_neighbors_nodeB = new TreeSet<>();
            second_neighbors_nodeA = calculate_second_level_neighbors(nodeA);
            second_neighbors_nodeB = calculate_second_level_neighbors(nodeB);
            TreeSet<Integer> common_nodes = new TreeSet<>();
            common_nodes.addAll(get_insection(second_neighbors_nodeA, second_neighbors_nodeB));
            TreeSet<Integer> union_neighbors = new TreeSet<>();
            union_neighbors.addAll(second_neighbors_nodeA);
            union_neighbors.addAll(second_neighbors_nodeB);
            ArrayList<Integer> union_neighbors_Array = new ArrayList<>();
            union_neighbors_Array.addAll(union_neighbors);
            int local_KCore_NodeA = calculate_local_KCore(nodeA,union_neighbors_Array);
            int local_KCore_NodeB = calculate_local_KCore(nodeB,union_neighbors_Array);

            similarity = java.lang.Math.sqrt((double) ((double) (local_KCore_NodeA * local_KCore_NodeB)));
        } else if ("LocalKCore".equals(similarity_type)) {
//            TreeSet<Integer> second_neighbors_nodeA = new TreeSet<>();
//            second_neighbors_nodeA = calculate_second_level_neighbors(nodeA);

            ArrayList<Integer> union_neighbors_Array = new ArrayList<>();
            union_neighbors_Array.addAll(node_neighborsA);
            int local_KCore_NodeA = calculate_local_KCore(nodeA,union_neighbors_Array);
            int local_KCore_NodeB = calculate_local_KCore(nodeB,union_neighbors_Array);

            similarity = java.lang.Math.sqrt((double) ((double) (local_KCore_NodeA * local_KCore_NodeB)));
        } else if ("One".equals(similarity_type)) {
            //Take real links among community as the similarity index.
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(common_neighbors);
            int real_links_among_community = calculate_links_among_community(community);
            similarity = (double)real_links_among_community;
        } else if ("Two".equals(similarity_type)) {
            //Take real links`s degrees among community as the similarity index.
            int real_links_degree_among_community = calculate_links_weight_among_community(common_neighbors);
            similarity = (double)real_links_degree_among_community;
        } else if ("Three".equals(similarity_type)) {
            int real_degree = calculate_links_weight_among_community(common_neighbors);
            for(int node : common_neighbors) {
                ArrayList<Integer> node_mass = new ArrayList<>();
                node_mass.addAll(get_node_neighbors(node));
                node_mass.add(node);
                similarity += (double)node_mass.size() * (double)real_degree;
            }
        } else if ("Four".equals(similarity_type)) {
            int real_degree = calculate_links_weight_among_community(common_neighbors);
            int will_degree = 0;
            int size = common_neighbors.size();
            for(int commonNode : common_neighbors) {
                int commonNodeDegree = getDegree(commonNode);
                will_degree += commonNodeDegree;
            }
            double density = (double)real_degree;
            for(int node : common_neighbors) {
                ArrayList<Integer> node_mass = new ArrayList<>();
                node_mass.addAll(get_node_neighbors(node));
                node_mass.add(node);
                int weight = 0;
                for(int node_mass_id : node_mass) {
                    weight += getDegree(node_mass_id);
                }
                similarity += (double)weight * density;
            }
        }

        return similarity;
    }
    public int getDegree(int node_id) {
        int degree = adjacencyTable.get(node_id).size() - 1;
        return degree;
    }

    public TreeSet<Integer> calculate_second_level_neighbors(int node) {
        TreeSet<Integer> second_level_neighbors = new TreeSet<>();
        TreeSet<Integer> node_neighbors = new TreeSet();
        node_neighbors.addAll(get_node_neighbors(node));
        second_level_neighbors.addAll(node_neighbors);
        for(int neighbor : node_neighbors) {
            TreeSet<Integer> neighbor_neighbors = new TreeSet();
            neighbor_neighbors.addAll(get_node_neighbors(neighbor));
            second_level_neighbors.addAll(neighbor_neighbors);
        }
        return second_level_neighbors;
    }

    public int calculate_local_KCore(int node, ArrayList<Integer> community) {
        int local_KCore_NodeA = 0;
        ArrayList<ArrayList<Integer>> adjacencyTableTemp = new ArrayList<>();
        ArrayList<Integer> nodeSet = new ArrayList<>();
        nodeSet.addAll(community);
        adjacencyTableTemp = calculate_adcacencyTableTemp(nodeSet);

        CalculateNodeKCore cnkc = new CalculateNodeKCore(nodeSet.size(), adjacencyTableTemp);
        ArrayList<NodeKCoreValue> Local_Nodes_KCore = cnkc.calculate_node_KCore_for_all_nodes();
        for(NodeKCoreValue localNKCV : Local_Nodes_KCore) {
            if(localNKCV.id == node) {
                local_KCore_NodeA = localNKCV.getCore_value();
            }
        }
        return local_KCore_NodeA;
    }

    public int calculate_links_among_community(ArrayList<Integer> community) {
        int links_between_neighbors = 0;
        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.addAll(community);
        for (int i = 0; i < nodes.size(); i++) {
            int neighbor_1 = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                int neighbor_2 = nodes.get(j);
                if (adjacencyTable.get(neighbor_1).contains(neighbor_2)) {
                    links_between_neighbors++;
                }
            }
        }
        return links_between_neighbors;
    }

    public int calculate_links_weight_among_community(TreeSet<Integer> community) {
        int weight = 0;
        ArrayList<Integer> nodes = new ArrayList<>();
        nodes.addAll(community);
        for (int i = 0; i < nodes.size(); i++) {
            int nodeA = nodes.get(i);
            int degree_nodeA = getDegree(nodeA);
            for (int j = i + 1; j < nodes.size(); j++) {
                int nodeB = nodes.get(j);
                int degree_nodeB = getDegree(nodeB);
                if (adjacencyTable.get(nodeA).contains(nodeB)) {
                    weight += degree_nodeA + degree_nodeB;
                }
            }
        }
        return weight;
    }
    public ArrayList<ArrayList<Integer>> calculate_adcacencyTableTemp(ArrayList<Integer> nodes) {
        ArrayList<ArrayList<Integer>> adjacencyTableTemp = new ArrayList<>();
        ArrayList<Integer> commonNeighbors = new ArrayList<>();
        commonNeighbors.addAll(nodes);
        for (int i=0; i < commonNeighbors.size(); i++) {
            ArrayList<Integer> adjacencyTemp = new ArrayList<>();
            int commonNodeA = commonNeighbors.get(i);
            adjacencyTemp.add(commonNodeA);
            for (int j=0; j < commonNeighbors.size(); j++) {
                int commonNodeB = commonNeighbors.get(j);
                if(commonNodeA != commonNodeB) {
                    if(adjacencyTable.get(commonNodeA).contains(commonNodeB)) {
                        adjacencyTemp.add(commonNodeB);
                    }
                }
            }
            adjacencyTableTemp.add(adjacencyTemp);
        }
        return adjacencyTableTemp;
    }

    public TreeSet<Integer> extend_community(TreeSet<Integer> detected_community) {
        TreeSet<Integer> temp_community = new TreeSet<>();
        TreeSet<Integer> community_neighbors = new TreeSet<>();
        Queue<Integer> suspicious_nodes = new LinkedList<>();
        temp_community.addAll(detected_community);
        community_neighbors = get_community_neighbors(temp_community);

        TreeSet<Integer> temp_list = new TreeSet<>();
        temp_list.addAll(community_neighbors);
        TreeSet<Integer> new_members = new TreeSet<>();
        do {
            suspicious_nodes.addAll(temp_list);
            temp_list.clear();
            new_members.clear();
            while(!suspicious_nodes.isEmpty()) {
                int node_id = suspicious_nodes.poll();
                boolean contain = isContained(node_id, temp_community);
                if (contain) {
                    temp_community.add(node_id);
                    new_members.add(node_id);
                    TreeSet<Integer> neighbor_suspicious_nodes = new TreeSet<>();
                    neighbor_suspicious_nodes = get_node_neighbors(node_id);
                    neighbor_suspicious_nodes.removeAll(get_insection(neighbor_suspicious_nodes, temp_community));
                    temp_list.addAll(neighbor_suspicious_nodes);
                } else {
                    temp_list.add(node_id);
                }
            }
        } while (!new_members.isEmpty());

        return temp_community;
    }
    public TreeSet<Integer> get_community_neighbors(TreeSet<Integer> detected_community) {
        TreeSet<Integer> community_neighbors = new TreeSet<>();
        for(int node : detected_community) {
            TreeSet<Integer> neighbors = get_node_neighbors(node);
            for(int neighbor : neighbors) {
                if(!detected_community.contains(neighbor)) {
                    community_neighbors.add(neighbor);
                }
            }
        }
        return community_neighbors;
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
        for (Iterator<Integer> iter = node_neighbors.iterator(); iter.hasNext(); ) {
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
                .hasNext(); ) {
            Integer neighbor = iter.next();
            int neighbor_centrality = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
            NodeCentrality node = new NodeCentrality(neighbor,
                    neighbor_centrality);
            community_neighbors_in_decrease_orde.add(node);
        }
        return community_neighbors_in_decrease_orde;
    }

    public double calculateNodeToNodeSimilarityForSeeding(int nodeA, int nodeB) {
        double similarity = 0;
        TreeSet<Integer> node_neighborsA = new TreeSet<>();
        TreeSet<Integer> node_neighborsB = new TreeSet<>();
        TreeSet<Integer> common_neighbors = new TreeSet<>();
        node_neighborsA = get_node_neighbors(nodeA);
        node_neighborsB = get_node_neighbors(nodeB);
        node_neighborsA.add(nodeA);
        node_neighborsB.add(nodeB);

        //Using the new similarity index.
        common_neighbors = get_insection(node_neighborsA, node_neighborsB);
        int real_degree = calculate_links_weight_among_community(common_neighbors);
        int will_degree = 0;
        int size = common_neighbors.size();
        for(int commonNode : common_neighbors) {
            int commonNodeDegree = getDegree(commonNode);
            will_degree += commonNodeDegree;
        }
        double density = (double)real_degree;
        for(int node : common_neighbors) {
            ArrayList<Integer> node_mass = new ArrayList<>();
            node_mass.addAll(get_node_neighbors(node));
            node_mass.add(node);
            similarity += (double)node_mass.size() * density;
        }

        //Take Jaccard Index as the similarity measure.
        /*TreeSet<Integer> unionOfAB = new TreeSet<>();
        unionOfAB.addAll(node_neighborsA);
        unionOfAB.addAll(node_neighborsB);
        similarity = (double) common_neighbors.size() / (double) unionOfAB.size();*/

        return similarity;
    }

    /*
     * (Tested) Calculate the relation strength of the neighbors of the node
     * which have higher centrality than the node
     */
    public double calculate_relation_strength_of_node_set(
            ArrayList<Integer> node_set, int community_neighbor,
            int community_neighbor_centrality) {
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
        for (Iterator<Integer> iter = node_set.iterator(); iter.hasNext(); ) {
            Integer neighbor = iter.next();
            int centrality_of_neighbor = node_centrality.get(neighbor).centrality; // calculate_node_centrality(neighbor);
            weight += centrality_of_neighbor;
        }
        double node_set_relation_strength = weight * density;
        return node_set_relation_strength;
    }

    public ArrayList<ArrayList<Integer>> run_third(int seed_id) {
        ArrayList<ArrayList<Integer>> partitionF = new ArrayList<>();
        TreeSet<Integer> detected_community = new TreeSet<>();
        TreeSet<Integer> rest_nodes = new TreeSet<>();

        seed_id = seeding(seed_id);
        detected_community = initial_community(seed_id);
        detected_community = extend_community(detected_community);

        for (int i = 0; i < node_size; i++) {
            if (!detected_community.contains(i)) {
                rest_nodes.add(i);
            }
        }
        ArrayList<Integer> temp_community = new ArrayList<>();
        ArrayList<Integer> temp_rest_nodes = new ArrayList<>();
        temp_community.addAll(detected_community);
        temp_rest_nodes.addAll(rest_nodes);
        partitionF.add(temp_community);
        partitionF.add(temp_rest_nodes);
        return partitionF;
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

    }
}
