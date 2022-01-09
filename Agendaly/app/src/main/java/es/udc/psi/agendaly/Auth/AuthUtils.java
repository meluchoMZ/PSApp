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
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Base64;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.udc.psi.agendaly.GlobalApplication;
import es.udc.psi.agendaly.R;

public class AuthUtils {
	private static final String SPFPATH = "usr_shared_preferences";
	private static final String emailPatternRegexp = "(\\w|.+)@(?:\\w|.+)\\.(?:\\w+)";
	private static final byte[] codingScheme =
			{
					(byte) 0x10, (byte) 0x3c, (byte) 0x4d, (byte) 0xe6, (byte) 0xc7,
					(byte) 0x86, (byte)	0x10, (byte) 0x25, (byte) 0xb4, (byte) 0x61,
					(byte) 0x47, (byte) 0xae, (byte) 0x09, (byte) 0x3f, (byte) 0x13,
					(byte) 0x17, (byte) 0xe9, (byte) 0xfd, (byte) 0xa4, (byte) 0xe7,
					(byte) 0x82, (byte) 0xb2, (byte) 0x77, (byte) 0x81, (byte) 0xe7
			};
	protected static boolean validateEmailAddress(String emailAddress) {
		Pattern p = Pattern.compile(emailPatternRegexp);
		Matcher m = p.matcher(emailAddress);
		return m.matches();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	protected static String encode(String source) throws NullPointerException {
		if (source == null) {
			throw new NullPointerException();
		}
		Base64.Encoder encoder = Base64.getEncoder();
		byte[] sourceBytes = source.getBytes();
		int length = Math.min(sourceBytes.length, codingScheme.length);
		byte[] outputBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			sourceBytes[i] ^= codingScheme[i];
		}
		return encoder.encodeToString(sourceBytes);
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	protected static String decode(String source) throws NullPointerException {
		if (source == null) {
			throw new NullPointerException();
		}
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] sourceBytes = decoder.decode(source);
		int length = Math.min(sourceBytes.length, codingScheme.length);
		byte[] outputBytes = new byte[length];
		for (int i = 0; i < length; i++) {
			sourceBytes[i] ^= codingScheme[i];
		}
		return new String(sourceBytes);
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public static void saveUser(User user) {
		SharedPreferences.Editor sp = AuthenticationActivity.getContext().getSharedPreferences(SPFPATH, Context.MODE_PRIVATE).edit();
		if (user.getProvider().equals(AuthenticationActivity.AUTH_TYPE_AGENDALY_ACCOUNT)) {
			sp.putString("user", user.getEmail());
			sp.putString("passwd", encode(user.getPassword()));
		}
		sp.putString("authMethod", user.getProvider());
		sp.apply();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public static User retrieveUser() {
		SharedPreferences sp = GlobalApplication.getContext().getSharedPreferences(SPFPATH, Context.MODE_PRIVATE);
		String spEmail = sp.getString("user", null);
		String spPasswd = sp.getString("passwd", null);
		String spMethod = sp.getString("authMethod", null);
		return new User(spEmail, spPasswd == null ? null : decode(spPasswd), spMethod);
	}

	public static void removeUser() {
		SharedPreferences sp = AuthenticationActivity.getContext().getSharedPreferences(SPFPATH, Context.MODE_PRIVATE);
		sp.edit().clear().apply();
	}

	public static void saveUserTokenInFirestore(String mail) {
		if (mail == null) return;
		FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
			if (task.isSuccessful() && task.getResult() != null) {
				// do whatever with token
				FirebaseFirestore db = FirebaseFirestore.getInstance();
				HashMap<String, String> map = new HashMap<>();
				map.put("token", task.getResult());
				db.collection("userTokens").document(mail)
						.set(map);
			}
		});
	}
}
