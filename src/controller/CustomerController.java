package controller;

import dao.CustomerDAO;
import model.CustomerModel;
import database.JDBC_Util;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CustomerController {
    private CustomerDAO customerDAO;

    public CustomerController() throws SQLException {
        Connection connection = JDBC_Util.getConnection();
        this.customerDAO = new CustomerDAO(connection);
    }

    // Lấy danh sách khách hàng
    public List<CustomerModel> getCustomerList() {
        try {
            return customerDAO.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Thêm khách hàng mới với mã tự động
    public boolean addCustomer(CustomerModel customer) {
        try {
            // Kiểm tra số điện thoại đã tồn tại chưa
            if (customerDAO.isPhoneNumberExists(customer.getSdtKH())) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại trong hệ thống");
            }

            return customerDAO.addCustomerWithAutoMaKH(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    // Thêm khách hàng với mã cụ thể
    public boolean addCustomerWithMaKH(CustomerModel customer) {
        try {
            // Kiểm tra số điện thoại đã tồn tại chưa
            if (customerDAO.isPhoneNumberExists(customer.getSdtKH())) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại trong hệ thống");
            }

            // Kiểm tra mã KH đã tồn tại chưa
            if (customerDAO.isMaKHExists(customer.getMaKH())) {
                throw new IllegalArgumentException("Mã khách hàng đã tồn tại trong hệ thống");
            }

            return customerDAO.addCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    // Cập nhật khách hàng
    public boolean updateCustomer(CustomerModel customer) {
        try {
            // Kiểm tra số điện thoại đã tồn tại chưa (trừ mã KH hiện tại)
            if (customerDAO.isPhoneNumberExists(customer.getSdtKH(), customer.getMaKH())) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại trong hệ thống");
            }
            return customerDAO.updateCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    // XÓA KHÁCH HÀNG - PHIÊN BẢN ĐƠN GIẢN
    public boolean deleteCustomer(String maKH) {
        try {
            System.out.println("Controller: Deleting customer: " + maKH);
            return customerDAO.deleteCustomer(maKH);
        } catch (SQLException e) {
            System.err.println("Controller: Delete failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // XÓA KHÁCH HÀNG - PHIÊN BẢN CÓ KIỂM TRA (khuyến nghị)
    public String deleteCustomerWithCheck(String maKH) {
        try {
            System.out.println("Controller: Deleting customer with check: " + maKH);
            return customerDAO.deleteCustomerWithCheck(maKH);
        } catch (SQLException e) {
            String errorMsg = "Lỗi khi xóa khách hàng: " + e.getMessage();
            System.err.println("Controller: " + errorMsg);
            e.printStackTrace();
            return errorMsg;
        }
    }

    // Tìm kiếm theo tất cả trường
    public List<CustomerModel> searchAllFields(String keyword) {
        try {
            return customerDAO.searchAllFields(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tìm kiếm theo mã KH
    public List<CustomerModel> searchByMaKH(String keyword) {
        try {
            return customerDAO.searchByMaKH(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tìm kiếm theo tên KH
    public List<CustomerModel> searchByTenKH(String keyword) {
        try {
            return customerDAO.searchByTenKH(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tìm kiếm theo số điện thoại
    public List<CustomerModel> searchBySDT(String keyword) {
        try {
            return customerDAO.searchBySDT(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Tìm kiếm theo địa chỉ
    public List<CustomerModel> searchByDiaChi(String keyword) {
        try {
            return customerDAO.searchByDiaChi(keyword);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lấy khách hàng theo mã
    public CustomerModel getCustomerByMaKH(String maKH) {
        try {
            return customerDAO.getCustomerByMaKH(maKH);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Lấy mã khách hàng tiếp theo
    public String getNextMaKH() {
        try {
            return customerDAO.getNextMaKH();
        } catch (SQLException e) {
            e.printStackTrace();
            return "KH001";
        }
    }

    // Kiểm tra số điện thoại đã tồn tại chưa
    public boolean isPhoneNumberExists(String sdtKH) {
        try {
            return customerDAO.isPhoneNumberExists(sdtKH);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra số điện thoại đã tồn tại chưa (trừ mã KH hiện tại)
    public boolean isPhoneNumberExists(String sdtKH, String excludeMaKH) {
        try {
            return customerDAO.isPhoneNumberExists(sdtKH, excludeMaKH);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra mã KH đã tồn tại chưa
    public boolean isMaKHExists(String maKH) {
        try {
            return customerDAO.isMaKHExists(maKH);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Kiểm tra kết nối
    public boolean isReady() {
        try {
            return customerDAO != null && JDBC_Util.getConnection() != null
                    && !JDBC_Util.getConnection().isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    // Phương thức debug
    public void debugDelete(String maKH) {
        try {
            System.out.println("=== DEBUG DELETE ===");
            System.out.println("maKH to delete: " + maKH);
            System.out.println("Connection valid: " + isReady());

            // Kiểm tra tồn tại
            boolean exists = customerDAO.isCustomerExists(maKH);
            System.out.println("Customer exists: " + exists);

            if (exists) {
                CustomerModel customer = customerDAO.getCustomerByMaKH(maKH);
                System.out.println("Customer details: " + customer.getTenKH());
            }

            // Kiểm tra ràng buộc
            boolean hasConstraints = customerDAO.hasForeignConstraints(maKH);
            System.out.println("Has foreign constraints: " + hasConstraints);

            // Thử xóa
            boolean result = customerDAO.deleteCustomer(maKH);
            System.out.println("Delete result: " + result);

        } catch (SQLException e) {
            System.out.println("Debug error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}