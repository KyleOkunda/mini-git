import java.io.*;
import java.util.*;

public class CommitObj {
    private Integer commitId;
    private Integer prevCommitId;
    private String commitMessage;
    private ArrayList<File> commitedFiles;

    CommitObj(String message, ArrayList<File> files){

        String currentDirPath = System.getProperty("user.dir");

        message = message.replace(" ", ",");

        //Get branch name
        String branchName = Main.getBranchName();
        String branchRefFile = "";
        if(branchName.equals("master")){
               branchRefFile = "commitObj.txt";
        } else{
               branchRefFile = branchName + "Obj.txt";
        }
        
        //Assign attributes
        commitMessage = "\"" + message + "\"";
        commitedFiles = files;

        //Generate commitId
        //Check if a previous commit exists then increment its id
        //If none exist set default to 1000
        try{
            BufferedReader reader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\" + branchRefFile));
            String line1 = reader.readLine();
            
            if(line1 == null){ //If there is no previous commit
                commitId = 1000;
                prevCommitId = null;
                BufferedWriter writerToCommitObj = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\" + branchRefFile));                
                writerToCommitObj.write(commitId + " ");
                writerToCommitObj.write(prevCommitId + " ");
                writerToCommitObj.write(commitMessage + " ");
                
                
                for(File file : commitedFiles){ //Create new files, copy of the committed file
                    
                    writerToCommitObj.write(file.getName() + " ");
                    File commitFolder = new File( currentDirPath + "\\.minigit\\" + branchName);
                    commitFolder.mkdir();
                    File commitIdFolder = new File(commitFolder + "\\" + Integer.toString(commitId));
                    commitIdFolder.mkdir();                    
                    String pathURL = commitIdFolder + "\\" + file.getName();

                    BufferedWriter writerToTrackedFile = new BufferedWriter(new FileWriter(pathURL));
                    //Read content of original and copy to the copy file
                    BufferedReader fileReader = new BufferedReader(new FileReader(file.getName()));
                    //System.out.println("Reading " + file.getName());
                    Boolean stillReading = true;
                    while(stillReading){
                    String line = fileReader.readLine();
                    if(line == null){
                        stillReading = false;
                        fileReader.close();
                        break;
                    } else{
                        //System.out.println(line);
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
                BufferedReader commitObjReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\" + branchRefFile));
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
                reader.close();

                BufferedWriter writerToCommitObj = new BufferedWriter(new FileWriter( currentDirPath + "\\.minigit\\" + branchRefFile));
                //Rewrite the original contents first
                for(String commitLine : commitsArray){
                    writerToCommitObj.write(commitLine);
                    writerToCommitObj.newLine();
                }
                
                writerToCommitObj.write(commitId + " ");
                writerToCommitObj.write(prevCommitId + " ");
                writerToCommitObj.write(commitMessage + " ");
               
                
                for(File file : commitedFiles){ //Create new files, copy of the committed file
                    
                    writerToCommitObj.write(file.getName() + " ");
                    File commitFolder = new File( currentDirPath + "\\.minigit\\" + branchName);
                    commitFolder.mkdir();
                    File commitIdFolder = new File(commitFolder + "\\" + Integer.toString(commitId));
                    commitIdFolder.mkdir();                    
                    String pathURL = commitIdFolder + "\\" + file.getName();

                    

                    //Read content of original and copy to the copy file
                    BufferedWriter writerToTrackingFile = new BufferedWriter(new FileWriter(pathURL));                 
                    BufferedReader trackedFileReader = new BufferedReader(new FileReader(file.getName()));                    
                    Boolean stillReadingTrackedFile = true;
                    while(stillReadingTrackedFile){   
                        
                        String line = trackedFileReader.readLine();
                        if(line == null){
                            stillReadingTrackedFile = false;
                            trackedFileReader.close();
                            break;
                        } else{
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
            String branch = Main.getBranchName();
            BufferedWriter mainTxtWriter = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\main.txt"));
            mainTxtWriter.write(Boolean.toString(Main.isCommitted));
            mainTxtWriter.newLine();
            mainTxtWriter.newLine();
            mainTxtWriter.write(branch);
            mainTxtWriter.close();
        } catch(IOException e){
            System.err.println("Error occured while emptying the staging area: \n" + e);
        }

        

    }
}
