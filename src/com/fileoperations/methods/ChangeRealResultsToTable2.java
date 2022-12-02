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
import java.util.TreeSet;

public class ChangeRealResultsToTable2
{
	public String source_file;
	public String target_file;

	public ChangeRealResultsToTable2()
	{
		this.source_file = new String();
		this.target_file = new String();
	}

	public ChangeRealResultsToTable2(String source_file, String target_file)
	{
		this.source_file = source_file;
		this.target_file = target_file;
	}

	public double[] rank_NMI_F_Q(ArrayList<Double> values)
	{
		TreeSet<Double> values_tree = new TreeSet<Double>();
		values_tree.addAll(values);
		double[] ranks = new double[values.size()];
		double rank = 0;
		double previous_value = -12345;
		while (!values_tree.isEmpty())
		{
			double current_value = values_tree.pollLast();
			if (current_value != -12345)
			{
				if (current_value != previous_value)
				{
					rank++;
					previous_value = current_value;
					for (int i = 0; i < values.size(); i++)
					{
						if (current_value == values.get(i))
						{
							ranks[i] = rank;
						}
					}
				} else
				{
					for (int i = 0; i < values.size(); i++)
					{
						if (current_value == values.get(i))
						{
							ranks[i] = rank;
						}
					}
				}
			}
		}

		rank++;
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i) == -12345)
			{
				ranks[i] = rank;
			}
		}
		return ranks;
	}

	public double[] rank_Time(ArrayList<Double> values)
	{
		TreeSet<Double> values_tree = new TreeSet<Double>();
		values_tree.addAll(values);
		double[] ranks = new double[values.size()];
		double rank = 0;
		double previous_value = -12345;
		while (!values_tree.isEmpty())
		{
			double current_value = values_tree.pollFirst();
			if (current_value != -12345)
			{
				if (current_value != previous_value)
				{
					rank++;
					previous_value = current_value;
					for (int i = 0; i < values.size(); i++)
					{
						if (current_value == values.get(i))
						{
							ranks[i] = rank;
						}
					}
				} else
				{
					for (int i = 0; i < values.size(); i++)
					{
						if (current_value == values.get(i))
						{
							ranks[i] = rank;
						}
					}
				}
			}
		}

		rank++;
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i) == -12345)
			{
				ranks[i] = rank;
			}
		}
		return ranks;
	}

	public double[] rank_D(ArrayList<Double> values)
	{
		TreeSet<Double> values_tree = new TreeSet<Double>();
		for (Iterator<Double> iter = values.iterator(); iter.hasNext();)
		{
			values_tree.add(Math.abs(iter.next()));
		}
		double[] ranks = new double[values.size()];
		double rank = 0;
		double previous_value = -12345;
		while (!values_tree.isEmpty())
		{
			double current_value = values_tree.pollFirst();
			if (current_value != 12345)
			{
				if (current_value != previous_value)
				{
					rank++;
					previous_value = current_value;
					for (int i = 0; i < values.size(); i++)
					{
						if (current_value == Math.abs(values.get(i)))
						{
							ranks[i] = rank;
						}
					}
				} else
				{
					for (int i = 0; i < values.size(); i++)
					{
						if (current_value == Math.abs(values.get(i)))
						{
							ranks[i] = rank;
						}
					}
				}
			}
		}

		rank++;
		for (int i = 0; i < values.size(); i++)
		{
			if (values.get(i) == -12345)
			{
				ranks[i] = rank;
			}
		}
		return ranks;
	}
	
	public void out_put(int i)
	{
		if (i == 0)
		{
			System.out.print("\\textit{Karate}      ");
		} else if (i == 1)
		{
			System.out.print("\\textit{Dolphin}     ");
		} else if (i == 2)
		{
			System.out.print("\\textit{Football}    ");
		} else if (i == 3)
		{
			System.out.print("\\textit{Book}        ");
		} else if (i == 4)
		{
			System.out.print("\\textit{Amazon}      ");
		} else if (i == 5)
		{
			System.out.print("\\textit{DBLP}        ");
		} else if (i == 6)
		{
			System.out.print("\\textit{Youtube}     ");
		} else if (i == 7)
		{
			System.out.print("\\textit{LiveJournal} ");
		} else if (i == 8)
		{
			System.out.print("\\textit{DE}          ");
		} else if (i == 9)
		{
			System.out.print("\\textit{EN}          ");
		} else if (i == 10)
		{
			System.out.print("\\textit{ES}          ");
		} else if (i == 11)
		{
			System.out.print("\\textit{FR}          ");
		} else if (i == 12)
		{
			System.out.print("\\textit{PT}          ");
		} else if (i == 13)
		{
			System.out.print("\\textit{RU}          ");
		} else if (i == 14)
		{
			System.out.print("\\textit{Facebook}    ");
		} else if (i == 15)
		{
			System.out.print("\\textit{Github}      ");
		}
	}

	public void get_matlab_code_of_a_specific_measurement()
	{

		ArrayList<ArrayList<Double>> NMI = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> F = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> Q = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> D = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> Time = new ArrayList<ArrayList<Double>>();

		File file = new File(this.source_file);
		BufferedReader file_reader = null;
		try
		{
			file_reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			while ((tempString = file_reader.readLine()) != null)
			{
				line++;
				if (line % 13 == 3)
				{
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++)
					{
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					double temp_1 = values_of_line.remove((values_of_line
							.size() - 1));
					double temp_2 = values_of_line.remove((values_of_line
							.size() - 1));
					values_of_line.add(temp_1);
					values_of_line.add(temp_2);
					NMI.add(values_of_line);
				}

				else if (line % 13 == 4)
				{
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++)
					{
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					double temp_1 = values_of_line.remove((values_of_line
							.size() - 1));
					double temp_2 = values_of_line.remove((values_of_line
							.size() - 1));
					values_of_line.add(temp_1);
					values_of_line.add(temp_2);
					Q.add(values_of_line);
				}

				else if (line % 13 == 8)
				{
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++)
					{
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					double temp_1 = values_of_line.remove((values_of_line
							.size() - 1));
					double temp_2 = values_of_line.remove((values_of_line
							.size() - 1));
					values_of_line.add(temp_1);
					values_of_line.add(temp_2);
					F.add(values_of_line);
				}

				else if (line % 13 == 11)
				{
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++)
					{
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					double temp_1 = values_of_line.remove((values_of_line
							.size() - 1));
					double temp_2 = values_of_line.remove((values_of_line
							.size() - 1));
					values_of_line.add(temp_1);
					values_of_line.add(temp_2);
					D.add(values_of_line);
				}

				else if (line % 13 == 12)
				{
					String[] strs;
					strs = tempString.split("\t");
					ArrayList<Double> values_of_line = new ArrayList<Double>();
					for (int i = 1; i < strs.length; i++)
					{
						double value = Double.parseDouble(strs[i]);
						values_of_line.add(value);
					}
					double temp_1 = values_of_line.remove((values_of_line
							.size() - 1));
					double temp_2 = values_of_line.remove((values_of_line
							.size() - 1));
					values_of_line.add(temp_1);
					values_of_line.add(temp_2);
					Time.add(values_of_line);
				}
			}
			file_reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (file_reader != null)
			{
				try
				{
					file_reader.close();
				} catch (IOException e1)
				{
				}
			}
		}

		ArrayList<ArrayList<Double>> NMI_temp = new ArrayList<ArrayList<Double>>();
		NMI_temp.addAll(NMI);
		for (int i = 0; i < NMI_temp.size(); i++)
		{
			ArrayList<Double> line = NMI_temp.get(i);
			line.remove(line.size() - 1);
			line.remove(line.size() - 1);
		}
		ArrayList<ArrayList<Double>> F_temp = new ArrayList<ArrayList<Double>>();
		F_temp.addAll(F);
		for (int i = 0; i < F_temp.size(); i++)
		{
			ArrayList<Double> line = F_temp.get(i);
			line.remove(line.size() - 1);
			line.remove(line.size() - 1);
		}
		ArrayList<ArrayList<Double>> Q_temp = new ArrayList<ArrayList<Double>>();
		Q_temp.addAll(Q);
		for (int i = 0; i < Q_temp.size(); i++)
		{
			ArrayList<Double> line = Q_temp.get(i);
			line.remove(line.size() - 1);
			line.remove(line.size() - 1);
		}
		ArrayList<ArrayList<Double>> D_temp = new ArrayList<ArrayList<Double>>();
		D_temp.addAll(D);
		for (int i = 0; i < D_temp.size(); i++)
		{
			ArrayList<Double> line = D_temp.get(i);
			line.remove(line.size() - 1);
			line.remove(line.size() - 1);
		}
		ArrayList<ArrayList<Double>> Time_temp = new ArrayList<ArrayList<Double>>();
		Time_temp.addAll(Time);
		for (int i = 0; i < Time_temp.size(); i++)
		{
			ArrayList<Double> line = Time_temp.get(i);
			line.remove(line.size() - 2);
		}

		double[] NMI_average_ranks = new double[NMI_temp.size()];
		double[] F_average_ranks = new double[F_temp.size()];
		double[] Q_average_ranks = new double[Q_temp.size()];
		double[] D_average_ranks = new double[D_temp.size()];
		double[] Time_average_ranks = new double[Time_temp.size()];

		System.out.println("NMI");
		for (int i = 0; i < NMI_temp.size(); i++)
		{
//			out_put(i);
			ArrayList<Double> line = NMI_temp.get(i);
			double max_value = 0;
			for (int j = 0; j < line.size(); j++)
			{
				double value = line.get(j);
				if (value > max_value)
				{
					max_value = value;
				}
//				System.out.print("&" + value + "\t");
			}
//			System.out.println("\\\\ " + max_value);

//			out_put(i);
			double[] ranks = rank_NMI_F_Q(line);
			for (int j = 0; j < ranks.length; j++)
			{
				NMI_average_ranks[j]+=ranks[j];
//				System.out.print("&" + ranks[j] + "\t");
			}
//			System.out.println();
			
			out_put(i);
			for (int j = 0; j < ranks.length; j++)
			{
				System.out.print("&" + NMI_average_ranks[j]/16 + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("F");
		for (int i = 0; i < F_temp.size(); i++)
		{
//			out_put(i);
			ArrayList<Double> line = F_temp.get(i);
			double max_value = 0;
			for (int j = 0; j < line.size(); j++)
			{
				double value = line.get(j);
				if (value > max_value)
				{
					max_value = value;
				}
//				System.out.print("&" + value + "\t");
			}
//			System.out.println("\\\\ " + max_value);

//			out_put(i);
			double[] ranks = rank_NMI_F_Q(line);
			for (int j = 0; j < ranks.length; j++)
			{
				F_average_ranks[j]+=ranks[j];
//				System.out.print("&" + ranks[j] + "\t");
			}
//			System.out.println();
			
			out_put(i);
			for (int j = 0; j < ranks.length; j++)
			{
				System.out.print("&" + F_average_ranks[j]/16 + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Q");
		for (int i = 0; i < Q_temp.size(); i++)
		{
//			out_put(i);
			ArrayList<Double> line = Q_temp.get(i);
			double max_value = 0;
			for (int j = 0; j < line.size(); j++)
			{
				double value = line.get(j);
				if (value > max_value)
				{
					max_value = value;
				}
//				System.out.print("&" + value + "\t");
			}
//			System.out.println("\\\\ " + max_value);

//			out_put(i);
			double[] ranks = rank_NMI_F_Q(line);
			for (int j = 0; j < ranks.length; j++)
			{
				Q_average_ranks[j]+=ranks[j];
//				System.out.print("&" + ranks[j] + "\t");
			}
//			System.out.println();
			
			out_put(i);
			for (int j = 0; j < ranks.length; j++)
			{
				System.out.print("&" + Q_average_ranks[j]/16 + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("D");
		for (int i = 0; i < D_temp.size(); i++)
		{
//			out_put(i);
			ArrayList<Double> line = D_temp.get(i);
			double min_deta_value = Double.MAX_VALUE;
			for (int j = 0; j < line.size(); j++)
			{
				double value = line.get(j);
				if (Math.abs(value) < min_deta_value)
				{
					min_deta_value = Math.abs(value);
				}
//				System.out
//						.print("&" + (Math.round(value * 100) / 100.0) + "\t");
			}
//			System.out.println("\\\\ "
//					+ (Math.round(min_deta_value * 100) / 100.0));

//			out_put(i);
			double[] ranks = rank_D(line);
			for (int j = 0; j < ranks.length; j++)
			{
				D_average_ranks[j]+=ranks[j];
//				System.out.print("&" + ranks[j] + "\t");
			}
//			System.out.println();
			
			out_put(i);
			for (int j = 0; j < ranks.length; j++)
			{
				System.out.print("&" + D_average_ranks[j]/16 + "\t");
			}
			System.out.println();
		}
		System.out.println();

		System.out.println("Time");
		for (int i = 0; i < Time_temp.size(); i++)
		{
//			out_put(i);
			ArrayList<Double> line = Time_temp.get(i);
			double min_value = Double.MAX_VALUE;
			for (int j = 0; j < line.size(); j++)
			{
				double value = line.get(j);
				if (value < min_value)
				{
					min_value = value;
				}
				value = value / 1000;
//				System.out
//						.print("&" + (Math.round(value * 100) / 100.0) + "\t");
			}
//			System.out.println("\\\\ " + (Math.round(min_value * 0.1) / 100.0));

//			out_put(i);
			double[] ranks = rank_Time(line);
			for (int j = 0; j < ranks.length; j++)
			{
				Time_average_ranks[j]+=ranks[j];
//				System.out.print("&" + ranks[j] + "\t");
			}
//			System.out.println();
			
			out_put(i);
			for (int j = 0; j < ranks.length; j++)
			{
				System.out.print("&" + Time_average_ranks[j]/16 + "\t");
			}
			System.out.println();
		}
	}

	public static void main(String[] args)
	{
		ChangeRealResultsToTable2 results_to_matlab = new ChangeRealResultsToTable2();
		// results_to_matlab.source_file =
		// "C:/Users/123/Desktop/tests/real-world networks results.txt";
		results_to_matlab.source_file = "C:/Users/naruto/Desktop/tests/real-world networks results.txt";
		// results_to_matlab.source_file =
		// "C:/Users/Administrator/Desktop/tests/artifical networks/mu_results.txt";
		results_to_matlab.get_matlab_code_of_a_specific_measurement();
	}
}
