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

@Autonomous(name = "RedAssistAuto", group = "KQ")
public class RedAssistAuto extends OpMode {


    Follower follower;

    Intake intake;
    Midtake midtake;
    Outtake outtake;

    // At beginning
    Pose startPose;
    Pose scorePose;

    // At end
    Pose pickupPose;
    Pose jamPose;

    Timer opmodeTimer;

    protected int pathState;
    protected int paths;
    protected double headingLock;


    public void buildPaths(){
        headingLock = 0;
        startPose  = new Pose(99.0, 9, headingLock);
        scorePose  = new Pose(102.0, 12, headingLock);
        pickupPose = new Pose(134.0, 12, headingLock);
        jamPose    = new Pose( 105, 12, 0);
    }

    @Override
    public void init(){
        this.pathState   = 0;
        this.paths       = 5;
        this.opmodeTimer = new Timer();
        this.follower    = Constants.createFollower(hardwareMap);
        this.intake      = new Intake(hardwareMap);
        this.midtake     = new Midtake(hardwareMap);
        this.outtake     = new Outtake(hardwareMap);
        this.buildPaths();
        this.follower.setPose(startPose);
        buildPaths();
    }

    @Override
    public void start(){
        this.opmodeTimer.resetTimer();
        this.midtake.closeLock();
        this.outtake.enableOuttake(5700); // 5700 Good Battery, 5800 Okay Battery, 6000 Bad. bethany
    }

    @Override
    public void loop(){

        Pose p = follower.getPose();

        telemetry.addData("Path State", pathState);
        telemetry.addData("Lock Open", midtake.isLockOpen());
        telemetry.addData("Pose","(%.1f, %.1f, %.0f°)", p.getX(), p.getY(), Math.toDegrees(p.getHeading()));
        telemetry.addData("Time Elapesed", opmodeTimer.getElapsedTimeSeconds());
        telemetry.addData("Path Completion", follower.getPathCompletion());
        telemetry.update();

        follower.update();
        updatePath();

    }

    public void updatePathState() {
        follower.breakFollowing();;
        this.pathState = (this.pathState + 1) % this.paths;
    }

    public void updatePath(){ // DONT MAKE FUN OF ME LEO I DIDNT WANT TO BUILD PATHS CUZ NOTHING WOULD INHERIT THIS
        switch (pathState) {
            case 0: // Shoot
                if (follower.isBusy() || opmodeTimer.getElapsedTimeSeconds() < 1.5) { break; }
                this.intake.enableIntake();
                this.midtake.enableMidtake();
                midtake.openLock();
                opmodeTimer.resetTimer();
                updatePathState();
                break;
            case 1: // To Pickup
                if (follower.isBusy() || opmodeTimer.getElapsedTimeSeconds() < 2) { break; }
                midtake.closeLock();
                follower.followPath(follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), pickupPose))
                        .setHeadingConstraint(headingLock)
                        .addParametricCallback(0.89, this::updatePathState)
                        .build());
                break;
            case 2: // To Jam
                if (follower.isBusy()) { break; }
                follower.followPath(follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), jamPose))
                        .setReversed()
                        .setHeadingConstraint(headingLock)
                        .addParametricCallback(0.93, this::updatePathState)
                        .build());
                break;
            case 3: // To pickup
                if (follower.isBusy()) { break; }
                follower.followPath(follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), pickupPose))
                        .setHeadingConstraint(headingLock)
                        .addParametricCallback(0.93, this::updatePathState)
                        .build());
                break;
            case 4: // To shoot
                if (follower.isBusy()) { break; }
                midtake.closeLock();
                follower.followPath(follower.pathBuilder()
                        .addPath(new BezierLine(follower.getPose(), scorePose))
                        .setHeadingConstraint(headingLock)
                        .setReversed()
                        .addParametricCallback(0.89, this::updatePathState)
                        .build());
                break;

        }
    }

}
