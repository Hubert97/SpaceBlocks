package controller;

import java.io.InputStream;

import com.fazecast.jSerialComm.SerialPort;

import model.gameModel;

public class gamePadController extends AbsCon{

	
	
	private gameModel gModel;
	private SerialPort comPort;
	private InputStream in; 
	/**
	 * Opens CoM port, initiates coms with external mcu
	 * @param gModel gameModel - to send platform position
	 * @param windowWith
	 */
	public gamePadController(gameModel gModel, int windowWith) 
	{
		this.windowWidth=windowWith;
		this.gModel=gModel;
		comPort =SerialPort.getCommPort("COM9");
    	comPort.openPort();
    	
   	 	System.out.print(comPort.getPortDescription());
   	 	comPort.openPort();
   	 	comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
   	 	in = comPort.getInputStream();
	}


	/**
	 * Checks if buffer isnt full if it isn't it checks puts read val to model
	 */
	public void updatePlatformPosition()
	{
		try
		{
		if(in.available()>0)
		{

				String buffer="";
				char lastByte=0;
			
				while(in.available()>0 && (lastByte=(char)in.read())!='\n')
				{
					
					buffer=buffer+lastByte;
				}
				buffer=buffer.replaceAll("\\r|\\n", "");;
				gModel.setPlatformPosition((int)map(Integer.parseInt(buffer),0,910,windowWidth-190,0));
				while(in.available()>0)
				{
					lastByte=(char)in.read();
				}
				//System.out.println(buffer);
		}
		
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	/**
	 * Maps x valu from in min to in max on to out range output is proportional to input
	 * @param x
	 * @param in_min
	 * @param in_max
	 * @param out_min
	 * @param out_max
	 * @return
	 */
	private long map(long x, long in_min, long in_max, long out_min, long out_max)
	{
		  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
		}
	
	public void close()
	{
		try {
			in.close();
			comPort.closePort();
		}
		catch(Exception e) {e.printStackTrace();}
	}

}
