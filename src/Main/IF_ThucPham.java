/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.LoaiThucPhamDAO;
import DAO.NhanVienDAO;
import DAO.ThucPhamDAO;
import Entity.LoaiThucPham;
import Entity.ThucPham;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xdate;
import Untils.Xmail;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class IF_ThucPham extends javax.swing.JInternalFrame {

    /**
     * Creates new form IF_ThucPham
     */
    int pageIndexDS;
    int pageIndexLT;
    int row = -1;
    DefaultTableModel dtmDS;
    DefaultTableModel dtmLT;
    ThucPhamDAO tpdao;
    NhanVienDAO nvdao;
    LoaiThucPhamDAO ltpdao;
    List<LoaiThucPham> lstLoaiThucPhams;

    public IF_ThucPham() {
        initComponents();
        init();
    }

    void init() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(true);
        setTitle("QUẢN LÝ NGUYÊN LIỆU");
        setDefaultCloseOperation(2);
        dtmDS = (DefaultTableModel) tblDS.getModel();
        dtmLT = (DefaultTableModel) tblLT.getModel();
        tabs.setSelectedIndex(1);
        tpdao = new ThucPhamDAO();
        nvdao = new NhanVienDAO();
        ltpdao = new LoaiThucPhamDAO();
        lstLoaiThucPhams = ltpdao.selectByAll();
        fillToDanhSach();
        fillToLuuTru();
        fillcboLTP();
        updateStatus();
    }

    void fillcboLTP() {
        DefaultComboBoxModel dcbmLT = (DefaultComboBoxModel) cboLT.getModel();
        DefaultComboBoxModel dcbmTT = (DefaultComboBoxModel) cboLoaiThucPham.getModel();
        // dcbmDS.removeAllElements();
        dcbmLT.removeAllElements();
        dcbmTT.removeAllElements();
        for (LoaiThucPham x : lstLoaiThucPhams) {
            //      dcbmDS.addElement(x.getTenLoaiTP());
            dcbmLT.addElement(x.getTenLoaiTP());
            dcbmTT.addElement(x.getTenLoaiTP());
        }
    }

    void fillToDanhSach() {
        List<ThucPham> lst = tpdao.selectPagingFull(1, pageIndexDS,txtTimDS.getText());
        lblPageIndexDS.setText(pageIndexDS + 1 + "");
        updateStatusPage();
        dtmDS.setRowCount(0);
        lst.forEach((tp) -> {
            lblNgayMua.setText(Xdate.toString(tp.getNgaymua(),"yyyy/MM/dd"));
            lblNgayTaoDS.setText(Xdate.toString(tp.getNgaynhap(),"yyyy/MM/dd"));
            lblNhanVienmua.setText(tp.getMaNV());
            dtmDS.addRow(new Object[]{
                tp.getMaNL(),
                tp.getTenNL(),
                tp.getMaLoaiTP(),
                tp.getSoLuong()});
            
        });
    }

    void fillToLuuTru() {
        List<ThucPham> lst = tpdao.selectPagingFull(0, pageIndexLT, txtTimLT.getText());
        lblPageIndexLT.setText(pageIndexLT + 1 + "");
        updateStatusPage();
        dtmLT.setRowCount(0);
        lst.forEach((tp) -> {
            dtmLT.addRow(new Object[]{
                tp.getMaNL(),
                tp.getTenNL(),
                tp.getMaLoaiTP(),
                tp.getSoLuong()});
        });
    }

    void resetForm() {
        row = -1;
        txtMa.setText("");
        txtTen.setText("");
        txtMoTa.setText("");
        txtNgaymua.setDate(null);
        txtSoLuong.setText("");
        txtnvMua.setText("");
        txtMoTa.setText("");
        updateStatus();
    }

    void setForm(ThucPham tp) {
        txtMa.setText(tp.getMaNL() + "");
        txtTen.setText(tp.getTenNL());
        txtSoLuong.setText(String.valueOf(tp.getSoLuong()));
        cboLoaiThucPham.setSelectedItem(ltpdao.selectById(tp.getMaLoaiTP()).getTenLoaiTP());
        txtNgaymua.setDate((tp.getNgaymua()));
        lblngayTao.setText(Xdate.toString(tp.getNgaynhap(),"yyyy/MM/DD"));
        txtMoTa.setText(tp.getMoTa());
    }

    void updateStatusPage() {
        boolean firstDS = pageIndexDS == 0;
        boolean firstLT = pageIndexLT == 0;
        boolean lastDS = tpdao.selectPagingFull(1, pageIndexDS + 1, txtTimDS.getText()).isEmpty();
        boolean lastLT = tpdao.selectPagingFull(0, pageIndexLT + 1, txtTimLT.getText()).isEmpty();
        btnPreDS.setEnabled(!firstDS);
        btnPreLT.setEnabled(!firstLT);
        btnNextDS.setEnabled(!lastDS);
        btnNextLT.setEnabled(!lastLT);
    }

    void updateStatus() {
        boolean edit = (row >= 0);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    void khoiPhuc() {
        if (tblLT.getSelectedRow() >= 0) {
            if (MsgBox.confirm(this, "Bạn có chắc muốn khôi phục thực phẩm này?")) {

                ThucPham tp = tpdao.selectById(Integer.parseInt(tblLT.getValueAt(tblLT.getSelectedRow(), 0).toString()));
                tp.setTrangThai(true);
                tpdao.update(tp);
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

    ThucPham getForm() {
        ThucPham tp = new ThucPham();
        tp.setTenNL(txtTen.getText());
        tp.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        tp.setNgaymua(txtNgaymua.getDate());
        tp.setNgaynhap(Xdate.now());
        tp.setMaLoaiTP(lstLoaiThucPhams.get(cboLoaiThucPham.getSelectedIndex()).getMaLoaiTP());
        tp.setMoTa(txtMoTa.getText());
        tp.setMaNV(txtnvMua.getText());
        tp.setTrangThai(true);
        return tp;
    }

    void chiTiet() {
        row = tblDS.getSelectedRow();
        setForm(tpdao.selectById(Integer.parseInt(tblDS.getValueAt(row, 0).toString())));
        tabs.setSelectedIndex(0);
        updateStatus();
    }

    boolean checkValidate() {

        if (txtTen.getText().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập tên thực phẩm!");
            txtTen.requestFocus();
            return false;
        } else if (txtSoLuong.getText().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập số lượng!");
            txtSoLuong.requestFocus();
            return false;
        } else if (txtNgaymua.getDate() == null) {
            MsgBox.alert(this, "Vui lòng chọn ngày nhập!");
            return false;
        }
        return true;
    }

    void insert() {
        if (checkValidate()) {
            try {
                tpdao.insert(getForm());
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                MsgBox.alert(this, "Thêm thực phẩm thành công!");
                resetForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Thêm sản phẩm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("minhdungvipro@gmail.com");
                }
                e.printStackTrace();
            }
        }
    }

    void update() {
        if (checkValidate()) {
            try {
                tpdao.update(getForm());
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                MsgBox.alert(this, "Chỉnh sửa thực phẩm thành công");
                resetForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Chỉnh sửa sản phẩm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("minhdungvipro@gmail.com");
                }

            }
        }
    }

    void delete() {
        if (MsgBox.confirm(this, "Bạn có chắc chắn muốn xoá thực phẩm này?")) {
            try {
                tpdao.delete(Integer.parseInt(txtMa.getText()));
                pageIndexDS = 0;
                pageIndexLT = 0;
                fillToDanhSach();
                fillToLuuTru();
                MsgBox.alert(this, "Xoá thực phẩm thành công");
                resetForm();
            } catch (Exception e) {
                if (MsgBox.confirm(this, "Xoá thực phẩm thất bại!! Bạn có muốn báo lỗi tới nhà phát triển?")) {
                    Xmail.writeException(e, getForm());
                    Xmail.sendBugs("minhdungvipro@gmail.com");
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
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtMoTa = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        cboLoaiThucPham = new javax.swing.JComboBox<>();
        txtNgaymua = new com.toedter.calendar.JDateChooser();
        txtTen = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtnvMua = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        lblngayTao = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTimDS = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        CLDNgayMua = new com.toedter.calendar.JDateChooser();
        jPanel13 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btnPreDS = new javax.swing.JButton();
        lblPageIndexDS = new javax.swing.JLabel();
        btnNextDS = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDS = new javax.swing.JTable();
        btnNextDS1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNgayTaoDS = new javax.swing.JLabel();
        lblNgayMua = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblNhanVienmua = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtTimLT = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        CLDNgayMua1 = new com.toedter.calendar.JDateChooser();
        btnKhoiPhuc = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnPreLT = new javax.swing.JButton();
        btnNextLT = new javax.swing.JButton();
        lblPageIndexLT = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblLT = new javax.swing.JTable();

        setClosable(true);

        tabs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jPanel5.setBackground(new java.awt.Color(238, 238, 238));

        jPanel8.setBackground(new java.awt.Color(255, 217, 102));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 168, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(241, 194, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("QUẢN LÝ NGUYÊN LIỆU");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(236, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Mã thực phẩm");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Tên thực phẩm");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Ngày tạo");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Mô tả");

        txtMa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        txtMoTa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Số lượng");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("Loại thực phẩm");

        cboLoaiThucPham.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboLoaiThucPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hải sản" }));

        txtTen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

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

        btnMoi.setBackground(new java.awt.Color(0, 51, 153));
        btnMoi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setText("MỚI");
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Ngày mua");

        txtnvMua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("NV mua");

        lblngayTao.setText("2021/12/04");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel4)
                            .addComponent(jLabel18)
                            .addComponent(jLabel9)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNgaymua, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboLoaiThucPham, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMa, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
                            .addComponent(txtnvMua)
                            .addComponent(lblngayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cboLoaiThucPham, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNgaymua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblngayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtnvMua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtMoTa, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnXoa))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        tabs.addTab("Thông tin", jPanel5);

        jPanel10.setBackground(new java.awt.Color(255, 217, 102));

        txtTimDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimDSActionPerformed(evt);
            }
        });
        txtTimDS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimDSKeyReleased(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(0, 51, 153));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("TÌM KIẾM");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CLDNgayMua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTimDS, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimDS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(CLDNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(241, 194, 50));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 51, 153));
        jLabel13.setText("QUẢN LÝ NGUYÊN LIỆU");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        lblPageIndexDS.setText("1");

        btnNextDS.setBackground(new java.awt.Color(0, 51, 153));
        btnNextDS.setForeground(new java.awt.Color(255, 255, 255));
        btnNextDS.setText(">>");
        btnNextDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextDSActionPerformed(evt);
            }
        });

        tblDS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblDS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã TP", "Tên TP", "Loại thực phẩm", "Số lượng"
            }
        ));
        tblDS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDSMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDSMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblDS);

        btnNextDS1.setBackground(new java.awt.Color(0, 51, 153));
        btnNextDS1.setForeground(new java.awt.Color(255, 255, 255));
        btnNextDS1.setText("XEM CHI TIẾT");
        btnNextDS1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextDS1ActionPerformed(evt);
            }
        });

        jLabel8.setText("Ngày tạo");

        jLabel10.setText("Ngày mua");

        lblNgayTaoDS.setText("yyyy/MM/dd");

        lblNgayMua.setText("yyyy/MM/dd");

        jLabel15.setText("Nhân viên mua");

        lblNhanVienmua.setText("jLabel16");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(206, 206, 206)
                        .addComponent(btnPreDS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPageIndexDS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextDS)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNextDS1)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNhanVienmua)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18)
                                .addComponent(lblNgayTaoDS, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(lblNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102))))))
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNgayTaoDS, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addComponent(lblNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(lblNhanVienmua))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreDS)
                            .addComponent(lblPageIndexDS)
                            .addComponent(btnNextDS)
                            .addComponent(btnNextDS1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        tabs.addTab("Danh sách", jPanel6);

        jPanel11.setBackground(new java.awt.Color(255, 217, 102));

        txtTimLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimLTActionPerformed(evt);
            }
        });
        txtTimLT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimLTKeyReleased(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(0, 51, 153));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("TÌM KIẾM");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTimLT)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(CLDNgayMua1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtTimLT, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CLDNgayMua1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        jLabel14.setText(" QUẢN LÝ NGUYÊN LIỆU");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(221, 221, 221)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        lblPageIndexLT.setText("1");

        tblLT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã TP", "Tên TP", "Sl", "Loại  TP", "Ngày nhập", "Ngày tạo", "NV mua"
            }
        ));
        tblLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLTMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblLTMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblLT);
        if (tblLT.getColumnModel().getColumnCount() > 0) {
            tblLT.getColumnModel().getColumn(4).setHeaderValue("Ngày nhập");
            tblLT.getColumnModel().getColumn(5).setHeaderValue("Ngày tạo");
            tblLT.getColumnModel().getColumn(6).setHeaderValue("NV mua");
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(0, 224, Short.MAX_VALUE)
                        .addComponent(btnPreLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblPageIndexLT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNextLT)
                        .addGap(79, 79, 79)
                        .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE))
                .addContainerGap())
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
                        .addGap(7, 7, 7)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnPreLT)
                            .addComponent(lblPageIndexLT)
                            .addComponent(btnNextLT)
                            .addComponent(btnKhoiPhuc, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        tabs.addTab("Lưu trữ", jPanel7);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(tabs)
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 731, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        // TODO add your handling code here:
        resetForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtTimDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimDSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimDSActionPerformed

    private void txtTimDSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimDSKeyReleased
        // TODO add your handling code here:
        pageIndexDS = 0;
        fillToDanhSach();
    }//GEN-LAST:event_txtTimDSKeyReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        pageIndexDS = 0;
    }//GEN-LAST:event_jButton7ActionPerformed

    private void btnPreDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreDSActionPerformed
        // TODO add your handling code here:
        pageIndexDS--;
        fillToDanhSach();
    }//GEN-LAST:event_btnPreDSActionPerformed

    private void btnNextDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDSActionPerformed
        // TODO add your handling code here:
        pageIndexDS++;
        fillToDanhSach();
    }//GEN-LAST:event_btnNextDSActionPerformed

    private void txtTimLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimLTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimLTActionPerformed

    private void txtTimLTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimLTKeyReleased
        // TODO add your handling code here:
        pageIndexLT = 0;
        fillToLuuTru();
    }//GEN-LAST:event_txtTimLTKeyReleased

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        // TODO add your handling code here:
        khoiPhuc();
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnPreLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreLTActionPerformed
        // TODO add your handling code here:
        pageIndexLT--;
        fillToLuuTru();
    }//GEN-LAST:event_btnPreLTActionPerformed

    private void btnNextLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextLTActionPerformed
        // TODO add your handling code here:
        pageIndexLT++;
        fillToLuuTru();
    }//GEN-LAST:event_btnNextLTActionPerformed

    private void tblDSMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSMousePressed
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            this.row = tblDS.getSelectedRow();
            this.chiTiet();
        }
    }//GEN-LAST:event_tblDSMousePressed

    private void tblDSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDSMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            chiTiet();
        }
    }//GEN-LAST:event_tblDSMouseClicked

    private void btnNextDS1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextDS1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextDS1ActionPerformed

    private void tblLTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLTMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblLTMouseClicked

    private void tblLTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLTMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tblLTMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser CLDNgayMua;
    private com.toedter.calendar.JDateChooser CLDNgayMua1;
    private javax.swing.JButton btnKhoiPhuc;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNextDS;
    private javax.swing.JButton btnNextDS1;
    private javax.swing.JButton btnNextLT;
    private javax.swing.JButton btnPreDS;
    private javax.swing.JButton btnPreLT;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboLoaiThucPham;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblNgayMua;
    private javax.swing.JLabel lblNgayTaoDS;
    private javax.swing.JLabel lblNhanVienmua;
    private javax.swing.JLabel lblPageIndexDS;
    private javax.swing.JLabel lblPageIndexLT;
    private javax.swing.JLabel lblngayTao;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDS;
    private javax.swing.JTable tblLT;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtMoTa;
    private com.toedter.calendar.JDateChooser txtNgaymua;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimDS;
    private javax.swing.JTextField txtTimLT;
    private javax.swing.JTextField txtnvMua;
    // End of variables declaration//GEN-END:variables
}
