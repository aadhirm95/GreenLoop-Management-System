import controllers.DashboardController;
import controllers.LoginController;
import controllers.OrderController;
import models.*;
import services.*;
import services.Impl.*;
import views.DashboardView;
import views.LoginView;
import views.OrderView;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        DatabaseConnectionService databaseConnectionService = new MySQLConnectionService();

        // Create the view
        LoginView loginView = new LoginView();
        loginView.setVisible(true);

        // Create the service
        EmployeeService employeeService = new EmployeeServiceImpl(databaseConnectionService);
        RoleService roleService = new RoleServiceImpl(databaseConnectionService);
        // Create the controller
        LoginController loginController = new LoginController(null, loginView, employeeService, roleService);
    }

    /*public static void main(String[] args) {
        DatabaseConnectionService databaseConnectionService = new MySQLConnectionService();
        EmployeeService employeeService = new EmployeeServiceImpl(databaseConnectionService);
        RoleService roleService = new RoleServiceImpl(databaseConnectionService);
        Employee employee = employeeService.authenticate(new Employee("john", "john"));
        if(employee.getEmployeeId()>0){
            employee.setRole(roleService.getRoleById(employee.getRoleId()));
            DashboardModel dashboardModel = new DashboardModel(employee);
            DashboardView dashboardView = new DashboardView(dashboardModel);
            DashboardController dashboardController = new DashboardController(dashboardView, dashboardModel);
            dashboardView.setVisible(true);
        }else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/

}