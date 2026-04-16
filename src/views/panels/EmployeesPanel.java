package views.panels;

import controllers.DashboardController;
import models.Employee;
import models.EmployeeTableModel;
import models.Role;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifyEmployee;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeesPanel extends JPanel {
    private List<Employee> employees;
    private List<Role> roles;

    private EmployeeTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public EmployeesPanel(DashboardController dashboardController, List<Employee> employees, List<Role> roles) {
        this.employees = employees;
        this.roles = roles;
        this.createUIComponents();
        this.dashboardController = dashboardController;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw a rectangle
        g.setColor(Color.decode("#BBBBBB"));
        g.drawRect(40, 80, 850, 95); // (x, y, width, height)
    }

    private void createUIComponents() {

        this.setLayout(null);
        this.setOpaque(true);


        model = new EmployeeTableModel(employees, roles);
        table = new JTable(model);

        TableRowSorter<EmployeeTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);



        JLabel titleLabel = CS.paintLabels(this, "", "Manage Employees", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font calibriLight = new Font("Calibri Light", Font.PLAIN, 14);

        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0,true);
        filterLabel.setBounds(50, 90, 50, 20);



        JLabel employeeIdLabel = CS.paintLabels(this, "", "Employee ID", "#7faab5", "#ffffff", 0,true);
        employeeIdLabel.setBounds(50, 120, 150, 20);

        JTextField empIdFilterField = new JTextField();
        empIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = empIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for employee id is 0
                }
            }
        });
        empIdFilterField.setBounds(50, 145, 150, 20);
        this.add(empIdFilterField);



        JLabel fnLabel = CS.paintLabels(this, "", "First Name", "#7faab5", "#ffffff", 0,true);
        fnLabel.setBounds(220, 120, 150, 20);

        JTextField fnFilterField = new JTextField();
        fnFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = fnFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for firstname is 2
                }
            }
        });
        fnFilterField.setBounds(220, 145, 150, 20);
        this.add(fnFilterField);



        JLabel emailLabel = CS.paintLabels(this, "", "Email", "#7faab5", "#ffffff", 0,true);
        emailLabel.setBounds(390, 120, 150, 20);

        JTextField emailFilterField = new JTextField();
        emailFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = emailFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 7)); // Column index for email is 7
                }
            }
        });
        emailFilterField.setBounds(390, 145, 150, 20);
        this.add(emailFilterField);




        JLabel mobileLabel = CS.paintLabels(this, "", "Mobile", "#7faab5", "#ffffff", 0,true);
        mobileLabel.setBounds(560, 120, 150, 20);

        JTextField mobileFilterField = new JTextField();
        mobileFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = mobileFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 6)); // Column index for mobile is 6
                }
            }
        });
        mobileFilterField.setBounds(560, 145, 150, 20);
        this.add(mobileFilterField);



        JLabel unLabel = CS.paintLabels(this, "", "Username", "#7faab5", "#ffffff", 0,true);
        unLabel.setBounds(730, 120, 150, 20);

        JTextField usernameFilterField = new JTextField();
        usernameFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = usernameFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 4)); // Column index for Username is 4
                }
            }
        });
        usernameFilterField.setBounds(730, 145, 150, 20);
        this.add(usernameFilterField);


        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("View/Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");



        // Add action listeners for menu items
        addItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            Employee employee = new Employee();
            ModifyEmployee modifyEmployee = new ModifyEmployee(EmployeesPanel.this,"Add New Employee", employee,roles);
            modifyEmployee.setVisible(true);
        });

        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Employee employee = employees.get(modelRow);

                ModifyEmployee modifyEmployee = new ModifyEmployee(EmployeesPanel.this,"Edit Employee", employee,roles);
                modifyEmployee.setVisible(true);

            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                boolean deleted = dashboardController.crudEmployee(employees.get(modelRow), 'd');
                if(deleted){
                    employees.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    this.getParent().repaint();
                    JOptionPane.showMessageDialog(this, "Employee deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(this, "Failed to delete employee.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        contextMenu.add(addItem);
        contextMenu.add(editItem);
        contextMenu.add(deleteItem);


        // Add mouse listener to table to show context menu
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // For Windows/Linux
                    showContextMenu(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) { // For macOS
                    showContextMenu(e);
                }
            }

            private void showContextMenu(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    table.setRowSelectionInterval(row, row);
                    contextMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });




        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(40, 200, 850, 400);
        //g.drawRect(40, 80, 850, 95); // (x, y, width, height)
        this.add(tableScrollPane);

        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener(e -> {
            ModifyEmployee modifyEmployee = new ModifyEmployee(EmployeesPanel.this, "Add New Employee", new Employee(), roles);
            modifyEmployee.setVisible(true);
        });
        this.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 620, 100, 30);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> showDeleteDialog());
        this.add(deleteButton);


    }


    public boolean updateEmployee(Employee updatedEmployee){

        boolean updated = this.dashboardController.crudEmployee(updatedEmployee, 'u');
        if(updated){
            int employeeIndex = -1;
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getEmployeeId() == updatedEmployee.getEmployeeId()) {
                    employeeIndex = i;
                    break;
                }
            }

            // Update the employee in the list
            if (employeeIndex != -1) {
                employees.set(employeeIndex, updatedEmployee);
                model.fireTableRowsUpdated(employeeIndex, employeeIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found");
            }
        }
        return updated;
    }

    public boolean createEmployee(Employee newEmployee) {
        // Add the new employee to the list
        boolean created = dashboardController.crudEmployee(newEmployee, 'c');

        if(created){
            List<Employee> employees_ = dashboardController.getAllEmployees();
            Collections.sort(employees_, Comparator.comparingInt(Employee::getEmployeeId));
            employees.add(employees_.get(employees_.size()-1));

            int newRowIndex = employees.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Employee> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Employee",
                employees,
                new String[]{"ID", "Title", "First Name", "Last Name", "Username"},
                employee -> new Object[]{employee.getEmployeeId(), employee.getTitle(), employee.getFirstName(), employee.getLastName(), employee.getUsername()},
                "Filter by Name",
                0,
                2,
                employee -> dashboardController.crudEmployee(employee, 'd'),
                this::removeEmployeeFromTable
        );
        dialog.setVisible(true);
    }

    private void removeEmployeeFromTable(Employee employee) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getEmployeeId() == employee.getEmployeeId()) {
                employees.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }


}
