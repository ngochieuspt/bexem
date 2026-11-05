package vn.behocroitv;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.view.ViewGroup;

public class MainActivity extends Activity {
    private SharedPreferences prefs;
    private LinearLayout panel;

    private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }

    private GradientDrawable bg(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        showPanel();
    }

    private TextView label(String value, int size, boolean heading) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(8), dp(7), dp(8), dp(7));
        if (heading) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    private Button action(String value, Runnable job) {
        Button b = new Button(this);
        b.setText(value);
        b.setTextSize(19);
        b.setAllCaps(false);
        b.setFocusable(true);
        b.setSingleLine(false);
        b.setTextColor(Color.WHITE);
        b.setBackground(bg(Color.rgb(37, 91, 154), 12));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, dp(62));
        lp.setMargins(0, dp(5), 0, dp(5));
        panel.addView(b, lp);
        b.setOnFocusChangeListener((v, hasFocus) ->
            b.setBackground(bg(hasFocus ? Color.rgb(30, 133, 215) : Color.rgb(37, 91, 154), 12)));
        b.setOnClickListener(v -> job.run());
        return b;
    }

    private void startServiceWithAction(String action) {
        Intent intent = new Intent(this, QuizService.class);
        if (action != null) intent.setAction(action);
        try {
            startForegroundService(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Không bật được ứng dụng nền", Toast.LENGTH_LONG).show();
        }
    }

    private void showPanel() {
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.rgb(10, 24, 47));

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.setVerticalScrollBarEnabled(false);

        LinearLayout center = new LinearLayout(this);
        center.setOrientation(LinearLayout.VERTICAL);
        center.setGravity(Gravity.CENTER);
        center.setPadding(dp(12), dp(20), dp(12), dp(20));
        scroll.addView(center, new ScrollView.LayoutParams(-1, -1));

        panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setGravity(Gravity.CENTER_HORIZONTAL);
        panel.setPadding(dp(22), dp(18), dp(22), dp(20));
        panel.setBackground(bg(Color.rgb(20, 48, 89), 16));

        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int width = Math.min(dp(760), (int)(screenWidth * 0.75f));
        center.addView(panel, new LinearLayout.LayoutParams(width, -2));

        panel.addView(label("BÉ HỌC RỒI XEM TV", 27, true));
        panel.addView(label("Quyền hiển thị: " +
                (Settings.canDrawOverlays(this) ? "Đã cấp" : "Chưa cấp"), 17, false));

        action("Cấp quyền hiển thị", () -> {
            try {
                startActivity(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        android.net.Uri.parse("package:" + getPackageName())));
            } catch (Exception e) {
                Toast.makeText(this, "Vào Cài đặt để cấp quyền hiển thị", Toast.LENGTH_LONG).show();
            }
        });

        panel.addView(label("Hỏi sau mỗi", 18, false));
        Spinner spinner = new Spinner(this);
        String[] choices = {"5 phút", "10 phút", "15 phút", "20 phút"};
        int[] times = {5, 10, 15, 20};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, choices);
        spinner.setAdapter(adapter);
        int current = prefs.getInt("minutes", 10);
        for (int i = 0; i < times.length; i++) if (times[i] == current) spinner.setSelection(i);
        LinearLayout.LayoutParams slp = new LinearLayout.LayoutParams(-1, dp(50));
        slp.setMargins(0, 0, 0, dp(6));
        panel.addView(spinner, slp);
        spinner.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            public void onItemSelected(android.widget.AdapterView<?> p, View v, int pos, long id) {
                prefs.edit().putInt("minutes", times[pos]).apply();
            }
            public void onNothingSelected(android.widget.AdapterView<?> p) {}
        });

        action("Bắt đầu", () -> {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Cần cấp quyền hiển thị", Toast.LENGTH_LONG).show();
                return;
            }
            prefs.edit().putBoolean("enabled", true).apply();
            startServiceWithAction(null);
            showPanel();
        });

        action("Thử câu hỏi", () -> {
            if (!Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Cần cấp quyền hiển thị", Toast.LENGTH_LONG).show();
                return;
            }
            prefs.edit().putBoolean("enabled", true).apply();
            startServiceWithAction("TEST");
        });

        action("Tắt", () -> {
            prefs.edit().putBoolean("enabled", false).apply();
            stopService(new Intent(this, QuizService.class));
            showPanel();
        });

        panel.addView(label("Trạng thái: " +
                (prefs.getBoolean("enabled", false) ? "Đã bật" : "Đã tắt"), 19, true));
        panel.addView(label("Trả lời đúng để tiếp tục xem TV.", 15, false));

        root.addView(scroll, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);
    }

    @Override public void onResume() {
        super.onResume();
        if (prefs != null && panel != null) showPanel();
    }
}
