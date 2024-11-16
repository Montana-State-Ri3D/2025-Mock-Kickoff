package frc.robot.subsystems.Elevator;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

public class ElevatorRealIO implements ElevatorIO{

    private CANSparkMax elevatorMasterMotor;
    private CANSparkMax elevatorSlaveMotor;
    
    private RelativeEncoder elevatorMasterEncoder;
    private RelativeEncoder elevatorSlaveEncoder;

    private CANSparkMax[] Motors;
    private RelativeEncoder[] Encoders;

    public ElevatorRealIO(int elevatorMotor){
        this.elevatorMasterMotor = new CANSparkMax(elevatorMotor, CANSparkMax.MotorType.kBrushless);
        this.elevatorSlaveMotor = new CANSparkMax(elevatorMotor, CANSparkMax.MotorType.kBrushless);

        this.Motors = new CANSparkMax[2];
        this.Motors[0] = this.elevatorMasterMotor;
        this.Motors[1] = this.elevatorSlaveMotor;


        for (CANSparkMax motor : this.Motors) {
            motor.restoreFactoryDefaults();
            motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            motor.setSmartCurrentLimit(40);
        }

        this.elevatorMasterMotor.setInverted(false);

        this.elevatorSlaveMotor.follow(this.elevatorMasterMotor, true);

        this.elevatorMasterEncoder = this.elevatorMasterMotor.getEncoder();
        this.elevatorSlaveEncoder = this.elevatorSlaveMotor.getEncoder();


        this.Encoders = new RelativeEncoder[2];
        this.Encoders[0] = this.elevatorMasterEncoder;
        this.Encoders[1] = this.elevatorSlaveEncoder;
    }

    public void elevate(double master){
        this.elevatorMasterMotor.set(master);
    }
    
    public void toggleMode() {
        if (this.elevatorMasterMotor.getIdleMode() == CANSparkMax.IdleMode.kBrake) {
            for (CANSparkMax motor : this.Motors) {
                motor.setIdleMode(CANSparkMax.IdleMode.kCoast);
            }
        } else {
            for (CANSparkMax motor : this.Motors) {
                motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
            }
        }
    }

    public void updateInputs(ElevatorIOInputs inputs) {
        inputs.elevatorMasterPower = this.elevatorMasterMotor.get();
        inputs.elevatorSlavePower = this.elevatorSlaveMotor.get();
        inputs.elevatorMasterPosition = this.elevatorMasterEncoder.getPosition();
        inputs.elevatorSlavePosition = this.elevatorSlaveEncoder.getPosition();
        inputs.elevatorMasterVelocity = this.elevatorMasterEncoder.getVelocity();
        inputs.elevatorSlaveVelocity = this.elevatorSlaveEncoder.getVelocity();
        inputs.elevatorMasterCurrent = this.elevatorMasterMotor.getOutputCurrent();
        inputs.elevatorSlaveCurrent = this.elevatorSlaveMotor.getOutputCurrent();
        inputs.isBrake = this.elevatorMasterMotor.getIdleMode() == CANSparkMax.IdleMode.kBrake;
    }
}
