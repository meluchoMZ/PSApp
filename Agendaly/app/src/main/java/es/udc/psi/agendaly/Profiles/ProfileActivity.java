package es.udc.psi.agendaly.Profiles;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import es.udc.psi.agendaly.Auth.AuthUtils;
import es.udc.psi.agendaly.Auth.AuthenticationActivity;
import es.udc.psi.agendaly.Auth.User;
import es.udc.psi.agendaly.BaseActivity;
import es.udc.psi.agendaly.R;

public class ProfileActivity extends BaseActivity {
	@BindView(R.id.profile_sign_out_button)
	public Button signOutButton;

	@BindView(R.id.user_mail)
	public TextView tv;

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_activity);
		User user = AuthUtils.retrieveUser();
		tv.setText(user.getEmail());
		signOutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// do sign out
				AuthUtils.removeUser();
				// return to sign in page
				Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
				startActivity(intent);
			}
		});
	}
}
