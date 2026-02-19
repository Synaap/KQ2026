package org.firstinspires.ftc.teamcode.auto; // make sure this aligns with class location

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name = "AutoRedClose", group = "AutoModes")
public class KQAutoRedClose extends KQAuto {

    @Override
    public void buildPaths(){
        this.startPose = new Pose(123,123, Math.toRadians(220));
        this.grabPickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(this.startPose, new Pose(90, 84, 0), new Pose(120, 84, 0)))
                .build();
        this.scorePickup1 = follower.pathBuilder()
                .addPath(new BezierCurve(follower.getPose(), new Pose(90, 84, 0), new Pose(120, 84, 0)))
                .build();
    }

}