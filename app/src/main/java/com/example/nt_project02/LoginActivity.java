package com.example.nt_project02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 초기화 Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.CheckButton).setOnClickListener(onClickListener);
        findViewById(R.id.gotoPasswordResetbutton).setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.CheckButton:
                    login();
                    break;
                case R.id.gotoPasswordResetbutton:
                    MystartActivity(PasswordResetActivity.class);
                    break;
            }


        }


    };

    private void login() {

        String email = ((EditText) findViewById(R.id.NameEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordEditText)).getText().toString();


        if (email.length() > 0 && password.length() > 0) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인 성공");
                                MystartActivity(MainActivity.class);

                            } else {

                                if (task.getException() != null) {
                                    startToast(task.getException().toString());
                                }
                            }

                            // ...
                        }
                    });

        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요.");
        }


    }




    private void startToast(String msg){

        Toast.makeText(LoginActivity.this, msg,
                Toast.LENGTH_SHORT).show();
    }
    private void MystartActivity(Class c){
        Intent intent=new Intent(this,c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}