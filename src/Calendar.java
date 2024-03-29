import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class Calendar extends JFrame {

	private JPanel contentPane;
	
	///////////////////////////////////////////////////
	//GLOBAL VARIABLES
	//////////////////////////////////////////////////
	static JLabel lblMonth, lblYear;
	    static JButton btnPrev, btnNext;
	    static JTable tblCalendar;
	    static JComboBox cmbYear;
	    static Container pane;
	    static DefaultTableModel mtblCalendar; //Table model
	    static JScrollPane stblCalendar; //The scrollpane
	    static JPanel pnlCalendar;
	    static int realYear, realMonth, realDay, currentYear, currentMonth;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Calendar frame = new Calendar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Calendar() {
		setTitle("School Time");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 251, 370);
		contentPane = new JPanel();
		contentPane.setBounds(100, 100, 246, 366);
		contentPane.setBackground(new Color(242, 246, 220));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(84, 31, 46, 14);
		contentPane.add(lblNewLabel);
		
		  //Look and feel
		        try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		        catch (ClassNotFoundException e) {}
		        catch (InstantiationException e) {}
		        catch (IllegalAccessException e) {}
		        catch (UnsupportedLookAndFeelException e) {}
		        
		        //Create control
		                lblMonth = new JLabel ("January");		        
		                lblYear = new JLabel ("Change year:");		        
		                cmbYear = new JComboBox();		        
		                btnPrev = new JButton ("<<");		        
		                btnNext = new JButton (">>");
		                mtblCalendar = new DefaultTableModel(){public boolean isCellEditable(int rowIndex, int mColIndex){return false;}};
		                tblCalendar = new JTable(mtblCalendar);
		                stblCalendar = new JScrollPane(tblCalendar);
		                pnlCalendar = new JPanel(null);
		                //Set border
		                pnlCalendar.setBorder(BorderFactory.createTitledBorder("Calendar"));
		                //Register action listeners
		                btnPrev.addActionListener(new btnPrev_Action());
		                btnNext.addActionListener(new btnNext_Action());
		                cmbYear.addActionListener(new cmbYear_Action());
		                //Add controls to pane
		                pane.add(pnlCalendar);
		                pnlCalendar.add(lblMonth);
		                pnlCalendar.add(lblYear);
		                pnlCalendar.add(cmbYear);
		                pnlCalendar.add(btnPrev);
		                pnlCalendar.add(btnNext);
		                pnlCalendar.add(stblCalendar);
		                //Set bounds
		                pnlCalendar.setBounds(0, 0, 320, 335);
		                lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 100, 25);
		                lblYear.setBounds(10, 305, 80, 20);
		                cmbYear.setBounds(230, 305, 80, 20);
		                btnPrev.setBounds(10, 25, 50, 25);
		                btnNext.setBounds(260, 25, 50, 25);
		                stblCalendar.setBounds(10, 50, 300, 250);

		                //Get real month/year
		                        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
		                        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
		                        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
		                        realYear = cal.get(GregorianCalendar.YEAR); //Get year
		                        currentMonth = realMonth; //Match month and year
		                        currentYear = realYear;

		                        //Add headers
		                        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
		                        for (int i=0; i<7; i++){
		                            mtblCalendar.addColumn(headers[i]);
		                        }
		                        
		                        tblCalendar.getParent().setBackground(tblCalendar.getBackground()); //Set background
		                                tblCalendar.getTableHeader().setResizingAllowed(false);
		                                tblCalendar.getTableHeader().setReorderingAllowed(false);
		                                tblCalendar.setColumnSelectionAllowed(true);
		                                tblCalendar.setRowSelectionAllowed(true);
		                                tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		                                //Set row/column count
		                                tblCalendar.setRowHeight(38);
		                                mtblCalendar.setColumnCount(7);
		                                mtblCalendar.setRowCount(6);
		                                for (int i=realYear-100; i<=realYear+100; i++){
		                                	cmbYear.addItem(String.valueOf(i));
		                                }
		                                //Refresh calendar
		                                refreshCalendar (realMonth, realYear); //Refresh calendar

	}
	
	 public static void refreshCalendar(int month, int year){
		         String[] months =  {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		         int nod, som; //Number Of Days, Start Of Month
		         btnPrev.setEnabled(true);

		         btnNext.setEnabled(true);

		         if (month == 0 && year <= realYear-10){btnPrev.setEnabled(false);} //Too early

		         if (month == 11 && year >= realYear+100){btnNext.setEnabled(false);} //Too late

		         lblMonth.setText(months[month]); //Refresh the month label (at the top)

		         lblMonth.setBounds(160-lblMonth.getPreferredSize().width/2, 25, 180, 25); //Re-align label with calendar

		         cmbYear.setSelectedItem(String.valueOf(year)); //Select the correct year in the combo box
		         for (int i=0; i<6; i++){
		             for (int j=0; j<7; j++){
		                 mtblCalendar.setValueAt(null, i, j);
		             }
		         }
		         GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		         nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		         som = cal.get(GregorianCalendar.DAY_OF_WEEK);
		         for (int i=1; i<=nod; i++){
		             int row = new Integer((i+som-2)/7);
		             int column  =  (i+som-2)%7;
		             mtblCalendar.setValueAt(i, row, column);
		         }
		         tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
		     }
		     static class tblCalendarRenderer extends DefaultTableCellRenderer{
		         public Component getTableCellRendererComponent (JTable table, Object value, boolean selected, boolean focused, int row, int column){
		             super.getTableCellRendererComponent(table, value, selected, focused, row, column);
		             if (column == 0 || column == 6){ //Week-end
		                 setBackground(new Color(255, 220, 220));
		             }
		             else{ //Week
		            	 setBackground(new Color(255, 255, 255));
		             }
		             if (value != null){
		                 if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear){ //Today
		                     setBackground(new Color(220, 220, 255));
		                 }
		             }
		             setBorder(null);
		             setForeground(Color.black);
		             return this; 
		         }
		     }
		     static class btnPrev_Action implements ActionListener{
		         public void actionPerformed (ActionEvent e){
		             if (currentMonth == 0){ //Back one year
		                 currentMonth = 11;
		                 currentYear -= 1;
		             }
		             else{ //Back one month
		                 currentMonth -= 1;
		             }
		             refreshCalendar(currentMonth, currentYear);
		         }
		     }
		     static class btnNext_Action implements ActionListener{
		         public void actionPerformed (ActionEvent e){
		             if (currentMonth == 11){ //Foward one year
		                 currentMonth = 0;
		                 currentYear += 1;
		             }
		             else{ //Foward one month
		                 currentMonth += 1;
		             }
		             refreshCalendar(currentMonth, currentYear);
		         }
		     }
		     static class cmbYear_Action implements ActionListener{
		         public void actionPerformed (ActionEvent e){
		             if (cmbYear.getSelectedItem() != null){
		            	 String b = cmbYear.getSelectedItem().toString();
		                 currentYear = Integer.parseInt(b);
		                 refreshCalendar(currentMonth, currentYear);

		             }

		         }

		     }


}
