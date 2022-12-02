package com.wangshenglong.methods;

import DataReaderRealWorldNetwork.DataReaderRealWorldNetwork;
import com.wangshenglong.THIRD.*;
import com.wangshenglong.chen.ChenAlgorithm;
import com.wangshenglong.clauset.ClausetAlgorithm;
import com.wangshenglong.fifth.FifthAlgorithm;
import com.wangshenglong.fifth.NodeImportance;
import com.wangshenglong.lcd.LCDAlgorithm;
import com.wangshenglong.ls.LSAlgorithm;
import com.wangshenglong.lwp.LWPAlgorithm;
import com.wangshenglong.vi.VIAlgorithm;
import com.pulicoperations.methods.NMI;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

public class ThirdRunMethodsOnRealWorldNetwork {
    public DataReaderRealWorldNetwork data_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public String file_path_result;
    public TreeSet<NodeDegree> nodes_degree;

    public ThirdRunMethodsOnRealWorldNetwork(String file_path_community,
                                             String file_path_table, String file_path_result) {
        this.data_reader = new DataReaderRealWorldNetwork(file_path_community,
                file_path_table);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
        this.nodes_degree = new TreeSet<NodeDegree>(new NodeDegree());
    }

    /* (Tested) initial n, m, nc, adjacency table, real communities */
    public void initial_paramaters() throws IOException {
        this.data_reader.read_realworld_network();
        this.n = this.data_reader.n;
        this.m = this.data_reader.m;
        this.nc = this.data_reader.nc;
        this.adjacencyTable.addAll(this.data_reader.adjacencyTable);
        this.data_reader.adjacencyTable.clear();
        this.real_communities.addAll(this.data_reader.real_communities);
        this.data_reader.real_communities.clear();
        System.out.println("node number: " + n);
        System.out.println("link number: " + m);
        System.out.println("community number: " + nc);
    }

    //20200120 fifth_algorithm needs
    /* (Tested) Get node neighbors */
    public TreeSet<Integer> get_node_neighbors(int node_id) {
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(adjacencyTable.get(node_id));
        neighbors.remove((Integer) node_id);
        return neighbors;
    }

    //20200120 fifth_algorithm needs
    /* (Tested) Calculate node importance */
    public int calculate_node_importance(int node_id) {
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
        return all_links;
    }

    //20200120 fifth_algorithm needs
    /* (Tested) calculate_node_importance_for_all_nodes */
    public ArrayList<NodeImportance> calculate_node_importance_for_all_nodes() {
        ArrayList<NodeImportance> nodes_importance = new ArrayList<NodeImportance>();
        for (int i = 0; i < this.n; i++) {
            NodeImportance node_importance = new NodeImportance(i,
                    calculate_node_importance(i));
            nodes_importance.add(node_importance);
        }
        return nodes_importance;
    }

    /* (Tested) Get insection size */
    public int get_insection_size(ArrayList<Integer> detected_community,
                                  ArrayList<Integer> real_community) {
        int insection_size = 0;
        for (Iterator<Integer> iter = detected_community.iterator(); iter
                .hasNext(); ) {
            Integer member = iter.next();
            if (real_community.contains(member)) {
                insection_size++;
            }
        }
        return insection_size;
    }

    /* (Tested) Get insection size */
    public int get_insection_size(TreeSet<Integer> community,
                                  TreeSet<Integer> detected_community) {
        int insection_size = 0;
        for (Iterator<Integer> iter = community.iterator(); iter.hasNext(); ) {
            Integer member = iter.next();
            if (detected_community.contains(member)) {
                insection_size++;
            }
        }
        return insection_size;
    }

    /* (Tested) Get real partitions containing the seed node */
    public ArrayList<ArrayList<ArrayList<Integer>>> get_real_partitions_containing_seed(
            int seed_id) {
        ArrayList<ArrayList<Integer>> real_communities_containing_seed = new ArrayList<ArrayList<Integer>>();
        for (Iterator<ArrayList<Integer>> iter = real_communities.iterator(); iter
                .hasNext(); ) {
            ArrayList<Integer> real_community = iter.next();
            if (real_community.contains((Integer) seed_id)) {
                real_communities_containing_seed.add(real_community);
            }
        }
        ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for (Iterator<ArrayList<Integer>> iter = real_communities_containing_seed
                .iterator(); iter.hasNext(); ) {
            ArrayList<Integer> real_community = iter.next();
            ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
            for (int i = 0; i < this.n; i++) {
                if (!real_community.contains(i)) {
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

    /*
     * (Tested) Get the nodes with max degree from real communities as seeds for
     * algorithms
     */
    public TreeSet<Integer> get_max_degree_nodes_from_real_communities() {
        TreeSet<Integer> seeds = new TreeSet<Integer>();
        for (Iterator<ArrayList<Integer>> iter_real_community = real_communities
                .iterator(); iter_real_community.hasNext(); ) {
            ArrayList<Integer> real_community = iter_real_community.next();
            int node_with_max_degree = -1;
            int max_degree = 0;
            for (Iterator<Integer> iter_member = real_community.iterator(); iter_member
                    .hasNext(); ) {
                Integer member = iter_member.next();
                if (adjacencyTable.get(member).size() > max_degree) {
                    max_degree = adjacencyTable.get(member).size();
                    node_with_max_degree = member;
                }
            }
            seeds.add(node_with_max_degree);
        }
        return seeds;
    }

    /* (Tested) write results into files */
    public void write_results_into_files(String algorithm_name, long cost_time, long costTime_average, double NMI_average,
                                         double nodes_correctly_classified_percentage, ArrayList<Double> RPF_average,
                                         ArrayList<SeedAndCommunity> detected_communities) throws IOException {
        String file = this.file_path_result + algorithm_name;
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        bw.write("cost_time: " + cost_time + "\r\n");
        bw.write("costTime_average: " + costTime_average + "\r\n");
        bw.write("NMI_average: " + NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + nodes_correctly_classified_percentage + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = RPF_average.iterator(); iter_rpf
                .hasNext(); ) {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        bw.write("detected_communities:" + "\r\n");
        for (Iterator<SeedAndCommunity> iter_community = detected_communities
                .iterator(); iter_community.hasNext(); ) {
            SeedAndCommunity community = iter_community.next();
            bw.write("seed: " + community.seed + "\t" + "community: ");
            for (Iterator<Integer> iter_member = community.community.iterator(); iter_member
                    .hasNext(); ) {
                Integer member = iter_member.next();
                bw.write(member + "\t");
            }
            bw.write("\r\n");
        }

        bw.write("\r\n");
        DecimalFormat df = new DecimalFormat("0.00");
        bw.write("NMI " + df.format(NMI_average) + "\r\n");
        bw.write("R " + df.format(RPF_average.get(0)) + "\r\n");
        bw.write("P " + df.format(RPF_average.get(1)) + "\r\n");
        bw.write("F-score " + df.format(RPF_average.get(2)) + "\r\n");
        bw.write("time cost(ms) " + cost_time + "\r\n");
        bw.write("time cost average(ms) " + costTime_average + "\r\n");
        bw.write("NCR " + df.format(nodes_correctly_classified_percentage) + "\r\n");
        bw.write(df.format(NMI_average) + "\r\n");
        bw.write(df.format(RPF_average.get(0)) + "\r\n");
        bw.write(df.format(RPF_average.get(1)) + "\r\n");
        bw.write(df.format(RPF_average.get(2)) + "\r\n");
        bw.write(cost_time + "\r\n");
        bw.write(costTime_average + "\r\n");
        bw.write(df.format(nodes_correctly_classified_percentage) + "\r\n");

        bw.close();
    }

    public void run_methords_on_realworld_network(ArrayList<String> algorithm_names, String run_type) throws IOException {
        /*****************************************************************************************/
        initial_paramaters();
        ArrayList<NodeImportance> nodes_importance = calculate_node_importance_for_all_nodes();
        CalculateNodeCentrality cnc = new CalculateNodeCentrality(n, adjacencyTable, "node_mass");//node_mass;node_degree;degrees;
        ArrayList<NodeCentrality> nodes_centrality = cnc.calculate_node_centrality_for_all_nodes();
        CalculateNodeKCore cnkc = new CalculateNodeKCore(n, adjacencyTable);
        ArrayList<NodeKCoreValue> nodes_KCore = cnkc.calculate_node_KCore_for_all_nodes();

        /************      add method      ************/
        ClausetAlgorithm clauset = new ClausetAlgorithm(n, m, adjacencyTable);
        LWPAlgorithm lwp = new LWPAlgorithm(n, m, adjacencyTable);
        ChenAlgorithm chen = new ChenAlgorithm(n, m, adjacencyTable);
        LSAlgorithm ls = new LSAlgorithm(n, m, adjacencyTable);
        LCDAlgorithm lcd = new LCDAlgorithm(n, m, adjacencyTable);
        VIAlgorithm vi = new VIAlgorithm(n, m, adjacencyTable);
        FifthAlgorithm fifth = new FifthAlgorithm(n, m, adjacencyTable, nodes_importance);
        //Jaccard;Salton;First;Ding18;Ding20;Access21;NS22;CoNeighbors;RAIndex;Ding19;SecLevel;LocalKCore;FirstKCore;Density;One;Two;Three;
        ThirdAlgorithm thirdFirstOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "First", "old");
        ThirdAlgorithm thirdFirstNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "First", "new");
        ThirdAlgorithm thirdJaccardOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Jaccard", "old");
        ThirdAlgorithm thirdJaccardNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Jaccard", "new");
        ThirdAlgorithm thirdSaltonOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Salton", "old");
        ThirdAlgorithm thirdSaltonNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Salton", "new");
        ThirdAlgorithm thirdDing18Old = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Ding18", "old");
        ThirdAlgorithm thirdDing18New = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Ding18", "new");
        ThirdAlgorithm thirdDing20Old = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Ding20", "old");
        ThirdAlgorithm thirdDing20New = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Ding20", "new");
        ThirdAlgorithm thirdAccess21Old = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Access21", "old");
        ThirdAlgorithm thirdAccess21New = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Access21", "new");
        ThirdAlgorithm thirdNS22Old = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "NS22", "old");
        ThirdAlgorithm thirdNS22New = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "NS22", "new");
        ThirdAlgorithm thirdCoNeighborsOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "CoNeighbors", "old");
        ThirdAlgorithm thirdCoNeighborsNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "CoNeighbors", "new");
        ThirdAlgorithm thirdRAIndexOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "RAIndex", "old");
        ThirdAlgorithm thirdRAIndexNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "RAIndex", "new");
        ThirdAlgorithm thirdDing19Old = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Ding19", "old");
        ThirdAlgorithm thirdDing19New = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Ding19", "new");
        ThirdAlgorithm thirdSecLevelOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "SecLevel", "old");
        ThirdAlgorithm thirdSecLevelNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "SecLevel", "new");
        ThirdAlgorithm thirdLocalKCoreOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "LocalKCore", "old");
        ThirdAlgorithm thirdLocalKCoreNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "LocalKCore", "new");
        ThirdAlgorithm thirdFirstKCoreOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "FirstKCore", "old");
        ThirdAlgorithm thirdFirstKCoreNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "FirstKCore", "new");
        ThirdAlgorithm thirdOneOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "One", "old");
        ThirdAlgorithm thirdOneNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "One", "new");
        ThirdAlgorithm thirdTwoOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Two", "old");
        ThirdAlgorithm thirdTwoNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Two", "new");
        ThirdAlgorithm thirdThreeOld = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Three", "old");
        ThirdAlgorithm thirdThreeNew = new ThirdAlgorithm(n, m, adjacencyTable, nodes_centrality, nodes_KCore, "Three", "new");


        //run on core_node or all nodes
        TreeSet<Integer> seeds = new TreeSet<>();
        int seed_size = 0;
        int count = 0;
        if ("all_nodes".equals(run_type)) {
            seed_size = this.n;
            for (int i = 0; i < seed_size; i++) {
                seeds.add(i);
            }
        } else if ("core_node".equals(run_type)) {
            seeds = get_max_degree_nodes_from_real_communities();
            seed_size = seeds.size();
        } else if ("random_node".equals(run_type)) {
            Random r = new Random();
            for (int i = 0; i < 20; i++) {
                int random = r.nextInt(this.n);
                System.out.println(random);
                seeds.add(random);
            }
            seed_size = seeds.size();
        }


        for (String method_name : algorithm_names) {
            String algorithm_name = "(" + method_name + ").txt";
            double NMI_average = 0;
            /* The percentage of nodes that are correctly classified */
            double nodes_correctly_classified_percentage = 0;
            //20221016 Change average_time into all_time.
            long costTime_average = 0;
            long cost_time = 0;
            /* r, p, f */
            ArrayList<Double> rpf_average = new ArrayList<Double>();
            double r_average = 0;
            double p_average = 0;
            double f_average = 0;
            /* detected distinct communities with out sub set */
            ArrayList<SeedAndCommunity> detected_communities = new ArrayList<SeedAndCommunity>();


            count = seed_size;
            for (int seed_id : seeds) {
                count--;
                System.out.println(method_name + " left node sizes: " + count);
                System.out.println(method_name + " seed_id: " + seed_id);
                long startTime_once = 0;
                long endTime_once = 0;
                ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
                ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
                ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = get_real_partitions_containing_seed(seed_id);

                startTime_once = System.currentTimeMillis();
                /************      add method      ************/
                switch (method_name) {
                    case "clauset":
                        partitionF = clauset.run_clauset(seed_id);
                        break;
                    case "chen":
                        partitionF = chen.run_chen(seed_id);
                        break;
                    case "lwp":
                        partitionF = lwp.run_lwp(seed_id);
                        break;
                    case "ls":
                        partitionF = ls.run_ls(seed_id);
                        break;
                    case "vi":
                        partitionF = vi.run_vi(seed_id);
                        break;
                    case "fifth":
                        partitionF = fifth.run_fifth(seed_id);
                        break;
                    case "thirdFirstOld":
                        partitionF = thirdFirstOld.run_third(seed_id);
                        break;
                    case "thirdFirstNew":
                        partitionF = thirdFirstNew.run_third(seed_id);
                        break;
                    case "thirdJaccardOld":
                        partitionF = thirdJaccardOld.run_third(seed_id);
                        break;
                    case "thirdJaccardNew":
                        partitionF = thirdJaccardNew.run_third(seed_id);
                        break;
                    case "thirdSaltonNew":
                        partitionF = thirdSaltonNew.run_third(seed_id);
                        break;
                    case "thirdSaltonOld":
                        partitionF = thirdSaltonOld.run_third(seed_id);
                        break;
                    case "thirdDing18New":
                        partitionF = thirdDing18New.run_third(seed_id);
                        break;
                    case "thirdDing18Old":
                        partitionF = thirdDing18Old.run_third(seed_id);
                        break;
                    case "thirdDing20New":
                        partitionF = thirdDing20New.run_third(seed_id);
                        break;
                    case "thirdDing20Old":
                        partitionF = thirdDing20Old.run_third(seed_id);
                        break;
                    case "thirdAccess21New":
                        partitionF = thirdAccess21New.run_third(seed_id);
                        break;
                    case "thirdAccess21Old":
                        partitionF = thirdAccess21Old.run_third(seed_id);
                        break;
                    case "thirdNS22Old":
                        partitionF = thirdNS22Old.run_third(seed_id);
                        break;
                    case "thirdNS22New":
                        partitionF = thirdNS22New.run_third(seed_id);
                        break;
                    case "thirdCoNeighborsOld":
                        partitionF = thirdCoNeighborsOld.run_third(seed_id);
                        break;
                    case "thirdCoNeighborsNew":
                        partitionF = thirdCoNeighborsNew.run_third(seed_id);
                        break;
                    case "thirdRAIndexOld":
                        partitionF = thirdRAIndexOld.run_third(seed_id);
                        break;
                    case "thirdRAIndexNew":
                        partitionF = thirdRAIndexNew.run_third(seed_id);
                        break;
                    case "thirdDing19Old":
                        partitionF = thirdDing19Old.run_third(seed_id);
                        break;
                    case "thirdDing19New":
                        partitionF = thirdDing19New.run_third(seed_id);
                        break;
                    case "thirdSecLevelOld":
                        partitionF = thirdSecLevelOld.run_third(seed_id);
                        break;
                    case "thirdSecLevelNew":
                        partitionF = thirdSecLevelNew.run_third(seed_id);
                        break;
                    case "thirdLocalKCoreOld":
                        partitionF = thirdLocalKCoreOld.run_third(seed_id);
                        break;
                    case "thirdLocalKCoreNew":
                        partitionF = thirdLocalKCoreNew.run_third(seed_id);
                        break;
                    case "thirdFirstKCoreOld":
                        partitionF = thirdFirstKCoreOld.run_third(seed_id);
                        break;
                    case "thirdFirstKCoreNew":
                        partitionF = thirdFirstKCoreNew.run_third(seed_id);
                        break;
                    case "thirdOneOld":
                        partitionF = thirdOneOld.run_third(seed_id);
                        break;
                    case "thirdOneNew":
                        partitionF = thirdOneNew.run_third(seed_id);
                        break;
                    case "thirdTwoOld":
                        partitionF = thirdTwoOld.run_third(seed_id);
                        break;
                    case "thirdTwoNew":
                        partitionF = thirdTwoNew.run_third(seed_id);
                        break;
                    case "thirdThreeOld":
                        partitionF = thirdThreeOld.run_third(seed_id);
                        break;
                    case "thirdThreeNew":
                        partitionF = thirdThreeNew.run_third(seed_id);
                        break;
                }
                if ("lcd".equals(method_name)) {
                    /* lcd */
                    partitionFS = lcd.run_lcd(seed_id);
                    endTime_once = System.currentTimeMillis();
                    long costTime_once = endTime_once - startTime_once;
                    cost_time += costTime_once;
                    double NMI_once_average = 0;
                    boolean is_partitionFS_contain_seed = false;
                    double r_once_average = 0;
                    double p_once_average = 0;
                    double f_once_average = 0;
                    for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                            .iterator(); iter_partitionR.hasNext(); ) {
                        ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                                .next();
                        double NMI_once_average_one = 0;
                        double r_once_average_one = 0;
                        double p_once_average_one = 0;
                        double f_once_average_one = 0;
                        for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF = partitionFS
                                .iterator(); iter_partitionF.hasNext(); ) {
                            partitionF = iter_partitionF.next();
                            SeedAndCommunity seed_community = new SeedAndCommunity(
                                    seed_id, partitionF.get(0));
                            detected_communities.add(seed_community);
                            if (partitionF.get(0).contains(seed_id)) {
                                is_partitionFS_contain_seed = true;
                            }
                            double NMI_value = NMI.NMIPartitionUnoverLap(partitionF,
                                    partitionR, this.n);
                            NMI_once_average_one += NMI_value;
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
                            r_once_average_one += r;
                            p_once_average_one += p;
                            f_once_average_one += f;
                        }
                        NMI_once_average_one /= partitionFS.size();
                        NMI_once_average += NMI_once_average_one;
                        r_once_average_one /= partitionFS.size();
                        p_once_average_one /= partitionFS.size();
                        f_once_average_one /= partitionFS.size();
                        r_once_average += r_once_average_one;
                        p_once_average += p_once_average_one;
                        f_once_average += f_once_average_one;
                    }
                    NMI_once_average /= partitionRS.size();
                    NMI_average += NMI_once_average;
                    if (is_partitionFS_contain_seed) {
                        nodes_correctly_classified_percentage++;
                    }
                    r_once_average /= partitionRS.size();
                    p_once_average /= partitionRS.size();
                    f_once_average /= partitionRS.size();
                    r_average += r_once_average;
                    p_average += p_once_average;
                    f_average += f_once_average;
                } else {
                    SeedAndCommunity seed_community = new SeedAndCommunity(
                            seed_id, partitionF.get(0));
                    detected_communities.add(seed_community);
                    endTime_once = System.currentTimeMillis();
                    long costTime_once = endTime_once - startTime_once;
                    cost_time += costTime_once;
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
            }
            //20221016 Change average_time into all_time.
            costTime_average = cost_time / seed_size;
            NMI_average /= seed_size;
            nodes_correctly_classified_percentage /= (double) seed_size;
            r_average /= seed_size;
            p_average /= seed_size;
            f_average /= seed_size;
            rpf_average.add(r_average);
            rpf_average.add(p_average);
            rpf_average.add(f_average);
            //20221016 Change average_time into all_time.
//        write_results_into_files(algorithm_name, cost_time, costTime_average, NMI_average, nodes_correctly_classified_percentage, rpf_average, detected_communities);
            write_results_into_files(algorithm_name, cost_time, costTime_average, NMI_average, nodes_correctly_classified_percentage, rpf_average, detected_communities);
            System.out.println(method_name + ":");
            System.out.println("cost_time:" + cost_time);
            System.out.println("costTime_average:" + costTime_average);
            System.out.println("NMI_average:" + NMI_average);
            System.out.println("r_average:" + r_average);
            System.out.println("p_average:" + p_average);
            System.out.println("f_average:" + f_average);
            System.out.println("nodes_correctly_classified_percentage:" + nodes_correctly_classified_percentage);
        }
    }

    public static void main(String[] args) throws IOException {
        String dateset_name = "musae_DE";
        //karate;dolphin;football;krebsbook;
        //musae_DE;musae_EN;musae_ES;musae_facebook;musae_FR;musae_github;musae_PT;musae_RU;
        //Combined-APMS;Ito-core;LC-multiple;Uetz-screen;Y2H-union;CCSB-YI1;
        //dblp;amazon;youtube;lj;
        //lastfm_asia;

        String name = dateset_name;
        String file_path = "D:/dataset/data set/";
        // D:/dataset/data set/
        // F:/1papers/20190411 first/data set/

        int dataSetNumbers = 1;
        if ("dblp".equals(name) || "youtube".equals(name)) {
            dataSetNumbers = 11;
            file_path = file_path + name + "/";
        }
        for (int i = 1; i <= dataSetNumbers; i++) {
            if ("dblp".equals(name) || "youtube".equals(name)) {
                dateset_name = name + i;
                System.out.println(dateset_name);
            }

            //PC Â·¾¶
            String finial_path = file_path + dateset_name + "/";
            //MAC Â·¾¶
//            String file_path = "/Users/long/Desktop/data set/" + dateset_name + "/";
            String file_name = dateset_name;
            String network = "_network.txt";
            String community = "_community.txt";
            String table = "_table.txt";
            String result = "_result";
            String file_path_network = finial_path + file_name + network;
            String file_path_community = finial_path + file_name + community;
            String file_path_table = finial_path + file_name + table;
            String file_path_result = finial_path + file_name + result;
            ThirdRunMethodsOnRealWorldNetwork run_methords = new ThirdRunMethodsOnRealWorldNetwork(
                    file_path_community, file_path_table, file_path_result);
            ArrayList<String> algorithm_names = new ArrayList<>();
            /************      add method      ************/
//            algorithm_names.add("thirdFirstNew");
//            algorithm_names.add("thirdFirstOld");
//            algorithm_names.add("thirdDingNew");
//            algorithm_names.add("thirdDingOld");
//            algorithm_names.add("thirdSaltonNew");
//            algorithm_names.add("thirdSaltonOld");
//            algorithm_names.add("thirdCoNeighborsNew");
//            algorithm_names.add("thirdCoNeighborsOld");


//            algorithm_names.add("thirdSecLevelNew");
//            algorithm_names.add("thirdSecLevelOld");
//            algorithm_names.add("thirdLocalKCoreNew");
//            algorithm_names.add("thirdLocalKCoreOld");
//            algorithm_names.add("thirdFirstKCoreNew");
//            algorithm_names.add("thirdFirstKCoreOld");


            algorithm_names.add("thirdThreeNew");
            algorithm_names.add("thirdThreeOld");
            algorithm_names.add("thirdTwoNew");
            algorithm_names.add("thirdTwoOld");
            algorithm_names.add("thirdOneNew");
            algorithm_names.add("thirdOneOld");

            algorithm_names.add("thirdDing20New");
            algorithm_names.add("thirdDing20Old");
            algorithm_names.add("thirdJaccardNew");
            algorithm_names.add("thirdJaccardOld");
            algorithm_names.add("thirdRAIndexNew");
            algorithm_names.add("thirdRAIndexOld");
            algorithm_names.add("thirdDing19New");
            algorithm_names.add("thirdDing19Old");
            algorithm_names.add("thirdNS22New");
            algorithm_names.add("thirdNS22Old");
            algorithm_names.add("thirdAccess21New");
            algorithm_names.add("thirdAccess21Old");
            algorithm_names.add("thirdDing18New");
            algorithm_names.add("thirdDing18Old");


            algorithm_names.add("clauset");
            algorithm_names.add("lwp");
            algorithm_names.add("chen");
            algorithm_names.add("ls");

            algorithm_names.add("fifth");
            algorithm_names.add("lcd");

            algorithm_names.add("vi");


            String run_type = "all_nodes";   //core_node; all_nodes; random_node
            run_methords.run_methords_on_realworld_network(algorithm_names, run_type);

            //Remind
            System.out.println("Warning: THE PROGRAM HAS FINISHED RUNNING!!!");
//        Remind r = new Remind();
//        r.remind();
        }
    }

}