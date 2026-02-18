package org.firstinspires.ftc.teamcode.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


import org.firstinspires.ftc.teamcode.shared.PathBuilder;

@Autonomous(name = "2026AutoIteration1", group = "KQ")
public class KQAuto extends OpMode {


    protected Follower follower;
    protected Timer pathTimer, actionTimer, opmodeTimer;

    protected int pathState;

    protected Path scorePreload;
    protected Pose startPose;
    protected PathChain grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;

    private int detectedTagID = -1;

    public void buildPaths(){
        return;
    }

    public void initStartPose(){
        return;
    }

    @Override
    public void init() {
        this.buildPaths();
        aprilTag = new AprilTagProcessor.Builder()
                .setDrawAxes(true)
                .setDrawTagOutline(true)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(WebcamName.class, "Webcam 1"))
                .addProcessor(aprilTag)
                .build();
    }

    @Override
    public void loop() {
        for (AprilTagDetection detection : aprilTag.getDetections()) {
            detectedTagID = detection.id;
        }

    }
}
