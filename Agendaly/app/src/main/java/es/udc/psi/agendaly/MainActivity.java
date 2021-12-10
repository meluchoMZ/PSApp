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


public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
	private TextView monthYearText;
	private RecyclerView calendarRecyclerView;
	private LocalDate selectedDate;

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
		initWidgets();
		selectedDate = LocalDate.now();
		setMonthView();

		Button b = findViewById(R.id.feature_launcherL);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent auth = new Intent(getApplicationContext(), AuthenticationActivity.class);
				startActivity(auth);
			}
		});

		Button l = findViewById(R.id.feature_launcherL);
		l.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent horario = new Intent(getApplicationContext(), Horario.class);
				startActivity(horario);

			}
		});
	}

	private void initWidgets()
	{
		calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
		monthYearText = findViewById(R.id.monthYearTV);
	}

	private void setMonthView()
	{
		monthYearText.setText(monthYearFromDate(selectedDate));
		ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

		CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
		RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
		calendarRecyclerView.setLayoutManager(layoutManager);
		calendarRecyclerView.setAdapter(calendarAdapter);
	}

	private ArrayList<String> daysInMonthArray(LocalDate date)
	{
		ArrayList<String> daysInMonthArray = new ArrayList<>();
		YearMonth yearMonth = YearMonth.from(date);

		int daysInMonth = yearMonth.lengthOfMonth();

		LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
		int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

		for(int i = 1; i <= 42; i++)
		{
			if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
			{
				daysInMonthArray.add("");
			}
			else
			{
				daysInMonthArray.add(String.valueOf(i - dayOfWeek));
			}
		}
		return  daysInMonthArray;
	}

	private String monthYearFromDate(LocalDate date)
	{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
		return date.format(formatter);
	}

	public void previousMonthAction(View view)
	{
		selectedDate = selectedDate.minusMonths(1);
		setMonthView();
	}

	public void nextMonthAction(View view)
	{
		selectedDate = selectedDate.plusMonths(1);
		setMonthView();
	}

	@Override
	public void onItemClick(int position, String dayText)
	{
		if(!dayText.equals(""))
		{
			String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}
	}
}

