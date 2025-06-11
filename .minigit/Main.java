import java.io.*;
import java.util.*;
public class Main {
    static boolean isCommitted = false;
    static ArrayList<File> stagingArea = new ArrayList<>();

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
                if(Main.isCommitted){
                    System.out.println("Working tree clean");
                } else{
                    System.out.println("Changes were made");
                }
            }
        }

        //Handle second command, add
        if(args[0].equals("add")){
            if(args.length > 2){
                System.out.println("Adding to the staging area needs only two commandline arguemnts. \n Specify one file at a time or all of them using '*'");
            } else if( args.length == 1){
                System.out.println("Adding to the staging requires two commandline arguments. \n" + //
                                        " Specify one file at a time or all of them using '*'");
            } else{
                String cdpath = System.getProperty("user.dir");

                // Remove /.minigit from the path
                cdpath = cdpath.replace(".minigit", ""); // Path ends with "/" or "\" depending on OS, remove this last char
                cdpath = cdpath.substring(0, cdpath.length() - 1);

                //Get list of file in the working directory
                File dir = new File(cdpath);
                File[] files = null;
                if(dir.exists() && dir.isDirectory()){
                    files = dir.listFiles(); //Array of files and folders in the working directory
                }else{
                    System.out.println("Unknown error occured, please try again");
                }

                if(args[1].equals("*")){ // Add all files to the stagingArea arraylist

                    if(files != null){
                        for(File file : files){
                            if(file.isFile()){
                                Main.stagingArea.add(file);
                            } else if(file.isDirectory()){
                                parseDirectory(file);
                            }
                        }
                    }
                    for(File f : Main.stagingArea){
                        System.out.println(f.getName());
                    }

                    System.out.println("Adding all");
                } else{
                    boolean isAdded = false; //Checks if the selected file is added to the staging area
                    if(files != null){
                        for(File file : files){
                            if(file.isFile() && file.getName().equals(args[1])){
                                isAdded = true;
                                Main.stagingArea.add(file);
                            }
                        }
                        if(isAdded == false){
                            System.out.println(args[1] + " file not found");
                        }

                    }
                    for(File f : Main.stagingArea){
                        System.out.println(f.getName());
                    }
                    
                }
            }
        }

         if(args[0].equals("staging")){
            if(args.length > 1){
                System.out.println("To see the staging area only requires one commandline argument");
            } else{
                if(stagingArea.size() == 0){
                    System.out.println("Staging area is empty");
                } else{
                    for(File f : Main.stagingArea){
                        System.out.println(f.getName());
                    }
                }
            }
         }

         if(args[0].equals("commit")){
            if(args.length > 2){
                System.out.println("Commiting requires only two commandline arguments.");
            } else if(args.length == 1){
                System.out.println("Please provide commit message");
            } else{
                CommitObj newCommit = new CommitObj();
            }
         }
       
    }

}
