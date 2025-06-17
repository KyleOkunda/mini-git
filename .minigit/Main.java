import java.io.*;
import java.util.*;
public class Main {
    static Boolean isCommitted = false;
    static ArrayList<String> stagingArea = new ArrayList<>();
    static ArrayList<File> fileStagingArea = new ArrayList<File>();

    //To be handled
    private static void parseDirectory(File f){
        f.getAbsolutePath();
        
        
    }

    public static void main(String[] args) {

        //Handle first command, status
        if(args[0].equals("status")){
            if(args.length > 1){
                System.out.println("Checking status only needs one command line argument");
            } else{
                // Fetch data from main.txt line 1
                try{
                    BufferedReader reader = new BufferedReader(new FileReader(".minigit\\main.txt"));
                    String line = reader.readLine();
                    Main.isCommitted = Boolean.parseBoolean(line);
                    reader.close();

                    if(Main.isCommitted){
                        System.out.println("Working tree clean");
                    } else{
                        System.out.println("Changes present, please add to staging and commit");
                    }

                } catch(IOException e){
                    System.err.println("Error occured: " + "\n" + e);
                }
            }
            return;
        }

        //Handle second command, add
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
                        BufferedReader reader = new BufferedReader(new FileReader(".minigit\\main.txt"));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(".minigit\\main.txt"));
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
                        BufferedReader reader = new BufferedReader(new FileReader(".minigit\\main.txt"));
                        
                        // Read current values held
                        reader.readLine();
                        String line2 = reader.readLine();
                        reader.close();

                        // Rewrite the current values
                        BufferedWriter writer = new BufferedWriter(new FileWriter(".minigit\\main.txt"));
                        writer.write(Boolean.toString(Main.isCommitted));
                        writer.newLine();
                        
                        if(line2 != null){
                            String[] fileNames = line2.split(" ");
                            for(String fileName : fileNames){
                            
                            if(!fileName.equals(args[1])){
                                Main.stagingArea.add(fileName);
                                writer.write(fileName + " ");
                            }                     
                           
                        }
                        }

                        //Add the specified file
                        if(files != null){
                        for(File file : files){
                            if(file.isFile() && file.getName().equals(args[1])){
                                isAdded = true;
                                writer.write(file.getName());
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
                 try(BufferedReader reader = new BufferedReader(new FileReader(".minigit\\main.txt"))){
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
                BufferedReader reader = new BufferedReader(new FileReader(".minigit\\main.txt"));
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
                        BufferedReader commitObjReader = new BufferedReader(new FileReader(".minigit\\commitObj.txt"));
                        while(stillReading){
                            String line = commitObjReader.readLine();
                            if(line == null){
                                break;
                            } else{
                                commitArray.add(line);
                            }
                        }
                        commitObjReader.close();

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
                

            }

            return;
         }
       
    }

}
