package ca.sheridancollege.daksh.controller;

	import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
	import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
	import org.springframework.boot.test.context.SpringBootTest;
	import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import ca.sheridancollege.daksh.beans.Mission;
import ca.sheridancollege.daksh.database.DatabaseAccess;


	
	@SpringBootTest
	@AutoConfigureTestDatabase
	@AutoConfigureMockMvc
	public class TestHomeController 
	{
		@Autowired
		private DatabaseAccess da;
		@Autowired
		private MockMvc mockmvc;
		
		@Test
		public void testIndex() throws Exception
		{
		mockmvc.perform(get("/")).andExpect(status().isOk())
		.andExpect(view().name("index"))
		.andDo(print());
		}
		
		@Test
		public void testNewMission() throws Exception
		{
		LinkedMultiValueMap<String,String> requestparam=new LinkedMultiValueMap<>();
		requestparam.add("title", "hello");
		requestparam.add("gadget1", "hammer");
		requestparam.add("gadget2", "shield");
		requestparam.add("agent", "agent");
		int orgSize=da.getMissionList().size(); //retrieve no of rows from table
		mockmvc.perform(post("/newMission").params(requestparam))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/viewMissionByValue?agent=agent")).andDo(print());
		int newSize=da.getMissionList().size();
		assertEquals(orgSize+1,newSize);
		}
		
		@Test
		public void testUpdateMission() throws Exception
		{
		List<Mission> mission=da.getMissionList();
		Mission miss = mission.get(0); 
		int id=miss.getId();
		miss.setTitle("HELLO");
		mockmvc.perform(post("/updateMission").param("agent", "agent").flashAttr("mission", miss))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/viewMissionByValue?agent=agent")).andDo(print());
		
		assertEquals(miss.getTitle(),"HELLO"); 
		}
		
		@Test
		public void testDeleteMission() throws Exception
		{
		List<Mission> mission=da.getMissionList(); 
		Mission miss= mission.get(0); 
		int id=miss.getId();
		int orgSize=da.getMissionList().size(); 
		mockmvc.perform(get("/deleteMissionById/{id}",id))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/")).andDo(print());
		int newSize=da.getMissionList().size();
		assertEquals(orgSize-1,newSize);
		}
		
		
	}
