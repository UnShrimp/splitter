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
                }
            }
            if (parsed.get("-o") != null)
            {
                if (!parsed.get("-o").isEmpty())
                {
                    pathTo = parsed.get("-o").getFirst();
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
                    TypeStatistics tStat = new TypeStatistics();
                    
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
                                case 0: tStat.add(Long.parseLong(data.get(i).get(j))); break;
                                case 1: tStat.add(Double.parseDouble(data.get(i).get(j)));break;
                                case 2: tStat.add(data.get(i).get(j).length());break;
                            }
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
