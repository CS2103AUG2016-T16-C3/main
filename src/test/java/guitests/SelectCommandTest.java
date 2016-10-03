package guitests;

import org.junit.Test;
import seedu.address.model.person.ReadOnlyTask;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest extends AddressBookGuiTest {


    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(10); //invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); //first person in the list
        int personCount = td.getTypicalTasks().length;
        assertSelectionSuccess(personCount); //last person in the list
        int middleIndex = personCount / 2;
        assertSelectionSuccess(middleIndex); //a person in the middle of the list

        assertSelectionInvalid(personCount + 1); //invalid index
        assertTaskSelected(middleIndex); //assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList(){
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The person index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: "+index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(personListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = personListPanel.getSelectedTasks().get(0);
        assertEquals(personListPanel.getTask(index-1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(personListPanel.getSelectedTasks().size(), 0);
    }

}
