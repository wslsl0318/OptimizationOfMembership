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

public class ChangeResultsToTable
{
	public String source_file;
	public String target_file;

	public ChangeResultsToTable()
	{
		this.source_file = new String();
		this.target_file = new String();
	}

	public ChangeResultsToTable(String source_file, String target_file)
	{
		this.source_file = source_file;
		this.target_file = target_file;
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
				if (line % 12 == 3)
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

				else if (line % 12 == 4)
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

				else if (line % 12 == 8)
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

				else if (line % 12 == 11)
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

				else if (line % 12 == 0)
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

		System.out.println("NMI");
		for (int i = 0; i < NMI.size(); i++)
		{
			ArrayList<Double> line = NMI.get(i);
			double max_value = 0;
			for (int j = 0; j < line.size() - 2; j++)
			{
				double value = line.get(j);
				if (value > max_value)
				{
					max_value = value;
				}
				System.out.print("&" + value + " ");
			}
			System.out.println("\\\\ " + max_value);
		}
		System.out.println();

		System.out.println("F");
		for (int i = 0; i < F.size(); i++)
		{
			ArrayList<Double> line = F.get(i);
			double max_value = 0;
			for (int j = 0; j < line.size() - 2; j++)
			{
				double value = line.get(j);
				if (value > max_value)
				{
					max_value = value;
				}
				System.out.print("&" + value + " ");
			}
			System.out.println("\\\\ " + max_value);
		}
		System.out.println();

		System.out.println("Q");
		for (int i = 0; i < Q.size(); i++)
		{
			ArrayList<Double> line = Q.get(i);
			double max_value = 0;
			for (int j = 0; j < line.size() - 2; j++)
			{
				double value = line.get(j);
				if (value > max_value)
				{
					max_value = value;
				}
				System.out.print("&" + value + " ");
			}
			System.out.println("\\\\ " + max_value);
		}
		System.out.println();

		System.out.println("D");
		for (int i = 0; i < D.size(); i++)
		{
			ArrayList<Double> line = D.get(i);
			double min_deta_value = Double.MAX_VALUE;
			for (int j = 0; j < line.size() - 2; j++)
			{
				double value = line.get(j);
				if (Math.abs(value) < min_deta_value)
				{
					min_deta_value = Math.abs(value);
				}
				System.out.print("&" + (Math.round(value * 100) / 100.0) + " ");
			}
			System.out.println("\\\\ " + (Math.round(min_deta_value * 100) / 100.0));
		}
		System.out.println();

		System.out.println("Time");
		for (int i = 0; i < Time.size(); i++)
		{
			ArrayList<Double> line = Time.get(i);
			double min_value = Double.MAX_VALUE;
			for (int j = 0; j < line.size()-1; j++)
			{
				double value = line.get(j);
				if (value < min_value)
				{
					min_value = value;
				}
				value = value / 1000;
				System.out.print("&" + (Math.round(value * 100) / 100.0) + " ");
			}
			System.out.println("\\\\ " + (Math.round(min_value * 0.1) / 100.0));
		}
	}

	public static void main(String[] args)
	{
		ChangeResultsToTable results_to_matlab = new ChangeResultsToTable();
		results_to_matlab.source_file = "C:/Users/123/Desktop/tests/artifical networks/n_results.txt";
		// results_to_matlab.source_file =
		// "C:/Users/naruto/Desktop/tests/artifical networks/mu_results.txt";
		// results_to_matlab.source_file =
		// "C:/Users/Administrator/Desktop/tests/artifical networks/mu_results.txt";
		results_to_matlab.get_matlab_code_of_a_specific_measurement();
	}
}
