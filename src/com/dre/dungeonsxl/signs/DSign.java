package com.dre.dungeonsxl.signs;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;

import com.dre.dungeonsxl.P;
import com.dre.dungeonsxl.game.GameWorld;

public abstract class DSign {
	protected static P p = P.p;
	
	protected Sign sign;
	protected GameWorld gworld;
	
	//Distance Trigger
	private boolean isDistanceTrigger = false;
	private int dtDistance = 5;
	
	//Redstone Trigger
	private boolean isRedstoneTrigger = false;
	private Block rtBlock;
	
	//Sign Trigger
	private boolean isSignTrigger = false;
	private int stId;
	
	public abstract boolean check();
	
	public abstract String getPermissions();
	
	public abstract boolean isOnDungeonInit();
	
	public DSign(Sign sign, GameWorld gworld){
		this.sign = sign;
		this.gworld = gworld;

		//Check TriggerSigns
		if(sign.getLine(0).equalsIgnoreCase("["+SIGNTrigger.name+"]")) {
			rtBlock = getSignRtBlock(sign);
		}
		
		//Check Trigger
		String[] funcSplit = sign.getLine(3).split(",");
		for(String funcSplitPart:funcSplit){
			String[] splitted = funcSplitPart.split(" ");
			if(splitted.length > 0){
				if(splitted[0].equalsIgnoreCase("R")){
					rtBlock = getSignRtBlock(sign);
					if(rtBlock != null){
						this.isRedstoneTrigger = true;
					}
				} else if(splitted[0].equalsIgnoreCase("D")){
					this.isDistanceTrigger = true;
					
					if(splitted.length > 1){
						dtDistance = p.parseInt(splitted[1]);
					}
				} else if(splitted[0].equalsIgnoreCase("T")){
					this.isSignTrigger = true;
					if(splitted.length > 1){
						stId = p.parseInt(splitted[1]);
					}
				}
			}
		}
	}
	
	
	
	public Block getSignRtBlock(Sign sign){
		Block redBlock = null;
		if(sign.getBlock().getType() == Material.WALL_SIGN){
			switch(sign.getData().getData()){
				case 5:
					redBlock = sign.getBlock().getRelative(BlockFace.WEST);
					break;
				case 4:
					redBlock = sign.getBlock().getRelative(BlockFace.EAST);
					break;
				case 3:
					redBlock = sign.getBlock().getRelative(BlockFace.NORTH);
					break;
				case 2:
					redBlock = sign.getBlock().getRelative(BlockFace.SOUTH);
					break;
			}
		} else {
			redBlock = sign.getBlock().getRelative(BlockFace.DOWN);
		}
		return redBlock;
	}
	

	public void onInit(){
		
	}
	
	public void onTrigger(){
		
	}
	
	public static DSign create(Sign sign, GameWorld gworld){
		String[] lines = sign.getLines();
		DSign dSign = null;
		
		if(lines[0].equalsIgnoreCase("["+SIGNCheckpoint.name+"]")) {
			dSign = new SIGNCheckpoint(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNChest.name+"]")) {
			dSign = new SIGNChest(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNChunkUpdater.name+"]")) {
			dSign = new SIGNChunkUpdater(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNClasses.name+"]")) {
			dSign = new SIGNClasses(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNEnd.name+"]")) {
			dSign = new SIGNEnd(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNLeave.name+"]")) {
			dSign = new SIGNLeave(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNLobby.name+"]")) {
			dSign = new SIGNLobby(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNMob.name+"]")) {
			dSign = new SIGNMob(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNMsg.name+"]")) {
			dSign = new SIGNMsg(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNPlace.name+"]")) {
			dSign = new SIGNPlace(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNReady.name+"]")) {
			dSign = new SIGNReady(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNSoundMsg.name+"]")) {
			dSign = new SIGNSoundMsg(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNStart.name+"]")) {
			dSign = new SIGNStart(sign, gworld);
		} else if (lines[0].equalsIgnoreCase("["+SIGNTrigger.name+"]")) {
			dSign = new SIGNTrigger(sign, gworld);
		}
		
		if (dSign != null && gworld != null) {
			if (dSign.isOnDungeonInit()) {
				dSign.onInit();
			}
		}
		
		return dSign;
	}

	public void setDistanceOnly() {
		isRedstoneTrigger = false;
		isSignTrigger = false;
	}

	//Getter anb Setter
	public boolean isRedstoneTrigger() {
		return isRedstoneTrigger;
	}

	public boolean isDistanceTrigger() {
		return isDistanceTrigger;
	}
	
	public Block getRtBlock() {
		return rtBlock;
	}
	
	public int getDtDistance() {
		return dtDistance;
	}

	public boolean isSignTrigger() {
		return isSignTrigger;
	}

	public boolean isTrigger() {
		return false;
	}

	public int getStId() {
		return stId;
	}
	
	public Sign getSign() {
		return sign;
	}
	
}
