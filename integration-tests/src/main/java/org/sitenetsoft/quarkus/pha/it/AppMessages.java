package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.i18n.Message;
import io.quarkus.qute.i18n.MessageBundle;

/**
 * The default ("msg") Qute message bundle. Method names are the keys referenced
 * in templates as {@code {msg:key}}; the {@code @Message} value is the English
 * (default-locale) text. Localized overrides live in {@code messages/msg_<lang>.properties}
 * (see msg_fr.properties), keyed by the same method names.
 *
 * <p>The bundle namespace is registered at build time from this interface, which
 * is why an explicit interface is used rather than a properties-only default.
 */
@MessageBundle
public interface AppMessages {

    @Message("Skip to content")
    String skip_to_content();

    @Message("Internationalization")
    String i18n_title();

    @Message("This page's text comes from Qute message bundles. The active locale is chosen per request, so the same template renders in any language.")
    String i18n_intro();

    @Message("Hello! This text is localized.")
    String i18n_greeting();

    @Message("Current language: English")
    String i18n_current_language();

    @Message("Profile")
    String i18n_profile_heading();

    @Message("Save")
    String i18n_save();

    @Message("Cancel")
    String i18n_cancel();

    @Message("Language")
    String i18n_language_label();

    @Message("English")
    String i18n_lang_english();

    @Message("Français")
    String i18n_lang_french();
}
