package vn.behocroitv;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
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
    new Question("Lịch sử và Địa lí","Đâu là một đại dương?",new String[]{"Châu Á","Thái Bình Dương","Châu Âu","Châu Phi"},1),
    new Question("Toán","Số 1 có phải số nguyên tố không?",new String[]{"Có","Không","Khi là số lẻ","Khi là số chẵn"},1),
    new Question("Toán","18 + 27 bằng bao nhiêu?",new String[]{"35","43","45","47"},2),
    new Question("Toán","2 mũ 5 bằng bao nhiêu?",new String[]{"10","16","25","32"},3),
    new Question("Toán","Số nào chia hết cho 9?",new String[]{"124","153","172","205"},1),
    new Question("Toán","5 - 12 bằng bao nhiêu?",new String[]{"-17","-7","7","17"},1),
    new Question("Toán","Góc bẹt bằng bao nhiêu độ?",new String[]{"60","90","120","180"},3),
    new Question("Toán","Hình chữ nhật dài 8 cm, rộng 3 cm có diện tích?",new String[]{"11 cm²","22 cm²","24 cm²","48 cm²"},2),
    new Question("Toán","Phân số nào bằng 1/2?",new String[]{"2/3","2/4","3/4","1/3"},1),
    new Question("Ngữ văn","Từ nào sau đây là từ ghép?",new String[]{"Lung linh","Nho nhỏ","Cây bút","Lấp ló"},2),
    new Question("Ngữ văn","Câu nào là câu hỏi?",new String[]{"Em đang học bài.","Bạn tên là gì?","Trời đẹp quá!","Hãy mở sách."},1),
    new Question("Ngữ văn","Câu cảm thán thường kết thúc bằng dấu nào?",new String[]{"Dấu phẩy","Dấu hai chấm","Dấu hỏi","Dấu chấm than"},3),
    new Question("Ngữ văn","Nhân vật chính trong truyện Thánh Gióng là ai?",new String[]{"Sơn Tinh","Thánh Gióng","Thạch Sanh","Sọ Dừa"},1),
    new Question("Ngữ văn","Từ nào là từ láy?",new String[]{"Mặt trời","Lung linh","Xe máy","Con đường"},1),
    new Question("Ngữ văn","Từ nào là đại từ dùng để xưng hô?",new String[]{"Tôi","Đẹp","Xanh","Chạy"},0),
    new Question("Ngữ văn","Trong câu 'Gió hát bên hiên nhà', biện pháp tu từ là gì?",new String[]{"So sánh","Nhân hóa","Liệt kê","Nói quá"},1),
    new Question("Ngữ văn","Thơ lục bát có cặp câu lần lượt bao nhiêu tiếng?",new String[]{"5 và 7","6 và 8","7 và 7","8 và 8"},1),
    new Question("Ngữ văn","Từ nào chỉ đặc điểm?",new String[]{"Nhảy","Đẹp","Đi","Ăn"},1),
    new Question("Ngữ văn","Từ nào là danh từ chỉ nơi chốn?",new String[]{"Trường học","Nhanh","Học","Hiền"},0),
    new Question("Ngữ văn","Văn bản kể lại chuỗi sự việc thường thuộc kiểu gì?",new String[]{"Tự sự","Miêu tả","Biểu cảm","Nghị luận"},0),
    new Question("Ngữ văn","Câu nào có phép so sánh?",new String[]{"Em đi học.","Mặt trăng như chiếc đĩa bạc.","Trời mưa to.","Chim hót vang."},1),
    new Question("Ngữ văn","Từ nào viết đúng chính tả?",new String[]{"sắp sếp","sắp xếp","xắp xếp","xắp sếp"},1),
    new Question("Ngữ văn","Bài thơ thường chia thành các đơn vị nào?",new String[]{"Đoạn hội thoại","Khổ thơ","Câu lệnh","Phép tính"},1),
    new Question("Tiếng Anh","The cat is ___ the table. (ở dưới)",new String[]{"in","on","under","between"},2),
    new Question("Tiếng Anh","He ___ English every day.",new String[]{"study","studies","studying","studied"},1),
    new Question("Tiếng Anh","The opposite of 'hot' is ___",new String[]{"warm","cold","large","fast"},1),
    new Question("Tiếng Anh","___ you like music?",new String[]{"Does","Do","Is","Are"},1),
    new Question("Tiếng Anh","There is ___ orange on the table.",new String[]{"a","an","some","many"},1),
    new Question("Tiếng Anh","What is the plural of 'box'?",new String[]{"boxs","boxes","boxies","boxen"},1),
    new Question("Tiếng Anh","My brother is ___ than me.",new String[]{"tall","taller","tallest","more tall"},1),
    new Question("Tiếng Anh","We ___ watching TV now.",new String[]{"is","am","are","be"},2),
    new Question("Khoa học tự nhiên","Dụng cụ nào dùng để đo chiều dài?",new String[]{"Cân","Thước","Đồng hồ","Nhiệt kế"},1),
    new Question("Khoa học tự nhiên","Nước sôi ở nhiệt độ nào tại áp suất thường?",new String[]{"0 độ C","50 độ C","80 độ C","100 độ C"},3),
    new Question("Khoa học tự nhiên","Vật nào là chất rắn?",new String[]{"Không khí","Nước lọc","Viên đá","Hơi nước"},2),
    new Question("Khoa học tự nhiên","Bộ phận nào của cây thường quang hợp nhiều nhất?",new String[]{"Lá","Rễ","Hoa","Quả"},0),
    new Question("Khoa học tự nhiên","Đơn vị đo thời gian trong hệ SI là gì?",new String[]{"Mét","Giây","Kilôgam","Lít"},1),
    new Question("Khoa học tự nhiên","Con người cần khí nào để hô hấp?",new String[]{"Nitơ","Oxi","Hiđrô","Cacbon đioxit"},1),
    new Question("Khoa học tự nhiên","Lực ma sát có thể có tác dụng nào?",new String[]{"Chỉ làm vật nóng","Cản trở chuyển động","Làm vật biến mất","Tạo ra ánh sáng"},1),
    new Question("Khoa học tự nhiên","Sinh vật nào sau đây là thực vật?",new String[]{"Nấm rơm","Cây lúa","Giun đất","Vi khuẩn"},1),
    new Question("Khoa học tự nhiên","Chất nào tan tốt trong nước?",new String[]{"Cát","Đá","Muối ăn","Dầu ăn"},2),
    new Question("Khoa học tự nhiên","Đơn vị nào thường dùng đo thể tích chất lỏng?",new String[]{"Mét","Lít","Gam","Giây"},1),
    new Question("Lịch sử và Địa lí","Mặt Trời mọc ở hướng nào?",new String[]{"Tây","Bắc","Đông","Nam"},2),
    new Question("Lịch sử và Địa lí","Trái Đất tự quay quanh trục theo hướng nào?",new String[]{"Tây sang Đông","Đông sang Tây","Bắc sang Nam","Nam sang Bắc"},0),
    new Question("Lịch sử và Địa lí","Châu lục nào có diện tích lớn nhất?",new String[]{"Châu Âu","Châu Á","Châu Phi","Châu Úc"},1),
    new Question("Lịch sử và Địa lí","Tỉ lệ bản đồ 1:100000. 1 cm trên bản đồ ứng với?",new String[]{"0,1 km","1 km","10 km","100 km"},1),
    new Question("Lịch sử và Địa lí","La bàn thường dùng để xác định điều gì?",new String[]{"Nhiệt độ","Độ cao","Phương hướng","Thời gian"},2),
    new Question("Lịch sử và Địa lí","Lịch âm dựa chủ yếu vào chu kì của thiên thể nào?",new String[]{"Mặt Trăng","Sao Hỏa","Sao Kim","Sao Mộc"},0),
    new Question("Lịch sử và Địa lí","Nền văn minh Ai Cập cổ đại gắn với dòng sông nào?",new String[]{"Sông Nin","Sông Hằng","Sông Mã","Sông Đà"},0),
    new Question("Lịch sử và Địa lí","Kim tự tháp Giza nằm ở quốc gia nào?",new String[]{"Hy Lạp","Ấn Độ","Ai Cập","Trung Quốc"},2),
    new Question("Lịch sử và Địa lí","Đâu là một châu lục?",new String[]{"Thái Bình Dương","Châu Phi","Sông Nin","Biển Đông"},1),
    new Question("Lịch sử và Địa lí","Màu xanh dương trên bản đồ thường chỉ gì?",new String[]{"Núi cao","Vùng nước","Sa mạc","Đường biên giới"},1)
 };
 private static final Random RANDOM = new Random();
 private static final ArrayList<Integer> BAG = new ArrayList<>();
 public static synchronized Question next(Question previous) {
  if (BAG.isEmpty()) {
   for (int i=0;i<ALL.length;i++) BAG.add(i);
   Collections.shuffle(BAG,RANDOM);
  }
  // Avoid repeating the last question at the next shuffle-cycle boundary.
  if (BAG.size()>1 && previous!=null && ALL[BAG.get(BAG.size()-1)].text.equals(previous.text))
   Collections.swap(BAG,BAG.size()-1,BAG.size()-2);
  Question original=ALL[BAG.remove(BAG.size()-1)];
  String[] options=original.answers.clone();
  int correct=original.correct;
  for (int i=options.length-1;i>0;i--) {
   int j=RANDOM.nextInt(i+1);
   String temp=options[i];options[i]=options[j];options[j]=temp;
   if(correct==i)correct=j; else if(correct==j)correct=i;
  }
  return new Question(original.subject,original.text,options,correct);
 }
}