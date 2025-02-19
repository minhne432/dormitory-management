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
    (4,  '123 ABC St', '2003-05-10', 'CNTT',   'student4@univ.edu',  'Student Four',   'Nam', '0901000004', 'CNTT1'),
    (5,  '123 ABC St', '2003-04-11', 'CNTT',   'student5@univ.edu',  'Student Five',   'Nam', '0901000005', 'CNTT1'),
    (6,  '234 BCD St', '2002-03-12', 'CNTT',   'student6@univ.edu',  'Student Six',    'Nam', '0901000006', 'CNTT2'),
    (7,  '234 BCD St', '2002-02-05', 'CNTT',   'student7@univ.edu',  'Student Seven',  'Nữ',  '0901000007', 'CNTT2'),
    (8,  '345 CDE St', '2003-01-05', 'KinhTe', 'student8@univ.edu',  'Student Eight',  'Nữ',  '0901000008', 'KT1'),
    (9,  '345 CDE St', '2003-08-09', 'KinhTe', 'student9@univ.edu',  'Student Nine',   'Nam', '0901000009', 'KT1'),
    (10, '456 DEF St', '2003-07-07', 'KinhTe', 'student10@univ.edu', 'Student Ten',    'Nữ',  '0901000010', 'KT2'),
    (11, '456 DEF St', '2003-06-08', 'CNTT',   'student11@univ.edu', 'Student Eleven', 'Nam', '0901000011', 'CNTT3'),
    (12, '567 EFG St', '2004-12-01', 'XHNV',   'student12@univ.edu', 'Student Twelve', 'Nữ',  '0901000012', 'XH1'),
    (13, '567 EFG St', '2002-11-02', 'XHNV',   'student13@univ.edu', 'Student Thirteen','Nữ', '0901000013', 'XH1'),
    (14, '678 FGH St', '2002-10-03', 'KinhTe', 'student14@univ.edu', 'Student Fourteen','Nam','0901000014', 'KT3'),
    (15, '789 GHI St', '2002-09-04', 'XHNV',   'student15@univ.edu', 'Student Fifteen','Nữ',  '0901000015', 'XH2'),
    (16, '789 GHI St', '2002-08-05', 'CNTT',   'student16@univ.edu', 'Student Sixteen','Nam', '0901000016', 'CNTT4'),
    (17, '876 HIJ St', '2002-07-06', 'KinhTe', 'student17@univ.edu', 'Student Seventeen','Nữ','0901000017', 'KT4'),
    (18, '987 IJK St', '2002-06-07', 'KinhTe', 'student18@univ.edu', 'Student Eighteen','Nam','0901000018', 'KT5'),
    (19, '987 IJK St', '2002-05-08', 'CNTT',   'student19@univ.edu', 'Student Nineteen','Nam','0901000019', 'CNTT5'),
    (20, '987 IJK St', '2002-12-25', 'KinhTe', 'student20@univ.edu', 'Student Twenty',  'Nữ', '0901000020', 'KT7');

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
INSERT INTO services (service_id, description, service_name, service_type, unit, unit_price)
VALUES
    (1, 'Internet cáp quang',      'Internet',         'ROOM',    'tháng',  100000),
    (2, 'Dịch vụ giặt ủi',         'Giặt ủi',          'PERSONAL','lần',    20000),
    (3, 'Điện sinh hoạt',          'Điện',             'ROOM',    'kWh',    3000),
    (4, 'Nước sinh hoạt',          'Nước',             'ROOM',    'm3',     7000);

-- =========================================
-- 8. Gán sinh viên vào phòng (bảng room_assignments)
--   Ví dụ: 4 sinh viên nam đầu tiên vào phòng 1 (loại nam 4 người),
--          4 sinh viên nữ tiếp theo vào phòng 2, ...
--   (Tùy chỉnh theo nhu cầu)
-- =========================================
INSERT INTO room_assignments (assigned_date, end_date, room_id, student_id)
VALUES
    -- Phòng 1 (A101, 4 nam)
    ('2025-01-03',NULL, 1, 4),
    ('2025-01-03',NULL, 1, 5),
    ('2025-01-03',NULL, 1, 6),
    ('2025-01-03',NULL, 1, 9),

    -- Phòng 2 (A102, 4 nữ)
    ('2025-01-05',NULL, 2, 7),
    ('2025-01-05',NULL, 2, 8),
    ('2025-01-05',NULL, 2, 10),
    ('2025-01-05',NULL, 2, 12);

-- Cập nhật lại current_occupancy cho các phòng đã có người ở:
UPDATE rooms SET current_occupancy = 4 WHERE room_id = 1;  -- 4 sinh viên
UPDATE rooms SET current_occupancy = 4 WHERE room_id = 2;  -- 4 sinh viên

-- =========================================
-- 9. Tạo hóa đơn (bills) mẫu để test
--   - Tạo hóa đơn 'phòng' (tiền phòng)
--   - Tạo hóa đơn 'điện-nước'
--   - Tạo hóa đơn 'dịch-vụ'
-- =========================================
INSERT INTO bills (bill_id, bill_type, billing_period, due_date, issue_date, status, total_amount, room_id, student_id)
VALUES
    -- Hóa đơn 1: tiền phòng cho Student 4
    (1, 'phòng', '2025-02', '2025-02-10', '2025-02-01', 'unpaid', 500000, 1, 4),

    -- Hóa đơn 2: điện-nước cho Student 4
    (2, 'điện-nước', '2025-02', '2025-02-10', '2025-02-01', 'unpaid', 40000, 1, 4),

    -- Hóa đơn 3: dịch-vụ cho Student 4 (VD: internet + giặt ủi)
    (3, 'dịch-vụ', '2025-02', '2025-02-10', '2025-02-01', 'unpaid', 120000, 1, 4),

    -- Thêm hóa đơn cho 1 sinh viên khác (VD: Student 5)
    (4, 'phòng', '2025-02', '2025-02-10', '2025-02-01', 'unpaid', 500000, 1, 5),
    (5, 'điện-nước', '2025-02', '2025-02-10', '2025-02-01', 'unpaid', 35000, 1, 5),
    (6, 'dịch-vụ', '2025-02', '2025-02-10', '2025-02-01', 'unpaid', 100000, 1, 5);

-- =========================================
-- 10. Thêm chi tiết hóa đơn (bill_items) cho hóa đơn 'dịch-vụ'
--     Mỗi bill_item gắn với một service cụ thể
-- =========================================
INSERT INTO bill_items (bill_item_id, amount, bill_id, service_id)
VALUES
    -- Hóa đơn 3 của student 4: Internet (100000) + Giặt ủi (20000)
    (1, 100000, 3, 1),  -- Internet
    (2, 20000,  3, 2),  -- Giặt ủi

    -- Hóa đơn 6 của student 5: chỉ Internet (100000)
    (3, 100000, 6, 1);

-- =========================================
-- 11. Thêm service_usage (giả lập chỉ số điện/nước) để bạn test việc tạo hóa đơn
--     Trong thực tế, bạn có thể tạo bill từ bảng này (khi invoiced = 'NO')
--     Ở đây ta ràng buộc bill_id (nếu đã được lập hóa đơn) hoặc NULL
-- =========================================
INSERT INTO service_usage
(usage_id, current_reading, invoiced, previous_reading, record_date, bill_id, room_id, service_id, student_id)
VALUES
    -- Giả sử Student 4 ở phòng 1, service Điện (id=3), Nước (id=4)
    (1, 120, 'YES', 100, '2025-01-25', 2, 1, 3, 4),   -- đã lập hóa đơn 2 (điện)
    (2, 25,  'YES', 20,  '2025-01-25', 2, 1, 4, 4),   -- đã lập hóa đơn 2 (nước)

    -- Giả sử Student 5 ở phòng 1, service Điện (id=3), Nước (id=4)
    (3, 110, 'YES', 90,  '2025-01-25', 5, 1, 3, 5),   -- đã lập hóa đơn 5 (điện)
    (4, 22,  'YES', 18,  '2025-01-25', 5, 1, 4, 5),   -- đã lập hóa đơn 5 (nước)

    -- Thêm ví dụ service_usage chưa lập hóa đơn (invoiced = 'NO')
    -- (để bạn có thể test tạo bill mới)
    (5, 130, 'NO',  120, '2025-02-26', NULL, 1, 3, 4),
    (6, 28,  'NO',  25,  '2025-02-26', NULL, 1, 4, 4),

    (7, 125, 'NO',  110, '2025-02-26', NULL, 1, 3, 5),
    (8, 24,  'NO',  22,  '2025-02-26', NULL, 1, 4, 5);

-- =========================================
-- 12. Thêm một vài payment (thanh toán) mẫu
--     (Tham chiếu tới các bill_id đã có)
-- =========================================
INSERT INTO payments (payment_id, amount_paid, payment_date, payment_method, status, bill_id)
VALUES
    (1,  200000, '2025-02-05', 'Chuyển khoản', 'pending', 1),  -- Mới thanh toán một phần
    (2,  500000, '2025-02-06', 'Tiền mặt',     'completed', 4),-- Trả đủ luôn
    (3,  40000,  '2025-02-07', 'Chuyển khoản', 'completed', 2); -- Trả đủ

-- =========================================
-- 13. Tùy chọn: thêm ví dụ về notifications
-- =========================================
INSERT INTO notifications (notification_id, created_at, message, read_status, title, student_id)
VALUES
    (1, '2025-02-05 09:00:00', 'Bạn có một hóa đơn mới cần thanh toán', 'unread', 'Thông báo hóa đơn', 4),
    (2, '2025-02-06 14:00:00', 'Thanh toán của bạn đã được ghi nhận',  'read',   'Xác nhận thanh toán', 5);

-- (Có thể thêm dữ liệu cho các bảng khác như applications, approved_applications,
--  student_service_registrations... nếu bạn cần)
--------------------------------------------------------------------------------
-- 1) THÊM CÁC ĐƠN ĐĂNG KÝ KÝ TÚC XÁ Ở BẢNG GỐC `applications`
--    (Xem chú thích bên dưới về cách VIEW pending_applications / approved_applications hoạt động)
--------------------------------------------------------------------------------
INSERT INTO applications
(application_id, approval_date, note, status, submission_date, approved_by, dorm_id, student_id)
VALUES
    -- Đơn #21: Student 11 vào KTX Khu B (dorm_id=2), đang chờ duyệt
    (21, NULL, 'Muốn ở KTX khu B', 'pending', '2025-03-01', NULL, 2, 11),
    -- Đơn #22: Student 12 vào KTX Khu B (dorm_id=2), đang chờ duyệt
    (22, NULL, 'Nhờ sắp xếp phòng Khu B', 'pending', '2025-03-01', NULL, 2, 12),
    -- Đơn #23: Student 13 vào KTX Khu A (dorm_id=1), đang chờ duyệt
    (23, NULL, 'Mong được ở gần căn tin khu A', 'pending', '2025-03-02', NULL, 1, 13),
    -- Đơn #24: Student 16 vào KTX Khu B, đã được duyệt bởi Manager 1
    (24, '2025-03-05', 'Hồ sơ đầy đủ, phù hợp', 'approved', '2025-03-01', 1, 2, 16),
    -- Đơn #25: Student 20 vào KTX Khu A, bị từ chối (rejected) bởi Manager 2
    (25, '2025-03-06', 'Thiếu một số giấy tờ quan trọng', 'rejected', '2025-03-02', 2, 1, 20);

--------------------------------------------------------------------------------
-- 2) THÊM CÁC ĐƠN ĐĂNG KÝ SỬ DỤNG DỊCH VỤ Ở BẢNG GỐC `student_service_registrations`
--    (Xem chú thích bên dưới về cách VIEW student_service_requests hoạt động)
--------------------------------------------------------------------------------
INSERT INTO student_service_registrations
(registration_id, actual_quantity, approval_date, end_date, start_date, status, approved_by, service_id, student_id)
VALUES
    -- #11: Student 7 đăng ký dịch vụ Internet (service_id=1) từ 2025-03-10 → 2025-06-10, đang chờ duyệt
    (11, NULL, NULL, '2025-06-10', '2025-03-10', 'pending', NULL, 1, 7),
    -- #12: Student 8 đăng ký dịch vụ Giặt ủi (service_id=2) từ 2025-03-05 → 2025-03-20, đã được duyệt (manager 2)
    (12, 5, '2025-03-06', '2025-03-20', '2025-03-05', 'approved', 2, 2, 8),
    -- #13: Student 10 đăng ký Internet (service_id=1), bị từ chối (rejected)
    (13, NULL, '2025-03-08', '2025-06-01', '2025-03-01', 'rejected', 3, 1, 10),
    -- #14: Student 14 đăng ký Internet (service_id=1) từ 2025-03-15 → 2025-04-15, đã được duyệt (manager 1)
    (14, NULL, '2025-03-16', '2025-04-15', '2025-03-15', 'approved', 1, 1, 14);


    -- Lên lịch tu dong tao hoa don tien phong.
    INSERT INTO billing_schedules (active, schedule_time) VALUES (b'1', NOW() + INTERVAL 1 MINUTE);


-- Hoàn tất script dữ liệu mẫu.
-- Bạn có thể chỉnh sửa/cơi nới thêm để phù hợp hơn với mục đích test của mình.


-- HOÀN TẤT GIAO DỊCH
COMMIT;