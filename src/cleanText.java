import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cleanText {
    public static void cleanSymptoms(){
        Path symptomPath = Paths.get("database\\symptom\\symptomList");
        File symptomFolder = new File(symptomPath.toString());
        File[] symptomFiles = symptomFolder.listFiles();
        Pattern titlePattern = Pattern.compile("::.*::");
        TreeMap<String, LinkedHashMap<String, StringBuilder>> files = new TreeMap<>();

        String title = null;
        String line = null;

        for(File symptom: symptomFiles){
            files.put(symptom.getName(), new LinkedHashMap<String, StringBuilder>());

            try (BufferedReader reader = new BufferedReader(new FileReader(symptomPath.resolve(symptom.getName()).toString()))){        
                while((line=reader.readLine()) != null){
                    Matcher titleMatch = titlePattern.matcher(line);
                    if(titleMatch.find()){
                        title = line.trim();
                        files.get(symptom.getName()).put(title, new StringBuilder(""));
                    } else {
                        if(!line.isEmpty()){
                            files.get(symptom.getName()).get(title).append(line).append("\n");
                        }
                    }
                }
            } catch (NullPointerException e){
                System.out.println(symptom.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(symptomPath.resolve(symptom.getName()).toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
                for(Map.Entry<String, StringBuilder> content : files.get(symptom.getName()).entrySet()){
                    writer.write(content.getKey());
                    writer.write("\n");
                    writer.write(content.getValue().toString());
                    writer.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.print("End of clean up...");
    }

    public static void cleanDiagnosis(){
        Path diagnosisPath = Paths.get("database\\symptom\\symptomList");
        File diagnosisFolder = new File(diagnosisPath.toString());
        File[] diagnosisFiles = diagnosisFolder.listFiles();
        Pattern titlePattern = Pattern.compile("::.*::");
        TreeMap<String, LinkedHashMap<String, StringBuilder>> files = new TreeMap<>();

        String title = null;
        String line = null;

        for(File diagnosis: diagnosisFiles){
            files.put(diagnosis.getName(), new LinkedHashMap<String, StringBuilder>());

            try (BufferedReader reader = new BufferedReader(new FileReader(diagnosisPath.resolve(diagnosis.getName()).toString()))){        
                while((line=reader.readLine()) != null){
                    Matcher titleMatch = titlePattern.matcher(line);
                    if(titleMatch.find()){
                        title = line.trim();
                        files.get(diagnosis.getName()).put(title, new StringBuilder(""));
                    } else {
                        if(!line.isEmpty()){
                            files.get(diagnosis.getName()).get(title).append(line).append("\n");
                        }
                    }
                }
            } catch (NullPointerException e){
                System.out.println(diagnosis.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (BufferedWriter writer = Files.newBufferedWriter(Path.of(diagnosisPath.resolve(diagnosis.getName()).toString()), StandardOpenOption.TRUNCATE_EXISTING)) {
                for(Map.Entry<String, StringBuilder> content : files.get(diagnosis.getName()).entrySet()){
                    writer.write(content.getKey());
                    writer.write("\n");
                    writer.write(content.getValue().toString());
                    writer.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.print("End of clean up...");
    }
}
