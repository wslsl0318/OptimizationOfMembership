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

public class ChangeResultsToMatlab {
	public String source_file;
	public String target_file;

	public ChangeResultsToMatlab() {
		this.source_file = new String();
		this.target_file = new String();
	}

	public ChangeResultsToMatlab(String source_file, String target_file) {
		this.source_file = source_file;
		this.target_file = target_file;
	}

	public void get_matlab_code_of_a_specific_measurement() {

		ArrayList<ArrayList<Double>> NMI = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> F = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> Q = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> D = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> Time = new ArrayList<ArrayList<Double>>();

		File file = new File(this.source_file);
		BufferedReader file_reader = null;
		try {
			file_reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			while ((tempString = file_reader.readLine()) != null) {
				line++;
				if (line % 12 == 3) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					NMI.add(values_of_line);
				}

				else if (line % 12 == 4) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					Q.add(values_of_line);
				}

				else if (line % 12 == 5) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					F.add(values_of_line);
				}

				else if (line % 12 == 0) {
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++) {
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					D.add(values_of_line);
				}

//				else if (line % 12 == 0) {
//					String[] strs;
//					strs = tempString.split("\t");
//					ArrayList<Double> values_of_line = new ArrayList<Double>();
//					for (int i = 1; i < strs.length; i++) {
//						double value = Double.parseDouble(strs[i]);
//						values_of_line.add(value);
//					}
//					Time.add(values_of_line);
//				}
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

		// System.out.println("NMI");
		// for (Iterator<ArrayList<Double>> iter = NMI.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();
		// System.out.println("F");
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
		// System.out.println("D");
		// for (Iterator<ArrayList<Double>> iter = D.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();
		// System.out.println("Time");
		// for (Iterator<ArrayList<Double>> iter = Time.iterator();
		// iter.hasNext();) {
		// System.out.println(iter.next());
		// }
		// System.out.println();


		double[][] NMI_matrix = new double[NMI.get(0).size()][NMI.size()];
		for (int i = 0; i < NMI.size(); i++) {
			for (int j = 0; j < NMI.get(i).size(); j++) {
				NMI_matrix[j][i] = NMI.get(i).get(j);
			}
		}
		for (int j = 0; j < NMI.size(); j++) {
			double temp = NMI_matrix[9][j];
			NMI_matrix[9][j] = NMI_matrix[10][j];
			NMI_matrix[10][j] = temp;
		}
		System.out.println("NMI");
		for (int i = 0; i < NMI.get(0).size(); i++) {
			if (i == 0) {
				System.out.print("LEBR1   = [ ");
			} else if (i == 1) {
				System.out.print("LEBR2   = [ ");
			} else if (i == 2) {
				System.out.print("LEBR3   = [ ");
			} else if (i == 3) {
				System.out.print("LFM     = [ ");
			} else if (i == 4) {
				System.out.print("DOCNET  = [ ");
			} else if (i == 5) {
				System.out.print("CFM     = [ ");
			} else if (i == 6) {
				System.out.print("TWD     = [ ");
			} else if (i == 7) {
				System.out.print("LEPB    = [ ");
			} else if (i == 8) {
				System.out.print("LECC    = [ ");
			} else if (i == 9) {
				System.out.print("WCPM    = [ ");
			} else if (i == 10) {
				System.out.print("LOUVAIN = [ ");
			}

			for (int j = 0; j < NMI.size(); j++) {
				System.out.print(NMI_matrix[i][j] + "\t");
			}
			System.out.println("]");
		}
		System.out.println();

		double[][] F_matrix = new double[F.get(0).size()][F.size()];
		for (int i = 0; i < F.size(); i++) {
			for (int j = 0; j < F.get(i).size(); j++) {
				F_matrix[j][i] = F.get(i).get(j);
			}
		}
		for (int j = 0; j < F.size(); j++) {
			double temp = F_matrix[9][j];
			F_matrix[9][j] = F_matrix[10][j];
			F_matrix[10][j] = temp;
		}
		System.out.println("F");
		for (int i = 0; i < F.get(0).size(); i++) {
			if (i == 0) {
				System.out.print("LEBR1   = [ ");
			} else if (i == 1) {
				System.out.print("LEBR2   = [ ");
			} else if (i == 2) {
				System.out.print("LEBR3   = [ ");
			} else if (i == 3) {
				System.out.print("LFM     = [ ");
			} else if (i == 4) {
				System.out.print("DOCNET  = [ ");
			} else if (i == 5) {
				System.out.print("CFM     = [ ");
			} else if (i == 6) {
				System.out.print("TWD     = [ ");
			} else if (i == 7) {
				System.out.print("LEPB    = [ ");
			} else if (i == 8) {
				System.out.print("LECC    = [ ");
			} else if (i == 9) {
				System.out.print("WCPM    = [ ");
			} else if (i == 10) {
				System.out.print("LOUVAIN = [ ");
			}

			for (int j = 0; j < F.size(); j++) {
				System.out.print(F_matrix[i][j] + "\t");
			}
			System.out.println("]");
		}
		System.out.println();
		
		double[][] Q_matrix = new double[Q.get(0).size()][Q.size()];
		for (int i = 0; i < Q.size(); i++) {
			for (int j = 0; j < Q.get(i).size(); j++) {
				Q_matrix[j][i] = Q.get(i).get(j);
			}
		}
		for (int j = 0; j < Q.size(); j++) {
			double temp = Q_matrix[9][j];
			Q_matrix[9][j] = Q_matrix[10][j];
			Q_matrix[10][j] = temp;
		}
		System.out.println("Q");
		for (int i = 0; i < Q.get(0).size(); i++) {
			if (i == 0) {
				System.out.print("LEBR1   = [ ");
			} else if (i == 1) {
				System.out.print("LEBR2   = [ ");
			} else if (i == 2) {
				System.out.print("LEBR3   = [ ");
			} else if (i == 3) {
				System.out.print("LFM     = [ ");
			} else if (i == 4) {
				System.out.print("DOCNET  = [ ");
			} else if (i == 5) {
				System.out.print("CFM     = [ ");
			} else if (i == 6) {
				System.out.print("TWD     = [ ");
			} else if (i == 7) {
				System.out.print("LEPB    = [ ");
			} else if (i == 8) {
				System.out.print("LECC    = [ ");
			} else if (i == 9) {
				System.out.print("WCPM    = [ ");
			} else if (i == 10) {
				System.out.print("LOUVAIN = [ ");
			}

			for (int j = 0; j < Q.size(); j++) {
				System.out.print(Q_matrix[i][j] + "\t");
			}
			System.out.println("]");
		}
		System.out.println();
		
		double[][] D_matrix = new double[D.get(0).size()][D.size()];
		for (int i = 0; i < D.size(); i++) {
			for (int j = 0; j < D.get(i).size(); j++) {
				D_matrix[j][i] = D.get(i).get(j);
			}
		}
		for (int j = 0; j < D.size(); j++) {
			double temp = D_matrix[9][j];
			D_matrix[9][j] = D_matrix[10][j];
			D_matrix[10][j] = temp;
		}
		System.out.println("D");
		for (int i = 0; i < D.get(0).size(); i++) {
			if (i == 0) {
				System.out.print("LEBR1   = [ ");
			} else if (i == 1) {
				System.out.print("LEBR2   = [ ");
			} else if (i == 2) {
				System.out.print("LEBR3   = [ ");
			} else if (i == 3) {
				System.out.print("LFM     = [ ");
			} else if (i == 4) {
				System.out.print("DOCNET  = [ ");
			} else if (i == 5) {
				System.out.print("CFM     = [ ");
			} else if (i == 6) {
				System.out.print("TWD     = [ ");
			} else if (i == 7) {
				System.out.print("LEPB    = [ ");
			} else if (i == 8) {
				System.out.print("LECC    = [ ");
			} else if (i == 9) {
				System.out.print("WCPM    = [ ");
			} else if (i == 10) {
				System.out.print("LOUVAIN = [ ");
			}

			for (int j = 0; j < D.size(); j++) {
				System.out.print(D_matrix[i][j] + "\t");
			}
			System.out.println("]");
		}
		System.out.println();
		
		double[][] Time_matrix = new double[Time.get(0).size()][Time.size()];
		for (int i = 0; i < Time.size(); i++) {
			for (int j = 0; j < Time.get(i).size(); j++) {
				Time_matrix[j][i] = Time.get(i).get(j);
			}
		}
		for (int j = 0; j < Time.size(); j++) {
			double temp = Time_matrix[9][j];
			Time_matrix[9][j] = Time_matrix[10][j];
			Time_matrix[10][j] = temp;
		}
		System.out.println("Time");
		for (int i = 0; i < Time.get(0).size(); i++) {
			if (i == 0) {
				System.out.print("LEBR1   = [ ");
			} else if (i == 1) {
				System.out.print("LEBR2   = [ ");
			} else if (i == 2) {
				System.out.print("LEBR3   = [ ");
			} else if (i == 3) {
				System.out.print("LFM     = [ ");
			} else if (i == 4) {
				System.out.print("DOCNET  = [ ");
			} else if (i == 5) {
				System.out.print("CFM     = [ ");
			} else if (i == 6) {
				System.out.print("TWD     = [ ");
			} else if (i == 7) {
				System.out.print("LEPB    = [ ");
			} else if (i == 8) {
				System.out.print("LECC    = [ ");
			} else if (i == 9) {
				System.out.print("WCPM    = [ ");
			} else if (i == 10) {
				System.out.print("LOUVAIN = [ ");
			}

			for (int j = 0; j < Time.size(); j++) {
				System.out.print(Time_matrix[i][j] + "\t");
			}
			System.out.println("]");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		ChangeResultsToMatlab results_to_matlab = new ChangeResultsToMatlab();
		results_to_matlab.source_file = "E:/Algorithms results/Algorithms results (overlap)/artifical networks/1000/on_results.txt";
		results_to_matlab.get_matlab_code_of_a_specific_measurement();
	}
}
