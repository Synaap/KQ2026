package org.firstinspires.ftc.teamcode.teleop;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.shared.Goal;
import org.firstinspires.ftc.teamcode.shared.PathBuilder;
import org.firstinspires.ftc.teamcode.shared.takes.Intake;
import org.firstinspires.ftc.teamcode.shared.takes.Midtake;
import org.firstinspires.ftc.teamcode.shared.takes.Outtake;
import org.firstinspires.ftc.teamcode.shared.AimController;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Drive – Pedro", group = "2026")
public class KQTeleOp extends OpMode {

    private Drive drive;
    private Intake intake;
    private Midtake midtake;
    private Outtake outtake;
    private AimController aimController; // Removed usage to simplify debugging cuz delayed
    private Follower follower;

    private double turret_power = 0.5;
    private final double outtakeSpeedHigh = 10000.0;
    private final double outtakeSpeedLow = 5000.0;
    private double outtakeSpeed = outtakeSpeedHigh;

    private Goal goal;

    private boolean aPrev, bPrev, xPrev, yPrev, lbPrev, rbPrev, following;

    @Override public void init() {
        drive = new Drive(hardwareMap);
        telemetry.addLine("Init OK: A=Slow, B=FieldCentric, X=Lock 90°, Y=Free");
        telemetry.update();

        intake = new Intake(hardwareMap);
        midtake = new Midtake(hardwareMap);
        outtake = new Outtake(hardwareMap);

        midtake.closeLock();

        follower = Constants.createFollower(hardwareMap);
        follower.setPose(new Pose(99, 7, Math.toRadians(0))); // Starting pose

        aimController = new AimController(58.0f, 0.2f, 0.072f);

        // Targets are in AimController
        this.goal = Goal.BLUE; // TODO: Update this to read April Tag
        this.following = false;
    }

    @Override public void start() {
        drive.startTeleop(); // required in Pedro 2.x teleop flow
        outtake.enableOuttake(outtakeSpeed);
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

        boolean dLeft = gamepad2.dpadLeftWasPressed(),
                dUp = gamepad2.dpadUpWasPressed(),
                dRight = gamepad2.dpadRightWasPressed(),
                dDown = gamepad2.dpad_down,
                rt = gamepad2.right_trigger > Drive.deadzone,
                lt = gamepad2.left_trigger > Drive.deadzone,
                rb = gamepad2.right_bumper,
                lb = gamepad2.left_bumper,
                a2 = gamepad2.aWasPressed(),
                x2 = gamepad2.xWasPressed();


        drive.drive(fwd, str, trn);
        drive.update();

        Pose p = drive.getPose();
        telemetry.addData("FC", drive.isFieldCentric());
        telemetry.addData("Slow", drive.isSlowMode());
        telemetry.addData("Lock", drive.getHeadingLock()==null? "free" : Math.toDegrees(drive.getHeadingLock()));
        telemetry.addData("Pose","(%.1f, %.1f, %.0f°)", p.getX(), p.getY(), Math.toDegrees(p.getHeading()));
        telemetry.addData("Turret Power", turret_power);
        telemetry.addData("Target RPM", outtakeSpeed);
        telemetry.addData("Reversed", intake.getDirection() == DcMotorSimple.Direction.REVERSE);
        telemetry.update();

        // Reverse intake/midtake
        if (a2) {
            intake.reverse();
            midtake.reverse();
        }

        // Switch far/close
        if (x2) {
            outtakeSpeed = outtakeSpeed == outtakeSpeedHigh ? outtakeSpeedLow : outtakeSpeedHigh;
        }

        // D-Pad up toggles turret
        if (dUp) {
            if (outtake.isEnabled())
                outtake.disableOuttake();
            else
                outtake.enableOuttake(outtakeSpeed);
        }


        // D-pad left moves turret 15 degrees left
        if (lb) {
            outtake.aimTurret(turret_power);
        }


        // D-pad right moves turret 15 degrees right
        else if (rb) {
            outtake.aimTurret(-turret_power);

        } else{outtake.aimTurret(0.0);}
        // Cycle ball
        if (dDown) {
            outtake.enableOuttake(0.09);
            midtake.enableMidtake();
            midtake.openLock();
        }

        // Open Lock, Spin Midtake
        if (rt) {
            this.midtake.openLock();
            this.midtake.enableMidtake();
        }

        // Activate Intake, Midtake
        if (lt) {
            this.intake.enableIntake();
            this.midtake.enableMidtake();
        }

        // Disable the motors
        if (!lt && !rt && !dDown) {
            this.intake.disableIntake();
            this.midtake.disableMidtake();
            this.midtake.closeLock();
            if (this.outtake.isEnabled())
                this.outtake.enableOuttake(outtakeSpeed);
        }

        //

        lbPrev = lb;
        rbPrev = rb;

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
