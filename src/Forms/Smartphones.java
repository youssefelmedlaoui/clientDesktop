package Forms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ServerDaoRemote;
import entities.SmartPhone;
import entities.User;
import javax.swing.UIManager;

public class Smartphones extends JFrame {

	private JPanel contentPane;
	private DefaultTableModel model;
    private int id = -1;
    private JTextField imeiField;
    private int i;
    private Long idS;
    private String[] l= null;
    private String[] l2= null;
    private String type;
    private String user=null;
    private Long idB;
    private JTable table;
    
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
					Smartphones frame = new Smartphones();
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
			List<SmartPhone> smartphones =stub.findAllSmartPhones();
            for (SmartPhone s : smartphones) {
                model.addRow(new Object[]{
                    s.getIdSP(),
                    s.getImei(),
                    s.getUser().getNom(),     
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
	
	public Smartphones() {
		setTitle("Smartphones");
		
		getContentPane().setBackground(Color.LIGHT_GRAY);
		//updateComboBoxBloc();
				
		setResizable(true);
		setBounds(100, 100, 824, 435);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(0, 0, 808, 208);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Imei :");
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Tw Cen MT", Font.PLAIN, 22));
		lblNewLabel.setBounds(101, 25, 68, 19);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("User :");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		lblNewLabel_1.setBounds(505, 22, 64, 24);
		panel.add(lblNewLabel_1);
		
		imeiField = new JTextField();
		imeiField.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		imeiField.setBackground(Color.WHITE);
		imeiField.setBounds(190, 27, 142, 20);
		panel.add(imeiField);
		imeiField.setColumns(10);
		
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLACK);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					String imei=imeiField.getText();
					List<User> users =stub.findAllUsers();
					for(User u: users) {
						if(u.getNom().equals(user)) 
							idB=u.getIdUser();
							}
					
					
					SmartPhone s=new SmartPhone(imei);
					stub.addSmartPhone(s, idB);
					recharger();
					imeiField.setText("");
					
								
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
				

			
		});
		//update comboBox
		try {
			ServerDaoRemote stub = lookUpSalleDaoRemote();
			List<User> b =stub.findAllUsers();
			l=new String[b.size()];
			
			for(int i=0;i<b.size();i++) {
		      l[i]=b.get(i).getNom();
			}
			
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		btnNewButton.setBounds(160, 112, 121, 23);
		panel.add(btnNewButton);
		
		 JComboBox UserComboBox = new JComboBox(l);
		 UserComboBox.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		 UserComboBox.addItemListener(new ItemListener() {
		 	public void itemStateChanged(ItemEvent e) {
		 		if (e.getStateChange() == ItemEvent.SELECTED) {
					user=e.getItem().toString();
					}
		 		
				

		 	}
		 });
		UserComboBox.setBounds(610, 25, 135, 24);
		panel.add(UserComboBox);
		
		JButton btnNewButton_1 = new JButton("UPDATE");
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		btnNewButton_1.setBackground(Color.BLACK);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					
					
					List<User> users =stub.findAllUsers();
					for(User u: users) {
						if(u.getNom().equals(UserComboBox.getSelectedItem().toString())) 
							idB=u.getIdUser();
							}
					
				
					 SmartPhone s=new SmartPhone(idS,imeiField.getText());
					  stub.update(s, idB);
					
					  recharger();
					  imeiField.setText("");
					
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(357, 112, 126, 23);
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("DELETE");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setFont(new Font("Tw Cen MT", Font.PLAIN, 21));
		btnNewButton_2.setBackground(Color.BLACK);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ServerDaoRemote stub = lookUpSalleDaoRemote();
					
					
					  stub.delete(stub.findSmartPhoneById(idS));
					
					  recharger();
					  imeiField.setText("");
									
				} catch (NamingException s) {
					s.printStackTrace();
				}
				
			}
		});
		btnNewButton_2.setBounds(558, 112, 107, 23);
		panel.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane();
		
		scrollPane.setBounds(20, 209, 788, 176);
		getContentPane().add(scrollPane);
////////////////////////////////////////////////////////	
		table = new JTable();
		table.setFont(new Font("Tw Cen MT", Font.ITALIC, 12));
		table.setForeground(Color.WHITE);
		table.setBackground(Color.BLACK);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 i=table.getSelectedRow();
				 idS=Long.valueOf(model.getValueAt(table.getSelectedRow(), 0).toString());
				 System.out.println(i);
				imeiField.setText(model.getValueAt(i, 1).toString());
				UserComboBox.setSelectedItem((model.getValueAt(i, 3).toString()));
				
			}
		});
		model=new DefaultTableModel();
		Object[] column = {"Id_SmartPhone","Imei","User"};
		Object[] row=new Object[0];
		model.setColumnIdentifiers(column);
		table.setModel(model);
		scrollPane.setViewportView(table);
		recharger();
		
		
	}

}
