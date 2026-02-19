package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.shared.Goal;
import org.firstinspires.ftc.teamcode.shared.PathBuilder;
import org.firstinspires.ftc.teamcode.shared.takes.Intake;
import org.firstinspires.ftc.teamcode.shared.takes.Midtake;
import org.firstinspires.ftc.teamcode.shared.takes.Outtake;
import org.firstinspires.ftc.teamcode.shared.AimController;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Drive – Pedro", group = "T")
public class KQTeleOp extends OpMode {
    private Drive drive;
    private Intake intake;
    private Midtake midtake;
    private Outtake outtake;
    private AimController aimController;

    private Goal goal;

    private boolean aPrev, bPrev, xPrev, yPrev, following;
    private static final double AIM_HEADING_TOL_RAD = Math.toRadians(2.0); // 2°

    @Override public void init() {
        drive = new Drive(hardwareMap);
        telemetry.addLine("Init OK: A=Slow, B=FieldCentric, X=Lock 90°, Y=Free");
        telemetry.update();

        // Shooter: names must match your RC config
        outtake = new Outtake(hardwareMap, "outtakeMotor" ); // or pass null if no hood

        // Targets are in AimController
        this.goal = Goal.BLUE; // TODO: Update this to read April Tag
        this.following = false;
    }

    @Override public void start() {
        drive.startTeleop(); // required in Pedro 2.x teleop flow
    }


    @Override public void loop() {

        follower.update();

        if (this.following && !follower.isBusy()) this.following = false;

        if(this.following)
            return;

        // Stick mapping (FTC docs & Pedro example use negatives on left axes)
        double fwd = -gamepad1.left_stick_y; // + forward
        double str = -gamepad1.left_stick_x; // + right (note Pedro example uses negative)
        double trn = -gamepad1.right_stick_x; // + CCW

        // toggles
        boolean a = gamepad1.a, b = gamepad1.b, x = gamepad1.x, y = gamepad1.y;
        if (a && !aPrev) drive.toggleSlowMode();
        if (b && !bPrev) drive.toggleFieldCentric();
        if (x && !xPrev) drive.setHeadingLock(Math.toRadians(90));
        if (y && !yPrev) drive.setHeadingLock(null);
        aPrev = a; bPrev = b; xPrev = x; yPrev = y;

        drive.drive(fwd, str, trn);
        drive.update();

        Pose p = drive.getPose();
        telemetry.addData("FC", drive.isFieldCentric());
        telemetry.addData("Slow", drive.isSlowMode());
        telemetry.addData("Lock", drive.getHeadingLock()==null? "free" : Math.toDegrees(drive.getHeadingLock()));
        telemetry.addData("Pose","(%.1f, %.1f, %.0f°)", p.getX(), p.getY(), Math.toDegrees(p.getHeading()));
        telemetry.update();

        if (gamepad1.right_bumper) {
            // 1) Snap heading toward goal
            double theta = aimController.targetHeading(p, this.goal);
            drive.setHeadingLock(theta);

            // 2) Spin robot to match target
            follower.followPath(PathBuilder.getRotationPath(p, aimController.targetHeading(p, this.goal)));
            following = true;

        } else {
            // release heading lock when not aiming
            if (drive.getHeadingLock() != null) drive.setHeadingLock(null);
        }

        if (gamepad1.right_trigger > Drive.deadzone){

            boolean aligned = Math.abs(wrap(aimController.targetHeading(p, this.goal) - p.getHeading())) < AIM_HEADING_TOL_RAD;
            boolean ready = outtake.atSpeed();

        if (gamepad1.left_bumper && aligned && ready) {
            // TODO: actuate your feed/trigger here (servo or motor)
            // e.g., feeder.setPosition(0.75); sleep 120ms; back to 0.25 (do non-blocking in OpMode!)
        }

    }

    @Override public void stop() { drive.stop(); }

    private static double wrap(double a){
        while(a-> Math.PI) a-=2*Math.PI;
        while(a<-Math.PI) a+=2*Math.PI;
        return a;
    }

}
