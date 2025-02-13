Dưới đây là một bản tóm tắt các màn hình (screens) cần có cho hai quy trình và các wireframe HTML đơn giản để hiển thị các form, bảng và liên kết giữa các màn hình.

---

## A. Quy trình đăng ký dịch vụ của sinh viên

### Các màn hình cần có

1. **Student Dashboard (Danh sách dịch vụ)**

   - Hiển thị danh sách các dịch vụ khả dụng.
   - Mỗi dịch vụ có nút “Register” để chuyển sang form đăng ký.
   - Liên kết đến trang “Registration Status” (trạng thái đăng ký).

2. **Service Registration Form (Form đăng ký dịch vụ)**

   - Hiển thị thông tin dịch vụ (read-only) và các trường cần nhập như ngày bắt đầu, (có thể là ngày kết thúc).
   - Nút “Submit Registration” gửi yêu cầu đăng ký.

3. **Registration Status (Trạng thái đăng ký)**
   - Hiển thị thông báo kết quả (ví dụ: “Pending”, “Approved”, “Rejected”).
   - Liên kết quay lại Student Dashboard.

---

### Wireframe HTML cho quy trình đăng ký dịch vụ của sinh viên

#### 1. Student Dashboard – Danh sách dịch vụ

```html
<!-- student_dashboard.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Student Dashboard - Available Services</title>
  </head>
  <body>
    <h1>Available Services</h1>
    <table border="1">
      <tr>
        <th>Service Name</th>
        <th>Description</th>
        <th>Price</th>
        <th>Action</th>
      </tr>
      <tr>
        <td>Service 1</td>
        <td>Description for service 1</td>
        <td>$10</td>
        <td><a href="registration_form.html?service=1">Register</a></td>
      </tr>
      <tr>
        <td>Service 2</td>
        <td>Description for service 2</td>
        <td>$15</td>
        <td><a href="registration_form.html?service=2">Register</a></td>
      </tr>
    </table>
    <p><a href="registration_status.html">View Registration Status</a></p>
  </body>
</html>
```

#### 2. Service Registration Form – Form đăng ký dịch vụ

```html
<!-- registration_form.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Service Registration Form</title>
  </head>
  <body>
    <h1>Service Registration Form</h1>
    <form action="submit_registration.html" method="post">
      <label for="serviceName">Service Name:</label>
      <input
        type="text"
        id="serviceName"
        name="serviceName"
        readonly
        value="Service 1"
      /><br /><br />

      <label for="startDate">Start Date:</label>
      <input type="date" id="startDate" name="startDate" required /><br /><br />

      <label for="endDate">End Date (optional):</label>
      <input type="date" id="endDate" name="endDate" /><br /><br />

      <input type="submit" value="Submit Registration" />
    </form>
    <p><a href="student_dashboard.html">Back to Dashboard</a></p>
  </body>
</html>
```

#### 3. Registration Status – Trạng thái đăng ký

```html
<!-- registration_status.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Registration Status</title>
  </head>
  <body>
    <h1>Your Service Registration Status</h1>
    <p>Registration for Service 1 is currently: <strong>Pending</strong>.</p>
    <p><a href="student_dashboard.html">Back to Dashboard</a></p>
  </body>
</html>
```

---

## B. Quy trình lập hóa đơn của người quản lý

### Các màn hình cần có

1. **Manager Dashboard**

   - Hiển thị danh sách các yêu cầu đăng ký dịch vụ đang chờ duyệt.
   - Bảng liệt kê các đăng ký với thông tin như Registration ID, Student, Service, Start Date.
   - Nút “Review” để duyệt từng đăng ký.
   - Khu vực “Billing” với nút “Create Invoice” để chuyển sang màn hình lập hóa đơn.

2. **Registration Approval Screen**

   - Hiển thị chi tiết yêu cầu đăng ký của sinh viên.
   - Cho phép nhập nhận xét (approval comment) và có nút “Approve” hoặc “Reject”.
   - Liên kết quay lại Manager Dashboard.

3. **Billing Creation Screen**

   - Form nhập các thông tin chung của hóa đơn: Billing Period, Issue Date, Due Date.
   - Hiển thị bảng liệt kê các “Invoice Items” (các dịch vụ và số tiền tương ứng).
   - Tính và hiển thị “Total Amount”.
   - Nút “Generate Invoice” để tạo hóa đơn.
   - Liên kết quay lại Manager Dashboard.

4. **Invoice Summary Screen (Tùy chọn)**
   - Hiển thị chi tiết hóa đơn đã tạo, bao gồm danh sách các invoice items và tổng tiền.
   - Liên kết quay lại Manager Dashboard.

---

### Wireframe HTML cho quy trình lập hóa đơn của người quản lý

#### 1. Manager Dashboard

```html
<!-- manager_dashboard.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Manager Dashboard</title>
  </head>
  <body>
    <h1>Manager Dashboard</h1>

    <h2>Pending Service Registrations</h2>
    <table border="1">
      <tr>
        <th>Registration ID</th>
        <th>Student</th>
        <th>Service</th>
        <th>Start Date</th>
        <th>Action</th>
      </tr>
      <tr>
        <td>1</td>
        <td>John Doe</td>
        <td>Service 1</td>
        <td>2025-02-15</td>
        <td><a href="registration_approval.html?id=1">Review</a></td>
      </tr>
    </table>

    <h2>Billing</h2>
    <p><a href="billing_creation.html">Create Invoice</a></p>
  </body>
</html>
```

#### 2. Registration Approval Screen

```html
<!-- registration_approval.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Review Service Registration</title>
  </head>
  <body>
    <h1>Review Service Registration</h1>
    <p><strong>Registration ID:</strong> 1</p>
    <p><strong>Student:</strong> John Doe</p>
    <p><strong>Service:</strong> Service 1</p>
    <p><strong>Start Date:</strong> 2025-02-15</p>
    <p><strong>Status:</strong> Pending</p>

    <form action="update_registration.html" method="post">
      <label for="approvalComment">Approval Comment:</label><br />
      <textarea
        id="approvalComment"
        name="approvalComment"
        rows="4"
        cols="50"
      ></textarea
      ><br /><br />

      <button type="submit" name="action" value="approve">Approve</button>
      <button type="submit" name="action" value="reject">Reject</button>
    </form>
    <p><a href="manager_dashboard.html">Back to Dashboard</a></p>
  </body>
</html>
```

#### 3. Billing Creation Screen

```html
<!-- billing_creation.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Create Invoice</title>
  </head>
  <body>
    <h1>Create Invoice</h1>
    <form action="submit_invoice.html" method="post">
      <label for="billingPeriod">Billing Period:</label>
      <input
        type="text"
        id="billingPeriod"
        name="billingPeriod"
        placeholder="YYYY-MM"
        required
      /><br /><br />

      <label for="issueDate">Issue Date:</label>
      <input type="date" id="issueDate" name="issueDate" required /><br /><br />

      <label for="dueDate">Due Date:</label>
      <input type="date" id="dueDate" name="dueDate" required /><br /><br />

      <h2>Invoice Items</h2>
      <table border="1">
        <tr>
          <th>Service</th>
          <th>Usage/Quantity</th>
          <th>Amount</th>
        </tr>
        <tr>
          <td>Service 1</td>
          <td>100 units</td>
          <td>$10.00</td>
        </tr>
        <tr>
          <td>Service 2</td>
          <td>50 units</td>
          <td>$7.50</td>
        </tr>
      </table>
      <br />
      <label for="totalAmount">Total Amount:</label>
      <input
        type="text"
        id="totalAmount"
        name="totalAmount"
        value="$17.50"
        readonly
      /><br /><br />

      <input type="submit" value="Generate Invoice" />
    </form>
    <p><a href="manager_dashboard.html">Back to Dashboard</a></p>
  </body>
</html>
```

#### 4. Invoice Summary Screen (Tùy chọn)

```html
<!-- invoice_summary.html -->
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Invoice Summary</title>
  </head>
  <body>
    <h1>Invoice Summary</h1>
    <p><strong>Invoice ID:</strong> 101</p>
    <p><strong>Billing Period:</strong> 2025-02</p>
    <p><strong>Issue Date:</strong> 2025-02-28</p>
    <p><strong>Due Date:</strong> 2025-03-10</p>
    <h2>Invoice Items</h2>
    <table border="1">
      <tr>
        <th>Service</th>
        <th>Usage/Quantity</th>
        <th>Amount</th>
      </tr>
      <tr>
        <td>Service 1</td>
        <td>100 units</td>
        <td>$10.00</td>
      </tr>
      <tr>
        <td>Service 2</td>
        <td>50 units</td>
        <td>$7.50</td>
      </tr>
    </table>
    <p><strong>Total Amount:</strong> $17.50</p>
    <p><a href="manager_dashboard.html">Back to Dashboard</a></p>
  </body>
</html>
```

---

## Tóm tắt

- **Sinh viên**:

  - **Student Dashboard**: Xem danh sách dịch vụ và truy cập form đăng ký.
  - **Service Registration Form**: Nhập thông tin (ngày bắt đầu, ngày kết thúc) và gửi đăng ký.
  - **Registration Status**: Kiểm tra trạng thái đăng ký.

- **Người quản lý**:
  - **Manager Dashboard**: Xem các đăng ký đang chờ duyệt và truy cập vào khu vực lập hóa đơn.
  - **Registration Approval Screen**: Xem chi tiết đăng ký, duyệt hay từ chối.
  - **Billing Creation Screen**: Nhập thông tin hóa đơn, liệt kê các dịch vụ (invoice items) và tạo hóa đơn.
  - **Invoice Summary Screen** (nếu cần): Xem lại chi tiết hóa đơn đã tạo.

Những wireframe HTML trên đây mô phỏng luồng điều hướng cơ bản giữa các màn hình, từ đó bạn có thể phát triển giao diện đầy đủ và tích hợp logic nghiệp vụ theo yêu cầu.
