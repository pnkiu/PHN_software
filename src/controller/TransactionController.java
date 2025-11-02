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

        // (Các listener của bạn đã đúng)
        this.view.addAddgdListener(new AddgdListener());
        this.view.addDeletegdListener(new DeletegdListener());
        this.view.addViewDetailsListener(new ViewDetailsListener());
        this.view.addEditgdListener(new EditListener());
        hienThiDB();
    }

    public void hienThiDB() {
        List<TransactionModel> gd = dao.selectAll();
        view.hienthidulieu(gd);
    }
    public void moCuaSoThemKhachHang() {
        view.formThemKhachHang(this);
    }

    // (Hàm themKhachHangMoi của bạn đã đúng)
    public void themKhachHangMoi(String tenKH, String sdtKH, String diaChi) {
        try {
            if (tenKH.isEmpty() || sdtKH.isEmpty()) {
                view.showErrorMessage("Tên và SĐT khách hàng không được để trống!");
                return;
            }
            if (customerDAO.isPhoneNumberExists(sdtKH)) {
                view.showErrorMessage("Số điện thoại này đã được đăng ký cho khách hàng khác!");
                return;
            }
            CustomerModel khMoi = new CustomerModel();
            khMoi.setTenKH(tenKH);
            khMoi.setSdtKH(sdtKH);
            khMoi.setDckH(diaChi);
            khMoi.setTongChiTieu(0);
            khMoi.setSoLanMua(0);

            boolean success = customerDAO.addCustomer(khMoi);

            if (success) {
                view.showSuccessMessage("Thêm khách hàng mới thành công!");
                view.closeAddCustomerDialog();
                List<CustomerModel> dsMoi = customerDAO.selectAll();
                view.refreshCustomerComboBox(dsMoi, khMoi);
            } else {
                view.showErrorMessage("Thêm khách hàng thất bại!");
            }
        } catch (Exception ex) {
            view.showErrorMessage("Lỗi khi thêm khách hàng: " + ex.getMessage());
            ex.printStackTrace();
        }
    }


    class AddgdListener implements ActionListener {
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

    class DeletegdListener implements ActionListener {
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

    // SỬA: Bỏ 'public' (cho nhất quán)
    class ViewDetailsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransactionModel tx = view.getSelectedTransaction();
            if (tx == null) {
                return;
            }

            try {
                CustomerModel kh = customerDAO.getCustomerByMaKH(tx.getMaKH());
                StaffModel nv = staffDAO.getStaffByMaNV(tx.getMaNV());
                ProductModel sp = productDAO.getProductByMaOto(tx.getMaOTO());
                view.showDetailDialog(tx, kh, nv, sp);

            } catch (Exception ex) {
                view.showErrorMessage("Lỗi khi tải chi tiết: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    // (Các hàm moCuaSoChonSanPham, setSanPhamSauKhiChon đã đúng)
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

    // SỬA: Lỗi 1 - Xử lý 'boolean' trả về từ DAO
    public void them(String maGD, String maKH, String maNV, String maOTO, double tongtien, String ngayGD, int soLuong) {
        if (maGD.isEmpty() || maKH.isEmpty() || maNV.isEmpty() || maOTO.isEmpty()) {
            view.showErrorMessage("Vui lòng điền đầy đủ thông tin !");
            return;
        }

        TransactionModel newGD = new TransactionModel(maGD, maKH, maNV, maOTO, tongtien, ngayGD, soLuong);

        // SỬA: dao.insert() giờ trả về boolean
        boolean success = dao.insert(newGD);

        if (success) {
            view.showSuccessMessage("Thêm giao dịch và trừ kho thành công!");
            view.closeAddDialog();
            hienThiDB();
        } else {
            // Lỗi có thể do hết hàng
            view.showErrorMessage("Thêm thất bại! (Lỗi CSDL hoặc không đủ hàng tồn kho)");
        }
    }

    // (class EditListener đã đúng)
    class EditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransactionModel tx = view.getSelectedTransaction();
            if (tx == null) return;

            try {
                CustomerModel kh = customerDAO.getCustomerByMaKH(tx.getMaKH());
                StaffModel nv = staffDAO.getStaffByMaNV(tx.getMaNV());
                ProductModel sp = productDAO.getProductByMaOto(tx.getMaOTO());

                List<CustomerModel> dsKhachHang = customerDAO.selectAll();
                List<StaffModel> dsNhanVien = staffDAO.selectAll();

                if(kh == null || nv == null) {
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


    // SỬA: Lỗi 2 - Đổi tên hàm cho khớp với View
    public void suaGiaoDich(TransactionModel tx, CustomerModel kh) {
        boolean txSuccess = false;
        boolean khSuccess = false;
        String errorMessage = "";

        try {
            // (Code logic 2-trong-1 của bạn đã đúng)
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
}