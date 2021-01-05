package assistant.Utils;

import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.Set;
import java.util.stream.Collectors;

public class DonutChart extends PieChart {
    private final double SPACE = 30; /* Gap between outer and inner pie*/
    private PieChart innerChart;
    private Circle circle;
    private Bounds outerPieBounds;

    public DonutChart(ObservableList<Data> data) {
        super(data);
        innerChart = new PieChart() {
            @Override
            protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
                super.layoutChartChildren(top, left, contentWidth, contentHeight);
                final Pane chartContent = (Pane) lookup(".chart-content");
                chartContent.setPadding(new Insets(0));
            }
        };
        innerChart.setPadding(new Insets(0));
        innerChart.setLabelsVisible(false);
        innerChart.setLegendVisible(false);

        circle = new Circle(40);
        circle.setOpacity(1);
        circle.setStyle("-fx-fill:white;");
    }

    @Override
    protected void layoutChartChildren(double top, double left, double contentWidth, double contentHeight) {
        super.layoutChartChildren(top, left, contentWidth, contentHeight);
        final Pane chartContent = (Pane) lookup(".chart-content");
        if (!chartContent.getChildren().contains(innerChart)) {
            chartContent.getChildren().add(innerChart);
        }
        if (!chartContent.getChildren().contains(circle)) {
            chartContent.getChildren().add(circle);
        }
        updateOuterPieBounds();
        double cX = outerPieBounds.getMinX() + (outerPieBounds.getWidth() / 2);
        double cY = outerPieBounds.getMinY() + (outerPieBounds.getHeight() / 2);
        circle.setCenterX(cX);
        circle.setCenterY(cY);
        double innerSize = outerPieBounds.getWidth() - (2 * SPACE);
        innerChart.resize(innerSize, innerSize); // THIS WHERE YOUR ISSUE LIES. YOU NEED TO PROVIDE THE SIZE TO INNER CHART
        innerChart.setTranslateX(cX - innerChart.getWidth() / 2);
        innerChart.setTranslateY(cY - innerChart.getHeight() / 2);
    }

    public void setInnerChartData(ObservableList<PieChart.Data> data) {
        innerChart.setData(data);
    }

    /**
     * Determining the outer pie visual bounds.
     */
    private void updateOuterPieBounds() {
        Pane chartContent = (Pane) lookup(".chart-content");
        Set<Node> pieNodes = chartContent.getChildren().stream().filter(node -> node.getStyleClass().contains("chart-pie")).collect(Collectors.toSet());
        double minX = getWidth();
        double minY = getHeight();
        double maxX = 0, maxY = 0;
        for (Node pie : pieNodes) {
            Bounds pieBounds = pie.getBoundsInParent();
            minX = Math.min(minX, pieBounds.getMinX());
            minY = Math.min(minY, pieBounds.getMinY());
            maxX = Math.max(maxX, pieBounds.getMaxX());
            maxY = Math.max(maxY, pieBounds.getMaxY());
        }
        outerPieBounds = new BoundingBox(minX, minY, maxX - minX, maxY - minY);
    }
}
