package ksu.com.academy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etMessage;
    private Button btnSend;
    private ImageView ivTelegram;
    private ImageView ivInstagram;
    private ImageView ivHabr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prepare();
        setListeners();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                sendEmail(etMessage.getText().toString());
                break;
            case R.id.telegram:
                Utils.openLink(this, R.string.telegram_package, R.string.telegram_link);
                break;
            case R.id.instagram:
                Utils.openLink(this, R.string.instagram_package, R.string.instagram_link);
                break;
            case R.id.habr:
                Utils.openLink(this, R.string.habr_package, R.string.habr_link);
                break;
        }
    }

    private void prepare() {
        etMessage = findViewById(R.id.message_et);
        btnSend = findViewById(R.id.send_btn);
        ivTelegram = findViewById(R.id.telegram);
        ivInstagram = findViewById(R.id.instagram);
        ivHabr = findViewById(R.id.habr);

        addCopyrightText();
    }

    private void setListeners() {
        btnSend.setOnClickListener(this);
        ivTelegram.setOnClickListener(this);
        ivInstagram.setOnClickListener(this);
        ivHabr.setOnClickListener(this);
    }

    private void addCopyrightText() {
        TextView tvCopyright = new TextView(this);
        tvCopyright.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        tvCopyright.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.text_size_default));
        tvCopyright.setText(R.string.copyright);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_HORIZONTAL;
        tvCopyright.setLayoutParams(params);

        LinearLayout containerLayout = findViewById(R.id.container_ll);
        containerLayout.addView(tvCopyright);
    }

    private void sendEmail(String message) {
        MessageActivity.show(this, etMessage.getText().toString());
    }

}
