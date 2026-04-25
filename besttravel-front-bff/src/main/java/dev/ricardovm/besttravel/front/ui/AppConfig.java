package dev.ricardovm.besttravel.front.ui;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.shared.ui.Transport;
import com.vaadin.flow.theme.aura.Aura;

@StyleSheet(Aura.STYLESHEET)
@StyleSheet("styles.css")
@Push(transport = Transport.LONG_POLLING)
public class AppConfig implements AppShellConfigurator {
}
