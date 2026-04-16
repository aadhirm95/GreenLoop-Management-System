package views.panels;

import models.Employee;
import services.CS;

import java.awt.*;
import javax.swing.*;

public class HomePanel extends JPanel {
    private static final Color HEADER_BG = Color.BLACK;
    private static final int CONTENT_WIDTH = 640;
    private static final int WELCOME_WIDTH = 640;
    private static final Color TABLE_BG = new Color(232, 236, 239);
    private static final Color TABLE_TEXT = Color.BLACK;
    private static final Font TABLE_FONT = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font TABLE_VALUE_FONT = new Font("Segoe UI", Font.BOLD, 13);

    private Image watermarkImage;
    private Employee employee;
    private JLabel welcomeLabel;
    private JLabel titleLabel;
    private JLabel label_1_k;
    private JLabel label_1_v;
    private JLabel label_2_k;
    private JLabel label_2_v;
    private JLabel label_3_k;
    private JLabel label_3_v;
    private JLabel label_4_k;
    private JLabel label_4_v;
    private JLabel label_5_k;
    private JLabel label_5_v;
    private JLabel label_6_k;
    private JLabel label_6_v;
    private JLabel label_7_k;
    private JLabel label_7_v;
    private JLabel label_8_k;
    private JLabel label_8_v;
    private JLabel label_9_k;
    private JLabel label_9_v;
    private JLabel label_10_k;
    private JLabel label_10_v;

    private String teal = "#7faab5";
    private String facebookBlue = "#0075FB";
    private String violet = "#8f00ff";
    private String darkViolet = "#5c05a1";
    private String emeraldGreen = "#1abc9c";
    private String lightGreen = "#d5f4e6";

    private String bgColor = darkViolet;
    private String fgColor = "#FFFFFF";

    public HomePanel(Employee employee) {
        this.employee = employee;
        loadWatermarkImage();
        this.createUIComponents();
    }

    private void loadWatermarkImage() {
        try {
            watermarkImage = new ImageIcon("resources/images/logo.png").getImage();
        } catch (Exception e) {
            watermarkImage = null;
        }
    }

    private void createUIComponents() {
        this.setLayout(null);
        this.setBackground(new Color(245, 248, 250));

        String name = "Welcome " + employee.getTitle() + " " + employee.getFirstName() + " " + employee.getLastName() + "!";

        welcomeLabel = CS.paintLabels(this, "", name, "#000000", fgColor, 0, true);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        this.add(welcomeLabel);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

        JPanel statsHeaderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(HEADER_BG);
                g2d.fillRect(0, 0, this.getWidth(), 4);
            }
        };
        this.add(statsHeaderPanel);

        JLabel sectionHeader = new JLabel("ACCOUNT DETAILS");
        sectionHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sectionHeader.setForeground(new Color(44, 62, 80));
        sectionHeader.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(sectionHeader);

        titleLabel = new JLabel();
        this.add(titleLabel);

        label_1_k = CS.paintLabels(this, "    ", "Employee ID", "#34495e", "#2c3e50", 2, true);
        label_1_v = CS.paintLabels(this, "    ", String.valueOf(employee.getEmployeeId()), "#34495e", "#27ae60", 2, true);

        label_2_k = CS.paintLabels(this, "    ", "Title", "#34495e", "#2c3e50", 2, true);
        label_2_v = CS.paintLabels(this, "    ", employee.getTitle(), "#34495e", "#27ae60", 2, true);

        label_3_k = CS.paintLabels(this, "    ", "First Name", "#34495e", "#2c3e50", 2, true);
        label_3_v = CS.paintLabels(this, "    ", employee.getFirstName(), "#34495e", "#27ae60", 2, true);

        label_4_k = CS.paintLabels(this, "    ", "Last Name", "#34495e", "#2c3e50", 2, true);
        label_4_v = CS.paintLabels(this, "    ", employee.getLastName(), "#34495e", "#27ae60", 2, true);

        label_5_k = CS.paintLabels(this, "    ", "Username", "#34495e", "#2c3e50", 2, true);
        label_5_v = CS.paintLabels(this, "    ", employee.getUsername(), "#34495e", "#27ae60", 2, true);

        label_6_k = CS.paintLabels(this, "    ", "Address", "#34495e", "#2c3e50", 2, true);
        label_6_v = CS.paintLabels(this, "    ", employee.getAddress(), "#34595e", "#27ae60", 2, true);

        label_7_k = CS.paintLabels(this, "    ", "Mobile", "#34595e", "#2c3e50", 2, true);
        label_7_v = CS.paintLabels(this, "    ", employee.getMobile(), "#34595e", "#27ae60", 2, true);

        label_8_k = CS.paintLabels(this, "    ", "Email", "#34595e", "#2c3e50", 2, true);
        label_8_v = CS.paintLabels(this, "    ", employee.getEmail(), "#34595e", "#27ae60", 2, true);

        label_9_k = CS.paintLabels(this, "    ", "Schedule", "#34595e", "#2c3e50", 2, true);
        label_9_v = CS.paintLabels(this, "    ", employee.getSchedule(), "#34595e", "#27ae60", 2, true);

        label_10_k = CS.paintLabels(this, "    ", "Role", "#34595e", "#2c3e50", 2, true);
        label_10_v = CS.paintLabels(this, "    ", employee.getRole().getRoleName(), "#34595e", "#27ae60", 2, true);

        styleTableLabel(label_1_k, false);
        styleTableLabel(label_1_v, true);
        styleTableLabel(label_2_k, false);
        styleTableLabel(label_2_v, true);
        styleTableLabel(label_3_k, false);
        styleTableLabel(label_3_v, true);
        styleTableLabel(label_4_k, false);
        styleTableLabel(label_4_v, true);
        styleTableLabel(label_5_k, false);
        styleTableLabel(label_5_v, true);
        styleTableLabel(label_6_k, false);
        styleTableLabel(label_6_v, true);
        styleTableLabel(label_7_k, false);
        styleTableLabel(label_7_v, true);
        styleTableLabel(label_8_k, false);
        styleTableLabel(label_8_v, true);
        styleTableLabel(label_9_k, false);
        styleTableLabel(label_9_v, true);
        styleTableLabel(label_10_k, false);
        styleTableLabel(label_10_v, true);

        this.setBounds();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color c1 = new Color(245, 248, 250);
        Color c2 = new Color(235, 242, 245);

        GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        if (watermarkImage != null) {
            Graphics2D watermarkGraphics = (Graphics2D) g2d.create();
            watermarkGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));
            int watermarkSize = 360;
            int watermarkX = (getWidth() - watermarkSize) / 2;
            int watermarkY = (getHeight() - watermarkSize) / 2;
            watermarkGraphics.drawImage(watermarkImage, watermarkX, watermarkY, watermarkSize, watermarkSize, this);
            watermarkGraphics.dispose();
        }
    }

    @Override
    public void doLayout() {
        super.doLayout();
        setBounds();
    }

    public void setBounds() {
        int totalWidth = 520;
        int w_k = 220;
        int w_v = 280;
        int h = 28;
        int d = 8;
        int g = 20;
        int centeredContentX = Math.max(120, (getWidth() - CONTENT_WIDTH) / 2);
        int centeredTableX = Math.max(120, (getWidth() - (totalWidth + g)) / 2);
        int y = 125;

        welcomeLabel.setBounds(Math.max(120, (getWidth() - WELCOME_WIDTH) / 2), 20, WELCOME_WIDTH, 40);

        Component[] components = this.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel && component != this) {
                component.setBounds(centeredContentX, 80, CONTENT_WIDTH, 4);
            }
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if ("ACCOUNT DETAILS".equals(label.getText())) {
                    label.setBounds(centeredContentX, 90, CONTENT_WIDTH, 25);
                }
            }
        }

        label_1_k.setBounds(centeredTableX, y, w_k, h);
        label_1_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_2_k.setBounds(centeredTableX, y, w_k, h);
        label_2_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_3_k.setBounds(centeredTableX, y, w_k, h);
        label_3_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_4_k.setBounds(centeredTableX, y, w_k, h);
        label_4_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_5_k.setBounds(centeredTableX, y, w_k, h);
        label_5_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_6_k.setBounds(centeredTableX, y, w_k, h);
        label_6_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_7_k.setBounds(centeredTableX, y, w_k, h);
        label_7_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_8_k.setBounds(centeredTableX, y, w_k, h);
        label_8_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_9_k.setBounds(centeredTableX, y, w_k, h);
        label_9_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
        y += (d + h);

        label_10_k.setBounds(centeredTableX, y, w_k, h);
        label_10_v.setBounds((centeredTableX + w_k + g), y, w_v, h);
    }

    private void styleTableLabel(JLabel label, boolean valueLabel) {
        label.setOpaque(true);
        label.setBackground(TABLE_BG);
        label.setForeground(TABLE_TEXT);
        label.setFont(valueLabel ? TABLE_VALUE_FONT : TABLE_FONT);
        label.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
    }
}
