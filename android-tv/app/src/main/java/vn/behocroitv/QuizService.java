package vn.behocroitv;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.*;
import android.provider.Settings;
import android.view.*;
import android.widget.*;

public class QuizService extends Service {
    private static final int NAVY = Color.rgb(9, 24, 49);
    private static final int CARD = Color.rgb(20, 48, 87);
    private static final int NORMAL = Color.rgb(35, 77, 125);
    private static final int FOCUSED_GREEN = Color.rgb(15, 152, 91);
    private final Handler handler = new Handler(Looper.getMainLooper());
    private WindowManager manager;
    private View overlay;
    private long due;
    private boolean started = false;
    private QuizBank.Question current;

    private int dp(float size) {
        return (int) (size * getResources().getDisplayMetrics().density + 0.5f);
    }

    private GradientDrawable rounded(int color, int radius, boolean highlighted) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(radius));
        if (highlighted) shape.setStroke(dp(3), Color.rgb(188, 255, 211));
        return shape;
    }

    private final Runnable timer = new Runnable() {
        @Override public void run() {
            if (started && overlay == null && SystemClock.elapsedRealtime() >= due) {
                showQuiz();
            }
            if (started) handler.postDelayed(this, 1000);
        }
    };

    @Override public void onCreate() {
        super.onCreate();
        manager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    private long interval() {
        return getSharedPreferences("settings", MODE_PRIVATE)
                .getInt("minutes", 10) * 60000L;
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (!Settings.canDrawOverlays(this)) {
            stopSelf();
            return START_NOT_STICKY;
        }
        NotificationManager notifications =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notifications.createNotificationChannel(new NotificationChannel(
                "learning", "Bé học rồi xem TV", NotificationManager.IMPORTANCE_LOW));
        Notification note = new Notification.Builder(this, "learning")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Bé học rồi xem TV")
                .setContentText("Đang chờ câu hỏi")
                .setOngoing(true)
                .build();
        startForeground(22, note);
        if (!started) {
            started = true;
            due = SystemClock.elapsedRealtime() + interval();
            handler.post(timer);
        }
        if (intent != null && "TEST".equals(intent.getAction())) showQuiz();
        return START_STICKY;
    }

    private TextView text(String content, int size, boolean bold) {
        TextView t = new TextView(this);
        t.setText(content);
        t.setTextSize(size);
        t.setTextColor(Color.WHITE);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(8), dp(7), dp(8), dp(7));
        t.setSingleLine(false);
        t.setHorizontallyScrolling(false);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    private Button answerButton(int index, LinearLayout answers) {
        final int selected = index;
        Button button = new Button(this);
        button.setText(((char) ('A' + index)) + ". " + current.answers[index]);
        button.setTextSize(22);
        button.setTextColor(Color.WHITE);
        button.setTypeface(null, Typeface.BOLD);
        button.setAllCaps(false);
        button.setSingleLine(false);
        button.setMaxLines(4);
        button.setEllipsize(null);
        button.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        button.setPadding(dp(20), dp(10), dp(16), dp(10));
        button.setFocusable(true);
        button.setFocusableInTouchMode(true);
        button.setBackground(rounded(NORMAL, 12, false));
        button.setOnFocusChangeListener((v, hasFocus) -> {
            button.setBackground(rounded(hasFocus ? FOCUSED_GREEN : NORMAL, 12, hasFocus));
            button.setScaleX(hasFocus ? 1.015f : 1f);
            button.setScaleY(hasFocus ? 1.015f : 1f);
        });
        button.setOnClickListener(v -> {
            if (selected == current.correct) {
                Toast.makeText(this, "Chính xác! Tiếp tục xem nhé!", Toast.LENGTH_SHORT).show();
                close();
                due = SystemClock.elapsedRealtime() + interval();
            } else {
                Toast.makeText(this, "Chưa đúng, thử câu khác nhé!", Toast.LENGTH_SHORT).show();
                close();
                handler.postDelayed(this::showQuiz, 350);
            }
        });
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(-1, -2);
        params.setMargins(0, dp(5), 0, dp(5));
        button.setMinHeight(dp(70));
        answers.addView(button, params);
        return button;
    }

    private void showQuiz() {
        if (overlay != null || !Settings.canDrawOverlays(this)) return;
        current = QuizBank.next(current);

        FrameLayout screen = new FrameLayout(this) {
            @Override public boolean dispatchKeyEvent(KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) return true;
                return super.dispatchKeyEvent(event);
            }
        };
        screen.setBackgroundColor(NAVY);
        screen.setFocusableInTouchMode(true);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setClipToPadding(false);
        scroll.setVerticalScrollBarEnabled(false);
        scroll.setPadding(dp(8), dp(12), dp(8), dp(12));

        LinearLayout centered = new LinearLayout(this);
        centered.setOrientation(LinearLayout.VERTICAL);
        centered.setGravity(Gravity.CENTER);
        scroll.addView(centered, new ScrollView.LayoutParams(-1, -1));

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackground(rounded(CARD, 18, false));
        card.setPadding(dp(24), dp(18), dp(24), dp(18));

        int displayWidth = getResources().getDisplayMetrics().widthPixels;
        int safeWidth = Math.min(dp(820), (int)(displayWidth * 0.78f));
        centered.addView(card, new LinearLayout.LayoutParams(safeWidth, -2));

        card.addView(text("BÉ HỌC RỒI XEM TV", 26, true));
        TextView question = text(current.text, 27, true);
        LinearLayout.LayoutParams questionParams = new LinearLayout.LayoutParams(-1, -2);
        questionParams.setMargins(0, dp(10), 0, dp(12));
        card.addView(question, questionParams);

        LinearLayout answers = new LinearLayout(this);
        answers.setOrientation(LinearLayout.VERTICAL);
        Button first = null;
        for (int i = 0; i < 4; i++) {
            Button b = answerButton(i, answers);
            if (first == null) first = b;
        }
        card.addView(answers, new LinearLayout.LayoutParams(-1, -2));
        TextView hint = text("Di chuyển bằng phím ▲ ▼, chọn bằng OK", 16, false);
        card.addView(hint);

        screen.addView(scroll, new FrameLayout.LayoutParams(-1, -1));
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                -1, -1, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);

        try {
            manager.addView(screen, params);
            overlay = screen;
            final Button firstAnswer = first;
            if (firstAnswer != null) {
                firstAnswer.post(() -> {
                    firstAnswer.requestFocus();
                    firstAnswer.setBackground(rounded(FOCUSED_GREEN, 12, true));
                });
            }
        } catch (RuntimeException e) {
            overlay = null;
            Toast.makeText(this, "TV không cho phép hiển thị câu hỏi",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void close() {
        if (overlay != null) {
            try { manager.removeView(overlay); } catch (RuntimeException ignored) {}
            overlay = null;
        }
    }

    @Override public void onDestroy() {
        started = false;
        handler.removeCallbacks(timer);
        close();
        super.onDestroy();
    }

    @Override public IBinder onBind(Intent intent) {
        return null;
    }
}
