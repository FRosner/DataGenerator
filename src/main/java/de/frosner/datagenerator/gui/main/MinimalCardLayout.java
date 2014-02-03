package de.frosner.datagenerator.gui.main;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Collection;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import de.frosner.datagenerator.util.ApplicationMetaData;

/**
 * {@linkplain CardLayout} adjusting its preferred size to the size of the currently visible card. The normal behavior
 * is to use the maximum size of all cards.
 */
public class MinimalCardLayout extends CardLayout {

	private static final long serialVersionUID = ApplicationMetaData.SERIAL_VERSION_UID;

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Component visibleCard = getVisibleCard(parent);
		if (visibleCard != null) {
			Insets insets = parent.getInsets();
			Dimension preferredSize = visibleCard.getPreferredSize();
			preferredSize.width += insets.left + insets.right;
			preferredSize.height += insets.top + insets.bottom;
			return preferredSize;
		}
		return super.preferredLayoutSize(parent);
	}

	private Component getVisibleCard(Container parent) {
		Collection<Component> visibleCards = Lists.newArrayList();
		for (Component component : parent.getComponents()) {
			if (component.isVisible()) {
				visibleCards.add(component);
			}
		}

		return (visibleCards.isEmpty()) ? null : Iterables.getOnlyElement(visibleCards);
	}

}
