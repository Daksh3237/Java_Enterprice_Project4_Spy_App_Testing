package ca.sheridancollege.daksh.database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import ca.sheridancollege.daksh.beans.Mission;
import ca.sheridancollege.daksh.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureTestDatabase
public class TestDatabaseAccess {
	@Autowired
	public DatabaseAccess database;
	
	@Test
    public void testGetMissionList() {
        List<Mission> missions = database.getMissionList();
        assertTrue(missions.size() > 0);
    }
	
	@Test
	public void testGetAgentList() {
		    String agent = "Austin Powers";
	        List<Mission> agents = database.getAgentList(agent);
	        assertTrue(agents.size() > 0);
	    }
	
	@Test
	public void testDeleteMissionById()
	{
		List<Mission> mission=database.getMissionList();
		int id=mission.get(0).getId(); 
		int orgSize=database.getMissionList().size(); 
		System.out.println("deleteMission no of rows orgsize="+orgSize);
		database.deleteMissionById(id);
		int newSize=database.getMissionList().size(); 
		System.out.println("deleteMission no of rows newsize="+newSize);
		
		assertEquals(orgSize-1,newSize); //2==2 yes test pass
	}
	
	@Test
    public void testEditMission() {

		List<Mission> miss=database.getMissionList();
		Mission mi1=miss.get(0); 
		int misId=mi1.getId();
		
        Mission mission = database.getMission(misId);

        mission.setAgent("UpdatedAgent");
        mission.setTitle("UpdatedTitle");
        mission.setGadget1("UpdatedGadget1");
        mission.setGadget2("UpdatedGadget2");

        int rows = database.editMission(mission);

        assertTrue(rows > 0);

        Mission updatedMission = database.getMission(misId);

        assertEquals("UpdatedAgent", updatedMission.getAgent());
        assertEquals("UpdatedTitle", updatedMission.getTitle());
        assertEquals("UpdatedGadget1", updatedMission.getGadget1());
        assertEquals("UpdatedGadget2", updatedMission.getGadget2());
    }
	
	 @Test
	 public void testGetMission() {
	    List<Mission> mission =database.getMissionList();
		Mission mi1=mission.get(0); 
		int id=mi1.getId();
	    Mission miss = database.getMission(id);
	    assertTrue(miss != null);
	}
	  
	@Test
	public void testGetNewMission() 
	{
		Mission mi1=new Mission();
		mi1.setAgent("agent");
		mi1.setTitle("hello");
		mi1.setGadget1("hammer");
		mi1.setGadget2("shield");
		
		int orgsize=database.getMissionList().size();
		System.out.println("MissionList no of rows orgsize="+orgsize);
		database.getNewMission(mi1);
		
		int newsize=database.getMissionList().size();
		System.out.println("MissionList no of rows newsize="+newsize);
		
		assertEquals(orgsize+1 ,newsize);

		
	}
	
	
	
	
}
