package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import dao.ProductDAO;
import dao.StaffDAO;
import dao.CustomerDAO;
import dao.TransactionDAO;
import model.CustomerModel;
import model.ProductModel;
import model.StaffModel;
import model.TransactionModel;
import view.TransacionView;
import view.ChonSanPhamView;
import javax.swing.*;
import javax.swing.SwingUtilities;

public class TransactionController {
    private TransacionView view;
    private TransactionDAO dao;
    private StaffDAO staffDAO;
    private CustomerDAO customerDAO;
    private ProductDAO productDAO;
    private ChonSanPhamView chonSanPhamView;


    public TransactionController(TransacionView view, TransactionDAO dao) {
        this.view = view;
        this.dao = dao;
        this.productDAO = new ProductDAO();
        this.customerDAO = new CustomerDAO();
        this.staffDAO = new StaffDAO();

        this.view.addAddgdListener(new AddgdListener());
        this.view.addDeletegdListener(new DeletegdListener());
        this.view.addViewDetailsListener(new ViewDetailsListener());
        this.view.addEditgdListener(new EditListener());
        this.view.addSearchListener(new SearchListener());
        hienThiDB();
    }

    public void hienThiDB() {
        List<TransactionModel> gd = dao.selectAll();
        view.hienthidulieu(gd);
    }

    public void moCuaSoThemKhachHang() {
        view.formThemKhachHang(this);
    }

    //thêm khách hàng mới trong form them gd
    public void themKhachHangMoi(String tenKH, String sdtKH, String diaChi) {
        try {
            if (tenKH.isEmpty() || sdtKH.isEmpty()) {
                view.showErrorMessage("Tên và SĐT khách hàng không được để trống!");
                return;
            }
            if (customerDAO.ktrSDT(sdtKH)) {
                view.showErrorMessage("Số điện thoại này đã được đăng ký cho khách hàng khác!");
                return;
            }
            CustomerModel khMoi = new CustomerModel();
            khMoi.setTenKH(tenKH);
            khMoi.setSdtKH(sdtKH);
            khMoi.setDcKH(diaChi);
            khMoi.setTongChiTieu(0);
            khMoi.setSoLanMua(0);

            boolean success = customerDAO.addCustomer(khMoi);

            if (success) {
                view.showSuccessMessage("Thêm khách hàng mới thành công!");
                view.closeAddCustomerDialog();
                List<CustomerModel> dsMoi = customerDAO.selectAll();
                view.refreshCustomer(dsMoi, khMoi);
            } else {
                view.showErrorMessage("Thêm khách hàng thất bại!");
            }
        } catch (Exception ex) {
            view.showErrorMessage("Lỗi khi thêm khách hàng: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    public class AddgdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                List<CustomerModel> dsKhachHang = customerDAO.selectAll();
                List<StaffModel> dsNhanVien = staffDAO.selectAll();
                view.formThemsp(TransactionController.this, dsKhachHang, dsNhanVien);
            } catch (Exception ex) {
                view.showErrorMessage("Lỗi tải dữ liệu Khách Hàng/Nhân Viên: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public class DeletegdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransactionModel tx = view.getSelectedTransaction();
            if (tx == null) {
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view,
                    "Bạn có chắc chắn muốn xóa giao dịch " + tx.getMaGD() + "?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int rowsAffected = dao.delete(tx.getMaGD());
                    if (rowsAffected > 0) {
                        view.showSuccessMessage("Xóa giao dịch thành công!");
                        hienThiDB();
                    } else {
                        view.showErrorMessage("Xóa thất bại!");
                    }
                } catch (Exception ex) {
                    view.showErrorMessage("Lỗi khi xóa: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    //lấy dữ liệu để xem chi tiết giao dịch
    public class ViewDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransactionModel tx = view.getSelectedTransaction();
            if (tx == null) {
                return;
            }

            try {
                CustomerModel kh = customerDAO.getCustomer(tx.getMaKH());
                StaffModel nv = staffDAO.getStaff(tx.getMaNV());
                ProductModel sp = productDAO.getProductByMaOto(tx.getMaOTO());
                view.showDetail(tx, kh, nv, sp);

            } catch (Exception ex) {
                view.showErrorMessage("Lỗi khi tải chi tiết: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }


    public void moCuaSoChonSanPham() {
        List<ProductModel> dsSanPham = productDAO.selectAll();

        if (chonSanPhamView == null) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
            chonSanPhamView = new ChonSanPhamView(parentFrame, this, dsSanPham);
        } else {
            chonSanPhamView.updateTable(dsSanPham);
        }
        chonSanPhamView.setVisible(true);
    }

    public void setSanPhamSauKhiChon(ProductModel sanPham) {
        if (sanPham != null) {
            view.setSanPhamDaChon(sanPham);
        }
    }


    public void them(String maGD, String maKH, String maNV, String maOTO, double tongtien, String ngayGD, int soLuong) {
        if (maGD.isEmpty() || maKH.isEmpty() || maNV.isEmpty() || maOTO.isEmpty()) {
            view.showErrorMessage("Vui lòng điền đầy đủ thông tin !");
            return;
        }
        TransactionModel newGD = new TransactionModel(maGD, maKH, maNV, maOTO, tongtien, ngayGD, soLuong);
        boolean success = dao.insert(newGD);
        if (success) {
            view.showSuccessMessage("Thêm giao dịch và trừ kho thành công!");
            view.closeAddDialog();
            hienThiDB();
        } else {
            view.showErrorMessage("Thêm thất bại! (Lỗi CSDL hoặc không đủ hàng tồn kho)");
        }
    }

    public class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransactionModel tx = view.getSelectedTransaction();
            if (tx == null) return;

            try {
                CustomerModel kh = customerDAO.getCustomer(tx.getMaKH());
                StaffModel nv = staffDAO.getStaff(tx.getMaNV());
                ProductModel sp = productDAO.getProductByMaOto(tx.getMaOTO());

                List<CustomerModel> dsKhachHang = customerDAO.selectAll();
                List<StaffModel> dsNhanVien = staffDAO.selectAll();

                if (kh == null || nv == null) {
                    view.showErrorMessage("Không tìm thấy thông tin Khách Hàng hoặc Nhân Viên của giao dịch này.");
                    return;
                }

                view.formSuaGiaoDich(TransactionController.this, tx, kh, nv, sp, dsKhachHang, dsNhanVien);

            } catch (Exception ex) {
                view.showErrorMessage("Lỗi khi tải dữ liệu để sửa: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public void suaGiaoDich(TransactionModel tx, CustomerModel kh) {
        boolean txSuccess = false;
        boolean khSuccess = false;
        String errorMessage = "";

        try {
            int txRows = dao.update(tx);
            if (txRows > 0) {
                txSuccess = true;
            }
            boolean khUpdated = customerDAO.updateCustomer(kh);
            if (khUpdated) {
                khSuccess = true;
            }

        } catch (Exception e) {
            errorMessage = e.getMessage();
            e.printStackTrace();
        }
        if (txSuccess && khSuccess) {
            view.showSuccessMessage("Cập nhật giao dịch VÀ địa chỉ khách hàng thành công!");
            hienThiDB();
        } else if (txSuccess && !khSuccess) {
            view.showErrorMessage("Cập nhật giao dịch thành công, nhưng cập nhật địa chỉ khách hàng thất bại.");
            hienThiDB();
        } else {
            view.showErrorMessage("Cập nhật thất bại: " + errorMessage);
        }
    }

    public class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String keyword = view.getSearchText();

                // Nếu ô tìm kiếm rỗng, tải lại toàn bộ bảng
                if (keyword == null || keyword.trim().isEmpty()) {
                    hienThiDB(); // Gọi hàm tải lại toàn bộ
                } else {
                    // Nếu có chữ, gọi hàm search
                    List<TransactionModel> searchResult = dao.search(keyword);
                    if (searchResult.isEmpty()) {
                        view.showErrorMessage("Không tìm thấy kết quả nào khớp với '" + keyword + "'");
                    }
                    view.hienthidulieu(searchResult); // Hiển thị kết quả (kể cả rỗng)
                }
            } catch (Exception ex) {
                view.showErrorMessage("Lỗi khi tìm kiếm: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}