package com.wangshenglong.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReadRealResults {

    public MethodResult ReadRealResults(String file_path_result, String method_name, String dataset_name) {
        File file = new File(file_path_result);
        BufferedReader reader = null;
        MethodResult RealResult = new MethodResult();
        RealResult.setMethod_name(method_name);
        RealResult.setDataset_name(dataset_name);
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null && line <= 6) {
                if (line == 1) {
                    int costTime = Integer.parseInt(tempString.split(" ")[1]);
                    RealResult.setCostTime(costTime);
                } else if (line == 2) {
                    int costTime_average = Integer.parseInt(tempString.split(" ")[1]);
                    RealResult.setCostTime_average(costTime_average);
                } else if (line == 3) {
                    double NMI = Double.parseDouble(tempString.split(" ")[1]);
                    RealResult.setNMI(NMI);
                } else if (line == 4) {
                    double node_correct = Double.parseDouble(tempString.split(" ")[1]);
                    RealResult.setNode_correct(node_correct);
                } else if (line == 6) {
                    double Rvalue = Double.parseDouble(tempString.split("\t")[0]);
                    double Pvalue = Double.parseDouble(tempString.split("\t")[1]);
                    double Fvalue = Double.parseDouble(tempString.split("\t")[2]);
                    RealResult.setRvalue(Rvalue);
                    RealResult.setPvalue(Pvalue);
                    RealResult.setFvalue(Fvalue);
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return RealResult;
    }

    public void showResults(ArrayList<MethodResult> list, String dataset_name, String show_type) {
        DecimalFormat df = new DecimalFormat("0.0000");
        String method_name = "";
        String costTime = "";
        String costTime_average = "";
        String nmi = "";
        String p = "";
        String r = "";
        String f = "";
        String nodecor = "";
        if("Latex".equals(show_type)) {
            System.out.print("\\textit{" + dataset_name + "}");
            for (MethodResult temp : list) {
                method_name = method_name + "  &" + temp.getMethod_name();
                costTime = costTime + " &" + temp.getCostTime();
                costTime_average = costTime_average + " &" + temp.getCostTime_average();
                nmi = nmi + " &" + df.format(temp.getNMI());
                p = p + " &" + df.format(temp.getPvalue());
                r = r + " &" + df.format(temp.getRvalue());
                f = f + " &" + df.format(temp.getFvalue());
                nodecor = nodecor + " &" + df.format(temp.getNode_correct());
            }
            costTime = "&\\textit{{Time}(ms)}" + costTime + "\\\\";
            costTime_average = "&\\textit{{Time_average}(ms)}" + costTime_average + "\\\\";
            nmi = "&\\textit{NMI}" + nmi + "\\\\";
            r = "&\\textit{Recall}" + r + "\\\\";
            p = "&\\textit{Precision}" + p + "\\\\";
            f = "&\\textit{F-Score}" + f + "\\\\";
            nodecor = "&\\textit{NODECOR}" + nodecor + "\\\\";
        } else if("word".equals(show_type)){
            System.out.print(dataset_name);
            for (MethodResult temp : list) {
                method_name = method_name + "  " + temp.getMethod_name();
                costTime = costTime + " " + temp.getCostTime();
                costTime_average = costTime_average + " " + temp.getCostTime_average();
                nmi = nmi + " " + df.format(temp.getNMI());
                p = p + " " + df.format(temp.getPvalue());
                r = r + " " + df.format(temp.getRvalue());
                f = f + " " + df.format(temp.getFvalue());
                nodecor = nodecor + " " + df.format(temp.getNode_correct());
            }
            costTime = "Time(ms)" + costTime;
            costTime_average = "Time_average(ms)" + costTime_average;
            nmi = "NMI" + nmi;
            r = "Recall" + r;
            p = "Precision" + p;
            f = "F-Score" + f;
            nodecor = "NODECOR" + nodecor;
        }

        System.out.println(method_name);
        System.out.println(nmi);
        System.out.println(r);
        System.out.println(p);
        System.out.println(f);
        System.out.println(costTime);
        System.out.println(costTime_average);
        System.out.println(nodecor);
        System.out.println();
    }

    public static void main(String[] args) throws IOException {

        ArrayList<String> dataSets = new ArrayList<>();
//        dataSets.add("karate");
//        dataSets.add("dolphin");
//        dataSets.add("football");
//        dataSets.add("krebsbook");

//        dataSets.add("amazon");
//        dataSets.add("lastfm_asia");


        dataSets.add("musae_EN");
        dataSets.add("musae_ES");
        dataSets.add("musae_FR");
        dataSets.add("musae_PT");
        dataSets.add("musae_RU");

        dataSets.add("musae_DE");
//        dataSets.add("musae_github");
//        dataSets.add("musae_facebook");

//        dataSets.add("Combined-APMS");
//        dataSets.add("Ito-core");
//        dataSets.add("LC-multiple");
//        dataSets.add("Uetz-screen");
//        dataSets.add("Y2H-union");
//        dataSets.add("CCSB-YI1");


        String show_type = "word";//Latex;word;
        /************      add method      ************/
        ArrayList<String> algorithm_names = new ArrayList<>();
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



        String file_result = "";
        ReadRealResults reader = new ReadRealResults();
        for (String dataset_name : dataSets) {
            String file_path = "D:/dataset/data set/";
            // F:/1papers/20190411 first/data set/
            // D:/dataset/data set/
            file_path = file_path + dataset_name + "/";
            ArrayList<MethodResult> resultArrayList = new ArrayList<>();
            for (String method_name : algorithm_names) {
                file_result = file_path + dataset_name + "_result(" + method_name + ").txt";
                MethodResult a = reader.ReadRealResults(file_result, method_name, dataset_name);
                resultArrayList.add(a);
            }
            reader.showResults(resultArrayList, dataset_name, show_type);
        }
    }

}
