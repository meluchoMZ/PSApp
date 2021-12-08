/*
 * Agendaly, 2021
 * Authors:
 *  Laura Cabezas González
 *  Blanca María Fernández Martín
 *  Miguel Blanco Godón
 */
package es.udc.psi.agendaly.Auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import es.udc.psi.agendaly.R;

public class AuthTestActivity extends AppCompatActivity {
	private boolean activeSession = false;
	private TextView userTv, authMethodTv;
	private Button signOut;
	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authtest_activity);
		setTitle("Inicio");
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			userTv = findViewById(R.id.user_tv);
			userTv.setText(bundle.getString("user"));
			authMethodTv = findViewById(R.id.method_tv);
			authMethodTv.setText(bundle.getString("authMethod"));
			signOut = findViewById(R.id.test_so_button);
			// saving sign in data for automatic sign in
			SharedPreferences.Editor sp = getApplicationContext().getSharedPreferences(getString(R.string.shared_preferences_file), Context.MODE_PRIVATE).edit();
			if (bundle.getString("authMethod").equals(AuthenticationActivity.AUTH_TYPE_AGENDALY_ACCOUNT)) {
				sp.putString("user", bundle.getString("user"));
				sp.putString("passwd",Utils.encode(bundle.getString("password")));
			}
			sp.putString("authMethod", bundle.getString("authMethod"));
			boolean committed = sp.commit();
			activeSession = true;
			if (!committed) {
				Log.d("_TAG", "cannot save preferences");
			}
			signOut.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					FirebaseAuth.getInstance().signOut();
					SharedPreferences internalSP = getSharedPreferences(getString(R.string.shared_preferences_file), Context.MODE_PRIVATE);
					internalSP.edit().clear().apply();
					activeSession = false;
					onBackPressed();
				}
			});
		}
	}
	@Override
	public void onBackPressed() {
		if (activeSession) {
			finishAffinity();
		} else {
			super.onBackPressed();
		}
	}
}
