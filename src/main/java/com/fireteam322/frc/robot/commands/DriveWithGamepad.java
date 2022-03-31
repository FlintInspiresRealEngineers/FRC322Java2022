/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.fireteam322.frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import com.fireteam322.frc.robot.subsystems.Chassis;

/**
 * Have the robot drive racing game style.
 */
public class DriveWithGamepad extends CommandBase {
	private final Chassis m_chassis;
	private final DoubleSupplier m_speed, m_rotation;
	private final JoystickButton m_brakeButton;

	/**
	 * Creates a new DriveWithJoystick command.
	 *
	 * @param speed       The control input for the left side of the drive
	 * @param drivetrain The drivetrain subsystem to drive
	 */
	public DriveWithGamepad(DoubleSupplier speed, DoubleSupplier rotation, Chassis chassis,
			JoystickButton brake) {
		m_chassis = chassis;
		m_rotation = rotation;
		m_speed = speed;
		m_brakeButton = brake;
		addRequirements(m_chassis);
	}

	@Override
	public void initialize() {
		// m_chassis.setupEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		m_chassis.drive(m_speed.getAsDouble(), m_rotation.getAsDouble());
		if (m_brakeButton.get())
			m_chassis.brake(true);
		else
			m_chassis.brake(false);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	public void end(boolean interrupted) {
		m_chassis.drive(0, 0);
	}
}