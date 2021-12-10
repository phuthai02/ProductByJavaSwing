/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.NhanVienDAO;
import DAO.SKKMDAO;
import Entity.SuKienKhuyenMai;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xdate;
import Untils.Ximage;
import Untils.Xmail;
import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_SKKM extends javax.swing.JInternalFrame {

    int row = -1;
    DefaultTableModel modelDS;
    DefaultTableModel modelLT;
    SKKMDAO daoSK;
    int pageIndexDS;
    int pageIndexLT;

    /**
     * Creates new form IF_SKKM
     */
    public IF_SKKM() {
        initComponents();
        init();
    }

    void init() {
        setResizable(false);
        setTitle("QUẢN LÝ SỰ KIỆN KHUYẾN MÃI");
        setDefaultCloseOperation(2);
        jPanel13.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel10.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel2.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel14.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel11.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        daoSK = new SKKMDAO();
        prepareGUI();
        tabs.setSelectedIndex(0);
        modelDS = (DefaultTableModel) tblDanhSach.getModel();
        modelLT = (DefaultTableModel) tblLuuTru.getModel();
        SuKienKhuyenMai sk = new SuKienKhuyenMai();
        sk.setNgayTao(Xdate.now());
        setForm(sk);
        txtNgayTao.setEnabled(false);
    }

    void prepareGUI() {

        String h[] = {"Mã SKKM", "Tên SKKM", "Giá trị KM", "Ngày bắt đầu", "Ngày Kết Thúc", "Ngày Tạo", "Mã NV"};
        modelDS = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        modelLT = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        tblDanhSach.setModel(modelDS);
        fillTableDS();
        tblLuuTru.setModel(modelLT);
        fillTableLT();
        updateStatusPage();

    }

    void fillTableDS() {
        modelDS.setRowCount(0);
        Map<String, String> mapNV = new NhanVienDAO().selectHoTenNV();
        List<SuKienKhuyenMai> list = daoSK.selectPagingFull(getSuKien(), 1, txtTimKiemDS.getText().trim(), Xdate.toString(new Date(), "yyyy-MM-dd"), pageIndexDS);
        lblIndexDS.setText(pageIndexDS + 1 + "");
        for (SuKienKhuyenMai sk : list) {
            modelDS.addRow(new Object[]{
                sk.getMaSKKM(),
                sk.getTenSKKM(),
                sk.getGiaTriKM(),
                sk.getNgayBatDau(),
                sk.getNgayKetThuc(),
                sk.getNgayTao(),
                sk.getMaNV()});

        }
    }

    void fillTableLT() {
        modelLT.setRowCount(0);
        Map<String, String> mapNV = new NhanVienDAO().selectHoTenNV();
        List<SuKienKhuyenMai> list = daoSK.selectPagingFull(getSuKienLT(), 0, txtTimKiemLT.getText().trim(), Xdate.toString(new Date(), "yyyy-MM-dd"), pageIndexLT);
        lblIndexLT.setText(pageIndexLT + 1 + "");
        for (SuKienKhuyenMai sk : list) {
            modelLT.addRow(new Object[]{
                sk.getMaSKKM(),
                sk.getTenSKKM(),
                sk.getGiaTriKM(),
                sk.getNgayBatDau(),
                sk.getNgayKetThuc(),
                sk.getNgayTao(),
                sk.getMaNV()});

        }
    }

    int getSuKien() {
        if (rdoDangDienRa.isSelected()) {
            return 0;
        } else if (rdoSapDienRa.isSelected()) {
            return 1;
        } else if (rdoKetThuc.isSelected()) {
            return 2;
        }
        return 0;
    }

    int getSuKienLT() {
        if (rdoDangDienRaLT.isSelected()) {
            return 0;
        } else if (rdoSapDienRaLT.isSelected()) {
            return 1;
        } else if (rdoKetThucLT.isSelected()) {
            return 2;
        }
        return 0;
    }

    void updateStatusPage() {
        boolean firstDS = pageIndexDS == 0;
        boolean firstLT = pageIndexLT == 0;
        boolean lastDS = daoSK.selectPagingFull(getSuKien(), 1, txtTimKiemDS.getText().trim(), Xdate.toString(new Date(), "yyyy-MM-dd"), pageIndexDS + 1).isEmpty();
        boolean lastLT = daoSK.selectPagingFull(getSuKienLT(), 1, txtTimKiemLT.getText().trim(), Xdate.toString(new Date(), "yyyy-MM-dd"), pageIndexLT + 1).isEmpty();
        btnPreDS.setEnabled(!firstDS);
        btnNextDS.setEnabled(!lastDS);
        btnPreLT.setEnabled(!firstLT);
        btnNextLT.setEnabled(!lastLT);
    }

    void setForm(SuKienKhuyenMai sk) {
        txtMaSKKM.setText(sk.getMaSKKM() + "");
        txtTenSKKM.setText(sk.getTenSKKM());
        txtGiaTriKM.setText(sk.getGiaTriKM() + "");
        txtNgayBatDau.setDate(sk.getNgayBatDau());
        txtNgayKetThuc.setDate(sk.getNgayKetThuc());
        txtNgayTao.setDate(sk.getNgayTao());
    }

    SuKienKhuyenMai getForm() {
        SuKienKhuyenMai sk = new SuKienKhuyenMai();
        if (txtMaSKKM.getText().trim().length() == 0) {
            txtMaSKKM.setText("0");
        }
        sk.setMaSKKM(Integer.parseInt(txtMaSKKM.getText().trim()));
        sk.setTenSKKM(txtTenSKKM.getText().trim());
        sk.setGiaTriKM(Double.parseDouble(txtGiaTriKM.getText().trim()));
        sk.setNgayBatDau(txtNgayBatDau.getDate());
        sk.setNgayKetThuc(txtNgayKetThuc.getDate());
        sk.setNgayTao(txtNgayTao.getDate());
        sk.setMaNV(Auth.user.getMaNV());
        return sk;
    }

    void statusButton() {
        boolean edit = (row >= 0);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void clearForm() {
        SuKienKhuyenMai sk = new SuKienKhuyenMai();
        sk.setNgayTao(Xdate.now());
        this.setForm(sk);
        txtNgayTao.setEnabled(false);
        this.row = -1;
        statusButton();
    }

    boolean checkValidate() {
        if (txtTenSKKM.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập tên sự kiện!");
            txtTenSKKM.requestFocus();
            return false;
        }
        if (txtTenSKKM.getText().trim().length() > 30) {
            MsgBox.alert(this, "Tên sự kiện nhỏ hơn 30 kí tự!");
            txtTenSKKM.requestFocus();
            return false;
        }
        if (txtGiaTriKM.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập giá trị khuyến mãi!");
            txtGiaTriKM.requestFocus();
            return false;
        } else {
            try {
                if (Double.parseDouble(txtGiaTriKM.getText().trim()) <= 0) {
                    MsgBox.alert(this, "Vui lòng nhập giá trị khuyến mãi lớn hơn 0!");
                    txtGiaTriKM.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Vui lòng nhập vào số!");
                txtGiaTriKM.requestFocus();
                return false;
            }
        }
        int ngayBD;
        int ngayKT;
        if (txtNgayBatDau.getDate() == null) {
            MsgBox.alert(this, "Vui lòng chọn ngày bắt đầu");
            return false;
        } 
        if (txtNgayKetThuc.getDate() == null) {           
            MsgBox.alert(this, "Vui lòng chọn ngày kết thúc");
            return false;
        }
        if (txtNgayBatDau.getDate() == null) {
            ngayBD = 20000101;
            MsgBox.alert(this, "Vui lòng chọn ngày bắt đầu");
        } else {
            ngayBD = Integer.parseInt(Xdate.toString(txtNgayBatDau.getDate(), "yyyyMMdd"));
        }
        if (txtNgayKetThuc.getDate() == null) {
            ngayKT = 20220101;
            MsgBox.alert(this, "Vui lòng chọn ngày kết thúc");
        } else {
            ngayKT = Integer.parseInt(Xdate.toString(txtNgayKetThuc.getDate(), "yyyyMMdd"));
        }
        if (ngayBD < Integer.parseInt(Xdate.toString(txtNgayTao.getDate(), "yyyyMMdd"))) {
            MsgBox.alert(this, "Ngày bắt đầu phải sau ngày tạo");
            return false;
        }
        if (ngayKT < Integer.parseInt(Xdate.toString(txtNgayTao.getDate(), "yyyyMMdd"))) {
            MsgBox.alert(this, "Ngày kết thúc phải sau ngày tạo");
            return false;
        }

        if (ngayKT < ngayBD) {
            MsgBox.alert(this, "Ngày kết thúc phải sau ngày bắt đầu");
            return false;
        }
        if (txtNgayBatDau.getDate() == null) {
            MsgBox.alert(this, "Vui lòng chọn ngày bắt đầu!");
            return false;
        }
        if (txtNgayKetThuc.getDate() == null) {
            MsgBox.alert(this, "Vui lòng chọn ngày kết thúc!");
            return false;
        }

        if (txtNgayTao.getDate() == null) {
            MsgBox.alert(this, "Vui lòng chọn ngày tạo!");
            return false;
        }
        return true;
    }

    void insert() {
        if (checkValidate()) {
            SuKienKhuyenMai sk = getForm();
            try {
                daoSK.insert(sk);
                this.fillTableDS();
                this.clearForm();
                MsgBox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm mới thất bại!");
                e.printStackTrace();
            }
        }

    }

    void update() {
        if (checkValidate()) {
            SuKienKhuyenMai sk = getForm();
            try {
                daoSK.update(sk);
                this.fillTableDS();
                this.fillTableLT();
                clearForm();
                MsgBox.alert(this, "Cập nhật thành công!");
                tabs.setSelectedIndex(0);
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhật thất bại!");
                e.printStackTrace();
            }
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn có chắc muốn xóa sự kiện này ?")) {
            try {
                daoSK.delete(txtMaSKKM.getText().trim());
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillTableDS();
                fillTableLT();
                MsgBox.alert(this, "Xóa thành công!");
                clearForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Xoá Khách hàng thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("duongnqph17265@fpt.edu.vn");
                }
            }
        }
    }

    void khoiPhuc() {
        if (tblLuuTru.getSelectedRow() >= 0) {
            if (MsgBox.confirm(this, "Bạn có chắc muốn khôi phục sự kiện này?")) {
                SuKienKhuyenMai sk = daoSK.selectById(tblLuuTru.getValueAt(tblLuuTru.getSelectedRow(), 0).toString());
                sk.setTrangThai(true);
                daoSK.update(sk);
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillTableDS();
                fillTableLT();
                MsgBox.alert(this, "Khôi phục thành công");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        tabDanhSach = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTimKiemDS = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        rdoDangDienRa = new javax.swing.JRadioButton();
        rdoSapDienRa = new javax.swing.JRadioButton();
        rdoKetThuc = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnPreDS = new javax.swing.JButton();
        lblIndexDS = new javax.swing.JLabel();
        btnNextDS = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDanhSach = new javax.swing.JTable();
        tabThongTin = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtTenSKKM = new javax.swing.JTextField();
        txtNgayTao = new com.toedter.calendar.JDateChooser();
        txtNgayBatDau = new com.toedter.calendar.JDateChooser();
        txtNgayKetThuc = new com.toedter.calendar.JDateChooser();
        txtGiaTriKM = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        txtMaSKKM = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tabLuuTru = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtTimKiemLT = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        rdoDangDienRaLT = new javax.swing.JRadioButton();
        rdoSapDienRaLT = new javax.swing.JRadioButton();
        rdoKetThucLT = new javax.swing.JRadioButton();
        jLabel8 = new javax.swing.JLabel();
        btnKhoiPhuc = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnPreLT = new javax.swing.JButton();
        btnNextLT = new javax.swing.JButton();
        lblIndexLT = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLuuTru = new javax.swing.JTable();

        setClosable(true);

        tabs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 217, 102));

        txtTimKiemDS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemDSKeyReleased(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Trạng thái");

        buttonGroup1.add(rdoDangDienRa);
        rdoDangDienRa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoDangDienRa.setSelected(true);
        rdoDangDienRa.setText("Đang diễn ra");
        rdoDangDienRa.setOpaque(false);
        rdoDangDienRa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDangDienRaMouseClicked(evt);
            }
        });
        rdoDangDienRa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangDienRaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoSapDienRa);
        rdoSapDienRa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoSapDienRa.setText("Sắp diễn ra");
        rdoSapDienRa.setOpaque(false);
        rdoSapDienRa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSapDienRaMouseClicked(evt);
            }
        });
        rdoSapDienRa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSapDienRaActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoKetThuc);
        rdoKetThuc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKetThuc.setText("Kết thúc");
        rdoKetThuc.setOpaque(false);
        rdoKetThuc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoKetThucMouseClicked(evt);
            }
        });
        rdoKetThuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKetThucActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Tìm kiếm");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiemDS)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoKetThuc)
                            .addComponent(rdoSapDienRa)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoDangDienRa)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 41, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemDS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoDangDienRa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoSapDienRa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoKetThuc)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(241, 194, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 153));
        jLabel13.setText("QUẢN LÝ SỰ KIỆN KHUYỄN MÃI");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnPreDS.setBackground(new java.awt.Color(0, 51, 153));
        btnPreDS.setForeground(new java.awt.Color(255, 255, 255));
        btnPreDS.setText("<<");
        btnPreDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreDSActionPerformed(evt);
            }
        });

        lblIndexDS.setText("1");

        btnNextDS.setBackground(new java.awt.Color(0, 51, 153));
        btnNextDS.setForeground(new java.awt.Color(255, 255, 255));
        btnNextDS.setText(">>");
        btnNextDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextDSActionPerformed(evt);
            }
        });

        tblDanhSach.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SKKM", "Tên SKKM", "Giá trị KM", "Ngày bắt đầu", "Ngày kết thúc", "Ngày tạo", "Mã NV", "Trạng thái"
            }
        ));
        tblDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDanhSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblDanhSach);

        javax.swing.GroupLayout tabDanhSachLayout = new javax.swing.GroupLayout(tabDanhSach);
        tabDanhSach.setLayout(tabDanhSachLayout);
        tabDanhSachLayout.setHorizontalGroup(
            tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDanhSachLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDanhSachLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDanhSachLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnPreDS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIndexDS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextDS)
                        .addGap(205, 205, 205))))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabDanhSachLayout.setVerticalGroup(
            tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabDanhSachLayout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDanhSachLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(tabDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreDS)
                            .addComponent(lblIndexDS)
                            .addComponent(btnNextDS))
                        .addContainerGap())
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabs.addTab("Danh sách", tabDanhSach);

        tabThongTin.setBackground(new java.awt.Color(238, 238, 238));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã SKKM");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên SKKM");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Giá trị KM");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Ngày bắt đầu");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Ngày kết thúc");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Ngày tạo");

        btnThem.setBackground(new java.awt.Color(0, 51, 153));
        btnThem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("THÊM");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
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

        txtTenSKKM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtGiaTriKM.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        btnMoi.setBackground(new java.awt.Color(0, 51, 153));
        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel7))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel6))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))))
                .addGap(46, 46, 46)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTenSKKM)
                    .addComponent(txtGiaTriKM)
                    .addComponent(txtMaSKKM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(80, 80, 80))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(206, Short.MAX_VALUE)
                .addComponent(btnThem)
                .addGap(45, 45, 45)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(btnXoa)
                .addGap(43, 43, 43)
                .addComponent(btnMoi)
                .addGap(128, 128, 128))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaSKKM, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtTenSKKM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtGiaTriKM, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addComponent(txtNgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgayKetThuc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)))
                .addGap(54, 54, 54)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa)
                    .addComponent(btnMoi))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(241, 194, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("QUẢN LÝ SỰ KIỆN KHUYẾN MÃI");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(139, 139, 139))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout tabThongTinLayout = new javax.swing.GroupLayout(tabThongTin);
        tabThongTin.setLayout(tabThongTinLayout);
        tabThongTinLayout.setHorizontalGroup(
            tabThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabThongTinLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(tabThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        tabThongTinLayout.setVerticalGroup(
            tabThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabThongTinLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabs.addTab("Thông tin", tabThongTin);

        jPanel11.setBackground(new java.awt.Color(255, 217, 102));

        txtTimKiemLT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemLTKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("Trạng thái");

        buttonGroup2.add(rdoDangDienRaLT);
        rdoDangDienRaLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoDangDienRaLT.setSelected(true);
        rdoDangDienRaLT.setText("Đang diễn ra");
        rdoDangDienRaLT.setOpaque(false);
        rdoDangDienRaLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDangDienRaLTMouseClicked(evt);
            }
        });
        rdoDangDienRaLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoDangDienRaLTActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoSapDienRaLT);
        rdoSapDienRaLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoSapDienRaLT.setText("Sắp diễn ra");
        rdoSapDienRaLT.setOpaque(false);
        rdoSapDienRaLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoSapDienRaLTMouseClicked(evt);
            }
        });
        rdoSapDienRaLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoSapDienRaLTActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdoKetThucLT);
        rdoKetThucLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoKetThucLT.setText("Kết thúc");
        rdoKetThucLT.setOpaque(false);
        rdoKetThucLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoKetThucLTMouseClicked(evt);
            }
        });
        rdoKetThucLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoKetThucLTActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tìm Kiếm");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiemLT)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdoKetThucLT)
                            .addComponent(rdoSapDienRaLT)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rdoDangDienRaLT)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 45, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTimKiemLT, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoDangDienRaLT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoSapDienRaLT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdoKetThucLT)
                .addContainerGap(285, Short.MAX_VALUE))
        );

        btnKhoiPhuc.setBackground(new java.awt.Color(0, 51, 153));
        btnKhoiPhuc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnKhoiPhuc.setForeground(new java.awt.Color(255, 255, 255));
        btnKhoiPhuc.setText("KHÔI PHỤC");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        jPanel14.setBackground(new java.awt.Color(241, 194, 50));

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 51, 153));
        jLabel14.setText("QUẢN LÝ SỰ KIỆN KHUYỄN MÃI");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

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

        lblIndexLT.setText("1");

        tblLuuTru.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLuuTru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã SKKM", "Tên SKKM", "Giá trị KM", "Ngày bắt đầu", "Ngày kết thúc", "Ngày tạo", "Tên NV"
            }
        ));
        jScrollPane3.setViewportView(tblLuuTru);

        javax.swing.GroupLayout tabLuuTruLayout = new javax.swing.GroupLayout(tabLuuTru);
        tabLuuTru.setLayout(tabLuuTruLayout);
        tabLuuTruLayout.setHorizontalGroup(
            tabLuuTruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabLuuTruLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(tabLuuTruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabLuuTruLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 209, Short.MAX_VALUE)
                        .addComponent(btnPreLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblIndexLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextLT)
                        .addGap(87, 87, 87)
                        .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(tabLuuTruLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3))))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        tabLuuTruLayout.setVerticalGroup(
            tabLuuTruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tabLuuTruLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabLuuTruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(tabLuuTruLayout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(tabLuuTruLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreLT)
                            .addComponent(lblIndexLT)
                            .addComponent(btnNextLT)
                            .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        tabs.addTab("Lưu trữ", tabLuuTru);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 141, Short.MAX_VALUE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemDSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemDSKeyReleased
        fillTableDS();
    }//GEN-LAST:event_txtTimKiemDSKeyReleased

    private void rdoDangDienRaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDangDienRaMouseClicked
        fillTableDS();
    }//GEN-LAST:event_rdoDangDienRaMouseClicked

    private void rdoDangDienRaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangDienRaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDangDienRaActionPerformed

    private void rdoSapDienRaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSapDienRaMouseClicked
        fillTableDS();
    }//GEN-LAST:event_rdoSapDienRaMouseClicked

    private void rdoSapDienRaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSapDienRaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoSapDienRaActionPerformed

    private void rdoKetThucMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoKetThucMouseClicked
        fillTableDS();
    }//GEN-LAST:event_rdoKetThucMouseClicked

    private void rdoKetThucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKetThucActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKetThucActionPerformed

    private void btnPreDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreDSActionPerformed
        pageIndexDS--;
        fillTableDS();
        updateStatusPage();
    }//GEN-LAST:event_btnPreDSActionPerformed

    private void btnNextDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDSActionPerformed
        pageIndexDS++;
        fillTableDS();
        updateStatusPage();
    }//GEN-LAST:event_btnNextDSActionPerformed

    private void tblDanhSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDanhSachMouseClicked
        if (evt.getClickCount() == 2) {
            this.row = tblDanhSach.getSelectedRow();
            int maSKKM = (Integer) tblDanhSach.getValueAt(this.row, 0);
            SuKienKhuyenMai sk = daoSK.selectById(maSKKM + "");
            this.setForm(sk);
            tabs.setSelectedIndex(1);
            this.statusButton();

        }
    }//GEN-LAST:event_tblDanhSachMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
       
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtTimKiemLTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemLTKeyReleased
        fillTableLT();
    }//GEN-LAST:event_txtTimKiemLTKeyReleased

    private void rdoDangDienRaLTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDangDienRaLTMouseClicked
        fillTableLT();
    }//GEN-LAST:event_rdoDangDienRaLTMouseClicked

    private void rdoDangDienRaLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoDangDienRaLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoDangDienRaLTActionPerformed

    private void rdoSapDienRaLTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoSapDienRaLTMouseClicked
        fillTableLT();
    }//GEN-LAST:event_rdoSapDienRaLTMouseClicked

    private void rdoSapDienRaLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoSapDienRaLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoSapDienRaLTActionPerformed

    private void rdoKetThucLTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoKetThucLTMouseClicked
        fillTableLT();
    }//GEN-LAST:event_rdoKetThucLTMouseClicked

    private void rdoKetThucLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoKetThucLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoKetThucLTActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        this.khoiPhuc();
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnPreLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreLTActionPerformed
        pageIndexLT--;
        fillTableLT();
        updateStatusPage();
    }//GEN-LAST:event_btnPreLTActionPerformed

    private void btnNextLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextLTActionPerformed
        pageIndexLT++;
        fillTableLT();
        updateStatusPage();
    }//GEN-LAST:event_btnNextLTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblIndexDS;
    private javax.swing.JLabel lblIndexLT;
    private javax.swing.JPanel panel;
    private javax.swing.JRadioButton rdoDangDienRa;
    private javax.swing.JRadioButton rdoDangDienRaLT;
    private javax.swing.JRadioButton rdoKetThuc;
    private javax.swing.JRadioButton rdoKetThucLT;
    private javax.swing.JRadioButton rdoSapDienRa;
    private javax.swing.JRadioButton rdoSapDienRaLT;
    private javax.swing.JPanel tabDanhSach;
    private javax.swing.JPanel tabLuuTru;
    private javax.swing.JPanel tabThongTin;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDanhSach;
    private javax.swing.JTable tblLuuTru;
    private javax.swing.JTextField txtGiaTriKM;
    private javax.swing.JLabel txtMaSKKM;
    private com.toedter.calendar.JDateChooser txtNgayBatDau;
    private com.toedter.calendar.JDateChooser txtNgayKetThuc;
    private com.toedter.calendar.JDateChooser txtNgayTao;
    private javax.swing.JTextField txtTenSKKM;
    private javax.swing.JTextField txtTimKiemDS;
    private javax.swing.JTextField txtTimKiemLT;
    // End of variables declaration//GEN-END:variables
}
