import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CommitObj {
    private Integer commitId;
    private Integer prevCommitId;
    private String commitMessage;
    private ArrayList<File> commitedFiles;

    CommitObj(String message, ArrayList<File> files){

        message = message.replace(" ", ",");
        
        //Assign attributes
        commitMessage = "\"" + message + "\"";
        commitedFiles = files;

        //Generate commitId
        //Check if a previous commit exists then increment its id
        //If none exist set default to 1000
        try{
            BufferedReader reader = new BufferedReader(new FileReader(".minigit\\commitObj.txt"));
            String line1 = reader.readLine();
            reader.close();
            
            if(line1 == null){ //If there is no previous commit
                commitId = 1000;
                prevCommitId = null;
                BufferedWriter writerToCommitObj = new BufferedWriter(new FileWriter(".minigit\\commitObj.txt"));                
                writerToCommitObj.write(commitId + " ");
                writerToCommitObj.write(prevCommitId + " ");
                writerToCommitObj.write(commitMessage + " ");
                
                
                for(File file : commitedFiles){ //Create new files, copy of the committed file
                    
                    writerToCommitObj.write(file.getName() + " ");
                    File commitFolder = new File(".minigit\\commits");
                    commitFolder.mkdir();
                    File commitIdFolder = new File(commitFolder + "\\" + Integer.toString(commitId));
                    commitIdFolder.mkdir();
                    System.out.println(commitFolder.isDirectory());
                    String pathURL = commitIdFolder + "\\" + file.getName();

                    BufferedWriter writerToTrackedFile = new BufferedWriter(new FileWriter(pathURL));
                    //Read content of original and copy to the copy file
                    BufferedReader fileReader = new BufferedReader(new FileReader(file.getName()));
                    System.out.println("Reading " + file.getName());
                    Boolean stillReading = true;
                    while(stillReading){
                    String line = fileReader.readLine();
                    if(line == null){
                        stillReading = false;
                        fileReader.close();
                        break;
                    } else{
                        System.out.println(line);
                        writerToTrackedFile.write(line);
                        writerToTrackedFile.newLine();
                    }
                    
                }
                writerToTrackedFile.close();

                }
                
                writerToCommitObj.close();
                reader.close();
                
                
            } else{ //If a previous commit exists
                
                //Fetch the previous commit, last element of the arrayList                
                ArrayList<String> commitsArray = new ArrayList<>();
                Boolean stillReadingCommitObj = true;
                BufferedReader commitObjReader = new BufferedReader(new FileReader(".minigit\\commitObj.txt"));
                while(stillReadingCommitObj){
                    
                    String line = commitObjReader.readLine();
                    
                    if(line == null){
                        stillReadingCommitObj = false;
                        break;
                    } else{                        
                        commitsArray.add(line);
                    }
                }
                commitObjReader.close();
                String prevCommit = commitsArray.get(commitsArray.size() - 1).split(" ")[0];
                prevCommitId = Integer.parseInt(prevCommit);
                commitId = prevCommitId + 1;                

                BufferedWriter writerToCommitObj = new BufferedWriter(new FileWriter(".minigit\\commitObj.txt"));
                //Rewrite the original contents first
                for(String commitLine : commitsArray){
                    writerToCommitObj.write(commitLine);
                    writerToCommitObj.newLine();
                }
                
                writerToCommitObj.write(commitId + " ");
                writerToCommitObj.write(prevCommitId + " ");
                writerToCommitObj.write(commitMessage + " ");
               
                // Create a folder for this commit                
                Path path = Paths.get(".minigit", "commits", Integer.toString(commitId));
                try{

                    Files.createDirectory(path);
                    System.out.println("Folder created successfully");

                } catch(IOException e){
                    System.err.println("Error while creating new dir: \n" + e);
                    e.printStackTrace();
                }

                for(File file : commitedFiles){ //Create new files, copy of the committed file
                    
                    writerToCommitObj.write(file.getName() + " ");
                    Path pathURL = Paths.get(".minigit", "commits", Integer.toString(commitId), file.getName());
                    try{

                        Files.createFile(pathURL);
                        System.out.println("Folder created successfully");

                    } catch(IOException e){
                        System.err.println("Error while creating new file: \n" + e);
                        e.printStackTrace();
                    }
                    //File newFile = new File(pathURL);
                    //newFile.createNewFile();

                    //Read content of original and copy to the copy file
                    String filepath = pathURL.toString();
                    BufferedWriter writerToTrackingFile = new BufferedWriter(new FileWriter(filepath));                 
                    BufferedReader trackedFileReader = new BufferedReader(new FileReader(file.getName()));
                    System.out.println("Reading " + file.getName());
                    Boolean stillReadingTrackedFile = true;
                    while(stillReadingTrackedFile){   
                        
                        String line = trackedFileReader.readLine();
                        if(line == null){
                            stillReadingTrackedFile = false;
                            trackedFileReader.close();
                            break;
                        } else{
                            System.out.println(line);
                            writerToTrackingFile.write(line);                        
                            writerToTrackingFile.newLine();
                        }
                                        

                    }
                    writerToTrackingFile.close(); 
                
                             
            }
            writerToCommitObj.close();
        }
        } catch(IOException e){
            System.err.println("Error occured while making the commit: \n" + e);
        }

        //Empty the staging area
        Main.isCommitted = true;
        Main.stagingArea.clear();
        Main.fileStagingArea.clear();
        try{
            BufferedWriter mainTxtWriter = new BufferedWriter(new FileWriter(".minigit\\main.txt"));
            mainTxtWriter.write(Boolean.toString(Main.isCommitted));
            mainTxtWriter.close();
        } catch(IOException e){
            System.err.println("Error occured while emptying the staging area: \n" + e);
        }

        

    }
}
