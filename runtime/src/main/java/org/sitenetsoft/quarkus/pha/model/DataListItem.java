package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One row of a {@link DataList}. Build with {@link DataList#item(Object...)}
 * (varargs of Strings and/or {@link DataListCell}s) and refine with the
 * {@code withX} copies: expandable detail content, a leading checkbox,
 * trailing action areas and a selection key for clickable lists.
 */
@TemplateData
public final class DataListItem {

    private List<DataListCell> cells = List.of();
    private String detail;
    private DataList detailList;
    private boolean startOpen;
    private String toggleAriaLabel;
    private String checkId;
    private boolean checkbox;
    private String key;
    private List<DataListItemAction> actionAreas = List.of();
    // resolved by DataList.build()
    private int number;

    private DataListItem() {
    }

    private DataListItem copy() {
        DataListItem i = new DataListItem();
        i.cells = cells;
        i.detail = detail;
        i.detailList = detailList;
        i.startOpen = startOpen;
        i.toggleAriaLabel = toggleAriaLabel;
        i.checkId = checkId;
        i.checkbox = checkbox;
        i.key = key;
        i.actionAreas = actionAreas;
        i.number = number;
        return i;
    }

    static DataListItem of(Object... cells) {
        List<DataListCell> list = new ArrayList<>();
        for (Object c : cells) {
            if (c instanceof DataListCell cell) {
                list.add(cell);
            } else if (c instanceof String s) {
                list.add(DataListCell.text(s));
            } else {
                throw new IllegalArgumentException(
                        "Item cells must be String or DataListCell, got: " + (c == null ? "null" : c.getClass()));
            }
        }
        DataListItem i = new DataListItem();
        i.cells = List.copyOf(list);
        return i;
    }

    /** Copy with expandable detail text revealed by the row toggle. */
    public DataListItem expandsTo(String detail) {
        DataListItem i = copy();
        i.detail = Objects.requireNonNull(detail, "detail");
        return i;
    }

    /** Copy whose expandable content hosts a nested data list. */
    public DataListItem expandsToList(DataList detailList) {
        DataListItem i = copy();
        i.detailList = Objects.requireNonNull(detailList, "detailList");
        return i;
    }

    /** Copy whose expandable content starts revealed. */
    public DataListItem expanded() {
        DataListItem i = copy();
        i.startOpen = true;
        return i;
    }

    /** Copy with an explicit toggle aria-label (default derives from the first text cell). */
    public DataListItem toggleAriaLabel(String ariaLabel) {
        DataListItem i = copy();
        i.toggleAriaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return i;
    }

    /** Copy with a leading selection checkbox carrying the given input id. */
    public DataListItem checkbox(String checkId) {
        DataListItem i = copy();
        i.checkbox = true;
        i.checkId = Objects.requireNonNull(checkId, "checkId");
        return i;
    }

    /**
     * Copy identified by a selection key for clickable lists. The key is
     * emitted verbatim as a JS literal (e.g. {@code "1"} renders
     * {@code sel === 1}; pass {@code "'a'"} for string keys).
     */
    public DataListItem key(String key) {
        DataListItem i = copy();
        i.key = Objects.requireNonNull(key, "key");
        return i;
    }

    /** Copy with a trailing button action area. */
    public DataListItem action(TableAction... actions) {
        return withArea(new DataListItemAction(false, null, List.of(actions), null));
    }

    /** Copy with a trailing kebab-menu action area. */
    public DataListItem kebab(String ariaLabel, TableAction... actions) {
        return withArea(new DataListItemAction(true, Objects.requireNonNull(ariaLabel, "ariaLabel"),
                List.of(actions), null));
    }

    /**
     * Copy with a responsive action pair: a kebab menu below the lg
     * breakpoint and the same actions as buttons at lg and up.
     */
    public DataListItem responsiveActions(String kebabAriaLabel, TableAction... actions) {
        DataListItem i = withArea(new DataListItemAction(true,
                Objects.requireNonNull(kebabAriaLabel, "kebabAriaLabel"), List.of(actions), "pf-m-hidden-on-lg"));
        return i.withArea(new DataListItemAction(false, null, List.of(actions), "pf-m-hidden pf-m-visible-on-lg"));
    }

    private DataListItem withArea(DataListItemAction area) {
        DataListItem i = copy();
        List<DataListItemAction> a = new ArrayList<>(actionAreas);
        a.add(area);
        i.actionAreas = List.copyOf(a);
        return i;
    }

    /** Copy with its 1-based number; called from DataList.build(). */
    DataListItem resolved(int number) {
        DataListItem i = copy();
        i.number = number;
        return i;
    }

    public List<DataListCell> cells() {
        return cells;
    }

    public boolean isExpandable() {
        return detail != null || detailList != null;
    }

    public String detail() {
        return detail;
    }

    /** Nested data list rendered in the expandable content, or null. */
    public DataList detailList() {
        return detailList;
    }

    public boolean isStartOpen() {
        return startOpen;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public String checkId() {
        return checkId;
    }

    /** Selection key, emitted verbatim into the Alpine expressions. */
    public String key() {
        return key;
    }

    public List<DataListItemAction> actionAreas() {
        return actionAreas;
    }

    public int number() {
        return number;
    }

    /** aria-label for the expand toggle button. */
    public String toggleAriaLabel() {
        if (toggleAriaLabel != null) {
            return toggleAriaLabel;
        }
        String subject = cells.stream()
                .filter(c -> c.text() != null)
                .map(DataListCell::text)
                .findFirst().orElse("row");
        return "Toggle details for " + subject;
    }
}
