//Aluna: Barbara Goncalves
package ifpe;

import java.awt.Color;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class BabiBot extends AdvancedRobot{

	public void run() {
		//setando as cores do robo
		setBodyColor(Color.magenta); //corpo do robô
		setGunColor(Color.white); //cor do canhão
		setRadarColor(Color.black); //cor do radar
		setScanColor(Color.pink); //cor da luz do radar
		setBulletColor(Color.magenta); //cor do tiro do robô
		setAdjustRadarForGunTurn(false);
		
		while(true){
			turnRight(90);
		}
	}

	/**
	 * Evento que é disparado ao scannear um robô
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		
		// setTurnRight(e.getBearing());
	
			setTurnRight(normalRelativeAngleDegrees(e.getBearing()
					+ getHeading() - getGunHeading())); // retornando a posição
														// exata do inimigo
			setMaxVelocity(8.0);

			if (e.getDistance() > 100) {
				setAhead(e.getDistance() + 10);
			} else {
				setMaxVelocity(100.0);
				setBack(100);
			}
			// Decisões para atirar no inimigo
			if ((getEnergy() > 90 || getEnergy() > e.getEnergy())
					&& (e.getDistance() < 200)) {
				fire(3);
			}
			if (getEnergy() < 90
					&& (e.getDistance() >= 200 && e.getDistance() <= 300)) {
				fire(1);
			}
			if (e.getDistance() > 300) {
				return;
			}
		
		execute();
		scan();
	}

	/**
	 * onHitRobot:  Set him as our new target
	 */
	public void onHitRobot(HitRobotEvent e){
		setBack(200);
		setTurnRight(180);
		setMaxVelocity(50.0);
	
		execute();
	}

	public void onHitByBullet(HitByBulletEvent e){
		setBack(300);
		setTurnLeft(180);		
		execute();
	}
	
	public void onHitWall(HitWallEvent e){
		setBack(200);
		setTurnGunLeftRadians(Math.PI /2);
		setTurnLeft(90);	
		execute();
	}
	
}
