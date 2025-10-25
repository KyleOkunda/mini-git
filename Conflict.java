import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Conflict {
    
    JFrame frame;
    JPanel sidebar;
    JLabel sidebarTitle;
    JPanel titles;
    JPanel editorContainer;
    JTextArea masterEditor;
    JTextArea incomingEditor;
    JTextArea acceptEditor;
    JPanel btnArea;
    static ArrayList<String> buttonList;
    
     Conflict(Hashtable<String, String> master, Hashtable<String, String> incoming){
        int windowWidth = 1200;
        int windowHeight = 800;
        frame = new JFrame("Handling Conflict.");     

        sidebar = new JPanel();
        sidebar.setBounds(0, 0, (windowWidth / 5), windowHeight);
        sidebar.setBackground(Color.GRAY);
        sidebar.setPreferredSize(new Dimension(200, 800));
        sidebar.setLayout(new FlowLayout());
        JScrollPane sidebarScroll = new JScrollPane(sidebar);
        sidebarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Hide horizontal scrollbar
        sidebarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(sidebarScroll, BorderLayout.WEST);

        JButton acceptBtn = new JButton("Accept Changes");
        //acceptBtn.setBounds(0, 750, 240, 50);        
        frame.add(sidebarScroll, BorderLayout.WEST);        

        editorContainer = new JPanel();        
        editorContainer.setBounds((windowWidth / 5) + 30, 0, 930, windowHeight);
        editorContainer.setBackground(Color.GRAY);
        editorContainer.setPreferredSize(new Dimension(930, 800));
        editorContainer.setLayout(new BorderLayout());

        titles = new JPanel();
        titles.setBounds((windowWidth / 5) + 30, 0, (windowWidth - (windowWidth / 5)), 50);
        titles.setLayout(new BorderLayout());
        //titles.setPreferredSize(new Dimension(930, 50));
        editorContainer.add(titles, BorderLayout.NORTH);

        masterEditor = new JTextArea();
        JLabel masterTitle = new JLabel("Master File.");
        masterTitle.setBounds((windowWidth / 5) + 30, 0, 435, 50);        
        masterEditor.setBounds((windowWidth / 5) + 30, 50, 435, (windowHeight / 2) - 50);
        masterEditor.setBackground(Color.DARK_GRAY);
        masterEditor.setForeground(Color.WHITE);
        masterEditor.setFont(new Font("verdana", Font.PLAIN, 13));
        masterEditor.setText("Master File Editor");
        masterEditor.setEditable(true);
        masterEditor.setCaretColor(Color.WHITE);
        masterEditor.setLineWrap(false);
        JScrollPane masterScroll = new JScrollPane(masterEditor);
        masterScroll.setPreferredSize(new Dimension(488, 350));
        masterScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        masterScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        titles.add(masterTitle, BorderLayout.EAST);
        editorContainer.add(masterScroll, BorderLayout.EAST);


        incomingEditor = new JTextArea();
        JLabel incomingTitle = new JLabel("Incoming File.");
        incomingTitle.setBounds((windowWidth / 5) + 435, 0, 435, 50);                  
        incomingEditor.setBounds((windowWidth / 5) + 435, 50, 435, (windowHeight / 2));
        incomingEditor.setBackground(Color.DARK_GRAY);
        incomingEditor.setForeground(Color.WHITE);
        incomingEditor.setFont(new Font("verdana", Font.PLAIN, 13));
        incomingEditor.setText("Incoming File Editor");
        incomingEditor.setEditable(true);
        incomingEditor.setCaretColor(Color.WHITE);
        incomingEditor.setLineWrap(false);
        JScrollPane incomingScroll = new JScrollPane(incomingEditor);
        incomingScroll.setPreferredSize(new Dimension(488, 400));
        incomingScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        incomingScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        titles.add(incomingTitle, BorderLayout.WEST);
        editorContainer.add(incomingScroll, BorderLayout.WEST);

        acceptEditor = new JTextArea();
        acceptEditor.setBounds((windowWidth / 5) + 30, 400, (windowWidth - (windowWidth / 5)), (windowHeight / 2));
        acceptEditor.setBackground(Color.DARK_GRAY);
        acceptEditor.setForeground(Color.WHITE);
        acceptEditor.setFont(new Font("verdana", Font.PLAIN, 13));
        //acceptEditor.setText("Accept File Editor");
        acceptEditor.setEditable(true);
        acceptEditor.setCaretColor(Color.WHITE);
        acceptEditor.setLineWrap(false);
        JScrollPane acceptScroll = new JScrollPane(acceptEditor);
        acceptScroll.setPreferredSize(new Dimension(930, 350));
        acceptScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        acceptScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        editorContainer.add(acceptScroll, BorderLayout.SOUTH);

        frame.add(editorContainer);
        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setLayout(null);

        acceptBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {                     
                    int result = JOptionPane.showConfirmDialog(null,
                     "Are you sure you want to accept these changes?\n Once accepted the result cannot be changed.",
                     "Please confrim before proceeding.",
                     JOptionPane.OK_CANCEL_OPTION);
                    
                     if(result == JOptionPane.OK_OPTION){
                        System.out.println("Creating new commit for resolved conflict.");
                     } else{
                        System.out.println("Back to resolving conflict.");
                     }

                }
            });

        
        for(String key : master.keySet()){
            JButton btn = new JButton(key);
            sidebar.add(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {                     
                    masterEditor.setText(master.get(key));
                    incomingEditor.setText(incoming.get(key));
                    acceptEditor.setText("Please choose one format to accept");
                }
            });
        }
        sidebar.add(acceptBtn);

        
    }

    public static void main(String[] args) {
        Hashtable<String, String> base = new Hashtable<>();
        Hashtable<String, String> master = new Hashtable<>();
        
        base.put("index", "line on index \nsecond line on index");
        base.put("node", "line on node \n" + //
                        "second line on node");
        base.put("app", "line on app \n" + //
                        "second line on app");
        base.put("main", "line on main \n" + //
                        "second line on main");

        master.put("index", "line on index \nsecond line on index");
        master.put("node", "line on node \n" + //
                        "second line on node");
        master.put("app", "line on app \n" + //
                        "second line on app");
        master.put("main", "line on main \n" + //
                        "second line on main");
        new Conflict(master, base);
    }


}
