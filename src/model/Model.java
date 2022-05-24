package model;

public class Model 
{
	private boolean useForceblocks=false;	//use forceblocks
	private boolean useMHblocks=false;		//use multihit blocks flag
	private boolean useExCon = false;		//use external controller class

	public void setForceBlocks(boolean in) { 
		useForceblocks=in;	
		}
	public boolean getForceBlocks() {
		return useForceblocks;
		}
	public void setMHblocks(boolean in) {
		useMHblocks=in; 
		}
	public boolean getMHblocks() {
		return useMHblocks;
		}
	public void setUseExCon(boolean in){
		useExCon = in;
	}
	public boolean getUseExCon() {
		return useExCon;
	}
	
}
