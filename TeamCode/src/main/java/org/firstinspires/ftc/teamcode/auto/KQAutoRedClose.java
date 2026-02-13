package org.firstinspires.ftc.teamcode.auto; // make sure this aligns with class location

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "RedClose", group = "AutoModes")
public class KQAutoRedClose extends KQAuto {

    @Override
    public void initStartPose(){
        this.startPose = new Pose();
    }
    @Override
    public void buildPaths(){

    }

}