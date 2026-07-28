import { test, expect } from "@playwright/test";

test.describe("Smoke tests", () => {
  test("home page loads with hero and browse CTA", async ({ page }) => {
    await page.goto("/");
    await expect(page.locator("h1")).toContainText("Framework-free frontend components");
    await expect(page.locator('a[href="/components"]').first()).toBeVisible();
  });

  test("home page links to licenses page", async ({ page }) => {
    await page.goto("/");
    await expect(page.locator('#ws-page-content-router a[href="/licenses"]')).toBeVisible();
    await page.goto("/licenses");
    await expect(page.locator("h1")).toContainText("Licenses");
  });

  test("components index loads with correct title", async ({ page }) => {
    await page.goto("/components");
    await expect(page).toHaveTitle("Component Showcase");
  });

  test("components index shows the Explore components heading", async ({ page }) => {
    await page.goto("/components");
    const heading = page.locator("h1");
    await expect(heading).toHaveText("Explore components");
  });

  test("gallery shows component cards", async ({ page }) => {
    await page.goto("/components");
    const cards = page.locator(".pf-v6-l-gallery .pf-v6-c-card");
    await expect(cards).toHaveCount(126);
  });

  test("implemented components link to demo pages", async ({ page }) => {
    await page.goto("/components");
    const aboutModalLink = page.locator('#ws-page-content-router a[href="/components/about-modal"]').first();
    await expect(aboutModalLink).toBeVisible();

    const accordionLink = page.locator('#ws-page-content-router a[href="/components/accordion"]').first();
    await expect(accordionLink).toBeVisible();

    const actionListLink = page.locator('#ws-page-content-router a[href="/components/action-list"]').first();
    await expect(actionListLink).toBeVisible();

    const alertLink = page.locator('#ws-page-content-router a[href="/components/alert"]').first();
    await expect(alertLink).toBeVisible();

    const avatarLink = page.locator('#ws-page-content-router a[href="/components/avatar"]').first();
    await expect(avatarLink).toBeVisible();

    const backToTopLink = page.locator('#ws-page-content-router a[href="/components/back-to-top"]').first();
    await expect(backToTopLink).toBeVisible();

    const backdropLink = page.locator('#ws-page-content-router a[href="/components/backdrop"]').first();
    await expect(backdropLink).toBeVisible();

    const backgroundImageLink = page.locator('#ws-page-content-router a[href="/components/background-image"]').first();
    await expect(backgroundImageLink).toBeVisible();

    const badgeLink = page.locator('#ws-page-content-router a[href="/components/badge"]').first();
    await expect(badgeLink).toBeVisible();

    const bannerLink = page.locator('#ws-page-content-router a[href="/components/banner"]').first();
    await expect(bannerLink).toBeVisible();

    const brandLink = page.locator('#ws-page-content-router a[href="/components/brand"]').first();
    await expect(brandLink).toBeVisible();

    const breadcrumbLink = page.locator('#ws-page-content-router a[href="/components/breadcrumb"]').first();
    await expect(breadcrumbLink).toBeVisible();

    const buttonLink = page.locator('#ws-page-content-router a[href="/components/button"]').first();
    await expect(buttonLink).toBeVisible();

    const calendarMonthLink = page.locator('#ws-page-content-router a[href="/components/calendar-month"]').first();
    await expect(calendarMonthLink).toBeVisible();

    const cardLink = page.locator('#ws-page-content-router a[href="/components/card"]').first();
    await expect(cardLink).toBeVisible();

    const checkboxLink = page.locator('#ws-page-content-router a[href="/components/checkbox"]').first();
    await expect(checkboxLink).toBeVisible();

    const chipLink = page.locator('#ws-page-content-router a[href="/components/chip"]').first();
    await expect(chipLink).toBeVisible();

    const clipboardCopyLink = page.locator('#ws-page-content-router a[href="/components/clipboard-copy"]').first();
    await expect(clipboardCopyLink).toBeVisible();

    const codeBlockLink = page.locator('#ws-page-content-router a[href="/components/code-block"]').first();
    await expect(codeBlockLink).toBeVisible();

    const codeEditorLink = page.locator('#ws-page-content-router a[href="/components/code-editor"]').first();
    await expect(codeEditorLink).toBeVisible();

    const contentLink = page.locator('#ws-page-content-router a[href="/components/content"]').first();
    await expect(contentLink).toBeVisible();

    const contextSelectorLink = page.locator('#ws-page-content-router a[href="/components/context-selector"]').first();
    await expect(contextSelectorLink).toBeVisible();

    const customMenusLink = page.locator('#ws-page-content-router a[href="/components/custom-menus"]').first();
    await expect(customMenusLink).toBeVisible();

    const dividerLink = page.locator('#ws-page-content-router a[href="/components/divider"]').first();
    await expect(dividerLink).toBeVisible();

    const emptyStateLink = page.locator('#ws-page-content-router a[href="/components/empty-state"]').first();
    await expect(emptyStateLink).toBeVisible();

    const expandableSectionLink = page.locator('#ws-page-content-router a[href="/components/expandable-section"]').first();
    await expect(expandableSectionLink).toBeVisible();

    const helperTextLink = page.locator('#ws-page-content-router a[href="/components/helper-text"]').first();
    await expect(helperTextLink).toBeVisible();

    const hintLink = page.locator('#ws-page-content-router a[href="/components/hint"]').first();
    await expect(hintLink).toBeVisible();

    const iconLink = page.locator('#ws-page-content-router a[href="/components/icon"]').first();
    await expect(iconLink).toBeVisible();

    const labelLink = page.locator('#ws-page-content-router a[href="/components/label"]').first();
    await expect(labelLink).toBeVisible();

    const listLink = page.locator('#ws-page-content-router a[href="/components/list"]').first();
    await expect(listLink).toBeVisible();

    const panelLink = page.locator('#ws-page-content-router a[href="/components/panel"]').first();
    await expect(panelLink).toBeVisible();

    const progressLink = page.locator('#ws-page-content-router a[href="/components/progress"]').first();
    await expect(progressLink).toBeVisible();

    const skeletonLink = page.locator('#ws-page-content-router a[href="/components/skeleton"]').first();
    await expect(skeletonLink).toBeVisible();

    const spinnerLink = page.locator('#ws-page-content-router a[href="/components/spinner"]').first();
    await expect(spinnerLink).toBeVisible();

    const timestampLink = page.locator('#ws-page-content-router a[href="/components/timestamp"]').first();
    await expect(timestampLink).toBeVisible();

    const titleLink = page.locator('#ws-page-content-router a[href="/components/title"]').first();
    await expect(titleLink).toBeVisible();

    const truncateLink = page.locator('#ws-page-content-router a[href="/components/truncate"]').first();
    await expect(truncateLink).toBeVisible();

    const dataListLink = page.locator('#ws-page-content-router a[href="/components/data-list"]').first();
    await expect(dataListLink).toBeVisible();

    const descriptionListLink = page.locator('#ws-page-content-router a[href="/components/description-list"]').first();
    await expect(descriptionListLink).toBeVisible();

    const formLink = page.locator('#ws-page-content-router a[href="/components/form"]').first();
    await expect(formLink).toBeVisible();

    const formControlLink = page.locator('#ws-page-content-router a[href="/components/form-control"]').first();
    await expect(formControlLink).toBeVisible();

    const formSelectLink = page.locator('#ws-page-content-router a[href="/components/form-select"]').first();
    await expect(formSelectLink).toBeVisible();

    const numberInputLink = page.locator('#ws-page-content-router a[href="/components/number-input"]').first();
    await expect(numberInputLink).toBeVisible();

    const radioLink = page.locator('#ws-page-content-router a[href="/components/radio"]').first();
    await expect(radioLink).toBeVisible();

    const switchLink = page.locator('#ws-page-content-router a[href="/components/switch"]').first();
    await expect(switchLink).toBeVisible();

    const textAreaLink = page.locator('#ws-page-content-router a[href="/components/text-area"]').first();
    await expect(textAreaLink).toBeVisible();

    const textInputLink = page.locator('#ws-page-content-router a[href="/components/text-input"]').first();
    await expect(textInputLink).toBeVisible();

    const textInputGroupLink = page.locator('#ws-page-content-router a[href="/components/text-input-group"]').first();
    await expect(textInputGroupLink).toBeVisible();

    const toggleGroupLink = page.locator('#ws-page-content-router a[href="/components/toggle-group"]').first();
    await expect(toggleGroupLink).toBeVisible();

    const simpleListLink = page.locator('#ws-page-content-router a[href="/components/simple-list"]').first();
    await expect(simpleListLink).toBeVisible();

    const simpleFileUploadLink = page.locator('#ws-page-content-router a[href="/components/simple-file-upload"]').first();
    await expect(simpleFileUploadLink).toBeVisible();

    const tileLink = page.locator('#ws-page-content-router a[href="/components/tile"]').first();
    await expect(tileLink).toBeVisible();

    const datePickerLink = page.locator('#ws-page-content-router a[href="/components/date-picker"]').first();
    await expect(datePickerLink).toBeVisible();

    const dropdownLink = page.locator('#ws-page-content-router a[href="/components/dropdown"]').first();
    await expect(dropdownLink).toBeVisible();

    const drawerLink = page.locator('#ws-page-content-router a[href="/components/drawer"]').first();
    await expect(drawerLink).toBeVisible();

    const inlineEditLink = page.locator('#ws-page-content-router a[href="/components/inline-edit"]').first();
    await expect(inlineEditLink).toBeVisible();

    const inputGroupLink = page.locator('#ws-page-content-router a[href="/components/input-group"]').first();
    await expect(inputGroupLink).toBeVisible();

    const jumpLinksLink = page.locator('#ws-page-content-router a[href="/components/jump-links"]').first();
    await expect(jumpLinksLink).toBeVisible();

    const menuLink = page.locator('#ws-page-content-router a[href="/components/menu"]').first();
    await expect(menuLink).toBeVisible();

    const menuToggleLink = page.locator('#ws-page-content-router a[href="/components/menu-toggle"]').first();
    await expect(menuToggleLink).toBeVisible();

    const modalLink = page.locator('#ws-page-content-router a[href="/components/modal"]').first();
    await expect(modalLink).toBeVisible();

    const notificationBadgeLink = page.locator('#ws-page-content-router a[href="/components/notification-badge"]').first();
    await expect(notificationBadgeLink).toBeVisible();

    const notificationDrawerLink = page.locator('#ws-page-content-router a[href="/components/notification-drawer"]').first();
    await expect(notificationDrawerLink).toBeVisible();

    const overflowMenuLink = page.locator('#ws-page-content-router a[href="/components/overflow-menu"]').first();
    await expect(overflowMenuLink).toBeVisible();

    const popoverLink = page.locator('#ws-page-content-router a[href="/components/popover"]').first();
    await expect(popoverLink).toBeVisible();

    const tooltipLink = page.locator('#ws-page-content-router a[href="/components/tooltip"]').first();
    await expect(tooltipLink).toBeVisible();

    const progressStepperLink = page.locator('#ws-page-content-router a[href="/components/progress-stepper"]').first();
    await expect(progressStepperLink).toBeVisible();

    const loginPageLink = page.locator('#ws-page-content-router a[href="/components/login-page"]').first();
    await expect(loginPageLink).toBeVisible();

    const mastheadLink = page.locator('#ws-page-content-router a[href="/components/masthead"]').first();
    await expect(mastheadLink).toBeVisible();

    const navigationLink = page.locator('#ws-page-content-router a[href="/components/navigation"]').first();
    await expect(navigationLink).toBeVisible();

    const pageLink = page.locator('#ws-page-content-router a[href="/components/page"]').first();
    await expect(pageLink).toBeVisible();

    const paginationLink = page.locator('#ws-page-content-router a[href="/components/pagination"]').first();
    await expect(paginationLink).toBeVisible();

    const searchInputLink = page.locator('#ws-page-content-router a[href="/components/search-input"]').first();
    await expect(searchInputLink).toBeVisible();

    const selectLink = page.locator('#ws-page-content-router a[href="/components/select"]').first();
    await expect(selectLink).toBeVisible();

    const sidebarLink = page.locator('#ws-page-content-router a[href="/components/sidebar"]').first();
    await expect(sidebarLink).toBeVisible();

    const skipToContentLink = page.locator('#ws-page-content-router a[href="/components/skip-to-content"]').first();
    await expect(skipToContentLink).toBeVisible();

    const sliderLink = page.locator('#ws-page-content-router a[href="/components/slider"]').first();
    await expect(sliderLink).toBeVisible();

    const tabsLink = page.locator('#ws-page-content-router a[href="/components/tabs"]').first();
    await expect(tabsLink).toBeVisible();

    const toolbarLink = page.locator('#ws-page-content-router a[href="/components/toolbar"]').first();
    await expect(toolbarLink).toBeVisible();

    const treeViewLink = page.locator('#ws-page-content-router a[href="/components/tree-view"]').first();
    await expect(treeViewLink).toBeVisible();

    const wizardLink = page.locator('#ws-page-content-router a[href="/components/wizard"]').first();
    await expect(wizardLink).toBeVisible();

    const applicationLauncherLink = page.locator('#ws-page-content-router a[href="/components/application-launcher"]').first();
    await expect(applicationLauncherLink).toBeVisible();

    const dateAndTimePickerLink = page.locator('#ws-page-content-router a[href="/components/date-and-time-picker"]').first();
    await expect(dateAndTimePickerLink).toBeVisible();

    const dragAndDropLink = page.locator('#ws-page-content-router a[href="/components/drag-and-drop"]').first();
    await expect(dragAndDropLink).toBeVisible();

    const dualListSelectorLink = page.locator('#ws-page-content-router a[href="/components/dual-list-selector"]').first();
    await expect(dualListSelectorLink).toBeVisible();

    const multipleFileUploadLink = page.locator('#ws-page-content-router a[href="/components/multiple-file-upload"]').first();
    await expect(multipleFileUploadLink).toBeVisible();

    const optionsMenuLink = page.locator('#ws-page-content-router a[href="/components/options-menu"]').first();
    await expect(optionsMenuLink).toBeVisible();

    const passwordGeneratorLink = page.locator('#ws-page-content-router a[href="/components/password-generator"]').first();
    await expect(passwordGeneratorLink).toBeVisible();

    const passwordStrengthLink = page.locator('#ws-page-content-router a[href="/components/password-strength"]').first();
    await expect(passwordStrengthLink).toBeVisible();

    const tableLink = page.locator('#ws-page-content-router a[href="/components/table"]').first();
    await expect(tableLink).toBeVisible();

    const timePickerLink = page.locator('#ws-page-content-router a[href="/components/time-picker"]').first();
    await expect(timePickerLink).toBeVisible();

    const rectangleSelectionLink = page.locator('#ws-page-content-router a[href="/components/rectangle-selection"]').first();
    await expect(rectangleSelectionLink).toBeVisible();

    const mapLink = page.locator('#ws-page-content-router a[href="/components/map"]').first();
    await expect(mapLink).toBeVisible();

    const documentEditorLink = page.locator('#ws-page-content-router a[href="/components/document-editor"]').first();
    await expect(documentEditorLink).toBeVisible();

    const chartLink = page.locator('#ws-page-content-router a[href="/components/chart"]').first();
    await expect(chartLink).toBeVisible();

    const liveSearchLink = page.locator('#ws-page-content-router a[href="/components/live-search"]').first();
    await expect(liveSearchLink).toBeVisible();

    const infiniteScrollLink = page.locator('#ws-page-content-router a[href="/components/infinite-scroll"]').first();
    await expect(infiniteScrollLink).toBeVisible();

    const clickToLoadLink = page.locator('#ws-page-content-router a[href="/components/click-to-load"]').first();
    await expect(clickToLoadLink).toBeVisible();

    const lazyModalLink = page.locator('#ws-page-content-router a[href="/components/lazy-modal"]').first();
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
    const searchInput = page.locator('input[placeholder="Search by name"]');
    await expect(searchInput).toBeVisible();

    await searchInput.fill("accordion");
    const visibleCards = page.locator('#main-content .pf-v6-l-gallery > div:visible');
    await expect(visibleCards).toHaveCount(1);
    await expect(page.locator('#accordion')).toBeVisible();
  });
});
