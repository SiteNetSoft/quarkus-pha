import { test, expect } from "@playwright/test";

test.describe("Smoke tests", () => {
  test("home page loads with hero and browse CTA", async ({ page }) => {
    await page.goto("/");
    await expect(page.locator("h1")).toContainText("Framework-free frontend components");
    await expect(page.locator('a[href="/components"]').first()).toBeVisible();
  });

  test("home page links to licenses page", async ({ page }) => {
    await page.goto("/");
    await expect(page.locator('a[href="/licenses"]')).toBeVisible();
    await page.goto("/licenses");
    await expect(page.locator("h1")).toContainText("Licenses");
  });

  test("components index loads with correct title", async ({ page }) => {
    await page.goto("/components");
    await expect(page).toHaveTitle("Component Showcase");
  });

  test("homepage displays All components heading", async ({ page }) => {
    await page.goto("/components");
    const heading = page.locator("h1");
    await expect(heading).toHaveText("All components");
  });

  test("gallery shows component cards", async ({ page }) => {
    await page.goto("/components");
    const cards = page.locator(".pf-v6-l-gallery .pf-v6-c-card");
    await expect(cards).toHaveCount(126);
  });

  test("implemented components link to demo pages", async ({ page }) => {
    await page.goto("/components");
    const aboutModalLink = page.locator('a[href="/components/about-modal"]');
    await expect(aboutModalLink).toBeVisible();

    const accordionLink = page.locator('a[href="/components/accordion"]');
    await expect(accordionLink).toBeVisible();

    const actionListLink = page.locator('a[href="/components/action-list"]');
    await expect(actionListLink).toBeVisible();

    const alertLink = page.locator('a[href="/components/alert"]');
    await expect(alertLink).toBeVisible();

    const avatarLink = page.locator('a[href="/components/avatar"]');
    await expect(avatarLink).toBeVisible();

    const backToTopLink = page.locator('a[href="/components/back-to-top"]');
    await expect(backToTopLink).toBeVisible();

    const backdropLink = page.locator('a[href="/components/backdrop"]');
    await expect(backdropLink).toBeVisible();

    const backgroundImageLink = page.locator('a[href="/components/background-image"]');
    await expect(backgroundImageLink).toBeVisible();

    const badgeLink = page.locator('a[href="/components/badge"]');
    await expect(badgeLink).toBeVisible();

    const bannerLink = page.locator('a[href="/components/banner"]');
    await expect(bannerLink).toBeVisible();

    const brandLink = page.locator('a[href="/components/brand"]');
    await expect(brandLink).toBeVisible();

    const breadcrumbLink = page.locator('a[href="/components/breadcrumb"]');
    await expect(breadcrumbLink).toBeVisible();

    const buttonLink = page.locator('a[href="/components/button"]');
    await expect(buttonLink).toBeVisible();

    const calendarMonthLink = page.locator('a[href="/components/calendar-month"]');
    await expect(calendarMonthLink).toBeVisible();

    const cardLink = page.locator('a[href="/components/card"]');
    await expect(cardLink).toBeVisible();

    const checkboxLink = page.locator('a[href="/components/checkbox"]');
    await expect(checkboxLink).toBeVisible();

    const chipLink = page.locator('a[href="/components/chip"]');
    await expect(chipLink).toBeVisible();

    const clipboardCopyLink = page.locator('a[href="/components/clipboard-copy"]');
    await expect(clipboardCopyLink).toBeVisible();

    const codeBlockLink = page.locator('a[href="/components/code-block"]');
    await expect(codeBlockLink).toBeVisible();

    const codeEditorLink = page.locator('a[href="/components/code-editor"]');
    await expect(codeEditorLink).toBeVisible();

    const contentLink = page.locator('a[href="/components/content"]');
    await expect(contentLink).toBeVisible();

    const contextSelectorLink = page.locator('a[href="/components/context-selector"]');
    await expect(contextSelectorLink).toBeVisible();

    const customMenusLink = page.locator('a[href="/components/custom-menus"]');
    await expect(customMenusLink).toBeVisible();

    const dividerLink = page.locator('a[href="/components/divider"]');
    await expect(dividerLink).toBeVisible();

    const emptyStateLink = page.locator('a[href="/components/empty-state"]');
    await expect(emptyStateLink).toBeVisible();

    const expandableSectionLink = page.locator('a[href="/components/expandable-section"]');
    await expect(expandableSectionLink).toBeVisible();

    const helperTextLink = page.locator('a[href="/components/helper-text"]');
    await expect(helperTextLink).toBeVisible();

    const hintLink = page.locator('a[href="/components/hint"]');
    await expect(hintLink).toBeVisible();

    const iconLink = page.locator('a[href="/components/icon"]');
    await expect(iconLink).toBeVisible();

    const labelLink = page.locator('a[href="/components/label"]');
    await expect(labelLink).toBeVisible();

    const listLink = page.locator('a[href="/components/list"]');
    await expect(listLink).toBeVisible();

    const panelLink = page.locator('a[href="/components/panel"]');
    await expect(panelLink).toBeVisible();

    const progressLink = page.locator('a[href="/components/progress"]');
    await expect(progressLink).toBeVisible();

    const skeletonLink = page.locator('a[href="/components/skeleton"]');
    await expect(skeletonLink).toBeVisible();

    const spinnerLink = page.locator('a[href="/components/spinner"]');
    await expect(spinnerLink).toBeVisible();

    const timestampLink = page.locator('a[href="/components/timestamp"]');
    await expect(timestampLink).toBeVisible();

    const titleLink = page.locator('a[href="/components/title"]');
    await expect(titleLink).toBeVisible();

    const truncateLink = page.locator('a[href="/components/truncate"]');
    await expect(truncateLink).toBeVisible();

    const dataListLink = page.locator('a[href="/components/data-list"]');
    await expect(dataListLink).toBeVisible();

    const descriptionListLink = page.locator('a[href="/components/description-list"]');
    await expect(descriptionListLink).toBeVisible();

    const formLink = page.locator('a[href="/components/form"]');
    await expect(formLink).toBeVisible();

    const formControlLink = page.locator('a[href="/components/form-control"]');
    await expect(formControlLink).toBeVisible();

    const formSelectLink = page.locator('a[href="/components/form-select"]');
    await expect(formSelectLink).toBeVisible();

    const numberInputLink = page.locator('a[href="/components/number-input"]');
    await expect(numberInputLink).toBeVisible();

    const radioLink = page.locator('a[href="/components/radio"]');
    await expect(radioLink).toBeVisible();

    const switchLink = page.locator('a[href="/components/switch"]');
    await expect(switchLink).toBeVisible();

    const textAreaLink = page.locator('a[href="/components/text-area"]');
    await expect(textAreaLink).toBeVisible();

    const textInputLink = page.locator('a[href="/components/text-input"]');
    await expect(textInputLink).toBeVisible();

    const textInputGroupLink = page.locator('a[href="/components/text-input-group"]');
    await expect(textInputGroupLink).toBeVisible();

    const toggleGroupLink = page.locator('a[href="/components/toggle-group"]');
    await expect(toggleGroupLink).toBeVisible();

    const simpleListLink = page.locator('a[href="/components/simple-list"]');
    await expect(simpleListLink).toBeVisible();

    const simpleFileUploadLink = page.locator('a[href="/components/simple-file-upload"]');
    await expect(simpleFileUploadLink).toBeVisible();

    const tileLink = page.locator('a[href="/components/tile"]');
    await expect(tileLink).toBeVisible();

    const datePickerLink = page.locator('a[href="/components/date-picker"]');
    await expect(datePickerLink).toBeVisible();

    const dropdownLink = page.locator('a[href="/components/dropdown"]');
    await expect(dropdownLink).toBeVisible();

    const drawerLink = page.locator('a[href="/components/drawer"]');
    await expect(drawerLink).toBeVisible();

    const inlineEditLink = page.locator('a[href="/components/inline-edit"]');
    await expect(inlineEditLink).toBeVisible();

    const inputGroupLink = page.locator('a[href="/components/input-group"]');
    await expect(inputGroupLink).toBeVisible();

    const jumpLinksLink = page.locator('a[href="/components/jump-links"]');
    await expect(jumpLinksLink).toBeVisible();

    const menuLink = page.locator('a[href="/components/menu"]');
    await expect(menuLink).toBeVisible();

    const menuToggleLink = page.locator('a[href="/components/menu-toggle"]');
    await expect(menuToggleLink).toBeVisible();

    const modalLink = page.locator('a[href="/components/modal"]');
    await expect(modalLink).toBeVisible();

    const notificationBadgeLink = page.locator('a[href="/components/notification-badge"]');
    await expect(notificationBadgeLink).toBeVisible();

    const notificationDrawerLink = page.locator('a[href="/components/notification-drawer"]');
    await expect(notificationDrawerLink).toBeVisible();

    const overflowMenuLink = page.locator('a[href="/components/overflow-menu"]');
    await expect(overflowMenuLink).toBeVisible();

    const popoverLink = page.locator('a[href="/components/popover"]');
    await expect(popoverLink).toBeVisible();

    const tooltipLink = page.locator('a[href="/components/tooltip"]');
    await expect(tooltipLink).toBeVisible();

    const progressStepperLink = page.locator('a[href="/components/progress-stepper"]');
    await expect(progressStepperLink).toBeVisible();

    const loginPageLink = page.locator('a[href="/components/login-page"]');
    await expect(loginPageLink).toBeVisible();

    const mastheadLink = page.locator('a[href="/components/masthead"]');
    await expect(mastheadLink).toBeVisible();

    const navigationLink = page.locator('a[href="/components/navigation"]');
    await expect(navigationLink).toBeVisible();

    const pageLink = page.locator('a[href="/components/page"]');
    await expect(pageLink).toBeVisible();

    const paginationLink = page.locator('a[href="/components/pagination"]');
    await expect(paginationLink).toBeVisible();

    const searchInputLink = page.locator('a[href="/components/search-input"]');
    await expect(searchInputLink).toBeVisible();

    const selectLink = page.locator('a[href="/components/select"]');
    await expect(selectLink).toBeVisible();

    const sidebarLink = page.locator('a[href="/components/sidebar"]');
    await expect(sidebarLink).toBeVisible();

    const skipToContentLink = page.locator('a[href="/components/skip-to-content"]');
    await expect(skipToContentLink).toBeVisible();

    const sliderLink = page.locator('a[href="/components/slider"]');
    await expect(sliderLink).toBeVisible();

    const tabsLink = page.locator('a[href="/components/tabs"]');
    await expect(tabsLink).toBeVisible();

    const toolbarLink = page.locator('a[href="/components/toolbar"]');
    await expect(toolbarLink).toBeVisible();

    const treeViewLink = page.locator('a[href="/components/tree-view"]');
    await expect(treeViewLink).toBeVisible();

    const wizardLink = page.locator('a[href="/components/wizard"]');
    await expect(wizardLink).toBeVisible();

    const applicationLauncherLink = page.locator('a[href="/components/application-launcher"]');
    await expect(applicationLauncherLink).toBeVisible();

    const dateAndTimePickerLink = page.locator('a[href="/components/date-and-time-picker"]');
    await expect(dateAndTimePickerLink).toBeVisible();

    const dragAndDropLink = page.locator('a[href="/components/drag-and-drop"]');
    await expect(dragAndDropLink).toBeVisible();

    const dualListSelectorLink = page.locator('a[href="/components/dual-list-selector"]');
    await expect(dualListSelectorLink).toBeVisible();

    const multipleFileUploadLink = page.locator('a[href="/components/multiple-file-upload"]');
    await expect(multipleFileUploadLink).toBeVisible();

    const optionsMenuLink = page.locator('a[href="/components/options-menu"]');
    await expect(optionsMenuLink).toBeVisible();

    const passwordGeneratorLink = page.locator('a[href="/components/password-generator"]');
    await expect(passwordGeneratorLink).toBeVisible();

    const passwordStrengthLink = page.locator('a[href="/components/password-strength"]');
    await expect(passwordStrengthLink).toBeVisible();

    const tableLink = page.locator('a[href="/components/table"]');
    await expect(tableLink).toBeVisible();

    const timePickerLink = page.locator('a[href="/components/time-picker"]');
    await expect(timePickerLink).toBeVisible();

    const rectangleSelectionLink = page.locator('a[href="/components/rectangle-selection"]');
    await expect(rectangleSelectionLink).toBeVisible();

    const mapLink = page.locator('a[href="/components/map"]');
    await expect(mapLink).toBeVisible();

    const documentEditorLink = page.locator('a[href="/components/document-editor"]');
    await expect(documentEditorLink).toBeVisible();

    const chartLink = page.locator('a[href="/components/chart"]');
    await expect(chartLink).toBeVisible();

    const liveSearchLink = page.locator('a[href="/components/live-search"]');
    await expect(liveSearchLink).toBeVisible();

    const infiniteScrollLink = page.locator('a[href="/components/infinite-scroll"]');
    await expect(infiniteScrollLink).toBeVisible();

    const clickToLoadLink = page.locator('a[href="/components/click-to-load"]');
    await expect(clickToLoadLink).toBeVisible();

    const lazyModalLink = page.locator('a[href="/components/lazy-modal"]');
    await expect(lazyModalLink).toBeVisible();
  });

  test("demo page links are visible on index", async ({ page }) => {
    await page.goto("/components");
    await expect(page.locator('#demo-dashboard')).toBeVisible();
    await expect(page.locator('#demo-data-management')).toBeVisible();
    await expect(page.locator('#demo-settings')).toBeVisible();
    await expect(page.locator('#demo-landing')).toBeVisible();
  });

  test("search input filters component cards", async ({ page }) => {
    await page.goto("/components");
    const searchInput = page.locator('input[placeholder="Search components by name"]');
    await expect(searchInput).toBeVisible();

    await searchInput.fill("accordion");
    const visibleCards = page.locator('.pf-v6-l-gallery > div:visible');
    await expect(visibleCards).toHaveCount(1);
    await expect(page.locator('#accordion')).toBeVisible();
  });
});
