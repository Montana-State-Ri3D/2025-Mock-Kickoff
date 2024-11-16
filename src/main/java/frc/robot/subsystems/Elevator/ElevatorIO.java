package frc.robot.subsystems.Elevator;

import org.littletonrobotics.junction.AutoLog;

public interface ElevatorIO {
       
    @AutoLog
    public class ElevatorIOInputs{
        public double elevatorPower;

        public double elevatorCurrent;

        public double elevatorEncoder;

        public boolean isBrake;
    }
    default void updateInputs(ElevatorIOInputs inputs){}
    default void setPower(double elevatorPower){}
    default void setBrake(boolean isBrake){}
}
