package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.List;

/**
 * One {@code <tbody>} group of a {@link Table}. Most tables have a single
 * body; use {@link Table.Builder#body(TableBody.Stripe)} to start additional
 * groups (e.g. continuously striped multi-tbody tables).
 */
@TemplateData
public final class TableBody {

    /** Striping applied to this body: none, odd rows, or even rows. */
    public enum Stripe { NONE, ODD, EVEN }

    private final Stripe stripe;
    private final List<TableRow> rows;

    TableBody(Stripe stripe, List<TableRow> rows) {
        this.stripe = stripe;
        this.rows = List.copyOf(rows);
    }

    public List<TableRow> rows() {
        return rows;
    }

    public boolean isStripedOdd() {
        return stripe == Stripe.ODD;
    }

    public boolean isStripedEven() {
        return stripe == Stripe.EVEN;
    }
}
