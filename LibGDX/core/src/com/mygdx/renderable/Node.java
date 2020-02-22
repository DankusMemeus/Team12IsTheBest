package com.mygdx.renderable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.house.House;

import static java.lang.Float.*;

/**
 *
 */
public class Node extends Renderable {
	
	private House house;
	private ArrayList<NPC> residents;

	private Map<Vector2, String> notes = new HashMap<>();
	private Map<String, Boolean> noteSeen;
	private Boolean isDiseased;
	private String imageURL;

	private float x;
	private float y;
	
	private boolean level1Researched = false;
	private boolean level2Researched = false;
	private boolean level3Researched = false;
	private boolean level4Researched = false;

	public Node(House house, ArrayList<NPC> residents, Map<Vector2, String> notes, Map<String, Boolean> noteSeen, String imageURL, float x, float y) {
		super.setSprite(new Texture(Gdx.files.internal(imageURL)), x, y);
		this.x = x;
		this.y = y;
		this.house = house;
		this.imageURL = imageURL;
		isDiseased = false;
		this.residents = residents;
		System.out.println(residents.size());
		this.notes = notes;
		this.noteSeen = noteSeen;
		updateHouseDiseased();
	}

	public Node(String imageURL, float x, float y, String[] attributes) {
		super.setSprite(new Texture(Gdx.files.internal(imageURL)), x, y);
		this.x = x;
		this.y = y;

		this.imageURL = imageURL;
		isDiseased = false;
		residents = new ArrayList<>();
		noteSeen = new HashMap<>();
		setAllVillagers(attributes);
		this.house = new House(attributes);
		updateHouseDiseased();
	}

	
	
	public void setAllVillagers(String[] attributes) {
		for(String s : attributes) {
			if(s.contains(",") && !s.contains("(")) {
				String[] values = s.split(",");
				NPC n = new NPC(parseFloat(values[2]));
				n.updateSprite(parseFloat(values[0]),
						parseFloat(values[1]));
				residents.add(n);
			}
			else if (s.contains(",")) {
				String[] values = s.split(",");
				notes.put(new Vector2(parseFloat(values[1]), valueOf(values[2])), values[0].substring(1));
				noteSeen.put(values[0].substring(1), false);
			}
		}
		

	}
	
	public int generateNumOfNPCs(int highBound, int lowBound) {
		return lowBound;
	}
	
	
	public ArrayList<NPC> getNPCs() {
		return residents;
	}
	
	
	public void setNodes(ArrayList<NPC> villagers) {
		residents = villagers;
	}
	
	public int[][] getArray() {
		return house.getArray();
	}
	
	
	public Boolean isDiseased() {
		return isDiseased;
	}
	
	public void updateHouseDiseased() {
		this.isDiseased = false;
		for(NPC n : residents) {
			if (!n.getStatus().equals("Sick") && !n.getStatus().equals("Dead")) {
				continue;
			}
			this.isDiseased = true;
			break;
		}
	}
	
	public Boolean isAllInHouseDiseased() {
		for(NPC n : residents) {
			if(n.getStatus().equals("Alive")) {
				return false;
			}
		}
		return true;
	}
	
	
	public int getNumberOfInfected()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Dead")) x++;
		}
		return x;
	}
	
	public int getNumberOfSick()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick")) x++;
		}
		return x;
	}
	
	public int getNumberOfAlive()
	{
		int x = 0;
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Alive")) x++;
		}
		return x;
	}
	
	public Boolean everyoneBurnt()
	{
		for(NPC resident : residents)
		{
			if(!resident.getStatus().equals("Burnt")) return false;
		}
		return true;
	}
	
	public Boolean shouldGameEnd()
	{
		for(NPC resident : residents)
		{
			if(resident.getStatus().equals("Sick")) {
				return false;
			}
			else if(resident.getStatus().equals("Dead")) {
				return false;
			}
		}
		return true;
	}
	
	public int getNumberOfResidents() {
		return residents.size();
	}

	public House getHouse() {
		return house;
	}
	
	public ArrayList<NPC> getResidents() {
		return residents;
	}
	
	public Map<Vector2, String> getNotes() {
		return notes;
	}
	
	public Map<String, Boolean> getNoteValidation() {
		return noteSeen;
	}
	
	public void setNoteSeen(String Message) {
		noteSeen.put(Message, true);
	}
	
	public void serializeVillagers() {    
		FileHandle handle = Gdx.files.local("temp/villagers.temp");
		StringBuilder s = new StringBuilder();
		for(NPC npc : residents) {
			s.append(npc.getHealth()).append(",").append(npc.getSprite().getX()).append(",").append(npc.getSprite().getY()).append("\n");
		}
		handle.writeString(s.toString(), false);
	}
	
	public void resetVillagers() {
		FileHandle handle = Gdx.files.local("temp/villagers.temp");
        String[] lines = handle.readString().split("\\r?\\n");
        residents.clear();
        for(int i = 0; i < lines.length; i++) {
			String[] values = lines[i].split(",");
			NPC n = new NPC(valueOf(values[0]));
			n.updateSprite(valueOf(values[1]), valueOf(values[2]));
			residents.add(n);
		}
	}


	public void infectRandom(float probabilty)
	{
		Random rand = new Random();
		for(NPC resident : residents) {
			if(rand.nextFloat()<probabilty)
			{
				resident.infect();
			}
		}
		
	}
	
	public void upgradeLevelKnown() {
		if(!level4Researched && level3Researched && level2Researched && level1Researched) {
			level4Researched = true;
		}
		else if(!level3Researched && level2Researched && level1Researched) {
			level3Researched = true;
		}
		else if(!level2Researched && level1Researched) {
			level2Researched = true;
		}
		else if(!level1Researched) {
			level1Researched = true;
		}
	}
	
	public Boolean reachedMaxLevel() {
		return level3Researched;
	}
	
	public Boolean getLevel1() {
		return level1Researched;
	}
	
	public Boolean getLevel2() {
		return level2Researched;
	}
	
	public Boolean getLevel3() {
		return level3Researched;
	}
	
	public Boolean getLevel4() {
		return level3Researched;
	}
	
	public Boolean setLevel1(Boolean value) {
		return level1Researched = value;
	}
	
	public Boolean setLevel2(Boolean value) {
		return level2Researched = value;
	}
	
	public Boolean setLevel3(Boolean value) {
		return level3Researched = value;
	}
	
	public Boolean setLevel4(Boolean value) {
		return level4Researched = value;
	}
	
	public boolean areAllDead() {
		for(NPC npc : residents) {
			if(!npc.getStatus().equals("Burnt")) {
				return false;
			}
		}
		return true;
	}
	
	public boolean areAllAlive() {
		for(NPC npc : residents) {
			if(!npc.getStatus().equals("Alive")) {
				return false;
			}
		}
		return true;
	}

	public String getImageURL() {
		return imageURL;
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}
}