import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;
public class Person extends Visual{
	private Random brain = new Random();//brains are random and make people aimlessly do things
	private int directionIndex = 3;//direction which people go, start at 3 (northeast)
	private Timer turnTimer;
	private int enterTime;//time which users initially walk north to get towards the center screen
	private int personIndex;//keeps track of which image to select
	private ArrayList<Image>directionImages = new ArrayList<Image>();//holds four images of person from different angle
/**
 * creates 1 of 6 random people, initializes their direction images, and starts new thread to make them walk around
 * @param x
 * @param y
 */
	Person(int x, int y) {
		super(x, y);
		personIndex = brain.nextInt(6) * 4;//choose 1 of 6 sets of 4 person images
		for(int i = 0; i < 4; i++){
			directionImages.add(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/Person" + personIndex + ".png")));
			personIndex++;
		}
		enterTime = brain.nextInt(1000);//get a random number for time for which person will walk northward
		turnTimer = new Timer();//create new timer to walk or change direction every 50 milliseconds
		turnTimer.scheduleAtFixedRate(new TimerTask() {
			public void run(){
				if(enterTime > 0){// person hasnt reached their initial entering process, go only northeast or nothwest
					int x = brain.nextInt(9);
					if(x < 1)
						directionIndex = 3;
					else if (x < 2)
						directionIndex = 2;
					image = directionImages.get(directionIndex);
					enterTime--;
				}
				else if(brain.nextInt(20)==0){//once finished with entering process change direction 5% of the time
					directionIndex += brain.nextInt(2) ;
					directionIndex = directionIndex % 4;
					image = directionImages.get(directionIndex);
					try {Thread.sleep(100);}//stop moving for 100milliseconds if turning
					catch (InterruptedException e) {e.printStackTrace();}
				}
			if(directionIndex > 1) yPosition -= 1;//if facing northwest move northwest,if facing southwest move southwest, etc...
			else yPosition += 1;
			if (directionIndex % 3 == 0) xPosition += 2;
			else xPosition -= 2;
			
			}
		}, 0, 50);
	
	}
/**
 * humans should not be considered by deleter function in Mapdraw, so regardless of position return false
 * @return false always	
 */
	public boolean isOverlapped(Visual other){return false;}
/**
 * Humans are not manipulated by building rotate functions	
 */
	void rotate() {}

}
