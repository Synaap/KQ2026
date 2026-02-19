package org.firstinspires.ftc.teamcode.shared.takes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Outtake {

    private double targetRPM;

    DcMotorEx outtakeMotor;

    public Outtake(HardwareMap hw){
        this.outtakeMotor = hw.get(DcMotorEx.class, "outtakeMotor");
        this.setPIDFCoefficients();
    }

    public void enableOuttake(double deg){
        this.targetRPM = deg;
        this.outtakeMotor.setVelocity(deg, AngleUnit.DEGREES);
    }

    public void disableOuttake(){
        this.targetRPM = 0;
        this.outtakeMotor.setPower(0.0);
    }

    public void setPIDFCoefficients(){
        PIDFCoefficients pc = new PIDFCoefficients(0.08f, 0.0f, 0.0f, 0.12f); // Untuned
        this.outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pc);
    }

    public boolean atSpeed() { return this.outtakeMotor.getVelocity(AngleUnit.DEGREES) >= this.targetRPM; };

}
