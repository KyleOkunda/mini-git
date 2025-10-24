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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Conflict {
    
    JFrame frame;
    JPanel sidebar;
    JLabel sidebarTitle;
    JPanel editorContainer;
    JTextArea masterEditor;
    JTextArea incomingEditor;
    JTextArea acceptEditor;
    static ArrayList<String> buttonList;
    
     Conflict(Hashtable<String, String> master, Hashtable<String, String> incoming){
        int windowWidth = 1200;
        int windowHeight = 800;
        frame = new JFrame("Handling Conflict.");     

        sidebar = new JPanel();
        sidebar.setBounds(0, 0, (windowWidth / 5), windowHeight);
        sidebar.setBackground(Color.GRAY);        
        sidebar.setLayout(new FlowLayout());
        JScrollPane sidebarScroll = new JScrollPane(sidebar);
        sidebarScroll.setPreferredSize(new Dimension(200, 800));
        sidebarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Hide horizontal scrollbar
        sidebarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(sidebarScroll, BorderLayout.WEST);

        editorContainer = new JPanel();        
        editorContainer.setBounds((windowWidth / 5) + 30, 0, 930, windowHeight);
        editorContainer.setBackground(Color.GRAY);
        //editorContainer.setPreferredSize(new Dimension(930, 800));
        editorContainer.setLayout(new BorderLayout());

        masterEditor = new JTextArea();
        masterEditor.setBounds((windowWidth / 5) + 30, 0, 435, (windowHeight / 2));
        masterEditor.setBackground(Color.DARK_GRAY);
        masterEditor.setForeground(Color.WHITE);
        masterEditor.setFont(new Font("verdana", Font.PLAIN, 13));
        masterEditor.setText("Master File Editor");
        masterEditor.setEditable(true);
        masterEditor.setCaretColor(Color.WHITE);
        masterEditor.setLineWrap(false);
        JScrollPane masterScroll = new JScrollPane(masterEditor);
        masterScroll.setPreferredSize(new Dimension(488, 400));
        masterScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        masterScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        editorContainer.add(masterScroll, BorderLayout.EAST);


        incomingEditor = new JTextArea();
        incomingEditor.setBounds((windowWidth / 5) + 435, 0, 435, (windowHeight / 2));
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
        editorContainer.add(incomingScroll, BorderLayout.WEST);

        acceptEditor = new JTextArea();
        acceptEditor.setBounds((windowWidth / 5) + 30, 400, (windowWidth - (windowWidth / 5)), (windowHeight / 2));
        acceptEditor.setBackground(Color.DARK_GRAY);
        acceptEditor.setForeground(Color.WHITE);
        acceptEditor.setFont(new Font("verdana", Font.PLAIN, 13));
        acceptEditor.setText("Accept File Editor");
        acceptEditor.setEditable(true);
        acceptEditor.setCaretColor(Color.WHITE);
        acceptEditor.setLineWrap(false);
        JScrollPane acceptScroll = new JScrollPane(acceptEditor);
        acceptScroll.setPreferredSize(new Dimension(930, 400));
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

        
        
        for(String key : master.keySet()){
            JButton btn = new JButton(key);
            sidebar.add(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {                     
                    masterEditor.setText(master.get(key));
                }
            });
        }

    }

    public static void main(String[] args) {
        new Conflict(null, null);
    }


}
