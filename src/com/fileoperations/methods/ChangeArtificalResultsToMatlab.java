package com.fileoperations.methods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

public class ChangeArtificalResultsToMatlab {
	public String source_file;
	public String target_file;

	public ChangeArtificalResultsToMatlab() {
		this.source_file = new String();
		this.target_file = new String();
	}

	public ChangeArtificalResultsToMatlab(String source_file, String target_file) {
		this.source_file = source_file;
		this.target_file = target_file;
	}

	public void get_matlab_code_of_a_specific_measurement() {

		ArrayList<ArrayList<Double>> NMI = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> F = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> Q = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> D = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> T = new ArrayList<ArrayList<Double>>();

		File file = new File(this.source_file);
		BufferedReader file_reader = null;
		try {
			file_reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			while ((tempString = file_reader.readLine()) != null) {
				line++;
				if (line % 7 == 3) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					NMI.add(values_of_line);
				} else if (line % 7 == 4) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					F.add(values_of_line);
				}

				else if (line % 7 == 5) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					Q.add(values_of_line);
				}

				else if (line % 7 == 6) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					D.add(values_of_line);
				}

				else if (line % 7 == 0) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					T.add(values_of_line);
				}
			}
			file_reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (file_reader != null) {
				try {
					file_reader.close();
				} catch (IOException e1) {
				}
			}
		}

		double[][] NMI_matrix = new double[NMI.get(0).size()][NMI.size()];
		for (int i = 0; i < NMI.size(); i++) {
			for (int j = 0; j < NMI.get(i).size(); j++) {
				NMI_matrix[j][i] = NMI.get(i).get(j);
			}
		}
		double[][] F_matrix = new double[F.get(0).size()][F.size()];
		for (int i = 0; i < F.size(); i++) {
			for (int j = 0; j < F.get(i).size(); j++) {
				F_matrix[j][i] = F.get(i).get(j);
			}
		}
		double[][] Q_matrix = new double[Q.get(0).size()][Q.size()];
		for (int i = 0; i < Q.size(); i++) {
			for (int j = 0; j < Q.get(i).size(); j++) {
				Q_matrix[j][i] = Q.get(i).get(j);
			}
		}
		double[][] D_matrix = new double[D.get(0).size()][D.size()];
		for (int i = 0; i < D.size(); i++) {
			for (int j = 0; j < D.get(i).size(); j++) {
				D_matrix[j][i] = D.get(i).get(j);
			}
		}
		double[][] T_matrix = new double[T.get(0).size()][T.size()];
		for (int i = 0; i < T.size(); i++) {
			for (int j = 0; j < T.get(i).size(); j++) {
				T_matrix[j][i] = T.get(i).get(j);
			}
		}

		// System.out.println("NMI");
		// for (Iterator<ArrayList<Double>> iter = NMI.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();
		// System.out.println("F-Measure");
		// for (Iterator<ArrayList<Double>> iter = F.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();
		// System.out.println("Q");
		// for (Iterator<ArrayList<Double>> iter = Q.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();
		// System.out.println("D-Score");
		// for (Iterator<ArrayList<Double>> iter = D.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();
		// System.out.println("Time");
		// for (Iterator<ArrayList<Double>> iter = T.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();

		// System.out.println("figure;");
		//
		// /* out print NMI */
		// out_print_range_of_y();
		// System.out.println("y=y';");
		// for (int i = 0; i < NMI.get(0).size(); i++) {
		// if (i == 0) {
		// System.out.print("xNE =[ ");//
		// } else if (i == 1) {
		// System.out.print("xCS1=[ ");//
		// } else if (i == 2) {
		// System.out.print("xCS2=[ ");//
		// } else if (i == 3) {
		// System.out.print("xCS3=[ ");//
		// } else if (i == 4) {
		// System.out.print("xCS4=[ ");//
		// } else if (i == 5) {
		// System.out.print("xCM1=[ ");//
		// } else if (i == 6) {
		// System.out.print("xCM2=[ ");//
		// } else if (i == 7) {
		// System.out.print("xCM3=[ ");//
		// } else if (i == 8) {
		// System.out.print("xCM4=[ ");
		// } else if (i == 9) {
		// System.out.print("xRO1=[ ");//
		// } else if (i == 10) {
		// System.out.print("xRO2=[ ");//
		// } else if (i == 11) {
		// System.out.print("xRO3=[ ");
		// } else if (i == 12) {
		// System.out.print("xRO4=[ ");
		// } else if (i == 13) {
		// System.out.print("xRO5=[ ");
		// } else if (i == 14) {
		// System.out.print("xRO6=[ ");
		// } else if (i == 15) {
		// System.out.print("xWPM=[ ");//
		// } else if (i == 16) {
		// System.out.print("xLFM=[ ");//
		// } else if (i == 17) {
		// System.out.print("xDOC=[ ");//
		// } else if (i == 18) {
		// System.out.print("xCFM=[ ");//
		// } else if (i == 19) {
		// System.out.print("xTWD=[ ");//
		// } else if (i == 20) {
		// System.out.print("xLOU=[ ");
		// }
		// for (int j = 0; j < NMI.size(); j++) {
		// System.out.print(NMI_matrix[i][j] + "\t");
		// }
		// System.out.println("]");
		// }
		// System.out.println("subplot(2,2,1);");
		// System.out
		// .println("plot(y,xNE,'',y,xCS1,'',y,xCS2,'',y,xCS3,'',y,xCS4,'',y,xCM1,'',y,xCM2,'',y,xCM3,'',y,xCM4,'',y,xRO1,'',y,xRO2,'',y,xRO3,'',y,xRO4,'',y,xRO5,'',y,xRO6,'',y,xWPM,'',y,xLFM,'',y,xDOC,'',y,xCFM,'',y,xTWD,'',y,xLOU,'','LineWidth',1);");
		// System.out.println("grid on");
		// System.out
		// .println("legend('NE','CS1','CS2','CS3','CS4','CM1','CM2','CM3','CM4','RO1','RO2','RO3','RO4','RO5','RO6','WPM','LFM','DOC','CFM','TWD','LOU');");
		// System.out
		// .println("xlabel(['$\\alpha_{n}$'],'interpreter','latex','Fontsize',10.5);");
		// // $\\mu$;$|C|_{max}$;$d_{max}$;$O_{n}$;$O_{m}$;$\\alpha_{n}$
		// System.out
		// .println("ylabel(['$NMI$'],'interpreter','latex','Fontsize',10.5);");
		//
		// System.out.println();
		//
		// /* out print F */
		// out_print_range_of_y();
		// System.out.println("y=y';");
		// for (int i = 0; i < F.get(0).size(); i++) {
		// if (i == 0) {
		// System.out.print("xNE =[ ");//
		// } else if (i == 1) {
		// System.out.print("xCS1=[ ");//
		// } else if (i == 2) {
		// System.out.print("xCS2=[ ");//
		// } else if (i == 3) {
		// System.out.print("xCS3=[ ");//
		// } else if (i == 4) {
		// System.out.print("xCS4=[ ");//
		// } else if (i == 5) {
		// System.out.print("xCM1=[ ");//
		// } else if (i == 6) {
		// System.out.print("xCM2=[ ");//
		// } else if (i == 7) {
		// System.out.print("xCM3=[ ");//
		// } else if (i == 8) {
		// System.out.print("xCM4=[ ");
		// } else if (i == 9) {
		// System.out.print("xRO1=[ ");//
		// } else if (i == 10) {
		// System.out.print("xRO2=[ ");//
		// } else if (i == 11) {
		// System.out.print("xRO3=[ ");
		// } else if (i == 12) {
		// System.out.print("xRO4=[ ");
		// } else if (i == 13) {
		// System.out.print("xRO5=[ ");
		// } else if (i == 14) {
		// System.out.print("xRO6=[ ");
		// } else if (i == 15) {
		// System.out.print("xWPM=[ ");//
		// } else if (i == 16) {
		// System.out.print("xLFM=[ ");//
		// } else if (i == 17) {
		// System.out.print("xDOC=[ ");//
		// } else if (i == 18) {
		// System.out.print("xCFM=[ ");//
		// } else if (i == 19) {
		// System.out.print("xTWD=[ ");//
		// } else if (i == 20) {
		// System.out.print("xLOU=[ ");
		// }
		// for (int j = 0; j < F.size(); j++) {
		// System.out.print(F_matrix[i][j] + "\t");
		// }
		// System.out.println("]");
		// }
		// System.out.println("subplot(2,2,2);");
		// System.out
		// .println("plot(y,xNE,'',y,xCS1,'',y,xCS2,'',y,xCS3,'',y,xCS4,'',y,xCM1,'',y,xCM2,'',y,xCM3,'',y,xCM4,'',y,xRO1,'',y,xRO2,'',y,xRO3,'',y,xRO4,'',y,xRO5,'',y,xRO6,'',y,xWPM,'',y,xLFM,'',y,xDOC,'',y,xCFM,'',y,xTWD,'',y,xLOU,'','LineWidth',1);");
		// System.out.println("grid on");
		// System.out
		// .println("legend('NE','CS1','CS2','CS3','CS4','CM1','CM2','CM3','CM4','RO1','RO2','RO3','RO4','RO5','RO6','WPM','LFM','DOC','CFM','TWD','LOU');");
		// System.out
		// .println("xlabel(['$\\alpha_{n}$'],'interpreter','latex','Fontsize',10.5);");
		// // $\\mu$;$|C|_{max}$;$d_{max}$;$O_{n}$;$O_{m}$;$\\alpha_{n}$
		// System.out
		// .println("ylabel(['$F$-$Measure$'],'interpreter','latex','Fontsize',10.5);");
		//
		// System.out.println();
		//
		// /* out print Q */
		// out_print_range_of_y();
		// System.out.println("y=y';");
		// for (int i = 0; i < Q.get(0).size(); i++) {
		// if (i == 0) {
		// System.out.print("xNE =[ ");//
		// } else if (i == 1) {
		// System.out.print("xCS1=[ ");//
		// } else if (i == 2) {
		// System.out.print("xCS2=[ ");//
		// } else if (i == 3) {
		// System.out.print("xCS3=[ ");//
		// } else if (i == 4) {
		// System.out.print("xCS4=[ ");//
		// } else if (i == 5) {
		// System.out.print("xCM1=[ ");//
		// } else if (i == 6) {
		// System.out.print("xCM2=[ ");//
		// } else if (i == 7) {
		// System.out.print("xCM3=[ ");//
		// } else if (i == 8) {
		// System.out.print("xCM4=[ ");
		// } else if (i == 9) {
		// System.out.print("xRO1=[ ");//
		// } else if (i == 10) {
		// System.out.print("xRO2=[ ");//
		// } else if (i == 11) {
		// System.out.print("xRO3=[ ");
		// } else if (i == 12) {
		// System.out.print("xRO4=[ ");
		// } else if (i == 13) {
		// System.out.print("xRO5=[ ");
		// } else if (i == 14) {
		// System.out.print("xRO6=[ ");
		// } else if (i == 15) {
		// System.out.print("xWPM=[ ");//
		// } else if (i == 16) {
		// System.out.print("xLFM=[ ");//
		// } else if (i == 17) {
		// System.out.print("xDOC=[ ");//
		// } else if (i == 18) {
		// System.out.print("xCFM=[ ");//
		// } else if (i == 19) {
		// System.out.print("xTWD=[ ");//
		// } else if (i == 20) {
		// System.out.print("xLOU=[ ");
		// }
		// for (int j = 0; j < Q.size(); j++) {
		// System.out.print(Q_matrix[i][j] + "\t");
		// }
		// System.out.println("]");
		// }
		// System.out.println("subplot(2,2,3);");
		// System.out
		// .println("plot(y,xNE,'',y,xCS1,'',y,xCS2,'',y,xCS3,'',y,xCS4,'',y,xCM1,'',y,xCM2,'',y,xCM3,'',y,xCM4,'',y,xRO1,'',y,xRO2,'',y,xRO3,'',y,xRO4,'',y,xRO5,'',y,xRO6,'',y,xWPM,'',y,xLFM,'',y,xDOC,'',y,xCFM,'',y,xTWD,'',y,xLOU,'','LineWidth',1);");
		// System.out.println("grid on");
		// System.out
		// .println("legend('NE','CS1','CS2','CS3','CS4','CM1','CM2','CM3','CM4','RO1','RO2','RO3','RO4','RO5','RO6','WPM','LFM','DOC','CFM','TWD','LOU');");
		// System.out
		// .println("xlabel(['$\\alpha_{n}$'],'interpreter','latex','Fontsize',10.5);");
		// // $\\mu$;$|C|_{max}$;$d_{max}$;$O_{n}$;$O_{m}$;$\\alpha_{n}$
		// System.out
		// .println("ylabel(['$Q$'],'interpreter','latex','Fontsize',10.5);");
		//
		// System.out.println();
		//
		// /* out print D */
		// out_print_range_of_y();
		// System.out.println("y=y';");
		// for (int i = 0; i < D.get(0).size(); i++) {
		// if (i == 0) {
		// System.out.print("xNE =[ ");//
		// } else if (i == 1) {
		// System.out.print("xCS1=[ ");//
		// } else if (i == 2) {
		// System.out.print("xCS2=[ ");//
		// } else if (i == 3) {
		// System.out.print("xCS3=[ ");//
		// } else if (i == 4) {
		// System.out.print("xCS4=[ ");//
		// } else if (i == 5) {
		// System.out.print("xCM1=[ ");//
		// } else if (i == 6) {
		// System.out.print("xCM2=[ ");//
		// } else if (i == 7) {
		// System.out.print("xCM3=[ ");//
		// } else if (i == 8) {
		// System.out.print("xCM4=[ ");
		// } else if (i == 9) {
		// System.out.print("xRO1=[ ");//
		// } else if (i == 10) {
		// System.out.print("xRO2=[ ");//
		// } else if (i == 11) {
		// System.out.print("xRO3=[ ");
		// } else if (i == 12) {
		// System.out.print("xRO4=[ ");
		// } else if (i == 13) {
		// System.out.print("xRO5=[ ");
		// } else if (i == 14) {
		// System.out.print("xRO6=[ ");
		// } else if (i == 15) {
		// System.out.print("xWPM=[ ");//
		// } else if (i == 16) {
		// System.out.print("xLFM=[ ");//
		// } else if (i == 17) {
		// System.out.print("xDOC=[ ");//
		// } else if (i == 18) {
		// System.out.print("xCFM=[ ");//
		// } else if (i == 19) {
		// System.out.print("xTWD=[ ");//
		// } else if (i == 20) {
		// System.out.print("xLOU=[ ");
		// }
		// for (int j = 0; j < D.size(); j++) {
		// System.out.print(D_matrix[i][j] + "\t");
		// }
		// System.out.println("]");
		// }
		// System.out.println("subplot(2,2,4);");
		// System.out
		// .println("plot(y,xNE,'',y,xCS1,'',y,xCS2,'',y,xCS3,'',y,xCS4,'',y,xCM1,'',y,xCM2,'',y,xCM3,'',y,xCM4,'',y,xRO1,'',y,xRO2,'',y,xRO3,'',y,xRO4,'',y,xRO5,'',y,xRO6,'',y,xWPM,'',y,xLFM,'',y,xDOC,'',y,xCFM,'',y,xTWD,'',y,xLOU,'','LineWidth',1);");
		// System.out.println("grid on");
		// System.out
		// .println("legend('NE','CS1','CS2','CS3','CS4','CM1','CM2','CM3','CM4','RO1','RO2','RO3','RO4','RO5','RO6','WPM','LFM','DOC','CFM','TWD','LOU');");
		// System.out
		// .println("xlabel(['$\\alpha_{n}$'],'interpreter','latex','Fontsize',10.5);");
		// // $\\mu$;$|C|_{max}$;$d_{max}$;$O_{n}$;$O_{m}$;$\\alpha_{n}$
		// System.out
		// .println("ylabel(['$D$-$Score$'],'interpreter','latex','Fontsize',10.5);");
		//
		// System.out.println();

		/* out print T */
		// out_print_range_of_y();
		// System.out.println("y=y';");
		for (int i = 0; i < T.get(0).size(); i++) {
			if (i == 0) {
				System.out.print("xNE =[ ");//
			} else if (i == 1) {
				System.out.print("xCS1=[ ");//
			} else if (i == 2) {
				System.out.print("xCS2=[ ");//
			} else if (i == 3) {
				System.out.print("xCS3=[ ");//
			} else if (i == 4) {
				System.out.print("xCS4=[ ");//
			} else if (i == 5) {
				System.out.print("xCM1=[ ");//
			} else if (i == 6) {
				System.out.print("xCM2=[ ");//
			} else if (i == 7) {
				System.out.print("xCM3=[ ");//
			} else if (i == 8) {
				System.out.print("xCM4=[ ");
			} else if (i == 9) {
				System.out.print("xRO1=[ ");//
			} else if (i == 10) {
				System.out.print("xRO2=[ ");//
			} else if (i == 11) {
				System.out.print("xRO3=[ ");
			} else if (i == 12) {
				System.out.print("xRO4=[ ");
			} else if (i == 13) {
				System.out.print("xRO5=[ ");
			} else if (i == 14) {
				System.out.print("xRO6=[ ");
			} else if (i == 15) {
				System.out.print("xWPM=[ ");//
			} else if (i == 16) {
				System.out.print("xLFM=[ ");//
			} else if (i == 17) {
				System.out.print("xDOC=[ ");//
			} else if (i == 18) {
				System.out.print("xCFM=[ ");//
			} else if (i == 19) {
				System.out.print("xTWD=[ ");//
			} else if (i == 20) {
				System.out.print("xLOU=[ ");
			}
			for (int j = 0; j < T.size(); j++) {
				System.out.print(T_matrix[i][j] + "\t");
			}
			System.out.println("]");
		}
		// System.out.println("subplot(2,2,4);");
		// System.out
		// .println("plot(y,xNE,'',y,xCS1,'',y,xCS2,'',y,xCS3,'',y,xCS4,'',y,xCM1,'',y,xCM2,'',y,xCM3,'',y,xCM4,'',y,xRO1,'',y,xRO2,'',y,xRO3,'',y,xRO4,'',y,xRO5,'',y,xRO6,'',y,xWPM,'',y,xLFM,'',y,xDOC,'',y,xCFM,'',y,xTWD,'',y,xLOU,'','LineWidth',1);");
		// System.out.println("grid on");
		// System.out
		// .println("legend('NE','CS1','CS2','CS3','CS4','CM1','CM2','CM3','CM4','RO1','RO2','RO3','RO4','RO5','RO6','WPM','LFM','DOC','CFM','TWD','LOU');");
		// System.out
		// .println("xlabel(['$\\alpha_{n}$'],'interpreter','latex','Fontsize',10.5);");
		// // $\\mu$;$|C|_{max}$;$d_{max}$;$O_{n}$;$O_{m}$;$\\alpha_{n}$
		// System.out
		// .println("ylabel(['$Time$'],'interpreter','latex','Fontsize',10.5);");
		//
		// System.out.println();
	}

	public void out_print_range_of_y() {
		System.out.println("y=1:1:5;");
		// 0.1:0.1:0.8 //mu
		// 20:20:100 //c,d,on
		// 2:2:10 //om
		// 1:1:5 //n
	}

	public static void main(String[] args) {
		ChangeArtificalResultsToMatlab results_to_matlab = new ChangeArtificalResultsToMatlab();
		results_to_matlab.source_file = "E:/Algorithms results/Algorithms results (overlap)/artifical networks/1000/n_results.txt";
		results_to_matlab.get_matlab_code_of_a_specific_measurement();
	}
}
