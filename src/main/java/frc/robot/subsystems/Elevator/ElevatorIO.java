package frc.robot.subsystems.Elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
       
    @AutoLog
    public class ElevatorIOInputs{
        public double elevatorMasterPower;
        public double elevatorSlavePower;

        public double elevatorMasterCurrent;
        public double elevatorSlaveCurrent;

        public double elevatorMasterEncoder;
        public double elevatorSlaveEncoder;

        public double elevatorMasterPosition;
        public double elevatorSlavePosition;

        public double elevatorMasterVelocity;
        public double elevatorSlaveVelocity;

        
        public boolean isBrake;
    }
    default void updateInputs(ElevatorIOInputs inputs){}
    default void setPower(double elevatorPower){}
    default void setBrake(boolean isBrake){}
}
