/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.splitter;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.splitter.ArgParser;
import org.splitter.TypeStatistics;

public class Splitter {
    
    public static int typeLine(String string) {
        int intValue = 2;
        
        if(string == null || string.equals("")){
            intValue = -1;
            return intValue;
        }
        try {
            Long.parseLong(string);
            intValue = 0;
            return intValue;
        } catch(NumberFormatException  e){
            
        }
        
        try {
            Double.parseDouble(string);
            intValue = 1;
            return intValue;
        } catch(NumberFormatException  e){
            
        }
        return intValue;
    }
    public static void main(String[] args) {
        try {
            boolean appendFile = false;
            int statistics = 0;
            String prefix = "";
            String pathTo = "";
            List<String> INPUT_FILE_NAME = new LinkedList<String>();
            String[] OUTPUT_FILE_NAME = {"integers", "floats", "strings"};
            String[] statNames = {"Minimum integer value: ","Minimum float value: ",
                    "Shortest string length:","Maximum integer value: ","Maximum float value: ","Longest string length:"};
            String dotTXT = ".txt";
            int statShort = 0;
            int statFull = 0;
            
            ArgParser parsed = new ArgParser(args);
            parsed.addKey("-p", true);
            parsed.addKey("-o", true);
            parsed.addKey("-a", false);
            parsed.addKey("-s", false);
            parsed.addKey("-f", false);
            parsed.parse();
            if (parsed.get("-p") != null)
            {
                if (!parsed.get("-p").isEmpty())
                {
                    prefix = parsed.get("-p").getFirst();
                } else {
                    //System.err.println("Warning: no output file name prefix. Using empty.");
                }
            }
            if (parsed.get("-o") != null)
            {
                if (!parsed.get("-o").isEmpty())
                {
                    pathTo = parsed.get("-o").getFirst();
                } else {
                    //System.err.println("Warning: no output file path. Using this folder.");
                }
            }
            if (parsed.get("-a") != null)
            {
                appendFile = true;
            }
            if (parsed.get("-s") != null)
            {
                statShort = 1;
            }
            if (parsed.get("-f") != null)
            {
                statFull = 1;
            }
            for (String i : parsed.get("")) {
                Path inputFile = Paths.get(i);
                File iF = inputFile.resolve(i).toFile();
                if (!Files.exists(inputFile)) {
                    System.err.println("Warning: input file "+i+" doesn't exit. Skipping.");
                } else {
                    INPUT_FILE_NAME.add(i);
                }
            }
            
            if (INPUT_FILE_NAME.isEmpty()) {
                System.err.println("Error: no input files. Exiting.");
                System.exit(1);
            }
            
            Path outputPath = Paths.get("");
            try {
                outputPath = Paths.get(pathTo);
                if (!Files.exists(outputPath)) {
                    System.err.println("Warning: no output path. Creating.");
                    Files.createDirectories(outputPath);
                }
            } catch (Exception e) {
                System.err.println("Warning: can't create path with name: "+pathTo+". Using default one.");
                outputPath = Paths.get("");
            }
                                   
            if (statFull !=0) {
                statistics = 2;
                if (statShort != 0) {
                    System.err.println("Warning: -f overrides -s.");
                }
            } else if (statShort != 0) {
                statistics = 1;
            }
            
            String line;
            ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < 3; i++) {
                ArrayList<String> singleDataType = new ArrayList<String>();
                data.add(singleDataType);
            }
            
            for (String i : INPUT_FILE_NAME) {
                FileReader reader = new FileReader(i);
                BufferedReader bufferedReader = new BufferedReader(reader);
                
                while((line = bufferedReader.readLine()) != null){
                    int typeOf = typeLine(line); 
                    if (typeOf != -1) {
                       data.get(typeOf).add(line);
                    }
                }
                reader.close();
            }
            
            for (int i = 0; i < 3; i++)
            {
                if (!data.get(i).isEmpty()) {
                    /*double fSumVal = 0;
                    double fMinVal = 0;
                    double fMaxVal = 0;
                    long iSumVal = 0;
                    long iMinVal = 0;
                    long iMaxVal = 0;*/
                    
//                    TypeStatistics<long> integerStat = new TypeStatistics();
//                    TypeStatistics<double> floatStat = new TypeStatistics();
//                    TypeStatistics<long> stringStat = new TypeStatistics();
                    
                    
//                    TypeStatistics integerStat = new TypeStatistics();
//                    integerStat.add("df".length());
//                    TypeStatistics floatStat = new TypeStatistics();
//                    TypeStatistics stringStat = new TypeStatistics();


                    TypeStatistics tStat = new TypeStatistics();
                    
                    //outputPath.resolve(pathTo);
                    //FileWriter writer = new FileWriter(pathTo + prefix + OUTPUT_FILE_NAME[i] + dotTXT, appendFile);
                    File outputFile = outputPath.resolve(prefix + OUTPUT_FILE_NAME[i] + dotTXT).toFile();
                    FileWriter writer;
                    try {
                        writer = new FileWriter(outputFile, appendFile);
                    } catch (FileNotFoundException e){
                        System.err.println("Warning: can't create file with name: "+outputFile.toString()+". Using default one.");
                        writer = new FileWriter(OUTPUT_FILE_NAME[i] + dotTXT, appendFile);
                    }
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    
                    for (int j = 0; j < data.get(i).size(); j++) {
                        bufferedWriter.write(data.get(i).get(j));
                        bufferedWriter.newLine();
                        if (statistics == 2)
                        {
                            switch(i) {
                                //case 0: integerStat.add(Integer.parseInt(data.get(i).get(j))); break;
                                //case 1: floatStat.add(Float.parseFloat(data.get(i).get(j)));break;
//                                case 0: integerStat.add(Long.parseLong(data.get(i).get(j))); break;
//                                case 1: floatStat.add(Double.parseDouble(data.get(i).get(j)));break;
//                                case 2: stringStat.add(data.get(i).get(j).length());break;
                                case 0: tStat.add(Long.parseLong(data.get(i).get(j))); break;
                                case 1: tStat.add(Double.parseDouble(data.get(i).get(j)));break;
                                case 2: tStat.add(data.get(i).get(j).length());break;
                            }
                            
                            /*float jVal;
                            if (i != 2) {
                                jVal = Float.parseFloat(data.get(i).get(j));
                                sumVal += jVal;
                            } else {
                                jVal  = data.get(i).get(j).length();
                            }
                            if (j == 0) {
                                minVal = jVal;
                                maxVal = jVal;
                            } else {
                                minVal = Math.min(minVal, jVal);
                                maxVal = Math.max(maxVal, jVal);
                            }*/
                        }
                    }
                    if (statistics != 0) {
                        System.out.println("Number of "+OUTPUT_FILE_NAME[i]+" is: "+data.get(i).size());
                        if (statistics == 2) {
                            if (i != 2) {
                                 System.out.println("Sum: "+ tStat.getSum());
                                 System.out.println("Average: "+ tStat.getSum().doubleValue()/data.get(i).size());
                            }
                            System.out.println(statNames[i]+ tStat.getMin());
                            System.out.println(statNames[i+3]+ tStat.getMax());
                            System.out.println();
                            
                            /*switch(i) {
                                case 0: 
                                    System.out.println("Sum: "+ (long)sumVal);
                                    System.out.println("Average: "+ (double)sumVal/data.get(i).size());
                                    System.out.println("Min: "+ (int)minVal);
                                    System.out.println("Max: "+ (int)maxVal);
                                    break;
                                case 1: 
                                    System.out.println("Sum: "+ sumVal);
                                    System.out.println("Average: "+ sumVal/data.get(i).size());
                                    System.out.println("Min: "+ minVal);
                                    System.out.println("Max: "+ maxVal);
                                    System.out.println();
                                    break;
                                case 2: 
                                    System.out.println("Shortest: "+ (int)minVal);
                                    System.out.println("Longest: "+ (int)maxVal);
                                    System.out.println();
                                    break;
                            }*/
                        }
                    }
                    bufferedWriter.close(); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
