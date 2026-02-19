package org.firstinspires.ftc.teamcode.shared;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;

public class PathBuilder {

    private static final int PARK_X_BLUE = 105;
    private static final int PARK_X_RED  = 57;
    private static final int PARK_Y      = 33;

    // Get a path with the new heading, just rotating it in place.
    public static PathChain getRotationPath(Pose robot, double targetHeading){
        Pose newHeading = new Pose(robot.getX(), robot.getY(), targetHeading);

        return follower.pathBuilder()
                .addPath(new BezierLine(robot, newHeading))
                .build();


    }

    // Get a path to park where we need.
    public static PathChain getParkingPath(Pose robot, Goal goal){

        Pose newHeading;

        if(goal == Goal.RED)
            newHeading = new Pose(PARK_X_RED, PARK_Y);
        else
            newHeading = new Pose(PARK_X_BLUE, PARK_Y);

        return follower.pathBuilder()
                .addPath(new BezierLine(robot, newHeading))
                .setLinearHeadingInterpolation(robot.getHeading(), 0)
                .build();

    }

}
