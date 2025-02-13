Dưới đây là **một quy trình mẫu** để xử lý hóa đơn điện/nước cho **từng phòng** (chỉ tạo **một hóa đơn** duy nhất gắn với phòng). Mục tiêu của quy trình là:

- Hệ thống ghi nhận lượng sử dụng (điện, nước...) theo phòng.
- Mỗi phòng chỉ có **một hóa đơn** chung cho dịch vụ điện nước trong kỳ.
- **Một người đại diện** của phòng sẽ thực hiện thanh toán thay mặt cho các thành viên còn lại.
- Hệ thống **không** chia tiền chi tiết giữa các thành viên.

---

## 1. Thu thập dữ liệu usage cho phòng

1. **Ghi nhận chỉ số điện nước**

   - Mỗi lần ghi chỉ số, hệ thống tạo một bản ghi trong `service_usage` với `room_id`, `service_id` (điện hoặc nước), `previous_reading`, `current_reading`, `record_date`, `invoiced = 'NO'` (chưa lập hóa đơn).
   - Nếu phòng có nhiều thành viên, hệ thống **vẫn** chỉ xem phòng như một đơn vị. Không tách usage cho từng người.

2. **Tính toán chi phí**
   - Định kỳ (mỗi tháng, hoặc theo chu kỳ) hoặc khi cần lập hóa đơn, hệ thống lấy các dòng `service_usage` có `room_id` = X, `invoiced = 'NO'`.
   - Tính tiền theo công thức:  
     \[
     \text{Tổng chi phí} = \sum\_{mỗi service_usage} (current_reading - previous_reading) \times unit_price
     \]
   - Hoặc nếu nhà trường có **giá nhiều bậc** (bậc thang theo số kWh/m³), có thể áp dụng logic tính toán nâng cao hơn, nhưng nguyên tắc vẫn là gộp cho toàn bộ phòng.

---

## 2. Tạo hóa đơn cho phòng

1. **Tạo một bản ghi trong `bills`**

   - `room_id`: tham chiếu đến phòng đang lập hóa đơn.
   - `student_id`: tùy cấu trúc, có thể **bỏ trống** hoặc **chọn đại diện**. Nếu trong thiết kế DB bắt buộc phải có `student_id`, bạn có thể gán cho người đại diện.
   - `issue_date`, `due_date`, `billing_period`, `status = 'unpaid'`, `total_amount` = tổng chi phí điện/nước của phòng.

2. **Ghi chi tiết trong `bill_items` (nếu có)**

   - Nếu bạn dùng bảng `bill_items`, mỗi dòng `bill_items` sẽ tham chiếu `service_id` (điện, nước), kèm số tiền `amount` tương ứng.
   - Sau khi thêm xong các items, tính `total_amount` = tổng tất cả `amount`.

3. **Đánh dấu usage đã được lập hóa đơn**

   - Cập nhật `service_usage.invoiced = 'YES'` cho các bản ghi usage vừa tính.
   - Mục đích: tránh việc một lần sử dụng bị đưa vào nhiều kỳ hóa đơn.

4. **Thông báo cho phòng / người đại diện**
   - Hệ thống có thể gửi thông báo (bảng `notifications`) cho toàn bộ thành viên của phòng hoặc riêng cho người đại diện về việc “Hóa đơn phòng X đã được tạo”.

---

## 3. Thanh toán của người đại diện

1. **Một người đại diện thanh toán**

   - Trong thực tế, phòng tự thỏa thuận xem ai sẽ là người thanh toán.
   - Hệ thống chỉ ghi nhận **một giao dịch** (một payment) cho hóa đơn:
     - Tạo `payments`: `bill_id`, `amount_paid`, `payment_date`, `payment_method`, `status = 'completed'`.
   - Hệ thống không theo dõi việc chia tiền giữa các thành viên.

2. **Cập nhật trạng thái hóa đơn**
   - Khi `amount_paid` >= `total_amount`, cập nhật `bills.status = 'paid'`.
   - Nếu quá hạn `due_date` mà chưa trả đủ, chuyển `bills.status = 'overdue'`.

---

## 4. Gợi ý hoặc lưu ý về cấu trúc CSDL

- **Bảng `bills`**:
  - Có cột `room_id` (để gắn hóa đơn với phòng).
  - `student_id` có thể để **nullable** (nếu bạn không cần biết cụ thể ai là người đại diện). Hoặc bạn có thể dùng cột `paid_by` hay `representative_id` để rõ ràng người trả.
- **Bảng `service_usage`**:
  - Đảm bảo có cột `room_id`.
  - Mỗi lần ghi nhận usage, `student_id` có thể để trống hoặc bạn có thể gán ngẫu nhiên 1 sinh viên của phòng. Nhưng do **tính phí cho cả phòng**, logic quan trọng nhất là `room_id`.
- **Bảng `payments`**:
  - Có cột `bill_id` (bắt buộc) để biết hóa đơn nào được thanh toán.
  - Thêm cột `paid_by` (liên kết `student_id`) nếu muốn biết người trả là ai. (Nếu đã có `student_id` trong `bills`, cột này có thể trùng ý nghĩa. Bạn tùy chọn thiết kế.)

---

## Tóm tắt quy trình

1. **Ghi nhận usage** (điện, nước...) cho phòng → 2. **Tính toán** tổng phí cho phòng → 3. **Tạo một hóa đơn duy nhất** (chỉ gắn `room_id`, có thể gắn `student_id` = người đại diện) → 4. **Người đại diện thanh toán** → 5. Hóa đơn chuyển `paid`, các thành viên trong phòng tự chia tiền bên ngoài, hệ thống không can thiệp.

Quy trình này đảm bảo mỗi phòng chỉ có **một hóa đơn** cho điện/nước mỗi kỳ, và việc chia nhỏ chi phí giữa các thành viên hoàn toàn do nội bộ phòng tự xử lý.
