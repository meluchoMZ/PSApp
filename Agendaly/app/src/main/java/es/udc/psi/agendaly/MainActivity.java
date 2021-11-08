/*
 * Agendaly, 2021
 * Authors:
 *  Laura Cabezas González
 *  Blanca María Fernández Martín
 *  Miguel Blanco Godón
 */
package es.udc.psi.agendaly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import es.udc.psi.agendaly.TimeTable.Horario;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{


	/*
	To avoid interference between use cases, this activity should be used as
	launcher until we have decided the organization of the app.
	Each change in the repository should be managed as follow:
		- If it is a new feature, it should be developed on a branch called "feature/<feature_name>"
		- If a bug is detected, a new issue should be added to github. The bug should be
		  developed in a branch called "bug/<issue_id>"
	Commits should be pushed to it's branch. Then, changes will be merged. After this, those
	branches should be removed.
	Commits should have the form: "[<bug/feature>#<id/name>] <commit message>"
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button M = findViewById(R.id.feature_launcherM);
		M.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent horario = new Intent(getApplicationContext(), AuthenticationActivity.class);
				startActivity(horario);

			}
		});
		Button b = findViewById(R.id.feature_launcherB);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent auth = new Intent(getApplicationContext(), CalendarActivity.class);
				startActivity(auth);
			}});
		Button l = findViewById(R.id.feature_launcherL);
		l.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent horario = new Intent(getApplicationContext(), Horario.class);
				startActivity(horario);

			}
		});
	}

}

