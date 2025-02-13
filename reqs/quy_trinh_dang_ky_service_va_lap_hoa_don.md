Dưới đây là **tóm tắt** hai quy trình chính trong hệ thống: **(1) Quy trình đăng ký dịch vụ của sinh viên** và **(2) Quy trình lập hóa đơn của người quản lý**.

---

## 1. Quy trình đăng ký dịch vụ của sinh viên

1. **Sinh viên xem danh sách dịch vụ**

   - Truy cập phần “Dịch vụ” để xem các thông tin: tên dịch vụ, mô tả, loại hình (cố định / theo usage), giá...

2. **Tạo yêu cầu đăng ký**

   - Sinh viên chọn dịch vụ muốn dùng và gửi yêu cầu đăng ký.
   - Hệ thống thêm một bản ghi mới trong `student_service_registrations`:
     - Ghi nhận `student_id`, `service_id`, ngày bắt đầu (`start_date`), trạng thái = `pending`.

3. **Người quản lý duyệt**

   - Người quản lý truy cập danh sách các yêu cầu (`pending`), xem chi tiết.
   - **Nếu duyệt**: cập nhật `status = 'approved'`, điền `approved_by`, `approval_date`.
   - **Nếu từ chối**: cập nhật `status = 'rejected'`, điền `approved_by`, `approval_date`.

4. **Hoàn tất đăng ký**
   - Sau khi được duyệt, sinh viên chính thức dùng dịch vụ (status = `approved`).
   - (Tuỳ chọn) Hệ thống gửi thông báo đến sinh viên trong bảng `notifications`.

---

## 2. Quy trình lập hóa đơn của người quản lý

1. **Thu thập dữ liệu tính phí**

   - Dựa trên **loại dịch vụ**:
     - **Dịch vụ theo usage (điện, nước...)**: lấy dữ liệu từ `service_usage` (current_reading, previous_reading), tính chênh lệch, nhân đơn giá.
     - **Dịch vụ cố định hằng tháng**: dùng `unit_price` trong `services`.
     - **Gộp nhiều dịch vụ**: tổng hợp chi phí cho từng dịch vụ liên quan đến sinh viên/ phòng.

2. **Tạo hoá đơn mới** (`bills`)

   - Ghi `student_id`, `issue_date`, `due_date`, `status = 'unpaid'`, v.v.
   - **Nếu hệ thống gộp nhiều dịch vụ** cho một hóa đơn, dùng bảng `bill_items` để liệt kê từng dịch vụ kèm chi phí.
   - Tính tổng tiền (`total_amount`) = tổng tất cả `bill_items`.

3. **Cập nhật trạng thái**

   - Sau khi tạo hoá đơn, **nếu có bảng `service_usage`**:
     - Đánh dấu `invoiced = 'YES'` cho các bản ghi usage đã được đưa vào hóa đơn.
   - Có thể gửi thông báo hóa đơn mới đến sinh viên.

4. **Thanh toán**
   - Sinh viên nộp tiền, hệ thống tạo `payment` tương ứng (`bill_id`, `amount_paid`, `payment_date`, ...).
   - Khi thanh toán đủ, cập nhật `bills.status = 'paid'`. Quá hạn thì `bills.status = 'overdue'`.

---

**Tóm lại**, với hai quy trình trên, hệ thống đảm bảo luồng nghiệp vụ khép kín: từ lúc sinh viên đăng ký dịch vụ, người quản lý xét duyệt cho đến khi tạo hóa đơn và sinh viên thanh toán.
