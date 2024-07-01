package ca.sheridancollege.daksh.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mission {
	
	private  int id;
	private String title;
	private String gadget1;
	private String gadget2;
	private String agent;
	private final String[] agents={"Johnny English","Natasha Romanova","Austin Powers"};
}
