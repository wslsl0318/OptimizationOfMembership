package com.wangshenglong.methods;

public class RunMethordsOnRealWorldNetwork
{
    /*public DataReaderRealWorldNetwork data_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public String file_path_result;
    public TreeSet<NodeDegree> nodes_degree;

    public RunMethordsOnRealWorldNetwork(String file_path_community,
                                         String file_path_table, String file_path_result)
    {
        this.data_reader = new DataReaderRealWorldNetwork(file_path_community,
                file_path_table);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
        this.nodes_degree = new TreeSet<NodeDegree>(new NodeDegree());
    }

    *//* (Tested) initial n, m, nc, adjacency table, real communities *//*
    public void initial_paramaters() throws IOException
    {
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
        // System.out.println("adjacencyTable");
        // for (Iterator<ArrayList<Integer>> iter = adjacencyTable.iterator();
        // iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
        // System.out.println("real_communities");
        // for (Iterator<ArrayList<Integer>> iter = real_communities.iterator();
        // iter
        // .hasNext();)
        // {
        // System.out.println(iter.next());
        // }
    }

    *//* (Tested) Get all nodes degree *//*
    public void get_all_nodes_degree()
    {
        for (int i = 0; i < this.n; i++)
        {
            NodeDegree node_degree = new NodeDegree(i, this.adjacencyTable.get(
                    i).size() - 1);
            nodes_degree.add(node_degree);
        }
    }

    *//* (Tested) Get real partitions containing the seed node *//*
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

    *//* (Tested) Get node neighbors *//*
    public TreeSet<Integer> get_node_neighbors(int node_id)
    {
        TreeSet<Integer> neighbors = new TreeSet<Integer>();
        neighbors.addAll(adjacencyTable.get(node_id));
        neighbors.remove((Integer) node_id);
        return neighbors;
    }

    *//* (Tested) Calculate node importance *//*
    public int calculate_node_importance(int node_id)
    {
        TreeSet<Integer> neighbors_tree = get_node_neighbors(node_id);
        ArrayList<Integer> neighbors = new ArrayList<Integer>();
        neighbors.addAll(neighbors_tree);
        int links_between_neighbors = 0;
        int all_links = 0;
        for (int i = 0; i < neighbors.size(); i++)
        {
            int neighbor_1 = neighbors.get(i);
            for (int j = i + 1; j < neighbors.size(); j++)
            {
                int neighbor_2 = neighbors.get(j);
                if (adjacencyTable.get(neighbor_1).contains(neighbor_2))
                {
                    links_between_neighbors++;
                }
            }
        }
        all_links = links_between_neighbors + neighbors.size();
        // System.out.println(all_links);
        return all_links;
    }

    *//* (Tested) calculate_node_importance_for_all_nodes *//*
    public ArrayList<NodeImportance> calculate_node_importance_for_all_nodes()
    {
        ArrayList<NodeImportance> nodes_importance = new ArrayList<NodeImportance>();
        for (int i = 0; i < this.n; i++)
        {
            NodeImportance node_importance = new NodeImportance(i,
                    calculate_node_importance(i));
            nodes_importance.add(node_importance);
        }
        return nodes_importance;
    }

    *//* (Tested) Get insection size *//*
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

    *//* (Tested) Get insection size *//*
    public int get_insection_size(TreeSet<Integer> community,
                                  TreeSet<Integer> detected_community)
    {
        int insection_size = 0;
        for (Iterator<Integer> iter = community.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            if (detected_community.contains(member))
            {
                insection_size++;
            }
        }
        return insection_size;
    }

    *//* (Tested) Get insection size *//*
    public TreeSet<Integer> get_insection(TreeSet<Integer> node_set_1,
                                          TreeSet<Integer> node_set_2)
    {
        TreeSet<Integer> insection = new TreeSet<Integer>();
        for (Iterator<Integer> iter = node_set_1.iterator(); iter.hasNext();)
        {
            Integer member = iter.next();
            if (node_set_2.contains(member))
            {
                insection.add(member);
            }
        }
        return insection;
    }

    *//* (Tested) update detected communities *//*
    public void update_detected_distinct_communities_with_sub_set(
            ArrayList<TreeSet<Integer>> detected_communities,
            ArrayList<Integer> community)
    {
        TreeSet<Integer> community_temp = new TreeSet<Integer>();
        community_temp.addAll(community);
        if (!detected_communities.isEmpty())
        {
            // ArrayList<TreeSet<Integer>> communities_to_be_removed = new
            // ArrayList<TreeSet<Integer>>();
            boolean is_added = true;
            for (Iterator<TreeSet<Integer>> iter_detected_community = detected_communities
                    .iterator(); iter_detected_community.hasNext();)
            {
                TreeSet<Integer> detected_community = iter_detected_community
                        .next();
                int insection_size = get_insection_size(detected_community,
                        community_temp);
                // if (insection_size < community.size()
                // && insection_size == detected_community.size())
                // {
                // communities_to_be_removed.add(detected_community);
                // }
                // if (insection_size == community.size()
                // && insection_size <= detected_community.size())
                if (insection_size == community.size()
                        && insection_size == detected_community.size())
                {
                    is_added = false;
                }
            }
            if (is_added == true)
            {
                // if (!communities_to_be_removed.isEmpty())
                // {
                // detected_communities.removeAll(communities_to_be_removed);
                // }
                if (!community_temp.isEmpty())
                {
                    detected_communities.add(community_temp);
                }
            }
        } else
        {
            if (!community_temp.isEmpty())
            {
                detected_communities.add(community_temp);
            }
        }
    }

    *//* (Tested) update detected communities *//*
    public void update_detected_distinct_communities_with_out_sub_set(
            ArrayList<TreeSet<Integer>> detected_communities,
            ArrayList<Integer> community)
    {
        TreeSet<Integer> community_temp = new TreeSet<Integer>();
        community_temp.addAll(community);
        if (!detected_communities.isEmpty())
        {
            ArrayList<TreeSet<Integer>> communities_to_be_removed = new ArrayList<TreeSet<Integer>>();
            boolean is_added = true;
            for (Iterator<TreeSet<Integer>> iter_detected_community = detected_communities
                    .iterator(); iter_detected_community.hasNext();)
            {
                TreeSet<Integer> detected_community = iter_detected_community
                        .next();
                int insection_size = get_insection_size(detected_community,
                        community_temp);
                if (insection_size < community.size()
                        && insection_size == detected_community.size())
                {
                    communities_to_be_removed.add(detected_community);
                }
                if (insection_size == community.size()
                        && insection_size <= detected_community.size())

                {
                    is_added = false;
                }
            }
            if (is_added == true)
            {
                if (!communities_to_be_removed.isEmpty())
                {
                    detected_communities.removeAll(communities_to_be_removed);
                }
                if (!community_temp.isEmpty())
                {
                    detected_communities.add(community_temp);
                }
            }
        } else
        {
            if (!community_temp.isEmpty())
            {
                detected_communities.add(community_temp);
            }
        }
    }

    *//* (Tested) write results into files *//*
    public void write_results_into_files(String algorithm_name,
                                         long costTime_average, double NMI_average,
                                         double nodes_correctly_classified_percentage,
                                         ArrayList<Double> RPF_average,
                                         ArrayList<SeedAndCommunity> detected_communities)
            throws IOException
    {
        String file = this.file_path_result + algorithm_name;
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        // bw.write("costTime_average: " + costTime_average + "\r\n");
        bw.write("NMI_average: " + NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + nodes_correctly_classified_percentage + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = RPF_average.iterator(); iter_rpf
                .hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        bw.write("detected_communities:" + "\r\n");
        for (Iterator<SeedAndCommunity> iter_community = detected_communities
                .iterator(); iter_community.hasNext();)
        {
            SeedAndCommunity community = iter_community.next();
            bw.write("seed: " + community.seed + "\t" + "community: ");
            for (Iterator<Integer> iter_member = community.community.iterator(); iter_member
                    .hasNext();)
            {
                Integer member = iter_member.next();
                bw.write(member + "\t");
            }
            bw.write("\r\n");
        }
        bw.close();
    }

    *//*
     * (Tested) Get the nodes with max degree from real communities as seeds for
     * algorithms
     *//*
    public TreeSet<Integer> get_max_degree_nodes_from_real_communities()
    {
        TreeSet<Integer> seeds = new TreeSet<Integer>();
        for (Iterator<ArrayList<Integer>> iter_real_community = real_communities
                .iterator(); iter_real_community.hasNext();)
        {
            ArrayList<Integer> real_community = iter_real_community.next();
            int node_with_max_degree = -1;
            int max_degree = 0;
            for (Iterator<Integer> iter_member = real_community.iterator(); iter_member
                    .hasNext();)
            {
                Integer member = iter_member.next();
                if (adjacencyTable.get(member).size() > max_degree)
                {
                    max_degree = adjacencyTable.get(member).size();
                    node_with_max_degree = member;
                }
            }
            seeds.add(node_with_max_degree);
        }
        return seeds;
    }

    *//* (Tested) Get top seeds *//*
    public TreeSet<Integer> get_top_100_seed_nodes(TreeSet<Integer> seeds)
    {
        TreeSet<NodeDegree> ranked_seeds = new TreeSet<NodeDegree>(
                new NodeDegree());
        for (Iterator<Integer> iter = seeds.iterator(); iter.hasNext();)
        {
            Integer seed = iter.next();
            NodeDegree node_degree = new NodeDegree(seed, this.adjacencyTable
                    .get(seed).size() - 1);
            ranked_seeds.add(node_degree);
        }
        TreeSet<Integer> top_100_seeds = new TreeSet<Integer>();
        for (int i = 0; i < 100; i++)
        {
            top_100_seeds.add(ranked_seeds.pollFirst().id);
        }
        return top_100_seeds;
    }

    public void run_methords_on_realworld_network() throws IOException
    {
        *//*****************************************************************************************//*
        initial_paramaters();
        // get_all_nodes_degree();
        *//* algorithm_name *//*
        String fifth_algorithm_name = "(fifth).txt";
        String clauset_algorithm_name = "(clauset).txt";
        String lwp_algorithm_name = "(lwp).txt";
        String chen_algorithm_name = "(chen).txt";
        String ls_algorithm_name = "(ls).txt";
        String lcd_algorithm_name = "(lcd).txt";
        String is_degree_algorithm_name = "(isdegree).txt";	//20190528 Influence Spreading
        String is_kcore_algorithm_name = "(iskcore).txt";	//20190528 Influence Spreading
        *//* NMI *//*
        double fifth_NMI_average = 0;
        double clauset_NMI_average = 0;
        double lwp_NMI_average = 0;
        double chen_NMI_average = 0;
        double ls_NMI_average = 0;
        double lcd_NMI_average = 0;
        double is_degree_NMI_average = 0;	//20190528 Influence Spreading
        double is_kcore_NMI_average = 0;	//20190528 Influence Spreading
        *//* The percentage of nodes that are correctly classified *//*
        double fifth_nodes_correctly_classified_percentage = 0;
        double clauset_nodes_correctly_classified_percentage = 0;
        double lwp_nodes_correctly_classified_percentage = 0;
        double chen_nodes_correctly_classified_percentage = 0;
        double ls_nodes_correctly_classified_percentage = 0;
        double lcd_nodes_correctly_classified_percentage = 0;
        double is_degree_nodes_correctly_classified_percentage = 0;	//20190528 Influence Spreading
        double is_kcore_nodes_correctly_classified_percentage = 0;	//20190528 Influence Spreading
        *//* average cost time for algorithm on one seed *//*
        long fifth_costTime_average = 0;
        long clauset_costTime_average = 0;
        long lwp_costTime_average = 0;
        long chen_costTime_average = 0;
        long ls_costTime_average = 0;
        long lcd_costTime_average = 0;
        long is_degree_costTime_average = 0;	//20190528 Influence Spreading
        long is_kcore_costTime_average = 0;	//20190528 Influence Spreading
        *//* r, p, f *//*
        ArrayList<Double> fifth_rpf_average = new ArrayList<Double>();
        double fifth_r_average = 0;
        double fifth_p_average = 0;
        double fifth_f_average = 0;
        ArrayList<Double> clauset_rpf_average = new ArrayList<Double>();
        double clauset_r_average = 0;
        double clauset_p_average = 0;
        double clauset_f_average = 0;
        ArrayList<Double> lwp_rpf_average = new ArrayList<Double>();
        double lwp_r_average = 0;
        double lwp_p_average = 0;
        double lwp_f_average = 0;
        ArrayList<Double> chen_rpf_average = new ArrayList<Double>();
        double chen_r_average = 0;
        double chen_p_average = 0;
        double chen_f_average = 0;
        ArrayList<Double> ls_rpf_average = new ArrayList<Double>();
        double ls_r_average = 0;
        double ls_p_average = 0;
        double ls_f_average = 0;
        ArrayList<Double> lcd_rpf_average = new ArrayList<Double>();
        double lcd_r_average = 0;
        double lcd_p_average = 0;
        double lcd_f_average = 0;
        ArrayList<Double> is_degree_rpf_average = new ArrayList<Double>();	//20190528 Influence Spreading
        double is_degree_r_average = 0;
        double is_degree_p_average = 0;
        double is_degree_f_average = 0;
        ArrayList<Double> is_kcore_rpf_average = new ArrayList<Double>();	//20190528 Influence Spreading
        double is_kcore_r_average = 0;
        double is_kcore_p_average = 0;
        double is_kcore_f_average = 0;
        *//* detected distinct communities with out sub set *//*
        ArrayList<SeedAndCommunity> fifth_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<SeedAndCommunity> clauset_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<SeedAndCommunity> lwp_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<SeedAndCommunity> chen_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<SeedAndCommunity> ls_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<SeedAndCommunity> lcd_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<SeedAndCommunity> is_degree_detected_communities = new ArrayList<SeedAndCommunity>();	//20190528 Influence Spreading
        ArrayList<SeedAndCommunity> is_kcore_detected_communities = new ArrayList<SeedAndCommunity>();	//20190528 Influence Spreading

        long startTime_temp = System.currentTimeMillis();
        ArrayList<NodeImportance> nodes_importance = calculate_node_importance_for_all_nodes();
        *//*20190529 test influence as node importance*//**//*
		NodeInfluence ni = new NodeInfluence();
		ArrayList<NodeInfluence> nodes_influence = ni.getNodeInfluence("kcore", adjacencyTable);
		ArrayList<FifthNodeImportance> nodes_importance = new ArrayList<>();
		for(NodeInfluence nf : nodes_influence) {
			FifthNodeImportance fni = new FifthNodeImportance();
			fni.id  = nf.id;
			fni.importance = nf.influence;
			nodes_importance.add(fni);
		}
		*//**//*20190529 test influence as node importance*//*
        long endTime_temp = System.currentTimeMillis();
        IsAlgorithm is_degree = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");	//20190528 Influence Spreading
        IsAlgorithm is_kcore = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");	//20190528 Influence Spreading
        FifthAlgorithm fifth = new FifthAlgorithm(n, m, adjacencyTable,
                nodes_importance);
        ClausetAlgorithm clauset = new ClausetAlgorithm(n, m, adjacencyTable);
        LWPAlgorithm lwp = new LWPAlgorithm(n, m, adjacencyTable);
        ChenAlgorithm chen = new ChenAlgorithm(n, m, adjacencyTable);
        LSAlgorithm ls = new LSAlgorithm(n, m, adjacencyTable);
        LCDAlgorithm lcd = new LCDAlgorithm(n, m, adjacencyTable);

        // TreeSet<Integer> seeds =
        // get_max_degree_nodes_from_real_communities();
        // TreeSet<Integer> top_100_seeds = get_top_100_seed_nodes(seeds);
        int seed_size = this.n;// top_100_seeds.size();// this.n;//seeds.size();
        // seeds.size();
        // System.out.println(top_100_seeds.size()
        // + "," + seeds.size() + ","
        // + seeds);
        // int count = seed_size;
        // for (Iterator<Integer> iter_seed_id = seeds.iterator(); iter_seed_id
        // .hasNext();) {
        // count--;
        // System.out.println(count);
        // Integer seed_id = iter_seed_id.next();

        *//*******************************  20190531 test max degree nodes  *****************************//*
//		TreeSet<Integer> seeds = get_max_degree_nodes_from_real_communities();
//		seed_size = seeds.size();
//		int count = seed_size;
//		for (Iterator<Integer> iter_seed_id = seeds.iterator(); iter_seed_id
//				.hasNext();)
//		{
//			count--;
//			System.out.println("seed_size_left: " + count);
//			Integer seed_id = iter_seed_id.next();
        *//*******************************  20190531 test max degree nodes  *****************************//*
        int left_nodes_size = this.n;
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            left_nodes_size--;
            System.out.println("left_nodes_size: " + left_nodes_size);
            *//*******************************  All nodes  *****************************//*
            System.out.println("seed_id: " + seed_id);
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionRS = get_real_partitions_containing_seed(seed_id);
            *//*****************************************************************************************//*
            *//* fifth *//*
            startTime_once = System.currentTimeMillis();
            partitionF = fifth.run_fifth(seed_id);
            SeedAndCommunity fifth_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            fifth_detected_communities.add(fifth_seed_community);
            endTime_once = System.currentTimeMillis();
            long fifth_costTime_once = endTime_once - startTime_once;
            fifth_costTime_average += fifth_costTime_once;
            double fifth_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                fifth_nodes_correctly_classified_percentage++;
            }
            double fifth_r_once_average = 0;
            double fifth_p_once_average = 0;
            double fifth_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double fifth_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                fifth_NMI_once_average += fifth_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r = (double) insection_size / (double) partitionR_size;
                double p = (double) insection_size / (double) partitionF_size;
                double f = 2 * r * p / (r + p);
                fifth_r_once_average += r;
                fifth_p_once_average += p;
                fifth_f_once_average += f;
            }
            fifth_NMI_once_average /= partitionRS.size();
            fifth_NMI_average += fifth_NMI_once_average;
            fifth_r_once_average /= partitionRS.size();
            fifth_p_once_average /= partitionRS.size();
            fifth_f_once_average /= partitionRS.size();
            fifth_r_average += fifth_r_once_average;
            fifth_p_average += fifth_p_once_average;
            fifth_f_average += fifth_f_once_average;
            *//*****************************************************************************************//*
            *//* clauset *//*
            startTime_once = System.currentTimeMillis();
            partitionF = clauset.run_clauset(seed_id);
            SeedAndCommunity clauset_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            clauset_detected_communities.add(clauset_seed_community);
            endTime_once = System.currentTimeMillis();
            long clauset_costTime_once = endTime_once - startTime_once;
            clauset_costTime_average += clauset_costTime_once;
            double clauset_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                clauset_nodes_correctly_classified_percentage++;
            }
            double clauset_r_once_average = 0;
            double clauset_p_once_average = 0;
            double clauset_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double clauset_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                clauset_NMI_once_average += clauset_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r = (double) insection_size / (double) partitionR_size;
                double p = (double) insection_size / (double) partitionF_size;
                double f = 2 * r * p / (r + p);
                clauset_r_once_average += r;
                clauset_p_once_average += p;
                clauset_f_once_average += f;
            }
            clauset_NMI_once_average /= partitionRS.size();
            clauset_NMI_average += clauset_NMI_once_average;
            clauset_r_once_average /= partitionRS.size();
            clauset_p_once_average /= partitionRS.size();
            clauset_f_once_average /= partitionRS.size();
            clauset_r_average += clauset_r_once_average;
            clauset_p_average += clauset_p_once_average;
            clauset_f_average += clauset_f_once_average;
            *//*****************************************************************************************//*
            *//* lwp *//*
            startTime_once = System.currentTimeMillis();
            partitionF = lwp.run_lwp(seed_id);
            SeedAndCommunity lwp_seed_community = new SeedAndCommunity(seed_id,
                    partitionF.get(0));
            lwp_detected_communities.add(lwp_seed_community);
            endTime_once = System.currentTimeMillis();
            long lwp_costTime_once = endTime_once - startTime_once;
            lwp_costTime_average += lwp_costTime_once;
            double lwp_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                lwp_nodes_correctly_classified_percentage++;
            }
            double lwp_r_once_average = 0;
            double lwp_p_once_average = 0;
            double lwp_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double lwp_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                lwp_NMI_once_average += lwp_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r;
                double p;
                double f;
                if (insection_size == 0)
                {
                    r = 0;
                    p = 0;
                    f = 0;
                } else
                {
                    r = (double) insection_size / (double) partitionR_size;
                    p = (double) insection_size / (double) partitionF_size;
                    f = 2 * r * p / (r + p);
                }
                lwp_r_once_average += r;
                lwp_p_once_average += p;
                lwp_f_once_average += f;
            }
            lwp_NMI_once_average /= partitionRS.size();
            lwp_NMI_average += lwp_NMI_once_average;
            lwp_r_once_average /= partitionRS.size();
            lwp_p_once_average /= partitionRS.size();
            lwp_f_once_average /= partitionRS.size();
            lwp_r_average += lwp_r_once_average;
            lwp_p_average += lwp_p_once_average;
            lwp_f_average += lwp_f_once_average;
            *//*****************************************************************************************//*
            *//* chen *//*
            startTime_once = System.currentTimeMillis();
            partitionF = chen.run_chen(seed_id);
            SeedAndCommunity chen_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            chen_detected_communities.add(chen_seed_community);
            endTime_once = System.currentTimeMillis();
            long chen_costTime_once = endTime_once - startTime_once;
            chen_costTime_average += chen_costTime_once;
            double chen_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                chen_nodes_correctly_classified_percentage++;
            }
            double chen_r_once_average = 0;
            double chen_p_once_average = 0;
            double chen_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double chen_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                chen_NMI_once_average += chen_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r;
                double p;
                double f;
                if (insection_size == 0)
                {
                    r = 0;
                    p = 0;
                    f = 0;
                } else
                {
                    r = (double) insection_size / (double) partitionR_size;
                    p = (double) insection_size / (double) partitionF_size;
                    f = 2 * r * p / (r + p);
                }
                chen_r_once_average += r;
                chen_p_once_average += p;
                chen_f_once_average += f;
            }
            chen_NMI_once_average /= partitionRS.size();
            chen_NMI_average += chen_NMI_once_average;
            chen_r_once_average /= partitionRS.size();
            chen_p_once_average /= partitionRS.size();
            chen_f_once_average /= partitionRS.size();
            chen_r_average += chen_r_once_average;
            chen_p_average += chen_p_once_average;
            chen_f_average += chen_f_once_average;
            *//*****************************************************************************************//*
            *//* ls *//*
            startTime_once = System.currentTimeMillis();
            partitionF = ls.run_ls(seed_id);
            SeedAndCommunity ls_seed_community = new SeedAndCommunity(seed_id,
                    partitionF.get(0));
            ls_detected_communities.add(ls_seed_community);
            endTime_once = System.currentTimeMillis();
            long ls_costTime_once = endTime_once - startTime_once;
            ls_costTime_average += ls_costTime_once;
            double ls_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                ls_nodes_correctly_classified_percentage++;
            }
            double ls_r_once_average = 0;
            double ls_p_once_average = 0;
            double ls_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double ls_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                ls_NMI_once_average += ls_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r;
                double p;
                double f;
                if (insection_size == 0)
                {
                    r = 0;
                    p = 0;
                    f = 0;
                } else
                {
                    r = (double) insection_size / (double) partitionR_size;
                    p = (double) insection_size / (double) partitionF_size;
                    f = 2 * r * p / (r + p);
                }
                ls_r_once_average += r;
                ls_p_once_average += p;
                ls_f_once_average += f;
            }
            ls_NMI_once_average /= partitionRS.size();
            ls_NMI_average += ls_NMI_once_average;
            ls_r_once_average /= partitionRS.size();
            ls_p_once_average /= partitionRS.size();
            ls_f_once_average /= partitionRS.size();
            ls_r_average += ls_r_once_average;
            ls_p_average += ls_p_once_average;
            ls_f_average += ls_f_once_average;
            *//*****************************************************************************************//*
            *//* lcd *//*
            startTime_once = System.currentTimeMillis();
            partitionFS = lcd.run_lcd(seed_id);
            endTime_once = System.currentTimeMillis();
            long lcd_costTime_once = endTime_once - startTime_once;
            lcd_costTime_average += lcd_costTime_once;
            double lcd_NMI_once_average = 0;
            boolean is_partitionFS_contain_seed = false;
            double lcd_r_once_average = 0;
            double lcd_p_once_average = 0;
            double lcd_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double lcd_NMI_once_average_one = 0;
                double lcd_r_once_average_one = 0;
                double lcd_p_once_average_one = 0;
                double lcd_f_once_average_one = 0;
                for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF = partitionFS
                        .iterator(); iter_partitionF.hasNext();)
                {
                    partitionF = iter_partitionF.next();
                    SeedAndCommunity lcd_seed_community = new SeedAndCommunity(
                            seed_id, partitionF.get(0));
                    lcd_detected_communities.add(lcd_seed_community);
                    if (partitionF.get(0).contains(seed_id))
                    {
                        is_partitionFS_contain_seed = true;
                    }
                    double lcd_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                            partitionR, this.n);
                    lcd_NMI_once_average_one += lcd_NMI;
                    int partitionF_size = partitionF.get(0).size();
                    int partitionR_size = partitionR.get(0).size();
                    int insection_size = get_insection_size(partitionF.get(0),
                            partitionR.get(0));
                    double r;
                    double p;
                    double f;
                    if (insection_size == 0)
                    {
                        r = 0;
                        p = 0;
                        f = 0;
                    } else
                    {
                        r = (double) insection_size / (double) partitionR_size;
                        p = (double) insection_size / (double) partitionF_size;
                        f = 2 * r * p / (r + p);
                    }
                    lcd_r_once_average_one += r;
                    lcd_p_once_average_one += p;
                    lcd_f_once_average_one += f;
                }
                lcd_NMI_once_average_one /= partitionFS.size();
                lcd_NMI_once_average += lcd_NMI_once_average_one;
                lcd_r_once_average_one /= partitionFS.size();
                lcd_p_once_average_one /= partitionFS.size();
                lcd_f_once_average_one /= partitionFS.size();
                lcd_r_once_average += lcd_r_once_average_one;
                lcd_p_once_average += lcd_p_once_average_one;
                lcd_f_once_average += lcd_f_once_average_one;
            }
            lcd_NMI_once_average /= partitionRS.size();
            lcd_NMI_average += lcd_NMI_once_average;
            if (is_partitionFS_contain_seed)
            {
                lcd_nodes_correctly_classified_percentage++;
            }
            lcd_r_once_average /= partitionRS.size();
            lcd_p_once_average /= partitionRS.size();
            lcd_f_once_average /= partitionRS.size();
            lcd_r_average += lcd_r_once_average;
            lcd_p_average += lcd_p_once_average;
            lcd_f_average += lcd_f_once_average;
            *//*****************************************************************************************//*
            *//* is_degree 20190528 *//*
            startTime_once = System.currentTimeMillis();
            *//*20190529 test influence as node importance*//**//*
			ArrayList<FifthNodeImportance> is_nodes_importance = calculate_node_importance_for_all_nodes();
			is_degree.node_influences.clear();
			for(FifthNodeImportance fni : is_nodes_importance) {
				NodeInfluence is_ni = new NodeInfluence();
				is_ni.id = fni.id;
				is_ni.influence = fni.importance;
				is_degree.node_influences.add(is_ni);
			}
			*//**//*20190529 test influence as node importance*//*
            partitionF = is_degree.run_is(seed_id);
            SeedAndCommunity is_degree_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            is_degree_detected_communities.add(is_degree_seed_community);
            endTime_once = System.currentTimeMillis();
            long is_degree_costTime_once = endTime_once - startTime_once;
            is_degree_costTime_average += is_degree_costTime_once;
            double is_degree_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                is_degree_nodes_correctly_classified_percentage++;
            }
            double is_degree_r_once_average = 0;
            double is_degree_p_once_average = 0;
            double is_degree_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double is_degree_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                is_degree_NMI_once_average += is_degree_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r = (double) insection_size / (double) partitionR_size;
                double p = (double) insection_size / (double) partitionF_size;
                double f = 2 * r * p / (r + p);
                is_degree_r_once_average += r;
                is_degree_p_once_average += p;
                is_degree_f_once_average += f;
            }
            is_degree_NMI_once_average /= partitionRS.size();
            is_degree_NMI_average += is_degree_NMI_once_average;
            is_degree_r_once_average /= partitionRS.size();
            is_degree_p_once_average /= partitionRS.size();
            is_degree_f_once_average /= partitionRS.size();
            is_degree_r_average += is_degree_r_once_average;
            is_degree_p_average += is_degree_p_once_average;
            is_degree_f_average += is_degree_f_once_average;
            *//*****************************************************************************************//*
            *//* is_kcore 20190528 *//*
            startTime_once = System.currentTimeMillis();
            partitionF = is_kcore.run_is(seed_id);
            SeedAndCommunity is_kcore_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            is_kcore_detected_communities.add(is_kcore_seed_community);
            endTime_once = System.currentTimeMillis();
            long is_kcore_costTime_once = endTime_once - startTime_once;
            is_kcore_costTime_average += is_kcore_costTime_once;
            double is_kcore_NMI_once_average = 0;
            if (partitionF.get(0).contains(seed_id))
            {
                is_kcore_nodes_correctly_classified_percentage++;
            }
            double is_kcore_r_once_average = 0;
            double is_kcore_p_once_average = 0;
            double is_kcore_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionR = partitionRS
                    .iterator(); iter_partitionR.hasNext();)
            {
                ArrayList<ArrayList<Integer>> partitionR = iter_partitionR
                        .next();
                double is_kcore_NMI = NMI.NMIPartitionUnoverLap(partitionF,
                        partitionR, this.n);
                is_kcore_NMI_once_average += is_kcore_NMI;
                int partitionF_size = partitionF.get(0).size();
                int partitionR_size = partitionR.get(0).size();
                int insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                double r = (double) insection_size / (double) partitionR_size;
                double p = (double) insection_size / (double) partitionF_size;
                double f = 2 * r * p / (r + p);
                is_kcore_r_once_average += r;
                is_kcore_p_once_average += p;
                is_kcore_f_once_average += f;
            }
            is_kcore_NMI_once_average /= partitionRS.size();
            is_kcore_NMI_average += is_kcore_NMI_once_average;
            is_kcore_r_once_average /= partitionRS.size();
            is_kcore_p_once_average /= partitionRS.size();
            is_kcore_f_once_average /= partitionRS.size();
            is_kcore_r_average += is_kcore_r_once_average;
            is_kcore_p_average += is_kcore_p_once_average;
            is_kcore_f_average += is_kcore_f_once_average;
            *//*****************************************************************************************//*
        }
        *//*****************************************************************************************//*
        *//* fifth *//*
        // fifth_costTime_average += startTime_temp - endTime_temp;
        fifth_costTime_average /= seed_size;
        fifth_NMI_average /= seed_size;
        fifth_nodes_correctly_classified_percentage /= (double) seed_size;
        fifth_r_average /= seed_size;
        fifth_p_average /= seed_size;
        fifth_f_average /= seed_size;
        fifth_rpf_average.add(fifth_r_average);
        fifth_rpf_average.add(fifth_p_average);
        fifth_rpf_average.add(fifth_f_average);
        write_results_into_files(fifth_algorithm_name, fifth_costTime_average,
                fifth_NMI_average, fifth_nodes_correctly_classified_percentage,
                fifth_rpf_average, fifth_detected_communities);
        *//*****************************************************************************************//*
        *//* clauset *//*
        clauset_costTime_average /= seed_size;
        clauset_NMI_average /= seed_size;
        clauset_nodes_correctly_classified_percentage /= (double) seed_size;
        clauset_r_average /= seed_size;
        clauset_p_average /= seed_size;
        clauset_f_average /= seed_size;
        clauset_rpf_average.add(clauset_r_average);
        clauset_rpf_average.add(clauset_p_average);
        clauset_rpf_average.add(clauset_f_average);
        write_results_into_files(clauset_algorithm_name,
                clauset_costTime_average, clauset_NMI_average,
                clauset_nodes_correctly_classified_percentage,
                clauset_rpf_average, clauset_detected_communities);
        *//*****************************************************************************************//*
        *//* lwp *//*
        lwp_costTime_average /= seed_size;
        lwp_NMI_average /= seed_size;
        lwp_nodes_correctly_classified_percentage /= (double) seed_size;
        lwp_r_average /= seed_size;
        lwp_p_average /= seed_size;
        lwp_f_average /= seed_size;
        lwp_rpf_average.add(lwp_r_average);
        lwp_rpf_average.add(lwp_p_average);
        lwp_rpf_average.add(lwp_f_average);
        write_results_into_files(lwp_algorithm_name, lwp_costTime_average,
                lwp_NMI_average, lwp_nodes_correctly_classified_percentage,
                lwp_rpf_average, lwp_detected_communities);
        *//*****************************************************************************************//*
        *//* chen *//*
        chen_costTime_average /= seed_size;
        chen_NMI_average /= seed_size;
        chen_nodes_correctly_classified_percentage /= (double) seed_size;
        chen_r_average /= seed_size;
        chen_p_average /= seed_size;
        chen_f_average /= seed_size;
        chen_rpf_average.add(chen_r_average);
        chen_rpf_average.add(chen_p_average);
        chen_rpf_average.add(chen_f_average);
        write_results_into_files(chen_algorithm_name, chen_costTime_average,
                chen_NMI_average, chen_nodes_correctly_classified_percentage,
                chen_rpf_average, chen_detected_communities);
        *//*****************************************************************************************//*
        *//* ls *//*
        ls_costTime_average /= seed_size;
        ls_NMI_average /= seed_size;
        ls_nodes_correctly_classified_percentage /= (double) seed_size;
        ls_r_average /= seed_size;
        ls_p_average /= seed_size;
        ls_f_average /= seed_size;
        ls_rpf_average.add(ls_r_average);
        ls_rpf_average.add(ls_p_average);
        ls_rpf_average.add(ls_f_average);
        write_results_into_files(ls_algorithm_name, ls_costTime_average,
                ls_NMI_average, ls_nodes_correctly_classified_percentage,
                ls_rpf_average, ls_detected_communities);
        *//*****************************************************************************************//*
        *//* lcd *//*
        lcd_costTime_average /= seed_size;
        lcd_NMI_average /= seed_size;
        lcd_nodes_correctly_classified_percentage /= (double) seed_size;
        lcd_r_average /= seed_size;
        lcd_p_average /= seed_size;
        lcd_f_average /= seed_size;
        lcd_rpf_average.add(lcd_r_average);
        lcd_rpf_average.add(lcd_p_average);
        lcd_rpf_average.add(lcd_f_average);
        write_results_into_files(lcd_algorithm_name, lcd_costTime_average,
                lcd_NMI_average, lcd_nodes_correctly_classified_percentage,
                lcd_rpf_average, lcd_detected_communities);
        *//*****************************************************************************************//*
        *//* is_degree 20190528 *//*
        // is_degree_costTime_average += startTime_temp - endTime_temp;
        is_degree_costTime_average /= seed_size;
        is_degree_NMI_average /= seed_size;
        is_degree_nodes_correctly_classified_percentage /= (double) seed_size;
        is_degree_r_average /= seed_size;
        is_degree_p_average /= seed_size;
        is_degree_f_average /= seed_size;
        is_degree_rpf_average.add(is_degree_r_average);
        is_degree_rpf_average.add(is_degree_p_average);
        is_degree_rpf_average.add(is_degree_f_average);
        write_results_into_files(is_degree_algorithm_name, is_degree_costTime_average,
                is_degree_NMI_average, is_degree_nodes_correctly_classified_percentage,
                is_degree_rpf_average, is_degree_detected_communities);
        *//*****************************************************************************************//*
        *//* is_kcore 20190528 *//*
        // is_kcore_costTime_average += startTime_temp - endTime_temp;
        is_kcore_costTime_average /= seed_size;
        is_kcore_NMI_average /= seed_size;
        is_kcore_nodes_correctly_classified_percentage /= (double) seed_size;
        is_kcore_r_average /= seed_size;
        is_kcore_p_average /= seed_size;
        is_kcore_f_average /= seed_size;
        is_kcore_rpf_average.add(is_kcore_r_average);
        is_kcore_rpf_average.add(is_kcore_p_average);
        is_kcore_rpf_average.add(is_kcore_f_average);
        write_results_into_files(is_kcore_algorithm_name, is_kcore_costTime_average,
                is_kcore_NMI_average, is_kcore_nodes_correctly_classified_percentage,
                is_kcore_rpf_average, is_kcore_detected_communities);
        *//*****************************************************************************************//*
    }

    public static void main(String[] args) throws IOException
    {
        
        String file_name = "karate";
        // karate;dolphin;football;krebsbook;dblp;amazon;youtube;lj
        String network = "_network.txt";
        String community = "_community.txt";
        String table = "_table.txt";
        String result = "_result";
        String file_path_network = file_path + file_name + network;
        String file_path_community = file_path + file_name + community;
        String file_path_table = file_path + file_name + table;
        String file_path_result = file_path + file_name + result;
        RunMethordsOnRealWorldNetwork run_methords = new RunMethordsOnRealWorldNetwork(
                file_path_community, file_path_table, file_path_result);
        run_methords.run_methords_on_realworld_network();
    }*/
}