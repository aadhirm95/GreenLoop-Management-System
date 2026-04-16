package views;

import models.DashboardModel;
import models.Employee;
import services.CS;
import views.panels.HomePanel;
import views.panels.SideBar;

import javax.swing.*;

public class DashboardView extends JFrame {


    private JPanel mainPanel;
    private SideBar sideBar;
    private JScrollPane sideBarScrollPane;
    private JPanel contentPanel;
    private HomePanel homePanel;
    private DashboardModel dashboardModel;
    private String currentPanelName = "Home";


    public DashboardView(DashboardModel dashboardModel){
        this.dashboardModel = dashboardModel;
        this.createUIComponents();
    }
    public DashboardView() {
        this.createUIComponents();
    }

    private void createUIComponents() {

        this.mainPanel = new JPanel(null);
        this.sideBar = new SideBar(this.dashboardModel.getCurrentUser().getRole().getRoleName());
        this.sideBarScrollPane = new JScrollPane(this.sideBar);
        this.homePanel = new HomePanel(this.dashboardModel.getCurrentUser());
        this.contentPanel = this.homePanel;

        this.sideBarScrollPane.setBounds(0, 0, 220, 860);
        this.sideBarScrollPane.setBorder(null);
        this.sideBarScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.sideBarScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.contentPanel.setBounds(220, 0, 960, 860);

        this.mainPanel.add(this.sideBarScrollPane);
        this.mainPanel.add(this.contentPanel);

        JLabel footerLabel = CS.paintFooter(mainPanel);
        footerLabel.setBounds(0, 840, 1200, 20);

        this.setContentPane(mainPanel);

        this.setTitle("GreenLoop");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(40,40,1200,860);
    }


    public Employee getCurrentUser() {
        return this.dashboardModel.getCurrentUser();
    }

    public void setCurrentUser(Employee currentUser) {
        if(this.dashboardModel==null){
            this.dashboardModel = new DashboardModel(currentUser);
        }else {
            this.dashboardModel.setCurrentUser(currentUser);
        }
    }

    public DashboardModel getDashboardModel() {
        return dashboardModel;
    }

    public void setDashboardModel(DashboardModel dashboardModel) {
        this.dashboardModel = dashboardModel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public SideBar getSideBar() {
        return sideBar;
    }

    public void setSideBar(SideBar sideBar) {
        this.sideBar = sideBar;
    }

    public JScrollPane getSideBarScrollPane() {
        return sideBarScrollPane;
    }

    public void setSideBarScrollPane(JScrollPane sideBarScrollPane) {
        this.sideBarScrollPane = sideBarScrollPane;
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(JPanel contentPanel) {
        this.contentPanel = contentPanel;
    }

    public HomePanel getHomePanel() {
        return homePanel;
    }

    public void setHomePanel(HomePanel homePanel) {
        this.homePanel = homePanel;
    }

    public String getCurrentPanelName() {
        return currentPanelName;
    }

    public void setCurrentPanelName(String currentPanelName) {
        this.currentPanelName = currentPanelName;
    }
}
