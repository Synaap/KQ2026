package org.firstinspires.ftc.teamcode.teleop;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

public class Drive {
    private final Follower follower;

    private boolean fieldCentric = true;  // if true, pass robotCentric=false to setTeleOpDrive
    private boolean slowMode = false;
    private Double headingLock = null;    // radians; null = free turn

    // Tunables
    private double deadzone = 0.05;
    private double slowTransScale = 0.35;
    private double slowTurnScale = 0.45;
    private double holdKp = 2.0;   // heading lock P gain
    private double holdClamp = 0.8;   // max |turn| while locked

    public Drive(HardwareMap hw) {
        follower = Constants.createFollower(hw);
    }

    // Call from OpMode.start()
    public void startTeleop() {
        follower.startTeleopDrive(); // switch follower into driver-control mode
    }

    public void setStartingPose(Pose p) {
        follower.setStartingPose(p);
    }

    public Pose getPose() {
        return follower.getPose();
    }

    public Follower getFollower() {
        return follower;
    }

    public void setFieldCentric(boolean v) {
        fieldCentric = v;
    }

    public void toggleFieldCentric() {
        fieldCentric = !fieldCentric;
    }

    public boolean isFieldCentric() {
        return fieldCentric;
    }

    public void setSlowMode(boolean v) {
        slowMode = v;
    }

    public void toggleSlowMode() {
        slowMode = !slowMode;
    }

    public boolean isSlowMode() {
        return slowMode;
    }

    public void setHeadingLock(Double radiansOrNull) {
        headingLock = radiansOrNull;
    }

    public Double getHeadingLock() {
        return headingLock;
    }

    // main drive call each loop
    public void drive(double forward, double strafe, double turn) {
        forward = dz(forward, deadzone); // Dz checks deadzone
        strafe = dz(strafe, deadzone);
        turn = dz(turn, deadzone);

        double transScale = slowMode ? slowTransScale : 1.0;
        double turnScale = slowMode ? slowTurnScale : 1.0;

        forward *= transScale;
        strafe *= transScale;

        if (headingLock != null) {
            double err = wrap(headingLock - follower.getPose().getHeading());
            double hold = clamp(err * holdKp, -holdClamp, holdClamp);
            turn = hold * turnScale; // override driver turn while locked
        } else {
            turn *= turnScale;
        }

        boolean robotCentric = !fieldCentric; // API expects "robotCentric" flag
        follower.setTeleOpDrive(forward, strafe, turn, robotCentric);
    }

    // MUST be called once per loop
    public void update() {
        follower.update();
    }

    public void stop() {
        follower.setTeleOpDrive(0, 0, 0, true);
        follower.update();
    }

    // utils
    private static double dz(double v, double z) {
        return Math.abs(v) < z ? 0.0 : v;
    }

    private static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    private static double wrap(double a) {
        while (a > Math.PI) a -= 2 * Math.PI;
        while (a < -Math.PI) a += 2 * Math.PI;
        return a;
    }
}