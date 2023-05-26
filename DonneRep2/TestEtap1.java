import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;
import java.rmi.registry.*;


public class TestEtap1 extends Frame {
	public TextArea		text;
	public TextField	data;
	SharedObject		sentence;
	static String		myName;

	public static void main(String argv[]) {
		
		if (argv.length != 1) {
			System.out.println("java Irc <number of writers>");
			return;
		}
        int writers_nb = Integer.parseInt(argv[0]);
	
		// initialize the system
		Client.init();
		
		// look up the IRC object in the name server
		// if not found, create it, and register it in the name server
		SharedObject s = Client.lookup("IRC");
		if (s == null) {
			s = Client.create(new Sentence());
			Client.register("IRC", s);
		}
		// create the graphical part
		new TestEtap1(s,writers_nb);
	}

	public TestEtap1(SharedObject s,int i) {
	
		setLayout(new FlowLayout());
	
		text=new TextArea(10,60);
		text.setEditable(false);
		text.setForeground(Color.red);
		add(text);
	
		data=new TextField(60);
		add(data);
	
		Button write_button = new Button("Start writers");
		write_button.addActionListener(new writers(this,i));
		add(write_button);
		Button read_button = new Button("Start reader");
		read_button.addActionListener(new readers(this));
		add(read_button);
		
		setSize(470,300);
		text.setBackground(Color.black); 
		show();
		
		sentence = s;
	}
}



class readers implements ActionListener {
	TestEtap1 irc;
	public readers (TestEtap1 i) {
		irc = i;
	}
	public void actionPerformed (ActionEvent e) {
		
		// lock the object in read mode
		irc.sentence.lock_read();
		
		// invoke the method
		String s = ((Sentence)(irc.sentence.obj)).read();
		
		// unlock the object
		irc.sentence.unlock();
		
		// display the read value
		irc.text.append(s+"\n");
	}
}

class writers implements ActionListener {
	TestEtap1 irc;
    int numbers;
    static int cpt = 1;
	public writers (TestEtap1 i,int j) {
        	irc = i;
            numbers = j;
	}
	public void actionPerformed (ActionEvent e) {
        for (int i = 0 ; i <numbers;i++) {
            new Thread() {
                public void run() {
                    // get the value to be written from the buffer
                    String s = irc.data.getText();
                
                    // lock the object in write mode
                    irc.sentence.lock_write();
        
                    // invoke the method
                    ((Sentence)(irc.sentence.obj)).write("   "+(cpt++));
                    irc.data.setText("");
        
                    // unlock the object
                    irc.sentence.unlock();
                }
            }.start();
        }
		
	}
}
