package com.example.recipebook;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipebook.DBHelpers.LoginDBHelper;

public class LoginActivity extends AppCompatActivity {

	public static LoginActivity loginActivity;

	private TextView sign_up;
	private Button sign_in;
	private EditText etName, etPass;

	private LoginDBHelper DB;
	private SQLiteDatabase usersDB;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		loginActivity = this;

		sign_in = findViewById(R.id.sign_in_btn);

		sign_up = findViewById(R.id.sign_up_2);

		etName = findViewById(R.id.sign_in_name);
		etPass = findViewById(R.id.sign_in_password);

		DB = new LoginDBHelper(this);
		usersDB = DB.getReadableDatabase();

		initTextView();
		onBtnClickLogin();
	}

	private void onBtnClickLogin() {
		sign_in.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String name = etName.getText().toString();
				String password = etPass.getText().toString();

				final Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);

				LayoutInflater inflanter = getLayoutInflater();
				View toast_l = inflanter.inflate(R.layout.model_toast,  (ViewGroup) findViewById(R.id.toast_layout));
				toast.setView(toast_l);
				TextView textToast = toast_l.findViewById(R.id.toast_1);

				if(name.equals("") || password.equals("")) {
					textToast.setText("Заполните все поля");
					toast.show();
				}
				else {
					Boolean checkUserPass = DB.checkUsernameAndPassword(name, password);
					if(checkUserPass == true) {
						textToast.setText("С возвращением");
						toast.show();

						int user_id = DB.getUserId(name);

						Intent intent = new Intent(loginActivity, HomeActivity.class);
						intent.putExtra("user_id", user_id);
						startActivity(intent);
						loginActivity.finish();
					}
					else {
						textToast.setText("Неверные имя или пароль");
						toast.show();
					}
				}
			}
		});
	}

	private void initTextView() {
		sign_up.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(loginActivity, RegistrationActivity.class);
				startActivity(intent);
				loginActivity.finish();
			}
		});
	}
}
