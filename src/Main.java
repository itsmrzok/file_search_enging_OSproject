import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Main extends JFrame {
	
	private Container c;
	
	private Font f = new Font("Calibri", Font.BOLD, 20);
	private Font f2 = new Font("Calibri", Font.BOLD, 20);

	private JTextField t1;
	private JButton b1, shut, rest, pwr, b2;
	private JLabel menubg, pwbg;
	private JList jl;
	private JScrollPane scroll;
	
	private String FileName = new String();
	private String fname, ext;
	
	private int sw = 2;
	
	
	private String OpenFile;
	
	////////////////////////////////////////
	public static String str2[]  = new String[99999999];   
	public static String srchstr;
	public static int ind = 0;
	//********************************************************
	//DefaultDirSetup
	public static String maindirpath = "D:\\";
	//********************************************************
	///////////////////////////////////////////	
	//----------------------------------------//
	DefaultListModel listModel  = new DefaultListModel();
	
	//-----------------------------------------//
	
	public Main() {
		setVisible(true);
		setBounds(50, 50, 884, 637);
		setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
		setTitle("Operating System Project - 22CS078");
		setResizable(false);       
		
		initC();
	}
	
	public void initC() {
		c = getContentPane();
		c.setLayout(null);
		c.setBackground(Color.white);
		
		ImageIcon ico = new ImageIcon(getClass().getResource("searchbg.png"));
		this.setIconImage(ico.getImage());
		
		createUI();
	}
	
	public void createUI() {
		
		t1 = new JTextField();
		t1.setBounds(195, 148, 330, 20);
		t1.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		t1.setToolTipText("Enter File Name");
		t1.setText(FileName);
		t1.setFont(f);
		c.add(t1);
		
		
		b1 = new JButton();
		b1.setBounds(554, 132, 129, 46);
		b1.setContentAreaFilled(false);
		b1.setToolTipText("Search File");
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				CallSearchEngine();
			}
		});
		c.add(b1);
		
		
		jl = new JList(listModel);
		jl.setBackground(Color.black);
		jl.setFont(f2);
		jl.setToolTipText("Search Result");
		jl.setForeground(Color.white);
		jl.addListSelectionListener((ListSelectionListener) new ListSelectionListener() {
			  public void valueChanged(ListSelectionEvent evt) {
			    if (!evt.getValueIsAdjusting()) {
			    	if(jl.getSelectedIndex()>=0) {
			    	OpenFile = jl.getSelectedValue().toString();
			    	System.out.println("Clicked on : "+OpenFile);
			    	}
			    }
			  }
			});
		
		scroll = new JScrollPane(jl);
		scroll.setBounds(150, 220, 560, 300);
		c.add(scroll);
		
		b2 = new JButton("Open");
		b2.setBounds(370, 530, 100, 40);
		b2.setFont(f);
		b2.setToolTipText("Open Selected File");
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ind>0) {
					OpenFileWithCMD();
				}
			}
		});
		c.add(b2);
		
		ImageIcon ppbgr = new ImageIcon(getClass().getResource("pbtn1.png"));
		pwbg = new JLabel(ppbgr);
		pwbg.setBounds(6, 557, 49, 40);
		c.add(pwbg);
		
		shut = new JButton();
		shut.setBounds(24, 442, 94, 30);
		shut.setContentAreaFilled(false);
		shut.setToolTipText("Shutdown PC");
		shut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int yn = JOptionPane.showConfirmDialog(null,
						"Shutdown Your PC? _", "Confirmation", JOptionPane.YES_NO_OPTION);
				
				if(yn==JOptionPane.YES_OPTION) {
					CallPower(1);
				}
				else
					JOptionPane.showMessageDialog(null, "Operation Cancelled -_-");
			}
		});
		c.add(shut);
		
		rest = new JButton();
		rest.setBounds(24, 483, 94, 30);
		rest.setContentAreaFilled(false);
		rest.setToolTipText("Restart PC");
		rest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int yn = JOptionPane.showConfirmDialog(null,
						"Restart Your PC? _", "Confirmation", JOptionPane.YES_NO_OPTION);
				
				if(yn==JOptionPane.YES_OPTION) {
					CallPower(2);
				}
				else
					JOptionPane.showMessageDialog(null, "Operation Cancelled -_-");
			}
		});
		c.add(rest);
		
		ImageIcon mnbg = new ImageIcon(getClass().getResource("mnb.png"));
		menubg = new JLabel(mnbg);
		menubg.setBounds(6, 400, 130, 156);
		c.add(menubg);

		if(sw==1) {
			System.out.println(sw);
			menubg.setVisible(true);
			shut.setVisible(true);
			rest.setVisible(true);
			sw = 2;
		}
		else if(sw==2) {
			System.out.println(sw);
			menubg.setVisible(false);
			shut.setVisible(false);
			rest.setVisible(false);
			sw = 1;
		}

		pwr = new JButton();
		pwr.setBounds(6, 557, 49, 40);
		pwr.setContentAreaFilled(false);
		pwr.setToolTipText("Power Menu");
		pwr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				c.removeAll();
				createUI();
			}
		});
		c.add(pwr);			
		
		ImageIcon bgr = new ImageIcon(getClass().getResource("mainscrn.png"));
		JLabel page = new JLabel(bgr);
		page.setLayout(null);
		page.setBounds(-16, -7, 900, 610);
		page.setOpaque(true);
		c.add(page);
		
		repaint();
	}
	public void OpenFileWithCMD() {
		try {
			Desktop.getDesktop().open(new File(OpenFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void CallSearchEngine() {
		
		listModel.clear();
		ind = 0;
		FileName = t1.getText();
		
		System.out.println("Input String is : "+FileName);
		
		if(FileName.isEmpty())
			JOptionPane.showMessageDialog(null, "Please Enter File Name! -_-'");
		else {
			FileNameExt();
		}
	}
	////////////////////////////////////////////////
	public void FileNameExt() {
		int len = FileName.length();
		int i;
		boolean extTrue = false;
		
		for(i=len-1; i>0; i--) {
			if(FileName.charAt(i)=='.') {
				extTrue = true;
				break;
			}
		}
		
		if(extTrue) {
			FileSearchinit();
		}
		else {
			SearchAllFileType();
		}
	}
	
	/*public void AddExtToFileName() {
		String FileExtName[] = {"zip", "7z", "rar", "java", "jar", "class", "cpp", "c", "py", "pypynb", "txt", "rtf", "doc", "docx", "html", "htm", "pdf", "xls", "xlsx", "ppt", "pptx", "mp4", "mp3", "3ga", "aac", "aax", "mkv", "mov", "wmv", "avi", "flv", "dll", "bat", "jpg", "jpeg", "png", "gif", "bmp", "psd"};
		int len = FileExtName.length;
		int i;
		
		for(i=0; i<len; i++)
		{
			//System.out.println(FileName+"."+FileExtName[i]);
			FileName = strg+"."+FileExtName[i];
			FileSearchinit();
		}
		
	}*/
	public void searchAllFile(File[] arr, int level, String srch) {
		for (File f : arr) 
        {           	 
        	if(!(f.getAbsolutePath().equals(maindirpath+"System Volume Information") || f.getAbsolutePath().equals(maindirpath+"$RECYCLE.BIN"))) {
        		if(f.isDirectory()) {
        			searchAllFile(f.listFiles(), level + 1, srch);
        		}
        		
        		else if(f.isFile()) {
        			if(f.getName().contains(srch)) {
        				str2[ind] = f.getAbsolutePath();
        				System.out.println(str2[ind]);
        				listModel.addElement(str2[ind]);
        				ind++;
        			}
        		}
        	}
        }
	}
	public void SearchAllFileType() {

		File maindir = new File(maindirpath);
		str2[0] = maindirpath;
		
		srchstr = FileName;
		
		if(maindir.exists() && maindir.isDirectory())
		{
			File arr[] = maindir.listFiles();
								
			searchAllFile(arr,0, srchstr);
		}
		///////////////////////////////////////
		///////////////////////////////////////
		/*maindirpath = "E:\\";
		
		File maindir1 = new File(maindirpath);
		str2[0] = maindirpath;
		
		srchstr = FileName;
		
		if(maindir1.exists() && maindir1.isDirectory())
		{
			File arr[] = maindir1.listFiles();
								
			searchAllFile(arr,0, srchstr);
		}
		
		maindirpath = "F:\\";
		
		File maindir2 = new File(maindirpath);
		str2[0] = maindirpath;
		
		srchstr = FileName;
		
		if(maindir2.exists() && maindir2.isDirectory())
		{
			File arr[] = maindir2.listFiles();
								
			searchAllFile(arr,0, srchstr);
		}*/
		
		/*maindirpath = "G:\\";
		
		File maindir3 = new File(maindirpath);
		str2[0] = maindirpath;
		
		srchstr = FileName;
		
		if(maindir3.exists() && maindir3.isDirectory())
		{
			File arr[] = maindir3.listFiles();
								
			searchAllFile(arr,0, srchstr);
		}*/
		
		// maindirpath = "H:\\";
		
		// File maindir4 = new File(maindirpath);
		// str2[0] = maindirpath;
		
		// srchstr = FileName;
		
		// if(maindir4.exists() && maindir4.isDirectory())
		// {
		// 	File arr[] = maindir4.listFiles();
								
		// 	searchAllFile(arr,0, srchstr);
		// }
		/////////////////////////////////////////	
		////////////////////////////////////////
		if(ind<=0) {
			System.out.println("!!!!!No Such File Found!!!!!");
			listModel.addElement("!!!!!No Such File Found!!!!!");
		}
	}
	/////////////////////////////////////////////////
	//////////////////////////////////////////////////
	public void searchFile(File[] arr, int level, String srch) 
    {
        for (File f : arr) 
        {   
        	 
        	if(!(f.getAbsolutePath().equals(maindirpath+"System Volume Information") || f.getAbsolutePath().equals(maindirpath+"$RECYCLE.BIN"))) {
        		if(f.isDirectory()) {
        			searchFile(f.listFiles(), level + 1, srch);
        		}
        		
        		else if(f.isFile()) {
        			if(srch.equals(f.getName())) {
        				str2[ind] = f.getAbsolutePath();
        				System.out.println(str2[ind]);
        				listModel.addElement(str2[ind]);
        				ind++;
        			}
        		}
        	}
        }
   }
	
	public void FileSearchinit() {
			
			File maindir = new File(maindirpath);
			str2[0] = maindirpath;
			
			srchstr = FileName;
			
			if(maindir.exists() && maindir.isDirectory())
			{
				File arr[] = maindir.listFiles();
									
				searchFile(arr,0, srchstr);
			}
			
			/////////////////////////////////////////
			/////////////////////////////////////////////
			/*maindirpath = "E:\\";
			
			File maindir1 = new File(maindirpath);
			str2[0] = maindirpath;
			
			srchstr = FileName;
			
			if(maindir1.exists() && maindir1.isDirectory())
			{
				File arr[] = maindir1.listFiles();
									
				searchFile(arr,0, srchstr);
			}
			
			maindirpath = "F:\\";
			
			File maindir2 = new File(maindirpath);
			str2[0] = maindirpath;
			
			srchstr = FileName;
			
			if(maindir2.exists() && maindir2.isDirectory())
			{
				File arr[] = maindir2.listFiles();
									
				searchFile(arr,0, srchstr);
			}*/
			
			/*maindirpath = "G:\\";
			
			File maindir3 = new File(maindirpath);
			str2[0] = maindirpath;
			
			srchstr = FileName;
			
			if(maindir3.exists() && maindir3.isDirectory())
			{
				File arr[] = maindir3.listFiles();
									
				searchFile(arr,0, srchstr);
			}*/
			
			/*maindirpath = "H:\\";
			
			File maindir4 = new File(maindirpath);
			str2[0] = maindirpath;
			
			srchstr = FileName;
			
			if(maindir4.exists() && maindir4.isDirectory())
			{
				File arr[] = maindir4.listFiles();
									
				searchFile(arr,0, srchstr);
			}*/
			/////////////////////////////////////
			/////////////////////////////////////
			
			if(ind<=0) {
				System.out.println("!!!!!No Such File Found!!!!!");
				listModel.addElement("!!!!!No Such File Found!!!!!");
			}
			
			
	}
	//////////////////////////////////////////////////
	
	//////////////////////////////////////////////////
	public void CallPower(int a) {
		
		int x = 0;
		 
		Runtime runtime = Runtime.getRuntime();
		
		if(a==1) {
			try {
				runtime.exec("shutdown -s -t " +x);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				runtime.exec("shutdown -r -t " +x);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	///////////////////////////////////////////////////
	
	public static void main(String args[]) {

		new Main();
	}
	
}