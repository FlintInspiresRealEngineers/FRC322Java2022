/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.fireteam322.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fireteam322.frc.robot.Constants;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
  // The Shooter is both our upper level ball intake and our ball output
  // mechanism.
  private final WPI_TalonSRX m_leftShooterMotor = new WPI_TalonSRX(Constants.LEFT_SHOOTER_MOTOR);
  private final WPI_TalonSRX m_rightShooterMotor = new WPI_TalonSRX(Constants.RIGHT_SHOOTER_MOTOR);
  private final WPI_TalonSRX m_topShooterMotor = new WPI_TalonSRX(Constants.TOP_SHOOTER_MOTOR);

  private final MotorControllerGroup m_shooterMotors =
      new MotorControllerGroup(m_leftShooterMotor, m_rightShooterMotor, m_topShooterMotor);

  /** Creates a new Shooter. */
  public Shooter() {
    super();
    // Set the inversion on the shooter motors.
    m_leftShooterMotor.setInverted(false);
    m_rightShooterMotor.setInverted(false);
    m_topShooterMotor.setInverted(true);
    m_shooterMotors.setInverted(true);

    // Set the shooter motors to Coast so they don't stop balls moving through them.
    m_leftShooterMotor.setNeutralMode(NeutralMode.Coast);
    m_rightShooterMotor.setNeutralMode(NeutralMode.Coast);
    m_topShooterMotor.setNeutralMode(NeutralMode.Coast);
  }

  public void stop() {
    m_shooterMotors.stopMotor();
  }

  public void run(double speed) {
    m_shooterMotors.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
