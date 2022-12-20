package edu.umb.cs681.hw11;

import java.util.concurrent.atomic.AtomicReference;

public class Aircraft {

    private AtomicReference<Position> position;

    public Aircraft(Position position) {
        this.position = new AtomicReference<Position>(position);
    }

    public Position getPosition() {
        return position.get();
    }

    public void setPosition(double latitude, double longitude, double altitude) {
        position.set(new Position(latitude, longitude, altitude));
    }

}
