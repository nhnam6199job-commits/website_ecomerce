# website_ecomerce
This document outlines the product requirements for "Project Phoenix", a modern, robust, and scalable e-commerce website. The platform will provide a seamless shopping experience for customers and an intuitive management interface for administrators.
EPIC-1: Project Setup & Foundation (Sprint 0)
Mục tiêu: Thiết lập môi trường, cấu trúc dự án và database để đội ngũ có thể bắt đầu phát triển.

ID	Type	Title	Description
ST-01	Story	Setup Project Scaffolding	Thiết lập cấu trúc cơ bản cho cả backend và frontend.
TSK-01	Task	[BE] Initialize Spring Boot Project	Tạo dự án Spring Boot với các dependencies cần thiết (Spring Web, Spring Data JPA, Spring Security, MySQL Driver, JJWT).
TSK-02	Task	[BE] Configure Project Structure	Thiết lập các package: controller, service, repository, model, config, dto, security.
TSK-03	Task	[BE] Configure application.properties	Cấu hình kết nối database MySQL, port server, và các thuộc tính cơ bản.
TSK-04	Task	[FE] Initialize React.js Project	Sử dụng create-react-app hoặc Vite để tạo dự án React.
TSK-05	Task	[FE] Configure Project Structure	Thiết lập các folder: components, pages, services, hooks, contexts, utils.
TSK-06	Task	[DB] Create Initial Database Schema	Viết và chạy script SQL để tạo tất cả các bảng (users, products, categories, orders, order_items) với các ràng buộc khóa ngoại.
TSK-07	Task	[DEVOPS] Setup Git Repository	Khởi tạo Git repository và push cấu trúc dự án ban đầu lên (e.g., GitHub, GitLab).

Xuất sang Trang tính
EPIC-2: Authentication & User Management (Sprint 1)
Mục tiêu: Hoàn thiện luồng đăng ký, đăng nhập và quản lý thông tin cơ bản của người dùng.

ID	Type	Title	Description & Acceptance Criteria (AC)
ST-02	Story	User Registration	Là người dùng mới, tôi muốn đăng ký tài khoản bằng email và mật khẩu.
TSK-08	Task	[BE] Create User Entity & DTOs	Tạo User entity, và các DTOs cho request (RegisterRequest) và response.
TSK-09	Task	[BE] Implement Registration Endpoint	Tạo POST /api/v1/auth/register. Logic bao gồm: validate input, kiểm tra email tồn tại, hash mật khẩu bằng BCrypt, và lưu user mới với ROLE_CUSTOMER.
TSK-10	Task	[FE] Create Registration Page	Xây dựng form đăng ký với các trường: Full Name, Email, Password, Confirm Password và validation phía client.
TSK-11	Task	[FE] Implement Registration API Call	Gọi API đăng ký từ form và xử lý response (hiển thị thông báo thành công hoặc lỗi).
AC: User có thể tạo tài khoản thành công và dữ liệu được lưu vào DB. Mật khẩu phải được hash.
ST-03	Story	User Login	Là người dùng đã có tài khoản, tôi muốn đăng nhập để truy cập các tính năng cá nhân.
TSK-12	Task	[BE] Implement JWT Generation & Validation	Cấu hình Spring Security với JWT. Tạo JwtTokenProvider để tạo và xác thực token.
TSK-13	Task	[BE] Implement Login Endpoint	Tạo POST /api/v1/auth/login. Logic bao gồm: xác thực thông tin đăng nhập, nếu thành công thì tạo và trả về JWT.
TSK-14	Task	[FE] Create Login Page	Xây dựng form đăng nhập với Email, Password.
TSK-15	Task	[FE] Implement Login & State Management	Gọi API đăng nhập, khi thành công, lưu JWT vào localStorage và cập nhật global state (React Context/Redux) để xác nhận user đã đăng nhập.
TSK-16	Task	[FE] Create Authenticated Route Guard	Tạo HOC (Higher-Order Component) hoặc logic để bảo vệ các route yêu cầu đăng nhập.
AC: User đăng nhập thành công sẽ nhận được JWT. User có thể truy cập các trang được bảo vệ.

Xuất sang Trang tính
EPIC-3: Product Catalog (Sprint 2)
Mục tiêu: Xây dựng chức năng quản lý sản phẩm cho admin và hiển thị sản phẩm cho người dùng.

ID	Type	Title	Description & AC
ST-04	Story	Admin: Product Management (CRUD)	Là admin, tôi muốn tạo, xem, cập nhật, và xóa sản phẩm.
TSK-17	Task	[BE] Create Product & Category Entities	Tạo Product và Category entities, DTOs và repositories.
TSK-18	Task	[BE] Implement Admin Product Endpoints	Tạo AdminProductController với các endpoint CRUD (/api/v1/admin/products). Bảo vệ các endpoint này, chỉ cho phép ROLE_ADMIN.
TSK-19	Task	[FE] Create Admin Product Management Page	Xây dựng giao diện trang quản lý sản phẩm (dạng bảng) trong khu vực admin.
TSK-20	Task	[FE] Create "Add/Edit Product" Form	Tạo một form (có thể tái sử dụng) để admin nhập thông tin sản phẩm.
TSK-21	Task	[FE] Implement Admin Product API Calls	Tích hợp các API CRUD sản phẩm vào giao diện admin.
AC: Admin có thể thêm, sửa, xóa sản phẩm. Chỉ admin mới có thể truy cập chức năng này.
ST-05	Story	User: View Products	Là người dùng, tôi muốn xem danh sách sản phẩm và chi tiết từng sản phẩm.
TSK-22	Task	[BE] Implement Public Product Endpoints	Tạo ProductController với GET /api/v1/products (hỗ trợ pagination) và GET /api/v1/products/{id}.
TSK-23	Task	[FE] Create Product Listing Page	Xây dựng trang hiển thị danh sách sản phẩm dưới dạng lưới (grid), lấy dữ liệu từ API.
TSK-24	Task	[FE] Create Product Detail Page	Xây dựng trang hiển thị chi tiết một sản phẩm (ảnh, mô tả, giá,...).
TSK-25	Task	[FE] Implement Search & Filter (Basic)	Thêm ô tìm kiếm cơ bản trên trang danh sách sản phẩm để lọc theo tên.
AC: Mọi người dùng (kể cả khách) đều có thể xem danh sách và chi tiết sản phẩm.

Xuất sang Trang tính
EPIC-4: Shopping Cart & Checkout (Sprint 3)
Mục tiêu: Cho phép người dùng thêm sản phẩm vào giỏ hàng và tiến hành thanh toán.

ID	Type	Title	Description & AC
ST-06	Story	Manage Shopping Cart	Là người dùng đã đăng nhập, tôi muốn thêm/xóa sản phẩm và thay đổi số lượng trong giỏ hàng.
TSK-26	Task	[BE] Implement Cart Logic	Backend quyết định lưu giỏ hàng trong state của React (cho MVP) hay DB. Tạm chọn lưu ở Frontend state để đơn giản hóa.
TSK-27	Task	[FE] Implement Cart State Management	Sử dụng React Context hoặc Redux để quản lý trạng thái giỏ hàng (danh sách sản phẩm, số lượng).
TSK-28	Task	[FE] Create "Add to Cart" Functionality	Thêm nút "Add to Cart" trên trang sản phẩm. Khi click, cập nhật state của giỏ hàng.
TSK-29	Task	[FE] Create Cart Page	Xây dựng trang giỏ hàng hiển thị các sản phẩm đã thêm, cho phép thay đổi số lượng, xóa sản phẩm và hiển thị tổng tiền.
AC: Người dùng có thể thêm sản phẩm vào giỏ. Các thay đổi (số lượng, xóa) được cập nhật chính xác.
ST-07	Story	Checkout Process	Là người dùng, tôi muốn tiến hành thanh toán từ giỏ hàng.
TSK-30	Task	[BE] Create Order & OrderItem Entities	Đảm bảo các entities đã sẵn sàng từ TSK-06.
TSK-31	Task	[BE] Implement "Create Order" Endpoint	Tạo POST /api/v1/orders. Đây là một giao dịch (transaction): tạo Order mới, tạo các OrderItem từ giỏ hàng, (tùy chọn: giảm số lượng stock_quantity của sản phẩm).
TSK-32	Task	[FE] Create Checkout Page	Xây dựng trang thanh toán yêu cầu người dùng nhập địa chỉ giao hàng.
TSK-33	Task	[FE] Implement "Place Order" API Call	Khi người dùng xác nhận đơn hàng, gửi thông tin giỏ hàng và địa chỉ đến backend.
TSK-34	Task	[FE] Create Order Confirmation Page	Sau khi đặt hàng thành công, điều hướng người dùng đến trang xác nhận "Thank You".
AC: User có thể đặt hàng thành công. Một record orders và các record order_items tương ứng được tạo trong DB.

Xuất sang Trang tính
EPIC-5: Order Management (Sprint 4)
Mục tiêu: Cho phép người dùng xem lại lịch sử đơn hàng và admin quản lý các đơn hàng đó.

ID	Type	Title	Description & AC
ST-08	Story	User: View Order History	Là người dùng đã đăng nhập, tôi muốn xem lại các đơn hàng đã đặt.
TSK-35	Task	[BE] Implement "Get My Orders" Endpoint	Tạo GET /api/v1/orders/me để trả về danh sách đơn hàng của người dùng đang đăng nhập.
TSK-36	Task	[FE] Create "My Orders" Page	Trong khu vực tài khoản người dùng, tạo trang hiển thị danh sách các đơn hàng đã đặt cùng trạng thái của chúng.
AC: Người dùng chỉ thấy được đơn hàng của chính mình.
ST-09	Story	Admin: Manage Orders	Là admin, tôi muốn xem tất cả đơn hàng và cập nhật trạng thái của chúng.
TSK-37	Task	[BE] Implement Admin Order Endpoints	Tạo GET /api/v1/admin/orders và PUT /api/v1/admin/orders/{id}/status. Bảo vệ các endpoint này cho ROLE_ADMIN.
TSK-38	Task	[FE] Create Admin Order Management Page	Xây dựng giao diện cho admin để xem danh sách tất cả các đơn hàng.
TSK-39	Task	[FE] Implement "Update Status" Functionality	Thêm chức năng (vd: dropdown) để admin thay đổi trạng thái đơn hàng (Pending -> Shipped -> Delivered).
AC: Admin có thể xem tất cả đơn hàng và cập nhật trạng thái của chúng thành công.
