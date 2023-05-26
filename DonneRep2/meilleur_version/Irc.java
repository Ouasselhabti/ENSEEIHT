import java.awt.*;
import java.awt.event.*;
import java.rmi.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.JLabel;

import java.lang.*;
import java.rmi.registry.*;


public class Irc extends Frame {
	public TextArea		text;
	public TextField	data;
	SharedObject		sentence;
	static String		myName;
	//pour cliniotation.
	private LightUpPanel notificationPanel;

	public static void main(String argv[]) {
		
		if (argv.length != 1) {
			System.out.println("java Irc <name>");
			return;
		}
		myName = argv[0];
	
		// initialize the system
		Client.init();
		
		// look up the IRC object in the name server
		// if not found, create it, and register it in the name server
		SharedObject s = (SharedObject) Client.lookup("IRC");
		if (s == null) {
			s = Client.create(new Sentence());
			Client.register("IRC", s);
		}
		// create the graphical part
		new Irc(s);
	}

	public Irc(SharedObject s) {
	
		setLayout(new FlowLayout());
	
		text=new TextArea(10,60);
		text.setEditable(false);
		text.setForeground(Color.red);
		add(text);
	
		data=new TextField(60);
		add(data);
	
		Button write_button = new Button("write");
		write_button.addActionListener(new writeListener(this));
		add(write_button);
		Button read_button = new Button("read");
		read_button.addActionListener(new readListener(this));
		add(read_button);
		/*ADDING ABONNER DESABONNER*/
		Button subscribe_button = new Button("subscribe");
		subscribe_button.addActionListener(new SubscribeListener(this));
		add(subscribe_button);
		
		Button unsubscribe_button = new Button("unsubscribe");
		unsubscribe_button.addActionListener(new UnsubscribeListener(this));
		add(unsubscribe_button);

		/*ADDING NOTIFICATION PANNEL*/
		Panel notification_panel = new Panel(new FlowLayout());
		notification_panel.setBackground(Color.lightGray);
		
		JLabel notification_label = new JLabel("Notifications:");
		notification_panel.add(notification_label);
		
		TextArea notification_text = new TextArea(2, 60);
		notification_text.setEditable(false);
		/*try {
			notification_text.append(Client.client.notifyCallback());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		notification_panel.add(notification_text);
		
		add(notification_panel);
		/*Ajout pour clignotement */
		notificationPanel = new LightUpPanel();
		/*end pour clignotement */
		add(notificationPanel);
		/*END ADDING NOTIFICATION PANNEL*/
		
		/*END ADDING ABONNER DESABONNER*/
		
		setSize(470,300);
		text.setBackground(Color.black); 
		show();
		
		sentence = s;
	}
}



class readListener implements ActionListener {
	Irc irc;
	public readListener (Irc i) {
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

class writeListener implements ActionListener {
	Irc irc;
	public writeListener (Irc i) {
        	irc = i;
	}
	public void actionPerformed (ActionEvent e) {
		
		// get the value to be written from the buffer
        	String s = irc.data.getText();
        	
        	// lock the object in write mode
		irc.sentence.lock_write();
		
		// invoke the method
		((Sentence)(irc.sentence.obj)).write(Irc.myName+" wrote "+s);
		irc.data.setText("");
		
		// unlock the object
		irc.sentence.unlock();
	}
}

	/*Class for subscrib listner and unsubscribelistner */
	class SubscribeListener implements ActionListener {
		Irc irc;
		public SubscribeListener(Irc i) {
			irc = i;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Client.client.subscribe(irc.sentence);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	class UnsubscribeListener implements ActionListener {
		Irc irc;
		public UnsubscribeListener(Irc i) {
			irc = i;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				Client.client.unsubscribe(irc.sentence);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}



