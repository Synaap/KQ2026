package org.firstinspires.ftc.teamcode.shared.takes;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Midtake {
    private HardwareMap hw;
    private Servo lock;
    private ServoController lockController;
    private DcMotorEx midtakeMotor;
    private boolean enabled = false;
    private boolean lockOpen = false;
    private double RPS = fromRPM(120);
    private final double OPEN_ANGLE = 0.0f;
    private final double CLOSE_ANGLE = 1/7.0f;


    public Midtake(HardwareMap hw){
        this.hw = hw;
        this.lock = hw.get(Servo.class, "lockServo");
        this.lock.setDirection(Servo.Direction.REVERSE);
        this.midtakeMotor = hw.get(DcMotorEx.class, "midtakeMotor");
        this.setMidtakeDirection(DcMotorSimple.Direction.FORWARD);
        this.closeLock();
    }
    public void openLock(){
        if (lockOpen) { return; }
        lock.setPosition(OPEN_ANGLE);
        lockOpen = true;

    }

    public void closeLock(){
        lock.setPosition(CLOSE_ANGLE);
        lockOpen = false;
    }

    public boolean isLockOpen() { return lockOpen; }

    public boolean isEnabled() { return enabled; }

    public void enableMidtake(){
        if (enabled) { return; }
        this.midtakeMotor.setVelocity(RPS, AngleUnit.DEGREES);
    }

    public void disableMidtake(){
        this.midtakeMotor.setPower(0.0f);
    }

    public void setMidtakeDirection(DcMotorSimple.Direction direction){
        this.midtakeMotor.setDirection(direction);
    }

    public void reverse() {
        if (this.midtakeMotor.getDirection() == DcMotorSimple.Direction.FORWARD)
            this.setMidtakeDirection(DcMotorSimple.Direction.REVERSE);
        else
            this.setMidtakeDirection(DcMotorSimple.Direction.FORWARD);
    }

    public static double fromRPM(double RPM){
        return RPM * ((((1+(46/17.0))) * (1+(46/17.0))) * (1+(46/17.0)) * 28) / 60;
    }

}
