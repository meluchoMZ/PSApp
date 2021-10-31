/*
 * Agendaly, 2021
 * Authors:
 *  Laura Cabezas González
 *  Blanca María Fernández Martín
 *  Miguel Blanco Godón
 */
package es.udc.psi.agendaly;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
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
		/*
		byte[] sourceBytes = source.getBytes();
		// Password obfuscation
		int length = sourceBytes.length + 2*codingScheme.length;
		byte[] obyt = new byte[length];
		for (int i = 0; i < codingScheme.length; i++) {
			obyt[i]  = codingScheme[i];
		}
		for (int i = 0; i < sourceBytes.length; i++) {
			obyt[i] = sourceBytes[i];
		}
		for (int i = 0; i < codingScheme.length; i++) {
			obyt[i]  = codingScheme[i];
		}
		for (int i = 0; i < sourceBytes.length; i++);
		 */
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
		/*
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] sourceBytes = source.getBytes();
		int length = sourceBytes.length - 2* codingScheme.length;
		byte [] output = new byte[length];
		for (int i = 0; i < length; i++) {
			output[i] = sourceBytes[length+i];
		}
		 */
	}
}
