import java.io.*;
import java.util.*;
public class Main {
    static Boolean isCommitted = false;
    static ArrayList<String> stagingArea = new ArrayList<>();
    static ArrayList<File> fileStagingArea = new ArrayList<File>();
    static Hashtable<String, String> checkout = new Hashtable<>();

    //To be handled
    private static void parseDirectory(File f){
        f.getAbsolutePath();
        
        
    }
    private static void isRepoFunction(String[] args){

        
        String currentDirPath = System.getProperty("user.dir");

        if(args.length == 0){
            System.out.println("Help");
            return;
        }

        //Handle command, status
        // Should show current branch, whether modified or unmodified, list untracked files
        if(args[0].equals("status")){
            if(args.length > 1){
                System.out.println("Checking status only needs one command line argument");
            } else{
                
                try{
                    //Get all files contents and compare to those of last commit
                    //Get all files
                    String cdpath = System.getProperty("user.dir");                    
                    File cd = new File(cdpath);
                    if(cd.isDirectory() && cd.exists()){
                        File[] files = cd.listFiles();
                        Boolean isModified = false;
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
                                BufferedReader commitObjReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\commitObj.txt"));
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
                                    System.out.println("No files are being tracked at the moment. \n Please add to staging and commit to track files.");
                                    return;
                                }

                                // Read contents
                                //Check if file exists in previous commit
                                File commitFile = new File(currentDirPath + "\\.minigit\\commits\\" + prevCommit + "\\" + file.getName());
                                if(!commitFile.isFile()){
                                    System.out.println();
                                    System.out.println("Untracked file: " + file.getName());
                                    System.out.println();
                                    continue;
                                }
                                BufferedReader commitFileReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\commits\\" + prevCommit + "\\" + file.getName()));
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
                        if(isModified){
                            System.out.println("Changes present. \n Add to staging and commit.");
                        } else{
                            System.out.println("Working Tree Clean. \n No changes to commit.");
                        }
                    }

                } catch(IOException e){
                    System.err.println("Error occured while checking status: " + "\n" + e);
                } 
            }
            return;
        }

        //Handle command, add
        if(args[0].equals("add")){
            
            if(args.length > 2){
                System.out.println("Adding to the staging area needs only two commandline arguemnts. \n Specify one file at a time or all of them using '*'");
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

                if(args[1].equals("*")){ // Add all files to the stagingArea arraylist

                    //Write to the main.txt
                    try{
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
                        assert line2 == null : "Line 2 is returning null";
                        if(line2 == null){
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
                
                if(line2 == null){
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
                        BufferedReader commitObjReader = new BufferedReader(new FileReader(currentDirPath + "\\.minigit\\commitObj.txt"));
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
                        System.out.println("Commit history:");
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
                File commitDir = new File(currentDirPath + "\\.minigit\\commits\\" + cid);
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
                        new Editor(Main.checkout);

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
                System.out.println("Initializing repo...");
                String cdpath = currentDir.getAbsolutePath();
                String vcspath = cdpath + "\\.minigit";
                String commitObj = vcspath + "\\commitObj.txt";
                String maintxt = vcspath + "\\main.txt";
                File vcsDir = new File(vcspath);
                File commitObjFile = new File(commitObj);
                File maintxtFile = new File(maintxt);
                
                try{
                    System.out.println(vcsDir.mkdir());
                    System.out.println(commitObjFile.createNewFile());
                    System.out.println(maintxtFile.createNewFile());
                } catch(IOException e){
                    System.err.println("Error while creating required files: \n" + e);
                }
                
            } else{
                System.out.println("This is not a repo please initialize");
            }
        }

    } }
