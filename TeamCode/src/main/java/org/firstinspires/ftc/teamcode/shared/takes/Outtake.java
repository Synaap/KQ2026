package org.firstinspires.ftc.teamcode.shared.takes;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Outtake {

    private double targetRPM;

    DcMotorEx outtakeMotor;
    DcMotorEx outtakeMotor2;
    Servo turretServo;

    public Outtake(HardwareMap hw){
        this.turretServo = hw.get(Servo.class, "turretServo");
        this.outtakeMotor = hw.get(DcMotorEx.class, "outtakeMotor");
        this.outtakeMotor2 = hw.get(DcMotorEx.class, "outtakeMotor2");
        this.outtakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.setPIDFCoefficients();
    }

    public void aimTurret(Pose currentPose, double targetHeading){
        double servoPosition = (currentPose.getHeading() + targetHeading) % 360;
        turretServo.setPosition(servoPosition);
    }

    public void enableOuttake(double rpm){
        this.targetRPM = rpm;
        this.outtakeMotor.setVelocity(fromRPM(rpm), AngleUnit.DEGREES);
        this.outtakeMotor2.setVelocity(fromRPM(rpm), AngleUnit.DEGREES);
    }

    public void disableOuttake(){
        this.targetRPM = 0;
        this.outtakeMotor.setPower(0.0);
        this.outtakeMotor2.setPower(0.0);
    }

    public void setPIDFCoefficients(){
        PIDFCoefficients pc = new PIDFCoefficients(400.0f, 0.0f, 0.0f, 14.0f); // Untuned
        this.outtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pc);
        this.outtakeMotor2.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pc);
    }

    public boolean atSpeed() { return this.outtakeMotor.getVelocity(AngleUnit.DEGREES) >= fromRPM(this.targetRPM); };

    public static double fromRPM(double rpm) { return rpm * ((((1+(46/17.0))) * (1+(46/17.0))) * 28) / 60; } // The random looking numbers is the encoder forumla
}
