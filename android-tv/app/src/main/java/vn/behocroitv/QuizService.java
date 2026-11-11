package vn.behocroitv;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.*;
import android.provider.Settings;
import android.text.InputType;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;

public class QuizService extends Service {
    private static final long MAX_WATCH_MS = 60L * 60L * 1000L;
    private static final String PARENT_PIN = "2001";
    private static final int NAVY = Color.rgb(9, 24, 49);
    private static final int CARD = Color.rgb(20, 48, 87);
    private static final int NORMAL = Color.rgb(35, 77, 125);
    private static final int GREEN = Color.rgb(15, 152, 91);
    private final Handler handler = new Handler(Looper.getMainLooper());
    private WindowManager manager;
    private View overlay;
    private QuizBank.Question current;
    private boolean started = false;
    private boolean quizActive = false;
    private boolean breakLocked = false;
    private int remainingCorrect = 3;
    private long watchedMs;
    private long nextQuizMs;
    private long lastTick;
    private long lastSaved;

    private int dp(float n) {
        return (int) (n * getResources().getDisplayMetrics().density + 0.5f);
    }
    private GradientDrawable rounded(int color, int radius, boolean selected) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        if (selected) d.setStroke(dp(3), Color.rgb(180, 255, 208));
        return d;
    }

    private long interval() {
        return getSharedPreferences("settings", MODE_PRIVATE)
                .getInt("minutes", 10) * 60000L;
    }

    private void persist() {
        getSharedPreferences("settings", MODE_PRIVATE).edit()
            .putLong("watch_elapsed_ms", watchedMs)
            .putLong("next_quiz_ms", nextQuizMs)
            .apply();
        lastSaved = SystemClock.elapsedRealtime();
    }

    private final Runnable timer = new Runnable() {
        @Override public void run() {
            if (!started) return;
            long now = SystemClock.elapsedRealtime();
            long delta = now - lastTick;
            lastTick = now;
            // Do not charge time spent answering, entering PIN or while the TV sleeps.
            if (!breakLocked && !quizActive && overlay == null && delta > 0 && delta <= 3000) {
                watchedMs += delta;
                nextQuizMs -= delta;
            }
            if (watchedMs >= MAX_WATCH_MS) {
                watchedMs = MAX_WATCH_MS;
                breakLocked = true;
                quizActive = false;
                if (overlay != null) closeOverlay();
                showBreak();
                persist();
            } else if (!breakLocked && !quizActive && nextQuizMs <= 0) {
                startQuizRound();
            }
            if (now - lastSaved >= 5000) persist();
            handler.postDelayed(this, 1000);
        }
    };

    @Override public void onCreate() {
        super.onCreate();
        manager = (WindowManager) getSystemService(WINDOW_SERVICE);
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
                .setContentText("Đang giám sát thời gian xem")
                .setOngoing(true).build();
        startForeground(22, note);
        if (!started) {
            started = true;
            android.content.SharedPreferences pref = getSharedPreferences("settings", MODE_PRIVATE);
            watchedMs = pref.getLong("watch_elapsed_ms", 0);
            nextQuizMs = pref.getLong("next_quiz_ms", interval());
            breakLocked = watchedMs >= MAX_WATCH_MS;
            lastTick = SystemClock.elapsedRealtime();
            lastSaved = lastTick;
            handler.post(timer);
        }
        if (breakLocked) showBreak();
        else if (intent != null && "TEST".equals(intent.getAction()) && !quizActive)
            startQuizRound();
        return START_STICKY;
    }

    private TextView text(String s, int size, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextColor(Color.WHITE);
        t.setTextSize(size);
        t.setGravity(Gravity.CENTER);
        t.setPadding(dp(6), dp(8), dp(6), dp(8));
        t.setSingleLine(false);
        if (bold) t.setTypeface(null, Typeface.BOLD);
        return t;
    }

    private static class OverlayFrame extends FrameLayout {
        OverlayFrame(Context c) {super(c);}
        @Override public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ||
                    event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE) return true;
            return super.dispatchKeyEvent(event);
        }
    }

    private LinearLayout newCard(FrameLayout screen) {
        screen.setBackgroundColor(NAVY);
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.setVerticalScrollBarEnabled(false);
        scroll.setPadding(dp(8), dp(8), dp(8), dp(8));
        LinearLayout center = new LinearLayout(this);
        center.setOrientation(LinearLayout.VERTICAL);
        center.setGravity(Gravity.CENTER);
        scroll.addView(center, new ScrollView.LayoutParams(-1, -1));
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(22), dp(16), dp(22), dp(16));
        card.setBackground(rounded(CARD, 16, false));
        int width = getResources().getDisplayMetrics().widthPixels;
        center.addView(card, new LinearLayout.LayoutParams(
                Math.min(dp(820), (int)(width * 0.78f)), -2));
        screen.addView(scroll, new FrameLayout.LayoutParams(-1, -1));
        return card;
    }

    private void mount(FrameLayout frame, View focusTarget) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                -1, -1, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                PixelFormat.TRANSLUCENT);
        try {
            manager.addView(frame, params);
            overlay = frame;
            if (focusTarget != null) focusTarget.post(focusTarget::requestFocus);
        } catch (RuntimeException e) {
            overlay = null;
        }
    }

    private Button answer(int index, LinearLayout target) {
        final int selected = index;
        Button b = new Button(this);
        b.setText(((char)('A' + index)) + ". " + current.answers[index]);
        b.setTextSize(21);
        b.setTextColor(Color.WHITE);
        b.setTypeface(null, Typeface.BOLD);
        b.setAllCaps(false);
        b.setSingleLine(false);
        b.setMaxLines(5);
        b.setEllipsize(null);
        b.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        b.setPadding(dp(16), dp(9), dp(12), dp(9));
        b.setMinHeight(dp(62));
        b.setFocusable(true);
        b.setBackground(rounded(NORMAL, 10, false));
        b.setOnFocusChangeListener((view, focused) ->
            b.setBackground(rounded(focused ? GREEN : NORMAL, 10, focused)));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(-1, -2);
        lp.setMargins(0, dp(4), 0, dp(4));
        target.addView(b, lp);
        b.setOnClickListener(v -> {
            if (!quizActive || breakLocked) return;
            if (selected == current.correct) {
                remainingCorrect--;
                Toast.makeText(this, "Đúng rồi!", Toast.LENGTH_SHORT).show();
            } else {
                remainingCorrect++;
                Toast.makeText(this, "Chưa đúng! Thêm 1 câu cần trả lời đúng.", Toast.LENGTH_SHORT).show();
            }
            closeOverlay();
            if (remainingCorrect <= 0) {
                quizActive = false;
                nextQuizMs = interval();
                persist();
                Toast.makeText(this, "Hoàn thành! Tiếp tục xem nhé.", Toast.LENGTH_SHORT).show();
            } else {
                // Maintain the quiz round without counting viewing time between questions.
                handler.postDelayed(this::showQuizQuestion, 250);
            }
        });
        return b;
    }

    private void startQuizRound() {
        if (breakLocked || quizActive || overlay != null) return;
        remainingCorrect = 3;
        quizActive = true;
        showQuizQuestion();
    }

    private void showQuizQuestion() {
        if (!quizActive || breakLocked || overlay != null) return;
        current = QuizBank.next(current);
        FrameLayout frame = new OverlayFrame(this);
        LinearLayout card = newCard(frame);
        card.addView(text("BÉ HỌC RỒI XEM TV", 26, true));
        card.addView(text("Cần đúng thêm " + remainingCorrect + " câu", 18, false));
        card.addView(text(current.text, 26, true));
        LinearLayout options = new LinearLayout(this);
        options.setOrientation(LinearLayout.VERTICAL);
        Button first = null;
        for (int i=0; i<4; i++) {
            Button b = answer(i, options);
            if (first == null) first = b;
        }
        card.addView(options, new LinearLayout.LayoutParams(-1, -2));
        card.addView(text("Dùng ▲ ▼ và OK để trả lời", 15, false));
        mount(frame, first);
    }

    private Button pinKey(String label, Runnable action) {
        Button key = new Button(this);
        key.setText(label);
        key.setTextSize(24);
        key.setTextColor(Color.WHITE);
        key.setTypeface(null, Typeface.BOLD);
        key.setAllCaps(false);
        key.setFocusable(true);
        key.setBackground(rounded(NORMAL, 10, false));
        key.setOnFocusChangeListener((v, focused) ->
                key.setBackground(rounded(focused ? GREEN : NORMAL, 10, focused)));
        key.setOnClickListener(v -> action.run());
        return key;
    }

    private void showBreak() {
        if (overlay != null || !Settings.canDrawOverlays(this)) return;
        FrameLayout frame = new OverlayFrame(this);
        LinearLayout card = newCard(frame);
        card.addView(text("ĐẾN GIỜ NGHỈ NGƠI!", 28, true));
        card.addView(text("Bạn đã xem TV đủ 60 phút. Hãy nghỉ ngơi nhé!", 21, false));
        card.addView(text("Dùng remote chọn các số để nhập mã phụ huynh", 17, false));

        StringBuilder entered = new StringBuilder();
        TextView pinDisplay = text("○ ○ ○ ○", 28, true);
        card.addView(pinDisplay);
        TextView error = text("", 15, false);
        card.addView(error);

        Runnable refresh = () -> {
            StringBuilder dots = new StringBuilder();
            for (int i=0; i<4; i++) {
                if (i>0) dots.append(" ");
                dots.append(i<entered.length() ? "●" : "○");
            }
            pinDisplay.setText(dots.toString());
        };
        Runnable validate = () -> {
            if (PARENT_PIN.equals(entered.toString())) {
                watchedMs = 0;
                nextQuizMs = interval();
                breakLocked = false;
                quizActive = false;
                entered.setLength(0);
                persist();
                closeOverlay();
                lastTick = SystemClock.elapsedRealtime();
                Toast.makeText(this, "Đã mở khóa. Có thể tiếp tục xem.",
                        Toast.LENGTH_SHORT).show();
            } else {
                entered.setLength(0);
                refresh.run();
                error.setText("Mật khẩu không đúng. Hãy nhập lại.");
            }
        };
        Runnable delete = () -> {
            if (entered.length()>0) entered.deleteCharAt(entered.length()-1);
            error.setText("");
            refresh.run();
        };
        GridLayout keypad = new GridLayout(this);
        keypad.setColumnCount(3);
        keypad.setRowCount(4);
        keypad.setUseDefaultMargins(false);
        final Button[] first = new Button[1];
        String[] keys = {"1","2","3","4","5","6","7","8","9","Xóa","0","OK"};
        for (int i=0; i<keys.length; i++) {
            final String keyName=keys[i];
            Runnable click;
            if ("Xóa".equals(keyName)) click=delete;
            else if ("OK".equals(keyName)) click=validate;
            else click=() -> {
                if (entered.length()<4) entered.append(keyName);
                error.setText("");
                refresh.run();
                // Submit as soon as all four numbers have been entered.
                if (entered.length()==4) validate.run();
            };
            Button b=pinKey(keyName,click);
            if (first[0]==null) first[0]=b;
            GridLayout.LayoutParams lp=new GridLayout.LayoutParams(
                    GridLayout.spec(i/3), GridLayout.spec(i%3, 1f));
            lp.width=0;
            lp.height=dp(57);
            lp.setMargins(dp(4),dp(3),dp(4),dp(3));
            keypad.addView(b,lp);
        }
        card.addView(keypad,new LinearLayout.LayoutParams(-1,-2));
        card.addView(text("Di chuyển bằng ▲ ▼ ◀ ▶, bấm OK để chọn số", 14, false));

        // Physical number buttons on compatible remotes are supported too.
        frame.setOnKeyListener((v,keyCode,event) -> {
            if (event.getAction()!=KeyEvent.ACTION_DOWN) return false;
            if (keyCode==KeyEvent.KEYCODE_DEL) { delete.run(); return true; }
            if (keyCode>=KeyEvent.KEYCODE_0 && keyCode<=KeyEvent.KEYCODE_9) {
                int digit=keyCode-KeyEvent.KEYCODE_0;
                if (entered.length()<4) entered.append(digit);
                error.setText("");
                refresh.run();
                if (entered.length()==4) validate.run();
                return true;
            }
            return false;
        });
        mount(frame,first[0]);
    }

    private void closeOverlay() {
        if (overlay != null) {
            try { manager.removeView(overlay); } catch (RuntimeException ignored) {}
            overlay = null;
        }
    }

    @Override public void onDestroy() {
        started = false;
        handler.removeCallbacks(timer);
        persist();
        closeOverlay();
        super.onDestroy();
    }
    @Override public IBinder onBind(Intent intent) { return null; }
}
