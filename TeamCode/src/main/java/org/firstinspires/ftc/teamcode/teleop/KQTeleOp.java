package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.geometry.Pose;

import org.firstinspires.ftc.teamcode.shared.Outtake;
import org.firstinspires.ftc.teamcode.shared.AimController;


@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Drive – Pedro", group = "T")
public class TeleOp extends OpMode {
    private Drive drive;
    private Outtake outtake;
    private AimController aimController;

    private boolean aPrev, bPrev, xPrev, yPrev;
    private static final double AIM_HEADING_TOL_RAD = Math.toRadians(2.0); // 2°

    @Override public void init() {
        drive = new Drive(hardwareMap);
        telemetry.addLine("Init OK: A=Slow, B=FieldCentric, X=Lock 90°, Y=Free");
        telemetry.update();

        // Shooter: names must match your RC config
        outtake = new Outtake(hardwareMap, "outtakeMotor" ); // or pass null if no hood

        // Pick alliance target. Example goal positions (replace with real field coords):
        // Red goal at (144, 72), Blue goal at (0, 72) — adjust to your game setup!
        boolean isRed = true; // set from a gamepad toggle or dashboard
        double goalX = isRed ? 144.0 : 0.0;
        double goalY = 72.0;



    }

    @Override public void start() {
        drive.startTeleop(); // required in Pedro 2.x teleop flow
    }


    @Override public void loop() {
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
            double theta = autoAim.targetHeading(p);
            drive.setHeadingLock(theta);

            // 2) Spin shooter based on distance (and hood if present)
            outtake.setTarget(autoAim.rpmFor(p), autoAim.hoodFor(p));
        } else {
            // release heading lock when not aiming
            if (drive.getHeadingLock() != null) drive.setHeadingLock(null);
        }

        boolean aligned = Math.abs(wrap(autoAim.targetHeading(p) - p.getHeading())) < AIM_HEADING_TOL_RAD;
        boolean ready   = outtake.atSpeed();

        if (gamepad1.left_bumper && aligned && ready) {
            // TODO: actuate your feed/trigger here (servo or motor)
            // e.g., feeder.setPosition(0.75); sleep 120ms; back to 0.25 (do non-blocking in OpMode!)
        }

// … existing driving code …

    }

    @Override public void stop() { drive.stop(); }

    private static double wrap(double a){
        while(a> Math.PI) a-=2*Math.PI;
        while(a<-Math.PI) a+=2*Math.PI;
        return a;
    }

}
