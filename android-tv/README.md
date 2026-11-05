# Bé học rồi xem TV - Sony Android TV

Ứng dụng dành cho gia đình: Sau mỗi 10 phút (có thể điều chỉnh), một câu hỏi kiến thức lớp 6 xuất hiện dưới dạng lớp phủ. Trả lời đúng để xem tiếp, trả lời sai để thử câu khác. Bản thử nghiệm có 50 câu Toán, Ngữ văn, Tiếng Anh, Khoa học tự nhiên, Lịch sử và Địa lí.

## Mã nguồn và build

- Nhánh `be-hoc-roi-xem-tv` chứa dự án tại `android-tv/`.
- Workflow `Build Be Hoc Roi Xem TV APK` chạy trên GitHub Actions sau push, hoặc có thể chạy thủ công nếu GitHub hiển thị nút đó.
- Mở Actions, chọn bản chạy, tải artifact `BeHocRoiXemTV-Lop6-APK`, giải nén để lấy `app-debug.apk`.
- APK chưa được thử trên phần cứng Sony KD-49X8500H.

## Cài qua mạng, không cần USB

**Cách 1 (máy Android):** Điện thoại và TV cùng Wi-Fi. Cài ứng dụng truyền tệp qua mạng trên cả hai thiết bị nếu Play Store có phiên bản tương thích. Tải APK trên điện thoại rồi chuyển tới TV; dùng file manager mở và cài APK. Cho phép ứng dụng file manager cài từ nguồn này nếu TV yêu cầu.

**Cách 2 (iPhone hoặc bất kỳ điện thoại nào):** Có thể tải APK về máy tính, rồi dùng trình quản lý file/HTTP server trong mạng nội bộ để cung cấp một đường dẫn HTTP tới file APK. Trên TV cài Downloader by AFTVnews hoặc trình duyệt/tệp phù hợp, nhập URL trực tiếp của APK (không phải ZIP/artifact GitHub), rồi Install. URL cần do người dùng tự tạo hoặc có máy chủ lưu trữ APK. Không nhập tài khoản GitHub vào website tải APK lạ.

## Quyền trên TV

Cấp quyền Display over other apps / Hiển thị trên ứng dụng khác, mở ứng dụng và chọn Start. Dùng nút Test quiz để kiểm tra remote, sau đó mở YouTube và chờ đủ 10 phút. Dịch vụ nền và cửa sổ đè có thể bị hệ điều hành TV ngăn chặn. Không dùng như công cụ khóa truy cập chống vượt qua; video vẫn có thể tiếp tục phát tiếng ở nền.

## An toàn

APK debug chỉ để thử nghiệm cá nhân, không dùng cho phát hành công khai. Nên quét tệp và chỉ cài APK từ nguồn tin cậy. Không lưu dữ liệu cá nhân học sinh hay mật khẩu trong ứng dụng.
