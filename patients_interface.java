import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;  // Add this import for TableCellRenderer
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class patient_page extends JFrame {
    // [Previous code remains the same]
    
    // Custom renderer for the button column
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, 
                                                      boolean isSelected, boolean hasFocus, 
                                                      int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    // Custom editor for the button column
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        
        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }
        
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, 
                                                    boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }
        
        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (label.equals("Book")) {
                    // Get the doctor information
                    String doctorId = doctorTable.getValueAt(doctorTable.getSelectedRow(), 0).toString();
                    String doctorName = doctorTable.getValueAt(doctorTable.getSelectedRow(), 1).toString();
                    bookAppointment(doctorId, doctorName);
                } else if (label.equals("Cancel")) {
                    // Get the appointment information
                    String appointmentId = appointmentTable.getValueAt(appointmentTable.getSelectedRow(), 0).toString();
                    cancelAppointment(appointmentId);
                }
            }
            isPushed = false;
            return label;
        }
        
        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }
    
    // [Remaining code stays the same]
}
