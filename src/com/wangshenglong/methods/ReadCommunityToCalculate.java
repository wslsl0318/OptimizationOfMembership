package com.wangshenglong.methods;

import DataReaderDetectedNetwork.DataReaderDetectedNetwork;
import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;
import com.pulicoperations.methods.NMI;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

public class ReadCommunityToCalculate {
    public DataReaderRealWorldNetwork data_reader;
    public DataReaderDetectedNetwork detected_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public ArrayList<ArrayList<Integer>> detected_communities;
    public String file_path_result;
    public ArrayList<NodeDegree> nodes_degree;
    public int[] nodes_influence_list;
    public ReadCommunityToCalculate(String file_path_community,
                                             String file_path_table, String file_path_result)
    {
        this.data_reader = new DataReaderRealWorldNetwork(file_path_community,
                file_path_table);
        this.detected_reader = new DataReaderDetectedNetwork(file_path_result);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.detected_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
        this.nodes_degree = new ArrayList<NodeDegree>();
    }

    /* (Tested) initial n, m, nc, adjacency table, real communities */
    public void initial_paramaters() throws IOException
    {
        this.data_reader.read_realworld_network();
        this.detected_reader.read_detected_communities();
        this.n = this.data_reader.n;
        this.m = this.data_reader.m;
        this.nc = this.data_reader.nc;
        this.adjacencyTable.addAll(this.data_reader.adjacencyTable);
        this.data_reader.adjacencyTable.clear();
        this.real_communities.addAll(this.data_reader.real_communities);
        this.data_reader.real_communities.clear();
        this.detected_communities.addAll(this.detected_reader.detected_communities);
        this.detected_reader.detected_communities.clear();
        this.nodes_degree = getNodeDegree();
        getNodeInfluenceList();
    }

    public ArrayList<NodeDegree> getNodeDegree() {
        ArrayList<NodeDegree> nodeDegrees = new ArrayList<>();
        TreeSet<Integer> degree_number = new TreeSet<>();
        for(int i=0;i<this.n;i++) {
            NodeDegree nd = new NodeDegree();
            nd.setId(i);
            int degree = this.adjacencyTable.get(i).size() - 1;
            nd.setDegree(degree);
            if(!degree_number.contains(degree)) {
                degree_number.add(degree);
            }
            nodeDegrees.add(nd);
        }
        NodeDegree ndlast = new NodeDegree();
        ndlast = nodeDegrees.get(this.n-1);
        ndlast.setNeighbors(degree_number);
        return nodeDegrees;
    }

    public void getNodeInfluenceList() {
        TreeSet<Integer> nodeInfluenceLists = new TreeSet<>();
        NodeDegree ndlast = new NodeDegree();
        ndlast = nodes_degree.get(this.n-1);
        TreeSet<Integer> degree_list = ndlast.getNeighbors();
        int degree_max = degree_list.last();
        this.nodes_influence_list = new int[degree_max+1];
        int size = degree_list.size();
        double range = Math.ceil((double)size/10);
        int range_left = (int) size % 10;

        int tag1 = 10;
        int tag2 = 1;
        int tag3 = 1;
        for(int degree : degree_list) {
            nodes_influence_list[degree] = tag1;
            if(tag1 > range_left && range_left != 0) {
                if(tag2 % (range-1) == 0) {
                    tag2 = 0;
                    tag1--;
                }
                tag2++;
            } else {
                if(tag3 % range == 0) {
                    tag3 = 0;
                    tag1--;
                }
                tag3++;
            }
        }
    }

    public void caculate_nmi_f() {
        int seed_size = 0;
        double NMI_average = 0;
        /* The percentage of nodes that are correctly classified */
        double nodes_correctly_classified_percentage = 0;
        long costTime_average = 0;
        /* r, p, f */
        ArrayList<Double> rpf_average = new ArrayList<Double>();
        double r_average = 0;
        double p_average = 0;
        double f_average = 0;
        for(Iterator<ArrayList<Integer>> detectedIter = detected_communities.iterator();detectedIter.hasNext();) {
            seed_size++;
            int community_size = detected_communities.size();
//            System.out.println(seed_size + " / " + community_size);
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(detectedIter.next());
            int seed_id = community.get(0);
            ArrayList<Integer> restnodes = new ArrayList<>();
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = get_real_partitions_containing_seed(seed_id);
            community.remove(0);
            for(int i = 0; i < n; i++) {
                if(!community.contains(i)) {
                    restnodes.add(i);
                }
            }
            partitionF.add(community);
            partitionF.add(restnodes);

            double NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id)) {
                nodes_correctly_classified_percentage++;
            }
            double r_once_average = 0;
            double p_once_average = 0;
            double f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext(); ) {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double NMI_value = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                NMI_once_average += NMI_value;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r;
                double p;
                double f;
                if (insection_size == 0) {
                    r = 0;
                    p = 0;
                    f = 0;
                } else {
                    r = (double) insection_size / (double) partitionR_size;
                    p = (double) insection_size / (double) partitionF_size;
                    f = 2 * r * p / (r + p);
                }
                r_once_average += r;
                p_once_average += p;
                f_once_average += f;
            }
            NMI_once_average /= partitionRS.size();
            NMI_average += NMI_once_average;
            r_once_average /= partitionRS.size();
            p_once_average /= partitionRS.size();
            f_once_average /= partitionRS.size();
            r_average += r_once_average;
            p_average += p_once_average;
            f_average += f_once_average;
        }
        costTime_average /= seed_size;
        NMI_average /= seed_size;
        nodes_correctly_classified_percentage /= (double) seed_size;
        r_average /= seed_size;
        p_average /= seed_size;
        f_average /= seed_size;
        rpf_average.add(r_average);
        rpf_average.add(p_average);
        rpf_average.add(f_average);
        System.out.println("costTime_average:" + costTime_average);
        System.out.println("NMI_average:" + NMI_average);
        System.out.println("r_average:" + r_average);
        System.out.println("p_average:" + p_average);
        System.out.println("f_average:" + f_average);
        System.out.println("nodes_correctly_classified_percentage:" + nodes_correctly_classified_percentage);
    }

    /* (Tested) Get real partitions containing the seed node */
    public ArrayList<ArrayList<ArrayList<Integer>>> get_real_partitions_containing_seed(
            int seed_id)
    {
        ArrayList<ArrayList<Integer>> real_communities_containing_seed = new ArrayList<ArrayList<Integer>>();
        for (Iterator<ArrayList<Integer>> iter = real_communities.iterator(); iter
                .hasNext();)
        {
            ArrayList<Integer> real_community = iter.next();
            if (real_community.contains((Integer) seed_id))
            {
                real_communities_containing_seed.add(real_community);
            }
        }
        ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for (Iterator<ArrayList<Integer>> iter = real_communities_containing_seed
                .iterator(); iter.hasNext();)
        {
            ArrayList<Integer> real_community = iter.next();
            ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
            for (int i = 0; i < this.n; i++)
            {
                if (!real_community.contains(i))
                {
                    rest_nodes.add(i);
                }
            }
            ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
            partitionR.add(real_community);
            partitionR.add(rest_nodes);
            partitionRS.add(partitionR);
        }
        return partitionRS;
    }

    /* (Tested) Get insection size */
    public int get_insection_size(ArrayList<Integer> detected_community,
                                  ArrayList<Integer> real_community)
    {
        int insection_size = 0;
        for (Iterator<Integer> iter = detected_community.iterator(); iter
                .hasNext();)
        {
            Integer member = iter.next();
            if (real_community.contains(member))
            {
                insection_size++;
            }
        }
        return insection_size;
    }

    public ArrayList<Float> caculate_LCE() {
        ArrayList<Float> LCE_Valid = new ArrayList();
        int seed_size = 0;
        int[] LCE = new int[this.n];
        for(Iterator<ArrayList<Integer>> detectedIter = detected_communities.iterator();detectedIter.hasNext();) {
            seed_size++;
            int community_size = detected_communities.size();
//            System.out.println(seed_size + " / " + community_size);
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(detectedIter.next());
            int seed_id = community.get(0);
            community.remove(0);
            int size = community.size();
            int seed_position = 0;
            for(int i=0;i<size;i++) {
                if(seed_id == community.get(i)) {
                    seed_position = i;
                }
            }
            int[][] map = new int[size][size];
            for(int i=0;i<size;i++) {
                int nodeA = community.get(i);
                ArrayList<Integer> neighbors = this.adjacencyTable.get(nodeA);
                for(int j=0;j<size;j++) {
                    int nodeB = community.get(j);
                    if(nodeA == nodeB) {
                        map[i][j] = 0x3fffffff;
                    } else if(neighbors.contains(nodeB)) {
                        map[i][j] = 1;
                    } else {
                        map[i][j] = 0x3fffffff;
                    }
                }
            }
            for(int k=0;k<size;k++) {
                for(int i=0;i<size;i++) {
                    for(int j=0;j<size;j++) {
                        if (map[i][j]>map[i][k]+map[k][j]) {
                            int temp = map[i][j];
                            map[i][j]=map[i][k]+map[k][j];
                            if(map[i][j] > 0x3fffffff) {
                                map[i][j] = temp;
                            }
                        }
                    }
                }
            }

            int[] path_length = new int[size];
            for(int i=0;i<size;i++) {
                for(int j=0;j<size;j++) {
                    if(i != j) {
                        if(map[i][j] != 0x3fffffff) {
                            path_length[i] += map[i][j];
                        }
                    }
                }
            }
            for(int i=0;i<size;i++) {
                if(path_length[seed_position] > path_length[i]) {
                    LCE[seed_id] = 0;
                    /*int nodeA = community.get(seed_position);
                    int nodeB = community.get(i);
                    int degreeA = this.adjacencyTable.get(nodeA).size()-1;
                    int degreeB = this.adjacencyTable.get(nodeB).size()-1;
                    System.out.println("seed: "+nodeA+"degree= "+degreeA+" node: " + nodeB + "degree= "+degreeB);*/
                    break;
                }
                LCE[seed_id] = 1;
            }
        }
        double LCE_average = 0;
        for(int i:LCE) {
            LCE_average += i;
        }
        LCE_average /= seed_size;

        LCE_Valid.add((float)LCE_average);

//        System.out.println("LCE:" + LCE_average);

        String type = "Influence_sort"; //100 (100 per group); Influence_sort;
        if("100".equals(type)) {
            int valid_node = 0;
            int tag = 0;
            int tag2 = 1;
            for (int i : LCE) {
                valid_node += i;
                if (tag % 100 == 99) {
                    float ratio = 0;
                    ratio = (float) valid_node / 100;
                    LCE_Valid.add(ratio);
                    System.out.println(tag2 + ":  " + ratio);
                    valid_node = 0;
                    tag2++;
                }
                tag++;
            }
        } else if ("Influence_sort".equals(type)) {
            int[] invalidNodes = new int[10];
            int[] validNodes = new int[10];

            int j = 0;
            for (int i : LCE) {
                int degree = this.nodes_degree.get(j).getDegree();
                int influence_sort = nodes_influence_list[degree];
                if (i == 0) {
                    invalidNodes[influence_sort - 1] += 1;
                } else if (i == 1) {
                    validNodes[influence_sort - 1] += 1;
                }
                j++;
            }
            for (int i = 0; i < 10; i++) {
                float ratio = 0;
                float sum = validNodes[i] + invalidNodes[i];
                if (sum == 0) {
                    ratio = 0;
                } else {
                    ratio = (float) (validNodes[i] / sum);
                }
                LCE_Valid.add(ratio);
                System.out.println(i + ":   " + ratio);
            }
        }
        return LCE_Valid;
    }

    public double caculate_LCU() {
        int seed_size = 0;
        double LCU = this.n;
        ArrayList<ArrayList<Integer>> communities = new ArrayList<>();
        for(Iterator<ArrayList<Integer>> detectedIter = detected_communities.iterator();detectedIter.hasNext();) {
            seed_size++;
            int community_size = detected_communities.size();
//            System.out.println(seed_size + " / " + community_size);
            ArrayList<Integer> community = new ArrayList<>();
            community.addAll(detectedIter.next());
            int seed_id = community.get(0);
            community.remove(0);
            int size = community.size();
            communities.add(community);
        }
        ArrayList<Integer> invalid = new ArrayList();
        for(int i=0; i<this.n; i++) {
            for(int j=i+1; j<this.n; j++) {
                if(!invalid.contains(i) && !invalid.contains(j)) {
                    ArrayList<Integer> communityA = new ArrayList();
                    ArrayList<Integer> communityB = new ArrayList();
                    communityA.addAll(communities.get(i));
                    communityB.addAll(communities.get(j));
                    int sizeA = communityA.size();
                    int sizeB = communityB.size();


                    String type = "noncontain"; //1.|contain| A contains B is not allowed 2. |noncontain| else
                    String tag = "";

                    if ("contain".equals(type)) {
                        if (sizeA > sizeB) {
                            tag = "A";
                            for (int node : communityB) {
                                if (!communityA.contains(node)) {
                                    tag = "false";
                                    break;
                                }
                            }
                        } else {
                            tag = "B";
                            for (int node : communityA) {
                                if (!communityB.contains(node)) {
                                    tag = "false";
                                    break;
                                }
                            }
                        }
                        if ("A".equals(tag)) {
                            LCU -= 1;
                            invalid.add(j);
                        } else if ("B".equals(tag)) {
                            invalid.add(i);
                            LCU -= 1;
                            break;
                        }
                    } else if ("noncontain".equals(type)) {
                        if (sizeA == sizeB) {
                            tag = "true";
                            for (int node : communityB) {
                                if (!communityA.contains(node)) {
                                    tag = "false";
                                    break;
                                }
                            }
                        }
                        if ("true".equals(tag)) {
                            invalid.add(i);
                            LCU -= 1;
                            break;
                        }
                    }
                }
            }
        }
        LCU /= seed_size;

//        System.out.println("LCU:" + LCU);
        return LCU;
    }

    public static void showResults(ArrayList<MethodResult> list, String dataset_name) {
        DecimalFormat df = new DecimalFormat("0.00");
        String method_name = "";
        String LCE = "";
        String LCU = "";
        String validString = "";

        System.out.print("\\textit{" + dataset_name + "}" );
        for(MethodResult temp : list) {
            String validMethodString = "";
            method_name = method_name + "  &" + temp.getMethod_name();
            LCE = LCE + " &" + df.format(temp.getLCE());
            LCU = LCU + " &" + df.format(temp.getLCU());
            for(float valid : temp.getValid()) {
                validMethodString =  validMethodString + " &" + df.format(valid);
            }
            validMethodString = "\\textit{" + temp.getMethod_name() + "}" + validMethodString + "\\\\";
            validString = validString + validMethodString + "\n";
        }

        LCE = "&\\textit{LCE}" + LCE + "\\\\";
        LCU = "&\\textit{LCU}" + LCU + "\\\\";

        System.out.println(method_name);
        System.out.println(LCE);
        System.out.println(LCU);
        System.out.println(validString);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> dataset_names = new ArrayList<>();
        //karate;dolphin;football;krebsbook;dblp;amazon;youtube;lj
//        dataset_names.add("karate");
//        dataset_names.add("dolphin");
//        dataset_names.add("krebsbook");
//        dataset_names.add("football");
//        dataset_names.add("amazon");

//        dataset_names.add("dblp1");
//        dataset_names.add("dblp2");
//        dataset_names.add("dblp3");
//        dataset_names.add("dblp4");
//        dataset_names.add("dblp5");
//        dataset_names.add("dblp6");
//        dataset_names.add("dblp7");
//        dataset_names.add("dblp8");
//        dataset_names.add("dblp9");
//        dataset_names.add("dblp10");
//        dataset_names.add("dblp11");

        dataset_names.add("youtube1");
        dataset_names.add("youtube2");
        dataset_names.add("youtube3");
        dataset_names.add("youtube4");
        dataset_names.add("youtube5");
        dataset_names.add("youtube6");
        dataset_names.add("youtube7");
        dataset_names.add("youtube8");
        dataset_names.add("youtube9");
        dataset_names.add("youtube10");
        dataset_names.add("youtube11");
//        dataset_names.add("lj");
//        dataset_names.add("lastfm_asia");
        ArrayList<String> algorithm_names = new ArrayList<>();
        //chen;clauset;fifth;iskcore;lcd;ls;lwp;vi;isdegree;
        //seedOrientDegree;seedOrientKcore
//        algorithm_names.add("seedOrientDegree");
        algorithm_names.add("seedOrientKcore");

//        algorithm_names.add("fifth");

        algorithm_names.add("clauset");
        algorithm_names.add("lwp");
        algorithm_names.add("chen");
        algorithm_names.add("ls");
//        algorithm_names.add("vi");
//        algorithm_names.add("lcd");

        for(String dataset_name : dataset_names) {
            ArrayList<MethodResult> RealResults = new ArrayList<>();
            for(String algorithm_name : algorithm_names) {
//                System.out.println("dateset_name :  " + dataset_name);
//                System.out.println("algorithm_name :  " + algorithm_name);
                String file_path = "";
                if(dataset_name.length() > 4 && "dblp".equals(dataset_name.substring(0,4))) {
                    file_path = "F:/1papers/20190411 first/data set/" + dataset_name.substring(0,4) + "/" + dataset_name + "/";
                }
                else if(dataset_name.length() > 6 && "youtube".equals(dataset_name.substring(0,7))) {
                    file_path = "F:/1papers/20190411 first/data set/" + dataset_name.substring(0,7) + "/" + dataset_name + "/";
                } else {
                    file_path = "F:/1papers/20190411 first/data set/" + dataset_name + "/";
                }


                String network = "_network.txt";
                String community = "_community.txt";
                String table = "_table.txt";
                String result = "_result";
                String file_name = dataset_name;
                String file_path_network = file_path + file_name + network;
                String file_path_community = file_path + file_name + community;
                String file_path_table = file_path + file_name + table;
                String file_path_result = file_path + file_name + result + "(" + algorithm_name + ").txt";
                ReadCommunityToCalculate run_methords = new ReadCommunityToCalculate(
                        file_path_community, file_path_table, file_path_result);
                run_methords.initial_paramaters();
                MethodResult RealResult = new MethodResult();
//                run_methords.caculate_nmi_f();

                ArrayList<Float> LCE_Valid = new ArrayList();
                LCE_Valid.addAll(run_methords.caculate_LCE());
                double LCE = (double)LCE_Valid.get(0);
                float[] valid = new float[10];
                for(int i=1;i<LCE_Valid.size();i++) {
                    float valid_ratio = LCE_Valid.get(i);
                    valid[i-1] = valid_ratio;
                }
                RealResult.setLCE(LCE);
                RealResult.setValid(valid);

                RealResult.setLCU(run_methords.caculate_LCU());
                RealResult.setMethod_name(algorithm_name);
                RealResult.setDataset_name(dataset_name);
                RealResults.add(RealResult);
            }
            showResults(RealResults, dataset_name);
        }
    }
}
