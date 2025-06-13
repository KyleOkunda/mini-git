import java.io.*;
import java.util.*;

public class CommitObj {
    private Integer commitId;
    private Integer prevCommitId;
    private String commitMessage;
    private ArrayList<File> commitedFiles;

    CommitObj(String message, ArrayList<File> files){
        
        //Assign attributes
        commitMessage = message;
        commitedFiles = files;

        //Generate commitId
        //Check if a previous commit exists then increment its id
        //If none exist set default to 1000
        try{
            BufferedReader reader = new BufferedReader(new FileReader(".minigit\\commitObj.txt"));
            String line1 = reader.readLine();
            
            if(line1 == null){ //If there is no previous commit
                commitId = 1000;
                prevCommitId = null;
                BufferedWriter writerToCommitObj = new BufferedWriter(new FileWriter(".minigit\\commitObj.txt"));                
                writerToCommitObj.write(commitId + " ");
                writerToCommitObj.write(prevCommitId + " ");
                writerToCommitObj.write(commitMessage + " ");
                

                BufferedWriter writerToTrackedFile = new BufferedWriter(new FileWriter(".minigit\\commitObj.txt"));

                for(File file : commitedFiles){ //Create new files, copy of the committed file
                    String[] filenameParts = file.getName().split("\\.");
                    
                    String filename = filenameParts[0];
                    String ext ="." + filenameParts[1];
                    filename = filename + "_" + Integer.toString(commitId);
                    filename = filename + ext;
                    writerToCommitObj.write(filename + " ");

                    String pathURL = ".minigit\\trackedFiles\\" + filename;

                    writerToTrackedFile = new BufferedWriter(new FileWriter(pathURL));
                    //Read content of original
                    BufferedReader fileReader = new BufferedReader(new FileReader(file.getName()));
                    System.out.println("Reading " + file.getName());
                    Boolean stillReading = true;
                    while(stillReading){
                    String line = fileReader.readLine();
                    if(line == null){
                        stillReading = false;
                        break;
                    } else{
                        System.out.println(line);
                        writerToTrackedFile.write(line);
                    }
                    writerToTrackedFile.close();
                }

                }
                
                writerToCommitObj.close();
                
                
            } else{ //If a previous commit exists
                
                //Fetch the previous commit, last element of the arrayList
                ArrayList<String> commitsArray = new ArrayList<>();
                Boolean stillReading = true;
                while(stillReading){
                    String line = reader.readLine();
                    if(line == null){
                        stillReading = false;
                        break;
                    } else{
                        commitsArray.add(line);
                    }
                }
                String prevCommit = commitsArray.get(commitsArray.size() - 1).split(" ")[1];
                prevCommitId = Integer.parseInt(prevCommit);
                commitId = prevCommitId++;
                reader.close();

                BufferedWriter writerToCommitObj = new BufferedWriter(new FileWriter(".minigit\\commitObj.txt"));                
                writerToCommitObj.write(commitId + " ");
                writerToCommitObj.write(prevCommitId + " ");
                writerToCommitObj.write(commitMessage + " ");
                writerToCommitObj.close();

                BufferedWriter writerToTrackedFile = null;

                for(File file : commitedFiles){
                    String[] filenameParts = file.getName().split(".");
                    String filename = filenameParts[0];
                    String ext = filenameParts[1];
                    filename = filename + Integer.toString(commitId);
                    filename = filename + ext;
                    writerToCommitObj.write(filename + " ");

                    String pathURL = ".minigit\\trackedFiles\\" + filename;

                    writerToTrackedFile = new BufferedWriter(new FileWriter(pathURL));

                }
                writerToTrackedFile.close();
                
            }
        } catch(IOException e){
            System.err.println("Error occured while making the commit: \n" + e);
        }
        

    }
}
