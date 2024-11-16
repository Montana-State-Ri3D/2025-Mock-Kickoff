package frc.robot.subsystems.Intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import frc.robot.Constants.IntakeConstants;

public class IntakeRealIO implements IntakeIO{
    private CANSparkMax leftIntake;
    private CANSparkMax rightIntake;
    private CANSparkMax pivotMotor;
    private boolean isBrake;
    private RelativeEncoder leftIntakeEncoder;
    private RelativeEncoder rightIntakeEncoder;
    private RelativeEncoder pivotEncoder;
    private CANSparkMax[] Motors;

    public IntakeRealIO(int leftIntake, int rightIntake, int pivotMotor){
        this.leftIntake = new CANSparkMax(leftIntake, CANSparkMax.MotorType.kBrushless);
        this.rightIntake = new CANSparkMax(rightIntake, CANSparkMax.MotorType.kBrushless);
        this.pivotMotor = new CANSparkMax(pivotMotor, CANSparkMax.MotorType.kBrushless);

        this.Motors = new CANSparkMax[3];
        this.Motors[0] = this.leftIntake;
        this.Motors[1] = this.rightIntake;
        this.Motors[2] = this.pivotMotor;

        for (CANSparkMax motor: this.Motors){
            motor.restoreFactoryDefaults();
            motor.setIdleMode(CANSparkMax.IdleMode.kBrake);

        }

        this.leftIntake.setInverted(false);
        this.rightIntake.setInverted(true);

        this.rightIntake.follow(this.leftIntake, false);

        leftIntakeEncoder = this.leftIntake.getEncoder();
        rightIntakeEncoder = this.rightIntake.getEncoder();
        pivotEncoder = this.pivotMotor.getEncoder();

        rightIntakeEncoder.setPositionConversionFactor(IntakeConstants.INTAKE_WHEEL_RADIUS*2.0*Math.PI*IntakeConstants.INTAKE_RATIO);
        leftIntakeEncoder.setPositionConversionFactor(IntakeConstants.INTAKE_WHEEL_RADIUS*2.0*Math.PI*IntakeConstants.INTAKE_RATIO);
        pivotEncoder.setPositionConversionFactor(IntakeConstants.PIVOT_ARM_RADIUS*2.0*Math.PI*IntakeConstants.PIVOT_RATIO);

        rightIntakeEncoder.setVelocityConversionFactor(IntakeConstants.INTAKE_WHEEL_RADIUS*2.0*Math.PI*IntakeConstants.INTAKE_RATIO/60.0);
        leftIntakeEncoder.setVelocityConversionFactor(IntakeConstants.INTAKE_WHEEL_RADIUS*2.0*Math.PI*IntakeConstants.INTAKE_RATIO/60.0);
        pivotEncoder.setVelocityConversionFactor(IntakeConstants.PIVOT_ARM_RADIUS*2.0*Math.PI*IntakeConstants.PIVOT_RATIO/60.0);
        
    }

    public void setBrake(boolean isBrake) {
        if(isBrake){
            for (CANSparkMax motor: this.Motors){
                motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
            }
        }else{
            for (CANSparkMax motor: this.Motors){
                motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            }
        }
        this.isBrake = isBrake;
        

    }

    public void setPower(double leftPower, double rightPower) {
       leftIntake.set(leftPower);
    }

    public void updateInputs(IntakeIOInputs inputs) {
        inputs.isBrake = isBrake;
        inputs.leftCurrent = leftIntake.getOutputCurrent();
        inputs.rightCurrent = rightIntake.getOutputCurrent();
        inputs.rightVelo = rightIntakeEncoder.getVelocity();
        inputs.leftVelo = leftIntakeEncoder.getVelocity();
        inputs.pivotPosition = pivotEncoder.getPosition();
        inputs.leftPower = leftIntake.get();
        inputs.rightPower = rightIntake.get();
        inputs.pivotPower = pivotMotor.get();
        
    }
}
