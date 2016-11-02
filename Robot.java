
package org.usfirst.frc.team5974.robot;


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



public class Robot extends IterativeRobot {
    //Defining Variables
    RobotDrive myDrive;
    Joystick controller;
    double joy1X;  //Joystick 1 X,Y
    double joy1Y;
    double joy2X;  //Joystick 2 X,Y
    double joy2Y;
    //Jed's Not Victor
    VictorSP upperLeftMotor = new VictorSP(0); //Upper left Motor
    VictorSP lowerRightMotor = new VictorSP(1); //Lower Right Motor
    VictorSP upperRightMotor = new VictorSP(3); //Upper Right Motor
    VictorSP lowerLeftMotor = new VictorSP(2); //Lower Left Motor   
    
    
    public void updateStuff() {
        joy1X = controller.getRawAxis(0); //left joystick X
    	joy1Y = controller.getRawAxis(1); //left joystick Y
    	joy2X = controller.getRawAxis(4); //right joystick
    	joy2Y = controller.getRawAxis(5);
    	deadZone(); //See below
    }
    public void deadZone() {
        double deadZoneDouble = 0.15;
    	if (joy1Y <= Math.abs(deadZoneDouble)) { //For joysticks
    		joy1Y = 0;
    	}
    	if (joy2Y <= Math.abs(deadZoneDouble)) { //For joysticks
    		joy2Y = 0;
    	}
    
    }
	
    public void robotInit() {
    	
    }

    
    public void autonomousInit() {
    	
    }

    
    public void autonomousPeriodic() {
    	
    }

    
    public void teleopPeriodic() {
        
    }
    
    
    public void testPeriodic() {
    	upperLeftMotor.set(joy1Y); //Control the left motors with the first joystick
	lowerLeftMotor.set(joy1Y);
	upperRightMotor.set(joy2Y); //Control the right motors with the second joystick
	lowerRightMotor.set(joy2Y);
    }
    
}
