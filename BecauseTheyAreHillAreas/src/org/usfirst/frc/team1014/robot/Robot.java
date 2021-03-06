
package org.usfirst.frc.team1014.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


import org.usfirst.frc.team1014.robot.commands.StartPosCenterScale;
import org.usfirst.frc.team1014.robot.commands.StartPosCenterSwitch;
import org.usfirst.frc.team1014.robot.commands.StartPosLeftScale;
import org.usfirst.frc.team1014.robot.commands.StartPosLeftSwitch;
import org.usfirst.frc.team1014.robot.commands.StartPosRightScale;
import org.usfirst.frc.team1014.robot.commands.StartPosRightSwitch;
import org.usfirst.frc.team1014.robot.subsystems.ExampleSubsystem;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	double FMSAutoData;
    
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		oi = new OI();
		
		
	    
		// chooser.addObject("My Auto", new MyAutoCommand());
		chooser.addObject("Start Pos Center Scale", new StartPosCenterScale(FMSAutoData));
		chooser.addObject("Start Pos Center Switch", new StartPosCenterSwitch(FMSAutoData));
		chooser.addObject("Start Pos Left Scale", new StartPosLeftScale(FMSAutoData));
		chooser.addObject("Start Pos Left Switch", new StartPosLeftSwitch(FMSAutoData));
		chooser.addObject("Start Pos Right Scale", new StartPosRightScale(FMSAutoData));
		chooser.addObject("Start Pos Right Switch", new StartPosRightSwitch(FMSAutoData));
		SmartDashboard.putData("Auto mode", chooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		
		/*This is where the robot gets the robot gets the data from the FMS 
		 then turns it into 1 of 4 options from LLL(1) RRR(2) LRL(3) RLR(4)
		*/
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		 
		if(gameData.charAt(0) == 'L')
		{
			FMSAutoData = 1;
		} if(gameData.charAt(1) == 'R'){
			FMSAutoData = 3;
		} if(gameData.charAt(0) == 'R'){
			FMSAutoData = 2;
		} if(gameData.charAt(1) == 'L'){
			FMSAutoData = 4;
		}

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */
		/*
		//LLL
		if (FMSAutoData == 1 ){
			
		} 
		///RRR
		if (FMSAutoData == 2){
			
		} 
		//LRL
		if (FMSAutoData == 3){
			
		} 
		//RLR
		if (FMSAutoData == 4){
			
		} */
		
		
		//Here we would use chooser to input the starting position and choice of scale 
		//or switch rather than to select the auto command
		autonomousCommand = chooser.getSelected();
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
