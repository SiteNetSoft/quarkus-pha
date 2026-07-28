package org.sitenetsoft.quarkus.pha.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The JSON view-model contract, swept across every model class:
 *  - every model must RESOLVE a Jackson deserializer (a conflicting-setter or missing-creator
 *    definition fails here even when "{}" is rejected later for missing required fields);
 *  - every Builder field must be bindable from JSON (a public 1-arg method of the same name),
 *    unless exempted below as deliberate Java-only sugar;
 *  - every non-builder model must expose a @JsonCreator.
 */
class JsonModelContractTest {

    private static final List<Class<?>> MODELS = List.of(
            org.sitenetsoft.quarkus.pha.model.AboutModal.class,
            org.sitenetsoft.quarkus.pha.model.Accordion.class,
            org.sitenetsoft.quarkus.pha.model.AccordionItem.class,
            org.sitenetsoft.quarkus.pha.model.ActionList.class,
            org.sitenetsoft.quarkus.pha.model.Alert.class,
            org.sitenetsoft.quarkus.pha.model.Avatar.class,
            org.sitenetsoft.quarkus.pha.model.BackToTop.class,
            org.sitenetsoft.quarkus.pha.model.BackgroundImage.class,
            org.sitenetsoft.quarkus.pha.model.Badge.class,
            org.sitenetsoft.quarkus.pha.model.Banner.class,
            org.sitenetsoft.quarkus.pha.model.Brand.class,
            org.sitenetsoft.quarkus.pha.model.Breadcrumb.class,
            org.sitenetsoft.quarkus.pha.model.BreadcrumbItem.class,
            org.sitenetsoft.quarkus.pha.model.Button.class,
            org.sitenetsoft.quarkus.pha.model.CalendarMonth.class,
            org.sitenetsoft.quarkus.pha.model.Card.class,
            org.sitenetsoft.quarkus.pha.model.CatalogItemHeader.class,
            org.sitenetsoft.quarkus.pha.model.CatalogTile.class,
            org.sitenetsoft.quarkus.pha.model.Check.class,
            org.sitenetsoft.quarkus.pha.model.ClipboardCopy.class,
            org.sitenetsoft.quarkus.pha.model.CodeBlock.class,
            org.sitenetsoft.quarkus.pha.model.Compass.class,
            org.sitenetsoft.quarkus.pha.model.Content.class,
            org.sitenetsoft.quarkus.pha.model.DataList.class,
            org.sitenetsoft.quarkus.pha.model.DataListCell.class,
            org.sitenetsoft.quarkus.pha.model.DataListItem.class,
            org.sitenetsoft.quarkus.pha.model.DataListItemAction.class,
            org.sitenetsoft.quarkus.pha.model.DescriptionList.class,
            org.sitenetsoft.quarkus.pha.model.DescriptionListGroup.class,
            org.sitenetsoft.quarkus.pha.model.Divider.class,
            org.sitenetsoft.quarkus.pha.model.Drawer.class,
            org.sitenetsoft.quarkus.pha.model.EmptyState.class,
            org.sitenetsoft.quarkus.pha.model.ExpandableSection.class,
            org.sitenetsoft.quarkus.pha.model.FilterSidePanel.class,
            org.sitenetsoft.quarkus.pha.model.Form.class,
            org.sitenetsoft.quarkus.pha.model.FormSelect.class,
            org.sitenetsoft.quarkus.pha.model.HelperText.class,
            org.sitenetsoft.quarkus.pha.model.Hero.class,
            org.sitenetsoft.quarkus.pha.model.Hint.class,
            org.sitenetsoft.quarkus.pha.model.Icon.class,
            org.sitenetsoft.quarkus.pha.model.InlineEdit.class,
            org.sitenetsoft.quarkus.pha.model.InputGroup.class,
            org.sitenetsoft.quarkus.pha.model.ItemList.class,
            org.sitenetsoft.quarkus.pha.model.JumpLinkItem.class,
            org.sitenetsoft.quarkus.pha.model.JumpLinks.class,
            org.sitenetsoft.quarkus.pha.model.Label.class,
            org.sitenetsoft.quarkus.pha.model.LabelGroup.class,
            org.sitenetsoft.quarkus.pha.model.Masthead.class,
            org.sitenetsoft.quarkus.pha.model.Menu.class,
            org.sitenetsoft.quarkus.pha.model.MenuItem.class,
            org.sitenetsoft.quarkus.pha.model.MenuToggle.class,
            org.sitenetsoft.quarkus.pha.model.Modal.class,
            org.sitenetsoft.quarkus.pha.model.Nav.class,
            org.sitenetsoft.quarkus.pha.model.NavEntry.class,
            org.sitenetsoft.quarkus.pha.model.NotificationBadge.class,
            org.sitenetsoft.quarkus.pha.model.NotificationDrawer.class,
            org.sitenetsoft.quarkus.pha.model.NumberInput.class,
            org.sitenetsoft.quarkus.pha.model.OverflowMenu.class,
            org.sitenetsoft.quarkus.pha.model.Page.class,
            org.sitenetsoft.quarkus.pha.model.Pagination.class,
            org.sitenetsoft.quarkus.pha.model.Panel.class,
            org.sitenetsoft.quarkus.pha.model.Popover.class,
            org.sitenetsoft.quarkus.pha.model.Progress.class,
            org.sitenetsoft.quarkus.pha.model.ProgressStepper.class,
            org.sitenetsoft.quarkus.pha.model.ProgressStepperStep.class,
            org.sitenetsoft.quarkus.pha.model.PropertiesSidePanel.class,
            org.sitenetsoft.quarkus.pha.model.Radio.class,
            org.sitenetsoft.quarkus.pha.model.SearchInput.class,
            org.sitenetsoft.quarkus.pha.model.Sidebar.class,
            org.sitenetsoft.quarkus.pha.model.SimpleList.class,
            org.sitenetsoft.quarkus.pha.model.SimpleListItem.class,
            org.sitenetsoft.quarkus.pha.model.SimpleListSection.class,
            org.sitenetsoft.quarkus.pha.model.Skeleton.class,
            org.sitenetsoft.quarkus.pha.model.SkipToContent.class,
            org.sitenetsoft.quarkus.pha.model.Slider.class,
            org.sitenetsoft.quarkus.pha.model.Spinner.class,
            org.sitenetsoft.quarkus.pha.model.Switch.class,
            org.sitenetsoft.quarkus.pha.model.TabItem.class,
            org.sitenetsoft.quarkus.pha.model.Table.class,
            org.sitenetsoft.quarkus.pha.model.TableAction.class,
            org.sitenetsoft.quarkus.pha.model.TableBody.class,
            org.sitenetsoft.quarkus.pha.model.TableCell.class,
            org.sitenetsoft.quarkus.pha.model.TableColumn.class,
            org.sitenetsoft.quarkus.pha.model.TableRow.class,
            org.sitenetsoft.quarkus.pha.model.TableTreeNode.class,
            org.sitenetsoft.quarkus.pha.model.Tabs.class,
            org.sitenetsoft.quarkus.pha.model.TextArea.class,
            org.sitenetsoft.quarkus.pha.model.TextInput.class,
            org.sitenetsoft.quarkus.pha.model.TextInputGroup.class,
            org.sitenetsoft.quarkus.pha.model.Tile.class,
            org.sitenetsoft.quarkus.pha.model.Timestamp.class,
            org.sitenetsoft.quarkus.pha.model.Title.class,
            org.sitenetsoft.quarkus.pha.model.ToggleGroup.class,
            org.sitenetsoft.quarkus.pha.model.Toolbar.class,
            org.sitenetsoft.quarkus.pha.model.Tooltip.class,
            org.sitenetsoft.quarkus.pha.model.TreeView.class,
            org.sitenetsoft.quarkus.pha.model.TreeViewItem.class,
            org.sitenetsoft.quarkus.pha.model.Truncate.class,
            org.sitenetsoft.quarkus.pha.model.VerticalTabs.class,
            org.sitenetsoft.quarkus.pha.model.Wizard.class,
            org.sitenetsoft.quarkus.pha.model.Menu.Group.class);

    /** Builder fields that are deliberately NOT part of the JSON contract (internal wiring). */
    private static final Set<String> EXEMPT_FIELDS = Set.of(
            // body/stripe accumulation internals — JSON binds the friendly "rows" alias instead
            "Table.doneBodies", "Table.currentStripe", "Table.currentRows",
            // computed by Table.build() from treeNodes / favoritesSortable — never JSON inputs
            "Table.resolvedTreeRows", "Table.treeRootXData", "Table.treeBodyXData", "Table.rootXData");

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void every_model_resolves_a_deserializer() {
        List<String> broken = new ArrayList<>();
        for (Class<?> c : MODELS) {
            try {
                mapper.readValue("{}", c);
            } catch (InvalidDefinitionException e) {
                broken.add(c.getSimpleName() + ": " + e.getOriginalMessage());
            } catch (Exception expected) {
                // required-field/build() rejections of an empty object are fine
            }
        }
        assertTrue(broken.isEmpty(), () -> String.join("\n", broken));
    }

    @Test
    void every_builder_field_is_bindable() {
        List<String> gaps = new ArrayList<>();
        for (Class<?> c : MODELS) {
            Class<?> builder = Arrays.stream(c.getDeclaredClasses())
                    .filter(n -> n.getSimpleName().equals("Builder")).findFirst().orElse(null);
            if (builder == null) {
                continue;
            }
            for (Field f : builder.getDeclaredFields()) {
                if (Modifier.isStatic(f.getModifiers())) {
                    continue;
                }
                String key = c.getSimpleName() + "." + f.getName();
                if (EXEMPT_FIELDS.contains(key)) {
                    continue;
                }
                boolean bindable = Arrays.stream(builder.getDeclaredMethods())
                        .anyMatch(m -> m.getName().equals(f.getName())
                                && m.getParameterCount() == 1
                                && Modifier.isPublic(m.getModifiers()));
                if (!bindable) {
                    gaps.add(key);
                }
            }
        }
        assertTrue(gaps.isEmpty(), () -> gaps.size() + " unbindable builder fields:\n" + String.join("\n", gaps));
    }

    @Test
    void every_non_builder_model_has_a_creator() {
        List<String> missing = new ArrayList<>();
        for (Class<?> c : MODELS) {
            boolean hasBuilder = c.isAnnotationPresent(JsonDeserialize.class)
                    && Arrays.stream(c.getDeclaredClasses()).anyMatch(n -> n.getSimpleName().equals("Builder"));
            boolean fieldBound = c.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonAutoDetect.class);
            if (hasBuilder || fieldBound || c.isEnum()) {
                continue;
            }
            boolean creator = false;
            for (Constructor<?> k : c.getDeclaredConstructors()) {
                if (k.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonCreator.class)) {
                    creator = true;
                }
            }
            for (Method m : c.getDeclaredMethods()) {
                if (m.isAnnotationPresent(com.fasterxml.jackson.annotation.JsonCreator.class)) {
                    creator = true;
                }
            }
            if (!creator) {
                missing.add(c.getSimpleName());
            }
        }
        assertTrue(missing.isEmpty(), () -> "models without a @JsonCreator:\n" + String.join("\n", missing));
    }
}
