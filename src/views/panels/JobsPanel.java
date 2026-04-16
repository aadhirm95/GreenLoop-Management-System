package views.panels;

import controllers.DashboardController;
import models.Employee;
import models.Jobs;
import models.JobsTableModel;
import services.CS;
import views.DeleteSelectionDialog;
import views.ModifyJob;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class JobsPanel extends JPanel {
    private List<Jobs> jobs;
    private JobsTableModel model;
    private JTable table;
    private DashboardController dashboardController;

    public JobsPanel(DashboardController dashboardController, List<Jobs> jobs) {
        this.jobs = jobs;
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

        model = new JobsTableModel(jobs);
        table = new JTable(model);

        TableRowSorter<JobsTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        JLabel titleLabel = CS.paintLabels(this, "", "Manage Jobs", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        Font titleFont = new Font("Dialog", Font.BOLD, 12);
        Font calibriLight = new Font("Calibri Light", Font.PLAIN, 14);

        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0, true);
        filterLabel.setBounds(50, 90, 50, 20);

        JLabel jobIdLabel = CS.paintLabels(this, "", "Job ID", "#7faab5", "#ffffff", 0, true);
        jobIdLabel.setBounds(50, 120, 150, 20);

        JTextField jobIdFilterField = new JTextField();
        jobIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = jobIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Column index for job id is 0
                }
            }
        });
        jobIdFilterField.setBounds(50, 145, 150, 20);
        this.add(jobIdFilterField);

        JLabel orderIdLabel = CS.paintLabels(this, "", "Order ID", "#7faab5", "#ffffff", 0, true);
        orderIdLabel.setBounds(220, 120, 150, 20);

        JTextField orderIdFilterField = new JTextField();
        orderIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = orderIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 1)); // Column index for order id is 1
                }
            }
        });
        orderIdFilterField.setBounds(220, 145, 150, 20);
        this.add(orderIdFilterField);

        JLabel employeeIdLabel = CS.paintLabels(this, "", "Employee ID", "#7faab5", "#ffffff", 0, true);
        employeeIdLabel.setBounds(390, 120, 150, 20);

        JTextField employeeIdFilterField = new JTextField();
        employeeIdFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = employeeIdFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 2)); // Column index for employee id is 2
                }
            }
        });
        employeeIdFilterField.setBounds(390, 145, 150, 20);
        this.add(employeeIdFilterField);

        JLabel statusLabel = CS.paintLabels(this, "", "Status", "#7faab5", "#ffffff", 0, true);
        statusLabel.setBounds(560, 120, 150, 20);

        JTextField statusFilterField = new JTextField();
        statusFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = statusFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 6)); // Column index for status is 6
                }
            }
        });
        statusFilterField.setBounds(560, 145, 150, 20);
        this.add(statusFilterField);

        JLabel assignedDateLabel = CS.paintLabels(this, "", "Assigned Date", "#7faab5", "#ffffff", 0, true);
        assignedDateLabel.setBounds(730, 120, 150, 20);

        JTextField assignedDateFilterField = new JTextField();
        assignedDateFilterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = assignedDateFilterField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 7)); // Column index for assigned date is 7
                }
            }
        });
        assignedDateFilterField.setBounds(730, 145, 150, 20);
        this.add(assignedDateFilterField);

        // Create custom context menu
        JPopupMenu contextMenu = new JPopupMenu();
        JMenuItem editItem = new JMenuItem("View/Edit");
        JMenuItem deleteItem = new JMenuItem("Delete");

        // Add action listeners for menu items


        editItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                Jobs job = jobs.get(modelRow);

                ModifyJob modifyJob = new ModifyJob(JobsPanel.this, getEmployeeById(job.getEmployeeId()), "Edit Job", job);
                modifyJob.setVisible(true);
            }
        });

        deleteItem.addActionListener(e -> {
            int viewRow = table.getSelectedRow();
            if (viewRow >= 0) {
                int modelRow = table.convertRowIndexToModel(viewRow); // Convert view index to model index
                boolean deleted = dashboardController.crudJob(jobs.get(modelRow), 'd');
                if (deleted) {
                    jobs.remove(modelRow);
                    model.fireTableRowsDeleted(modelRow, modelRow);
                    this.getParent().repaint();
                    JOptionPane.showMessageDialog(this, "Job deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete job.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(40, 620, 100, 30);
        deleteButton.setBackground(new Color(220, 53, 69));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(e -> showDeleteDialog());
        this.add(deleteButton);

    }

    public boolean updateJob(Jobs updatedJob) {
        boolean updated = this.dashboardController.crudJob(updatedJob, 'u');

        if (updated) {
            int jobIndex = -1;
            for (int i = 0; i < jobs.size(); i++) {
                if (jobs.get(i).getJobId() == updatedJob.getJobId()) {
                    jobIndex = i;
                    break;
                }
            }

            // Update the job in the list
            if (jobIndex != -1) {
                jobs.set(jobIndex, updatedJob);

                // Notify the table model that the data has changed
                model.fireTableRowsUpdated(jobIndex, jobIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Job not found");
            }
        }
        return updated;
    }

    public boolean createJob(Jobs newJob) {
        // Add the new job to the list
        boolean created = dashboardController.crudJob(newJob, 'c');

        if (created) {
            List<Jobs> jobs_ = dashboardController.getAllJobs();
            jobs_.sort((o1, o2) -> Integer.compare(o1.getJobId(), o2.getJobId()));
            jobs.add(jobs_.get(jobs.size() - 1));
            int newRowIndex = jobs.size() - 1;
            model.fireTableRowsInserted(newRowIndex, newRowIndex);
            this.getParent().repaint();
        }
        return created;
    }

    public Employee getEmployeeById(int id) {
        Employee employee = new Employee();
        employee.setEmployeeId(id);
        if(dashboardController.crudEmployee(employee, 'r')){
            return employee;
        }else {
            return null;
        }
    }

    public void sendJobAssignmentEmail(int employeeId, int jobId) {
        dashboardController.sendJobAssignmentEmail(employeeId, jobId);
    }

    public void sendJobCompletionEmail(int orderId) {
        dashboardController.sendJobCompletionEmail(orderId);
    }

    private void showDeleteDialog() {
        DeleteSelectionDialog<Jobs> dialog = new DeleteSelectionDialog<>(
                this,
                "Delete Job",
                jobs,
                new String[]{"Job ID", "Order ID", "Employee ID", "Status"},
                job -> new Object[]{job.getJobId(), job.getOrderId(), job.getEmployeeId(), job.getStatus()},
                "Filter by Name / Status",
                0,
                3,
                job -> dashboardController.crudJob(job, 'd'),
                this::removeJobFromTable
        );
        dialog.setVisible(true);
    }

    private void removeJobFromTable(Jobs job) {
        for (int i = 0; i < jobs.size(); i++) {
            if (jobs.get(i).getJobId() == job.getJobId()) {
                jobs.remove(i);
                model.fireTableRowsDeleted(i, i);
                repaint();
                return;
            }
        }
    }
}
