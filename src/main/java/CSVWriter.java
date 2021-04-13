import com.google.common.base.Joiner;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.*;


public class CSVWriter {

    private static HashMap<String, List<Enrollee>> hashMap;

    public static void main(String[] args) throws IOException {

        hashMap  = new HashMap<String,List<Enrollee>>();

        //Reader for CSV Files, current path to Temp File
        Reader reader = new FileReader("C:\\Users\\Nick\\IdeaProjects\\NewCSV\\newEnrolleesFile.csv");
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withHeader("userId","name","version","insuranceCompany")
                .withIgnoreHeaderCase()
                .withTrim());

        for (CSVRecord csvRecord : csvParser) {
            // Using Parser to access values by the names assigned to each column
            boolean isNew = true;
            String userId = csvRecord.get("userId");
            String name = csvRecord.get("name");
            Integer version = Integer.parseInt(csvRecord.get("version"));
            String insuranceCompany = csvRecord.get("insuranceCompany");

            //Set Values into Enrollee Object
            Enrollee newEnrollee = new Enrollee();
            newEnrollee.setUserId(userId);
            newEnrollee.setName(name);
            newEnrollee.setVersion(version);
            newEnrollee.setInsuranceCompany(insuranceCompany);

            // Map to see if the insurance company exists
            if(hashMap.containsKey(insuranceCompany))
            {
                // Check for the duplicate User Id
                for(int i = 0; i < hashMap.get(insuranceCompany).size(); i++)
                {
                    if(hashMap.get(insuranceCompany).get(i).getUserId().equals(newEnrollee.getUserId()))
                    {
                        // New Enrollee check for the latest Version
                        if(hashMap.get(insuranceCompany).get(i).getVersion() < newEnrollee.getVersion())
                        {
                            hashMap.get(insuranceCompany).set(i, newEnrollee);
                            isNew = false;
                            break;
                        }
                    }
                }

                // New Enrolle object add it to the hashMap
                if(isNew) {
                    hashMap.get(insuranceCompany).add(newEnrollee);
                }
            }
            else
            {
                // Map the new Enrolle list to insurance company after creating it
                List<Enrollee> newList = new ArrayList<Enrollee>();
                newList.add(newEnrollee);
                hashMap.put(insuranceCompany, newList);
            }
        }

        //File Number
        int fileNum = 1;

        // In the hashmap go through list of Enrolless
        for (Map.Entry<String,List<Enrollee>> entry : hashMap.entrySet()) {

            // Enrolle list by Names and sort them
            Collections.sort(hashMap.get(entry.getValue().get(0).getInsuranceCompany()), compareByName);
            String fileName="C:\\Users\\Nick\\IdeaProjects\\NewCSV\\insuranceFile"+fileNum+".csv";
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(fw);

            //Print the new insuranceFile.csv
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("userId","name","version","insuranceCompany"));

            for(int i = 0; i < hashMap.get(entry.getValue().get(0).getInsuranceCompany()).size(); i++) {
                //Create new record for csv file
                csvPrinter.printRecord(entry.getValue().get(i).getUserId(), entry.getValue().get(i).getName(),
                        entry.getValue().get(i).getVersion(), entry.getValue().get(i).getInsuranceCompany());
            }
            //File number + Name Increment them
            fileNum++;
            csvPrinter.flush();
        }

        // Print out the HashMap using the Guava method
        System.out.println(convertWithGuava(hashMap));
    }

    // CovertwithGuava to print in the screen the json string
    public static String convertWithGuava(HashMap<String, List<Enrollee>> map) {
        System.out.println("");
        return Joiner.on(",").withKeyValueSeparator("=").join(map);
    }

    public HashMap<String, List<Enrollee>> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, List<Enrollee>> hashMap) {
        this.hashMap = hashMap;
    }

    // Sort Enrolless by Name
    static Comparator<Enrollee> compareByName = new Comparator<Enrollee>() {
        @Override
        public int compare(Enrollee o1, Enrollee o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };
}
