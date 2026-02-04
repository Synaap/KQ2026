package org.firstinspires.ftc.teamcode.shared;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {

    DcMotorEx outtakeMotor;


    public Outtake(HardwareMap hw, String name){
        this.outtakeMotor = hw.get(DcMotorEx.class, name);
    }

}
