package sample;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.StackPane;

public final class GroupBox extends Parent {

    private StackPane _stackPane;
    private TitledPane _titledPane;

    public GroupBox() {
        _stackPane = new StackPane();
        _titledPane = new TitledPane();
        setContentPadding(new Insets(10));
        _titledPane.setCollapsible(false);
        _titledPane.setContent(_stackPane);
        super.getChildren().add(_titledPane);
    }

    public GroupBox(String title, Node content) {
        this();
        setText(title);
        setContent(content);
    }

    public GroupBox(String title, Node content, Insets contentPadding) {
        this(title, content);
        setContentPadding(contentPadding);
    }

    public void setText(String value) {
        _titledPane.setText(value);
    }

    public void setContent(Node node) {
        _stackPane.getChildren().add(node);
    }

    public void setContentPadding(Insets value) {
        _stackPane.setPadding(value);
    }
}