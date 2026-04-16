package views.panels;

import controllers.DashboardController;
import models.Order;
import models.OrderTableModel;
import services.CS;
import views.DeleteSelectionDialog;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class OrderPanel extends JPanel {
    private List<Order> orders;
    private final OrderTableModel model;
    private final JTable table;

    public OrderPanel(DashboardController dashboardController, List<Order> orders) {
        this.setOrders(orders);
        this.model = new OrderTableModel(orders);
        this.table = new JTable(model);

        TableRowSorter<OrderTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Orders", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font calibriLight = new Font("Calibri Light", Font.PLAIN, 14);




        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0,true);
        filterLabel.setBounds(50, 90, 50, 20);




        //1{"Order ID", "Customer ID", "Order Date", "Status", "Total Price", "Repair", "Repaint"}
        JLabel employeeIdLabel = CS.paintLabels(this, "", "Order ID", "#7faab5", "#ffffff", 0,true);
        employeeIdLabel.setBounds(50, 120, 150, 20);

        JTextField empIdFilterField = new JTextField();
        empIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = empIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for order id is 0
                }
            }
        });
        empIdFilterField.setBounds(50, 145, 150, 20);
        this.add(empIdFilterField);


        //2{"Order ID", "Customer ID", "Order Date", "Status", "Total Price", "Repair", "Repaint"}
        JLabel customerIdLabel = CS.paintLabels(this, "", "Customer ID", "#7faab5", "#ffffff", 0,true);
        customerIdLabel.setBounds(220, 120, 150, 20);

        JTextField customerIdFilterField = new JTextField();
        customerIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = customerIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // Column index for Customer ID is 1
                }
            }
        });
        customerIdFilterField.setBounds(220, 145, 150, 20);
        this.add(customerIdFilterField);


        //3{"Order ID", "Customer ID", "Order Date", "Status", "Total Price", "Repair", "Repaint"}
        JLabel orderDateLabel = CS.paintLabels(this, "", "Order Date", "#7faab5", "#ffffff", 0,true);
        orderDateLabel.setBounds(390, 120, 150, 20);

        JTextField orderDateFilterField = new JTextField();
        orderDateFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = orderDateFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for Order Date is 2
                }
            }
        });
        orderDateFilterField.setBounds(390, 145, 150, 20);
        this.add(orderDateFilterField);



        //4{"Order ID", "Customer ID", "Order Date", "Status", "Total Price", "Repair", "Repaint"}
        JLabel statusLabel = CS.paintLabels(this, "", "Status", "#7faab5", "#ffffff", 0,true);
        statusLabel.setBounds(560, 120, 150, 20);

        JTextField statusFilterField = new JTextField();
        statusFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = statusFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 3)); // Column index for Status is 3
                }
            }
        });
        statusFilterField.setBounds(560, 145, 150, 20);
        this.add(statusFilterField);



        //5{"Order ID", "Customer ID", "Order Date", "Status", "Total Price", "Repair", "Repaint"}
        JLabel totalPriceLabel = CS.paintLabels(this, "", "Total Price", "#7faab5", "#ffffff", 0,true);
        totalPriceLabel.setBounds(730, 120, 150, 20);

        JTextField totalPriceFilterField = new JTextField();
        totalPriceFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = totalPriceFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 4)); // Column index for Total Price is 4
                }
            }
        });
        totalPriceFilterField.setBounds(730, 145, 150, 20);
        this.add(totalPriceFilterField);





        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(40, 200, 850, 400);
        // Set layout to null
        this.setLayout(null);

        // Add tableScrollPane to the panel with specific x and y coordinates
        this.add(tableScrollPane);


        // Add Add button
        JButton addButton = new JButton("Add");
        addButton.setBounds(40, 620, 100, 30);
        addButton.addActionListener(e -> {
            //OrderView orderView = new OrderView(OrderPanel.this, "Add New Order", new Order());
            dashboardController.showOrderView(new Order(), "Add Order", OrderPanel.this);
        });
        this.add(addButton);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(150, 620, 100, 30);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> showDeleteDialog(dashboardController));
        this.add(deleteButton);



        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem addItem = new JMenuItem("Add");
        JMenuItem editItem = new JMenuItem("Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        // Add action listeners for menu items
        addItem.addActionListener(e -> {
            dashboardController.showOrderView(new Order(), "Add Order", OrderPanel.this);
        });

        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Order order = orders.get(modelRow);
                dashboardController.showOrderView(order, "Update Order", OrderPanel.this);
            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Order order = orders.get(modelRow);
                boolean deleted = dashboardController.crudOrder(order,'d');
                if(deleted){
                    orders.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    this.getParent().repaint();
                    JOptionPane.showMessageDialog(this, "Order deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }else {
                    JOptionPane.showMessageDialog(this, "Failed to delete order.", "Error", JOptionPane.ERROR_MESSAGE);
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


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw a rectangle
        //g.setColor(Color.decode("#BBBBBB"));
        CS.drawShaedBorder(g, new Rectangle(40, 80, 850, 95)); // (x, y, width, height)
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public void refreshTable() {
        model.setOrders(orders);
        model.fireTableDataChanged();
    }

    private void showDeleteDialog(DashboardController dashboardController) {
        DeleteSelectionDialog<Order> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Order",
                orders,
                new String[]{"Order ID", "Customer ID", "Order Date", "Total Price"},
                order -> new Object[]{order.getOrderId(), order.getCustomerId(), order.getOrderDate(), order.getTotalPrice()},
                "Filter by Name / Customer ID",
                0,
                1,
                order -> dashboardController.crudOrder(order, 'd'),
                this::removeOrderFromTable
        );
        dialog.setVisible(true);
    }

    private void removeOrderFromTable(Order order) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getOrderId() == order.getOrderId()) {
                orders.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }

}
