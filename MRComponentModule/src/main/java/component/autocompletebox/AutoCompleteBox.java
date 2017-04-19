package component.autocompletebox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

/**
 * Created by Sorumi on 17/4/19.
 */
public class AutoCompleteBox<T> extends ComboBox<T> {

    private boolean moveCaretToPos = false;

    private int caretPos;

    private int maxRowCount = 5;

    private ObservableList<T> data;

    final KeyCombination keyCombinationCtrlA = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN);

    public AutoCompleteBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AutoCompleteBox.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);


        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setData(ObservableList<T> data) {
        this.data = data;


        setEditable(true);
        setOnKeyPressed(event -> {
            hide();
        });
        setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.UP) {
                caretPos = -1;
                moveCaret(getEditor().getText().length());
                return;
            } else if (event.getCode() == KeyCode.DOWN) {
                if (!isShowing()) {
                    show();
                }
                caretPos = -1;
                moveCaret(getEditor().getText().length());
                return;
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                moveCaretToPos = true;
                caretPos = getEditor().getCaretPosition();
            } else if (event.getCode() == KeyCode.DELETE) {
                moveCaretToPos = true;
                caretPos = getEditor().getCaretPosition();
            }

            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                    || event.isControlDown() || event.getCode() == KeyCode.HOME
                    || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                return;
            }


            ObservableList list = FXCollections.observableArrayList();
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).toString().toLowerCase().startsWith(getEditor().getText().toLowerCase())) {
                    list.add(data.get(i));
                }
            }
            String t = getEditor().getText();

            setItems(list);
            setVisibleRowCount(Math.min(maxRowCount, list.size()));
            getEditor().setText(t);
            if (!moveCaretToPos) {
                caretPos = -1;
            }
            moveCaret(t.length());
            if (!list.isEmpty() && !t.equals("")) {
                show();

            } else {
                hide();
            }
        });
    }

    private void moveCaret(int textLength) {
        if (caretPos == -1) {
            getEditor().positionCaret(textLength);
        } else {
            getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

}
