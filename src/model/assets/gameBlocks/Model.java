package model.assets.gameBlocks;

public class Model 
{
//Display display;
BlockType board[][];
int bSizeX,bSizeY;
double basePOS;
boolean forceB;	
boolean multipleHitB;	

Model()
{

}

void setBasePos(double pos)
{
	if(pos>0)	//check if pos valid
		if(bSizeX>pos)
				basePOS=pos;
}

void init(int x, int y) //initiates board and preapares it to launch 
{
	bSizeX=x;
	bSizeY=y;

}

void run()
{
	//clock and updating stuff
}


}
