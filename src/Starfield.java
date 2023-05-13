import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Starfield extends Application {
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final int NUM_STARS = 1000;

    private Canvas canvas;
    private GraphicsContext gc;
    private List<Star> stars = new ArrayList<>();
    private Random random = new Random();

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT, Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();

        for (int i = 0; i < NUM_STARS; i++) {
            stars.add(new Star(random.nextInt(CANVAS_WIDTH), random.nextInt(CANVAS_HEIGHT)));
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

                for (Star star : stars) {
                    star.update();
                    star.draw(gc);
                }
            }
        };
        timer.start();
    }

    private class Star {
        private double x;
        private double y;
        private double z;
        private double speed;
        private double radius;

        public Star(double x, double y) {
            this.x = x;
            this.y = y;
            this.z = random.nextInt(CANVAS_WIDTH);
            this.speed = random.nextDouble() * 10 + 1;
            this.radius = random.nextDouble() * 2 + 1;
        }

        public void update() {
            z -= speed;
            if (z < 1) {
                z = CANVAS_WIDTH;
            }
        }

        public void draw(GraphicsContext gc) {
            double sx = x / z * CANVAS_WIDTH + CANVAS_WIDTH / 2;
            double sy = y / z * CANVAS_WIDTH + CANVAS_HEIGHT / 2;
            double r = radius / z * CANVAS_WIDTH;
            gc.setFill(Color.WHITE);
            gc.fillOval(sx - r, sy - r, 2 * r, 2 * r);
        }
    }
}