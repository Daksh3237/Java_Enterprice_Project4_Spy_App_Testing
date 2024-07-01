package ca.sheridancollege.daksh.controllers;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.daksh.beans.Mission;
import ca.sheridancollege.daksh.database.DatabaseAccess;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	
	@Autowired
	public DatabaseAccess database;
	
	@GetMapping("/")
	public String indexPage(Model model) {
		
		model.addAttribute("mission", new Mission());
		model.addAttribute("Missionlist", database.getMissionList());
		return "index";
	}
	
	@GetMapping("/createMission")
	public String createMission(Model model) 
	{
		model.addAttribute("mission", new Mission());
		return "create_mission";
	}
	
	@PostMapping("/newMission")
	public String newMission(@ModelAttribute Mission mission, @RequestParam String agent) 
	{
		
		int r1 = database.getNewMission(mission);
		System.out.println("Created Mission based on int r: " + r1);
		return "redirect:/viewMissionByValue?agent=" + agent; 
	}
	
	@GetMapping("/viewMissionByValue")
	public String viewMissionPage(Model model,@RequestParam("agent") String agent,HttpSession
			session) {
		List<Mission> agentMissions;
		model.addAttribute("agent", agent);
		
		if(session.isNew()) 
		{
			agentMissions = database.getAgentList(agent);
			
		}
		else
		{
			agentMissions = database.getAgentList(agent);
			session.getAttribute("agentMissions");
		}
		session.setAttribute("agentMissions", agentMissions);
		return "view_missions";
	}
	@GetMapping("/deleteMissionById/{id}")
	public String deleteMission(@PathVariable int id) {
		
		database.deleteMissionById(id);
		return "redirect:/"; 
	}
	
	@GetMapping("/editMissionById/{id}")
	public String editMission(Model model, @PathVariable int id)
	{
		Mission mission1 = database.getMission(id);
		
		if (mission1==null)
		{
			System.out.println("No result for mission id = "+id);
			return "redirect:/viewMissionByValue";
		}
		
		model.addAttribute("mission", mission1);
		return "edit_mission";
	}
	
	@PostMapping("/updateMission")
	public String updateMission(@ModelAttribute Mission mission, @RequestParam String agent) {
	     
	    mission.setAgent(agent); 
	    int r = database.editMission(mission);
	    System.out.println("Updated Mission based on int r: " + r);
	    return "redirect:/viewMissionByValue?agent=" + agent; 
	}

}
