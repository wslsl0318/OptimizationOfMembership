package com.wangshenglong.THIRD;

import java.util.ArrayList;

public class CalculateNodeKCore {
    public int n;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public CalculateNodeKCore(int n, ArrayList<ArrayList<Integer>> adjacencyTable) {
        this.n = n;
        this.adjacencyTable = adjacencyTable;
    }
    public ArrayList<NodeKCoreValue> calculate_node_KCore_for_all_nodes()
    {
        ArrayList<NodeKCoreValue> NodesKCoreValue = new ArrayList<NodeKCoreValue>();
        ArrayList<NodeKCoreTemp> NodesKCoreTemp = new ArrayList<>();
        for(int i=0; i<this.n; i++) {
            ArrayList<Integer> node_adjacent = new ArrayList<>();
            node_adjacent.addAll(adjacencyTable.get(i));
            int degree = node_adjacent.size()-1;
            int node_id = node_adjacent.get(0);
            NodeKCoreTemp ncTemp = new NodeKCoreTemp();
            ncTemp.setCore_value(0);
            ncTemp.setDegree_temp(degree);
            ncTemp.setId(node_id);
            node_adjacent.remove(0);
            ncTemp.setList(node_adjacent);
            NodesKCoreTemp.add(ncTemp);
        }
//        int KCore_value = 0;
        int degree = 1;
        Boolean tag1 = false;
        Boolean tag2 = false;

        do {
            tag1 = false;
            tag2 = false;
//            KCore_value++;
            ArrayList<Integer> removeList = new ArrayList<>();
            for (NodeKCoreTemp nkct : NodesKCoreTemp) {
                int degreeTemp = nkct.getDegree_temp();
                if (degreeTemp != 0 && degreeTemp <= degree) {
//                    System.out.println(" KCore: "+degree+" i: "+nkct.id);
                    nkct.setCore_value(degree);
                    nkct.setDegree_temp(0);
                    ArrayList<Integer> node_neighbors = nkct.getList();
                    removeList.add(nkct.getId());
                    tag1 = true;
                } else if(degreeTemp > degree) {
                    tag2 = true;
                }
            }
            if(tag1 == false) {
                degree++;
            } else if(tag1 == true) {
                for(int remove_id : removeList) {
                    for(int m=0; m<NodesKCoreTemp.size(); m++) {
                        int tempNodeId = NodesKCoreTemp.get(m).id;
                        if(tempNodeId == remove_id) {
                            for(int n=0; n<NodesKCoreTemp.get(m).getList().size(); n++) {
                                int neighbor = NodesKCoreTemp.get(m).getList().get(n);
                                for(int l=0; l<NodesKCoreTemp.size(); l++) {
                                    int neighbor_neighbor = NodesKCoreTemp.get(l).id;
                                    if(neighbor_neighbor == neighbor) {
                                        for(int o=0; o<NodesKCoreTemp.get(l).getList().size(); o++) {
                                            int neighbor_neighbor_id = NodesKCoreTemp.get(l).getList().get(o);
                                            if(neighbor_neighbor_id == remove_id) {
                                                NodesKCoreTemp.get(l).getList().remove(o);
                                                if(NodesKCoreTemp.get(l).getDegree_temp() != 0) {
                                                    NodesKCoreTemp.get(l).setDegree_temp(NodesKCoreTemp.get(l).getList().size());
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    /*for(int neighbor : NodesKCoreTemp.get(remove_id).getList()) {
                        for(int i=0; i<NodesKCoreTemp.get(neighbor).getList().size(); i++) {
                            int neighbor_neighbor = NodesKCoreTemp.get(neighbor).getList().get(i);
                            if(neighbor_neighbor == remove_id) {
                                *//*if(23==neighbor) {
                                    System.out.println("BUG2");
                                }*//*
                                NodesKCoreTemp.get(neighbor).getList().remove(i);
                                if(NodesKCoreTemp.get(neighbor).getDegree_temp() != 0) {
                                    NodesKCoreTemp.get(neighbor).setDegree_temp(NodesKCoreTemp.get(neighbor).getList().size());
                                }
                            }
                        }
                    }*/
                }
            }
        } while(tag1 || tag2);
        for(NodeKCoreTemp nkct : NodesKCoreTemp) {
            NodeKCoreValue nk = new NodeKCoreValue();
            nk.setCore_value(nkct.getCore_value());
            nk.setId(nkct.getId());
            NodesKCoreValue.add(nk);
        }





        /*ArrayList<ArrayList<Integer>> adjacencyTableTemp = new ArrayList<>();
        adjacencyTableTemp.addAll(adjacencyTable);
        Boolean tag1 = false;
        Boolean tag2 = false;
        int degree = 1;
        do{
            tag1 = false;
            tag2 = false;
            KCore_value++;
            ArrayList<ArrayList<Integer>> remove_node_lists = new ArrayList<>();
            ArrayList<Integer> remove_list = new ArrayList<>();
            int adjacencyTableTempSize = adjacencyTableTemp.size();
            for (int i = 0; i < adjacencyTableTempSize; i++) {
                if(KCore_value==3 && i == 9) {
                    System.out.println("KCore_value: "+KCore_value+" degree: "+degree+" i: "+i);
                }
                System.out.println("KCore_value: "+KCore_value+" degree: "+degree+" i: "+i);
                int node_neighbor_size = adjacencyTableTemp.get(i).size()-1;
                if (node_neighbor_size <= degree) {
                    remove_list.add(i);
                    for(int m=1; m<adjacencyTableTemp.get(i).size(); m++) {
                        int node_neighbor = adjacencyTableTemp.get(i).get(m);
                        for(int n=1;n<adjacencyTableTemp.get(node_neighbor).size(); n++) {
                            int neighbor_neighbor = adjacencyTableTemp.get(node_neighbor).get(n);
                            if(i == neighbor_neighbor) {
                                remove_list.add(n);
                            }
                        }
                        System.out.println("KCore_value: "+KCore_value+" degree: "+degree+" i: "+i+" neighbor: "+node_neighbor);
                    }
                    NodeKCoreValue node_KCore = new NodeKCoreValue();
                    node_KCore.setCore_value(KCore_value);
                    node_KCore.setId(i);
                    NodesKCoreValue.add(node_KCore);
                    tag1 = true;
                    remove_node_lists.add(remove_list);
                } else if(adjacencyTableTemp.get(i).size() > degree) {
                    tag2 = true;
                }
            }
            if(tag1 == true) {
                int size = remove_node_lists.size();
                for(int n=size; n>=0; n--) {
                    ArrayList<Integer> list = remove_node_lists.get(n);
                    for (int m = 1; m <list.size(); m++) {
                        int remove_node_id = remove_node_lists.get(n).get(0);
                        int remove_node_neighbor = remove_node_lists.get(n).get(m);
                        for(int l=1; l<adjacencyTableTemp.get(remove_node_neighbor).size(); l++) {
                            if(adjacencyTableTemp.get(n).get(l) == )
                        }
                        adjacencyTableTemp.get(node).remove
                        adjacencyTableTemp.remove(remove_node_id);
                    }
                }
            }
            if(tag1 == false && tag2 == true) {
                degree++;
            } else {
                degree = 1;
            }
        } while(tag1 || tag2);*/
//        System.out.println("END");
        return NodesKCoreValue;
    }
}
