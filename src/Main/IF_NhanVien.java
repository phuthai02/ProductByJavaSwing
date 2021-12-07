/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.NhanVienDAO;
import Entity.NhanVien;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xdate;
import Untils.Ximage;
import Untils.Xmail;
import Untils.Xpassword;
import java.awt.Color;
import java.io.File;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_NhanVien extends javax.swing.JInternalFrame {

    /**
     * Creates new form IF_NhanVien
     */
    public IF_NhanVien() {
        initComponents();
        init();
    }
    int pageIndexDS;
    int pageIndexLT;
    int row = -1;
    DefaultTableModel modelDS;
    DefaultTableModel modelLT;
    NhanVienDAO daoNV;
    String passWord;

    void init() {
        setResizable(false);
        setTitle("QUẢN LÝ NHÂN VIÊN");
        prepareGUI();
        tabs.setSelectedIndex(1);
        modelDS = (DefaultTableModel) tblNhanVien.getModel();
        modelLT = (DefaultTableModel) tblLuuTru.getModel();
        daoNV = new NhanVienDAO();
        fillToDanhSach();
        fillToLuuTru();
        updateStatus();
    }

    void prepareGUI() {
        pnlB4.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        pnlB1.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        pnlB5.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        pnlB3.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        pnlB2.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        addItemToCbo(cboDS);
        addItemToCbo(cboLT);
        String h[] = {"Mã NV", "Tên NV", "Ngày sinh", "Giới tính", "Địa chỉ", "SDT", "Email", "Ảnh", "Vai trò"};
        modelDS = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }

        };
        modelLT = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tblNhanVien.setModel(modelDS);
        tblLuuTru.setModel(modelLT);
    }

    void addItemToCbo(JComboBox cbo) {
        cbo.removeAllItems();
        cbo.addItem("Tất cả");
        cbo.addItem("Nam");
        cbo.addItem("Nữ");
    }

    String findGioiTinh(JComboBox cbo) {
        switch (cbo.getSelectedIndex()) {
            case 0:
                return "";
            case 1:
                return "1";
            case 2:
                return "0";
        }
        return null;
    }

    void fillToDanhSach() {
        List<NhanVien> lst = daoNV.selectPagingFull(1, pageIndexDS, txtTimDS.getText().trim(), findGioiTinh(cboDS));
        lblPageIndexDS.setText(pageIndexDS + 1 + "");
        updateStatusPage();
        modelDS.setRowCount(0);
        lst.forEach((nv) -> {
            modelDS.addRow(new Object[]{nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.isGioiTinh() ? "Nam" : "Nữ", nv.getDiaChi(), nv.getSDT(), nv.getEmail(), nv.getAnhNV(), nv.isVaiTro() ? "Chủ nhà hàng" : "Nhân viên"});
        });

    }

    void fillToLuuTru() {
        List<NhanVien> lst = daoNV.selectPagingFull(0, pageIndexLT, txtTimLT.getText().trim(), findGioiTinh(cboLT));
        lblPageIndexLT.setText(pageIndexLT + 1 + "");
        updateStatusPage();
        modelLT.setRowCount(0);
        lst.forEach((nv) -> {
            modelLT.addRow(new Object[]{nv.getMaNV(), nv.getTenNV(), nv.getNgaySinh(), nv.isGioiTinh() ? "Nam" : "Nữ", nv.getDiaChi(), nv.getSDT(), nv.getEmail(), nv.getAnhNV(), nv.isVaiTro() ? "Chủ nhà hàng" : "Nhân viên"});
        });

    }

    void updateStatusPage() {
        boolean firstDS = pageIndexDS == 0;
        boolean firstLT = pageIndexLT == 0;
        boolean lastDS = daoNV.selectPagingFull(1, pageIndexDS + 1, txtTimDS.getText().trim(), findGioiTinh(cboDS)).isEmpty();
        boolean lastLT = daoNV.selectPagingFull(0, pageIndexLT + 1, txtTimLT.getText().trim(), findGioiTinh(cboLT)).isEmpty();
        btnPreDS.setEnabled(!firstDS);
        btnPreLT.setEnabled(!firstLT);
        btnNextDS.setEnabled(!lastDS);
        btnNextLT.setEnabled(!lastLT);
    }

    void resetForm() {
        row = -1;
        txtMa.setText("");
        txtTen.setText("");
        txtDiaChi.setText("");
        txtEmail.setText("");
        txtSdt.setText("");
        txtNgaySinh.setDate(null);
        rdoNam.setSelected(true);
        rdoCNH.setSelected(true);
        lblAnh.setIcon(null);
        lblAnh.setToolTipText("");
        updateStatus();
    }

    void setForm(NhanVien nv) {
        txtMa.setText(nv.getMaNV());
        txtTen.setText(nv.getTenNV());
        txtDiaChi.setText(nv.getDiaChi());
        txtEmail.setText(nv.getEmail());
        txtSdt.setText(nv.getSDT());
        txtNgaySinh.setDate(Xdate.toDate(nv.getNgaySinh(), "yyyy-MM-dd"));
        if (nv.isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        if (nv.isVaiTro()) {
            rdoCNH.setSelected(true);
        } else {
            rdoNV.setSelected(true);
        }
        lblAnh.setIcon(Ximage.read(nv.getAnhNV(), lblAnh.getWidth() - 4, lblAnh.getHeight() - 4));
        lblAnh.setToolTipText(nv.getAnhNV());
    }

    void updateStatus() {
        boolean edit = (row >= 0);
        txtMa.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void khoiPhuc() {
        if (tblLuuTru.getSelectedRow() >= 0) {
            if (MsgBox.confirm(this, "Bạn có chắc muốn khôi phục nhân viên này?")) {

                NhanVien nv = daoNV.selectById(tblLuuTru.getValueAt(tblLuuTru.getSelectedRow(), 0).toString());
                daoNV.update(nv);
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                fillToLuuTru();
                MsgBox.alert(this, "Khôi phục thành công");
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn nhân viên cần khôi phục!");
        }
    }

    NhanVien getForm() {
        if (row >= 0) {
            return new NhanVien(txtMa.getText().trim(), txtTen.getText().trim(), daoNV.selectById(txtMa.getText().trim()).getMatKhau().trim(), txtDiaChi.getText().trim(), txtSdt.getText().trim(), txtEmail.getText().trim(), Xdate.toString(txtNgaySinh.getDate(), "yyyy-MM-dd"), rdoNam.isSelected(), lblAnh.getToolTipText(), daoNV.selectById(txtMa.getText().trim()).getMauNen(), rdoCNH.isSelected(), true);
        }
        passWord = Xpassword.randomPassword(8);
        return new NhanVien(txtMa.getText().trim(), txtTen.getText().trim(), passWord, txtDiaChi.getText().trim(), txtSdt.getText().trim(), txtEmail.getText().trim(), Xdate.toString(txtNgaySinh.getDate(), "yyyy-MM-dd"), rdoNam.isSelected(), lblAnh.getToolTipText(), "F1C232", rdoCNH.isSelected(), true);
    }

    void chonAnh() {
        JFileChooser jfc = new JFileChooser("logos");
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = jfc.getSelectedFile();
            Ximage.save(file);
            lblAnh.setToolTipText(file.getName());
            lblAnh.setIcon(Ximage.read(file.getName(), lblAnh.getWidth() - 4, lblAnh.getHeight() - 4));
        }
    }

    void chiTiet() {
        row = tblNhanVien.getSelectedRow();
        setForm(daoNV.selectById(tblNhanVien.getValueAt(row, 0).toString()));
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    boolean checkValidate() {
        String pEmail = "^[A-Za-z0-9]+[A-Za-z0-9]*+@fpt.edu.vn$";
        String pSDT = "^0[0-9]{9}$";
        String s = txtTen.getText().replaceAll("[^0-9]", "");
        String pKiTu = txtTen.getText().replaceAll("[^!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]", "");
        if (txtMa.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập mã nhân viên!");
            txtMa.requestFocus();
            return false;
        } else if (txtMa.getText().trim().length() > 10) {
            MsgBox.alert(this, "Mã nhân viên chứa tối đa 10 kí tự!");
            txtMa.requestFocus();
            return false;
        } else if (txtTen.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập tên nhân viên!");
            txtTen.requestFocus();
            return false;
        } else if ((txtTen.getText().trim().matches(s))) {
            MsgBox.alert(this, "Tên nhân viên phải chứa kí tự chữ");
            txtTen.requestFocus();
            return false;
        } else if ((txtTen.getText().trim().matches(pKiTu))) {
            MsgBox.alert(this, "Tên nhân viên không được chứa kí tự đặc biệt");
            txtTen.requestFocus();
            return false;
        } else if (!(txtTen.getText().trim().length() >= 6 && txtTen.getText().trim().length() <= 30)) {
            MsgBox.alert(this, "Họ tên nhân viên chứa từ 6 đến 30 kí tự");
            txtTen.requestFocus();
            return false;
        } else if (txtNgaySinh.getDate() == null) {
            MsgBox.alert(this, "Vui lòng nhập ngày sinh nhân viên!");;
            return false;
        } else if (txtSdt.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập số điện thoại nhân viên!");
            txtSdt.requestFocus();
            return false;
        } else if (!txtSdt.getText().trim().matches(pSDT)) {
            MsgBox.alert(this, "SDT không chính xác!");
            txtSdt.requestFocus();
            return false;
        } else if (txtEmail.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập email nhân viên!");
            txtEmail.requestFocus();
            return false;
        } else if (!txtEmail.getText().trim().matches(pEmail)) {
            MsgBox.alert(this, "Email phải có định dạng @fpt.edu.vn!");
            txtEmail.requestFocus();
            return false;
        } else if (txtDiaChi.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập địa chỉ nhân viên!");
            txtDiaChi.requestFocus();
            return false;
        } else if (!(txtDiaChi.getText().trim().length() >= 4 && txtDiaChi.getText().trim().length() <= 225)) {
            MsgBox.alert(this, "Địa chỉ nhân viên chứa từ 4 đến 255 kí tự");
            txtTen.requestFocus();
            return false;
        } else if (lblAnh.getToolTipText() == null) {
            MsgBox.alert(this, "Vui lòng chọn ảnh nhân viên!");
            return false;
        }
        return true;
    }

    void insert() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền thêm nhân viên");
        } else {
            if (checkValidate()) {
                NhanVien nv = getForm();
                if (!(daoNV.selectById(nv.getMaNV()) == null)) {
                    MsgBox.alert(this, "Mã nhân viên đã tồn tại!");
                    return;
                } else {
                    try {
                        daoNV.insert(nv);
                        pageIndexDS = 0;
                        pageIndexLT = 0;
                        fillToDanhSach();
                        fillToLuuTru();
                        Xmail.sendPassword(txtMa.getText().trim(), passWord, txtEmail.getText().trim());
                        MsgBox.alert(this, "Thêm nhân viên mới thành công. Tài khoản đã được gửi tới email: " + txtEmail.getText().trim());
                        resetForm();
                    } catch (Exception e) {
                        if (MsgBox.confirm(this, "Thêm nhân viên thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                            Xmail.writeException(e, getForm());
                            Xmail.sendBugs("thaidpph17321@fpt.edu.vn");
                        }
                    }
                }
            }
        }
    }

    void update() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền sửa nhân viên!");
        } else {
            if (checkValidate()) {
                try {
                    daoNV.update(getForm());
                    pageIndexDS = 0;
                    pageIndexLT = 0;
                    fillToDanhSach();
                    fillToLuuTru();
                    MsgBox.alert(this, "Chỉnh sửa nhân viên thành công");
                    resetForm();
                } catch (Exception e) {
                    if (MsgBox.confirm(this, "Chỉnh sửa nhân viên thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                        Xmail.writeException(e, getForm());
                        Xmail.sendBugs("thaidpph17321@fpt.edu.vn");
                    }
                }
            }
        }
    }

    void delete() {
        if (!Auth.isManager()) {
            MsgBox.alert(this, "Bạn không có quyền xóa nhân viên");
        } else {
            if (MsgBox.confirm(this, "Bạn có chắc chắn muốn xoá nhân viên này?")) {
                try {
                    daoNV.delete(txtMa.getText().trim());
                    pageIndexDS = 0;
                    pageIndexLT = 0;
                    fillToDanhSach();
                    fillToLuuTru();
                    MsgBox.alert(this, "Xoá nhân viên thành công");
                    resetForm();
                } catch (Exception e) {
                    if (MsgBox.confirm(this, "Xoá nhân viên thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                        Xmail.writeException(e, getForm());
                        Xmail.sendBugs("thaidpph17321@fpt.edu.vn");
                    }
                }
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup1 = new javax.swing.ButtonGroup();
        pna = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        txtDiaChi = new javax.swing.JTextField();
        lblAnh = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        rdoCNH = new javax.swing.JRadioButton();
        rdoNV = new javax.swing.JRadioButton();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        btnMoi = new javax.swing.JButton();
        pnlB5 = new javax.swing.JLabel();
        pn = new javax.swing.JPanel();
        pnlB4 = new javax.swing.JPanel();
        txtTimDS = new javax.swing.JTextField();
        cboDS = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        pnlB3 = new javax.swing.JLabel();
        btnPreDS = new javax.swing.JButton();
        lblPageIndexDS = new javax.swing.JLabel();
        btnNextDS = new javax.swing.JButton();
        btnChitiet = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        pnlB1 = new javax.swing.JPanel();
        txtTimLT = new javax.swing.JTextField();
        cboLT = new javax.swing.JComboBox<>();
        btnKhoiPhuc = new javax.swing.JButton();
        btnPreLT = new javax.swing.JButton();
        btnNextLT = new javax.swing.JButton();
        lblPageIndexLT = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLuuTru = new javax.swing.JTable();
        pnlB2 = new javax.swing.JLabel();

        setClosable(true);

        tabs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã nhân viên");

        txtMa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Họ tên");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Ngày sinh");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Giới tính");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("SĐT");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Email");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Địa chỉ");

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        btnThem.setBackground(new java.awt.Color(0, 51, 153));
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 51, 153));
        btnSua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 51, 153));
        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        txtTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtSdt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Vai trò");

        buttonGroup2.add(rdoCNH);
        rdoCNH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoCNH.setSelected(true);
        rdoCNH.setText("Chủ nhà hàng");

        buttonGroup2.add(rdoNV);
        rdoNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNV.setText("Nhân viên");

        btnMoi.setBackground(new java.awt.Color(0, 51, 153));
        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("Mới");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(20, 20, 20)
                                    .addComponent(txtMa))
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel5))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(39, 39, 39)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtEmail)
                                                .addComponent(txtDiaChi)
                                                .addComponent(txtSdt, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                            .addComponent(rdoNam)
                                                            .addGap(30, 30, 30)
                                                            .addComponent(rdoNu))
                                                        .addComponent(rdoCNH))
                                                    .addGap(0, 227, Short.MAX_VALUE))))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addGap(41, 41, 41)
                                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txtTen)
                                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(182, 182, 182)
                                .addComponent(rdoNV)
                                .addGap(139, 139, 139)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnThem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnMoi, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                        .addGap(16, 16, 16)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(rdoNam)
                                .addComponent(rdoNu, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblAnh, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoCNH)
                            .addComponent(rdoNV)
                            .addComponent(jLabel10)))
                    .addComponent(btnSua)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnMoi)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnThem)
                            .addComponent(btnXoa))))
                .addContainerGap(131, Short.MAX_VALUE))
        );

        pnlB5.setBackground(new java.awt.Color(241, 194, 50));
        pnlB5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        pnlB5.setForeground(new java.awt.Color(0, 51, 153));
        pnlB5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlB5.setText("QUẢN LÝ NHÂN VIÊN");
        pnlB5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlB5.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
            .addComponent(pnlB5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(pnlB5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabs.addTab("Thông tin", jPanel5);

        pnlB4.setBackground(new java.awt.Color(255, 217, 102));

        txtTimDS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimDSKeyReleased(evt);
            }
        });

        cboDS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboDS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboDS.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboDSItemStateChanged(evt);
            }
        });
        cboDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlB4Layout = new javax.swing.GroupLayout(pnlB4);
        pnlB4.setLayout(pnlB4Layout);
        pnlB4Layout.setHorizontalGroup(
            pnlB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlB4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimDS, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(cboDS, 0, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlB4Layout.setVerticalGroup(
            pnlB4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlB4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimDS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboDS, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblNhanVien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nv", "Tên nv", "Ngày sinh", "Giới tính", "Địa chỉ", "SDT", "Email", "Ảnh", "Vai trò"
            }
        ));
        tblNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhanVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhanVien);

        pnlB3.setBackground(new java.awt.Color(241, 194, 50));
        pnlB3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        pnlB3.setForeground(new java.awt.Color(0, 51, 153));
        pnlB3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlB3.setText("QUẢN LÝ NHÂN VIÊN");
        pnlB3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlB3.setOpaque(true);

        btnPreDS.setBackground(new java.awt.Color(0, 51, 153));
        btnPreDS.setForeground(new java.awt.Color(255, 255, 255));
        btnPreDS.setText("<<");
        btnPreDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreDSActionPerformed(evt);
            }
        });

        lblPageIndexDS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPageIndexDS.setText("1");
        lblPageIndexDS.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnNextDS.setBackground(new java.awt.Color(0, 51, 153));
        btnNextDS.setForeground(new java.awt.Color(255, 255, 255));
        btnNextDS.setText(">>");
        btnNextDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextDSActionPerformed(evt);
            }
        });

        btnChitiet.setBackground(new java.awt.Color(0, 51, 153));
        btnChitiet.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnChitiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChitiet.setText("Xem chi tiết");
        btnChitiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChitietActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnLayout = new javax.swing.GroupLayout(pn);
        pn.setLayout(pnLayout);
        pnLayout.setHorizontalGroup(
            pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLayout.createSequentialGroup()
                .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlB3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnLayout.createSequentialGroup()
                        .addComponent(pnlB4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                            .addGroup(pnLayout.createSequentialGroup()
                                .addComponent(btnPreDS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPageIndexDS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNextDS)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnChitiet, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnLayout.setVerticalGroup(
            pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnLayout.createSequentialGroup()
                .addComponent(pnlB3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreDS)
                            .addComponent(lblPageIndexDS)
                            .addComponent(btnNextDS)
                            .addComponent(btnChitiet))
                        .addGap(0, 54, Short.MAX_VALUE))
                    .addComponent(pnlB4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabs.addTab("Danh sách", pn);

        pnlB1.setBackground(new java.awt.Color(255, 217, 102));

        txtTimLT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimLTKeyReleased(evt);
            }
        });

        cboLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));
        cboLT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLTItemStateChanged(evt);
            }
        });
        cboLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlB1Layout = new javax.swing.GroupLayout(pnlB1);
        pnlB1.setLayout(pnlB1Layout);
        pnlB1Layout.setHorizontalGroup(
            pnlB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlB1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimLT, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(cboLT, javax.swing.GroupLayout.Alignment.TRAILING, 0, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlB1Layout.setVerticalGroup(
            pnlB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlB1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimLT, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnKhoiPhuc.setBackground(new java.awt.Color(0, 51, 153));
        btnKhoiPhuc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setText("Khôi phục");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        btnPreLT.setBackground(new java.awt.Color(0, 51, 153));
        btnPreLT.setForeground(new java.awt.Color(255, 255, 255));
        btnPreLT.setText("<<");
        btnPreLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreLTActionPerformed(evt);
            }
        });

        btnNextLT.setBackground(new java.awt.Color(0, 51, 153));
        btnNextLT.setForeground(new java.awt.Color(255, 255, 255));
        btnNextLT.setText(">>");
        btnNextLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextLTActionPerformed(evt);
            }
        });

        lblPageIndexLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblPageIndexLT.setText("1");

        tblLuuTru.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLuuTru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã nv", "Tên nv", "Ngày sinh", "Giới tính", "Địa chỉ", "SDT", "Email", "Ảnh", "Vai trò"
            }
        ));
        jScrollPane3.setViewportView(tblLuuTru);

        pnlB2.setBackground(new java.awt.Color(241, 194, 50));
        pnlB2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        pnlB2.setForeground(new java.awt.Color(0, 51, 153));
        pnlB2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlB2.setText("QUẢN LÝ NHÂN VIÊN");
        pnlB2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pnlB2.setOpaque(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(pnlB1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(btnPreLT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblPageIndexLT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNextLT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlB2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(pnlB2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 409, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnPreLT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblPageIndexLT)
                                .addComponent(btnNextLT)))
                        .addGap(0, 55, Short.MAX_VALUE))))
        );

        tabs.addTab("Lưu trữ", jPanel7);

        javax.swing.GroupLayout pnaLayout = new javax.swing.GroupLayout(pna);
        pna.setLayout(pnaLayout);
        pnaLayout.setHorizontalGroup(
            pnaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnaLayout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 777, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );
        pnaLayout.setVerticalGroup(
            pnaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblAnhMouseClicked

    private void txtTimDSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimDSKeyReleased
        pageIndexDS = 0;
        fillToDanhSach();
    }//GEN-LAST:event_txtTimDSKeyReleased

    private void cboDSItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboDSItemStateChanged
        if (cboDS.getItemCount() > 1) {
            pageIndexDS = 0;
            fillToDanhSach();
        }
    }//GEN-LAST:event_cboDSItemStateChanged

    private void cboDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDSActionPerformed

    private void tblNhanVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhanVienMouseClicked
        if (evt.getClickCount() == 2) {
            chiTiet();
        }
    }//GEN-LAST:event_tblNhanVienMouseClicked

    private void btnPreDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreDSActionPerformed
        pageIndexDS--;
        fillToDanhSach();
    }//GEN-LAST:event_btnPreDSActionPerformed

    private void btnNextDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDSActionPerformed
        pageIndexDS++;
        fillToDanhSach();
    }//GEN-LAST:event_btnNextDSActionPerformed

    private void btnChitietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChitietActionPerformed
        if (tblNhanVien.getSelectedRow() >= 0) {
            chiTiet();
        } else {
            MsgBox.alert(this, "Vui lòng chọn nhân viên cần xem chi tiết!");
        }
    }//GEN-LAST:event_btnChitietActionPerformed

    private void txtTimLTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimLTKeyReleased
        pageIndexLT = 0;
        fillToLuuTru();
    }//GEN-LAST:event_txtTimLTKeyReleased

    private void cboLTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLTItemStateChanged
        if (cboLT.getItemCount() > 1) {
            pageIndexLT = 0;
            fillToLuuTru();
        }
    }//GEN-LAST:event_cboLTItemStateChanged

    private void cboLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboLTActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        khoiPhuc();
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnPreLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreLTActionPerformed
        pageIndexLT--;
        fillToLuuTru();
    }//GEN-LAST:event_btnPreLTActionPerformed

    private void btnNextLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextLTActionPerformed
        pageIndexLT++;
        fillToLuuTru();
    }//GEN-LAST:event_btnNextLTActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        resetForm();
    }//GEN-LAST:event_btnMoiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChitiet;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNextDS;
    private javax.swing.JButton btnNextLT;
    private javax.swing.JButton btnPreDS;
    private javax.swing.JButton btnPreLT;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboDS;
    private javax.swing.JComboBox<String> cboLT;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblPageIndexDS;
    private javax.swing.JLabel lblPageIndexLT;
    private javax.swing.JPanel pn;
    private javax.swing.JPanel pna;
    private javax.swing.JPanel pnlB1;
    private javax.swing.JLabel pnlB2;
    private javax.swing.JLabel pnlB3;
    private javax.swing.JPanel pnlB4;
    private javax.swing.JLabel pnlB5;
    private javax.swing.JRadioButton rdoCNH;
    private javax.swing.JRadioButton rdoNV;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblLuuTru;
    private javax.swing.JTable tblNhanVien;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMa;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimDS;
    private javax.swing.JTextField txtTimLT;
    // End of variables declaration//GEN-END:variables
}
