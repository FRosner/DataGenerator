package de.frosner.datagenerator.gui.main;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.List;

import com.google.common.collect.Lists;

import de.frosner.datagenerator.exceptions.NoEnabledComponentInFocusOrderException;

/**
 * Class providing an ordered focus traversal policy. Add components to traverse in an ordered list. The policy will
 * traverse the list. Components that are disabled are skipped. At least one component must be enabled.
 * 
 * @throws NoEnabledComponentInFocusOrderException
 *             if not at least one component in the provided order is enabled
 */
public final class OrderedFocusTraversalPolicy extends FocusTraversalPolicy {

	private List<Component> _order;

	/**
	 * Creates a new {@linkplain OrderedFocusTraversalPolicy} with the specified component ordering.
	 * 
	 * @param order
	 *            of the components to focus
	 * 
	 * @throws NoEnabledComponentInFocusOrderException
	 *             if not at least one component in the provided order is enabled
	 */
	public OrderedFocusTraversalPolicy(List<Component> order) {
		_order = Lists.newArrayList(order);
		checkThatAtLeastOneComponentIsEnabled();
	}

	/**
	 * @throws NoEnabledComponentInFocusOrderException
	 *             if not at least one component in the provided order is enabled
	 */
	@Override
	public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
		checkThatAtLeastOneComponentIsEnabled();
		Component nextComponent = _order.get((_order.indexOf(aComponent) + 1) % _order.size());
		if (nextComponent.isEnabled()) {
			return nextComponent;
		}
		return getComponentAfter(focusCycleRoot, nextComponent);
	}

	/**
	 * @throws NoEnabledComponentInFocusOrderException
	 *             if not at least one component in the provided order is enabled
	 */
	@Override
	public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
		checkThatAtLeastOneComponentIsEnabled();
		int tabIndex = _order.indexOf(aComponent) - 1;
		if (tabIndex < 0) {
			tabIndex = _order.size() - 1;
		}
		Component nextComponent = _order.get(tabIndex);
		if (nextComponent.isEnabled()) {
			return nextComponent;
		}
		return getComponentBefore(focusCycleRoot, nextComponent);
	}

	/**
	 * @throws NoEnabledComponentInFocusOrderException
	 *             if not at least one component in the provided order is enabled
	 */
	@Override
	public Component getDefaultComponent(Container focusCycleRoot) {
		checkThatAtLeastOneComponentIsEnabled();
		return getFirstComponent(focusCycleRoot);
	}

	/**
	 * @throws NoEnabledComponentInFocusOrderException
	 *             if not at least one component in the provided order is enabled
	 */
	@Override
	public Component getLastComponent(Container focusCycleRoot) {
		checkThatAtLeastOneComponentIsEnabled();
		Component lastComponent = _order.get(_order.size() - 1);
		if (lastComponent.isEnabled()) {
			return lastComponent;
		}
		return getComponentBefore(focusCycleRoot, lastComponent);
	}

	/**
	 * @throws NoEnabledComponentInFocusOrderException
	 *             if not at least one component in the provided order is enabled
	 */
	@Override
	public Component getFirstComponent(Container focusCycleRoot) {
		checkThatAtLeastOneComponentIsEnabled();
		Component firstComponent = _order.get(0);
		if (firstComponent.isEnabled()) {
			return firstComponent;
		}
		return getComponentAfter(focusCycleRoot, firstComponent);
	}

	private void checkThatAtLeastOneComponentIsEnabled() {
		boolean atLeastOneComponentIsEnabled = false;
		for (Component component : _order) {
			if (component.isEnabled()) {
				atLeastOneComponentIsEnabled = true;
			}
		}
		if (!atLeastOneComponentIsEnabled) {
			throw new NoEnabledComponentInFocusOrderException(_order);
		}
	}
}