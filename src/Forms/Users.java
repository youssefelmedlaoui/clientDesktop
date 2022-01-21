package Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dao.ServerDaoRemote;
import entities.User;

public class Users extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
    private int id = -1;
    private JTextField nomField;
    private int i;
    private Long idS;
    private String[] l= null;
    private String[] l2= null;
    private String type;
    private String bloc=null;
    private Long idB;
    private JTable table;
    private JTextField prenomField;
    private JTextField emailField;
    private JTextField telField;
    private JDateChooser dateChooser;
    
    public static ServerDaoRemote lookUpSalleDaoRemote() throws NamingException {
		Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:4200");
		final Context context = new InitialContext(jndiProperties);

		return (ServerDaoRemote) context.lookup("ejb:earWeb3/Server/ServiceDao!dao.ServerDaoRemote");}
	/**
	 * Launch the application.
	 */
 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Users frame = new Users();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void recharger() {
        model.setRowCount(0);
        try {
        	ServerDaoRemote stub = lookUpSalleDaoRemote();
			List<User> users =stub.findAllUsers();
            for (User u : users) {
                model.addRow(new Object[]{
                    u.getIdUser(),
                    u.getNom(),
                    u.getPrenom(),
                    u.getEmail(),
                    u.getTel(),
                    u.getDate()
                       
                }
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

	/**
	 * Create the frame.
	 * 
	 */
	
	public Users() {
		setTitle("Users");
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		//updateComboBoxBloc();
		
	
		setResizable(true);
		setBounds(100, 100, 824, 435);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.WHITE);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 808, 250);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nom :");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		lblNewLabel.setBounds(21, 13, 53, 14);
		panel.add(lblNewLabel);
		
		nomField = new JTextField();
		nomField.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		nomField.setBounds(94, 11, 135, 21);
		panel.add(nomField);
		nomField.setColumns(10);
		
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					String nom=nomField.getText();
					String prenom=prenomField.getText();
					String email=emailField.getText();
					String tel=telField.getText();
					Date dateNais=dateChooser.getDate();

					User b=new User(nom,prenom,email,tel,dateNais);
					stub.create(b);
					recharger();
					 nomField.setText("");
						prenomField.setText("");
						emailField.setText("");
						telField.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
				

			
		});
		
		
		btnNewButton.setBounds(75, 138, 103, 33);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("UPDATE");
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					 stub.update(stub.findUserById(idB),nomField.getText(),prenomField.getText(),emailField.getText(),telField.getText(),dateChooser.getDate());
						
					 recharger();
					
					 nomField.setText("");
						prenomField.setText("");
						emailField.setText("");
						telField.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(283, 138, 121, 33);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("DELETE");
		btnNewButton_2.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(Color.BLACK);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					  System.out.println(idB);
					  stub.delete(stub.findUserById(idB));
					
					 recharger();
					 nomField.setText("");
						prenomField.setText("");
						emailField.setText("");
						telField.setText("");
						dateChooser.setCalendar(null);
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setBounds(538, 138, 108, 33);
		panel.add(btnNewButton_2);
		
		prenomField = new JTextField();
		prenomField.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		prenomField.setColumns(10);
		prenomField.setBounds(390, 13, 131, 20);
		panel.add(prenomField);
		
		emailField = new JTextField();
		emailField.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		emailField.setColumns(10);
		emailField.setBounds(671, 13, 114, 20);
		panel.add(emailField);
		
		telField = new JTextField();
		telField.setFont(new Font("Tw Cen MT", Font.PLAIN, 11));
		telField.setColumns(10);
		telField.setBounds(94, 70, 114, 20);
		panel.add(telField);
		
		JLabel lblPrenom = new JLabel("Prenom :");
		lblPrenom.setForeground(Color.BLACK);
		lblPrenom.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		lblPrenom.setBounds(284, 13, 88, 14);
		panel.add(lblPrenom);
		
		JLabel lblNewLabel_1_1 = new JLabel("Email :");
		lblNewLabel_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 21));
		lblNewLabel_1_1.setBounds(584, 14, 77, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Tel :");
		lblNewLabel_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		lblNewLabel_1_1_1.setBounds(21, 71, 53, 14);
		panel.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Date Naissance :");
		lblNewLabel_1_1_1_1.setForeground(Color.BLACK);
		lblNewLabel_1_1_1_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		lblNewLabel_1_1_1_1.setBounds(248, 71, 149, 14);
		panel.add(lblNewLabel_1_1_1_1);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(407, 70, 114, 20);
		panel.add(dateChooser);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(10, 261, 788, 133);
		getContentPane().add(scrollPane);
////////////////////////////////////////////////////////	
		table = new JTable();
		table.setFont(new Font("Tw Cen MT", Font.ITALIC, 15));
		table.setForeground(Color.WHITE);
		table.setBackground(Color.BLACK);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				i=table.getSelectedRow();
				 idB=Long.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString());
				 System.out.println(idB.toString());
				nomField.setText(model.getValueAt(i, 1).toString());
				prenomField.setText(model.getValueAt(i, 2).toString());
				emailField.setText(model.getValueAt(i, 3).toString());
				telField.setText(model.getValueAt(i, 4).toString());	
				
			}
		});
		model=new DefaultTableModel();
		Object[] column = {"Id_User","Nom","Prenom","Email","Tel","Date_Naissance"};
		Object[] row=new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		recharger();
		
		
	}

}
