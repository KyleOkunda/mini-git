import java.io.*;
import java.util.*;
public class Main {
    static Boolean isCommitted = false;
    static ArrayList<String> stagingArea = new ArrayList<>();
    static ArrayList<File> fileStagingArea = new ArrayList<File>();
    static Hashtable<String, String> checkout = new Hashtable<>();
    static String branchName = "master";
    
    static String getBranchName(){
        String currentDirPath = System.getProperty("user.dir");
        String branch = currentDirPath + "\\.minigit\\main.txt";
        
        try{

            BufferedReader mainFileReader = new BufferedReader(new FileReader(branch));
            mainFileReader.readLine();
            mainFileReader.readLine();
            branch = mainFileReader.readLine();
            mainFileReader.close();
            
            
        } catch(IOException e){
            System.err.println("Error while getting branch name: \n + e");
        }

        return branch;
    }

    static String getBranchCommitObjects(String branch){
        String currentDirPath = System.getProperty("user.dir");
        String branchRef = "";
        if(branch.equals("master")){
            branchRef = "commitObj.txt";
        } else{
            branchRef = branch + "Obj.txt";
        }

        //Read the reference file
        String commitObjects = "";
        try{
            Boolean stillReading = true;
            BufferedReader refFileReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\" + branchRef));
            
            while (stillReading) {
                String line = refFileReader.readLine();
                if(line == null){
                    break;
                } else{
                    commitObjects = commitObjects + line  + "\n";
                }
                
            }
            refFileReader.close();
        } catch(IOException e){
            System.err.println("Error while reading branch reference file of branch " + branch + ":\n" + e);
        }
        return commitObjects;
        
    }

    //To be handled
    private static void parseDirectory(File f){
        f.getAbsolutePath();
        
        
    }

    static Boolean getStatus(){

        Boolean isModified = false;
                    
        try{
            
                    //Get all files contents and compare to those of last commit
                    //Get all files
                    String currentDirPath = System.getProperty("user.dir");
                    String cdpath = System.getProperty("user.dir");                    
                    File cd = new File(cdpath);
                    if(cd.isDirectory() && cd.exists()){
                        File[] files = cd.listFiles();
                        
                        for(File file : files){
                            if(file.isFile() && !file.getName().equals(".gitignore")){
                                
                                BufferedReader modFileReader = new BufferedReader(new FileReader(file.getName()));
                                Boolean stillReading = true;
                                // Read the modified file
                                String modFileContent = "";
                                while(stillReading){
                                    String line = modFileReader.readLine();
                                    if(line == null){                                        
                                        break;
                                    } else{
                                        modFileContent = modFileContent + line;
                                    }
                                }
                                modFileReader.close();

                                // Read file from last commit
                                //Get last commit
                                String branchRef = "";
                                if(Main.getBranchName().equals("master")){
                                    branchRef = "commitObj.txt";
                                } else{
                                    branchRef = Main.getBranchName() + "Obj.txt";
                                }
                                BufferedReader commitObjReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\" + branchRef));
                                ArrayList<String> commitsArray = new ArrayList<>();
                                while(stillReading){
                                    String line = commitObjReader.readLine();
                                    if(line == null){                                        
                                        break;
                                    } else{
                                        commitsArray.add(line);
                                    }
                                }
                                commitObjReader.close();
                                String prevCommit = null;
                                if(commitsArray.size() > 0){ //Check if there is a previous commit
                                   prevCommit = commitsArray.getLast().split(" ")[0];
                                } else{
                                    
                                    isModified = null;
                                    return isModified;
                                }

                                // Read contents
                                //Check if file exists in previous commit
                                File commitFile = new File(currentDirPath + "\\.minigit\\"+ Main.getBranchName() + "\\" + prevCommit + "\\" + file.getName());
                                if(!commitFile.isFile()){
                                    
                                    System.out.println("Untracked file: " + file.getName());
                                    System.out.println();
                                    continue;
                                }
                                BufferedReader commitFileReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\"+ Main.getBranchName()+ "\\"  + prevCommit + "\\" + file.getName()));
                                String commitedContent = "";
                                                                  
                                while(stillReading){
                                    String line = commitFileReader.readLine();
                                    if(line == null){                                        
                                        break;
                                    } else{
                                        commitedContent = commitedContent + line;
                                    }
                                }
                                commitFileReader.close();
                                

                                if(!modFileContent.equals(commitedContent)){
                                    isModified = true;
                                    
                                } 

                            }
                        }
                        

                    }
        } catch(IOException e){
            System.err.println("Error occured while checking status: " + "\n" + e);
        }

        return isModified;

    }

    //Overload to remove the functionality for untracked files
    static Boolean getStatus(Boolean isSwitching){
        
        Boolean isModified = false;
                    
        try{
            
                    //Get all files contents and compare to those of last commit
                    //Get all files
                    String currentDirPath = System.getProperty("user.dir");
                    String cdpath = System.getProperty("user.dir");                    
                    File cd = new File(cdpath);
                    if(cd.isDirectory() && cd.exists()){
                        File[] files = cd.listFiles();
                        
                        for(File file : files){
                            if(file.isFile() && !file.getName().equals(".gitignore")){
                                
                                BufferedReader modFileReader = new BufferedReader(new FileReader(file.getName()));
                                Boolean stillReading = true;
                                // Read the modified file
                                String modFileContent = "";
                                while(stillReading){
                                    String line = modFileReader.readLine();
                                    if(line == null){                                        
                                        break;
                                    } else{
                                        modFileContent = modFileContent + line;
                                    }
                                }
                                modFileReader.close();

                                // Read file from last commit
                                //Get last commit
                                String branchRef = "";
                                if(Main.getBranchName().equals("master")){
                                    branchRef = "commitObj.txt";
                                } else{
                                    branchRef = Main.getBranchName() + "Obj.txt";
                                }
                                BufferedReader commitObjReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\" + branchRef));
                                ArrayList<String> commitsArray = new ArrayList<>();
                                while(stillReading){
                                    String line = commitObjReader.readLine();
                                    if(line == null){                                        
                                        break;
                                    } else{
                                        commitsArray.add(line);
                                    }
                                }
                                commitObjReader.close();
                                String prevCommit = null;
                                if(commitsArray.size() > 0){ //Check if there is a previous commit
                                   prevCommit = commitsArray.getLast().split(" ")[0];
                                } else{
                                    
                                    return null;
                                }

                                // Read contents                            
                                BufferedReader commitFileReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\"+ Main.getBranchName() + "\\"  + prevCommit + "\\" + file.getName()));
                                String commitedContent = "";
                                                                  
                                while(stillReading){
                                    String line = commitFileReader.readLine();
                                    if(line == null){                                        
                                        break;
                                    } else{
                                        commitedContent = commitedContent + line;
                                    }
                                }
                                commitFileReader.close();
                                

                                if(!modFileContent.equals(commitedContent)){
                                    isModified = true;
                                    
                                } 

                            }
                        }
                        

                    }
        } catch(IOException e){
            System.err.println("Error occured while checking status: " + "\n" + e);
        }

        return isModified;

    }


    private static void isRepoFunction(String[] args){

        
        String currentDirPath = System.getProperty("user.dir");
                
        if(args.length == 0){
            System.out.println("Here are valid commands: \n ");
            System.out.println("status: \n To check out the current state of the project");
            System.out.println("add:\n To add files to the staging area");
            System.out.println("staging: \n To view files in the staging area");
            System.out.println("commit: \n To create a snapshot of the state of the project");
            System.out.println("log: \n To view commit history");
            System.out.println("checkout: \n To checkout a previous commit");
            
            return;
        }

        //Handle command, status
        // Should show current branch, whether modified or unmodified, list untracked files
        if(args[0].equals("status")){
            if(args.length > 1){
                System.out.println("Checking status only needs one command line argument");
            } else{
                Boolean isModified = Main.getStatus();
                //System.out.println(isModified);
                if(isModified == null){
                    System.out.println("No files are being tracked at the moment. \n" + //
                                                " Please add to staging and commit to track files.");
                } else if(isModified == true){
                    System.out.println("Changes present. \n" + //
                                                " Add to staging and commit.");
                } else{
                    System.out.println("Working Tree Clean. \n No changes to commit.");
                } 
            }
            return;
            
        }

        //Handle command, add
        if(args[0].equals("add")){
            
            if(args.length > 2){
                System.out.println("Adding to the staging area needs only two commandline arguemnts. \n Specify one file at a time or all of them using '.'");
            } else if( args.length == 1){
                System.out.println("Adding to the staging requires two commandline arguments. \n" + //
                                        " Specify one file at a time or all of them using '*'");
            } else{

                Main.isCommitted = false;

                String cdpath = System.getProperty("user.dir");
                assert cdpath != null : "cdpath is null";

                // Remove /.minigit from the path
                cdpath = cdpath.replace(".minigit", ""); // Path ends with "/" or "\" depending on OS, remove this last char
                cdpath = cdpath.substring(0, cdpath.length());
                assert cdpath != null : "cdpath substring is null";

                //Get list of file in the working directory
                File dir = new File(cdpath);
                File[] files = null;
                if(dir.exists() && dir.isDirectory()){
                    files = dir.listFiles(); //Array of files and folders in the working directory
                }else{                
                    System.out.println("Directory does not exist or it is not a directory");
                }

                if(args[1].equals(".")){ // Add all files to the stagingArea arraylist

                    //Write to the main.txt
                    try{
                        String branch = Main.getBranchName();
                        BufferedReader reader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\main.txt"));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\main.txt"));
                        //First line is always the isCommitted value
                        writer.write(Boolean.toString(Main.isCommitted));
                        
                        writer.newLine();
                        
                        if(files != null){
                            assert files != null : "files is null";
                        for(File file : files){
                            if(file.isFile() && !file.getName().equals(".gitignore")){
                                writer.write(file.getName() + " ");
                                
                            } else if(file.isDirectory()){
                                parseDirectory(file);
                            }
                        }
                            writer.newLine();
                            writer.write(branch);
                            writer.close();
                        }
                     //Set the stagingArea arrayList
                        reader.readLine();
                        String line2 = reader.readLine();
                        assert line2 != null : "Line 2 might be null";
                        reader.close();

                        String[] fileNames = line2.split(" ");
                        for(String fileName : fileNames){
                            Main.stagingArea.add(fileName);
                           
                        }
                        

                    System.out.println("Added all of the above");
                    } catch(IOException e){
                        System.err.println("Error in adding to staging occured: " + "\n" + e);
                    }
                    
                } else{

                    boolean isAdded = false; //Checks if the selected file is added to the staging area
                    try{
                        BufferedReader reader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\main.txt"));
                        
                        // Read current values held
                        reader.readLine();
                        String line2 = reader.readLine();
                        reader.close();

                        // Rewrite the current values
                        BufferedWriter writer = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\main.txt"));
                        writer.write(Boolean.toString(Main.isCommitted));
                        writer.newLine();

                        //Add the specified file
                        if(files != null){
                        for(File file : files){
                            if(file.isFile() && !file.getName().equals(".gitignore")){
                                isAdded = true;
                                writer.write(file.getName() + " ");
                                Main.stagingArea.add(file.getName());
                            }
                        }
                        writer.close();
                        if(isAdded == false){
                            System.out.println(args[1] + " file not found");
                            return;
                        }

                        }
                        
                       
                       
                        
                        
                        System.out.println("File Added");

                    } catch(IOException e){
                        System.err.println("Error in adding to staging:" + "\n" + e);
                    }
                    
                   
                }
            }
            return;
        }

         if(args[0].equals("staging")){
            if(args.length > 1){
                System.out.println("To see the staging area only requires one commandline argument");
            } else{
                 try(BufferedReader reader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\main.txt"))){
                        reader.readLine();
                        String line2 = reader.readLine();                        
                        
                        if(line2.equals(null) || line2.equals("")){
                            System.out.println("Staging area is empty");
                        } else{
                            String[] fileNames = line2.split(" ");
                            for(String fileName : fileNames){
                                System.out.println(fileName);
                            }
                        }
                        
                        
                   } catch(IOException e){
                        System.err.println("Error occured while accessing staging area: \n" + e);
                   }
            }
            return;
         }

         if(args[0].equals("commit")){
            
            if(args.length > 2){
                System.out.println("Commiting requires only two commandline arguments.");
                return;
            } else if(args.length == 1){
                System.out.println("Please provide commit message.");
                return;
            }

            try{ // Get the files to commit
                BufferedReader reader = new BufferedReader(new FileReader( currentDirPath + "\\.minigit\\main.txt"));
                reader.readLine();
                String line2 = reader.readLine();
                
                if(line2 == null || line2.equals("")){
                    System.out.println("Please add files to staging area before commiting");
                    return;
                }

                String[] fileNames = line2.split(" ");
                String cdpath = System.getProperty("user.dir");
                
                File cd = new File(cdpath);
                File[] files = null;
                if(cd.exists() && cd.isDirectory()){
                    files = cd.listFiles();
                }

                if(files != null){
                    
                    for(File file : files){
                    
                        for(int i = 0; i < fileNames.length; i++){
                            if(file.getName().equals(fileNames[i])){
                                
                                Main.fileStagingArea.add(file);
                            }
                        }
                
                    }
                } else{
                    System.out.println("Please create files first");
                }
                

                new CommitObj(args[1], Main.fileStagingArea);
                


            } catch(IOException e){
                System.err.println("Error occured while fetching files to commit: \n" + e);
            }
            return;
            
         }


         if(args[0].equals("log")){ //Viewing commit history
                if(args.length > 1){
                    System.out.println("Logging the commit history only requires one commandline argument");
                } else{
                    //Retrieve the commits from commitObj.txt
                    try{
                        ArrayList<String> commitArray = new ArrayList<>();
                        Boolean stillReading = true;
                        String branchRefFile = "";
                        Main.branchName = Main.getBranchName();
                        //System.out.println(Main.branchName);
                        if(Main.branchName.equals("master")){
                            branchRefFile = "commitObj.txt";
                        } else{
                            branchRefFile = Main.branchName + "Obj.txt";
                        }
                        BufferedReader commitObjReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\" + branchRefFile));
                        while(stillReading){
                            String line = commitObjReader.readLine();
                            if(line == null){
                                break;
                            } else{
                                commitArray.add(line);
                            }
                        }
                        commitObjReader.close();

                        if(commitArray.size() < 1){
                            System.out.println("No commits yet. \n");
                            return;
                        }

                        //Display the commits
                        System.out.println("Commit history on branch " + Main.getBranchName() + ":");
                        for(int i = commitArray.size() - 1; i >= 0; i--){
                            String[] commit = commitArray.get(i).split(" ");
                            String cid = commit[0];
                            String message  = commit[2];
                            message = message.replace(",", " ");
                            System.out.println( cid + " " + message);

                            
                        }

                    } catch(IOException e){
                        System.err.println("Error while fetching commits: \n" + e);
                    }
                }
                return;
         }


         if(args[0].equals("checkout")){
            
            if(args.length < 2){
                System.out.println("Checking out requires two commandline arguments");
            } else if(args.length > 2){
                System.out.println("Checking out requires only two commandline arguments");
            } else{

                String cid = args[1]; //The commit id we are checking out
                String branch = Main.getBranchName();
                File commitDir = new File(currentDirPath + "\\.minigit\\"+ branch + "\\"  + cid);
                //System.out.println(commitDir.getAbsolutePath());
                if(commitDir.exists() && commitDir.isDirectory()){

                    File[] files = commitDir.listFiles();
                    if(files != null){

                        for(File file : files){ //Copy the content of each file present
                            if(file.isFile()){
                                 try{
                            BufferedReader commitReader = new BufferedReader(new FileReader(commitDir + "\\" + file.getName()));
                            Boolean stillReading = true;
                            ArrayList<String> fileContent = new ArrayList<>();
                            while(stillReading){
                                String line = commitReader.readLine();
                                if(line == null){
                                    break;                                   
                                } else{
                                    fileContent.add(line);
                                }
                            }
                            commitReader.close();
                            String wholeContent = "";
                            for(int i = 0; i < fileContent.size(); i++){
                                wholeContent = wholeContent + fileContent.get(i) + "\n";
                            }

                            Main.checkout.put(file.getName(), wholeContent);
                            

                        } catch(IOException e){
                            System.err.println("Error occured while rewinding to the commit: \n" + e);
                        }
                            }
                        }
                        new Editor(Main.checkout, cid);

                    }else{
                        System.out.println("Appears like this commit is empty. Please try making a new commit");
                    }

                } else{
                    System.out.println("Invalid commit");
                    return;
                }

            }

            return;
         }
         
         if(args[0].equals("branch")){
            if(args.length > 2){
                System.out.println("Branching only takes two command line arguments");
            } else if(args.length == 1){
                // Display all branches and show current branch
                String branch = Main.getBranchName();
                try{
                    BufferedReader branchesReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\branches.txt"));
                    Boolean stillReading = true;
                    while(stillReading){
                        String line = branchesReader.readLine();
                        if(line == null){
                            break;
                        } else{
                            if(line.equals(branch)){
                                System.out.println(line + "  <---- Head");
                                continue;
                            } else{
                                System.out.println(line);
                            }
                        }
                    }
                    branchesReader.close();
                } catch(IOException e){
                    System.err.println("Error while fetching branches: \n" + e);
                }
                return;
            } else{

                if(!Main.getBranchName().equals("master")){
                    System.out.println("Branch not created.\n Must  be on master to create a new branch. \n Please commit and merge to create a new branch.");

                    return;
                }

                // Add branch to the branches.txt file
                try{
                    BufferedWriter branchesFileWriter = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\branches.txt"));
                    branchesFileWriter.write("master \n");
                    branchesFileWriter.write(args[1]);
                    branchesFileWriter.close();
                } catch(IOException e){
                    System.err.println("Error while addding to branches list: \n" + e);
                }

                // Add commit history for this branch
                String branchCommitPath = currentDirPath + "\\.minigit\\" + args[1];
                String branchRef = args[1] + "Obj.txt";
                String branchRefPath = currentDirPath + "\\.minigit\\" + branchRef;
                File branchCommitDir = new File(branchCommitPath);
                File branchReFile = new File(branchRefPath);
                
                try{
                    branchReFile.createNewFile();
                    branchCommitDir.mkdir();
                } catch(IOException e){
                    System.err.println("Error occured while creating branch reference file: \n" + e);
                }

                //Write to branch reference file
                try{
                    BufferedWriter branchRefWriter = new BufferedWriter(new FileWriter(branchRefPath));
                    String branchObjects = Main.getBranchCommitObjects(Main.getBranchName());
                    branchRefWriter.write(branchObjects);
                    branchRefWriter.close();
                } catch(IOException e){
                    System.err.println("Error while writing to branch reference file: \n" + e);
                }

                //Copy actual commit content from master to the new branch
                // from /.minigit/master/1000 ... to /.minigit/newBranch/1000
                
                //Create reader of the master branch and writer for the new branch
                try{
                    String master = currentDirPath + "\\.minigit\\master";
                    File masterCommits = new File(master);
                    File[] commitDirs = null;
                    if(masterCommits.isDirectory()){
                        commitDirs = masterCommits.listFiles();
                        for(File commitDir : commitDirs){
                            String dupBranchCommitDirPath = branchCommitPath + "\\" + commitDir.getName();
                            File dupBranchCommitDir = new File(dupBranchCommitDirPath);
                            dupBranchCommitDir.mkdir();

                            File[] commitDirFiles = commitDir.listFiles();
                            for(File commitDirFile : commitDirFiles){
                                String filename = commitDirFile.getName();
                                BufferedReader commitDirFileReader = new BufferedReader(new FileReader(commitDirFile.getAbsolutePath()));
                                Boolean stillReading = true;
                                String contentToCopy = "";
                                while(stillReading){
                                    String line = commitDirFileReader.readLine();
                                    if(line == null){
                                        break;
                                    } else{
                                        contentToCopy = contentToCopy + line + "\n";
                                    }
                                }
                                commitDirFileReader.close();

                                String branchFilePath = dupBranchCommitDirPath + "\\" + filename;
                                File branchFile = new File(branchFilePath);
                                branchFile.createNewFile();
                                BufferedWriter branchFileWriter = new BufferedWriter(new FileWriter(branchFile.getAbsolutePath()));
                                branchFileWriter.write(contentToCopy);
                                branchFileWriter.close();

                            }


                        }

                    } else{
                        System.err.println("Error occured while accesing the master directory");
                        return;
                    }
                } catch(IOException e){
                    System.err.println("Error while contents from master to new branch: " + args[1] + "\n" + e);
                }


            }
            return;
         }

         if(args[0].equals("switch")){

            if(args.length > 1){
                System.out.println("Swtching branches only requires one commandline argument.");
              
            } else{

                String branch = Main.getBranchName();
                // Ensure all changes are saved and commited before switching
                Boolean isModified = Main.getStatus(true);
                if(isModified == true){
                    System.out.println("Please commit changes before switching branches.");

                }else{  
                    
                    if(branch.equals("master")){ //Switch to the other branch
                        try{
                            BufferedReader branchesReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\branches.txt"));
                            branchesReader.readLine();
                            String goToBranch = branchesReader.readLine();
                            branchesReader.close();

                            BufferedReader mainTxtReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\main.txt"));
                            String line1 = mainTxtReader.readLine();                            
                            mainTxtReader.close();

                            BufferedWriter mainTxtWriter = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\main.txt"));
                            mainTxtWriter.write(line1);
                            mainTxtWriter.newLine();
                            mainTxtWriter.newLine();
                            mainTxtWriter.write(goToBranch);
                            mainTxtWriter.close();

                            System.out.println("Switched to branch: " + goToBranch);



                        } catch(IOException e){
                            System.out.println("Error occured while swirching branches: \n" + e);
                        }
                    } else{

                        try{

                            BufferedReader mainTxtReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\main.txt"));
                            String line1 = mainTxtReader.readLine();                            
                            mainTxtReader.close();


                            BufferedWriter mainTxtWriter = new BufferedWriter(new FileWriter(currentDirPath + "\\.minigit\\main.txt"));
                            mainTxtWriter.write(line1);
                            mainTxtWriter.newLine();
                            mainTxtWriter.newLine();
                            mainTxtWriter.write("master");
                            mainTxtWriter.close();

                            System.out.println("Switched to branch: " + "master");



                        } catch(IOException e){
                            System.out.println("Error occured while swirching branches: \n" + e);
                        }

                    }
                }

            }

            return;
         }
       


    }

    public static void main(String[] args) {

        Boolean isRepo = false;        
        File currentDir = new File(System.getProperty("user.dir"));        
        File[] workingDirFiles = currentDir.listFiles();
        for(File file : workingDirFiles){
            if(file.getName().equals(".minigit")){
                isRepo = true;
                break;
            }
        }

        if(isRepo){
            
            isRepoFunction(args);

        } else{

            if(args.length == 0){
                System.out.println("Help");
            }else if(args[0].equals("init")){
                System.out.println("Initializing repo... \n");
                String cdpath = currentDir.getAbsolutePath();
                String vcspath = cdpath + "\\.minigit";
                String commitObj = vcspath + "\\commitObj.txt";
                String maintxt = vcspath + "\\main.txt";
                String branchTxt = vcspath + "\\branches.txt";
                File vcsDir = new File(vcspath);
                File commitObjFile = new File(commitObj);
                File maintxtFile = new File(maintxt);
                File branchTxtFile = new File(branchTxt);
                
                try{
                    vcsDir.mkdir();
                    commitObjFile.createNewFile();
                    maintxtFile.createNewFile();
                    branchTxtFile.createNewFile();

                    //Write to the main.txt
                    BufferedWriter mainWriter  = new BufferedWriter(new FileWriter(currentDir + "\\.minigit\\main.txt"));
                    mainWriter.write("false \n");
                    mainWriter.newLine();
                    mainWriter.write("master");
                    mainWriter.close();

                    BufferedWriter branchWriter = new BufferedWriter(new FileWriter(currentDir + "\\.minigit\\branches.txt"));
                    branchWriter.write("master");
                    branchWriter.close();


                    //Set branch to master
                    Main.branchName = "master";
                    System.out.println("Successfully intiliazed repo. \n Currently on branch: " + Main.branchName);
                } catch(IOException e){
                    System.err.println("Error while creating required files: \n" + e);
                }
                
            } else{
                System.out.println("This is not a repo please initialize");
            }
        }

    } }
