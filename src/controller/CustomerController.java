package controller;

import dao.CustomerDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.CustomerModel;
import view.CustomerView;

public class CustomerController {
    private CustomerView view;
    private CustomerDAO dao;

    public CustomerController(CustomerView view) {
        this.view = view;
        this.dao = CustomerDAO.getInstance();
        this.view.addAddCustomerListener(new AddCustomerListener());
        this.view.addDeleteCustomerListener(new DeleteCustomerListener());
        this.view.addEditCustomerListener(new EditCustomerListener());
        this.view.addSearchCustomerListener(new SearchCustomerListener());
        hienThiDB();
    }

    public void hienThiDB() {
        try {
            List<CustomerModel> customerList = dao.getAllCustomers();
            view.hienthidulieu(customerList);
        } catch (SQLException e) {
            view.showErrorMessage("Lỗi khi tải danh sách khách hàng: " + e.getMessage());
        }
    }

    class AddCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.formThemKH(CustomerController.this);
        }
    }

    class DeleteCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            xoaKhachHang();
        }
    }

    class EditCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CustomerModel selectedCustomer = view.getSelectedCustomer();
            if (selectedCustomer == null) {
                view.showErrorMessage("Vui lòng chọn 1 khách hàng để sửa!");
                return;
            }
            view.formSuaKH(CustomerController.this, selectedCustomer);
        }
    }

    class SearchCustomerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            timKiem();
        }
    }

    public void them(String tenKH, String dcKH, String sdtKH) {
        try {
            if (tenKH.isEmpty() || sdtKH.isEmpty()) {
                view.showErrorMessage("Tên và SĐT không được để trống!");
                return;
            }
            if (dao.ktrSDT(sdtKH)) {
                view.showErrorMessage("Số điện thoại này đã tồn tại!");
                return;
            }
            String newMaKH = dao.newMaKH();
            CustomerModel newCustomer = new CustomerModel(newMaKH, tenKH, dcKH, sdtKH, BigDecimal.ZERO, 0);
            boolean success = dao.addCustomer(newCustomer);
            if (success) {
                view.showSuccessMessage("Thêm khách hàng thành công!");
                view.closeAddDialog();
                hienThiDB();
            } else {
                view.showErrorMessage("Thêm thất bại!");
            }
        } catch (Exception ex) {
            view.showErrorMessage("Đã xảy ra lỗi: " + ex.getMessage());
        }
    }


    public void sua(String maKH, String tenKH, String dcKH, String sdtKH) {
        try {
            if (tenKH.isEmpty() || sdtKH.isEmpty()) {
                view.showErrorMessage("Tên và SĐT không được để trống!");
                return;
            }

            CustomerModel originalCustomer = dao.getCustomer(maKH);
            if (originalCustomer == null) {
                view.showErrorMessage("Không tìm thấy khách hàng!");
                return;
            }
            CustomerModel updatedCustomer = new CustomerModel(
                    maKH, tenKH, dcKH, sdtKH,
                    originalCustomer.getTongChiTieu(),
                    originalCustomer.getSoLanMua()
            );

            boolean success = dao.updateCustomer(updatedCustomer);

            if (success) {
                view.showSuccessMessage("Cập nhật khách hàng thành công!");
                view.closeEditDialog();
                hienThiDB();
            } else {
                view.showErrorMessage("Cập nhật thất bại!");
            }
        } catch (Exception ex) {
            view.showErrorMessage("Đã xảy ra lỗi: " + ex.getMessage());
        }
    }

    private void xoaKhachHang() {
        CustomerModel selected = view.getSelectedCustomer();
        if (selected == null) {
            view.showErrorMessage("Vui lòng chọn 1 khách hàng để xóa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(view,
                "Bạn có chắc muốn xóa khách hàng " + selected.getTenKH() + " không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = dao.deleteCustomer(selected.getMaKH());
                if (success) {
                    view.showSuccessMessage("Xóa thành công!");
                    hienThiDB();
                } else {
                    view.showErrorMessage("Xóa thất bại! (Có thể khách hàng đã có giao dịch)");
                }
            } catch (SQLException e) {
                view.showErrorMessage("Lỗi khi xóa: " + e.getMessage());
            }
        }
    }

    public void timKiem() {
        String keyword = view.getSearchKeyword();
        if (keyword == null || keyword.trim().isEmpty()) {
            hienThiDB();
        } else {
            try {
                List<CustomerModel> results = dao.searchAllFields(keyword);
                if (results.isEmpty()) {
                    view.showSuccessMessage("Không tìm thấy khách hàng nào với từ khóa: '" + keyword + "'");
                }
                view.hienthidulieu(results);
            } catch (SQLException e) {
                view.showErrorMessage("Lỗi khi tìm kiếm: " + e.getMessage());
            }
        }
    }
}