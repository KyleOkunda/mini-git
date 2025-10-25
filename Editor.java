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

public class Editor {
    
    JFrame frame;
    JPanel sidebar;
    JLabel sidebarTitle;
    JTextArea editor;
    static ArrayList<String> buttonList;
    
     Editor(Hashtable<String, String> checkout, String commitId){
        int windowWidth = 1200;
        int windowHeight = 800;
        frame = new JFrame("Viewing commit " + commitId + " on branch " + Main.getBranchName());     

        sidebar = new JPanel();
        sidebar.setBounds(0, 0, (windowWidth / 5), windowHeight);
        sidebar.setBackground(Color.GRAY);        
        sidebar.setLayout(new FlowLayout());
        sidebar.setPreferredSize(new Dimension(200, 800));
        JScrollPane sidebarScroll = new JScrollPane(sidebar);
        //sidebarScroll.setPreferredSize(new Dimension(200, 800));
        sidebarScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Hide horizontal scrollbar
        sidebarScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(sidebarScroll, BorderLayout.WEST);
        
        editor = new JTextArea();
        editor.setBounds((windowWidth / 5) + 30, 0, 700, windowHeight);
        editor.setBackground(Color.DARK_GRAY);
        editor.setForeground(Color.WHITE);
        editor.setFont(new Font("verdana", Font.PLAIN, 13));
        editor.setText("Please Select a file to checkout");
        editor.setEditable(false);
        editor.setCaretColor(Color.WHITE);
        editor.setLineWrap(false);
        JScrollPane scroll = new JScrollPane(editor);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.add(scroll, BorderLayout.CENTER);


        frame.setSize(windowWidth, windowHeight);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setBackground(Color.BLACK);  

        
        
        for(String key : checkout.keySet()){
            JButton btn = new JButton(key);
            sidebar.add(btn);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {                     
                    editor.setText(checkout.get(key));
                }
            });
        }

    }

}
