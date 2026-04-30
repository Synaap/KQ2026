package org.firstinspires.ftc.teamcode.auto;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.shared.takes.Intake;
import org.firstinspires.ftc.teamcode.shared.takes.Midtake;
import org.firstinspires.ftc.teamcode.shared.takes.Outtake;
@Autonomous(name = "BlueAuto", group = "KQ")
public class BlueAuto extends RedAssistAuto {
    @Override
    public void buildPaths(){
        headingLock = Math.toRadians(180);
        startPose = new Pose(55,9,headingLock);
        scorePose = new Pose(55,12,headingLock);
        pickupPose = new Pose (7,12,headingLock);
        jamPose = new Pose (16,12,headingLock);
    }

}
