SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE applications;
TRUNCATE TABLE bill_items;
TRUNCATE TABLE bills;
TRUNCATE TABLE dormitories;
TRUNCATE TABLE managers;
TRUNCATE TABLE notifications;
TRUNCATE TABLE payments;
TRUNCATE TABLE room_assignments;
TRUNCATE TABLE room_types;
TRUNCATE TABLE rooms;
TRUNCATE TABLE service_usage;
TRUNCATE TABLE services;
TRUNCATE TABLE student_service_registrations;
TRUNCATE TABLE students;
TRUNCATE TABLE users;
TRUNCATE TABLE billing_schedules;
TRUNCATE TABLE utility_billing_schedule;
SET FOREIGN_KEY_CHECKS = 1;


-- BẮT ĐẦU GIAO DỊCH (transaction)
START TRANSACTION;
-- =========================================
-- 1. Thêm dữ liệu cho bảng `users`
-- (3 manager, 17 student => tổng 20 người dùng)
-- =========================================
INSERT INTO users (user_id, created_at, password_hash, role, status, updated_at, username)
VALUES
    -- 3 MANAGERS
    (1,  '2025-01-01 10:00:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'manager', 'active', '2025-01-02 10:00:00', 'manager1'),
    (2,  '2025-01-01 10:10:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'manager', 'active', '2025-01-02 10:10:00', 'manager2'),
    (3,  '2025-01-01 10:20:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'manager', 'active', '2025-01-02 10:20:00', 'manager3'),
    -- 17 STUDENTS (id từ 4 đến 20)
    (4,  '2025-01-01 11:00:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:00:00', 'student4'),
    (5,  '2025-01-01 11:10:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:10:00', 'student5'),
    (6,  '2025-01-01 11:20:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:20:00', 'student6'),
    (7,  '2025-01-01 11:30:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:30:00', 'student7'),
    (8,  '2025-01-01 11:40:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:40:00', 'student8'),
    (9,  '2025-01-01 11:50:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 11:50:00', 'student9'),
    (10, '2025-01-01 12:00:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 12:00:00', 'student10'),
    (11, '2025-01-01 12:10:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 12:10:00', 'student11'),
    (12, '2025-01-01 12:20:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 12:20:00', 'student12'),
    (13, '2025-01-01 12:30:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 12:30:00', 'student13'),
    (14, '2025-01-01 12:40:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 12:40:00', 'student14'),
    (15, '2025-01-01 12:50:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 12:50:00', 'student15'),
    (16, '2025-01-01 13:00:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 13:00:00', 'student16'),
    (17, '2025-01-01 13:10:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 13:10:00', 'student17'),
    (18, '2025-01-01 13:20:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 13:20:00', 'student18'),
    (19, '2025-01-01 13:30:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 13:30:00', 'student19'),
    (20, '2025-01-01 13:40:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6','student', 'active', '2025-01-02 13:40:00', 'student20')
;




-- =========================================
-- 2. Thêm dữ liệu cho bảng `managers`
-- (manager_id trùng với user_id của người dùng có role = 'manager')
-- =========================================
INSERT INTO managers (manager_id, department, email, full_name, phone_number, position)
VALUES
    (1, 'Phòng Công tác SV', 'manager1@univ.edu', 'Manager One',   '0909000001', 'Trưởng phòng'),
    (2, 'Phòng Công tác SV', 'manager2@univ.edu', 'Manager Two',   '0909000002', 'Phó phòng'),
    (3, 'Phòng Quản lý KTX', 'manager3@univ.edu', 'Manager Three', '0909000003', 'Nhân viên');

-- =========================================
-- 3. Thêm dữ liệu cho bảng `students`
-- (student_id trùng với user_id của người dùng có role = 'student')
-- 17 sinh viên với id từ 4 -> 20
-- =========================================
INSERT INTO students
(student_id, address, date_of_birth, department, email, full_name, gender, phone_number, student_class)
VALUES
    (4,  '123 ABC St', '2003-05-10', 'CNTT',   'nh.minh0403@gmail.com',  'Student Four',   'Nam', '0901000004', 'CNTT1'),
    (5,  '123 ABC St', '2003-04-11', 'CNTT',   NULL,  'Student Five',   'Nam', '0901000005', 'CNTT1'),
    (6,  '234 BCD St', '2002-03-12', 'CNTT',   NULL,  'Student Six',    'Nam', '0901000006', 'CNTT2'),
    (7,  '234 BCD St', '2002-02-05', 'CNTT',   NULL,  'Student Seven',  'Nữ',  '0901000007', 'CNTT2'),
    (8,  '345 CDE St', '2003-01-05', 'KinhTe', NULL,  'Student Eight',  'Nữ',  '0901000008', 'KT1'),
    (9,  '345 CDE St', '2003-08-09', 'KinhTe', NULL,  'Student Nine',   'Nam', '0901000009', 'KT1'),
    (10, '456 DEF St', '2003-07-07', 'KinhTe', NULL, 'Student Ten',    'Nữ',  '0901000010', 'KT2'),
    (11, '456 DEF St', '2003-06-08', 'CNTT',   NULL, 'Student Eleven', 'Nam', '0901000011', 'CNTT3'),
    (12, '567 EFG St', '2004-12-01', 'XHNV',   NULL, 'Student Twelve', 'Nữ',  '0901000012', 'XH1'),
    (13, '567 EFG St', '2002-11-02', 'XHNV',   NULL, 'Student Thirteen','Nữ', '0901000013', 'XH1'),
    (14, '678 FGH St', '2002-10-03', 'KinhTe', NULL, 'Student Fourteen','Nam','0901000014', 'KT3'),
    (15, '789 GHI St', '2002-09-04', 'XHNV',   NULL, 'Student Fifteen','Nữ',  '0901000015', 'XH2'),
    (16, '789 GHI St', '2002-08-05', 'CNTT',   NULL, 'Student Sixteen','Nam', '0901000016', 'CNTT4'),
    (17, '876 HIJ St', '2002-07-06', 'KinhTe', NULL, 'Student Seventeen','Nữ','0901000017', 'KT4'),
    (18, '987 IJK St', '2002-06-07', 'KinhTe', NULL, 'Student Eighteen','Nam','0901000018', 'KT5'),
    (19, '987 IJK St', '2002-05-08', 'CNTT',   NULL, 'Student Nineteen','Nam','0901000019', 'CNTT5'),
    (20, '987 IJK St', '2002-12-25', 'KinhTe', 'nh.minh0403@gmail.com', 'Student Twenty',  'Nữ', '0901000020', 'KT7');



-- =========================================
-- 4. Thêm dữ liệu cho bảng `dormitories`
-- =========================================
INSERT INTO dormitories (dorm_id, address, description, dorm_name)
VALUES
    (1, 'Khu A, Đường 1', 'Gần căn tin',        'KTX Khu A'),
    (2, 'Khu B, Đường 2', 'Cạnh sân vận động',  'KTX Khu B');

-- =========================================
-- 5. Thêm dữ liệu cho bảng `room_types`
--  Mỗi khu tạo 2 loại phòng, ví dụ: 4 người, 6 người
-- =========================================
INSERT INTO room_types (room_type_id, gender, max_capacity, price, type_name, dorm_id)
VALUES
    (1, 'Nam', 4, 500000, 'Phòng 4 nam',   1),
    (2, 'Nữ', 4, 550000, 'Phòng 4 nữ',    1),
    (3, 'Nam', 6, 300000, 'Phòng 6 nam',  2),
    (4, 'Nữ', 6, 350000, 'Phòng 6 nữ',    2);

-- =========================================
-- 6. Thêm dữ liệu cho bảng `rooms`
--  Giả sử mỗi dorm có 2 phòng
-- =========================================
INSERT INTO rooms (room_id, current_occupancy, description, room_number, status, dorm_id, room_type_id)
VALUES
    (1,  0, 'Phòng trống', 'A101', 'available', 1, 1),  -- KTX Khu A, Loại phòng 4 nam
    (2,  0, 'Phòng trống', 'A102', 'available', 1, 2),  -- KTX Khu A, Loại phòng 4 nữ
    (3,  0, 'Phòng trống', 'B201', 'available', 2, 3),  -- KTX Khu B, Loại phòng 6 nam
    (4,  0, 'Phòng trống', 'B202', 'available', 2, 4);  -- KTX Khu B, Loại phòng 6 nữ

-- =========================================
-- 7. Thêm dữ liệu cho bảng `services`
--  Tạo một số dịch vụ để test tạo hóa đơn
-- =========================================
INSERT INTO services (service_id, description, service_name, service_type, unit, unit_price, visible_for_student)
VALUES
    (1,  'Internet cáp quang',              'Internet',           'ROOM',     'tháng', 100000, TRUE),
    (2,  'Dịch vụ giặt ủi',                 'Giặt ủi',            'PERSONAL', 'lần',   20000,  TRUE),
    (3,  'Điện sinh hoạt',                  'Điện',               'ROOM',     'kWh',   3000,   FALSE),
    (4,  'Nước sinh hoạt',                  'Nước',               'ROOM',     'm3',    7000,   FALSE),
    (5,  'Phòng ở cho sinh viên',          'Phòng ở',            'PERSONAL', 'tháng', 1000,   FALSE),
    (6,  'Thay gas bình',                   'Thay gas',           'ROOM',     'bình',  450000, TRUE),
    (7,  'Dịch vụ sửa chữa nhỏ',            'Sửa chữa',           'ROOM',     'lần',   50000,  FALSE),
    (8,  'In ấn tài liệu A4 đen trắng',     'In A4 trắng đen',    'PERSONAL', 'trang', 500,    TRUE),
    (9,  'In ấn tài liệu A4 màu',           'In A4 màu',          'PERSONAL', 'trang', 1500,   TRUE),
    (10, 'Phí gửi xe đạp',                 'Gửi xe đạp',         'PERSONAL', 'tháng', 10000,  TRUE),
    (11, 'Phí gửi xe máy',                 'Gửi xe máy',         'PERSONAL', 'tháng', 50000,  TRUE),
    (12, 'Mượn sách thư viện',             'Mượn sách',          'PERSONAL', 'lượt',  5000,      TRUE),
    (13, 'Dọn vệ sinh định kỳ phòng',       'Dọn vệ sinh phòng',  'ROOM',     'tháng', 80000,  FALSE),
    (14, 'Tư vấn tâm lý sinh viên',         'Tư vấn tâm lý',      'PERSONAL', 'buổi',  100000,      TRUE),
    (15, 'Sử dụng phòng học nhóm',          'Phòng học nhóm',     'PERSONAL', 'giờ',   20000,      TRUE);



-- 1. Thêm đơn đăng ký ở ký túc xá đã được duyệt cho 6 học sinh
-- (Ở đây dùng CURDATE() cho ngày duyệt và ngày nộp đơn;
--  approved_by = 1 (giả sử quản lý 1 duyệt), và dorm_id = 1 (theo phòng được xếp))
INSERT INTO applications (approval_date, status, submission_date, approved_by, dorm_id, student_id)
VALUES
  ( CURDATE() - INTERVAL 2 DAY, 'approved', CURDATE() - INTERVAL 2 DAY, 1, 1, 4),
  ( CURDATE() - INTERVAL 2 DAY, 'approved', CURDATE() - INTERVAL 2 DAY, 1, 1, 5),
  ( CURDATE() - INTERVAL 2 DAY, 'approved', CURDATE() - INTERVAL 2 DAY, 1, 1, 6),
  ( CURDATE() - INTERVAL 2 DAY, 'approved', CURDATE() - INTERVAL 2 DAY, 1, 1, 7),
  ( CURDATE() - INTERVAL 2 DAY, 'approved', CURDATE() - INTERVAL 2 DAY, 1, 1, 8),
  ( CURDATE() - INTERVAL 2 DAY, 'approved', CURDATE() - INTERVAL 2 DAY, 1, 1, 10);


-- 2. Cập nhật số người hiện tại (current_occupancy) cho các phòng được phân bổ
-- Giả sử 2 phòng được sử dụng là room_id = 1 và room_id = 2, mỗi phòng có 3 học sinh
UPDATE rooms
SET current_occupancy = 3
WHERE room_id IN (1, 2);

-- 3. Gán học sinh vào các phòng (bảng room_assignments)
-- Giả sử:
-- - Học sinh 4, 5, 6 (nam) được xếp vào phòng room_id = 1 (phù hợp với room_type 'Nam')
INSERT INTO room_assignments (assigned_date, end_date, room_id, student_id)
VALUES
  (CURDATE(), NULL, 1, 4),
  (CURDATE(), NULL, 1, 5),
  (CURDATE(), NULL, 1, 6);

-- - Học sinh 7, 8, 10 (nữ) được xếp vào phòng room_id = 2 (phù hợp với room_type 'Nữ')
INSERT INTO room_assignments (assigned_date, end_date, room_id, student_id)
VALUES
  (CURDATE(), NULL, 2, 7),
  (CURDATE(), NULL, 2, 8),
  (CURDATE(), NULL, 2, 10);

-- 4. Ghi số điện và số nước cho 2 phòng với thời điểm là "ngày này của tháng trước"
-- Dùng DATE_SUB(CURDATE(), INTERVAL 1 MONTH) để tính ngày ghi
-- Giả sử:
--   - Với điện: previous_reading = 100, current_reading = 150, service_id = 3 (Điện sinh hoạt)
--   - Với nước: previous_reading = 50,  current_reading = 60,  service_id = 4 (Nước sinh hoạt)
INSERT INTO service_usage (current_reading, invoiced, previous_reading, record_date, room_id, service_id, student_id)
VALUES
  -- Ghi số cho phòng room_id = 1
  (150, 'NO', 100, DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 1, 3, NULL),  -- Điện
  (60,  'NO', 50,  DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 1, 4, NULL),  -- Nước

  -- Ghi số cho phòng room_id = 2
  (150, 'NO', 100, DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 2, 3, NULL),  -- Điện
  (60,  'NO', 50,  DATE_SUB(CURDATE(), INTERVAL 1 MONTH), 2, 4, NULL);  -- Nước




-- 1. Thêm 10 sinh viên mới (user_id: 21 -> 30)

-- a) Thêm vào bảng `users`
INSERT INTO users (user_id, created_at, password_hash, role, status, updated_at, username)
VALUES
  (21, '2025-01-02 14:00:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:00:00', 'student21'),
  (22, '2025-01-02 14:10:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:10:00', 'student22'),
  (23, '2025-01-02 14:20:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:20:00', 'student23'),
  (24, '2025-01-02 14:30:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:30:00', 'student24'),
  (25, '2025-01-02 14:40:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:40:00', 'student25'),
  (26, '2025-01-02 14:50:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 14:50:00', 'student26'),
  (27, '2025-01-02 15:00:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:00:00', 'student27'),
  (28, '2025-01-02 15:10:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:10:00', 'student28'),
  (29, '2025-01-02 15:20:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:20:00', 'student29'),
  (30, '2025-01-02 15:30:00', '$2a$10$HZTSF2uLVsjKW2gTD5XvVOz3bwk6/xNyCSCiDp26dt4ZrVW19Ggt6', 'student', 'active', '2025-01-02 15:30:00', 'student30');

-- b) Thêm vào bảng `students`
INSERT INTO students (student_id, address, date_of_birth, department, email, full_name, gender, phone_number, student_class)
VALUES
  (21, '123 New St', '2003-03-01', 'CNTT', NULL, 'Student Twenty One', 'Nam', '0901000021', 'CNTT1'),
  (22, '123 New St', '2003-03-02', 'CNTT', NULL, 'Student Twenty Two', 'Nữ',  '0901000022', 'CNTT1'),
  (23, '234 New St', '2003-03-03', 'KinhTe',NULL, 'Student Twenty Three', 'Nam', '0901000023', 'KT1'),
  (24, '234 New St', '2003-03-04', 'KinhTe', NULL, 'Student Twenty Four', 'Nữ',  '0901000024', 'KT1'),
  (25, '345 New St', '2003-03-05', 'XHNV', NULL, 'Student Twenty Five', 'Nam', '0901000025', 'XH1'),
  (26, '345 New St', '2003-03-06', 'XHNV', NULL, 'Student Twenty Six', 'Nữ',  '0901000026', 'XH1'),
  (27, '456 New St', '2003-03-07', 'CNTT', NULL, 'Student Twenty Seven', 'Nam', '0901000027', 'CNTT2'),
  (28, '456 New St', '2003-03-08', 'CNTT', NULL, 'Student Twenty Eight', 'Nữ',  '0901000028', 'CNTT2'),
  (29, '567 New St', '2003-03-09', 'KinhTe', NULL, 'Student Twenty Nine', 'Nam', '0901000029', 'KT2'),
  (30, '567 New St', '2003-03-10', 'KinhTe', NULL, 'Student Thirty',      'Nữ',  '0901000030', 'KT2');


-- 2. Thêm dữ liệu mẫu cho 5 đơn đăng ký ở ký túc xá (status 'pending') cho 5 sinh viên
-- (Ở đây sử dụng student_id từ 21 đến 25, submission_date = CURDATE(), chưa được duyệt nên không có approved_by và approval_date)
INSERT INTO applications (approval_date, status, submission_date, approved_by, dorm_id, student_id)
VALUES
  (NULL, 'pending',  CURDATE() - INTERVAL 1 DAY, NULL, 1, 21),
  (NULL, 'pending',  CURDATE() - INTERVAL 2 DAY, NULL, 1, 22),
  (NULL, 'pending',  CURDATE() - INTERVAL 3 DAY, NULL, 1, 23),
  (NULL, 'pending',  CURDATE() - INTERVAL 4 DAY, NULL, 1, 24),
  (NULL, 'pending',  CURDATE() - INTERVAL 5 DAY, NULL, 1, 25);


-- 3. Thêm dữ liệu mẫu cho 6 yêu cầu đăng ký sử dụng dịch vụ giặt ủi (service_id = 2)
-- Giả sử 6 sinh viên ở phòng 1 và 2 là: student_id 4, 5, 6 (phòng 1) và student_id 7, 8, 10 (phòng 2)
INSERT INTO student_service_registrations (actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced)
VALUES
  (1, NULL, NULL, CURDATE() - INTERVAL 1 DAY, 'pending', NULL, 2, 4, 'NO'),
  (1, NULL, NULL, CURDATE() - INTERVAL 2 DAY, 'pending', NULL, 2, 5, 'NO'),
  (1, NULL, NULL, CURDATE() - INTERVAL 3 DAY, 'pending', NULL, 2, 6, 'NO'),
  (1, NULL, NULL, CURDATE() - INTERVAL 4 DAY, 'pending', NULL, 2, 7, 'NO'),
  (1, NULL, NULL, CURDATE() - INTERVAL 5 DAY, 'pending', NULL, 2, 8, 'NO'),
  (1, NULL, NULL, CURDATE() - INTERVAL 6 DAY, 'pending', NULL, 2, 10, 'NO');




-- 5 sinh viên được duyệt, ví dụ student_id từ 21 -> 25
-- Dịch vụ cá nhân: Giặt ủi (service_id = 2)
-- actual_quantity = NULL (chưa nhập số lượng sử dụng thực tế)
-- status = 'approved'
-- approved_by = 1 (giả sử là manager_id = 1)
-- approval_date = CURDATE(), start_date = CURDATE(), end_date = NULL
-- invoiced = 'NO' (chưa lên hóa đơn)

INSERT INTO student_service_registrations
    (actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced)
VALUES
    (NULL, CURDATE(), NULL, CURDATE() - INTERVAL 1 DAY, 'approved', 1, 2, 4, 'NO'),
    (NULL, CURDATE(), NULL, CURDATE() - INTERVAL 2 DAY, 'approved', 1, 2, 5, 'NO'),
    (NULL, CURDATE(), NULL, CURDATE() - INTERVAL 3 DAY, 'approved', 1, 2, 6, 'NO'),
    (NULL, CURDATE(), NULL, CURDATE() - INTERVAL 4 DAY, 'approved', 1, 2, 7, 'NO'),
    (NULL, CURDATE(), NULL, CURDATE() - INTERVAL 5 DAY, 'approved', 1, 2, 8, 'NO');



-- Lên lịch tu dong tao hoa don tien phong.
--    INSERT INTO billing_schedules (active, schedule_time) VALUES (b'1', NOW() + INTERVAL 1 MINUTE);

    -- Lên lịch tu dong tao hoa don dien nuoc.
--    INSERT INTO utility_billing_schedule (active, schedule_time) VALUES (b'1', NOW() + INTERVAL 1 MINUTE);



-- HOÀN TẤT GIAO DỊCH
COMMIT;

-- Bỏ các lệnh SET @variable

-- --- Tháng 9/2024 (Ghi số ngày 18/10/2024, Hóa đơn kỳ 2024-10) ---
-- Phòng 1 (Bill ID: 9, Usage IDs: 6, 7, Item IDs: 11, 12, Notif IDs: 13, 14, 15)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(9, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 1, NULL, '2024-10-18 10:00:00.000000', '2024-10-22 09:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(11, 150000, 9, 3, NULL, 50, 3000),
(12, 70000, 9, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(6, 50, 'YES', 0, '2024-10-18', 9, 1, 3, NULL),
(7, 10, 'YES', 0, '2024-10-18', 9, 1, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(13, '2024-10-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 4),
(14, '2024-10-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 5),
(15, '2024-10-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 6);

-- Phòng 2 (Bill ID: 10, Usage IDs: 8, 9, Item IDs: 13, 14, Notif IDs: 16, 17, 18)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(10, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 2, NULL, '2024-10-18 10:00:01.000000', '2024-10-23 10:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(13, 150000, 10, 3, NULL, 50, 3000),
(14, 70000, 10, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(8, 50, 'YES', 0, '2024-10-18', 10, 2, 3, NULL),
(9, 10, 'YES', 0, '2024-10-18', 10, 2, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(16, '2024-10-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 7),
(17, '2024-10-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 8),
(18, '2024-10-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-10 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-10 đã thanh toán', 10);

-- Phòng 3 (Bill ID: 11, Usage IDs: 10, 11, Item IDs: 15, 16)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(11, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 3, NULL, '2024-10-18 10:00:02.000000', '2024-10-24 11:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(15, 150000, 11, 3, NULL, 50, 3000),
(16, 70000, 11, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(10, 50, 'YES', 0, '2024-10-18', 11, 3, 3, NULL),
(11, 10, 'YES', 0, '2024-10-18', 11, 3, 4, NULL);

-- Phòng 4 (Bill ID: 12, Usage IDs: 12, 13, Item IDs: 17, 18)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(12, 'điện-nước', '2024-10', '2024-10-28', '2024-10-18', 'paid', 220000, 4, NULL, '2024-10-18 10:00:03.000000', '2024-10-25 12:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(17, 150000, 12, 3, NULL, 50, 3000),
(18, 70000, 12, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(12, 50, 'YES', 0, '2024-10-18', 12, 4, 3, NULL),
(13, 10, 'YES', 0, '2024-10-18', 12, 4, 4, NULL);


-- --- Tháng 10/2024 (Ghi số ngày 18/11/2024, Hóa đơn kỳ 2024-11) ---
-- Phòng 1 (Bill ID: 13, Usage IDs: 14, 15, Item IDs: 19, 20, Notif IDs: 19, 20, 21)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(13, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 1, NULL, '2024-11-18 10:00:00.000000', '2024-11-22 09:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(19, 150000, 13, 3, NULL, 50, 3000),
(20, 70000, 13, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(14, 100, 'YES', 50, '2024-11-18', 13, 1, 3, NULL),
(15, 20, 'YES', 10, '2024-11-18', 13, 1, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(19, '2024-11-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 4),
(20, '2024-11-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 5),
(21, '2024-11-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 6);

-- Phòng 2 (Bill ID: 14, Usage IDs: 16, 17, Item IDs: 21, 22, Notif IDs: 22, 23, 24)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(14, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 2, NULL, '2024-11-18 10:00:01.000000', '2024-11-23 10:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(21, 150000, 14, 3, NULL, 50, 3000),
(22, 70000, 14, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(16, 100, 'YES', 50, '2024-11-18', 14, 2, 3, NULL),
(17, 20, 'YES', 10, '2024-11-18', 14, 2, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(22, '2024-11-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 7),
(23, '2024-11-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 8),
(24, '2024-11-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-11 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-11 đã thanh toán', 10);

-- Phòng 3 (Bill ID: 15, Usage IDs: 18, 19, Item IDs: 23, 24)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(15, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 3, NULL, '2024-11-18 10:00:02.000000', '2024-11-24 11:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(23, 150000, 15, 3, NULL, 50, 3000),
(24, 70000, 15, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(18, 100, 'YES', 50, '2024-11-18', 15, 3, 3, NULL),
(19, 20, 'YES', 10, '2024-11-18', 15, 3, 4, NULL);

-- Phòng 4 (Bill ID: 16, Usage IDs: 20, 21, Item IDs: 25, 26)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(16, 'điện-nước', '2024-11', '2024-11-28', '2024-11-18', 'paid', 220000, 4, NULL, '2024-11-18 10:00:03.000000', '2024-11-25 12:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(25, 150000, 16, 3, NULL, 50, 3000),
(26, 70000, 16, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(20, 100, 'YES', 50, '2024-11-18', 16, 4, 3, NULL),
(21, 20, 'YES', 10, '2024-11-18', 16, 4, 4, NULL);


-- --- Tháng 11/2024 (Ghi số ngày 18/12/2024, Hóa đơn kỳ 2024-12) ---
-- Phòng 1 (Bill ID: 17, Usage IDs: 22, 23, Item IDs: 27, 28, Notif IDs: 25, 26, 27)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(17, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 1, NULL, '2024-12-18 10:00:00.000000', '2024-12-22 09:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(27, 150000, 17, 3, NULL, 50, 3000),
(28, 70000, 17, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(22, 150, 'YES', 100, '2024-12-18', 17, 1, 3, NULL),
(23, 30, 'YES', 20, '2024-12-18', 17, 1, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(25, '2024-12-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 4),
(26, '2024-12-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 5),
(27, '2024-12-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 6);

-- Phòng 2 (Bill ID: 18, Usage IDs: 24, 25, Item IDs: 29, 30, Notif IDs: 28, 29, 30)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(18, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 2, NULL, '2024-12-18 10:00:01.000000', '2024-12-23 10:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(29, 150000, 18, 3, NULL, 50, 3000),
(30, 70000, 18, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(24, 150, 'YES', 100, '2024-12-18', 18, 2, 3, NULL),
(25, 30, 'YES', 20, '2024-12-18', 18, 2, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(28, '2024-12-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 7),
(29, '2024-12-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 8),
(30, '2024-12-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2024-12 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2024-12 đã thanh toán', 10);

-- Phòng 3 (Bill ID: 19, Usage IDs: 26, 27, Item IDs: 31, 32)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(19, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 3, NULL, '2024-12-18 10:00:02.000000', '2024-12-24 11:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(31, 150000, 19, 3, NULL, 50, 3000),
(32, 70000, 19, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(26, 150, 'YES', 100, '2024-12-18', 19, 3, 3, NULL),
(27, 30, 'YES', 20, '2024-12-18', 19, 3, 4, NULL);

-- Phòng 4 (Bill ID: 20, Usage IDs: 28, 29, Item IDs: 33, 34)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(20, 'điện-nước', '2024-12', '2024-12-28', '2024-12-18', 'paid', 220000, 4, NULL, '2024-12-18 10:00:03.000000', '2024-12-25 12:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(33, 150000, 20, 3, NULL, 50, 3000),
(34, 70000, 20, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(28, 150, 'YES', 100, '2024-12-18', 20, 4, 3, NULL),
(29, 30, 'YES', 20, '2024-12-18', 20, 4, 4, NULL);


-- --- Tháng 12/2024 (Ghi số ngày 18/01/2025, Hóa đơn kỳ 2025-01) ---
-- Phòng 1 (Bill ID: 21, Usage IDs: 30, 31, Item IDs: 35, 36, Notif IDs: 31, 32, 33)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(21, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 1, NULL, '2025-01-18 10:00:00.000000', '2025-01-22 09:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(35, 150000, 21, 3, NULL, 50, 3000),
(36, 70000, 21, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(30, 200, 'YES', 150, '2025-01-18', 21, 1, 3, NULL),
(31, 40, 'YES', 30, '2025-01-18', 21, 1, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(31, '2025-01-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 4),
(32, '2025-01-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 5),
(33, '2025-01-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 6);

-- Phòng 2 (Bill ID: 22, Usage IDs: 32, 33, Item IDs: 37, 38, Notif IDs: 34, 35, 36)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(22, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 2, NULL, '2025-01-18 10:00:01.000000', '2025-01-23 10:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(37, 150000, 22, 3, NULL, 50, 3000),
(38, 70000, 22, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(32, 200, 'YES', 150, '2025-01-18', 22, 2, 3, NULL),
(33, 40, 'YES', 30, '2025-01-18', 22, 2, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(34, '2025-01-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 7),
(35, '2025-01-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 8),
(36, '2025-01-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-01 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-01 đã thanh toán', 10);

-- Phòng 3 (Bill ID: 23, Usage IDs: 34, 35, Item IDs: 39, 40)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(23, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 3, NULL, '2025-01-18 10:00:02.000000', '2025-01-24 11:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(39, 150000, 23, 3, NULL, 50, 3000),
(40, 70000, 23, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(34, 200, 'YES', 150, '2025-01-18', 23, 3, 3, NULL),
(35, 40, 'YES', 30, '2025-01-18', 23, 3, 4, NULL);

-- Phòng 4 (Bill ID: 24, Usage IDs: 36, 37, Item IDs: 41, 42)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(24, 'điện-nước', '2025-01', '2025-01-28', '2025-01-18', 'paid', 220000, 4, NULL, '2025-01-18 10:00:03.000000', '2025-01-25 12:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(41, 150000, 24, 3, NULL, 50, 3000),
(42, 70000, 24, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(36, 200, 'YES', 150, '2025-01-18', 24, 4, 3, NULL),
(37, 40, 'YES', 30, '2025-01-18', 24, 4, 4, NULL);


-- --- Tháng 01/2025 (Ghi số ngày 18/02/2025, Hóa đơn kỳ 2025-02) ---
-- Phòng 1 (Bill ID: 25, Usage IDs: 38, 39, Item IDs: 43, 44, Notif IDs: 37, 38, 39)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(25, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 1, NULL, '2025-02-18 10:00:00.000000', '2025-02-22 09:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(43, 150000, 25, 3, NULL, 50, 3000),
(44, 70000, 25, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(38, 250, 'YES', 200, '2025-02-18', 25, 1, 3, NULL),
(39, 50, 'YES', 40, '2025-02-18', 25, 1, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(37, '2025-02-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 4),
(38, '2025-02-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 5),
(39, '2025-02-18 10:01:00.000000', 'Hóa đơn điện nước phòng A101 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 6);

-- Phòng 2 (Bill ID: 26, Usage IDs: 40, 41, Item IDs: 45, 46, Notif IDs: 40, 41, 42)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(26, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 2, NULL, '2025-02-18 10:00:01.000000', '2025-02-23 10:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(45, 150000, 26, 3, NULL, 50, 3000),
(46, 70000, 26, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(40, 250, 'YES', 200, '2025-02-18', 26, 2, 3, NULL),
(41, 50, 'YES', 40, '2025-02-18', 26, 2, 4, NULL);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(40, '2025-02-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 7),
(41, '2025-02-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 8),
(42, '2025-02-18 10:01:01.000000', 'Hóa đơn điện nước phòng A102 kỳ 2025-02 (220000 VND) đã được thanh toán.', 'unread', 'Hóa đơn kỳ 2025-02 đã thanh toán', 10);

-- Phòng 3 (Bill ID: 27, Usage IDs: 42, 43, Item IDs: 47, 48)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(27, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 3, NULL, '2025-02-18 10:00:02.000000', '2025-02-24 11:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(47, 150000, 27, 3, NULL, 50, 3000),
(48, 70000, 27, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(42, 250, 'YES', 200, '2025-02-18', 27, 3, 3, NULL),
(43, 50, 'YES', 40, '2025-02-18', 27, 3, 4, NULL);

-- Phòng 4 (Bill ID: 28, Usage IDs: 44, 45, Item IDs: 49, 50)
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(28, 'điện-nước', '2025-02', '2025-02-28', '2025-02-18', 'paid', 220000, 4, NULL, '2025-02-18 10:00:03.000000', '2025-02-25 12:00:00.000000');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(49, 150000, 28, 3, NULL, 50, 3000),
(50, 70000, 28, 4, NULL, 10, 7000);
INSERT INTO service_usage (usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id) VALUES
(44, 250, 'YES', 200, '2025-02-18', 28, 4, 3, NULL),
(45, 50, 'YES', 40, '2025-02-18', 28, 4, 4, NULL);


-- --- Tháng 02/2025 (Ghi số ngày 18/03/2025, Hóa đơn kỳ 2025-03) ---
-- Dữ liệu gốc cho tháng 3/2025 (bill_id 1, 2) đã có.
-- Cập nhật chỉ số cũ (previous_reading) cho các bản ghi usage gốc (usage_id 1, 2, 3, 4)
-- để khớp với chỉ số cuối (current_reading) của tháng 2/2025 (là 250 cho điện, 50 cho nước)
UPDATE service_usage SET previous_reading = 250 WHERE usage_id = 1; -- Phòng 1, Điện, Bill 1
UPDATE service_usage SET previous_reading = 50 WHERE usage_id = 2;  -- Phòng 1, Nước, Bill 1
UPDATE service_usage SET previous_reading = 250 WHERE usage_id = 3; -- Phòng 2, Điện, Bill 2
UPDATE service_usage SET previous_reading = 50 WHERE usage_id = 4;  -- Phòng 2, Nước, Bill 2

-- **QUAN TRỌNG**: Dữ liệu gốc của bạn cho tháng 3/2025 có `current_reading` là 150 (điện) và 60 (nước).
-- Nếu `previous_reading` là 250 (từ tháng 2), thì `current_reading` phải lớn hơn 250.
-- Dữ liệu gốc hiện tại không hợp lý về mặt chỉ số liên tục.
-- Giả sử ta chấp nhận dữ liệu gốc này và không sửa đổi `current_reading`, `quantity`, `amount` của bill 1, 2.
-- Nếu muốn dữ liệu tháng 3 liền mạch, hãy bỏ comment phần UPDATE tùy chọn dưới đây:

/*
-- **TÙY CHỌN:** Sửa dữ liệu gốc tháng 3/2025 cho liền mạch (cập nhật chỉ số cuối và tính lại tiền)
-- Giả sử tháng 3 cũng dùng 50 kWh điện và 10 m3 nước
-- Phòng 1 (bill_id=1), Usage (usage_id=1, 2)
UPDATE service_usage SET previous_reading = 250, current_reading = 300 WHERE usage_id = 1; -- Điện: 250 -> 300 (dùng 50)
UPDATE service_usage SET previous_reading = 50, current_reading = 60 WHERE usage_id = 2;  -- Nước: 50 -> 60 (dùng 10)
UPDATE bill_items SET quantity = 50, amount = 150000 WHERE bill_item_id = 1; -- Cập nhật điện (bill_item 1 ứng với bill 1, service 3)
UPDATE bill_items SET quantity = 10, amount = 70000 WHERE bill_item_id = 2;  -- Cập nhật nước (bill_item 2 ứng với bill 1, service 4)
UPDATE bills SET total_amount = 220000 WHERE bill_id = 1; -- Cập nhật tổng bill 1

-- Phòng 2 (bill_id=2), Usage (usage_id=3, 4)
UPDATE service_usage SET previous_reading = 250, current_reading = 300 WHERE usage_id = 3; -- Điện: 250 -> 300 (dùng 50)
UPDATE service_usage SET previous_reading = 50, current_reading = 60 WHERE usage_id = 4;  -- Nước: 50 -> 60 (dùng 10)
UPDATE bill_items SET quantity = 50, amount = 150000 WHERE bill_item_id = 3; -- Cập nhật điện (bill_item 3 ứng với bill 2, service 3)
UPDATE bill_items SET quantity = 10, amount = 70000 WHERE bill_item_id = 4;  -- Cập nhật nước (bill_item 4 ứng với bill 2, service 4)
UPDATE bills SET total_amount = 220000 WHERE bill_id = 2; -- Cập nhật tổng bill 2
*/


--giả lập sử dụng dịch vụ cá nhân
-- ================================================================
-- === GIẢ LẬP DỮ LIỆU SỬ DỤNG DỊCH VỤ CÁ NHÂN (GIẶT ỦI) ===
-- === CHO SINH VIÊN Ở PHÒNG (4, 5, 6, 7, 8, 10)             ===
-- === TỪ THÁNG 10/2024 ĐẾN THÁNG 02/2025                     ===
-- ================================================================

-- --- Tháng 10/2024 (Hóa đơn dịch vụ kỳ 2024-10, phát hành đầu tháng 11) ---
-- Giả sử sử dụng 2 lần
-- Student 4 (RegID: 12, BillID: 29, ItemID: 51, NotifID: 43)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(12, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 4, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(29, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 4, '2024-11-01 08:00:00', '2024-11-05 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(51, 40000, 29, 2, 12, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(43, '2024-11-01 08:01:00', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 4);

-- Student 5 (RegID: 13, BillID: 30, ItemID: 52, NotifID: 44)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(13, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 5, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(30, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 5, '2024-11-01 08:00:01', '2024-11-06 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(52, 40000, 30, 2, 13, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(44, '2024-11-01 08:01:01', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 5);

-- Student 6 (RegID: 14, BillID: 31, ItemID: 53, NotifID: 45)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(14, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 6, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(31, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 6, '2024-11-01 08:00:02', '2024-11-07 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(53, 40000, 31, 2, 14, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(45, '2024-11-01 08:01:02', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 6);

-- Student 7 (RegID: 15, BillID: 32, ItemID: 54, NotifID: 46)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(15, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 7, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(32, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 7, '2024-11-01 08:00:03', '2024-11-08 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(54, 40000, 32, 2, 15, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(46, '2024-11-01 08:01:03', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 7);

-- Student 8 (RegID: 16, BillID: 33, ItemID: 55, NotifID: 47)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(16, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 8, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(33, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 8, '2024-11-01 08:00:04', '2024-11-09 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(55, 40000, 33, 2, 16, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(47, '2024-11-01 08:01:04', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 8);

-- Student 10 (RegID: 17, BillID: 34, ItemID: 56, NotifID: 48)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(17, 2, '2024-10-01', '2024-10-31', '2024-10-01', 'approved', 1, 2, 10, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(34, 'dịch-vụ', '2024-10', '2024-11-11', '2024-11-01', 'paid', 40000, NULL, 10, '2024-11-01 08:00:05', '2024-11-10 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(56, 40000, 34, 2, 17, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(48, '2024-11-01 08:01:05', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-10 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 10/2024', 10);

-- --- Tháng 11/2024 (Hóa đơn dịch vụ kỳ 2024-11, phát hành đầu tháng 12) ---
-- Giả sử sử dụng 3 lần
-- Student 4 (RegID: 18, BillID: 35, ItemID: 57, NotifID: 49)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(18, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 4, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(35, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 4, '2024-12-01 08:00:00', '2024-12-05 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(57, 60000, 35, 2, 18, 3, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(49, '2024-12-01 08:01:00', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 4);

-- Student 5 (RegID: 19, BillID: 36, ItemID: 58, NotifID: 50)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(19, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 5, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(36, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 5, '2024-12-01 08:00:01', '2024-12-06 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(58, 60000, 36, 2, 19, 3, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(50, '2024-12-01 08:01:01', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 5);

-- Student 6 (RegID: 20, BillID: 37, ItemID: 59, NotifID: 51)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(20, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 6, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(37, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 6, '2024-12-01 08:00:02', '2024-12-07 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(59, 60000, 37, 2, 20, 3, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(51, '2024-12-01 08:01:02', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 6);

-- Student 7 (RegID: 21, BillID: 38, ItemID: 60, NotifID: 52)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(21, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 7, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(38, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 7, '2024-12-01 08:00:03', '2024-12-08 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(60, 60000, 38, 2, 21, 3, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(52, '2024-12-01 08:01:03', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 7);

-- Student 8 (RegID: 22, BillID: 39, ItemID: 61, NotifID: 53)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(22, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 8, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(39, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 8, '2024-12-01 08:00:04', '2024-12-09 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(61, 60000, 39, 2, 22, 3, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(53, '2024-12-01 08:01:04', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 8);

-- Student 10 (RegID: 23, BillID: 40, ItemID: 62, NotifID: 54)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(23, 3, '2024-11-01', '2024-11-30', '2024-11-01', 'approved', 1, 2, 10, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(40, 'dịch-vụ', '2024-11', '2024-12-11', '2024-12-01', 'paid', 60000, NULL, 10, '2024-12-01 08:00:05', '2024-12-10 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(62, 60000, 40, 2, 23, 3, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(54, '2024-12-01 08:01:05', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-11 (60000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 11/2024', 10);


-- --- Tháng 12/2024 (Hóa đơn dịch vụ kỳ 2024-12, phát hành đầu tháng 1) ---
-- Giả sử sử dụng 4 lần
-- Student 4 (RegID: 24, BillID: 41, ItemID: 63, NotifID: 55)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(24, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 4, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(41, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 4, '2025-01-01 08:00:00', '2025-01-05 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(63, 80000, 41, 2, 24, 4, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(55, '2025-01-01 08:01:00', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 4);

-- Student 5 (RegID: 25, BillID: 42, ItemID: 64, NotifID: 56)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(25, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 5, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(42, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 5, '2025-01-01 08:00:01', '2025-01-06 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(64, 80000, 42, 2, 25, 4, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(56, '2025-01-01 08:01:01', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 5);

-- Student 6 (RegID: 26, BillID: 43, ItemID: 65, NotifID: 57)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(26, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 6, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(43, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 6, '2025-01-01 08:00:02', '2025-01-07 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(65, 80000, 43, 2, 26, 4, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(57, '2025-01-01 08:01:02', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 6);

-- Student 7 (RegID: 27, BillID: 44, ItemID: 66, NotifID: 58)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(27, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 7, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(44, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 7, '2025-01-01 08:00:03', '2025-01-08 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(66, 80000, 44, 2, 27, 4, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(58, '2025-01-01 08:01:03', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 7);

-- Student 8 (RegID: 28, BillID: 45, ItemID: 67, NotifID: 59)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(28, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 8, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(45, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 8, '2025-01-01 08:00:04', '2025-01-09 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(67, 80000, 45, 2, 28, 4, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(59, '2025-01-01 08:01:04', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 8);

-- Student 10 (RegID: 29, BillID: 46, ItemID: 68, NotifID: 60)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(29, 4, '2024-12-01', '2024-12-31', '2024-12-01', 'approved', 1, 2, 10, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(46, 'dịch-vụ', '2024-12', '2025-01-11', '2025-01-01', 'paid', 80000, NULL, 10, '2025-01-01 08:00:05', '2025-01-10 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(68, 80000, 46, 2, 29, 4, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(60, '2025-01-01 08:01:05', 'Hóa đơn dịch vụ Giặt ủi kỳ 2024-12 (80000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 12/2024', 10);


-- --- Tháng 01/2025 (Hóa đơn dịch vụ kỳ 2025-01, phát hành đầu tháng 2) ---
-- Giả sử sử dụng 5 lần
-- Student 4 (RegID: 30, BillID: 47, ItemID: 69, NotifID: 61)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(30, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 4, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(47, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 4, '2025-02-01 08:00:00', '2025-02-05 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(69, 100000, 47, 2, 30, 5, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(61, '2025-02-01 08:01:00', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 4);

-- Student 5 (RegID: 31, BillID: 48, ItemID: 70, NotifID: 62)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(31, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 5, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(48, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 5, '2025-02-01 08:00:01', '2025-02-06 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(70, 100000, 48, 2, 31, 5, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(62, '2025-02-01 08:01:01', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 5);

-- Student 6 (RegID: 32, BillID: 49, ItemID: 71, NotifID: 63)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(32, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 6, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(49, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 6, '2025-02-01 08:00:02', '2025-02-07 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(71, 100000, 49, 2, 32, 5, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(63, '2025-02-01 08:01:02', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 6);

-- Student 7 (RegID: 33, BillID: 50, ItemID: 72, NotifID: 64)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(33, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 7, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(50, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 7, '2025-02-01 08:00:03', '2025-02-08 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(72, 100000, 50, 2, 33, 5, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(64, '2025-02-01 08:01:03', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 7);

-- Student 8 (RegID: 34, BillID: 51, ItemID: 73, NotifID: 65)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(34, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 8, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(51, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 8, '2025-02-01 08:00:04', '2025-02-09 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(73, 100000, 51, 2, 34, 5, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(65, '2025-02-01 08:01:04', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 8);

-- Student 10 (RegID: 35, BillID: 52, ItemID: 74, NotifID: 66)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(35, 5, '2025-01-01', '2025-01-31', '2025-01-01', 'approved', 1, 2, 10, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(52, 'dịch-vụ', '2025-01', '2025-02-11', '2025-02-01', 'paid', 100000, NULL, 10, '2025-02-01 08:00:05', '2025-02-10 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(74, 100000, 52, 2, 35, 5, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(66, '2025-02-01 08:01:05', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-01 (100000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 01/2025', 10);


-- --- Tháng 02/2025 (Hóa đơn dịch vụ kỳ 2025-02, phát hành đầu tháng 3) ---
-- Giả sử sử dụng 2 lần
-- Student 4 (RegID: 36, BillID: 53, ItemID: 75, NotifID: 67)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(36, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 4, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(53, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 4, '2025-03-01 08:00:00', '2025-03-05 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(75, 40000, 53, 2, 36, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(67, '2025-03-01 08:01:00', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 4);

-- Student 5 (RegID: 37, BillID: 54, ItemID: 76, NotifID: 68)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(37, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 5, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(54, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 5, '2025-03-01 08:00:01', '2025-03-06 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(76, 40000, 54, 2, 37, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(68, '2025-03-01 08:01:01', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 5);

-- Student 6 (RegID: 38, BillID: 55, ItemID: 77, NotifID: 69)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(38, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 6, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(55, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 6, '2025-03-01 08:00:02', '2025-03-07 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(77, 40000, 55, 2, 38, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(69, '2025-03-01 08:01:02', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 6);

-- Student 7 (RegID: 39, BillID: 56, ItemID: 78, NotifID: 70)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(39, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 7, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(56, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 7, '2025-03-01 08:00:03', '2025-03-08 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(78, 40000, 56, 2, 39, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(70, '2025-03-01 08:01:03', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 7);

-- Student 8 (RegID: 40, BillID: 57, ItemID: 79, NotifID: 71)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(40, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 8, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(57, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 8, '2025-03-01 08:00:04', '2025-03-09 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(79, 40000, 57, 2, 40, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(71, '2025-03-01 08:01:04', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 8);

-- Student 10 (RegID: 41, BillID: 58, ItemID: 80, NotifID: 72)
INSERT INTO student_service_registrations (registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id, invoiced) VALUES
(41, 2, '2025-02-01', '2025-02-28', '2025-02-01', 'approved', 1, 2, 10, 'YES');
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id, created_date, paid_date) VALUES
(58, 'dịch-vụ', '2025-02', '2025-03-11', '2025-03-01', 'paid', 40000, NULL, 10, '2025-03-01 08:00:05', '2025-03-10 09:00:00');
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id, registration_id, quantity, unit_price) VALUES
(80, 40000, 58, 2, 41, 2, 20000);
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id) VALUES
(72, '2025-03-01 08:01:05', 'Hóa đơn dịch vụ Giặt ủi kỳ 2025-02 (40000 VND) đã được thanh toán.', 'unread', 'Hóa đơn dịch vụ tháng 02/2025', 10);