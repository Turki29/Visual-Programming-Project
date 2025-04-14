/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.doctorinterface;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class DoctorInterface extends JFrame {
private JButton Can1Btn, ReschdBtn,AccessBtn,AddMedBtn,CnfrmBtn,Can2Btn,LogOutBtn;
private JComboBox Apptmnts,PatPick;
private String Pat [] = { "ID : 1  |  Ali Fahad  | Age :  21  |  M  | 3:30PM 11/10/2025", "ID : 2  |  Sarah Mohammad  |  25  |  F  |  4:25PM  11/12/2025"};
private String arrPatPick [] = { "Ali Fahad ", "Sarah Mohammad "};
private JLabel LDocName,PMeds,PName,LApptmnts;
private JTextField TFPMeds;

    public DoctorInterface(String title) {
    super(title);
    this.setLocation(200,400);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel MainP = (JPanel) this.getContentPane();
    MainP.setLayout(new BoxLayout (MainP,BoxLayout.Y_AXIS));
    
    JPanel P1 = new JPanel(new GridLayout(2,1));
    JPanel P11 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    //JPanel P12 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    
     LDocName = new JLabel("Doctor : Nawaf Almutairi");
     AccessBtn = new JButton("Access Patient Records");
     AccessBtn.addActionListener(new AccessActionListener());
     P11.add(LDocName);
     P11.add(AccessBtn);
     P1.add(P11);
    // P1.add(P12);
     //
     JPanel P2 = new JPanel(new GridLayout(2,1));
     JPanel P21 = new JPanel(new FlowLayout(FlowLayout.LEFT));
     JPanel P22 = new JPanel();
 
     LApptmnts = new JLabel("Appointments: ");
     Apptmnts = new JComboBox(Pat);
     Can1Btn = new JButton("Cancel");
     ReschdBtn = new JButton("Reschedule");
     ReschdBtn.addActionListener(new ReschdActionListener());
     P21.add(LApptmnts,BorderLayout.NORTH);
     P21.add(Apptmnts );
     P22.add(Can1Btn );
     P22.add(ReschdBtn);
     P2.add(P21);
     P2.add(P22);
     //
     JPanel P3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
     PMeds = new JLabel("Prescribe Medication : ");
     AddMedBtn = new JButton("Add Perscribtion");
     AddMedBtn.addActionListener(new AddMedActionListener());
     P3.add(PMeds);
     P3.add(AddMedBtn);       
    //
    JPanel P4 = new JPanel();
    LogOutBtn = new JButton("Log Out");
    P4.add(LogOutBtn,BorderLayout.SOUTH);
    //

     MainP.add(P1);
    
     MainP.add(P2);
     
     MainP.add(P3);
     
     MainP.add(P4);
    
    
    
    this.pack();
    this.show();
    }
    
    
    
//Listeners here 
    public class ReschdActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new RescheduleInterface("Rescheduling");
        }
        
    }
    
    public class AccessActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e2) {
            new PatientRecordsInterface("Patient Records");
        }
        
    }
    
     public class AddMedActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new PrescribtionInterface("Prescribe Medication");
        }
        
    }
    
    
    
// 
    
    public class RescheduleInterface extends JFrame {
        private JComboBox CBDate;
        private String arrDate [] = { "Sat, 5:00PM 12/1/2025 ", "Mon, 5:30PM 12/3/2025"};
        private JButton CnfrmBtn,RetBtn;

        public RescheduleInterface(String title)  {
    super(title);
    this.setSize(400,400);
    this.setLocation(500,400);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel P = (JPanel) this.getContentPane();
    JPanel P1 = new JPanel(new FlowLayout());
    CBDate = new JComboBox(arrDate);
    CnfrmBtn = new JButton("Confirm");
    RetBtn = new JButton("Return");
    RetBtn.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
});
    P.add(CBDate,BorderLayout.NORTH);
    P1.add(CnfrmBtn);
    P1.add(RetBtn);
    P.add(P1);
    
    
    this.pack();
    this.show();
            
        }
         }
    
      public class PatientRecordsInterface extends JFrame {
        private JList TAPats;
       private String[] arrRecord = {
            "1 - Fahad Ali",
            "2 - Sarah Mohammad",  };
        private JButton AddBtn,DelBtn;

        public PatientRecordsInterface(String title)  {
    super(title);
    this.setSize(400,200);
    this.setLocation(500,400);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel P = (JPanel) this.getContentPane();
    JPanel P1 = new JPanel(new FlowLayout());
    TAPats = new JList(arrRecord);
    AddBtn = new JButton("Add");
    DelBtn = new JButton("Delete");
    P.add(TAPats,BorderLayout.NORTH);
    P1.add(AddBtn);
    P1.add(DelBtn);
    P.add(P1);
    
    
    this.pack();
    this.show();
            
        }
        
        
        }
       public class PrescribtionInterface extends JFrame {
        private JLabel LMed ;
        private JComboBox CBDate;
        private String arrDate [] = { "Sat, 5:00PM 12/1/2025 ", "Mon, 5:30PM 12/3/2025"};
        private JButton CnfrmBtn,RetBtn;

        public PrescribtionInterface(String title)  {
    super(title);
    this.setSize(400,400);
    this.setLocation(500,400);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JPanel P = (JPanel) this.getContentPane();
    P.setLayout(new GridLayout(3,1));
    JPanel P1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel P2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel P3 = new JPanel();
     PName = new JLabel("Choose Patient :");
     PatPick = new JComboBox(arrPatPick);
     LMed = new JLabel("Medication : ");
     TFPMeds = new JTextField(40);
     CnfrmBtn = new JButton("Confirm");
     Can2Btn = new JButton("Cancel");
    Can2Btn.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        dispose();
    }
});

     P1.add(PName);
     P1.add(PatPick);
     P2.add(LMed);
     P2.add(TFPMeds);
     P3.add(CnfrmBtn);
     P3.add(Can2Btn);
     P.add(P1);
     P.add(P2);
     P.add(P3);
    
    
    this.pack();
    this.show();
            
        }
         }
   
    
    public static void main(String[] args) {
      DoctorInterface DI = new DoctorInterface("Home Page");
       
    }
}
