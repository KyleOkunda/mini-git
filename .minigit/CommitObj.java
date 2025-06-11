import java.io.*;

public class CommitObj {
    int commitId;
    CommitObj prevCommit;
    String commitMessage;
    File[] commitedFiles;

    CommitObj(){
        
    }
}
