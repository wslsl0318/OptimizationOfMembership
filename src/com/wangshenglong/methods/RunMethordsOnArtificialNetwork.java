package com.wangshenglong.methods;

public class RunMethordsOnArtificialNetwork
{
    /*public DataReaderArtificialNetwork data_reader;
    public int n;// node number
    public int m;// link number
    public int nc;// community number;
    public ArrayList<ArrayList<Integer>> adjacencyTable;
    public ArrayList<ArrayList<Integer>> real_communities;
    public String file_path_result;
    public RunMethordsOnArtificialNetwork(String file_path_network,
                                          String file_path_community, String file_path_result)
    {
        this.data_reader = new DataReaderArtificialNetwork(file_path_network,
                file_path_community);
        this.adjacencyTable = new ArrayList<ArrayList<Integer>>();
        this.real_communities = new ArrayList<ArrayList<Integer>>();
        this.file_path_result = file_path_result;
    }
    *//* (Tested) initial n, m, nc, adjacency table, real communities *//*
    public void initial_paramaters() throws IOException
    {
        this.data_reader.read_artificial_network();
        this.n = this.data_reader.n;
        this.m = this.data_reader.m;
        this.nc = this.data_reader.nc;
        this.adjacencyTable.addAll(this.data_reader.adjacencyTable);
        this.data_reader.adjacencyTable.clear();
        this.real_communities.addAll(this.data_reader.real_communities);
        this.data_reader.real_communities.clear();
        // System.out.println("node number: " + n);
        // System.out.println("link number: " + m);
        // System.out.println("community number: " + nc);
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
    *//* (Tested) Get the real partition containing the seed node *//*
    public ArrayList<ArrayList<Integer>> get_real_partition_containing_seed(
            int seed_id)
    {
        ArrayList<Integer> real_community_containing_seed = new ArrayList<Integer>();
        for (Iterator<ArrayList<Integer>> iter = real_communities.iterator(); iter
                .hasNext();)
        {
            ArrayList<Integer> real_community = iter.next();
            if (real_community.contains((Integer) seed_id))
            {
                real_community_containing_seed = real_community;
            }
        }
        ArrayList<Integer> rest_nodes = new ArrayList<Integer>();
        for (int i = 0; i < this.n; i++)
        {
            if (!real_community_containing_seed.contains(i))
            {
                rest_nodes.add(i);
            }
        }
        ArrayList<ArrayList<Integer>> partitionR = new ArrayList<ArrayList<Integer>>();
        partitionR.add(real_community_containing_seed);
        partitionR.add(rest_nodes);
        return partitionR;
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
    *//* (Tested) wrute results into files *//*
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
    *//* (Tested) wrute results into files *//*
    public static void write_average_results_into_files(String target_path,
                                                        ArrayList<Measurements> measurements) throws IOException
    {
        String file = target_path + "results.txt";
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        BufferedWriter bw = new BufferedWriter(osw);
        *//* fifth *//*
        Measurements fifth_measurements = measurements.get(0);
        bw.write("fifth algorithm" + "\r\n");
        // bw.write("costTime_average: " + fifth_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + fifth_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + fifth_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = fifth_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* clauset *//*
        Measurements clauset_measurements = measurements.get(1);
        bw.write("clauset algorithm" + "\r\n");
        // bw.write("costTime_average: " + clauset_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + clauset_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + clauset_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = clauset_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* lwp *//*
        Measurements lwp_measurements = measurements.get(2);
        bw.write("lwp algorithm" + "\r\n");
        // bw.write("costTime_average: " + lwp_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + lwp_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + lwp_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = lwp_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* chen *//*
        Measurements chen_measurements = measurements.get(3);
        bw.write("chen algorithm" + "\r\n");
        // bw.write("costTime_average: " + chen_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + chen_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + chen_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = chen_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* ls *//*
        Measurements ls_measurements = measurements.get(4);
        bw.write("ls algorithm" + "\r\n");
        // bw.write("costTime_average: " + ls_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + ls_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + ls_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = ls_measurements.RPF_average.iterator(); iter_rpf
                .hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* lcd *//*
        Measurements lcd_measurements = measurements.get(5);
        bw.write("lcd algorithm" + "\r\n");
        // bw.write("costTime_average: " + lcd_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + lcd_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + lcd_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = lcd_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* is_degree *//*
        Measurements is_degree_measurements = measurements.get(5);
        bw.write("is_degree algorithm" + "\r\n");
        // bw.write("costTime_average: " + is_degree_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + is_degree_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + is_degree_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = is_degree_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        *//* is_kcore *//*
        Measurements is_kcore_measurements = measurements.get(5);
        bw.write("is_kcore algorithm" + "\r\n");
        // bw.write("costTime_average: " + is_kcore_measurements.costTime_average
        // + "\r\n");
        bw.write("NMI_average: " + is_kcore_measurements.NMI_average + "\r\n");
        bw.write("nodes_correctly_classified_percentage: "
                + is_kcore_measurements.nodes_correctly_classified_percentage
                + "\r\n");
        bw.write("RPF_average: " + "\r\n");
        for (Iterator<Double> iter_rpf = is_kcore_measurements.RPF_average
                .iterator(); iter_rpf.hasNext();)
        {
            Double rpf = iter_rpf.next();
            bw.write(rpf + "\t");
        }
        bw.write("\r\n");
        bw.close();
        *//* fifth,clauset,lwp,chen,ls,lcd,is_degree,is_kcore *//*
    }
    *//***********************************************************************************//*
 *//* fifth *//*
    public Measurements run_fifth_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String fifth_algorithm_name = "(fifth).txt";
        double fifth_NMI_average = 0;
        double fifth_nodes_correctly_classified_percentage = 0;
        long fifth_costTime_average = 0;
        ArrayList<Double> fifth_rpf_average = new ArrayList<Double>();
        double fifth_r_average = 0;
        double fifth_p_average = 0;
        double fifth_f_average = 0;
        ArrayList<SeedAndCommunity> fifth_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<NodeImportance> nodes_importance = calculate_node_importance_for_all_nodes();
        FifthAlgorithm fifth = new FifthAlgorithm(n, m, adjacencyTable,
                nodes_importance);
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = fifth.run_fifth(seed_id);
            SeedAndCommunity fifth_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            fifth_detected_communities.add(fifth_seed_community);
            endTime_once = System.currentTimeMillis();
            long fifth_costTime_once = endTime_once - startTime_once;
            fifth_costTime_average += fifth_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                fifth_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            fifth_NMI_average += NMI_temp;
            fifth_r_average += r;
            fifth_p_average += p;
            fifth_f_average += f;
        }
        fifth_costTime_average /= number_of_core;
        fifth_NMI_average /= number_of_core;
        fifth_nodes_correctly_classified_percentage /= (double) number_of_core;
        fifth_r_average /= number_of_core;
        fifth_p_average /= number_of_core;
        fifth_f_average /= number_of_core;
        fifth_rpf_average.add(fifth_r_average);
        fifth_rpf_average.add(fifth_p_average);
        fifth_rpf_average.add(fifth_f_average);
        write_results_into_files(fifth_algorithm_name, fifth_costTime_average,
                fifth_NMI_average, fifth_nodes_correctly_classified_percentage,
                fifth_rpf_average, fifth_detected_communities);
        Measurements measurements = new Measurements(fifth_algorithm_name,
                fifth_costTime_average, fifth_NMI_average,
                fifth_nodes_correctly_classified_percentage, fifth_rpf_average);
        return measurements;
    }
    *//* clauset *//*
    public Measurements run_clauset_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String clauset_algorithm_name = "(clauset).txt";
        double clauset_NMI_average = 0;
        double clauset_nodes_correctly_classified_percentage = 0;
        long clauset_costTime_average = 0;
        ArrayList<Double> clauset_rpf_average = new ArrayList<Double>();
        double clauset_r_average = 0;
        double clauset_p_average = 0;
        double clauset_f_average = 0;
        ArrayList<SeedAndCommunity> clauset_detected_communities = new ArrayList<SeedAndCommunity>();
        ClausetAlgorithm clauset = new ClausetAlgorithm(n, m, adjacencyTable);
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            // System.out.println("clauset");
            startTime_once = System.currentTimeMillis();
            partitionF = clauset.run_clauset(seed_id);
            SeedAndCommunity clauset_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            clauset_detected_communities.add(clauset_seed_community);
            endTime_once = System.currentTimeMillis();
            long clauset_costTime_once = endTime_once - startTime_once;
            clauset_costTime_average += clauset_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                clauset_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            clauset_NMI_average += NMI_temp;
            clauset_r_average += r;
            clauset_p_average += p;
            clauset_f_average += f;
        }
        clauset_costTime_average /= number_of_core;
        clauset_NMI_average /= number_of_core;
        clauset_nodes_correctly_classified_percentage /= (double) number_of_core;
        clauset_r_average /= number_of_core;
        clauset_p_average /= number_of_core;
        clauset_f_average /= number_of_core;
        clauset_rpf_average.add(clauset_r_average);
        clauset_rpf_average.add(clauset_p_average);
        clauset_rpf_average.add(clauset_f_average);
        write_results_into_files(clauset_algorithm_name,
                clauset_costTime_average, clauset_NMI_average,
                clauset_nodes_correctly_classified_percentage,
                clauset_rpf_average, clauset_detected_communities);
        Measurements measurements = new Measurements(clauset_algorithm_name,
                clauset_costTime_average, clauset_NMI_average,
                clauset_nodes_correctly_classified_percentage,
                clauset_rpf_average);
        return measurements;
    }
    *//* lwp *//*
    public Measurements run_lwp_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String lwp_algorithm_name = "(lwp).txt";
        double lwp_NMI_average = 0;
        double lwp_nodes_correctly_classified_percentage = 0;
        long lwp_costTime_average = 0;
        ArrayList<Double> lwp_rpf_average = new ArrayList<Double>();
        double lwp_r_average = 0;
        double lwp_p_average = 0;
        double lwp_f_average = 0;
        ArrayList<SeedAndCommunity> lwp_detected_communities = new ArrayList<SeedAndCommunity>();
        LWPAlgorithm lwp = new LWPAlgorithm(n, m, adjacencyTable);
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = lwp.run_lwp(seed_id);
            SeedAndCommunity lwp_seed_community = new SeedAndCommunity(seed_id,
                    partitionF.get(0));
            lwp_detected_communities.add(lwp_seed_community);
            endTime_once = System.currentTimeMillis();
            long lwp_costTime_once = endTime_once - startTime_once;
            lwp_costTime_average += lwp_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                lwp_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            if (insection_size == 0)
            {
                r = 0;
                p = 0;
                f = 0;
            } else
            {
                r = (double) insection_size / (double) partitionR.get(0).size();
                p = (double) insection_size / (double) partitionF.get(0).size();
                f = 2 * r * p / (r + p);
            }
            lwp_NMI_average += NMI_temp;
            lwp_r_average += r;
            lwp_p_average += p;
            lwp_f_average += f;
        }
        lwp_costTime_average /= number_of_core;
        lwp_NMI_average /= number_of_core;
        lwp_nodes_correctly_classified_percentage /= (double) number_of_core;
        lwp_r_average /= number_of_core;
        lwp_p_average /= number_of_core;
        lwp_f_average /= number_of_core;
        lwp_rpf_average.add(lwp_r_average);
        lwp_rpf_average.add(lwp_p_average);
        lwp_rpf_average.add(lwp_f_average);
        write_results_into_files(lwp_algorithm_name, lwp_costTime_average,
                lwp_NMI_average, lwp_nodes_correctly_classified_percentage,
                lwp_rpf_average, lwp_detected_communities);
        Measurements measurements = new Measurements(lwp_algorithm_name,
                lwp_costTime_average, lwp_NMI_average,
                lwp_nodes_correctly_classified_percentage, lwp_rpf_average);
        return measurements;
    }
    *//* chen *//*
    public Measurements run_chen_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String chen_algorithm_name = "(chen).txt";
        double chen_NMI_average = 0;
        double chen_nodes_correctly_classified_percentage = 0;
        long chen_costTime_average = 0;
        ArrayList<Double> chen_rpf_average = new ArrayList<Double>();
        double chen_r_average = 0;
        double chen_p_average = 0;
        double chen_f_average = 0;
        ArrayList<SeedAndCommunity> chen_detected_communities = new ArrayList<SeedAndCommunity>();
        ChenAlgorithm chen = new ChenAlgorithm(n, m, adjacencyTable);
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = chen.run_chen(seed_id);
            SeedAndCommunity chen_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            chen_detected_communities.add(chen_seed_community);
            endTime_once = System.currentTimeMillis();
            long chen_costTime_once = endTime_once - startTime_once;
            chen_costTime_average += chen_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                chen_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            if (insection_size == 0)
            {
                r = 0;
                p = 0;
                f = 0;
            } else
            {
                r = (double) insection_size / (double) partitionR.get(0).size();
                p = (double) insection_size / (double) partitionF.get(0).size();
                f = 2 * r * p / (r + p);
            }
            chen_NMI_average += NMI_temp;
            chen_r_average += r;
            chen_p_average += p;
            chen_f_average += f;
        }
        chen_costTime_average /= number_of_core;
        chen_NMI_average /= number_of_core;
        chen_nodes_correctly_classified_percentage /= (double) number_of_core;
        chen_r_average /= number_of_core;
        chen_p_average /= number_of_core;
        chen_f_average /= number_of_core;
        chen_rpf_average.add(chen_r_average);
        chen_rpf_average.add(chen_p_average);
        chen_rpf_average.add(chen_f_average);
        write_results_into_files(chen_algorithm_name, chen_costTime_average,
                chen_NMI_average, chen_nodes_correctly_classified_percentage,
                chen_rpf_average, chen_detected_communities);
        Measurements measurements = new Measurements(chen_algorithm_name,
                chen_costTime_average, chen_NMI_average,
                chen_nodes_correctly_classified_percentage, chen_rpf_average);
        return measurements;
    }
    *//* ls *//*
    public Measurements run_ls_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String ls_algorithm_name = "(ls).txt";
        double ls_NMI_average = 0;
        double ls_nodes_correctly_classified_percentage = 0;
        long ls_costTime_average = 0;
        ArrayList<Double> ls_rpf_average = new ArrayList<Double>();
        double ls_r_average = 0;
        double ls_p_average = 0;
        double ls_f_average = 0;
        ArrayList<SeedAndCommunity> ls_detected_communities = new ArrayList<SeedAndCommunity>();
        LSAlgorithm ls = new LSAlgorithm(n, m, adjacencyTable);
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = ls.run_ls(seed_id);
            endTime_once = System.currentTimeMillis();
            SeedAndCommunity ls_seed_community = new SeedAndCommunity(seed_id,
                    partitionF.get(0));
            ls_detected_communities.add(ls_seed_community);
            long ls_costTime_once = endTime_once - startTime_once;
            ls_costTime_average += ls_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                ls_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            if (insection_size == 0)
            {
                r = 0;
                p = 0;
                f = 0;
            } else
            {
                r = (double) insection_size / (double) partitionR.get(0).size();
                p = (double) insection_size / (double) partitionF.get(0).size();
                f = 2 * r * p / (r + p);
            }
            ls_NMI_average += NMI_temp;
            ls_r_average += r;
            ls_p_average += p;
            ls_f_average += f;
        }
        ls_costTime_average /= number_of_core;
        ls_NMI_average /= number_of_core;
        ls_nodes_correctly_classified_percentage /= (double) number_of_core;
        ls_r_average /= number_of_core;
        ls_p_average /= number_of_core;
        ls_f_average /= number_of_core;
        ls_rpf_average.add(ls_r_average);
        ls_rpf_average.add(ls_p_average);
        ls_rpf_average.add(ls_f_average);
        write_results_into_files(ls_algorithm_name, ls_costTime_average,
                ls_NMI_average, ls_nodes_correctly_classified_percentage,
                ls_rpf_average, ls_detected_communities);
        Measurements measurements = new Measurements(ls_algorithm_name,
                ls_costTime_average, ls_NMI_average,
                ls_nodes_correctly_classified_percentage, ls_rpf_average);
        return measurements;
    }
    *//* lcd *//*
    public Measurements run_lcd_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String lcd_algorithm_name = "(lcd).txt";
        double lcd_NMI_average = 0;
        double lcd_nodes_correctly_classified_percentage = 0;
        long lcd_costTime_average = 0;
        ArrayList<Double> lcd_rpf_average = new ArrayList<Double>();
        double lcd_r_average = 0;
        double lcd_p_average = 0;
        double lcd_f_average = 0;
        ArrayList<SeedAndCommunity> lcd_detected_communities = new ArrayList<SeedAndCommunity>();
        LCDAlgorithm lcd = new LCDAlgorithm(n, m, adjacencyTable);
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionFS = lcd.run_lcd(seed_id);
            endTime_once = System.currentTimeMillis();
            long lcd_costTime_once = endTime_once - startTime_once;
            lcd_costTime_average += lcd_costTime_once;
            double lcd_NMI_once_average = 0;
            double lcd_r_once_average = 0;
            double lcd_p_once_average = 0;
            double lcd_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF = partitionFS
                    .iterator(); iter_partitionF.hasNext();)
            {
                partitionF = iter_partitionF.next();
                SeedAndCommunity lcd_seed_community = new SeedAndCommunity(
                        seed_id, partitionF.get(0));
                lcd_detected_communities.add(lcd_seed_community);
                NMI_temp = NMI.NMIPartitionUnoverLap(partitionF, partitionR,
                        this.n);
                insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                if (insection_size == 0)
                {
                    r = 0;
                    p = 0;
                    f = 0;
                } else
                {
                    r = (double) insection_size
                            / (double) partitionR.get(0).size();
                    p = (double) insection_size
                            / (double) partitionF.get(0).size();
                    f = 2 * r * p / (r + p);
                }
                lcd_NMI_once_average += NMI_temp;
                lcd_r_once_average += r;
                lcd_p_once_average += p;
                lcd_f_once_average += f;
            }
            lcd_NMI_once_average /= partitionFS.size();
            lcd_r_once_average /= partitionFS.size();
            lcd_p_once_average /= partitionFS.size();
            lcd_f_once_average /= partitionFS.size();
            lcd_nodes_correctly_classified_percentage++;
            lcd_NMI_average += lcd_NMI_once_average;
            lcd_r_average += lcd_r_once_average;
            lcd_p_average += lcd_p_once_average;
            lcd_f_average += lcd_f_once_average;
        }
        lcd_costTime_average /= number_of_core;
        lcd_NMI_average /= number_of_core;
        lcd_nodes_correctly_classified_percentage /= (double) number_of_core;
        lcd_r_average /= number_of_core;
        lcd_p_average /= number_of_core;
        lcd_f_average /= number_of_core;
        lcd_rpf_average.add(lcd_r_average);
        lcd_rpf_average.add(lcd_p_average);
        lcd_rpf_average.add(lcd_f_average);
        write_results_into_files(lcd_algorithm_name, lcd_costTime_average,
                lcd_NMI_average, lcd_nodes_correctly_classified_percentage,
                lcd_rpf_average, lcd_detected_communities);
        Measurements measurements = new Measurements(lcd_algorithm_name,
                lcd_costTime_average, lcd_NMI_average,
                lcd_nodes_correctly_classified_percentage, lcd_rpf_average);
        return measurements;
    }
    *//*********************************************************************************//*
 *//* is_degree *//*
    public Measurements run_is_degree_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String is_degree_algorithm_name = "(is_degree).txt";
        double is_degree_NMI_average = 0;
        double is_degree_nodes_correctly_classified_percentage = 0;
        long is_degree_costTime_average = 0;
        ArrayList<Double> is_degree_rpf_average = new ArrayList<Double>();
        double is_degree_r_average = 0;
        double is_degree_p_average = 0;
        double is_degree_f_average = 0;
        ArrayList<SeedAndCommunity> is_degree_detected_communities = new ArrayList<SeedAndCommunity>();
//        IsAlgorithm is_degree = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = is_degree.run_is(seed_id);
            SeedAndCommunity is_degree_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            is_degree_detected_communities.add(is_degree_seed_community);
            endTime_once = System.currentTimeMillis();
            long is_degree_costTime_once = endTime_once - startTime_once;
            is_degree_costTime_average += is_degree_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                is_degree_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            is_degree_NMI_average += NMI_temp;
            is_degree_r_average += r;
            is_degree_p_average += p;
            is_degree_f_average += f;
        }
        is_degree_costTime_average /= number_of_core;
        is_degree_NMI_average /= number_of_core;
        is_degree_nodes_correctly_classified_percentage /= (double) number_of_core;
        is_degree_r_average /= number_of_core;
        is_degree_p_average /= number_of_core;
        is_degree_f_average /= number_of_core;
        is_degree_rpf_average.add(is_degree_r_average);
        is_degree_rpf_average.add(is_degree_p_average);
        is_degree_rpf_average.add(is_degree_f_average);
        write_results_into_files(is_degree_algorithm_name, is_degree_costTime_average,
                is_degree_NMI_average, is_degree_nodes_correctly_classified_percentage,
                is_degree_rpf_average, is_degree_detected_communities);
        Measurements measurements = new Measurements(is_degree_algorithm_name,
                is_degree_costTime_average, is_degree_NMI_average,
                is_degree_nodes_correctly_classified_percentage, is_degree_rpf_average);
        return measurements;
    }
    *//* is_kcore *//*
    public Measurements run_is_kcore_on_artificial_network_core_only(
            TreeSet<Integer> cores) throws IOException
    {
        int number_of_core = cores.size();
        String is_kcore_algorithm_name = "(is_kcore).txt";
        double is_kcore_NMI_average = 0;
        double is_kcore_nodes_correctly_classified_percentage = 0;
        long is_kcore_costTime_average = 0;
        ArrayList<Double> is_kcore_rpf_average = new ArrayList<Double>();
        double is_kcore_r_average = 0;
        double is_kcore_p_average = 0;
        double is_kcore_f_average = 0;
        ArrayList<SeedAndCommunity> is_kcore_detected_communities = new ArrayList<SeedAndCommunity>();
        IsAlgorithm is_kcore = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        for (Iterator<Integer> iter_core = cores.iterator(); iter_core
                .hasNext();)
        {
            Integer seed_id = iter_core.next();
            // for (int seed_id = 0; seed_id < this.n; seed_id++)
            // {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = is_kcore.run_is(seed_id);
            SeedAndCommunity is_kcore_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            is_kcore_detected_communities.add(is_kcore_seed_community);
            endTime_once = System.currentTimeMillis();
            long is_kcore_costTime_once = endTime_once - startTime_once;
            is_kcore_costTime_average += is_kcore_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                is_kcore_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            is_kcore_NMI_average += NMI_temp;
            is_kcore_r_average += r;
            is_kcore_p_average += p;
            is_kcore_f_average += f;
        }
        is_kcore_costTime_average /= number_of_core;
        is_kcore_NMI_average /= number_of_core;
        is_kcore_nodes_correctly_classified_percentage /= (double) number_of_core;
        is_kcore_r_average /= number_of_core;
        is_kcore_p_average /= number_of_core;
        is_kcore_f_average /= number_of_core;
        is_kcore_rpf_average.add(is_kcore_r_average);
        is_kcore_rpf_average.add(is_kcore_p_average);
        is_kcore_rpf_average.add(is_kcore_f_average);
        write_results_into_files(is_kcore_algorithm_name, is_kcore_costTime_average,
                is_kcore_NMI_average, is_kcore_nodes_correctly_classified_percentage,
                is_kcore_rpf_average, is_kcore_detected_communities);
        Measurements measurements = new Measurements(is_kcore_algorithm_name,
                is_kcore_costTime_average, is_kcore_NMI_average,
                is_kcore_nodes_correctly_classified_percentage, is_kcore_rpf_average);
        return measurements;
    }
    *//*********************************************************************************//*
 *//* fifth *//*
    public Measurements run_fifth_on_artificial_network() throws IOException
    {
        String fifth_algorithm_name = "(fifth).txt";
        double fifth_NMI_average = 0;
        double fifth_nodes_correctly_classified_percentage = 0;
        long fifth_costTime_average = 0;
        ArrayList<Double> fifth_rpf_average = new ArrayList<Double>();
        double fifth_r_average = 0;
        double fifth_p_average = 0;
        double fifth_f_average = 0;
        ArrayList<SeedAndCommunity> fifth_detected_communities = new ArrayList<SeedAndCommunity>();
        ArrayList<NodeImportance> nodes_importance = calculate_node_importance_for_all_nodes();
        FifthAlgorithm fifth = new FifthAlgorithm(n, m, adjacencyTable,
                nodes_importance);
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = fifth.run_fifth(seed_id);
            SeedAndCommunity fifth_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            fifth_detected_communities.add(fifth_seed_community);
            endTime_once = System.currentTimeMillis();
            long fifth_costTime_once = endTime_once - startTime_once;
            fifth_costTime_average += fifth_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                fifth_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            fifth_NMI_average += NMI_temp;
            fifth_r_average += r;
            fifth_p_average += p;
            fifth_f_average += f;
        }
        fifth_costTime_average /= n;
        fifth_NMI_average /= n;
        fifth_nodes_correctly_classified_percentage /= (double) n;
        fifth_r_average /= n;
        fifth_p_average /= n;
        fifth_f_average /= n;
        fifth_rpf_average.add(fifth_r_average);
        fifth_rpf_average.add(fifth_p_average);
        fifth_rpf_average.add(fifth_f_average);
        write_results_into_files(fifth_algorithm_name, fifth_costTime_average,
                fifth_NMI_average, fifth_nodes_correctly_classified_percentage,
                fifth_rpf_average, fifth_detected_communities);
        Measurements measurements = new Measurements(fifth_algorithm_name,
                fifth_costTime_average, fifth_NMI_average,
                fifth_nodes_correctly_classified_percentage, fifth_rpf_average);
        return measurements;
    }
    *//* clauset *//*
    public Measurements run_clauset_on_artificial_network() throws IOException
    {
        String clauset_algorithm_name = "(clauset).txt";
        double clauset_NMI_average = 0;
        double clauset_nodes_correctly_classified_percentage = 0;
        long clauset_costTime_average = 0;
        ArrayList<Double> clauset_rpf_average = new ArrayList<Double>();
        double clauset_r_average = 0;
        double clauset_p_average = 0;
        double clauset_f_average = 0;
        ArrayList<SeedAndCommunity> clauset_detected_communities = new ArrayList<SeedAndCommunity>();
        ClausetAlgorithm clauset = new ClausetAlgorithm(n, m, adjacencyTable);
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            // System.out.println("clauset");
            startTime_once = System.currentTimeMillis();
            partitionF = clauset.run_clauset(seed_id);
            SeedAndCommunity clauset_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            clauset_detected_communities.add(clauset_seed_community);
            endTime_once = System.currentTimeMillis();
            long clauset_costTime_once = endTime_once - startTime_once;
            clauset_costTime_average += clauset_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                clauset_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            clauset_NMI_average += NMI_temp;
            clauset_r_average += r;
            clauset_p_average += p;
            clauset_f_average += f;
        }
        clauset_costTime_average /= n;
        clauset_NMI_average /= n;
        clauset_nodes_correctly_classified_percentage /= (double) n;
        clauset_r_average /= n;
        clauset_p_average /= n;
        clauset_f_average /= n;
        clauset_rpf_average.add(clauset_r_average);
        clauset_rpf_average.add(clauset_p_average);
        clauset_rpf_average.add(clauset_f_average);
        write_results_into_files(clauset_algorithm_name,
                clauset_costTime_average, clauset_NMI_average,
                clauset_nodes_correctly_classified_percentage,
                clauset_rpf_average, clauset_detected_communities);
        Measurements measurements = new Measurements(clauset_algorithm_name,
                clauset_costTime_average, clauset_NMI_average,
                clauset_nodes_correctly_classified_percentage,
                clauset_rpf_average);
        return measurements;
    }
    *//* lwp *//*
    public Measurements run_lwp_on_artificial_network() throws IOException
    {
        String lwp_algorithm_name = "(lwp).txt";
        double lwp_NMI_average = 0;
        double lwp_nodes_correctly_classified_percentage = 0;
        long lwp_costTime_average = 0;
        ArrayList<Double> lwp_rpf_average = new ArrayList<Double>();
        double lwp_r_average = 0;
        double lwp_p_average = 0;
        double lwp_f_average = 0;
        ArrayList<SeedAndCommunity> lwp_detected_communities = new ArrayList<SeedAndCommunity>();
        LWPAlgorithm lwp = new LWPAlgorithm(n, m, adjacencyTable);
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = lwp.run_lwp(seed_id);
            SeedAndCommunity lwp_seed_community = new SeedAndCommunity(seed_id,
                    partitionF.get(0));
            lwp_detected_communities.add(lwp_seed_community);
            endTime_once = System.currentTimeMillis();
            long lwp_costTime_once = endTime_once - startTime_once;
            lwp_costTime_average += lwp_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                lwp_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            if (insection_size == 0)
            {
                r = 0;
                p = 0;
                f = 0;
            } else
            {
                r = (double) insection_size / (double) partitionR.get(0).size();
                p = (double) insection_size / (double) partitionF.get(0).size();
                f = 2 * r * p / (r + p);
            }
            lwp_NMI_average += NMI_temp;
            lwp_r_average += r;
            lwp_p_average += p;
            lwp_f_average += f;
        }
        lwp_costTime_average /= n;
        lwp_NMI_average /= n;
        lwp_nodes_correctly_classified_percentage /= (double) n;
        lwp_r_average /= n;
        lwp_p_average /= n;
        lwp_f_average /= n;
        lwp_rpf_average.add(lwp_r_average);
        lwp_rpf_average.add(lwp_p_average);
        lwp_rpf_average.add(lwp_f_average);
        write_results_into_files(lwp_algorithm_name, lwp_costTime_average,
                lwp_NMI_average, lwp_nodes_correctly_classified_percentage,
                lwp_rpf_average, lwp_detected_communities);
        Measurements measurements = new Measurements(lwp_algorithm_name,
                lwp_costTime_average, lwp_NMI_average,
                lwp_nodes_correctly_classified_percentage, lwp_rpf_average);
        return measurements;
    }
    *//* chen *//*
    public Measurements run_chen_on_artificial_network() throws IOException
    {
        String chen_algorithm_name = "(chen).txt";
        double chen_NMI_average = 0;
        double chen_nodes_correctly_classified_percentage = 0;
        long chen_costTime_average = 0;
        ArrayList<Double> chen_rpf_average = new ArrayList<Double>();
        double chen_r_average = 0;
        double chen_p_average = 0;
        double chen_f_average = 0;
        ArrayList<SeedAndCommunity> chen_detected_communities = new ArrayList<SeedAndCommunity>();
        ChenAlgorithm chen = new ChenAlgorithm(n, m, adjacencyTable);
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = chen.run_chen(seed_id);
            SeedAndCommunity chen_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            chen_detected_communities.add(chen_seed_community);
            endTime_once = System.currentTimeMillis();
            long chen_costTime_once = endTime_once - startTime_once;
            chen_costTime_average += chen_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                chen_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            if (insection_size == 0)
            {
                r = 0;
                p = 0;
                f = 0;
            } else
            {
                r = (double) insection_size / (double) partitionR.get(0).size();
                p = (double) insection_size / (double) partitionF.get(0).size();
                f = 2 * r * p / (r + p);
            }
            chen_NMI_average += NMI_temp;
            chen_r_average += r;
            chen_p_average += p;
            chen_f_average += f;
        }
        chen_costTime_average /= n;
        chen_NMI_average /= n;
        chen_nodes_correctly_classified_percentage /= (double) n;
        chen_r_average /= n;
        chen_p_average /= n;
        chen_f_average /= n;
        chen_rpf_average.add(chen_r_average);
        chen_rpf_average.add(chen_p_average);
        chen_rpf_average.add(chen_f_average);
        write_results_into_files(chen_algorithm_name, chen_costTime_average,
                chen_NMI_average, chen_nodes_correctly_classified_percentage,
                chen_rpf_average, chen_detected_communities);
        Measurements measurements = new Measurements(chen_algorithm_name,
                chen_costTime_average, chen_NMI_average,
                chen_nodes_correctly_classified_percentage, chen_rpf_average);
        return measurements;
    }
    *//* ls *//*
    public Measurements run_ls_on_artificial_network() throws IOException
    {
        String ls_algorithm_name = "(ls).txt";
        double ls_NMI_average = 0;
        double ls_nodes_correctly_classified_percentage = 0;
        long ls_costTime_average = 0;
        ArrayList<Double> ls_rpf_average = new ArrayList<Double>();
        double ls_r_average = 0;
        double ls_p_average = 0;
        double ls_f_average = 0;
        ArrayList<SeedAndCommunity> ls_detected_communities = new ArrayList<SeedAndCommunity>();
        LSAlgorithm ls = new LSAlgorithm(n, m, adjacencyTable);
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = ls.run_ls(seed_id);
            endTime_once = System.currentTimeMillis();
            SeedAndCommunity ls_seed_community = new SeedAndCommunity(seed_id,
                    partitionF.get(0));
            ls_detected_communities.add(ls_seed_community);
            long ls_costTime_once = endTime_once - startTime_once;
            ls_costTime_average += ls_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                ls_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            if (insection_size == 0)
            {
                r = 0;
                p = 0;
                f = 0;
            } else
            {
                r = (double) insection_size / (double) partitionR.get(0).size();
                p = (double) insection_size / (double) partitionF.get(0).size();
                f = 2 * r * p / (r + p);
            }
            ls_NMI_average += NMI_temp;
            ls_r_average += r;
            ls_p_average += p;
            ls_f_average += f;
        }
        ls_costTime_average /= n;
        ls_NMI_average /= n;
        ls_nodes_correctly_classified_percentage /= (double) n;
        ls_r_average /= n;
        ls_p_average /= n;
        ls_f_average /= n;
        ls_rpf_average.add(ls_r_average);
        ls_rpf_average.add(ls_p_average);
        ls_rpf_average.add(ls_f_average);
        write_results_into_files(ls_algorithm_name, ls_costTime_average,
                ls_NMI_average, ls_nodes_correctly_classified_percentage,
                ls_rpf_average, ls_detected_communities);
        Measurements measurements = new Measurements(ls_algorithm_name,
                ls_costTime_average, ls_NMI_average,
                ls_nodes_correctly_classified_percentage, ls_rpf_average);
        return measurements;
    }
    *//* lcd *//*
    public Measurements run_lcd_on_artificial_network() throws IOException
    {
        String lcd_algorithm_name = "(lcd).txt";
        double lcd_NMI_average = 0;
        double lcd_nodes_correctly_classified_percentage = 0;
        long lcd_costTime_average = 0;
        ArrayList<Double> lcd_rpf_average = new ArrayList<Double>();
        double lcd_r_average = 0;
        double lcd_p_average = 0;
        double lcd_f_average = 0;
        ArrayList<SeedAndCommunity> lcd_detected_communities = new ArrayList<SeedAndCommunity>();
        LCDAlgorithm lcd = new LCDAlgorithm(n, m, adjacencyTable);
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<ArrayList<Integer>>> partitionFS = new ArrayList<ArrayList<ArrayList<Integer>>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionFS = lcd.run_lcd(seed_id);
            endTime_once = System.currentTimeMillis();
            long lcd_costTime_once = endTime_once - startTime_once;
            lcd_costTime_average += lcd_costTime_once;
            double lcd_NMI_once_average = 0;
            double lcd_r_once_average = 0;
            double lcd_p_once_average = 0;
            double lcd_f_once_average = 0;
            for (Iterator<ArrayList<ArrayList<Integer>>> iter_partitionF = partitionFS
                    .iterator(); iter_partitionF.hasNext();)
            {
                partitionF = iter_partitionF.next();
                SeedAndCommunity lcd_seed_community = new SeedAndCommunity(
                        seed_id, partitionF.get(0));
                lcd_detected_communities.add(lcd_seed_community);
                NMI_temp = NMI.NMIPartitionUnoverLap(partitionF, partitionR,
                        this.n);
                insection_size = get_insection_size(partitionF.get(0),
                        partitionR.get(0));
                if (insection_size == 0)
                {
                    r = 0;
                    p = 0;
                    f = 0;
                } else
                {
                    r = (double) insection_size
                            / (double) partitionR.get(0).size();
                    p = (double) insection_size
                            / (double) partitionF.get(0).size();
                    f = 2 * r * p / (r + p);
                }
                lcd_NMI_once_average += NMI_temp;
                lcd_r_once_average += r;
                lcd_p_once_average += p;
                lcd_f_once_average += f;
            }
            lcd_NMI_once_average /= partitionFS.size();
            lcd_r_once_average /= partitionFS.size();
            lcd_p_once_average /= partitionFS.size();
            lcd_f_once_average /= partitionFS.size();
            lcd_nodes_correctly_classified_percentage++;
            lcd_NMI_average += lcd_NMI_once_average;
            lcd_r_average += lcd_r_once_average;
            lcd_p_average += lcd_p_once_average;
            lcd_f_average += lcd_f_once_average;
        }
        lcd_costTime_average /= n;
        lcd_NMI_average /= n;
        lcd_nodes_correctly_classified_percentage /= (double) n;
        lcd_r_average /= n;
        lcd_p_average /= n;
        lcd_f_average /= n;
        lcd_rpf_average.add(lcd_r_average);
        lcd_rpf_average.add(lcd_p_average);
        lcd_rpf_average.add(lcd_f_average);
        write_results_into_files(lcd_algorithm_name, lcd_costTime_average,
                lcd_NMI_average, lcd_nodes_correctly_classified_percentage,
                lcd_rpf_average, lcd_detected_communities);
        Measurements measurements = new Measurements(lcd_algorithm_name,
                lcd_costTime_average, lcd_NMI_average,
                lcd_nodes_correctly_classified_percentage, lcd_rpf_average);
        return measurements;
    }
    *//* is_degree *//*
    public Measurements run_is_degree_on_artificial_network() throws IOException
    {
        String is_degree_algorithm_name = "(is_degree).txt";
        double is_degree_NMI_average = 0;
        double is_degree_nodes_correctly_classified_percentage = 0;
        long is_degree_costTime_average = 0;
        ArrayList<Double> is_degree_rpf_average = new ArrayList<Double>();
        double is_degree_r_average = 0;
        double is_degree_p_average = 0;
        double is_degree_f_average = 0;
        ArrayList<SeedAndCommunity> is_degree_detected_communities = new ArrayList<SeedAndCommunity>();
        IsAlgorithm is_degree = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = is_degree.run_is(seed_id);
            SeedAndCommunity is_degree_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            is_degree_detected_communities.add(is_degree_seed_community);
            endTime_once = System.currentTimeMillis();
            long is_degree_costTime_once = endTime_once - startTime_once;
            is_degree_costTime_average += is_degree_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                is_degree_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            is_degree_NMI_average += NMI_temp;
            is_degree_r_average += r;
            is_degree_p_average += p;
            is_degree_f_average += f;
        }
        is_degree_costTime_average /= n;
        is_degree_NMI_average /= n;
        is_degree_nodes_correctly_classified_percentage /= (double) n;
        is_degree_r_average /= n;
        is_degree_p_average /= n;
        is_degree_f_average /= n;
        is_degree_rpf_average.add(is_degree_r_average);
        is_degree_rpf_average.add(is_degree_p_average);
        is_degree_rpf_average.add(is_degree_f_average);
        write_results_into_files(is_degree_algorithm_name, is_degree_costTime_average,
                is_degree_NMI_average, is_degree_nodes_correctly_classified_percentage,
                is_degree_rpf_average, is_degree_detected_communities);
        Measurements measurements = new Measurements(is_degree_algorithm_name,
                is_degree_costTime_average, is_degree_NMI_average,
                is_degree_nodes_correctly_classified_percentage, is_degree_rpf_average);
        return measurements;
    }
    *//* is_kcore *//*
    public Measurements run_is_kcore_on_artificial_network() throws IOException
    {
        String is_kcore_algorithm_name = "(is_kcore).txt";
        double is_kcore_NMI_average = 0;
        double is_kcore_nodes_correctly_classified_percentage = 0;
        long is_kcore_costTime_average = 0;
        ArrayList<Double> is_kcore_rpf_average = new ArrayList<Double>();
        double is_kcore_r_average = 0;
        double is_kcore_p_average = 0;
        double is_kcore_f_average = 0;
        ArrayList<SeedAndCommunity> is_kcore_detected_communities = new ArrayList<SeedAndCommunity>();
        IsAlgorithm is_kcore = new IsAlgorithm(n, m, adjacencyTable,"yes","yes");
        for (int seed_id = 0; seed_id < this.n; seed_id++)
        {
            long startTime_once = 0;
            long endTime_once = 0;
            ArrayList<ArrayList<Integer>> partitionF = new ArrayList<ArrayList<Integer>>();
            ArrayList<ArrayList<Integer>> partitionR = get_real_partition_containing_seed(seed_id);
            int insection_size = 0;
            double NMI_temp = 0;
            double r = 0;
            double p = 0;
            double f = 0;
            startTime_once = System.currentTimeMillis();
            partitionF = is_kcore.run_is(seed_id);
            SeedAndCommunity is_kcore_seed_community = new SeedAndCommunity(
                    seed_id, partitionF.get(0));
            is_kcore_detected_communities.add(is_kcore_seed_community);
            endTime_once = System.currentTimeMillis();
            long is_kcore_costTime_once = endTime_once - startTime_once;
            is_kcore_costTime_average += is_kcore_costTime_once;
            if (partitionF.get(0).contains(seed_id))
            {
                is_kcore_nodes_correctly_classified_percentage++;
            }
            NMI_temp = NMI
                    .NMIPartitionUnoverLap(partitionF, partitionR, this.n);
            insection_size = get_insection_size(partitionF.get(0),
                    partitionR.get(0));
            r = (double) insection_size / (double) partitionR.get(0).size();
            p = (double) insection_size / (double) partitionF.get(0).size();
            f = 2 * r * p / (r + p);
            is_kcore_NMI_average += NMI_temp;
            is_kcore_r_average += r;
            is_kcore_p_average += p;
            is_kcore_f_average += f;
        }
        is_kcore_costTime_average /= n;
        is_kcore_NMI_average /= n;
        is_kcore_nodes_correctly_classified_percentage /= (double) n;
        is_kcore_r_average /= n;
        is_kcore_p_average /= n;
        is_kcore_f_average /= n;
        is_kcore_rpf_average.add(is_kcore_r_average);
        is_kcore_rpf_average.add(is_kcore_p_average);
        is_kcore_rpf_average.add(is_kcore_f_average);
        write_results_into_files(is_kcore_algorithm_name, is_kcore_costTime_average,
                is_kcore_NMI_average, is_kcore_nodes_correctly_classified_percentage,
                is_kcore_rpf_average, is_kcore_detected_communities);
        Measurements measurements = new Measurements(is_kcore_algorithm_name,
                is_kcore_costTime_average, is_kcore_NMI_average,
                is_kcore_nodes_correctly_classified_percentage, is_kcore_rpf_average);
        return measurements;
    }
    public ArrayList<Measurements> run_methords_on_artificial_network()
            throws IOException
    {
        initial_paramaters();
        Measurements fifth_measurements = run_fifth_on_artificial_network();
        Measurements clauset_measurements = run_clauset_on_artificial_network();
        Measurements lwp_measurements = run_lwp_on_artificial_network();
        Measurements chen_measurements = run_chen_on_artificial_network();
        Measurements ls_measurements = run_ls_on_artificial_network();
        Measurements lcd_measurements = run_lcd_on_artificial_network();
        Measurements is_degree_measurements = run_is_degree_on_artificial_network();
        Measurements is_kcore_measurements = run_is_kcore_on_artificial_network();
        ArrayList<Measurements> measurements_on_one_data_set = new ArrayList<Measurements>();
        measurements_on_one_data_set.add(fifth_measurements);
        measurements_on_one_data_set.add(clauset_measurements);
        measurements_on_one_data_set.add(lwp_measurements);
        measurements_on_one_data_set.add(chen_measurements);
        measurements_on_one_data_set.add(ls_measurements);
        measurements_on_one_data_set.add(lcd_measurements);
        measurements_on_one_data_set.add(is_degree_measurements);
        measurements_on_one_data_set.add(is_kcore_measurements);
        return measurements_on_one_data_set;
    }
    *//*****************************************************************************************//*
    public ArrayList<Measurements> run_methords_on_artificial_network_core_only()
            throws IOException
    {
        initial_paramaters();
        TreeSet<Integer> cores = get_max_degree_nodes_from_real_communities();
        Measurements fifth_measurements = run_fifth_on_artificial_network_core_only(cores);
        Measurements clauset_measurements = run_clauset_on_artificial_network_core_only(cores);
        Measurements lwp_measurements = run_lwp_on_artificial_network_core_only(cores);
        Measurements chen_measurements = run_chen_on_artificial_network_core_only(cores);
        Measurements ls_measurements = run_ls_on_artificial_network_core_only(cores);
//		Measurements lcd_measurements = run_lcd_on_artificial_network_core_only(cores);
        Measurements is_degree_measurements = run_is_degree_on_artificial_network_core_only(cores);
        Measurements is_kcore_measurements = run_is_kcore_on_artificial_network_core_only(cores);
        ArrayList<Measurements> measurements_on_one_data_set = new ArrayList<Measurements>();
        measurements_on_one_data_set.add(fifth_measurements);
        measurements_on_one_data_set.add(clauset_measurements);
        measurements_on_one_data_set.add(lwp_measurements);
        measurements_on_one_data_set.add(chen_measurements);
        measurements_on_one_data_set.add(ls_measurements);
//		measurements_on_one_data_set.add(lcd_measurements);
        measurements_on_one_data_set.add(is_degree_measurements);
        measurements_on_one_data_set.add(is_kcore_measurements);
        return measurements_on_one_data_set;
    }
    public static void main(String[] args) throws IOException
    {
        String file_path_static = "F:/tests on artificial networks/mu(500)v1/";
        String network = ".txt";
        String community = ".clu";
        int number_of_file = 1;
        int number_of_network = 2;
        for (int i = 1; i <= number_of_file; i++)
        {
            String file_path = file_path_static + String.valueOf(i) + "/";
            ArrayList<Measurements> measurements = new ArrayList<Measurements>();
            *//* fifth *//*
            double fifth_average_NMI = 0;
            ArrayList<Double> fifth_average_RPF = new ArrayList<Double>();
            double fifth_average_r = 0;
            double fifth_average_p = 0;
            double fifth_average_f = 0;
            long fifth_average_timecost = 0;
            double fifth_average_nodes_correctly_classified_percentage = 0;
            *//* clauset *//*
            double clauset_average_NMI = 0;
            ArrayList<Double> clauset_average_RPF = new ArrayList<Double>();
            double clauset_average_r = 0;
            double clauset_average_p = 0;
            double clauset_average_f = 0;
            long clauset_average_timecost = 0;
            double clauset_average_nodes_correctly_classified_percentage = 0;
            *//* lwp *//*
            double lwp_average_NMI = 0;
            ArrayList<Double> lwp_average_RPF = new ArrayList<Double>();
            double lwp_average_r = 0;
            double lwp_average_p = 0;
            double lwp_average_f = 0;
            long lwp_average_timecost = 0;
            double lwp_average_nodes_correctly_classified_percentage = 0;
            *//* chen *//*
            double chen_average_NMI = 0;
            ArrayList<Double> chen_average_RPF = new ArrayList<Double>();
            double chen_average_r = 0;
            double chen_average_p = 0;
            double chen_average_f = 0;
            long chen_average_timecost = 0;
            double chen_average_nodes_correctly_classified_percentage = 0;
            *//* ls *//*
            double ls_average_NMI = 0;
            ArrayList<Double> ls_average_RPF = new ArrayList<Double>();
            double ls_average_r = 0;
            double ls_average_p = 0;
            double ls_average_f = 0;
            long ls_average_timecost = 0;
            double ls_average_nodes_correctly_classified_percentage = 0;
            *//* lcd *//*
            double lcd_average_NMI = 0;
            ArrayList<Double> lcd_average_RPF = new ArrayList<Double>();
            double lcd_average_r = 0;
            double lcd_average_p = 0;
            double lcd_average_f = 0;
            long lcd_average_timecost = 0;
            double lcd_average_nodes_correctly_classified_percentage = 0;
            *//* is_degree *//*
            double is_degree_average_NMI = 0;
            ArrayList<Double> is_degree_average_RPF = new ArrayList<Double>();
            double is_degree_average_r = 0;
            double is_degree_average_p = 0;
            double is_degree_average_f = 0;
            long is_degree_average_timecost = 0;
            double is_degree_average_nodes_correctly_classified_percentage = 0;
            *//* is_kcore *//*
            double is_kcore_average_NMI = 0;
            ArrayList<Double> is_kcore_average_RPF = new ArrayList<Double>();
            double is_kcore_average_r = 0;
            double is_kcore_average_p = 0;
            double is_kcore_average_f = 0;
            long is_kcore_average_timecost = 0;
            double is_kcore_average_nodes_correctly_classified_percentage = 0;
            for (int j = 1; j <= number_of_network; j++)
            {
                String file_name = String.valueOf(j);
                String file_path_network = file_path + file_name + network;
                String file_path_community = file_path + file_name + community;
                String file_path_results = file_path + file_name
                        + "_results.txt";
                System.out.println("file_path_network: " + file_path_network);
                System.out.println("file_path_community: "
                        + file_path_community);
                RunMethordsOnArtificialNetwork run_methords = new RunMethordsOnArtificialNetwork(
                        file_path_network, file_path_community,
                        file_path_results);
                // ArrayList<Measurements> measurements_on_one_data_set =
                // run_methords
                // .run_methords_on_artificial_network();
                ArrayList<Measurements> measurements_on_one_data_set = run_methords
                        .run_methords_on_artificial_network_core_only();
                *//* fifth *//*
                fifth_average_NMI += measurements_on_one_data_set.get(0).NMI_average;
                fifth_average_r += measurements_on_one_data_set.get(0).RPF_average
                        .get(0);
                fifth_average_p += measurements_on_one_data_set.get(0).RPF_average
                        .get(1);
                fifth_average_f += measurements_on_one_data_set.get(0).RPF_average
                        .get(2);
                fifth_average_timecost += measurements_on_one_data_set.get(0).costTime_average;
                fifth_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(0).nodes_correctly_classified_percentage;
                *//* clauset *//*
                clauset_average_NMI += measurements_on_one_data_set.get(1).NMI_average;
                clauset_average_r += measurements_on_one_data_set.get(1).RPF_average
                        .get(0);
                clauset_average_p += measurements_on_one_data_set.get(1).RPF_average
                        .get(1);
                clauset_average_f += measurements_on_one_data_set.get(1).RPF_average
                        .get(2);
                clauset_average_timecost += measurements_on_one_data_set.get(1).costTime_average;
                clauset_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(1).nodes_correctly_classified_percentage;
                *//* lwp *//*
                lwp_average_NMI += measurements_on_one_data_set.get(2).NMI_average;
                lwp_average_r += measurements_on_one_data_set.get(2).RPF_average
                        .get(0);
                lwp_average_p += measurements_on_one_data_set.get(2).RPF_average
                        .get(1);
                lwp_average_f += measurements_on_one_data_set.get(2).RPF_average
                        .get(2);
                lwp_average_timecost += measurements_on_one_data_set.get(2).costTime_average;
                lwp_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(2).nodes_correctly_classified_percentage;
                *//* chen *//*
                chen_average_NMI += measurements_on_one_data_set.get(3).NMI_average;
                chen_average_r += measurements_on_one_data_set.get(3).RPF_average
                        .get(0);
                chen_average_p += measurements_on_one_data_set.get(3).RPF_average
                        .get(1);
                chen_average_f += measurements_on_one_data_set.get(3).RPF_average
                        .get(2);
                chen_average_timecost += measurements_on_one_data_set.get(3).costTime_average;
                chen_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(3).nodes_correctly_classified_percentage;
                *//* ls *//*
                ls_average_NMI += measurements_on_one_data_set.get(4).NMI_average;
                ls_average_r += measurements_on_one_data_set.get(4).RPF_average
                        .get(0);
                ls_average_p += measurements_on_one_data_set.get(4).RPF_average
                        .get(1);
                ls_average_f += measurements_on_one_data_set.get(4).RPF_average
                        .get(2);
                ls_average_timecost += measurements_on_one_data_set.get(4).costTime_average;
                ls_average_nodes_correctly_classified_percentage = measurements_on_one_data_set
                        .get(4).nodes_correctly_classified_percentage;
                *//* lcd *//*
                lcd_average_NMI += measurements_on_one_data_set.get(5).NMI_average;
                lcd_average_r += measurements_on_one_data_set.get(5).RPF_average
                        .get(0);
                lcd_average_p += measurements_on_one_data_set.get(5).RPF_average
                        .get(1);
                lcd_average_f += measurements_on_one_data_set.get(5).RPF_average
                        .get(2);
                lcd_average_timecost += measurements_on_one_data_set.get(5).costTime_average;
                lcd_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(5).nodes_correctly_classified_percentage;
                *//* is_degree *//*
                is_degree_average_NMI += measurements_on_one_data_set.get(5).NMI_average;
                is_degree_average_r += measurements_on_one_data_set.get(5).RPF_average
                        .get(0);
                is_degree_average_p += measurements_on_one_data_set.get(5).RPF_average
                        .get(1);
                is_degree_average_f += measurements_on_one_data_set.get(5).RPF_average
                        .get(2);
                is_degree_average_timecost += measurements_on_one_data_set.get(5).costTime_average;
                is_degree_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(5).nodes_correctly_classified_percentage;
                *//* is_kcore *//*
                is_kcore_average_NMI += measurements_on_one_data_set.get(5).NMI_average;
                is_kcore_average_r += measurements_on_one_data_set.get(5).RPF_average
                        .get(0);
                is_kcore_average_p += measurements_on_one_data_set.get(5).RPF_average
                        .get(1);
                is_kcore_average_f += measurements_on_one_data_set.get(5).RPF_average
                        .get(2);
                is_kcore_average_timecost += measurements_on_one_data_set.get(5).costTime_average;
                is_kcore_average_nodes_correctly_classified_percentage += measurements_on_one_data_set
                        .get(5).nodes_correctly_classified_percentage;
            }
            *//* fifth *//*
            fifth_average_NMI /= number_of_network;
            fifth_average_r /= number_of_network;
            fifth_average_p /= number_of_network;
            fifth_average_f /= number_of_network;
            fifth_average_timecost /= number_of_network;
            fifth_average_nodes_correctly_classified_percentage /= number_of_network;
            fifth_average_RPF.add(fifth_average_r);
            fifth_average_RPF.add(fifth_average_p);
            fifth_average_RPF.add(fifth_average_f);
            *//* clauset *//*
            clauset_average_NMI /= number_of_network;
            clauset_average_r /= number_of_network;
            clauset_average_p /= number_of_network;
            clauset_average_f /= number_of_network;
            clauset_average_timecost /= number_of_network;
            clauset_average_nodes_correctly_classified_percentage /= number_of_network;
            clauset_average_RPF.add(clauset_average_r);
            clauset_average_RPF.add(clauset_average_p);
            clauset_average_RPF.add(clauset_average_f);
            *//* lwp *//*
            lwp_average_NMI /= number_of_network;
            lwp_average_r /= number_of_network;
            lwp_average_p /= number_of_network;
            lwp_average_f /= number_of_network;
            lwp_average_timecost /= number_of_network;
            lwp_average_nodes_correctly_classified_percentage /= number_of_network;
            lwp_average_RPF.add(lwp_average_r);
            lwp_average_RPF.add(lwp_average_p);
            lwp_average_RPF.add(lwp_average_f);
            *//* chen *//*
            chen_average_NMI /= number_of_network;
            chen_average_r /= number_of_network;
            chen_average_p /= number_of_network;
            chen_average_f /= number_of_network;
            chen_average_timecost /= number_of_network;
            chen_average_nodes_correctly_classified_percentage /= number_of_network;
            chen_average_RPF.add(chen_average_r);
            chen_average_RPF.add(chen_average_p);
            chen_average_RPF.add(chen_average_f);
            *//* ls *//*
            ls_average_NMI /= number_of_network;
            ls_average_r /= number_of_network;
            ls_average_p /= number_of_network;
            ls_average_f /= number_of_network;
            ls_average_timecost /= number_of_network;
            ls_average_nodes_correctly_classified_percentage /= number_of_network;
            ls_average_RPF.add(ls_average_r);
            ls_average_RPF.add(ls_average_p);
            ls_average_RPF.add(ls_average_f);
            *//* lcd *//*
            lcd_average_NMI /= number_of_network;
            lcd_average_r /= number_of_network;
            lcd_average_p /= number_of_network;
            lcd_average_f /= number_of_network;
            lcd_average_timecost /= number_of_network;
            lcd_average_nodes_correctly_classified_percentage /= number_of_network;
            lcd_average_RPF.add(lcd_average_r);
            lcd_average_RPF.add(lcd_average_p);
            lcd_average_RPF.add(lcd_average_f);
            *//* is_degree *//*
            is_degree_average_NMI /= number_of_network;
            is_degree_average_r /= number_of_network;
            is_degree_average_p /= number_of_network;
            is_degree_average_f /= number_of_network;
            is_degree_average_timecost /= number_of_network;
            is_degree_average_nodes_correctly_classified_percentage /= number_of_network;
            is_degree_average_RPF.add(is_degree_average_r);
            is_degree_average_RPF.add(is_degree_average_p);
            is_degree_average_RPF.add(is_degree_average_f);
            *//* is_kcore *//*
            is_kcore_average_NMI /= number_of_network;
            is_kcore_average_r /= number_of_network;
            is_kcore_average_p /= number_of_network;
            is_kcore_average_f /= number_of_network;
            is_kcore_average_timecost /= number_of_network;
            is_kcore_average_nodes_correctly_classified_percentage /= number_of_network;
            is_kcore_average_RPF.add(is_kcore_average_r);
            is_kcore_average_RPF.add(is_kcore_average_p);
            is_kcore_average_RPF.add(is_kcore_average_f);
            *//* measurements *//*
 *//* fifth *//*
            Measurements fifth_measurements = new Measurements();
            fifth_measurements.costTime_average = fifth_average_timecost;
            fifth_measurements.NMI_average = fifth_average_NMI;
            fifth_measurements.nodes_correctly_classified_percentage = fifth_average_nodes_correctly_classified_percentage;
            fifth_measurements.RPF_average = fifth_average_RPF;
            *//* clauset *//*
            Measurements clauset_measurements = new Measurements();
            clauset_measurements.costTime_average = clauset_average_timecost;
            clauset_measurements.NMI_average = clauset_average_NMI;
            clauset_measurements.nodes_correctly_classified_percentage = clauset_average_nodes_correctly_classified_percentage;
            clauset_measurements.RPF_average = clauset_average_RPF;
            *//* lwp *//*
            Measurements lwp_measurements = new Measurements();
            lwp_measurements.costTime_average = lwp_average_timecost;
            lwp_measurements.NMI_average = lwp_average_NMI;
            lwp_measurements.nodes_correctly_classified_percentage = lwp_average_nodes_correctly_classified_percentage;
            lwp_measurements.RPF_average = lwp_average_RPF;
            *//* chen *//*
            Measurements chen_measurements = new Measurements();
            chen_measurements.costTime_average = chen_average_timecost;
            chen_measurements.NMI_average = chen_average_NMI;
            chen_measurements.nodes_correctly_classified_percentage = chen_average_nodes_correctly_classified_percentage;
            chen_measurements.RPF_average = chen_average_RPF;
            *//* ls *//*
            Measurements ls_measurements = new Measurements();
            ls_measurements.costTime_average = ls_average_timecost;
            ls_measurements.NMI_average = ls_average_NMI;
            ls_measurements.nodes_correctly_classified_percentage = ls_average_nodes_correctly_classified_percentage;
            ls_measurements.RPF_average = ls_average_RPF;
            *//* lcd *//*
            Measurements lcd_measurements = new Measurements();
            lcd_measurements.costTime_average = lcd_average_timecost;
            lcd_measurements.NMI_average = lcd_average_NMI;
            lcd_measurements.nodes_correctly_classified_percentage = lcd_average_nodes_correctly_classified_percentage;
            lcd_measurements.RPF_average = lcd_average_RPF;
            *//* is_degree *//*
            Measurements is_degree_measurements = new Measurements();
            is_degree_measurements.costTime_average = is_degree_average_timecost;
            is_degree_measurements.NMI_average = is_degree_average_NMI;
            is_degree_measurements.nodes_correctly_classified_percentage = is_degree_average_nodes_correctly_classified_percentage;
            is_degree_measurements.RPF_average = is_degree_average_RPF;
            *//* is_kcore *//*
            Measurements is_kcore_measurements = new Measurements();
            is_kcore_measurements.costTime_average = is_kcore_average_timecost;
            is_kcore_measurements.NMI_average = is_kcore_average_NMI;
            is_kcore_measurements.nodes_correctly_classified_percentage = is_kcore_average_nodes_correctly_classified_percentage;
            is_kcore_measurements.RPF_average = is_kcore_average_RPF;
            *//* fifth,clauset,lwp,chen,ls,lcd,is_degree,is_kcore *//*
            measurements.add(fifth_measurements);
            measurements.add(clauset_measurements);
            measurements.add(lwp_measurements);
            measurements.add(chen_measurements);
            measurements.add(ls_measurements);
            measurements.add(lcd_measurements);
            measurements.add(is_degree_measurements);
            measurements.add(is_kcore_measurements);
            write_average_results_into_files(file_path, measurements);
        }
    }*/
}