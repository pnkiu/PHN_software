package controller;

import dao.UsersDAO;
import model.UsersModel;
import view.LoginFrame;
import view.CarManageView;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class LoginController {
    private LoginFrame view;
    private UsersDAO usersDAO;

    public LoginController(LoginFrame view) {
        this.view = view;
        this.usersDAO = new UsersDAO();
        initController();
        loadRememberedUser();
        displayAvailableUsers();
    }

    private void initController() {
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });

        view.getPasswordField().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
    }

    private void performLogin() {
        String tenDangNhap = view.getUsername();
        String matKhau = view.getPassword();
        boolean rememberMe = view.isRememberMeChecked();

        // Validate input
        if (tenDangNhap.isEmpty() || matKhau.isEmpty()) {
            view.showErrorMessage("Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!");
            return;
        }

        System.out.println("🔐 Đang đăng nhập với: " + tenDangNhap);

        // Kiểm tra thông tin đăng nhập từ database
        if (usersDAO.validateUser(tenDangNhap, matKhau)) {
            // Lấy thông tin nhân viên từ database
            UsersModel user = usersDAO.getUserByUsername(tenDangNhap);

            if (user != null) {
                // Lưu thông tin remember me nếu được chọn
                if (rememberMe) {
                    saveUserCredentials(tenDangNhap, matKhau);
                } else {
                    clearUserCredentials();
                }

//                view.showSuccessMessage("Đăng nhập thành công! Chào mừng " +
//                        user.getRoleString() + ": " + user.getTenNV());

                // Đóng form login
                view.dispose();

                // Mở CarManageView với thông tin user
                openCarManageView(user);

            } else {
                view.showErrorMessage("Lỗi khi lấy thông tin nhân viên!");
            }

        } else {
            view.showErrorMessage("Tên đăng nhập hoặc mật khẩu không đúng!");
        }
    }

    private void openCarManageView(UsersModel user) {
        System.out.println("Mở CarManageView cho: " + user.toString());

        // Chạy MainApplicationFrame trong Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Truyền thông tin user vào MainApplicationFrame
                    CarManageView frame = new CarManageView(user);
                    frame.setVisible(true);
                    System.out.println("✅ MainApplicationFrame đã khởi động thành công!");
                    System.out.println("👤 Người dùng: " + user.getTenNV() + " - " + user.getRoleString());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Lỗi khi khởi động ứng dụng: " + e.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
            }
        });
    }

    // Hiển thị danh sách nhân viên có sẵn trong database
    private void displayAvailableUsers() {
        System.out.println("\n📊 DANH SÁCH NHÂN VIÊN TRONG DATABASE:");
        System.out.println("─────────────────────────────────");

        List<UsersModel> users = usersDAO.getAllUsers();
        if (users.isEmpty()) {
            System.out.println("✗ Không có nhân viên nào trong database");
            System.out.println("✨ Vui lòng kiểm tra bảng nhanvien có dữ liệu chưa");
        } else {
            for (UsersModel user : users) {
                String role = user.isAdmin() ? "👑 Quản trị" : "👤 Nhân viên";
                System.out.printf("%s: %-15s / %-6s (Mã NV: %s, Tên: %s)\n",
                        role, user.getTenDangNhap(), user.getMatKhau(),
                        user.getMaNV(), user.getTenNV());
            }
        }
        System.out.println("─────────────────────────────────\n");
    }

    private void loadRememberedUser() {
        // TODO: Implement load remembered user from preferences/file
    }

    private void saveUserCredentials(String tenDangNhap, String matKhau) {
        // TODO: Implement save credentials to secure storage
        System.out.println("💾 Đã lưu thông tin đăng nhập cho: " + tenDangNhap);
    }

    private void clearUserCredentials() {
        // TODO: Implement clear saved credentials
        System.out.println("🗑️ Đã xóa thông tin đăng nhập đã lưu");
    }
}
