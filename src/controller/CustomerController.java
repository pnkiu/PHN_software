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

    // Thêm khách hàng mới
    public boolean addCustomer(CustomerModel customer) {
        try {
            // Kiểm tra số điện thoại đã tồn tại chưa
            if (customerDAO.isPhoneNumberExists(customer.getSdtKH())) {
                throw new IllegalArgumentException("Số điện thoại đã tồn tại trong hệ thống");
            }
            return customerDAO.addCustomer(customer);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
        }
    }

    // Xóa khách hàng
    public boolean deleteCustomer(int maKH) {
        try {
            return customerDAO.deleteCustomer(maKH);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
    public CustomerModel getCustomerByMaKH(int maKH) {
        try {
            return customerDAO.getCustomerByMaKH(maKH);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isReady() {
        try {
            return customerDAO != null && JDBC_Util.getConnection() != null
                    && !JDBC_Util.getConnection().isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}