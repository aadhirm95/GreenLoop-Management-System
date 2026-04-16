package views.panels;

import controllers.DashboardController;
import models.Customer;
import models.Role;
import models.RoleTableModel;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifyRole;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RolesPanel extends JPanel {
    private List<Role> roles;
    private RoleTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public RolesPanel(DashboardController dashboardController, List<Role> roles) {
        this.roles = roles;
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

        model = new RoleTableModel(roles);
        table = new JTable(model);


        TableRowSorter<RoleTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Roles", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font calibriLight = new Font("Calibri Light", Font.PLAIN, 14);

        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0,true);
        filterLabel.setBounds(50, 90, 50, 20);

        JLabel roleIdLabel = CS.paintLabels(this, "", "Role ID", "#7faab5", "#ffffff", 0,true);
        roleIdLabel.setBounds(50, 120, 150, 20);

        JTextField roleIdFilterField = new JTextField();
        roleIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = roleIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for role id is 0
                }
            }
        });
        roleIdFilterField.setBounds(50, 145, 150, 20);
        this.add(roleIdFilterField);

        JLabel roleNameLabel = CS.paintLabels(this, "", "Role Name", "#7faab5", "#ffffff", 0,true);
        roleNameLabel.setBounds(220, 120, 150, 20);

        JTextField roleNameFilterField = new JTextField();
        roleNameFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = roleNameFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // Column index for role name is 1
                }
            }
        });
        roleNameFilterField.setBounds(220, 145, 150, 20);
        this.add(roleNameFilterField);

        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("View/Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        // Add action listeners for menu items
        addItem.addActionListener(e -> {
            Role role = new Role();
            ModifyRole modifyRole = new ModifyRole(RolesPanel.this, "Add New Role", role);
            modifyRole.setVisible(true);
        });

        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Role role = roles.get(modelRow);

                ModifyRole modifyRole = new ModifyRole(RolesPanel.this, "Edit Role", role);
                modifyRole.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                boolean deleted = dashboardController.crudRole(roles.get(modelRow), 'd');
                if (deleted) {
                    roles.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    this.getParent().repaint();
                    JOptionPane.showMessageDialog(this, "Role deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete role.", "Error", JOptionPane.ERROR_MESSAGE);
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


        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener(e -> {
            Role role = new Role();
            ModifyRole modifyRole = new ModifyRole(RolesPanel.this, "Add New Role", role);
            modifyRole.setVisible(true);
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


    public boolean updateRole(Role updatedRole) {
        boolean updated = this.dashboardController.crudRole(updatedRole, 'u');

        if (updated) {
            int roleIndex = -1;
            for (int i = 0; i < roles.size(); i++) {
                if (roles.get(i).getRoleId() == updatedRole.getRoleId()) {
                    roleIndex = i;
                    break;
                }
            }

            // Update the role in the list
            if (roleIndex != -1) {
                roles.set(roleIndex, updatedRole);

                // Notify the table model that the data has changed
                model.fireTableRowsUpdated(roleIndex, roleIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Role not found");
            }
        }
        return updated;
    }

    public boolean createRole(Role newRole) {
        // Add the new role to the list
        boolean created = dashboardController.crudRole(newRole, 'c');

        if (created) {

            List<Role> roles_ = dashboardController.getAllRoles();
            roles_.sort((o1,o2) -> o1.getRoleId());
            roles.add(roles_.get(roles.size()-1));
            int newRowIndex = roles.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();

        }
        return created;
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Role> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Role",
                roles,
                new String[]{"Role ID", "Role Name"},
                role -> new Object[]{role.getRoleId(), role.getRoleName()},
                "Filter by Name",
                0,
                1,
                role -> dashboardController.crudRole(role, 'd'),
                this::removeRoleFromTable
        );
        dialog.setVisible(true);
    }

    private void removeRoleFromTable(Role role) {
        for (int i = 0; i < roles.size(); i++) {
            if (roles.get(i).getRoleId() == role.getRoleId()) {
                roles.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }
}
