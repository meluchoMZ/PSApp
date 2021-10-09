/*
 * Agendaly, 2021
 * Authors:
 *  Laura Cabezas González
 *  Blanca María Fernández Martín
 *  Miguel Blanco Godón
 */
package es.udc.psi.agendaly;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	/*
	To avoid interference between use cases, this activity should be used as
	launcher until we have decided the organization of the app.
	Each change in the repository should be managed as follow:
		- If it is a new feature, it should be developed on a branch called "feature/<feature_name>"
		- If a bug is detected, a new issue should be added to github. The bug should be
		  developed in a branch called "issue/<issue_id>"
	Commits should be pushed to it's branch. Then, changes will be merged. After this, those
	branches should be removed.
	Commits should have the form: "[<bug/feature>#<id/name>] <commit message>"
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}