package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.shared.PathBuilder;

@Autonomous(name = "2026AutoIteration1", group = "KQ")
public class KQAuto extends OpMode {

    protected Follower follower;
    protected Timer pathTimer, actionTimer, opmodeTimer;

    protected int pathState;

    protected Path scorePreload;
    protected Pose startPose;
    protected PathChain grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3;

    public void buildPaths(){
        return;
    }

    public void initStartPose(){
        return;
    }

    @Override
    public void init() {
        this.buildPaths();
    }

    @Override
    public void loop() {

    }
}
