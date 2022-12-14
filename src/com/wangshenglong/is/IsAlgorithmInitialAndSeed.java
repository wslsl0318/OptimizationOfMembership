package com.wangshenglong.is;

import java.util.*;

/******    20190614 Nodes Connectivity    ******/
public class IsAlgorithmInitialAndSeed {
    public int node_size;
    public int link_size;
    public int numberOfCommunities;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public NodeInfluence ni = new NodeInfluence();
    public TreeSet<NodeInfluence> node_influences;
    public Queue<Integer> c_must_queue;
    public String potential_community;
    public String core_nodes;

    public IsAlgorithmInitialAndSeed(int n, int m, ArrayList<ArrayList<Integer>> adjacencyTable, String potential_community, String core_nodes) {
        this.node_size = n;
        this.link_size = m;
        this.numberOfCommunities = 0;
        this.adjacencyTable = adjacencyTable;
        this.c_must_queue = new LinkedList<>();
        this.potential_community = potential_community;
        this.core_nodes = core_nodes;
    }
    public void initial(String type) {
        this.node_influences = ni.getNodeInfluence(type, adjacencyTable);
    }

    public ArrayList<ArrayList<Integer>> run_is(int seed_id) {
        ArrayList<ArrayList<Integer>> partitionF = new ArrayList<>();
        TreeSet<Integer> detected_community = new TreeSet<>();
        TreeSet<Integer> rest_nodes = new TreeSet<>();
        TreeSet<Integer> community_neighbors = new TreeSet<>();
        TreeSet<Integer> temp_detected_community = new TreeSet<Integer>();
        ArrayList<NodeInfluence> node_influences_id = new ArrayList<>();
        node_influences_id.addAll(node_influences);
        Collections.sort(node_influences_id, new Comparator<NodeInfluence>() {
            public int compare(NodeInfluence o1, NodeInfluence o2) {
                return o1.id - o2.id;
            }
        });

        //20200124 add candidate seed node algorithm  20200624 change basic method to potential community
        ClusterCore cCore = new ClusterCore();
        if("yes".equals(core_nodes)) {
            cCore = find_replacement_seed_node_for_initial_seed_node(seed_id, node_influences_id);
            detected_community = cCore.getList();
//            System.out.println("seed: " + cCore.getNode());
        }
        community_neighbors = get_community_neighbors(detected_community);
        Queue<Integer> suspicious_nodes = new LinkedList<>();
        suspicious_nodes.addAll(community_neighbors);
        TreeSet<Integer> temp_suspicious = new TreeSet<>();
        detected_community = extend_community(detected_community, suspicious_nodes, temp_suspicious, node_influences_id);

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

    public TreeSet<Integer> extend_community(TreeSet<Integer> detected_community, Queue<Integer> suspicious_nodes,
                                             TreeSet<Integer> temp_suspicious, ArrayList<NodeInfluence> node_influences_id) {
        Queue<Integer> new_suspicious_nodes = new LinkedList<>();
        while(!suspicious_nodes.isEmpty()) {
            int node_id = suspicious_nodes.poll();
            boolean contain = isContained(node_id, detected_community, node_influences_id);
            if (contain) {
                detected_community.add(node_id);
                TreeSet<Integer> neighbor_suspicious_nodes = new TreeSet<>();
                neighbor_suspicious_nodes = getSuspiciousNodes(node_id, detected_community,
                        suspicious_nodes, new_suspicious_nodes);
                new_suspicious_nodes.addAll(neighbor_suspicious_nodes);
            } else {
                new_suspicious_nodes.add(node_id);
            }
        }
        TreeSet<Integer> temp = new TreeSet<>();
        temp.addAll(new_suspicious_nodes);
        if(!isSame(temp_suspicious,temp)) {
            temp_suspicious.clear();
            temp_suspicious.addAll(new_suspicious_nodes);
            detected_community = extend_community(detected_community, new_suspicious_nodes, temp_suspicious, node_influences_id);
        }
        return detected_community;
    }

    public TreeSet<Integer> getSuspiciousNodes(int node_id, TreeSet<Integer> detected_community,
                                               Queue<Integer> suspicious_ndoes, Queue<Integer> new_suspicious_nodes) {
        TreeSet<Integer> neighbor_suspicious_nodes = new TreeSet<>();
        TreeSet<Integer> neighbors = getNeighbors(node_id, adjacencyTable);
        for(int neighbor_id : neighbors) {
            if(!detected_community.contains(neighbor_id) && !suspicious_ndoes.contains(neighbor_id)
                    && !new_suspicious_nodes.contains(neighbor_id)) {
                neighbor_suspicious_nodes.add(neighbor_id);
            }
        }
        return neighbor_suspicious_nodes;
    }

    public TreeSet<Integer> getNeighbors(int id, ArrayList<ArrayList<Integer>> adjacencyTables) {
        TreeSet<Integer> neighbours = new TreeSet<>();
        neighbours.addAll(adjacencyTables.get(id));
        neighbours.remove((Integer)id);
        return neighbours;
    }

    public TreeSet<Integer> initial_community(int seed_id, TreeSet<Integer> detected_community,
                                              TreeSet<Integer> temp_detected_community, ArrayList<NodeInfluence> node_influences_id) {
        Queue<Integer> detected_community_queue = new LinkedList<>();
        detected_community_queue.addAll(detected_community);
        detected_community_queue.remove((Integer)seed_id);
        while(!detected_community_queue.isEmpty()) {
            int node_id = detected_community_queue.poll();
            detected_community.remove((Integer)node_id);
            boolean contain = isContained(node_id, detected_community, node_influences_id);
            if (contain) {
                detected_community.add(node_id);
            }
        }
        if(!isSame(temp_detected_community,detected_community)) {
            temp_detected_community.clear();
            temp_detected_community.addAll(detected_community);
            detected_community = initial_community(seed_id, detected_community, temp_detected_community, node_influences_id);
        }
        return detected_community;
    }

    public boolean isSame(TreeSet<Integer> tree1, TreeSet<Integer> tree2) {
        boolean isSame = true;
        if(tree1.size() != tree2.size()) {
            return isSame = false;
        }
        for(int node : tree1) {
            if(!tree2.contains(node)) {
                isSame = false;
                break;
            }
        }
        return isSame;
    }

    public boolean isContained(int node_id, TreeSet<Integer> detected_community,
                               ArrayList<NodeInfluence> node_influences_id) {
        boolean isContained = true;
        TreeSet<Integer> neighbors = getNeighbors(node_id, adjacencyTable);
        ArrayList<Integer> neighbors_in = new ArrayList<>();
        TreeSet<Integer> neighbors_out = new TreeSet<>();
        int neighbors_conectivity_in = 0;
        int neighbors_conectivity_out = 0;
        TreeSet<Integer> connectivity_out = new TreeSet();
        for(int neighbor : neighbors) {
            if(detected_community.contains(neighbor)) {
                neighbors_in.add(neighbor);
            } else {
                neighbors_out.add(neighbor);
            }
        }

        /******    based on node influence    ******/
//        neighbors_conectivity_in = neighbors_in.size();
        for(int node_in : neighbors_in) {
            neighbors_conectivity_in += node_influences_id.get(node_in).influence;
        }

        /******    20200110 modify    ******/
        /*Stack<Integer> option_list = new Stack<>();
        ArrayList<TreeSet<Integer>> connectivity_lists = new ArrayList<>();
        TreeSet<Integer> connectivity_list = new TreeSet<>();
        for(int neighbor_id : neighbors_out) {
            option_list.push(neighbor_id);
        }
        while(!option_list.isEmpty()) {
            int node = option_list.pop();
            connectivity_list.add(node);
            TreeSet<Integer> n_neighbors = getNeighbors(node, adjacencyTable);
            int tag = 0;
            for(int n_neighbor_id : n_neighbors) {
                if(neighbors_out.contains(n_neighbor_id) && connectivity_list.add(n_neighbor_id)) {
                    option_list.remove((Integer)n_neighbor_id);
                    option_list.push(n_neighbor_id);
                    tag = 1;
                    break;
                }
            }
            if(tag == 0) {
                TreeSet<Integer> temp_list = new TreeSet<>();
                temp_list.addAll(connectivity_list);
                connectivity_lists.add(temp_list);
                connectivity_list.clear();
            }
        }*/


        if("yes".equals(this.potential_community)) {
            /******    20200110 modify    ******/
//            Queue<Integer> node_out_neighbors = new LinkedList<>();
            ArrayList<TreeSet<Integer>> connected_components = new ArrayList<>();
            connected_components = getConnectedComponents(neighbors_out);
            /*while(!neighbors_out.isEmpty()) {
                TreeSet<Integer> component = new TreeSet<>();
                TreeSet<Integer> queue = new TreeSet<Integer>();
                queue.add(neighbors_out.pollFirst());
                while (!queue.isEmpty())
                {
                    int node_out_neighbor_id = queue.pollFirst();
                    component.add(node_out_neighbor_id);
                    TreeSet<Integer> node_out_neighbor_neighbors = new TreeSet<>();
                    node_out_neighbor_neighbors.addAll(getNeighbors(node_out_neighbor_id, adjacencyTable));
                    TreeSet<Integer> insection = get_insection(node_out_neighbor_neighbors, neighbors_out);
                    queue.addAll(insection);
                    neighbors_out.removeAll(insection);
                }
                connected_components.add(component);
            }*/


            /******    based on node influence    ******/
//        for(TreeSet<Integer> tempList : connectivity_lists) {
//            int temp = tempList.size();
//            if(temp > neighbors_conectivity_out) {
//                neighbors_conectivity_out = temp;
//            }
//        }
            for(TreeSet<Integer> tempList : connected_components) {
                int temp_out_influences = 0;
                for(int node_out : tempList) {
                    temp_out_influences += node_influences_id.get(node_out).influence;
                }
                if(temp_out_influences > neighbors_conectivity_out) {
                    neighbors_conectivity_out = temp_out_influences;
                }
            }
        } else {
            for(int node_out : neighbors_out) {
                neighbors_conectivity_out += node_influences_id.get(node_out).influence;
            }
        }


        if(neighbors_conectivity_in < neighbors_conectivity_out) {
            isContained = false;
        }
        return isContained;
    }

    ArrayList<TreeSet<Integer>> getConnectedComponents(TreeSet<Integer> nodes) {
        ArrayList<TreeSet<Integer>> connected_components = new ArrayList<>();
        TreeSet<Integer> nodes_list = new TreeSet<>();
        nodes_list.addAll(nodes);
        while(!nodes_list.isEmpty()) {
            TreeSet<Integer> component = new TreeSet<>();
            TreeSet<Integer> queue = new TreeSet<Integer>();
            queue.add(nodes_list.pollFirst());
            while (!queue.isEmpty())
            {
                int node_neighbor_id = queue.pollFirst();
                component.add(node_neighbor_id);
                TreeSet<Integer> node_neighbor_neighbors = new TreeSet<>();
                node_neighbor_neighbors.addAll(getNeighbors(node_neighbor_id, adjacencyTable));
                TreeSet<Integer> insection = get_insection(node_neighbor_neighbors, nodes_list);
                queue.addAll(insection);
                nodes_list.removeAll(insection);
            }
            connected_components.add(component);
        }
        return connected_components;
    }

    public TreeSet<Integer> get_insection(TreeSet<Integer> setA,
                                          TreeSet<Integer> setB)
    {
        TreeSet<Integer> insection = new TreeSet<>();
        for (int node : setA) {
            if (setB.contains(node))
                insection.add(node);
        }
        return insection;
    }

    public TreeSet<Integer> get_community_neighbors(TreeSet<Integer> detected_community) {
        TreeSet<Integer> community_neighbors = new TreeSet<>();
        for(int node : detected_community) {
            TreeSet<Integer> neighbors = getNeighbors(node, adjacencyTable);
            for(int neighbor : neighbors) {
                if(!detected_community.contains(neighbor)) {
                    community_neighbors.add(neighbor);
                }
            }
        }
        return community_neighbors;
    }

    //20200124 add candidate seed node algorithm   20200624 change basic method to potential community
    /* (Tested) Find replacement node of the seed node */
    public ClusterCore find_replacement_seed_node_for_initial_seed_node(int seed_id, ArrayList<NodeInfluence> node_influences_id) {
        int seed_id_new = seed_id;
        ClusterCore candidateCore = new ClusterCore();
        do {
            candidateCore = find_replacement_seed_node_for_initial_seed_node_from_neighbors(seed_id_new,
                    node_influences_id);
            int candidate_seed = candidateCore.getNode();
            if (candidate_seed == -1) {
                candidateCore.setNode(seed_id_new);
                break;
            } else {
                seed_id_new = candidate_seed;
                candidateCore.setNode(seed_id_new);
            }
        } while (true);
        return candidateCore;
    }

    //20200624 change the basic method to potential community
    /* (Tested) find the replacement seed node forthe initial_seed_node */
    public ClusterCore find_replacement_seed_node_for_initial_seed_node_from_neighbors(
            int node_id, ArrayList<NodeInfluence> node_influences_id) {
        int seedInfluence = 0;
        TreeSet<Integer> seedCluster = new TreeSet<>();
        ClusterCore seedClusterCore = new ClusterCore();
        seedClusterCore = getClusterCore(node_id, node_influences_id);
        seedClusterCore.setNode(-1);
        seedInfluence = seedClusterCore.getInfluences();
        seedCluster = seedClusterCore.getList();
        TreeSet<Integer> neighbors_of_seed = getNeighbors(node_id, adjacencyTable);
        for (Iterator<Integer> iter = neighbors_of_seed.iterator(); iter
                .hasNext();) {
            Integer neighbor = iter.next();
            int neighbor_influence = 0;
            TreeSet<Integer> neighbor_component = new TreeSet<>();
            ClusterCore neighborCore = new ClusterCore();
            neighborCore = getClusterCore(neighbor, node_influences_id);
            neighbor_influence = neighborCore.getInfluences();
            neighbor_component = neighborCore.getList();
            if (neighbor_influence > seedInfluence && neighborCore.getList().contains(node_id)) {
                seedClusterCore.setNode(neighbor);
                seedClusterCore.setInfluences(neighbor_influence);
                seedClusterCore.setList(neighbor_component);
            }
        }
        return seedClusterCore;
    }


    public ClusterCore getClusterCore(int node, ArrayList<NodeInfluence> node_influences_id) {
        TreeSet<Integer> clusterCore = new TreeSet<>();
        int influences = 0;
        ClusterCore cCore = new ClusterCore();
        TreeSet<Integer> nodes_list = new TreeSet<>();
        clusterCore.add(node);
        TreeSet<Integer> temp = new TreeSet<>();
        nodes_list.addAll(getNeighbors(node,adjacencyTable));
        int seed_influence = node_influences_id.get(node).influence;
        for(int neighbor : nodes_list) {
            int neighbor_influence = node_influences_id.get(neighbor).influence;
            if(seed_influence > neighbor_influence) {
                clusterCore.add(neighbor);
            }
        }
        clusterCore = initial_community(node, clusterCore, temp, node_influences_id);
        for(int cluster_node : clusterCore) {
            influences += node_influences_id.get(cluster_node).influence;
        }
        cCore.setNode(node);
        cCore.setInfluences(influences);
        cCore.setList(clusterCore);
        return cCore;
    }
}