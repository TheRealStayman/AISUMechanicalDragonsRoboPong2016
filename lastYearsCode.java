
package org.usfirst.frc.team5974.robot;


import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.CameraServer; //The Camera
//import edu.wpi.first.wpilibj.vision.USBCamera;
import edu.wpi.first.wpilibj.DigitalInput; //Digital inputs
import edu.wpi.first.wpilibj.Timer; // The Timer
import edu.wpi.first.wpilibj.IterativeRobot; //Guess
import edu.wpi.first.wpilibj.Joystick; //The Controller
import edu.wpi.first.wpilibj.VictorSP; //The Motor Controller
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard; //The Dashboard
//import edu.wpi.first.wpilibj.interfaces.Gyro; //The Gyro
import edu.wpi.first.wpilibj.ADXL362; //The Accelerometer
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

public class Robot extends IterativeRobot {

	//Defining Variables
	RobotDrive myDrive;
	Joystick controller;
	CameraServer cameraServer;
	//USBCamera camera;
	int mode; //Steps for Autonomous Code
	double j1X_axis; //Left joystick X and Y axis
	double j1Y_axis;
	double leftTrigger; 
	double rightTrigger;
	double j2X_axis; //Right joystick X and Y axis
	double j2Y_axis;
	double newTime = 0; //Beginning and end of loop.
	double oldTime = 0;
	double dT; //Delta Time
	double velocity = 0;
	double pos = 0; //Position
	double accelAdjust = 0; //Adjusting the accelerometer
	boolean leftBumper;
	boolean yButton;
	boolean xButton;
	double robotDirection = 0;
	double robotSpeed = 0;
	int over9000 = 9001;
	Timer timer = new Timer(); //Defining variables
	ADXRS450_Gyro gyro = new ADXRS450_Gyro();
	ADXL362 accel = new ADXL362(Accelerometer.Range.k2G); //Accelerometer
	DigitalInput limitSwitch = new DigitalInput(0); //limit switch
	VictorSP upperLeftMotor = new VictorSP(0); //Upper left Motor
	VictorSP lowerRightMotor = new VictorSP(1); //Lower Right Motor
	VictorSP upperRightMotor = new VictorSP(3); //Upper Right Motor
	VictorSP lowerLeftMotor = new VictorSP(2); //Lower Left Motor
	VictorSP beltMotor1 = new VictorSP(4); //Ball picker-upper Motor Controller
	//VictorSP beltMotor2 = new VictorSP(5);
	
    public void updateStuff() { //Defining variables that need to be updated
    	j1X_axis = controller.getRawAxis(0); //left joystick
    	j1Y_axis = controller.getRawAxis(1);
    	leftTrigger = controller.getRawAxis(2); //ball picker-upper Motors
    	rightTrigger = controller.getRawAxis(3);
    	j2X_axis = controller.getRawAxis(4); //right joystick
    	j2Y_axis = controller.getRawAxis(5);
    	leftBumper = controller.getRawButton(5); //Speed Boost
    	xButton = controller.getRawButton(3);
    	yButton = controller.getRawButton(4);
    	cameraServer = CameraServer.getInstance(); //Camera
    	//camera = new USBCamera("cam0");
    	deadZone(); //See below
    	directionOfRobot();
		speedOfRobot();
		smartDashboardWidgets();
    }
    
    public void deadZone() { //The dead Zones for the Joysitcks and accelerometer
    	
    	/* Joysticks when not touched may not be exactly zero. This code *
    	 * tells the joysticks to be set to zero when they are not       *
    	 * touched. Accelerometers can get values from gravity and       *
    	 * rumbling. That is why we set dead zones for that as well      */
    	
    	if (j1Y_axis <= 0.15 && j1Y_axis >= -0.15) { //For joysticks
    		j1Y_axis = 0;
    	}
    	if (j2Y_axis <= 0.15 && j2Y_axis >= -0.15) {
    		j2Y_axis = 0;
    	}
    	//For Accelerometer
    	if (accel.getY() <= 0.010 && accel.getY() >= -0.010) {
    		accelAdjust = 0;
    	}
    	else {
    		accelAdjust = accel.getY();
    	}
    }
    
    public void ballStopper() { //Stops the motors from spinning when Limit Switch is pressed
    	
    	if (limitSwitch.get()) {
    		beltMotor1.set(0);
    		//beltMotor2.set(0);
    	}
    }
    
    public void directionOfRobot() { //Calculates where the Robot is facing
		
    	robotDirection = robotDirection + (gyro.getAngle());
    	if (robotDirection > 130) {
    		robotDirection = -129;
    	}
    	else if (robotDirection < -130) {
    		robotDirection = 129;
    	}
    }
    
    public void speedOfRobot() { //Calculates the speed of the robot
    	robotSpeed = (Math.abs(j1Y_axis) + Math.abs(j2Y_axis)) * 100;
    }
    
    public void yMovement() { //Calculates the distance the robot has gone
    	oldTime = newTime;
    	newTime = timer.get();
    	dT = newTime - oldTime;
    	velocity += dT * accelAdjust;
    	pos += dT * velocity; //1 ~= 9.8 Meters
    }
    
    public void smartDashboardWidgets() { //Gathers data and sends it to the Dashboard
    	boolean alwaysTrue = true;
    	
    	SmartDashboard.putBoolean("Limit Switch", limitSwitch.get());
        SmartDashboard.putNumber("Direction", robotDirection);
    	SmartDashboard.putNumber("Speed", velocity);
    	SmartDashboard.putNumber("Speed X Axis", accel.getX());
    	SmartDashboard.putNumber("Speed Y Axis", accelAdjust);
    	SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
    	SmartDashboard.putNumber("Movement", pos);
    }
    
    public void robotInit() { //When the robot turns on
    	controller = new Joystick(0);
    	updateStuff(); //Updates variables
    	newTime = 0; //sets timer to 0
    	gyro.calibrate();
    	
    	//Real time camera code
        //camera.setSize(640, 480);
        //camera.setFPS(15);
        //camera.updateSettings();
    	
        cameraServer.setQuality(25);
        cameraServer.startAutomaticCapture("cam0");
    }
/*  ___      _____ _                               
   / _ \    |_   _| |                              
  / /_\ \_   _| | | |__   ___  _ __ ___   __ _ ___ 
  |  _  | | | | | | '_ \ / _ \| '_ ` _ \ / _` / __|
  | | | | |_| | | | | | | (_) | | | | | | (_| \__ \
  \_| |_/\__,_\_/ |_| |_|\___/|_| |_| |_|\__,_|___/
*/
  
    
    public void autonomousInit() {
    	
    	timer.start();
    	gyro.reset();
    	pos = 0;
    	mode = 1;
    	
    	System.out.println("Let the ankle breaking commence!");
    	
    	controller.setRumble(Joystick.RumbleType.kRightRumble, 1); //Rumbles the controller
		controller.setRumble(Joystick.RumbleType.kLeftRumble, 1);
		Timer.delay(0.25);
		controller.setRumble(Joystick.RumbleType.kRightRumble, 0);
		controller.setRumble(Joystick.RumbleType.kLeftRumble, 0);
		Timer.delay(0.25);
		controller.setRumble(Joystick.RumbleType.kRightRumble, 1);
		controller.setRumble(Joystick.RumbleType.kLeftRumble, 1);
		Timer.delay(0.25);
		controller.setRumble(Joystick.RumbleType.kRightRumble, 0);
		controller.setRumble(Joystick.RumbleType.kLeftRumble, 0);
		
    }
    
    public void autonomousPeriodic() {

		yMovement(); //calling the functions
		updateStuff();
		
		
    /*	while (pos <= 0.818) { //Go forward until we reach this distance
    		upperLeftMotor.set(-0.5);
        	upperRightMotor.set(-0.5);
    	}
		upperLeftMotor.set(0.5); //Stop
    	upperRightMotor.set(0.5);
    	Timer.delay(0.01);
		upperLeftMotor.set(0);
    	upperRightMotor.set(0);
    	Timer.delay(1);
    	while (gyro.getAngle() <= 65) {
    		upperLeftMotor.set(-0.5); //Turn Right
    		upperRightMotor.set(0.5);
    	}
    	Timer.delay(0.1);
    	upperLeftMotor.set(0.375);
    	upperRightMotor.set(-0.375);
    	Timer.delay(0.01);
    	upperLeftMotor.set(0);
    	upperRightMotor.set(0);
    	*/
    	
    	if (mode == 0) {
    		upperLeftMotor.set(0);
    		upperRightMotor.set(0);
    		beltMotor1.set(0);
    	}
    	
    	if (mode == 1) {
    		//if (pos <= 0.734) { //Go forward until this distance is reached
    			upperLeftMotor.set(-0.52);
    			upperRightMotor.set(-0.5);
    			System.out.println(pos);
    			smartDashboardWidgets();
    			Timer.delay(5);
    			//yMovement();
    		//}
    		//else {
    			//Timer.delay(0.1);
        		upperLeftMotor.set(0.5); //Stop
        		upperRightMotor.set(0.5);
        		Timer.delay(0.01);
        		mode = 2;
    		//}
    	}
    	
    	if (mode == 2) {
    		upperLeftMotor.set(0);
    		upperRightMotor.set(0);
    		Timer.delay(0.1);
    		upperLeftMotor.set(0.5); //Go backwards a bit
    		upperRightMotor.set(0.5);
    		Timer.delay(0.5);
    		upperLeftMotor.set(0);
    		upperRightMotor.set(0);
    		Timer.delay(0.1);
    		pos = 0;
    		gyro.reset();
    		yMovement();
    		mode = 3;
    	}
    	
   		if (mode == 3) {
   			if (gyro.getAngle() <= 60) {
   				upperLeftMotor.set(-0.3); //Turn Right
   				upperRightMotor.set(0.3);
   				smartDashboardWidgets();
   			}
    	else { 
    		//Timer.delay(0.1);
    		upperLeftMotor.set(0.375);
    		upperRightMotor.set(-0.375);
    		Timer.delay(0.01);
    		upperLeftMotor.set(0);
    		upperRightMotor.set(0);
    		pos = 0;
    		yMovement();
    		gyro.reset();
    		mode = 4;
   			}
   		}
   		
    	if (mode == 4) { 
    		//if (pos <= 0.339) { //Go forward a little bit longer
    			upperLeftMotor.set(-0.5);
    			upperRightMotor.set(-0.5);
    			Timer.delay(3);
    			//yMovement();
    		//}
    		//else { 
    			//upperLeftMotor.set(0); //Stop
    			//upperRightMotor.set(0);
    			//Timer.delay(0.1);
    			//mode = 5;
        	//}
    	}
    	
    	if (mode == 5) {
    		beltMotor1.set(1); //Spit out the ball
    		//beltMotor2.set(1);
    		Timer.delay(5);
    		beltMotor1.set(0);
    		//beltMotor2.set(0);
    		timer.stop();
    		mode = 0;
    	}
    }
/*   _____    _                  
	|_   _|  | |                 
      | | ___| | ___  ___  _ __  
  	  | |/ _ \ |/ _ \/ _ \| '_ \ 
  	  | |  __/ |  __/ (_) | |_) |
  	  \_/\___|_|\___|\___/| .__/ 
                      	  | |    
                      	  |_|    
*/    
    public void teleopInit(){
    	System.out.println("Test Started!");
    	controller.setRumble(Joystick.RumbleType.kRightRumble, 1); //Rumbles the controller for 1 second
		controller.setRumble(Joystick.RumbleType.kLeftRumble, 1);
		Timer.delay(1);
		controller.setRumble(Joystick.RumbleType.kRightRumble, 0);
		controller.setRumble(Joystick.RumbleType.kLeftRumble, 0); 
		pos = 0;
		gyro.reset();
		timer.start();
    }
    public void teleopPeriodic(){
    	//Updating values
    	updateStuff();
    	smartDashboardWidgets();
    	System.out.println("Ready to drive!");
    	
    	//Controller buttons
    	
    	if(leftBumper == true) {
    		System.out.println("Left Bumper Pressed!"); //Go full speed when the leftBumper is pressed
    	}
    	else {
    		j1Y_axis = j1Y_axis/2; //Else, Go only half speed
    		j2Y_axis = j2Y_axis/2;
    		robotSpeed = robotSpeed/2;
    	}
    	if(xButton == true && yButton == true) {
    		System.out.println("Do a Barrel Roll");
    		upperLeftMotor.set(1);
    		lowerLeftMotor.set(1);
    		upperRightMotor.set(-1);
    		lowerRightMotor.set(-1);
    		Timer.delay(1);
    		upperLeftMotor.set(0);
    		lowerLeftMotor.set(0);
    		upperRightMotor.set(0);
    		lowerRightMotor.set(0);
    	}
    	
    	//Controlling the Robot with Tank Drive
    	
		upperLeftMotor.set(j1Y_axis); //Control the left motors with the first joystick
		lowerLeftMotor.set(j1Y_axis);
		upperRightMotor.set(j2Y_axis); //Control the right motors with the second joystick
		lowerRightMotor.set(j2Y_axis);
    	
		
		//Loading and Unloading Function
		if (leftTrigger > 0) { //When left trigger is pressed load a ball
				beltMotor1.set(leftTrigger);
				//beltMotor2.set(leftTrigger);
				ballStopper();
		}
		else if (rightTrigger > 0) { //When right trigger is pressed unload a ball
			beltMotor1.set(-1 * rightTrigger);
			//beltMotor2.set(-1 * rightTrigger);
		}
		else {
			beltMotor1.set(0);
			//beltMotor2.set(0);
		}
		
		SmartDashboard.putNumber("Loop", timer.get()); //Timer loop
		yMovement();
		SmartDashboard.putNumber("Y Position", pos); //Distance the robot has gone
    }
    
}
