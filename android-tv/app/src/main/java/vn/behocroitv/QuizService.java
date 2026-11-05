package vn.behocroitv;
import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.*;
import android.provider.Settings;
import android.view.*;
import android.widget.*;
public class QuizService extends Service {
 private final Handler handler=new Handler(Looper.getMainLooper());
 private WindowManager manager; private View overlay; private long due; private boolean started=false;
 private QuizBank.Question current; private final Runnable timer=new Runnable(){public void run(){if(started&&overlay==null&&SystemClock.elapsedRealtime()>=due)quiz();if(started)handler.postDelayed(this,1000);}};
 @Override public void onCreate(){super.onCreate();manager=(WindowManager)getSystemService(WINDOW_SERVICE);}
 long interval(){return getSharedPreferences("settings",MODE_PRIVATE).getInt("minutes",10)*60000L;}
 @Override public int onStartCommand(Intent i,int flags,int id){
  if(!Settings.canDrawOverlays(this)){stopSelf();return START_NOT_STICKY;}
  NotificationManager n=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);n.createNotificationChannel(new NotificationChannel("learning","Bé học rồi xem TV",NotificationManager.IMPORTANCE_LOW));
  Notification note=new Notification.Builder(this,"learning").setSmallIcon(android.R.drawable.ic_dialog_info).setContentTitle("Bé học rồi xem TV").setContentText("Đang chờ câu hỏi lớp 6").setOngoing(true).build();startForeground(22,note);
  if(!started){started=true;due=SystemClock.elapsedRealtime()+interval();handler.post(timer);}
  if(i!=null&&"TEST".equals(i.getAction()))quiz();
  return START_STICKY;
 }
 TextView text(String s,int size){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(Color.WHITE);v.setGravity(Gravity.CENTER);v.setPadding(8,12,8,12);return v;}
 void quiz(){
  if(overlay!=null||!Settings.canDrawOverlays(this))return;current=QuizBank.next(current);
  LinearLayout layout=new LinearLayout(this);layout.setOrientation(1);layout.setPadding(35,24,35,24);layout.setGravity(Gravity.CENTER);layout.setBackgroundColor(Color.rgb(15,31,67));
  layout.addView(text("BÉ HỌC RỒI XEM TV",31));
  layout.addView(text(current.subject+" - LỚP 6",20));
  layout.addView(text(current.text,29));
  LinearLayout row=new LinearLayout(this);row.setOrientation(1);
  for(int j=0;j<4;j++){final int answer=j;Button b=new Button(this);b.setText((char)('A'+j)+". "+current.answers[j]);b.setTextSize(22);b.setAllCaps(false);b.setFocusable(true);
   b.setOnClickListener(v->{if(answer==current.correct){Toast.makeText(this,"Chính xác! Tiếp tục xem nhé!",Toast.LENGTH_SHORT).show();close();due=SystemClock.elapsedRealtime()+interval();}else{Toast.makeText(this,"Chưa đúng, thử câu khác nhé!",Toast.LENGTH_SHORT).show();close();handler.postDelayed(()->quiz(),350);}});
   row.addView(b,new LinearLayout.LayoutParams(-1,76));}
  layout.addView(row);layout.addView(text("Dùng phím mũi tên và OK trên remote để trả lời.",18));
  FrameLayout screen=new FrameLayout(this){@Override public boolean dispatchKeyEvent(KeyEvent event){if(event.getKeyCode()==KeyEvent.KEYCODE_BACK||event.getKeyCode()==KeyEvent.KEYCODE_ESCAPE)return true;return super.dispatchKeyEvent(event);}};
  screen.setBackgroundColor(Color.rgb(5,12,28));FrameLayout.LayoutParams center=new FrameLayout.LayoutParams(-1,-2,Gravity.CENTER);center.leftMargin=100;center.rightMargin=100;screen.addView(layout,center);
  WindowManager.LayoutParams params=new WindowManager.LayoutParams(-1,-1,WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,PixelFormat.TRANSLUCENT);
  try{manager.addView(screen,params);overlay=screen;row.getChildAt(0).requestFocus();}catch(RuntimeException e){overlay=null;Toast.makeText(this,"TV không cho phép hiển thị câu hỏi",Toast.LENGTH_LONG).show();}
 }
 void close(){if(overlay!=null){try{manager.removeView(overlay);}catch(RuntimeException ignored){}overlay=null;}}
 @Override public void onDestroy(){started=false;handler.removeCallbacks(timer);close();super.onDestroy();}
 @Override public android.os.IBinder onBind(Intent intent){return null;}
}