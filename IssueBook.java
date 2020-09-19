package Soccer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class IssueBook extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ISSUE_PERIOD;
	private JTextField L_SSN;
	private JComboBox TITLE;
	private JTextField BIND;
	private JTextField LANGUAGE;
	private JTextField ISBN;
	private Calendar c = Calendar.getInstance();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IssueBook frame = new IssueBook();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void titleCBfilldata()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Connection conn;
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			System.out.println("connected");
			Statement query = (Statement) conn.createStatement();
			String selectQuery = "select * from BOOK where IN_LIBRARY= true";
			ResultSet rs = query.executeQuery(selectQuery);
			while(rs.next())
			{
				TITLE.addItem(rs.getString("title"));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	/**
	 * Create the frame.
	 */
	public IssueBook() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 397);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIssueBook = new JLabel("ISSUE BOOK");
		lblIssueBook.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblIssueBook.setBounds(148, 11, 161, 14);
		contentPane.add(lblIssueBook);
		
		JLabel lblIssuePeriod = new JLabel("ISSUE PERIOD");
		lblIssuePeriod.setBounds(36, 250, 70, 14);
		contentPane.add(lblIssuePeriod);
		
		JButton btnNewButton = new JButton("SUBMIT");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ARGS) {
				String S_SSN= "746-11-3480";
				String GRACE_PERIOD="7";
				Date date = new Date();
				c.setTime(date);
				java.sql.Date ISSUE_DATE = new java.sql.Date(c.getTimeInMillis());
				Date date1 = new Date();
				c.setTime(date1);
				c.add(Calendar.DATE, 28);
				java.sql.Date NOTIFICATION_DATE = new java.sql.Date(c.getTimeInMillis());
				createNewEntry(TITLE,BIND,LANGUAGE,ISBN,S_SSN,L_SSN,NOTIFICATION_DATE,GRACE_PERIOD,ISSUE_PERIOD,ISSUE_DATE);
				System.exit(0);
				
			}
		});
		btnNewButton.setBounds(159, 300, 89, 23);
		contentPane.add(btnNewButton);
		
		ISSUE_PERIOD = new JTextField();
		ISSUE_PERIOD.setColumns(10);
		ISSUE_PERIOD.setBounds(235, 247, 86, 20);
		contentPane.add(ISSUE_PERIOD);
		
		JLabel lblSsn = new JLabel("SSN");
		lblSsn.setBounds(36, 210, 60, 14);
		contentPane.add(lblSsn);
		
		L_SSN = new JTextField();
		L_SSN.setBounds(235, 207, 86, 20);
		contentPane.add(L_SSN);
		L_SSN.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("TITLE");
		lblNewLabel.setBounds(36, 64, 46, 14);
		contentPane.add(lblNewLabel);
		
		TITLE= new JComboBox();
		TITLE.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent args1) {
				try {
					Class.forName("com.mysql.jdbc.Driver").newInstance();
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					Connection conn;
					conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
					System.out.println("connected");
					String selectQuery = "select * from BOOK where TITLE=?";
					PreparedStatement pst = conn.prepareStatement(selectQuery);
					pst.setString(1, (String)TITLE.getSelectedItem());
					ResultSet rs = pst.executeQuery();
					while(rs.next())
					{
						BIND.setText(rs.getString("BIND"));
						LANGUAGE.setText(rs.getString("LANGUAGE"));
						ISBN.setText(rs.getString("ISBN"));						
					}
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();

				}
				
			}
		});
		TITLE.setBounds(235, 61, 114, 20);
		contentPane.add(TITLE);
		
		JLabel lblBind = new JLabel("BIND");
		lblBind.setBounds(36, 101, 46, 14);
		contentPane.add(lblBind);
		
		JLabel lblLanguage = new JLabel("LANGUAGE");
		lblLanguage.setBounds(36, 133, 70, 14);
		contentPane.add(lblLanguage);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(36, 173, 46, 14);
		contentPane.add(lblIsbn);
		
		BIND = new JTextField();
		BIND.setBounds(235, 98, 86, 20);
		contentPane.add(BIND);
		BIND.setColumns(10);
		
		LANGUAGE = new JTextField();
		LANGUAGE.setColumns(10);
		LANGUAGE.setBounds(235, 130, 86, 20);
		contentPane.add(LANGUAGE);
		
		ISBN = new JTextField();
		ISBN.setColumns(10);
		ISBN.setBounds(235, 170, 86, 20);
		contentPane.add(ISBN);
		
		JLabel lblNewLabel_1 = new JLabel("Choose the title of the book you want to borrow:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		lblNewLabel_1.setBounds(36, 39, 249, 14);
		contentPane.add(lblNewLabel_1);
		 titleCBfilldata();
	}
	private void createNewEntry(final JComboBox TITLE, final JTextField BIND,
			final JTextField LANGUAGE,final JTextField ISBN,String S_SSN,final JTextField L_SSN,java.sql.Date NOTIFICATION_DATE,
			String GRACE_PERIOD,final JTextField ISSUE_PERIOD,java.sql.Date ISSUE_DATE){
			try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/LIBRARY", "root", "root");
			PreparedStatement prep = conn.prepareStatement("insert into ISSUE_BOOK values (?,?,?,?,?,?,?,?,?,?)");) {
			
			prep.setString(1,(String)TITLE.getSelectedItem());
			prep.setString(2,  BIND.getText());
			prep.setString(3, LANGUAGE.getText());
			prep.setString(4, ISBN.getText());
			prep.setString(5,  S_SSN);
			prep.setString(6, L_SSN.getText());
			prep.setDate(7, NOTIFICATION_DATE);
			prep.setString(8,  GRACE_PERIOD);
			prep.setString(9, ISSUE_PERIOD.getText());
			prep.setDate(10, ISSUE_DATE);
			int res = prep.executeUpdate();

			if(res == 1 ) {
				JOptionPane.showMessageDialog(null, "Book issued successfully \n return by "+NOTIFICATION_DATE);
			}
		}catch(java.sql.SQLIntegrityConstraintViolationException userExsistException) {
			JOptionPane.showMessageDialog(null, "Book cannot be issued currently as it already exist");
		}
		catch (Exception exception) {

		}
	}

}
