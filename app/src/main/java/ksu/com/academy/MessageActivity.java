package ksu.com.academy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String EXTRA_MESSAGE = "extra_message";
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        message = getIntent().getStringExtra(EXTRA_MESSAGE);
        TextView tvMessage = findViewById(R.id.message_tv);
        tvMessage.setText(message);

        Button btnEmail = findViewById(R.id.email_btn);
        btnEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.email_btn) {
            Utils.sendEmail(this, message);
        }
    }

    public static void show(Activity activity, String message) {
        Intent intent = new Intent(activity, MessageActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        activity.startActivity(intent);
    }

}