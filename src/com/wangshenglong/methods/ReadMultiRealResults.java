package com.wangshenglong.methods;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ReadMultiRealResults {

    public MethodResult read_multi_real_results(int file_number, String method_name, String file_path_result)
    {
        File file = new File(file_path_result);
        BufferedReader reader = null;
        MethodResult AMResult = new MethodResult();
        AMResult.setMethod_name(method_name);
        AMResult.setFile_number(file_number);
        try
        {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null && line <=6)
            {
                if(line == 1) {
                    int costTime = Integer.parseInt(tempString.split(" ")[1]);
                    AMResult.setCostTime(costTime);
                }
                else if(line == 2) {
                    int costTime_average = Integer.parseInt(tempString.split(" ")[1]);
                    AMResult.setCostTime_average(costTime_average);
                }
                else if(line == 3) {
                    double NMI = Double.parseDouble(tempString.split(" ")[1]);
                    AMResult.setNMI(NMI);
                }
                else if(line == 4) {
                    double node_correct = Double.parseDouble(tempString.split(" ")[1]);
                    AMResult.setNode_correct(node_correct);
                }
                else if(line == 6){
                    double Rvalue = Double.parseDouble(tempString.split("\t")[0]);
                    double Pvalue = Double.parseDouble(tempString.split("\t")[1]);
                    double Fvalue = Double.parseDouble(tempString.split("\t")[2]);
                    AMResult.setRvalue(Rvalue);
                    AMResult.setPvalue(Pvalue);
                    AMResult.setFvalue(Fvalue);
                }
                line++;
            }
            reader.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                } catch (IOException e1)
                {
                }
            }
        }
        return AMResult;
    }

    public ArrayList<MethodResult> caculateAverageValue(ArrayList<MethodResult> list, int number_of_file, String algorithm_name) {
        ArrayList<MethodResult> averageList = new ArrayList<>();
        for (int i = 1; i <= number_of_file; i++) {
            MethodResult average = new MethodResult();
            average.setMethod_name(algorithm_name);
            average.setFile_number(i);
            averageList.add(average);
        }

        for(MethodResult tempResult : list) {
            String name = tempResult.getMethod_name();
            int file_number = tempResult.getFile_number();
            for(MethodResult tempAverage : averageList) {
                if(tempAverage.getFile_number() == tempResult.getFile_number()) {
                    tempAverage.setCostTime(tempAverage.getCostTime() + tempResult.getCostTime());
                    tempAverage.setCostTime_average(tempAverage.getCostTime_average() + tempResult.getCostTime_average());
                    tempAverage.setNMI(tempAverage.getNMI() + tempResult.getNMI());
                    tempAverage.setNode_correct(tempAverage.getNode_correct() + tempResult.getNode_correct());
                    tempAverage.setRvalue(tempAverage.getRvalue() + tempResult.getRvalue());
                    tempAverage.setPvalue(tempAverage.getPvalue() + tempResult.getPvalue());
                    tempAverage.setFvalue(tempAverage.getFvalue() + tempResult.getFvalue());
                }
            }
        }

        return averageList;
    }

    public ArrayList<String> caculateResults(ArrayList<MethodResult> list, String algorithm_name) {
        switch (algorithm_name) {
            case "clauset":
                algorithm_name = "Clauset";
                break;
            case "chen":
                algorithm_name = "Chen";
                break;
            case "lwp":
                algorithm_name = "LWP";
                break;
            case "ls":
                algorithm_name = "LS";
                break;
            case "vi":
                algorithm_name = "VI";
                break;
            case "lcd":
                algorithm_name = "LCD";
                break;
            case "iskcore":
                algorithm_name = "LCDPC3";
                break;
            case "isdegree":
                algorithm_name = "isdegree";
                break;
            case "iswithoutpc":
                algorithm_name = "iswithoutpc";
                break;
            case "isdegreeCn":
                algorithm_name = "LCDPC4";
                break;
            case "iswithoutpcCn":
                algorithm_name = "LCDPC5";
                break;
            case "isComponent":
                algorithm_name = "LCDPC1";
                break;
            case "componentWithoutpc":
                algorithm_name = "LCDPC2";
                break;
            case "fifth":
                algorithm_name = "RTLCD";
                break;
        }
        ArrayList<String> resultlist = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.0000");
        String costTime = "";
        String costTime_average = "";
        String nmi = "";
        String p = "";
        String r = "";
        String f = "";
        String nodecor = "";
        for(MethodResult temp : list) {
            costTime = costTime + " " + temp.getCostTime();
            costTime_average = costTime_average + " " + temp.getCostTime_average();
            nmi = nmi + " " + df.format(temp.getNMI());
            p = p + " " + df.format(temp.getPvalue());
            r = r + " " + df.format(temp.getRvalue());
            f = f + " " + df.format(temp.getFvalue());
            nodecor = nodecor + " " + df.format(temp.getNode_correct());
        }
        costTime = "costTime    " + algorithm_name + " = [" + costTime + " ]";
        costTime_average = "costTime_average    " + algorithm_name + " = [" + costTime_average + " ]";
        nmi = "NMI    " + algorithm_name + " = [" + nmi + " ]";
        r = "R    " + algorithm_name + " = [" + r + " ]";
        p = "P    " + algorithm_name + " = [" + p + " ]";
        f = "F    " + algorithm_name + " = [" + f + " ]";
        nodecor = "NODECOR    " + algorithm_name + " = [" + nodecor + " ]";
        resultlist.add(costTime);
        resultlist.add(costTime_average);
        resultlist.add(nmi);
        resultlist.add(r);
        resultlist.add(p);
        resultlist.add(f);
        resultlist.add(nodecor);
        return resultlist;
    }

    public void showResults(ArrayList<ArrayList<String>> resultLists) {
        String costTime = "";
        String costTime_average = "";
        String nmi = "";
        String r = "";
        String p = "";
        String f = "";
        String nodecor = "";

        for(ArrayList<String> list : resultLists) {
            costTime = costTime + "\n" + list.get(0);
            costTime_average = costTime_average + "\n" + list.get(1);
            nmi = nmi + "\n" + list.get(2);
            r = r + "\n" + list.get(3);
            p = p + "\n" + list.get(4);
            f = f + "\n" + list.get(5);
            nodecor = nodecor + "\n" + list.get(6);
        }
        System.out.println(nmi);
        System.out.println(r);
        System.out.println(p);
        System.out.println(f);
        System.out.println(costTime);
        System.out.println(costTime_average);
//        System.out.println(nodecor);
    }

    public static void main(String[] args) throws IOException {

        String dataSetName = "dblp";    //youtube;dblp;amazon
        String file_path_static = "D:/dataset/data set/" + dataSetName + "/" + dataSetName;
        // F:/1papers/20190411 first/data set/
        // D:/dataset/data set/
        int number_of_file = 10;
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
        ReadMultiRealResults reader = new ReadMultiRealResults();
        ArrayList<ArrayList<String>> resultLists = new ArrayList<>();
        for(String method_name : algorithm_names) {
            ArrayList<MethodResult> resultArrayList = new ArrayList<>();

            for(int file_number=1; file_number<=number_of_file; file_number++) {
                    file_result = file_path_static + file_number + "/"
                           + dataSetName + file_number + "_result(" + method_name + ").txt";
//                    System.out.println(file_result);
                    MethodResult a = reader.read_multi_real_results(file_number, method_name, file_result);
                    resultArrayList.add(a);
            }
            ArrayList<MethodResult> averageResultArrayList = new ArrayList<>();
            averageResultArrayList = reader.caculateAverageValue(resultArrayList, number_of_file, method_name);
            resultLists.add(reader.caculateResults(averageResultArrayList, method_name));
        }
        reader.showResults(resultLists);
    }

}
