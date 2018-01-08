/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//Make sure you change this to your team number.
package org.usfirst.frc.team3655.robot;

//These are all of the frc imports you can find them online.
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PWMSpeedController;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
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
	private static final String DEFAULT_AUTO = "Default";
	private static final String CUSTOM_AUTO = "My Auto";
	
	//If you want  to edit these values you just have to change the numbers at the end and you wont have to actually find it in the code.
	//This number is the speed of the motor during the autonomous period
	private static final double AUTO_SPEED = 0.5;
	//This number is the time it is active during the autonomous period (In seconds)
	private static final double AUTO_TIME = 3.0;
	//This number multiplies the joystick input by this number.
	private static final double TELEOP_SPEED = 0.5;
	
	//These are the ports for your motors and the joystick port should be set to the joystick that you use to move
	private static final int MOTOR1_PORT = 0;
	private static final int MOTOR2_PORT = 1;
	private static final int JOYSTICK_PORT = 0;
	
	//Here we are setting all of the speed controllers and a variable for if auto is selected.
	private String autoSelected;
	private SendableChooser<String> chooser = new SendableChooser<>();
	private PWMSpeedController speedController1 = null;
	private PWMSpeedController speedController2 = null;
	// Here we make a timer that we use in the autonomous.
	private Timer mainTimer = null;
	//Here we set the joystick so we can use it to control our speed controllers.
	private Joystick joystick = null;
	//Here we call 
	
	private DifferentialDrive drive = null;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", DEFAULT_AUTO);
		chooser.addObject("My Auto", CUSTOM_AUTO);
		SmartDashboard.putData("Auto choices", chooser);
		speedController1 = new Spark(MOTOR1_PORT);
		speedController2 = new Spark(MOTOR2_PORT);
		mainTimer = new Timer();
		joystick = new Joystick(JOYSTICK_PORT);
		drive = new DifferentialDrive(speedController1, speedController2);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
		mainTimer.reset();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
			case CUSTOM_AUTO:
				// Put custom auto code here
				if (mainTimer.get() > AUTO_TIME)
					drive.arcadeDrive(0, 0);
				else drive.arcadeDrive(AUTO_SPEED, 0);
				break;
			case DEFAULT_AUTO:
			default:
				// Put default auto code here
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		drive.arcadeDrive(joystick.getRawAxis(1) * TELEOP_SPEED, joystick.getRawAxis(0) * TELEOP_SPEED);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
