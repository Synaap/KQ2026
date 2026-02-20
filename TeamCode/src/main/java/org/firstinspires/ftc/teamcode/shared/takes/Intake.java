package org.firstinspires.ftc.teamcode.shared.takes;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Intake {

    DcMotorEx intakeMotor;
    private final double RPS = Midtake.fromRPM(60);

    public Intake(HardwareMap hw){
        this.intakeMotor = hw.get(DcMotorEx.class, "intakeMotor");
    }

    public void enableIntake(){ intakeMotor.setVelocity(RPS, AngleUnit.DEGREES); }

    public void disableIntake() { intakeMotor.setPower(0.0f); }

    public boolean atSpeed() { return intakeMotor.getVelocity() >= RPS; }

    public boolean stopped() { return intakeMotor.getVelocity() == 0.0f; }

    public void reverse() {
        if (this.intakeMotor.getDirection() == DcMotorSimple.Direction.FORWARD)
            this.intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        else
            this.intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public DcMotorSimple.Direction getDirection(){
        return intakeMotor.getDirection();
    }

}
