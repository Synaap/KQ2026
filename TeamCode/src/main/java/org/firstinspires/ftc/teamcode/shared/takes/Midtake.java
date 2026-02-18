package org.firstinspires.ftc.teamcode.shared.takes;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Midtake {
    private HardwareMap hw;
    private Servo lock;
    private DcMotorEx midtakeMotor;
    private final double OPEN_ANGLE = 1.0f/8;
    private final double CLOSE_ANGLE = 0.0f;


    public Midtake(HardwareMap hw){
        this.hw = hw;
        this.lock = hw.get(Servo.class, "lockServo");
        this.midtakeMotor = hw.get(DcMotorEx.class, "midtakeMotor");
        this.setMidtakeDirection(DcMotorSimple.Direction.FORWARD);
        this.setMidtakeCoefficients();
        this.closeLock();
    }
    public void openLock(){
        lock.setPosition(OPEN_ANGLE);
    }

    public void closeLock(){
        lock.setPosition(CLOSE_ANGLE);
    }

    public boolean isLockOpen(){
        return Math.abs(lock.getPosition() - OPEN_ANGLE) < 1.0f;
    }

    public void setMidtakeCoefficients(){
        PIDFCoefficients pc = new PIDFCoefficients(0.1f, 0.0f, 0.0f, 0.2f);
        this.midtakeMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, pc);
    }

    public void startMidtake(){
        this.midtakeMotor.setVelocity(100.0f, AngleUnit.DEGREES);
    }

    public void stopMidtake(){
        this.midtakeMotor.setPower(0.0f);
    }

    public void setMidtakeDirection(DcMotorSimple.Direction direction){
        this.midtakeMotor.setDirection(direction);
    }

}
