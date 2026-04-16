package views;

import services.CS;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginView extends JFrame{
    private JPanel mainPanel;
    private Image logoImage;


    public JTextField getUsernameText() {
        return usernameText;
    }

    public JPasswordField getPasswordText() {
        return passwordText;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    private JTextField usernameText;
    private JPasswordField passwordText;

    private JButton loginButton;
    private JButton cancelButton;



    public LoginView(){
        this.createUIComponents();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        final Image image;
        Image tempImage = null;
        try {
            java.net.URL imageUrl = getClass().getResource("/resources/images/ecofriendly_2.jpeg");
            if (imageUrl != null) {
                tempImage = new ImageIcon(imageUrl).getImage();
            } else {
                tempImage = new ImageIcon("resources/images/ecofriendly_2.jpeg").getImage();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        image = tempImage;

        try {
            logoImage = new ImageIcon("resources/images/logo.png").getImage();
        } catch (Exception e) {
            logoImage = null;
        }

        this.mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g.setColor(Color.decode("#D6EFD6"));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }

                if (logoImage != null) {
                    g.drawImage(logoImage, 20, 18, 92, 92, this);
                }
            }
        };

        this.setResizable(false);

        mainPanel.setLayout(null);

        Font font1 = new Font("Amasis MT Pro", Font.PLAIN, 70);
        Color color1 = Color.WHITE;

        JLabel titleLabel1 = new JLabel("GREENLOOP SYSTEM");
        Font smallerFont = new Font("Amasis MT Pro", Font.PLAIN, 60);
        titleLabel1.setFont(smallerFont);
        titleLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel1.setForeground(color1);
        titleLabel1.setLocation(80,240);
        titleLabel1.setSize(700,120);
        mainPanel.add(titleLabel1);

        JLabel logoTitleLabel = new JLabel("GreenLoop");
        logoTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logoTitleLabel.setForeground(Color.WHITE);
        logoTitleLabel.setBounds(120, 46, 180, 30);
        mainPanel.add(logoTitleLabel);

        JPanel whitePanel = new JPanel();
        whitePanel.setLayout(null);
        String htmlColor = "#66BB6A"; // HTML color for green
        Color color = Color.decode(htmlColor); // Decode the HTML color
        Color transparentColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 80); // Set alpha for transparency
        whitePanel.setBackground(transparentColor);
        mainPanel.add(whitePanel);

        whitePanel.setLocation(700,100);
        whitePanel.setSize(400,400);




        Font font5 = new Font("Amasis MT Pro", Font.BOLD, 14);
        Color color5 = Color.BLACK;

        JLabel usernameLabel = new JLabel();
        usernameLabel.setFont(font5);
        usernameLabel.setText("Username");
        usernameLabel.setForeground(color5);
        whitePanel.add(usernameLabel);


        whitePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        usernameText = new JTextField();
        usernameText.setFont(font5);
        usernameText.setForeground(color5);
        usernameText.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(usernameText);


        whitePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel passwordLabel = new JLabel();
        passwordLabel.setFont(font5);
        passwordLabel.setText("Password");
        passwordLabel.setForeground(color5);
        passwordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(passwordLabel);


        whitePanel.add(Box.createRigidArea(new Dimension(0, 10)));

        passwordText = new JPasswordField();
        passwordText.setFont(font5);
        passwordText.setForeground(color5);
        passwordText.setEchoChar('*');
        passwordText.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(passwordText);

        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        whitePanel.add(loginButton);
        whitePanel.add(cancelButton);


        usernameLabel.setLocation(75,150);
        usernameLabel.setSize(100,20);
        usernameText.setLocation(175,150);
        usernameText.setSize(150,20);

        passwordLabel.setLocation(75,180);
        passwordLabel.setSize(100,20);
        passwordText.setLocation(175,180);
        passwordText.setSize(150,20);

        loginButton.setLocation(115, 220);
        loginButton.setSize(100,25);
        cancelButton.setLocation(225, 220);
        cancelButton.setSize(100,25);

        JLabel footerLabel = CS.paintFooter(mainPanel);
        footerLabel.setFont(font5);
        footerLabel.setBounds(0, 560, 1200, 30);

        this.setContentPane(mainPanel);
        this.setTitle("GreenLoop System Login");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(50,50,1200,600);
    }
}
