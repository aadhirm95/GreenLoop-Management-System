package views.panels;

import controllers.DashboardController;
import models.Customer;
import models.CustomerTableModel;
import models.Employee;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifyCustomer;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class CustomersPanel extends JPanel {
    private List<Customer> customers;
    private CustomerTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public CustomersPanel(DashboardController dashboardController, List<Customer> customers) {
        this.customers = customers;
        this.dashboardController = dashboardController;
        this.createUIComponents();
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

        model = new CustomerTableModel(customers);
        table = new JTable(model);

        TableRowSorter<CustomerTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Customers", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font calibriLight = new Font("Calibri Light", Font.PLAIN, 14);

        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0, true);
        filterLabel.setBounds(50, 90, 50, 20);

        JLabel customerIdLabel = CS.paintLabels(this, "", "Customer ID", "#7faab5", "#ffffff", 0, true);
        customerIdLabel.setBounds(50, 120, 150, 20);

        JTextField customerIdFilterField = new JTextField();
        customerIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = customerIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for customer id is 0
                }
            }
        });
        customerIdFilterField.setBounds(50, 145, 150, 20);
        this.add(customerIdFilterField);

        // Filter for Name
        JLabel nameLabel = CS.paintLabels(this, "", "Name", "#7faab5", "#ffffff", 0, true);
        nameLabel.setBounds(220, 120, 150, 20);
        JTextField nameFilterField = new JTextField();
        nameFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = nameFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // Column index for name is 1
                }
            }
        });
        nameFilterField.setBounds(220, 145, 150, 20);
        this.add(nameFilterField);

        // Filter for Address
        JLabel addressLabel = CS.paintLabels(this, "", "Address", "#7faab5", "#ffffff", 0, true);
        addressLabel.setBounds(390, 120, 150, 20);
        JTextField addressFilterField = new JTextField();
        addressFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = addressFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for address is 2
                }
            }
        });
        addressFilterField.setBounds(390, 145, 150, 20);
        this.add(addressFilterField);

        // Filter for Mobile
        JLabel mobileLabel = CS.paintLabels(this, "", "Mobile", "#7faab5", "#ffffff", 0, true);
        mobileLabel.setBounds(560, 120, 150, 20);
        JTextField mobileFilterField = new JTextField();
        mobileFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = mobileFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 3)); // Column index for mobile is 3
                }
            }
        });
        mobileFilterField.setBounds(560, 145, 150, 20);
        this.add(mobileFilterField);

        // Filter for Email
        JLabel emailLabel = CS.paintLabels(this, "", "Email", "#7faab5", "#ffffff", 0, true);
        emailLabel.setBounds(730, 120, 150, 20);
        JTextField emailFilterField = new JTextField();
        emailFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = emailFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 4)); // Column index for email is 4
                }
            }
        });
        emailFilterField.setBounds(730, 145, 150, 20);
        this.add(emailFilterField);

        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("View/Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");


        // Add action listeners for menu items
        addItem.addActionListener(e -> {
            ModifyCustomer modifyCustomer = new ModifyCustomer(CustomersPanel.this, "Add New Customer", new Customer());
            modifyCustomer.setVisible(true);
        });


        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Customer customer = customers.get(modelRow);

                ModifyCustomer modifyCustomer = new ModifyCustomer(CustomersPanel.this, "Edit Customer", customer);
                modifyCustomer.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                boolean deleted = dashboardController.crudCustomer(customers.get(modelRow), 'd');
                if (deleted) {
                    customers.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    CustomersPanel.this.getParent().repaint();
                    JOptionPane.showMessageDialog(CustomersPanel.this, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(CustomersPanel.this, "Failed to delete customer.", "Error", JOptionPane.ERROR_MESSAGE);
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
        this.add(tableScrollPane);

        // Add button for adding a new customer
        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyCustomer modifyCustomer = new ModifyCustomer(CustomersPanel.this, "Add New Customer", new Customer());
                modifyCustomer.setVisible(true);
            }
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


    public boolean createCustomer(Customer newCustomer) {
        boolean created = dashboardController.crudCustomer(newCustomer, 'c');
        if(created){

            List<Customer> customers_ = dashboardController.getAllCustomers();
            customers_.sort((o1,o2) -> o1.getCustomerId());
            customers.add(customers_.get(customers_.size()-1));

            int newRowIndex = customers.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    public boolean updateCustomer(Customer updatedCustomer) {
        boolean updated = dashboardController.crudCustomer(updatedCustomer, 'u');
        if(updated){
            int customerIndex = -1;
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getCustomerId() == updatedCustomer.getCustomerId()) {
                    customerIndex = i;
                    break;
                }
            }
            // Update the customer in the list
            if (customerIndex != -1) {
                customers.set(customerIndex, updatedCustomer);
                model.fireTableRowsUpdated(customerIndex, customerIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Customer not found");
            }
        }
        return updated;
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Customer> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Customer",
                customers,
                new String[]{"Customer ID", "Name", "Mobile", "Email"},
                customer -> new Object[]{customer.getCustomerId(), customer.getName(), customer.getMobile(), customer.getEmail()},
                "Filter by Name",
                0,
                1,
                customer -> dashboardController.crudCustomer(customer, 'd'),
                this::removeCustomerFromTable
        );
        dialog.setVisible(true);
    }

    private void removeCustomerFromTable(Customer customer) {
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getCustomerId() == customer.getCustomerId()) {
                customers.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }
}
