import java.sql.Time;

public class CommitObj {
    int commitId;
    CommitObj prevCommit;
    Time time;
    String commitMessage;
    File[] commitedFiles;
}
