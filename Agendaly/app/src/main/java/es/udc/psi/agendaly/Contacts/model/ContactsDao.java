package es.udc.psi.agendaly.Contacts.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactsDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(Contact... contact);
	@Delete
	public void delete(Contact... contact);
	@Query("select * from contacts")
	public List<Contact> getAll();
	@Query("select token from contacts where email = (:email)")
	public String getTokenFromUser(String email);
}
