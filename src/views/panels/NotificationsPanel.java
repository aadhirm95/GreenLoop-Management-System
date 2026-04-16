package views.panels;

import controllers.DashboardController;
import models.Notification;
import models.NotificationTableModel;
import models.Part;
import models.Property;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifyNotification;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NotificationsPanel extends JPanel {
    private List<Notification> notifications;
    private List<Property> properties;
    private NotificationTableModel model;
    private JTable table;
    private DashboardController dashboardController;
    private Graphics g;

    private JTextField usernameField;
    private JTextField passwordField;

    public NotificationsPanel(DashboardController dashboardController, List<Notification> notifications, List<Property> properties) {
        this.dashboardController = dashboardController;
        this.notifications = notifications;
        this.properties = properties;
        createUIComponents();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw a rectangle
        CS.drawShaedBorder(g, new Rectangle(40, 80, 850, 95));
        CS.drawVerticalDoubleLine(g, 380,380,81,174);
        CS.drawShaedBorder(g, new Rectangle(40, 500, 850, 170));
        CS.drawShaedBorder(g, new Rectangle(50, 510, 330, 150));
        CS.drawShaedBorder(g, new Rectangle(390, 510, 490, 150));
        //50 515, 380 595
    }


    private void createUIComponents() {
        this.setLayout(null);
        model = new NotificationTableModel(notifications);
        table = new JTable(model);

        TableRowSorter<NotificationTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Notifications", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);


        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0,true);
        filterLabel.setBounds(50, 90, 50, 20);




        //1{"ID", "Part ID", "Part Name", "Remaining Quantity", "Minimum Quantity", "Notify"};
        JLabel partIdLabel = CS.paintLabels(this, "", "Part ID", "#7faab5", "#ffffff", 0,true);
        partIdLabel.setBounds(50, 120, 150, 20);

        JTextField partIdFilterField = new JTextField();
        partIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = partIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // Column index for part id is 1
                }
            }
        });
        partIdFilterField.setBounds(50, 145, 150, 20);
        this.add(partIdFilterField);


        //2{"ID", "Part ID", "Part Name", "Remaining Quantity", "Minimum Quantity", "Notify"};
        JLabel partNameLabel = CS.paintLabels(this, "", "Part Name", "#7faab5", "#ffffff", 0,true);
        partNameLabel.setBounds(220, 120, 150, 20);


        JTextField partNameFilterField = new JTextField();
        partNameFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = partNameFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for Part Name is 2
                }
            }
        });
        partNameFilterField.setBounds(220, 145, 150, 20);
        this.add(partNameFilterField);



        JCheckBox autoSupplier = new JCheckBox("Automatically request email orders from suppliers on low stock.");
        autoSupplier.setBounds(420, 95, 400, 20);
        this.add(autoSupplier);

        JCheckBox autoCustomer = new JCheckBox("Automatically send email to customer after job is completed.");
        autoCustomer.setBounds(420, 120, 400, 20);
        this.add(autoCustomer);

        JCheckBox autoEmployee = new JCheckBox("Automatically send email to employee on new job allocation.");
        autoEmployee.setBounds(420, 145, 400, 20);
        this.add(autoEmployee);











        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(40, 200, 850, 220);
        this.add(tableScrollPane);



        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("View/Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        addItem.addActionListener(e -> showModifyNotificationDialog(new Notification(), "Add New Notification"));
        editItem.addActionListener(e -> showModifyNotificationDialog(getSelectedNotification(), "Edit Notification"));
        deleteItem.addActionListener(e -> deleteSelectedNotification());

        contextMenu.add(addItem);
        contextMenu.add(editItem);
        contextMenu.add(deleteItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) showContextMenu(e);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) showContextMenu(e);
            }
            private void showContextMenu(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    table.setRowSelectionInterval(row, row);
                    contextMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 440, 100, 30);
        addButton.addActionListener(e -> showModifyNotificationDialog(new Notification(), "Add New Notification"));
        this.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 440, 100, 30);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> showDeleteDialog());
        this.add(deleteButton);

        JLabel emailSettingsTitleLabel = CS.paintLabels(this, "", "Email Settings", "#7faab5", "#ffffff", 0, true);
        emailSettingsTitleLabel.setBounds(60, 520, 150, 25);

        JLabel usernameLabel = CS.paintLabels(this, "", "Username", null, "#000000", 0, true);
        usernameLabel.setBounds(60, 555, 150, 25);

        usernameField = new JTextField();
        usernameField.setBounds(220, 555, 150, 25);
        this.add(usernameField);

        JLabel passwordLabel = CS.paintLabels(this, "", "Password", null, "#000000", 0, true);
        passwordLabel.setBounds(60, 590, 150, 25);


        passwordField = new JTextField();
        passwordField.setBounds(220, 590, 150, 25);
        this.add(passwordField);

        JButton saveCredentials = new JButton("Save");
        saveCredentials.setBounds(220, 625, 70, 25);
        this.add(saveCredentials);

        JButton resetCredentials = new JButton("Reset");
        resetCredentials.setBounds(300, 625, 70, 25);
        this.add(resetCredentials);




    }

    private void showModifyNotificationDialog(Notification notification, String title) {
        ModifyNotification modifyNotification = new ModifyNotification(this, title, notification);
        modifyNotification.setVisible(true);
    }

    private Notification getSelectedNotification() {
        int viewRow = table.getSelectedRow();
        if (viewRow >= 0) {
            int modelRow = table.convertRowIndexToModel(viewRow);
            return notifications.get(modelRow);
        }
        return null;
    }

    private void deleteSelectedNotification() {
        int viewRow = table.getSelectedRow();
        if (viewRow >= 0) {
            int modelRow = table.convertRowIndexToModel(viewRow);
            Notification notification = notifications.get(modelRow);
            boolean deleted = dashboardController.crudNotification(notification, 'd');
            if (deleted) {
                notifications.remove(modelRow);
                model.fireTableRowsDeleted(modelRow, modelRow);
                JOptionPane.showMessageDialog(this, "Notification deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete notification.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshTable() {
        model.fireTableDataChanged();
    }


    public boolean saveNotification(Notification notification) {
        boolean created = this.dashboardController.crudNotification(notification, 'c');
        if (created) {
            List<Notification> notifications_ = dashboardController.getAllNotifications();
            Collections.sort(notifications_, Comparator.comparingInt(Notification::getNotificationId));
            notifications.add(notifications_.get(notifications_.size()-1));

            int newRowIndex = notifications.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    public boolean updateNotification(Notification updatedNotification) {
        boolean updated = dashboardController.crudNotification(updatedNotification, 'u');
        if (updated) {
            int notificationIndex = -1;
            for (int i = 0; i < notifications.size(); i++) {
                if (notifications.get(i).getPartId() == updatedNotification.getPartId()) {
                    notificationIndex = i;
                    break;
                }
            }

            // Update the notification in the list
            if (notificationIndex != -1) {
                notifications.set(notificationIndex, updatedNotification);
                model.fireTableRowsUpdated(notificationIndex, notificationIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Notification not found");
            }
        }
        return updated;
    }

    public Part getPartByID(int id) {
        Part part = new Part();
        part.setPartId(id);
        if(dashboardController.crudPart(part, 'r')){
            return part;
        }else {
            return null;
        }
    }

    public void sendOrderRequestEmail(int partId) {
        dashboardController.sendOrderRequestEmail(partId);
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Notification> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Notification",
                notifications,
                new String[]{"Notification ID", "Part ID", "Part Name", "Minimum Quantity"},
                notification -> new Object[]{notification.getNotificationId(), notification.getPartId(), notification.getPartName(), notification.getMinQuantity()},
                "Filter by Name",
                0,
                2,
                notification -> dashboardController.crudNotification(notification, 'd'),
                this::removeNotificationFromTable
        );
        dialog.setVisible(true);
    }

    private void removeNotificationFromTable(Notification notification) {
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getNotificationId() == notification.getNotificationId()) {
                notifications.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }
}
