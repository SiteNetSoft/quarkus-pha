import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

String avatarImg = "data:image/svg+xml,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%2036%2036'%3E%3Ccircle%20cx='18'%20cy='18'%20r='18'%20fill='%23d2d2d2'/%3E%3Ccircle%20cx='18'%20cy='12'%20r='7'%20fill='%23fff'/%3E%3Cpath%20d='M4%2032c0-8%206-12%2014-12s14%204%2014%2012'%20fill='%23fff'/%3E%3C/svg%3E";

List<Avatar> avatars = List.of(
        Avatar.of(avatarImg, "Basic avatar with image").id("avatar-basic").build(),
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

// Template side, with the data in scope:
// {#for a in avatars}{#include components/data-display/avatar avatar=a /}{/for}
