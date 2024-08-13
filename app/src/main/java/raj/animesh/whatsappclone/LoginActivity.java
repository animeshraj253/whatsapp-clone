package raj.animesh.whatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;

import raj.animesh.whatsappclone.databinding.ActivityLoginBinding;
import raj.animesh.whatsappclone.viewmodel.MyViewModel;
import raj.animesh.whatsappclone.views.GroupsActivity;

public class LoginActivity extends AppCompatActivity {

    MyViewModel viewModel;
    ActivityLoginBinding activityLoginBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        activityLoginBinding.setVModel(viewModel);


        if(FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this, GroupsActivity.class));
            finishAffinity();
        }
        activityLoginBinding.loginAnonymousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });


    }
}