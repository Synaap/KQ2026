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
    private double RPS = fromRPM(120);
    private final double OPEN_ANGLE = 0.01f;
    private final double CLOSE_ANGLE = 1.0f/8;


    public Midtake(HardwareMap hw){
        this.hw = hw;
        this.lock = hw.get(Servo.class, "lockServo");
        this.lock.setDirection(Servo.Direction.REVERSE);
        this.midtakeMotor = hw.get(DcMotorEx.class, "midtakeMotor");
        this.setMidtakeDirection(DcMotorSimple.Direction.FORWARD);
        this.closeLock();
    }
    public void openLock(){
        lock.getController().pwmEnable();
        lock.setPosition(CLOSE_ANGLE);

    }

    public void closeLock(){
        lock.getController().pwmDisable();
    }

    public boolean isLockOpen(){
        return Math.abs(lock.getPosition() - OPEN_ANGLE) < 0.1f;
    }

    public void enableMidtake(){
        this.midtakeMotor.setVelocity(RPS, AngleUnit.DEGREES);
    }

    public void disableMidtake(){
        this.midtakeMotor.setPower(0.0f);
    }

    public void setMidtakeDirection(DcMotorSimple.Direction direction){
        this.midtakeMotor.setDirection(direction);
    }

    public static double fromRPM(double RPM){
        return RPM * ((((1+(46/17.0))) * (1+(46/17.0))) * (1+(46/17.0)) * 28) / 60;
    }

}
