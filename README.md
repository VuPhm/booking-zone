## II. CHI TIẾT TIẾN ĐỘ CÁC CHỨC NĂNG

### 1. Các hạng mục ĐÃ HOÀN THÀNH (100%)
- **[Backend] Hệ thống Xác thực & Phân quyền (Auth Engine):** Hoàn thiện cụm API Đăng ký / Đăng nhập, cấu hình Spring Security Stateless kết hợp với bộ lọc mã hóa JWT Authentication.
- **[Backend] Quản lý Dịch vụ (ServiceItem):** Hoàn thành trọn bộ API CRUD. Tách biệt rõ endpoint công khai (Public GET) cho khách và endpoint bảo mật dành riêng cho ADMIN.
- **[Frontend] Khung xương & API Client:** Khởi tạo Next.js App Router, thiết lập cấu trúc thư mục Clean Code, cấu hình Axios Interceptors tự động đính kèm Token.
- **[Frontend] Luồng Auth & Quản lý State:** Hoàn thiện giao diện Đăng ký/Đăng nhập (Validation bằng Zod + React Hook Form), lưu trạng thái đồng bộ qua Zustand Store.
- **[Frontend] Cơ chế bảo mật cửa ngõ (Next.js Middleware):** Hoàn thành bộ lọc Middleware tập trung cấp Server-side, đánh chặn và điều hướng chuẩn xác theo Role.

### 2. Các hạng mục ĐANG TRIỂN KHAI & KẾ HOẠCH REFACTOR (Tái cấu trúc)
- **Quản lý Khung giờ (TimeSlot) & Kế hoạch Refactor nhánh:** Đạt **85%**
    - *Backend:* Hoàn thiện thiết kế thực thể (Entity), viết xong tầng Service và API phân quyền. Sửa triệt để lỗi đồng bộ cơ sở dữ liệu (Hibernate không tự sinh bảng trong PostgreSQL).
    - *Frontend:* Đang hoàn thiện giao diện hiển thị danh sách khung giờ còn trống (AVAILABLE).
    - *Kế hoạch Refactor:* Tạm hoãn merge nhánh với Frontend nhằm tái cấu trúc lại tầng Security Backend. Thay thế việc kiểm tra mật khẩu thủ công bằng `passwordEncoder.matches()` tại Controller sang bộ quản lý tập trung `AuthenticationManager` chuẩn Spring Security (tự động gọi `UserDetailsService`).
- **Quản lý Đơn đặt lịch (Booking) & Đồng bộ luồng dữ liệu:** Đạt **35%**
    - *Backend:* Hoàn thiện thiết kế cấu trúc quan hệ dữ liệu `@ManyToOne` và `@OneToOne` giữa các thực thể User - ServiceItem - TimeSlot.
    - *Frontend:* Đang thiết kế giao diện chọn ngày đặt lịch bằng thư viện `React Day Picker`.
    - *Kế hoạch Refactor:* Thực hiện bóc tách các Monolithic Component giao diện lớn thành các Sub-components độc lập; chuyển toàn bộ logic gọi API dùng chung về cấu trúc Custom Hooks và Zustand Actions thay vì gọi trực tiếp tại component giao diện.

### 3. Các hạng mục CHƯA TRIỂN KHAI (Giai đoạn tiếp theo)
- **Tích hợp cổng thanh toán:** VNPay Gateway (0%).
- **Hệ thống thông báo tự động:** Gửi mail xác nhận qua Resend/Mailtrap (0%).
- **Tối ưu UI/UX nâng cao:** Bổ sung Skeleton Loading, Toast thông báo (0%).

---

## III. PHÂN CÔNG NHÂN SỰ VÀ TRÁCH NHIỆM CỤ THỂ

| Thành viên | Trách nhiệm & Task thực tế đã triển khai | Tình trạng và Lý do chưa merge nhánh (Slot / Booking) |
| :--- | :--- | :--- |
| **Phạm Lê Trường Vũ** | • Khởi tạo dự án Next.js App Router, Tailwind CSS, Shadcn UI và cấu hình Axios Interceptor tự động gài Token JWT.<br>• Xây dựng giao diện Login/Register (Validate bằng Zod + React Hook Form), kết nối API Auth theo Username.<br>• Thiết lập Zustand Store đồng bộ dữ liệu phiên đăng nhập.<br>• Triển khai Next.js Middleware chặn và phân luồng định tuyến tự động theo Role.<br>• Hoàn thiện giao diện lưới duyệt và hiển thị danh sách gói dịch vụ (Service Listing Page). | **CHƯA MERGE NHÁNH (Đạt 85% task Slot - 35% task Booking)**<br>• Đã hoàn thiện mã nguồn giao diện (UI thô) hiển thị Khung giờ (TimeSlot) và luồng chọn lịch đặt (Booking) trên nhánh cá nhân.<br>• **Lý do chưa merge:** Đang thực hiện tái cấu trúc (refactor) luồng quản lý trạng thái, bóc tách component lớn thành các sub-component độc lập và cô lập logic gọi API về Custom Hooks / Zustand Actions. Nhánh được giữ lại để chờ Backend hoàn tất refactor cụm bảo mật nhằm thực hiện kiểm thử End-to-End đồng bộ. |
| **Phương Phi Long** | • Khởi tạo Spring Boot, thiết lập cấu trúc Monorepo, cấu hình kết nối database PostgreSQL và kích hoạt logs theo dõi SQL.<br>• Cấu hình Spring Security Stateless, bộ lọc JwtFilter và xây dựng hoàn chỉnh các API Đăng ký / Đăng nhập (`/api/v1/auth/**`).<br>• Xây dựng cấu trúc tầng dữ liệu (Entity, Repository, Service) và bóc tách Endpoint RESTful công khai (`/api/v1/services`) cùng API bảo mật cho Admin (`/api/v1/admin/services/**`).<br>• Thiết lập kịch bản và chạy thử nghiệm thành công cụm API bằng công cụ HTTP Client (`services-test.http`) trong IntelliJ. | **CHƯA MERGE NHÁNH (Đạt 85% task Slot - 35% task Booking)**<br>• Đã xây dựng xong Entity `TimeSlot` (kiểu LocalTime), sửa lỗi tự động sinh bảng, viết xong tầng Service và API quản lý giờ trống (`/api/v1/slots/available`), thiết lập sẵn quan hệ `@ManyToOne` và `@OneToOne` cho thực thể Booking.<br>• **Lý do chưa merge:** Phát hiện luồng đối chiếu mật khẩu thủ công ở Controller cũ chưa tối ưu qua kịch bản thử nghiệm `slots-test.http`. Hiện đang giữ nhánh để tái cấu trúc (refactor) cụm Auth Backend sang cấu trúc `AuthenticationManager` tiêu chuẩn của Spring Security (gọi qua `UserDetailsService`) trước khi tích hợp luồng đặt lịch. |