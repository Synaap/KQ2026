package org.firstinspires.ftc.teamcode.auto; // make sure this aligns with class location
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.TelemetryManager;
import com.bylazar.telemetry.PanelsTelemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import com.pedropathing.geometry.Pose;

@Autonomous(name = "RedClose", group = "AutoModes")
@Configurable // Panels
public class KQAutoRedClose extends KQAuto {
    private TelemetryManager panelsTelemetry;
    public Follower follower;
    private int pathState;
    private Paths paths;


    @Override   public void init() {
        panelsTelemetry = PanelsTelemetry.INSTANCE.getTelemetry();


        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose(72, 8, Math.toRadians(90)));


        paths = new Paths(follower); // mKING NEW ONES


        panelsTelemetry.debug("Status", "Initialized");
        panelsTelemetry.update(telemetry);
    }


    @Override
    public void loop() {
        follower.update(); //
        pathState = autonomousPathUpdate();


        //
        panelsTelemetry.debug("Path State", pathState);
        panelsTelemetry.debug("X", follower.getPose().getX());
        panelsTelemetry.debug("Y", follower.getPose().getY());
        panelsTelemetry.debug("Heading", follower.getPose().getHeading());
        panelsTelemetry.update(telemetry);
    }


    public static class Paths {
        public PathChain Path1; // SHOOT PRELOADED
        public PathChain Path2; //INTAKE?
        public PathChain Path3; //INTAKE FS
        public PathChain Path4;
        public PathChain Path5; //SHOOT
        public PathChain Path6; //INTAKE?
        public PathChain Path7; // INTAKE FS FS
        public PathChain Path8; // MAKE THIS LONGER I THINK
        public PathChain Path9; // SHOOT BUT WE MIGHT HAVE TO BACK IT UP
        public PathChain Path10; // I DUNNO IF OUR INTAKE NEEDS TO POWER UP OR NAH
        public PathChain Path11; // INTAKE FS
        public PathChain Path12; //
        public PathChain Path13;//SHOOT


        public Paths(Follower follower) {
            Path1 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(20.386, 122.398),
                                    new Pose(57.904, 86.019)


                            )
                    )
                    .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(180))
                    .build();


            Path2 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(57.904, 86.019),
                                    new Pose(44.558, 85.462)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path3 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(44.558, 85.462),
                                    new Pose(15.346, 84.327)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path4 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(15.346, 84.327),
                                    new Pose(58.019, 85.712)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path5 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(58.019, 85.712),
                                    new Pose(49.019, 93.981)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path6 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(49.019, 93.981),
                                    new Pose(39.558, 60.808)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path7 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(39.558, 60.808),
                                    new Pose(16.038, 60.923)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path8 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(16.038, 60.923),
                                    new Pose(49.231, 94.846)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path9 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(49.231, 94.846),
                                    new Pose(46.558, 97.115)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path10 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(46.558, 97.115),
                                    new Pose(43.615, 36.173)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path11 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(43.615, 36.173),
                                    new Pose(16.212, 35.615)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path12 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(16.212, 35.615),
                                    new Pose(53.404, 89.750)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();


            Path13 = follower.pathBuilder()
                    .addPath(
                            new BezierLine(
                                    new Pose(53.404, 89.750),
                                    new Pose(46.692, 97.308)
                            )
                    )
                    .setTangentHeadingInterpolation()
                    .build();
        }
    }

    @Override
    public void autonomousPathUpdate() {

        return;
    }
}
