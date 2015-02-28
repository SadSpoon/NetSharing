import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


@SuppressWarnings("serial")
public class GUI extends JPanel{
	
	public static JFrame frame;
	static int WINDOW_WIDTH = 250;
	static int WINDOW_HEIGHT = 150;
	public static final String TITLE = "Pawsza connecting people";
	
	public GUI(){
		frame = new JFrame(TITLE);
	    frame.setContentPane(this);
	    frame.setLayout(null);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //frame.setLocationRelativeTo(null);; // center the application window
	    frame.setResizable(false);
	    frame.setBackground(Color.blue);
	    frame.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
	   	frame.setFocusable(true);
	    
	    JPanel panel_box = new JPanel();
	    panel_box.setBounds(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
	    panel_box.setLayout(new BoxLayout(panel_box, BoxLayout.Y_AXIS));
        JLabel lSSID = new JLabel("SSID");
        lSSID.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel_box.add(lSSID);
        JTextField tSSID = new JTextField();
        tSSID.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel_box.add(tSSID);
	    JLabel lPASS = new JLabel("Password");
	    lPASS.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel_box.add(lPASS);
        JPasswordField tPASS = new JPasswordField();
        tPASS.setAlignmentX(Component.CENTER_ALIGNMENT);
	    panel_box.add(tPASS);
	    

	   	JPanel buttonPane = new JPanel();
	   	buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
	   	buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 30, 10));
	   	buttonPane.add(Box.createHorizontalGlue());
	   	JButton start = new JButton();
	   	start.setText("Start hosting");
	   	start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	
				cmd.exec("netsh wlan start hostednetwork");
            }
        }); 
	   	buttonPane.add(start);
	   	buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
	   	JButton stop = new JButton();
	   	stop.setText("Stop hosting");
	   	stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	cmd.exec("netsh wlan set hostednetwork mode=allow \"ssid="+tSSID.getText()+"\" \"key="+tPASS.getPassword()+"\"");
				cmd.exec("netsh wlan stop hostednetwork");
            }
        }); 
	   	buttonPane.add(stop);
	   	panel_box.add(buttonPane);
	   	frame.add(panel_box);
	   	frame.pack();
	   	frame.setVisible(true);
	}
}
