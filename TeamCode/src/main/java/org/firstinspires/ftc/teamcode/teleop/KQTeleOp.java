package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
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
    private Follower follower;

    private Goal goal;

    private boolean aPrev, bPrev, xPrev, yPrev, following;

    @Override public void init() {
        drive = new Drive(hardwareMap);
        telemetry.addLine("Init OK: A=Slow, B=FieldCentric, X=Lock 90°, Y=Free");
        telemetry.update();

        intake = new Intake(hardwareMap);
        midtake = new Midtake(hardwareMap);
        outtake = new Outtake(hardwareMap); // or pass null if no hood

        follower = Constants.createFollower(hardwareMap);

        aimController = new AimController(58.0f, 0.2f, 0.072f);

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
            outtake.aimTurret(p, theta);
        } else {
            // release heading lock when not aiming
            if (drive.getHeadingLock() != null) drive.setHeadingLock(null);
        }

        // TODO: PLEASE MAKE THIS LOOK PRETTY
        if (gamepad1.right_trigger > Drive.deadzone) {
            this.outtake.enableOuttake(aimController.calculateMotorVelocity(follower.getPose(), this.goal));
            if(this.outtake.atSpeed())
                this.midtake.openLock();
        } else {
            this.midtake.closeLock();
            this.outtake.disableOuttake();
        }

        // Close lock, disable

        if (gamepad1.left_trigger > Drive.deadzone) {
            this.intake.enableIntake();
            this.midtake.enableMidtake();
        } else {
            this.intake.disableIntake();
            this.midtake.disableMidtake();
        }

        /*
        if (gamepad1.left_bumper && aligned && ready) {
            // TODO: actuate your feed/trigger here (servo or motor)
            // e.g., feeder.setPosition(0.75); sleep 120ms; back to 0.25 (do non-blocking in OpMode!)
        }
        */

    }

    @Override public void stop() { drive.stop(); }

    /*
    private static double wrap(double a){
        while(a-> Math.PI) a-=2*Math.PI;
        while(a<-Math.PI) a+=2*Math.PI;
        return a;
    }
    */
}
