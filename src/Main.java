import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "Modular Multiplication JellyBeanci(c)";
    public static final int width = 800;
    public static final int height = 800;
    private static Color backcolor = Color.rgb(51, 51, 51);
    private static Background background = new Background(new BackgroundFill(backcolor, new CornerRadii(0), new Insets(0, 0, 0, 0)));

    private static int dotValue = 50;
    private static int multValue = 2;
    private static Stage stg;
    private static Circle center;

    private static ArrayList<Circle> dots = new ArrayList<>();
    private static ArrayList<Line> lines = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception
    {
        stg = stage;
        BorderPane root = new BorderPane();
        Pane subRoot = new Pane();
        subRoot.setBackground(background);
        //
        Label dotLabel = new Label("Number of Dots");
        Label multLabel = new Label("Multiplier");
        Slider dotSlider = new Slider(0, 150, 50);
        Slider multSlider = new Slider(2, 10, 2);
        dotSlider.setBackground(background);
        multSlider.setBackground(background);
        //
        VBox vBox = new VBox();
        vBox.setBackground(background);
        vBox.setSpacing(3);
        vBox.setPadding(new Insets(2, 10, 2, 10));
        vBox.getChildren().addAll(dotSlider, multSlider);
        //
        root.setCenter(subRoot);
        root.setBottom(vBox);
        //
        child = subRoot.getChildren();
        //
        center = new Circle(width / 2, (height / 2) - 20, 5, Color.RED);
        child.addAll(center);
        //
        setDots();
        setPolylines();
        //
        dotSlider.setOnMouseDragged(e -> {
            dotValue = (int) dotSlider.getValue();
            setDots();
            setPolylines();
            printValues();
        });
        //
        multSlider.setOnMouseDragged(e -> {
            multValue = (int) multSlider.getValue();
            setPolylines();
            printValues();
        });
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root, width - 10, height - 10, backcolor));
        stage.show();
        root.requestFocus();
    }

    private static void setDots()
    {
        child.removeAll(dots);
        dots.clear();
        for (int i = 0; i < dotValue; i++)
        {
            Point2D point = Utils.endPoint(center.getCenterX(), center.getCenterY(), i * (360 / (double) dotValue) + 90, width * 0.45);
            dots.add(new Circle(point.getX(), point.getY(), 3, Color.SNOW));
        }
        for (Circle circle : dots)
        {
            child.add(circle);
        }
    }

    private static void setPolylines()
    {
        child.removeAll(lines);
        lines.clear();
        for (int i = 0; i < dots.size(); i++)
        {
            // For all dots
            // if Under range
            double startX = dots.get(i).getCenterX();
            double startY = dots.get(i).getCenterY();
            int next = (((i + 1) * multValue) - 1) % dots.size();
            double endX = dots.get(next).getCenterX();
            double endY = dots.get(next).getCenterY();
            Line tmp = new Line(startX, startY, endX, endY);
            tmp.setStroke(Color.YELLOW);
            tmp.setStrokeWidth(1);
            lines.add(tmp);
        }
        for (Line line : lines)
        {
            child.add(line);
        }
    }

    private static void printValues()
    {
        stg.setTitle(String.format("Dot: %3d, Multiplier: %2d", dotValue, multValue));
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
