/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.BanChoDAO;
import DAO.LoaiSanPhamDAO;
import DAO.NhanVienDAO;
import DAO.SanPhamDAO;
import Entity.LoaiSp;
import Entity.SanPham;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Ximage;
import Untils.Xmail;
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
public class IF_SanPham extends javax.swing.JInternalFrame {

    /**
     * Creates new form IF_SanPham
     */
    int pageIndexDS;
    int pageIndexLT;
    int row = -1;
    DefaultTableModel modelDS;
    DefaultTableModel modelLT;
    SanPhamDAO daoSP;
    BanChoDAO daoBC;
    LoaiSanPhamDAO daoLSP;
    List<LoaiSp> lstLSP;

    public IF_SanPham() {
        initComponents();
        init();
    }

    private void init() {
        setResizable(true);
        setTitle("QUẢN LÝ SẢN PHẨM");
        daoSP = new SanPhamDAO();
        daoLSP = new LoaiSanPhamDAO();
        lstLSP = daoLSP.selectAll();
        daoBC = new BanChoDAO();
        setDefaultCloseOperation(2);
        jPanel13.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel10.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel2.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel8.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel14.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        jPanel11.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
//        Auth.user = new NhanVienDAO().selectById("PH16552");
        prepareGUI();
        tabs.setSelectedIndex(0);
        modelDS = (DefaultTableModel) tblSanPham.getModel();
        modelLT = (DefaultTableModel) tblLuutru.getModel();
        resetForm();
        fillToDanhSach();
        fillToLuuTru();
        updateStatus();
    }

    void prepareGUI() {
        fillComBoBoxDVT(cboDVT);
        addItemToCbo(cboLocSp);
        addItemToCbo(cboLocLT);
        addItemToCbo(cboLoaiSP);
        String h[] = {"Mã SP", "Tên SP", "Đơn giá", "ĐVT", "Loại SP", "Ảnh", "Mô tả", "Tên NV"};
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

        tblSanPham.setModel(modelDS);
        tblLuutru.setModel(modelLT);
    }

    int giaMin() {
        if (rdoduoi100.isSelected()) {
            return 0;
        } else if (rdotu100500.isSelected()) {
            return 100001;
        } else if (rdotren500.isSelected()) {
            return 5000001;
        }
        return 0;
    }

    int giaMax() {
        if (rdoduoi100.isSelected()) {
            return 100000;
        } else if (rdotu100500.isSelected()) {
            return 500000;
        } else if (rdotren500.isSelected()) {
            return 100000000;
        }
        return 0;
    }

    int giaMinLT() {
        if (rdoDuoi100LT.isSelected()) {
            return 0;
        } else if (rdoTu100500LT.isSelected()) {
            return 100001;
        } else if (rdoTren500LT.isSelected()) {
            return 500001;
        }
        return 0;
    }

    int giaMaxLT() {
        if (rdoDuoi100LT.isSelected()) {
            return 100000;
        } else if (rdoTu100500LT.isSelected()) {
            return 500000;
        } else if (rdoTren500LT.isSelected()) {
            return 100000000;
        }
        return 0;
    }

    void fillToDanhSach() {
        List<SanPham> lst = daoSP.selectPagingFull(1, pageIndexDS, txtTimKiemsp.getText().trim(), lstLSP.get(cboLocSp.getSelectedIndex()).getMaLoaiSP(), giaMin(), giaMax());
        lblSo.setText(pageIndexDS + 1 + "");
        updateStatusPage();
        modelDS.setRowCount(0);
        for (SanPham sp : lst) {
            modelDS.addRow(new Object[]{sp.getMaSP(), sp.getTenSanPham(),
                sp.getDonGia(), sp.getDonViTinh(), daoLSP.selectByID(sp.getMaLoaiSP()).getTenLoaiSP(),
                sp.getAnhSP(), sp.getChiTiet(), new NhanVienDAO().selectById(sp.getMaNV()).getTenNV()});

        }

    }

    void fillToLuuTru() {
        List<SanPham> lst = daoSP.selectPagingFull(0, pageIndexLT, txtTimLT.getText().trim(), lstLSP.get(cboLocLT.getSelectedIndex()).getMaLoaiSP(), giaMinLT(), giaMaxLT());
        lblSoLT.setText(pageIndexLT + 1 + "");
        updateStatusPage();
        modelLT.setRowCount(0);
        for (SanPham sp : lst) {
            modelLT.addRow(new Object[]{sp.getMaSP(), sp.getTenSanPham(),
                sp.getDonGia(), sp.getDonViTinh(), daoLSP.selectByID(sp.getMaLoaiSP()).getTenLoaiSP(),
                sp.getAnhSP(), sp.getChiTiet(), new NhanVienDAO().selectById(sp.getMaNV()).getTenNV()});

        }

    }

    void fillComBoBoxDVT(JComboBox cbo) {
        cbo.removeAllItems();
        cbo.addItem("Đĩa");
        cbo.addItem("Bát");
        cbo.addItem("Chai");
        cbo.addItem("Lon");
        cbo.addItem("Ly");

    }

    void addItemToCbo(JComboBox cbo) {
        cbo.removeAllItems();
        lstLSP.forEach((loaiSp) -> {
            cbo.addItem(loaiSp.getTenLoaiSP());
        });

    }

    void updateStatusPage() {
        boolean firstDS = pageIndexDS == 0;
        boolean firstLT = pageIndexLT == 0;
        boolean lastDS = daoSP.selectPagingFull(1, pageIndexDS + 1, txtTimKiemsp.getText().trim(), lstLSP.get(cboLocSp.getSelectedIndex()).getMaLoaiSP(), giaMin(), giaMax()).isEmpty();
        boolean lastLT = daoSP.selectPagingFull(0, pageIndexLT + 1, txtTimLT.getText().trim(), lstLSP.get(cboLocLT.getSelectedIndex()).getMaLoaiSP(), giaMinLT(), giaMaxLT()).isEmpty();
        btnTruoc.setEnabled(!firstDS);
        btnTruocLT.setEnabled(!firstLT);
        btnSau.setEnabled(!lastDS);
        btnSauLT.setEnabled(!lastLT);
    }

    void resetForm() {
        row = -1;
        txtMaSP.setText("");
        txtTenSP.setText("");
        txtDonGia.setText("");
        txtMota.setText("");
        lblAnh.setIcon(null);
        lblAnh.setToolTipText("");
        txtTaoBoi.setText(Auth.user.getTenNV());
        txtTaoBoi.setEnabled(false);
        updateStatus();
    }

    void setForm(SanPham sp) {
        txtMaSP.setText(sp.getMaSP());
        txtTenSP.setText(sp.getTenSanPham());
        txtDonGia.setText(String.valueOf(sp.getDonGia()));
        cboDVT.setSelectedItem(sp.getDonViTinh().trim());
        cboLoaiSP.setSelectedItem(daoLSP.selectByID(sp.getMaLoaiSP()).getTenLoaiSP());
        txtMota.setText(sp.getChiTiet());
        txtTaoBoi.setText(new NhanVienDAO().selectById(sp.getMaNV()).getTenNV());
        lblAnh.setIcon(Ximage.read(sp.getAnhSP(), lblAnh.getWidth() - 4, lblAnh.getHeight() - 4));
        lblAnh.setToolTipText(sp.getAnhSP());
    }

    void updateStatus() {
        boolean edit = (row >= 0);
        txtMaSP.setEnabled(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void khoiPhuc() {
        if (tblLuutru.getSelectedRow() >= 0) {
            if (MsgBox.confirm(this, "Bạn có chắc muốn khôi phục sản phẩm này?")) {

                SanPham sp = daoSP.selectById(tblLuutru.getValueAt(tblLuutru.getSelectedRow(), 0).toString());
                sp.setTrangThai(true);
                daoSP.update(sp);
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                fillToLuuTru();
                MsgBox.alert(this, "Khôi phục thành công");
            }
        } else {
            MsgBox.alert(this, "Vui lòng chọn sản phẩm cần khôi phục!");
        }
    }

    SanPham getForm() {
        SanPham sp = new SanPham();
        sp.setMaSP(txtMaSP.getText().trim());
        sp.setMaLoaiSP(lstLSP.get(cboLoaiSP.getSelectedIndex()).getMaLoaiSP());
        sp.setTenSanPham(txtTenSP.getText().trim());
        sp.setDonGia(Integer.valueOf(txtDonGia.getText().trim()));
        sp.setDonViTinh(cboDVT.getSelectedItem().toString());
        sp.setAnhSP(lblAnh.getToolTipText());
        sp.setMaNV(Auth.user.getMaNV());
        sp.setChiTiet(txtMota.getText().trim());
        sp.setTrangThai(true);
        return sp;

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

    boolean checkValidate() {
        String s = txtTenSP.getText().replaceAll("[^0-9]", "");
        String pKiTu = txtTenSP.getText().replaceAll("[^!@#$%&*()_+=|<>?{}\\\\[\\\\]~-]", "");
        if (txtMaSP.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập mã sản phẩm!");
            txtMaSP.requestFocus();
            return false;
        } else if (txtTenSP.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập tên sản phẩm!");
            txtTenSP.requestFocus();
            return false;
        } else if ((txtTenSP.getText().trim().matches(s))) {
            MsgBox.alert(this, "Tên sản phẩm phải chứa kí tự chữ");
            txtTenSP.requestFocus();
            return false;
        } else if ((txtTenSP.getText().trim().matches(pKiTu))) {
            MsgBox.alert(this, "Tên sản phẩm không được chứa kí tự đặc biệt");
            txtTenSP.requestFocus();
            return false;
        } else if (txtTenSP.getText().trim().length() >= 1 && txtTenSP.getText().trim().length() < 3) {
            MsgBox.alert(this, " Tên sản phẩm ít nhất 3 kí tự!");
            txtTenSP.requestFocus();
            return false;
        } else if (txtTenSP.getText().trim().length() > 30) {
            MsgBox.alert(this, " Tên sản phẩm tối đa 30 kí tự!");
            txtTenSP.requestFocus();
            return false;
        } else if (txtDonGia.getText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập đơn giá sản phẩm!");
            txtDonGia.requestFocus();
            return false;
        } else {
            try {
                if (Integer.parseInt(txtDonGia.getText()) < 0) {
                    MsgBox.alert(this, "Vui lòng nhập đơn giá lớn hơn 0!");
                    txtDonGia.requestFocus();
                    return false;
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Vui lòng nhập vào số!");
                txtDonGia.requestFocus();
                return false;
            }
        }
        if (txtMota.getText().trim().length() > 50) {
            MsgBox.alert(this, " Mô tả tối đa 50 kí tự!");
            txtMota.requestFocus();
            return false;
        } else if (lblAnh.getToolTipText().trim().length() == 0) {
            MsgBox.alert(this, "Vui lòng chọn ảnh sản phẩm!");
            return false;
        }
        return true;
    }

    boolean checkMa() {
        String ma = txtMaSP.getText().trim();
        List<SanPham> list = daoSP.selectAll();
        for (SanPham sp : list) {
            if (sp.getMaSP().trim().equalsIgnoreCase(ma)) {
                MsgBox.alert(this, "Mã sản phẩm đã tồn tại!");
                txtMaSP.requestFocus();
                return false;
            }
        }
        return true;

    }

    void insert() {
        if (checkMa()) {
            if (checkValidate()) {
                try {
                    daoSP.insert(getForm());
                    pageIndexDS = 0;
                    pageIndexLT = 0;
                    fillToDanhSach();
                    fillToLuuTru();
                    MsgBox.alert(this, "Thêm sản phẩm thành công");
                    resetForm();
                } catch (Exception e) {
                    if (MsgBox.confirm(this, "Thêm sản phẩm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                        Xmail.writeException(e, getForm());
                        Xmail.sendBugs("hoantph16552@fpt.edu.vn");
                    }
                }
            }
        }
    }

    void update() {

        if (checkValidate()) {
            try {
                daoSP.update(getForm());
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                fillToLuuTru();
                MsgBox.alert(this, "Chỉnh sửa sản phẩm thành công");
                resetForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Chỉnh sửa sản phẩm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("hoantph16552@fpt.edu.vn");
                }
            }

        }
    }

    void delete() {
        if (daoBC.selectById(txtMaSP.getText().trim()) != null) {
            MsgBox.confirm(this, "Không thể xóa");
            return;
        }
        if (MsgBox.confirm(this, "Bạn có chắc chắn muốn xoá sản phẩm này?")) {
            try {
                daoSP.delete(txtMaSP.getText().trim());
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                fillToLuuTru();
                MsgBox.alert(this, "Xoá sản phẩm thành công");
                resetForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Xoá sản phẩm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("hoantph16552@fpt.edu.vn");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        tabs = new javax.swing.JTabbedPane();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTimKiemsp = new javax.swing.JTextField();
        cboLocSp = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        rdoduoi100 = new javax.swing.JRadioButton();
        rdotu100500 = new javax.swing.JRadioButton();
        rdotren500 = new javax.swing.JRadioButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnTruoc = new javax.swing.JButton();
        lblSo = new javax.swing.JLabel();
        btnSau = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtDonGia = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        txtTenSP = new javax.swing.JTextField();
        cboLoaiSP = new javax.swing.JComboBox<>();
        cboDVT = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtMota = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtTaoBoi = new javax.swing.JLabel();
        btnMOi = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtTimLT = new javax.swing.JTextField();
        cboLocLT = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        rdoDuoi100LT = new javax.swing.JRadioButton();
        rdoTu100500LT = new javax.swing.JRadioButton();
        rdoTren500LT = new javax.swing.JRadioButton();
        btnKhoiPhuc = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnTruocLT = new javax.swing.JButton();
        btnSauLT = new javax.swing.JButton();
        lblSoLT = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblLuutru = new javax.swing.JTable();

        setClosable(true);

        tabs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 217, 102));

        txtTimKiemsp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemspActionPerformed(evt);
            }
        });
        txtTimKiemsp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemspKeyReleased(evt);
            }
        });

        cboLocSp.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLocSp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tráng miệng" }));
        cboLocSp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocSpItemStateChanged(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Mức giá");

        buttonGroup1.add(rdoduoi100);
        rdoduoi100.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoduoi100.setSelected(true);
        rdoduoi100.setText("Dưới 100.000");
        rdoduoi100.setOpaque(false);
        rdoduoi100.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoduoi100MouseClicked(evt);
            }
        });

        buttonGroup1.add(rdotu100500);
        rdotu100500.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdotu100500.setText("100.000 - 500.000");
        rdotu100500.setOpaque(false);
        rdotu100500.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdotu100500MouseClicked(evt);
            }
        });

        buttonGroup1.add(rdotren500);
        rdotren500.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdotren500.setText("Trên 500.000");
        rdotren500.setOpaque(false);
        rdotren500.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdotren500MouseClicked(evt);
            }
        });
        rdotren500.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdotren500ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimKiemsp)
                    .addComponent(cboLocSp, 0, 148, Short.MAX_VALUE)
                    .addComponent(rdotren500)
                    .addComponent(rdotu100500)
                    .addComponent(rdoduoi100)
                    .addComponent(jLabel15))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimKiemsp, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLocSp, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoduoi100)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdotu100500)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdotren500)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(241, 194, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 153));
        jLabel13.setText("QUẢN LÝ SẢN PHẨM");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnTruoc.setBackground(new java.awt.Color(0, 51, 153));
        btnTruoc.setForeground(new java.awt.Color(255, 255, 255));
        btnTruoc.setText("<<");
        btnTruoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruocActionPerformed(evt);
            }
        });

        lblSo.setText("1");

        btnSau.setBackground(new java.awt.Color(0, 51, 153));
        btnSau.setForeground(new java.awt.Color(255, 255, 255));
        btnSau.setText(">>");
        btnSau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSauActionPerformed(evt);
            }
        });

        tblSanPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sp", "Tên sp", "Đơn giá", "DVT", "Loại sp", "Ảnh", "Mô tả", "Tên Nv"
            }
        ));
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(btnTruoc)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSau))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTruoc)
                            .addComponent(lblSo)
                            .addComponent(btnSau))
                        .addContainerGap())
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabs.addTab("Danh sách", jPanel6);

        jPanel5.setBackground(new java.awt.Color(238, 238, 238));

        jPanel8.setBackground(new java.awt.Color(255, 217, 102));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 163, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã sản phẩm");

        txtMaSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên sản phẩm");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Đơn giá");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Đơn vị tính");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Loại sản phẩm");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Mô tả");

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

        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
        );

        txtTenSP.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        cboLoaiSP.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboDVT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtMota.setColumns(20);
        txtMota.setRows(5);
        jScrollPane3.setViewportView(txtMota);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Tạo bởi:");

        txtTaoBoi.setText("Manv");

        btnMOi.setBackground(new java.awt.Color(0, 51, 153));
        btnMOi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMOi.setForeground(new java.awt.Color(255, 255, 255));
        btnMOi.setText("MỚI");
        btnMOi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMOiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel8))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLoaiSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTenSP)
                            .addComponent(cboDVT, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(txtTaoBoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnMOi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(123, 123, 123))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboDVT, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboLoaiSP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTaoBoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnXoa)
                            .addComponent(btnMOi))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSua)
                            .addComponent(btnThem)))
                    .addComponent(jLabel7))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(241, 194, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("QUẢN LÝ SẢN PHẨM");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
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
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabs.addTab("Thông tin", jPanel5);

        jPanel11.setBackground(new java.awt.Color(255, 217, 102));

        txtTimLT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimLTKeyReleased(evt);
            }
        });

        cboLocLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLocLT.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tráng miệng" }));
        cboLocLT.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLocLTItemStateChanged(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("Mức giá");

        buttonGroup2.add(rdoDuoi100LT);
        rdoDuoi100LT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoDuoi100LT.setSelected(true);
        rdoDuoi100LT.setText("Dưới 100.000");
        rdoDuoi100LT.setOpaque(false);
        rdoDuoi100LT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoDuoi100LTMouseClicked(evt);
            }
        });

        buttonGroup2.add(rdoTu100500LT);
        rdoTu100500LT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTu100500LT.setText("100.000 - 500.000");
        rdoTu100500LT.setOpaque(false);
        rdoTu100500LT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTu100500LTMouseClicked(evt);
            }
        });

        buttonGroup2.add(rdoTren500LT);
        rdoTren500LT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoTren500LT.setText("Trên 500.000");
        rdoTren500LT.setOpaque(false);
        rdoTren500LT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rdoTren500LTMouseClicked(evt);
            }
        });
        rdoTren500LT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoTren500LTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimLT)
                    .addComponent(cboLocLT, 0, 148, Short.MAX_VALUE)
                    .addComponent(rdoTren500LT)
                    .addComponent(rdoTu100500LT)
                    .addComponent(rdoDuoi100LT)
                    .addComponent(jLabel16))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimLT, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboLocLT, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoDuoi100LT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoTu100500LT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdoTren500LT)
                .addContainerGap(264, Short.MAX_VALUE))
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
        jLabel14.setText("QUẢN LÝ SẢN PHẨM");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(274, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnTruocLT.setBackground(new java.awt.Color(0, 51, 153));
        btnTruocLT.setForeground(new java.awt.Color(255, 255, 255));
        btnTruocLT.setText("<<");
        btnTruocLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTruocLTActionPerformed(evt);
            }
        });

        btnSauLT.setBackground(new java.awt.Color(0, 51, 153));
        btnSauLT.setForeground(new java.awt.Color(255, 255, 255));
        btnSauLT.setText(">>");
        btnSauLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSauLTActionPerformed(evt);
            }
        });

        lblSoLT.setText("1");

        tblLuutru.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLuutru.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã sp", "Tên sp", "Đơn giá", "DVT", "Loại sp", "Ảnh", "Mô tả", "Tên Nv"
            }
        ));
        jScrollPane4.setViewportView(tblLuutru);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 219, Short.MAX_VALUE)
                        .addComponent(btnTruocLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblSoLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSauLT)
                        .addGap(87, 87, 87)
                        .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4))))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTruocLT)
                            .addComponent(lblSoLT)
                            .addComponent(btnSauLT)
                            .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        tabs.addTab("Lưu trữ", jPanel7);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 152, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 545, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void txtTimKiemspActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemspActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemspActionPerformed

    private void txtTimKiemspKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemspKeyReleased
        pageIndexDS = 0;
        fillToDanhSach();
    }//GEN-LAST:event_txtTimKiemspKeyReleased

    private void cboLocSpItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocSpItemStateChanged
        if (cboLocSp.getItemCount() > 1) {
            pageIndexDS = 0;
            fillToDanhSach();
        }
    }//GEN-LAST:event_cboLocSpItemStateChanged

    private void rdoduoi100MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoduoi100MouseClicked
        fillToDanhSach();
    }//GEN-LAST:event_rdoduoi100MouseClicked

    private void rdotu100500MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdotu100500MouseClicked
        fillToDanhSach();
    }//GEN-LAST:event_rdotu100500MouseClicked

    private void rdotren500MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdotren500MouseClicked
        fillToDanhSach();
    }//GEN-LAST:event_rdotren500MouseClicked

    private void rdotren500ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdotren500ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdotren500ActionPerformed

    private void btnTruocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruocActionPerformed
        pageIndexDS--;
        fillToDanhSach();
    }//GEN-LAST:event_btnTruocActionPerformed

    private void btnSauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSauActionPerformed
        pageIndexDS++;
        fillToDanhSach();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSauActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        if (evt.getClickCount() == 2) {
            tabs.setSelectedIndex(1);
            row = tblSanPham.getSelectedRow();
            setForm(daoSP.selectById(tblSanPham.getValueAt(row, 0).toString()));
            updateStatus();
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void lblAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAnhMouseClicked
        if (evt.getClickCount() == 2) {
            chonAnh();
        }
    }//GEN-LAST:event_lblAnhMouseClicked

    private void txtTimLTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimLTKeyReleased
        pageIndexLT = 0;
        fillToLuuTru();
    }//GEN-LAST:event_txtTimLTKeyReleased

    private void cboLocLTItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLocLTItemStateChanged
        if (cboLocLT.getItemCount() > 1) {
            pageIndexLT = 0;
            fillToLuuTru();
        }
    }//GEN-LAST:event_cboLocLTItemStateChanged

    private void rdoDuoi100LTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoDuoi100LTMouseClicked
        fillToLuuTru();
    }//GEN-LAST:event_rdoDuoi100LTMouseClicked

    private void rdoTu100500LTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTu100500LTMouseClicked
        fillToLuuTru();
    }//GEN-LAST:event_rdoTu100500LTMouseClicked

    private void rdoTren500LTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rdoTren500LTMouseClicked
        fillToLuuTru();
    }//GEN-LAST:event_rdoTren500LTMouseClicked

    private void rdoTren500LTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoTren500LTActionPerformed

    }//GEN-LAST:event_rdoTren500LTActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        khoiPhuc();
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnTruocLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTruocLTActionPerformed
        pageIndexLT--;
        fillToLuuTru();
    }//GEN-LAST:event_btnTruocLTActionPerformed

    private void btnSauLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSauLTActionPerformed
        pageIndexLT++;
        fillToLuuTru();
    }//GEN-LAST:event_btnSauLTActionPerformed

    private void btnMOiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMOiActionPerformed
        resetForm();
    }//GEN-LAST:event_btnMOiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnMOi;
    private javax.swing.JButton btnSau;
    private javax.swing.JButton btnSauLT;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTruoc;
    private javax.swing.JButton btnTruocLT;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboDVT;
    private javax.swing.JComboBox<String> cboLoaiSP;
    private javax.swing.JComboBox<String> cboLocLT;
    private javax.swing.JComboBox<String> cboLocSp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblSo;
    private javax.swing.JLabel lblSoLT;
    private javax.swing.JRadioButton rdoDuoi100LT;
    private javax.swing.JRadioButton rdoTren500LT;
    private javax.swing.JRadioButton rdoTu100500LT;
    private javax.swing.JRadioButton rdoduoi100;
    private javax.swing.JRadioButton rdotren500;
    private javax.swing.JRadioButton rdotu100500;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblLuutru;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextArea txtMota;
    private javax.swing.JLabel txtTaoBoi;
    private javax.swing.JTextField txtTenSP;
    private javax.swing.JTextField txtTimKiemsp;
    private javax.swing.JTextField txtTimLT;
    // End of variables declaration//GEN-END:variables
}
