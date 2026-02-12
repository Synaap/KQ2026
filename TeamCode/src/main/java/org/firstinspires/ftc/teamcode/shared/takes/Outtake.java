package org.firstinspires.ftc.teamcode.shared.takes;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Outtake {

    private double targetRPM;

    DcMotorEx outtakeMotor;

    public Outtake(HardwareMap hw, String name){
        this.outtakeMotor = hw.get(DcMotorEx.class, name);
    }

    public void spinMotor(double rad){
        this.targetRPM = rad;
        this.outtakeMotor.setVelocity(rad, AngleUnit.RADIANS);
    }

    public void stopMotor(){
        this.targetRPM = 0;
        this.outtakeMotor.setPower(0.0);
    }

    public boolean atSpeed() { return this.outtakeMotor.getVelocity(AngleUnit.RADIANS) >= this.targetRPM; };

}
