package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One action in a table row's action cell: an inline button or an item inside
 * a kebab menu. Create via the static factories; used with
 * {@link TableCell#kebab} and {@link TableCell#actions}.
 */
@TemplateData
public final class TableAction {

    public enum Variant { LINK, PRIMARY, SECONDARY, DANGER }

    private final String label;
    private final Variant variant;

    private TableAction(String label, Variant variant) {
        this.label = Objects.requireNonNull(label, "label");
        this.variant = variant;
    }

    /** Inline link button ({@code pf-m-link pf-m-inline}). */
    public static TableAction link(String label) {
        return new TableAction(label, Variant.LINK);
    }

    /** Primary button (overflow menus). */
    public static TableAction primary(String label) {
        return new TableAction(label, Variant.PRIMARY);
    }

    /** Inline secondary button ({@code pf-m-secondary pf-m-inline}). */
    public static TableAction secondary(String label) {
        return new TableAction(label, Variant.SECONDARY);
    }

    /** Inline danger link button ({@code pf-m-link pf-m-inline pf-m-danger}). */
    public static TableAction danger(String label) {
        return new TableAction(label, Variant.DANGER);
    }

    public String label() {
        return label;
    }

    public boolean isLink() {
        return variant == Variant.LINK;
    }

    public boolean isSecondary() {
        return variant == Variant.SECONDARY;
    }

    public boolean isDanger() {
        return variant == Variant.DANGER;
    }

    /** Modifier classes for the inline button, matching PF's action-cell patterns. */
    public String buttonClasses() {
        switch (variant) {
            case PRIMARY: return "pf-m-primary pf-m-inline";
            case SECONDARY: return "pf-m-secondary pf-m-inline";
            case DANGER: return "pf-m-link pf-m-inline pf-m-danger";
            default: return "pf-m-link pf-m-inline";
        }
    }

    /** Modifier class for a full (non-inline) button in an overflow-menu group. */
    public String overflowButtonClasses() {
        switch (variant) {
            case PRIMARY: return "pf-m-primary";
            case SECONDARY: return "pf-m-secondary";
            case DANGER: return "pf-m-danger";
            default: return "pf-m-link";
        }
    }
}
