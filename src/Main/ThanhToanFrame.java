/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.BanChoDAO;
import DAO.BanDAO;
import DAO.HoaDonChiTietDAO;
import DAO.HoaDonDAO;
import DAO.KhachHangDao;
import DAO.NhanVienDAO;
import DAO.SKKMDAO;
import DAO.SanPhamDAO;
import Entity.BanCho;
import Entity.HoaDon;
import Entity.HoaDonChiTiet;
import Entity.KhachHang;
import Entity.SanPham;
import Entity.SuKienKhuyenMai;
import Untils.Auth;
import Untils.MsgBox;
import Untils.Xcurrency;
import Untils.Xdate;
import Untils.Xmail;
import java.awt.Color;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author doanp
 */
public class ThanhToanFrame extends javax.swing.JFrame {

    /**
     * Creates new form ThanhToanFrame
     */
    String maBan;
    DefaultTableModel modelSP;
    List<SuKienKhuyenMai> lstSKKM;

    public ThanhToanFrame(String Ban) {
        initComponents();
        maBan = Ban;
        initTT();
    }

    void initTT() {
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(2);
        setTitle("THANH TOÁN");
        jLabel3.setBackground(new Color(Integer.parseInt(Auth.user.getMauNen(), 16)));
        prepareGUITT();
    }

    void prepareGUITT() {
        setTableSPTT();
        fillCboSKTT();
        setTextToLBTT();
        DefaultFormTT(true);
        thanhToanTT();
    }

    void DefaultFormTT(boolean done) {
        btnHoanThanhTT.setEnabled(!done);
        btnInHoaDonTT.setEnabled(!done);
        txtSDTTT.setEnabled(done);
        txtEmailTT.setEnabled(done);
        txtTenTT.setEnabled(done);
        txtNgaySinhTT.setEnabled(done);
        btnHuyChonTT.setEnabled(!done);
    }

    void huyChonTT() {
        DefaultFormTT(true);
        txtTenTT.setText("");
        txtEmailTT.setText("");
        txtNgaySinhTT.setDate(null);
        rdoNamTT.setSelected(true);
        txtSDTTT.setText("");
        rdoNamTT.setEnabled(true);
        rdoNuTT.setEnabled(true);
        btnKTTT.setEnabled(true);
        btnThemMoiTT.setEnabled(true);
    }

    void traLaiTT() {
        int tongCong = Xcurrency.toInt(lblTongcong.getText());
        int traLai = Integer.parseInt(txtKhachDua.getText()) - tongCong;
        if (traLai > 0) {
            lblTraLai.setText(Xcurrency.toCurrency(traLai));
        } else {
            lblTraLai.setText("Còn thiếu " + Xcurrency.toCurrency(Math.abs(traLai)));
        }
    }

    void setTextToLBTT() {
        lblBan.setText(new BanDAO().selectById(maBan).getTenBan());
        lblOrder.setText(new NhanVienDAO().selectById(new BanChoDAO().getNV(maBan)).getTenNV());
        lblGioVao.setText(new BanChoDAO().getTimeIn(maBan));
        lblGioRa.setText(Xdate.toString(new Date(), "hh:mm:ss - EE, dd/MM/yyyy"));
        lblThanhToan.setText(Auth.user.getTenNV());
    }

    void thanhToanTT() {
        lblCongtien.setText(getCongTienTT());
        lblGiam.setText(getGiamTT());
        lblTongcong.setText(getTongCongTT());
        lblThanhChu.setText(Xcurrency.readNumber(Xcurrency.toInt(lblTongcong.getText())) + "Đồng");
        if (txtKhachDua.getText().length() > 0) {
            traLaiTT();
        }
    }

    String getTongCongTT() {
        int tongCong = Xcurrency.toInt(lblCongtien.getText()) - Xcurrency.toInt(lblGiam.getText());
        return Xcurrency.toCurrency(tongCong);
    }

    String getGiamTT() {
        if (cboSK.getItemCount() > 0) {
            int tienGiam = Xcurrency.toInt(lblCongtien.getText());
            double giaTri = lstSKKM.get(cboSK.getSelectedIndex()).getGiaTriKM();
            return Xcurrency.toCurrency((int) Math.round(tienGiam * giaTri / 100));
        } else {
            return "0 đ";
        }
    }

    String getCongTienTT() {
        int tongTien = 0;
        for (int i = 0; i < tblSP.getRowCount(); i++) {
            tongTien += Xcurrency.toInt(tblSP.getValueAt(i, 5).toString());
        }
        return Xcurrency.toCurrency(tongTien);
    }

    void fillCboSKTT() {
        cboSK.removeAllItems();
        lstSKKM = new SKKMDAO().selectSKDDR(1, Xdate.toString(new Date(), "yyyy-MM-dd"));
        for (SuKienKhuyenMai sk : lstSKKM) {
            cboSK.addItem(sk.getTenSKKM());
        }
    }

    void kiemTraTT() {
        if (txtSDTTT.getText().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập số điện thoại!");
        } else {
            KhachHang ks = new KhachHangDao().selectSDT(txtSDTTT.getText());
            if (!(ks == null)) {
                txtTenTT.setText(ks.getTenKH());
                txtEmailTT.setText(ks.getEmail());
                txtNgaySinhTT.setDate(ks.getNgaysinh());
                rdoNamTT.setSelected(ks.isGioiTinh());
                rdoNuTT.setSelected(!ks.isGioiTinh());
                btnHoanThanhTT.setEnabled(true);
                btnInHoaDonTT.setEnabled(true);
                txtSDTTT.setEnabled(false);
                txtEmailTT.setEnabled(false);
                txtTenTT.setEnabled(false);
                txtNgaySinhTT.setEnabled(false);
                rdoNamTT.setEnabled(false);
                rdoNuTT.setEnabled(false);
                btnKTTT.setEnabled(false);
                btnThemMoiTT.setEnabled(false);
                btnHuyChonTT.setEnabled(true);
            } else {
                btnHoanThanhTT.setEnabled(false);
                btnInHoaDonTT.setEnabled(false);
                MsgBox.alert(this, "Số điện thoại không tồn tại!");
            }
        }
    }

    void setTableSPTT() {
        String h[] = {"Mã sản phẩm", "Tên sản phẩm", "Số lượng", "ĐVT", "Đơn giá", "Tổng tiền"};
        modelSP = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int i, int i1) {
                return false;
            }
        };
        tblSP.setModel(modelSP);
        fillTableDSTT();
    }

    void fillTableDSTT() {
        modelSP.setRowCount(0);
        List<BanCho> lst = new BanChoDAO().selectByBan(maBan);
        for (BanCho banCho : lst) {
            SanPham sp = new SanPhamDAO().selectById(banCho.getMaSP());
            modelSP.addRow(new Object[]{sp.getMaSP(), sp.getTenSanPham(), banCho.getSoLuong(), sp.getDonViTinh(), Xcurrency.toCurrency(sp.getDonGia()), Xcurrency.toCurrency(sp.getDonGia() * banCho.getSoLuong())});

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblThanhToan = new javax.swing.JLabel();
        lblGioRa = new javax.swing.JLabel();
        lblGioVao = new javax.swing.JLabel();
        lblBan = new javax.swing.JLabel();
        lblOrder = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtTenTT = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtSDTTT = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtEmailTT = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        rdoNamTT = new javax.swing.JRadioButton();
        rdoNuTT = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        btnKTTT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblCongtien = new javax.swing.JLabel();
        lblGiam = new javax.swing.JLabel();
        lblTongcong = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        cboSK = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtKhachDua = new javax.swing.JTextField();
        lblTraLai = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblThanhChu = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnHoanThanhTT = new javax.swing.JButton();
        btnInHoaDonTT = new javax.swing.JButton();
        btnHuyTT = new javax.swing.JButton();
        btnThemMoiTT = new javax.swing.JButton();
        txtNgaySinhTT = new com.toedter.calendar.JDateChooser();
        btnHuyChonTT = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Tên khách hàng:");

        jLabel3.setBackground(new java.awt.Color(255, 217, 102));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("THANH TOÁN");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel3.setOpaque(true);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Nhân viên order:");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Giờ vào:");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("Giờ ra");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("Nhân viên thanh toán:");

        lblThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblThanhToan.setText("Thanh toán");

        lblGioRa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGioRa.setText("Giờ");

        lblGioVao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGioVao.setText("Giờ");

        lblBan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblBan.setText("Bàn");

        lblOrder.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOrder.setText("Order");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Bàn:");

        txtTenTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Số điện thoại:");

        txtSDTTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Email:");

        txtEmailTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Ngày sinh:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("Giới tính:");

        buttonGroup1.add(rdoNamTT);
        rdoNamTT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNamTT.setSelected(true);
        rdoNamTT.setText("Nam");

        buttonGroup1.add(rdoNuTT);
        rdoNuTT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rdoNuTT.setText("Nữ");

        tblSP.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblSP);

        btnKTTT.setText("Kiểm tra");
        btnKTTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKTTTActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Cộng tiền hàng:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Giảm:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel16.setText("Tổng cộng:");

        lblCongtien.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblCongtien.setText("1.000.000đ");

        lblGiam.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGiam.setText("1.000.000đ");

        lblTongcong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTongcong.setText("1.000.000đ");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Sự kiện khuyến mãi:");

        cboSK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cboSK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cboSK.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboSKItemStateChanged(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("Khách đưa:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("Trả lại:");

        txtKhachDua.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhachDuaKeyReleased(evt);
            }
        });

        lblTraLai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTraLai.setText("0 đ");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel17.setText("Thành chữ:");

        lblThanhChu.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblThanhChu.setText("Một Triệu Đồng");

        jPanel1.setLayout(new java.awt.GridLayout(3, 1, 0, 10));

        btnHoanThanhTT.setBackground(new java.awt.Color(102, 255, 102));
        btnHoanThanhTT.setText("Hoàn thành");
        btnHoanThanhTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoanThanhTTActionPerformed(evt);
            }
        });
        jPanel1.add(btnHoanThanhTT);

        btnInHoaDonTT.setBackground(new java.awt.Color(240, 112, 211));
        btnInHoaDonTT.setText("In hóa đơn");
        jPanel1.add(btnInHoaDonTT);

        btnHuyTT.setBackground(new java.awt.Color(102, 255, 255));
        btnHuyTT.setText("Hủy");
        btnHuyTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyTTActionPerformed(evt);
            }
        });
        jPanel1.add(btnHuyTT);

        btnThemMoiTT.setText("Thêm mới");
        btnThemMoiTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemMoiTTActionPerformed(evt);
            }
        });

        txtNgaySinhTT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        btnHuyChonTT.setText("Hủy chọn");
        btnHuyChonTT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyChonTTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addComponent(lblThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblGioVao, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtEmailTT, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblBan, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTenTT)
                                    .addComponent(txtSDTTT, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblGioRa, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(rdoNamTT)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(rdoNuTT)
                                        .addGap(150, 150, 150))
                                    .addComponent(txtNgaySinhTT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnKTTT)
                            .addComponent(btnThemMoiTT)
                            .addComponent(btnHuyChonTT)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblGiam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblCongtien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblTongcong, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel20))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblTraLai, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblThanhChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(cboSK, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnHuyChonTT, btnKTTT, btnThemMoiTT});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblBan)
                    .addComponent(txtTenTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnThemMoiTT))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblOrder)
                    .addComponent(jLabel5)
                    .addComponent(txtSDTTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnKTTT))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblGioVao)
                    .addComponent(jLabel8)
                    .addComponent(txtEmailTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuyChonTT))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lblGioRa)
                        .addComponent(jLabel10))
                    .addComponent(txtNgaySinhTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblThanhToan)
                    .addComponent(jLabel14)
                    .addComponent(rdoNamTT)
                    .addComponent(rdoNuTT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(cboSK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(lblCongtien)
                            .addComponent(jLabel19)
                            .addComponent(txtKhachDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGiam)
                            .addComponent(jLabel15)
                            .addComponent(jLabel20)
                            .addComponent(lblTraLai))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(lblTongcong))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblThanhChu)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtKhachDuaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachDuaKeyReleased
        if (!(txtKhachDua.getText().length() == 0)) {
            try {
                traLaiTT();
            } catch (Exception e) {
                MsgBox.alert(this, "Nhập tiền không đúng định dạng!");
                txtKhachDua.setText("");
            }
        } else {
            lblTraLai.setText("0 đ");

        }
    }//GEN-LAST:event_txtKhachDuaKeyReleased
    void themMoiTT() {
        if (checkValidateTT()) {
            KhachHang kh = new KhachHang(txtTenTT.getText(), txtSDTTT.getText(), txtEmailTT.getText(), txtNgaySinhTT.getDate(), rdoNamTT.isSelected(), Auth.user.getMaNV(), true);
            new KhachHangDao().insertNoID(kh);
            MsgBox.alert(this, "Thêm mới thành công");
        }
    }

    void hoanThanhTT() {
        HoaDon hd = new HoaDon(new KhachHangDao().selectSDT(txtSDTTT.getText()).getMaKH(), Xdate.toString(new Date(), "yyyy-MM-dd"), "", Auth.user.getMaNV(), lstSKKM.get(cboSK.getSelectedIndex()).getMaSKKM(), maBan, true);
        new HoaDonDAO().insert(hd);
        int maHD = new HoaDonDAO().selectMaHD(maBan).getMaHD();
        for (int i = 0; i < tblSP.getRowCount(); i++) {
            HoaDonChiTiet hdct = new HoaDonChiTiet(maHD, tblSP.getValueAt(i, 0).toString(), Integer.parseInt(tblSP.getValueAt(i, 2).toString()), Xcurrency.toInt(tblSP.getValueAt(i, 4).toString()), true);
            new HoaDonChiTietDAO().insert(hdct);
        }
        Xmail.sendThanks(txtEmailTT.getText(), txtTenTT.getText());
        MsgBox.alert(this, "Thanh toán thành công!");
        new BanChoDAO().delete(maBan);
        dispose();
    }

    boolean checkValidateTT() {
        String pEmail = "^.+@fpt.edu.vn$";
        String pSDT = "^0[0-9]{9}$";
        if (txtTenTT.getText().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập tên khách hàng!");
            txtTenTT.requestFocus();
            return false;
        } else if (txtSDTTT.getText().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập SDT khách hàng!");
            txtSDTTT.requestFocus();
            return false;
        } else if (!txtSDTTT.getText().matches(pSDT)) {
            MsgBox.alert(this, "SDT không đúng định dạng!");
            txtSDTTT.requestFocus();
            return false;
        } else if (txtEmailTT.getText().length() == 0) {
            MsgBox.alert(this, "Vui lòng nhập email khách hàng!");
            txtEmailTT.requestFocus();
            return false;
        } else if (!txtEmailTT.getText().matches(pEmail)) {
            MsgBox.alert(this, "Email không đúng định dạng!");
            txtEmailTT.requestFocus();
            return false;
        } else if (txtNgaySinhTT.getDate() == null) {
            MsgBox.alert(this, "Vui lòng nhập ngày sinh khách hàng!");
            return false;
        }
        return true;
    }
    private void cboSKItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboSKItemStateChanged
        if (cboSK.getItemCount() > 1) {
            thanhToanTT();
        }
    }//GEN-LAST:event_cboSKItemStateChanged

    private void btnKTTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKTTTActionPerformed
        kiemTraTT();
    }//GEN-LAST:event_btnKTTTActionPerformed

    private void btnThemMoiTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemMoiTTActionPerformed
        themMoiTT();
    }//GEN-LAST:event_btnThemMoiTTActionPerformed

    private void btnHuyTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyTTActionPerformed
        dispose();
    }//GEN-LAST:event_btnHuyTTActionPerformed

    private void btnHoanThanhTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoanThanhTTActionPerformed
        hoanThanhTT();
    }//GEN-LAST:event_btnHoanThanhTTActionPerformed

    private void btnHuyChonTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyChonTTActionPerformed
        huyChonTT();
    }//GEN-LAST:event_btnHuyChonTTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThanhToanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThanhToanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThanhToanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThanhToanFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ThanhToanFrame("B01").setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHoanThanhTT;
    private javax.swing.JButton btnHuyChonTT;
    private javax.swing.JButton btnHuyTT;
    private javax.swing.JButton btnInHoaDonTT;
    private javax.swing.JButton btnKTTT;
    private javax.swing.JButton btnThemMoiTT;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboSK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblCongtien;
    private javax.swing.JLabel lblGiam;
    private javax.swing.JLabel lblGioRa;
    private javax.swing.JLabel lblGioVao;
    private javax.swing.JLabel lblOrder;
    private javax.swing.JLabel lblThanhChu;
    private javax.swing.JLabel lblThanhToan;
    private javax.swing.JLabel lblTongcong;
    private javax.swing.JLabel lblTraLai;
    private javax.swing.JRadioButton rdoNamTT;
    private javax.swing.JRadioButton rdoNuTT;
    private javax.swing.JTable tblSP;
    private javax.swing.JTextField txtEmailTT;
    private javax.swing.JTextField txtKhachDua;
    private com.toedter.calendar.JDateChooser txtNgaySinhTT;
    private javax.swing.JTextField txtSDTTT;
    private javax.swing.JTextField txtTenTT;
    // End of variables declaration//GEN-END:variables
}
