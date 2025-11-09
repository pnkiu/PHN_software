package controller;

import dao.StaffDAO;
import model.StaffModel;
import java.util.ArrayList;

public class StaffController {
    private StaffDAO staffDAO;

    public StaffController() {
        this.staffDAO = StaffDAO.getInstance();
    }

    /**
     * Thêm nhân viên mới
     */
//    public boolean addStaff(StaffModel staff) {
//        return insertStaff(staff);
//    }

    public boolean insertStaff(StaffModel staff) {
        if (!validateStaffData(staff)) {
            return false;
        }
        return staffDAO.insert(staff);
    }

    /**
     * Cập nhật thông tin nhân viên
     */
    public boolean updateStaff(StaffModel staff) {
        if (!validateStaffData(staff)) {
            return false;
        }
        return staffDAO.update(staff);
    }

    /**
     * Xóa nhân viên
     */
    public boolean deleteStaff(String maNV) {
        return staffDAO.delete(maNV);
    }

    /**
     * Lấy thông tin nhân viên theo mã
     */
    public StaffModel getStaffById(String maNV) {
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
     * Validation dữ liệu nhân viên (bỏ ràng buộc mật khẩu)
     */
    public boolean validateStaffData(StaffModel staff) {
        if (staff == null) {
            System.out.println("❌ StaffModel is null");
            return false;
        }

        // Validate mã nhân viên
        if (staff.getMaNV() == null || staff.getMaNV().trim().isEmpty()) {
            System.out.println("❌ Mã nhân viên không được để trống");
            return false;
        }

        // Validate tên nhân viên
        if (staff.getTenNV() == null || staff.getTenNV().trim().isEmpty()) {
            System.out.println("❌ Tên nhân viên không được để trống");
            return false;
        }

        // Validate số điện thoại
        if (staff.getSdtNV() == null || staff.getSdtNV().trim().isEmpty()) {
            System.out.println("❌ Số điện thoại không được để trống");
            return false;
        }

        if (!staff.getSdtNV().matches("\\d{10,11}")) {
            System.out.println("❌ Số điện thoại phải có 10-11 chữ số");
            return false;
        }

        // Validate lương
        if (staff.getLuongNV() == null || staff.getLuongNV().trim().isEmpty()) {
            System.out.println("❌ Lương không được để trống");
            return false;
        }

        try {
            long salary = Long.parseLong(staff.getLuongNV());
            if (salary <= 0) {
                System.out.println("❌ Lương phải lớn hơn 0");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Lương phải là số hợp lệ");
            return false;
        }

        // Validate tên đăng nhập
        if (staff.getTenDangNhap() == null || staff.getTenDangNhap().trim().isEmpty()) {
            System.out.println("❌ Tên đăng nhập không được để trống");
            return false;
        }

        // BỎ RÀNG BUỘC MẬT KHẨU - chỉ kiểm tra không null
        if (staff.getMatKhau() == null) {
            System.out.println("❌ Mật khẩu không được null");
            return false;
        }

        System.out.println("✅ Validation passed for staff: " + staff.getTenNV());
        return true;
    }

    /**
     * Kiểm tra username đã tồn tại chưa
     */
    public boolean isUsernameExists(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return staffDAO.isTenDangNhapExists(username);
    }

    /**
     * Kiểm tra username đã tồn tại cho nhân viên khác
     */
    public boolean isUsernameExistsForOtherStaff(String username, String currentMaNV) {
        if (username == null || username.trim().isEmpty() || currentMaNV == null) {
            return false;
        }

        ArrayList<StaffModel> allStaff = staffDAO.selectAll();
        for (StaffModel staff : allStaff) {
            if (!staff.getMaNV().equals(currentMaNV) && username.equalsIgnoreCase(staff.getTenDangNhap())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiểm tra số điện thoại đã tồn tại chưa
     */
    public boolean isPhoneExists(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return staffDAO.isSdtExists(phone);
    }

    /**
     * Kiểm tra số điện thoại đã tồn tại cho nhân viên khác
     */
    public boolean isPhoneExistsForOtherStaff(String phone, String currentMaNV) {
        if (phone == null || phone.trim().isEmpty() || currentMaNV == null) {
            return false;
        }

        ArrayList<StaffModel> allStaff = staffDAO.selectAll();
        for (StaffModel staff : allStaff) {
            if (!staff.getMaNV().equals(currentMaNV) && phone.equals(staff.getSdtNV())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kiểm tra controller đã sẵn sàng chưa
     */
    public boolean isReady() {
        return staffDAO != null;
    }

    /**
     * Lấy mã nhân viên tiếp theo
     */
    public String getNextStaffId() {
        return staffDAO.getNextMaNV();
    }

    /**
     * Kiểm tra mã nhân viên đã tồn tại chưa
     */
    public boolean isMaNVExists(String maNV) {
        if (maNV == null || maNV.trim().isEmpty()) {
            return false;
        }
        StaffModel staff = staffDAO.selectById(maNV);
        return staff != null;
    }

    /**
     * Tạo đối tượng StaffModel mới với mã tự động
     */
    public StaffModel createNewStaff() {
        String nextMaNV = getNextStaffId();
        return new StaffModel(nextMaNV, "", "0", "", 1, "", "");
    }

    /**
     * Đăng nhập (nếu cần)
     */
    public StaffModel login(String tenDangNhap, String matKhau) {
        // Có thể implement sau nếu cần
        return null;
    }
}
//controller