package frc.robot.subsystems.Intake;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import frc.robot.Constants.IntakeConstants;



public class IntakeSimIO implements IntakeIO {
    private boolean isBrake;
    private CANSparkMax leftIntake;
    private CANSparkMax rightIntake;
    private CANSparkMax pivotMotor;
    private DCMotorSim leftIntakeSim;
    private DCMotorSim rightIntakeSim;
    private DCMotorSim pivotSim;

    public IntakeSimIO(){
        isBrake = true;
        
        leftIntake = new CANSparkMax(1, CANSparkMax.MotorType.kBrushless);
        rightIntake = new CANSparkMax(2, CANSparkMax.MotorType.kBrushless);
        pivotMotor = new CANSparkMax(3, CANSparkMax.MotorType.kBrushless);


        leftIntakeSim = new DCMotorSim(DCMotor.getNEO(1), IntakeConstants.INTAKE_RATIO, 1);
        rightIntakeSim = new DCMotorSim(DCMotor.getNEO(1), IntakeConstants.INTAKE_RATIO, 1);
        pivotSim = new DCMotorSim(DCMotor.getNEO(1), IntakeConstants.PIVOT_RATIO, 1);
        
    }
    
    
    public void setBrake(boolean isBrake) {
       this.isBrake = isBrake;
       
    }


    public void setPower(double leftPower, double rightPower) {
       leftIntake.set(leftPower);
       rightIntake.set(rightPower);
    }


    public void updateInputs(IntakeIOInputs inputs) {
        inputs.isBrake = isBrake;
        inputs.leftCurrent = leftIntakeSim.getCurrentDrawAmps();
        inputs.rightCurrent = rightIntakeSim.getCurrentDrawAmps();
    }
}
