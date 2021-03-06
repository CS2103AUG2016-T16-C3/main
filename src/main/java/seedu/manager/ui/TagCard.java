package seedu.manager.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import seedu.manager.model.task.ReadOnlyTask;
import seedu.manager.model.task.Tag;

// @@author A0148042M
/**
 * This TagCard will make UI shows a tag on the left side of task list
 */
public class TagCard extends UiPart{

    private static final String FXML = "TagListCard.fxml";

    @FXML
    private HBox tagCardPane;
    @FXML
    private Label id;
    @FXML 
    private Label tag;

    private ReadOnlyTask task;
    private Tag tagToLoad;
    private int displayedIndex;

    public TagCard(){

    }

    public static TagCard load(ReadOnlyTask task, int displayedIndex){
        TagCard card = new TagCard();
        card.task = task;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }
    
    public static TagCard load(Tag tag, int displayedIndex) {
        TagCard card = new TagCard();
        card.tagToLoad = tag;
        card.displayedIndex = displayedIndex;
        return UiPartLoader.loadUiPart(card);
    }

    @FXML
    public void initialize() {
        id.setText(displayedIndex + ". ");
        tag.setText(tagToLoad.getValue());
    }

    public HBox getLayout() {
        return tagCardPane;
    }

    @Override
    public void setNode(Node node) {
        tagCardPane = (HBox)node;
    }

    @Override
    public String getFxmlPath() {
        return FXML;
    }
}
