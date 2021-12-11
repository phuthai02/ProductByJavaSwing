/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import DAO.ThongKeDAO;
import Untils.Xcurrency;
import Untils.Xdate;
import static Untils.Xjfreechart.chartBaoCao;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author doanp
 */
public class IF_ThongKe extends javax.swing.JInternalFrame {

    /**
     * Creates new form IF_ThongKe
     */
    DefaultTableModel modelSP;
    DefaultTableModel modelNL;
    DefaultTableModel modelKH;
    DefaultTableModel modelNV;
    DefaultTableModel modelDT;

    public IF_ThongKe(int indexTab) {
        initComponents();
        init();
        tabs.setSelectedIndex(indexTab);
    }

    void init() {
        setSize(1200, 700);
        setResizable(false);
        setTitle("THỐNG KÊ");
        setDefaultCloseOperation(2);
        prepareGUI();
    }

    void prepareGUI() {
        setTableDT();
        setTableKH();
        setTableNL();
        setTableNV();
        setTableSP();
        fillTableSP();
        fillTableNL();
        fillTableKH();
        fillTableNV();
        fillTableDT();
    }

    void setTableSP() {
        String h[] = {"Mã SP", "Tên SP", "Đơn giá", "Số lượng bán được", "Doanh thu mang lại"};
        modelSP = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSP.setModel(modelSP);
    }

    void fillTableSP() {
        modelSP.setRowCount(0);
        List<Object[]> lst = new ThongKeDAO().getTKSP(txtBSP.getDate() == null ? "2000-01-01" : Xdate.toString(txtBSP.getDate(), "yyyy-MM-dd"),
                txtESP.getDate() == null ? "2200-01-01" : Xdate.toString(txtESP.getDate(), "yyyy-MM-dd"));
        for (Object[] obj : lst) {
            modelSP.addRow(new Object[]{obj[0], obj[1], Xcurrency.toCurrency((int) obj[2]), obj[3], Xcurrency.toCurrency((int) obj[4])});
        }
        fillChartPanelToPanel(pnlChartSP, chartBaoCao(lst, "THỐNG KÊ SẢN PHẨM", "SẢN PHẨM", "SỐ TIỀN(VND)", 1, 4, "ĐỒNG"));
    }

    void fillChartPanelToPanel(JPanel pn, ChartPanel cpn) {
        pn.setLayout(new BorderLayout());
        pn.removeAll();
        pn.add(cpn);
    }

    void setTableNL() {
        String h[] = {"Mã nguyên liệu", "Tên nguyên liệu", "Số lượng mua", "Giá mua", "Tổng chi"};
        modelNL = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblNL.setModel(modelNL);
    }

    void fillTableNL() {
        modelNL.setRowCount(0);
        List<Object[]> lst = new ThongKeDAO().getTKNL(txtBNL.getDate() == null ? "2000-01-01" : Xdate.toString(txtBNL.getDate(), "yyyy-MM-dd"),
                txtENL.getDate() == null ? "2200-01-01" : Xdate.toString(txtENL.getDate(), "yyyy-MM-dd"));
        for (Object[] obj : lst) {
            modelNL.addRow(new Object[]{obj[0], obj[1], obj[2], Xcurrency.toCurrency((int) obj[3]), Xcurrency.toCurrency((int) obj[4])});
        }
        fillChartPanelToPanel(pnlChartNL, chartBaoCao(lst, "THỐNG KÊ NGUYÊN LIỆU", "NGUYÊN LIỆU", "SỐ LƯỢNG", 1, 4, "ĐỒNG"));
    }

    void setTableKH() {
        String h[] = {"Mã KH", "Tên khách hàng", "Số lượng sử dụng", "Doanh thu đem lại"};
        modelKH = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblKH.setModel(modelKH);
    }

    void fillTableKH() {
        modelKH.setRowCount(0);
        List<Object[]> lst = new ThongKeDAO().getTKKH(txtBKH.getDate() == null ? "2000-01-01" : Xdate.toString(txtBKH.getDate(), "yyyy-MM-dd"),
                txtEKH.getDate() == null ? "2200-01-01" : Xdate.toString(txtEKH.getDate(), "yyyy-MM-dd"));
        for (Object[] obj : lst) {
            modelKH.addRow(new Object[]{obj[0], obj[1], obj[2], Xcurrency.toCurrency((int) obj[3])});
        }
        fillChartPanelToPanel(pnlChartKH, chartBaoCao(lst, "THỐNG KÊ KHÁCH HÀNG", "KHÁCH HÀNG", "SỐ TIỀN(VND)", 1, 3, "DỒNG"));
    }

    void setTableNV() {
        String h[] = {"Mã NV", "Tên nhân viên", "Số hóa đơn"};
        modelNV = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblNV.setModel(modelNV);
    }

    void fillTableNV() {
        modelNV.setRowCount(0);
        List<Object[]> lst = new ThongKeDAO().getTKNV(txtBNV.getDate() == null ? "2000-01-01" : Xdate.toString(txtBNV.getDate(), "yyyy-MM-dd"),
                txtENV.getDate() == null ? "2200-01-01" : Xdate.toString(txtENV.getDate(), "yyyy-MM-dd"));
        for (Object[] obj : lst) {
            modelNV.addRow(new Object[]{obj[0], obj[1], obj[2]});
        }
        fillChartPanelToPanel(pnlChartNV, chartBaoCao(lst, "THỐNG KÊ NHÂN VIÊN", "NHÂN VIÊN", "SỐ HÓA ĐƠN", 1, 2, "LẦN"));
    }

    void setTableDT() {
        String h[] = {"Hóa đơn cao nhất", "Hóa đơn nhập cao nhất", "Tổng thu", "Tổng chi", "Lợi nhuận"};
        modelDT = new DefaultTableModel(h, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblDT.setModel(modelDT);
    }

    void fillTableDT() {
        modelDT.setRowCount(0);
        Object[] obj = new ThongKeDAO().getTKDT(txtBDT.getDate() == null ? "2000-01-01" : Xdate.toString(txtBDT.getDate(), "yyyy-MM-dd"),
                txtEDT.getDate() == null ? "2200-01-01" : Xdate.toString(txtEDT.getDate(), "yyyy-MM-dd"));
        modelDT.addRow(new Object[]{Xcurrency.toCurrency((int) obj[0]),
            Xcurrency.toCurrency((int) obj[1]),
            Xcurrency.toCurrency((int) obj[2]),
            Xcurrency.toCurrency((int) obj[3]),
            Xcurrency.toCurrency((int) obj[2] - (int) obj[3])});
        List<Object[]> lst = new ArrayList<>();
        lst.add(new Object[]{"Tổng thu", obj[2]});
        lst.add(new Object[]{"Tổng chi", obj[3]});
        lst.add(new Object[]{"Lợi nhuận", (int) obj[2] - (int) obj[3]});
        fillChartPanelToPanel(pnlChartDT, chartBaoCao(lst, "THỐNG KÊ DOANH THU", "TỔNG THU CHI NHÀ HÀNG", "SỐ TIỀN(VND)", 0, 1, "ĐỒNG"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        pnl = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSP = new javax.swing.JTable();
        txtBSP = new com.toedter.calendar.JDateChooser();
        txtESP = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtLSP = new javax.swing.JButton();
        pnlChartSP = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtBNL = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtENL = new com.toedter.calendar.JDateChooser();
        btnLNL = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNL = new javax.swing.JTable();
        pnlChartNL = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtBKH = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtEKH = new com.toedter.calendar.JDateChooser();
        btnLKH = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKH = new javax.swing.JTable();
        pnlChartKH = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtBNV = new com.toedter.calendar.JDateChooser();
        jLabel9 = new javax.swing.JLabel();
        txtENV = new com.toedter.calendar.JDateChooser();
        btnLNV = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblNV = new javax.swing.JTable();
        pnlChartNV = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtBDT = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        txtEDT = new com.toedter.calendar.JDateChooser();
        btnLDT = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDT = new javax.swing.JTable();
        pnlChartDT = new javax.swing.JPanel();

        setClosable(true);

        jLabel1.setBackground(new java.awt.Color(241, 194, 50));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TOP 5 SẢN PHẨM");
        jLabel1.setOpaque(true);

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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("đến");

        txtLSP.setText("Lọc");
        txtLSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlChartSPLayout = new javax.swing.GroupLayout(pnlChartSP);
        pnlChartSP.setLayout(pnlChartSPLayout);
        pnlChartSPLayout.setHorizontalGroup(
            pnlChartSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartSPLayout.setVerticalGroup(
            pnlChartSPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlLayout = new javax.swing.GroupLayout(pnl);
        pnl.setLayout(pnlLayout);
        pnlLayout.setHorizontalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addComponent(txtBSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addGap(12, 12, 12)
                        .addComponent(txtESP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtLSP)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChartSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLayout.setVerticalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlLayout.createSequentialGroup()
                        .addGroup(pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(txtBSP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtESP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLSP))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                    .addComponent(pnlChartSP, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Sản phẩm", pnl);

        jLabel2.setBackground(new java.awt.Color(241, 194, 50));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TOP 5 THỰC PHẨM");
        jLabel2.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("đến");

        btnLNL.setText("Lọc");
        btnLNL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLNLActionPerformed(evt);
            }
        });

        tblNL.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblNL);

        javax.swing.GroupLayout pnlChartNLLayout = new javax.swing.GroupLayout(pnlChartNL);
        pnlChartNL.setLayout(pnlChartNLLayout);
        pnlChartNLLayout.setHorizontalGroup(
            pnlChartNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartNLLayout.setVerticalGroup(
            pnlChartNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtBNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(12, 12, 12)
                        .addComponent(txtENL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLNL))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChartNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtENL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLNL, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                    .addComponent(pnlChartNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Nguyên liệu", jPanel2);

        jLabel3.setBackground(new java.awt.Color(241, 194, 50));
        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TOP 5 KHÁCH HÀNG");
        jLabel3.setOpaque(true);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("đến");

        btnLKH.setText("Lọc");
        btnLKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLKHActionPerformed(evt);
            }
        });

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tblKH);

        javax.swing.GroupLayout pnlChartKHLayout = new javax.swing.GroupLayout(pnlChartKH);
        pnlChartKH.setLayout(pnlChartKHLayout);
        pnlChartKHLayout.setHorizontalGroup(
            pnlChartKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartKHLayout.setVerticalGroup(
            pnlChartKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtBKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addGap(12, 12, 12)
                        .addComponent(txtEKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLKH))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChartKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBKH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLKH, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                    .addComponent(pnlChartKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Khách hàng", jPanel3);

        jLabel4.setBackground(new java.awt.Color(241, 194, 50));
        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TOP 5 NHÂN VIÊN");
        jLabel4.setOpaque(true);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("đến");

        btnLNV.setText("Lọc");
        btnLNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLNVActionPerformed(evt);
            }
        });

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane4.setViewportView(tblNV);

        javax.swing.GroupLayout pnlChartNVLayout = new javax.swing.GroupLayout(pnlChartNV);
        pnlChartNV.setLayout(pnlChartNVLayout);
        pnlChartNVLayout.setHorizontalGroup(
            pnlChartNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartNVLayout.setVerticalGroup(
            pnlChartNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtBNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)
                        .addGap(12, 12, 12)
                        .addComponent(txtENV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLNV))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChartNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtENV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLNV, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                    .addComponent(pnlChartNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Nhân viên", jPanel4);

        jLabel5.setBackground(new java.awt.Color(241, 194, 50));
        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 153));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TỔNG THU CHI NHÀ HÀNG");
        jLabel5.setOpaque(true);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("đến");

        btnLDT.setText("Lọc");
        btnLDT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLDTActionPerformed(evt);
            }
        });

        tblDT.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane5.setViewportView(tblDT);

        javax.swing.GroupLayout pnlChartDTLayout = new javax.swing.GroupLayout(pnlChartDT);
        pnlChartDT.setLayout(pnlChartDTLayout);
        pnlChartDTLayout.setHorizontalGroup(
            pnlChartDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartDTLayout.setVerticalGroup(
            pnlChartDTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtBDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(12, 12, 12)
                        .addComponent(txtEDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLDT))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlChartDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtBDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtEDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnLDT, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
                    .addComponent(pnlChartDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Doanh thu", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, 992, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLSPActionPerformed
        fillTableSP();
    }//GEN-LAST:event_txtLSPActionPerformed

    private void btnLNLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLNLActionPerformed
        fillTableNL();
    }//GEN-LAST:event_btnLNLActionPerformed

    private void btnLKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLKHActionPerformed
        fillTableKH();
    }//GEN-LAST:event_btnLKHActionPerformed

    private void btnLNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLNVActionPerformed
        fillTableNV();
    }//GEN-LAST:event_btnLNVActionPerformed

    private void btnLDTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLDTActionPerformed
       fillTableDT();
    }//GEN-LAST:event_btnLDTActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLDT;
    private javax.swing.JButton btnLKH;
    private javax.swing.JButton btnLNL;
    private javax.swing.JButton btnLNV;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPanel pnl;
    private javax.swing.JPanel pnlChartDT;
    private javax.swing.JPanel pnlChartKH;
    private javax.swing.JPanel pnlChartNL;
    private javax.swing.JPanel pnlChartNV;
    private javax.swing.JPanel pnlChartSP;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblDT;
    private javax.swing.JTable tblKH;
    private javax.swing.JTable tblNL;
    private javax.swing.JTable tblNV;
    private javax.swing.JTable tblSP;
    private com.toedter.calendar.JDateChooser txtBDT;
    private com.toedter.calendar.JDateChooser txtBKH;
    private com.toedter.calendar.JDateChooser txtBNL;
    private com.toedter.calendar.JDateChooser txtBNV;
    private com.toedter.calendar.JDateChooser txtBSP;
    private com.toedter.calendar.JDateChooser txtEDT;
    private com.toedter.calendar.JDateChooser txtEKH;
    private com.toedter.calendar.JDateChooser txtENL;
    private com.toedter.calendar.JDateChooser txtENV;
    private com.toedter.calendar.JDateChooser txtESP;
    private javax.swing.JButton txtLSP;
    // End of variables declaration//GEN-END:variables
}
