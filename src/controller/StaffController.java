package controller;

import dao.StaffDAO;
import model.StaffModel;
import java.util.ArrayList;
import java.util.List;

public class StaffController {
    private StaffDAO staffDAO;

    public StaffController() {
        this.staffDAO = StaffDAO.getInstance();
    }

    // ============================ CRUD OPERATIONS ============================

    /**
     * Cập nhật thông tin nhân viên
     */
    public boolean updateStaff(StaffModel staff) {
        if (!validateStaffData(staff)) {
            return false;
        }
        int result = staffDAO.update(staff);
        return result > 0;
    }

    /**
     * Thêm nhân viên mới
     */
    public boolean insertStaff(StaffModel staff) {
        if (!validateStaffData(staff)) {
            return false;
        }
        int result = staffDAO.insert(staff);
        return result > 0;
    }

    /**
     * Xóa nhân viên
     */
    public boolean deleteStaff(int maNV) {
        int result = staffDAO.delete(maNV);
        return result > 0;
    }

    /**
     * Lấy thông tin nhân viên theo mã
     */
    public StaffModel getStaffById(int maNV) {
        return staffDAO.selectById(maNV);
    }

    /**
     * Lấy danh sách tất cả nhân viên
     */
    public ArrayList<StaffModel> getStaffList() {
        return staffDAO.selectAll();
    }

    // ============================ SEARCH OPERATIONS ============================

    public ArrayList<StaffModel> searchAllFields(String keyword) {
        return staffDAO.searchAllFields(keyword);
    }

    public ArrayList<StaffModel> searchByMaNV(String keyword) {
        return staffDAO.searchByMaNV(keyword);
    }

    public ArrayList<StaffModel> searchByTenNV(String keyword) {
        return staffDAO.searchByTenNV(keyword);
    }

    public ArrayList<StaffModel> searchBySDT(String keyword) {
        return staffDAO.searchBySDT(keyword);
    }

    public ArrayList<StaffModel> searchByChucVu(String keyword) {
        return staffDAO.searchByChucVu(keyword);
    }

    // ============================ VALIDATION ============================

    /**
     * Validation dữ liệu nhân viên
     */
    public boolean validateStaffData(StaffModel staff) {
        if (staff == null) return false;

        if (staff.getTenNV() == null || staff.getTenNV().trim().isEmpty()) {
            return false;
        }

        if (staff.getSdtNV() == null || !staff.getSdtNV().matches("\\d{10}")) {
            return false;
        }

        if (staff.getLuongNV() <= 0) {
            return false;
        }

        if (staff.getTenDangNhap() == null || staff.getTenDangNhap().trim().isEmpty()) {
            return false;
        }

        if (staff.getMatKhau() == null || staff.getMatKhau().trim().isEmpty()) {
            return false;
        }

        return true;
    }

    /**
     * Kiểm tra controller đã sẵn sàng chưa
     */
    public boolean isReady() {
        return staffDAO != null;
    }
}