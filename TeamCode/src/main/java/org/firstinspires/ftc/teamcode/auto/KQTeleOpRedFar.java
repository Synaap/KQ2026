package org.firstinspires.ftc.teamcode.auto;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.teleop.KQTeleOp;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;

@Autonomous(name = "KQTeleOpRedFar", group = "KQTeleop")
@Configurable // Panels
public class KQTeleOpRedFar extends OpMode {
    private TelemetryManager panelsTelemetry; // Panels Telemetry instance
    public Follower follower; // Pedro Pathing follower instance
    private int pathState; // Current autonomous path state (state machine)
    private Paths paths; // Paths defined in the Paths class

    @Override
    public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));

        paths = new Paths(follower); // Build paths

        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }

    @Override
    public void loop() {
        follower.update(); // Update Pedro Pathing
        pathState = autonomousPathUpdate(); // Update autonomous state machine

        // Log values to Panels and Driver Station
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }

    public static class Paths {
        public PathChain Path1;
        public PathChain Path2;
        public PathChain Path3;
        public PathChain Path4;
        public PathChain Path5;
        public PathChain Path6;
        public PathChain Path7;
        public PathChain Path8;
        public PathChain Path9;
        public PathChain Path10;
        public PathChain Path11;

        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 8.000),
                                    new Pose(56.000, 36.000)
                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();

            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.000, 36.000),
                                    new Pose(129.700, 35.442)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(129.700, 35.442),
                                    new Pose(56.791, 7.832)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path4 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.791, 7.832),
                                    new Pose(56.926, 10.297)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path5 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.926, 10.297),
                                    new Pose(108.754, 59.100)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path6 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(108.754, 59.100),
                                    new Pose(129.284, 58.996)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path7 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(129.284, 58.996),
                                    new Pose(56.538, 7.440)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path8 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.538, 7.440),
                                    new Pose(56.990, 10.570)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path9 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(56.990, 10.570),
                                    new Pose(109.845, 83.458)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path10 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(109.845, 83.458),
                                    new Pose(128.479, 83.094)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();

            Path11 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(128.479, 83.094),
                                    new Pose(56.535, 8.193)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }

    public int autonomousPathUpdate() {
        // Add your state machine Here
        // Access paths with paths.pathName
        // Refer to the Pedro Pathing Docs (Auto Example) for an example state machine
        return 0;
    }
}