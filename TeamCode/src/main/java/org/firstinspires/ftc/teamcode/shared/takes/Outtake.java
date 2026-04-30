package org.firstinspires.ftc.teamcode.shared.takes;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImpl;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Outtake {

    private double targetRPM;
    private final double maxRPM = 6000;
    private boolean enabled;

    DcMotorEx outtakeMotor;
    DcMotorEx outtakeMotor2;
    CRServo turretServo;

    public Outtake(HardwareMap hw){
        this.turretServo = hw.get(CRServo.class, "turretServo");
        this.outtakeMotor = hw.get(DcMotorEx.class, "outtakeMotor");
        this.outtakeMotor2 = hw.get(DcMotorEx.class, "outtakeMotor2");
        this.outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.setPIDFCoefficients();
    }

    public void aimTurret(double power){ // -1, to 1
        if (this.turretServo.getPower() == power) { return; }
        this.turretServo.setPower(power);
    }

    public void enableOuttake(double rpm){
        if (enabled && rpm == targetRPM) { return; }
        this.targetRPM = rpm;
        this.outtakeMotor.setPower(fromRPM(rpm, maxRPM));
        this.outtakeMotor2.setPower(fromRPM(rpm, maxRPM));
        this.enabled = true;
    }

    public void disableOuttake(){
        if (!enabled) { return; }
        this.targetRPM = 0;
        this.outtakeMotor.setPower(0.0);
        this.outtakeMotor2.setPower(0.0);
        this.enabled = false;
    }

    public void setPIDFCoefficients(){
        PIDFCoefficients pc = new PIDFCoefficients(125.0f, 0.0f, 0.0f, 21.1f); // Untuned
        this.outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pc);
        this.outtakeMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pc);
    }

    public boolean atSpeed() { return this.outtakeMotor.getPower() >= fromRPM(this.targetRPM, maxRPM); };

    public static double fromRPM(double rpm, double max) { return rpm/max; } // The random looking numbers is the encoder forumla

    private static double normalize(double degrees) { return degrees/180; }

    public boolean isEnabled() { return this.enabled; }
}
