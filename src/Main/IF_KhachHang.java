/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.BanDAO;
import DAO.HoaDonChiTietDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDao;
import DAO.NhanVienDAO;
import DAO.SKKMDAO;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xcurrency;
import Untils.Xdate;
import Untils.Xmail;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_KhachHang extends javax.swing.JInternalFrame {

    /**
     * Creates new form IF_KhachHang
     */
    int TrangKH;
    int TrangLT;
    KhachHangDao daoKh;
    NhanVienDAO daonv;
    HoaDonDAO daohd;
    HoaDonChiTietDAO daohdct;
    SKKMDAO daoskkkm;
    BanDAO daoban;
    int row = -1;

    public IF_KhachHang() {
        initComponents();
        init();
    }

    void init() {
        this.setTitle("QUẢN LÝ KHÁCH HÀNG");
        setResizable(false);
        setDefaultCloseOperation(2);
        jPanel13.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel10.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel2.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel14.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        btnTimKiemLT.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        daoKh = new KhachHangDao();
        daonv = new NhanVienDAO();
        fillComboboxNhanVien();
        fillTable();
        fillTableLT();
        StatusButton();
        txtMa.setEditable(false);
    }


    void fillComboboxNhanVien() {
        DefaultComboBoxModel mol = (DefaultComboBoxModel) cbbManv.getModel();
        DefaultComboBoxModel molLT = (DefaultComboBoxModel) cbbManvLT.getModel();
        mol.removeAllElements();
        molLT.removeAllElements();
        List<NhanVien> list = daonv.selectByAll();
        mol.addElement("Tất cả");
        molLT.addElement("Tất cả");
        for (NhanVien x : list) {
            mol.addElement(x);
            molLT.addElement(x);
        }
    }

    KhachHang getForm() {
        KhachHang kh = new KhachHang();
        kh.setTenKH(txtHoTen.getText().trim());
        kh.setNgaysinh(txtNgaySinh.getDate());
        kh.setSDT(txtSDT.getText().trim());
        kh.setGioiTinh(rdoNam.isSelected());
        kh.setMaNV(Auth.user.getMaNV().trim());
        kh.setEmail(txtEmail.getText().trim());
        kh.setMaKH(Integer.parseInt(txtMa.getText()));
        return kh;
    }

    void setForm(KhachHang kh) {
        txtHoTen.setText(kh.getTenKH());
        txtEmail.setText(kh.getEmail());
        txtNgaySinh.setDate(kh.getNgaysinh());
        txtMa.setText(kh.getMaKH() + "");
        txtSDT.setText(kh.getSDT());
        if (!kh.isGioiTinh()) {
            rdoNu.setSelected(true);
        } else {
            rdoNam.setSelected(kh.isGioiTinh());
            rdoNu.setSelected(!kh.isGioiTinh());
        }
    }

    void fillTableHD(int maKH) {
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDon1.getModel();
        dtm.setRowCount(0);
        daoskkkm = new SKKMDAO();
        daoban = new BanDAO();
        daonv = new NhanVienDAO();
        List<Object[]> obj = daoKh.selectByHD(maKH);
        if (!obj.isEmpty()) {
            for (Object[] x : obj) {
            dtm.addRow(new Object[]{
                x[0],
                x[1],
                daoskkkm.selectById(x[2].toString()).getGiaTriKM(),
                daoban.selectById(x[3].toString()).getTenBan(),
                daonv.selectById(x[4].toString()).getTenNV(),
                Xcurrency.toCurrency((int) x[5])
            });
        }
        }
    }

    void fillTable() {
        DefaultTableModel dtm = (DefaultTableModel) tblKhachHang.getModel();
        dtm.setRowCount(0);
        if (cbbManv.getSelectedItem().toString().equalsIgnoreCase("Tất cả")) {
            TimKiem();
        } else {
            try {
                NhanVien nv = (NhanVien) cbbManv.getSelectedItem();
                List<KhachHang> listKh = daoKh.selectPaging(1, TrangKH, nv.getMaNV());
                lblTrangKh.setText(TrangKH + 1 + "");
                upDateStatus();
                for (KhachHang kh : listKh) {
                    Object[] rdt = new Object[]{
                        kh.getMaKH(),
                        kh.getTenKH(),
                        Xdate.toString(kh.getNgaysinh(), "yyyy-MM-dd"),
                        kh.isGioiTinh() ? "Nam" : "Nữ",
                        kh.getSDT(),
                        kh.getEmail(),
                        nv.getTenNV()
                    };
                    dtm.addRow(rdt);
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
                e.printStackTrace();
            }
        }
    }

    void fillTableLT() {
        DefaultTableModel dtm = (DefaultTableModel) tblKhachHangLT.getModel();
        dtm.setRowCount(0);
        if (cbbManvLT.getSelectedItem().toString().equalsIgnoreCase("Tất cả")) {
            TimKiemLT();
        } else {
            try {
                NhanVien nv = (NhanVien) cbbManvLT.getSelectedItem();
                List<KhachHang> listKh = daoKh.selectPaging(0, TrangLT, nv.getMaNV());
                lblTrangKhachHangLT.setText(TrangLT + 1 + "");
                upDateStatusLT();
                for (KhachHang kh : listKh) {
                    Object[] rdt = new Object[]{
                        kh.getMaKH(),
                        kh.getTenKH(),
                        Xdate.toString(kh.getNgaysinh(), "yyyy-MM-dd"),
                        kh.isGioiTinh() ? "Nam" : "Nữ",
                        kh.getSDT(),
                        kh.getEmail(),
                        nv.getTenNV(),};
                    dtm.addRow(rdt);
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
                e.printStackTrace();
            }
        }
    }

    void clearForm() {
        DefaultTableModel dtm = (DefaultTableModel) tblHoaDon1.getModel();
        dtm.setRowCount(0);
        KhachHang kh = new KhachHang();
        this.setForm(kh);
        this.row = -1;
        StatusButton();
    }

    void update() {
        if (checkValidate()) {
            KhachHang kh = getForm();
            try {
                daoKh.update(kh);
                this.fillTable();
                clearForm();
                MsgBox.alert(this, "Cap nhat thanh cong!");
            } catch (Exception e) {
                MsgBox.alert(this, "Cap nhat that bai!");
                e.printStackTrace();
            }
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn có chắc chắn muốn xoá Khách hàng này?")) {
            try {
                daoKh.delete(Integer.parseInt(txtMa.getText().trim()));
                TrangKH = 0;
                TrangLT = 0;
                fillTable();
                fillTableLT();
                MsgBox.alert(this, "Xoá khách hàng thành công");
                clearForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Xoá Khách hàng thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("bichntph17469@fpt.edu.vn");
                }
            }
        }
    }

    void insert() {
        if (checkValidate()) {
            KhachHang kh = getForm();
            if (!(daoKh.selectById(kh.getMaKH()) == null)) {
                MsgBox.alert(this, "Ma da ton tai!");
                return;
            } else {
                try {
                    daoKh.insert(kh);
                    this.fillTable();
                    this.clearForm();
                    MsgBox.alert(this, "Them moi thanh cong!");
                } catch (Exception e) {
                    MsgBox.alert(this, "them moi that bai!");
                    e.printStackTrace();
                }
            }
        }

    }

    void upDateStatus() {
        NhanVien nv = (NhanVien) cbbManv.getSelectedItem();
        boolean firstDS = TrangKH == 0;
        boolean firstLT = TrangLT == 0;
        boolean lastDS = daoKh.selectPaging(1, TrangKH + 1, nv.getMaNV()).isEmpty();
        boolean lastLT = daoKh.selectPaging(1, TrangKH + 1, nv.getMaNV()).isEmpty();
        btnlast.setEnabled(!firstDS);
        btnLastLT.setEnabled(!firstLT);
        btnNext.setEnabled(!lastDS);
        btnnextLT.setEnabled(!lastLT);
    }

    void upDateStatusLT() {
        NhanVien nv = (NhanVien) cbbManvLT.getSelectedItem();
        boolean firstDS = TrangKH == 0;
        boolean firstLT = TrangLT == 0;
        boolean lastDS = daoKh.selectPaging(1, TrangKH + 1, nv.getMaNV().trim()).isEmpty();
        boolean lastLT = daoKh.selectPaging(1, TrangKH + 1, nv.getMaNV().trim()).isEmpty();
        btnlast.setEnabled(!firstDS);
        btnLastLT.setEnabled(!firstLT);
        btnNext.setEnabled(!lastDS);
        btnnextLT.setEnabled(!lastLT);
    }

    void StatusButton() {
        boolean edit = (row >= 0);
        txtMa.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void upDateStatusTXT() {
        boolean firstDS = TrangKH == 0;
        boolean firstLT = TrangLT == 0;
        boolean lastDS = daoKh.selectPaging(1, TrangKH + 1, txtTimKiem2.getText().trim()).isEmpty();
        boolean lastLT = daoKh.selectPaging(1, TrangKH + 1, txtTimKiemLT.getText().trim()).isEmpty();
        btnlast.setEnabled(!firstDS);
        btnLastLT.setEnabled(!firstLT);
        btnNext.setEnabled(!lastDS);
        btnnextLT.setEnabled(!lastLT);
    }

    void TimKiem() {
        Map<String, String> mapNV = daonv.selectHoTenNV();
        DefaultTableModel dtm = (DefaultTableModel) tblKhachHang.getModel();
        dtm.setRowCount(0);
        try {
            String keyword = txtTimKiem2.getText().trim();
            List<KhachHang> listKh = daoKh.selectByKeyWord(keyword, 1, TrangKH);
            lblTrangKh.setText(TrangKH + 1 + "");
            upDateStatusTXT();
            for (KhachHang kh : listKh) {
                Object[] rdt = new Object[]{
                    kh.getMaKH(),
                    kh.getTenKH(),
                    Xdate.toString(kh.getNgaysinh(), "yyyy-MM-dd"),
                    kh.isGioiTinh() ? "Nam" : "Nữ",
                    kh.getSDT(),
                    kh.getEmail(),
                    mapNV.get(kh.getMaNV())
                };
                dtm.addRow(rdt);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
        }
        clearForm();
    }

    void TimKiemLT() {
        Map<String, String> mapNV = daonv.selectHoTenNV();
        DefaultTableModel dtm = (DefaultTableModel) tblKhachHangLT.getModel();
        dtm.setRowCount(0);
        try {
            String keyword = txtTimKiemLT.getText().trim();
            List<KhachHang> listKh = daoKh.selectByKeyWord(keyword, 0, TrangLT);
            lblTrangKhachHangLT.setText(TrangLT + 1 + "");
            upDateStatusTXT();
            for (KhachHang kh : listKh) {
                Object[] rdt = new Object[]{
                    kh.getMaKH(),
                    kh.getTenKH(),
                    Xdate.toString(kh.getNgaysinh(), "yyyy-MM-dd"),
                    kh.isGioiTinh() ? "Nam" : "Nữ",
                    kh.getSDT(),
                    kh.getEmail(),
                    mapNV.get(kh.getMaNV())};
                dtm.addRow(rdt);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
        }
        clearForm();
    }

    void khoiPhuc() {
        if (tblKhachHangLT.getSelectedRow() >= 0) {
            if (MsgBox.confirm(this, "Bạn có chắc muốn khôi phục khách hàng này?")) {
                KhachHang kh = daoKh.selectById(Integer.parseInt(tblKhachHangLT.getValueAt(tblKhachHangLT.getSelectedRow(), 0).toString()));
                kh.setTrangThai(true);
                daoKh.update(kh);
                TrangKH = 0;
                TrangLT = 0;
                fillTable();
                fillTableLT();
                MsgBox.alert(this, "Khôi phục thành công");
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn nhân viên cần khách hàng!");
        }
    }

    boolean checkValidate() {
        String pEmail = "^[A-Za-z0-9]+[A-Za-z0-9]*+@fpt.edu.vn$";
        String pSDT = "^0[0-9]{9}$";
        String s = txtHoTen.getText().replaceAll("[^0-9]", "");
        String pKiTu = txtHoTen.getText().replaceAll("[^!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]", "");
        if (txtHoTen.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập tên khách hàng!");
            txtHoTen.requestFocus();
            return false;
        } else if ((txtHoTen.getText().trim().matches(s))) {
            MsgBox.alert(this, "Tên khách hàng phải chứa kí tự chữ");
            txtHoTen.requestFocus();
            return false;
        } else if ((txtHoTen.getText().trim().matches(pKiTu))) {
            MsgBox.alert(this, "Tên khách hàng không được chứa kí tự đặc biệt");
            txtHoTen.requestFocus();
            return false;
        } else if (!(txtHoTen.getText().trim().length() >= 6 && txtHoTen.getText().trim().length() <= 50)) {
            MsgBox.alert(this, "Họ tên khách hàng chứa từ 6 đến 50 kí tự");
            txtHoTen.requestFocus();
            return false;
        } else if (txtNgaySinh.getDate() == null) {
            MsgBox.alert(this, "Vui lòng nhập ngày sinh khách hàng!");;
            return false;
        } else if (txtSDT.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập số điện thoại khách hàng!");
            txtSDT.requestFocus();
            return false;
        } else if (!txtSDT.getText().trim().matches(pSDT)) {
            MsgBox.alert(this, "SDT không chính xác!");
            txtSDT.requestFocus();
            return false;
        } else if (txtEmail.getText().trim().length() > 0) {
            if (!txtEmail.getText().trim().matches(pEmail)) {
                MsgBox.alert(this, "Email phải có định dạng A@fpt.edu.vn!");
                txtSDT.requestFocus();
                return false;
            } else {
                return true;
            }
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        tabs = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTimKiem2 = new javax.swing.JTextField();
        cbbManv = new javax.swing.JComboBox<>();
        btnTimKiem2 = new javax.swing.JButton();
        rdoTatCa = new javax.swing.JRadioButton();
        rdoKhachMoi = new javax.swing.JRadioButton();
        rdoKhachQuen = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnlast = new javax.swing.JButton();
        lblTrangKh = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        btnMoi = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtEmail = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        txtMa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtNgaySinh = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHoaDon1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnTimKiemLT = new javax.swing.JPanel();
        txtTimKiemLT = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        cbbManvLT = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        rdoTatCaLT = new javax.swing.JRadioButton();
        rdoKhachMoiLT = new javax.swing.JRadioButton();
        rdoKhachQuenLT = new javax.swing.JRadioButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnLastLT = new javax.swing.JButton();
        btnnextLT = new javax.swing.JButton();
        lblTrangKhachHangLT = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHangLT = new javax.swing.JTable();
        btnKhoiPhuc = new javax.swing.JButton();

        setClosable(true);

        tabs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 217, 102));

        cbbManv.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbbManv.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbbManvItemStateChanged(evt);
            }
        });
        cbbManv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbManvActionPerformed(evt);
            }
        });

        btnTimKiem2.setBackground(new java.awt.Color(0, 51, 153));
        btnTimKiem2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnTimKiem2.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem2.setText("TÌM KIẾM");
        btnTimKiem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnTimKiem2MouseClicked(evt);
            }
        });
        btnTimKiem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiem2ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoTatCa);
        rdoTatCa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTatCa.setText("Tất cả");
        rdoTatCa.setOpaque(false);
        rdoTatCa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoKhachMoi);
        rdoKhachMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKhachMoi.setText("Khách mới");
        rdoKhachMoi.setOpaque(false);

        buttonGroup1.add(rdoKhachQuen);
        rdoKhachQuen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKhachQuen.setSelected(true);
        rdoKhachQuen.setText("Khách quen");
        rdoKhachQuen.setOpaque(false);
        rdoKhachQuen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhachQuenActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Trạng thái");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoTatCa)
                            .addComponent(rdoKhachMoi)
                            .addComponent(rdoKhachQuen)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTimKiem2)
                    .addComponent(cbbManv, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTimKiem2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTimKiem2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(cbbManv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoKhachQuen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoKhachMoi)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoTatCa)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(241, 194, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 153));
        jLabel13.setText("QUẢN LÝ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnlast.setBackground(new java.awt.Color(0, 51, 153));
        btnlast.setForeground(new java.awt.Color(255, 255, 255));
        btnlast.setText("<<");
        btnlast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlastActionPerformed(evt);
            }
        });

        lblTrangKh.setText("1");

        btnNext.setBackground(new java.awt.Color(0, 51, 153));
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        tblKhachHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "Ngày sinh", "Giới tính", "SĐT", "Email", "Tên NV"
            }
        ));
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnlast)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTrangKh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext)
                        .addGap(49, 677, Short.MAX_VALUE))))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnlast)
                            .addComponent(lblTrangKh)
                            .addComponent(btnNext))
                        .addGap(12, 12, 12))
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabs.addTab("Danh sách", jPanel6);

        jPanel5.setBackground(new java.awt.Color(238, 238, 238));

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

        buttonGroup2.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNam.setSelected(true);
        rdoNam.setText("Nam");

        buttonGroup2.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");

        btnMoi.setBackground(new java.awt.Color(0, 51, 153));
        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 51, 153));
        btnSua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("SỬA");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 51, 153));
        btnXoa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("XÓA");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtHoTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnThem.setBackground(new java.awt.Color(0, 51, 153));
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtMa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Mã khách hàng");

        tblHoaDon1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã HD", "Ngày tạo", "Khuyến mãi", "Bàn", "Tên nhân viên", "Tổng tiền"
            }
        ));
        jScrollPane4.setViewportView(tblHoaDon1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(44, 44, 44))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(70, 70, 70)
                                .addComponent(rdoNu))
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMa, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnMoi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)
                            .addComponent(jLabel5))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(241, 194, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("QUẢN LÝ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Thông tin", jPanel5);

        btnTimKiemLT.setBackground(new java.awt.Color(255, 217, 102));

        jButton6.setBackground(new java.awt.Color(0, 51, 153));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("TÌM KIẾM");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        cbbManvLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbbManvLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbManvLTActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Trạng thái");

        buttonGroup3.add(rdoTatCaLT);
        rdoTatCaLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTatCaLT.setText("Tất cả");
        rdoTatCaLT.setOpaque(false);
        rdoTatCaLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTatCaLTActionPerformed(evt);
            }
        });

        buttonGroup3.add(rdoKhachMoiLT);
        rdoKhachMoiLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKhachMoiLT.setText("Khách mới");
        rdoKhachMoiLT.setOpaque(false);

        buttonGroup3.add(rdoKhachQuenLT);
        rdoKhachQuenLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKhachQuenLT.setSelected(true);
        rdoKhachQuenLT.setText("Khách quen");
        rdoKhachQuenLT.setOpaque(false);
        rdoKhachQuenLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKhachQuenLTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnTimKiemLTLayout = new javax.swing.GroupLayout(btnTimKiemLT);
        btnTimKiemLT.setLayout(btnTimKiemLTLayout);
        btnTimKiemLTLayout.setHorizontalGroup(
            btnTimKiemLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTimKiemLTLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnTimKiemLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnTimKiemLTLayout.createSequentialGroup()
                        .addGroup(btnTimKiemLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoTatCaLT)
                            .addComponent(rdoKhachMoiLT)
                            .addComponent(rdoKhachQuenLT)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtTimKiemLT)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addComponent(cbbManvLT, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        btnTimKiemLTLayout.setVerticalGroup(
            btnTimKiemLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnTimKiemLTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemLT, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbbManvLT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoKhachQuenLT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoKhachMoiLT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoTatCaLT)
                .addContainerGap(253, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(241, 194, 50));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 153));
        jLabel14.setText("QUẢN LÝ KHÁCH HÀNG");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnLastLT.setBackground(new java.awt.Color(0, 51, 153));
        btnLastLT.setForeground(new java.awt.Color(255, 255, 255));
        btnLastLT.setText("<<");
        btnLastLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastLTActionPerformed(evt);
            }
        });

        btnnextLT.setBackground(new java.awt.Color(0, 51, 153));
        btnnextLT.setForeground(new java.awt.Color(255, 255, 255));
        btnnextLT.setText(">>");
        btnnextLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnextLTActionPerformed(evt);
            }
        });

        lblTrangKhachHangLT.setText("1");

        tblKhachHangLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblKhachHangLT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã KH", "Tên KH", "Ngày sinh", "Giới tính", "SĐT", "Email", "Tên NV"
            }
        ));
        jScrollPane3.setViewportView(tblKhachHangLT);

        btnKhoiPhuc.setBackground(new java.awt.Color(0, 51, 153));
        btnKhoiPhuc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setText("KHÔI PHỤC");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(btnTimKiemLT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnLastLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTrangKhachHangLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnnextLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 801, Short.MAX_VALUE)))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnTimKiemLT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLastLT)
                            .addComponent(lblTrangKhachHangLT)
                            .addComponent(btnnextLT)
                            .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        tabs.addTab("Lưu trữ", jPanel7);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        this.khoiPhuc();
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnnextLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextLTActionPerformed
        TrangLT++;
        fillTableLT();
    }//GEN-LAST:event_btnnextLTActionPerformed

    private void btnLastLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastLTActionPerformed
        TrangLT--;
        fillTableLT();
    }//GEN-LAST:event_btnLastLTActionPerformed

    private void rdoKhachQuenLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhachQuenLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKhachQuenLTActionPerformed

    private void rdoTatCaLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoTatCaLTActionPerformed

    private void cbbManvLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbManvLTActionPerformed
        int index = cbbManvLT.getSelectedIndex();
        if (index >= 0) {
            TrangKH = 0;
            fillTableLT();
        }
    }//GEN-LAST:event_cbbManvLTActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        TrangLT = 0;
        this.TimKiemLT();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        this.insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        this.delete();
        tabs.setSelectedIndex(2);
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        this.update();
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        this.clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblKhachHang.getSelectedRow();
            int makh = (Integer) tblKhachHang.getValueAt(this.row, 0);
            KhachHang kh = daoKh.selectById(makh);
            this.setForm(kh);
            fillTableHD(kh.getMaKH());
            tabs.setSelectedIndex(1);
            this.StatusButton();
        }
    }//GEN-LAST:event_tblKhachHangMouseClicked

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        TrangKH++;
        fillTable();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed
        TrangKH--;
        fillTable();
    }//GEN-LAST:event_btnlastActionPerformed

    private void rdoKhachQuenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKhachQuenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKhachQuenActionPerformed

    private void rdoTatCaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTatCaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoTatCaActionPerformed

    private void btnTimKiem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiem2ActionPerformed
        TrangKH = 0;
        this.TimKiem();
    }//GEN-LAST:event_btnTimKiem2ActionPerformed

    private void btnTimKiem2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiem2MouseClicked

    }//GEN-LAST:event_btnTimKiem2MouseClicked

    private void cbbManvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbManvActionPerformed
        int index = cbbManv.getSelectedIndex();
        if (index > -1) {
            this.fillTable();
        }
    }//GEN-LAST:event_cbbManvActionPerformed

    private void cbbManvItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbbManvItemStateChanged

    }//GEN-LAST:event_cbbManvItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnLastLT;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiem2;
    private javax.swing.JPanel btnTimKiemLT;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnlast;
    private javax.swing.JButton btnnextLT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cbbManv;
    private javax.swing.JComboBox<String> cbbManvLT;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblTrangKh;
    private javax.swing.JLabel lblTrangKhachHangLT;
    private javax.swing.JRadioButton rdoKhachMoi;
    private javax.swing.JRadioButton rdoKhachMoiLT;
    private javax.swing.JRadioButton rdoKhachQuen;
    private javax.swing.JRadioButton rdoKhachQuenLT;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JRadioButton rdoTatCa;
    private javax.swing.JRadioButton rdoTatCaLT;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblHoaDon1;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblKhachHangLT;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMa;
    private com.toedter.calendar.JDateChooser txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTimKiem2;
    private javax.swing.JTextField txtTimKiemLT;
    // End of variables declaration//GEN-END:variables
}
