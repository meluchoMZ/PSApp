/*
 * Agendaly, 2021
 * Authors:
 *  Laura Cabezas González
 *  Blanca María Fernández Martín
 *  Miguel Blanco Godón
 */
package es.udc.psi.agendaly.Auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import es.udc.psi.agendaly.R;
import es.udc.psi.agendaly.TimeTable.Horario;


public class AuthenticationActivity extends AppCompatActivity {
	public static Context context;
	protected static final String AUTH_TYPE_AGENDALY_ACCOUNT = "agendaly account";
	protected static final String AUTH_TYPE_GOOGLE = "google";
	private static final int GOOGLE_OK = 200;
	private EditText email, password;
	private Button signUpAgendaly, signInAgendaly, signGoogle;
	private ProgressDialog loginDialog;

	@RequiresApi(api = Build.VERSION_CODES.O)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this.getApplicationContext();
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage(getString(R.string.automatic_signin_dialog));
		loginDialog.setCancelable(false);
		loginDialog.setInverseBackgroundForced(false);
		User user = AuthUtils.retrieveUser();
		String spEmail = user.getEmail();
		String spPasswd = user.getPassword();
		String spMethod = user.getProvider();
		if (spEmail!=null && spPasswd!=null && spMethod!=null && spMethod.equals(AUTH_TYPE_AGENDALY_ACCOUNT)) {
			loginDialog.show();
			// tries automatic sign in using Agendaly account
			FirebaseAuth.getInstance().signInWithEmailAndPassword(spEmail, spPasswd)
					.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
						@Override
						public void onComplete(@NonNull Task<AuthResult> task) {
							if (!task.isSuccessful()) {
								loginDialog.hide();
								showAuthError(task.getException().getMessage());
							} else {
								Intent intent = new Intent(getApplicationContext(), Horario.class);
								intent.putExtra("user", spEmail);
								intent.putExtra("password", spPasswd);
								intent.putExtra("authMethod", spMethod);
								loginDialog.hide();
								startActivity(intent);
							}
						}
					});
		}
		if (spMethod!=null && spMethod.equals(AUTH_TYPE_GOOGLE)) {
			// tries automatic sign in using google account
			loginDialog.show();
			GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
					.requestIdToken("880869344178-22b2c9rs5kc7omms8gc2g5is6n8c2vhn.apps.googleusercontent.com").requestEmail().build();
			GoogleSignInClient client = GoogleSignIn.getClient(getApplicationContext(), gsio);
			startActivityForResult(client.getSignInIntent(), GOOGLE_OK);
		}
		setContentView(R.layout.authentication_activity);
		email = findViewById(R.id.email_tv);
		password = findViewById(R.id.passwd_tv);
		signUpAgendaly = findViewById(R.id.sign_up_button);
		signUpAgendaly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String emAddress = email.getText().toString();
				String passwd = password.getText().toString();
				if (emAddress.isEmpty()) {
					email.setError(getString(R.string.error_empty_email));
					return;
				}
				if (passwd.isEmpty()) {
					password.setError(getString(R.string.error_empty_password));
					return;
				}
				if (!AuthUtils.validateEmailAddress(emAddress)) {
					email.setError(getString(R.string.error_invalid_email));
					return;
				}
				loginDialog.show();
				FirebaseAuth.getInstance()
						.createUserWithEmailAndPassword(emAddress, passwd)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								loginDialog.hide();
								if (task.isSuccessful()) {
									Intent intent = new Intent(getApplicationContext(), Horario.class);
									AuthUtils.saveUser(new User(emAddress, passwd, AUTH_TYPE_AGENDALY_ACCOUNT));
									startActivity(intent);
								} else {
									showAuthError(task.getException().getMessage());
								}
							}
						});
			}
		});
		signInAgendaly = findViewById(R.id.sign_in_button);
		signInAgendaly.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String emAddress = email.getText().toString();
				String passwd = password.getText().toString();
				if (emAddress.isEmpty()) {
					email.setError(getString(R.string.error_empty_email));
					return;
				}
				if (passwd.isEmpty()) {
					password.setError(getString(R.string.error_empty_password));
					return;
				}
				if (!AuthUtils.validateEmailAddress(emAddress)) {
					email.setError(getString(R.string.error_invalid_email));
					return;
				}
				loginDialog.show();
				FirebaseAuth.getInstance()
						.signInWithEmailAndPassword(emAddress, passwd)
						.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
							@Override
							public void onComplete(@NonNull Task<AuthResult> task) {
								loginDialog.hide();
								if (task.isSuccessful()) {
									Intent intent = new Intent(getApplicationContext(), Horario.class);
									AuthUtils.saveUser(new User(emAddress, passwd, AUTH_TYPE_AGENDALY_ACCOUNT));
									startActivity(intent);
								} else {
									showAuthError(task.getException().getMessage());
								}
							}
						});
			}
		});
		signGoogle = findViewById(R.id.google_button);
		signGoogle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				GoogleSignInOptions gsio = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
						.requestIdToken("880869344178-22b2c9rs5kc7omms8gc2g5is6n8c2vhn.apps.googleusercontent.com").requestEmail().build();
				GoogleSignInClient client = GoogleSignIn.getClient(getApplicationContext(), gsio);
				startActivityForResult(client.getSignInIntent(), GOOGLE_OK);
			}
		});
	}

	private void showAuthError(String error) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.auth_error_dialog_title));
		builder.setMessage(error);
		builder.setPositiveButton(getString(R.string.auth_error_dialog_button), null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GOOGLE_OK) {
			Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
			try {
				GoogleSignInAccount account = task.getResult(ApiException.class);
				// firebase authentication
				if (account != null) {
					AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
					FirebaseAuth.getInstance().signInWithCredential(credential)
							.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
								@RequiresApi(api = Build.VERSION_CODES.O)
								@Override
								public void onComplete(@NonNull Task<AuthResult> task) {
									if (task.isSuccessful()) {
										Intent intent = new Intent(getApplicationContext(), Horario.class);
										AuthUtils.saveUser(new User(account.getEmail(), null, AUTH_TYPE_GOOGLE));
										loginDialog.hide();
										startActivity(intent);
									} else {
										showAuthError(task.getException().getMessage());
									}
								}
							});
				} else {
					showAuthError(task.getException().getMessage());
				}
			} catch (ApiException e) {
				showAuthError(e.getMessage());
			}
		}
	}

	@Override
	public void onBackPressed() {
		finishAffinity();
	}

	public static Context getContext() {
		return context;
	}
}
