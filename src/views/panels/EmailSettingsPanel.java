package views.panels;

import controllers.DashboardController;
import models.Property;
import services.CS;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmailSettingsPanel extends JPanel {


    private final DashboardController dashboardController;
    private JTextField protocolField;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField value1Field;
    private JTextField value2Field;
    private JTextField value3Field;
    private JTextField value4Field;
    private JTextField value5Field;
    private String bgColor = "#2c5f6b";
    private String fgColor = "#FFFFFF";
    private List<Property> properties;
    private JButton saveButton;
    private JButton cancelButton;
    private JButton resetButton;

    public EmailSettingsPanel(DashboardController dashboardController, List<Property> properties) {


        this.setLayout(null);
        this.dashboardController = dashboardController;
        this.properties = properties;


        JLabel titleLabel = CS.paintLabels(this, "    ", "Email Settings", "#148c34", "#ffffff", 0, true);
        titleLabel.setBounds(40, 40, 250, 25);

        createUIComponents();

    }


    private void createUIComponents() {
        int x = 80, y = 120, lW = 200, lH = 25, fW = 240, fH = 25, g = 40, d = 10;

        JLabel protocolLabel = CS.paintLabels(this, "    ", "mail.transport.protocol", bgColor, fgColor, 2, true);
        protocolLabel.setBounds(x, y, lW, lH);
        protocolField = new JTextField();
        protocolField.setEditable(false);
        protocolField.setBounds(x + lW + g, y, fW, fH);
        this.add(protocolField);
        y += lH + d;

        JLabel usernameLabel = CS.paintLabels(this, "    ", "Username", bgColor, fgColor, 2, true);
        usernameLabel.setBounds(x, y, lW, lH);
        usernameField = new JTextField();
        usernameField.setBounds(x + lW + g, y, fW, fH);
        this.add(usernameField);
        y += lH + d;

        JLabel passwordLabel = CS.paintLabels(this, "    ", "Password", bgColor, fgColor, 2, true);
        passwordLabel.setBounds(x, y, lW, lH);
        passwordField = new JTextField();
        passwordField.setBounds(x + lW + g, y, fW, fH);
        this.add(passwordField);
        y += lH + d;

        JLabel key1Label = CS.paintLabels(this, "    ", "mail.smtp.host:", bgColor, fgColor, 2, true);
        key1Label.setBounds(x, y, lW, lH);
        value1Field = new JTextField();
        value1Field.setBounds(x + lW + g, y, fW, fH);
        this.add(value1Field);
        y += lH + d;

        JLabel keyy2Label = CS.paintLabels(this, "    ", "mail.smtp.port", bgColor, fgColor, 2, true);
        keyy2Label.setBounds(x, y, lW, lH);
        value2Field = new JTextField();
        value2Field.setBounds(x + lW + g, y, fW, fH);
        this.add(value2Field);
        y += lH + d;

        JLabel key3Label = CS.paintLabels(this, "    ", "mail.smtp.auth", bgColor, fgColor, 2, true);
        key3Label.setBounds(x, y, lW, lH);
        value3Field = new JTextField();
        value3Field.setBounds(x + lW + g, y, fW, fH);
        this.add(value3Field);
        y += lH + d;

        JLabel key4Label = CS.paintLabels(this, "    ", "mail.debug", bgColor, fgColor, 2, true);
        key4Label.setBounds(x, y, lW, lH);
        value4Field = new JTextField();
        value4Field.setBounds(x + lW + g, y, fW, fH);
        this.add(value4Field);
        y += lH + d;

        JLabel key5Label = CS.paintLabels(this, "    ", "mail.smtp.starttls.enable", bgColor, fgColor, 2, true);
        key5Label.setBounds(x, y, lW, lH);
        value5Field = new JTextField();
        value5Field.setBounds(x + lW + g, y, fW, fH);
        this.add(value5Field);
        y += lH + d;


        // Create Save button
        saveButton = new JButton("Save");
        saveButton.setBounds(80, y + 40, 100, 25);
        saveButton.addActionListener(e -> updatePropertiesFromFields());
        this.add(saveButton);

        // Create Cancel button
        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(200, y + 40, 100, 25);
        cancelButton.addActionListener(e -> populateFields());
        this.add(cancelButton);

        // Create Reset button
        resetButton = new JButton("Hard Reset");
        resetButton.setBounds(320, y + 40, 100, 25);
        resetButton.addActionListener(e -> hardResetEmailSettings());
        this.add(resetButton);


        populateFields();

    }

    public void populateFields(){
        for (int i = 0; i < properties.size(); i++) {
            Property property = properties.get(i);
            if (property.getPropertyKey().startsWith("username")) {
                usernameField.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("password")) {
                passwordField.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("mail.smtp.host")) {
                value1Field.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("mail.smtp.port")) {
                value2Field.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("mail.smtp.auth")) {
                value3Field.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("mail.transport.protocol")) {
                protocolField.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("mail.debug")) {
                value4Field.setText(property.getValue());
            } else if (property.getPropertyKey().startsWith("mail.smtp.starttls.enable")) {
                value5Field.setText(property.getValue());
            }
        }
    }


    public void hardResetEmailSettings() {
        List<Property> properties = new ArrayList<>();
        properties.add(new Property("mail.debug", "true", "email"));
        properties.add(new Property("mail.smtp.auth", "true", "email"));
        properties.add(new Property("mail.smtp.host", "smtp.gmail.com", "email"));
        properties.add(new Property("mail.smtp.port", "587", "email"));
        properties.add(new Property("mail.smtp.starttls.enable", "true", "email"));
        properties.add(new Property("mail.transport.protocol", "smtp", "email"));
        properties.add(new Property("password", "uhwkzenjrjiyjcdo", "email"));
        properties.add(new Property("username", "kamshika236@gmail.com", "email"));
        boolean updated = this.dashboardController.addOrUpdateProperties(properties);
        if(updated){
            this.properties = properties;
            this.populateFields();
        }
    }

    public void updatePropertiesFromFields() {
        List<Property> updatedProperties = new ArrayList<>();
        updatedProperties.add(new Property("mail.transport.protocol", protocolField.getText(), "email"));
        updatedProperties.add(new Property("username", usernameField.getText(), "email"));
        updatedProperties.add(new Property("password", passwordField.getText(), "email"));
        updatedProperties.add(new Property("mail.smtp.host", value1Field.getText(), "email"));
        updatedProperties.add(new Property("mail.smtp.port", value2Field.getText(), "email"));
        updatedProperties.add(new Property("mail.smtp.auth", value3Field.getText(), "email"));
        updatedProperties.add(new Property("mail.debug", value4Field.getText(), "email"));
        updatedProperties.add(new Property("mail.smtp.starttls.enable", value5Field.getText(), "email"));
        boolean updated = this.dashboardController.addOrUpdateProperties(updatedProperties);
        if(updated){
            this.properties = properties;
            this.populateFields();
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw a rectangle
        CS.drawShaedBorder(g, new Rectangle(40, 80, 850, 500));
        //CS.drawVerticalDoubleLine(g, 380,380,81,174);
        //CS.drawDoubleBorder(g, new Rectangle(40, 500, 850, 170));
        //CS.drawDoubleBorder(g, new Rectangle(50, 510, 330, 150));
        //CS.drawDoubleBorder(g, new Rectangle(390, 510, 490, 150));
        //50 515, 380 595
    }


}
