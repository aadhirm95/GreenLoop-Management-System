package views.panels;

import controllers.DashboardController;
import models.Sale;
import services.CS;
import services.Impl.PDFGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class SalesReportPanel extends JPanel {

    private DashboardController dashboardController;
    private List<Sale> sales;

    private JTextField fromDateField;
    private JTextField toDateField;
    private String fromDateString;
    private String toDateString;
    private JTable serviceTable;
    private JTable salesTable;
    private JScrollPane serviceScrollPane;
    private JScrollPane salesScrollPane;

    public SalesReportPanel(DashboardController dashboardController) {
        this.dashboardController = dashboardController;

        this.setLayout(null);



        JLabel titleLabel = CS.paintLabels(this, "", "Sales Report", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);


        JLabel filterLabel = CS.paintLabels(this, "", "Find", "#7faab5", "#ffffff", 0,true);
        filterLabel.setBounds(50, 90, 50, 20);


        JLabel fromDateLabel = CS.paintLabels(this, "", "From Date (yyyy-MM-dd)", "#7faab5", "#ffffff", 0,true);
        fromDateLabel.setBounds(50, 120, 150, 20);

        fromDateField = new JTextField();
        fromDateField.setBounds(50, 145, 150, 20);
        this.add(fromDateField);


        JLabel toDateLabel = CS.paintLabels(this, "", "To Date (yyyy-MM-dd)", "#7faab5", "#ffffff", 0,true);
        toDateLabel.setBounds(220, 120, 150, 20);


        toDateField = new JTextField();
        toDateField.setBounds(220, 145, 150, 20);
        this.add(toDateField);



        JLabel searchLabel = CS.paintLabels(this, "", "Search", "#7faab5", "#ffffff", 0,true);
        searchLabel.setBounds(390, 120, 150, 20);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(390, 145, 150, 20);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        this.add(searchButton);


        JLabel downloadLabel = CS.paintLabels(this, "", "Download", "#7faab5", "#ffffff", 0,true);
        downloadLabel.setBounds(560, 120, 150, 20);

        JButton downloadButton = new JButton("Download");
        downloadButton.setBounds(560, 145, 150, 20);
        downloadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                download();
            }
        });
        this.add(downloadButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw a rectangle
        CS.drawShaedBorder(g, new Rectangle(40, 80, 850, 95));
        //CS.drawVerticalDoubleLine(g, 380,380,81,174);
        //CS.drawDoubleBorder(g, new Rectangle(40, 500, 850, 170));
        //CS.drawDoubleBorder(g, new Rectangle(50, 510, 330, 150));
        //CS.drawDoubleBorder(g, new Rectangle(390, 510, 490, 150));
        //50 515, 380 595
    }

    private void search(){
        fromDateString = fromDateField.getText();
        if(fromDateString.isEmpty()){
            fromDateString = "2000-01-01";
            fromDateField.setText(fromDateString);
        }
        toDateString = toDateField.getText();
        if(toDateString.isEmpty()){
            toDateString = "2100-01-01";
            toDateField.setText(toDateString);
        }

        sales = dashboardController.getSalesByRange(fromDateString, toDateString);

        serviceTable = createServiceTable();
        salesTable = createSalesTable();

        serviceScrollPane = new JScrollPane(serviceTable);
        serviceScrollPane.setBounds(40, 240, 400, 60);
        this.add(serviceScrollPane);

        salesScrollPane = new JScrollPane(salesTable);
        salesScrollPane.setBounds(40, 330, 850, 250);
        this.add(salesScrollPane);

    }

    private void download(){
        try {
            search();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
            fileChooser.setSelectedFile(new File("SalesReport_" + fromDateString + "_" + toDateString + ".pdf"));
            int option = fileChooser.showSaveDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String destination = selectedFile.getAbsolutePath();
                PDFGenerator.generateSalesReport(sales, destination, fromDateString, toDateString);
                JOptionPane.showMessageDialog(null, "PDF generated successfully!");
            }
        }  catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Error generating PDF!");
            ex.printStackTrace();
        }
    }

    private JTable createServiceTable() {
        String[] columnNames = {"Service Name", "Total Sales"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Sale sale : sales) {
            if (sale.isService()) {
                model.addRow(new Object[]{sale.getServiceName(), sale.getTotalSales()});
            }
        }

        return new JTable(model);
    }

    private JTable createSalesTable() {
        String[] columnNames = {"Part ID", "Part Name", "Unit Price", "Quantity", "Total Sales"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Sale sale : sales) {
            if (!sale.isService()) {
                model.addRow(new Object[]{sale.getPartId(), sale.getPartName(), sale.getUnitPrice(), sale.getQuantity(), sale.getTotalSales()});
            }
        }

        return new JTable(model);
    }


}
