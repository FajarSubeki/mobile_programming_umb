package demoapp.co.id;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

public class SocialLoginActivity extends AppCompatActivity {

    private TextView info;
    private ImageView profile;
    private LoginButton login;
    CallbackManager callBackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        info = findViewById(R.id.info);
        profile = findViewById(R.id.profile);
        login = findViewById(R.id.login);
        callBackManager = CallbackManager.Factory.create();
        login.registerCallback(callBackManager, new FacebookCallback<LoginResult>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(LoginResult loginResult) {
                info.setText("User ID : " + loginResult.getAccessToken().getUserId());
                String imageURL = "https://graph.facebook.com/"
                        +loginResult.getAccessToken().getUserId()+"/picture?return_ssl_resources=1";
                Picasso.get().load(imageURL).into(profile);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callBackManager.onActivityResult(requestCode, resultCode, data);
    }
}