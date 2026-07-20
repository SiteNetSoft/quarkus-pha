package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Avatar;

import java.util.List;

/**
 * Demo data for the avatar examples — the examples on /components/avatar are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/avatar/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class AvatarDemoData {

    public static String demoAvatarImg = "data:image/svg+xml,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%2036%2036'%3E%3Ccircle%20cx='18'%20cy='18'%20r='18'%20fill='%23d2d2d2'/%3E%3Ccircle%20cx='18'%20cy='12'%20r='7'%20fill='%23fff'/%3E%3Cpath%20d='M4%2032c0-8%206-12%2014-12s14%204%2014%2012'%20fill='%23fff'/%3E%3C/svg%3E";

    public static List<Avatar> demoAvatarsBasic = List.of(
            Avatar.of(demoAvatarImg, "Basic avatar with image").id("avatar-basic").build(),
            Avatar.svg("""
                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 48 48" fill="none">
                      <g>
                        <rect width="48" height="48" fill="#147878"></rect>
                        <rect x="36" y="42" width="6" height="6" fill="#004D4D"></rect>
                        <rect x="6" y="42" width="6" height="6" fill="#004D4D"></rect>
                        <rect x="12" y="36" width="24" height="6" fill="#004D4D"></rect>
                        <rect x="18" y="30" width="12" height="6" fill="#004D4D"></rect>
                        <rect width="6" height="24" transform="matrix(-1 0 0 1 48 12)" fill="#004D4D"></rect>
                        <rect width="6" height="6" transform="matrix(-1 0 0 1 42 30)" fill="#004D4D"></rect>
                        <rect width="6" height="6" transform="matrix(-1 0 0 1 36 24)" fill="#004D4D"></rect>
                        <rect width="6" height="6" transform="matrix(-1 0 0 1 42 18)" fill="#004D4D"></rect>
                        <rect width="6" height="6" transform="matrix(-1 0 0 1 36 12)" fill="#004D4D"></rect>
                        <rect width="6" height="6" transform="matrix(-1 0 0 1 42 6)" fill="#004D4D"></rect>
                        <rect x="18" width="12" height="12" fill="#004D4D"></rect>
                        <rect x="6" y="30" width="6" height="6" fill="#004D4D"></rect>
                        <rect x="12" y="24" width="6" height="6" fill="#004D4D"></rect>
                        <rect x="6" y="18" width="6" height="6" fill="#004D4D"></rect>
                        <rect x="12" y="12" width="6" height="6" fill="#004D4D"></rect>
                        <rect x="6" y="6" width="6" height="6" fill="#004D4D"></rect>
                        <rect y="12" width="6" height="24" fill="#004D4D"></rect>
                      </g>
                    </svg>""", "Basic avatar with custom content").id("avatar-basic-custom").build());

    public static Avatar demoAvatarBordered = Avatar.svg("""
            <svg class="pf-v6-svg" fill="currentColor" viewBox="0 0 32 32" aria-hidden="true" role="img" width="1em" height="1em">
              <path
                d="M28 12H17V9h1.75C19.44 9 20 8.44 20 7.75v-4.5C20 2.56 19.44 2 18.75 2h-5.5C12.56 2 12 2.56 12 3.25v4.5c0 .69.56 1.25 1.25 1.25H15v3H4c-1.654 0-3 1.346-3 3v12c0 1.654 1.346 3 3 3h24c1.654 0 3-1.346 3-3V15c0-1.654-1.346-3-3-3ZM14 4h4v3h-4V4Zm15 23a1 1 0 0 1-1 1H4c-.551 0-1-.448-1-1V15c0-.551.449-1 1-1h24c.552 0 1 .449 1 1v12ZM8.75 18v2a.75.75 0 0 1-.75.75H6a.75.75 0 0 1-.75-.75v-2a.75.75 0 0 1 .75-.75h2a.75.75 0 0 1 .75.75Zm18 0v2a.75.75 0 0 1-.75.75h-2a.75.75 0 0 1-.75-.75v-2a.75.75 0 0 1 .75-.75h2a.75.75 0 0 1 .75.75Zm-7.05 4.475a.873.873 0 0 1-.175 1.224c-1.023.77-2.242 1.176-3.525 1.176s-2.502-.406-3.526-1.176a.874.874 0 1 1 1.05-1.398c1.437 1.078 3.513 1.078 4.95 0a.871.871 0 0 1 1.225.174Z"
              ></path>
            </svg>""", "Bordered avatar with chatbot icon").id("avatar-bordered").bordered().build();

    public static List<Avatar> demoAvatarsBorderedSizes = List.of(
            Avatar.of(demoAvatarImg, "Small bordered avatar").id("avatar-bordered-sm").size("sm").bordered().build(),
            Avatar.of(demoAvatarImg, "Medium bordered avatar").id("avatar-bordered-md").size("md").bordered().build(),
            Avatar.of(demoAvatarImg, "Large bordered avatar").id("avatar-bordered-lg").size("lg").bordered().build(),
            Avatar.of(demoAvatarImg, "Extra large bordered avatar").id("avatar-bordered-xl").size("xl").bordered().build());

    public static List<Avatar> demoAvatarsColors = List.of(
            Avatar.silhouette("Red avatar").color("red").build(),
            Avatar.silhouette("Orange-red avatar").color("orangered").build(),
            Avatar.silhouette("Orange avatar").color("orange").build(),
            Avatar.silhouette("Yellow avatar").color("yellow").build(),
            Avatar.silhouette("Green avatar").color("green").build(),
            Avatar.silhouette("Teal avatar").color("teal").build(),
            Avatar.silhouette("Blue avatar").color("blue").build(),
            Avatar.silhouette("Purple avatar").color("purple").build(),
            Avatar.silhouette("Gray avatar").color("gray").build());

    public static List<Avatar> demoAvatarsInitials = List.of(
            Avatar.initials("C", "Avatar with initial C").id("avatar-initials").bordered().build(),
            Avatar.initials("C", "Red avatar with initial C").color("red").build(),
            Avatar.initials("C", "Orange-red avatar with initial C").color("orangered").build(),
            Avatar.initials("C", "Orange avatar with initial C").color("orange").build(),
            Avatar.initials("C", "Yellow avatar with initial C").color("yellow").build(),
            Avatar.initials("C", "Green avatar with initial C").color("green").build(),
            Avatar.initials("C", "Teal avatar with initial C").color("teal").build(),
            Avatar.initials("C", "Blue avatar with initial C").color("blue").build(),
            Avatar.initials("C", "Purple avatar with initial C").color("purple").build(),
            Avatar.initials("C", "Gray avatar with initial C").color("gray").build());

    public static List<Avatar> demoAvatarsSizes = List.of(
            Avatar.initials("C", "Small red avatar with initial C").id("avatar-sm").color("red").size("sm").build(),
            Avatar.initials("C", "Medium red avatar with initial C").id("avatar-md").color("red").size("md").build(),
            Avatar.initials("C", "Large red avatar with initial C").id("avatar-lg").color("red").size("lg").build(),
            Avatar.initials("C", "Extra large red avatar with initial C").id("avatar-xl").color("red").size("xl").build());
}
