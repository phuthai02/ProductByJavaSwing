CREATE DATABASE QuanLyNhaHang;
GO
USE QuanLyNhaHang;
GO

CREATE TABLE Ban
(
    MaBan VARCHAR(10) PRIMARY KEY,
    TenBan NVARCHAR(50) NOT NULL,
    ViTri NVARCHAR(30) NOT NULL,
    TrangThai BIT NOT NULL,
    SanSang BIT NOT NULL
);

CREATE TABLE NhanVien
(
    MaNV VARCHAR(10) PRIMARY KEY,
    TenNV NVARCHAR(30) NOT NULL,
    MatKhau VARCHAR(30) NOT NULL,
    DiaChi NVARCHAR(255) NULL,
    SDT VARCHAR(15) NULL,
    Email VARCHAR(30) NULL,
    NgaySinh DATE NULL,
    GioiTinh BIT NOT NULL,
    AnhNV VARCHAR(30) NOT NULL,
    MauNen VARCHAR(30) NOT NULL,
    VaiTro BIT NOT NULL,
    TrangThai BIT NOT NULL,
);

CREATE TABLE SuKienKhuyenMai
(
    MaSKKM INT IDENTITY(1, 1) PRIMARY KEY,
    TenSKKM NVARCHAR(30) NOT NULL,
    GiaTriKM DECIMAL(5, 2) NOT NULL,
    NgayBatDau DATE NOT NULL,
    NgayKetThuc DATE NOT NULL,
    NgayTao DATE 
		DEFAULT GETDATE(),
    MaNV VARCHAR(10) NOT NULL,
    TrangThai BIT NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV),
);


CREATE TABLE KhachHang
(
    MaKH INT IDENTITY(1, 1) PRIMARY KEY,
    TenKH NVARCHAR(50) NOT NULL,
    SDT VARCHAR(15) NULL,
    Email VARCHAR(30) NULL,
    NgaySinh DATE NULL,
    GioiTinh BIT NOT NULL,
    MaNV VARCHAR(10),
    TrangThai BIT NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES NhanVien (MaNV)
);

CREATE TABLE LoaiSanPham
(
    MaLoaiSP VARCHAR(10) PRIMARY KEY,
    TenLoaiSP NVARCHAR(30) NOT NULL,
);
CREATE TABLE LoaiThucPham
(
    MaLoaiTP VARCHAR(10) PRIMARY KEY,
    TenLoaiTP NVARCHAR(30) NOT NULL,
);

CREATE TABLE SanPham
(
    MaSP VARCHAR(10) PRIMARY KEY,
    MaLoaiSP VARCHAR(10) NOT NULL,
    TenSanPham NVARCHAR(30) NOT NULL,
    DonGia MONEY NOT NULL,
    DonViTinh NCHAR(20) NOT NULL,
    AnhSP VARCHAR(30) NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    ChiTiet NVARCHAR(50) NULL,
    TrangThai BIT NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV),
    FOREIGN KEY (MaLoaiSP) REFERENCES dbo.LoaiSanPham (MaLoaiSP),
);

CREATE TABLE HoaDon
(
    MaHD INT IDENTITY(1, 1) PRIMARY KEY,
    MaKH INT NOT NULL,
    NgayTao DATE NOT NULL,
    GhiChu NVARCHAR(50) NULL,
    MaNV VARCHAR(10) NOT NULL,
    MaSKKM INT NOT NULL,
    MaBan VARCHAR(10) NOT NULL,
    TrangThai BIT NOT NULL,
    FOREIGN KEY (MaKH) REFERENCES dbo.KhachHang (MaKH),
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV),
    FOREIGN KEY (MaSKKM) REFERENCES dbo.SuKienKhuyenMai (MaSKKM),
    FOREIGN KEY (MaBan) REFERENCES dbo.Ban (MaBan),
);


CREATE TABLE HoaDonChiTiet
(
    MaHD INT NOT NULL,
    MaSP VARCHAR(10) NOT NULL,
    SoLuong INT NOT NULL,
    Gia MONEY NOT NULL,
    TrangThai BIT NOT NULL,
    PRIMARY KEY (
                    MaHD,
                    MaSP
                ),
    FOREIGN KEY (MaHD) REFERENCES dbo.HoaDon (MaHD),
    FOREIGN KEY (MaSP) REFERENCES dbo.SanPham (MaSP)
);
CREATE TABLE BangCho
(
    MaBan VARCHAR(10) NOT NULL,
    MaSP VARCHAR(10) NOT NULL,
    SoLuong INT NOT NULL,
    Gia MONEY NOT NULL,
    GioVao NVARCHAR(30) NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    HoanThanh BIT NOT NULL,
    PRIMARY KEY (
                    MaBan,
                    MaSP
                ),
    FOREIGN KEY (MaBan) REFERENCES dbo.Ban (MaBan),
    FOREIGN KEY (MaSP) REFERENCES dbo.SanPham (MaSP),
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV)
);
CREATE TABLE BanDat
(
    MaBan VARCHAR(10) NOT NULL,
    GioDat VARCHAR(50) NOT NULL,
    MaKH INT NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    PRIMARY KEY (
                    MaBan,
                    GioDat
                ),
    FOREIGN KEY (MaKH) REFERENCES dbo.KhachHang (MaKH),
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV)
);
CREATE TABLE NguyenLieu
(
    MaNL INT IDENTITY(1, 1) PRIMARY KEY,
    MaLoaiTP VARCHAR(10) NOT NULL,
    TenNL NVARCHAR(30) NOT NULL,
    Ngaymua DATE NOT NULL,
    NgayTao DATE NOT NULL,
    SoLuong INT NOT NULL,
	DonViTinh NCHAR(20) NOT NULL,
    MaNV VARCHAR(10) NOT NULL,
    moTa NVARCHAR(30) NOT NULL,
    TrangThai BIT NOT NULL,
    FOREIGN KEY (MaNV) REFERENCES dbo.NhanVien (MaNV),
    FOREIGN KEY (MaLoaiTP) REFERENCES dbo.LoaiThucPham (MaLoaiTP),
);

insert Ban values('B01',N'Bàn số 1','1',1,1);
insert Ban values('B02',N'Bàn số 2','1',1,1);
insert Ban values('B03',N'Bàn số 3','1',1,1);
insert Ban values('B04',N'Bàn số 4','1',1,1);
insert Ban values('B05',N'Bàn số 5','1',1,1);
insert Ban values('B06',N'Bàn số 6','1',1,1);
insert Ban values('B07',N'Bàn số 7','1',1,1);
insert Ban values('B08',N'Bàn số 8','2',1,1);
insert Ban values('B09',N'Bàn số 9','2',1,1);
insert Ban values('B10',N'Bàn số 10','2',1,1);
insert Ban values('B11',N'Bàn số 11','2',1,1);
insert Ban values('B12',N'Bàn số 12','2',1,1);
insert Ban values('B13',N'Bàn số 13','2',1,1);
insert Ban values('B14',N'Bàn số 14','2',1,1);
insert Ban values('B15',N'Bàn số 15','3',1,1);
insert Ban values('B16',N'Bàn số 16','3',1,1);
insert Ban values('B17',N'Bàn số 17','3',1,1);
insert Ban values('B18',N'Bàn số 18','3',1,1);
insert Ban values('B19',N'Bàn số 19','3',1,1);
insert Ban values('B20',N'Bàn số 20','3',1,1);

insert into NhanVien values ('NV01',N'Nguyễn Thị Bích','123',N'Hà Nội','0368721337','bichntph17469@fpt.edu.vn','2002-09-01',0,'chunhahang2.png','F1C232',1,1);
insert into NhanVien values ('NV02',N'Nguyễn Quang Dương','123',N'Hà Nội','0368721338','duongnqph17265@fpt.edu.vn','2002-01-13',1,'nhanvien6.png','FFF',0,1);
insert into NhanVien values ('NV03',N'Nguyễn Thị Hoa','123',N'Thanh Hóa','0368721339','hoantPH16552@fpt.edu.vn','2002-12-02',0,'nhanvien3.png','FFF',0,1);
insert into NhanVien values ('NV04',N'Đoàn Phú Thái','123',N'Hải Dương','0368721336','thaidpph17321@fpt.edu.vn','2002-05-11',1,'nhanvien9.png','FFF',0,1);
insert into NhanVien values ('NV05',N'Nguyễn Minh Dũng','123',N'Nam Định','0368721335','dungnmph17385@fpt.edu.vn','2002-03-18',1,'chucuahang1.png','F1C232',1,1);
insert into NhanVien values ('NV06',N'Dương Xuân Hương','123',N'Hà Nội','0368721334','bichntph17469@fpt.edu.vn','2002-11-23',0,'nhanvien4.png','FFF',0,1);
insert into NhanVien values ('NV07',N'Nguyễn Thúy Linh','123',N'Hà Nội','0368721332','linntph17474@fpt.edu.vn','2002-09-27',0,'nhanvien7.png','F1C232',0,1);
insert into NhanVien values ('NV08',N'Phạm Đức Ngọc','123',N'Ninh Bình','0368721331','ngocpdPH17406@fpt.edu.vn','2002-06-26',1,'nhanvien10.png','FFF',0,1);

INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Giáng sinh',30, '2021-12-01', '2021-12-12', GETDATE(), 'NV01',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Sale cuối năm',20, '2021-12-05', '2021-12-30', GETDATE(), 'NV02',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'khuyến mãi 8/3',20, '2021-03-07', '2021-03-9', GETDATE(), 'NV03',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'black friday',40, '2021-12-01', '2021-12-21', GETDATE(), 'NV01',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Sinh nhật nhà hàng',30, '2021-12-07', '2021-12-10', GETDATE(), 'NV02',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'kỉ niệm 10 năm',10, '2021-12-12', '2021-12-22', GETDATE(), 'NV03',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Tết dương lịch',20, '2022-01-01', '2022-01-03', GETDATE(), 'NV01',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Mừng năm mới',30, '2022-01-14', '2021-01-25', GETDATE(), 'NV02',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Khuyến mãi 10%',10, '2022-01-07', '2022-01-25', GETDATE(), 'NV01',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Quốc khánh',40, '2021-09-01', '2021-09-02', GETDATE(), 'NV04',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Quốc tế thiếu nhi',30, '2021-05-31', '2021-06-01', GETDATE(), 'NV05',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Sinh nhật khách',10, '2021-12-01', '2021-12-31', GETDATE(), 'NV04',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Khuyến mãi 5%',5, '2021-05-01', '2021-05-08', GETDATE(), 'NV05',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Trung thu',25, '2021-09-14', '2021-09-18', GETDATE(), 'NV02',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Khách hàng thân thiết',15, '2021-11-26', '2021-12-08', GETDATE(), 'NV04',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Ngày lễ phục sinh',10, '2021-12-26', '2021-12-26', GETDATE(), 'NV02',1)
INSERT INTO dbo.SuKienKhuyenMai(TenSKKM,GiaTriKM,NgayBatDau, NgayKetThuc,NgayTao,MaNV,TrangThai)
VALUES(N'Nhà giáo Việt Nam',20, '2021-11-20', '2021-11-20', GETDATE(), 'NV03',1)

insert khachHang values(N'Hoàng Việt Đức', '0333456196','DuchvPH17480@fpt.edu.vn','2002-09-05',1,'NV01',1);
insert khachHang values(N'Nguyễn Công Trường', '0964853798','truongncph17481@fpt.edu.vn','2002-12-06',1,'NV05',1);
insert khachHang values(N'Hoàng Minh Nghĩa', '0975815593','nghiahmph17430@fpt.edu.vn','2002-03-14',1,'NV06',1);
insert khachHang values(N'Nguyễn Lê Hải', '0368721331','hainlph17488@fpt.edu.vn','2002-03-08',1,'NV01',1);
insert khachHang values(N'Nguyễn Trí Tuệ', '0359341896','tuentPH17259@fpt.edu.vn','2002-09-29',1,'NV02',1);
insert khachHang values(N'Nguyễn Bá Hà', '0325878373','hanbPH17245@fpt.edu.vn','2002-03-16',1,'NV03',1);
insert khachHang values(N'Đỗ Ngọc Sơn', '0368721310','sondnph17452@fpt.edu.vn','2002-09-18',1,'NV02',1);
insert khachHang values(N'Nguyễn Anh Tú', '0368721311','tunaph17499@fpt.edu.vn','2002-09-04',1,'NV01',1);
insert khachHang values(N'Đào Trường Sơn', '0368721312','truonsdph17537@fpt.edu.vn','2002-12-15',1,'NV07',1);
insert khachHang values(N'Nguyễn Đức Huy', '0981263224','huyndph17533@fpt.edu.vn','2002-03-14',1,'NV08',1);
insert khachHang values(N'Phạm Văn Đại', '0368721315','daipvph17321@fpt.edu.vn','2002-03-08',1,'NV05',1);
insert khachHang values(N'Nguyễn Duy Phương', '0368721313','phuonndph17278@fpt.edu.vn','2002-09-29',1,'NV06',1);
insert khachHang values(N'Nguyễn Văn Đương', '0368721317','duongnvph17448@fpt.edu.vn','2002-03-16',1,'NV03',1);
insert khachHang values(N'Vũ Xuân Dũng', '0353323512','dungvxph17264@fpt.edu.vn','2002-09-18',1,'NV02',1);
insert khachHang values(N'Trần Thị Hồng Nhung', '0394699202','nhungtthph17444@fpt.edu.vn','2002-09-18',1,'NV02',1);
insert khachHang values(N'Mai Văn Tùng', '0386599712','tungmvph17575@fpt.edu.vn','2002-09-04',1,'NV01',1);
insert khachHang values(N'Nguyễn Bá Doanh', '0988040547','doanhnbph17426@fpt.edu.vn','2002-12-15',1,'NV07',1);
insert khachHang values(N'Trần Huy Hoàng', '0911151581','hoangthph17350@fpt.edu.vn','2002-03-14',1,'NV08',1);
insert khachHang values(N'Trịnh Tiến Lực', '0397573143','lucttph17307@fpt.edu.vn','2002-03-08',1,'NV05',1);
insert khachHang values(N'Nguyễn Tiến Hải', '347766383','haintph17446@fpt.edu.vn','2002-09-29',1,'NV06',1);
insert khachHang values(N'Phạm Văn Hậu', '0328754899','haupvph17311@fpt.edu.vn','2002-03-16',1,'NV03',1);
insert khachHang values(N'Nguyễn Phú Quang', '0936352884','quangnpph17417@fpt.edu.vn','2002-09-18',1,'NV02',1);

INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('MKV', N'Món khai vị')
INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('MAK', N'Món ăn kèm')
INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('MTM', N'Món tráng miệng')
INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('GK', N'Giải khát')
INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('MC', N'Món chính')
INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('HQ', N'Hoa quả')
INSERT INTO LoaiSanPham (MaLoaiSP, TenLoaiSP) VALUES ('DB', N'Đặt Bàn')



INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('PDB', 'DB', N'Phí Đặt Bàn', 100000, N'Bàn', 'logos.png', 'NV01', N'Khách đặt lịch',1)

INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP02','GK', N'Nước chanh leo', 39000, N'ly', 'GK_ChanhLeo.png', 'NV01', N'Nước giải khát được làm từ chanh leo',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP03','GK', N'Nước ép dưa hấu', 39000, N'ly', 'GK_DuaHau.png', 'NV01', N'Nước ép dưa hấu mát lạnh',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP04','GK', N'Strong bow vị táo', 45000, N'chai', 'GK_SBViTao.png', 'NV01', N'Đồ uống có cồn',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP05','GK', N'Strong bow vị mật ong', 45000, N'chai', 'GK_SBMatOng.png', 'NV01', N'Đồ uống có cồn',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP06','GK', N'Strong bow vị dâu', 45000, N'chai', 'GK_SBViDau.png', 'NV01', N'Đồ uống có cồn',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP07','GK', N'Trà hoa cúc mật ong', 55000, N'ly', 'GK_HoaCucMatOng.png', 'NV01', N'Đồ thanh mát',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP08','GK', N'Cà phê sữa nóng', 59000, N'tách', 'GK_CaPheSuaNong.png', 'NV01', N'Cà phê nguyên chất',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP09','GK', N'Cà phê nguyên chất', 79000, N'tách', 'GK_CaPheNguyenChat.png', 'NV01', N'Cà phê nguyên chất',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP10','GK', N'Nước cam cà rốt', 59000, N'ly', 'GK_NuocCamCaRot.png', 'NV01', N'Nước cam ép cùng cà rốt',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP46', 'GK', N'Nước cam nguyên chất', 55000, N'ly', 'GK_CamEp.png', 'NV01', N'Nước giải khát được cam ép nguyên chất 99% ',1)

INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP11', 'HQ', N'Táo', 15000, N'đĩa', 'HQ_Tao.png', 'NV05', N'Táo nhập khẩu',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP12', 'HQ', N'Cam', 25000, N'đĩa', 'HQ_Cam.png', 'NV05', N'Cam ngọt',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP13', 'HQ', N'Quýt', 20000, N'đĩa', 'HQ_Quyt.png', 'NV05', N'Quýt ngọt',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP14', 'HQ', N'Nho', 55000, N'đĩa', 'HQ_Nho.png', 'NV05', N'Nho nhập khẩu',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP15', 'HQ', N'Bưởi', 15000, N'đĩa', 'HQ_Buoi.png', 'NV05', N'Bưởi ngọt',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP16', 'HQ', N'Dưa hấu', 25000, N'đĩa', 'HQ_DuaHau.png', 'NV05', N'Dưa hấu tại vườn',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP17', 'HQ', N'Thanh Long', 15000, N'đĩa', 'HQ_ThanhLong.png', 'NV05', N'Thanh long tại vườn',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP18', 'HQ', N'Dâu tây', 79000, N'đĩa', 'HQ_DauTay.png', 'NV05', N'Dâu tây nhập khẩu',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP19', 'HQ', N'Mix', 159000, N'đĩa', 'HQ_Mix.png', 'NV05', N'Được mix nhiều loại',1)

INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP20', 'MAK', N'KimPap', 109000, N'đĩa', 'MAK_KimPap.png', 'NV05', N'Món ăn kèm',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP21', 'MAK', N'Kim chi', 80000, N'đĩa', 'MAK_KimChi.png', 'NV05', N'Món ăn kèm',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP22', 'MAK', N'Cơm', 20000, N'bát', 'MAK_Com.png', 'NV05', N'Món ăn kèm',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP23', 'MAK', N'Ngô chiên', 59000, N'đĩa', 'MAK_NgoChien.png', 'NV05', N'Món ăn kèm',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP24', 'MAK', N'Khoai chiên', 79000, N'đĩa', 'MAK_KhoaiChien.png', 'NV05', N'Món ăn kèm',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP25', 'MAK', N'Bánh bao chiên', 15000, N'cái', 'MAK_BanhBaoChien.png', 'NV05', N'Món ăn kèm',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP26', 'MAK', N'Bánh mỳ chiên', 10000, N'cái', 'MAK_BanhMyChien.png', 'NV05', N'Món ăn kèm',1)

INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP27', 'MKV', N'salad cà chua dưa chuột',89000, N'đĩa', 'MKV_SCDua.png', 'NV05', N'Món khai vị',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP28', 'MKV', N'salad thịt bò', 199000, N'đĩa', 'MKV_SLBo.png', 'NV05', N'Món khai vị',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP29', 'MKV', N'gỏi bồn bồn tôm thịt', 189000, N'đĩa', 'MKV_BB.png', 'NV05', N'Món khai vị',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP30', 'MKV', N'gỏi cuốn tôm thịt', 159000, N'đĩa', 'MKV_GoiCuonTom.png', 'NV05',N'Món khai vị',1)

INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP31', 'MC', N'mực hấp gừng hành',219000, N'đĩa', 'MC_MucHapGungXa.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP32', 'MC', N'Mực xào cần tỏi', 219000, N'đĩa', 'MC_MucOng.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP33', 'MC', N'Mực tươi nướng xả  ớt', 219000, N'đĩa', 'MC_MucNuong.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP34', 'MC', N'Mực nướng ngũ vị', 219000, N'đĩa', 'MC_MucNuongNguVi.png', 'NV01',N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP35', 'MC', N'Mực một nắng nướng',219000, N'đĩa', 'MC_MucMotNang.png', 'NV01',N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP36', 'MC', N'Nghiêu hấp Thái', 199000, N'đĩa', 'MC_nghieu.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP37', 'MC', N'Gà hầm thuốc bắc', 290000, N'đĩa', 'MC_GaHam.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP38', 'MC', N'Gà rang muối', 200000, N'đĩa', 'MC_GaRangMuoi.png', 'NV01',N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP39', 'MC', N'Set lẩu gà đen Hmong', 790000, N'nồi', 'MC_GaHmong.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP40', 'MC', N'Set nướng thập cẩm', 590000, N'nồi', 'MC_SetLau.png', 'NV01', N'Món chính',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP41', 'MC', N'Set lẩu thập cẩm', 590000, N'nồi', 'MC_SetNuong.png', 'NV01',N'Món chính',1)

INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP42', 'MTM', N'Chè khoai dẻo',25000, N'bát', 'MTM_Che.png', 'NV05', N'Món tráng miệng',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP43', 'MTM', N'Bánh bông lan cuộn', 35000, N'cái', 'MTM_BBL.png', 'NV05',N'Món tráng miệng',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP44', 'MTM', N'Bánh kem dâu chocolate', 59000, N'đĩa', 'MTM_BKDCKL.png', 'NV05',N'Món tráng miệng',1)
INSERT INTO SanPham(MaSP, MaLoaiSP, TenSanPham, DonGia, DonViTinh, AnhSP, MaNV, ChiTiet, TrangThai)
VALUES ('SP45', 'MTM', N'Tổ yến tiềm trái dừa', 380000, N'bát', 'MTM_ToYen.png', 'NV05',N'Món tráng miệng',1)

insert into LoaiThucPham values ('LTP01',N'Tươi sống')
insert into LoaiThucPham values ('LTP02',N'Đông lạnh')
insert into LoaiThucPham values ('LTP03',N'Rau xanh')
insert into LoaiThucPham values ('LTP04',N'Đồ khô')
insert into LoaiThucPham values ('LTP05',N'Đóng hộp')

insert NguyenLieu values('LTP01','Mực ống','2021-12-05','2021-12-04',10,N'Kg','NV02',N'Mực dạng ống',1)
insert NguyenLieu values('LTP03','Cần tây','2021-12-05','2021-12-04',4,N'Kg','NV02',N'cần tây tươi',1)
insert NguyenLieu values('LTP03','Rau cải thảo','2021-12-05','2021-12-04',5,N'Kg','NV02',N'cải thảo tươi',1)
insert NguyenLieu values('LTP02','Bò mỹ','2021-12-05','2021-12-04',10,N'Kg','NV02',N'Bò mỹ',1)
insert NguyenLieu values('LTP05','Strong bow vị táo','2021-12-05','2021-12-04',4,N'Thùng','NV02',N'vị táo',1)
insert NguyenLieu values('LTP01','Gà mái','2021-12-05','2021-12-04',10,N'con','NV02',N'Gà',1)

 insert hoadon values(1,'2021-12-05',null,'NV01',2,'B01',1)
 insert hoadon values(2,'2021-12-05',null,'NV01',2,'B02',1)
 insert hoadon values(5,'2021-12-05',null,'NV01',4,'B04',1)
 insert hoadon values(3,'2021-12-05',null,'NV01',4,'B05',1)

insert HoaDonChiTiet values(1,'SP46',2,55000,1)
insert HoaDonChiTiet values(1,'SP44',1,59000,1)
insert HoaDonChiTiet values(1,'SP40',1,590000,1)
insert HoaDonChiTiet values(1,'SP28',2,199000,1)
insert HoaDonChiTiet values(1,'SP42',2,25000,1)

insert HoaDonChiTiet values(2,'SP31',4,219000,1)
insert HoaDonChiTiet values(2,'SP43',3,35000,1)
insert HoaDonChiTiet values(2,'SP45',1,380000,1)
insert HoaDonChiTiet values(2,'SP41',1,590000,1)
insert HoaDonChiTiet values(2,'SP34',2,219000,1)

insert HoaDonChiTiet values(3,'SP37',1,290000,1)
insert HoaDonChiTiet values(3,'SP36',2,199000,1)
insert HoaDonChiTiet values(3,'SP44',2,59000,1)
insert HoaDonChiTiet values(3,'SP06',2,45000,1)

insert HoaDonChiTiet values(5,'SP03',3,39000,1)
insert HoaDonChiTiet values(5,'SP13',3,20000,1)
insert HoaDonChiTiet values(5,'SP23',2,59000,1)
insert HoaDonChiTiet values(5,'SP38',2,200000,1)

select*from Ban
select*from NhanVien
select*from SuKienKhuyenMai
select*from khachhang
select*from LoaiSanPham
select*from sanpham
select*from LoaiThucPham
select*from NguyenLieu
select*from HoaDon
select*from HoaDonChiTiet
