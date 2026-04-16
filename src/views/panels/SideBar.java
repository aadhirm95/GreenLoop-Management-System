package views.panels;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class SideBar extends JPanel {
    private static final int SIDE_BAR_WIDTH = 220;

    /*
    1   ADMIN
    2   MANAGER
    3   OFFICER
    4   CLERK
    5   TECHNICIAN
    */
    private final String roleName;

    private ArrayList<JButton> buttons = new ArrayList<>();

    public ArrayList<JButton> getButtons() {
        return buttons;
    }

    private ImageIcon imageIcon;
    private int iW = 200, iH = 0;

    public SideBar(String roleName) {
        this.roleName = roleName;
        this.loadImageFromResource();
        this.createUIComponents();
    }

    private void createUIComponents() {
        this.setLayout(null);
        this.setBackground(new Color(245, 248, 250));

        final int d = 8;
        final int sideBarWidth = SIDE_BAR_WIDTH;
        final int btnH = 32;
        final int btnW = sideBarWidth - 2 * d;

        int x = d, y = 20;

        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color c1 = Color.BLACK;
                Color c2 = new Color(35, 35, 35);
                GradientPaint gp = new GradientPaint(0, 0, c1, 0, getHeight(), c2);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            }
        };
        headerPanel.setBounds(new Rectangle(x, y, btnW, btnH));
        headerPanel.setLayout(null);
        this.add(headerPanel);

        JLabel titleLabel = new JLabel("GreenLoop");
        titleLabel.setBounds(0, 0, btnW, btnH);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(titleLabel);

        y += (btnH + 15);
        y += iH + 10;

        JLabel dateLabel = new JLabel(getDateString());
        dateLabel.setBounds(new Rectangle(x, y, btnW, 20));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        dateLabel.setForeground(Color.BLACK);
        this.add(dateLabel);

        y += 22;

        JLabel timeLabel = new JLabel(getTimeString());
        timeLabel.setBounds(new Rectangle(x, y, btnW, 20));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        timeLabel.setForeground(Color.BLACK);
        this.add(timeLabel);

        y += (25 + d);

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                timeLabel.setText(getTimeString());
            }
        }).start();

        JButton homeBtn = new JButton("Home");
        styleMenuButton(homeBtn);
        homeBtn.setBounds(new Rectangle(x, y, btnW, btnH));
        this.add(homeBtn);
        buttons.add(homeBtn);
        y += (btnH + d);

        JButton myJobsBtn = new JButton("My Jobs");
        styleMenuButton(myJobsBtn);
        myJobsBtn.setBounds(new Rectangle(x, y, btnW, btnH));
        this.add(myJobsBtn);
        buttons.add(myJobsBtn);
        y += (btnH + d);

        if (roleName.toLowerCase().contains("admin") || roleName.toLowerCase().contains("manager")) {
            JButton employeesBtn = new JButton("Manage Employees");
            styleMenuButton(employeesBtn);
            employeesBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(employeesBtn);
            buttons.add(employeesBtn);
            y += (btnH + d);
        }

        if (roleName.toLowerCase().contains("admin") || roleName.toLowerCase().contains("manager")) {
            JButton rolesBtn = new JButton("Manage Roles");
            styleMenuButton(rolesBtn);
            rolesBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(rolesBtn);
            buttons.add(rolesBtn);
            y += (btnH + d);
        }

        if (roleName.toLowerCase().contains("admin") || roleName.toLowerCase().contains("manager") || roleName.toLowerCase().contains("clerk") || roleName.toLowerCase().contains("officer")) {
            JButton customersBtn = new JButton("Manage Customers");
            styleMenuButton(customersBtn);
            customersBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(customersBtn);
            buttons.add(customersBtn);
            y += (btnH + d);

            JButton suppliersBtn = new JButton("Manage Suppliers");
            styleMenuButton(suppliersBtn);
            suppliersBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(suppliersBtn);
            buttons.add(suppliersBtn);
            y += (btnH + d);

            JButton partsBtn = new JButton("Manage Parts");
            styleMenuButton(partsBtn);
            partsBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(partsBtn);
            buttons.add(partsBtn);
            y += (btnH + d);

            JButton inventoryBtn = new JButton("Manage Inventory");
            styleMenuButton(inventoryBtn);
            inventoryBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(inventoryBtn);
            buttons.add(inventoryBtn);
            y += (btnH + d);

            JButton ordersBtn = new JButton("Manage Orders");
            styleMenuButton(ordersBtn);
            ordersBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(ordersBtn);
            buttons.add(ordersBtn);
            y += (btnH + d);

            JButton jobsBtn = new JButton("Manage Jobs");
            styleMenuButton(jobsBtn);
            jobsBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(jobsBtn);
            buttons.add(jobsBtn);
            y += (btnH + d);

            JButton notificationBtn = new JButton("Manage Notifications");
            styleMenuButton(notificationBtn);
            notificationBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(notificationBtn);
            buttons.add(notificationBtn);
            y += (btnH + d);

            JButton salesReportBtn = new JButton("Sales Report");
            styleMenuButton(salesReportBtn);
            salesReportBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(salesReportBtn);
            buttons.add(salesReportBtn);
            y += (btnH + d);

            JButton emailSettingsBtn = new JButton("Email Settings");
            styleMenuButton(emailSettingsBtn);
            emailSettingsBtn.setBounds(new Rectangle(x, y, btnW, btnH));
            this.add(emailSettingsBtn);
            buttons.add(emailSettingsBtn);
            y += (btnH + d);
        }

        JButton logoutBtn = new JButton("Logout");
        styleLogoutButton(logoutBtn);
        logoutBtn.setBounds(new Rectangle(x, y, btnW, btnH));
        this.add(logoutBtn);
        buttons.add(logoutBtn);

        int preferredHeight = y + btnH + 12;
        this.setPreferredSize(new Dimension(sideBarWidth, preferredHeight));
    }

    private void styleMenuButton(JButton btn) {
        btn.setBackground(new Color(108, 117, 125));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    private void styleLogoutButton(JButton btn) {
        btn.setBackground(new Color(220, 53, 69));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
    }

    public String getTimeString() {
        LocalDateTime now = LocalDateTime.now();
        String pattern = "hh:mm:ss a";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);
        return now.format(formatter);
    }

    public String getDateString() {
        LocalDateTime now = LocalDateTime.now();
        String pattern = "@1, MMM d@2 yyyy";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH);

        String formattedDate = now.format(formatter);
        String dayOfWeek = now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

        int dayOfMonth = now.getDayOfMonth();
        String dayOfMonthSuffix = getDayOfMonthSuffix(dayOfMonth);

        formattedDate = formattedDate.replace("@1", dayOfWeek);
        formattedDate = formattedDate.replace("@2", dayOfMonthSuffix);

        return formattedDate;
    }

    private String getDayOfMonthSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }

    private void loadImageFromResource() {
        try {
            imageIcon = new ImageIcon(getClass().getResource("/resources/images/ship_sea_02.gif"));
            if (imageIcon != null) {
                int originalWidth = imageIcon.getIconWidth();
                int originalHeight = imageIcon.getIconHeight();
                iH = (int) (((double) iW / originalWidth) * originalHeight);
            }
        } catch (Exception e) {
            System.out.println("Error Message!");
            System.out.println(e.getLocalizedMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imageIcon != null) {
            Image image = imageIcon.getImage();
            g.drawImage(image, 10, 45, iW, iH, this);
        }
    }
}
