/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7144.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {

//Setting up auto, position sensitive
	private static final String DEFAULT_AUTO = "Default";
	private static final String CUSTOM_AUTO = "My Auto";
	private static final String C_AUTO = "Center Setup";
	private static final String R_AUTO = "Right Setup";
	private static final String L_AUTO = "Left Setup";

	//If you want  to edit these values you just have to change the numbers at the end and you wont have to actually find it in the code.
	//This number is the speed of the motor during the autonomous period
	private static final double AUTO_SPEED = 0.6;
	//This number is the time it is active during the autonomous period (In seconds)
	private static final double AUTO_TIME = 15.0;
	private static final double AUTO_DRIVETIME = 5.0;
	//This number multiplies the joystick input by this number.
	private static final double TELEOP_SPEED = 0.6;
	//These are the ports for your motors and the joystick port should be set to the joystick that you use to move
	private static final int MOTOR1_PORT = 0;
	private static final int MOTOR2_PORT = 1;
	private static final int JOYSTICK_PORT = 0;

	// Basic Setup
	private PWMSpeedController speedController1 = null;
	private PWMSpeedController speedController2 = null;
	private Timer mainTimer = null;
	private Joystick joystick = null;
	private Joystick joystick2;
	private DifferentialDrive drive = null;
	private String gameData;
	
	//Setup for motors
	private PWMSpeedController speedController3 = null;
	private PWMSpeedController speedController4 = null;
	private static final int MOTOR3_PORT = 2;
	private static final int MOTOR4_PORT = 3;	
	private static final int JOYSTICK2_PORT = 1;

	//maybe how the auto is selected?
	private String autoSelected;
	private SendableChooser<String> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
// Setup Position
		chooser.addDefault("Center Setup", C_AUTO);
		chooser.addObject("Right Setup", R_AUTO);
		chooser.addObject("Left Setup", L_AUTO);
//		chooser.addObject("My Auto", CUSTOM_AUTO);
		SmartDashboard.putData("Auto choices", chooser);

		speedController1 = new Spark(MOTOR1_PORT);
		speedController2 = new Spark(MOTOR2_PORT);
		mainTimer = new Timer();
		joystick = new Joystick(JOYSTICK_PORT);
		drive = new DifferentialDrive(speedController1, speedController2);
// Motors
		speedController3 = new Spark(MOTOR3_PORT);
		speedController4 = new Spark(MOTOR4_PORT);	
		joystick2 = new Joystick(JOYSTICK2_PORT);	
	
		//setup position buttons
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();

		gameData = DriverStation.getInstance().getGameSpecificMessage();
		mainTimer.reset();
	}

	/**
	 * This function is called periodically during autonomous. This is your running for loop
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		Scheduler.getInstance().run();
// ALL the different Auto options are listed below			
			case C_AUTO:
				if(mainTimer.get() < AUTO_DRIVETIME)
				{
					drive.arcadeDrive(AUTO_SPEED, 0.0);
				}
                drive.arcadeDrive(0.0, 0.0);
                break;
                
			case R_AUTO:
				if(gameData.charAt(0) == 'L')
				{
					if(mainTimer.get() < AUTO_DRIVETIME)
					{
						drive.arcadeDrive(AUTO_SPEED, 0.0);
					}
	                drive.arcadeDrive(0.0, 0.0);
				}
				if(gameData.charAt(0)=='R')
				{	
					if(mainTimer.get() < AUTO_DRIVETIME)
					{
						drive.arcadeDrive(AUTO_SPEED, 0.2);
					}
	                drive.arcadeDrive(0.0, 0.0);			
				}		
                break;				
                
			case L_AUTO:
				// Put custom auto code here
				if(gameData.charAt(0) == 'L')
				{
					if(mainTimer.get() < AUTO_DRIVETIME)
					{
						drive.arcadeDrive(AUTO_SPEED, 0.0);
					}
	                drive.arcadeDrive(0.0, 0.0);
				}
				if(gameData.charAt(0)=='R')
				{	
					if(mainTimer.get() < AUTO_DRIVETIME)
					{
						drive.arcadeDrive(AUTO_SPEED, 0.1);
					}
	                drive.arcadeDrive(0.0, 0.0);			
				}								
                break;

			case DEFAULT_AUTO:
			default:
				if(mainTimer.get() < AUTO_DRIVETIME)
				{
					drive.arcadeDrive(AUTO_SPEED, 0.0);
				}
                drive.arcadeDrive(0.0, 0.0);
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		drive.arcadeDrive(joystick.getRawAxis(1) * TELEOP_SPEED, joystick.getRawAxis(0) * TELEOP_SPEED );
	//motors
		speedController3.set(1.0*joystick2.getY());
		speedController4.set(-1.0*joystick2.getY());	
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
