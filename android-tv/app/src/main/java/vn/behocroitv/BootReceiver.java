package vn.behocroitv;
import android.content.*;
import android.os.Build;
import android.provider.Settings;
public class BootReceiver extends BroadcastReceiver {
 @Override public void onReceive(Context context, Intent intent) {
  if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
  if (!context.getSharedPreferences("settings",Context.MODE_PRIVATE).getBoolean("enabled",false)) return;
  if (!Settings.canDrawOverlays(context)) return;
  Intent service=new Intent(context,QuizService.class);
  try { if(Build.VERSION.SDK_INT>=26) context.startForegroundService(service); else context.startService(service); } catch(RuntimeException ignored) {}
 }
}
