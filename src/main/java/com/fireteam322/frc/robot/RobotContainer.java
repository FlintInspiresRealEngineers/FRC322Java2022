// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package com.fireteam322.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import com.fireteam322.frc.robot.commands.*;
import com.fireteam322.frc.robot.subsystems.*;
import com.fireteam322.frc.robot.utilities.*;
import com.fireteam322.frc.robot.utilities.Limelight.CameraMode;
import com.fireteam322.frc.robot.utilities.Limelight.LightMode;

/**
 * This class is where the bulk of the robot should be declared.
 * Since Command-based is a "declarative" paradigm, very little robot logic
 * should actually be handled in the {@link Robot} periodic methods (other than
 * the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and
 * button mappings) should be declared here.
 */
public class RobotContainer {
	// The robot's subsystems and commands are defined here...
	private static Command m_autoCommand;
	private static SendableChooser<Command> autonomousChooser = new SendableChooser<>();

	private final AddressableLEDs m_AddressableLEDs = new AddressableLEDs(Constants.ADDRESSABLE_LED_PORT,
			Constants.ADDRESSABLE_LED_LENGTH);
	private final Chassis m_chassis = new Chassis();
	private final LimelightCamera m_limelightCamera = new LimelightCamera();
	private final RobotCamera m_rearCamera = new RobotCamera("Rear Camera", Constants.REAR_CAMERA_CHANNEL);
	private final RobotPower m_robotPower = new RobotPower();
	private final RearClimber m_rearClimber = new RearClimber();
	private final FrontClimber m_frontClimber = new FrontClimber();
	private final Feeder m_feeder = new Feeder();
	private final Intake m_intake = new Intake();
	private final Shooter m_shooter = new Shooter();

	private final F310Controller m_driveStick = new F310Controller(Constants.DRIVE_STICK);
	private final F310Controller m_manipulatorStick = new F310Controller(Constants.MANIPULATOR_STICK);

	private final JoystickButton m_brakeButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kBumperLeft.getValue());

	private final JoystickButton m_rearClimbButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kX.getValue());
	private final JoystickButton m_rearClimbReverseButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kY.getValue());
	private final JoystickButton m_frontClimbButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kA.getValue());
	private final JoystickButton m_frontClimbReverseButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kB.getValue());
	private final JoystickButton m_visionModeButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kBumperLeft.getValue());
	private final JoystickButton m_driverModeButton = new JoystickButton(m_driveStick,
			F310Controller.Button.kBumperRight.getValue());

	private final Joystick m_leftDriveJoystick = new Joystick(Constants.LEFT_DRIVE_STICK);
	private final Joystick m_rightDriveJoystick = new Joystick(Constants.RIGHT_DRIVE_STICK);
	private final JoystickButton m_brakeButtonJoystick = new JoystickButton(m_rightDriveJoystick,
			Joystick.ButtonType.kTrigger.value);
	private final JoystickButton m_rearClimbButtonJoystick = new JoystickButton(m_rightDriveJoystick,
			Constants.CLIMB_JOYSTICK_BUTTON);
	private final JoystickButton m_frontClimbButtonJoystick = new JoystickButton(m_leftDriveJoystick,
			Constants.CLIMB_JOYSTICK_BUTTON);
	private final JoystickButton m_rearClimbReverseButtonJoystick = new JoystickButton(m_rightDriveJoystick,
			Constants.CLIMB_REVERSE_JOYSTICK_BUTTON);
	private final JoystickButton m_frontClimbReverseButtonJoystick = new JoystickButton(m_leftDriveJoystick,
			Constants.CLIMB_REVERSE_JOYSTICK_BUTTON);
	private final JoystickButton m_visionModeButtonJoystick = new JoystickButton(m_rightDriveJoystick,
			Constants.VISION_MODE_BUTTON);
	private final JoystickButton m_driverModeButtonJoystick = new JoystickButton(m_leftDriveJoystick,
			Constants.VISION_MODE_BUTTON);

	private final JoystickButton m_feederButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kA.getValue());
	private final JoystickButton m_feederReverseButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kB.getValue());
	private final JoystickButton m_shooterButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kX.getValue());
	private final JoystickButton m_shooterReverseButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kY.getValue());
	private final JoystickButton m_intakeReverseButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kBumperLeft.getValue());
	private final JoystickButton m_intakeButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kBumperRight.getValue());
	private final JoystickButton m_LEDOnButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kStart.getValue());
	private final JoystickButton m_LEDBlinkButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kStickLeft.getValue());
	private final JoystickButton m_LEDOffButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kBack.getValue());
	private final JoystickButton m_LEDDefaultButton = new JoystickButton(m_manipulatorStick,
			F310Controller.Button.kStickRight.getValue());

	/**
	 * The container for the robot. Contains subsystems, OI devices, and commands.
	 */
	public RobotContainer() {
		// Assign default commands

		if (Constants.CLASSIC_MODE) {
			m_chassis.setDefaultCommand(new DriveWithJoysticks(() -> (m_leftDriveJoystick.getY()),
					() -> m_rightDriveJoystick.getY(), m_chassis, m_brakeButtonJoystick));
		} else {
			m_chassis.setDefaultCommand(new DriveWithJoystick(
					() -> (m_driveStick.getRightTriggerAxis() - m_driveStick.getLeftTriggerAxis()),
					() -> (m_driveStick.getLeftX()), m_chassis, m_brakeButton));
		}

		m_feeder.setDefaultCommand(new RunFeeder(m_feeder, () -> -m_manipulatorStick.getLeftY()));

		m_intake.setDefaultCommand(new RunIntake(m_intake, () -> -m_manipulatorStick.getRightY()));

		m_shooter.setDefaultCommand(new RunShooter(m_shooter, () -> (m_manipulatorStick.getRightTriggerAxis()
				- m_manipulatorStick.getLeftTriggerAxis())));

		m_frontClimber.setDefaultCommand(new RunFrontClimber(m_frontClimber, m_manipulatorStick.getLeftX()));
		m_rearClimber.setDefaultCommand(new RunRearClimber(m_rearClimber, m_manipulatorStick.getRightX()));

		m_AddressableLEDs.setDefaultCommand(new AutomaticAddressableLED(m_AddressableLEDs));

		m_limelightCamera
				.setDefaultCommand(new LimelightLightModeControl(m_limelightCamera, Limelight.LightMode.kforceOff));

		m_rearCamera.setDefaultCommand(new RunRearCamera(m_rearCamera));

		if (Constants.ROBOT_POWER)
			m_robotPower.setDefaultCommand(new StartRobotPower(m_robotPower));

		// Setup the SendableChooser
		chooserSetup();

		// Configure the button bindings
		configureButtonBindings();
	}

	/**
	 * Use this method to define your button->command mappings.
	 * Buttons can be created by instantiating a {@link GenericHID}
	 * or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick}
	 * or {@link XboxController}), and then passing it to a
	 * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
	 */
	private void configureButtonBindings() {
		if (Constants.CLASSIC_MODE) {
			m_visionModeButtonJoystick
					.whileActiveOnce(new LimelightCameraModeControl(m_limelightCamera, CameraMode.kvision));
			m_driverModeButtonJoystick
					.whileActiveOnce(new LimelightCameraModeControl(m_limelightCamera, CameraMode.kdriver));
			m_rearClimbButtonJoystick.whileActiveOnce(new RunRearClimber(m_rearClimber, Constants.CLIMBER_SPEED));
			m_rearClimbReverseButtonJoystick
					.whileActiveOnce(new RunRearClimber(m_rearClimber, Constants.CLIMBER_REVERSE_SPEED));
			m_frontClimbButtonJoystick.whileActiveOnce(new RunFrontClimber(m_frontClimber, Constants.CLIMBER_SPEED));
			m_frontClimbReverseButtonJoystick
					.whileActiveOnce(new RunFrontClimber(m_frontClimber, Constants.CLIMBER_REVERSE_SPEED));
		} else {
			m_visionModeButton.whileActiveOnce(new LimelightCameraModeControl(m_limelightCamera, CameraMode.kvision));
			m_driverModeButton.whileActiveOnce(new LimelightCameraModeControl(m_limelightCamera, CameraMode.kdriver));
			m_rearClimbButton.whileActiveOnce(new RunRearClimber(m_rearClimber, Constants.CLIMBER_SPEED));
			m_rearClimbReverseButton
					.whileActiveOnce(new RunRearClimber(m_rearClimber, Constants.CLIMBER_REVERSE_SPEED));
			m_frontClimbButton.whileActiveOnce(new RunFrontClimber(m_frontClimber, Constants.CLIMBER_SPEED));
			m_frontClimbReverseButton
					.whileActiveOnce(new RunFrontClimber(m_frontClimber, Constants.CLIMBER_REVERSE_SPEED));
		}
		m_feederButton.whileActiveOnce(new RunFeeder(m_feeder, () -> Constants.FEEDER_SPEED), true);
		m_feederReverseButton.whileActiveOnce(new RunFeeder(m_feeder, () -> Constants.FEEDER_REVERSE_SPEED), true);

		m_shooterButton.whileActiveOnce(new RunShooter(m_shooter, () -> Constants.SHOOTER_SPEED), true);
		m_shooterReverseButton.whileActiveOnce(new RunShooter(m_shooter, () -> Constants.SHOOTER_REVERSE_SPEED), true);

		m_intakeButton.whileActiveOnce(new RunIntake(m_intake, () -> Constants.INTAKE_SPEED));
		m_intakeReverseButton.whileActiveOnce(new RunIntake(m_intake, () -> Constants.INTAKE_REVERSE_SPEED));

		m_LEDDefaultButton.whileActiveOnce(new LimelightLightModeControl(m_limelightCamera, LightMode.kpipeLine));
		m_LEDOffButton.whileActiveOnce(new LimelightLightModeControl(m_limelightCamera, LightMode.kforceOff));
		m_LEDBlinkButton.whileActiveOnce(new LimelightLightModeControl(m_limelightCamera, LightMode.kforceBlink));
		m_LEDOnButton.whileActiveOnce(new LimelightLightModeControl(m_limelightCamera, LightMode.kforceOn));
	}

	// Use this to setup the SendableChooser.
	private void chooserSetup() {
		// Add commands to Autonomous SendableChooser
		autonomousChooser.setDefaultOption("Do Nothing", new DoNothing());
		autonomousChooser.addOption("Basic Autonomous", new BasicAutonomous(m_chassis));
		autonomousChooser.addOption("Forward Autonomous", new ForwardAutonomous(m_chassis));
		autonomousChooser.addOption("Simple Autonomous", new SimpleAutonomous(m_chassis));
	}

	/**
	 * Use this to pass the autonomous command to the main {@link Robot} class.
	 *
	 * @return the command to run in autonomous
	 */
	public Command getAutonomousCommand() {
		m_autoCommand = autonomousChooser.getSelected();
		return m_autoCommand;
	}
}
