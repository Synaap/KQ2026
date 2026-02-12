package org.firstinspires.ftc.teamcode.shared;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathBuilder;
import com.pedropathing.paths.PathChain;

public class AimController {

    private final double exitAngle;
    private final double slip;
    private final double wheelRadius;

    public AimController(double theta, double slip, double radius){
        this.exitAngle   = theta;
        this.slip        = slip;
        this.wheelRadius = radius;
    }

    public double calculateSpeed(Pose robot, Goal goal){
        return Math.sqrt((this.distance(robot, goal) * 386.1)/Math.sin(2*Math.toRadians(this.exitAngle))); // 386.1 is G constant in inch/s^2
    }

    public double calculateMotorVelocity(Pose robot, Goal goal){
        double speed = this.calculateSpeed(robot, goal);
        return speed/(this.slip*this.wheelRadius);
    }

    public double targetHeading(Pose robot, Goal goal){
        double targetX = goal == Goal.BLUE ? 144.0f : 0.0f; // Robot X is subtracted later
        double targetY = 144.0f - robot.getY();

        targetX -= robot.getX();
        return Math.atan2(targetX, targetY);
    }

    private double distance(Pose robot, Goal goal){ // In inches
        double dX = goal == Goal.BLUE ? 144.0f : 0.0f; // Robot X is subtracted later
        double dY = 144.0f - robot.getY();

        dX -= robot.getX();
        return Math.hypot(dX, dY);
    }

    private double toRad(int RPM){
        return RPM*(Math.PI/30);
    }

    private int toRPM(double rad){
        return (int) Math.round(rad*(30/Math.PI));
    }

}
