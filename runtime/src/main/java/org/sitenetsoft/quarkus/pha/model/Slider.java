package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Slider component ({@code pf-v6-c-slider}).
 *
 * <pre>
 * Slider.of("sl-basic", 50).build()
 * Slider.of("sl-temp", 20).min(-10).max(40).build()
 * Slider.of("sl-disabled", 30).disabled().build()
 * </pre>
 *
 * <p>The rail-click and WAI-ARIA keyboard Alpine wiring is generated at build
 * time, mirroring the param shell (arrows/Home/End/PageUp/PageDown). Flanking
 * compositions (action buttons, synced number inputs) stay hand-written.
 */
@TemplateData
public final class Slider {

    private final String id;
    private final long value;
    private final long min;
    private final long max;
    private final boolean disabled;
    private final String ariaLabel;

    private Slider(Builder b) {
        this.id = b.id;
        this.value = b.value;
        this.min = b.min;
        this.max = b.max;
        this.disabled = b.disabled;
        this.ariaLabel = b.ariaLabel;
    }

    public static Builder of(String id, long value) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.value = value;
        return b;
    }

    public String id() {
        return id;
    }

    public long value() {
        return value;
    }

    public String minAttr() {
        return String.valueOf(min);
    }

    public String maxAttr() {
        return String.valueOf(max);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String ariaLabel() {
        return ariaLabel != null ? ariaLabel : "Value";
    }

    public String xData() {
        return "{ val: " + value + " }";
    }

    public String railClick() {
        return "if(!$el.closest('.pf-m-disabled')){ const rect = $el.getBoundingClientRect();"
                + " val = Math.round(((($event.clientX - rect.left) / rect.width) * (" + max + " - " + min + ")) + "
                + min + "); }";
    }

    /** WAI-ARIA slider keyboard chain — declaration-free &&-guards (Alpine handler constraint). */
    public String keydown() {
        return "!$el.closest('.pf-m-disabled') && "
                + "((($event.key === 'ArrowRight' || $event.key === 'ArrowUp') && ($event.preventDefault(), val = Math.min(" + max + ", val + 1))), "
                + "(($event.key === 'ArrowLeft' || $event.key === 'ArrowDown') && ($event.preventDefault(), val = Math.max(" + min + ", val - 1))), "
                + "($event.key === 'Home' && ($event.preventDefault(), val = " + min + ")), "
                + "($event.key === 'End' && ($event.preventDefault(), val = " + max + ")), "
                + "($event.key === 'PageUp' && ($event.preventDefault(), val = Math.min(" + max + ", val + 10))), "
                + "($event.key === 'PageDown' && ($event.preventDefault(), val = Math.max(" + min + ", val - 10))))";
    }

    public String thumbStyle() {
        return "'left: ' + ((val - " + min + ") / (" + max + " - " + min + ") * 100) + '%'";
    }

    public static final class Builder {
        private String id;
        private long value;
        private long min = 0;
        private long max = 100;
        private boolean disabled;
        private String ariaLabel;

        private Builder() {
        }

        public Builder min(long min) {
            this.min = min;
            return this;
        }

        public Builder max(long max) {
            this.max = max;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Slider build() {
            return new Slider(this);
        }
    }
}
