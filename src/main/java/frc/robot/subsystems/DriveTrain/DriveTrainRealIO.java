package frc.robot.subsystems.DriveTrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants.DriveTrainConstants;

public class DriveTrainRealIO implements DriveTrainIO {

    private CANSparkMax leftMotorBack;
    private CANSparkMax leftMotorFront;
    private CANSparkMax rightMotorBack;
    private CANSparkMax rightMotorFront;

    private RelativeEncoder leftEncoderBack;
    private RelativeEncoder leftEncoderFront;
    private RelativeEncoder rightEncoderBack;
    private RelativeEncoder rightEncoderFront;

    private CANSparkMax[] Motors = { leftMotorBack, leftMotorFront, rightMotorBack, rightMotorFront };
    private RelativeEncoder[] Encoders = { leftEncoderBack, leftEncoderFront, rightEncoderBack, rightEncoderFront };

    public DriveTrainRealIO(int leftMotorBack, int leftMotorFront, int rightMotorBack, int rightMotorFront) {

        this.leftMotorBack = new CANSparkMax(leftMotorBack, CANSparkMax.MotorType.kBrushless);
        this.leftMotorFront = new CANSparkMax(leftMotorFront, CANSparkMax.MotorType.kBrushless);
        this.rightMotorBack = new CANSparkMax(rightMotorBack, CANSparkMax.MotorType.kBrushless);
        this.rightMotorFront = new CANSparkMax(rightMotorFront, CANSparkMax.MotorType.kBrushless);

        for (CANSparkMax motor : this.Motors) {
            motor.restoreFactoryDefaults();
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            motor.setSmartCurrentLimit(40);
        }

        this.leftMotorFront.setInverted(false);
        this.leftMotorBack.setInverted(false);
        this.rightMotorFront.setInverted(false);
        this.rightMotorBack.setInverted(false);

        this.leftMotorBack.follow(this.leftMotorFront, false);
        this.rightMotorBack.follow(this.rightMotorFront, false);

        this.leftEncoderBack = this.leftMotorBack.getEncoder();
        this.leftEncoderFront = this.leftMotorFront.getEncoder();
        this.rightEncoderBack = this.rightMotorBack.getEncoder();
        this.rightEncoderFront = this.rightMotorFront.getEncoder();

        for (RelativeEncoder encoder : this.Encoders) {
            encoder.setPositionConversionFactor(
                    DriveTrainConstants.DRIVE_WHEEL_RADIUS * 2.0 * Math.PI * DriveTrainConstants.DRIVE_RADIO);
            encoder.setVelocityConversionFactor(
                    DriveTrainConstants.DRIVE_WHEEL_RADIUS * 2.0 * Math.PI * DriveTrainConstants.DRIVE_RADIO / 60.0);
        }

    }

    public void drive(double left, double right) {
        this.leftMotorFront.set(left);
        this.rightMotorFront.set(right);
    }

    public void toggleMode() {
        if (this.leftMotorFront.getIdleMode() == CANSparkMax.IdleMode.kBrake) {
            for (CANSparkMax motor : this.Motors) {
                motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            }
        } else {
            for (CANSparkMax motor : this.Motors) {
                motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
            }
        }
    }

    public void updateInputs(DriveTrainIOInputs inputs) {
        inputs.leftPower = this.leftMotorFront.get();
        inputs.rightPower = this.rightMotorFront.get();
        inputs.leftPosition = this.leftEncoderFront.getPosition();
        inputs.rightPosition = this.rightEncoderFront.getPosition();
        inputs.leftVelocity = this.leftEncoderFront.getVelocity();
        inputs.rightVelocity = this.rightEncoderFront.getVelocity();
        inputs.leftCurrent = this.leftMotorFront.getOutputCurrent();
        inputs.rightCurrent = this.rightMotorFront.getOutputCurrent();
        inputs.brake = this.leftMotorFront.getIdleMode() == CANSparkMax.IdleMode.kBrake;
    }

}
