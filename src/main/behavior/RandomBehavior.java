package main.behavior;

import java.util.List;
import java.util.Random;

import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;

public class RandomBehavior implements BehaviorStrategy {

    private static final double MAX_FORCE = 0.03;
    private static final double MAX_SPEED = 2.0;
    private final Random random = new Random();

    @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors) {
        double angleChange = (random.nextDouble() - 0.5) * Math.toRadians(30);
        double speed = Math.sqrt(boid.getVx() * boid.getVx() + boid.getVy() * boid.getVy());
        if (speed == 0) speed = MAX_SPEED / 2;

        double angle = Math.atan2(boid.getVy(), boid.getVx()) + angleChange;

        double newVx = Math.cos(angle) * speed;
        double newVy = Math.sin(angle) * speed;

        double steerX = newVx - boid.getVx();
        double steerY = newVy - boid.getVy();

        double magnitude = Math.sqrt(steerX * steerX + steerY * steerY);
        if (magnitude > MAX_FORCE) {
            steerX = (steerX / magnitude) * MAX_FORCE;
            steerY = (steerY / magnitude) * MAX_FORCE;
        }

        return new Forces(new Vector2D(steerX, steerY), Vector2D.ZERO, Vector2D.ZERO);
    }
}