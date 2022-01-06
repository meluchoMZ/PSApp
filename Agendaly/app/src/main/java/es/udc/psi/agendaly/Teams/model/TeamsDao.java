package es.udc.psi.agendaly.Teams.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface TeamsDao {
	@Insert(onConflict = OnConflictStrategy.ABORT)
	public void insert(Teams... teams);
	@Delete
	public void delete(Teams... teams);
	@Query("select * from teams")
	public List<Teams> getAll();
	@Query("select * from teams where uuid = (:uuid) order by tuid")
	public List<Teams> getTeamsCreatedByUser(String uuid);
}
