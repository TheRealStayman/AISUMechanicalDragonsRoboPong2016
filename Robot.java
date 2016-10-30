
package org.usfirst.frc.team5974.robot;

<<<<<<< HEAD
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

=======
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer; //The Camera\
import edu.wpi.first.wpilibj.DigitalInput; //Digital inputs
import edu.wpi.first.wpilibj.Timer; // The Timer
import edu.wpi.first.wpilibj.IterativeRobot; //Guess
import edu.wpi.first.wpilibj.Joystick; //The Controller
import edu.wpi.first.wpilibj.VictorSP; //The Motor Controller
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; //The Dashboard
import edu.wpi.first.wpilibj.ADXL362; //The Accelerometer
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
>>>>>>> origin/master



public class Robot extends IterativeRobot {
    //Defining Variables
    Joystick controller;
    RobotDrive drive;
    double[] joy1;
    double[] joy2;
    joy1 = new double[2];
    joy2 = new double[2];
	
    public void robotInit() {
    	
    }

    
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {
    	
    }

    
    public void teleopPeriodic() {
        
    }
    
    
    public void testPeriodic() {
    	
    }
    public void updateStuff() {
        joy1[0] = controller.getRawAxis(0); //left joystick X
    	joy1[1] = controller.getRawAxis(1); //left joystick Y
 //   	leftTrigger = controller.getRawAxis(2); //ball picker-upper Motors
  //  	rightTrigger = controller.getRawAxis(3);
    	joy2[0] = controller.getRawAxis(4); //right joystick
    	joy2[1] = controller.getRawAxis(5);
//    	leftBumper = controller.getRawButton(5); //Speed Boost
  //  	xButton = controller.getRawButton(3);
    //	yButton = controller.getRawButton(4);
    	deadZone(); //See below
    }
    public void deadZone() {
        double deadZoneDouble = 0.15;
    	if (joy1[1] <= Math.abs(deadZoneDouble)) { //For joysticks
    		joy1[1] = 0;
    	}
    	if (joy1[1] <= Math.abs(deadZoneDouble)) { //For joysticks
    		joy1[1] = 0;
    	}
    
    }
}
