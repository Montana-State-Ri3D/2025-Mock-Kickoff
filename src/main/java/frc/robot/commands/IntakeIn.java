package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake.Intake;

public class IntakeIn extends Command{
    Intake intake;

    private final BooleanSupplier cancel;
    private double speed;
    private double amps;

    private long initTime = -1;
    private long duration = 500;

    public IntakeIn(Intake intake, BooleanSupplier cancel, double speed, double amps) {
        this.cancel = cancel;
        this.speed = speed;
        this.amps = amps;
        addRequirements(intake);

        this.intake = intake;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        initTime = System.currentTimeMillis();
        intake.setPower(speed, speed);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.setPower(0, 0);
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        long currentTime = System.currentTimeMillis();
        return ((currentTime >= initTime + duration) && 
                (intake.getLeftCurrent() > amps) || (intake.getRightCurrent() > amps) || cancel.getAsBoolean());
    }
}
