package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

public class NoSelectionModel<T> extends MultipleSelectionModel<T> {

    @Override
    public ObservableList<Integer> getSelectedIndices() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public ObservableList<T> getSelectedItems() {
        return FXCollections.emptyObservableList();
    }

    @Override
    public void selectIndices(int index, int... indices) {
    	//Intentionally blank override
    }

    @Override
    public void selectAll() {
    	//Intentionally blank override
    }

    @Override
    public void selectFirst() {
    	//Intentionally blank override
    }

    @Override
    public void selectLast() {
    	//Intentionally blank override
    }

    @Override
    public void clearAndSelect(int index) {
    	//Intentionally blank override
    }

    @Override
    public void select(int index) {
    	//Intentionally blank override
    }

    @Override
    public void select(T obj) {
    	//Intentionally blank override
    }

    @Override
    public void clearSelection(int index) {
    	//Intentionally blank override
    }

    @Override
    public void clearSelection() {
    	//Intentionally blank override
    }

    @Override
    public boolean isSelected(int index) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public void selectPrevious() {
    	//Intentionally blank override
    }

    @Override
    public void selectNext() {
    	//Intentionally blank override
    }
}
