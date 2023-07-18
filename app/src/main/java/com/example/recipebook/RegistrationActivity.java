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

public class RegistrationActivity extends AppCompatActivity {

	public static RegistrationActivity registrationActivity;

	private TextView sign_in;
	private Button sign_up;

	private EditText etName;
	private EditText etEmail;
	private EditText etPass;
	private EditText etRepass;

	private LoginDBHelper DB;
	private SQLiteDatabase usersDB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_activity);

		registrationActivity = this;

		sign_up = findViewById(R.id.sign_up_btn);

		sign_in = findViewById(R.id.sign_in_2);

		etName = findViewById(R.id.sign_up_name);
		etEmail = findViewById(R.id.sign_up_email);
		etPass = findViewById(R.id.sign_up_password);
		etRepass = findViewById(R.id.sign_up_repassword);

		DB = new LoginDBHelper(this);
		usersDB = DB.getReadableDatabase();

		initTextView();
		onBtnClickRegistration();
	}

	private void onBtnClickRegistration() {
		sign_up.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String name = etName.getText().toString();
				String password = etPass.getText().toString();
				String repassword = etRepass.getText().toString();
				String email = etEmail.getText().toString();

				final Toast toast = new Toast(getApplicationContext());
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.setDuration(Toast.LENGTH_LONG);
				LayoutInflater inflanter = getLayoutInflater();
				View toast_l = inflanter.inflate(R.layout.model_toast,  (ViewGroup) findViewById(R.id.toast_layout));
				toast.setView(toast_l);
				TextView textToast = toast_l.findViewById(R.id.toast_1);

				if(name.equals("") || password.equals("") || repassword.equals("")|| email.equals("")){
					textToast.setText("Заполните все поля");
					toast.show();
				} else {
					if(password.equals(repassword)){
						Boolean checkUser = DB.checkUsername(name);
						if(checkUser == false){
							Boolean insert = DB.insertData(name, password, email);
							if(insert == true){
								textToast.setText("Регистрация прошла успешно");
								toast.show();

								int user_id = DB.getUserId(name);

								Intent intent = new Intent(registrationActivity, HomeActivity.class);
								intent.putExtra("user_id", user_id);
								startActivity(intent);
								registrationActivity.finish();
							}
						}else {
							textToast.setText("Имя пользователя уже существует, придумайте новое");
							toast.show();
						}
					}else {
						textToast.setText("Пароли не совпадают");
						toast.show();
					}
				}
			}
		});
	}

	private void initTextView() {
		sign_in.setOnClickListener(new TextView.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(registrationActivity, LoginActivity.class);
				startActivity(intent);
				registrationActivity.finish();
			}
		});
	}
}
