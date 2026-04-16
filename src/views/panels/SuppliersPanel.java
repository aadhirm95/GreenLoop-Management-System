package views.panels;

import controllers.DashboardController;
import models.Supplier;
import models.SupplierTableModel;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifySupplier;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class SuppliersPanel extends JPanel {
    private List<Supplier> suppliers;
    private SupplierTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public SuppliersPanel(DashboardController dashboardController, List<Supplier> suppliers) {
        this.suppliers = suppliers;
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

        model = new SupplierTableModel(suppliers);
        table = new JTable(model);

        TableRowSorter<SupplierTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Suppliers", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font calibriLight = new Font("Calibri Light", Font.PLAIN, 14);

        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0, true);
        filterLabel.setBounds(50, 90, 50, 20);

        JLabel supplierIdLabel = CS.paintLabels(this, "", "Supplier ID", "#7faab5", "#ffffff", 0, true);
        supplierIdLabel.setBounds(50, 120, 150, 20);

        JTextField supplierIdFilterField = new JTextField();
        supplierIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = supplierIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for supplier id is 0
                }
            }
        });
        supplierIdFilterField.setBounds(50, 145, 150, 20);
        this.add(supplierIdFilterField);

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

        JLabel contactNameLabel = CS.paintLabels(this, "", "Contact Name", "#7faab5", "#ffffff", 0, true);
        contactNameLabel.setBounds(390, 120, 150, 20);

        JTextField contactNameFilterField = new JTextField();
        contactNameFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = contactNameFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for contact name is 2
                }
            }
        });
        contactNameFilterField.setBounds(390, 145, 150, 20);
        this.add(contactNameFilterField);

        JLabel contactEmailLabel = CS.paintLabels(this, "", "Contact Email", "#7faab5", "#ffffff", 0, true);
        contactEmailLabel.setBounds(560, 120, 150, 20);

        JTextField contactEmailFilterField = new JTextField();
        contactEmailFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = contactEmailFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 3)); // Column index for contact email is 3
                }
            }
        });
        contactEmailFilterField.setBounds(560, 145, 150, 20);
        this.add(contactEmailFilterField);

        JLabel contactPhoneLabel = CS.paintLabels(this, "", "Contact Phone", "#7faab5", "#ffffff", 0, true);
        contactPhoneLabel.setBounds(730, 120, 150, 20);

        JTextField contactPhoneFilterField = new JTextField();
        contactPhoneFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = contactPhoneFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 4)); // Column index for contact phone is 4
                }
            }
        });
        contactPhoneFilterField.setBounds(730, 145, 150, 20);
        this.add(contactPhoneFilterField);


        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("View/Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        addItem.addActionListener(e -> {
            ModifySupplier modifySupplier = new ModifySupplier(SuppliersPanel.this, "Add New Supplier", new Supplier());
            modifySupplier.setVisible(true);
        });

        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Supplier supplier = suppliers.get(modelRow);
                ModifySupplier modifySupplier = new ModifySupplier(SuppliersPanel.this, "Edit Supplier", supplier);
                modifySupplier.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                boolean deleted = dashboardController.crudSupplier(suppliers.get(modelRow), 'd');
                if (deleted) {
                    suppliers.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    SuppliersPanel.this.getParent().repaint();
                    JOptionPane.showMessageDialog(SuppliersPanel.this, "Supplier deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(SuppliersPanel.this, "Failed to delete supplier.", "Error", JOptionPane.ERROR_MESSAGE);
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
                if (e.isPopupTrigger()) {
                    showContextMenu(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
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

        // Add button for adding a new supplier
        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener
                (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ModifySupplier modifySupplier = new ModifySupplier(SuppliersPanel.this, "Add New Supplier", new Supplier());
                        modifySupplier.setVisible(true);
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


    public boolean createSupplier(Supplier supplier) {
        // Add the new supplier to the list
        boolean created = dashboardController.crudSupplier(supplier, 'c');

        if (created) {
            List<Supplier> suppliers_ = dashboardController.getAllSuppliers();
            suppliers_.sort((o1, o2) -> o1.getSupplierId() - o2.getSupplierId());
            suppliers.add(suppliers_.get(suppliers_.size() - 1));

            int newRowIndex = suppliers.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    public boolean updateSupplier(Supplier updatedSupplier) {
        boolean updated = this.dashboardController.crudSupplier(updatedSupplier, 'u');

        if (updated) {
            int supplierIndex = -1;
            for (int i = 0; i < suppliers.size(); i++) {
                if (suppliers.get(i).getSupplierId() == updatedSupplier.getSupplierId()) {
                    supplierIndex = i;
                    break;
                }
            }

            if (supplierIndex != -1) {
                suppliers.set(supplierIndex, updatedSupplier);
                model.fireTableRowsUpdated(supplierIndex, supplierIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Supplier not found");
            }
        }
        return updated;
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Supplier> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Supplier",
                suppliers,
                new String[]{"Supplier ID", "Name", "Contact Name", "Contact Email"},
                supplier -> new Object[]{supplier.getSupplierId(), supplier.getName(), supplier.getContactName(), supplier.getContactEmail()},
                "Filter by Name",
                0,
                1,
                supplier -> dashboardController.crudSupplier(supplier, 'd'),
                this::removeSupplierFromTable
        );
        dialog.setVisible(true);
    }

    private void removeSupplierFromTable(Supplier supplier) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId() == supplier.getSupplierId()) {
                suppliers.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }
}
