package vn.behocroitv;
import java.util.Random;
public final class QuizBank {
 public static final class Question {
  public final String subject,text; public final String[] answers; public final int correct;
  Question(String subject,String text,String[] answers,int correct){this.subject=subject;this.text=text;this.answers=answers;this.correct=correct;}
 }
 static final Question[] ALL = {
    new Question("Toán","ƯCLN của 12 và 18 là bao nhiêu?",new String[]{"3","6","9","12"},1),
    new Question("Toán","BCNN của 4 và 6 là bao nhiêu?",new String[]{"6","8","12","24"},2),
    new Question("Toán","Số nào là số nguyên tố?",new String[]{"9","15","17","21"},2),
    new Question("Toán","5 mũ 2 bằng bao nhiêu?",new String[]{"10","20","25","50"},2),
    new Question("Toán","3/4 của 20 bằng bao nhiêu?",new String[]{"12","15","16","18"},1),
    new Question("Toán","Số đối của -7 là số nào?",new String[]{"-7","0","7","14"},2),
    new Question("Toán","(-3) + 8 bằng bao nhiêu?",new String[]{"-11","-5","5","11"},2),
    new Question("Toán","Một góc vuông có số đo?",new String[]{"45 độ","60 độ","90 độ","180 độ"},2),
    new Question("Toán","Chu vi hình vuông cạnh 5 cm?",new String[]{"10 cm","20 cm","25 cm","30 cm"},1),
    new Question("Toán","15 chia hết cho số nào?",new String[]{"2","4","5","8"},2),
    new Question("Toán","Số nào là ước của 24?",new String[]{"5","7","8","11"},2),
    new Question("Toán","2 mũ 4 bằng bao nhiêu?",new String[]{"8","12","16","24"},2),
    new Question("Ngữ văn","Từ nào sau đây là từ láy?",new String[]{"Xe đạp","Lấp lánh","Sách vở","Bàn ghế"},1),
    new Question("Ngữ văn","Từ nào chỉ hoạt động?",new String[]{"Chạy","Xanh","Đẹp","Tròn"},0),
    new Question("Ngữ văn","Từ nào là danh từ?",new String[]{"Nhanh","Bàn học","Đỏ","Chạy"},1),
    new Question("Ngữ văn","Biện pháp tu từ trong 'Mặt trời như quả cầu lửa'?",new String[]{"Nhân hóa","Điệp ngữ","So sánh","Ẩn dụ"},2),
    new Question("Ngữ văn","Truyện cổ tích thường có yếu tố nào?",new String[]{"Số liệu","Kì ảo","Công thức","Bản tin"},1),
    new Question("Ngữ văn","Từ nào là tính từ?",new String[]{"Chăm chỉ","Bơi","Vở","Trường"},0),
    new Question("Tiếng Anh","She ___ to school every day.",new String[]{"go","goes","going","gone"},1),
    new Question("Tiếng Anh","There ___ two books on the desk.",new String[]{"is","am","are","be"},2),
    new Question("Tiếng Anh","The opposite of 'big' is ___",new String[]{"long","tall","small","fast"},2),
    new Question("Tiếng Anh","I ___ twelve years old.",new String[]{"is","am","are","be"},1),
    new Question("Tiếng Anh","They ___ football on Sundays.",new String[]{"plays","play","playing","played"},1),
    new Question("Tiếng Anh","This is ___ apple.",new String[]{"a","an","the","any"},1),
    new Question("Tiếng Anh","What is the plural of 'child'?",new String[]{"childs","children","childes","childrens"},1),
    new Question("Tiếng Anh","My mother ___ a teacher.",new String[]{"are","am","is","be"},2),
    new Question("Tiếng Anh","What color is the sky on a clear day?",new String[]{"green","blue","red","brown"},1),
    new Question("Tiếng Anh","How many days are there in a week?",new String[]{"five","six","seven","eight"},2),
    new Question("Tiếng Anh","We go to school ___ Monday.",new String[]{"in","on","at","by"},1),
    new Question("Tiếng Anh","Which word is a fruit?",new String[]{"carrot","apple","potato","onion"},1),
    new Question("Khoa học tự nhiên","Nước đóng băng ở nhiệt độ nào (áp suất thường)?",new String[]{"0 độ C","10 độ C","50 độ C","100 độ C"},0),
    new Question("Khoa học tự nhiên","Cơ quan nào giúp con người hô hấp?",new String[]{"Gan","Phổi","Dạ dày","Thận"},1),
    new Question("Khoa học tự nhiên","Đơn vị đo khối lượng thường dùng?",new String[]{"Mét","Giây","Kilôgam","Lít"},2),
    new Question("Khoa học tự nhiên","Lực nào kéo vật về phía Trái Đất?",new String[]{"Lực ma sát","Trọng lực","Lực đẩy","Lực đàn hồi"},1),
    new Question("Khoa học tự nhiên","Quá trình cây xanh tạo chất hữu cơ nhờ ánh sáng?",new String[]{"Hô hấp","Quang hợp","Tiêu hóa","Thoát hơi nước"},1),
    new Question("Khoa học tự nhiên","Vật nào dẫn điện tốt?",new String[]{"Nhựa","Gỗ khô","Đồng","Cao su"},2),
    new Question("Khoa học tự nhiên","Nhiệt kế dùng để đo gì?",new String[]{"Khối lượng","Nhiệt độ","Độ dài","Thời gian"},1),
    new Question("Khoa học tự nhiên","Động vật nào thuộc nhóm cá?",new String[]{"Cá chép","Ếch","Thằn lằn","Chim sẻ"},0),
    new Question("Khoa học tự nhiên","Bộ phận nào của cây hút nước chủ yếu?",new String[]{"Hoa","Lá","Rễ","Quả"},2),
    new Question("Khoa học tự nhiên","Không khí là gì?",new String[]{"Một chất tinh khiết","Hỗn hợp khí","Một chất rắn","Một chất lỏng"},1),
    new Question("Lịch sử và Địa lí","Trái Đất có dạng gần giống hình gì?",new String[]{"Hình lập phương","Hình cầu","Hình trụ","Hình nón"},1),
    new Question("Lịch sử và Địa lí","Hướng đối diện với hướng Bắc?",new String[]{"Đông","Tây","Nam","Đông Bắc"},2),
    new Question("Lịch sử và Địa lí","Kinh tuyến gốc có kinh độ bao nhiêu?",new String[]{"0 độ","30 độ","90 độ","180 độ"},0),
    new Question("Lịch sử và Địa lí","Bản đồ dùng để thể hiện điều gì?",new String[]{"Bề mặt Trái Đất thu nhỏ","Bài hát","Bài thơ","Một phép tính"},0),
    new Question("Lịch sử và Địa lí","Kí hiệu bản đồ được giải thích ở đâu?",new String[]{"Tên tác giả","Chú giải","Lề giấy","Mặt sau"},1),
    new Question("Lịch sử và Địa lí","Ai Cập cổ đại hình thành bên sông nào?",new String[]{"Sông Nin","Sông Hằng","Sông Mê Công","Sông Đà"},0),
    new Question("Lịch sử và Địa lí","Một thế kỉ có bao nhiêu năm?",new String[]{"10","50","100","1000"},2),
    new Question("Lịch sử và Địa lí","Bắc, Nam, Đông, Tây là các gì?",new String[]{"Mùa","Hướng chính","Đại dương","Lục địa"},1),
    new Question("Lịch sử và Địa lí","Đường Xích đạo chia Trái Đất thành mấy bán cầu?",new String[]{"Một","Hai","Ba","Bốn"},1),
    new Question("Lịch sử và Địa lí","Đâu là một đại dương?",new String[]{"Châu Á","Thái Bình Dương","Châu Âu","Châu Phi"},1)
 };
 private static final Random RANDOM = new Random();
 public static Question next(Question previous){
  if(ALL.length==1)return ALL[0];
  Question q; do {q=ALL[RANDOM.nextInt(ALL.length)];} while(q==previous);
  return q;
 }
}