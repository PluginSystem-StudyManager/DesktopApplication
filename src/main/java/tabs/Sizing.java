package tabs;


import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.layout.Region;


public class Sizing {

    public static void setSize(Region node, Region parent, double width, double height) {
        setWidth(node, parent, width);
        setHeight(node, parent, height);
    }

    public static void setSize(DoubleProperty property, Region parent, double width, double height) {
        setWidth(property, parent, width);
        setHeight(property, parent, height);
    }

    public static void setSize(Region node, ReadOnlyDoubleProperty widthProperty, ReadOnlyDoubleProperty heightProperty,
                               double width, double height) {
        setWidth(node, widthProperty, width);
        setHeight(node, heightProperty, height);
    }


    public static void setWidth(Region node, Region parent, double size) {
        set(node.prefWidthProperty(), parent.widthProperty(), size);
    }

    public static void setWidth(DoubleProperty property, Region parent, double size) {
        set(property, parent.widthProperty(), size);
    }

    public static void setWidth(Region node, ReadOnlyDoubleProperty property, double size) {
        set(node.prefWidthProperty(), property, size);
    }

    public static void set(DoubleProperty nodeProperty, ReadOnlyDoubleProperty parentProperty, double size) {
        nodeProperty.bind(parentProperty.multiply(size));
    }

    public static void setWidth(Region node, double size) {
        Parent parent = node.getParent();
        while (!(parent instanceof Region) && parent != null) {
            parent = parent.getParent();
        }
        if (parent == null) {
            node.parentProperty().addListener(l -> {
                Region parentRegion = (Region) node.getParent();
                node.prefWidthProperty().bind(parentRegion.widthProperty().multiply(size));
            });
            // throw new RuntimeException("Can not find a suitable parent with a width property");
        } else {
            Region parentRegion = (Region) parent;
            node.prefWidthProperty().bind(parentRegion.widthProperty().multiply(size));
        }

    }

    public static void setHeight(Region node, Region parent, double size) {
        set(node.prefHeightProperty(), parent.heightProperty(), size);
    }

    public static void setHeight(DoubleProperty property, Region parent, double size) {
        set(property, parent.heightProperty(), size);
    }

    public static void setHeight(Region node, ReadOnlyDoubleProperty property, double size) {
        set(node.prefHeightProperty(), property, size);
    }

}
