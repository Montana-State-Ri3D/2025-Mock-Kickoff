package frc.robot.utilities;

import frc.robot.Robot;

import frc.robot.Constants.DriveTrainConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.subsystems.DriveTrain.*;
import frc.robot.subsystems.Intake.*;


public class SubsystemFactory {
    boolean isReal = false;

    public SubsystemFactory() {
        isReal = Robot.isReal();
    }

    public DriveTrain createDriveTrain() {
        if (isReal) {
            return new DriveTrain(
                    new DriveTrainRealIO(
                            DriveTrainConstants.LEFT_MOTOR_BACK_ID,
                            DriveTrainConstants.LEFT_MOTOR_FRONT_ID,
                            DriveTrainConstants.RIGHT_MOTOR_BACK_ID,
                            DriveTrainConstants.RIGHT_MOTOR_FRONT_ID,
                            DriveTrainConstants.PIGEON_ID
                            ));
        } else {
            return new DriveTrain(new DriveTrainSimIO());
        }
    }

    public Intake createIntake(){
        if (isReal){
            return new Intake(
                new IntakeRealIO(
                        IntakeConstants.LEFT_INTAKE_ID, 
                        IntakeConstants.RIGHT_INTAKE_ID,
                        IntakeConstants.PIVOT_MOTOR_ID
                        ));
        } else{
            return new Intake(new IntakeSimIO());
        }
    }

}
