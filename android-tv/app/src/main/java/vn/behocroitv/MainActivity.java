package vn.behocroitv;
import android.app.*;
import android.os.*;
import android.content.*;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
import android.graphics.Color;
public class MainActivity extends Activity {
  android.content.SharedPreferences prefs; LinearLayout panel;
  @Override public void onCreate(Bundle state){super.onCreate(state);prefs=getSharedPreferences("settings",MODE_PRIVATE);showPanel();}
  TextView label(String s){TextView t=new TextView(this);t.setText(s);t.setTextSize(23);t.setTextColor(Color.WHITE);t.setPadding(8,12,8,12);return t;}
  void button(String s,Runnable f){Button b=new Button(this);b.setText(s);b.setTextSize(21);b.setAllCaps(false);panel.addView(b,new LinearLayout.LayoutParams(-1,76));b.setOnClickListener(v->f.run());}
  void service(String action){Intent i=new Intent(this,QuizService.class);if(action!=null)i.setAction(action);try{startForegroundService(i);}catch(Exception e){Toast.makeText(this,"Không thể bật dịch vụ: "+e.getMessage(),Toast.LENGTH_LONG).show();}}
  void showPanel(){
   ScrollView scroll=new ScrollView(this);panel=new LinearLayout(this);panel.setOrientation(1);panel.setPadding(40,28,40,24);panel.setBackgroundColor(Color.rgb(15,32,64));scroll.addView(panel);setContentView(scroll);
   panel.addView(label("BÉ HỌC RỒI XEM TV - KIẾN THỨC LỚP 6"));
   panel.addView(label("Quyền hiển thị: "+(Settings.canDrawOverlays(this)?"Đã cấp":"Chưa cấp")));
   button("1. Cấp quyền hiển thị trên ứng dụng khác",()->{try{startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,android.net.Uri.parse("package:"+getPackageName())));}catch(Exception e){Toast.makeText(this,"Hãy mở quyền hiển thị đè trong Cài đặt TV",Toast.LENGTH_LONG).show();}});
   panel.addView(label("Thời gian giữa các câu hỏi:"));
   Spinner s=new Spinner(this);String[] choices={"5 phút","10 phút","15 phút","20 phút"};s.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,choices));int[] times={5,10,15,20};int current=prefs.getInt("minutes",10);for(int j=0;j<times.length;j++)if(times[j]==current)s.setSelection(j);panel.addView(s);
   s.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){public void onItemSelected(android.widget.AdapterView<?> p,View v,int pos,long id){prefs.edit().putInt("minutes",times[pos]).apply();}public void onNothingSelected(android.widget.AdapterView<?> p){}});
   button("2. Bắt đầu đếm thời gian",()->{if(!Settings.canDrawOverlays(this)){Toast.makeText(this,"Hãy cấp quyền trước",Toast.LENGTH_LONG).show();return;}prefs.edit().putBoolean("enabled",true).apply();service(null);showPanel();});
   button("3. Kiểm tra câu hỏi ngay",()->{if(!Settings.canDrawOverlays(this)){Toast.makeText(this,"Chưa cấp quyền",Toast.LENGTH_LONG).show();return;}prefs.edit().putBoolean("enabled",true).apply();service("TEST");});
   button("4. Dừng giám sát",()->{prefs.edit().putBoolean("enabled",false).apply();stopService(new Intent(this,QuizService.class));showPanel();});
   panel.addView(label("Trạng thái: "+(prefs.getBoolean("enabled",false)?"ĐANG BẬT":"ĐANG TẮT")));
   panel.addView(label("Lưu ý: bản thử nghiệm chỉ phủ câu hỏi lên màn hình; không tạm dừng âm thanh video. Sony có thể hạn chế ứng dụng chạy nền."));
  }
  @Override public void onResume(){super.onResume();if(panel!=null)showPanel();}
}