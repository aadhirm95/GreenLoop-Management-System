package views.panels;

import controllers.DashboardController;
import models.Employee;
import models.Part;
import models.PartTableModel;
import models.Supplier;
import services.CS;
import views.ModifyPart;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PartsPanel extends JPanel {
    private List<Part> parts;
    private PartTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public PartsPanel(DashboardController dashboardController, List<Part> parts) {
        this.parts = parts;
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

        model = new PartTableModel(parts);
        table = new JTable(model);

        TableRowSorter<PartTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Parts", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);


        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0, true);
        filterLabel.setBounds(50, 90, 50, 20);


        // Filter for Part ID
        JLabel partIdLabel = CS.paintLabels(this, "", "Part ID", "#7faab5", "#ffffff", 0, true);
        partIdLabel.setBounds(50, 120, 150, 20);
        JTextField partIdFilterField = new JTextField();
        partIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = partIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for part ID is 0
                }
            }
        });
        partIdFilterField.setBounds(50, 145, 150, 20);
        this.add(partIdFilterField);

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

        // Filter for Description
        JLabel descriptionLabel = CS.paintLabels(this, "", "Description", "#7faab5", "#ffffff", 0, true);
        descriptionLabel.setBounds(390, 120, 150, 20);
        JTextField descriptionFilterField = new JTextField();
        descriptionFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = descriptionFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for description is 2
                }
            }
        });
        descriptionFilterField.setBounds(390, 145, 150, 20);
        this.add(descriptionFilterField);

        // Filter for Price
        JLabel priceLabel = CS.paintLabels(this, "", "Price", "#7faab5", "#ffffff", 0, true);
        priceLabel.setBounds(560, 120, 150, 20);
        JTextField priceFilterField = new JTextField();
        priceFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = priceFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 3)); // Column index for price is 3
                }
            }
        });
        priceFilterField.setBounds(560, 145, 150, 20);
        this.add(priceFilterField);

        // Filter for Supplier ID
        JLabel supplierIdLabel = CS.paintLabels(this, "", "Supplier ID", "#7faab5", "#ffffff", 0, true);
        supplierIdLabel.setBounds(730, 120, 150, 20);
        JTextField supplierIdFilterField = new JTextField();
        supplierIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = supplierIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 4)); // Column index for supplier ID is 4
                }
            }
        });
        supplierIdFilterField.setBounds(730, 145, 150, 20);
        this.add(supplierIdFilterField);

        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        //Add Action
        addItem.addActionListener(e -> {
            ModifyPart modifyPart = new ModifyPart(PartsPanel.this, "Add New Part", new Part(), null);
            modifyPart.setVisible(true);
        });

        // Edit action
        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Part part = parts.get(modelRow);
                ModifyPart modifyPart = new ModifyPart(PartsPanel.this, "Edit Part", part, getSupplierById(part.getSupplierId()));
                modifyPart.setVisible(true);
            }
        });

        // Delete action
        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow);
                Part part = parts.get(modelRow);
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this part?", "Delete Part", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    boolean deleted = deletePart(part);
                    if (deleted) {
                        JOptionPane.showMessageDialog(null, "Part deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        updateTable();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to delete part.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        contextMenu.add(addItem);
        contextMenu.add(editItem);
        contextMenu.add(deleteItem);

        // Mouse listener for showing context menu on right-click
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        contextMenu.show(table, e.getX(), e.getY());
                    }
                }
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(40, 200, 850, 400);
        this.add(tableScrollPane);


        // Add button
        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the ModifyPart frame
                ModifyPart modifyPart = new ModifyPart(PartsPanel.this, "Add New Part", new Part(), null);
                modifyPart.setVisible(true);
            }
        });
        this.add(addButton);

    }


    public boolean createPart(Part part) {
        boolean created = dashboardController.crudPart(part, 'c');
        if (created) {
            List<Part> parts_ = dashboardController.getAllParts();
            Collections.sort(parts_, Comparator.comparingInt(Part::getPartId));
            parts.add(parts_.get(parts_.size()-1));

            int newRowIndex = parts.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    public boolean updatePart(Part updatedPart) {
        boolean updated = dashboardController.crudPart(updatedPart, 'u');
        if (updated) {
            int partIndex = -1;
            for (int i = 0; i < parts.size(); i++) {
                if (parts.get(i).getPartId() == updatedPart.getPartId()) {
                    partIndex = i;
                    break;
                }
            }

            // Update the part in the list
            if (partIndex != -1) {
                parts.set(partIndex, updatedPart);
                model.fireTableRowsUpdated(partIndex, partIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Part not found");
            }
        }
        return updated;
    }

    private boolean deletePart(Part part) {
        boolean deleted = dashboardController.crudPart(part, 'd');
        if (deleted) {
            parts.remove(part);
            model.fireTableDataChanged();
        }
        return deleted;
    }

    private void updateTable() {
        model.fireTableDataChanged();
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
}
