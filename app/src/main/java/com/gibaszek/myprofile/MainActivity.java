package com.gibaszek.myprofile;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView mailView;
    private TextView passView;
    private Button saveProfileBtn;
    private TextView notificatonsView;
    private Boolean sw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mailView = findViewById(R.id.mail);
        passView = findViewById(R.id.pass);
        saveProfileBtn = findViewById(R.id.saveProfileBtn);
        notificatonsView = findViewById(R.id.notificationsView);

        saveProfileBtn.setOnClickListener(v -> {
            if (sw) passEdit(this);
             else {
                this.sw = true;
                mailEdit(this);
             }
        });


    }

    private void mailEdit(Context context) {
        EditText mailInput = new EditText(context);
        mailInput.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        mailInput.setHint("Nowy email");
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Zmień Email");
        builder.setMessage("Podaj nowy email:");
        builder.setView(mailInput);

        builder.setPositiveButton("Zapisz", (dialogInterface, i) -> {
            if (!mailInput.getText().toString().contains("@")) {
                this.error("Błąd: Nieprawidłowy format emaila.");
                return;
            }
            mailView.setText(mailInput.getText().toString());
            passEdit(context);
            this.success("Profil zaktualizowany! Nowy email: " + mailInput.getText());
        });
        builder.setNegativeButton("Anuluj", (dialogInterface, i) -> {
            this.info("Edycja profilu anulowana.");
            dialogInterface.cancel();
        });
        builder.show();
    }

    private void passEdit(Context context) {
        EditText passInput = new EditText(context);
        EditText passRepass = new EditText(context);
        passInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passRepass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(passInput);
        layout.addView(passRepass);

        passInput.setHint("Wpisz hasło");
        passRepass.setHint("Powtórz hasło");

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Zmień Hasło");
        builder.setMessage("Podaj nowe hasło:");
        builder.setView(layout);

        builder.setPositiveButton("Zapisz", (dialogInterface, j) -> {
            if (!passInput.getText().toString().equals(passRepass.getText().toString())) {
                this.error("Błąd: Hasła nie pasują do siebie.");
                return;
            }
            String filtredPass = "";
            for(int i = 0; i < passInput.getText().toString().length(); i++) {
                filtredPass+="*";
            }
            passView.setText(filtredPass);
            this.success("Hasło zaktualizowane!");
        });

        builder.setNegativeButton("Anuluj", (dialogInterface, j) -> {
            this.info("Edycja profilu anulowana");
            dialogInterface.cancel();
        });
        this.sw = false;
        builder.show();
    }

    private void error(String c) {
        this.notificatonsView.setTextColor(Color.parseColor("#F5383A"));
        this.notificatonsView.setText(c);
    }

    private void success(String c) {
        this.notificatonsView.setTextColor(Color.parseColor("#58FF4C"));
        this.notificatonsView.setText(c);
    }

    private void info(String c) {
        this.notificatonsView.setTextColor(Color.parseColor("#747575"));
        this.notificatonsView.setText(c);
    }
}