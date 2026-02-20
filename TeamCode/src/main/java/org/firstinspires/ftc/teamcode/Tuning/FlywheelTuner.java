package org.firstinspires.ftc.teamcode.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.shared.takes.Outtake;

import java.util.ArrayList;

public class FlywheelTuner extends OpMode {

    private double offsets[] = {0.01, 0.1, 1, 10, 100};
    private DcMotorEx flywheel;
    private Telemetry telemtry;
    private double p;
    private double f;
    private double targetRPM = 0;
    private final double LOWRPM = 3000;
    private final double HIGHRPM = 5000;
    private int offsetIndex = 0;
    private boolean pMode = false;

    @Override
    public void init(){
        flywheel = hardwareMap.get(DcMotorEx.class, "outtakeMotor");
    }

    @Override
    public void loop(){

        telemtry.addData("CURRENT RPM", flywheel.getVelocity());
        telemtry.addData("TARGET RPM", targetRPM);
        telemtry.addData("P", p);
        telemtry.addData("F", f);
        telemtry.addData("OFFSET", offsets[offsetIndex]);
        telemtry.update();

        if (gamepad1.aWasPressed())
            offsetIndex = (offsetIndex + 1) % offsets.length;

        if (gamepad1.bWasPressed())
            pMode = !pMode;

        if (gamepad1.xWasPressed())
            targetRPM = targetRPM == HIGHRPM ? LOWRPM : HIGHRPM;

        if (gamepad1.dpadLeftWasPressed()){
            if (pMode)
                p -= offsets[offsetIndex];
            else
                f -= offsetIndex;
        }

        if (gamepad1.dpadRightWasPressed()) {
            if (pMode)
                p += offsets[offsetIndex];
            else
                f += offsetIndex;
        }

        PIDFCoefficients coefficients = new PIDFCoefficients(p, 0.0, 0.0, f);
        flywheel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, coefficients);
        flywheel.setVelocity(Outtake.fromRPM(targetRPM));

    }

}
