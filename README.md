# 📅 Booking & Service Management System (Hệ thống Đặt lịch & Quản lý Doanh nghiệp)

Hệ thống quản lý và đặt lịch dịch vụ đa bên (Multi-tenant), hỗ trợ phân quyền chặt chẽ giữa Quản trị viên hệ thống (Platform Admin), Chủ cửa hàng đối tác (Provider Admin), và Khách hàng (Customer). Hệ thống áp dụng kiến trúc tách rời (Decoupled Architecture) với độ an toàn dữ liệu cao và cơ chế kiểm soát trạng thái mạng tối ưu.

---

## 🗺️ Sơ đồ Kiến trúc & Định vị Mã nguồn (Project Landscape)

Dự án được cấu trúc thành hai phân hệ độc lập chạy song song, phân tác rõ ràng giữa tầng Lưu trữ logic và tầng Hiển thị giao diện:

* **Phân hệ Backend (Spring Boot API Engine):** Xử lý logic nghiệp vụ xử lý đặt lịch nguyên tử, kiểm soát băm mật khẩu, bóc tách phân quyền an toàn và ngăn chặn bẫy trùng lịch.
    * *Đường dẫn cục bộ:* `./booking-system-backend`
    * *Kho mã nguồn:* [https://github.com/VuPhm/booking-system-backend]
* **Phân hệ Frontend (Next.js App Router Interface):** Hệ thống giao diện tương tác động cao, tối ưu hóa lưu bộ nhớ đệm client, chốt chặn bảo mật Server-side.
    * *Đường dẫn cục bộ:* `./booking-system-frontend`
    * *Kho mã nguồn:* [https://github.com/VuPhm/booking-system-frontend]

---

## 📁 Cấu trúc Thư mục Tổng thể (Directory Blueprint)

```text
booking-zone/ (Thư mục gốc tổng)
├── booking-system-backend/       # Phân hệ xử lý Logic & Database
│   ├── src/main/java/edu/uaf/    # Mã nguồn Java Spring Boot
│   ├── pom.xml                   # Quản lý thư viện Maven
│   └── api-test.http             # Kịch bản kiểm thử API tự động
├── booking-system-frontend/      # Phân hệ Hiển thị & Phiên giao diện
│   ├── src/app/                  # Cấu trúc Next.js App Router (v15)
│   ├── src/store/                # Quản lý Client State (Zustand)
│   └── src/middleware.ts         # Chốt chặn điều hướng Server-side Cookie
└── README.md                     # Tài liệu đặc tả tổng thể hệ thống
