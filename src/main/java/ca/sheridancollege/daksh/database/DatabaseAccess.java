package ca.sheridancollege.daksh.database;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.daksh.beans.Mission;

	@Repository
	public class DatabaseAccess {
		private NamedParameterJdbcTemplate jdbc;
		public DatabaseAccess(NamedParameterJdbcTemplate jdbc1) {
			this.jdbc=jdbc1;
		}
		public List<Mission> getMissionList()
		{
			MapSqlParameterSource namedParam=new MapSqlParameterSource();
			String str1="SELECT*FROM mission;";
			ArrayList<Mission> missionlist=(ArrayList<Mission>)jdbc.query(str1, namedParam,new BeanPropertyRowMapper<Mission>(Mission.class));
			return missionlist;
		}
		
		public List<Mission> getAgentList(String agent)
		{
			MapSqlParameterSource namedParam=new MapSqlParameterSource();
			String str2="SELECT*FROM mission Where agent=:agent;";
			namedParam.addValue("agent",agent);
			ArrayList<Mission> missionlist=(ArrayList<Mission>)jdbc.query(str2, namedParam,new BeanPropertyRowMapper<Mission>(Mission.class));
			return missionlist;
		}
		
		public void deleteMissionById(int id) {
			
			MapSqlParameterSource namedParam=new MapSqlParameterSource();
			String str3="DELETE from mission WHERE id=:id;";
			namedParam.addValue("id",id);
			int r= jdbc.update(str3, namedParam);
			if(r>0)
				System.out.println("deleted mission="+id+" from table");
		}
		public int editMission(Mission mission) {
		    MapSqlParameterSource namedParam = new MapSqlParameterSource();
		    String sql = "UPDATE mission SET agent=:agent, title=:title, gadget1=:gadget1, gadget2=:gadget2 WHERE id=:id;";

		    namedParam.addValue("agent", mission.getAgent());
		    namedParam.addValue("title", mission.getTitle());
		    namedParam.addValue("gadget1", mission.getGadget1());
		    namedParam.addValue("gadget2", mission.getGadget2());
		    namedParam.addValue("id", mission.getId());

		    int r = jdbc.update(sql, namedParam);
		    return r;
		}
		
		public Mission getMission(int id)
		{
			MapSqlParameterSource namedParam=new MapSqlParameterSource();
			String str4="select * from mission where id=:id;";
			
			namedParam.addValue("id", id);
			
			BeanPropertyRowMapper<Mission> mapper = new BeanPropertyRowMapper<>(Mission.class);
			
			Mission mission = null;
			
			try
			{
				mission = jdbc.queryForObject(str4, namedParam, mapper);
			}
			catch (EmptyResultDataAccessException e)
			{
				System.out.println("Mission not found for id="+id);
			}
			return mission;
		}
		
		public int getNewMission(Mission mission)
		{
			MapSqlParameterSource namedParam=new MapSqlParameterSource();
		    String str5 = "INSERT INTO mission (agent, title, gadget1, gadget2) VALUES (:agent, :title, :gadget1, :gadget2)";
		    namedParam.addValue("agent", mission.getAgent());
		    namedParam.addValue("title", mission.getTitle());
		    namedParam.addValue("gadget1", mission.getGadget1());
		    namedParam.addValue("gadget2", mission.getGadget2());

		    int r1 = jdbc.update(str5, namedParam);
		    return r1;
		    
		}
		
}
