package views.panels;

import controllers.DashboardController;
import models.Inventory;
import models.InventoryTableModel;
import models.Part;
import models.Supplier;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifyInventory;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InventoryPanel extends JPanel {
    private List<Inventory> inventories;
    private InventoryTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public InventoryPanel(DashboardController dashboardController, List<Inventory> inventories) {
        this.inventories = inventories;
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
        this.setLayout(null); // Set null layout

        // Initialize table model and table
        model = new InventoryTableModel(inventories);
        table = new JTable(model);

        // Set up row sorter
        TableRowSorter<InventoryTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Set up labels
        JLabel titleLabel = CS.paintLabels(this, "", "Inventory Management", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        // Add filter labels and fields
        int labelX = 50;
        int fieldX = 50;
        int y = 90;
        int labelWidth = 150;
        int fieldWidth = 150;
        int height = 20;

        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0, true);
        filterLabel.setBounds(50, 90, 50, 20);


        JLabel inventoryIdLabel = CS.paintLabels(this, "", "Inventory ID", "#7faab5", "#ffffff", 0, true);
        inventoryIdLabel.setBounds(50, 120, 150, 20);

        JTextField inventoryIdFilterField = createFilterField();
        inventoryIdFilterField.setBounds(50, 145, 150, 20);
        inventoryIdFilterField.addKeyListener(createFilterKeyListener(sorter, inventoryIdFilterField, 0));
        this.add(inventoryIdFilterField);

        JLabel partIdLabel = CS.paintLabels(this, "", "Part ID", "#7faab5", "#ffffff", 0, true);
        partIdLabel.setBounds(220, 120, 150, 20);

        JTextField partIdFilterField = createFilterField();
        partIdFilterField.setBounds(220, 145, 150, 20);
        partIdFilterField.addKeyListener(createFilterKeyListener(sorter, partIdFilterField, 1)); // Assuming partId is in the second column
        this.add(partIdFilterField);


        JLabel quantityLabel = CS.paintLabels(this, "", "Quantity", "#7faab5", "#ffffff", 0, true);
        quantityLabel.setBounds(390, 120, 150, 20);

        JTextField quantityFilterField = createFilterField();
        quantityFilterField.setBounds(390, 145, 150, 20);
        quantityFilterField.addKeyListener(createFilterKeyListener(sorter, quantityFilterField, 2)); // Assuming quantity is in the third column
        this.add(quantityFilterField);


        JLabel locationLabel = CS.paintLabels(this, "", "Location", "#7faab5", "#ffffff", 0, true);
        locationLabel.setBounds(560, 120, 150, 20);

        JTextField locationFilterField = createFilterField();
        locationFilterField.setBounds(560, 145, 150, 20);
        locationFilterField.addKeyListener(createFilterKeyListener(sorter, locationFilterField, 3)); // Assuming location is in the fourth column
        this.add(locationFilterField);

        // Create scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(40, 200, 850, 400);
        this.add(tableScrollPane);


        // Add button for adding new inventory item
        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener(e -> {
            ModifyInventory modifyInventory = new ModifyInventory(InventoryPanel.this, "Add Inventory", new Inventory(), null);
            modifyInventory.setVisible(true);
        });
        this.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 620, 100, 30);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> showDeleteDialog());
        this.add(deleteButton);

        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        // Add action listeners for menu items
        addItem.addActionListener(e -> {
            ModifyInventory modifyInventory = new ModifyInventory(InventoryPanel.this, "Add Inventory", new Inventory(), null);
            modifyInventory.setVisible(true);
        });


        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Inventory inventory = inventories.get(modelRow);
                ModifyInventory modifyInventory = new ModifyInventory(InventoryPanel.this, "Edit Inventory", inventory, getPartByID(inventory.getPartId()));
                modifyInventory.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                boolean deleted = dashboardController.crudInventory(inventories.get(modelRow), 'd');
                if (deleted) {
                    inventories.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    this.getParent().repaint();
                    JOptionPane.showMessageDialog(this, "Inventory item deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete inventory item.", "Error", JOptionPane.ERROR_MESSAGE);
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


    }


    // Helper method to create filter fields
    public JTextField createFilterField() {
        JTextField textField = new JTextField();
        return textField;
    }

    // Helper method to create key listener for filter fields
    public KeyAdapter createFilterKeyListener(TableRowSorter<InventoryTableModel> sorter, JTextField filterField, int columnIndex) {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = filterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, columnIndex));
                }
            }
        };
    }

    public boolean createInventory(Inventory newInventory) {



        boolean created = dashboardController.crudInventory(newInventory, 'c');

        if (created) {
            List<Inventory> inventories_ = dashboardController.getAllInventories();
            Collections.sort(inventories_, Comparator.comparingInt(Inventory::getInventoryId));
            inventories.add(inventories_.get(inventories_.size() - 1));

            int newRowIndex = inventories.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    public boolean updateInventory(Inventory updatedInventory) {
        boolean updated = this.dashboardController.crudInventory(updatedInventory, 'u');
        if (updated) {
            int inventoryIndex = -1;
            for (int i = 0; i < inventories.size(); i++) {
                if (inventories.get(i).getInventoryId() == updatedInventory.getInventoryId()) {
                    inventoryIndex = i;
                    break;
                }
            }

            // Update the inventory in the list
            if (inventoryIndex != -1) {
                inventories.set(inventoryIndex, updatedInventory);
                model.fireTableRowsUpdated(inventoryIndex, inventoryIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Inventory not found");
            }
        }
        return updated;
    }



    public Part getPartByID(int id) {
        Part part = new Part();
        part.setPartId(id);
        if(dashboardController.crudPart(part, 'r')){
            part.setSupplier(getSupplierById(part.getSupplierId()));
            return part;
        }else {
            return null;
        }
    }

    public Supplier getSupplierById(int id) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId(id);
        if(dashboardController.crudSupplier(supplier, 'r')){
            return supplier;
        }else {
            return null;
        }
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Inventory> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Inventory",
                inventories,
                new String[]{"Inventory ID", "Part ID", "Quantity", "Location"},
                inventory -> new Object[]{inventory.getInventoryId(), inventory.getPartId(), inventory.getQuantity(), inventory.getLocation()},
                "Filter by Name / Location",
                0,
                3,
                inventory -> dashboardController.crudInventory(inventory, 'd'),
                this::removeInventoryFromTable
        );
        dialog.setVisible(true);
    }

    private void removeInventoryFromTable(Inventory inventory) {
        for (int i = 0; i < inventories.size(); i++) {
            if (inventories.get(i).getInventoryId() == inventory.getInventoryId()) {
                inventories.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }


}
